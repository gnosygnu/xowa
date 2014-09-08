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
	String			Key();
	Db_conn_info	Conn_info();
	void			Conn_open();
	void			Conn_term();
	void			Txn_bgn();
	void			Txn_end();
	Db_engine		Make_new(Db_conn_info conn_info);

	Db_rdr			New_db_rdr(Object o, String sql);		// ResultSet if desktop; Cursor if android
	Db_stmt			New_db_stmt(Db_provider provider, Db_qry sql);
	Object			Execute(Db_qry cmd);
	DataRdr			NewDataRdr(java.sql.ResultSet rdr, String sql); 
	Object			New_db_cmd(String sql);
}
class Db_engine_null implements Db_engine {
	public String			Key() {return Db_conn_info_.Null.Key();}
	public Db_conn_info		Conn_info() {return Db_conn_info_.Null;}
	public void				Conn_open() {}
	public void				Conn_term() {}
	public void				Txn_bgn() {}
	public void				Txn_end() {}
	public Db_engine		Make_new(Db_conn_info conn_info) {return this;}

	public Object Execute(Db_qry cmd) {return cmd.ExecRdrAble() ? (Object)DataRdr_.Null : -1;}
	public Object	New_db_cmd(String sql) {throw Err_.not_implemented_();}
	public DataRdr	NewDataRdr(java.sql.ResultSet rdr, String sql) {return DataRdr_.Null;} 
	public Db_rdr	New_db_rdr(Object o, String sql) {return Db_rdr_.Null;}
	public Db_stmt	New_db_stmt(Db_provider provider, Db_qry qry) {return Db_stmt_.Null;}
        public static final Db_engine_null _ = new Db_engine_null(); Db_engine_null() {}
}
class ExecSqlWkr implements Db_qryWkr {
	public Object Exec(Db_engine engineObj, Db_qry cmd) {
		Db_engine_sql_base engine = (Db_engine_sql_base)engineObj;
		String sql = engine.SqlWtr().XtoSqlQry(cmd, false); // Tfds.Write(sql);
		return cmd.ExecRdrAble() ? (Object)engine.ExecuteReader(sql) : engine.ExecuteNonQuery(sql);
	}
}
