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
public class Fsm_mnt_tbl implements Rls_able {
	private final String tbl_name = "fsdb_mnt"; private final Dbmeta_fld_list flds = Dbmeta_fld_list.new_();
	private final String fld_id, fld_name, fld_url;		
	private final Db_conn conn;
	public Fsm_mnt_tbl(Db_conn conn, boolean schema_is_1) {
		this.conn = conn;
		fld_id				= flds.Add_int_pkey	("mnt_id");
		fld_name			= flds.Add_str		("mnt_name", 255);
		fld_url				= flds.Add_str		("mnt_url", 255);
		conn.Rls_reg(this);
	}
	public void Create_tbl() {
		Dbmeta_tbl_itm meta = Dbmeta_tbl_itm.New(tbl_name, flds);
		conn.Ddl_create_tbl(meta);
		this.Insert(Fsm_mnt_mgr.Mnt_idx_main, Mnt_name_main, Mnt_name_main);
		this.Insert(Fsm_mnt_mgr.Mnt_idx_user, Mnt_name_user, Mnt_name_user);
	}
	public void Rls() {}
	public void Insert(int id, String name, String url) {
		Db_stmt stmt = conn.Stmt_insert(tbl_name, flds);
		stmt.Clear().Val_int(fld_id, id).Val_str(fld_name, name).Val_str(fld_url, url).Exec_insert();
	}
	public void Update(int id, String name, String url) {
		Db_stmt stmt = conn.Stmt_update_exclude(tbl_name, flds, fld_id);
		stmt.Clear().Val_str(fld_name, name).Val_str(fld_url, url).Crt_int(fld_id, id).Exec_update();
	}	
	public Fsm_mnt_itm[] Select_all() {
		List_adp list = List_adp_.new_();
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, Dbmeta_fld_itm.Str_ary_empty).Clear().Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				Fsm_mnt_itm itm = new Fsm_mnt_itm(rdr.Read_int(fld_id), rdr.Read_str(fld_name), rdr.Read_str(fld_url));
				list.Add(itm);
			}
		}
		finally {rdr.Rls();}
		return (Fsm_mnt_itm[])list.To_ary_and_clear(Fsm_mnt_itm.class);
	}
	public static final String Mnt_name_main = "fsdb.main", Mnt_name_user = "fsdb.user";
}
