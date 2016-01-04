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
public class Xoh_gly_hzip_tst {
	private final Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	@Test   public void Basic() {
		fxt.Test__bicode("~'!{,L#{\"g{\"b0!A1~!1A.png~9\"D\"D{\"g{\"b0!B1~!1B.png~9\"D\"Dabc", String_.Concat_lines_nl_skip_last
		( "<ul class='gallery mw-gallery-traditional' style='max-width:978px; _width:978px;'>"
		,   "<li class='gallerybox' style='width:155px;'>"
		,     "<div style='width:155px;'>"
		,       "<div class='thumb' style='width:150px;'>"
		,         "<div style='margin:15px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "</div>"
		,       "<div class='gallerytext'>"
		,         "<p>A1</p>"
		,       "</div>"
		,     "</div>"
		,   "</li>"
		,   "<li class='gallerybox' style='width:155px;'>"
		,     "<div style='width:155px;'>"
		,       "<div class='thumb' style='width:150px;'>"
		,         "<div style='margin:15px auto;'><a href='/wiki/File:B.png' class='image' xowa_title='B.png'><img data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "</div>"
		,       "<div class='gallerytext'>"
		,         "<p>B1</p>"
		,       "</div>"
		,     "</div>"
		,   "</li>"
		, "</ul>abc"));
	}
	@Test   public void Capt_is_empty() {		// PURPOSE: handle empty caption
		fxt.Test__bicode("~'!!#{\"g{\"b0#~!1A.png~9\"D\"D{\"g{\"b0#!1A.png9\"D\"D<p>abc</p>", String_.Concat_lines_nl_skip_last
		( "<ul class='gallery mw-gallery-traditional'>"
		,   "<li class='gallerybox' style='width:155px;'>"
		,     "<div style='width:155px;'>"
		,       "<div class='thumb' style='width:150px;'>"
		,         "<div style='margin:15px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "</div>"
		,       "<div class='gallerytext'></div>"
		,     "</div>"
		,   "</li>"
		,   "<li class='gallerybox' style='width:155px;'>"
		,     "<div style='width:155px;'>"
		,       "<div class='thumb' style='width:150px;'>"
		,         "<div style='margin:15px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "</div>"
		,       "<div class='gallerytext'></div>"
		,     "</div>"
		,   "</li>"
		, "</ul><p>abc</p>"));
	}
	@Test   public void Capt_is_br() {	// PURPOSE: handle captions which have <br>, not <p>; PAGE:s.w:Sociology; DATE:2015-12-20
		fxt.Test__bicode("~'!!\"{\"g{\"b0\"<b><i>A1</i></b>~!1A.png~9\"D\"D", String_.Concat_lines_nl_skip_last
		( "<ul class='gallery mw-gallery-traditional'>"
		,   "<li class='gallerybox' style='width:155px;'>"
		,     "<div style='width:155px;'>"
		,       "<div class='thumb' style='width:150px;'>"
		,         "<div style='margin:15px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "</div>"
		,       "<div class='gallerytext'><br>"
		,         "<b><i>A1</i></b>"
		,       "</div>"
		,     "</div>"
		,   "</li>"
		, "</ul>"));
	}
	@Test   public void No_max_width() {	// PURPOSE: if no perrow=# then no "style='max-width:###; _width:###;'"
		fxt.Test__bicode("~'!!\"{\"g{\"b0!A1~!1A.png~9\"D\"D", String_.Concat_lines_nl_skip_last
		( "<ul class='gallery mw-gallery-traditional'>"
		,   "<li class='gallerybox' style='width:155px;'>"
		,     "<div style='width:155px;'>"
		,       "<div class='thumb' style='width:150px;'>"
		,         "<div style='margin:15px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "</div>"
		,       "<div class='gallerytext'>"
		,         "<p>A1</p>"
		,       "</div>"
		,     "</div>"
		,   "</li>"
		, "</ul>"));
	}
	@Test   public void Extra_cls() {		// PURPOSE: handle extra cls; EX: <gallery class='abc'>
		fxt.Test__bicode("~'1!cls1 cls2~\"{\"g{\"b0!A1~!1A.png~9\"D\"D", String_.Concat_lines_nl_skip_last
		( "<ul class='gallery mw-gallery-traditional cls1 cls2'>"
		,   "<li class='gallerybox' style='width:155px;'>"
		,     "<div style='width:155px;'>"
		,       "<div class='thumb' style='width:150px;'>"
		,         "<div style='margin:15px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "</div>"
		,       "<div class='gallerytext'>"
		,         "<p>A1</p>"
		,       "</div>"
		,     "</div>"
		,   "</li>"
		, "</ul>"));
	}
	@Test   public void Xtra_style__append() {		// PURPOSE: handle appended style; EX: <gallery perrow=4 style='color:blue; float:left;'>
		fxt.Test__bicode("~'){,Lcolor:blue; float:left;~\"{\"g{\"b0!A1~!1A.png~9\"D\"D", String_.Concat_lines_nl_skip_last
		( "<ul class='gallery mw-gallery-traditional' style='max-width:978px; _width:978px; color:blue; float:left;'>"
		,   "<li class='gallerybox' style='width:155px;'>"
		,     "<div style='width:155px;'>"
		,       "<div class='thumb' style='width:150px;'>"
		,         "<div style='margin:15px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "</div>"
		,       "<div class='gallerytext'>"
		,         "<p>A1</p>"
		,       "</div>"
		,     "</div>"
		,   "</li>"
		, "</ul>"));
	}
	@Test   public void Xtra_style__replace() {		// PURPOSE: handle replaced style; EX: <gallery style='color:blue; float:left;'>
		fxt.Test__bicode("~')!color:blue; float:left;~\"{\"g{\"b0!A1~!1A.png~9\"D\"D", String_.Concat_lines_nl_skip_last
		( "<ul class='gallery mw-gallery-traditional' style='color:blue; float:left;'>"
		,   "<li class='gallerybox' style='width:155px;'>"
		,     "<div style='width:155px;'>"
		,       "<div class='thumb' style='width:150px;'>"
		,         "<div style='margin:15px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "</div>"
		,       "<div class='gallerytext'>"
		,         "<p>A1</p>"
		,       "</div>"
		,     "</div>"
		,   "</li>"
		, "</ul>"));
	}
	@Test   public void Xtra_atr() {			// PURPOSE: handle extra atr; EX: <gallery id='abc'>
		fxt.Test__bicode("~'A! id=\"abc\"\"{\"g{\"b0!A1~!1A.png~9\"D\"D", String_.Concat_lines_nl_skip_last
		( "<ul class='gallery mw-gallery-traditional' id='abc'>"
		,   "<li class='gallerybox' style='width:155px;'>"
		,     "<div style='width:155px;'>"
		,       "<div class='thumb' style='width:150px;'>"
		,         "<div style='margin:15px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "</div>"
		,       "<div class='gallerytext'>"
		,         "<p>A1</p>"
		,       "</div>"
		,     "</div>"
		,   "</li>"
		, "</ul>"));
	}
	@Test   public void Tidy__br_at_end() {	// PURPOSE: handle inconsistent tidy behavior where <p> is put on one line ("<p>a</p>") unless it ends with <br> ("<p>a<br>\n</p>")
		fxt.Test__bicode("~'!!\"{\"g{\"b0!A1<br>~!1A.png~9\"D\"D", String_.Concat_lines_nl_skip_last
		( "<ul class='gallery mw-gallery-traditional'>"
		,   "<li class='gallerybox' style='width:155px;'>"
		,     "<div style='width:155px;'>"
		,       "<div class='thumb' style='width:150px;'>"
		,         "<div style='margin:15px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "</div>"
		,       "<div class='gallerytext'>"
		,         "<p>A1<br>"	// TIDY: <br> forces </p> to be on next line
		,         "</p>"
		,       "</div>"
		,     "</div>"
		,   "</li>"
		, "</ul>"));
	}
	@Test   public void Tidy__empty() {	// PURPOSE: no items should place </ul> on same line
		fxt.Test__bicode("~'!!!", String_.Concat_lines_nl_skip_last
		( "<ul class='gallery mw-gallery-traditional'></ul>"));	// TIDY: <ul></ul> should be on same line if 0 items
	}
}
