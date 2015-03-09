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
	@gplx.Internal protected void Ctor(Db_url url) {this.url = url;}
	public abstract String Tid();
	public Db_url Url() {return url;} protected Db_url url;
	public abstract		Db_engine New_clone(Db_url url);
	public Db_rdr		New_rdr_by_obj(Object rdr, String sql) {
		Db_rdr__basic rv = (Db_rdr__basic)New_rdr_clone();	
		rv.Ctor((ResultSet)rdr, sql);	
		return rv;
	}
	@gplx.Virtual public Db_rdr New_rdr_clone() {return new Db_rdr__basic();}
	public Db_stmt		New_stmt_prep(Db_qry qry) {return new Db_stmt_cmd(this, qry);}
	public void			Txn_bgn() {Exec_as_obj(Db_qry_sql.xtn_("BEGIN TRANSACTION;"));}
	public void			Txn_end() {Exec_as_obj(Db_qry_sql.xtn_("COMMIT TRANSACTION;"));}
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
		catch (Exception exc) {throw Err_.err_(exc, "exec nonQuery failed").Add("sql", sql).Add("err", Err_.Message_gplx_brief(exc));}
	}
	private DataRdr Exec_as_rdr(String sql) {
		try {
			Statement cmd = New_stmt_exec(sql);	
			cmd.execute(sql);										
			ResultSet rdr = cmd.getResultSet();	
			return New_rdr(rdr, sql);
		}
		catch (Exception exc) {throw Err_.err_(exc, "exec reader failed").Add("sql", sql).Add("err", Err_.Message_gplx_brief(exc));}
	}
	public void Exec_ddl_create_tbl(Db_meta_tbl meta) {Exec_as_int(meta.To_sql_create());}
	public void Exec_ddl_create_idx(Gfo_usr_dlg usr_dlg, Db_meta_idx... ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Db_meta_idx idx = ary[i];
			usr_dlg.Plog_many("", "", "db.idx.create; db=~{0} idx=~{1}", url.Database(), idx.Name());
			Exec_as_int(idx.To_sql_create());
		}
	}
	public void Exec_ddl_append_fld(String tbl, Db_meta_fld fld) {
		Exec_as_int(Db_sqlbldr__sqlite.I.Bld_alter_tbl_add(tbl, fld));
	}
	@gplx.Virtual public void Exec_env_db_attach(String alias, Io_url db_url) {}
	@gplx.Virtual public void	Exec_env_db_detach(String alias) {}
	@gplx.Virtual public DataRdr New_rdr(ResultSet rdr, String sql) {return gplx.stores.Db_data_rdr_.new_(rdr, sql);}
	@gplx.Virtual public Sql_qry_wtr SqlWtr() {return Sql_qry_wtr_.new_ansi();}
	@gplx.Internal protected abstract Connection Conn_new();	
		private Connection connection;
	public void Conn_open() {connection = Conn_new();}
	public void Conn_term() {
		if (connection == null) return;	// connection never opened; just exit
		try 	{connection.close();}
		catch 	(Exception e) {throw Err_.err_(e, "Conn_term.fail; url={0} err={1}", url.Xto_raw(), Err_.Message_lang(e));}
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
		catch (SQLException e) {throw Err_.err_(e, "connection open failed").Add("ConnectInfo", Url().Xto_raw());}
	}
	}
