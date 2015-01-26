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
import gplx.dbs.*; import gplx.xowa.dbs.*; import gplx.xowa.dbs.tbls.*; import gplx.xowa.hdumps.dbs.*;
public class Xodb_hdump_mgr_setup {
	public static Xodb_file Hdump_db_file_init(Xodb_hdump_mgr hdump_mgr) {
		Xow_wiki wiki = hdump_mgr.Wiki();
		Xodb_mgr_sql db_mgr_as_sql = wiki.Db_mgr_as_sql();
		Xodb_file rv = db_mgr_as_sql.Fsys_mgr().Get_tid_root(Xodb_file_tid.Tid_html);
		if (rv == null) rv = Setup(db_mgr_as_sql);
		hdump_mgr.Text_tbl().Conn_(rv.Conn());
		return rv;
	}
	private static Xodb_file Setup(Xodb_mgr_sql db_mgr) {
		Xodb_fsys_mgr fsys_mgr = db_mgr.Fsys_mgr();
		Assert_col__page_html_db_id(fsys_mgr.Conn_core(), db_mgr.Tbl_xowa_cfg());
		Xodb_file html_db_file = Create_db(db_mgr, fsys_mgr);
		Create_idx(html_db_file);
		return html_db_file;
	}
	public static void Assert_col__page_html_db_id(Db_conn core_provider, Xodb_xowa_cfg_tbl cfg_tbl) {
		String val = cfg_tbl.Select_val_or(Xodb_fsys_mgr.Cfg_grp_db_meta, Cfg_itm_html_db_exists, "n");
		if (String_.Eq(val, "y")) return;
		try {
			core_provider.Exec_sql(Sql_ddl__page_html_db_id);
			cfg_tbl.Insert_str(Xodb_fsys_mgr.Cfg_grp_db_meta, Cfg_itm_html_db_exists, "y");
			cfg_tbl.Conn().Txn_mgr().Txn_end_all_bgn_if_none();
		}	catch (Exception e) {Gfo_usr_dlg_._.Warn_many("", "", "failed to update core: db=~{0} err=~{1}", core_provider.Url().Xto_raw(), Err_.Message_gplx(e));}
	}
	private static Xodb_file Create_db(Xodb_mgr_sql db_mgr, Xodb_fsys_mgr fsys_mgr) {
		Xodb_file html_db_file = fsys_mgr.Make(Xodb_file_tid.Tid_html);
		html_db_file.Conn().Exec_sql(Xodb_wiki_page_html_tbl.Tbl_sql);
		db_mgr.Tbl_xowa_db().Commit_all(fsys_mgr.Conn_core(), db_mgr.Fsys_mgr().Files_ary());
		return html_db_file;
	}
	private static void Create_idx(Xodb_file html_db_file) {
		Sqlite_engine_.Idx_create(html_db_file.Conn(), Xodb_wiki_page_html_tbl.Idx_core);
	}
	private static final String Cfg_itm_html_db_exists = "html_db.exists";
	public static final String 
	  Sql_ddl__page_html_db_id		= "ALTER TABLE page ADD COLUMN page_html_db_id integer NOT NULL DEFAULT '-1'"
	;
}
