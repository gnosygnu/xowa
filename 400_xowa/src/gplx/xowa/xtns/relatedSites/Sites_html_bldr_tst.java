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
package gplx.xowa.xtns.relatedSites; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Sites_html_bldr_tst {
	@Before public void init() {fxt.Clear();} private Sites_html_bldr_fxt fxt = new Sites_html_bldr_fxt();
	@Test  public void Basic() {
		fxt.Init_ttl("commons:A");
		fxt.Init_ttl("dmoz:A");
		fxt.Init_ttl("w:A");		// not in sites_regy_mgr; ignore
		fxt.Init_ttl("commons:A");	// test dupe doesn't show up
		fxt.Test_bld(String_.Concat_lines_nl_skip_last
		( "<div id='p-relatedsites' class='portal'>"
		, "  <h3>Related sites</h3>"
		, "  <div class='body'>"
		, "    <ul>"
		, "      <li class='interwiki-commons'><a class='xowa-hover-off' href='/site/commons.wikimedia.org/wiki/Category:A'>Wikimedia Commons</a></li>"
		, "      <li class='interwiki-dmoz'><a class='xowa-hover-off' href='http://www.dmoz.org/A'>DMOZ</a></li>"
		, "    </ul>"
		, "  </div>"
		, "</div>"
		));
	}
}
class Sites_html_bldr_fxt {
	private Xoae_app app; private Xowe_wiki wiki; private Xoae_page page;
	private Sites_xtn_mgr xtn_mgr;
	public void Clear() {
		this.app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
		Xop_fxt.Init_msg(wiki, "relatedsites-title", "Related sites");
		this.xtn_mgr = wiki.Xtn_mgr().Xtn_sites();
		xtn_mgr.Enabled_y_();
		xtn_mgr.Xtn_init_by_wiki(wiki);
		wiki.Xwiki_mgr().Add_by_csv(Bry_.new_a7(String_.Concat_lines_nl_skip_last
		( "0|w|en.wikipedia.org/wiki/~{0}|Wikipedia"
		, "0|commons|commons.wikimedia.org/wiki/Category:~{0}|Wikimedia Commons"
		, "0|dmoz|http://www.dmoz.org/~{0}|DMOZ"
		)));
		Init_regy_mgr("commons", "dmoz");
		this.page = wiki.Parser_mgr().Ctx().Page();
	}
	private void Init_regy_mgr(String... ary) {xtn_mgr.Regy_mgr().Set_many(ary);}
	public void Init_ttl(String lnki_ttl) {
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, Bry_.new_u8(lnki_ttl));
		xtn_mgr.Regy_mgr().Match(page, ttl);
	}
	public void Test_bld(String expd) {
		Bry_bfr tmp_bfr = Bry_bfr_.Reset(255);
		Sites_xtn_skin_itm skin_itm = (Sites_xtn_skin_itm)page.Html_data().Xtn_skin_mgr().Get_or_null(Sites_xtn_skin_itm.KEY);
		skin_itm.Write(tmp_bfr, page);
		Tfds.Eq_str_lines(expd, tmp_bfr.To_str_and_clear());
	}
}
