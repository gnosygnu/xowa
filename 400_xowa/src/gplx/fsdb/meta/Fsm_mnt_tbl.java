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
package gplx.fsdb.meta; import gplx.*; import gplx.fsdb.*;
import gplx.dbs.*;
public class Fsm_mnt_tbl {
	private String tbl_name = "file_meta_mnt"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private String fld_id, fld_name, fld_url;		
	private Db_conn conn;
	public void Conn_(Db_conn new_conn, boolean created, boolean version_is_1) {
		this.conn = new_conn; flds.Clear();
		String fld_prefix = "";
		if (version_is_1) {
			tbl_name		= "fsdb_mnt";
			fld_prefix		= "mnt_";
		}
		fld_id				= flds.Add_int(fld_prefix + "id");
		fld_name			= flds.Add_str(fld_prefix + "name", 255);
		fld_url				= flds.Add_str(fld_prefix + "url", 255);
		if (created) {
			Db_meta_tbl meta = Db_meta_tbl.new_(tbl_name, flds
			, Db_meta_idx.new_unique_by_tbl(tbl_name, "pkey", fld_id)
			);
			conn.Exec_create_tbl_and_idx(meta);
			this.Insert(Fsm_mnt_mgr.Mnt_idx_main, "fsdb.main", "fsdb.main");
			this.Insert(Fsm_mnt_mgr.Mnt_idx_user, "fsdb.user", "fsdb.user");
		}
	}
	public void Insert(int id, String name, String url) {
		Db_stmt stmt = conn.Stmt_insert(tbl_name, flds);
		stmt.Clear().Val_int(fld_id, id).Val_str(fld_name, name).Val_str(fld_url, url).Exec_insert();
	}
	public void Update(int id, String name, String url) {
		Db_stmt stmt = conn.Stmt_update_exclude(tbl_name, flds, fld_id);
		stmt.Clear().Val_str(fld_name, name).Val_str(fld_url, url).Crt_int(fld_id, id).Exec_update();
	}	
	public Fsm_mnt_itm[] Select_all() {
		Db_stmt stmt = conn.Stmt_select(tbl_name, flds, Db_meta_fld.Ary_empy);
		Db_rdr rdr = Db_rdr_.Null;
		ListAdp list = ListAdp_.new_();
		try {
			rdr = stmt.Clear().Exec_select_as_rdr();
			while (rdr.Move_next()) {
				Fsm_mnt_itm itm = new Fsm_mnt_itm(rdr.Read_int(fld_id), rdr.Read_str(fld_name), rdr.Read_str(fld_url));
				list.Add(itm);
			}
		}
		finally {rdr.Rls();}
		return (Fsm_mnt_itm[])list.Xto_ary_and_clear(Fsm_mnt_itm.class);
	}
}
