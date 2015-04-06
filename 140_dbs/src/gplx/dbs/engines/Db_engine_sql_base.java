/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.dbs.engines; import gplx.*; import gplx.dbs.*;
import java.sql.*; 
import gplx.dbs.engines.*; import gplx.dbs.qrys.*; import gplx.dbs.sqls.*;
public abstract class Db_engine_sql_base implements Db_engine {
	@gplx.Internal protected void Ctor(Db_conn_info conn_info) {this.conn_info = conn_info;}
	public abstract String Tid();
	public Db_conn_info Conn_info() {return conn_info;} protected Db_conn_info conn_info;
	public abstract		Db_engine New_clone(Db_conn_info conn_info);
	public Db_rdr		New_rdr__rls_manual(Object rdr_obj, String sql)					{return New_rdr(null, rdr_obj, sql);}
	public Db_rdr		New_rdr__rls_auto(Db_stmt stmt, Object rdr_obj, String sql)	{return New_rdr(stmt, rdr_obj, sql);}
	@gplx.Virtual public Db_rdr New_rdr_clone() {return new Db_rdr__basic();}
	public Db_stmt		New_stmt_prep(Db_qry qry) {return new Db_stmt_cmd(this, qry);}
	@gplx.Virtual public void	Txn_bgn(String name)	{Exec_as_obj(Db_qry_sql.xtn_("BEGIN TRANSACTION;"));}
	@gplx.Virtual public void	Txn_end()				{Exec_as_obj(Db_qry_sql.xtn_("COMMIT TRANSACTION;"));}
	@gplx.Virtual public void	Txn_cxl()				{Exec_as_obj(Db_qry_sql.xtn_("ROLLBACK TRANSACTION;"));}
	@gplx.Virtual public void	Txn_sav()				{this.Txn_end(); this.Txn_bgn("");}
	public Object		Exec_as_obj(Db_qry qry) {
		if (qry.Tid() == Db_qry_.Tid_flush) return null;	// ignore flush (delete-db) statements
		String sql = this.SqlWtr().Xto_str(qry, false); // DBG: Tfds.Write(sql);
		return qry.Exec_is_rdr() ? (Object)this.Exec_as_rdr(sql) : this.Exec_as_int(sql);
	}
	protected int Exec_as_int(String sql) {
		try {
			Statement cmd = New_stmt_exec(sql);	
			return cmd.executeUpdate(sql);			
		}
		catch (Exception exc) {throw Err_.new_("db.engine:exec failed; url={0} sql={1} err={2}", conn_info.Xto_api(), sql, Err_.Message_gplx_brief(exc));}
	}
	private DataRdr Exec_as_rdr(String sql) {
		try {
			Statement cmd = New_stmt_exec(sql);	
			cmd.execute(sql);										
			ResultSet rdr = cmd.getResultSet();	
			return New_rdr(rdr, sql);
		}
		catch (Exception exc) {throw Err_.new_("db.engine:rdr failed; url={0} sql={1} err={2}", conn_info.Xto_api(), sql, Err_.Message_gplx_brief(exc));}
	}
	public void Ddl_create_tbl(Db_meta_tbl tbl) {Exec_as_int(tbl.To_sql_create());}
	public void Ddl_create_idx(Gfo_usr_dlg usr_dlg, Db_meta_idx... ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Db_meta_idx idx = ary[i];
			usr_dlg.Plog_many("", "", "creating database index (please wait); db=~{0} idx=~{1}", conn_info.Database(), idx.Name());
			Exec_as_int(idx.To_sql_create());
		}
	}
	public void Ddl_append_fld(String tbl, Db_meta_fld fld)	{Exec_as_int(Db_sqlbldr__sqlite.I.Bld_alter_tbl_add(tbl, fld));}
	public void Ddl_delete_tbl(String tbl)						{Exec_as_int(Db_sqlbldr__sqlite.I.Bld_drop_tbl(tbl));}
	@gplx.Virtual public void Env_db_attach(String alias, Io_url db_url) {}
	@gplx.Virtual public void	Env_db_detach(String alias) {}
	@gplx.Virtual public DataRdr New_rdr(ResultSet rdr, String sql) {return gplx.stores.Db_data_rdr_.new_(rdr, sql);}
	@gplx.Virtual public Sql_qry_wtr SqlWtr() {return Sql_qry_wtr_.new_ansi();}
	private Db_rdr New_rdr(Db_stmt stmt, Object rdr, String sql) {
		Db_rdr__basic rv = (Db_rdr__basic)New_rdr_clone();	
		rv.Ctor(stmt, (ResultSet)rdr, sql);	
		return rv;
	}
	@gplx.Internal protected abstract Connection Conn_new();	
		protected Connection connection;
	public void Conn_open() {connection = Conn_new();}
	public void Conn_term() {
		if (connection == null) return;	// connection never opened; just exit
		try 	{connection.close();}
		catch 	(Exception e) {throw Err_.err_(e, "Conn_term.fail; url={0} err={1}", conn_info.Xto_raw(), Err_.Message_lang(e));}
		connection = null;
	}
	public Object New_stmt_prep_as_obj(String sql) {
		if (connection == null) connection = Conn_new();	// auto-open connection
		try 	{return connection.prepareStatement(sql);}
		catch 	(Exception e) {throw Err_.err_(e, "New_stmt_prep.fail; sql={0} err={1}", sql, Err_.Message_lang(e));}
	}
	private Statement New_stmt_exec(String sql) {
		if (connection == null) connection = Conn_new();	// auto-open connection
		try 	{return connection.createStatement();}
		catch 	(Exception e) {throw Err_.err_(e, "New_stmt_exec.fail; sql={0} err={1}", sql, Err_.Message_lang(e));}
	}
	protected Connection Conn_make_by_url(String url, String uid, String pwd) {
		try {return DriverManager.getConnection(url, uid, pwd);}
		catch (SQLException e) {throw Err_.err_(e, "connection open failed").Add("ConnectInfo", Conn_info().Xto_raw());}
	}
	}
