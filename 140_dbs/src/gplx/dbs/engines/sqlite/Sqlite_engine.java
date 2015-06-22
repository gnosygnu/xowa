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
package gplx.dbs.engines.sqlite; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import java.sql.*; 
import gplx.stores.*; import gplx.dbs.engines.*; import gplx.dbs.engines.sqlite.*;
import gplx.dbs.qrys.*; 
public class Sqlite_engine extends Db_engine_sql_base {
	private final Sqlite_txn_mgr txn_mgr; private final Sqlite_schema_mgr schema_mgr;
	Sqlite_engine() {
		this.txn_mgr = new Sqlite_txn_mgr(this);
		this.schema_mgr = new Sqlite_schema_mgr(this);
	}
	@Override public String Tid() {return Sqlite_conn_info.Tid_const;}
	@Override public Db_engine New_clone(Db_conn_info connectInfo) {
		Sqlite_engine rv = new Sqlite_engine();
		rv.Ctor(connectInfo);
		return rv;
	}
	@Override public DataRdr New_rdr(ResultSet rdr, String commandText) {return Sqlite_rdr.new_(rdr, commandText);}
	@Override public Db_rdr New_rdr_clone() {return new Db_rdr__sqlite();}
	@Override public void	Env_db_attach(String alias, Io_url db_url)	{Exec_as_int(String_.Format("ATTACH '{0}' AS {1};", db_url.Raw(), alias));}
	@Override public void	Env_db_detach(String alias)					{Exec_as_int(String_.Format("DETACH {0};", alias));}
	@Override public void	Txn_bgn(String name)	{txn_mgr.Txn_bgn(name);}
	@Override public void	Txn_end()				{txn_mgr.Txn_end();}
	@Override public void	Txn_cxl()				{txn_mgr.Txn_cxl();}
	@Override public void	Txn_sav()				{txn_mgr.Txn_sav();}
	@Override public boolean	Meta_tbl_exists(String tbl)				{return schema_mgr.Tbl_exists(tbl);}
	@Override public boolean	Meta_fld_exists(String tbl, String fld) {return schema_mgr.Fld_exists(tbl, fld);}
		static boolean loaded = false; 
	@gplx.Internal @Override protected Connection Conn_new() {
		if (!loaded) {
			try {
				Class.forName("org.sqlite.JDBC");
			}
			catch (ClassNotFoundException e) {throw Err_.new_("could not load sqlite jdbc driver");}
			loaded = true;					
		}
		Sqlite_conn_info conn_info_as_sqlite = (Sqlite_conn_info)conn_info;
		Connection rv = Conn_make_by_url("jdbc:sqlite://" + String_.Replace(conn_info_as_sqlite.Url().Raw(), "\\", "/"), "", "");
		return rv;
	}
		public static final Sqlite_engine _ = new Sqlite_engine();
}
class Db_rdr__sqlite extends Db_rdr__basic {	@Override public byte Read_byte(String k)		{try {return (byte)Int_.cast_(rdr.getObject(k));} catch (Exception e) {throw Err_.new_("read failed: k={0} type={1} err={2}", k, Byte_.Cls_val_name, Err_.Message_lang(e));}} 
		@Override public boolean Read_bool_by_byte(String k) {
		try {
			int val = rdr.getInt(k);
			return val == 1;
		} 	catch (Exception e) {throw Err_.new_("read failed: i={0} type={1} err={2}", k, Bool_.Cls_val_name, Err_.Message_lang(e));}
	}
	@Override public long Read_long(String k) {
		try {
			long val = rdr.getLong(k);
			Number n = (Number)val;
			return n.longValue();
		} 	catch (Exception e) {throw Err_.new_("read failed: i={0} type={1} err={2}", k, Long_.Cls_val_name, Err_.Message_lang(e));}
	}
	@Override public float Read_float(String k) {
		try {
			Double val = (Double)rdr.getDouble(k);
			return val == null ? Float.NaN : val.floatValue();
		} 	catch (Exception e) {throw Err_.new_("read failed: i={0} type={1} err={2}", k, Float_.Cls_val_name, Err_.Message_lang(e));}
	}
	@Override public DateAdp Read_date_by_str(String k) {
		try {
			String val = rdr.getString(k);
			return val == null ? null : DateAdp_.parse_fmt(val, "yyyyMMdd_HHmmss");
		} 	catch (Exception e) {throw Err_.new_("read failed: i={0} type={1} err={2}", k, DateAdp_.Cls_ref_type, Err_.Message_lang(e));}
	}
//	@Override public DecimalAdp ReadDecimalOr(String key, DecimalAdp or) {
//		Object val = Read(key);
//		Double d = ((Double)val);
//		return val == null ? null : DecimalAdp_.double_(d);
//	}
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
