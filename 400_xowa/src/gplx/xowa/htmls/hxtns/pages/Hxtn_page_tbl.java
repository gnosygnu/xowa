/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.htmls.hxtns.pages;

import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.frameworks.objects.Rls_able;
import gplx.dbs.Db_conn;
import gplx.dbs.Db_rdr;
import gplx.dbs.Db_rdr_;
import gplx.dbs.Db_stmt;
import gplx.dbs.Db_stmt_;
import gplx.dbs.DbmetaFldList;
import gplx.dbs.Dbmeta_idx_itm;
import gplx.dbs.Dbmeta_tbl_itm;

public class Hxtn_page_tbl implements Rls_able {
	private static final String tbl_name = "hxtn_page"; private final DbmetaFldList flds = new DbmetaFldList();
	private final String fld_page_id, fld_wkr_id, fld_data_id;
	private final Db_conn conn; private Db_stmt stmt_insert;
	public Hxtn_page_tbl(Db_conn conn) {
		this.conn = conn;
		conn.Rls_reg(this);
		flds.AddIntPkeyAutonum("id");
		this.fld_page_id = flds.AddInt("page_id");
		this.fld_wkr_id = flds.AddInt("wkr_id");
		this.fld_data_id = flds.AddInt("data_id");
	}
	public String Tbl_name() {return tbl_name;}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public Db_conn Conn() {return conn;}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
	public void Stmt_bgn() {
		stmt_insert = conn.Stmt_insert(tbl_name, flds);
	}
	public void Stmt_end() {
		this.Rls();
		if (!conn.Meta_idx_exists(tbl_name, "pkey"))
			conn.Meta_idx_create(Dbmeta_idx_itm.new_unique_by_tbl(tbl_name, "pkey", fld_page_id, fld_wkr_id, fld_data_id));
	}
	public void Insert_by_rdr(Db_rdr rdr) {
		Db_stmt_.Insert_by_rdr(flds, rdr, stmt_insert);
	}
	public void Insert_exec(int page_id, int wkr_id, int data_id) {
		stmt_insert.Clear()
			.Val_int(fld_page_id    , page_id)
			.Val_int(fld_wkr_id     , wkr_id)
			.Val_int(fld_data_id    , data_id)
		.Exec_insert();
	}
	public boolean Exists(int page_id, int wkr_id, int data_id) {
		Db_rdr rdr = Db_rdr_.Empty;
		try {
			rdr = conn.Stmt_select(tbl_name, flds, fld_page_id, fld_wkr_id, fld_data_id)
				.Crt_int(fld_page_id, page_id)
				.Crt_int(fld_wkr_id, wkr_id)
				.Crt_int(fld_data_id, data_id)
				.Exec_select__rls_auto();
			return rdr.Move_next();
		} finally {
			rdr.Rls();
		}
	}
	public List_adp Select_by_page(int page_id) {
		List_adp rv = List_adp_.New();
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_page_id)
			.Crt_int(fld_page_id, page_id)
			.Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				Hxtn_page_itm itm = new Hxtn_page_itm(rdr.Read_int(fld_page_id), rdr.Read_int(fld_wkr_id), rdr.Read_int(fld_data_id));
				rv.Add(itm);
			}
		} finally {
			rdr.Rls();
		}
		return rv;
	}
}
