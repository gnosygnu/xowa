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
package gplx.xowa.xtns.cite; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class References_nde_pre_tst {	
	@Before public void init() {fxt.Clear_ref_mgr();} private Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Pre_ignored() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "<ref> x</ref>"
			, "<references/>"
			), String_.Concat_lines_nl_skip_last
			( "<sup id=\"cite_ref-0\" class=\"reference\"><a href=\"#cite_note-0\">[1]</a></sup>"
			, "<ol class=\"references\">"
			, "<li id=\"cite_note-0\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-0\">^</a></span> <span class=\"reference-text\"> x</span></li>"
			, "</ol>"
			, ""
			));
	}
	@Test  public void Pre_ignored_2() {	// PURPOSE: <ref> creates <li> which will effectively disable all pre; PAGE:en.w:Robert_Browning
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "<ref>x"
			, " y"
			, "</ref>"
			, "<references/>"
			), String_.Concat_lines_nl_skip_last
			( "<sup id=\"cite_ref-0\" class=\"reference\"><a href=\"#cite_note-0\">[1]</a></sup>"
			, "<ol class=\"references\">"
			, "<li id=\"cite_note-0\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-0\">^</a></span> <span class=\"reference-text\">x"
			, " y"
			, "</span></li>"
			, "</ol>"
			, ""
			));
	}
	@Test  public void Pre_ignored_3() {	// PURPOSE: " <references>" should not create pre; fr.w:Heidi_(roman); DATE:2014-02-17
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "<ref>x</ref>"
			, ""
			, " <references/>"
			), String_.Concat_lines_nl_skip_last
			( "<p><sup id=\"cite_ref-0\" class=\"reference\"><a href=\"#cite_note-0\">[1]</a></sup>"
			, "</p>"
			, " <ol class=\"references\">"
			, "<li id=\"cite_note-0\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-0\">^</a></span> <span class=\"reference-text\">x</span></li>"
			, "</ol>"
			, ""
			));
		fxt.Init_para_n_();
	}
}
