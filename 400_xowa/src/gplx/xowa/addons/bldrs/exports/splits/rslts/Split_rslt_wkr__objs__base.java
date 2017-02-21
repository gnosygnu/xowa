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
package gplx.xowa.addons.bldrs.exports.splits.rslts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
import gplx.core.primitives.*; import gplx.dbs.*; import gplx.xowa.addons.bldrs.exports.splits.rslts.*;
public abstract class Split_rslt_wkr__objs__base implements Split_rslt_wkr {
	private Split_rslt_mgr rslt_mgr;
	private Db_conn wkr_conn; private Db_stmt stmt; private int db_id;
	private final    List_adp pkey_list = List_adp_.New();
	private final    String tbl_name; private final    int pkey_flds_len; private final    Dbmeta_fld_itm[] pkey_flds; private final    String[] pkey_names;
	public Split_rslt_wkr__objs__base() {
		this.tbl_name = Tbl_name();
		this.pkey_flds = Pkey_flds();
		this.pkey_flds_len = pkey_flds.length;
		this.pkey_names = new String[pkey_flds_len];
		for (int i = 0; i < pkey_flds_len; ++i)
			pkey_names[i] = pkey_flds[i].Name();
	}
	public abstract byte Tid();
	public abstract String Tbl_name();
	public abstract Dbmeta_fld_itm[] Pkey_flds();
	public int Row_count() {return pkey_list.Len();}
	public long Obj_size() {return obj_size;} private long obj_size;
	public void On__init(Split_rslt_mgr rslt_mgr, Db_conn wkr_conn) {
		this.rslt_mgr = rslt_mgr;
		this.wkr_conn = wkr_conn;
		Dbmeta_tbl_itm meta_tbl = Dbmeta_tbl_itm.New(tbl_name, Dbmeta_fld_itm.new_int("db_id"));
		for (Dbmeta_fld_itm pkey_fld : pkey_flds )
			meta_tbl.Flds().Add(pkey_fld);
		wkr_conn.Meta_tbl_remake(meta_tbl);
		this.stmt = wkr_conn.Stmt_insert(tbl_name, String_.Ary_add(String_.Ary("db_id"), pkey_names));
	}
	public void On__nth__new(int db_id) {
		this.db_id = db_id;
		this.obj_size = 0;
	}
	public void On__nth__itm(int size, Object... pkey_objs) {
		this.obj_size += size;
		rslt_mgr.Db_size_add_(size);
		pkey_list.Add(pkey_objs);
	}
	public void On__nth__rls() {
		wkr_conn.Txn_bgn(tbl_name);
		int len = pkey_list.Len();
		for (int i = 0; i < len; ++i) {
			stmt.Clear().Val_int("db_id", db_id);
			Object[] pkey_objs = (Object[])pkey_list.Get_at(i);
			for (int j = 0; j < pkey_flds_len; ++j) {
				Dbmeta_fld_itm pkey_fld = pkey_flds[j];
				gplx.dbs.stmts.Db_stmt_arg_list.Fill_val(stmt, pkey_fld.Type().Tid_ansi(), pkey_fld.Name(), pkey_objs[j]);
			}
			stmt.Exec_insert();
		}
		wkr_conn.Txn_end();
		pkey_list.Clear();
	}
	public void On_term() {
		stmt = Db_stmt_.Rls(stmt);
		wkr_conn.Meta_idx_create(tbl_name, "pkey", pkey_names);
	}
}
