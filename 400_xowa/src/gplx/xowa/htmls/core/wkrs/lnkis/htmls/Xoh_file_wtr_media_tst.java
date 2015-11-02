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
import org.junit.*;
public class Xoh_file_wtr_media_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Before public void init() {fxt.Reset();}
	@Test  public void Lnki_caption_nested_media() { // PAGE:en.w:Beethoven;
		fxt.Test_parse_page_wiki_str("[[File:A.png|thumb|b [[Media:A.ogg]] c]]", String_.Concat_lines_nl_skip_last
		(	"<div class=\"thumb tright\">"
		,	"  <div id=\"xowa_file_div_0\" class=\"thumbinner\" style=\"width:220px;\">"
		,	"    <a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/220px.png\" width=\"0\" height=\"0\" /></a>"
		,	"    <div class=\"thumbcaption\">"
		,	"      <div class=\"magnify\">"
		,	"        <a href=\"/wiki/File:A.png\" class=\"internal\" title=\"Enlarge\">"
		,	"          <img src=\"file:///mem/xowa/bin/any/xowa/file/mediawiki.file/magnify-clip.png\" width=\"15\" height=\"11\" alt=\"\" />"
		,	"        </a>"
		,	"      </div>"
		,	"      b <a href=\"file:///mem/wiki/repo/trg/orig/4/2/A.ogg\" xowa_title=\"A.ogg\">"
		,	"</a> c"
		,	"    </div>"
		,	"  </div>"
		,	"</div>"
		,	""
		));
	}
	@Test  public void Lnki_media_normal() {
		fxt.Test_parse_page_wiki_str("[[Media:A.png|b]]", String_.Concat_lines_nl_skip_last
		(	"<a href=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" xowa_title=\"A.png\">b"
		,	"</a>"
		));
	}
	@Test  public void Lnki_media_literal() {
		fxt.Test_parse_page_wiki_str("[[:Media:A.ogg|b]]", String_.Concat_lines_nl_skip_last
		(	"<a href=\"file:///mem/wiki/repo/trg/orig/4/2/A.ogg\" xowa_title=\"A.ogg\">b"
		,	"</a>"
		));
	}
	@Test  public void Lnki_media_literal_pdf() {
		fxt.Wiki().Html_mgr().Img_suppress_missing_src_(true);	// simulate missing file; DATE:2014-01-30
		fxt.Test_parse_page_wiki_str("[[Media:A.pdf|b]]", String_.Concat_lines_nl_skip_last
		(	"<a href=\"file:///mem/wiki/repo/trg/orig/e/f/A.pdf\" xowa_title=\"A.pdf\">b"
		,	"</a>"
		));
		Tfds.Eq(0, fxt.Page().File_queue().Count());			// make sure media does not add to queue
		fxt.Wiki().Html_mgr().Img_suppress_missing_src_(false);
	}
}
