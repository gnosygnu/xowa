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
package gplx.fsdb; import gplx.*;
import gplx.dbs.*;
public class Fsdb_dir_tbl {
	private Db_conn conn;
	private Db_stmt stmt_insert, stmt_update, stmt_select_by_name;
	public Fsdb_dir_tbl(Db_conn conn, boolean created) {
		this.conn = conn;
		if (created) Create_table();
	}
	public void Rls() {
		if (stmt_insert != null) {stmt_insert.Rls(); stmt_insert = null;}
		if (stmt_update != null) {stmt_update.Rls(); stmt_update = null;}
		if (stmt_select_by_name != null) {stmt_select_by_name.Rls(); stmt_select_by_name = null;}
	}
	public void Create_table() {
		Sqlite_engine_.Tbl_create(conn, Tbl_name, Tbl_sql);
		Sqlite_engine_.Idx_create(conn, Idx_name);
	}
	private Db_stmt Insert_stmt() {return Db_stmt_.new_insert_(conn, Tbl_name, Fld_dir_id, Fld_dir_owner_id, Fld_dir_name);}
	public void Insert(int id, String name, int owner_id) {
		if (stmt_insert == null) stmt_insert = Insert_stmt();
		try {
			stmt_insert.Clear()
			.Val_int(id)
			.Val_int(owner_id)
			.Val_str(name)
			.Exec_insert();
		}	catch (Exception exc) {stmt_insert = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}	
	private Db_stmt Update_stmt() {return Db_stmt_.new_update_(conn, Tbl_name, String_.Ary(Fld_dir_id), Fld_dir_owner_id, Fld_dir_name);}
	public void Update(int id, String name, int owner_id) {
		if (stmt_update == null) stmt_update = Update_stmt();
		try {
			stmt_update.Clear()
			.Val_int(id)
			.Val_str(name)
			.Val_int(owner_id)
			.Exec_update();
		}	catch (Exception exc) {stmt_update = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
	}
	private Db_stmt Select_itm_stmt() {
		Db_qry qry = Db_qry_.select_().From_(Tbl_name).Cols_(Fld_dir_id, Fld_dir_owner_id).Where_(Db_crt_.eq_(Fld_dir_name, Bry_.Empty));
		return conn.New_stmt(qry);
	}
	public Fsdb_dir_itm Select_itm(String dir_name) {
		if (stmt_select_by_name == null) stmt_select_by_name = Select_itm_stmt();
		DataRdr rdr = DataRdr_.Null;
		try {
			rdr = stmt_select_by_name.Clear()
				.Val_str(dir_name)
				.Exec_select();
			while (rdr.MoveNextPeer()) {
				return new Fsdb_dir_itm(rdr.ReadInt(Fld_dir_id), rdr.ReadInt(Fld_dir_owner_id), dir_name);
			}
			return Fsdb_dir_itm.Null;
		}
		finally {rdr.Rls();}
	}
	public static final String Tbl_name = "fsdb_dir", Fld_dir_id = "dir_id", Fld_dir_owner_id = "dir_owner_id", Fld_dir_name = "dir_name";
	private static final String Tbl_sql = String_.Concat_lines_nl
	(	"CREATE TABLE IF NOT EXISTS fsdb_dir"
	,	"( dir_id            integer             NOT NULL    PRIMARY KEY"
	,	", dir_owner_id      integer             NOT NULL"
	,	", dir_name          varchar(255)        NOT NULL"
	,	");"
	);
	public static final Db_idx_itm
	  Idx_name = Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS fsdb_dir__name       ON fsdb_dir (dir_name, dir_owner_id, dir_id);")
	;
}
