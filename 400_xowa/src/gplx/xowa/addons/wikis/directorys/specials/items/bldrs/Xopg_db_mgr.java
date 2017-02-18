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
package gplx.xowa.addons.wikis.directorys.specials.items.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.directorys.*; import gplx.xowa.addons.wikis.directorys.specials.*; import gplx.xowa.addons.wikis.directorys.specials.items.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*;
import gplx.xowa.wikis.dbs.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;	import gplx.xowa.wikis.data.site_stats.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.addons.wikis.directorys.dbs.*;
import gplx.xowa.addons.wikis.directorys.specials.items.bldrs.*;
import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.dbs.*;
public class Xopg_db_mgr {
	public static int Create
		( Xowd_page_tbl page_tbl, Xowd_text_tbl text_tbl, Xowd_site_ns_tbl ns_tbl, Db_cfg_tbl cfg_tbl
		, int ns_id, byte[] ttl_page_db, byte[] text_raw, int cat_db_id) {
		// get next page_id
		int page_id = cfg_tbl.Select_int_or("db", "page.id_next", 1);
		cfg_tbl.Upsert_int("db", "page.id_next", page_id + 1);

		// zip if needed
		byte[] text_zip = text_tbl.Zip(text_raw);

		// TODO.XO: should call redirect mgr
		boolean redirect = Bool_.N;

		// do insert
		page_tbl.Insert_bgn();
		text_tbl.Insert_bgn();
		int ns_count = ns_tbl.Select_ns_count(ns_id) + 1;
		try {
			page_tbl.Insert_cmd_by_batch(page_id, ns_id, ttl_page_db, redirect, Datetime_now.Get(), text_raw.length, ns_count, 0, -1, cat_db_id);
			text_tbl.Insert_cmd_by_batch(page_id, text_zip);
			ns_tbl.Update_ns_count(ns_id, ns_count);
		} finally {
			page_tbl.Insert_end();
			text_tbl.Insert_end();
		}
		return page_id;
	}
	public static void Delete(Xowe_wiki wiki, Xoa_ttl page_ttl) {
		// init vars
		Xow_db_mgr db_mgr = wiki.Data__core_mgr();
		Xow_db_file core_db = db_mgr.Db__core();
		Xowd_page_itm tmp = new Xowd_page_itm();

		// get meta
		core_db.Tbl__page().Select_by_ttl(tmp, page_ttl);
		int page_id = tmp.Id();
		int ns_id = tmp.Ns_id();

		// adjust site_stats
		int page_is_file = ns_id == Xow_ns_.Tid__file ? 1 : 0;
		Xowd_site_stats_mgr site_stats_row = new Xowd_site_stats_mgr(wiki);
		core_db.Tbl__site_stats().Select(site_stats_row);
		core_db.Tbl__site_stats().Update(site_stats_row.Num_articles() - 1, site_stats_row.Num_pages() - 1, site_stats_row.Num_files() - page_is_file);
		
		// adjust site_ns
		int ns_count = core_db.Tbl__ns().Select_ns_count(ns_id);
		core_db.Tbl__ns().Update_ns_count(ns_id, ns_count - 1);

		// text_db
		Xow_db_file text_db = db_mgr.Dbs__get_by_id_or_null(tmp.Text_db_id());
		if (text_db != null) {
			text_db.Tbl__text().Delete(page_id);
		}

		// html_db
		Xow_db_file html_db = db_mgr.Dbs__get_by_id_or_null(tmp.Html_db_id());
		if (html_db != null) {
			html_db.Tbl__html().Delete(page_id);
		}

		// cat_core, cat_link
		gplx.xowa.addons.wikis.ctgs.edits.Xoctg_edit_mgr.Delete(wiki, ns_id, page_id);

		// search_link
		Srch_search_addon.Get(wiki).Delete_links(ns_id, page_id);

		// delete from page
		core_db.Tbl__page().Delete(page_id);
	}
	public static void Update_page_id(Xow_db_mgr db_mgr, int old_id, int new_id) {
		// init vars
		Xow_db_file core_db = db_mgr.Db__core();
		Xowd_page_itm tmp = new Xowd_page_itm();

		// get ns_id
		core_db.Tbl__page().Select_by_id(tmp, old_id);
		int ns_id = tmp.Ns_id();

		// text_db
		Xow_db_file text_db = db_mgr.Dbs__get_by_id_or_null(tmp.Text_db_id());
		if (text_db != null) {
			text_db.Tbl__text().Update_page_id(old_id, new_id);
		}

		// html_db
		Xow_db_file html_db = db_mgr.Dbs__get_by_id_or_null(tmp.Html_db_id());
		if (html_db != null) {
			html_db.Tbl__html().Update_page_id(old_id, new_id);
		}

		// cat_core, cat_link
		gplx.xowa.addons.wikis.ctgs.edits.Xoctg_edit_mgr.Update_page_id(db_mgr, ns_id, old_id, new_id);

		// search_link
		gplx.xowa.addons.wikis.searchs.dbs.Srch_db_mgr srch_db_mgr = new gplx.xowa.addons.wikis.searchs.dbs.Srch_db_mgr(db_mgr);
		srch_db_mgr.Update_links(ns_id, old_id, new_id);
		// NOTE: should clear search_results_cache, but for now, update_page_id is only called as a maint proc when wiki is loaded

		// delete from page
		core_db.Tbl__page().Update_page_id(old_id, new_id);
	}
}
