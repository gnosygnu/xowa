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
package gplx.xowa.parsers.miscs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*; import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
public class Xop_under_lxr_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Before public void init() {fxt.Reset();}
	@After public void term() {fxt.Init_para_n_();}
	@Test   public void Toc_basic() {
		fxt.Test_parse_page_all_str("a__TOC__b", "ab");
	}
	@Test   public void Toc_match_failed() {
		fxt.Test_parse_page_all_str("a__TOCA__b", "a__TOCA__b");
	}
	@Test   public void Toc_match_ci() {
		fxt.Test_parse_page_all_str("a__toc__b", "ab");
	}
	@Test   public void Notoc_basic() {
		fxt.Wtr_cfg().Toc__show_(Bool_.Y);	// NOTE: must enable in order for TOC to show (and to make sure NOTOC suppresses) 
		fxt.Test_parse_page_all_str__esc(String_.Concat_lines_nl
		(	"__NOTOC__"
		,	"==a=="
		,	"==b=="
		,	"==c=="
		,	"==d=="
		), String_.Concat_lines_nl
		(	"<h2><span class='mw-headline' id='a'>a</span></h2>"
		,	""
		,	"<h2><span class='mw-headline' id='b'>b</span></h2>"
		,	""
		,	"<h2><span class='mw-headline' id='c'>c</span></h2>"
		,	""
		,	"<h2><span class='mw-headline' id='d'>d</span></h2>"
		));
		fxt.Wtr_cfg().Toc__show_(Bool_.N);
	}
	@Test   public void Ignore_pre() {
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str("a\n   __NOTOC__\n", String_.Concat_lines_nl
		(	"<p>a"
		,	"</p>"		// NOTE: do not capture "   " in front of __NOTOC__; confirmed against MW; DATE:2014-02-19
		,	""
		,	"<p><br/>"
		,	"</p>"
		));
		fxt.Init_para_n_();
	}
	@Test   public void Toc_works() {	// PURPOSE: make sure "suppressed" pre does not somehow suppress TOC
		fxt.Wtr_cfg().Toc__show_(Bool_.Y);
		fxt.Init_para_y_();
		Bry_bfr tmp = Bry_bfr_.New();
		String expd = String_.Concat_lines_nl
		( "<p>a"
		, "</p>"
		, "<div id=\"toc\" class=\"toc\">"
		, "  <div id=\"toctitle\" class=\"toctitle\">"
		, "    <h2>Contents</h2>"
		, "  </div>"
		, "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#b\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">b</span></a>"
		, "    </li>"
		, "  </ul>"
		, "</div>"
		, ""
		, "<h2><span class=\"mw-headline\" id=\"b\">b</span></h2>"
		);
		String actl = gplx.xowa.addons.htmls.tocs.Xowe_hdr_bldr_fxt.Bld_page_with_toc(tmp, fxt, "a\n__TOC__\n==b==\n");
		Tfds.Eq_str_lines(expd, actl);
		fxt.Init_para_n_();
		fxt.Wtr_cfg().Toc__show_(Bool_.N);
	}		
	@Test  public void Ignore_pre_after() {	// PURPOSE: "__TOC__\s\n" must be trimmed at end, else false pre; assertion only (no code exists to handle this test);  DATE:2013-07-08
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl
		(	"a"
		,	"__NOTOC__ "
		,	"b"
		), String_.Concat_lines_nl
		(	"<p>a"
		,	"</p>"		// NOTE: do not capture " "; confirmed against MW; DATE:2014-02-19
		,	""
		,	"<p>b"
		,	"</p>"
		));
		fxt.Init_para_n_();
	}
	@Test  public void Disambig() {	// PURPOSE: ignore "__DISAMBIG__"; EX:{{disambiguation}} DATE:2013-07-24
		fxt.Test_parse_page_all_str("__DISAMBIG__", "");
	}
	@Test  public void Nocontentconvert() {	 // simple test; test for flag only; DATE:2014-02-06
		gplx.xowa.parsers.Xop_ctx_page_data page_data = fxt.Ctx().Page_data();
		Tfds.Eq(page_data.Lang_convert_content(), true);
		Tfds.Eq(page_data.Lang_convert_title(), true);
		fxt.Test_parse_page_all_str("__NOCONTENTCONVERT__ __NOTITLECONVERT__", " ");
		Tfds.Eq(page_data.Lang_convert_content(), false);
		Tfds.Eq(page_data.Lang_convert_title(), false);
	}
	@Test  public void Eos() {	// PURPOSE: check that __ at eos doesn't fail; es.s:Luisa de Bustamante: 3; DATE:2014-02-15
		fxt.Test_parse_page_all_str("__", "__");
	}
	@Test  public void Pre_toc() {				// PURPOSE: make sure that "\n\s__TOC" does not create pre; PAGE:de.w:Main_Page; DATE:2014-04-07
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		(	"a"
		,	" __TOC__ "							// NOTE: this should not be a pre; DATE:2014-07-05
		,	"b"
		), String_.Concat_lines_nl
		(	"<p>a"
		,	"</p>"
		,	" "									// NOTE: \s should not be captured, but leaving for now
		,	""
		,	"<p>b"
		,	"</p>"
		));
		fxt.Init_para_n_();
	}
	@Test  public void Pre_notoc() {			// PURPOSE: make sure that "\n\s__NOTOC" does not create pre. note that mechanism is different from TOC; DATE:2014-07-05
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		(	"a"
		,	" __NOTOC__ "						// NOTE: does not capture " "; confirmed against MW
		,	"b"
		), String_.Concat_lines_nl
		(	"<p>a"
		,	"</p>"
		,	""
		,	"<p>b"
		,	"</p>"
		));
		fxt.Init_para_n_();
	}
	@Test  public void Hook_alt() {	// PURPOSE: ja wikis use alternate __; DATE:2014-03-04
		Xowe_wiki wiki = fxt.Wiki(); Xol_lang_itm lang = wiki.Lang();
		fxt.Init_lang_kwds(lang, Xol_kwd_grp_.Id_toc, true, "＿＿TOC＿＿");
		wiki.Parser_mgr().Main().Init_by_lang(lang);
		fxt.Test_parse_page_all_str("a＿＿TOC＿＿b", "ab");
	}
	@Test  public void Ascii_ci() {	// PURPOSE: case-insensitive ascii; DATE:2014-07-10
		Xowe_wiki wiki = fxt.Wiki(); Xol_lang_itm lang = wiki.Lang();
		fxt.Init_lang_kwds(lang, Xol_kwd_grp_.Id_toc, false, "__TOC__");
		wiki.Parser_mgr().Main().Init_by_lang(lang);
		fxt.Test_parse_page_all_str("a__TOC__b", "ab");
		fxt.Test_parse_page_all_str("a__toc__b", "ab");
	}
	@Test  public void Utf8_ci() {	// PURPOSE: case-insensitive UTF8; DATE:2014-07-10
		Xowe_wiki wiki = fxt.Wiki(); Xol_lang_itm lang = wiki.Lang();
		lang.Case_mgr_u8_();
		fxt.Init_lang_kwds(lang, Xol_kwd_grp_.Id_toc, false, "__AÉI__");
		wiki.Parser_mgr().Main().Init_by_lang(lang);
		fxt.Test_parse_page_all_str("a__AÉI__b", "ab");
		fxt.Test_parse_page_all_str("a__aéi__b", "ab");
	}
	@Test  public void Utf8_ci_asymmetric() {	// PURPOSE: case-insensitive UTF8; asymmetric; DATE:2014-07-10
		Xowe_wiki wiki = fxt.Wiki(); Xol_lang_itm lang = wiki.Lang();
		lang.Case_mgr_u8_();
		fxt.Init_lang_kwds(lang, Xol_kwd_grp_.Id_toc, false, "__İÇİNDEKİLER__");	// __TOC__ for tr.w
		wiki.Parser_mgr().Main().Init_by_lang(lang);
		fxt.Test_parse_page_all_str("a__İçindekiler__b", "ab");
	}
	@Test  public void Cs() {	// PURPOSE: cs (ascii / utf8 doesn't matter); DATE:2014-07-11
		Xowe_wiki wiki = fxt.Wiki(); Xol_lang_itm lang = wiki.Lang();
		fxt.Init_lang_kwds(lang, Xol_kwd_grp_.Id_toc	, Bool_.Y, "__TOC__");
		wiki.Parser_mgr().Main().Init_by_lang(lang);
		fxt.Test_parse_page_all_str("a__TOC__b"		, "ab");			// ci.pass
		fxt.Test_parse_page_all_str("a__toc__b"		, "a__toc__b");		// ci.pass
	}
	@Test  public void Ascii_cs_ci() {	// PURPOSE: test simultaneous cs and ci; DATE:2014-07-11
		Xowe_wiki wiki = fxt.Wiki(); Xol_lang_itm lang = wiki.Lang();
		fxt.Init_lang_kwds(lang, Xol_kwd_grp_.Id_toc	, Bool_.N, "__TOC__");
		fxt.Init_lang_kwds(lang, Xol_kwd_grp_.Id_notoc	, Bool_.Y, "__NOTOC__");
		wiki.Parser_mgr().Main().Init_by_lang(lang);
		fxt.Test_parse_page_all_str("a__TOC__b"		, "ab");			// ci.pass
		fxt.Test_parse_page_all_str("a__toc__b"		, "ab");			// ci.pass
		fxt.Test_parse_page_all_str("a__NOTOC__b"	, "ab");			// cs.pass
		fxt.Test_parse_page_all_str("a__notoc__b"	, "a__notoc__b");	// cs.fail
	}
	@Test  public void Notoc_in_tmpl() {	// PURPOSE: test simultaneous cs and ci; DATE:2017-06-11
		fxt.Init_page_create("Template:Notoc", "page_text\n__NOTOC__");
		fxt.Wiki().Html_mgr().Html_wtr().Cfg().Toc__show_(true);

		fxt.Test__parse_to_html_w_skin(String_.Concat_lines_nl
		( "==A1=="
		, "==A2=="
		, "==A3=="
		, "==A4=="
		, "{{Notoc}}"
		), String_.Concat_lines_nl
		( "<div id=\"toc\" class=\"toc\">"
		, "  <div id=\"toctitle\" class=\"toctitle\">"
		, "    <h2>Contents</h2>"
		, "  </div>"
		, "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#A1\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">A1</span></a>"
		, "    </li>"
		, "    <li class=\"toclevel-1 tocsection-2\"><a href=\"#A2\"><span class=\"tocnumber\">2</span> <span class=\"toctext\">A2</span></a>"
		, "    </li>"
		, "    <li class=\"toclevel-1 tocsection-3\"><a href=\"#A3\"><span class=\"tocnumber\">3</span> <span class=\"toctext\">A3</span></a>"
		, "    </li>"
		, "    <li class=\"toclevel-1 tocsection-4\"><a href=\"#A4\"><span class=\"tocnumber\">4</span> <span class=\"toctext\">A4</span></a>"
		, "    </li>"
		, "  </ul>"
		, "</div>"
		, "<h2><span class=\"mw-headline\" id=\"A1\">A1</span></h2>"
		, ""
		, "<h2><span class=\"mw-headline\" id=\"A2\">A2</span></h2>"
		, ""
		, "<h2><span class=\"mw-headline\" id=\"A3\">A3</span></h2>"
		, ""
		, "<h2><span class=\"mw-headline\" id=\"A4\">A4</span></h2>"
		, "page_text"
		));
	}
}
