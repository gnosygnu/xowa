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
package gplx.xowa.users.bmks; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.dbs.*; import gplx.dbs.qrys.*;
public class Xoud_bmk_dir_tbl implements Rls_able {
	private final String tbl_name = "bmk_dir"; private final Dbmeta_fld_list flds = Dbmeta_fld_list.new_();
	private final String fld_id, fld_owner, fld_sort, fld_name;
	public Xoud_bmk_dir_tbl(Db_conn conn) {
		this.conn = conn;
		fld_id						= flds.Add_int_pkey_autonum("dir_id");
		fld_owner					= flds.Add_int("dir_owner");
		fld_sort					= flds.Add_int("dir_sort");
		fld_name					= flds.Add_str("dir_name", 255);
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;} private final Db_conn conn;
	public String Tbl_name() {return tbl_name;}
	public void Create_tbl() {conn.Ddl_create_tbl(Dbmeta_tbl_itm.New(tbl_name, flds.To_fld_ary()));}
	public void Insert(int owner, int sort, byte[] name) {
		Db_stmt stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
			.Val_int(fld_owner, owner).Val_int(fld_sort, sort).Val_bry_as_str(fld_name, name)
			.Exec_insert();
	}
	public void Update(int id, int owner, int sort, byte[] name) {
		Db_stmt stmt_update = conn.Stmt_update_exclude(tbl_name, flds, fld_id);
		stmt_update.Clear()
			.Val_int(fld_owner, owner).Val_int(fld_sort, sort).Val_bry_as_str(fld_name, name)
			.Crt_int(fld_id, id)
			.Exec_update();
	}
	public void Delete(int id) {
		Db_stmt stmt_delete = conn.Stmt_delete(tbl_name, fld_id);
		stmt_delete.Clear().Crt_int(fld_id, id).Exec_delete();
	}
//		private Xoud_bmk_dir_row new_row(Db_rdr rdr) {
//			return new Xoud_bmk_dir_row
//			( rdr.Read_int(fld_id)
//			, rdr.Read_int(fld_owner)
//			, rdr.Read_int(fld_sort)
//			, rdr.Read_bry_by_str(fld_name)
//			);
//		}
	public void Rls() {}
}
