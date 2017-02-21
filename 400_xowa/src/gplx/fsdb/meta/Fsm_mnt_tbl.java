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
package gplx.fsdb.meta; import gplx.*; import gplx.fsdb.*;
import gplx.dbs.*;
public class Fsm_mnt_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_id, fld_name, fld_url;		
	private final    Db_conn conn;
	public Fsm_mnt_tbl(Db_conn conn, boolean schema_is_1) {
		this.conn = conn;
		fld_id				= flds.Add_int_pkey	("mnt_id");
		fld_name			= flds.Add_str		("mnt_name", 255);
		fld_url				= flds.Add_str		("mnt_url", 255);
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name = "fsdb_mnt";
	public void Create_tbl() {
		Dbmeta_tbl_itm meta = Dbmeta_tbl_itm.New(tbl_name, flds);
		conn.Meta_tbl_create(meta);
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
		List_adp list = List_adp_.New();
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
