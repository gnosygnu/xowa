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
public class References_nde_pre_tst {	
	@Before public void init() {fxt.Clear_ref_mgr();} private final Xop_fxt fxt = new Xop_fxt();
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
