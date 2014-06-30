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
package gplx.gfui; import gplx.*;
import org.junit.*;
public class Gfui_tab_itm_data_tst {		
	@Before public void init() {} private Gfui_tab_itm_data_fxt fxt = new Gfui_tab_itm_data_fxt();
	@Test  public void Get_idx_after_closing() {
		fxt.Test_Get_idx_after_closing(0, 1, -1);
		fxt.Test_Get_idx_after_closing(4, 5, 3);
		fxt.Test_Get_idx_after_closing(3, 5, 4);
	}
}
class Gfui_tab_itm_data_fxt {
	public void Test_Get_idx_after_closing(int cur, int len, int expd) {
		Tfds.Eq(expd, Gfui_tab_itm_data.Get_idx_after_closing(cur, len));
	}
}
