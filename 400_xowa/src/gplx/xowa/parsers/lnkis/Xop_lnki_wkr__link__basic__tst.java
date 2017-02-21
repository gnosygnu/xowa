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
package gplx.xowa.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
import gplx.xowa.langs.cases.*;
public class Xop_lnki_wkr__link__basic__tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_n_();} private final    Xop_fxt fxt = new Xop_fxt();
	@Test  public void Link() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=File:B.png|c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/wiki/File:B.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
	}
	@Test  public void Link_blank() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=|c]]", String_.Concat_lines_nl_skip_last
		( "<img id=\"xoimg_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" />"
		));
	}
	@Test  public void Link_external() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=http://www.b.org|c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"http://www.b.org\" rel=\"nofollow\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
	}
	@Test  public void Link_file_system() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=file:///C/B.png|c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"file:///C/B.png\" class=\"image\" xowa_title=\"B.png\"><img id=\"xoimg_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
	}
	@Test  public void Link_file_ns() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=File:B.png|c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/wiki/File:B.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
	}
	@Test  public void Link_external_relative() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=//fr.wikipedia.org/wiki/|c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"https://fr.wikipedia.org/wiki/\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
	}
	@Test  public void Link_external_absolute() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=http://fr.wikipedia.org/wiki/|c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"http://fr.wikipedia.org/wiki/\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
	}
	@Test  public void Link_external_double_http() {// PURPOSE.fix: link=http://a.org?b=http://c.org breaks lnki; DATE:2013-02-03
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=//a.org?b=http://c.org]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"https://a.org?b=http://c.org\" rel=\"nofollow\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
	}
	@Test  public void Encode() {// PURPOSE: encode invalid characters in link; PAGE:en.w:List_of_cultural_heritage_sites_in_Punjab,_Pakistan DATE:2014-07-16
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|link=//b?c\">|d]]"
		, "<a href=\"https://b?c%22%3E\" rel=\"nofollow\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"d\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a>"
		);
	}
	@Test  public void Anchor() {// PURPOSE: handle anchors; PAGE:en.w: en.w:History_of_Nauru; DATE:2015-12-27
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|link=#b]]"
		, "<a href=\"/wiki/Test_page#b\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a>"
		);
	}
}
