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
import org.junit.*;
public class Xoh_file_wtr__media__tst {		
	@Before public void init() {fxt.Reset();} private final    Xop_fxt fxt = new Xop_fxt();
	@Test  public void Page_only() { // if no caption, then use page name; DATE:2017-05-20
		fxt.Test_parse_page_wiki_str("[[Media:A.png]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" xowa_title=\"A.png\">Media:A.png"
		, "</a>"
		));
	}
	@Test  public void Nested_caption() { // PAGE:en.w:Beethoven;
		fxt.Test_parse_page_wiki_str("[[File:A.png|thumb|b [[Media:A.ogg|media_caption]] c]]", String_.Concat_lines_nl_skip_last
		( "<div class=\"thumb tright\">"
		, "  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"
		, "    <a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/220px.png\" width=\"0\" height=\"0\" /></a>"
		, "    <div class=\"thumbcaption\">"
		,       "<div class=\"magnify\"><a href=\"/wiki/File:A.png\" class=\"internal\" title=\"Enlarge\"></a></div>b <a href=\"file:///mem/wiki/repo/trg/orig/4/2/A.ogg\" xowa_title=\"A.ogg\">media_caption"
		, "</a> c"
		, "    </div>"
		, "  </div>"
		, "</div>"
		, ""
		));
	}
	@Test  public void Caption() {
		fxt.Test_parse_page_wiki_str("[[Media:A.png|b]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" xowa_title=\"A.png\">b"
		, "</a>"
		));
	}
	@Test  public void Literal() {
		fxt.Test_parse_page_wiki_str("[[:Media:A.ogg|b]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"file:///mem/wiki/repo/trg/orig/4/2/A.ogg\" xowa_title=\"A.ogg\">b"
		, "</a>"
		));
	}
	@Test  public void Literal_w_missing() {
		fxt.Wiki().Html_mgr().Img_suppress_missing_src_(true);	// simulate missing file; DATE:2014-01-30
		fxt.Test_parse_page_wiki_str("[[Media:A.pdf|b]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"file:///mem/wiki/repo/trg/orig/e/f/A.pdf\" xowa_title=\"A.pdf\">b"
		, "</a>"
		));
		Tfds.Eq(0, fxt.Page().File_queue().Count());			// make sure media does not add to queue
		fxt.Wiki().Html_mgr().Img_suppress_missing_src_(false);
	}
}
