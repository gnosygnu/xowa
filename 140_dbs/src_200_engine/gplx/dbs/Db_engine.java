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
public interface Db_engine {
	String			Tid();
	Db_url	Url();
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
}
class Db_engine_null implements Db_engine {
	public String			Tid() {return Db_url__null.Tid_const;}
	public Db_url			Url() {return Db_url_.Null;}
	public void				Conn_open() {}
	public void				Conn_term() {}
	public Db_engine		New_clone(Db_url url) {return this;}
	public Db_rdr			New_rdr_by_obj(Object o, String sql) {return Db_rdr_.Null;}
	public Db_stmt			New_stmt_prep(Db_qry qry) {return Db_stmt_.Null;}
	public Object			New_stmt_prep_as_obj(String sql) {throw Err_.not_implemented_();}
	public DataRdr			New_rdr(java.sql.ResultSet rdr, String sql) {return DataRdr_.Null;} 
	public void				Txn_bgn() {}
	public void				Txn_end() {}
	public Object			Exec_as_obj(Db_qry cmd) {return cmd.Exec_is_rdr() ? (Object)DataRdr_.Null : -1;}
        public static final Db_engine_null _ = new Db_engine_null(); Db_engine_null() {}
}
