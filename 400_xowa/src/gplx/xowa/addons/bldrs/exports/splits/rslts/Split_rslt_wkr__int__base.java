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
package gplx.xowa.addons.bldrs.exports.splits.rslts;
import gplx.dbs.*;
import gplx.types.basics.arrays.IntAry;
public abstract class Split_rslt_wkr__int__base implements Split_rslt_wkr {
	private Split_rslt_mgr rslt_mgr;
	private Db_conn wkr_conn; private Db_stmt stmt; private int db_id;
	private final IntAry pkey_ary = new IntAry(128);
	private final String tbl_name, pkey_name;
	public Split_rslt_wkr__int__base() {
		this.tbl_name = Tbl_name();
		this.pkey_name = Pkey_name();
	}
	public abstract byte Tid();
	public abstract String Tbl_name();
	public abstract String Pkey_name();
	public int Row_count() {return pkey_ary.Len();}
	public long Obj_size() {return obj_size;} private long obj_size;
	public void On__init(Split_rslt_mgr rslt_mgr, Db_conn wkr_conn) {
		this.rslt_mgr = rslt_mgr;
		this.wkr_conn = wkr_conn;
		wkr_conn.Meta_tbl_remake(Dbmeta_tbl_itm.New(tbl_name
		, DbmetaFldItm.NewInt("db_id")
		, DbmetaFldItm.NewInt(pkey_name)
		));
		this.stmt = wkr_conn.Stmt_insert(tbl_name, "db_id", pkey_name);
	}
	public void On__nth__new(int db_id) {
		this.db_id = db_id;
		this.obj_size = 0;
	}
	public void On__nth__itm(int size, int pkey) {
		obj_size += size;
		rslt_mgr.Db_size_add_(size);
		pkey_ary.Add(pkey);
	}
	public void On__nth__rls() {
		wkr_conn.Txn_bgn(tbl_name);
		int len = pkey_ary.Len();			
		for (int i = 0; i < len; ++i) {
			stmt.Clear().Val_int("db_id", db_id).Val_int(pkey_name, pkey_ary.GetAt(i)).Exec_insert();
		}
		wkr_conn.Txn_end();
		pkey_ary.Clear();
	}
	public void On_term() {
		stmt = Db_stmt_.Rls(stmt);
		wkr_conn.Meta_idx_create(tbl_name, "page_id", pkey_name);
	}
}
