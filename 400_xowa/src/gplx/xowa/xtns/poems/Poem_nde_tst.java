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
package gplx.xowa.xtns.poems; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Poem_nde_tst {
	@Before public void init() {fxt.Wiki().Xtn_mgr().Init_by_wiki(fxt.Wiki());} private Xop_fxt fxt = new Xop_fxt();
	@Test  public void Lines() {	// NOTE: first \n (poem\n) and last \n (\n</poem>)ignored
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "<poem>"
		, "a"
		, "b"
		, "c"
		, "d"
		, "</poem>"
		), String_.Concat_lines_nl_skip_last
		( "<div class=\"poem\">"
		, "<p>"
		, "a<br/>"
		, "b<br/>"
		, "c<br/>"
		, "d"
		, "</p>"
		, "</div>"
		));
	}
	@Test  public void Indent() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "<poem>"
		, "a 1"
		, "  b 1"
		, "c 1"
		, "  d 1"
		, "</poem>"
		), String_.Concat_lines_nl_skip_last
		( "<div class=\"poem\">"
		, "<p>"
		, "a 1<br/>"
		, "&#160;&#160;b 1<br/>"
		, "c 1<br/>"
		, "&#160;&#160;d 1"
		, "</p>"
		, "</div>"
		));
	}
	@Test  public void Indent_2() {	// PURPOSE: indent on 1st line caused page_break
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "<poem>"
		, "  a"
		, "  b"
		, "  c"
		, "  d"
		, "</poem>"
		), String_.Concat_lines_nl_skip_last
		( "<div class=\"poem\">"
		, "<p>"
		, "&#160;&#160;a<br/>"
		, "&#160;&#160;b<br/>"
		, "&#160;&#160;c<br/>"
		, "&#160;&#160;d"
		, "</p>"
		, "</div>"
		));
	}
	@Test  public void List() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "<poem>"
		, ":a"
		, ":b"
		, "</poem>"
		), String_.Concat_lines_nl_skip_last
		( "<div class=\"poem\">"
		, "<p>"
		, ""
		, "<dl>"
		, "  <dd>a"
		, "  </dd>"
		, "  <dd>b"
		, "  </dd>"
		, "</dl>"
		, "</p>"
		, "</div>"
		));
	}
	@Test  public void Xtn() {	// NOTE: behavior as per MW: DATE:2014-03-03
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test_print", "{{{1}}}");
		fxt.Init_defn_add("test_outer", String_.Concat_lines_nl_skip_last
		( "{{test_print|a <poem>b}}c</poem>}}"
		));
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "{{test_outer}}"
//			( "{{test_print|a <poem>b}}c</poem>}}"
		), String_.Concat_lines_nl_skip_last
		( "a <div class=\"poem\">"
		, "<p>"
		, "b}}c"
		, "</p>"
		, "</div>"
		));
		fxt.Init_defn_clear();
	}
	@Test  public void Comment() {
		fxt.Test_parse_page_wiki_str("<poem>a<!-- b --> c</poem>", String_.Concat_lines_nl_skip_last
		( "<div class=\"poem\">"
		, "<p>"
		, "a c"
		, "</p>"
		, "</div>"
		));
	}
	@Test  public void Err_empty_line() {
		fxt.Test_parse_page_wiki_str("<poem>\n</poem>", String_.Concat_lines_nl_skip_last
		( "<div class=\"poem\">"
		, "<p>"
		, ""
		, "</p>"
		, "</div>"
		));
	}
	@Test  public void Ref() {	// PURPOSE: <ref> inside poem was not showing up; DATE:2014-01-17
		fxt.Test_parse_page_all_str
		(	String_.Concat_lines_nl_skip_last
		( "<poem>a<ref>b</ref></poem>"
		, "<references/>"), String_.Concat_lines_nl_skip_last
		( "<div class=\"poem\">"
		, "<p>"
		, "a<sup id=\"cite_ref-0\" class=\"reference\"><a href=\"#cite_note-0\">[1]</a></sup>"
		, "</p>"
		, "</div>"
		, "<ol class=\"references\">"
		, "<li id=\"cite_note-0\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-0\">^</a></span> <span class=\"reference-text\">b</span></li>"
		, "</ol>"
		, ""
		));
	}
	@Test  public void Template_tkn() {	// PURPOSE: make sure {{{1}}} is interpreted as invk_prm, not as text; DATE:2014-03-03
		fxt.Test_parse_tmpl("<poem>{{{1}}}</poem>"
		, fxt.tkn_xnde_(0, 20).Subs_
		(	fxt.tkn_tmpl_prm_find_(fxt.tkn_txt_(9, 10))
		)
		);
	}
	@Test  public void Template_parse() {	// PURPOSE: <poem> inside template was not evaluating args; EX:en.s:The_Canterville_Ghost; DATE:2014-03-03
		fxt.Init_page_create("Template:A", "<poem>{{{1}}}</poem>");
		fxt.Test_parse_page_all_str("{{A|b}}", String_.Concat_lines_nl_skip_last
		( "<div class=\"poem\">"
		, "<p>"
		, "b"	// was {{{1}}}
		, "</p>"
		, "</div>"
		));
	}
	@Test  public void Nested() {	// PURPOSE: handled nested poems; EX:en.s:The_Canterville_Ghost; DATE:2014-03-03
		fxt.Test_parse_page_all_str("a<poem>b<poem>c</poem>d</poem>e", String_.Concat_lines_nl_skip_last
		( "a<div class=\"poem\">"
		, "<p>"
		, "b<div class=\"poem\">"
		, "<p>"
		, "c"
		, "</p>"
		, "</div>"
		, "</p>"
		, "</div>de"
		));
	}
}
