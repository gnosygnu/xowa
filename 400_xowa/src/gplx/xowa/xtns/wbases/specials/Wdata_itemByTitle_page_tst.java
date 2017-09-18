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
package gplx.xowa.xtns.wbases.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import org.junit.*; import gplx.xowa.wikis.domains.*;
public class Wdata_itemByTitle_page_tst {
	@Before public void init() {fxt.Clear();} private Wdata_itemByTitle_page_fxt fxt = new Wdata_itemByTitle_page_fxt();
	@Test   public void Url() {
		fxt.Init_wdata_page("Q2", "q2_earth");
		fxt.Init_wdata_label("enwiki", "Earth", "Q2");
		fxt.Test_open("Special:ItemByTitle/enwiki/Earth", "q2_earth");
	}
	@Test   public void Missing() {
		fxt.Test_open("Special:ItemByTitle/enwiki/unknown_page", fxt.Expd_html("enwiki", "unknown page"));
	}
	@Test   public void Html() {
		fxt.Test_open("Special:ItemByTitle", fxt.Expd_html("enwiki", ""));
	}
	@Test   public void Args() {
		fxt.Init_wdata_page("Q2", "q2_earth");
		fxt.Init_wdata_label("enwiki", "Earth", "Q2");
		fxt.Test_open("Special:ItemByTitle?site=enwiki&page=Earth", "q2_earth");
	}
	@Test   public void Url_decode() {	// PURPOSE: spaces not handled; EX: Albert Einstein -> Albert+Einstein; DATE:2013-07-24
		fxt.Init_wdata_page("Q2", "q2_page");
		fxt.Init_wdata_label("enwiki", "A_B", "Q2");
		fxt.Test_open("Special:ItemByTitle?site=enwiki&page=A+B", "q2_page");
	}
}
class Wdata_itemByTitle_page_fxt {
	public void Clear() {
		parser_fxt = new Xop_fxt();
		parser_fxt.Reset();
		app = parser_fxt.App();
		wiki = parser_fxt.Wiki();
		special_page = wiki.Special_mgr().Page_itemByTitle();
		wdata_fxt = new Wdata_wiki_mgr_fxt().Init(parser_fxt, true);
	}	private Xop_fxt parser_fxt; private Xoae_app app; private Wdata_itemByTitle_page special_page; private Xowe_wiki wiki;
	Wdata_wiki_mgr_fxt wdata_fxt;
	public void Init_wdata_page(String qid_ttl, String text) {
		Wdata_doc doc = wdata_fxt.Wdoc_bldr(qid_ttl).Xto_wdoc();
		app.Wiki_mgr().Wdata_mgr().Doc_mgr.Add(Bry_.new_a7(qid_ttl), doc);
		parser_fxt.Init_page_create(app.Wiki_mgr().Wdata_mgr().Wdata_wiki(), qid_ttl, text);
	}
	public void Init_wdata_label(String wmf_key_str, String wdata_label, String qid) {
		wdata_fxt.Init_qids_add("en", Xow_domain_tid_.Tid__wikipedia, wdata_label, qid);		
	}
	public void Test_open(String link, String expd) {
		Xoae_page page = wiki.Parser_mgr().Ctx().Page();
		Xoa_url url = app.User().Wikii().Utl__url_parser().Parse(Bry_.new_u8(link));
		page.Url_(url);
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, Bry_.new_a7(link));
		page.Ttl_(ttl);
		special_page.Special__gen(wiki, page, url, ttl);
		Tfds.Eq_str_lines(expd, String_.new_a7(page.Db().Text().Text_bry()));
	}
	public String Expd_html(String wmf_key, String ttl_str) {
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_k004();
		special_page.Html_fmtr().Bld_bfr_many(tmp_bfr, "Search for items by site and title", "Site", wmf_key, "Page", ttl_str, "Search");
		return tmp_bfr.To_str_and_rls();
	}
}
