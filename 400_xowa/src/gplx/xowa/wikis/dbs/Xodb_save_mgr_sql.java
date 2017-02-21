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
import gplx.core.ios.*; import gplx.dbs.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*; import gplx.dbs.qrys.*;
import gplx.xowa.wikis.*;
public class Xodb_save_mgr_sql implements Xodb_save_mgr {
	private final    Xodb_mgr_sql db_mgr;
	public Xodb_save_mgr_sql(Xodb_mgr_sql db_mgr) {this.db_mgr = db_mgr;} 
	public boolean Create_enabled() {return create_enabled;} public void Create_enabled_(boolean v) {create_enabled = v;} private boolean create_enabled;
	public boolean Update_modified_on_enabled() {return update_modified_on_enabled;} public void Update_modified_on_enabled_(boolean v) {update_modified_on_enabled = v;} private boolean update_modified_on_enabled;
	public int Page_id_next() {return page_id_next;} public void Page_id_next_(int v) {page_id_next = v;} private int page_id_next;
	public int Data_create(Xoa_ttl ttl, byte[] text_raw) {
		int ns_id = ttl.Ns().Id();
		Xow_db_file db_file = db_mgr.Core_data_mgr().Db__core();
		int ns_count = db_file.Tbl__ns().Select_ns_count(ns_id) + 1;
		int page_id = db_file.Tbl__cfg().Select_int_or(Xowd_cfg_key_.Grp__db, Xowd_cfg_key_.Key__wiki__page__id_next, -1);
		if (page_id == -1) {	// HACK: changed for tests; was dbs.qrys.Db_qry_sql.rdr_("SELECT (Max(page_id) + 1) AS max_page_id FROM page;")
			Db_rdr rdr = db_mgr.Core_data_mgr().Tbl__page().Conn().Stmt_select(db_file.Tbl__page().Tbl_name(), String_.Ary(db_file.Tbl__page().Fld_page_id()), Dbmeta_fld_itm.Str_ary_empty).Exec_select__rls_auto();
			try {
				int max_page_id = -1;
				while (rdr.Move_next()) {
					int cur_page_id = rdr.Read_int("page_id");
					if (cur_page_id > max_page_id) max_page_id = cur_page_id;
				}
				page_id = max_page_id + 1;
				db_mgr.Core_data_mgr().Tbl__cfg().Upsert_int(Xowd_cfg_key_.Grp__db, Xowd_cfg_key_.Key__wiki__page__id_next, page_id + 1);
			} finally {rdr.Rls();}
		}
		Xow_db_mgr fsys_mgr = db_mgr.Core_data_mgr();
		Xow_db_file page_text_db = fsys_mgr.Db__text();
		if (page_text_db == null) page_text_db = fsys_mgr.Db__core();	// HACK: needed for create new wiki DATE:2016-10-29
		Xowd_text_tbl page_text_tbl = page_text_db.Tbl__text();
		byte[] text_zip = page_text_tbl.Zip(text_raw);
		boolean redirect = db_mgr.Wiki().Redirect_mgr().Is_redirect(text_raw, text_raw.length);
		Xowd_page_tbl page_core_tbl = db_mgr.Core_data_mgr().Tbl__page();
		page_core_tbl.Insert_bgn();
		page_text_tbl.Insert_bgn();
		try {
			db_mgr.Core_data_mgr().Create_page(page_core_tbl, page_text_tbl, page_id, ns_id, ttl.Page_db(), redirect, Datetime_now.Get(), text_zip, text_raw.length, ns_count, page_text_db.Id(), -1);
			db_file.Tbl__ns().Update_ns_count(ns_id, ns_count);
			db_file.Tbl__cfg().Update_int(Xowd_cfg_key_.Grp__db, Xowd_cfg_key_.Key__wiki__page__id_next, page_id + 1);
		} finally {
			page_core_tbl.Insert_end();
			page_text_tbl.Insert_end();
		}
		return page_id;
	}
	public void Data_update(Xoae_page page, byte[] text_raw) {
		boolean redirect = db_mgr.Wiki().Redirect_mgr().Is_redirect(text_raw, text_raw.length);
		DateAdp modified = update_modified_on_enabled ? Datetime_now.Get() : page.Db().Page().Modified_on();
		int page_id = page.Db().Page().Id();
		db_mgr.Core_data_mgr().Tbl__page().Update__redirect__modified(page_id, redirect, modified);
		Xowd_page_itm db_page = new Xowd_page_itm();
		db_mgr.Load_mgr().Load_by_id(db_page, page_id);
		Xowd_text_tbl text_tbl = db_mgr.Core_data_mgr().Dbs__get_by_id_or_fail(db_page.Text_db_id()).Tbl__text();
		text_tbl.Update(page_id, text_raw);
//			int html_db_id = db_page.Html_db_id();
//			if (html_db_id != -1)
//				db_mgr.Core_data_mgr().Tbl__page().Update__html_db_id(page_id, -1);	// zap html_db_id so that next load will repopulate it
	}
	public void Data_rename(Xoae_page page, int trg_ns, byte[] trg_ttl) {
		db_mgr.Core_data_mgr().Tbl__page().Update__ns__ttl(page.Db().Page().Id(), trg_ns, trg_ttl);
	}
	public void Clear() {}
}
