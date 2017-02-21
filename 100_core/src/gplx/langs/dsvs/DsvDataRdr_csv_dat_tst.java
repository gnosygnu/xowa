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
import org.junit.*; import gplx.core.strings.*; import gplx.core.gfo_ndes.*;
public class DsvDataRdr_csv_dat_tst {
	@Before public void setup() {
		fx.Parser_(DsvParser.csv_(false, GfoFldList_.Null));
		fx.Clear();
	}	DsvDataRdr_fxt fx = DsvDataRdr_fxt.new_();
	@Test  public void Empty() {
		fx.run_parse_("");
		fx.tst_DatNull();
	}
	@Test  public void Fld_0() {
		fx.run_parse_("a");
		fx.tst_DatCsv(fx.ary_("a"));
	}
	@Test  public void Fld_N() {
		fx.run_parse_("a,b,c");
		fx.tst_FldListCsv("fld0", "fld1", "fld2");
		fx.tst_DatCsv(fx.ary_("a", "b", "c"));
	}
	@Test  public void Row_N() {
		fx.run_parse_
			(	"a,b,c", String_.CrLf
			,	"1,2,3"
			);
		fx.tst_DatCsv
			( fx.ary_("a", "b", "c")
			, fx.ary_("1", "2", "3")
			);
	}
	@Test  public void Escape_WhiteSpace() {
		fx.run_parse_("a,\" \t\",c");
		fx.tst_DatCsv(fx.ary_("a", " \t", "c"));
	}
	@Test  public void Escape_FldSep() {
		fx.run_parse_("a,\",\",c");
		fx.tst_DatCsv(fx.ary_("a", ",", "c"));
	}
	@Test  public void Escape_RowSep() {
		fx.run_parse_("a,\"" + String_.CrLf + "\",c");
		fx.tst_DatCsv(fx.ary_("a", String_.CrLf, "c"));
	}
	@Test  public void Escape_Quote() {
		fx.run_parse_("a,\"\"\"\",c");
		fx.tst_DatCsv(fx.ary_("a", "\"", "c"));
	}
	@Test  public void Blank_Null() {
		fx.run_parse_("a,,c");
		fx.tst_DatCsv(fx.ary_("a", null, "c"));
	}
	@Test  public void Blank_EmptyString() {
		fx.run_parse_("a,\"\",c");
		fx.tst_DatCsv(fx.ary_("a", "", "c"));
	}
	@Test  public void Blank_Null_Multiple() {
		fx.run_parse_(",,");
		fx.tst_DatCsv(fx.ary_(null, null, null));
	}
	@Test  public void TrailingNull() {
		fx.run_parse_("a,");
		fx.tst_DatCsv(fx.ary_("a", null));
	}
	@Test  public void TrailingEmpty() {
		fx.run_parse_("a,\"\"");
		fx.tst_DatCsv(fx.ary_("a", ""));
	}
	@Test  public void Quote_Error() {
		try {
			fx.run_parse_("a,\"\" ,c");
			Tfds.Fail_expdError();
		}
		catch (Err e) {
    			Tfds.Eq_true(String_.Has(Err_.Message_lang(e), "invalid quote in quoted field"));
		}
	}
	@Test  public void Misc_AllowValsLessThanFields() {
		// assume null when vals.Count < fields.Count; PURPOSE: MsExcel will not save trailing commas for csvExport; ex: a, -> a
		fx.run_parse_
			( "a0,a1", String_.CrLf
			, "b0"
			);
		fx.tst_DatCsv
			( fx.ary_("a0", "a1")
			, fx.ary_("b0", null)
			);
	}
	@Test  public void Misc_NewLineValidForSingleColumnTables() {
		fx.run_parse_
			( "a", String_.CrLf
			, String_.CrLf
			, "c" , String_.CrLf
			, String_.CrLf
			);
		fx.tst_DatCsv
			( fx.ary_("a")
			, fx.ary_null_()
			, fx.ary_("c")
			, fx.ary_null_()
			);
	}
	@Test  public void Misc_NewLineValidForSingleColumnTables_FirstLine() {
		fx.run_parse_
			( String_.CrLf
			, "b", String_.CrLf
			, "c"
			);
		fx.tst_DatCsv
			( fx.ary_null_()
			, fx.ary_("b")
			, fx.ary_("c")
			);
	}
	@Test  public void Hdr_Basic() {
		fx.Parser_(DsvParser.csv_(true, GfoFldList_.Null));
		fx.run_parse_
			(	"id,name", String_.CrLf
			,	"0,me"
			);
		fx.tst_FldListCsv("id", "name");
		fx.tst_DatCsv(fx.ary_("0", "me"));
	}
//		@Test  public void Hdr_Manual() {
//			fx.Parser_(DsvParser.csv_(false, GfoFldList_.new_().Add("id", IntClassXtn.Instance).Add("name", StringClassXtn.Instance), true));
//			fx.run_parse_("0,me"); 
//			fx.tst_DatCsv(fx.ary_(0, "me"));	// NOTE: testing auto-parsing of id to int b/c id fld is IntClassXtn.Instance;
//		}
}
class DsvDataRdr_fxt {
	public Object[] ary_(Object... ary) {return ary;}
	public Object[] ary_null_() {return new Object[] {null};}
	public void Clear() {parser.Init(); root = null;}
	public DsvParser Parser() {return parser;} public DsvDataRdr_fxt Parser_(DsvParser val) {parser = val; return this;} DsvParser parser = DsvParser.dsv_();
	public GfoNde Root() {return root;} GfoNde root;
	public void run_parse_(String... ary)			{root = parser.ParseAsNde(String_.Concat(ary));}
	public void run_parse_lines_(String... ary)	{root = parser.ParseAsNde(String_.Concat_lines_crlf(ary));}
	public DsvDataRdr_fxt tst_FldListCsv(String... names) {return tst_Flds(TblIdx0, GfoFldList_.str_(names));}
	public DsvDataRdr_fxt tst_Flds(int tblIdx, GfoFldList expdFlds) {
		GfoNde tbl = root.Subs().FetchAt_asGfoNde(tblIdx);
		List_adp expdList = List_adp_.New(), actlList = List_adp_.New();
		String_bldr sb = String_bldr_.new_();
		GfoFldList_BldDbgList(expdFlds, expdList, sb);
		GfoFldList_BldDbgList(tbl.SubFlds(), actlList, sb);
		Tfds.Eq_list(expdList, actlList);
		return this;
	}
	void GfoFldList_BldDbgList(GfoFldList flds, List_adp list, String_bldr sb) {
		for (int i = 0; i < flds.Count(); i++) {
			GfoFld fld = flds.Get_at(i);
			sb.Add(fld.Key()).Add(",").Add(fld.Type().Key());
			list.Add(sb.To_str_and_clear());
		}
	}
	public DsvDataRdr_fxt tst_Tbls(String... expdNames) {
		List_adp actlList = List_adp_.New();
		for (int i = 0; i < root.Subs().Count(); i++) {
			GfoNde tbl = root.Subs().FetchAt_asGfoNde(i);
			actlList.Add(tbl.Name());
		}
		Tfds.Eq_ary(expdNames, actlList.To_str_ary());
		return this;
	}
	public DsvDataRdr_fxt tst_DatNull() {
		Tfds.Eq(0, root.Subs().Count());
		return this;
	}
	public DsvDataRdr_fxt tst_DatCsv(Object[]... expdRows) {return tst_Dat(0, expdRows);}
	public DsvDataRdr_fxt tst_Dat(int tblIdx, Object[]... expdRows) {
		GfoNde tbl = root.Subs().FetchAt_asGfoNde(tblIdx);
		if (expdRows.length == 0) {
			Tfds.Eq(0, tbl.Subs().Count());
			return this;
		}
		List_adp expdList = List_adp_.New(), actlList = List_adp_.New();
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < tbl.Subs().Count(); i++) {
			GfoNde row = tbl.Subs().FetchAt_asGfoNde(i);
			for (int j = 0; j < row.Flds().Count(); j++)  {
				if (j != 0) sb.Add("~");
				sb.Add_obj(Object_.Xto_str_strict_or_null_mark(row.ReadAt(j)));
			}
			expdList.Add(sb.To_str_and_clear());
		}
		for (Object[] expdRow : expdRows) {
			if (expdRow == null) {
				actlList.Add("");
				continue;
			}
			for (int j = 0; j < expdRow.length; j++) {
				if (j != 0) sb.Add("~");
				sb.Add_obj(Object_.Xto_str_strict_or_null_mark(expdRow[j]));
			}
			actlList.Add(sb.To_str_and_clear());
		}
		Tfds.Eq_list(expdList, actlList);
		return this;
	}
	public static DsvDataRdr_fxt new_() {return new DsvDataRdr_fxt();}
	static final    int TblIdx0 = 0;
}
