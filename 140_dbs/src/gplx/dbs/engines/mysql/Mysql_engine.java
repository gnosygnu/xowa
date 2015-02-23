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
package gplx.dbs.engines.mysql; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import gplx.stores.*; import gplx.dbs.engines.*; import gplx.dbs.sqls.*;
import java.sql.*; 
public class Mysql_engine extends Db_engine_sql_base {
	@Override public String Tid() {return Mysql_url.Tid_const;}
	@Override public Sql_qry_wtr SqlWtr() {return Sql_qry_wtr_.new_escape_backslash();}
	@Override public Db_engine New_clone(Db_url connectInfo) {
		Mysql_engine rv = new Mysql_engine();
		rv.Ctor(connectInfo);
		return rv;
	}
	@Override public DataRdr New_rdr(ResultSet rdr, String commandText) {return Mysql_rdr.new_(rdr, commandText);}
		@gplx.Internal @Override protected Connection Conn_new() {
		Mysql_url url_as_mysql = (Mysql_url)url; 
		return Conn_make_by_url("jdbc:mysql://localhost/" + url_as_mysql.Database() + "?characterEncoding=UTF8", url_as_mysql.Uid(), url_as_mysql.Pwd());
	}
		public static final Mysql_engine _ = new Mysql_engine(); Mysql_engine() {}
}
class Mysql_rdr extends Db_data_rdr {
		//PATCH:MYSQL:byte actually returned as int by Jdbc ResultSet (or MYSQL impmentation); convert to byte 
	@Override public byte ReadByte(String key) {return ReadByteOr(key, Byte.MAX_VALUE);}
	@Override public byte ReadByteOr(String key, byte or) {
		Object val = Read(key);
		return val == null ? or : ((Integer)val).byteValue();
	}
	public static Mysql_rdr new_(ResultSet rdr, String commandText) {
		Mysql_rdr rv = new Mysql_rdr();
		rv.ctor_db_data_rdr(rdr, commandText);
		return rv;
	}	Mysql_rdr() {}
}
