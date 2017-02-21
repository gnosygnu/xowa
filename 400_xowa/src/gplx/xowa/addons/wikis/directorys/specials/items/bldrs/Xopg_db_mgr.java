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
package gplx.xowa.addons.wikis.directorys.specials.items.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.directorys.*; import gplx.xowa.addons.wikis.directorys.specials.*; import gplx.xowa.addons.wikis.directorys.specials.items.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*;
import gplx.xowa.wikis.dbs.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;	import gplx.xowa.wikis.data.site_stats.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.addons.wikis.directorys.dbs.*;
import gplx.xowa.addons.wikis.directorys.specials.items.bldrs.*;
import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.dbs.*;
public class Xopg_db_mgr {
	public static int Create
		( Xowd_page_tbl page_tbl, Xowd_text_tbl text_tbl, int text_db_id, Xowd_site_ns_tbl ns_tbl, Db_cfg_tbl cfg_tbl
		, int ns_id, byte[] ttl_page_db, byte[] text_raw, int cat_db_id) {
		// get next page_id			
		int page_id = cfg_tbl.Select_int_or(Xowd_cfg_key_.Grp__db, Xowd_cfg_key_.Key__wiki__page__id_next, 1);

		// check if page_id is unique; needed for existing WMF .xowa dbs which don't set Xowd_cfg_key_.Key__wiki__page__id_next; DATE:2017-02-19
		if (page_tbl.Select_by_id_or_null(page_id) != null) {
			int max_page_id = page_tbl.Conn().Exec_select_max_as_int(page_tbl.Tbl_name(), page_tbl.Fld_page_id(), Xowd_page_tbl.INVALID_PAGE_ID);
			if (max_page_id == Xowd_page_tbl.INVALID_PAGE_ID) {
				throw Err_.new_wo_type("no max found in page_tbl even though page_id was not unique?: db=~{0} page_id=~{1}", page_tbl.Conn().Conn_info().Db_api(), page_id);
			}
			page_id = max_page_id + 1;
		}

		// update it
		cfg_tbl.Upsert_int(Xowd_cfg_key_.Grp__db, Xowd_cfg_key_.Key__wiki__page__id_next, page_id + 1);

		// zip if needed
		byte[] text_zip = text_tbl.Zip(text_raw);

		// TODO.XO: should call redirect mgr
		boolean redirect = Bool_.N;

		// do insert
		page_tbl.Insert_bgn();
		text_tbl.Insert_bgn();
		int ns_count = ns_tbl.Select_ns_count(ns_id) + 1;
		try {
			page_tbl.Insert_cmd_by_batch(page_id, ns_id, ttl_page_db, redirect, Datetime_now.Get(), text_raw.length, ns_count, text_db_id, -1, cat_db_id);
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
		srch_db_mgr.Init(0);	// NOTE: num_pages doesn't matter for updating links
		srch_db_mgr.Update_links(ns_id, old_id, new_id);
		// NOTE: should clear search_results_cache, but for now, update_page_id is only called as a maint proc when wiki is loaded

		// delete from page
		core_db.Tbl__page().Update_page_id(old_id, new_id);
	}
}
