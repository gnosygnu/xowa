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
package gplx.xowa.html.xouis.fmtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.xouis.*;
import gplx.xowa.html.xouis.tbls.*;
import org.junit.*;
public class Xoui_tbl_fmtr_tst {
	@Before public void init() {fxt.Clear();} private final Xoui_tbl_fmtr_fxt fxt = new Xoui_tbl_fmtr_fxt();
	@Test  public void Basic() {
//			fxt.Test_write
//			( fxt.Make_tbl()
//			, String_.Concat_lines_nl_skip_last()
//			);
	}
}
class Xoui_tbl_fmtr_fxt {
	private final Bry_bfr bfr = Bry_bfr.new_(255);
	private final Xoui_tbl_fmtr tbl_fmtr = new Xoui_tbl_fmtr();
	public void Clear() {}
	public Xoui_tbl_itm Make_tbl() {
		return null;
	}
	public void Test_write(Xoui_tbl_itm tbl, String expd) {
		tbl_fmtr.Write(bfr, tbl);
		Tfds.Eq_str_lines(expd, bfr.Xto_str_and_clear());
	}
}
