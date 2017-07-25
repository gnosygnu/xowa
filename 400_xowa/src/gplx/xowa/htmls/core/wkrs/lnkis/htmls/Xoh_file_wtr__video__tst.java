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
public class Xoh_file_wtr__video__tst {
	@Before public void init() {fxt.Reset();} private final    Xop_fxt fxt = new Xop_fxt();
	@Test  public void Video__full__ogv__width_y() {// EX: [[File:A.ogv|320px|bcd|alt=efg]]; DATE:2016-08-05
		fxt.Test_parse_page_wiki_str
		( "[[File:A.ogv|320px|bcd|alt=efg]]", String_.Concat_lines_nl_skip_last
		( "    <div class=\"xowa_media_div\">"
		, "      <div>"
		+	        "<a href=\"/wiki/File:A.ogv\" class=\"image\" title=\"A.ogv\" xowa_title=\"A.ogv\">"
		+	          "<img id=\"xoimg_0\" alt=\"efg\" src=\"\" width=\"320\" height=\"0\" />"
		+	        "</a>"
		, "      </div>"
		,       "<div><a id=\"xowa_file_play_0\" href=\"file:///mem/wiki/repo/trg/orig/d/0/A.ogv\" xowa_title=\"A.ogv\" class=\"xowa_media_play\" style=\"width:318px;max-width:320px;\" alt=\"Play sound\"></a></div>"
		, "    </div>"
		));		
	}
	@Test  public void Video__full__ogv__width_n() {// EX: [[File:A.ogv]]; DATE:2016-08-05
		fxt.Test_parse_page_wiki_str
		( "[[File:A.ogv]]", String_.Concat_lines_nl_skip_last
		( "    <div class=\"xowa_media_div\">"
		, "      <div>"
		+	        "<a href=\"/wiki/File:A.ogv\" class=\"image\" title=\"A.ogv\" xowa_title=\"A.ogv\">"
		+	          "<img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/orig/d/0/A.ogv\" width=\"0\" height=\"0\" />"	// NOTE: src should probably be empty;
		+	        "</a>"
		, "      </div>"
		,       "<div><a id=\"xowa_file_play_0\" href=\"file:///mem/wiki/repo/trg/orig/d/0/A.ogv\" xowa_title=\"A.ogv\" class=\"xowa_media_play\" style=\"width:218px;max-width:220px;\" alt=\"Play sound\"></a></div>"
		, "    </div>"
		));		
	}
	@Test  public void Video__full__ogv__time() {
		Xof_file_fxt file_fxt = Xof_file_fxt.new_all(fxt.Wiki());
		file_fxt.Exec_orig_add(Bool_.Y, "A.ogv", Xof_ext_.Id_ogv, 220, 300, "");
		fxt.Wiki().File__fsdb_mode().Tid__v2__mp__y_();
		fxt.Hctx_(gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx.Hdump);

		fxt.Test_parse_page_wiki_str
		( "[[File:A.ogv|thumbtime=4]]", String_.Concat_lines_nl_skip_last
		( "    <div class=\"xowa_media_div\">"
		, "      <div>"
		+	        "<a href=\"/wiki/File:A.ogv\" class=\"image\" title=\"A.ogv\" xowa_title=\"A.ogv\">"
		+	          "<img src=\"file:///mem/wiki/repo/trg/thumb/d/0/A.ogv/220px-4.jpg\" width=\"220\" height=\"300\" alt=\"\"/>"	// NOTE: src should probably be empty;
		+	        "</a>"
		, "      </div>"
		,       "<div><a href=\"\" xowa_title=\"A.ogv\" class=\"xowa_media_play\" style=\"width:218px;max-width:220px;\" alt=\"Play sound\"></a></div>"
		, "    </div>"
		));		
	}
	@Test  public void Video__full_ogg() {// PURPOSE: ogg w/ width should default to video; otherwise dynamic-update won't be able to convert audio-button to thumb; DATE:2016-08-05
		// NOTE: simulates app w/ fsdb
		Xof_file_fxt file_fxt = Xof_file_fxt.new_all(fxt.Wiki());
		file_fxt.Exec_orig_add(Bool_.Y, "A.ogg", Xof_ext_.Id_ogv, 320, 300, "");

		fxt.Test_parse_page_wiki_str
		( "[[File:A.ogg|320px|bcd|alt=efg]]", String_.Concat_lines_nl_skip_last
		( "    <div class=\"xowa_media_div\">"
		, "      <div><a href=\"/wiki/File:A.ogg\" class=\"image\" title=\"A.ogg\" xowa_title=\"A.ogg\"><img id=\"xoimg_0\" alt=\"efg\" src=\"file:///mem/wiki/repo/trg/thumb/4/2/A.ogg/-1px.jpg\" width=\"320\" height=\"300\" /></a>"
		, "      </div>"
		, "<div><a id=\"xowa_file_play_0\" href=\"file:///mem/wiki/repo/trg/orig/4/2/A.ogg\" xowa_title=\"A.ogg\" class=\"xowa_media_play\" style=\"width:318px;max-width:320px;\" alt=\"Play sound\"></a></div>"
		, "    </div>"
		));		
	}
	@Test  public void Video__thumb() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.ogv|thumb|320px|bcd|alt=efg]]", String_.Concat_lines_nl_skip_last
		( "<div class=\"thumb tright\">"
		, "  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"		// NOTE:220px is default w for "non-found" thumb; DATE:2014-09-24
		, "    <div class=\"xowa_media_div\">"
		, "      <div>"
		+	        "<a href=\"/wiki/File:A.ogv\" class=\"image\" title=\"A.ogv\" xowa_title=\"A.ogv\">"
		+	          "<img id=\"xoimg_0\" alt=\"efg\" src=\"\" width=\"320\" height=\"0\" />"
		+	        "</a>"
		, "      </div>"
		,       "<div><a id=\"xowa_file_play_0\" href=\"file:///mem/wiki/repo/trg/orig/d/0/A.ogv\" xowa_title=\"A.ogv\" class=\"xowa_media_play\" style=\"width:318px;max-width:320px;\" alt=\"Play sound\"></a></div>"
		, "    </div>"
		, "    <div class=\"thumbcaption\">"
		,       "<div class=\"magnify\"><a href=\"/wiki/File:A.ogv\" class=\"internal\" title=\"Enlarge\"></a></div>bcd"
		, "    </div>"
		, "    <div class=\"xowa_alt_text\">"
		, "    <hr/>"
		, "    <div class=\"thumbcaption\">efg"
		, "    </div>"
		, "    </div>"
		, "  </div>"
		, "</div>"
		, ""
		));		
	}
	@Test  public void Video__thumb_webm() {	// PURPOSE: webm thumb wasn't being shown; DATE:2014-01-25
		fxt.Test_parse_page_wiki_str
		( "[[File:A.webm|thumb|320px|a|alt=b]]", String_.Concat_lines_nl_skip_last
		( "<div class=\"thumb tright\">"
		, "  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"	// NOTE:220px is default w for "non-found" thumb; DATE:2014-09-24
		, "    <div class=\"xowa_media_div\">"
		, "      <div>"
		+	        "<a href=\"/wiki/File:A.webm\" class=\"image\" title=\"A.webm\" xowa_title=\"A.webm\">"
		+	          "<img id=\"xoimg_0\" alt=\"b\" src=\"\" width=\"320\" height=\"0\" />"
		+	        "</a>"
		, "      </div>"
		,       "<div><a id=\"xowa_file_play_0\" href=\"file:///mem/wiki/repo/trg/orig/3/4/A.webm\" xowa_title=\"A.webm\" class=\"xowa_media_play\" style=\"width:318px;max-width:320px;\" alt=\"Play sound\"></a></div>"
		, "    </div>"
		, "    <div class=\"thumbcaption\">"
		,       "<div class=\"magnify\"><a href=\"/wiki/File:A.webm\" class=\"internal\" title=\"Enlarge\"></a></div>a"
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
}
