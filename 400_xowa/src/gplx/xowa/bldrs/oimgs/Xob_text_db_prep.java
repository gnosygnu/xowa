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
package gplx.xowa.bldrs.oimgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.dbs.*; import gplx.dbs.engines.sqlite.*; import gplx.xowa.dbs.*;
public class Xob_text_db_prep extends Xob_itm_basic_base implements Xob_cmd {
	private Xodb_file[] db_files;
	public Xob_text_db_prep(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return KEY_oimg;} public static final String KEY_oimg = "wiki.text_db_prep";
	public void Cmd_ini(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {
		wiki.Init_assert();
	}
	public void Cmd_run() {
		Xodb_fsys_mgr db_fsys_mgr = wiki.Db_mgr_as_sql().Fsys_mgr();
		String page_db_url = db_fsys_mgr.Get_tid_root(Xodb_file_tid.Tid_core).Url().Raw();
		db_files = db_fsys_mgr.Files_ary();
		int len = db_files.length;
		for (int i = 0; i < len; i++) {
			Xodb_file db_file = db_files[i];
			if (db_file.Tid() == Xodb_file_tid.Tid_text)
				Prep_db(page_db_url, db_file);
		}
	}
	public void Cmd_end() {
		int len = db_files.length;
		for (int i = 0; i < len; i++) {
			Xodb_file db_file = db_files[i];
			if (db_file.Tid() == Xodb_file_tid.Tid_text)
				db_file.Rls();
		}
		db_files = null;
	}
	public void Cmd_print() {}
	private void Prep_db(String page_db_url, Xodb_file text_db) {
		usr_dlg.Note_many("", "", "copying page_rows to text_db: ~{0}", text_db.Url().NameOnly());
		Db_conn conn = text_db.Conn();
		Sqlite_engine_.Tbl_create_and_delete(conn, "page_dump", Sql_create_tbl);
		Sqlite_engine_.Db_attach(conn, "page_db", page_db_url);
		conn.Txn_mgr().Txn_bgn_if_none();
		conn.Exec_sql(String_.Format(Sql_insert_data, text_db.Id()));
		conn.Txn_mgr().Txn_end_all();
		Sqlite_engine_.Idx_create(conn, Idx_create);
	}
	private static final String Sql_create_tbl = String_.Concat_lines_nl
	( "CREATE TABLE IF NOT EXISTS page_dump"
	, "(    page_id             integer"
	, ",    page_title          varchar(256)"
	, ",    page_namespace      integer"
	, ",    page_is_redirect    integer"
	, ");"
	)
	, Sql_insert_data = String_.Concat_lines_nl
	( "INSERT INTO page_dump (page_id, page_title, page_namespace, page_is_redirect)"
	, "SELECT  p.page_id"
	, ",       p.page_title"
	, ",       p.page_namespace"
	, ",       p.page_is_redirect"
	, "FROM    page_db.page p"
	, "WHERE   p.page_file_idx = {0}"
	, ";"
	)
	;
	private static final Db_idx_itm Idx_create = Db_idx_itm.sql_
	( "CREATE UNIQUE INDEX page_dump_index ON page_dump (page_id, page_namespace, page_is_redirect, page_title);"
	);
}
