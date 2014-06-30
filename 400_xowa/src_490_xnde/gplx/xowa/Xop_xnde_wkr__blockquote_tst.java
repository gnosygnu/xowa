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
package gplx.xowa; import gplx.*;
import org.junit.*;
public class Xop_xnde_wkr__blockquote_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Pre() { // PURPOSE: preserve leading spaces within blockquote; PAGE:en.w:Tenerife_airport_disaster
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "<blockquote>"
		, " a"
		, "</blockquote>"
		), String_.Concat_lines_nl_skip_last
		( "<blockquote>"
		, " a"
		, "</blockquote>"
		));
		fxt.Init_para_n_();
	}
	@Test  public void Trailing_nls() { // PURPOSE: para/pre not working after blockquote; PAGE:en.w:Snappy_(software); DATE:2014-04-25
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "<blockquote>a"
		, "</blockquote>"
		, ""
		, "b"
		, ""
		, " c"
		), String_.Concat_lines_nl_skip_last
		( "<blockquote>a"
		, "</blockquote>"
		, ""
		, "<p>b"
		, "</p>"
		, ""
		, "<pre>c"
		, "</pre>"
		));
		fxt.Init_para_n_();
	}
	@Test  public void Dangling_multiple() { // PURPOSE: handle multiple dangling; PAGE:en.w:Ring_a_Ring_o'_Roses DATE:2014-06-26
		fxt.Test_parse_page_wiki_str("<blockquote>a<blockquote>b", "<blockquote>a</blockquote><blockquote>b</blockquote>");
	}
}
