/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.langs.dsvs; import gplx.*; import gplx.langs.*;
import gplx.core.strings.*; import gplx.core.gfo_ndes.*; import gplx.core.stores.*;
public class DsvDataWtr extends DataWtr_base implements DataWtr {
	public void InitWtr(String key, Object val) {
		if (key == DsvStoreLayout.Key_const) layout = (DsvStoreLayout)val;
	}
	@Override public void WriteData(String name, Object val)		{sb.WriteFld(val == null ? null : val.toString());}
	public void WriteLeafBgn(String leafName)					{}
	public void WriteLeafEnd()									{sb.WriteRowSep();}
	@Override public void WriteNodeBgn(String name)				{WriteTableBgn(name, GfoFldList_.Null);}
	public void WriteTableBgn(String name, GfoFldList flds) {
		for (int i = 0; i < layout.HeaderList().Count(); i++) {
			DsvHeaderItm data = layout.HeaderList().Get_at(i);
			int id = data.Id();				
			if		(id == DsvHeaderItm.Id_TableName)	WriteTableName(name);
			else if (id == DsvHeaderItm.Id_LeafNames)	WriteMeta(flds, true, sym.FldNamesSym());
			else if (id == DsvHeaderItm.Id_LeafTypes)	WriteMeta(flds, false, sym.FldTypesSym());
			else if (id == DsvHeaderItm.Id_BlankLine)	sb.WriteRowSep();
			else if (id == DsvHeaderItm.Id_Comment)		WriteComment(data.Val().toString());
		}
	}
	@Override public void WriteNodeEnd()							{}
	public void Clear() {sb.Clear();}
	public String To_str() {return sb.To_str();}
	void WriteTableName(String tableName) {
		sb.WriteFld(tableName);
		sb.WriteCmd(sym.TblNameSym());
		sb.WriteRowSep();
	}
	void WriteMeta(GfoFldList flds, boolean isName, String cmd) {
		for (int i = 0; i < flds.Count(); i++) {
			GfoFld fld = flds.Get_at(i);
			String val = isName ? fld.Key(): fld.Type().Key();
			sb.WriteFld(val);
		}
		if (layout.WriteCmdSequence()) sb.WriteCmd(cmd);
		sb.WriteRowSep();
	}
	void WriteComment(String comment) {
		sb.WriteFld(comment);
		sb.WriteCmd(sym.CommentSym());
		sb.WriteRowSep();
	}
	@Override public SrlMgr SrlMgr_new(Object o) {return new DsvDataWtr();}
	DsvStringBldr sb; DsvSymbols sym = DsvSymbols.default_(); DsvStoreLayout layout = DsvStoreLayout.csv_dat_();
	@gplx.Internal protected DsvDataWtr() {sb = DsvStringBldr.new_(sym);}
}
class DsvStringBldr {
	public void Clear() {sb.Clear();}
	public String To_str() {return sb.To_str();}
	public void WriteCmd(String cmd) {
		WriteFld(sym.CmdSequence(), true);
		WriteFld(cmd);
	}
	public void WriteFldSep() {sb.Add(sym.FldSep());}
	public void WriteRowSep() {
		sb.Add(sym.RowSep());
		isNewRow = true;
	}
	public void WriteFld(String val) {WriteFld(val, false);}
	void WriteFld(String val, boolean writeRaw) {
		if (isNewRow)							// if isNewRow, then fld is first, and no fldSpr needed (RowSep serves as fldSpr)
			isNewRow = false;
		else
			sb.Add(sym.FldSep());

		if (val == null) {}						// null -> append nothing
		else if (String_.Eq(val, String_.Empty))// "" -> append ""
			sb.Add("\"\"");
		else if (writeRaw)						// only cmds should be writeRaw (will append  ," ")
			sb.Add(val);
		else {									// escape as necessary; ex: "the quote "" char"; "the comma , char"
			boolean quoteField = false;
			if (String_.Has(val, sym.QteDlm())) {
				val = String_.Replace(val, "\"", "\"\"");
				quoteField = true;
			}
			else if (String_.Has(val, sym.FldSep()))
				quoteField = true;
			else if (sym.RowSepIsNewLine() 
				&& (String_.Has(val, "\n") || String_.Has(val, "\r")))
				quoteField = true;
			else if (String_.Has(val, sym.RowSep()))
				quoteField = true;

			if (quoteField) sb.Add(sym.QteDlm());
			sb.Add(val);
			if (quoteField) sb.Add(sym.QteDlm());
		}
	}

	String_bldr sb = String_bldr_.new_(); DsvSymbols sym; boolean isNewRow = true;
	public static DsvStringBldr new_(DsvSymbols sym) {
		DsvStringBldr rv = new DsvStringBldr();
		rv.sym = sym;
		return rv;
	}	DsvStringBldr() {}
}
