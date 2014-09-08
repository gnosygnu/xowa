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
public class Db_qry_sql implements Db_qry {
	public int Tid() {return Db_qry_.Tid_basic;}
	public String KeyOfDb_qry() {return KeyConst;} public static final String KeyConst = "SQL";
	public boolean ExecRdrAble() {return isReader;} private boolean isReader;
	public int Exec_qry(Db_provider provider) {return provider.Exec_qry(this);}
	public String XtoSql() {return sql;} private String sql;		
	public static Db_qry_sql dml_(String sql) {return sql_(sql);}
	public static Db_qry_sql ddl_(String sql) {return sql_(sql);}
	public static Db_qry_sql xtn_(String sql) {return sql_(sql);}
	static Db_qry_sql sql_(String sql) {
		Db_qry_sql rv = new Db_qry_sql();
		rv.sql = sql; rv.isReader = false;
		return rv;
	}
	public static Db_qry_sql rdr_(String sql) {
		Db_qry_sql rv = new Db_qry_sql();
		rv.sql = sql; rv.isReader = true;
		return rv;
	}
	public static Db_qry_sql as_(Object obj) {return obj instanceof Db_qry_sql ? (Db_qry_sql)obj : null;}
	public static Db_qry_sql cast_(Object obj) {try {return (Db_qry_sql)obj;} catch(Exception exc) {throw Err_.type_mismatch_exc_(exc, Db_qry_sql.class, obj);}}
}
