/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.htmls;

import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.xowa.Xoa_app_fxt;
import gplx.xowa.Xoa_page_;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx;
import gplx.xowa.wikis.pages.Xopg_view_mode_;
import org.junit.Before;
import org.junit.Test;

public class Xoh_page_wtr_wkr_tst {
	@Before public void init() {fxt.Clear();} private Xoh_page_wtr_fxt fxt = new Xoh_page_wtr_fxt();
	@Test public void BuildPagenameForTab() {
		fxt.Wiki().Msg_mgr().Get_or_make(BryUtl.NewA7("pagetitle-view-mainpage")).Atrs_set(BryUtl.NewA7("{{SITENAME}} - WikiDescription"), false, true);
		fxt.Wiki().Msg_mgr().Get_or_make(BryUtl.NewA7("pagetitle")).Atrs_set(BryUtl.NewA7("~{0} - {{SITENAME}}"), true, true);

		fxt.Test_BuildPagenameForTab("basic"            , "Earth"                              , BoolUtl.N,"Earth");
		fxt.Test_BuildPagenameForTab("ns"               , "File:A.png"                         , BoolUtl.N,"File:A.png");
		fxt.Test_BuildPagenameForTab("special"          , "Special:Search/earth"               , BoolUtl.N,"Special:Search/earth");
		fxt.Test_BuildPagenameForTab("special:no qargs" , "Special:Search/earth"               , BoolUtl.N,"Special:Search/earth?fulltext=y");
		fxt.Test_BuildPagenameForTab("http.page"        , "Earth - Wikipedia"                  , BoolUtl.Y,"Earth");
		fxt.Test_BuildPagenameForTab("http.mainpage"    , "Wikipedia - WikiDescription"        , BoolUtl.Y,"Main_Page");
	}
	@Test public void BuildPagenameForH1() {
		fxt.Test_BuildPagenameForH1("Full_txt"         , "Two words"           , "Two_words", null);
		fxt.Test_BuildPagenameForH1("no qargs"         , "Special:Search/earth", "Special:Search/earth?fulltext=y", null);
		fxt.Test_BuildPagenameForH1("display overrides", "Display"             , "Title", "Display");
	}
	@Test public void Edit() {
		fxt.Test_edit("&#9;", "&amp;#9;\n");	// NOTE: cannot by &#9; or will show up in edit box as "\t" and save as "\t" instead of &#9;
	}
	@Test public void Css() {
		fxt.App().Html_mgr().Page_mgr().Content_code_fmt().Fmt_("<pre style='overflow:auto'>~{page_text}</pre>");
		fxt.Test_read("MediaWiki:Common.css", ".xowa {}", "<pre style='overflow:auto'>.xowa {}</pre>");
		fxt.App().Html_mgr().Page_mgr().Content_code_fmt().Fmt_("<pre>~{page_text}</pre>");
	}
	@Test public void Amp_disable() {	// PURPOSE: in js documents; &quot; should be rendered as &quot;, not as "; DATE:2013-11-07
		fxt.Test_read("MediaWiki:Gadget.js", "&quot;", "<pre>&amp;quot;</pre>");
	}
}
class Xoh_page_wtr_fxt {
	private BryWtr tmp_bfr = BryWtr.NewAndReset(255);
	public void Clear() {
		if (app == null) {
			app = Xoa_app_fxt.Make__app__edit();
			wiki = Xoa_app_fxt.Make__wiki__edit(app);
		}
	}
	public Xoae_app App() {return app;} private Xoae_app app;
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
	public void Test_BuildPagenameForH1(String note, String expd, String ttl, String display) {
		Xoa_ttl page_ttl = Xoa_ttl.Parse(wiki, BryUtl.NewA7(ttl));
		GfoTstr.Eq(expd, Xoh_page_wtr_wkr_.BuildPagenameForH1(tmp_bfr, page_ttl, BryUtl.NewA7(display)), note);
	}
	public void Test_BuildPagenameForTab(String note, String expd, boolean isHttpServer, String raw) {
		Xoa_ttl page_ttl = Xoa_ttl.Parse(wiki, BryUtl.NewA7(raw));
		byte[] mainpage_title = Xoa_page_.Main_page_bry;

		GfoTstr.Eq(expd, Xoh_page_wtr_wkr_.BuildPagenameForTab(tmp_bfr, isHttpServer, wiki.Msg_mgr(), page_ttl, mainpage_title));
	}
	public void Test_edit(String raw, String expd) {
		wiki.Html_mgr().Page_wtr_mgr().Html_capable_(true);
		Xoae_page page = wiki.Parser_mgr().Ctx().Page();
		page.Db().Text().Text_bry_(BryUtl.NewU8(raw));
		Xoh_page_wtr_mgr mgr = wiki.Html_mgr().Page_wtr_mgr();
		Xoh_page_wtr_wkr wkr = mgr.Wkr(Xopg_view_mode_.Tid__edit);
		wkr.Write_body(tmp_bfr, wiki.Parser_mgr().Ctx(), Xoh_wtr_ctx.Basic, page);
		GfoTstr.EqObj(expd, tmp_bfr.ToStrAndClear());
	}
	public void Test_read(String page_name, String page_text, String expd) {
		wiki.Html_mgr().Page_wtr_mgr().Html_capable_(true);
		Xoae_page page = wiki.Parser_mgr().Ctx().Page();
		page.Ttl_(Xoa_ttl.Parse(wiki, BryUtl.NewA7(page_name)));
		page.Db().Text().Text_bry_(BryUtl.NewU8(page_text));
		Xoh_page_wtr_mgr mgr = wiki.Html_mgr().Page_wtr_mgr();
		Xoh_page_wtr_wkr wkr = mgr.Wkr(Xopg_view_mode_.Tid__read);
		wkr.Write_body(tmp_bfr, wiki.Parser_mgr().Ctx(), Xoh_wtr_ctx.Basic, page);
		GfoTstr.EqObj(expd, tmp_bfr.ToStrAndClear());
	}
}
