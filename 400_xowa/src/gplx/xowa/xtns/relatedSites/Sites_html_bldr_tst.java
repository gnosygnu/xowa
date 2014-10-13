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
	private Xoa_app app; private Xow_wiki wiki; private Xoa_page page;
	private Sites_xtn_mgr xtn_mgr;
	public void Clear() {
		this.app = Xoa_app_fxt.app_();
		this.wiki = Xoa_app_fxt.wiki_tst_(app);
		Xop_fxt.Init_msg(wiki, "relatedsites-title", "Related sites");
		this.xtn_mgr = wiki.Xtn_mgr().Xtn_sites();
		xtn_mgr.Enabled_y_();
		xtn_mgr.Xtn_init_by_wiki(wiki);
		wiki.Xwiki_mgr().Add_many(Bry_.new_ascii_(String_.Concat_lines_nl_skip_last
		( "w|en.wikipedia.org/wiki/$1|Wikipedia"
		, "commons|commons.wikimedia.org/wiki/Category:$1|Wikimedia Commons"
		, "dmoz|http://www.dmoz.org/$1|DMOZ"
		)));
		Init_regy_mgr("commons", "dmoz");
		this.page = wiki.Ctx().Cur_page();
	}
	private void Init_regy_mgr(String... ary) {xtn_mgr.Regy_mgr().Set_many(ary);}
	public void Init_ttl(String lnki_ttl) {
		Xoa_ttl ttl = Xoa_ttl.parse_(wiki, Bry_.new_utf8_(lnki_ttl));
		xtn_mgr.Regy_mgr().Match(page, ttl);
	}
	public void Test_bld(String expd) {
		Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
		Sites_xtn_skin_itm skin_itm = (Sites_xtn_skin_itm)page.Html_data().Xtn_skin_mgr().Get_or_null(Sites_xtn_skin_itm.KEY);
		skin_itm.Write(tmp_bfr, page);
		Tfds.Eq_str_lines(expd, tmp_bfr.Xto_str_and_clear());
	}
}
