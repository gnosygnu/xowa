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
import gplx.core.stores.*; import gplx.dbs.metas.*; import gplx.dbs.sqls.*;
public interface Db_engine {
	String			Tid();
	Db_conn_info	Conn_info();
	Sql_qry_wtr		Sql_wtr();
	Db_engine		New_clone(Db_conn_info url);
	Db_rdr			New_rdr__rls_manual	(Object rdr_obj, String sql);				// Object o:ResultSet if desktop; Cursor if android
	Db_rdr			New_rdr__rls_auto	(Db_stmt stmt, Object rdr_obj, String sql);	// Object o:ResultSet if desktop; Cursor if android
	Db_stmt			New_stmt_prep(Db_qry qry);
	Object			New_stmt_prep_as_obj(String sql);
	DataRdr			New_rdr(java.sql.ResultSet rdr, String sql); 
	void			Txn_bgn(String name);
	String			Txn_end();
	void			Txn_cxl();
	void			Txn_sav();
	void			Conn_open();
	void			Conn_term();
	Object			Exec_as_obj(Db_qry qry);
	void			Ddl_create_tbl(Dbmeta_tbl_itm meta);
	void			Ddl_create_idx(Gfo_usr_dlg usr_dlg, Dbmeta_idx_itm... ary);
	void			Ddl_append_fld(String tbl, Dbmeta_fld_itm fld);
	void			Ddl_delete_tbl(String tbl);
	void			Env_db_attach(String alias, Io_url db_url);
	void			Env_db_detach(String alias);
	boolean			Meta_tbl_exists(String tbl);
	boolean			Meta_fld_exists(String tbl, String fld);
	Dbmeta_tbl_mgr	Meta_tbl_load_all();
}
