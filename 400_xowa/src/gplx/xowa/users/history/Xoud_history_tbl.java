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
package gplx.xowa.users.history; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.dbs.metas.*; import gplx.dbs.metas.updates.*;
public class Xoud_history_tbl implements Rls_able {
	private final    String tbl_name = "user_history"; private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_id, fld_wiki, fld_url, fld_time, fld_count;
	private final    Db_conn conn; private Db_stmt stmt_select_by_page, stmt_select_by_top, stmt_insert, stmt_update, stmt_delete;
	public Xoud_history_tbl(Db_conn conn) {
		this.conn = conn;
		fld_id							= flds.Add_int_pkey_autonum("hist_id");
		fld_wiki						= flds.Add_str("hist_wiki", 255);
		fld_url							= flds.Add_str("hist_url", 255);
		fld_time						= flds.Add_str("hist_time", 20);
		fld_count						= flds.Add_int("hist_count");
		stmt_insert = stmt_update = stmt_delete = stmt_select_by_page = stmt_select_by_top = null;
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds, Dbmeta_idx_itm.new_unique_by_tbl(tbl_name, "pkey", fld_wiki, fld_url)));}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_update = Db_stmt_.Rls(stmt_update);
		stmt_delete = Db_stmt_.Rls(stmt_delete);
		stmt_select_by_page = Db_stmt_.Rls(stmt_select_by_page);
		stmt_select_by_top = Db_stmt_.Rls(stmt_select_by_top);
	}
	public void Insert(byte[] wiki, byte[] url, DateAdp time, int count) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
			.Val_bry_as_str(fld_wiki, wiki)
			.Val_bry_as_str(fld_url , url)
			.Val_str(fld_time, time.XtoStr_fmt_iso_8561())
			.Val_int(fld_count, count)
			.Exec_insert();
	}
	public void Update(int id, DateAdp time, int count) {
		if (stmt_update == null) stmt_update = conn.Stmt_update(tbl_name, String_.Ary(fld_id), fld_time, fld_count);
		stmt_update.Clear()
			.Val_str(fld_time, time.XtoStr_fmt_iso_8561())
			.Val_int(fld_count, count)
			.Crt_int(fld_id, id)
			.Exec_update();
	}
	public void Delete(int id) {
		Db_stmt stmt_delete = conn.Stmt_delete(tbl_name, fld_id);
		stmt_delete.Clear().Crt_int(fld_id, id).Exec_delete();
	}
	public Xoud_history_row Select_or_null(int id) {
		if (stmt_select_by_page == null) stmt_select_by_page = conn.Stmt_select(tbl_name, flds, fld_id);
		Db_rdr rdr = stmt_select_by_page.Clear().Crt_int(fld_id, id).Exec_select__rls_manual();
		try {
			return rdr.Move_next() ? new_row(rdr) : null;
		}
		finally {rdr.Rls();}
	}
	public void Select_by_top(List_adp rv, int count) {
		if (stmt_select_by_top == null) {
			Db_qry__select_in_tbl qry = new Db_qry__select_in_tbl(tbl_name, flds.To_str_ary(), null, null, null, fld_time + " DESC", " LIMIT " + Int_.To_str(count));
			stmt_select_by_top = conn.Stmt_new(qry);
		}
		Db_rdr rdr = stmt_select_by_top.Clear().Exec_select__rls_manual();
		try {
			rv.Clear();				
			while (rdr.Move_next()) {
				Xoud_history_row row = new_row(rdr);
				rv.Add(row);
			}
		}
		finally {rdr.Rls();}
	}
	private Xoud_history_row new_row(Db_rdr rdr) {
		return new Xoud_history_row
		( rdr.Read_int(fld_id)
		, rdr.Read_bry_by_str(fld_wiki)
		, rdr.Read_bry_by_str(fld_url)
		, rdr.Read_date_by_str(fld_time)
		, rdr.Read_int(fld_count)
		);
	}
}
