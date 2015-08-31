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
package gplx.xowa.html.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import org.junit.*; import gplx.xowa.files.*;
public class Xoh_file_wtr_audio_video_tst {
	@Before public void init() {fxt.Reset();} private Xop_fxt fxt = new Xop_fxt();
	@Test  public void Audio_full() {
		fxt.Test_parse_page_wiki_str
		(	"[[File:A.oga|noicon]]", String_.Concat_lines_nl_skip_last
		(	"    <div id=\"xowa_media_div\">"
		,	"      <div>"
		,	"        <a id=\"xowa_file_play_0\" href=\"file:///mem/wiki/repo/trg/orig/4/f/A.oga\" xowa_title=\"A.oga\" class=\"xowa_anchor_button\" style=\"width:218px;max-width:1024px;\">"
		,	"          <img src=\"file:///mem/xowa/bin/any/xowa/file/mediawiki.file/play.png\" width=\"22\" height=\"22\" alt=\"Play sound\" />"
		,	"        </a>"
		,	"      </div>"
		,	"    </div>"
		,	"    <div class=\"thumbcaption\">"
		,	"      "
		,	"    </div>"
		));		
	}
	@Test  public void Audio_full_ogg() {// PURPOSE: ogg should show src on first load
		fxt.Wiki().Html_mgr().Img_suppress_missing_src_(true);	// simulate release-mode wherein missing images will not have src
		fxt.Test_parse_page_all_str
		(	"[[File:A.ogg]]", String_.Concat_lines_nl_skip_last
		(	"    <div id=\"xowa_media_div\">"
		,	"      <div>"
		,	"        <a href=\"/wiki/File:A.ogg\" class=\"image\" title=\"A.ogg\">"
		,	"          <img id=\"xowa_file_img_0\" src=\"file:///mem/wiki/repo/trg/orig/4/2/A.ogg\" width=\"220\" height=\"-1\" alt=\"\" />"	// note that src still exists (needed for clicking)
		,	"        </a>"
		,	"      </div>"
		,	"      <div>"
		,	"        <a id=\"xowa_file_play_0\" href=\"file:///mem/wiki/repo/trg/orig/4/2/A.ogg\" xowa_title=\"A.ogg\" class=\"xowa_anchor_button\" style=\"width:218px;max-width:220px;\">"
		,	"          <img src=\"file:///mem/xowa/bin/any/xowa/file/mediawiki.file/play.png\" width=\"22\" height=\"22\" alt=\"Play sound\" />"
		,	"        </a>"
		,	"      </div>"
		,	"    </div>"
		));
		fxt.Wiki().Html_mgr().Img_suppress_missing_src_(false);
	}
	@Test  public void Audio_thumb() {
		fxt.Test_parse_page_wiki_str
		(	"[[File:A.oga|thumb|a|alt=b]]", String_.Concat_lines_nl_skip_last
		(	"<div class=\"thumb tright\">"
		,	"  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"
		,	"    <div id=\"xowa_media_div\">"
		,	"      <div>"
		,	"        <a id=\"xowa_file_play_0\" href=\"file:///mem/wiki/repo/trg/orig/4/f/A.oga\" xowa_title=\"A.oga\" class=\"xowa_anchor_button\" style=\"width:218px;max-width:1024px;\">"
		,	"          <img src=\"file:///mem/xowa/bin/any/xowa/file/mediawiki.file/play.png\" width=\"22\" height=\"22\" alt=\"Play sound\" />"
		,	"        </a>"
		,	"      </div>"
		,	"      <div>"
		,	"        <a href=\"/wiki/File:A.oga\" class=\"image\" title=\"About this file\">"
		,	"          <img src=\"file:///mem/xowa/bin/any/xowa/file/mediawiki.file/info.png\" width=\"22\" height=\"22\" />"
		,	"        </a>"
		,	"      </div>"
		,	"    </div>"
		,	"    <div class=\"thumbcaption\">"
		,	"      <div class=\"magnify\">"
		,	"        <a href=\"/wiki/File:A.oga\" class=\"internal\" title=\"Enlarge\">"
		,	"          <img src=\"file:///mem/xowa/bin/any/xowa/file/mediawiki.file/magnify-clip.png\" width=\"15\" height=\"11\" alt=\"\" />"
		,	"        </a>"
		,	"      </div>"
		,	"      a"
		,	"    </div>"
		,	"    <hr/>"
		,	"    <div class=\"thumbcaption\">"
		,	"b"
		,	"    </div>"
		,	"  </div>"
		,	"</div>"
		,	""
		));		
	}
	@Test  public void Audio_full_width() {	// ISSUE: width arg ignored for v2; zh.b:小学数学/自然数; DATE:2014-05-03
		fxt.Wiki().File_mgr().Version_2_y_();
		fxt.App().Usere().Init_by_app(fxt.App());	// TEST: init cache else null reference
		fxt.Test_html_wiki_frag("[[File:A.oga|30px|a]]", "<div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:30px;\">");
		fxt.Wiki().File_mgr().Version_1_y_();
	}
	@Test  public void Audio_noicon() {
		fxt.Test_parse_page_wiki_str
		(	"[[File:A.oga|thumb|noicon|a|alt=b]]", String_.Concat_lines_nl_skip_last
		(	"    <div id=\"xowa_media_div\">"
		,	"      <div>"
		,	"        <a id=\"xowa_file_play_0\" href=\"file:///mem/wiki/repo/trg/orig/4/f/A.oga\" xowa_title=\"A.oga\" class=\"xowa_anchor_button\" style=\"width:218px;max-width:1024px;\">"
		,	"          <img src=\"file:///mem/xowa/bin/any/xowa/file/mediawiki.file/play.png\" width=\"22\" height=\"22\" alt=\"Play sound\" />"
		,	"        </a>"
		,	"      </div>"
		,	"    </div>"
		,	"    <div class=\"thumbcaption\">"
		,	"      a"
		,	"    </div>"
		,	"    <hr/>"
		,	"    <div class=\"thumbcaption\">"
		,	"b"
		,	"    </div>"
		));		
	}
	@Test  public void Video_full() {
		fxt.Test_parse_page_wiki_str
		(	"[[File:A.ogv|400px|a|alt=b]]", String_.Concat_lines_nl_skip_last
		(	"    <div id=\"xowa_media_div\">"
		,	"      <div>"
		,	"        <a href=\"/wiki/File:A.ogv\" class=\"image\" title=\"A.ogv\">"
		,	"          <img id=\"xowa_file_img_0\" src=\"file:///\" width=\"400\" height=\"0\" alt=\"b\" />"
		,	"        </a>"
		,	"      </div>"
		,	"      <div>"
		,	"        <a id=\"xowa_file_play_0\" href=\"file:///mem/wiki/repo/trg/orig/d/0/A.ogv\" xowa_title=\"A.ogv\" class=\"xowa_anchor_button\" style=\"width:398px;max-width:400px;\">"
		,	"          <img src=\"file:///mem/xowa/bin/any/xowa/file/mediawiki.file/play.png\" width=\"22\" height=\"22\" alt=\"Play sound\" />"
		,	"        </a>"
		,	"      </div>"
		,	"    </div>"
		));		
	}
	@Test  public void Video_full_ogg() {// PURPOSE: ogg should default to video on first load; otherwise dynamic-update won't be able to put in thumb; DATE:2015-05-21
		Xof_file_fxt file_fxt = Xof_file_fxt.new_all(fxt.Wiki());
		file_fxt.Exec_orig_add(Bool_.Y, "A.ogg", Xof_ext_.Id_ogv, 400, 400, "");
		fxt.Test_parse_page_wiki_str
		( "[[File:A.ogg|400px|a|alt=b]]", String_.Concat_lines_nl_skip_last
		( "<div class=\"thumb tright\">"
		, "  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:400px;\">"
		, "    <div id=\"xowa_media_div\">"
		, "      <div>"
		, "        <a id=\"xowa_file_play_0\" href=\"file:///\" xowa_title=\"A.ogg\" class=\"xowa_anchor_button\" style=\"width:398px;max-width:1024px;\">"
		, "          <img src=\"file:///mem/xowa/bin/any/xowa/file/mediawiki.file/play.png\" width=\"22\" height=\"22\" alt=\"Play sound\" />"
		, "        </a>"
		, "      </div>"
		, "      <div>"
		, "        <a href=\"/wiki/File:A.ogg\" class=\"image\" title=\"About this file\">"
		, "          <img src=\"file:///mem/xowa/bin/any/xowa/file/mediawiki.file/info.png\" width=\"22\" height=\"22\" />"
		, "        </a>"
		, "      </div>"
		, "    </div>"
		, "    <div class=\"thumbcaption\">"
		, "      <div class=\"magnify\">"
		, "        <a href=\"/wiki/File:A.ogg\" class=\"internal\" title=\"Enlarge\">"
		, "          <img src=\"file:///mem/xowa/bin/any/xowa/file/mediawiki.file/magnify-clip.png\" width=\"15\" height=\"11\" alt=\"\" />"
		, "        </a>"
		, "      </div>"
		, "      a"
		, "    </div>"
		, "    <hr/>"
		, "    <div class=\"thumbcaption\">"
		, "b"
		, "    </div>"
		, "  </div>"
		, "</div>"
		));		
	}
	@Test  public void Video_thumb() {
		fxt.Test_parse_page_wiki_str
		(	"[[File:A.ogv|thumb|400px|a|alt=b]]", String_.Concat_lines_nl_skip_last
		(	"<div class=\"thumb tright\">"
		,	"  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"		// NOTE:220px is default w for "non-found" thumb; DATE:2014-09-24
		,	"    <div id=\"xowa_media_div\">"
		,	"      <div>"
		,	"        <a href=\"/wiki/File:A.ogv\" class=\"image\" title=\"A.ogv\">"
		,	"          <img id=\"xowa_file_img_0\" src=\"file:///\" width=\"400\" height=\"0\" alt=\"b\" />"
		,	"        </a>"
		,	"      </div>"
		,	"      <div>"
		,	"        <a id=\"xowa_file_play_0\" href=\"file:///mem/wiki/repo/trg/orig/d/0/A.ogv\" xowa_title=\"A.ogv\" class=\"xowa_anchor_button\" style=\"width:398px;max-width:400px;\">"
		,	"          <img src=\"file:///mem/xowa/bin/any/xowa/file/mediawiki.file/play.png\" width=\"22\" height=\"22\" alt=\"Play sound\" />"
		,	"        </a>"
		,	"      </div>"
		,	"    </div>"
		,	"    <div class=\"thumbcaption\">"
		,	"      <div class=\"magnify\">"
		,	"        <a href=\"/wiki/File:A.ogv\" class=\"internal\" title=\"Enlarge\">"
		,	"          <img src=\"file:///mem/xowa/bin/any/xowa/file/mediawiki.file/magnify-clip.png\" width=\"15\" height=\"11\" alt=\"\" />"
		,	"        </a>"
		,	"      </div>"
		,	"      a"
		,	"    </div>"
		,	"    <hr/>"
		,	"    <div class=\"thumbcaption\">"
		,	"b"
		,	"    </div>"
		,	"  </div>"
		,	"</div>"
		,	""
		));		
	}
	@Test  public void Video_thumb_webm() {	// PURPOSE: webm thumb wasn't being shown; DATE:2014-01-25
		fxt.Test_parse_page_wiki_str
		(	"[[File:A.webm|thumb|400px|a|alt=b]]", String_.Concat_lines_nl_skip_last
		(	"<div class=\"thumb tright\">"
		,	"  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"	// NOTE:220px is default w for "non-found" thumb; DATE:2014-09-24
		,	"    <div id=\"xowa_media_div\">"
		,	"      <div>"
		,	"        <a href=\"/wiki/File:A.webm\" class=\"image\" title=\"A.webm\">"
		,	"          <img id=\"xowa_file_img_0\" src=\"file:///\" width=\"400\" height=\"0\" alt=\"b\" />"
		,	"        </a>"
		,	"      </div>"
		,	"      <div>"
		,	"        <a id=\"xowa_file_play_0\" href=\"file:///mem/wiki/repo/trg/orig/3/4/A.webm\" xowa_title=\"A.webm\" class=\"xowa_anchor_button\" style=\"width:398px;max-width:400px;\">"
		,	"          <img src=\"file:///mem/xowa/bin/any/xowa/file/mediawiki.file/play.png\" width=\"22\" height=\"22\" alt=\"Play sound\" />"
		,	"        </a>"
		,	"      </div>"
		,	"    </div>"
		,	"    <div class=\"thumbcaption\">"
		,	"      <div class=\"magnify\">"
		,	"        <a href=\"/wiki/File:A.webm\" class=\"internal\" title=\"Enlarge\">"
		,	"          <img src=\"file:///mem/xowa/bin/any/xowa/file/mediawiki.file/magnify-clip.png\" width=\"15\" height=\"11\" alt=\"\" />"
		,	"        </a>"
		,	"      </div>"
		,	"      a"
		,	"    </div>"
		,	"    <hr/>"
		,	"    <div class=\"thumbcaption\">"
		,	"b"
		,	"    </div>"
		,	"  </div>"
		,	"</div>"
		,	""
		));		
	}
}
//		@Test  public void Ogg() {
//			fxt.Src_en_wiki_repo().Ext_rules().Get_or_new(Xof_ext_.Bry_ogg).View_max_(0);
//			fxt	.ini_page_api("commons", "A.ogg", "", 0, 0);
//			fxt	.Lnki_orig_("A.ogg")
//				.Src(	)
//				.Trg(	
//						fxt.reg_("mem/xowa/file/#meta/en.wikipedia.org/4/42.csv", "A.ogg|z||2?0,0|0?0,0")
//					)
//				.tst();
//			fxt	.Lnki_orig_("A.ogg")
//			.Html_orig_src_("file:///mem/trg/en.wikipedia.org/raw/4/2/A.ogg")
//			.tst();
//			fxt.Src_en_wiki_repo().Ext_rules().Get_or_new(Xof_ext_.Bry_ogg).View_max_(-1);
//		}
