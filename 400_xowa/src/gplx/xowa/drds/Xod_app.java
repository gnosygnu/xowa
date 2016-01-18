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
import gplx.xowa.wikis.nss.*; import gplx.xowa.files.gui.*;
import gplx.xowa.specials.search.*; import gplx.xowa.specials.randoms.*;
public class Xod_app {
	private final Xoav_app app;
	private final Xod_page_mgr page_mgr = new Xod_page_mgr();
	private final Xod_file_mgr file_mgr = new Xod_file_mgr();
	public Xod_app(Xoav_app app) {
		this.app = app;
	}
	public int Wikis__count() {
		int rv = app.Wiki_mgri().Count();
		return rv - 1;	// ignore home wiki
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
	public String[] Wiki__search(Cancelable cancelable, Xow_wiki wiki, Xows_ui_async ui_async, String search) {
		Xows_db_wkr search_wkr = new Xows_db_wkr();
		Xows_db_row[] rows = search_wkr.Search_by_drd(cancelable, wiki, ui_async, Bry_.new_u8(search), 50);
		int len = rows.length;
		String[] rv = new String[len];
		for (int i = 0; i < len; ++i) {
			rv[i] = String_.new_u8(rows[i].Page_ttl().Page_txt());
		}
		return rv;
	}
	public void Page__load_files(Xow_wiki wiki, Xod_page_itm pg, Xog_js_wkr js_wkr) {
		file_mgr.Load_files(wiki, pg, js_wkr);
	}
}
