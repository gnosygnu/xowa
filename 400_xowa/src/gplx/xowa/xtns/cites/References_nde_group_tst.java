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
public class References_nde_group_tst {	
	@Before public void init() {fxt.Clear_ref_mgr();} private final Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Basic() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "<ref>x</ref>"
			, "<ref group='group_a'>y</ref>"
			, "<ref>z</ref>"
			, "<references group='group_a'/>"
			, "<references/>"
			), String_.Concat_lines_nl_skip_last
			( "<sup id=\"cite_ref-0\" class=\"reference\"><a href=\"#cite_note-0\">[1]</a></sup>"
			, "<sup id=\"cite_ref-1\" class=\"reference\"><a href=\"#cite_note-1\">[group_a 1]</a></sup>"
			, "<sup id=\"cite_ref-2\" class=\"reference\"><a href=\"#cite_note-2\">[2]</a></sup>"
			, "<ol class=\"references\">"
			, "<li id=\"cite_note-1\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-1\">^</a></span> <span class=\"reference-text\">y</span></li>"
			, "</ol>"
			, "<ol class=\"references\">"
			, "<li id=\"cite_note-0\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-0\">^</a></span> <span class=\"reference-text\">x</span></li>"
			, "<li id=\"cite_note-2\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-2\">^</a></span> <span class=\"reference-text\">z</span></li>"
			, "</ol>"
			, ""
			));
	}
	@Test  public void Lower_alpha_is_ignored() {
		String expd = 
			String_.Concat_lines_nl_skip_last
			( "<sup id=\"cite_ref-0\" class=\"reference\"><a href=\"#cite_note-0\">[1]</a></sup>"	// note:do not show lower-alpha; DATE:2014-07-21
			, "<sup id=\"cite_ref-1\" class=\"reference\"><a href=\"#cite_note-1\">[1]</a></sup>"
			, "<ol class=\"references\">"
			, "<li id=\"cite_note-0\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-0\">^</a></span> <span class=\"reference-text\">x</span></li>"
			, "</ol>"
			, "<ol class=\"references\">"
			, "<li id=\"cite_note-1\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-1\">^</a></span> <span class=\"reference-text\">y</span></li>"
			, "</ol>"
			, ""
			);
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "<ref group='lower-alpha'>x</ref>"
			, "<ref>y</ref>"
			, "<references group='lower-alpha'/>"
			, "<references group=''/>"
			), expd);
	}
	@Test  public void Empty() {	// PURPOSE: <references group=""/> is same as <references/>; DATE:2013-02-06
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "<ref>b</ref>"
			, "<references group=\"\"/>"
			), String_.Concat_lines_nl_skip_last
			( "<sup id=\"cite_ref-0\" class=\"reference\"><a href=\"#cite_note-0\">[1]</a></sup>"
			, "<ol class=\"references\">"
			, "<li id=\"cite_note-0\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-0\">^</a></span> <span class=\"reference-text\">b</span></li>"
			, "</ol>"
			, ""
			));
	}
	@Test  public void Val_less_defaults_to_key() {	// PURPOSE: similar to above, except "group" is same as "group=group"; DATE:2014-07-03
		String expd = String_.Concat_lines_nl_skip_last
			( "<sup id=\"cite_ref-0\" class=\"reference\"><a href=\"#cite_note-0\">[group 1]</a></sup>"
			, "<ol class=\"references\">"
			, "<li id=\"cite_note-0\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-0\">^</a></span> <span class=\"reference-text\">b</span></li>"
			, "</ol>"
			, "");
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "<ref group='group'>b</ref>"
			, "<references group/>"
			), expd
			);
		fxt.Clear_ref_mgr();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last	// PURPOSE.FIX: similar to above, except "group />" was not same as "group/>"; SEE:xatr_parser and " a " test; DATE:2014-07-03
			( "<ref group='group'>b</ref>"
			, "<references group />"
			), expd
			);
	}
	@Test   public void Empty_group() {	// PURPOSE: group without items should be blank; should not throw error; DATE:2013-02-12
		fxt.Test_parse_page_wiki_str("<references name='group_a'/>", "");
	}
	@Test  public void Multiple_same_name_groups() {	// PURPOSE: multiple groups with same name "clears" out references; DATE:2013-02-11
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "<ref>a</ref>"
			, "<references/>"
			, "<ref>b</ref>"
			, "<references/>"
			, "<ref>c</ref>"
			, "<references/>"
			), String_.Concat_lines_nl_skip_last
			( "<sup id=\"cite_ref-0\" class=\"reference\"><a href=\"#cite_note-0\">[1]</a></sup>"
			, "<ol class=\"references\">"
			, "<li id=\"cite_note-0\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-0\">^</a></span> <span class=\"reference-text\">a</span></li>"
			, "</ol>"
			, "<sup id=\"cite_ref-1\" class=\"reference\"><a href=\"#cite_note-1\">[1]</a></sup>"
			, "<ol class=\"references\">"
			, "<li id=\"cite_note-1\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-1\">^</a></span> <span class=\"reference-text\">b</span></li>"
			, "</ol>"
			, "<sup id=\"cite_ref-2\" class=\"reference\"><a href=\"#cite_note-2\">[1]</a></sup>"
			, "<ol class=\"references\">"
			, "<li id=\"cite_note-2\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-2\">^</a></span> <span class=\"reference-text\">c</span></li>"
			, "</ol>"
			, ""
			));
	}
}
