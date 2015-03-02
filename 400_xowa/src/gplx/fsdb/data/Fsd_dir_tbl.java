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
package gplx.fsdb.data; import gplx.*; import gplx.fsdb.*;
import gplx.dbs.*;
public class Fsd_dir_tbl {
	private String tbl_name = "file_data_dir"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private String fld_id, fld_owner_id, fld_name;		
	private Db_conn conn; private Db_stmt stmt_insert, stmt_update, stmt_select_by_name;		
	public void Conn_(Db_conn new_conn, boolean created, boolean schema_is_1) {
		this.conn = new_conn; flds.Clear();
		String fld_prefix = "";
		if (schema_is_1) {
			tbl_name			= "fsdb_dir";
			fld_prefix			= "dir_";
		}
		fld_id				= flds.Add_int(fld_prefix + "id");
		fld_owner_id		= flds.Add_int(fld_prefix + "owner_id");
		fld_name			= flds.Add_str(fld_prefix + "name", 255);
		if (created) {
			Db_meta_tbl meta = Db_meta_tbl.new_(tbl_name, flds
			, Db_meta_idx.new_unique_by_tbl(tbl_name, "pkey", fld_id)
			, Db_meta_idx.new_normal_by_tbl(tbl_name, "name", fld_name, fld_owner_id, fld_id)
			);
			conn.Exec_create_tbl_and_idx(meta);
		}
		stmt_insert = stmt_update = stmt_select_by_name = null;
	}
	public void Insert(int id, String name, int owner_id) {
		if (stmt_insert == null) stmt_insert = conn.Rls_reg(conn.Stmt_insert(tbl_name, flds));
		stmt_insert.Clear()
			.Val_int(fld_id, id)
			.Val_int(fld_owner_id, owner_id)
			.Val_str(fld_name, name)
			.Exec_insert();
	}	
	public void Update(int id, String name, int owner_id) {
		if (stmt_update == null) stmt_update = conn.Rls_reg(conn.Stmt_update_exclude(tbl_name, flds, fld_id));
		stmt_update.Clear()
			.Val_int(fld_owner_id, owner_id)
			.Val_str(fld_name, name)
			.Crt_int(fld_id, id)
			.Exec_update();
	}
	public Fsd_dir_itm Select_itm(String name) {
		if (stmt_select_by_name == null) stmt_select_by_name = conn.Rls_reg(conn.Stmt_select(tbl_name, flds, fld_name));
		Db_rdr rdr = Db_rdr_.Null;
		try {
			rdr = stmt_select_by_name.Clear().Crt_str(fld_name, name).Exec_select_as_rdr();
			return rdr.Move_next()
				? new Fsd_dir_itm(rdr.Read_int(fld_id), rdr.Read_int(fld_owner_id), name)
				: Fsd_dir_itm.Null
			;
		}
		finally {rdr.Rls();}
	}
}
