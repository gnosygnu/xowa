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
package gplx.xowa.dbs.hdumps; import gplx.*; import gplx.xowa.*; import gplx.xowa.dbs.*;
import gplx.dbs.*; import gplx.xowa.dbs.tbls.*; import gplx.xowa.dbs.hdumps.saves.*;
public class Hdump_db_mgr {
	public Hdump_db_mgr() {
		save_mgr.Tbl_(text_tbl);
	}
	public Hdump_save_mgr Save_mgr() {return save_mgr;} private Hdump_save_mgr save_mgr = new Hdump_save_mgr();
	public Hdump_text_tbl Text_tbl() {return text_tbl;} private Hdump_text_tbl text_tbl = new Hdump_text_tbl();
	@gplx.Internal protected void Mode_mem_() {
		text_tbl = new Hdump_text_tbl_mem();
		save_mgr.Tbl_(text_tbl);
	}
	public Db_provider Db_provider_by_page(int page_id) {
		return null;
	}
}
class Hdump_db_mgr_setup {
	public static Xodb_file Setup(Xodb_mgr_sql db_mgr) {
		Xodb_fsys_mgr fsys_mgr = db_mgr.Fsys_mgr();
		Update_core(fsys_mgr);
		Xodb_file html_db_file = Create_db(db_mgr, fsys_mgr);
		Create_idx(html_db_file);
		return html_db_file;
	}
	public static void Update_core(Xodb_fsys_mgr fsys_mgr) {
		Db_provider core_provider = fsys_mgr.Provider_core();
		try {
			Xodb_xowa_cfg_tbl.Insert_str(core_provider, "db.meta", "html_db.exists", "y");
			core_provider.Exec_sql("ALTER TABLE page ADD COLUMN html_db_idx integer NOT NULL DEFAULT '-1'");
		}	catch (Exception e) {Gfo_usr_dlg_._.Warn_many("", "", "failed to update core: db=~{0} err=~{1}", core_provider.ConnectInfo().Raw_of_db_connect(), Err_.Message_gplx(e));}
	}
	public static Xodb_file Create_db(Xodb_mgr_sql db_mgr, Xodb_fsys_mgr fsys_mgr) {
		Xodb_file html_db_file = fsys_mgr.Make(Xodb_file_tid.Tid_html);
		html_db_file.Provider().Exec_sql(Hdump_text_tbl.Tbl_sql);
		db_mgr.Tbl_xowa_db().Commit_all(fsys_mgr.Provider_core(), db_mgr.Fsys_mgr().Files_ary());
		return html_db_file;
	}
	public static void Create_idx(Xodb_file html_db_file) {
		Sqlite_engine_.Idx_create(html_db_file.Provider(), Hdump_text_tbl.Idx_core);
	}
}
