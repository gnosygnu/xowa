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
package gplx.xowa.addons.wikis.htmls.css.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.htmls.*; import gplx.xowa.addons.wikis.htmls.css.*;
import gplx.dbs.*;
public class Xowd_css_core_tbl implements Rls_able {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_id, fld_key, fld_updated_on;
	public Xowd_css_core_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_id				= flds.Add_int_pkey_autonum("css_id");
		this.fld_key			= flds.Add_str("css_key", 255);
		this.fld_updated_on		= flds.Add_str("css_updated_on", 20);
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;} private final    Db_conn conn;
	public String Tbl_name() {return tbl_name;} private final    String tbl_name = "css_core";
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds, Dbmeta_idx_itm.new_unique_by_tbl(tbl_name, "main", fld_key)));}
	public void Rls() {}
	public int Insert(String key, DateAdp updated_on) {
		Db_stmt stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Val_str(fld_key, key).Val_str(fld_updated_on, updated_on.XtoStr_fmt_yyyyMMdd_HHmmss()).Exec_insert();
		return Select_id_by_key(key);
	}
	public void Update(int id, String key, DateAdp updated_on) {
		Db_stmt stmt_update = conn.Stmt_update_exclude(tbl_name, flds, fld_id);
		stmt_update.Val_str(fld_key, key).Val_str(fld_updated_on, updated_on.XtoStr_fmt_yyyyMMdd_HHmmss()).Crt_int(fld_id, id).Exec_update();
	}
	public Xowd_css_core_itm Select_by_key(String key) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_key).Crt_str(fld_key, key).Exec_select__rls_auto();
		try {return rdr.Move_next() ? new_itm(rdr) : null;}
		finally {rdr.Rls();}
	}
	public int Select_id_by_key(String key) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_key).Crt_str(fld_key, key).Exec_select__rls_auto();
		try {return rdr.Move_next() ? rdr.Read_int(fld_id) : Id_null;}
		finally {rdr.Rls();}
	}
	public Xowd_css_core_itm[] Select_all() {	// TEST:
		Db_stmt stmt = conn.Stmt_select(tbl_name, flds);
		return Select_by_stmt(stmt);
	}
	private Xowd_css_core_itm[] Select_by_stmt(Db_stmt stmt) {
		List_adp rv = List_adp_.New();
		Db_rdr rdr = stmt.Exec_select__rls_auto();
		try {
			while (rdr.Move_next())
				rv.Add(new_itm(rdr));
		} finally {rdr.Rls();}
		return (Xowd_css_core_itm[])rv.To_ary_and_clear(Xowd_css_core_itm.class);
	}
	public void Delete_all() {
		conn.Stmt_delete(tbl_name).Exec_delete();
	}
	private Xowd_css_core_itm new_itm(Db_rdr rdr) {
		return new Xowd_css_core_itm(rdr.Read_int(fld_id), rdr.Read_str(fld_key), rdr.Read_date_by_str(fld_updated_on));
	}
	public static final int Id_null = -1;
}
