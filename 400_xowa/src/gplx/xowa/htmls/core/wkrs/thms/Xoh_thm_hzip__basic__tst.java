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
public class Xoh_thm_hzip__basic__tst {
	private final    Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	@Before public void setup() {fxt.Clear();}
	@Test   public void Image_wo_alt_text() {// LEGACY: pre xowa_alt_text
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
	@Test   public void Image_w_alt_text() {// NOTE: xowa_alt_text; PAGE:es.w:Biome DATE:2017-09-04
		fxt.Test__bicode("~&{\"^abc~abc~!uA.png~)#Sabc~", String_.Concat_lines_nl_skip_last
		( "<div class='thumb tleft'>"
		,   "<div class='thumbinner' style='width:220px;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|220|-1|-1|-1|-1' src='' width='0' height='0' class='thumbimage' alt='abc'></a> "
		,     "<div class='thumbcaption'>"
		,       "<div class='magnify'><a href='/wiki/File:A.png' class='internal' title='Enlarge'></a></div>"
		,       "abc</div>"
		,     "<div class=\"xowa_alt_text\">"
		,     "<hr>"
		,     "<div class='thumbcaption'>abc</div>"
		,     "</div>"
		,   "</div>"
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
	@Test   public void Div_width_uses_img_width() {
		Xof_fsdb_itm itm = new Xof_fsdb_itm();
		itm.Init_at_lnki(Xof_exec_tid.Tid_wiki_page, Bry_.new_a7("en.w"), Bry_.new_a7("A.png"), Xop_lnki_type.Id_null, -1, 220, -1, -1, -1, 0);
		itm.Init_at_cache(true, 400, 440, Io_url_.mem_fil_("mem/A.png"));
		Xou_cache_finder_mem finder = fxt.Init_file_mgr__mem();
		finder.Add(itm);

		fxt.Test__decode("~&3abc~abc~!uA.png~)#Sabc~", String_.Concat_lines_nl_skip_last
		( "<div class='thumb tleft'>"
		,   "<div class='thumbinner' style='width:400px;'><a href='/wiki/File:A.png' class='image' xowa_title='A.png'><img data-xowa-title='A.png' data-xoimg='0|220|-1|-1|-1|-1' src='file:///mem/A.png' width='400' height='440' class='thumbimage' alt='abc'></a> "
		,     "<div class='thumbcaption'>"
		,       "<div class='magnify'><a href='/wiki/File:A.png' class='internal' title='Enlarge'></a></div>"
		,       "abc</div>"
		,     "<hr>"
		,     "<div class='thumbcaption'>abc</div>"
		,   "</div>"
		, "</div>"
		));

		fxt.Init_file_mgr__noop();
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
