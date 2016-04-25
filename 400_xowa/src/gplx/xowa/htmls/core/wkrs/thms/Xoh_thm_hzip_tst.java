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
import org.junit.*; import gplx.langs.htmls.*; import gplx.xowa.htmls.core.hzips.*;
public class Xoh_thm_hzip_tst {
	private final    Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	@Before public void setup() {fxt.Clear();}
	@Test   public void Image() {
		fxt.Test__bicode("~&3abc~abc~!uA.png~)#Sabc~", String_.Concat_lines_nl_skip_last
		( "<div class='thumb tleft'>"
		,   "<div class='thumbinner' style='width:220px;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' class='thumbimage' alt='abc'></a> "
		,     "<div class='thumbcaption'>"
		,       "<div class='magnify'><a href='/wiki/File:A.png' class='internal' title='Enlarge'></a></div>"
		,       "abc</div>"
		,     "<hr>"
		,     "<div class='thumbcaption'>abc</div>"
		,   "</div>"
		, "</div>"
		));
	}
	@Test   public void Video() {
		fxt.Test__bicode("~&%test_caption~|E9eA.ogv~%A.ogv~~", Gfh_utl.Replace_apos(String_.Concat_lines_nl_skip_last
		( "<div class='thumb tright'>"
		,   "<div class='thumbinner' style='width:220px;'>"
		,     "<div class='xowa_media_div'>"
		,       "<div><a href='/wiki/File:A.ogv' class='image' title='A.ogv' xowa_title='A.ogv'><img data-xowa-title='A.ogv' data-xoimg='4|-1|-1|-1|-1|-1' src='' width='0' height='0' alt=''></a></div>"
		,       "<div><a href='' xowa_title='A.ogv' class='xowa_media_play' style='width:218px;max-width:220px;' alt='Play sound'></a></div>"
		,     "</div>"
		,     "<div class='thumbcaption'>"
		,       "<div class='magnify'><a href='/wiki/File:A.ogv' class='internal' title='Enlarge'></a></div>"
		,       "test_caption</div>"
		,   "</div>"
		, "</div>"
		)));
	}
	@Test   public void Audio() {
		fxt.Test__bicode(String_.Replace(String_.Concat_lines_nl
		( "<div class='thumb tright'>"
		, "  <div class='thumbinner' style='width:11px;'>"
		, "    <div class='xowa_media_div'>"
		,       "<div><a href='' xowa_title='A.oga' class='xowa_media_play' style='width:218px;max-width:220px;' alt='Play sound'/></div>"
		,       "<div>~$|#na)~A.oga~About this file~</div>"
		, "    </div>"
		, "    <div class='thumbcaption'>"
		,       "<div class='magnify'>~$|%%,)~A.oga~Enlarge~</div>b"
		, "    </div>"
		, "  </div>"
		, "</div>"
		), "'", "\""), String_.Concat_lines_nl_skip_last
		( "<div class='thumb tright'>"
		, "  <div class='thumbinner' style='width:11px;'>"
		, "    <div class='xowa_media_div'>"
		,       "<div><a href='' xowa_title='A.oga' class='xowa_media_play' style='width:218px;max-width:220px;' alt='Play sound'/></div>"
		,       "<div><a href='/wiki/File:A.oga' class='xowa_media_info' title='About this file'></a></div>"
		, "    </div>"
		, "    <div class='thumbcaption'>"
		,       "<div class='magnify'><a href='/wiki/File:A.oga' class='internal' title='Enlarge'></a></div>b"
		, "    </div>"
		, "  </div>"
		, "</div>"
		));		
	}
	@Test   public void Capt_is_missing() {	// [[File:A.png|thumb]]
		fxt.Test__bicode("~&#~!%A.png~)#S~", String_.Concat_lines_nl_skip_last
		( "<div class='thumb tleft'>"
		,   "<div class='thumbinner' style='width:220px;'><a href='/wiki/File:A.png' class='image' title='' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' class='thumbimage' alt=''></a> "
		,     "<div class='thumbcaption'>"
		,       "<div class='magnify'><a href='/wiki/File:A.png' class='internal' title='Enlarge'></a></div>"
		,       "</div>"
		,   "</div>"
		, "</div>"
		));
	}
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
	@Test   public void Fix__omitted_table_tail() {	// PURPOSE.hdiff: handle omitted </table>; PAGE:en.w:Alphabet; DATE:2016-01-06
		fxt.Test__bicode(String_.Concat_lines_nl_skip_last
		( "~&]&D~"
		, "<table>"
		, "<tr>"
		, "<td>"
		, "abc"
		, "</td>"
		, "</tr>"
		, "</table>"
		, "~!5A.png~-&D"
		), String_.Concat_lines_nl_skip_last
		( "<div class='thumb tright'>"
		, "<div class='thumbinner' style='width:460px;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='4|460|-1|-1|-1|-1' src='' width='0' height='0' class='thumbimage' alt=''></a> "
		, "<div class='thumbcaption'>"
		, "<div class='magnify'><a href='/wiki/File:A.png' class='internal' title='Enlarge'></a></div>"
		, "</div>"
		, "<table>"
		, "<tr>"
		, "<td>"
		, "abc"
		, "</td>"
		, "</tr>"
		, "</table>"
		, "</div>"
		, "</div>"
		));
	}
//		@Test   public void Dump() {
//			Xowe_wiki en_d = fxt.Init_wiki_alias("wikt", "en.wiktionary.org");
//			gplx.xowa.wikis.nss.Xow_ns_mgr ns_mgr = en_d.Ns_mgr();
//			ns_mgr.Ns_main().Case_match_(gplx.xowa.wikis.nss.Xow_ns_case_.Tid__all);
//
//			fxt.Wiki().Ns_mgr().Aliases_add(gplx.xowa.wikis.nss.Xow_ns_.Tid__portal, "WP");
//			fxt.Wiki().Ns_mgr().Init();
//
//			fxt.Init_mode_is_b256_(Bool_.N);
//			fxt.Exec_write_to_fsys(Io_url_.new_dir_("C:\\xowa\\debug\\html\\"), "hzip.html");
//			fxt.Init_mode_is_b256_(Bool_.N);
//		}
}
