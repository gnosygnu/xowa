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
import gplx.dbs.*; import gplx.xowa.dbs.*; import gplx.xowa.dbs.tbls.*; import gplx.xowa.hdumps.saves.*; import gplx.xowa.hdumps.dbs.*;
public class Xob_hdump_bldr {
	private Bry_bfr tmp_bfr = Bry_bfr.reset_(Io_mgr.Len_mb);
	private Xow_wiki wiki; private Xodb_file core_db; private Xodb_xowa_cfg_tbl cfg_tbl; private Db_stmt cfg_update_stmt;
	private int hdump_db_id; private long hdump_db_size, hdump_db_max; private Db_provider hdump_db_provider;
	private Xodb_hdump_mgr hdump_mgr; private Hdump_save_mgr hdump_save_mgr;
	public Xob_hdump_bldr(Xow_wiki wiki, Db_provider make_provider, long hdump_db_max) {
		this.wiki = wiki; this.hdump_db_max = hdump_db_max;
		core_db = wiki.Db_mgr_as_sql().Fsys_mgr().Get_tid_root(Xodb_file_tid.Tid_core);
		hdump_mgr = wiki.Db_mgr_as_sql().Hdump_mgr(); hdump_save_mgr = hdump_mgr.Save_mgr();
		cfg_tbl = new Xodb_xowa_cfg_tbl().Provider_(make_provider); cfg_update_stmt = cfg_tbl.Update_stmt();
		Xodb_hdump_mgr_setup.Assert_col__page_html_db_id(core_db.Provider(), cfg_tbl);
		Init_hdump_db(Db_get_last_or_make(wiki), cfg_tbl.Select_val_as_long_or(Cfg_grp_hdump_make, Cfg_itm_hdump_size, 0));
		hdump_db_provider.Txn_mgr().Txn_bgn_if_none();
	}
	public void Insert_page(Xoa_page page) {
		hdump_mgr.Write(tmp_bfr, page);
		int body_len = hdump_save_mgr.Insert_body(page, page.Revision_data().Id());
		hdump_db_size += body_len;
		if (hdump_db_size > hdump_db_max) {
			Db_term(core_db, hdump_db_provider, hdump_db_id);
			Init_hdump_db(wiki.Db_mgr_as_sql().Fsys_mgr().Make(Xodb_file_tid.Tid_html), 0);
			Db_init(hdump_db_provider);
		}
	}
	public void Commit() {
		Xodb_mgr_sql db_mgr = wiki.Db_mgr_as_sql(); db_mgr.Tbl_xowa_db().Commit_all(db_mgr.Fsys_mgr());	// commit new html_dbs
		hdump_db_provider.Txn_mgr().Txn_end_all_bgn_if_none();
		cfg_tbl.Update(cfg_update_stmt, Cfg_grp_hdump_make, Cfg_itm_hdump_size, Long_.Xto_str(hdump_db_size));
	}
	public void Bld_term() {
		this.Commit();
		Db_term(core_db, hdump_db_provider, hdump_db_id);
	}
	private void Init_hdump_db(Xodb_file db_file, long hdump_db_size) {
		this.hdump_db_id = db_file.Id();
		this.hdump_db_provider = db_file.Provider();
		this.hdump_db_size = hdump_db_size;
		this.hdump_save_mgr.Tbl().Provider_(hdump_db_provider);
	}
	private static Xodb_file Db_get_last_or_make(Xow_wiki wiki) {
		Xodb_fsys_mgr fsys_mgr = wiki.Db_mgr_as_sql().Fsys_mgr();
		Xodb_file rv = fsys_mgr.Get_tid_root(Xodb_file_tid.Tid_html);
		if (rv == null) {
			rv = fsys_mgr.Make(Xodb_file_tid.Tid_html);
			Db_init(rv.Provider());
		}
		return rv;
	}
	private static void Db_init(Db_provider p) {p.Exec_sql(Hdump_text_tbl.Tbl_sql);}
	private static void Db_term(Xodb_file core_db_file, Db_provider hdump_db_provider, int hdump_db_id) {
		hdump_db_provider.Txn_mgr().Txn_end();
		Sqlite_engine_.Idx_create(hdump_db_provider, Hdump_text_tbl.Idx_core);
		Sqlite_engine_.Db_attach(hdump_db_provider, "page_db", core_db_file.Url().Raw());
		hdump_db_provider.Exec_sql(String_.Format(Sql_update_page, hdump_db_id));	// update all page_html_db_id entries in page_db
		Sqlite_engine_.Db_detach(hdump_db_provider, "page_db");
		hdump_db_provider.Txn_mgr().Txn_end_all();
	}
	private static final String Cfg_grp_hdump_make = "hdump.make", Cfg_itm_hdump_size = "hdump.size";
	private static final String Sql_update_page = String_.Concat_lines_nl_skip_last
	( "REPLACE INTO page_db.page"
	, "SELECT   p.page_id"
	, ",        p.page_namespace"
	, ",        p.page_title"
	, ",        p.page_is_redirect"
	, ",        p.page_touched"
	, ",        p.page_len"
	, ",        p.page_random_int"
	, ",        p.page_file_idx"
	, ",        {0}"
	, "FROM     page_db.page p"
	, "         JOIN html_text h ON p.page_id = h.page_id"
	);
}
