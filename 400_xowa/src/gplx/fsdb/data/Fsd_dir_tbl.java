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
public class Fsd_dir_tbl implements Rls_able {
	private final String tbl_name = "fsdb_dir"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private final String fld_id, fld_owner_id, fld_name;		
	private final Db_conn conn; private Db_stmt stmt_insert, stmt_update, stmt_select_by_name;		
	public Fsd_dir_tbl(Db_conn conn, boolean schema_is_1) {
		this.conn = conn;
		this.fld_id				= flds.Add_int_pkey	("dir_id");
		this.fld_owner_id		= flds.Add_int		("dir_owner_id");
		this.fld_name			= flds.Add_str		("dir_name", 255);
		conn.Rls_reg(this);
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_update = Db_stmt_.Rls(stmt_update);
		stmt_select_by_name = Db_stmt_.Rls(stmt_select_by_name);
	}
	public void Create_tbl() {
		conn.Ddl_create_tbl
		( Db_meta_tbl.new_(tbl_name, flds
		, Db_meta_idx.new_normal_by_tbl(tbl_name, "name", fld_name, fld_owner_id, fld_id)));
	}
	public void Insert(int id, byte[] name, int owner_id) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
			.Val_int(fld_id, id)
			.Val_int(fld_owner_id, owner_id)
			.Val_bry_as_str(fld_name, name)
			.Exec_insert();
	}	
	public void Update(int id, byte[] name, int owner_id) {
		if (stmt_update == null) stmt_update = conn.Stmt_update_exclude(tbl_name, flds, fld_id);
		stmt_update.Clear()
			.Val_int(fld_owner_id, owner_id)
			.Val_bry_as_str(fld_name, name)
			.Crt_int(fld_id, id)
			.Exec_update();
	}
	public Fsd_dir_itm Select_or_null(byte[] name) {
		if (stmt_select_by_name == null) stmt_select_by_name = conn.Stmt_select(tbl_name, flds, fld_name);
		Db_rdr rdr = stmt_select_by_name.Clear().Crt_bry_as_str(fld_name, name).Exec_select__rls_manual();
		try {
			return rdr.Move_next()
				? new Fsd_dir_itm(rdr.Read_int(fld_id), rdr.Read_int(fld_owner_id), name)
				: Fsd_dir_itm.Null
			;
		}
		finally {rdr.Rls();}
	}
}
