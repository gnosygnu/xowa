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
package gplx.xowa.wikis.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.primitives.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.apps.gfs.*; 
import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.metas.*; import gplx.xowa.wikis.data.*;
public class Xodb_load_mgr_sql implements Xodb_load_mgr {
	private final    Xodb_mgr_sql db_mgr;
	public Xodb_load_mgr_sql(Xodb_mgr_sql db_mgr) {this.db_mgr = db_mgr;}
	public void Load_init(Xowe_wiki wiki) {
		Xow_db_file db_core = wiki.Data__core_mgr().Db__core();
		Load_cfg(wiki);
		db_core.Tbl__site_stats().Select(wiki.Stats());
		db_core.Tbl__ns().Select_all(wiki.Ns_mgr());
	}
	private static void Load_cfg(Xow_wiki wiki) {
		byte[] main_page = null, bldr_version = null, siteinfo_misc = null, siteinfo_mainpage = null;
		DateAdp modified_latest = null;

		// load from xowa_cfg
		gplx.dbs.cfgs.Db_cfg_hash prop_hash = wiki.Data__core_mgr().Db__core().Tbl__cfg().Select_as_hash(Xowd_cfg_key_.Grp__wiki_init);
		int len = prop_hash.Len();
		for (int i = 0; i < len; i++) {
			gplx.dbs.cfgs.Db_cfg_itm prop = prop_hash.Get_at(i);
			String prop_key = prop.Key();
			try {
				if      (String_.Eq(prop_key, Xowd_cfg_key_.Key__init__main_page))         main_page = Bry_.new_u8(prop.Val());
				else if (String_.Eq(prop_key, Xowd_cfg_key_.Key__init__bldr_version))      bldr_version = Bry_.new_u8(prop.Val());
				else if (String_.Eq(prop_key, Xowd_cfg_key_.Key__init__siteinfo_misc))     siteinfo_misc = Bry_.new_u8(prop.Val());
				else if (String_.Eq(prop_key, Xowd_cfg_key_.Key__init__siteinfo_mainpage)) siteinfo_mainpage = Bry_.new_u8(prop.Val());
				else if (String_.Eq(prop_key, Xowd_cfg_key_.Key__init__modified_latest))   modified_latest = DateAdp_.parse_gplx(prop.Val());
			} catch (Exception exc) {
				Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to set prop; key=~{0} val=~{1} err=~{2}", prop_key, prop.Val(), Err_.Message_gplx_log(exc));
			}
		}

		wiki.Props().Init_by_load_2(main_page, bldr_version, siteinfo_misc, siteinfo_mainpage, modified_latest);
	}
	public boolean Load_by_ttl(Xowd_page_itm rv, Xow_ns ns, byte[] ttl) {
		return db_mgr.Core_data_mgr().Tbl__page().Select_by_ttl(rv, ns, ttl);
	}
	public void Load_by_ttls(Cancelable cancelable, Ordered_hash rv, boolean fill_idx_fields_only, int bgn, int end) {
		db_mgr.Core_data_mgr().Tbl__page().Select_in__ns_ttl(cancelable, rv, db_mgr.Wiki().Ns_mgr(), fill_idx_fields_only, bgn, end);
	}
	public void Load_page(Xowd_page_itm rv, Xow_ns ns) {
		if (rv.Text_db_id() == -1) return; // NOTE: page_sync will create pages with -1 text_db_id; DATE:2017-05-06

		// get text
		Xowd_text_tbl text_tbl = db_mgr.Core_data_mgr().Dbs__get_by_id_or_fail(rv.Text_db_id()).Tbl__text();
		byte[] text_bry = text_tbl.Select(rv.Id());
		rv.Text_(text_bry);
	}
	public boolean Load_by_id	(Xowd_page_itm rv, int id) {return db_mgr.Core_data_mgr().Tbl__page().Select_by_id(rv, id);}
	public void Load_by_ids(Cancelable cancelable, List_adp rv, int bgn, int end) {db_mgr.Core_data_mgr().Tbl__page().Select_in__id(cancelable, false, true, rv, bgn, end);}
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
}
