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
public class Fsd_fil_tbl implements RlsAble {
	private final String tbl_name = "fsdb_fil"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private final String fld_id, fld_owner_id, fld_name, fld_xtn_id, fld_ext_id, fld_size, fld_modified, fld_hash, fld_bin_db_id;
	private final String idx_owner;		
	private Db_conn conn; private Db_stmt stmt_insert, stmt_update, stmt_select_by_name; private int mnt_id;
	public Fsd_fil_tbl(Db_conn conn, boolean schema_is_1, int mnt_id) {
		this.conn = conn; this.mnt_id = mnt_id;
		this.fld_id					= flds.Add_int_pkey	("fil_id");
		this.fld_owner_id			= flds.Add_int		("fil_owner_id");
		this.fld_xtn_id				= flds.Add_int		("fil_xtn_id");
		this.fld_ext_id				= flds.Add_int		("fil_ext_id");
		this.fld_bin_db_id			= flds.Add_int		("fil_bin_db_id");		// group ints at beginning of table
		this.fld_name				= flds.Add_str		("fil_name", 255);
		this.fld_size				= flds.Add_long		("fil_size");
		this.fld_modified			= flds.Add_str		("fil_modified", 14);	// stored as yyyyMMddHHmmss
		this.fld_hash				= flds.Add_str		("fil_hash", 40);
		this.idx_owner				= Db_meta_idx.Bld_idx_name(tbl_name, "owner");
		conn.Rls_reg(this);
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_update = Db_stmt_.Rls(stmt_update);
		stmt_select_by_name = Db_stmt_.Rls(stmt_select_by_name);
	}
	public void Create_tbl() {
		conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds
		, Db_meta_idx.new_unique_by_name(tbl_name, idx_owner, fld_owner_id, fld_name, fld_id)
		));
	}
	public void Insert(int id, int owner_id, byte[] name, int xtn_id, int ext_id, long size, int bin_db_id) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
		.Val_int(fld_id, id)
		.Val_int(fld_owner_id, owner_id)
		.Val_int(fld_xtn_id, xtn_id)
		.Val_int(fld_ext_id, ext_id)
		.Val_int(fld_bin_db_id, bin_db_id)
		.Val_bry_as_str(fld_name, name)
		.Val_long(fld_size, size)
		.Val_str(fld_modified, String_.Empty)
		.Val_str(fld_hash, String_.Empty)
		.Exec_insert();
	}	
	public void Update(int id, int owner_id, byte[] name, int xtn_id, int ext_id, long size, int bin_db_id) {
		if (stmt_update == null) stmt_update = conn.Stmt_update_exclude(tbl_name, flds, fld_id);
		stmt_update.Clear()
		.Val_int(fld_owner_id, owner_id)
		.Val_int(fld_xtn_id, xtn_id)
		.Val_int(fld_ext_id, ext_id)
		.Val_int(fld_bin_db_id, bin_db_id)
		.Val_bry_as_str(fld_name, name)
		.Val_long(fld_size, size)
		.Val_str(fld_modified, String_.Empty)
		.Val_str(fld_hash, String_.Empty)
		.Crt_int(fld_id, id)
		.Exec_update();
	}	
	public Fsd_fil_itm Select_or_null(int dir_id, byte[] fil_name) {
		if (stmt_select_by_name == null) {
			Db_qry__select_cmd qry = Db_qry__select_cmd.new_().From_(tbl_name).Cols_(flds.To_str_ary()).Where_(Db_crt_.eq_many_(fld_owner_id, fld_name)).Indexed_by_(idx_owner);
			stmt_select_by_name = conn.Stmt_new(qry);
		}
		Db_rdr rdr = stmt_select_by_name.Clear()
				.Crt_int(fld_owner_id, dir_id)
				.Crt_bry_as_str(fld_name, fil_name)
				.Exec_select__rls_manual();
		try {
			return rdr.Move_next() ? new_(mnt_id, rdr) : Fsd_fil_itm.Null;
		}
		finally {rdr.Rls();}
	}
	public void Select_all(Bry_bfr key_bfr, gplx.core.caches.Gfo_cache_mgr_bry cache) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, Db_meta_fld.Ary_empty).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				Fsd_fil_itm fil = new_(mnt_id, rdr);
				byte[] cache_key = Fsd_fil_itm.Gen_cache_key(key_bfr, fil.Dir_id(), fil.Name());
				cache.Add(cache_key, fil);
			}
		}
		finally {rdr.Rls();}
	}
	private Fsd_fil_itm new_(int mnt_id, Db_rdr rdr) {
		return new Fsd_fil_itm().Ctor(mnt_id, rdr.Read_int(fld_owner_id), rdr.Read_int(fld_id), rdr.Read_int(fld_bin_db_id), rdr.Read_bry_by_str(fld_name), rdr.Read_int(fld_ext_id));
	}
}
