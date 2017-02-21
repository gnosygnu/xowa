/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.files.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.dbs.*; import gplx.dbs.engines.sqlite.*;
class Xofc_dir_tbl implements Rls_able {
	private String tbl_name = "file_cache_dir"; private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private String fld_id, fld_name;
	private Db_conn conn; private final    Db_stmt_bldr stmt_bldr = new Db_stmt_bldr(); private Db_stmt select_stmt;
	public void Conn_(Db_conn new_conn, boolean created, boolean schema_is_1) {
		this.conn = new_conn; flds.Clear();
		String fld_prefix = "";
		if (schema_is_1) {
			tbl_name		= "cache_dir";
			fld_prefix		= "dir_";
		}
		fld_id				= flds.Add_int_pkey(fld_prefix + "id");
		fld_name			= flds.Add_str(fld_prefix + "name", 255);
		if (created) {
			Dbmeta_tbl_itm meta = Dbmeta_tbl_itm.New(tbl_name, flds
			, Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "name", fld_name)
			);
			conn.Meta_tbl_create(meta);
		}
		stmt_bldr.Conn_(conn, tbl_name, flds, fld_id);
		conn.Rls_reg(this);
	}
	public void Rls() {
		select_stmt = Db_stmt_.Rls(select_stmt);
	}
	public String Db_save(Xofc_dir_itm itm) {
		try {
			Db_stmt stmt = stmt_bldr.Get(itm.Cmd_mode());
			switch (itm.Cmd_mode()) {
				case Db_cmd_mode.Tid_create:	stmt.Clear().Val_int(fld_id, itm.Id())	.Val_bry_as_str(fld_name, itm.Name()).Exec_insert(); break;
				case Db_cmd_mode.Tid_update:	stmt.Clear()							.Val_bry_as_str(fld_name, itm.Name()).Crt_int(fld_id, itm.Id()).Exec_update(); break;
				case Db_cmd_mode.Tid_delete:	stmt.Clear().Crt_int(fld_id, itm.Id()).Exec_delete(); break;
				case Db_cmd_mode.Tid_ignore:	break;
				default:						throw Err_.new_unhandled(itm.Cmd_mode());
			}
			itm.Cmd_mode_(Db_cmd_mode.Tid_ignore);
			return null;
		} catch (Exception e) {
			stmt_bldr.Rls(); // rls bldr, else bad stmt will lead to other failures
			return Err_.Message_gplx_full(e);
		}
	}
	public void Cleanup() {
		select_stmt = Db_stmt_.Rls(select_stmt);
		stmt_bldr.Rls();
	}
	public Xofc_dir_itm Select_one(byte[] name) {
		if (select_stmt == null) select_stmt = conn.Stmt_select(tbl_name, flds, fld_name);
		Db_rdr rdr = select_stmt.Clear().Crt_bry_as_str(fld_name, name).Exec_select__rls_manual();
		try {
			return rdr.Move_next() ? new_itm(rdr) : Xofc_dir_itm.Null;
		}
		finally {rdr.Rls();}
	}		
	public void Select_all(List_adp list) {
		list.Clear();
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, Dbmeta_fld_itm.Str_ary_empty).Exec_select__rls_auto();
		try {
			while (rdr.Move_next())
				list.Add(new_itm(rdr));
		}
		finally {rdr.Rls();}
	}
	public int Select_max_uid() {return conn.Exec_select_as_int("SELECT Max(uid) AS MaxId FROM cache_dir;", -1);}
	private Xofc_dir_itm new_itm(Db_rdr rdr) {
		return new Xofc_dir_itm(rdr.Read_int(fld_id), rdr.Read_bry_by_str(fld_name), Db_cmd_mode.Tid_ignore);
	}
}
