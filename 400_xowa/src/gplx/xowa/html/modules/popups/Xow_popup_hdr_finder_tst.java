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
public class Xow_popup_hdr_finder_tst {
	@Before public void init() {fxt.Clear();} private Xop_popup_hdr_finder_fxt fxt = new Xop_popup_hdr_finder_fxt();
	@Test   public void Basic() {
		String src_str = String_.Concat_lines_nl_skip_last
		( "a"
		, "==b1=="
		, "c"
		);
		fxt.Test_find(src_str, "b1",   2);
		fxt.Test_find_not(src_str, "b");
		fxt.Test_find_not(src_str, "a");
	}
	@Test   public void Mid() {
		String src_str = String_.Concat_lines_nl_skip_last
		( "a"
		, "==b=="
		, "c"
		, "==d=="
		, "e"
		);
		fxt.Test_find(src_str, "d",  10);
	}
	@Test   public void Eos() {
		String src_str = String_.Concat_lines_nl_skip_last
		( "a"
		, "==b=="
		);
		fxt.Test_find(src_str, "b",  2);
	}
	@Test   public void Bos() {
		String src_str = String_.Concat_lines_nl_skip_last
		( "==a=="
		, "b"
		);
		fxt.Test_find(src_str, "a",  0);
	}
}
class Xop_popup_hdr_finder_fxt {
	private Xow_popup_hdr_finder finder = new Xow_popup_hdr_finder();
	public void Clear() {
	}
	public void Test_find_not(String src_str, String hdr_str) {Test_find(src_str, hdr_str, Bry_finder.Not_found);}
	public void Test_find(String src_str, String hdr_str, int expd)  {
		byte[] src = Bry_.new_utf8_(src_str);
		byte[] hdr = Bry_.new_utf8_(hdr_str);
		Tfds.Eq(expd, finder.Find(src, src.length, hdr, 0), hdr_str);
	}
}
