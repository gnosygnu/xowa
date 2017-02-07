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
package gplx.xowa.htmls.core.wkrs.glys; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.hzips.*;
public class Xoh_gly_hzip__caption__tst {
	private final    Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	@Test   public void Capt_is_empty() {		// PURPOSE: handle empty caption
		fxt.Test__bicode("~'!!#{\"g{\"b0#~!1A.png~9\"D\"D{\"g{\"b0#!1A.png9\"D\"D<p>abc</p>", String_.Concat_lines_nl_skip_last
		( "<ul data-xogly='-1|-1|-1' class='gallery mw-gallery-traditional'>"
		,   "<li class='gallerybox' style='width:155px;'>"
		,     "<div style='width:155px;'>"
		,       "<div class='thumb' style='width:150px;'>"
		,         "<div style='margin:15px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "</div>"
		,       "<div class='gallerytext'></div>"
		,     "</div>"
		,   "</li>"
		,   "<li class='gallerybox' style='width:155px;'>"
		,     "<div style='width:155px;'>"
		,       "<div class='thumb' style='width:150px;'>"
		,         "<div style='margin:15px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "</div>"
		,       "<div class='gallerytext'></div>"
		,     "</div>"
		,   "</li>"
		, "</ul><p>abc</p>"));
	}
	@Test   public void Capt_is_br() {	// PURPOSE: handle captions which have <br>, not <p>; PAGE:s.w:Sociology; DATE:2015-12-20
		fxt.Test__bicode(String_.Concat_lines_nl_skip_last
		( "~'!!\"{\"g{\"b0\"<b><i>A1</i></b>"
		, "~!1A.png~9\"D\"D"), String_.Concat_lines_nl_skip_last
		( "<ul data-xogly='-1|-1|-1' class='gallery mw-gallery-traditional'>"
		,   "<li class='gallerybox' style='width:155px;'>"
		,     "<div style='width:155px;'>"
		,       "<div class='thumb' style='width:150px;'>"
		,         "<div style='margin:15px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "</div>"
		,       "<div class='gallerytext'><br>"
		,         "<b><i>A1</i></b>"
		,       "</div>"
		,     "</div>"
		,   "</li>"
		, "</ul>"));
	}
	@Test   public void Capt_has_multiple_p() {	// PURPOSE: handle captions with multiple <p>; PAGE:en.w:Wikipedia:Bot_requests/Archive_25; DATE:2016-01-12
		fxt.Test__bicode(String_.Concat_lines_nl_skip_last
		( "~'!!\"{\"g{\"b0!a<br>"
		, "b</p>"
		, "<p><br>"
		, "~!1A.png~9\"D\"D"), String_.Concat_lines_nl_skip_last
		( "<ul data-xogly='-1|-1|-1' class='gallery mw-gallery-traditional'>"
		, "<li class='gallerybox' style='width:155px;'>"
		, "<div style='width:155px;'>"
		, "<div class='thumb' style='width:150px;'>"
		, "<div style='margin:15px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		, "</div>"
		, "<div class='gallerytext'>"
		, "<p>a<br>"
		, "b</p>"
		, "<p><br>"
		, "</p>"
		, "</div>"
		, "</div>"
		, "</li>"
		, "</ul>"));
	}
	@Test   public void Capt_has_complicated_nl_behavior() {// handle complicated captions which force <div> on different line PAGE:en.w:Tamago_kake_gohan; DATE:2016-01-05
		fxt.Test__bicode(String_.Concat_lines_nl_skip_last
		( "~'!!\"{\"g{\"b>\"<div class=\"center\"><a href=\"/wiki/B.png\" class=\"image\" xowa_title=\"B.png\"><img data-xowa-title=\"A.png\" data-xoimg=\"0|120|-1|-1|-1|-1\" src=\"\" width=\"0\" height=\"0\" alt=\"\"></a></div>"
		, "abc~\"\\A.png~#9\"D\"D"
		), String_.Concat_lines_nl_skip_last
		( "<ul data-xogly='-1|-1|-1' class='gallery mw-gallery-traditional'>"
		, "<li class='gallerybox' style='width:155px;'>"
		, "<div style='width:155px;'>"
		, "<div class='thumb' style='width:150px;'>"
		, "<div style='margin:29px auto;'><a href='/wiki/A.png' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		, "</div>"
		, "<div class='gallerytext'><br>"
		, "<div class='center'><a href='/wiki/B.png' class='image' xowa_title='B.png'><img data-xowa-title='A.png' data-xoimg='0|120|-1|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		, "abc</div>"	// NOTE: tidy forces </div> to be on same line instead of next
		, "</div>"
		, "</li>"
		, "</ul>"
		));
	}
	@Test   public void Capt_has_hr() {// handle <hr> which causes <p> to end early; PAGE:de.v:Kurs:Photoshop_Einzell�sungen/HighKey_mit_CS5; DATE:2016-06-24
		fxt.Test__bicode(String_.Concat_lines_nl_skip_last
		( "~'!!\"{\"g{\"b>%a</p>"
		, "<hr>"
		, "b"
		, "~\"\\A.png~#9\"D\"D")
		, String_.Concat_lines_nl_skip_last
		( "<ul data-xogly='-1|-1|-1' class='gallery mw-gallery-traditional'>"
		, "<li class='gallerybox' style='width:155px;'>"
		, "<div style='width:155px;'>"
		, "<div class='thumb' style='width:150px;'>"
		, "<div style='margin:29px auto;'><a href='/wiki/A.png' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		, "</div>"
		, "<div class='gallerytext'>"
		, "<p>a</p>"	// NOTE: </p> placed before <hr>
		, "<hr>"
		, "b"			// NOTE: no <p> for "b"
		, "</div>"
		, "</div>"
		, "</li>"
		, "</ul>"
		));
	}
}
