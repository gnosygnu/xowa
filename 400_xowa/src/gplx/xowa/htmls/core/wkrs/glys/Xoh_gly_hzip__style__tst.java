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
package gplx.xowa.htmls.core.wkrs.glys; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.hzips.*;
public class Xoh_gly_hzip__style__tst {
	private final    Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	@Test   public void Style__no_max_width() {	// PURPOSE: if no perrow=# then no "style='max-width:###; _width:###;'"
		fxt.Test__bicode("~'!!\"{\"g{\"b0!A1~!1A.png~9\"D\"D", String_.Concat_lines_nl_skip_last
		( "<ul data-xogly='-1|-1|-1' class='gallery mw-gallery-traditional'>"
		,   "<li class='gallerybox' style='width:155px;'>"
		,     "<div style='width:155px;'>"
		,       "<div class='thumb' style='width:150px;'>"
		,         "<div style='margin:15px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "</div>"
		,       "<div class='gallerytext'>"
		,         "<p>A1</p>"
		,       "</div>"
		,     "</div>"
		,   "</li>"
		, "</ul>"));
	}
	@Test   public void Style__no_width() {	// PURPOSE: if "_width" omitted, do not add back; EX: style="max-width:648px; margin:auto; background:transparent;"; PAGE:en.w:Wikipedia:Village_pump_(technical)/Archive_86 DATE:2016-01-12
		fxt.Test__bicode("~'i{,L! color:blue;~\"{\"g{\"b0!A1~!1A.png~9\"D\"D", String_.Concat_lines_nl_skip_last
		( "<ul data-xogly='-1|-1|-1' class='gallery mw-gallery-traditional' style='max-width:978px; color:blue;'>"
		,   "<li class='gallerybox' style='width:155px;'>"
		,     "<div style='width:155px;'>"
		,       "<div class='thumb' style='width:150px;'>"
		,         "<div style='margin:15px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "</div>"
		,       "<div class='gallerytext'>"
		,         "<p>A1</p>"
		,       "</div>"
		,     "</div>"
		,   "</li>"
		, "</ul>"));
	}
	@Test   public void Style__max_width_duped() {	// PURPOSE: if max-width duped, do not delete 2nd; EX: style="max-width:648px; color:blue; max-width:648px;"; PAGE:en.w:Wikipedia:Village_pump_(technical)/Archive_86 DATE:2016-01-12
		fxt.Test__bicode("~'){(Z max-width:648px; color:blue;~\"{\"g{\"b0!A1~!1A.png~9\"D\"D", String_.Concat_lines_nl_skip_last
		( "<ul data-xogly='-1|-1|-1' class='gallery mw-gallery-traditional' style='max-width:652px; _width:652px; max-width:648px; color:blue;'>"
		,   "<li class='gallerybox' style='width:155px;'>"
		,     "<div style='width:155px;'>"
		,       "<div class='thumb' style='width:150px;'>"
		,         "<div style='margin:15px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "</div>"
		,       "<div class='gallerytext'>"
		,         "<p>A1</p>"
		,       "</div>"
		,     "</div>"
		,   "</li>"
		, "</ul>"));
	}
	@Test   public void Style__append() {		// PURPOSE: handle appended style; EX: <gallery perrow=4 style='color:blue; float:left;'>
		fxt.Test__bicode("~'){,L color:blue; float:left;~\"{\"g{\"b0!A1~!1A.png~9\"D\"D", String_.Concat_lines_nl_skip_last
		( "<ul data-xogly='-1|-1|-1' class='gallery mw-gallery-traditional' style='max-width:978px; _width:978px; color:blue; float:left;'>"
		,   "<li class='gallerybox' style='width:155px;'>"
		,     "<div style='width:155px;'>"
		,       "<div class='thumb' style='width:150px;'>"
		,         "<div style='margin:15px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "</div>"
		,       "<div class='gallerytext'>"
		,         "<p>A1</p>"
		,       "</div>"
		,     "</div>"
		,   "</li>"
		, "</ul>"));
	}
	@Test   public void Style__invalid_unclosed() {	// handle broken styles; EX: <gallery style='center'>
		fxt.Test__bicode("~'9!center~center~\"{\"g{\"bl!abc~!1A.png~9\"D\"D", String_.Concat_lines_nl_skip_last
		( "<ul data-xogly='-1|-1|-1' class='gallery mw-gallery-traditional center' style='center'>"
		, "<li class='gallerybox' style='width:155px;'>"
		, "<div style='width:155px;'>"
		, "<div class='thumb' style='width:150px;'>"
		, "<div style='margin:75px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		, "</div>"
		, "<div class='gallerytext'>"
		, "<p>abc</p>"
		, "</div>"
		, "</div>"
		, "</li>"
		, "</ul>"
		));
	}
	@Test   public void Style__extra_colon() {	// handle broken styles; EX: <gallery style='a:b:c:d;' PAGE:en.w:Bronze_Horseman DATE:2016-01-05
		fxt.Test__bicode("~'9!center~color:red:float:right;~\"{\"g{\"bl!abc~!1A.png~9\"D\"D", String_.Concat_lines_nl_skip_last
		( "<ul data-xogly='-1|-1|-1' class='gallery mw-gallery-traditional center' style='color:red:float:right;'>"
		, "<li class='gallerybox' style='width:155px;'>"
		, "<div style='width:155px;'>"
		, "<div class='thumb' style='width:150px;'>"
		, "<div style='margin:75px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		, "</div>"
		, "<div class='gallerytext'>"
		, "<p>abc</p>"
		, "</div>"
		, "</div>"
		, "</li>"
		, "</ul>"
		));
	}
	@Test   public void Style__replace() {		// PURPOSE: handle replaced style; EX: <gallery style='color:blue; float:left;'>
		fxt.Test__bicode("~')!color:blue; float:left;~\"{\"g{\"b0!A1~!1A.png~9\"D\"D", String_.Concat_lines_nl_skip_last
		( "<ul data-xogly='-1|-1|-1' class='gallery mw-gallery-traditional' style='color:blue; float:left;'>"
		,   "<li class='gallerybox' style='width:155px;'>"
		,     "<div style='width:155px;'>"
		,       "<div class='thumb' style='width:150px;'>"
		,         "<div style='margin:15px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "</div>"
		,       "<div class='gallerytext'>"
		,         "<p>A1</p>"
		,       "</div>"
		,     "</div>"
		,   "</li>"
		, "</ul>"));
	}
	@Test   public void Style__ws() {		// PURPOSE: handle ws in style; EX: <gallery class="gallery mw-gallery-traditional" style="max-width:1115px; _width:1115px;  color:blue;'>; PAGE:en.w:Anti-Serb_sentiment; DATE:2016-01-08
		fxt.Test__bicode("~'){,L  color:blue;~\"{\"g{\"b0!A1~!1A.png~9\"D\"D", String_.Concat_lines_nl_skip_last
		( "<ul data-xogly='-1|-1|-1' class='gallery mw-gallery-traditional' style='max-width:978px; _width:978px;  color:blue;'>"
		,   "<li class='gallerybox' style='width:155px;'>"
		,     "<div style='width:155px;'>"
		,       "<div class='thumb' style='width:150px;'>"
		,         "<div style='margin:15px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "</div>"
		,       "<div class='gallerytext'>"
		,         "<p>A1</p>"
		,       "</div>"
		,     "</div>"
		,   "</li>"
		, "</ul>"));
	}
}
