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
//namespace gplx.xowa.htmls.core.wkrs.addons.medias {
//	import org.junit.*; using gplx.core.tests;
//	using gplx.langs.htmls; using gplx.xowa.htmls.core.makes.tests;
//	public class Xoh_media_hzip_tst {
//		private final    Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
//		@Test   public void Video__full__ogv__width_y() {	// EX: [[File:A.ogv|320px|bcd|alt=efg]]; DATE:2016-08-05
//			fxt.Test__bicode	// NOTE: caption not rendered; may need to reconsider;
//			( "*!!aA.ogv~)$bA.ogv~efg~", String_.Concat_lines_nl_skip_last
//			( "<div class='xowa_media_div'>"
//			,   "<div>" 
//			+     "<a href='/wiki/File:A.ogv' class='image' title='A.ogv' xowa_title='A.ogv'>"
//			+       "<img data-xowa-title='A.ogv' data-xoimg='0|320|-1|-1|-1|-1' src='' width='0' height='0' alt='efg'>"
//			+     "</a>"
//			+   "</div>"
//			,   "<div>" 
//			+     "<a href='' xowa_title='A.ogv' class='xowa_media_play' style='width:318px;max-width:320px;' alt='Play sound'>"
//			+     "</a>"
//			+   "</div>"
//			, "</div>"
//			));
//		}
//		@Test   public void Video__full__ogv__width_n() {	// EX: [[File:A.ogv]]; DATE:2016-08-05
//			fxt.Test__bicode
//			( "*!!aA.ogv~!A.ogv~efg~", String_.Concat_lines_nl_skip_last
//			( "<div class='xowa_media_div'>"
//			,   "<div>" 
//			+     "<a href='/wiki/File:A.ogv' class='image' title='A.ogv' xowa_title='A.ogv'>"
//			+       "<img data-xowa-title='A.ogv' data-xoimg='0|-1|-1|-1|-1|-1' src='' width='0' height='0' alt='efg'>"
//			+     "</a>"
//			+   "</div>"
//			,   "<div>" 
//			+     "<a href='' xowa_title='A.ogv' class='xowa_media_play' style='width:218px;max-width:220px;' alt='Play sound'>"	// NOTE: default to 220px if no w specified
//			+     "</a>"
//			+   "</div>"
//			, "</div>"
//			));
//		}
//		@Test   public void Audio__thumb() {
//			String html = Gfh_utl.Replace_apos(String_.Concat_lines_nl
//			( "<div class='thumb tright'>"
//			, "  <div class='thumbinner' style='width:11px;'>"
//			, "    <div class='xowa_media_div'>"
//			,       "<div><a href='' xowa_title='A.oga' class='xowa_media_play' style='width:318px;max-width:320px;' alt='Play sound'></a></div>"
//			,       "<div><a href='/wiki/File:A.oga' class='xowa_media_info' title='About this file'></a></div>"
//			, "</div>"
//			, "    <div class='thumbcaption'>"
//			,       "<div class='magnify'><a href='/wiki/File:A.oga' class='internal' title='Enlarge'></a></div>b"
//			, "    </div>"
//			, "  </div>"
//			, "</div>"
//			));
//			
//			String hzip = String_.Concat_lines_nl
//			( "<div class=\"thumb tright\">"
//			, "  <div class=\"thumbinner\" style=\"width:11px;\">"
//			, "    ~*#$bA.oga"
//			, "    <div class=\"thumbcaption\">"
//			,       "<div class=\"magnify\">$|%%,)A.ogaEnlarge</div>b"
//			, "    </div>"
//			, "  </div>"
//			, "</div>"
//			);
//			fxt.Test__bicode(hzip, html);
//		}
//		@Test   public void Audio__noicon() {
//			String html = Gfh_utl.Replace_apos(String_.Concat_lines_nl
//			( "    <div class='xowa_media_div'>"
//			,       "<div><a href='' xowa_title='A.oga' class='xowa_media_play' style='width:318px;max-width:320px;' alt='Play sound'></a></div>"
//			, "</div>"
//			));
//			
//			String hzip = String_.Concat_lines_nl
//			( "    ~*$$bA.oga"
//			);
//			fxt.Test__bicode(hzip, html);
//		}
//	}
//}
