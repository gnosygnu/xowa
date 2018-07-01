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
package gplx.xowa.addons.wikis.pages.randoms.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.pages.*; import gplx.xowa.addons.wikis.pages.randoms.*;
import org.junit.*; import gplx.xowa.specials.*;
public class Rndm_root_special_tst {
	@Before public void init() {fxt.Clear();} private Rndm_root_special_fxt fxt = new Rndm_root_special_fxt();
	@Test   public void Ns_main() {
		fxt.Init_create_page("A");
		fxt.Init_create_page("A/B/C");
		fxt.Test_open("Special:RandomRootPage/Main", "A");	// NOTE: result will always be "A"; "A" -> "A"; "A/B/C" -> "A"
	}
	@Test   public void Ns_help() {
		fxt.Init_create_page("Help:A");
		fxt.Init_create_page("Help:A/B/C");
		fxt.Test_open("Special:RandomRootPage/Help", "Help:A");
	}
}
class Rndm_root_special_fxt {
	private Xop_fxt parser_fxt; private Rndm_root_special special_page; private Xowe_wiki wiki;
	public void Clear() {
		parser_fxt = new Xop_fxt();
		parser_fxt.Reset();
		wiki = parser_fxt.Wiki();
		special_page = new gplx.xowa.addons.wikis.pages.randoms.specials.Rndm_root_special();
	}
	public void Init_create_page(String page) {parser_fxt.Init_page_create(page, page);}
	public void Test_open(String special_url, String expd) {
		Xoae_page page = Test_special_open(wiki, special_page, special_url);
		Tfds.Eq(expd, String_.new_a7(page.Url().Page_bry()));
		Tfds.Eq(expd, String_.new_a7(page.Db().Text().Text_bry()));
	}
	public static Xoae_page Test_special_open(Xowe_wiki wiki, Xow_special_page special_page, String special_url) {
		Xoae_page page = wiki.Parser_mgr().Ctx().Page();
		Xoa_url url = wiki.Utl__url_parser().Parse(Bry_.new_u8(special_url));
		page.Url_(url);
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, Bry_.new_a7(special_url));
		page.Ttl_(ttl);
		special_page.Special__gen(wiki, page, url, ttl);
		return page;
	}
}
