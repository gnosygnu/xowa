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
package gplx.dbs.engines;

import gplx.libs.dlgs.Gfo_usr_dlg;
import gplx.libs.files.Io_url;
import gplx.core.stores.DataRdr;
import gplx.dbs.Db_conn;
import gplx.dbs.Db_conn_info;
import gplx.dbs.Db_qry;
import gplx.dbs.Db_rdr;
import gplx.dbs.Db_stmt;
import gplx.dbs.DbmetaFldItm;
import gplx.dbs.Dbmeta_idx_itm;
import gplx.dbs.Dbmeta_tbl_itm;
import gplx.dbs.conn_props.Db_conn_props_mgr;
import gplx.dbs.metas.Dbmeta_tbl_mgr;
import gplx.dbs.qrys.bats.Db_batch_mgr;
import gplx.dbs.sqls.SqlQryWtr;
import gplx.dbs.wkrs.SqlWkrMgr;

public interface Db_engine {
	String				Tid();
	Db_conn_info		Conn_info();
	Db_conn_props_mgr	Props();
	Db_batch_mgr		Batch_mgr();
	SqlQryWtr Sql_wtr();
	void                CtorConn(SqlWkrMgr wkrMgr);
	Db_engine			New_clone(Db_conn_info conn_info);
	void				Conn_open();
	void				Conn_term();
	void				Txn_bgn(String name);		// NOTE: sqlite has different transaction semantics with SAVEPOINT
	String				Txn_end();
	void				Txn_cxl();
	void				Txn_sav();
	void				Meta_tbl_create(Dbmeta_tbl_itm meta);
	void				Meta_tbl_delete(String tbl);
	void				Meta_fld_append(String tbl, DbmetaFldItm fld);
	void				Meta_idx_create(Gfo_usr_dlg usr_dlg, Dbmeta_idx_itm... ary);
	void				Meta_idx_delete(String idx);
	Dbmeta_tbl_mgr		Meta_mgr();
	boolean				Meta_tbl_exists(String tbl);
	boolean				Meta_fld_exists(String tbl, String fld);
	boolean				Meta_idx_exists(String idx);
	Object				Exec_as_obj(Db_qry qry);
	Db_rdr				Exec_as_rdr__rls_manual	(Object rdr_obj, String sql);				// Object o:ResultSet if desktop; Cursor if android
	Db_rdr				Exec_as_rdr__rls_auto	(Db_stmt stmt, Object rdr_obj, String sql);	// Object o:ResultSet if desktop; Cursor if android
	Db_stmt				Stmt_by_qry(Db_qry qry);
	Object				Stmt_by_sql(String sql);
	void				Env_db_attach(String alias, Db_conn conn);
	void				Env_db_attach(String alias, Io_url db_url);
	void				Env_db_detach(String alias);
	DataRdr				New_rdr(java.sql.ResultSet rdr, String sql); 
}
