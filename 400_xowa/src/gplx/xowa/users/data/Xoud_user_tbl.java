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
package gplx.xowa.users.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.dbs.*;
class Xoud_user_mgr {
	private Xoud_user_tbl tbl = new Xoud_user_tbl();
	public void Conn_(Db_conn conn, boolean created) {tbl.Conn_(conn, created);}
	public int Get_id_or_new(String name) {
		int rv = tbl.Select_id_by_name(name);
		if (rv == Int_.Min_value) {
			rv = tbl.Select_id_next();
			tbl.Insert(rv, name);
		}
		return rv;
	}
}
class Xoud_user_tbl {
	private String tbl_name = "user_user_regy"; private final Dbmeta_fld_list flds = Dbmeta_fld_list.new_();
	private String fld_id, fld_name;
	private Db_conn conn;
	public void Conn_(Db_conn new_conn, boolean created) {
		this.conn = new_conn; flds.Clear();
		fld_id					= flds.Add_int_pkey("id");
		fld_name				= flds.Add_str("name", 255);
		if (created) {
			Dbmeta_tbl_itm meta = Dbmeta_tbl_itm.New(tbl_name, flds
			, Dbmeta_idx_itm.new_unique_by_tbl(tbl_name, "name", fld_name)
			);
			conn.Ddl_create_tbl(meta);
		}
	}
	public void Insert(int id, String name) {
		Db_stmt stmt = conn.Stmt_insert(tbl_name, flds);
		stmt.Val_int(fld_id, id).Val_str(fld_name, name)
			.Exec_insert();
	}
	public int Select_id_by_name(String name) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_name).Crt_str(fld_name, name).Exec_select__rls_auto();
		try {
			return rdr.Move_next() ? rdr.Read_int(fld_id) : Int_.Min_value;
		}
		finally {rdr.Rls();}
	}
	public int Select_id_next() {
		int rv = 1;
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, Dbmeta_fld_itm.Str_ary_empty).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				int cur = rdr.Read_int(fld_id);
				if (cur >= rv) rv = cur + 1;
			}
			return rv;
		}
		finally {rdr.Rls();}
	}
}
