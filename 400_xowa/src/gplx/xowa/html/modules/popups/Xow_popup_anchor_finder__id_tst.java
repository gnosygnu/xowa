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
package gplx.xowa.html.modules.popups; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.modules.*;
import org.junit.*;
import gplx.xowa.apis.xowa.html.modules.*;
import gplx.xowa.gui.views.*;
public class Xow_popup_anchor_finder__id_tst {
	@Before public void init() {fxt.Clear();} private Xop_popup_hdr_finder_fxt fxt = new Xop_popup_hdr_finder_fxt();
	@Test   public void Basic() {
		String src_str = String_.Concat_lines_nl_skip_last
		( "b"
		, "<span id=\"a\"/>"
		, "c"
		);
		fxt.Test_find(src_str, "a",  1);
		fxt.Test_find_not(src_str, "b");
		fxt.Test_find_not(src_str, "c");
	}
	@Test   public void Ws() {
		String src_str = String_.Concat_lines_nl_skip_last
		( "b"
		, "<span id = \"a\"/>"
		, "c"
		);
		fxt.Test_find(src_str, "a",  1);
	}
	@Test   public void Fail() {
		String src_str = String_.Concat_lines_nl_skip_last
		( "b"
		, "<span xid = \"a\"/>"
		, "c"
		);
		fxt.Test_find_not(src_str, "a");
	}
}
