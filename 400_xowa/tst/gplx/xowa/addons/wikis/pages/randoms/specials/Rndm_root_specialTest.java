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
package gplx.xowa.addons.wikis.pages.randoms.specials;

import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.dbs.wkrs.randoms.TestRandomWkr;
import gplx.xowa.Xoa_app_fxt;
import gplx.xowa.Xoa_test_;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xop_fxt;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.specials.Xow_special_page;
import gplx.xowa.wikis.nss.Xow_ns_;
import org.junit.Test;

public class Rndm_root_specialTest {
	private final RandomRootTstr tstr = new RandomRootTstr();
	@Test public void NsMain() {
		tstr.InitCreatePage("A", "A/B/C");
		tstr.Test("Special:RandomRootPage/Main", Xow_ns_.Tid__main, "A");	// NOTE: will always be rootPage; EX: "A" -> "A"; "A/B/C" -> "A"
	}
	@Test public void NsHelp() {
		tstr.InitCreatePage("Help:A", "Help:A/B/C");
		tstr.Test("Special:RandomRootPage/Help", Xow_ns_.Tid__help, "Help:A");
	}
}
class RandomRootTstr {
	private Xowe_wiki wiki;
	private Xop_fxt parserTstr;
	private TestRandomWkr testRandomWkr;
	public RandomRootTstr() {
		// init db-aware wiki
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
		Xoa_test_.Init__db__edit(wiki);
		wiki.Data__core_mgr().Db__text().Tbl__text().Create_tbl(); // NOTE: need to call text.Create_tbl b/c Init__db__edit does not create it

		// init parserTstr
		this.parserTstr = new Xop_fxt(app, wiki);

		// init testRandomWkr
		this.testRandomWkr = TestRandomWkr.New(wiki.Data__core_mgr().Db__core().Conn());
	}
	public void InitCreatePage(String... ary) {
		for (String page : ary) {
			parserTstr.Init_page_create(page, page);

			// add ttl.Root to the testRandomWkr
			Xoa_ttl pageTtl = wiki.Ttl_parse(BryUtl.NewU8(page));
			testRandomWkr.AddRow(StringUtl.NewU8(pageTtl.Root_txt()));
		}
	}
	public void Test(String special_url, int expd_ns, String expd) {
		// call Special:RandomRoot
		Rndm_root_special special_page = new gplx.xowa.addons.wikis.pages.randoms.specials.Rndm_root_special();
		Xoae_page page = Test_special_open(wiki, special_page, special_url);

		// test sql
		GfoTstr.Eq("page_title", testRandomWkr.SelectRandomRowSelect());
		GfoTstr.Eq("page p", testRandomWkr.SelectRandomRowFrom());
		GfoTstr.Eq("p.page_namespace = " + expd_ns + " AND p.page_redirect_id = -1 AND p.page_title NOT LIKE '%/%'", testRandomWkr.SelectRandomRowWhere());

		// test page
		GfoTstr.Eq(expd, page.Url().Page_bry());
		GfoTstr.Eq("", page.Db().Text().Text_bry()); // ISSUE#:719:redirect should not load page else redirect info will get lost; EX:"Redirected from trg_ttl" instead of "Redirected from src_ttl"; PAGE:en.s; DATE:2020-05-13
	}
	public static Xoae_page Test_special_open(Xowe_wiki wiki, Xow_special_page special_page, String special_url) {
		Xoae_page page = Init_page(wiki, special_url);
		special_page.Special__gen(wiki, page, page.Url(), page.Ttl());
		return page;
	}
	private static Xoae_page Init_page(Xowe_wiki wiki, String url_str) {
		// basic boot-strapping to make sure ctx.Page has .Url and .Ttl
		byte[] url_bry = BryUtl.NewU8(url_str);
		Xoae_page page = wiki.Parser_mgr().Ctx().Page();
		page.Url_(wiki.Utl__url_parser().Parse(url_bry));
		page.Ttl_(Xoa_ttl.Parse(wiki, url_bry));
		return page;
	}
}
