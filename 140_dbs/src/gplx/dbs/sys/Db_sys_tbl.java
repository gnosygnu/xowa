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
package gplx.dbs.sys; import gplx.*; import gplx.dbs.*;
class Db_sys_tbl implements Rls_able {
	private final    String tbl_name = "gfdb_sys";
	private String fld_key, fld_val;
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    Db_conn conn; private Db_stmt stmt_insert, stmt_update, stmt_select;
	public Db_sys_tbl(Db_conn conn) {
		this.conn = conn;
		fld_key = flds.Add_str_pkey("sys_key", 255); fld_val = flds.Add_text("sys_val");
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public int Assert_int_or(String key, int or) {
		String rv = Assert_str_or(key, Int_.To_str(or));
		try {return Int_.parse(rv);}
		catch (Exception e) {Err_.Noop(e); return or;}
	}
	public String Assert_str_or(String key, String or) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_key);
		Db_rdr rdr = stmt_select.Clear().Crt_str(fld_key, key).Exec_select__rls_manual();
		try {
			if (rdr.Move_next())
				return rdr.Read_str(fld_val);
			else {
				Insert_str(key, or);
				return or;
			}
		}	finally {rdr.Rls();}
	}
	public void Update_int(String key, int val) {Update_str(key, Int_.To_str(val));}
	private void Update_str(String key, String val) {
		if (stmt_update == null) stmt_update = conn.Stmt_update(tbl_name, String_.Ary(fld_key), fld_val);
		stmt_update.Clear()
		.Val_str(fld_val		, val)
		.Crt_str(fld_key		, key)
		.Exec_update();
	}		
	private void Insert_str(String key, String val) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
		.Val_str(fld_key		, key)
		.Val_str(fld_val		, val)
		.Exec_insert();
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_update = Db_stmt_.Rls(stmt_update);
		stmt_select = Db_stmt_.Rls(stmt_select);
	}
}
