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
package gplx.xowa.files.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.dbs.*; import gplx.dbs.engines.sqlite.*;
class Xofc_dir_tbl {
	private String tbl_name = "file_cache_dir"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private String fld_id, fld_name;
	private Db_conn conn; private final Db_stmt_bldr stmt_bldr = new Db_stmt_bldr(); private Db_stmt select_stmt;
	public void Conn_(Db_conn new_conn, boolean created, boolean version_is_1) {
		this.conn = new_conn; flds.Clear();
		String fld_prefix = "";
		if (version_is_1) {
			tbl_name		= "cache_dir";
			fld_prefix		= "dir_";
		}
		fld_id				= flds.Add_int_pkey(fld_prefix + "id");
		fld_name			= flds.Add_str(fld_prefix + "name", 255);
		if (created) {
			Db_meta_tbl meta = Db_meta_tbl.new_(tbl_name, flds
			, Db_meta_idx.new_normal_by_tbl(tbl_name, "name", fld_name)
			);
			conn.Exec_create_tbl_and_idx(meta);
		}
		stmt_bldr.Conn_(conn, tbl_name, flds, fld_id);
	}
	public String Db_save(Xofc_dir_itm itm) {
		try {
			Db_stmt stmt = stmt_bldr.Get(itm.Cmd_mode());
			switch (itm.Cmd_mode()) {
				case Db_cmd_mode.Tid_create:	stmt.Clear().Val_int(fld_id, itm.Id())	.Val_bry_as_str(fld_name, itm.Name()).Exec_insert(); break;
				case Db_cmd_mode.Tid_update:	stmt.Clear()							.Val_bry_as_str(fld_name, itm.Name()).Crt_int(fld_id, itm.Id()).Exec_update(); break;
				case Db_cmd_mode.Tid_delete:	stmt.Clear().Crt_int(fld_id, itm.Id()).Exec_delete(); break;
				case Db_cmd_mode.Tid_ignore:	break;
				default:						throw Err_.unhandled(itm.Cmd_mode());
			}
			itm.Cmd_mode_(Db_cmd_mode.Tid_ignore);
			return null;
		} catch (Exception e) {
			stmt_bldr.Rls(); // rls bldr, else bad stmt will lead to other failures
			return Err_.Message_gplx_brief(e);
		}
	}
	public void Cleanup() {
		select_stmt = Db_stmt_.Rls(select_stmt);
		stmt_bldr.Rls();
	}
	public Xofc_dir_itm Select_one(byte[] name) {
		if (select_stmt == null) select_stmt = conn.Rls_reg(conn.Stmt_select(tbl_name, flds, fld_name));
		Db_rdr rdr = Db_rdr_.Null;
		try {
			rdr = select_stmt.Clear().Crt_bry_as_str(fld_name, name).Exec_select_as_rdr();
			return rdr.Move_next() ? new_itm(rdr) : Xofc_dir_itm.Null;
		}
		finally {rdr.Rls();}
	}		
	public void Select_all(ListAdp list) {
		list.Clear();
		Db_stmt select_all_stmt = conn.Stmt_select(tbl_name, flds, Db_meta_fld.Ary_empy);
		Db_rdr rdr = Db_rdr_.Null;
		try {
			rdr = select_all_stmt.Exec_select_as_rdr();
			while (rdr.Move_next())
				list.Add(new_itm(rdr));
		}
		finally {rdr.Rls();}
	}
	public int Select_max_uid() {return Db_conn_.Select_fld0_as_int_or(conn, "SELECT Max(uid) AS MaxId FROM cache_dir;", -1);}
	private Xofc_dir_itm new_itm(Db_rdr rdr) {
		return new Xofc_dir_itm(rdr.Read_int(fld_id), rdr.Read_bry_by_str(fld_name), Db_cmd_mode.Tid_ignore);
	}
}
