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
package gplx.dbs.engines;
import gplx.libs.dlgs.Gfo_usr_dlg;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.libs.files.Io_url;
import gplx.types.errs.ErrUtl;
import gplx.types.commons.KeyVal;
import gplx.core.stores.DataRdr;
import gplx.dbs.Db_conn;
import gplx.dbs.Db_conn_info;
import gplx.dbs.Db_qry;
import gplx.dbs.Db_qry_;
import gplx.dbs.Db_rdr;
import gplx.dbs.Db_rdr__basic;
import gplx.dbs.Db_stmt;
import gplx.dbs.DbmetaFldItm;
import gplx.dbs.Dbmeta_idx_itm;
import gplx.dbs.Dbmeta_tbl_itm;
import gplx.dbs.conn_props.Db_conn_props_mgr;
import gplx.dbs.metas.Dbmeta_tbl_mgr;
import gplx.dbs.qrys.Db_qry_sql;
import gplx.dbs.qrys.Db_stmt_cmd;
import gplx.dbs.qrys.bats.Db_batch_mgr;
import gplx.dbs.sqls.SqlQryWtr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public abstract class Db_engine_sql_base implements Db_engine {
	public void Ctor(Db_conn_info conn_info) {this.conn_info = conn_info;}
	public abstract String		Tid();
	public Db_conn_info			Conn_info() {return conn_info;} protected Db_conn_info conn_info;
	public Db_conn_props_mgr	Props() {return props;} private final Db_conn_props_mgr props = new Db_conn_props_mgr();
	public Db_batch_mgr			Batch_mgr() {return batch_mgr;} private final Db_batch_mgr batch_mgr = new Db_batch_mgr();
	public abstract SqlQryWtr Sql_wtr();
	public abstract				Db_engine New_clone(Db_conn_info conn_info);
	public Db_rdr				Exec_as_rdr__rls_manual(Object rdr_obj, String sql)				{return New_rdr(null, rdr_obj, sql);}
	public Db_rdr				Exec_as_rdr__rls_auto(Db_stmt stmt, Object rdr_obj, String sql)	{return New_rdr(stmt, rdr_obj, sql);}
	public 			Db_rdr New_rdr_clone() {return new Db_rdr__basic();}
	public Db_stmt		Stmt_by_qry(Db_qry qry) {return new Db_stmt_cmd(this, qry);}
	protected String Txn_bgn_str() {return "BEGIN TRANSACTION;";}
	protected String Txn_end_str() {return "COMMIT TRANSACTION;";}
	public void			Txn_bgn(String name)	{Exec_as_obj(Db_qry_sql.xtn_(this.Txn_bgn_str()));}
	public String		Txn_end()				{Exec_as_obj(Db_qry_sql.xtn_(this.Txn_end_str())); batch_mgr.Txn_end().Run(this); return "";}
	public void			Txn_cxl()				{Exec_as_obj(Db_qry_sql.xtn_("ROLLBACK TRANSACTION;"));}
	public void			Txn_sav() {
		String txn_name = this.Txn_end();
		this.Txn_bgn(txn_name);
	}
	public Object		Exec_as_obj(Db_qry qry) {
		if (qry.Tid() == Db_qry_.Tid_flush) return null;	// ignore flush (delete-db) statements
		String sql = this.Sql_wtr().ToSqlStr(qry, false); // DBG: Tfds.Write(sql);
		return qry.ReturnsRdr() ? (Object)this.Exec_as_rdr(sql) : this.Exec_as_int(sql);
	}
	protected int Exec_as_int(String sql) {
		try {
			Statement cmd = New_stmt_exec(sql);	
			return cmd.executeUpdate(sql);			
		}
		catch (Exception e) {throw ErrUtl.NewArgs(e, "db.engine:exec failed", "url", conn_info.Db_api(), "sql", sql);}
	}
	private DataRdr Exec_as_rdr(String sql) {
		try {
			Statement cmd = New_stmt_exec(sql);	
			cmd.execute(sql);										
			ResultSet rdr = cmd.getResultSet();	
			return New_rdr(rdr, sql);
		}
		catch (Exception e) {throw ErrUtl.NewArgs(e, "db.engine:rdr failed", "url", conn_info.Db_api(), "sql", sql);}
	}
	public void Meta_tbl_create(Dbmeta_tbl_itm tbl) {Exec_as_int(tbl.To_sql_create(this.Sql_wtr()));				this.Meta_mgr().Load_all();}
	public void Meta_tbl_delete(String tbl)			{Exec_as_int(this.Sql_wtr().Schema_wtr().Bld_drop_tbl(tbl));	this.Meta_mgr().Load_all();}
	public void Meta_idx_create(Gfo_usr_dlg usr_dlg, Dbmeta_idx_itm... ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Dbmeta_idx_itm idx = ary[i];
			usr_dlg.Plog_many("", "", "creating db index (please wait); db=~{0} idx=~{1}", conn_info.Database(), idx.Name());
			Exec_as_int(idx.To_sql_create(Sql_wtr()));
		}
		this.Meta_mgr().Load_all();
	}
	public void Meta_idx_delete(String idx) {
		if (Meta_idx_exists(idx)) Exec_as_int("DROP INDEX " + idx);
	}
	public void Meta_fld_append(String tbl, DbmetaFldItm fld) {
		Gfo_usr_dlg_.Instance.Plog_many("", "", "adding column to table: db=~{0} tbl=~{1} fld=~{2}", conn_info.Database(), tbl, fld.Name());
		try {
			Exec_as_int(this.Sql_wtr().Schema_wtr().Bld_alter_tbl_add(tbl, fld));
			Gfo_usr_dlg_.Instance.Plog_many("", "", "column added to table: db=~{0} tbl=~{1} fld=~{2}", conn_info.Database(), tbl, fld.Name());
		}
		catch (Exception e) {	// catch error if column already added to table
			Gfo_usr_dlg_.Instance.Warn_many("", "", "column not added to table: db=~{0} tbl=~{1} fld=~{2} err=~{3}", conn_info.Database(), tbl, fld.Name(), ErrUtl.ToStrFull(e));
		}
		this.Meta_mgr().Load_all();
	}
	public boolean Meta_tbl_exists(String tbl)					{return false;}
	public boolean	Meta_fld_exists(String tbl, String fld)		{return false;}
	public boolean	Meta_idx_exists(String idx)					{return false;}
	public abstract Dbmeta_tbl_mgr Meta_mgr();
	public void Env_db_attach(String alias, Io_url db_url)	{}
	public void Env_db_attach(String alias, Db_conn db_url)	{}
	public void	Env_db_detach(String alias)					{}
	public DataRdr New_rdr(ResultSet rdr, String sql)		{return gplx.core.stores.Db_data_rdr_.new_(rdr, sql);}
	private Db_rdr New_rdr(Db_stmt stmt, Object rdr, String sql) {
		Db_rdr__basic rv = (Db_rdr__basic)New_rdr_clone();	
		rv.Ctor(stmt, (ResultSet)rdr, sql);	
		return rv;
	}
	public abstract Connection Conn_make();	
	private		void Batch_mgr__conn_bgn() {batch_mgr.Conn_bgn().Run(this);}
	private		void Batch_mgr__conn_end() {batch_mgr.Conn_end().Run(this);}
	private void Conn_assert() {
		this.connection = Conn_make();	// auto-open connection
		Batch_mgr__conn_bgn();
	}
		protected Connection connection;
	public void Conn_open() {connection = Conn_make();}
	public void Conn_term() {
		if (connection == null) return;	// connection never opened; just exit
		this.Batch_mgr__conn_end();
		try 	{connection.close();}
		catch 	(Exception e) {throw ErrUtl.NewArgs(e, "Conn_term failed", "url", conn_info.Raw());}
		connection = null;
	}
	@Override public Object Stmt_by_sql(String sql) {
		if (connection == null) Conn_assert();
		try 	{return connection.prepareStatement(sql);}
		catch 	(Exception e) {
			throw ErrUtl.NewArgs(e, "New_stmt_prep failed", "sql", sql);
			}
	}
	private Statement New_stmt_exec(String sql) {
		if (connection == null) Conn_assert();
		try 	{return connection.createStatement();}
		catch 	(Exception e) {throw ErrUtl.NewArgs(e, "New_stmt_exec failed", "sql", sql);}
	}
	protected Connection Conn_make_by_url(String url, String uid, String pwd) {
		try {return DriverManager.getConnection(url, uid, pwd);}
		catch (SQLException e) {throw ErrUtl.NewArgs(e, "connection open failed", "info", Conn_info().Raw());}
	}
	protected Connection Conn__new_by_url_and_props(String url, KeyVal... props) {
		try {
			java.util.Properties properties = new java.util.Properties();
			for (KeyVal prop : props)
				properties.setProperty(prop.KeyToStr(), prop.ValToStrOrEmpty());
			return DriverManager.getConnection(url, properties);
		}
		catch (SQLException e) {throw ErrUtl.NewArgs(e, "connection open failed", "info", Conn_info().Raw());}
	}
	}
