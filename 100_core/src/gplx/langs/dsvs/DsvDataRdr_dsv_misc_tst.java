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
import org.junit.*; import gplx.core.gfo_ndes.*; import gplx.core.type_xtns.*;
public class DsvDataRdr_dsv_misc_tst {
	@Before public void setup() {fx.Clear();} DsvDataRdr_fxt fx = DsvDataRdr_fxt.new_();
	@Test  public void CmdDlm_NearMatches() {
		fx.run_parse_("a, ,b");
		fx.tst_DatCsv(fx.ary_("a", " ", "b"));
		fx.Clear();

		fx.run_parse_("a,\" \",b");
		fx.tst_DatCsv(fx.ary_("a", " ", "b"));
		fx.Clear();

		fx.run_parse_("a, ,b,\" \",c");
		fx.tst_DatCsv(fx.ary_("a", " ", "b", " ", "c"));
		fx.Clear();
	}
	@Test  public void CmdDlm_DoNotSpanLines() {
		fx.run_parse_lines_
			(	"a, "
			,	"\" \",b"
			);
		fx.tst_DatCsv
			(	fx.ary_("a", " ")
			,	fx.ary_(" ", "b")
			);
	}
	@Test  public void CmdDlm_SecondFldMustBeQuoted() {
		fx.run_parse_lines_("a, , ,b");	// will fail with "invalid command: b", if second , , is interpreted as command delimiter
		fx.tst_DatCsv(fx.ary_("a", " ", " ", "b"));
	}
	@Test  public void Null_Int() {
		fx.run_parse_	// not using run_parse_lines_ b/c (a) will have extra lineBreak; (b) test will look funny;
			(	"int," + StringClassXtn.Key_const + ", ,\" \",$", String_.CrLf
			,	",val1"
			);
		fx.tst_Tbls(DsvTblBldr.NullTblName);
		fx.tst_Flds(0, GfoFldList_.new_().Add("fld0", IntClassXtn.Instance).Add("fld1", StringClassXtn.Instance));
		fx.tst_Dat(0, fx.ary_(null, "val1"));
	}
	@Test  public void Null_String() {
		fx.run_parse_	// not using run_parse_lines_ b/c (a) will have extra lineBreak; (b) test will look funny;
			(	StringClassXtn.Key_const + "," + StringClassXtn.Key_const + ", ,\" \",$", String_.CrLf
			,	",val1"
			);
		fx.tst_Tbls(DsvTblBldr.NullTblName);
		fx.tst_Flds(0, GfoFldList_.new_().Add("fld0", StringClassXtn.Instance).Add("fld1", StringClassXtn.Instance));
		fx.tst_Dat(0, fx.ary_(null, "val1"));
	}
	@Test  public void EmptyString() {
		fx.run_parse_	// not using run_parse_lines_ b/c (a) will have extra lineBreak; (b) test will look funny;
			(	StringClassXtn.Key_const + "," + StringClassXtn.Key_const + ", ,\" \",$", String_.CrLf
			,	"\"\",val1"
			);
		fx.tst_Tbls(DsvTblBldr.NullTblName);
		fx.tst_Flds(0, GfoFldList_.new_().Add("fld0", StringClassXtn.Instance).Add("fld1", StringClassXtn.Instance));
		fx.tst_Dat(0, fx.ary_("", "val1"));
	}
}
