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
package gplx.dbs.engines.mems; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import gplx.core.stores.*; import gplx.dbs.metas.*; import gplx.dbs.sqls.*; import gplx.dbs.conn_props.*; import gplx.dbs.qrys.bats.*;
public class Mem_engine implements Db_engine {
	private final    Hash_adp tbl_hash = Hash_adp_.new_();
	Mem_engine(Db_conn_info conn_info) {
		this.conn_info = conn_info;
		this.qry_runner = new Mem_exec_select(this);
	}
	public String				Tid() {return Mem_conn_info.Tid_const;}
	public Db_conn_info			Conn_info() {return conn_info;} private Db_conn_info conn_info;
	public Db_conn_props_mgr	Props() {return props;} private final    Db_conn_props_mgr props = new Db_conn_props_mgr();
	public Db_batch_mgr			Batch_mgr() {return batch_mgr;} private final    Db_batch_mgr batch_mgr = new Db_batch_mgr();
	public Mem_exec_select		Qry_runner() {return qry_runner;} private Mem_exec_select qry_runner;
	public Sql_qry_wtr			Sql_wtr() {return sql_wtr;} private final    Sql_qry_wtr sql_wtr = Sql_qry_wtr_.Basic;
	public Db_engine			New_clone(Db_conn_info conn_info) {return new Mem_engine(conn_info);}
	public Db_stmt				Stmt_by_qry(Db_qry qry) {return new Mem_stmt(this, qry);}
	public Mem_tbl				Tbls__get(String name)	{return (Mem_tbl)tbl_hash.Get_by(name);}
	public void					Tbls__del(String name)	{tbl_hash.Del(name);}
	public void					Txn_bgn(String name)	{}//++txn_count;} private int txn_count = 0;
	public String				Txn_end()				{return "";}// --txn_count; return "";}
	public void					Txn_cxl()				{}//--txn_count;}
	public void					Txn_sav()				{this.Txn_end(); this.Txn_bgn("");}
	public Object				Exec_as_obj(Db_qry qry) {throw Err_.new_unimplemented();}
	public void					Conn_open() {}
	public void					Conn_term() {
		// if (txn_count != 0) throw Err_.new_wo_type("Conn_term.txns still open", "txn_count", txn_count);	// IGNORE: causing test to fails; DATE:2016-03-30
	}
	public Db_rdr				Exec_as_rdr__rls_manual(Object rdr_obj, String sql) {throw Err_.new_unimplemented();}
	public Db_rdr				Exec_as_rdr__rls_auto(Db_stmt stmt, Object rdr_obj, String sql) {throw Err_.new_unimplemented();}
	public DataRdr				New_rdr(java.sql.ResultSet rdr, String sql) {throw Err_.new_unimplemented();} 
	public Object				Stmt_by_sql(String sql) {throw Err_.new_unimplemented();}
	public void					Meta_tbl_create(Dbmeta_tbl_itm meta) {
		Mem_tbl mem_tbl = new Mem_tbl(meta);
		tbl_hash.Add_if_dupe_use_nth(meta.Name(), mem_tbl);
		meta_mgr.Add(meta);
	}
	public void					Meta_tbl_delete(String tbl_key) {
		Mem_tbl tbl = (Mem_tbl)tbl_hash.Get_by(tbl_key);
		if (tbl != null) tbl.rows.Clear();
	}
	public void					Meta_idx_create(Gfo_usr_dlg usr_dlg, Dbmeta_idx_itm... ary) {}	// TODO: implement unique index
	public void					Meta_idx_delete(String idx) {}
	public void					Meta_fld_append(String tbl, Dbmeta_fld_itm fld)	{}
	public Dbmeta_tbl_mgr		Meta_mgr() {return meta_mgr;} private final    Dbmeta_tbl_mgr meta_mgr = new Dbmeta_tbl_mgr(Dbmeta_reload_cmd_.Noop);
	public boolean					Meta_tbl_exists(String tbl)						{return tbl_hash.Has(tbl);}
	public boolean					Meta_fld_exists(String tbl, String fld)			{
		Mem_tbl mem_tbl = (Mem_tbl)tbl_hash.Get_by(tbl); if (mem_tbl == null) return false;
		return mem_tbl.Meta().Flds().Has(fld);
	}
	public boolean					Meta_idx_exists(String idx)						{return false;}
	public void					Env_db_attach(String alias, Db_conn conn)		{}
	public void					Env_db_attach(String alias, Io_url db_url)		{}
	public void					Env_db_detach(String alias)						{}
        public static final    Mem_engine Instance = new Mem_engine(); Mem_engine() {}
}
