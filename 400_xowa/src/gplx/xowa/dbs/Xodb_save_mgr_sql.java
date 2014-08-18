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
import gplx.dbs.*; import gplx.ios.*;
public class Xodb_save_mgr_sql implements Xodb_save_mgr {
	public Xodb_save_mgr_sql(Xodb_mgr_sql db_mgr) {
		this.db_mgr = db_mgr; zip_mgr = db_mgr.Wiki().App().Zip_mgr();
	} 	private Xodb_mgr_sql db_mgr; private Io_stream_zip_mgr zip_mgr;
	public boolean Create_enabled() {return create_enabled;} public void Create_enabled_(boolean v) {create_enabled = v;} private boolean create_enabled;
	public boolean Update_modified_on_enabled() {return update_modified_on_enabled;} public void Update_modified_on_enabled_(boolean v) {update_modified_on_enabled = v;} private boolean update_modified_on_enabled;
	public int Page_id_next() {return page_id_next;} public void Page_id_next_(int v) {page_id_next = v;} private int page_id_next;
	public void Data_create(Xoa_ttl ttl, byte[] text) {
		int ns_id = ttl.Ns().Id();
		int ns_count = db_mgr.Tbl_xowa_ns().Select_ns_count(ns_id) + 1;
		String page_id = db_mgr.Tbl_xowa_cfg().Select_val_or("db", "page.id_nxt", null);
		int page_id_int = -1;
		if (page_id == null) {
			DataRdr rdr = db_mgr.Tbl_page().Provider().Exec_sql_as_rdr("SELECT (Max(page_id) + 1) AS max_page_id FROM page;");
			if (rdr.MoveNextPeer()) {
				page_id = Int_.XtoStr(rdr.ReadInt("max_page_id"));
				page_id_int = Int_.parse_(page_id);
				db_mgr.Tbl_xowa_cfg().Insert_int("db", "page.id_next", page_id_int);
			}
			rdr.Rls();
		}
		else
			page_id_int = Int_.parse_(page_id);

		Xodb_fsys_mgr fsys_mgr = db_mgr.Fsys_mgr();
		int file_idx = fsys_mgr.Tid_text_idx();
		boolean redirect = db_mgr.Wiki().Redirect_mgr().Is_redirect(text, text.length);
		Db_stmt page_stmt = db_mgr.Tbl_page().Insert_stmt(fsys_mgr.Provider_page());
		Db_provider text_provider = db_mgr.Fsys_mgr().Get_by_idx(file_idx).Provider();
		Db_stmt text_stmt = db_mgr.Tbl_text().Insert_stmt(text_provider);
		text = zip_mgr.Zip(db_mgr.Data_storage_format(), text);
		try {
			db_mgr.Page_create(page_stmt, text_stmt, page_id_int, ns_id, text, redirect, DateAdp_.Now(), text, ns_count, file_idx);
			db_mgr.Tbl_xowa_ns().Update_ns_count(ns_id, ns_count);
			db_mgr.Tbl_xowa_cfg().Update("db", "page.id_next", page_id + 1);
		} finally {
			page_stmt.Rls();
			text_stmt.Rls();
		}
	}
	public void Data_update(Xoa_page page, byte[] text) {
		boolean redirect = db_mgr.Wiki().Redirect_mgr().Is_redirect(text, text.length);
		DateAdp modified = update_modified_on_enabled ? DateAdp_.Now() : page.Revision_data().Modified_on();
		boolean redirect_changed = redirect != db_mgr.Wiki().Redirect_mgr().Is_redirect(page.Data_raw(), page.Data_raw().length);
		boolean modified_changed = !modified.Eq(page.Revision_data().Modified_on());
		int kv_len = 0;
		if (redirect_changed) ++kv_len;
		if (modified_changed) ++kv_len;
		Db_qry qry = null;
		if (kv_len > 0) {
			KeyVal[] kv_ary = new KeyVal[kv_len];
			int kv_idx = 0;
			if (redirect_changed) kv_ary[kv_idx++] = KeyVal_.new_("page_is_redirect", redirect_changed);
			if (modified_changed) kv_ary[kv_idx++] = KeyVal_.new_("page_touched", Xto_touched_str(modified));
			qry = Db_qry_.update_common_("page", Db_crt_.eq_("page_id", page.Revision_data().Id()), kv_ary);
			Db_provider provider = db_mgr.Fsys_mgr().Provider_core();
			provider.Txn_mgr().Txn_bgn_if_none();
			provider.Exec_qry(qry);
			provider.Txn_mgr().Txn_end_all();
		}
		Xodb_page db_page = new Xodb_page();
		db_mgr.Load_mgr().Load_by_id(db_page, page.Revision_data().Id());
		text = zip_mgr.Zip(db_mgr.Data_storage_format(), text);
		db_mgr.Tbl_text().Update(db_page.Db_file_idx(), page.Revision_data().Id(), text);
	}
	public void Data_rename(Xoa_page page, int trg_ns, byte[] trg_ttl) {
		Db_qry qry = Db_qry_.update_common_("page", Db_crt_.eq_("page_id", page.Revision_data().Id())
		, KeyVal_.new_("page_namespace", trg_ns)
		, KeyVal_.new_("page_title", String_.new_utf8_(trg_ttl))
		);
		try {
			db_mgr.Fsys_mgr().Provider_core().Exec_qry(qry);
		} catch (Exception exc) {
			if (String_.Has(Err_.Message_gplx_brief(exc), "columns page_namespace, page_random_int are not unique")) {	// HACK: terrible hack, but moving pages across ns will break UNIQUE index
				db_mgr.Fsys_mgr().Provider_core().Exec_sql("DROP INDEX page__name_random;"); // is UNIQUE by default
				db_mgr.Fsys_mgr().Provider_core().Exec_sql("CREATE INDEX page__name_random ON page (page_namespace, page_random_int);");
				db_mgr.Fsys_mgr().Provider_core().Exec_qry(qry);
			}
		}
	}
	public void Clear() {}
	private static String Xto_touched_str(DateAdp v) {return v.XtoStr_fmt("yyyyMMddHHmmss");}
}
