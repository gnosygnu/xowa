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
package gplx.xowa.xtns.cldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.dbs.*;
class Cldr_lang_tbl implements Rls_able {
	private final    String tbl_name = "cldr_lang"; private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_cldr_code, fld_lang_code, fld_lang_name;
	private final    Db_conn conn; private Db_stmt stmt_select, stmt_insert;
	public Cldr_lang_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_cldr_code			= flds.Add_str("cldr_code", 32);
		this.fld_lang_code			= flds.Add_str("lang_code", 32);
		this.fld_lang_name			= flds.Add_str("lang_name", 2048);
		conn.Rls_reg(this);
	}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds, Dbmeta_idx_itm.new_unique_by_tbl(tbl_name, "main", fld_cldr_code, fld_lang_code)));}
	public void Insert_bgn() {conn.Txn_bgn("cldr_lang__inser"); stmt_insert = conn.Stmt_insert(tbl_name, flds);}
	public void Insert_end() {conn.Txn_end(); stmt_insert = Db_stmt_.Rls(stmt_insert);}
	public void Insert_cmd_by_batch(byte[] cldr_code, byte[] lang_code, byte[] lang_name) {
		stmt_insert.Clear().Val_bry_as_str(fld_cldr_code, cldr_code).Val_bry_as_str(fld_lang_code, lang_code).Val_bry_as_str(fld_lang_name, lang_name).Exec_insert();
	}
	public byte[] Select(byte[] cldr_code, byte[] lang_code) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_cldr_code, fld_lang_code);
		Db_rdr rdr = stmt_select.Clear().Val_bry_as_str(fld_cldr_code, cldr_code).Val_bry_as_str(fld_lang_code, lang_code).Exec_select__rls_manual();
		try {return (byte[])rdr.Read_bry(fld_lang_name);}
		finally {rdr.Rls();}
	}
	public void Rls() {
		stmt_select = Db_stmt_.Rls(stmt_select);
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
}
