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
package gplx.xowa.parsers.hdrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_hdr_wkr__div_wrapper_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_y_();} private Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Basic() {	// PURPOSE: basic div_wrapper test; DATE:2015-06-24
		fxt.Wtr_cfg().Hdr__div_wrapper_(Bool_.Y);
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "==a=="
		, "b"
		, "==c=="
		, "d"
		, "==e=="
		, "f"
		), String_.Concat_lines_nl_skip_last
		( "<h2>a</h2>"
		, "<div>"
		, ""
		, "<p>b"
		, "</p>"
		, ""
		, "</div>"
		, "<h2>c</h2>"
		, "<div>"
		, ""
		, "<p>d"
		, "</p>"
		, ""
		, "</div>"
		, "<h2>e</h2>"
		, "<div>"
		, ""
		, "<p>f"
		, "</p>"
		, "</div>"
		));
		fxt.Wtr_cfg().Hdr__div_wrapper_(Bool_.N);
	}
}