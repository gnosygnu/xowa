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
package gplx.xowa.htmls.core.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.dbs.*;
public class Xoh_redlink_tbl implements Rls_able {
	private final    String tbl_name = "html_redlink"; private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_page_id, fld_redlink_uids;
	private final    Db_conn conn; private Db_stmt stmt_select, stmt_insert, stmt_delete, stmt_update;
	public Xoh_redlink_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_page_id			= flds.Add_int_pkey("page_id");
		this.fld_redlink_uids		= flds.Add_bry("redlink_uids");
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Insert_bgn() {conn.Txn_bgn("html_redlink__insert"); stmt_insert = conn.Stmt_insert(tbl_name, flds);}
	public void Insert_end() {conn.Txn_end(); stmt_insert = Db_stmt_.Rls(stmt_insert);}
	public void Insert(int page_id, byte[] redlink_uids) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear().Val_int(fld_page_id, page_id).Val_bry(fld_redlink_uids, redlink_uids).Exec_insert();
	}
	public void Update(int page_id, byte[] redlink_uids) {
		if (stmt_update == null) stmt_update = conn.Stmt_update_exclude(tbl_name, flds, fld_page_id);
		stmt_update.Clear().Val_bry(fld_redlink_uids, redlink_uids).Crt_int(fld_page_id, page_id).Exec_update();
	}
	public void Delete(int page_id) {
		if (stmt_delete == null) stmt_delete = conn.Stmt_delete(tbl_name, fld_page_id);
		stmt_delete.Clear().Crt_int(fld_page_id, page_id).Exec_delete();
	}
	public byte[] Select_or_null(int page_id) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_page_id);
		Db_rdr rdr = stmt_select.Clear().Crt_int(fld_page_id, page_id).Exec_select__rls_manual();
		try {
			return rdr.Move_next() ? rdr.Read_bry(fld_redlink_uids) : null;
		}
		finally {rdr.Rls();}
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_delete = Db_stmt_.Rls(stmt_delete);
		stmt_select = Db_stmt_.Rls(stmt_select);
		stmt_update = Db_stmt_.Rls(stmt_update);
	}
}
