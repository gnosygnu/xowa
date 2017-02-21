/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.dbs.engines.sqlite; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import java.sql.*; 
import gplx.core.stores.*; import gplx.dbs.engines.*; import gplx.dbs.engines.sqlite.*; import gplx.dbs.metas.*; import gplx.dbs.sqls.*;
import gplx.dbs.qrys.*; 
import gplx.core.ios.IoItmFil;
import org.sqlite.SQLiteConnection;
public class Sqlite_engine extends Db_engine_sql_base {
	private final    Sqlite_txn_mgr txn_mgr; private final    Sqlite_schema_mgr schema_mgr;
	Sqlite_engine() {
		this.txn_mgr = new Sqlite_txn_mgr(this);
		this.schema_mgr = new Sqlite_schema_mgr(this);
	}
	@Override public String Tid() {return Sqlite_conn_info.Key_const;}
	@Override public gplx.dbs.sqls.Sql_qry_wtr Sql_wtr() {return Sql_qry_wtr_.New__sqlite();}
	@Override public Db_engine New_clone(Db_conn_info connectInfo) {
		Sqlite_engine rv = new Sqlite_engine();
		rv.Ctor(connectInfo);
		return rv;
	}
	@Override public DataRdr New_rdr(ResultSet rdr, String commandText) {return Sqlite_rdr.new_(rdr, commandText);}
	@Override public Db_rdr	New_rdr_clone() {return new Db_rdr__sqlite();}
	@Override public void	Env_db_attach(String alias, Db_conn conn)	{
		Db_conn_info cs_obj = conn.Conn_info(); if (!String_.Eq(cs_obj.Key(), Sqlite_conn_info.Key_const)) throw Err_.new_("dbs", "must attach to sqlite databases", "conn", cs_obj.Raw());
		Sqlite_conn_info cs = (Sqlite_conn_info)cs_obj;
		Env_db_attach(alias, cs.Url());
	}
	@Override public void			Env_db_attach(String alias, Io_url db_url)	{Exec_as_int(String_.Format("ATTACH '{0}' AS {1};", db_url.Raw(), alias));}
	@Override public void			Env_db_detach(String alias)					{Exec_as_int(String_.Format("DETACH {0};", alias));}
	@Override public void			Txn_bgn(String name)						{txn_mgr.Txn_bgn(name);}
	@Override public String			Txn_end()									{return txn_mgr.Txn_end();}
	@Override public void			Txn_cxl()									{txn_mgr.Txn_cxl();}
	@Override public void			Txn_sav()									{txn_mgr.Txn_sav();}
	@Override public Dbmeta_tbl_mgr	Meta_mgr()									{return schema_mgr.Tbl_mgr();}
	@Override public boolean			Meta_tbl_exists(String tbl)					{return schema_mgr.Tbl_exists(tbl);}
	@Override public boolean			Meta_fld_exists(String tbl, String fld)		{return schema_mgr.Fld_exists(tbl, fld);}
	@Override public boolean			Meta_idx_exists(String idx)					{return schema_mgr.Idx_exists(idx);}
	@Override public Db_stmt Stmt_by_qry(Db_qry qry) {return new Sqlite_stmt(this, qry);}
		private static boolean loaded = false;
	protected void Meta_tbl_gather_hook() {throw Err_.new_unimplemented();}
	@gplx.Internal @Override protected Connection Conn_make() {
		if (!loaded) {
			try {
				Class.forName("org.sqlite.JDBC");
			}
			catch (ClassNotFoundException e) {throw Err_.new_exc(e, "db", "could not load sqlite jdbc driver");}
			loaded = true;					
		}
		
		// init vars for opening connection
		Sqlite_conn_info conn_info_as_sqlite = (Sqlite_conn_info)conn_info;
		Io_url sqlite_fs_url = conn_info_as_sqlite.Url();
		String sqlite_db_url = "jdbc:sqlite://" + String_.Replace(sqlite_fs_url.Raw(), "\\", "/");
		
		// set open_mode flag if conn is read-only; needed else all SELECT queries will be very slow; DATE:2016-09-03
		IoItmFil sqlite_fs_itm = Io_mgr.Instance.QueryFil(sqlite_fs_url);
		Keyval[] props = sqlite_fs_itm.Exists() && sqlite_fs_itm.ReadOnly()	// NOTE: must check if it exists; else missing-file will be marked as readonly connection, and missing-file will sometimes be dynamically created as read-write; DATE:2016-09-04
			? Keyval_.Ary(Keyval_.new_("open_mode", "1"))
			: Keyval_.Ary_empty;

		// open connection
		Connection rv = Conn__new_by_url_and_props(sqlite_db_url, props);
		
		// set busyTimeout; needed else multiple processes accessing same db can cause "database is locked" error; DATE:2016-09-03
		SQLiteConnection rv_as_sqlite = (org.sqlite.SQLiteConnection)rv;
		try {rv_as_sqlite.setBusyTimeout(10000);}
		catch (SQLException e) {Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to set busy timeout; err=~{0}", Err_.Message_gplx_log(e));}
		return rv;
	}
		public static final    Sqlite_engine Instance = new Sqlite_engine();
}
class Db_rdr__sqlite extends Db_rdr__basic {	@Override public byte Read_byte(String k)		{try {return (byte)Int_.cast(rdr.getObject(k));} catch (Exception e) {throw Err_.new_exc(e, "db", "read failed", "k", k, "type", Byte_.Cls_val_name);}} 
		@Override public boolean Read_bool_by_byte(String k) {
		try {
			int val = rdr.getInt(k);
			return val == 1;
		} 	catch (Exception e) {throw Err_.new_exc(e, "db", "read failed", "i", k, "type", Bool_.Cls_val_name);}
	}
	@Override public long Read_long(String k) {
		try {
			long val = rdr.getLong(k);
			Number n = (Number)val;
			return n.longValue();
		} 	catch (Exception e) {throw Err_.new_exc(e, "db", "read failed", "i", k, "type", Long_.Cls_val_name);}
	}
	@Override public float Read_float(String k) {
		try {
			Double val = (Double)rdr.getDouble(k);
			return val == null ? Float.NaN : val.floatValue();
		} 	catch (Exception e) {throw Err_.new_exc(e, "db", "read failed:", "i", k, "type", Float_.Cls_val_name);}
	}
	@Override public DateAdp Read_date_by_str(String k) {
		try {
			String val = rdr.getString(k);
			return val == null ? null : DateAdp_.parse_fmt(val, "yyyyMMdd_HHmmss");
		} 	catch (Exception e) {throw Err_.new_exc(e, "db", "read failed", "i", k, "type", DateAdp_.Cls_ref_type);}
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
	@Override public Decimal_adp ReadDecimal(String key) {return ReadDecimalOr(key, null);}
	@Override public Decimal_adp ReadDecimalOr(String key, Decimal_adp or) {
		Object val = Read(key);
		Double d = ((Double)val);
		return val == null ? or : Decimal_adp_.double_(d);
	}
	@Override public DateAdp ReadDate(String key) {return ReadDateOr(key, null);}
	@Override public DateAdp ReadDateOr(String key, DateAdp or) {
		Object val = Read(key);
		return val == null ? or : DateAdp_.parse_fmt((String)val, "M/dd/yyyy hh:mm tt");
	}
	@Override public boolean ReadBool(String key) {return ReadBoolOr(key, false);}
	@Override public boolean ReadBoolOr(String key, boolean or) {
		Object val = Read(key);
		return val == null ? or : Int_.cast(val) == 1;
	}
	@Override public byte ReadByte(String key) {return ReadByteOr(key, Byte_.Zero);}
	@Override public byte ReadByteOr(String key, byte or) {
		Object val = Read(key);
		return val == null ? or : (byte)Int_.cast(val);
	}
	@Override public long ReadLong(String key) {return ReadLongOr(key, Long_.Min_value);}
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
class Sqlite_stmt extends gplx.dbs.qrys.Db_stmt_cmd {	public Sqlite_stmt(Db_engine engine, Db_qry qry) {super(engine, qry);}
	@Override protected Db_stmt Add_date(boolean where, String k, DateAdp v) {
		if (k == Dbmeta_fld_itm.Key_null) return this;	// key is explicitly null; ignore; allows version_2+ type definitions
		return super.Add_str(where, k, v.XtoStr_fmt_iso_8561());
	}
}
