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
package gplx.dbs.engines.tdbs; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import gplx.core.stores.*; import gplx.dbs.metas.*; import gplx.dbs.conn_props.*; import gplx.dbs.qrys.*; import gplx.dbs.sqls.*; import gplx.dbs.qrys.bats.*;
public class TdbEngine implements Db_engine {
	public String Tid() {return Tdb_conn_info.Tid_const;}
	public Db_conn_info			Conn_info() {return conn_info;} private Db_conn_info conn_info;
	public Db_conn_props_mgr	Props() {return props;} private final    Db_conn_props_mgr props = new Db_conn_props_mgr();
	public Db_batch_mgr			Batch_mgr() {return batch_mgr;} private final    Db_batch_mgr batch_mgr = new Db_batch_mgr();
	public Sql_qry_wtr			Sql_wtr() {return sql_wtr;} private final    Sql_qry_wtr sql_wtr = Sql_qry_wtr_.New__basic();
	public TdbDatabase Db() {return db;} TdbDatabase db;
	public void Conn_open() {
		Tdb_conn_info tdb_url = (Tdb_conn_info)conn_info;
		String url_str = tdb_url.Server();
		db = loadMgr.LoadTbls(Io_url_.new_any_(url_str));
	}
	public void Conn_term() {}
	public void Txn_bgn(String name)	{}
	public String Txn_end()				{return "";}
	public void Txn_cxl()				{}
	public void Txn_sav()				{}
	public Db_engine New_clone(Db_conn_info conn_info) {
		TdbEngine rv = new TdbEngine();
		rv.CtorTdbEngine(conn_info);
		rv.Conn_open();
		return rv;
	}
	public Object Exec_as_obj(Db_qry qry) {
		Db_qryWkr wkr = (Db_qryWkr)wkrs.Get_by_or_fail(qry.Tid());
		return wkr.Exec(this, qry);
	}
	public Db_stmt	Stmt_by_qry(Db_qry qry) {return new Db_stmt_sql().Parse(qry, sql_wtr.To_sql_str(qry, true));}
	public Object	Stmt_by_sql(String sql) {throw Err_.new_unimplemented();}
	public Db_rdr	Exec_as_rdr__rls_manual(Object rdr_obj, String sql) {return Db_rdr_.Empty;}
	public Db_rdr	Exec_as_rdr__rls_auto(Db_stmt stmt, Object rdr_obj, String sql) {return Db_rdr_.Empty;}
	public DataRdr	New_rdr(java.sql.ResultSet rdr, String sql) {return DataRdr_.Null;} 
	public TdbTable FetchTbl(String name) {
		TdbTable tbl = db.Tables().Get_by_or_fail(name);
		if (!tbl.IsLoaded()) loadMgr.LoadTbl(db, tbl);
		return tbl;
	}
	public void FlushAll() {
		saveMgr.SaveDb(db);
	}
	public void FlushTbl(TdbTable tbl) {
		saveMgr.SaveFile(db, tbl.File());
	}
	public void	Meta_tbl_create(Dbmeta_tbl_itm meta) {throw Err_.new_unimplemented();}
	public void Meta_idx_create(Gfo_usr_dlg usr_dlg, Dbmeta_idx_itm... ary) {throw Err_.new_unimplemented();}
	public void	Meta_idx_delete(String idx) {throw Err_.new_unimplemented();}
	public void				Meta_fld_append(String tbl, Dbmeta_fld_itm fld) {throw Err_.new_unimplemented();}
	public void				Meta_tbl_delete(String tbl)						{}
	public boolean				Meta_tbl_exists(String name)					{return false;}
	public boolean				Meta_fld_exists(String tbl, String fld)			{return false;}
	public boolean				Meta_idx_exists(String idx)						{return false;}
	public void				Env_db_attach(String alias, Db_conn conn)		{}
	public void				Env_db_attach(String alias, Io_url db_url)		{}
	public void				Env_db_detach(String alias)						{}
	public Dbmeta_tbl_mgr	Meta_mgr()										{return meta_mgr;} private final    Dbmeta_tbl_mgr meta_mgr = new Dbmeta_tbl_mgr(Dbmeta_reload_cmd_.Noop);

	Hash_adp wkrs = Hash_adp_.New(); TdbDbLoadMgr loadMgr = TdbDbLoadMgr.new_(); TdbDbSaveMgr saveMgr = TdbDbSaveMgr.new_();
	public static final    TdbEngine Instance = new TdbEngine(); 
	void CtorTdbEngine(Db_conn_info conn_info) {
		this.conn_info = conn_info;
		wkrs.Add(Db_qry_.Tid_select, TdbSelectWkr.Instance);
		wkrs.Add(Db_qry_.Tid_insert, TdbInsertWkr.new_());
		wkrs.Add(Db_qry_.Tid_update, TdbUpdateWkr.new_());
		wkrs.Add(Db_qry_.Tid_delete, TdbDeleteWkr.new_());
		wkrs.Add(Db_qry_.Tid_flush, TdbFlushWkr.new_());
	}
	public static TdbEngine as_(Object obj) {return obj instanceof TdbEngine ? (TdbEngine)obj : null;}
	public static TdbEngine cast(Object obj) {try {return (TdbEngine)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, TdbEngine.class, obj);}}
}
interface Db_qryWkr {
	Object Exec(Db_engine engine, Db_qry cmd);
}
class Db_qryWkr_ {
	public static final    Db_qryWkr Null = new Db_qryWrk_null();
}
class Db_qryWrk_null implements Db_qryWkr {
	public Object Exec(Db_engine engine, Db_qry cmd) {return null;}
}
