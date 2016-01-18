/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.htmls.core.wkrs.lnkis.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*;
import org.junit.*; import gplx.xowa.files.*;
public class Xoh_file_wtr__video__tst {
	@Before public void init() {fxt.Reset();} private final Xop_fxt fxt = new Xop_fxt();
	@Test  public void Video__full() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.ogv|400px|a|alt=b]]", String_.Concat_lines_nl_skip_last
		( "    <div class=\"xowa_media_div\">"
		, "      <div>"
		+	        "<a href=\"/wiki/File:A.ogv\" class=\"image\" title=\"A.ogv\" xowa_title=\"A.ogv\">"
		+	          "<img id=\"xoimg_0\" alt=\"b\" src=\"\" width=\"400\" height=\"0\" />"
		+	        "</a>"
		, "      </div>"
		,       "<div><a id=\"xowa_file_play_0\" href=\"file:///mem/wiki/repo/trg/orig/d/0/A.ogv\" xowa_title=\"A.ogv\" class=\"xowa_media_play\" style=\"width:398px;max-width:400px;\" alt=\"Play sound\"></a></div>"
		, "    </div>"
		));		
	}
	@Test  public void Video__full_ogg() {// PURPOSE: ogg should default to video on first load; otherwise dynamic-update won't be able to put in thumb; DATE:2015-05-21
		Xof_file_fxt file_fxt = Xof_file_fxt.new_all(fxt.Wiki());
		file_fxt.Exec_orig_add(Bool_.Y, "A.ogg", Xof_ext_.Id_ogv, 400, 400, "");
		fxt.Test_parse_page_wiki_str
		( "[[File:A.ogg|400px|a|alt=b]]", String_.Concat_lines_nl_skip_last
		( "<div class=\"thumb tright\">"
		, "  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:400px;\">"
		, "    <div class=\"xowa_media_div\">"
		,       "<div><a id=\"xowa_file_play_0\" href=\"\" xowa_title=\"A.ogg\" class=\"xowa_media_play\" style=\"width:398px;max-width:1024px;\" alt=\"Play sound\"></a></div>"
		,       "<div><a href=\"/wiki/File:A.ogg\" class=\"xowa_media_info\" title=\"About this file\"></a></div>"
		, "    </div>"
		, "    <div class=\"thumbcaption\">"
		,       "<div class=\"magnify\"><a href=\"/wiki/File:A.ogg\" class=\"internal\" title=\"Enlarge\"></a></div>a"
		, "    </div>"
		, "    <hr/>"
		, "    <div class=\"thumbcaption\">b"
		, "    </div>"
		, "  </div>"
		, "</div>"
		));		
	}
	@Test  public void Video__thumb() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.ogv|thumb|400px|a|alt=b]]", String_.Concat_lines_nl_skip_last
		( "<div class=\"thumb tright\">"
		, "  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"		// NOTE:220px is default w for "non-found" thumb; DATE:2014-09-24
		, "    <div class=\"xowa_media_div\">"
		, "      <div>"
		+	        "<a href=\"/wiki/File:A.ogv\" class=\"image\" title=\"A.ogv\" xowa_title=\"A.ogv\">"
		+	          "<img id=\"xoimg_0\" alt=\"b\" src=\"\" width=\"400\" height=\"0\" />"
		+	        "</a>"
		, "      </div>"
		,       "<div><a id=\"xowa_file_play_0\" href=\"file:///mem/wiki/repo/trg/orig/d/0/A.ogv\" xowa_title=\"A.ogv\" class=\"xowa_media_play\" style=\"width:398px;max-width:400px;\" alt=\"Play sound\"></a></div>"
		, "    </div>"
		, "    <div class=\"thumbcaption\">"
		,       "<div class=\"magnify\"><a href=\"/wiki/File:A.ogv\" class=\"internal\" title=\"Enlarge\"></a></div>a"
		, "    </div>"
		, "    <hr/>"
		, "    <div class=\"thumbcaption\">b"
		, "    </div>"
		, "  </div>"
		, "</div>"
		, ""
		));		
	}
	@Test  public void Video__thumb_webm() {	// PURPOSE: webm thumb wasn't being shown; DATE:2014-01-25
		fxt.Test_parse_page_wiki_str
		( "[[File:A.webm|thumb|400px|a|alt=b]]", String_.Concat_lines_nl_skip_last
		( "<div class=\"thumb tright\">"
		, "  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"	// NOTE:220px is default w for "non-found" thumb; DATE:2014-09-24
		, "    <div class=\"xowa_media_div\">"
		, "      <div>"
		+	        "<a href=\"/wiki/File:A.webm\" class=\"image\" title=\"A.webm\" xowa_title=\"A.webm\">"
		+	          "<img id=\"xoimg_0\" alt=\"b\" src=\"\" width=\"400\" height=\"0\" />"
		+	        "</a>"
		, "      </div>"
		,       "<div><a id=\"xowa_file_play_0\" href=\"file:///mem/wiki/repo/trg/orig/3/4/A.webm\" xowa_title=\"A.webm\" class=\"xowa_media_play\" style=\"width:398px;max-width:400px;\" alt=\"Play sound\"></a></div>"
		, "    </div>"
		, "    <div class=\"thumbcaption\">"
		,       "<div class=\"magnify\"><a href=\"/wiki/File:A.webm\" class=\"internal\" title=\"Enlarge\"></a></div>a"
		, "    </div>"
		, "    <hr/>"
		, "    <div class=\"thumbcaption\">b"
		, "    </div>"
		, "  </div>"
		, "</div>"
		, ""
		));		
	}
}
