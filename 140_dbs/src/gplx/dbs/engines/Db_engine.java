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
public interface Db_engine {
	String			Tid();
	Db_url			Url();
	Db_engine		New_clone(Db_url url);
	Db_rdr			New_rdr_by_obj(Object o, String sql);		// Object o:ResultSet if desktop; Cursor if android
	Db_stmt			New_stmt_prep(Db_qry qry);
	Object			New_stmt_prep_as_obj(String sql);
	DataRdr			New_rdr(java.sql.ResultSet rdr, String sql); 
	void			Txn_bgn();
	void			Txn_end();
	void			Conn_open();
	void			Conn_term();
	Object			Exec_as_obj(Db_qry qry);
	void			Exec_ddl_create_tbl(Db_meta_tbl meta);
	void			Exec_ddl_create_idx(Gfo_usr_dlg usr_dlg, Db_meta_idx... ary);
	void			Exec_ddl_append_fld(String tbl, Db_meta_fld fld);
	void			Exec_env_db_attach(String alias, Io_url db_url);
	void			Exec_env_db_detach(String alias);
}
