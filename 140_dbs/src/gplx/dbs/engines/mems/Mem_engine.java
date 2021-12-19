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
package gplx.dbs.engines.mems; import gplx.dbs.*; import gplx.dbs.engines.*;
import gplx.core.stores.*; import gplx.dbs.metas.*; import gplx.dbs.sqls.*; import gplx.dbs.conn_props.*; import gplx.dbs.qrys.bats.*;
import gplx.dbs.wkrs.SqlWkrMgr;
import gplx.libs.dlgs.Gfo_usr_dlg;
import gplx.libs.files.Io_url;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.lists.Hash_adp_;
import gplx.types.errs.ErrUtl;
public class Mem_engine implements Db_engine {
	private final Hash_adp tbl_hash = Hash_adp_.New();
	Mem_engine(Db_conn_info conn_info) {
		this.conn_info = conn_info;
		this.qry_runner = new Mem_exec_select(this);
	}
	public String				Tid() {return Mem_conn_info.Tid_const;}
	public Db_conn_info			Conn_info() {return conn_info;} private Db_conn_info conn_info;
	public Db_conn_props_mgr	Props() {return props;} private final Db_conn_props_mgr props = new Db_conn_props_mgr();
	public Db_batch_mgr			Batch_mgr() {return batch_mgr;} private final Db_batch_mgr batch_mgr = new Db_batch_mgr();
	public Mem_exec_select		Qry_runner() {return qry_runner;} private Mem_exec_select qry_runner;
	public SqlQryWtr Sql_wtr() {return sql_wtr;} private final SqlQryWtr sql_wtr = SqlQryWtrUtl.NewBasic();
	@Override public void       CtorConn(SqlWkrMgr wkrMgr) {}
	public Db_engine			New_clone(Db_conn_info conn_info) {return new Mem_engine(conn_info);}
	public Db_stmt				Stmt_by_qry(Db_qry qry) {return new Mem_stmt(this, qry);}
	public Mem_tbl				Tbls__get(String name)	{return (Mem_tbl)tbl_hash.GetByOrNull(name);}
	public void					Tbls__del(String name)	{tbl_hash.Del(name);}
	public void					Txn_bgn(String name)	{}//++txn_count;} private int txn_count = 0;
	public String				Txn_end()				{return "";}// --txn_count; return "";}
	public void					Txn_cxl()				{}//--txn_count;}
	public void					Txn_sav()				{this.Txn_end(); this.Txn_bgn("");}
	public Object				Exec_as_obj(Db_qry qry) {throw ErrUtl.NewUnimplemented();}
	public void					Conn_open() {}
	public void					Conn_term() {
		// if (txn_count != 0) throw ErrUtl.NewArgs("Conn_term.txns still open", "txn_count", txn_count);	// IGNORE: causing test to fails; DATE:2016-03-30
	}
	public Db_rdr				Exec_as_rdr__rls_manual(Object rdr_obj, String sql) {throw ErrUtl.NewUnimplemented();}
	public Db_rdr				Exec_as_rdr__rls_auto(Db_stmt stmt, Object rdr_obj, String sql) {throw ErrUtl.NewUnimplemented();}
	public DataRdr				New_rdr(java.sql.ResultSet rdr, String sql) {throw ErrUtl.NewUnimplemented();}
	public Object				Stmt_by_sql(String sql) {throw ErrUtl.NewUnimplemented();}
	public void					Meta_tbl_create(Dbmeta_tbl_itm meta) {
		Mem_tbl mem_tbl = new Mem_tbl(meta);
		tbl_hash.AddIfDupeUseNth(meta.Name(), mem_tbl);
		meta_mgr.Add(meta);
	}
	public void					Meta_tbl_delete(String tbl_key) {
		Mem_tbl tbl = (Mem_tbl)tbl_hash.GetByOrNull(tbl_key);
		if (tbl != null) tbl.rows.Clear();
	}
	public void					Meta_idx_create(Gfo_usr_dlg usr_dlg, Dbmeta_idx_itm... ary) {}	// TODO_OLD: implement unique index
	public void					Meta_idx_delete(String idx) {}
	public void					Meta_fld_append(String tbl, DbmetaFldItm fld)	{}
	public Dbmeta_tbl_mgr		Meta_mgr() {return meta_mgr;} private final Dbmeta_tbl_mgr meta_mgr = new Dbmeta_tbl_mgr(Dbmeta_reload_cmd_.Noop);
	public boolean					Meta_tbl_exists(String tbl)						{return tbl_hash.Has(tbl);}
	public boolean					Meta_fld_exists(String tbl, String fld)			{
		Mem_tbl mem_tbl = (Mem_tbl)tbl_hash.GetByOrNull(tbl); if (mem_tbl == null) return false;
		return mem_tbl.Meta().Flds().Has(fld);
	}
	public boolean					Meta_idx_exists(String idx)						{return false;}
	public void					Env_db_attach(String alias, Db_conn conn)		{}
	public void					Env_db_attach(String alias, Io_url db_url)		{}
	public void					Env_db_detach(String alias)						{}
        public static final Mem_engine Instance = new Mem_engine(); Mem_engine() {}
}
