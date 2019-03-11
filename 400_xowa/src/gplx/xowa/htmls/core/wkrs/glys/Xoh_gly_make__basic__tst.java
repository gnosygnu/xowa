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
import org.junit.*; import gplx.xowa.htmls.core.makes.tests.*; import gplx.xowa.parsers.lnkis.*;
public class Xoh_gly_make__basic__tst {
	private final    Xoh_make_fxt fxt = new Xoh_make_fxt();
	@Before public void Init() {fxt.Clear();}
	@Test   public void Media_div() {
		String orig = String_.Concat_lines_nl_skip_last
		( "<ul data-xogly='-1|-1|-1' class='gallery mw-gallery-traditional' style='max-width:978px; _width:978px;'>"
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
		,   "<li class='gallerybox' style='width:155px;'>"
		,     "<div style='width:155px;'>"
		,       "<div class='thumb' style='width:150px;'>"
		,         "<div style='margin:15px auto;'><a href='/wiki/File:B.png' class='image' xowa_title='B.png'><img data-xowa-title='B.png' data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "</div>"
		,       "<div class='gallerytext'>"
		,         "<p>B1</p>"
		,       "</div>"
		,     "</div>"
		,   "</li>"
		, "</ul>abc");
		String expd = String_.Concat_lines_nl_skip_last
		( "<ul data-xogly='-1|-1|-1' class='gallery mw-gallery-traditional' style='max-width:978px; _width:978px;'>"
		,   "<li class='gallerybox' style='width:155px;'>"
		,     "<div style='width:155px;'>"
		,       "<div class='thumb' style='width:150px;'>"
		,         "<div style='margin:15px auto;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img id='xoimg_0' data-xowa-title='A.png' data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "</div>"
		,       "<div class='gallerytext'>"
		,         "<p>A1</p>"
		,       "</div>"
		,     "</div>"
		,   "</li>"
		,   "<li class='gallerybox' style='width:155px;'>"
		,     "<div style='width:155px;'>"
		,       "<div class='thumb' style='width:150px;'>"
		,         "<div style='margin:15px auto;'><a href='/wiki/File:B.png' class='image' xowa_title='B.png'><img id='xoimg_1' data-xowa-title='B.png' data-xoimg='0|120|120|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "</div>"
		,       "<div class='gallerytext'>"
		,         "<p>B1</p>"
		,       "</div>"
		,     "</div>"
		,   "</li>"
		, "</ul>abc");
		fxt.Test__make(orig, fxt.Page_chkr().Body_(expd)
			.Imgs__add("en.w", "A.png", Xop_lnki_type.Id_null, -1, 120, 120, -1, -1)
			.Imgs__add("en.w", "B.png", Xop_lnki_type.Id_null, -1, 120, 120, -1, -1)
		);
	}
}
