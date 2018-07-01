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
package gplx.xowa.parsers.tblws; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_tblw_wkr__double_pipe_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_y_();} private final Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void No_tblw() {			// PURPOSE: if || has no tblw, treat as lnki; none; DATE:2014-05-06
		fxt.Test_parse_page_all_str("[[A||b|c]]", String_.Concat_lines_nl_skip_last
		( "<p><a href=\"/wiki/A\">b|c</a>"	// NOTE: technically this should be "|b|c", but difficult to implement; DATE:2014-05-06
		, "</p>"
		, ""
		));
	}
	@Test  public void Lnki_nth() {	// PURPOSE: if || is nth pipe, then treat as lnki; PAGE:en.w:Main_Page;de.w:Main_Page; DATE:2014-05-06
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "{|"
		, "|[[File:A.png|b||c]]"
		, "|}"
		) , String_.Concat_lines_nl_skip_last
		( "<table>"
		, "  <tr>"
		, "    <td><a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		, ""
		)
		);
	}
	@Test  public void Lnki_list_1st() {	// PURPOSE: if || is 1st pipe, but inside list, then treat as lnki; EX:w:Second_Boer_War; DATE:2014-05-05
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "{|"
		, "|"
		, "*[[A||b]]"
		, "|}"
		) , String_.Concat_lines_nl_skip_last
		( "<table>"
		, "  <tr>"
		, "    <td>"
		, ""
		, "      <ul>"
		, "        <li><a href=\"/wiki/A\">b</a>"	// NOTE: technically this should be "|b", but difficult to implement; DATE:2014-05-06
		, "        </li>"
		, "      </ul>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		, ""
		)
		);
	}
	@Test  public void Double_bang_lnki() {	// PURPOSE: do not treat !! as tblw; PAGE:en.w:Pink_(singer); DATE:2014-06-25
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "{|"
		, "|"
		, "[[A!!b]]"
		, "|}"
		) , String_.Concat_lines_nl_skip_last
		( "<table>"
		, "  <tr>"
		, "    <td>"
		, ""
		, "<p><a href=\"/wiki/A!!b\">A!!b</a>"
		, "</p>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		, ""
		)
		);
	}
	@Test  public void Double_bang_list() {	// PURPOSE: do not treat !! as tblw; PAGE:en.w:Wikipedia:Featured_picture_candidates; DATE:2014-10-19
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "{|"
		, "* a !! b"
		, "|}"
		) , String_.Concat_lines_nl_skip_last
		( "<table>"
		, "  <ul>"
		, "    <li> a !! b"
		, "    </li>"
		, "  </ul>"
		, "  <tr>"
		, "    <td>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		, "</p>"	// NOTE: </p> is incorrect, but benign
		)
		);
	}
}
