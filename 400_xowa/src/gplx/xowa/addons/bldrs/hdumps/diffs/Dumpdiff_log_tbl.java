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
package gplx.xowa.addons.bldrs.hdumps.diffs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.hdumps.*;
import gplx.dbs.*;
class Dumpdiff_log_tbl implements Db_tbl {		
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld__page_id, fld__cur_snip, fld__prv_snip;
	private Db_stmt stmt__insert;
	public Dumpdiff_log_tbl(Db_conn conn) {
		this.conn = conn;
		flds.Add_int_pkey("uid");
		this.fld__page_id = flds.Add_int("page_id");
		this.fld__cur_snip = flds.Add_str("cur_snip", 1024);
		this.fld__prv_snip = flds.Add_str("prv_snip", 1024);
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name = "diff_log";
	public Db_conn Conn() {return conn;} private final    Db_conn conn;
	public void Create_tbl() {
		conn.Meta_tbl_remake(Dbmeta_tbl_itm.New(tbl_name, flds, Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, fld__page_id, fld__page_id)));
	}
	public void Insert_bgn() {
		stmt__insert = conn.Stmt_insert(tbl_name, fld__page_id, fld__cur_snip, fld__prv_snip);
		conn.Txn_bgn("diff_log");
	}
	public void Insert_by_batch(int page_id, byte[] prv_snip, byte[] cur_snip) {
		stmt__insert.Clear().Val_int(fld__page_id, page_id).Val_bry_as_str(fld__cur_snip, cur_snip).Val_bry_as_str(fld__prv_snip, prv_snip).Exec_insert();
	}
	public void Insert_end() {
		conn.Txn_end();
		stmt__insert.Rls();
	}
	public void Rls() {
		stmt__insert = Db_stmt_.Rls(stmt__insert);
	}

	public static Dumpdiff_log_tbl New(Xowe_wiki wiki) {
		Db_conn conn = Db_conn_bldr.Instance.Get_or_autocreate(true, wiki.Fsys_mgr().Root_dir().GenSubFil("xowa.diff.sqlite3"));
		Dumpdiff_log_tbl rv = new Dumpdiff_log_tbl(conn);
		conn.Meta_tbl_remake(rv);
		return rv;
	}
}
