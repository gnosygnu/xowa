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
import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.dbs.engines.sqlite.*;
public class Fsd_fil_tbl {
	private String tbl_name = "file_data_fil"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private String fld_id, fld_owner_id, fld_name, fld_xtn_id, fld_ext_id, fld_size, fld_modified, fld_hash, fld_bin_db_id;
	private String Idx_owner;		
	private Db_conn conn; private Db_stmt stmt_insert, stmt_update, stmt_select_by_name;		
	public void Conn_(Db_conn new_conn, boolean created, boolean schema_is_1) {
		this.conn = new_conn; flds.Clear();
		String fld_prefix = "";
		if (schema_is_1) {
			tbl_name			= "fsdb_fil";
			fld_prefix			= "fil_";
		}
		fld_id					= flds.Add_int(fld_prefix + "id");
		fld_owner_id			= flds.Add_int(fld_prefix + "owner_id");
		fld_xtn_id				= flds.Add_int(fld_prefix + "xtn_id");
		fld_ext_id				= flds.Add_int(fld_prefix + "ext_id");
		fld_bin_db_id			= flds.Add_int(fld_prefix + "bin_db_id");		// group ints at beginning of table
		fld_name				= flds.Add_str(fld_prefix + "name", 255);
		fld_size				= flds.Add_long(fld_prefix + "size");
		fld_modified			= flds.Add_str(fld_prefix + "modified", 14);	// stored as yyyyMMddHHmmss
		fld_hash				= flds.Add_str(fld_prefix + "hash", 40);
		Idx_owner				= Db_meta_idx.Bld_idx_name(tbl_name, "owner");
		if (created) {
			Db_meta_tbl meta = Db_meta_tbl.new_(tbl_name, flds
			, Db_meta_idx.new_unique_by_tbl(tbl_name, "pkey", fld_id)
			, Db_meta_idx.new_unique_by_name(tbl_name, Idx_owner, fld_owner_id, fld_name, fld_id)
			);
			conn.Exec_create_tbl_and_idx(meta);
		}
		stmt_insert = stmt_update = stmt_select_by_name = null;			
	}
	public void Insert(int id, int owner_id, String name, int xtn_id, int ext_id, long size, DateAdp modified, String hash, int bin_db_id) {
		if (stmt_insert == null) stmt_insert = conn.Rls_reg(conn.Stmt_insert(tbl_name, flds));
		stmt_insert.Clear()
		.Val_int(fld_id, id)
		.Val_int(fld_owner_id, owner_id)
		.Val_int(fld_xtn_id, xtn_id)
		.Val_int(fld_ext_id, ext_id)
		.Val_int(fld_bin_db_id, bin_db_id)
		.Val_str(fld_name, name)
		.Val_long(fld_size, size)
		.Val_str(fld_modified, Sqlite_engine_.X_date_to_str(modified))
		.Val_str(fld_hash, hash)
		.Exec_insert();
	}	
	public void Update(int id, int owner_id, String name, int xtn_id, int ext_id, long size, DateAdp modified, String hash, int bin_db_id) {
		if (stmt_update == null) stmt_update = conn.Rls_reg(conn.Stmt_update_exclude(tbl_name, flds, fld_id));
		stmt_update.Clear()
		.Val_int(fld_owner_id, owner_id)
		.Val_int(fld_xtn_id, xtn_id)
		.Val_int(fld_ext_id, ext_id)
		.Val_int(fld_bin_db_id, bin_db_id)
		.Val_str(fld_name, name)
		.Val_long(fld_size, size)
		.Val_str(fld_modified, Sqlite_engine_.X_date_to_str(modified))
		.Val_str(fld_hash, hash)
		.Crt_int(fld_id, id)
		.Exec_update();
	}	
	public Fsd_fil_itm Select_itm_by_name(int dir_id, String fil_name) {
		if (stmt_select_by_name == null) {
			Db_qry__select_cmd qry = Db_qry__select_cmd.new_().From_(tbl_name).Cols_(flds.To_str_ary()).Where_(Db_crt_.eq_many_(fld_owner_id, fld_name)).Indexed_by_(Idx_owner);
			stmt_select_by_name = conn.Rls_reg(conn.Stmt_new(qry));
		}
		Db_rdr rdr = Db_rdr_.Null;
		try {
			rdr = stmt_select_by_name.Clear()
				.Crt_int(fld_owner_id, dir_id)
				.Crt_str(fld_name, fil_name)
				.Exec_select_as_rdr();
			return rdr.Move_next()
				? new Fsd_fil_itm().Init(rdr.Read_int(fld_id), rdr.Read_int(fld_owner_id), rdr.Read_int(fld_ext_id), rdr.Read_str(fld_name), rdr.Read_int(fld_bin_db_id))
				: Fsd_fil_itm.Null;
		}
		finally {rdr.Rls();}
	}
}
