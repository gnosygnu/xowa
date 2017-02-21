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
package gplx.xowa.htmls.core.wkrs.lnkis.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*;
import org.junit.*; import gplx.xowa.files.*;
public class Xoh_file_wtr__image__link__tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Before public void init() {fxt.Reset();}
	@Test  public void Link__file() {	// PURPOSE.FIX: link=file:/// was creating "href='/wiki/file'" handle IPA links; EX:[[File:Speakerlink-new.svg|11px|link=file:///C:/xowa/file/commons.wikimedia.org/orig/c/7/a/3/En-LudwigVanBeethoven.ogg|Listen]]; PAGE:en.w:Beethoven DATE:2015-12-28
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|11px|link=file:///C:/A.ogg|b]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"file:///C:/A.ogg\" class=\"image\" xowa_title=\"A.ogg\">"
		+ "<img id=\"xoimg_0\" alt=\"b\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/11px.png\" width=\"11\" height=\"0\" />"
		+ "</a>"
		));		
	}
	@Test  public void Link__empty() {	// empty link should not create anchor; EX:[[File:A.png|link=|abc]]; PAGE:en.w:List_of_counties_in_New_York; DATE:2016-01-10
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|11px|link=|abc]]", String_.Concat_lines_nl_skip_last
		( "<img id=\"xoimg_0\" alt=\"abc\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/11px.png\" width=\"11\" height=\"0\" />"
		));		
	}
	@Test  public void Link__lc() {	// links to items in same Srch_rslt_cbk should automatically title-case words; DATE:2016-01-11
		fxt.Init_xwiki_add_wiki_and_user_("en", "en.wikipedia.org");
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|11px|link=en:Help:a?b=c#d|abc]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/wiki/Help:A?b=c#d\" class=\"image\" xowa_title=\"A.png\">"	// "Help:A" not "Help:a"
		+ "<img id=\"xoimg_0\" alt=\"abc\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/11px.png\" width=\"11\" height=\"0\" />"
		+ "</a>"));
		fxt.Init_xwiki_clear();
	}
}
