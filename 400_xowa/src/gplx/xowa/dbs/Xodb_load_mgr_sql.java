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
import gplx.core.primitives.*; import gplx.dbs.*; import gplx.dbs.cfgs.*;
import gplx.xowa.apps.*; import gplx.xowa.bldrs.cmds.ctgs.*; import gplx.xowa.ctgs.*; import gplx.xowa.specials.search.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
public class Xodb_load_mgr_sql implements Xodb_load_mgr {
	public Xodb_load_mgr_sql(Xodb_mgr_sql db_mgr, Xowd_db_mgr fsys_mgr) {this.db_mgr = db_mgr; this.fsys_mgr = fsys_mgr;} private Xodb_mgr_sql db_mgr; Xowd_db_mgr fsys_mgr;
	public byte Search_version() {
		if (search_version_init_needed) Search_version_init();
		return search_version;
	}	private byte search_version = gplx.xowa.specials.search.Xosrh_core.Version_null;
	public void Search_version_refresh() {
		search_version_init_needed = true;
		Search_version_init();
	}
	public void Load_init(Xowe_wiki wiki) {
		Load_init_cfg(wiki);
		Xowd_db_file db_core = wiki.Data_mgr__core_mgr().Db__core();
		db_core.Tbl__site_stats().Select(wiki.Stats());
		db_core.Tbl__ns().Select_all(wiki.Ns_mgr());
	}
	private void Load_init_cfg(Xowe_wiki wiki) {
		String version_key = Xoa_gfs_mgr.Build_code(Xowe_wiki.Invk_props, Xow_wiki_props.Invk_bldr_version);
		Db_cfg_hash cfg_hash = db_mgr.Core_data_mgr().Tbl__cfg().Select_as_hash(Xow_cfg_consts.Grp_wiki_init);
		String version_val = cfg_hash.Get(version_key).To_str_or("");
		Xodb_upgrade_mgr.Upgrade(db_mgr, cfg_hash, version_key, version_val);
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_k004();
		Xoa_gfs_mgr gfs_mgr = wiki.Appe().Gfs_mgr();
		try {
			int len = cfg_hash.Len();
			for (int i = 0; i < len; ++i) {
				Db_cfg_itm cfg_itm = cfg_hash.Get_at(i);
				gfs_mgr.Build_prop_set(bfr, Bry_.new_utf8_(cfg_itm.Key()), Bry_.new_utf8_(cfg_itm.To_str_or("")));
			}
			gfs_mgr.Run_str_for(wiki, bfr.Xto_str_and_clear());
		}	finally {bfr.Mkr_rls();}
	}
	public boolean Load_by_ttl(Xowd_page_itm rv, Xow_ns ns, byte[] ttl) {return db_mgr.Core_data_mgr().Tbl__page().Select_by_ttl(rv, ns, ttl);}
	public void Load_by_ttls(Cancelable cancelable, OrderedHash rv, boolean fill_idx_fields_only, int bgn, int end) {
		db_mgr.Core_data_mgr().Tbl__page().Select_in__ns_ttl(cancelable, rv, db_mgr.Wiki().Ns_mgr(), fill_idx_fields_only, bgn, end);
	}
	public void Load_page(Xowd_page_itm rv, Xow_ns ns, boolean timestamp_enabled) {
		Xowd_text_tbl text_tbl = db_mgr.Core_data_mgr().Dbs__get_at(rv.Text_db_id()).Tbl__text();
		byte[] text_bry = text_tbl.Select(rv.Id());
		rv.Text_(text_bry);
	}
	public boolean Load_by_id	(Xowd_page_itm rv, int id) {return db_mgr.Core_data_mgr().Tbl__page().Select_by_id(rv, id);}
	public void Load_by_ids(Cancelable cancelable, ListAdp rv, int bgn, int end) {db_mgr.Core_data_mgr().Tbl__page().Select_in__id(cancelable, false, rv, bgn, end);}
	public boolean Load_ctg_v1(Xoctg_view_ctg rv, byte[] ctg_bry) {
		int cat_page_id = db_mgr.Core_data_mgr().Tbl__page().Select_id(Xow_ns_.Id_category, ctg_bry); if (cat_page_id == Xowd_page_itm.Id_null) return false;			
		Xowd_category_itm ctg = fsys_mgr.Db__cat_core().Tbl__cat_core().Select(cat_page_id); if (ctg == Xowd_category_itm.Null) return false;
		return Ctg_select_v1(db_mgr.Wiki(), db_mgr.Core_data_mgr(), rv, ctg.File_idx(), ctg);
	}
	public boolean Load_ctg_v2(Xoctg_data_ctg rv, byte[] ctg_bry) {throw Err_.not_implemented_();}
	public void Load_ctg_v2a(Xoctg_view_ctg rv, Xoctg_url ctg_url, byte[] ctg_ttl, int load_max) {
		int cat_page_id = db_mgr.Core_data_mgr().Tbl__page().Select_id(Xow_ns_.Id_category, ctg_ttl); if (cat_page_id == Xowd_page_itm.Id_null) return;
		Xowd_category_itm ctg = fsys_mgr.Db__cat_core().Tbl__cat_core().Select(cat_page_id); if (ctg == Xowd_category_itm.Null) return;
		ListAdp list = ListAdp_.new_();
		Load_ctg_v2a_db_retrieve(rv, ctg_url, cat_page_id, load_max, ctg.File_idx(), list);
		Load_ctg_v2a_ui_sift(rv, ctg, list);
	}
	private void Load_ctg_v2a_db_retrieve(Xoctg_view_ctg rv, Xoctg_url ctg_url, int cat_page_id, int load_max, int cat_link_db_idx, ListAdp list) {
		int len = Xoa_ctg_mgr.Tid__max;
		for (byte i = Xoa_ctg_mgr.Tid_subc; i < len; i++) {
			boolean arg_is_from = ctg_url.Grp_fwds()[i] == Bool_.N_byte;
			byte[] arg_sortkey = ctg_url.Grp_idxs()[i];
			Xowd_cat_link_tbl cat_link_tbl = db_mgr.Core_data_mgr().Dbs__get_at(cat_link_db_idx).Tbl__cat_link();
			int found = cat_link_tbl.Select_by_type(list, cat_page_id, i, arg_sortkey, arg_is_from, load_max);
			if (found > 0 && found == load_max + 1) {
				Xowd_page_itm last_page = (Xowd_page_itm)ListAdp_.Pop(list);
				Xoctg_page_xtn last_ctg = (Xoctg_page_xtn)last_page.Xtn();
				rv.Grp_by_tid(i).Itms_last_sortkey_(last_ctg.Sortkey());
			}
		}
		db_mgr.Core_data_mgr().Tbl__page().Select_in__id(Cancelable_.Never, list);
	}
	private void Load_ctg_v2a_ui_sift(Xoctg_view_ctg rv, Xowd_category_itm ctg, ListAdp list) {
		int len = list.Count();
		Xowe_wiki wiki = this.db_mgr.Wiki();
		byte prv_tid = Byte_.Max_value_127;
		Xoctg_view_grp view_grp = null;
		for (int i = 0; i < len; i++) {
			Xowd_page_itm db_page = (Xowd_page_itm)list.FetchAt(i);
			if (db_page.Ns_id() == Int_.MinValue) continue;	// HACK: page not found; ignore
			Xoctg_page_xtn db_ctg = (Xoctg_page_xtn)db_page.Xtn();
			byte cur_tid = db_ctg.Tid();
			if (prv_tid != cur_tid) {
				view_grp = rv.Grp_by_tid(cur_tid);
				prv_tid = cur_tid; 
			}
			Xoa_ttl ttl = Xoa_ttl.parse_(wiki, db_page.Ns_id(), db_page.Ttl_page_db());
			Xoctg_view_itm view_itm = new Xoctg_view_itm().Sortkey_(db_ctg.Sortkey()).Ttl_(ttl);
			view_itm.Load_by_ttl_data(cur_tid, db_page.Id(), Xowd_page_itm.Modified_on_null_int, db_page.Text_len());
			view_grp.Itms_add(view_itm);
		}
		len = Xoa_ctg_mgr.Tid__max;
		for (byte i = Xoa_ctg_mgr.Tid_subc; i < len; i++) {
			view_grp = rv.Grp_by_tid(i);
			view_grp.Itms_make();
			view_grp.Total_(ctg.Count_by_tid(i));
		}
	}
	private boolean search_version_init_needed = true;
	private void Search_version_init() {
		if (search_version_init_needed) {
			search_version_init_needed = false;
			Xowd_db_file search_db = db_mgr.Core_data_mgr().Db__search();
			search_version = search_db == Xowd_db_file.Null ? Xosrh_core.Version_1 : Xosrh_core.Version_2;
		}
	}
	public void Load_search(Cancelable cancelable, ListAdp rv, byte[] search, int results_max) {
		if (search_version_init_needed) Search_version_init();
		if (search_version == gplx.xowa.specials.search.Xosrh_core.Version_1)
			db_mgr.Core_data_mgr().Tbl__page().Select_by_search(cancelable, rv, search, results_max);
		else {
			Xowd_db_mgr core_data_mgr = db_mgr.Core_data_mgr();
			core_data_mgr.Db__search().Tbl__search_word().Select_by_word(cancelable, core_data_mgr.Db__search().Tbl__search_link(), rv, search, results_max);
			core_data_mgr.Tbl__page().Select_in__id(cancelable, true, rv);
		}
	}
	public void Load_ttls_for_all_pages(Cancelable cancelable, ListAdp rslt_list, Xowd_page_itm rslt_nxt, Xowd_page_itm rslt_prv, Int_obj_ref rslt_count, Xow_ns ns, byte[] key, int max_results, int min_page_len, int browse_len, boolean include_redirects, boolean fetch_prv_item) {
		db_mgr.Core_data_mgr().Tbl__page().Select_for_special_all_pages(cancelable, rslt_list, rslt_nxt, rslt_prv, rslt_count, ns, key, max_results, min_page_len, browse_len, include_redirects, fetch_prv_item);
	}
	public void Load_ttls_for_search_suggest(Cancelable cancelable, ListAdp rslt_list, Xow_ns ns, byte[] key, int max_results, int min_page_len, int browse_len, boolean include_redirects, boolean fetch_prv_item) {
		db_mgr.Core_data_mgr().Tbl__page().Select_for_search_suggest(cancelable, rslt_list, ns, key, max_results, min_page_len, browse_len, include_redirects, fetch_prv_item);
	}
	public int Load_ctg_count(byte[] ttl) {
		if (db_mgr.Core_data_mgr().Db__cat_core() == null) return 0;
		int page_id = db_mgr.Core_data_mgr().Tbl__page().Select_id(Xow_ns_.Id_category, ttl);
		if (page_id == Xowd_page_itm.Id_null) return 0;	// title not found; return 0;
		return db_mgr.Core_data_mgr().Db__cat_core().Tbl__cat_core().Select(page_id).Count_all();
	}
	public byte[] Load_qid(byte[] wiki_alias, byte[] ns_num, byte[] ttl)	{return db_mgr.Core_data_mgr().Db__wbase().Tbl__wbase_qid().Select_qid(wiki_alias, ns_num, ttl);}
	public int Load_pid(byte[] lang_key, byte[] pid_name)					{return db_mgr.Core_data_mgr().Db__wbase().Tbl__wbase_pid().Select_pid(lang_key, pid_name);}
	public byte[] Find_random_ttl(Xow_ns ns) {return db_mgr.Core_data_mgr().Tbl__page().Select_random(ns);}
	public void Clear() {}
	public Xowd_page_itm[] Load_ctg_list(byte[][] ctg_ttls) {
		if (db_mgr.Core_data_mgr().Db__cat_core() == null) return Xowd_page_itm.Ary_empty;
		int len = ctg_ttls.length;
		OrderedHash hash = OrderedHash_.new_bry_();
		for (int i = 0; i < len; i++) {
			Xowd_page_itm page = new Xowd_page_itm();
			byte[] ttl = Xoa_ttl.Replace_spaces(ctg_ttls[i]);	// NOTE: ctg_ttls has spaces since v1 rendered it literally;
			page.Ttl_page_db_(ttl);
			if (!hash.Has(ttl))
				hash.Add(ttl, page);
		}
		len = hash.Count();	// must update len (!hash.Has() may have skipped titles)
		db_mgr.Core_data_mgr().Tbl__page().Select_in__ttl(Cancelable_.Never, hash, Xow_ns_.Id_category, 0, len);
		OrderedHash hash2 = OrderedHash_.new_();
		for (int i = 0; i < len; i++) {
			Xowd_page_itm page = (Xowd_page_itm)hash.FetchAt(i);
			if (!hash2.Has(page.Id_val()))
				hash2.Add(page.Id_val(), page);
		}
		len = hash2.Count();	// must update len (!hash2.Has() may have skipped titles)
		db_mgr.Core_data_mgr().Db__cat_core().Tbl__cat_core().Select_by_cat_id_in(Cancelable_.Never, hash2, 0, len);
		return (Xowd_page_itm[])hash.Xto_ary(Xowd_page_itm.class);
	}
	private static boolean Ctg_select_v1(Xowe_wiki wiki, Xowd_db_mgr core_data_mgr, Xoctg_view_ctg view_ctg, int link_db_id, Xowd_category_itm ctg) {
		ListAdp link_list = ListAdp_.new_();
		core_data_mgr.Dbs__get_at(link_db_id).Tbl__cat_link().Select_in(link_list, ctg.Id());
		int link_list_len = link_list.Count();
		link_list.SortBy(Xowd_page_itm_sorter.IdAsc);
		core_data_mgr.Tbl__page().Select_in__id(Cancelable_.Never, false, link_list, 0, link_list_len);
		link_list.SortBy(Xowd_page_itm_sorter.Ns_id_TtlAsc);
		boolean rv = false;
		for (int i = 0; i < link_list.Count(); i++) {
			Xowd_page_itm page = (Xowd_page_itm)link_list.FetchAt(i);
			if (page.Ns_id() == Int_.MinValue) continue;	// HACK: page not found; ignore
			byte ctg_tid = Xodb_load_mgr_txt.Load_ctg_v1_tid(page.Ns_id());
			Xoctg_view_grp ctg_grp = view_ctg.Grp_by_tid(ctg_tid);
			Xoctg_view_itm ctg_itm = new Xoctg_view_itm();
			ctg_itm.Load_by_ttl_data(ctg_tid, page.Id(), 0, page.Text_len());
			ctg_itm.Ttl_(Xoa_ttl.parse_(wiki, page.Ns_id(), page.Ttl_page_db()));
			ctg_itm.Sortkey_(page.Ttl_page_db());
			ctg_grp.Itms_add(ctg_itm);
			rv = true;
		}
		for (byte i = 0; i < Xoa_ctg_mgr.Tid__max; i++) {
			Xoctg_view_grp ctg_grp = view_ctg.Grp_by_tid(i);
			ctg_grp.Itms_make();
			ctg_grp.Total_(ctg_grp.Itms().length);
		}
		return rv;
	}
}
