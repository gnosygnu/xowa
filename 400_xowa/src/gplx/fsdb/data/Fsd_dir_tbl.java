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
package gplx.fsdb.data; import gplx.*; import gplx.fsdb.*;
import gplx.dbs.*;
public class Fsd_dir_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_id, fld_owner_id, fld_name;		
	private final    Db_conn conn; private Db_stmt stmt_select_by_name;		
	public Fsd_dir_tbl(Db_conn conn, boolean schema_is_1) {
		this.conn = conn;
		this.fld_id				= flds.Add_int_pkey	("dir_id");
		this.fld_owner_id		= flds.Add_int		("dir_owner_id");
		this.fld_name			= flds.Add_str		("dir_name", 255);
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name = "fsdb_dir";
	public void Create_tbl() {
		conn.Meta_tbl_create
		( Dbmeta_tbl_itm.New(tbl_name, flds
		, Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "name", fld_name, fld_owner_id, fld_id)));
	}
	public void Insert(int id, byte[] name, int owner_id) {
		Db_stmt stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
			.Val_int(fld_id, id)
			.Val_int(fld_owner_id, owner_id)
			.Val_bry_as_str(fld_name, name)
			.Exec_insert();
		stmt_insert.Rls();
	}	
	public void Update(int id, byte[] name, int owner_id) {
		Db_stmt stmt_update = conn.Stmt_update_exclude(tbl_name, flds, fld_id);
		stmt_update.Clear()
			.Val_int(fld_owner_id, owner_id)
			.Val_bry_as_str(fld_name, name)
			.Crt_int(fld_id, id)
			.Exec_update();
		stmt_update.Rls();
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
	public void Rls() {
		stmt_select_by_name = Db_stmt_.Rls(stmt_select_by_name);
	}
}
