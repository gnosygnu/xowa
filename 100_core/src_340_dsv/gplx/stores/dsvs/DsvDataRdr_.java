/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.stores.dsvs; import gplx.*; import gplx.stores.*;
import gplx.core.strings.*;
import gplx.texts.*; /*CharStream*/
public class DsvDataRdr_ {
	public static DataRdr dsv_(String text)										{return DsvParser.dsv_().ParseAsRdr(text);}
	public static DataRdr csv_hdr_(String text)									{return csv_opts_(text, DsvDataRdrOpts.new_().HasHeader_(true));}
	public static DataRdr csv_dat_(String text)									{return csv_opts_(text, DsvDataRdrOpts.new_());}
	public static DataRdr csv_dat_with_flds_(String text, String... flds) {return csv_opts_(text, DsvDataRdrOpts.new_().Flds_(GfoFldList_.str_(flds)));}
	public static DataRdr csv_opts_(String text, DsvDataRdrOpts opts) {
		DsvParser parser = DsvParser.csv_(opts.HasHeader(), opts.Flds()); // NOTE: this sets up the bldr; do not call .Init after this
		parser.Symbols().RowSep_(opts.NewLineSep()).FldSep_(opts.FldSep());

		DataRdr root = parser.ParseAsRdr(text);	// don't return root; callers expect csv to return rdr for rows
		DataRdr csvTable = root.Subs();
		return csvTable.Subs();
	}
}
class DsvParser {
	@gplx.Internal protected DsvSymbols Symbols() {return sym;} DsvSymbols sym = DsvSymbols.default_();
	@gplx.Internal protected void Init() {sb.Clear(); bldr.Init(); qteOn = false;}
	@gplx.Internal protected DataRdr ParseAsRdr(String raw) {return GfoNdeRdr_.root_(ParseAsNde(raw), csvOn);} // NOTE: csvOn means parse; assume manual typed flds not passed in (ex: id,Int)
	@gplx.Internal protected GfoNde ParseAsNde(String raw) {
		if (String_.Eq(raw, "")) return bldr.BldRoot();
		CharStream strm = CharStream.pos0_(raw);
		while (true) {
			if (strm.AtEnd()) {
				ProcessLine(strm, true);
				break;
			}
			if		(qteOn)
				ReadStreamInQte(strm);
			else if (strm.MatchAndMove(sym.QteDlm()))
				qteOn = true;
			else if (strm.MatchAndMove(sym.FldSep()))
				ProcessFld(strm);
			else if (strm.MatchAndMove(sym.RowSep()))
				ProcessLine(strm, false);
			else {
				sb.Add(strm.Cur());
				strm.MoveNext();
			}
		}
		return bldr.BldRoot();
	}
	void ReadStreamInQte(CharStream strm) {
		if (strm.MatchAndMove(sym.QteDlm())) {								// is quote
			if (strm.MatchAndMove(sym.QteDlm()))							// double quote -> quote; "a""
				sb.Add(sym.QteDlm());
			else if (strm.MatchAndMove(sym.FldSep())) {						// closing quote before field; "a",
				ProcessFld(strm);
				qteOn = false;
			}
			else if (strm.MatchAndMove(sym.RowSep()) || strm.AtEnd()) {		// closing quote before record; "a"\r\n
				ProcessLine(strm, false);
				qteOn = false;
			}
			else
				throw Err_.new_("invalid quote in quoted field; quote must be followed by quote, fieldSpr, or recordSpr").Add("sym", strm.Cur()).Add("text", strm.Xto_str_by_pos(strm.Pos() - 10, strm.Pos() + 10));
		}
		else {																// regular char; append and continue
			sb.Add(strm.Cur());
			strm.MoveNext();
		}
	}
	void ProcessFld(CharStream strm) {
		String val = sb.Xto_str_and_clear();
		if (cmdSeqOn) {
			cmdSeqOn = false;
			if (String_.Eq(val, sym.CmdDlm()) && qteOn) {		// 2 cmdDlms in a row; cmdSeq encountered; next fld must be cmdName
				nextValType = ValType_CmdName;
				return;
			}
			tkns.Add(sym.CmdDlm());	// curTkn is not cmdDlm; prevTkn happened to be cmdDlm; add prev to tkns and continue; ex: a, ,b
		}
		if		(String_.Eq(val, sym.CmdDlm()))					// val is cmdDlm; do not add now; wait til next fld to decide
			cmdSeqOn = true;
		else if (nextValType == ValType_Data) {
			if (String_.Len(val) == 0) val = qteOn ? "" : null; // differentiate between null and emptyString; ,, vs ,"",
			tkns.Add(val);
		}
		else if (nextValType == ValType_CmdName) {
			if		(String_.Eq(val, sym.TblNameSym()))		lineMode = LineType_TblBgn;		// #
			else if (String_.Eq(val, sym.FldNamesSym()))	lineMode = LineType_FldNames;	// @
			else if (String_.Eq(val, sym.FldTypesSym()))	lineMode = LineType_FldTypes;	// $
			else if (String_.Eq(val, sym.CommentSym()))		lineMode = LineType_Comment;	// '
			else											throw Err_.new_("unknown dsv cmd").Add("cmd", val);
		}
		else
			throw Err_.new_("unable to process field value").Add("value", val);
	}
	void ProcessLine(CharStream strm, boolean cleanup) {
		if (sb.Count() == 0 && tkns.Count() == 0)
			if (csvOn) {		// csvOn b/c csvMode allows blank lines as empty data
				if (cleanup)	// cleanup b/c blankLine should not be added when called by cleanup, else will always add extra row at end
					return;		// cleanup, so no further action needed; return;
				else
					ProcessFld(strm);
			}
			else
				lineMode = LineType_BlankLine;
		else
			ProcessFld(strm);						// always process fld; either (1) chars waiting in sb "a,b"; or (2) last char was fldSep "a,"
		if (cmdSeqOn) {								// only happens if last fld is comma space (, ); do not let cmds span lines
			cmdSeqOn = false;
			tkns.Add(sym.CmdDlm());
		}
		if		(lineMode == LineType_TblBgn)		bldr.MakeTblBgn(tkns);
		else if (lineMode == LineType_FldNames)		bldr.MakeFldNames(tkns);
		else if (lineMode == LineType_FldTypes)		bldr.MakeFldTypes(tkns);
		else if (lineMode == LineType_Comment)		bldr.MakeComment(tkns);
		else if (lineMode == LineType_BlankLine)	bldr.MakeBlankLine();
		else										bldr.MakeVals(tkns);
		nextValType = ValType_Data;
		lineMode = LineType_Data;
	}
	String_bldr sb = String_bldr_.new_(); ListAdp tkns = ListAdp_.new_(); DsvTblBldr bldr = DsvTblBldr.new_();
	boolean cmdSeqOn = false, qteOn = false, csvOn = false;
	int nextValType = ValType_Data, lineMode = LineType_Data;		
	@gplx.Internal protected static DsvParser dsv_() {return new DsvParser();}
	@gplx.Internal protected static DsvParser csv_(boolean hasHdr, GfoFldList flds) {
		DsvParser rv = new DsvParser();
		rv.csvOn = true;
		rv.lineMode = hasHdr ? LineType_FldNames : LineType_Data;
		ListAdp names = ListAdp_.new_(), types = ListAdp_.new_();
		for (int i = 0; i < flds.Count(); i++) {
			GfoFld fld = flds.FetchAt(i);
			names.Add(fld.Key()); types.Add(fld.Type().Key());
		}
		rv.bldr.MakeFldNames(names); rv.bldr.MakeFldTypes(types);
		return rv;
	}
	static final int ValType_Data = 0, ValType_CmdName = 1;
	static final int LineType_Data = 0, LineType_Comment = 1, LineType_TblBgn = 2, LineType_FldNames = 3, LineType_FldTypes = 4, LineType_BlankLine = 5;
}
class DsvTblBldr {
	public void Init() {
		root = GfoNde_.root_(); tbl = GfoNde_.tbl_(NullTblName, GfoFldList_.new_());
		fldNames.Clear(); fldTypes.Clear();
		stage = Stage_Init;
	}
	public GfoNde BldRoot() {
		if (stage != Stage_Init) CreateTbl();	// CreateTbl if HDR or ROW is in progress
		return root;
	}
	public void MakeTblBgn(ListAdp tkns) {
		if (stage != Stage_Init) CreateTbl();	// CreateTbl if HDR or ROW is in progress
		tbl.Name_((String)tkns.FetchAt(0));
		layout.HeaderList().Add_TableName();
		stage = Stage_Hdr; tkns.Clear();
	}
	public void MakeFldNames(ListAdp tkns) {
		if (stage == Stage_Row) CreateTbl();	// CreateTbl if ROW is in progress; NOTE: exclude HDR, as first HDR would have called CreateTbl
		fldNames.Clear();
		for (Object fldNameObj : tkns)
			fldNames.Add(fldNameObj);
		layout.HeaderList().Add_LeafNames();
		stage = Stage_Hdr; tkns.Clear();
	}
	public void MakeFldTypes(ListAdp tkns) {
		if (stage == Stage_Row) CreateTbl();	// CreateTbl if ROW is in progress; NOTE: exclude HDR, as first HDR would have called CreateTbl
		fldTypes.Clear();
		for (Object fldTypeObj : tkns) {
			ClassXtn type = ClassXtnPool._.FetchOrFail((String)fldTypeObj);
			fldTypes.Add(type);
		}
		layout.HeaderList().Add_LeafTypes();
		stage = Stage_Hdr; tkns.Clear();
	}
	public void MakeComment(ListAdp tkns) {			
		if (stage == Stage_Row)				// comments in ROW; ignore; NOTE: tkns.Clear() could be merged, but this seems clearer
			tkns.Clear();
		else {								// comments in HDR
			String_bldr sb = String_bldr_.new_();
			for (int i = 0; i < tkns.Count(); i++)
				sb.Add((String)tkns.FetchAt(i));
			layout.HeaderList().Add_Comment(sb.XtoStr());
			tkns.Clear();
		}
	}
	public void MakeBlankLine() {
		if (stage != Stage_Init) CreateTbl();	// CreateTbl if HDR or ROW is in progress;
		layout.HeaderList().Add_BlankLine();
		stage = Stage_Init;						// NOTE: mark stage as INIT;
	}
	public void MakeVals(ListAdp tkns) {
		if (stage != Stage_Row) CreateFlds(tkns.Count());		// stage != Stage_Row means if (noRowsCreated)
		GfoNde row = GfoNde_.vals_(tbl.SubFlds(), MakeValsAry(tkns));
		tbl.Subs().Add(row);
		stage = Stage_Row; tkns.Clear();
	}
	Object[] MakeValsAry(ListAdp tkns) {
		GfoFldList subFlds = tbl.SubFlds(); int subFldsCount = subFlds.Count();
		if (tkns.Count() > subFldsCount) throw Err_.new_("values.Count cannot be greater than fields.Count").Add("values.Count", tkns.Count()).Add("fields.Count", subFldsCount);
		Object[] rv = new Object[subFldsCount];
		for (int i = 0; i < subFldsCount; i++) {
			ClassXtn typx = subFlds.FetchAt(i).Type();
			String val = i < tkns.Count() ? (String)tkns.FetchAt(i) : null;
			rv[i] = typx.ParseOrNull(val);
		}
		return rv;
	}
	void CreateTbl() {
		if (tbl.SubFlds().Count() == 0) CreateFlds(0);		// this check occurs when tbl has no ROW; (TOMB: tdb test fails)
		tbl.EnvVars().Add(DsvStoreLayout.Key_const, layout);
		root.Subs().Add(tbl); // add pending table
		layout = DsvStoreLayout.dsv_brief_();
		tbl = GfoNde_.tbl_(NullTblName, GfoFldList_.new_());
		stage = Stage_Hdr;
	}		
	void CreateFlds(int valCount) {
		int fldNamesCount = fldNames.Count(), fldTypesCount = fldTypes.Count();
		if (fldNamesCount == 0 && fldTypesCount == 0) {			// csv tbls where no names or types, just values
			for (int i = 0; i < valCount; i++)
				tbl.SubFlds().Add("fld" + i, StringClassXtn._);
		}
		else {													// all else, where either names or types is defined
			int maxCount = fldNamesCount > fldTypesCount ? fldNamesCount : fldTypesCount;
			for (int i = 0; i < maxCount; i++) {
				String name = i < fldNamesCount ? (String)fldNames.FetchAt(i) : "fld" + i;
				ClassXtn typx = i < fldTypesCount ? (ClassXtn)fldTypes.FetchAt(i) : StringClassXtn._;
				tbl.SubFlds().Add(name, typx);
			}
		}
	}
	GfoNde root; GfoNde tbl; DsvStoreLayout layout = DsvStoreLayout.dsv_brief_();
	ListAdp fldNames = ListAdp_.new_(); ListAdp fldTypes = ListAdp_.new_(); 
	int stage = Stage_Init;
	public static DsvTblBldr new_() {return new DsvTblBldr();} DsvTblBldr() {this.Init();}
	@gplx.Internal protected static final String NullTblName = "";
	static final int Stage_Init = 0, Stage_Hdr = 1, Stage_Row = 2;
}
