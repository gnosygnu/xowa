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
public class Xoh_file_wtr__djvu__tst {
	@Before public void init() {fxt.Reset();} private final    Xop_fxt fxt = new Xop_fxt();
	@Test  public void Djvu() {// ISSUE#:440 TODO
		fxt.Test_parse_page_wiki_str
		( "[[File:A.djvu|thumb|page=1]]", String_.Concat_lines_nl_skip_last
			( "<div class=\"thumb tright\">"
			, "  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"
			, "    <a href=\"/wiki/File:A.djvu\" class=\"image\" xowa_title=\"A.djvu\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/6/A.djvu/220px-1.jpg\" width=\"0\" height=\"0\" /></a>"
			, "    <div class=\"thumbcaption\">"
			, "<div class=\"magnify\"><a href=\"/wiki/File:A.djvu\" class=\"internal\" title=\"Enlarge\"></a></div>"
			, "    </div>"
			, "  </div>"
			, "</div>")
		);
	}
}
