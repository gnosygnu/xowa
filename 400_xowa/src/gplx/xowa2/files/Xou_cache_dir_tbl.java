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
package gplx.xowa2.files; import gplx.*; import gplx.xowa2.*;
import gplx.dbs.*; import gplx.xowa.files.*;
class Xou_cache_dir_tbl {
	private Db_conn conn; private Db_stmt_bldr stmt_bldr;
	public void Init(Db_conn conn) {this.conn = conn;}
	public void Select_all(Xou_cache_mgr cache_mgr) {
		Db_stmt stmt = Db_stmt_.new_select_as_rdr(conn, Db_qry__select_in_tbl.new_(Tbl_name, String_.Ary_empty, Flds_all));
		Db_rdr rdr = Db_rdr_.Null;
		try {
			rdr = stmt.Clear().Exec_select_as_rdr();
			while (rdr.Move_next()) {
				Xou_cache_dir dir = Make(rdr);
				cache_mgr.Dir__add(dir);
			}
		}
		catch (Exception e) {stmt = null; throw Db_stmt_.err_(e, Tbl_name, "Select_by_key");}
		finally {rdr.Rls();}
	}
	public String Commit(Xou_cache_dir itm) {
		try {
			if (stmt_bldr == null) stmt_bldr = new Db_stmt_bldr(Tbl_name, String_.Ary(Fld_dir_id), Fld_dir_name).Init(conn);
			Db_stmt stmt = stmt_bldr.Get(itm.Cmd_mode());
			switch (itm.Cmd_mode()) {
				case Db_cmd_mode.Create:	stmt.Clear().Val_int(itm.Dir_id()); Commit_stmt_args(stmt, itm); stmt.Exec_insert(); break;
				case Db_cmd_mode.Update:	stmt.Clear();						 Commit_stmt_args(stmt, itm); stmt.Val_int(itm.Dir_id()).Exec_update(); break;
				case Db_cmd_mode.Delete:	stmt.Clear().Val_int(itm.Dir_id()); stmt.Exec_delete();	break;
				case Db_cmd_mode.Ignore:	break;
				default:					throw Err_.unhandled(itm.Cmd_mode());
			}
			itm.Cmd_mode_(Db_cmd_mode.Ignore);
			return null;
		} catch (Exception e) {
			stmt_bldr = null;	// null out bldr, else bad stmt will lead to other failures
			return Err_.Message_gplx_brief(e);
		}
	}
	private void Commit_stmt_args(Db_stmt stmt, Xou_cache_dir itm) {
		stmt.Val_bry_as_str(itm.Dir_name());
	}
	private Xou_cache_dir Make(Db_rdr rdr) {
		return new Xou_cache_dir
		( rdr.Read_int(Ord_dir_id)
		, rdr.Read_bry_by_str(Ord_dir_name)
		);
	}
//		private static final String Tbl_sql = String_.Concat_lines_nl
//		( "CREATE TABLE user_cache_dir"
//		, "( dir_id            integer       NOT NULL        PRIMARY KEY       AUTOINCREMENT"
//		, ", dir_name          varchar(255)  NOT NULL"
//		, ");"
//		);
	public static final String Tbl_name = "user_cache_dir", Fld_dir_id = "dir_id", Fld_dir_name = "fil_name";
	private static final String[] Flds_all = new String[] {Fld_dir_id, Fld_dir_name};
	private static final int Ord_dir_id = 0, Ord_dir_name = 1;
}
