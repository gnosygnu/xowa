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
package gplx.xowa.htmls.core.wkrs.thms; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.makes.tests.*;
public class Xoh_thm_html_tst {
	private final Xoh_make_fxt fxt = new Xoh_make_fxt();
	@Test   public void Image() {
		fxt.Test__html("[[File:A.png|thumb|test_caption]]", String_.Concat_lines_nl_skip_last
		( "<div class='thumb tright'>"
		, "  <div class='thumbinner' style='width:220px;'>"
		, "    <a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xowa-title=\"A.png\" data-xoimg='4|-1|-1|-1|-1|-1' src='' width='0' height='0' alt=''/></a>"
		, "    <div class='thumbcaption'>"
		,       "<div class='magnify'><a href='/wiki/File:A.png' class='internal' title='Enlarge'></a></div>test_caption"
		, "    </div>"
		, "  </div>"
		, "</div>"
		));
	}
	@Test  public void Audio__link() {	// PURPOSE: handle IPA links; EX:[[File:Speakerlink-new.svg|11px|link=file:///C:/xowa/file/commons.wikimedia.org/orig/c/7/a/3/En-LudwigVanBeethoven.ogg|Listen]]; PAGE:en.w:Beethoven DATE:2015-12-28
		fxt.Test__html("[[File:A.oga|11px|link=file:///C:/A.ogg|b]]", String_.Concat_lines_nl_skip_last
		( "<div class=\"thumb tright\">"
		, "  <div class=\"thumbinner\" style=\"width:11px;\">"
		, "    <div class=\"xowa_media_div\">"
		,       "<div><a href=\"\" xowa_title=\"A.oga\" class=\"xowa_media_play\" style=\"width:218px;max-width:220px;\" alt=\"Play sound\"></a></div>"
		,       "<div><a href=\"/wiki/File:A.oga\" class=\"xowa_media_info\" title=\"About this file\"></a></div>"
		, "    </div>"
		, "    <div class=\"thumbcaption\">"
		,       "<div class=\"magnify\"><a href=\"/wiki/File:A.oga\" class=\"internal\" title=\"Enlarge\"></a></div>b"
		, "    </div>"
		, "  </div>"
		, "</div>"
		, ""
		));		
	}
	@Test   public void Video() {
		fxt.Test__html("[[File:A.ogv|thumb|test_caption]]", String_.Concat_lines_nl_skip_last
		( "<div class=\"thumb tright\">"
		, "  <div class=\"thumbinner\" style=\"width:220px;\">"
		, "    <div class=\"xowa_media_div\">"
		, "      <div><a href=\"/wiki/File:A.ogv\" class=\"image\" title=\"A.ogv\" xowa_title=\"A.ogv\"><img data-xowa-title=\"A.ogv\" data-xoimg=\"4|-1|-1|-1|-1|-1\" src=\"\" width=\"0\" height=\"0\" alt=\"\"/></a>"
		, "      </div>"
		,       "<div><a href=\"\" xowa_title=\"A.ogv\" class=\"xowa_media_play\" style=\"width:218px;max-width:220px;\" alt=\"Play sound\"></a></div>"
		, "    </div>"
		, "    <div class=\"thumbcaption\">"
		,       "<div class=\"magnify\"><a href=\"/wiki/File:A.ogv\" class=\"internal\" title=\"Enlarge\"></a></div>test_caption"
		, "    </div>"
		, "  </div>"
		, "</div>"
		));
	}
}
