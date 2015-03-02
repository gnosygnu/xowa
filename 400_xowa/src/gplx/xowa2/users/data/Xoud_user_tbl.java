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
package gplx.xowa2.users.data; import gplx.*; import gplx.xowa2.*; import gplx.xowa2.users.*;
import gplx.dbs.*;
class Xoud_user_mgr {
	private Xoud_user_tbl tbl = new Xoud_user_tbl();
	public void Conn_(Db_conn conn, boolean created) {tbl.Conn_(conn, created);}
	public int Get_id_or_new(String name) {
		int rv = tbl.Select_id_by_name(name);
		if (rv == Int_.MinValue) {
			rv = tbl.Select_id_next();
			tbl.Insert(rv, name);
		}
		return rv;
	}
}
class Xoud_user_tbl {
	private String tbl_name = "user_user_regy"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private String fld_id, fld_name;
	private Db_conn conn;
	public void Conn_(Db_conn new_conn, boolean created) {
		this.conn = new_conn; flds.Clear();
		fld_id					= flds.Add_int_pkey("id");
		fld_name				= flds.Add_str("name", 255);
		if (created) {
			Db_meta_tbl meta = Db_meta_tbl.new_(tbl_name, flds
			, Db_meta_idx.new_unique_by_tbl(tbl_name, "name", fld_name)
			);
			conn.Exec_create_tbl_and_idx(meta);
		}
	}
	public void Insert(int id, String name) {
		Db_stmt stmt = conn.Stmt_insert(tbl_name, flds);
		stmt.Val_int(fld_id, id).Val_str(fld_name, name)
			.Exec_insert();
	}
	public int Select_id_by_name(String name) {
		Db_rdr rdr = Db_rdr_.Null;
		try {
			Db_stmt stmt = conn.Stmt_select(tbl_name, flds, fld_name);
			rdr = stmt.Crt_str(fld_name, name).Exec_select_as_rdr();
			return rdr.Move_next() ? rdr.Read_int(fld_id) : Int_.MinValue;
		}
		finally {rdr.Rls();}
	}
	public int Select_id_next() {
		Db_rdr rdr = Db_rdr_.Null;
		int rv = 1;
		try {
			Db_stmt stmt = conn.Stmt_select(tbl_name, flds, Db_meta_fld.Ary_empy);
			rdr = stmt.Exec_select_as_rdr();
			while (rdr.Move_next()) {
				int cur = rdr.Read_int(fld_id);
				if (cur >= rv) rv = cur + 1;
			}
			return rv;
		}
		finally {rdr.Rls();}
	}
}
