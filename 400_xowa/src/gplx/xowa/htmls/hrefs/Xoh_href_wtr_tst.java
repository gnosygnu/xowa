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
package gplx.xowa.htmls.hrefs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import org.junit.*;
import gplx.core.net.*; import gplx.xowa.wikis.nss.*;
public class Xoh_href_wtr_tst {		
	private final    Xoh_href_wtr_fxt fxt = new Xoh_href_wtr_fxt();
	@Test   public void Xwiki_enc()					{fxt.Test_build("wikt:abc?d"				, "/site/en.wiktionary.org/wiki/abc%3Fd");}	
	@Test   public void Page_quote()				{fxt.Test_build("a\"b\"c"					, "/wiki/A%22b%22c");}
	@Test   public void Page()						{fxt.Test_build("abc"						, "/wiki/Abc");}
	@Test   public void Page_ns()					{fxt.Test_build("Image:A.png"				, "/wiki/Image:A.png");}
	@Test   public void Anchor()					{fxt.Test_build("#abc"						, "#abc");}
	@Test   public void Page_anchor()				{fxt.Test_build("Abc#def"					, "/wiki/Abc#def");}
	@Test   public void Xwiki()						{fxt.Test_build("wikt:abc"					, "/site/en.wiktionary.org/wiki/abc");}	// NOTE: "abc" not capitalized, b/c other wiki's case sensitivity is not known; this emulates WP's behavior
	@Test   public void Xwiki_2()					{fxt.Test_build("wikt:Special:Search/a"		, "/site/en.wiktionary.org/wiki/Special:Search/a");}
	@Test   public void Category() 					{fxt.Test_build("Category:abc"				, "/wiki/Category:Abc");}
	@Test   public void Xwiki_wikimedia_mail() {	// PURPOSE: DATE:2015-04-22
		fxt.Prep_xwiki_by_many("0|mail|https://lists.wikimedia.org/mailman/listinfo/~{0}|Wikitech Mailing List");
		fxt.Test_build("mail:A"				, "https://lists.wikimedia.org/mailman/listinfo/A");
	}
}
class Xoh_href_wtr_fxt {
	private final    Xowe_wiki wiki;
	private final    Bry_bfr tmp_bfr = Bry_bfr_.Reset(255);
	private final    Xoh_href_wtr href_wtr = new Xoh_href_wtr();
	public Xoh_href_wtr_fxt() {
		this.app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
		wiki.Xwiki_mgr().Add_by_csv(Bry_.new_a7("1|wikt|en.wiktionary.org"));
		app.Usere().Wiki().Xwiki_mgr().Add_by_csv(Bry_.new_a7("1|en.wiktionary.org|en.wiktionary.org"));
	}
	public Xoae_app App() {return app;} private final    Xoae_app app;
	public Xoh_href_wtr_fxt Prep_wiki_cs(String domain) {
		Xow_wiki wiki = app.Wiki_mgr().Get_by_or_make_init_n(Bry_.new_u8(domain));
		wiki.Ns_mgr().Ns_main().Case_match_(Xow_ns_case_.Tid__all);
		return this;
	}
	public Xoh_href_wtr_fxt Prep_xwiki_by_many(String raw) {wiki.Xwiki_mgr().Add_by_csv(Bry_.new_u8(raw)); return this;} // need to add to wiki's xwiki_mgr for ttl_parse
	public void Test_build(String raw, String expd) {
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, Bry_.new_u8(raw));
		href_wtr.Build_to_bfr(tmp_bfr, app, wiki.Domain_bry(), ttl);
		Tfds.Eq(expd, tmp_bfr.To_str_and_clear());
	}
}
