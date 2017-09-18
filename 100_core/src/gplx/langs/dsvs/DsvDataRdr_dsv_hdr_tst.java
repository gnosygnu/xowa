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
public class DsvDataRdr_dsv_hdr_tst {
	@Before public void setup() {fx.Clear();} DsvDataRdr_fxt fx = DsvDataRdr_fxt.new_();
	@Test  public void Names() {
		fx.run_parse_lines_
			(	"id,name, ,\" \",@"
			,	"0,me"
			,	"1,you"
			);
		fx.tst_Flds(0, GfoFldList_.str_("id", "name"));
		fx.tst_Tbls(DsvTblBldr.NullTblName);
		fx.tst_Dat(0
			,	fx.ary_("0", "me")
			,	fx.ary_("1", "you")
			);
	}
	@Test  public void Types() {
		fx.run_parse_lines_
			(	"int," + StringClassXtn.Key_const + ", ,\" \",$"
			,	"0,me"
			,	"1,you"
			);
		fx.tst_Flds(0, GfoFldList_.new_().Add("fld0", IntClassXtn.Instance).Add("fld1", StringClassXtn.Instance));
		fx.tst_Dat(0
			,	fx.ary_(0, "me")
			,	fx.ary_(1, "you")
			);
	}
	@Test  public void NamesAndTypes() {
		fx.run_parse_lines_
			(	"id,name, ,\" \",@"
			,	"int," + StringClassXtn.Key_const + ", ,\" \",$"
			,	"0,me"
			,	"1,you"
			);
		fx.tst_Flds(0, GfoFldList_.new_().Add("id", IntClassXtn.Instance).Add("name", StringClassXtn.Instance));
		fx.tst_Dat(0
			,	fx.ary_(0, "me")
			,	fx.ary_(1, "you")
			);
	}
	@Test  public void MultipleTables_NoData() {
		fx.run_parse_lines_
			(	"persons, ,\" \",#"
			,	"id,name, ,\" \",@"
			,	"things, ,\" \",#"
			,	"id,data, ,\" \",@"
			);
		fx.tst_Tbls("persons", "things");
		fx.tst_Flds(0, GfoFldList_.str_("id", "name"));
		fx.tst_Flds(1, GfoFldList_.str_("id", "data"));
		fx.tst_Dat(0);
		fx.tst_Dat(1);
	}
	@Test  public void Comment() {
		fx.run_parse_lines_
			(	"--------------------, ,\" \",//"
			,	"tbl0, ,\" \",#"
			,	"a0,a1"
			);
		fx.tst_Tbls("tbl0");
		fx.tst_Dat(0, fx.ary_("a0", "a1"));
	}
}
