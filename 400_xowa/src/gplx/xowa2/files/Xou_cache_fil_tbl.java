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
class Xou_cache_fil_tbl {
	private Db_conn conn; private Db_stmt_bldr stmt_bldr;
	public void Init(Db_conn conn) {this.conn = conn;}
	public Xou_cache_fil Select_by_key(Xou_cache_mgr cache_mgr, int dir_id, byte[] fil_name, boolean fil_is_orig, int fil_w, double fil_time, int fil_page) {
		String[] flds_where = String_.Ary(Fld_dir_id, Fld_fil_name, Fld_fil_is_orig, Fld_fil_w, Fld_fil_time, Fld_fil_page);
		Db_stmt stmt = Db_stmt_.new_select_as_rdr(conn, Db_qry__select_in_tbl.new_(Tbl_name, flds_where, Flds_all));
		Db_rdr rdr = Db_rdr_.Null;
		try {
			rdr = stmt.Clear()
			.Val_int(dir_id)
			.Val_bry_as_str(fil_name)
			.Val_bool_as_byte(fil_is_orig)
			.Val_int(fil_w)
			.Val_int(Xof_doc_thumb.Db_save_int(fil_time))
			.Val_int(Xof_doc_page.Db_save_int(fil_page))
			.Exec_select_as_rdr()
			;
			return rdr.Move_next() ? Make(cache_mgr, rdr) : null;
		}
		catch (Exception e) {stmt = null; throw Db_stmt_.err_(e, Tbl_name, "Select_by_key");}
		finally {rdr.Rls();}
	}
	public String Commit(Xou_cache_fil itm) {
		try {
			if (stmt_bldr == null) stmt_bldr = new Db_stmt_bldr(Tbl_name, String_.Ary(Fld_uid), Fld_dir_id, Fld_fil_name, Fld_fil_ext, Fld_fil_is_orig, Fld_fil_w, Fld_fil_h, Fld_fil_time, Fld_fil_page, Fld_fil_size, Fld_fil_view_time).Init(conn);
			Db_stmt stmt = stmt_bldr.Get(itm.Cmd_mode());
			switch (itm.Cmd_mode()) {
				case Db_cmd_mode.Create:	stmt.Clear().Val_int(itm.Uid()); Commit_stmt_args(stmt, itm); stmt.Exec_insert(); break;
				case Db_cmd_mode.Update:	stmt.Clear();					  Commit_stmt_args(stmt, itm); stmt.Val_int(itm.Uid()).Exec_update(); break;
				case Db_cmd_mode.Delete:	stmt.Clear().Val_int(itm.Uid()); stmt.Exec_delete();	break;
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
	private void Commit_stmt_args(Db_stmt stmt, Xou_cache_fil itm) {
		stmt.Val_int(itm.Dir_id())
			.Val_bry_as_str(itm.Fil_name())
			.Val_int(itm.Fil_ext())
			.Val_bool_as_byte(itm.Fil_is_orig())
			.Val_int(itm.Fil_w())
			.Val_int(itm.Fil_h())
			.Val_int(Xof_doc_thumb.Db_save_int(itm.Fil_time()))
			.Val_int(Xof_doc_page.Db_save_int(itm.Fil_page()))
			.Val_long(itm.Fil_size())
			.Val_long(itm.Fil_view_time())
			;
	}
	private Xou_cache_fil Make(Xou_cache_mgr cache_mgr, Db_rdr rdr) {
		int dir_id = rdr.Read_int(Ord_dir_id);
		return new Xou_cache_fil
		( rdr.Read_int(Ord_uid)
		, dir_id
		, cache_mgr.Dir__get_by_id(dir_id)
		, rdr.Read_bry_by_str(Ord_fil_name)
		, rdr.Read_int(Ord_fil_ext)
		, rdr.Read_bool_by_byte(Ord_fil_is_orig)
		, rdr.Read_int(Ord_fil_w)
		, rdr.Read_int(Ord_fil_h)
		, Xof_doc_thumb.Db_load_int(rdr, Ord_fil_time)
		, Xof_doc_page.Db_load_int(rdr, Ord_fil_page)
		, rdr.Read_long(Ord_fil_size)
		, rdr.Read_long(Ord_fil_view_time)
		);
	}
//		private static final String Tbl_sql = String_.Concat_lines_nl
//		( "CREATE TABLE user_cache_fil"
//		, "( uid               integer       NOT NULL        PRIMARY KEY       AUTOINCREMENT"
//		, ", dir_id            integer       NOT NULL"
//		, ", fil_name          varchar(255)  NOT NULL"
//		, ", fil_ext           integer       NOT NULL"
//		, ", fil_is_orig       tinyint       NOT NULL"
//		, ", fil_w             integer       NOT NULL"
//		, ", fil_h             integer       NOT NULL"
//		, ", fil_time          integer       NOT NULL"
//		, ", fil_page          integer       NOT NULL"
//		, ", fil_size          bigint        NOT NULL"
//		, ", fil_view_time     bigint        NOT NULL"
//		, ");"
//		);
//		private static final Db_idx_itm
//		  Idx_key = Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS user_cache_fil__key ON user_cache_fil (dir_id, file_name, fil_is_orig, fil_w, fil_time, fil_page);")
//		;
	public static final String Tbl_name = "user_cache_fil"
	, Fld_uid = "uid", Fld_dir_id = "dir_id", Fld_fil_name = "fil_name", Fld_fil_ext = "fil_ext", Fld_fil_is_orig = "fil_is_orig"
	, Fld_fil_w = "fil_w", Fld_fil_h = "fil_h", Fld_fil_time = "fil_time", Fld_fil_page = "fil_page"
	, Fld_fil_size = "fil_size", Fld_fil_view_time = "fil_view_time"
	;
	private static final String[] Flds_all = new String[] {Fld_uid, Fld_dir_id, Fld_fil_name, Fld_fil_ext, Fld_fil_is_orig, Fld_fil_w, Fld_fil_h, Fld_fil_time, Fld_fil_page, Fld_fil_size, Fld_fil_view_time};
	private static final int Ord_uid = 0, Ord_dir_id = 1, Ord_fil_name = 2, Ord_fil_ext = 3, Ord_fil_is_orig = 4, Ord_fil_w = 5, Ord_fil_h = 5
	, Ord_fil_time = 6, Ord_fil_page = 7, Ord_fil_size = 9, Ord_fil_view_time = 10
	;
}
