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
package gplx.xowa.dbs; import gplx.*; import gplx.xowa.*;
import gplx.core.primitives.*; import gplx.dbs.*;
import gplx.xowa.apps.*; import gplx.xowa.bldrs.imports.ctgs.*; import gplx.xowa.ctgs.*; import gplx.xowa.specials.search.*; import gplx.xowa.dbs.tbls.*;
public class Xodb_load_mgr_sql implements Xodb_load_mgr {
	public Xodb_load_mgr_sql(Xodb_mgr_sql db_mgr, Xodb_fsys_mgr fsys_mgr) {this.db_mgr = db_mgr; this.fsys_mgr = fsys_mgr;} private Xodb_mgr_sql db_mgr; Xodb_fsys_mgr fsys_mgr;
	public byte Search_version() {
		if (search_provider == null) Search_version_init();
		return search_version;
	}	private byte search_version = gplx.xowa.specials.search.Xosrh_core.Version_null;
	public void Search_version_refresh() {
		search_provider = null;
		Search_version_init();
	}
	public void Load_init(Xowe_wiki wiki) {
		Load_init_cfg(wiki);
		db_mgr.Tbl_site_stats().Select(wiki);
		db_mgr.Tbl_xowa_ns().Select_all(wiki.Ns_mgr());
	}
	private void Load_init_cfg(Xowe_wiki wiki) {
		String_obj_ref version_val = String_obj_ref.null_();
		String version_key = Xoa_gfs_mgr.Build_code(Xowe_wiki.Invk_props, Xow_wiki_props.Invk_bldr_version);
		KeyVal[] kv_ary = db_mgr.Tbl_xowa_cfg().Select_kvs(Xodb_mgr_sql.Grp_wiki_init, version_key, version_val);
		Xodb_upgrade_mgr.Upgrade(db_mgr, kv_ary, version_key, version_val.Val());
		Bry_bfr bfr = wiki.Utl_bry_bfr_mkr().Get_k004();
		Xoa_gfs_mgr gfs_mgr = wiki.Appe().Gfs_mgr();
		try {
			int len = kv_ary.length;
			for (int i = 0; i < len; i++) {
				KeyVal kv = kv_ary[i];
				gfs_mgr.Build_prop_set(bfr, Bry_.new_utf8_(kv.Key()), Bry_.new_utf8_(kv.Val_to_str_or_empty()));
			}
			gfs_mgr.Run_str_for(wiki, bfr.Xto_str_and_clear());
		}	finally {bfr.Mkr_rls();}
	}
	public boolean Load_by_ttl(Xodb_page rv, Xow_ns ns, byte[] ttl) {return db_mgr.Tbl_page().Select_by_ttl(rv, ns, ttl);}
	public void Load_by_ttls(Cancelable cancelable, OrderedHash rv, boolean fill_idx_fields_only, int bgn, int end) {db_mgr.Tbl_page().Select_by_ttl_in(cancelable, rv, db_mgr.Db_ctx(), fill_idx_fields_only, bgn, end);}
	public void Load_page(Xodb_page rv, Xow_ns ns, boolean timestamp_enabled) {rv.Text_(db_mgr.Tbl_text().Select(rv.Text_db_id(), rv.Id()));}
	public boolean Load_by_id	(Xodb_page rv, int id) {return db_mgr.Tbl_page().Select_by_id(rv, id);}
	public void Load_by_ids(Cancelable cancelable, ListAdp rv, int bgn, int end) {db_mgr.Tbl_page().Select_by_id_list(cancelable, false, rv, bgn, end);}
	public boolean Load_ctg_v1(Xoctg_view_ctg rv, byte[] ctg_bry) {
		int cat_page_id = db_mgr.Tbl_page().Select_id(Xow_ns_.Id_category, ctg_bry); if (cat_page_id == Xodb_mgr_sql.Page_id_null) return false;
		Xodb_category_itm ctg = db_mgr.Tbl_category().Select(fsys_mgr.Conn_ctg(), cat_page_id); if (ctg == Xodb_category_itm.Null) return false;
		Db_conn p = fsys_mgr.Get_by_idx(ctg.File_idx()).Conn();
		return db_mgr.Ctg_select_v1(rv, p, ctg);
	}
	public boolean Load_ctg_v2(Xoctg_data_ctg rv, byte[] ctg_bry) {throw Err_.not_implemented_();}
	public void Load_ctg_v2a(Xoctg_view_ctg rv, Xoctg_url ctg_url, byte[] ctg_ttl, int load_max) {
		int cat_page_id = db_mgr.Tbl_page().Select_id(Xow_ns_.Id_category, ctg_ttl); if (cat_page_id == Xodb_mgr_sql.Page_id_null) return;
		Xodb_category_itm ctg = db_mgr.Tbl_category().Select(fsys_mgr.Conn_ctg(), cat_page_id); if (ctg == Xodb_category_itm.Null) return;
		Db_conn p = fsys_mgr.Get_by_idx(ctg.File_idx()).Conn();
		ListAdp list = ListAdp_.new_();
		Load_ctg_v2a_db_retrieve(rv, ctg_url, cat_page_id, load_max, p, list);
		Load_ctg_v2a_ui_sift(rv, ctg, list);
	}
	private void Load_ctg_v2a_db_retrieve(Xoctg_view_ctg rv, Xoctg_url ctg_url, int cat_page_id, int load_max, Db_conn p, ListAdp list) {
		int len = Xoa_ctg_mgr.Tid__max;
		for (byte i = Xoa_ctg_mgr.Tid_subc; i < len; i++) {
			boolean arg_is_from = ctg_url.Grp_fwds()[i] == Bool_.N_byte;
			byte[] arg_sortkey = ctg_url.Grp_idxs()[i];
			int found = db_mgr.Tbl_categorylinks().Select_by_type(p, list, cat_page_id, i, arg_sortkey, arg_is_from, load_max);
			if (found > 0 && found == load_max + 1) {
				Xodb_page last_page = (Xodb_page)ListAdp_.Pop(list);
				Xoctg_page_xtn last_ctg = (Xoctg_page_xtn)last_page.Xtn();
				rv.Grp_by_tid(i).Itms_last_sortkey_(last_ctg.Sortkey());
			}
		}
		db_mgr.Tbl_page().Select_by_id_list(Cancelable_.Never, list);
	}
	private void Load_ctg_v2a_ui_sift(Xoctg_view_ctg rv, Xodb_category_itm ctg, ListAdp list) {
		int len = list.Count();
		Xowe_wiki wiki = this.db_mgr.Wiki();
		byte prv_tid = Byte_.Max_value_127;
		Xoctg_view_grp view_grp = null;
		for (int i = 0; i < len; i++) {
			Xodb_page db_page = (Xodb_page)list.FetchAt(i);
			if (db_page.Ns_id() == Int_.MinValue) continue;	// HACK: page not found; ignore
			Xoctg_page_xtn db_ctg = (Xoctg_page_xtn)db_page.Xtn();
			byte cur_tid = db_ctg.Tid();
			if (prv_tid != cur_tid) {
				view_grp = rv.Grp_by_tid(cur_tid);
				prv_tid = cur_tid; 
			}
			Xoa_ttl ttl = Xoa_ttl.parse_(wiki, db_page.Ns_id(), db_page.Ttl_wo_ns());
			Xoctg_view_itm view_itm = new Xoctg_view_itm().Sortkey_(db_ctg.Sortkey()).Ttl_(ttl);
			view_itm.Load_by_ttl_data(cur_tid, db_page.Id(), Xodb_page.Timestamp_null, db_page.Text_len());
			view_grp.Itms_add(view_itm);
		}
		len = Xoa_ctg_mgr.Tid__max;
		for (byte i = Xoa_ctg_mgr.Tid_subc; i < len; i++) {
			view_grp = rv.Grp_by_tid(i);
			view_grp.Itms_make();
			view_grp.Total_(ctg.Count_by_tid(i));
		}
	}
	private Db_conn search_provider = null;
	private void Search_version_init() {
		if (search_provider == null) {
			Xodb_file search_file = db_mgr.Fsys_mgr().Get_tid_root(Xodb_file_tid.Tid_search);
			if (search_file == null) {
				search_provider = Db_conn_.Null;
				search_version = gplx.xowa.specials.search.Xosrh_core.Version_1;
			}
			else {
				search_provider = search_file.Conn();
				search_version = gplx.xowa.specials.search.Xosrh_core.Version_2;
			}
		}
	}
	public void Load_search(Cancelable cancelable, ListAdp rv, byte[] search, int results_max) {
		if (search_provider == null) Search_version_init();
		if (search_version == gplx.xowa.specials.search.Xosrh_core.Version_1)
			db_mgr.Tbl_page().Select_by_search(cancelable, rv, search, results_max);
		else {
			Xodb_search_title_word_tbl.Select_by_word(cancelable, rv, db_mgr.Db_ctx(), search, results_max, db_mgr.Fsys_mgr().Get_tid_root(Xodb_file_tid.Tid_search).Conn());
			db_mgr.Tbl_page().Select_by_id_list(cancelable, true, rv);
		}
	}
	public void Load_ttls_for_all_pages(Cancelable cancelable, ListAdp rslt_list, Xodb_page rslt_nxt, Xodb_page rslt_prv, Int_obj_ref rslt_count, Xow_ns ns, byte[] key, int max_results, int min_page_len, int browse_len, boolean include_redirects, boolean fetch_prv_item) {
		db_mgr.Tbl_page().Load_ttls_for_all_pages(cancelable, rslt_list, rslt_nxt, rslt_prv, rslt_count, ns, key, max_results, min_page_len, browse_len, include_redirects, fetch_prv_item);
	}
	public void Load_ttls_for_search_suggest(Cancelable cancelable, ListAdp rslt_list, Xow_ns ns, byte[] key, int max_results, int min_page_len, int browse_len, boolean include_redirects, boolean fetch_prv_item) {
		db_mgr.Tbl_page().Load_ttls_for_search_suggest(cancelable, rslt_list, ns, key, max_results, min_page_len, browse_len, include_redirects, fetch_prv_item);
	}
	public int Load_ctg_count(byte[] ttl) {
		int page_id = db_mgr.Tbl_page().Select_id(Xow_ns_.Id_category, ttl);
		if (page_id == Xodb_mgr_sql.Page_id_null) return 0;	// title not found; return 0;
		return db_mgr.Tbl_category().Select(fsys_mgr.Conn_ctg(), page_id).Count_all();
	}
	public byte[] Load_qid(byte[] wiki_alias, byte[] ns_num, byte[] ttl)	{return db_mgr.Tbl_wdata_qids().Select_qid(fsys_mgr.Conn_wdata(), wiki_alias, ns_num, ttl);}
	public int Load_pid(byte[] lang_key, byte[] pid_name)					{return db_mgr.Tbl_wdata_pids().Select_pid(fsys_mgr.Conn_wdata(), lang_key, pid_name);}
	public byte[] Find_random_ttl(Xow_ns ns) {return db_mgr.Tbl_page().Select_random(ns);}
	public void Clear() {}
	public Xodb_page[] Load_ctg_list(byte[][] ctg_ttls) {
		int len = ctg_ttls.length;
		OrderedHash hash = OrderedHash_.new_bry_();
		for (int i = 0; i < len; i++) {
			Xodb_page page = new Xodb_page();
			byte[] ttl = Xoa_ttl.Replace_spaces(ctg_ttls[i]);	// NOTE: ctg_ttls has spaces since v1 rendered it literally;
			page.Ttl_wo_ns_(ttl);
			if (!hash.Has(ttl))
				hash.Add(ttl, page);
		}
		len = hash.Count();	// must update len (!hash.Has() may have skipped titles)
		db_mgr.Tbl_page().Select_by_ttl_in(Cancelable_.Never, hash, Xow_ns_.Id_category, 0, len);
		OrderedHash hash2 = OrderedHash_.new_();
		for (int i = 0; i < len; i++) {
			Xodb_page page = (Xodb_page)hash.FetchAt(i);
			if (!hash2.Has(page.Id_val()))
				hash2.Add(page.Id_val(), page);
		}
		len = hash2.Count();	// must update len (!hash2.Has() may have skipped titles)
		db_mgr.Tbl_category().Select_by_cat_id_in(Cancelable_.Never, hash2, fsys_mgr.Conn_ctg(), db_mgr.Db_ctx(), 0, len);
		return (Xodb_page[])hash.Xto_ary(Xodb_page.class);
	}
}
