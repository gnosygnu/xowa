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
package gplx.xowa.hdumps; import gplx.*; import gplx.xowa.*;
import gplx.dbs.*; import gplx.ios.*; import gplx.xowa.dbs.*; import gplx.xowa.dbs.tbls.*; import gplx.xowa.hdumps.saves.*; import gplx.xowa.hdumps.dbs.*; import gplx.xowa.hdumps.core.*; import gplx.xowa.html.hzips.*;
public class Xob_hdump_bldr {
	private Xodb_fsys_mgr fsys_mgr; private Xodb_file core_db; private Xodb_xowa_db_tbl db_tbl; private Xodb_xowa_cfg_tbl cfg_tbl; private Db_stmt cfg_update_stmt;
	private int hdump_db_id; private long hdump_db_size, hdump_db_max; private Db_conn hdump_db_provider;
	private Xodb_hdump_mgr hdump_mgr; private Hdump_save_mgr hdump_save_mgr;
	public Xob_hdump_bldr(Xodb_mgr_sql db_mgr, Db_conn make_provider, long hdump_db_max) {
		this.hdump_db_max = hdump_db_max;
		this.fsys_mgr = db_mgr.Fsys_mgr();
		core_db = fsys_mgr.Get_tid_root(Xodb_file_tid.Tid_core);
		db_tbl = db_mgr.Tbl_xowa_db();
		cfg_tbl = new Xodb_xowa_cfg_tbl().Conn_(make_provider); cfg_update_stmt = cfg_tbl.Update_stmt();
		Xodb_hdump_mgr_setup.Assert_col__page_html_db_id(core_db.Conn(), cfg_tbl);
		hdump_mgr = db_mgr.Hdump_mgr(); hdump_save_mgr = hdump_mgr.Save_mgr();
		hdump_save_mgr.Hdump_stats_enable_y_(make_provider);
	}
	public void Bld_init() {
		Db_init(Db_get_last_or_make(fsys_mgr), cfg_tbl.Select_val_as_long_or(Cfg_grp_hdump_make, Cfg_itm_hdump_size, 0));
		hdump_db_provider.Txn_mgr().Txn_bgn_if_none();
	}
	public void Bld_term() {
		this.Commit();
		Db_term(core_db, hdump_db_provider, hdump_db_id);
	}
	private Bry_bfr tmp_bfr = Bry_bfr.reset_(Io_mgr.Len_mb); private Xow_hzip_stats hzip_stats = new Xow_hzip_stats();
	public void Insert_page(Xoa_page page) {
		hdump_mgr.Write2(tmp_bfr, page, hzip_stats);
		hdump_db_size += hdump_save_mgr.Insert_body(page, hzip_stats, page.Revision_data().Id());
		if (hdump_db_size > hdump_db_max) {
			Db_term(core_db, hdump_db_provider, hdump_db_id);				
			Db_init(Db_make(fsys_mgr), 0);
		}
	}
	public void Commit() {
		db_tbl.Commit_all(fsys_mgr);								// commit new html_dbs
		hdump_db_provider.Txn_mgr().Txn_end_all_bgn_if_none();		// commit entries
		cfg_tbl.Update(cfg_update_stmt, Cfg_grp_hdump_make, Cfg_itm_hdump_size, Long_.Xto_str(hdump_db_size));	// update cfg; should happen after commit entries
	}
	private static Xodb_file Db_get_last_or_make(Xodb_fsys_mgr fsys_mgr) {
		Xodb_file rv = fsys_mgr.Get_tid_root(Xodb_file_tid.Tid_html);
		if (rv == null) rv = Db_make(fsys_mgr);
		return rv;
	}
	private void Db_init(Xodb_file db_file, long hdump_db_size) {
		this.hdump_db_id = db_file.Id();
		this.hdump_db_size = hdump_db_size;
		this.hdump_db_provider = db_file.Conn();
		this.hdump_save_mgr.Tbl().Conn_(hdump_db_provider);
	}
	private static Xodb_file Db_make(Xodb_fsys_mgr fsys_mgr) {
		Xodb_file rv = fsys_mgr.Make(Xodb_file_tid.Tid_html);
		rv.Conn().Exec_sql(Xodb_wiki_page_html_tbl.Tbl_sql);
		return rv;
	}
	private static void Db_term(Xodb_file core_db_file, Db_conn hdump_db_provider, int hdump_db_id) {
		hdump_db_provider.Txn_mgr().Txn_end_all();											// commit transactions
		Sqlite_engine_.Idx_create(hdump_db_provider, Xodb_wiki_page_html_tbl.Idx_core);		// create index
		Sqlite_engine_.Db_attach(hdump_db_provider, "page_db", core_db_file.Url().Raw());	// update page_db.page with page_html_db_id
		hdump_db_provider.Txn_mgr().Txn_bgn();
		hdump_db_provider.Exec_sql(String_.Format(Sql_update_page_html_db_id, hdump_db_id));
		hdump_db_provider.Txn_mgr().Txn_end();
		Sqlite_engine_.Db_detach(hdump_db_provider, "page_db");
		hdump_db_provider.Conn_term();														// release conn
	}
	private static final String Cfg_grp_hdump_make = "hdump.make", Cfg_itm_hdump_size = "hdump.size";
	private static final String Sql_update_page_html_db_id = String_.Concat_lines_nl_skip_last
	( "REPLACE INTO page_db.page (page_id, page_namespace, page_title, page_is_redirect, page_touched, page_len, page_random_int, page_file_idx, page_redirect_id, page_html_db_id)"
	, "SELECT   p.page_id"
	, ",        p.page_namespace"
	, ",        p.page_title"
	, ",        p.page_is_redirect"
	, ",        p.page_touched"
	, ",        p.page_len"
	, ",        p.page_random_int"
	, ",        p.page_file_idx"
	, ",        p.page_redirect_id"
	, ",        {0}"
	, "FROM     page_db.page p"
	, "         JOIN wiki_page_html h ON p.page_id = h.page_id"
	);
}
