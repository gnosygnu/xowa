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
package gplx.xowa.users.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.dbs.*;
public class Xoud_site_tbl implements Rls_able {
	public static final String Tbl_name = "user_site";
	private final    String tbl_name = Tbl_name; private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_site_id, fld_site_priority, fld_site_domain, fld_site_name, fld_site_path, fld_site_xtn, fld_site_date;
	private final    Db_conn conn;
	public Xoud_site_tbl(Db_conn conn) {
		this.conn = conn;
		fld_site_id				= flds.Add_int_pkey("site_id");
		fld_site_priority		= flds.Add_int("site_priority");			// EX: 0=default; 1+ is order if 0 is unavailable
		fld_site_domain			= flds.Add_str("site_domain", 255);			// EX: en.wikipedia.org; NOTE: no protocol (https:)
		fld_site_name			= flds.Add_str("site_name", 255);			// EX: English Wikipedia
		fld_site_path			= flds.Add_str("site_path", 255);			// EX: ~{xowa_root}/wiki/en.wikipedia.org/
		fld_site_date			= conn.Meta_fld_append_if_missing(tbl_name, flds, Dbmeta_fld_itm.new_str("site_date", 255).Default_("")); // EX: 2016-06-10
		fld_site_xtn			= flds.Add_text("site_xtn");
		conn.Rls_reg(this);
	}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Rls() {}
	public void Insert(int site_id, int priority, String domain, String name, String path, String date, String xtn) {
		Db_stmt stmt = conn.Stmt_insert(tbl_name, flds);			
		Exec_stmt(stmt, Bool_.N, site_id, priority, domain, name, path, date, xtn);
	}
	public void Update(int site_id, int priority, String domain, String name, String path, String date, String xtn) {
		Db_stmt stmt = conn.Stmt_update_exclude(tbl_name, flds, fld_site_id);
		Exec_stmt(stmt, Bool_.Y, site_id, priority, domain, name, path, date, xtn);
	}
	public void Delete(int site_id) {
		Db_stmt stmt = conn.Stmt_delete(tbl_name, fld_site_id);
		stmt.Crt_int(fld_site_id, site_id).Exec_delete();
	}
	public void Delete_by_domain(byte[] domain) {
		conn.Stmt_delete(tbl_name, fld_site_domain).Crt_bry_as_str(fld_site_domain, domain).Exec_delete();
	}
	public Xoud_site_row[] Select_all() {
		List_adp rv = List_adp_.New();
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds).Exec_select__rls_auto();
		try {
			while (rdr.Move_next())
				rv.Add(New_site(rdr));
			return (Xoud_site_row[])rv.To_ary_and_clear(Xoud_site_row.class);
		}
		finally {rdr.Rls();}
	}
	public Xoud_site_row Select_by_domain(byte[] domain) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_site_domain).Crt_bry_as_str(fld_site_domain, domain).Exec_select__rls_auto();
		try {return rdr.Move_next() ? New_site(rdr) : null;}	// NOTE: old versions allowed multiple wikis with same domain; only return 1st
		finally {rdr.Rls();}
	}
	private void Exec_stmt(Db_stmt stmt, boolean update, int site_id, int priority, String domain, String name, String path, String date, String xtn) {
		if (!update)
			stmt.Val_int(fld_site_id, site_id);
		stmt.Val_int(fld_site_priority, priority).Val_str(fld_site_domain, domain).Val_str(fld_site_name, name).Val_str(fld_site_path, path)
			.Val_str(fld_site_date, date).Val_str(fld_site_xtn, xtn);
		if (update)
			stmt.Crt_int(fld_site_id, site_id);
		if (update)
			stmt.Exec_update();
		else
			stmt.Exec_insert();
	}
	private Xoud_site_row New_site(Db_rdr rdr) {
		return new Xoud_site_row
		( rdr.Read_int(fld_site_id)
		, rdr.Read_int(fld_site_priority)
		, rdr.Read_str(fld_site_domain)
		, rdr.Read_str(fld_site_name)
		, rdr.Read_str(fld_site_path)
		, rdr.Read_str(fld_site_date)
		, rdr.Read_str(fld_site_xtn)
		);
	}
}
