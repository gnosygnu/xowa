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
package gplx.xowa.htmls.core.wkrs.imgs;
import gplx.types.basics.utls.StringUtl;
import org.junit.*; import gplx.xowa.htmls.core.makes.tests.*; import gplx.xowa.parsers.lnkis.*;
public class Xoh_img_make__dump__tst {
	private final Xoh_make_fxt fxt = new Xoh_make_fxt();
	@Before public void Init() {fxt.Clear();}
	@Test public void Basic() {
		String
		  orig = "<a href='/wiki/File:A.png' class='image' title='abc' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|220|110|0.5|-1|-1' src='file:///mem/xowa/file/en.wikipedia.org/thumb/7/0/A.png/220px.png' width='220' height='110' alt='abc'></a>"
		, expd = "<a href='/wiki/File:A.png' class='image' title='abc' xowa_title='A.png'><img id='xoimg_0' data-xowa-title='A.png' data-xoimg='0|220|110|0.5|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		;
		fxt.Test__make(orig, fxt.Page_chkr().Body_(expd)
			.Imgs__add("en.w", "A.png", Xop_lnki_type.Id_null, 0.5, 220, 110, -1, -1)
		);
	}
	@Test public void Utf8() {
		// fsdb_itm.lnki_ttl comes from data-xowa-title
		String
		  orig = "<a href='/wiki/File:A%C3%A9_b.png' class='image' title='abc' xowa_title='A%C3%A9_b.png'><img data-xowa-title='A%C3%A9_b.png' data-xoimg='0|220|110|0.5|-1|-1' src='' width='220' height='110' alt='abc'></a>"
		, expd = "<a href='/wiki/File:A%C3%A9_b.png' class='image' title='abc' xowa_title='A%C3%A9_b.png'><img id='xoimg_0' data-xowa-title='A%C3%A9_b.png' data-xoimg='0|220|110|0.5|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		;
		fxt.Test__make(orig, fxt.Page_chkr().Body_(expd)
			.Imgs__add("en.w", "Aé_b.png", Xop_lnki_type.Id_null, 0.5, 220, 110, -1, -1)
		);
	}
	@Test public void Utf8_src() {
		// fsdb_itm.lnki_ttl comes from src (unsupported use-case for make which should assume no pre-existing files)
		String
		  orig = "<a href='/wiki/File:A%C3%A9_b.png' class='image' title='abc' xowa_title='A%C3%A9_b.png'><img data-xowa-title='A%C3%A9_b.png' data-xoimg='0|220|110|0.5|-1|-1' src='file:///mem/xowa/file/en.wikipedia.org/thumb/7/0/A%C3%A9_b.png/220px.png' width='220' height='110' alt='abc'></a>"
		, expd = "<a href='/wiki/File:A%C3%A9_b.png' class='image' title='abc' xowa_title='A%C3%A9_b.png'><img id='xoimg_0' data-xowa-title='A%C3%A9_b.png' data-xoimg='0|220|110|0.5|-1|-1' src='' width='0' height='0' alt='abc'></a>"
		;
		fxt.Test__make(orig, fxt.Page_chkr().Body_(expd)
			.Imgs__add("en.w", "Aé_b.png", Xop_lnki_type.Id_null, 0.5, 220, 110, -1, -1)
		);
	}
	@Test public void Media_div() {
		String orig = StringUtl.ConcatLinesNl("<div class='thumb tright'>"
				, "<div class='thumbinner' style='width:220px;'>"
				, "<div class='xowa_media_div'>"
				, "<div><a href='/wiki/File:A.ogv' class='image' title='A.ogv' xowa_title='A.ogv'><img src='file:////home/lnxusr/xowa/file/commons.wikimedia.org/thumb/4/2/8/6/A.ogv/220px.jpg' width='220' height='165' alt=''></a></div>"
				, "<div><a href='' xowa_title='A.ogv' class='xowa_media_play' style='width:218px;max-width:220px;' alt='Play sound'></a></div>"
				, "</div>"
				, "<div class='thumbcaption'>"
				, "<div class='magnify'><a href='/wiki/File:A.ogv' class='@gplx.frameworks.objects.Internal protected' title='abcde'></a></div>"
				, "xyz</div>"
				, "</div>"
				, "</div>");
		String expd = StringUtl.ConcatLinesNl("<div class='thumb tright'>"
				, "<div class='thumbinner' style='width:220px;'>"
				, "<div class='xowa_media_div'>"
				, "<div><a href='/wiki/File:A.ogv' class='image' title='A.ogv' xowa_title='A.ogv'><img id='xoimg_0' src='file:////home/lnxusr/xowa/file/commons.wikimedia.org/thumb/4/2/8/6/A.ogv/220px.jpg' width='220' height='165' alt=''></a></div>"
				, "<div><a id='xowa_file_play_0' href='' xowa_title='A.ogv' class='xowa_media_play' style='width:218px;max-width:220px;' alt='Play sound'></a></div>"
				, "</div>"
				, "<div class='thumbcaption'>"
				, "<div class='magnify'><a href='/wiki/File:A.ogv' class='@gplx.frameworks.objects.Internal protected' title='abcde'></a></div>"
				, "xyz</div>"
				, "</div>"
				, "</div>");
		fxt.Test__make(orig, fxt.Page_chkr().Body_(expd)
			.Imgs__add("c", "A.ogv", Xop_lnki_type.Tid_orig_known, -1, 220, -1, -1, -1)
		);
	}
//		@Test public void Html_exists__y__cache() {
//			String
//			  orig = "<a href='/wiki/File:A.png' class='image' title='abc'><img alt='abc' data-xoimg='0|220|110|0.5|-1|-1' src='file:///mem/xowa/file/en.wikipedia.org/thumb/7/0/A.png/220px.png' width='220' height='110'/></a>"
//			, expd = "<a href='/wiki/File:A.png' class='image' title='abc'><img id='xoimg_0' alt='abc' src='file:///mem/xowa/file/commons.wikimedia.org/thumb/7/0/B.png/330px.png' width='330' height='110'/></a>"
//			;
//			fxt.Init_img_cache("en.w", "A.png", 0, 0.5, 220, 110, -1, -1, BoolUtl.Y, "B.png", 330, 110, -1, -1);
//			fxt.Test__make(orig
//			, fxt.Page_chkr().Body_(expd)
//				.Imgs__add("en.w", "A.png", 0, 0.5, 220, 110, -1, -1)
//			);
//		}
//		@Test public void Html_exists__y__html() {
//			String
//			  orig = "<a href='/wiki/File:A.png' class='image' title='abc'><img alt='abc' src='file:///mem/xowa/file/en.wikipedia.org/thumb/7/0/A.png/220px.png' width='330' height='110'/></a>"
//			, expd = "<a href='/wiki/File:A.png' class='image' title='abc'><img id='xoimg_0' alt='abc' src='file:///mem/xowa/file/commons.wikimedia.org/thumb/7/0/B.png/330px.png' width='330' height='110'/></a>"
//			;
//			fxt.Test__make(orig
//			, fxt.Page_chkr().Body_(expd)
//				.Imgs__add("en.w", "A.png", 0, 0.5, 220, 110, -1, -1)
//			);
//		}
}
