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
import gplx.stores.*;
import java.sql.*; 
class Sqlite_engine extends Db_engine_sql_base {
	@Override public String Key() {return Db_conn_info__sqlite.Key_const;}
	@Override public Db_engine Make_new(Db_conn_info connectInfo) {
		Sqlite_engine rv = new Sqlite_engine();
		rv.ctor_SqlEngineBase(connectInfo);
		return rv;
	}
	@Override public DataRdr NewDataRdr(ResultSet rdr, String commandText) {return Sqlite_rdr.new_(rdr, commandText);}
		static boolean loaded = false; 
	@gplx.Internal @Override protected Connection Conn_new() {
		if (!loaded) {
			try {
				Class.forName("org.sqlite.JDBC");
			}
			catch (ClassNotFoundException e) {throw Err_.new_("could not load sqlite jdbc driver");}
			loaded = true;					
		}
		Db_conn_info__sqlite connUrl = (Db_conn_info__sqlite)conn_info;
		return NewDbCon("jdbc:sqlite://" + String_.Replace(connUrl.Database(), "\\", "/"), "", "");
	}
	private boolean pragma_needed = true; 
	@Override public void Txn_bgn() {
//		Execute(Db_qry_sql.xtn_("PRAGMA ENCODING=\"UTF-8\";"));
//		Execute(Db_qry_sql.xtn_("PRAGMA journal_mode = OFF;"));	// will cause out of memory
//		Execute(Db_qry_sql.xtn_("PRAGMA journal_mode = MEMORY;"));
		if (pragma_needed) {
			Execute(Db_qry_sql.xtn_("PRAGMA synchronous = OFF;"));
			pragma_needed = false;
		}
//		Execute(Db_qry_sql.xtn_("PRAGMA temp_store = MEMORY;"));
//		Execute(Db_qry_sql.xtn_("PRAGMA locking_mode = EXCLUSIVE;"));
//		Execute(Db_qry_sql.xtn_("PRAGMA cache_size=4000;"));	// too many will also cause out of memory		
		Execute(Db_qry_sql.xtn_("BEGIN TRANSACTION;"));
	}
		@gplx.Internal protected static final Sqlite_engine _ = new Sqlite_engine(); Sqlite_engine() {}
}
class Sqlite_rdr extends Db_data_rdr {		@Override public float ReadFloat(String key) {return ReadFloatOr(key, Float.NaN);}
	@Override public float ReadFloatOr(String key, float or) {
		Object val = Read(key);
		Double d = ((Double)val);
		return val == null ? or : d.floatValue();
	}
	@Override public DecimalAdp ReadDecimal(String key) {return ReadDecimalOr(key, null);}
	@Override public DecimalAdp ReadDecimalOr(String key, DecimalAdp or) {
		Object val = Read(key);
		Double d = ((Double)val);
		return val == null ? or : DecimalAdp_.double_(d);
	}
	@Override public DateAdp ReadDate(String key) {return ReadDateOr(key, null);}
	@Override public DateAdp ReadDateOr(String key, DateAdp or) {
		Object val = Read(key);
		return val == null ? or : DateAdp_.parse_fmt((String)val, "M/dd/yyyy hh:mm tt");
	}
	@Override public boolean ReadBool(String key) {return ReadBoolOr(key, false);}
	@Override public boolean ReadBoolOr(String key, boolean or) {
		Object val = Read(key);
		return val == null ? or : Int_.cast_(val) == 1;
	}
	@Override public byte ReadByte(String key) {return ReadByteOr(key, Byte_.Zero);}
	@Override public byte ReadByteOr(String key, byte or) {
		Object val = Read(key);
		return val == null ? or : (byte)Int_.cast_(val);
	}
	@Override public long ReadLong(String key) {return ReadLongOr(key, Long_.MinValue);}
	@Override public long ReadLongOr(String key, long or) {
		Object val = Read(key);
		if (val == null) return or;
		Number n = (Number)val;
		return n.longValue();
	}
		public static Sqlite_rdr new_(ResultSet rdr, String commandText) {
		Sqlite_rdr rv = new Sqlite_rdr();
		rv.ctor_db_data_rdr(rdr, commandText);
		return rv;
	}	Sqlite_rdr() {}
}
