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
import org.junit.*;
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
