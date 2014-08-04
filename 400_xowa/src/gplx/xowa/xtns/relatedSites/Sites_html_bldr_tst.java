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
		fxt.Init_ttl("w:A");
		fxt.Test_bld(String_.Concat_lines_nl_skip_last
		( "<div id=\"p-relatedsites\" class=\"portal\">"
		, "  <h3>Related articles</h3>"
		, "  <div class=\"body\">"
		, "    <ul>"
		, "      <li class=\"interwiki-commons\"><a href=\"commons.wikimedia.org/wiki/Category:A\">Wikimedia Commons</a></li>"
		, "      <li class=\"interwiki-w\"><a href=\"en.wikipedia.org/wiki/A\">Wikipedia</a></li>"
		, "    </ul>"
		, "  </div>"
		, "</div>"
		));
	}
}
class Sites_html_bldr_fxt {
	private Xoa_app app; private Xow_wiki wiki;
	private ListAdp list = ListAdp_.new_();
	private Sites_xtn_mgr xtn_mgr;
	public void Clear() {
		this.app = Xoa_app_fxt.app_();
		this.wiki = Xoa_app_fxt.wiki_tst_(app);
		wiki.Xwiki_mgr().Add_many(Bry_.new_ascii_("w|en.wikipedia.org/wiki/$1|Wikipedia\ncommons|commons.wikimedia.org/wiki/Category:$1|Wikimedia Commons"));
		wiki.Lang().Msg_mgr().Itm_by_key_or_new("relatedarticles-title", "Related articles");
		this.xtn_mgr = wiki.Xtn_mgr().Xtn_sites();
		xtn_mgr.Enabled_y_();
		xtn_mgr.Xtn_init_by_wiki(wiki);
		list.Clear();
	}
	public void Init_ttl(String lnki_ttl) {
		Xoa_ttl ttl = Xoa_ttl.parse_(wiki, Bry_.new_utf8_(lnki_ttl));
		xtn_mgr.Match(ttl, list);
	}
	public void Test_bld(String expd) {
		byte[] actl = xtn_mgr.Html_bldr().Bld_all(list);
		Tfds.Eq_str_lines(expd, String_.new_utf8_(actl));
	}
}
