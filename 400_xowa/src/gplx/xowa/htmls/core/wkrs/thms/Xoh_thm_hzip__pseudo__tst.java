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
package gplx.xowa.htmls.core.wkrs.thms; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.langs.htmls.*; import gplx.xowa.htmls.core.hzips.*;
import gplx.xowa.files.*; import gplx.xowa.files.caches.*; import gplx.xowa.parsers.lnkis.*;
public class Xoh_thm_hzip__pseudo__tst {
	private final    Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	@Before public void setup() {fxt.Clear();}
	@Test   public void Fake__div_1__next_nde() {	// PURPOSE: handle fake-thumbs with pseudo thumbimage class; PAGE:s.w:Mars
		fxt.Test__bicode(String_.Replace(String_.Concat_lines_nl_skip_last
		( "<div class='thumb tright' style='width:212px;'>"
		, "<div class='thumbinner'>"
		, "<div style='margin:1px;width:202px;'>"
		, "<div class='thumbimage'>~%-eA.jpg~Image~)#?A.jpg~abc~</div>"
		, "<div class='thumbcaption' style='clear:left;'>abc</div>"
		, "</div>"
		, "</div>"
		, "</div>"
		), "'", "\""), String_.Concat_lines_nl_skip_last
		( "<div class='thumb tright' style='width:212px;'>"
		, "<div class='thumbinner'>"
		, "<div style='margin:1px;width:202px;'>"
		, "<div class='thumbimage'><a href='/wiki/Image:A.jpg' class='image' title='A.jpg' xowa_title='A.jpg'><img data-xowa-title='A.jpg' data-xoimg='0|200|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a></div>"
		, "<div class='thumbcaption' style='clear:left;'>abc</div>"
		, "</div>"
		, "</div>"
		, "</div>"
		));
	}
	@Test   public void Fake__div_1__style() {	// PURPOSE.hdiff: handle fake-thumbs with bad style; PAGE:en.w:Carlisle_United_F.C.
		fxt.Test__bicode(String_.Replace(String_.Concat_lines_nl_skip_last
		( "<div class='thumb tright'>"
		, "<div class='thumbinner' style='width: px;'>"
		, "<div style='position: relative; top: -75px; left: -px; width: 200px'>"
		, "~%-eA.jpg~Image~)#?A.jpg~abc~</div>"
		, "<div class='thumbcaption'>"
		, "<div class='magnify'>~${#7)A.jpg~</div>"
		, "abc"
		, "</div>"
		, "</div>"
		, "</div>"
		), "'", "\""), String_.Concat_lines_nl_skip_last
		( "<div class='thumb tright'>"
		, "<div class='thumbinner' style='width: px;'>"
		, "<div style='position: relative; top: -75px; left: -px; width: 200px'>"
		, "<a href='/wiki/Image:A.jpg' class='image' title='A.jpg' xowa_title='A.jpg'><img data-xowa-title='A.jpg' data-xoimg='0|200|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a></div>"
		, "<div class='thumbcaption'>"
		, "<div class='magnify'><a href='/wiki/File:A.jpg' title='File:A.jpg'>File:A.jpg</a></div>"
		, "abc"
		, "</div>"
		, "</div>"
		, "</div>"
		));
	}
	@Test   public void Fake__div_1__width_w_space() {	// PURPOSE.hdiff: handle fake-thumbs with style of "width "; PAGE:en.w:Abraham_Lincoln
		fxt.Test__bicode(String_.Replace(String_.Concat_lines_nl_skip_last
		( "<div class='thumb tright'>"
		, "<div class='thumbinner' style='width: 230px;'>"
		, "<div style='width: 230px; height: 270px; overflow: hidden;'>"
		, "~%-eA.jpg~Image~)#?A.jpg~abc~</div>"
		, "<div class='thumbcaption'>"
		, "<div class='magnify'>~${#7)A.jpg~</div>"
		, "abc"
		, "</div>"
		, "</div>"
		, "</div>"
		), "'", "\""), String_.Concat_lines_nl_skip_last
		( "<div class='thumb tright'>"
		, "<div class='thumbinner' style='width: 230px;'>"
		, "<div style='width: 230px; height: 270px; overflow: hidden;'>"
		, "<a href='/wiki/Image:A.jpg' class='image' title='A.jpg' xowa_title='A.jpg'><img data-xowa-title='A.jpg' data-xoimg='0|200|-1|-1|-1|-1' src='' width='0' height='0' alt='abc'></a></div>"
		, "<div class='thumbcaption'>"
		, "<div class='magnify'><a href='/wiki/File:A.jpg' title='File:A.jpg'>File:A.jpg</a></div>"
		, "abc"
		, "</div>"
		, "</div>"
		, "</div>"
		));
	}
	@Test   public void Fake__div_1__thumbimage() {	// PURPOSE.hdiff: handle fake-thumbs with image-map style; PAGE:en.w:UK
		String html = String_.Concat_lines_nl_skip_last
		( "<div class='thumb tright'>"
		, "<div class='thumbinner' style='width:252px;'>"
		, "<div class='thumbimage' style='width:250px;'>"
		, "<center>"
		, "<div style='width: 250px; float: none; margin: none; padding: none; border: none; clear: none; background-color: #ffffff; position: relative;'>"
		, "{0}"
		, "<div style='font-size: smaller; line-height: 10px;'>"
		, "<div style='position:absolute;text-align:center;left:150.25px;top:292px'>abc</div>"
		, "</div>"
		, "</div>"
		, "</center>"
		, "</div>"
		, "<div class='thumbcaption'>def</div>"
		, "</div>"
		, "</div>"
		);
		fxt.Test__bicode(String_.Replace(String_.Format(html, "<div style=\"background-color: ;\">~%!!A.svg~)#q~</div>"), "'", "\""), String_.Format(html, "<div style='background-color: ;'><a href='/wiki/File:A.svg' class='image' title='' xowa_title='A.svg'><img data-xowa-title='A.svg' data-xoimg='0|250|-1|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"));
	}
	@Test   public void Fake__div_1__extra_attribs() {// PURPOSE.hdiff: handle fake thumbs with extra attribs; PAGE:en.w:Wikipedia:New_CSS_framework; DATE:2016-01-11
		String html = String_.Concat_lines_nl_skip_last
		( "<div class='thumb tright'>"
		, "<div class='thumbinner' style='width:252px;color:blue;'>{0}"	// "color:blue;" is invalid attribs
		, "<div class='thumbcaption'>abc"
		, "abc</div>"
		, "</div>"
		, "</div>"
		);
		fxt.Test__bicode(String_.Replace(String_.Format(html, "~%!!A.png~)#g~"), "'", "\""), String_.Format(html, "<a href='/wiki/File:A.png' class='image' title='' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|240|-1|-1|-1|-1' src='' width='0' height='0' alt=''></a>"));
	}
	@Test   public void Fake__div_2__not_media() {	// PURPOSE.hdiff: handle fake-thumbs created through en.w:Template:Image_label_begin; PAGE:en.w:Blackburnshire; DATE:2016-01-04
		String html = String_.Concat_lines_nl_skip_last
		( "<div class='thumb tright'>"
		, "<div class='thumbinner' style='width:240px;'>"
		, "<div style='width: 240pxpx;'>"
		, "{0}"
		, "<div>abc"
		, "</div>"
		, "</div>"
		, "<div class='thumbcaption'>"
		, "{1}"
		, "bcd</div>"
		, "</div>"
		, "</div>"
		);
		fxt.Test__bicode(String_.Replace(String_.Format(html, "<div>~%!!A.png~)#g~</div>", "<div class=\"magnify\">~$a)A.png~</div>"), "'", "\""), String_.Format(html, "<div><a href='/wiki/File:A.png' class='image' title='' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|240|-1|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>", "<div class='magnify'><a href='/wiki/File:A.png' title='File:A.png'>A.png</a></div>"));
	}
}
