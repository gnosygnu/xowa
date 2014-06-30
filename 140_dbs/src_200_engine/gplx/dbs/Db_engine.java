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
interface Db_engine extends RlsAble {
	String Key();
	Db_connect ConnectInfo();
	void Connect();
	Object Execute(Db_qry cmd);
	DataRdr NewDataRdr(java.sql.ResultSet rdr, String sql); 
	Db_stmt New_db_stmt(Db_provider provider, Db_qry sql);
	Object New_db_cmd(String sql);
	void Txn_bgn();
	void Txn_end();
	Db_engine MakeEngine(Db_connect connectInfo);
}
class Db_engine_null implements Db_engine {
	public String Key() {return Db_connect_.Null.Key_of_db_connect();}
	public Db_connect ConnectInfo() {return Db_connect_.Null;}
	public void Connect() {}
	public Object Execute(Db_qry cmd) {return cmd.ExecRdrAble() ? (Object)DataRdr_.Null : -1;}
	public Object New_db_cmd(String sql) {throw Err_.not_implemented_();}
	public DataRdr NewDataRdr(java.sql.ResultSet rdr, String sql) {return DataRdr_.Null;} 
	public Db_stmt New_db_stmt(Db_provider provider, Db_qry qry) {return Db_stmt_.Null;}
	public void Txn_bgn() {}
	public void Txn_end() {}
	public void Rls() {}
	public Db_engine MakeEngine(Db_connect connectInfo) {return this;}
        public static final Db_engine_null _ = new Db_engine_null(); Db_engine_null() {}
}
class ExecSqlWkr implements Db_qryWkr {
	public Object Exec(Db_engine engineObj, Db_qry cmd) {
		Db_engine_sql_base engine = (Db_engine_sql_base)engineObj;
		String sql = engine.SqlWtr().XtoSqlQry(cmd, false); // Tfds.Write(sql);
		return cmd.ExecRdrAble() ? (Object)engine.ExecuteReader(sql) : engine.ExecuteNonQuery(sql);
	}
}
