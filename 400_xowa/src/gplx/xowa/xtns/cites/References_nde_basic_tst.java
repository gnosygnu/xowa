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
package gplx.xowa.xtns.cites; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class References_nde_basic_tst {	
	@Before public void init() {fxt.Clear_ref_mgr();} private final Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Basic() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "<ref>x</ref>"
			, "<ref>y</ref>"
			, "<ref>z</ref>"
			, "<references/>"
			), String_.Concat_lines_nl_skip_last
			( "<sup id=\"cite_ref-0\" class=\"reference\"><a href=\"#cite_note-0\">[1]</a></sup>"
			, "<sup id=\"cite_ref-1\" class=\"reference\"><a href=\"#cite_note-1\">[2]</a></sup>"
			, "<sup id=\"cite_ref-2\" class=\"reference\"><a href=\"#cite_note-2\">[3]</a></sup>"
			, "<ol class=\"references\">"
			, "<li id=\"cite_note-0\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-0\">^</a></span> <span class=\"reference-text\">x</span></li>"
			, "<li id=\"cite_note-1\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-1\">^</a></span> <span class=\"reference-text\">y</span></li>"
			, "<li id=\"cite_note-2\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-2\">^</a></span> <span class=\"reference-text\">z</span></li>"
			, "</ol>"
			, ""
			));
	}
	@Test  public void Name_dif() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "<ref name='r_x'>x</ref>"
			, "<ref name='r_y'>y</ref>"
			, "<ref name='r_z'>z</ref>"
			, "<references/>"
			), String_.Concat_lines_nl_skip_last
			( "<sup id=\"cite_ref-r_x_0-0\" class=\"reference\"><a href=\"#cite_note-r_x-0\">[1]</a></sup>"
			, "<sup id=\"cite_ref-r_y_1-0\" class=\"reference\"><a href=\"#cite_note-r_y-1\">[2]</a></sup>"
			, "<sup id=\"cite_ref-r_z_2-0\" class=\"reference\"><a href=\"#cite_note-r_z-2\">[3]</a></sup>"
			, "<ol class=\"references\">"
			, "<li id=\"cite_note-r_x-0\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-r_x_0-0\">^</a></span> <span class=\"reference-text\">x</span></li>"
			, "<li id=\"cite_note-r_y-1\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-r_y_1-0\">^</a></span> <span class=\"reference-text\">y</span></li>"
			, "<li id=\"cite_note-r_z-2\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-r_z_2-0\">^</a></span> <span class=\"reference-text\">z</span></li>"
			, "</ol>"
			, ""
			));
	}
	@Test  public void Name_same() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "<ref name='r_x'>x</ref>"
			, "<ref name='r_y'>y</ref>"
			, "<ref name='r_x'>z</ref>"
			, "<references/>"
			), String_.Concat_lines_nl_skip_last
			( "<sup id=\"cite_ref-r_x_0-0\" class=\"reference\"><a href=\"#cite_note-r_x-0\">[1]</a></sup>"
			, "<sup id=\"cite_ref-r_y_1-0\" class=\"reference\"><a href=\"#cite_note-r_y-1\">[2]</a></sup>"
			, "<sup id=\"cite_ref-r_x_0-1\" class=\"reference\"><a href=\"#cite_note-r_x-0\">[1]</a></sup>"
			, "<ol class=\"references\">"
			, "<li id=\"cite_note-r_x-0\"><span class=\"mw-cite-backlink\">^ <sup><a href=\"#cite_ref-r_x_0-0\">a</a></sup> <sup><a href=\"#cite_ref-r_x_0-1\">b</a></sup></span> <span class=\"reference-text\">x</span></li>"
			, "<li id=\"cite_note-r_y-1\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-r_y_1-0\">^</a></span> <span class=\"reference-text\">y</span></li>"
			, "</ol>"
			, ""
			));
	}
	@Test  public void Name_same_text_in_last_ref() {	// WP:Hundred Years' War
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "<ref name='ref_a'></ref>"
			, "<ref name='ref_a'></ref>"
			, "<ref name='ref_a'>x</ref>"
			, "<references/>"
			), String_.Concat_lines_nl_skip_last
			( "<sup id=\"cite_ref-ref_a_0-0\" class=\"reference\"><a href=\"#cite_note-ref_a-0\">[1]</a></sup>"
			, "<sup id=\"cite_ref-ref_a_0-1\" class=\"reference\"><a href=\"#cite_note-ref_a-0\">[1]</a></sup>"
			, "<sup id=\"cite_ref-ref_a_0-2\" class=\"reference\"><a href=\"#cite_note-ref_a-0\">[1]</a></sup>"
			, "<ol class=\"references\">"
			, "<li id=\"cite_note-ref_a-0\"><span class=\"mw-cite-backlink\">^ <sup><a href=\"#cite_ref-ref_a_0-0\">a</a></sup> <sup><a href=\"#cite_ref-ref_a_0-1\">b</a></sup> <sup><a href=\"#cite_ref-ref_a_0-2\">c</a></sup></span> <span class=\"reference-text\">x</span></li>"
			, "</ol>"
			, ""
			));
	}
	@Test  public void List_ignored() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "<ref>*x</ref>"
			, "<references/>"
			), String_.Concat_lines_nl_skip_last
			( "<sup id=\"cite_ref-0\" class=\"reference\"><a href=\"#cite_note-0\">[1]</a></sup>"
			, "<ol class=\"references\">"
			, "<li id=\"cite_note-0\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-0\">^</a></span> <span class=\"reference-text\">*x</span></li>"
			, "</ol>"
			, ""
			));
	}
	@Test  public void Name_mixed_case() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "<ref NAME=A>x</ref>"
			, "<ref Name=A>y</ref>"
			, "<references/>"
			), String_.Concat_lines_nl_skip_last
			( "<sup id=\"cite_ref-A_0-0\" class=\"reference\"><a href=\"#cite_note-A-0\">[1]</a></sup>"
			, "<sup id=\"cite_ref-A_0-1\" class=\"reference\"><a href=\"#cite_note-A-0\">[1]</a></sup>"
			, "<ol class=\"references\">"
			, "<li id=\"cite_note-A-0\"><span class=\"mw-cite-backlink\">^ <sup><a href=\"#cite_ref-A_0-0\">a</a></sup> <sup><a href=\"#cite_ref-A_0-1\">b</a></sup></span> <span class=\"reference-text\">x</span></li>"
			, "</ol>"
			, ""
			));
	}
	@Test  public void Name2() { // PURPOSE: make sure inline tag matches open tag
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "a<ref name=name_0>b</ref>"
			, "b<ref name=name_0/>"
			, "<references/>"
			), String_.Concat_lines_nl_skip_last
			( "a<sup id=\"cite_ref-name_0_0-0\" class=\"reference\"><a href=\"#cite_note-name_0-0\">[1]</a></sup>"
			, "b<sup id=\"cite_ref-name_0_0-1\" class=\"reference\"><a href=\"#cite_note-name_0-0\">[1]</a></sup>"
			, "<ol class=\"references\">"
			, "<li id=\"cite_note-name_0-0\"><span class=\"mw-cite-backlink\">^ <sup><a href=\"#cite_ref-name_0_0-0\">a</a></sup> <sup><a href=\"#cite_ref-name_0_0-1\">b</a></sup></span> <span class=\"reference-text\">b</span></li>"
			, "</ol>"
			, ""
			));
	}
	@Test  public void References_refs() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "a<ref group=group_0 name=name_0/>"
			, "<references group=group_0>"
			, "  <ref name=name_0>b</ref>"
			, "</references>"
			), String_.Concat_lines_nl_skip_last
			( "a<sup id=\"cite_ref-name_0_0-0\" class=\"reference\"><a href=\"#cite_note-name_0-0\">[group_0 1]</a></sup>"
			, "<ol class=\"references\">"
			, "<li id=\"cite_note-name_0-0\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-name_0_0-0\">^</a></span> <span class=\"reference-text\">b</span></li>"
			, "</ol>"
			, ""
			));
	}
	@Test  public void Nested() {	// PURPOSE: nested ref was creating 3rd [1]
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "a<ref name=r_x/>"
			, "b<ref name=r_x/>"
			, "<references>"
			, "<ref name=r_x>c</ref>"
			, "</references>"
			), String_.Concat_lines_nl_skip_last
			( "a<sup id=\"cite_ref-r_x_0-0\" class=\"reference\"><a href=\"#cite_note-r_x-0\">[1]</a></sup>"
			, "b<sup id=\"cite_ref-r_x_0-1\" class=\"reference\"><a href=\"#cite_note-r_x-0\">[1]</a></sup>"
			, "<ol class=\"references\">"
			, "<li id=\"cite_note-r_x-0\"><span class=\"mw-cite-backlink\">^ <sup><a href=\"#cite_ref-r_x_0-0\">a</a></sup> <sup><a href=\"#cite_ref-r_x_0-1\">b</a></sup></span> <span class=\"reference-text\">c</span></li>"
			, "</ol>"
			, ""
			));
	}
	@Test  public void Key_ignore_nl_tab() {	// PURPOSE: \n in ref_name should be escaped to \s; PAGE:en.w:Self-Transcendence 3100 Mile Race
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "<ref name=\"name\na\">b</ref>"
			, "<references/>"
			), String_.Concat_lines_nl_skip_last
			( "<sup id=\"cite_ref-name_a_0-0\" class=\"reference\"><a href=\"#cite_note-name_a-0\">[1]</a></sup>"
			, "<ol class=\"references\">"
			, "<li id=\"cite_note-name_a-0\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-name_a_0-0\">^</a></span> <span class=\"reference-text\">b</span></li>"
			, "</ol>"
			, ""
			));
	}
	@Test   public void Empty_group_before_ref() {	// PURPOSE: empty grp before itm should not throw error; DATE:2013-02-18; EX: w:Help:External links and references; Johnstown,_Colorado
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl
			( "<references/><references/>a<ref>test</ref>"
			, "<references/>"
			), String_.Concat_lines_nl
			( "a<sup id=\"cite_ref-0\" class=\"reference\"><a href=\"#cite_note-0\">[1]</a></sup>"
			, "<ol class=\"references\">"
			, "<li id=\"cite_note-0\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-0\">^</a></span> <span class=\"reference-text\">test</span></li>"
			, "</ol>"
			, ""
			));
	}
	@Test  public void Follow() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "<ref name='ref_a'>x</ref>"
			, "<ref>y</ref>"
			, "<ref follow='ref_a'>z</ref>"
			, "<references/>"
			), String_.Concat_lines_nl_skip_last
			( "<sup id=\"cite_ref-ref_a_0-0\" class=\"reference\"><a href=\"#cite_note-ref_a-0\">[1]</a></sup>"
			, "<sup id=\"cite_ref-1\" class=\"reference\"><a href=\"#cite_note-1\">[2]</a></sup>"
			, "<ol class=\"references\">"
			, "<li id=\"cite_note-ref_a-0\"><span class=\"mw-cite-backlink\">^ <sup><a href=\"#cite_ref-ref_a_0-0\">a</a></sup></span> <span class=\"reference-text\">x z</span></li>"
			, "<li id=\"cite_note-1\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-1\">^</a></span> <span class=\"reference-text\">y</span></li>"
			, "</ol>"
			, ""
			));
	}
}
