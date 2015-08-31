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
package gplx.dbs.engines.tdbs; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import gplx.dbs.qrys.*; import gplx.dbs.sqls.*;
public class TdbEngine implements Db_engine {
	public String Tid() {return Tdb_conn_info.Tid_const;}
	public Db_conn_info Conn_info() {return conn_info;} private Db_conn_info conn_info;
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
	public Db_stmt	New_stmt_prep(Db_qry qry) {return new Db_stmt_sql().Parse(qry, Sql_qry_wtr_.I.Xto_str(qry, true));}
	public Object	New_stmt_prep_as_obj(String sql) {throw Err_.new_unimplemented();}
	public Db_rdr	New_rdr__rls_manual(Object rdr_obj, String sql) {return Db_rdr_.Empty;}
	public Db_rdr	New_rdr__rls_auto(Db_stmt stmt, Object rdr_obj, String sql) {return Db_rdr_.Empty;}
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
	public void	Ddl_create_tbl(Db_meta_tbl meta) {throw Err_.new_unimplemented();}
	public void Ddl_create_idx(Gfo_usr_dlg usr_dlg, Db_meta_idx... ary) {throw Err_.new_unimplemented();}
	public void			Ddl_append_fld(String tbl, Db_meta_fld fld) {throw Err_.new_unimplemented();}
	public void			Ddl_delete_tbl(String tbl)						{}
	public void			Env_db_attach(String alias, Io_url db_url)		{}
	public void			Env_db_detach(String alias)						{}
	public boolean			Meta_tbl_exists(String name)					{return false;}
	public boolean			Meta_fld_exists(String tbl, String fld)			{return false;}

	Hash_adp wkrs = Hash_adp_.new_(); TdbDbLoadMgr loadMgr = TdbDbLoadMgr.new_(); TdbDbSaveMgr saveMgr = TdbDbSaveMgr.new_();
	public static final TdbEngine _ = new TdbEngine(); 
	void CtorTdbEngine(Db_conn_info conn_info) {
		this.conn_info = conn_info;
		wkrs.Add(Db_qry_.Tid_select, TdbSelectWkr._);
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
	public static final Db_qryWkr Null = new Db_qryWrk_null();
}
class Db_qryWrk_null implements Db_qryWkr {
	public Object Exec(Db_engine engine, Db_qry cmd) {return null;}
}
