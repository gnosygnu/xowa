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
 package gplx.dbs.engines.noops; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import gplx.core.stores.*; import gplx.dbs.metas.*; import gplx.dbs.sqls.*; import gplx.dbs.conn_props.*; import gplx.dbs.qrys.bats.*;
public class Noop_engine implements Db_engine {
	public String				Tid() {return Noop_conn_info.Tid_const;}
	public Db_conn_info			Conn_info() {return Db_conn_info_.Null;}
	public Db_conn_props_mgr	Props() {return props;} private final    Db_conn_props_mgr props = new Db_conn_props_mgr();
	public Db_batch_mgr			Batch_mgr() {return batch_mgr;} private final    Db_batch_mgr batch_mgr = new Db_batch_mgr();
	public Sql_qry_wtr			Sql_wtr() {return sql_wtr;} private final    Sql_qry_wtr sql_wtr = Sql_qry_wtr_.Basic;
	public void					Conn_open() {}
	public void					Conn_term() {}
	public Db_engine			New_clone(Db_conn_info url) {return this;}
	public Db_rdr				Exec_as_rdr__rls_manual	(Object rdr_obj, String sql)				{return Db_rdr_.Empty;}
	public Db_rdr				Exec_as_rdr__rls_auto	(Db_stmt stmt, Object rdr_obj, String sql)	{return Db_rdr_.Empty;}
	public Db_stmt				Stmt_by_qry(Db_qry qry) {return Db_stmt_.Null;}
	public Object				Stmt_by_sql(String sql) {throw Err_.new_unimplemented();}
	public DataRdr				New_rdr(java.sql.ResultSet rdr, String sql) {return DataRdr_.Null;} 
	public void					Txn_bgn(String name)	{}
	public String				Txn_end()				{return "";}
	public void					Txn_cxl()				{}
	public void					Txn_sav()				{}
	public Object				Exec_as_obj(Db_qry cmd) {return cmd.Exec_is_rdr() ? (Object)DataRdr_.Null : -1;}
	public void					Meta_tbl_create(Dbmeta_tbl_itm meta) {}
	public void					Meta_tbl_delete(String tbl)						{}
	public void					Meta_idx_create(Gfo_usr_dlg usr_dlg, Dbmeta_idx_itm... ary) {}
	public void					Meta_idx_delete(String idx) {}
	public void					Meta_fld_append(String tbl, Dbmeta_fld_itm fld)	{}
	public void					Env_db_attach(String alias, Db_conn conn)		{}
	public void					Env_db_attach(String alias, Io_url db_url)		{}
	public void					Env_db_detach(String alias)						{}
	public boolean					Meta_tbl_exists(String tbl)						{return false;}
	public boolean					Meta_fld_exists(String tbl, String fld)			{return false;}
	public boolean					Meta_idx_exists(String idx)						{return false;}
	public Dbmeta_tbl_mgr		Meta_mgr()										{return meta_tbl_mgr;} private final    Dbmeta_tbl_mgr meta_tbl_mgr = new Dbmeta_tbl_mgr(Dbmeta_reload_cmd_.Noop);
        public static final    Noop_engine Instance = new Noop_engine(); Noop_engine() {}
}
