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
import org.junit.*;
public class DsvDataRdr_dsv_dat_tst {
	@Before public void setup() {fx.Clear();} DsvDataRdr_fxt fx = DsvDataRdr_fxt.new_();
	@Test  public void NameOnly() {
		fx.run_parse_("tableName, ,\" \",#");
		fx.tst_Tbls("tableName");
		fx.tst_Dat(0);
	}
	@Test  public void Rows_N() {
		fx.run_parse_lines_
			(	"numbers, ,\" \",#"
			,	"1,2,3"
			,	"4,5,6"
			);
		fx.tst_Tbls("numbers");
		fx.tst_Dat(0
			,	fx.ary_("1", "2", "3")
			,	fx.ary_("4", "5", "6")
			);
	}
	@Test  public void Tbls_N() {
		fx.run_parse_lines_
			(	"letters, ,\" \",#"
			,	"a,b,c"
			,	"numbers, ,\" \",#"
			,	"1,2,3"
			,	"4,5,6"
			);
		fx.tst_Tbls("letters", "numbers");
		fx.tst_Dat(0, fx.ary_("a", "b", "c"));
		fx.tst_Dat(1, fx.ary_("1", "2", "3"), fx.ary_("4", "5", "6"));
	}
	@Test  public void IgnoreTrailingBlankRow() {
		fx.run_parse_lines_
			(	"letters, ,\" \",#"
			,	"a,b,c"
			,	""									// ignored
			);
		fx.tst_Tbls("letters");
		fx.tst_Dat(0, fx.ary_("a", "b", "c"));
	}
	@Test  public void AllowCommentsDuringData() {
		fx.run_parse_lines_
			(	"letters, ,\" \",#"
			,	"a,b,c"
			,	"//	letters omitted, ,\" \",//"		// these comments are not preserved
			,	"x,y,z"
			);
		fx.tst_Tbls("letters");
		fx.tst_Dat(0, fx.ary_("a", "b", "c"), fx.ary_("x", "y", "z"));
	}		
}
