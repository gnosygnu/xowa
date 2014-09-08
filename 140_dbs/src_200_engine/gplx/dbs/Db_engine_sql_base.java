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
package gplx.dbs; import gplx.*;
import java.sql.*; 
abstract class Db_engine_sql_base implements Db_engine {
	public abstract String Key();
	public Db_conn_info Conn_info() {return conn_info;} protected Db_conn_info conn_info;
	@gplx.Virtual public void Txn_bgn() {Execute(Db_qry_sql.xtn_("BEGIN TRANSACTION;"));}
	@gplx.Virtual public void Txn_end() {Execute(Db_qry_sql.xtn_("COMMIT TRANSACTION;"));}
	public abstract Db_engine Make_new(Db_conn_info conn_info);
	@gplx.Virtual public Sql_cmd_wtr SqlWtr() {return Sql_cmd_wtr_.Ansi;}
	public Object Execute(Db_qry cmd) {
		Db_qryWkr wkr = (Db_qryWkr)wkrs.FetchOrFail(cmd.KeyOfDb_qry());
		return wkr.Exec(this, cmd);
	}
	@gplx.Internal protected void ctor_SqlEngineBase(Db_conn_info conn_info) {
		this.conn_info = conn_info;
		wkrs.Add(Db_qry_select.KeyConst, new ExecSqlWkr());
		wkrs.Add(Db_qry_insert.KeyConst, new ExecSqlWkr());
		wkrs.Add(Db_qry_update.KeyConst, new ExecSqlWkr());
		wkrs.Add(Db_qry_delete.KeyConst, new ExecSqlWkr());
		wkrs.Add(Db_qry_sql.KeyConst, new ExecSqlWkr());
		wkrs.Add(Db_qry_flush.KeyConst, Db_qryWkr_.Null);
	}	HashAdp wkrs = HashAdp_.new_();
	@gplx.Internal @gplx.Virtual protected int ExecuteNonQuery(String sql) {
		try {
			Statement cmd = NewDbCmd(sql);	
			return cmd.executeUpdate(sql);	
		}
		catch (Exception exc) {throw Err_.err_(exc, "exec nonQuery failed").Add("sql", sql).Add("err", Err_.Message_gplx_brief(exc));}
	}
	@gplx.Internal @gplx.Virtual protected DataRdr ExecuteReader(String sql) {
		try {
			Statement cmd = NewDbCmd(sql);			
			cmd.execute(sql);										
			ResultSet rdr = cmd.getResultSet();	
			return NewDataRdr(rdr, sql);
		}
		catch (Exception exc) {throw Err_.err_(exc, "exec reader failed").Add("sql", sql).Add("err", Err_.Message_gplx_brief(exc));}
	}
	@gplx.Internal protected abstract Connection Conn_new();	
	@gplx.Virtual public DataRdr NewDataRdr(ResultSet rdr, String sql) {return gplx.stores.Db_data_rdr_.new_(rdr, sql);}
	public Db_rdr	New_db_rdr(Object rdr, String sql) {return new Db_rdr__basic((ResultSet)rdr, sql);}
	public Db_stmt	New_db_stmt(Db_provider provider, Db_qry qry) {return new Db_stmt_cmd(provider, qry);}
		public Object New_db_cmd(String sql) {
		try {return connection.prepareStatement(sql);}
		catch (Exception e) {
			throw Err_.err_(e, "failed to prepare sql; sql={0}", sql);}
	}
	public void Conn_open() {
		connection = Conn_new();
	}	private Connection connection;
	public void Conn_term() {
//		if (Env_.Mode_testing()) return;	// WORKAROUND:MYSQL:else errors randomly when running all tests. possible connection pooling issue (?); // commented out 2013-08-22
		try 	{connection.close();}
		catch 	(SQLException e) {throw Err_.err_(e, "close connection failed").Add("ConnectInfo", conn_info.Str_raw());}
	}
	Statement NewDbCmd(String commandText) {
		Statement cmd = null;
		try 	{cmd = connection.createStatement();}
		catch 	(SQLException e) {throw Err_.err_(e, "could not create statement").Add("commandText", commandText).Add("e", Err_.Message_lang(e));}
		return cmd;
	}
	protected Connection NewDbCon(String url, String uid, String pwd) {
		try {return DriverManager.getConnection(url, uid, pwd);}
		catch (SQLException e) {throw Err_.err_(e, "connection open failed").Add("ConnectInfo", Conn_info().Str_raw());}
	}
	}
