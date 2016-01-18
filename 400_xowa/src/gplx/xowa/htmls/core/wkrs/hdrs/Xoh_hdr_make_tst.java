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
package gplx.xowa.htmls.core.wkrs.hdrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.makes.tests.*;
public class Xoh_hdr_make_tst {
	private final Xoh_make_fxt fxt = new Xoh_make_fxt();
	@Test   public void Basic() {
		String html = String_.Concat_lines_nl_skip_last
		( "z"
		, "<h2><span class='mw-headline' id='A_1'>A 1</span></h2>"
		, "a 1"
		, "<h2><span class='mw-headline' id='B'>B</span></h2>"
		, "b"
		);
		fxt.Test__make(html, fxt.Page_chkr().Body_(html)	// make sure body is same
			.Sections__add(0, 2, ""		, ""	, "z")
			.Sections__add(1, 2, "A_1"	, "A 1"	, "a 1")
			.Sections__add(2, 2, "B"	, "B"	, "b")
		);
	}
	@Test   public void Consecutive() {
		String html = String_.Concat_lines_nl_skip_last
		( "abc"
		, "<h2><span class='mw-headline' id='A'>A</span></h2>"
		, "<h2><span class='mw-headline' id='B'>B</span></h2>"
		, "xyz"
		);
		fxt.Test__make(html, fxt.Page_chkr().Body_(html)	// make sure body is same
			.Sections__add(0, 2, ""		, ""	, "abc")
			.Sections__add(1, 2, "A"	, "A"	, "")
			.Sections__add(2, 2, "B"	, "B"	, "xyz")
		);
	}
}
