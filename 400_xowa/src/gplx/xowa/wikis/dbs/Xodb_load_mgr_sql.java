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
package gplx.xowa.wikis.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.primitives.*; import gplx.dbs.*; import gplx.dbs.cfgs.*;
import gplx.xowa.apps.gfs.*; import gplx.xowa.addons.wikis.ctgs.bldrs.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.metas.*; import gplx.xowa.wikis.data.*;
import gplx.xowa.addons.wikis.searchs.*;
import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.urls.*;
public class Xodb_load_mgr_sql implements Xodb_load_mgr {
	private Xodb_mgr_sql db_mgr; Xow_db_mgr fsys_mgr;
	public Xodb_load_mgr_sql(Xow_wiki wiki, Xodb_mgr_sql db_mgr, Xow_db_mgr fsys_mgr) {this.db_mgr = db_mgr; this.fsys_mgr = fsys_mgr;}
	public void Load_init(Xowe_wiki wiki) {
		Load_init_cfg(wiki);
		Xow_db_file db_core = wiki.Data__core_mgr().Db__core();
		db_core.Tbl__site_stats().Select(wiki.Stats());
		db_core.Tbl__ns().Select_all(wiki.Ns_mgr());
	}
	private void Load_init_cfg(Xowe_wiki wiki) {
		String version_key = Xoa_gfs_wtr_.Write_func_chain(Xowe_wiki.Invk_props, Xow_wiki_props.Invk_bldr_version);
		Db_cfg_hash cfg_hash = db_mgr.Core_data_mgr().Tbl__cfg().Select_as_hash(Xow_cfg_consts.Grp__wiki_init);
		String version_val = cfg_hash.Get_by(version_key).To_str_or("");
		Xodb_upgrade_mgr.Upgrade(db_mgr, cfg_hash, version_key, version_val);
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_k004();
		Xoa_gfs_mgr gfs_mgr = wiki.Appe().Gfs_mgr();
		try {
			int len = cfg_hash.Len();
			for (int i = 0; i < len; ++i) {
				Db_cfg_itm cfg_itm = cfg_hash.Get_at(i);
				Xoa_gfs_wtr_.Write_prop(bfr, Bry_.new_u8(cfg_itm.Key()), Bry_.new_u8(cfg_itm.To_str_or("")));
			}
			gfs_mgr.Run_str_for(wiki, bfr.To_str_and_clear());
		}	finally {bfr.Mkr_rls();}
	}
	public boolean Load_by_ttl(Xowd_page_itm rv, Xow_ns ns, byte[] ttl) {return db_mgr.Core_data_mgr().Tbl__page().Select_by_ttl(rv, ns, ttl);}
	public void Load_by_ttls(Cancelable cancelable, Ordered_hash rv, boolean fill_idx_fields_only, int bgn, int end) {
		db_mgr.Core_data_mgr().Tbl__page().Select_in__ns_ttl(cancelable, rv, db_mgr.Wiki().Ns_mgr(), fill_idx_fields_only, bgn, end);
	}
	public void Load_page(Xowd_page_itm rv, Xow_ns ns) {
		Xowd_text_tbl text_tbl = db_mgr.Core_data_mgr().Dbs__get_by_id_or_fail(rv.Text_db_id()).Tbl__text();
		byte[] text_bry = text_tbl.Select(rv.Id());
		rv.Text_(text_bry);
	}
	public boolean Load_by_id	(Xowd_page_itm rv, int id) {return db_mgr.Core_data_mgr().Tbl__page().Select_by_id(rv, id);}
	public void Load_by_ids(Cancelable cancelable, List_adp rv, int bgn, int end) {db_mgr.Core_data_mgr().Tbl__page().Select_in__id(cancelable, false, true, rv, bgn, end);}
	public boolean Load_ctg_v1(Xoctg_catpage_ctg rv, byte[] ctg_bry) {
		int cat_page_id = db_mgr.Core_data_mgr().Tbl__page().Select_id(Xow_ns_.Tid__category, ctg_bry); if (cat_page_id == Xowd_page_itm.Id_null) return false;			
		Xowd_category_itm ctg = fsys_mgr.Db__cat_core().Tbl__cat_core().Select(cat_page_id); if (ctg == Xowd_category_itm.Null) return false;
		return Ctg_select_v1(db_mgr.Wiki(), db_mgr.Core_data_mgr(), rv, ctg.File_idx(), ctg);
	}
	public void Load_ttls_for_all_pages(Cancelable cancelable, List_adp rslt_list, Xowd_page_itm rslt_nxt, Xowd_page_itm rslt_prv, Int_obj_ref rslt_count, Xow_ns ns, byte[] key, int max_results, int min_page_len, int browse_len, boolean include_redirects, boolean fetch_prv_item) {
		db_mgr.Core_data_mgr().Tbl__page().Select_for_special_all_pages(cancelable, rslt_list, rslt_nxt, rslt_prv, rslt_count, ns, key, max_results, min_page_len, browse_len, include_redirects, fetch_prv_item);
	}
	public void Load_ttls_for_search_suggest(Cancelable cancelable, List_adp rslt_list, Xow_ns ns, byte[] key, int max_results, int min_page_len, int browse_len, boolean include_redirects, boolean fetch_prv_item) {
		db_mgr.Core_data_mgr().Tbl__page().Select_for_search_suggest(cancelable, rslt_list, ns, key, max_results, min_page_len, browse_len, include_redirects, fetch_prv_item);
	}
	public byte[] Load_qid(byte[] wiki_alias, byte[] ns_num, byte[] ttl)	{return db_mgr.Core_data_mgr().Db__wbase().Tbl__wbase_qid().Select_qid(wiki_alias, ns_num, ttl);}
	public int Load_pid(byte[] lang_key, byte[] pid_name)					{return db_mgr.Core_data_mgr().Db__wbase().Tbl__wbase_pid().Select_pid(lang_key, pid_name);}
	public byte[] Find_random_ttl(Xow_ns ns) {return db_mgr.Core_data_mgr().Tbl__page().Select_random(ns);}
	public Xodb_page_rdr Get_page_rdr(Xowe_wiki wiki) {return new Xodb_page_rdr__sql(wiki);}
	public void Clear() {}
	private static boolean Ctg_select_v1(Xowe_wiki wiki, Xow_db_mgr core_data_mgr, Xoctg_catpage_ctg view_ctg, int link_db_id, Xowd_category_itm ctg) {
		List_adp link_list = List_adp_.New();
		core_data_mgr.Dbs__get_by_id_or_fail(link_db_id).Tbl__cat_link().Select_in(link_list, ctg.Id());
		int link_list_len = link_list.Count();
		link_list.Sort_by(Xowd_page_itm_sorter.IdAsc);
		core_data_mgr.Tbl__page().Select_in__id(Cancelable_.Never, false, true, link_list, 0, link_list_len);
		link_list.Sort_by(Xowd_page_itm_sorter.Ns_id_TtlAsc);
		boolean rv = false;
		for (int i = 0; i < link_list.Count(); i++) {
			Xowd_page_itm page = (Xowd_page_itm)link_list.Get_at(i);
			if (page.Ns_id() == Int_.Min_value) continue;	// HACK: page not found; ignore
			byte ctg_tid = Load_ctg_v1_tid(page.Ns_id());
			Xoctg_catpage_grp ctg_grp = view_ctg.Grp_by_tid(ctg_tid);
			Xoctg_catpage_itm ctg_itm = new Xoctg_catpage_itm(page.Id(), Xoa_ttl.Parse(wiki, page.Ns_id(), page.Ttl_page_db()), page.Ttl_page_db());
			ctg_grp.Itms__add(ctg_itm);
			rv = true;
		}
		view_ctg.Make_itms();
		return rv;
	}
	public static byte Load_ctg_v1_tid(int ns_id) {
		switch (ns_id) {
			case Xow_ns_.Tid__category: return Xoa_ctg_mgr.Tid__subc;
			case Xow_ns_.Tid__file:		return Xoa_ctg_mgr.Tid__file;
			default: 					return Xoa_ctg_mgr.Tid__page;
		}		
	}
}
