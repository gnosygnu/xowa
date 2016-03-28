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
package gplx.xowa.drds; import gplx.*; import gplx.xowa.*;
import gplx.xowa.drds.pages.*; import gplx.xowa.drds.files.*;
import gplx.xowa.apps.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.nss.*; import gplx.xowa.files.gui.*;
import gplx.xowa.addons.searchs.searchers.rslts.*; import gplx.xowa.specials.randoms.*;
import gplx.langs.htmls.encoders.*; import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.addons.searchs.*; import gplx.xowa.addons.searchs.searchers.*;
import gplx.xowa.langs.cases.*;
public class Xod_app {
	private final    Xoav_app app;
	private final    Xod_page_mgr page_mgr = new Xod_page_mgr();
	private final    Xod_file_mgr file_mgr = new Xod_file_mgr();
	private final    Srch_ns_mgr ns_mgr = new Srch_ns_mgr();
	public Xod_app(Xoav_app app) {
		this.app = app;
		ns_mgr.Add_main_if_empty();
	}
	public Xow_wiki Wikis__get_by_domain(String wiki_domain) {
		Xow_wiki rv = app.Wiki_mgri().Get_by_or_make_init_y(Bry_.new_u8(wiki_domain));
		if (rv != null && rv.Data__core_mgr() == null) rv.Init_by_wiki();
		return rv;
	}
	public Xod_page_itm Wiki__get_by_url(Xow_wiki wiki, Xoa_url page_url) {
		return page_mgr.Get_page(wiki, page_url);
	}
	public Xod_page_itm Wiki__get_random(Xow_wiki wiki, Xow_ns ns) {
		byte[] random_ttl_bry = wiki.Data__core_mgr().Tbl__page().Select_random(ns);
		Xoa_url url = wiki.Utl__url_parser().Parse(random_ttl_bry);
		return Wiki__get_by_url(wiki, url);
	}
	public void Wiki__search(Cancelable cxl, Srch_rslt_cbk cbk, Xow_wiki wiki, String search, int bgn, int end) {
		Srch_search_addon addon = Get_addon(wiki);
		Srch_search_qry qry = Srch_search_qry.New__drd(wiki, ns_mgr, Bry_.new_u8(search), bgn, end);
		addon.Search(qry, cbk);
	}
	public void Page__load_files(Xow_wiki wiki, Xod_page_itm pg, Xog_js_wkr js_wkr) {
		file_mgr.Load_files(wiki, pg, js_wkr);
		app.User().User_db_mgr().Cache_mgr().Db_save();
	}
	public static byte[] To_page_url(Xow_wiki wiki, String canonical_str) {// NOTE: need canonical_url to handle "A:B" where "A:" is not a ns, even though PageTitle treats "A:" as a namespace
		byte[] canonical_bry = Bry_.new_u8(canonical_str);
		int page_bgn = Bry_find_.Move_fwd(canonical_bry, Xoh_href_.Bry__wiki, 0); if (page_bgn == Bry_find_.Not_found) throw Err_.new_("drd", "uknown url format: no '/wiki/'", "url", canonical_bry);
		byte[] page_bry = Bry_.Mid(canonical_bry, page_bgn, canonical_bry.length);	// get bry; EX: https://en.wikipedia.org/wiki/A -> A
		page_bry = Gfo_url_encoder_.Http_url.Decode(page_bry);						// decode %-encoding; convert + to space
		page_bry = Xoa_ttl.Replace_spaces(page_bry);								// convert spaces to unders; canonical-url has spaces
		return page_bry;
	}
	private Srch_search_addon Get_addon(Xow_wiki wiki) {return Srch_search_addon.Get(wiki);}
}
