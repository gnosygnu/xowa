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
import gplx.ios.*; import gplx.dbs.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.wikis.*;
public class Xodb_save_mgr_sql implements Xodb_save_mgr {
	private final Xow_page_mgr page_mgr;
	private final Xodb_mgr_sql db_mgr;
	public Xodb_save_mgr_sql(Xodb_mgr_sql db_mgr, Xow_page_mgr page_mgr) {this.db_mgr = db_mgr; this.page_mgr = page_mgr;} 
	public boolean Create_enabled() {return create_enabled;} public void Create_enabled_(boolean v) {create_enabled = v;} private boolean create_enabled;
	public boolean Update_modified_on_enabled() {return update_modified_on_enabled;} public void Update_modified_on_enabled_(boolean v) {update_modified_on_enabled = v;} private boolean update_modified_on_enabled;
	public int Page_id_next() {return page_id_next;} public void Page_id_next_(int v) {page_id_next = v;} private int page_id_next;
	public void Data_create(Xoa_ttl ttl, byte[] text_raw) {
		int ns_id = ttl.Ns().Id();
		Xowd_db_file db_file = db_mgr.Core_data_mgr().Db__core();
		int ns_count = db_file.Tbl__ns().Select_ns_count(ns_id) + 1;
		int page_id = db_file.Tbl__cfg().Select_int_or("db", "page.id_nxt", -1);
		if (page_id == -1) {	// HACK: was dbs.qrys.Db_qry_sql.rdr_("SELECT (Max(page_id) + 1) AS max_page_id FROM page;")
			Db_rdr rdr = db_mgr.Core_data_mgr().Tbl__page().Conn().Stmt_select(db_file.Tbl__page().Tbl_name(), String_.Ary(db_file.Tbl__page().Fld_page_id()), Db_meta_fld.Ary_empy).Exec_select__rls_auto();
			try {
				int max_page_id = -1;
				while (rdr.Move_next()) {
					int cur_page_id = rdr.Read_int("page_id");
					if (cur_page_id > max_page_id) max_page_id = cur_page_id;
				}
				page_id = max_page_id + 1;
				db_mgr.Core_data_mgr().Tbl__cfg().Upsert_int("db", "page.id_next", page_id + 1);
			} finally {rdr.Rls();}
		}

		Xowd_db_mgr fsys_mgr = db_mgr.Core_data_mgr();
		Xowd_db_file page_text_db = fsys_mgr.Db__text();
		Xowd_text_tbl page_text_tbl = page_text_db.Tbl__text();
		byte[] text_zip = page_text_tbl.Zip(text_raw);
		boolean redirect = db_mgr.Wiki().Redirect_mgr().Is_redirect(text_raw, text_raw.length);
		Xowd_page_tbl page_core_tbl = db_mgr.Core_data_mgr().Tbl__page();
		page_core_tbl.Insert_bgn();
		page_text_tbl.Insert_bgn();
		try {
			page_mgr.Create(page_core_tbl, page_text_tbl, page_id, ns_id, ttl.Page_db(), redirect, DateAdp_.Now(), text_zip, text_raw.length, ns_count, page_text_db.Id(), -1);
			db_file.Tbl__ns().Update_ns_count(ns_id, ns_count);
			db_file.Tbl__cfg().Update_int("db", "page.id_next", page_id + 1);
		} finally {
			page_core_tbl.Insert_end();
			page_text_tbl.Insert_end();
		}
	}
	public void Data_update(Xoae_page page, byte[] text_raw) {
		boolean redirect = db_mgr.Wiki().Redirect_mgr().Is_redirect(text_raw, text_raw.length);
		DateAdp modified = update_modified_on_enabled ? DateAdp_.Now() : page.Revision_data().Modified_on();
		db_mgr.Core_data_mgr().Tbl__page().Update__redirect__modified(page.Revision_data().Id(), redirect, modified);
		Xowd_page_itm db_page = new Xowd_page_itm();
		db_mgr.Load_mgr().Load_by_id(db_page, page.Revision_data().Id());
		Xowd_text_tbl text_tbl = db_mgr.Core_data_mgr().Dbs__get_at(db_page.Text_db_id()).Tbl__text();
		text_tbl.Update(page.Revision_data().Id(), text_raw);
	}
	public void Data_rename(Xoae_page page, int trg_ns, byte[] trg_ttl) {
		db_mgr.Core_data_mgr().Tbl__page().Update__ns__ttl(page.Revision_data().Id(), trg_ns, trg_ttl);
	}
	public void Clear() {}
}
