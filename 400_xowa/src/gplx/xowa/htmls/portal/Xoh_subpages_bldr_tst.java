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
package gplx.xowa.htmls.portal; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import org.junit.*; import gplx.xowa.wikis.nss.*;
public class Xoh_subpages_bldr_tst {
	@Before public void init() {fxt.Init();} private Xoh_subpages_bldr_fxt fxt = new Xoh_subpages_bldr_fxt();
	@Test  public void Basic() {
		fxt.Test_bld("Help:A/B/C", String_.Concat_lines_nl_skip_last
		( "<span class=\"subpages\">"
		, "  &lt; <a href=\"/wiki/Help:A\" title=\"Help:A\">Help:A</a>"
		, "  &lrm; | <a href=\"/wiki/Help:A/B\" title=\"Help:A/B\">B</a>"
		, "</span>"
		));
	}
	@Test  public void Skip_page() {
		fxt.Wiki().Ns_mgr().Add_new(104, "Page");
		fxt.Wiki().Ns_mgr().Ns_page_id_(104);
		fxt.Test_bld("Page:A/B/C", "");
	}
}
class Xoh_subpages_bldr_fxt {
	private Xoae_app app;
	private Xoh_subpages_bldr subpages_bldr = new Xoh_subpages_bldr();
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
	public void Init() {
		this.app = Xoa_app_fxt.app_();
		this.wiki = Xoa_app_fxt.wiki_tst_(app);
		wiki.Ns_mgr().Ids_get_or_null(Xow_ns_.Id_help).Subpages_enabled_(true);
	}
	public void Test_bld(String ttl_str, String expd) {
		byte[] actl = subpages_bldr.Bld(wiki.Ns_mgr(), Xoa_ttl.parse(wiki, Bry_.new_u8(ttl_str)));
		Tfds.Eq_str_lines(expd, String_.new_u8(actl));
	}
}
