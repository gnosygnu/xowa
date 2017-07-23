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
public class Xoh_file_wtr__audio__tst {
	@Before public void init() {fxt.Reset();} private final    Xop_fxt fxt = new Xop_fxt();
	@Test  public void Audio__full() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.oga|noicon]]", String_.Concat_lines_nl_skip_last
		( "    <div class=\"xowa_media_div\">"
		,       "<div><a id=\"xowa_file_play_0\" href=\"file:///mem/wiki/repo/trg/orig/4/f/A.oga\" xowa_title=\"A.oga\" class=\"xowa_media_play\" style=\"width:218px;max-width:220px;\" alt=\"Play sound\"></a></div>"
		, "    </div>"
		, "    <div class=\"thumbcaption\">"
		, "    </div>"
		));		
	}
	@Test  public void Audio__full_ogg() {// PURPOSE: ogg should show src on first load
		fxt.Wiki().Html_mgr().Img_suppress_missing_src_(true);	// simulate release-mode wherein missing images will not have src
		fxt.Test_parse_page_all_str
		( "[[File:A.ogg]]", String_.Concat_lines_nl_skip_last
		( "    <div class=\"xowa_media_div\">"
		, "      <div>"
		+	        "<a href=\"/wiki/File:A.ogg\" class=\"image\" title=\"A.ogg\" xowa_title=\"A.ogg\">"
		+	          "<img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/orig/4/2/A.ogg\" width=\"220\" height=\"-1\" />"	// note that src still exists (needed for clicking)
		+	        "</a>"
		, "      </div>"
		,       "<div><a id=\"xowa_file_play_0\" href=\"file:///mem/wiki/repo/trg/orig/4/2/A.ogg\" xowa_title=\"A.ogg\" class=\"xowa_media_play\" style=\"width:218px;max-width:220px;\" alt=\"Play sound\"></a></div>"
		, "    </div>"
		));
		fxt.Wiki().Html_mgr().Img_suppress_missing_src_(false);
	}
	@Test  public void Audio__thumb() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.oga|thumb|a|alt=b]]", String_.Concat_lines_nl_skip_last
		( "<div class=\"thumb tright\">"
		, "  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"
		, "    <div class=\"xowa_media_div\">"
		,       "<div><a id=\"xowa_file_play_0\" href=\"file:///mem/wiki/repo/trg/orig/4/f/A.oga\" xowa_title=\"A.oga\" class=\"xowa_media_play\" style=\"width:218px;max-width:220px;\" alt=\"Play sound\"></a></div>"
		,       "<div><a href=\"/wiki/File:A.oga\" class=\"xowa_media_info\" title=\"About this file\"></a></div>"
		, "    </div>"
		, "    <div class=\"thumbcaption\">"
		,       "<div class=\"magnify\"><a href=\"/wiki/File:A.oga\" class=\"internal\" title=\"Enlarge\"></a></div>a"
		, "    </div>"
		, "    <div class=\"xowa_alt_text\">"
		, "    <hr/>"
		, "    <div class=\"thumbcaption\">b"
		, "    </div>"
		, "    </div>"
		, "  </div>"
		, "</div>"
		, ""
		));		
	}
	@Test  public void Audio__full_width() {	// ISSUE: width arg ignored for v2; zh.b:小学数学/自然数; DATE:2014-05-03
		fxt.Wiki().File_mgr().Version_2_y_();
		fxt.App().Usere().Init_by_app(fxt.App());	// TEST: init cache else null reference
		fxt.Test_html_wiki_frag("[[File:A.oga|30px|a]]", "<div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:30px;\">");
		fxt.Wiki().File_mgr().Version_1_y_();
	}
	@Test  public void Audio__noicon() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.oga|thumb|noicon|a|alt=b]]", String_.Concat_lines_nl_skip_last
		( "    <div class=\"xowa_media_div\">"
		,       "<div><a id=\"xowa_file_play_0\" href=\"file:///mem/wiki/repo/trg/orig/4/f/A.oga\" xowa_title=\"A.oga\" class=\"xowa_media_play\" style=\"width:218px;max-width:220px;\" alt=\"Play sound\"></a></div>"
		, "    </div>"
		, "    <div class=\"thumbcaption\">a"
		, "    </div>"
		, "    <div class=\"xowa_alt_text\">"
		, "    <hr/>"
		, "    <div class=\"thumbcaption\">b"
		, "    </div>"
		, "    </div>"
		));		
	}
}
//		@Test  public void Ogg() {
//			fxt.Src_en_wiki_repo().Ext_rules().Get_or_new(Xof_ext_.Bry_ogg).View_max_(0);
//			fxt	.ini_page_api("commons", "A.ogg", "", 0, 0);
//			fxt	.Lnki_orig_("A.ogg")
//				.Src( )
//				.Trg( 
//						fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/4/42.csv", "A.ogg|z||2?0,0|0?0,0")
//					)
//				.tst();
//			fxt	.Lnki_orig_("A.ogg")
//			.Html_orig_src_("file:///mem/trg/en.wikipedia.org/raw/4/2/A.ogg")
//			.tst();
//			fxt.Src_en_wiki_repo().Ext_rules().Get_or_new(Xof_ext_.Bry_ogg).View_max_(-1);
//		}
