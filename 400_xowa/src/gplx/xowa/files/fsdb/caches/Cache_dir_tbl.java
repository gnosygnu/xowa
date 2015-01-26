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
package gplx.xowa.files.fsdb.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
import gplx.dbs.*;
class Cache_dir_tbl {
	private Db_conn conn;
	private Db_stmt select_stmt;
	private Db_stmt_bldr stmt_bldr;
	public void Db_init(Db_conn conn) {this.conn = conn;}
	public String Db_save(Cache_dir_itm itm) {
		try {
			if (stmt_bldr == null) stmt_bldr = new Db_stmt_bldr(Tbl_name, String_.Ary(Fld_dir_id), Fld_dir_name).Init(conn);
			Db_stmt stmt = stmt_bldr.Get(itm.Cmd_mode());
			switch (itm.Cmd_mode()) {
				case Db_cmd_mode.Create:	stmt.Clear().Val_int(itm.Uid()).Val_bry_as_str(itm.Dir_bry()).Exec_insert(); break;
				case Db_cmd_mode.Update:	stmt.Clear()					.Val_bry_as_str(itm.Dir_bry()).Val_int(itm.Uid()).Exec_update(); break;
				case Db_cmd_mode.Delete:	stmt.Clear().Val_int(itm.Uid()).Exec_delete();	break;
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
	public void Db_term() {
		if (select_stmt != null) select_stmt.Rls();
		if (stmt_bldr != null) stmt_bldr.Rls();
	}
	public void Db_when_new(Db_conn p) {
		Sqlite_engine_.Tbl_create(p, Tbl_name, Tbl_sql);
		Sqlite_engine_.Idx_create(p, Idx_name);
	}
	public int Select_max_uid() {return Db_conn_.Select_fld0_as_int_or(conn, "SELECT Max(uid) AS MaxId FROM cache_dir;", -1);}
	public Cache_dir_itm Select(byte[] name) {
		if (select_stmt == null) select_stmt = Db_stmt_.new_select_(conn, Tbl_name, String_.Ary(Fld_dir_name));
		DataRdr rdr = DataRdr_.Null;
		try {
			rdr = select_stmt.Clear()
			.Val_bry_as_str(name)
			.Exec_select();
			if (rdr.MoveNextPeer()) {
				return new Cache_dir_itm().Init_by_load(rdr);
			}
			else
				return Cache_dir_itm.Null;
		}
		catch (Exception e) {select_stmt = null; throw Err_.err_(e, "stmt failed");}
		finally {rdr.Rls();}
	}		
	public void Select_all(ListAdp list) {
		list.Clear();
		Db_stmt select_all_stmt = Db_stmt_.new_select_all_(conn, Tbl_name);
		DataRdr rdr = DataRdr_.Null;
		try {
			rdr = select_all_stmt.Exec_select();
			while (rdr.MoveNextPeer()) {
				Cache_dir_itm dir_itm = new Cache_dir_itm().Init_by_load(rdr);
				list.Add(dir_itm);
			}
		}
		catch (Exception e) {throw Err_.err_(e, "stmt failed");}
		finally {rdr.Rls();}
	}
	private static final String Tbl_sql = String_.Concat_lines_nl
	( "CREATE TABLE cache_dir"
	, "( dir_id            integer       NOT NULL        PRIMARY KEY"
	, ", dir_name          varchar(255)"
	, ");"
	);
	public static final String Tbl_name = "cache_dir"
	, Fld_dir_id = "dir_id", Fld_dir_name = "dir_name"
	;
	private static final Db_idx_itm
		Idx_name     		= Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS cache_dir__name ON cache_dir (dir_name);")
	;
}
