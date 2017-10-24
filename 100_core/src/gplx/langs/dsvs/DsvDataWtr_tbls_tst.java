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
import org.junit.*; import gplx.core.gfo_ndes.*;
public class DsvDataWtr_tbls_tst {
	@Before public void setup() {
		DsvStoreLayout layout = DsvStoreLayout.dsv_brief_();
		layout.HeaderList().Add_TableName();
		wtr.InitWtr(DsvStoreLayout.Key_const, layout);
	}
	@Test  public void Rows_0() {
		root = fx_nde.tbl_("tbl0");
		expd = String_.Concat_lines_crlf(	"tbl0, ,\" \",#");
		fx.tst_XtoStr(wtr, root, expd);
	}
	@Test  public void Rows_N() {
		root = fx_nde.tbl_
			(	"numbers"
			,	fx_nde.row_vals_(1, 2, 3)
			,	fx_nde.row_vals_(4, 5, 6)
			);
		expd = String_.Concat_lines_crlf
			(	"numbers, ,\" \",#"
			,	"1,2,3"
			,	"4,5,6"
			);
		fx.tst_XtoStr(wtr, root, expd);
	}
	@Test  public void Tbls_N_Empty() {
		root = fx_nde.root_
			(	fx_nde.tbl_("tbl0")
			,	fx_nde.tbl_("tbl1")
			);
		expd = String_.Concat_lines_crlf
			(	"tbl0, ,\" \",#"
			,	"tbl1, ,\" \",#"
			);
		fx.tst_XtoStr(wtr, root, expd);
	}
	@Test  public void Tbls_N() {
		root = fx_nde.root_
			(	fx_nde.tbl_("letters"
			,		fx_nde.row_vals_("a", "b", "c"))
			,	fx_nde.tbl_("numbers"
			,		fx_nde.row_vals_(1, 2, 3)
			,		fx_nde.row_vals_(4, 5, 6)
			));
		expd = String_.Concat_lines_crlf
			(	"letters, ,\" \",#"
			,	"a,b,c"
			,	"numbers, ,\" \",#"
			,	"1,2,3"
			,	"4,5,6"
			);
		fx.tst_XtoStr(wtr, root, expd);
	}
	GfoNde root; String expd; DsvDataWtr wtr = DsvDataWtr_.csv_dat_(); DsvDataWtr_fxt fx = DsvDataWtr_fxt.new_(); GfoNdeFxt fx_nde = GfoNdeFxt.new_(); 
}
