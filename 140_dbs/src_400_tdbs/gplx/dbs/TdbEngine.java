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
class TdbEngine implements Db_engine {
	public String Key() {return KeyDef;} public static final String KeyDef = "tdb";
	public Db_conn_info Conn_info() {return conn_info;} Db_conn_info conn_info;
	public TdbDatabase Db() {return db;} TdbDatabase db;
	public void Conn_open() {
		String urlStr = (String)conn_info.Server();
		db = loadMgr.LoadTbls(Io_url_.new_any_(urlStr));
	}
	public void Conn_term() {}
	public void Txn_bgn() {}
	public void Txn_end() {}
	public Db_engine Make_new(Db_conn_info conn_info) {
		TdbEngine rv = new TdbEngine();
		rv.CtorTdbEngine(conn_info);
		return rv;
	}
	public Object Execute(Db_qry cmd) {
		Db_qryWkr wkr = (Db_qryWkr)wkrs.FetchOrFail(cmd.KeyOfDb_qry());
		return wkr.Exec(this, cmd);
	}
	public DataRdr NewDataRdr(java.sql.ResultSet rdr, String sql) {return DataRdr_.Null;} 
	public Db_stmt New_db_stmt(Db_provider provider, Db_qry qry) {return new Db_stmt_sql().Parse(Sql_cmd_wtr_.Ansi.XtoSqlQry(qry, true));}
	public Object New_db_cmd(String sql) {throw Err_.not_implemented_();}
	public Db_rdr	New_db_rdr(Object o, String sql) {return Db_rdr_.Null;}
	public TdbTable FetchTbl(String name) {
		TdbTable tbl = db.Tables().FetchOrFail(name);
		if (!tbl.IsLoaded()) loadMgr.LoadTbl(db, tbl);
		return tbl;
	}
	public void FlushAll() {
		saveMgr.SaveDb(db);
	}
	public void FlushTbl(TdbTable tbl) {
		saveMgr.SaveFile(db, tbl.File());
	}

	HashAdp wkrs = HashAdp_.new_(); TdbDbLoadMgr loadMgr = TdbDbLoadMgr.new_(); TdbDbSaveMgr saveMgr = TdbDbSaveMgr.new_();
	@gplx.Internal protected static final TdbEngine _ = new TdbEngine(); 
	//TdbEngine() {this.conn_info = TdbConnectUrl._;}
	void CtorTdbEngine(Db_conn_info conn_info) {
		this.conn_info = conn_info;
		wkrs.Add(Db_qry_select.KeyConst, TdbSelectWkr._);
		wkrs.Add(Db_qry_insert.KeyConst, TdbInsertWkr.new_());
		wkrs.Add(Db_qry_update.KeyConst, TdbUpdateWkr.new_());
		wkrs.Add(Db_qry_delete.KeyConst, TdbDeleteWkr.new_());
		wkrs.Add(Db_qry_flush.KeyConst, TdbFlushWkr.new_());
	}
	public static TdbEngine as_(Object obj) {return obj instanceof TdbEngine ? (TdbEngine)obj : null;}
	public static TdbEngine cast_(Object obj) {try {return (TdbEngine)obj;} catch(Exception exc) {throw Err_.type_mismatch_exc_(exc, TdbEngine.class, obj);}}
}
