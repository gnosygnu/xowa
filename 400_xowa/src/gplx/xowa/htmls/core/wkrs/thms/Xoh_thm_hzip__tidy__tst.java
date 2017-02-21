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
public class Xoh_thm_hzip__tidy__tst {
	private final    Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	@Before public void setup() {fxt.Clear();}
	@Test   public void Tidy__moved_capt() {
		fxt.Test__bicode("~&S~abc\n~!uA.png~)#Sabc~", String_.Concat_lines_nl_skip_last
		( "<div class='thumb tleft'>"
		,   "<div class='thumbinner' style='width:220px;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' class='thumbimage' alt='abc'></a> "
		,     "<div class='thumbcaption'>"
		,       "<div class='magnify'><a href='/wiki/File:A.png' class='internal' title='Enlarge'></a></div>"
		,     "</div>abc"
		,   "</div>"
		, "</div>"
		));
	}
	@Test   public void Tidy__extra_closing_div() {// handle extra closing div in caption; PAGE:en.w:Damask; DATE:2016-01-05
		fxt.Test__bicode(String_.Concat_lines_nl_skip_last
		( "~&U<div>A</div>"
		, "B~"
		, "C~!1A.png~-\"b"), String_.Concat_lines_nl_skip_last
		( "<div class='thumb tright'>"
		, "<div class='thumbinner' style='width:220px;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='4|150|-1|-1|-1|-1' src='' width='0' height='0' alt=''></a> "
		, "<div class='thumbcaption'>"
		, "<div class='magnify'><a href='/wiki/File:A.png' class='internal' title='Enlarge'></a></div>"
		, "<div>A</div>"
		, "B</div>"
		, "C</div>"
		, "</div>"
		));
	}
	@Test   public void Tidy__ul() {	// tidy will move <div> on to different lines depending on <ul>; PAGE:en.w:Chimney_sweep; DATE:2016-01-05
		fxt.Test__bicode(String_.Concat_lines_nl_skip_last
		( "~&=$b* ABC~"
		, "<ul>"
		, "<li>ABC</li>"
		, "</ul>"
		, "~!uA.png~-$bABC~"), String_.Concat_lines_nl_skip_last
		( "<div class='thumb tright'>"
		, "<div class='thumbinner' style='width:320px;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='4|320|-1|-1|-1|-1' src='' width='0' height='0' class='thumbimage' alt='ABC'></a> "
		, "<div class='thumbcaption'>"
		, "<div class='magnify'><a href='/wiki/File:A.png' class='internal' title='Enlarge'></a></div>"
		, "* ABC</div>"
		, "<hr>"
		, "<div class='thumbcaption'>"
		, "<ul>"
		, "<li>ABC</li>"
		, "</ul>"
		, "</div>"
		, "</div>"
		, "</div>"
		));
	}
	@Test   public void Tidy__video() {	// tidy may relocate xowa-alt-div to last div; PAGE:en.w:Non-helical_models_of_DNA_structure; DATE:2016-01-11
		fxt.Test__bicode(String_.Concat_lines_nl_skip_last
		( "~&eabc~"
		, "<hr>"
		, "<div class=\"thumbcaption\">bcd</div>"
		, "~|E9eA.ogv~%A.ogv~~"), Gfh_utl.Replace_apos(String_.Concat_lines_nl_skip_last
		( "<div class='thumb tright'>"
		,   "<div class='thumbinner' style='width:220px;'>"
		,     "<div class='xowa_media_div'>"
		,       "<div><a href='/wiki/File:A.ogv' class='image' title='A.ogv' xowa_title='A.ogv'><img data-xowa-title='A.ogv' data-xoimg='4|-1|-1|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "<div><a href='' xowa_title='A.ogv' class='xowa_media_play' style='width:218px;max-width:220px;' alt='Play sound'></a></div>"
		,     "</div>"
		,     "<div class='thumbcaption'>"
		,       "<div class='magnify'><a href='/wiki/File:A.ogv' class='internal' title='Enlarge'></a></div>"
		,       "abc</div>"
		,   "</div>"
		, "<hr>"
		, "<div class='thumbcaption'>bcd</div>"
		, "</div>"
		)));
	}
}
