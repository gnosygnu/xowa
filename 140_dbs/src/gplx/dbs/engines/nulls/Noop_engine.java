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
 package gplx.dbs.engines.nulls; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import gplx.core.stores.*; import gplx.dbs.metas.*; import gplx.dbs.sqls.*;
public class Noop_engine implements Db_engine {
	public String			Tid() {return Noop_conn_info.Tid_const;}
	public Db_conn_info		Conn_info() {return Db_conn_info_.Null;}
	public Sql_qry_wtr		Sql_wtr() {return sql_wtr;} private final Sql_qry_wtr sql_wtr = Sql_qry_wtr_.Basic;
	public void				Conn_open() {}
	public void				Conn_term() {}
	public Db_engine		New_clone(Db_conn_info url) {return this;}
	public Db_rdr			New_rdr__rls_manual	(Object rdr_obj, String sql)				{return Db_rdr_.Empty;}
	public Db_rdr			New_rdr__rls_auto	(Db_stmt stmt, Object rdr_obj, String sql)	{return Db_rdr_.Empty;}
	public Db_stmt			New_stmt_prep(Db_qry qry) {return Db_stmt_.Null;}
	public Object			New_stmt_prep_as_obj(String sql) {throw Err_.new_unimplemented();}
	public DataRdr			New_rdr(java.sql.ResultSet rdr, String sql) {return DataRdr_.Null;} 
	public void				Txn_bgn(String name)	{}
	public String			Txn_end()				{return "";}
	public void				Txn_cxl()				{}
	public void				Txn_sav()				{}
	public Object			Exec_as_obj(Db_qry cmd) {return cmd.Exec_is_rdr() ? (Object)DataRdr_.Null : -1;}
	public void				Ddl_create_tbl(Dbmeta_tbl_itm meta) {}
	public void				Ddl_create_idx(Gfo_usr_dlg usr_dlg, Dbmeta_idx_itm... ary) {}
	public void				Ddl_append_fld(String tbl, Dbmeta_fld_itm fld)	{}
	public void				Ddl_delete_tbl(String tbl)						{}
	public void				Env_db_attach(String alias, Io_url db_url)		{}
	public void				Env_db_detach(String alias)						{}
	public boolean				Meta_tbl_exists(String tbl)						{return false;}
	public boolean				Meta_fld_exists(String tbl, String fld)			{return false;}
	public Dbmeta_tbl_mgr	Meta_tbl_load_all()								{return meta_tbl_mgr;} private final Dbmeta_tbl_mgr meta_tbl_mgr = new Dbmeta_tbl_mgr();
        public static final Noop_engine Instance = new Noop_engine(); Noop_engine() {}
}
