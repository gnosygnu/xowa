/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.poems; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Poem_nde_tst {
	@Before public void init() {fxt.Wiki().Xtn_mgr().Init_by_wiki(fxt.Wiki());} private final    Xop_fxt fxt = new Xop_fxt();
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
	@Test  public void Nbsp_basic() {
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
	@Test  public void Nbsp_line_0() {// PURPOSE: indent on 1st line caused page_break
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
	@Test  public void Nbsp_blank_lines() {// PURPOSE: check blank lines; PAGE:none
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "<poem>"
		, "  a"
		, "  "
		, "  "
		, "  b"
		, "</poem>"
		), String_.Concat_lines_nl_skip_last
		( "<div class=\"poem\">"
		, "<p>"
		, "&#160;&#160;a<br/>"
		, "&#160;&#160;<br/>"
		, "&#160;&#160;<br/>"
		, "&#160;&#160;b"
		, "</p>"
		, "</div>"
		));
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
	@Test  public void Xtn() {	// NOTE: behavior as per MW: DATE:2014-03-03
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test_print", "{{{1}}}");
		fxt.Init_defn_add("test_outer", String_.Concat_lines_nl_skip_last
		( "{{test_print|a <poem>b}}c</poem>}}"
		));
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "{{test_outer}}"
		), String_.Concat_lines_nl_skip_last
		( "a <div class=\"poem\">"
		, "<p>"
		, "b}}c"
		, "</p>"
		, "</div>"
		));
		fxt.Init_defn_clear();
	}
	@Test  public void Err_empty_line() {// PURPOSE: one \n caused poem to fail
		fxt.Test_parse_page_wiki_str("<poem>\n</poem>", String_.Concat_lines_nl_skip_last
		( "<div class=\"poem\">"
		, "<p>"
		, ""
		, "</p>"
		, "</div>"
		));
	}
	@Test  public void Err_dangling_comment() {// PURPOSE: ArrayIndexOutOfBoundsError if poem has <references/> and ends with <!--; PAGE:en.s:The Hebrew Nation did not write it; DATE:2015-01-31
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "<poem>"
		, "<references/>"
		, "<!--"
		), String_.Concat_lines_nl_skip_last
		( "&lt;poem&gt;"
		));
	}
	@Test  public void Ref() {	// PURPOSE: <ref> inside poem was not showing up; DATE:2014-01-17
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
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
		( fxt.tkn_tmpl_prm_find_(fxt.tkn_txt_(9, 10))
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
		, "</div>d"
		, "</p>"
		, "</div>e"
		));
	}
	@Test  public void Indent_line_0() {	// PURPOSE: handle colon on 1st line; PAGE:en.w:Mary_Wollstonecraft DATE:2014-10-19
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "<poem>"
		, ":a"
		, ":b"
		, "</poem>"
		), String_.Concat_lines_nl_skip_last
		( "<div class=\"poem\">"
		, "<p>"
		, "<span class='mw-poem-indented' style='display: inline-block; margin-left: 1em;'>a</span><br/>"
		, "<span class='mw-poem-indented' style='display: inline-block; margin-left: 1em;'>b</span>"
		, "</p>"
		, "</div>"
		));
	}
	@Test  public void Indent_many() {	// PURPOSE: handle colon on 2nd line; PAGE:vi.s:Văn_Côi_thánh_nguyệt_tán_tụng_thi_ca DATE:2014-10-15
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "<poem>"
		, "a"
		, ":b"
		, "::c"
		, "</poem>"
		), String_.Concat_lines_nl_skip_last
		( "<div class=\"poem\">"
		, "<p>"
		, "a<br/>"
		, "<span class='mw-poem-indented' style='display: inline-block; margin-left: 1em;'>b</span><br/>"
		, "<span class='mw-poem-indented' style='display: inline-block; margin-left: 2em;'>c</span>"
		, "</p>"
		, "</div>"
		));
	}
	@Test  public void Indent_blank() {	// PURPOSE: check blank lines; PAGE:none DATE:2014-10-19
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "<poem>"
		, ":a"
		, ":"
		, ":b"
		, "</poem>"
		), String_.Concat_lines_nl_skip_last
		( "<div class=\"poem\">"
		, "<p>"
		, "<span class='mw-poem-indented' style='display: inline-block; margin-left: 1em;'>a</span><br/>"
		, "<span class='mw-poem-indented' style='display: inline-block; margin-left: 1em;'></span><br/>"
		, "<span class='mw-poem-indented' style='display: inline-block; margin-left: 1em;'>b</span>"
		, "</p>"
		, "</div>"
		));
	}
	@Test  public void List_does_not_end() {// PURPOSE: list does not end with "\ntext"; PAGE:vi.s:Dương_Từ_Hà_Mậu_(dị_bản_mới); li.s:Sint_Servaes_legende/Nuuj_fergmènter DATE:2014-10-19
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "<poem>"
		, "a"
		, "*b"
		, "**c"
		, "d"
		, "</poem>"
		), String_.Concat_lines_nl_skip_last
		( "<div class=\"poem\">"
		, "<p>"
		, "a<br/>"
		, "<ul>"
		, "  <li>b<br/>"
		, "    <ul>"
		, "      <li>c<br/>"
		, "      </li>"
		, "    </ul>"
		, "  </li>"
		, "</ul>"
		, "d"			// was being embedded directly after <li>c
		, "</p>"
		, "</div>"
		));
	}
	@Test  public void Page() {	// PURPOSE: handled dangling poems across pages; PAGE:ca.s:Llibre_de_Disputació_de_l'Ase DATE:2014-10-19
		fxt.Init_xtn_pages();
		fxt.Init_page_create("Page:A/1", "<poem>a\nb\n");
		fxt.Init_page_create("Page:A/2", "<poem>c\nd\n");
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to=2 />", String_.Concat_lines_nl_skip_last
		( "<p><div class=\"poem\">"
		, "<p>"
		, "a<br/>"
		, "b"
		, "</p>"
		, "</div>&#32;<div class=\"poem\">"
		, "<p>"
		, "c<br/>"	// NOTE: "<br/>" not "<br/><br/>"
		, "d"
		, "</p>"
		, "</div>&#32;"
		, "</p>"
		));
	}
}
