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
package gplx.xowa.html.portal; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import org.junit.*;
public class Xoh_rtl_utl_tst {
	@Before public void init() {fxt.Init();} private Xoh_rtl_utl_fxt fxt = new Xoh_rtl_utl_fxt();
	@Test  public void Basic() {
		fxt.Test_reverse_li("<ul><li>a</li><li>b</li></ul>", "<ul><li>b</li><li>a</li></ul>");
	}
	@Test  public void Zero() {
		fxt.Test_reverse_li("a", "a");
	}
	@Test  public void One() {
		fxt.Test_reverse_li("<ul><li>a</li></ul>", "<ul><li>a</li></ul>");
	}
	@Test  public void Example() {
		fxt.Test_reverse_li(String_.Concat_lines_nl_skip_last
		( "<div>"
		, "  <ul>"
		, "    <li>a"
		, "    </li>"
		, "    <li>b"
		, "    </li>"
		, "    <li>c"
		, "    </li>"
		, "  </ul>"
		, "</div>"
		), String_.Concat_lines_nl_skip_last
		( "<div>"
		, "  <ul>"
		, "    <li>c"
		, "    </li>"
		, "    <li>b"
		, "    </li>"
		, "    <li>a"
		, "    </li>"
		, "  </ul>"
		, "</div>"
		));
	}
}
class Xoh_rtl_utl_fxt {
	public void Init() {
	}
	public void Test_reverse_li(String raw, String expd) {
		byte[] actl = Xoh_rtl_utl.Reverse_li(Bry_.new_utf8_(raw));
		Tfds.Eq_str_lines(expd, String_.new_utf8_(actl));
	}
}
