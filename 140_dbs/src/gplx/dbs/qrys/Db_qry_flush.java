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
package gplx.dbs.qrys; import gplx.*; import gplx.dbs.*;
import gplx.dbs.sqls.*;
public class Db_qry_flush implements Db_qry {
	public int			Tid() {return Db_qry_.Tid_flush;}
	public boolean			Exec_is_rdr() {return false;}
	public String		Base_table() {return tableNames[0];}
	public String		Xto_sql() {return Sql_qry_wtr_.I.Xto_str(this, false);}		
	public int Exec_qry(Db_conn conn) {return conn.Exec_qry(this);}

	public String[] TableNames() {return tableNames;} private String[] tableNames;
	

	public static Db_qry_flush as_(Object obj) {return obj instanceof Db_qry_flush ? (Db_qry_flush)obj : null;}
	public static Db_qry_flush cast_(Object obj) {try {return (Db_qry_flush)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, Db_qry_flush.class, obj);}}
	public static Db_qry_flush new_(String... ary) {
		Db_qry_flush rv = new Db_qry_flush();
		rv.tableNames = ary;
		return rv;
	}	Db_qry_flush() {}
}
