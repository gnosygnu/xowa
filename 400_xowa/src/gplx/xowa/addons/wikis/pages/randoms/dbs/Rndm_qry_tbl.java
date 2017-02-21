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
package gplx.xowa.addons.wikis.pages.randoms.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.pages.*; import gplx.xowa.addons.wikis.pages.randoms.*;
import gplx.core.ios.*; import gplx.dbs.*; import gplx.dbs.utls.*;
public class Rndm_qry_tbl implements Rls_able {
	private final    String tbl_name = "rndm_qry"; private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_qry_idx, fld_rng_end, fld_qry_key, fld_qry_data, fld_qry_name;
	private final    Db_conn conn;
	public Rndm_qry_tbl(Db_conn conn) {
		this.conn = conn;
		fld_qry_idx		= flds.Add_int_pkey("qry_idx");		// EX: 0
		fld_rng_end		= flds.Add_int("rng_end");			// EX: 123
		fld_qry_key		= flds.Add_str("qry_key", 255);		// EX: xowa.ns.0
		fld_qry_data	= flds.Add_str("qry_data", 255);	// EX: type=ns;ns=0
		fld_qry_name	= flds.Add_str("qry_name", 255);	// EX: Main Namespace - All
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public int Select_rng_end(int qry_idx) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_qry_idx).Exec_select__rls_auto();
		try {return rdr.Move_next() ? rdr.Read_int(fld_rng_end) : 0;}
		finally {rdr.Rls();}
	}
	public int Select_qry_max() {
		Db_rdr rdr = conn.Stmt_sql("SELECT Coalesce(Max(qry_idx), 0) AS qry_idx FROM rndm_qry").Exec_select__rls_auto();	// ANSI.Y
		try {return rdr.Move_next() ? rdr.Read_int(fld_qry_idx) : 0;}
		finally {rdr.Rls();}
	}
	public int Select_by_key(String key) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_qry_key).Crt_str(fld_qry_key, key).Exec_select__rls_auto();
		try {return rdr.Move_next() ? rdr.Read_int(fld_qry_idx) : -1;}
		finally {rdr.Rls();}
	}
	public void Insert(int qry_idx, int rng_end, String qry_key, String qry_data, String qry_name) {
		conn.Stmt_insert(tbl_name, flds).Val_int(fld_qry_idx, qry_idx).Val_int(fld_rng_end, rng_end)
			.Val_str(fld_qry_key, qry_key).Val_str(fld_qry_data, qry_data).Val_str(fld_qry_name, qry_name).Exec_insert();
	}
	public void Delete_by_qry_idx(int qry_idx) {conn.Stmt_delete(tbl_name, fld_qry_idx).Crt_int(fld_qry_idx, qry_idx).Exec_delete();}
	public void Rls() {}
}
