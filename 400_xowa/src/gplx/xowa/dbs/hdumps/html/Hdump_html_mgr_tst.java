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
package gplx.xowa.dbs.hdumps.html; import gplx.*; import gplx.xowa.*; import gplx.xowa.dbs.*; import gplx.xowa.dbs.hdumps.*;
import org.junit.*;
public class Hdump_html_mgr_tst {
	@Before public void init() {fxt.Clear();} private Hdump_html_mgr_fxt fxt = new Hdump_html_mgr_fxt();
	@Test  public void Basic() {
		// fxt.Test_save("A b c", fxt.itm_text_("A b c"));
	}
}
class Hdump_html_mgr_fxt {
	public void Clear() {
	}
}
