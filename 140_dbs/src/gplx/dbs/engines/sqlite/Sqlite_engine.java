/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.dbs.engines.sqlite;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.utls.ClassUtl;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateUtl;
import gplx.types.commons.GfoDecimal;
import gplx.types.commons.GfoDecimalUtl;
import gplx.types.basics.utls.FloatUtl;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.types.basics.utls.IntUtl;
import gplx.libs.files.Io_mgr;
import gplx.libs.files.Io_url;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.core.ios.IoItmFil;
import gplx.core.stores.DataRdr;
import gplx.core.stores.Db_data_rdr;
import gplx.dbs.Db_conn;
import gplx.dbs.Db_conn_info;
import gplx.dbs.Db_qry;
import gplx.dbs.Db_rdr;
import gplx.dbs.Db_rdr__basic;
import gplx.dbs.Db_stmt;
import gplx.dbs.DbmetaFldItm;
import gplx.dbs.engines.Db_engine;
import gplx.dbs.engines.Db_engine_sql_base;
import gplx.dbs.metas.Dbmeta_tbl_mgr;
import gplx.dbs.sqls.SqlQryWtr;
import gplx.dbs.sqls.SqlQryWtrUtl;
import gplx.dbs.wkrs.SqlWkrMgr;
import gplx.dbs.wkrs.randoms.SqliteRandomWkr;
import org.sqlite.SQLiteConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Sqlite_engine extends Db_engine_sql_base {
	private final Sqlite_txn_mgr txn_mgr; private final Sqlite_schema_mgr schema_mgr;
	Sqlite_engine() {
		this.txn_mgr = new Sqlite_txn_mgr(this);
		this.schema_mgr = new Sqlite_schema_mgr(this);
	}
	@Override public String Tid() {return Sqlite_conn_info.Key_const;}
	@Override public SqlQryWtr Sql_wtr() {return SqlQryWtrUtl.NewSqlite();}
	public void CtorConn(SqlWkrMgr wkrMgr) {
		wkrMgr.Set(new SqliteRandomWkr());
	}

	@Override public Db_engine New_clone(Db_conn_info connectInfo) {
		Sqlite_engine rv = new Sqlite_engine();
		rv.Ctor(connectInfo);
		return rv;
	}
	@Override public DataRdr New_rdr(ResultSet rdr, String commandText) {return Sqlite_rdr.new_(rdr, commandText);}
	@Override public Db_rdr	New_rdr_clone() {return new Db_rdr__sqlite();}
	@Override public void	Env_db_attach(String alias, Db_conn conn)	{
		Db_conn_info cs_obj = conn.Conn_info(); if (!StringUtl.Eq(cs_obj.Key(), Sqlite_conn_info.Key_const)) throw ErrUtl.NewArgs("must attach to sqlite databases", "conn", cs_obj.Raw());
		Sqlite_conn_info cs = (Sqlite_conn_info)cs_obj;
		Env_db_attach(alias, cs.Url());
	}
	@Override public void			Env_db_attach(String alias, Io_url db_url)	{Exec_as_int(StringUtl.Format("ATTACH '{0}' AS {1};", db_url.Raw(), alias));}
	@Override public void			Env_db_detach(String alias)					{Exec_as_int(StringUtl.Format("DETACH {0};", alias));}
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
	protected void Meta_tbl_gather_hook() {throw ErrUtl.NewUnimplemented();}
	@Override public Connection Conn_make() {
		if (!loaded) {
			try {
				Class.forName("org.sqlite.JDBC");
			}
			catch (ClassNotFoundException e) {throw ErrUtl.NewArgs(e, "could not load sqlite jdbc driver");}
			loaded = true;					
		}
		
		// init vars for opening connection
		Sqlite_conn_info conn_info_as_sqlite = (Sqlite_conn_info)conn_info;
		Io_url sqlite_fs_url = conn_info_as_sqlite.Url();
		String sqlite_db_url = "jdbc:sqlite://" + StringUtl.Replace(sqlite_fs_url.Raw(), "\\", "/");
		
		// set open_mode flag if conn is read-only; needed else all SELECT queries will be very slow; DATE:2016-09-03
		IoItmFil sqlite_fs_itm = Io_mgr.Instance.QueryFil(sqlite_fs_url);
		boolean read_only = sqlite_fs_itm.Exists() // NOTE: must check if it exists; else missing-file will be marked as readonly connection, and missing-file will sometimes be dynamically created as read-write; DATE:2016-09-04
			  && Io_mgr.Instance.Query_read_only(sqlite_fs_url, Sqlite_engine_.Read_only_detection);
		KeyVal[] props = read_only
			? KeyValUtl.Ary(KeyVal.NewStr("open_mode", "1"))
			: KeyValUtl.AryEmpty;
		if (read_only) {
			Gfo_usr_dlg_.Instance.Note_many("", "", "Sqlite db opened as read-only: url=~{0}", sqlite_fs_url.Xto_api());
		}

		// open connection
		Connection rv = Conn__new_by_url_and_props(sqlite_db_url, props);
		
		// set busyTimeout; needed else multiple processes accessing same db can cause "database is locked" error; DATE:2016-09-03
		SQLiteConnection rv_as_sqlite = (org.sqlite.SQLiteConnection)rv;
		try {rv_as_sqlite.setBusyTimeout(10000);}
		catch (SQLException e) {Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to set busy timeout; err=~{0}", ErrUtl.ToStrLog(e));}
		return rv;
	}
	public static final Sqlite_engine Instance = new Sqlite_engine();
}
class Db_rdr__sqlite extends Db_rdr__basic {	@Override public byte Read_byte(String k)		{try {return (byte)IntUtl.Cast(rdr.getObject(k));} catch (Exception e) {throw ErrUtl.NewArgs(e, "read failed", "k", k, "type", ByteUtl.ClsValName);}}
		@Override public boolean Read_bool_by_byte(String k) {
		try {
			int val = rdr.getInt(k);
			return val == 1;
		} 	catch (Exception e) {throw ErrUtl.NewArgs(e, "read failed", "i", k, "type", BoolUtl.ClsValName);}
	}
	@Override public long Read_long(String k) {
		try {
			long val = rdr.getLong(k);
			Number n = (Number)val;
			return n.longValue();
		} 	catch (Exception e) {throw ErrUtl.NewArgs(e, "read failed", "i", k, "type", LongUtl.ClsValName);}
	}
	@Override public float Read_float(String k) {
		try {
			Double val = (Double)rdr.getDouble(k);
			return val == null ? Float.NaN : val.floatValue();
		} 	catch (Exception e) {throw ErrUtl.NewArgs(e, "read failed:", "i", k, "type", FloatUtl.ClsValName);}
	}
	@Override public GfoDate Read_date_by_str(String k) {
		try {
			String val = rdr.getString(k);
			return val == null ? null : GfoDateUtl.ParseFmt(val, "yyyyMMdd_HHmmss");
		} 	catch (Exception e) {throw ErrUtl.NewArgs(e, "read failed", "i", k, "type", GfoDateUtl.ClsRefType);}
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
	@Override public GfoDecimal ReadDecimal(String key) {return ReadDecimalOr(key, null);}
	@Override public GfoDecimal ReadDecimalOr(String key, GfoDecimal or) {
		Object val = Read(key);
		if (val == null) return or;
		if (ClassUtl.EqByObj(Double.class, val)) {
			return GfoDecimalUtl.NewByDouble((Double)val);
		}
		else if (ClassUtl.EqByObj(Integer.class, val)) { // 2021-09-16|needed for gfds
			return GfoDecimalUtl.NewByInt((Integer)val);
		}
		else {
			throw ErrUtl.NewFmt("sqlite decimal must be either double or int; val={0}", val);
		}
	}
	@Override public GfoDate ReadDate(String key) {return ReadDateOr(key, null);}
	@Override public GfoDate ReadDateOr(String key, GfoDate or) {
		Object val = Read(key);
		if (val == null) return or;
		String valStr = (String)val;
		try {
			return GfoDateUtl.ParseFmt(valStr, "M/dd/yyyy hh:mm tt");
		}
		catch (Exception exc) { // 2021-09-16|needed for gfds
			return GfoDateUtl.ParseFmt(valStr, "yyyy-MM-dd hh:mm:ss");
		}
	}
	@Override public boolean ReadBool(String key) {return ReadBoolOr(key, false);}
	@Override public boolean ReadBoolOr(String key, boolean or) {
		Object val = Read(key);
		return val == null ? or : IntUtl.Cast(val) == 1;
	}
	@Override public byte ReadByte(String key) {return ReadByteOr(key, ByteUtl.Zero);}
	@Override public byte ReadByteOr(String key, byte or) {
		Object val = Read(key);
		return val == null ? or : (byte)IntUtl.Cast(val);
	}
	@Override public long ReadLong(String key) {return ReadLongOr(key, LongUtl.MinValue);}
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
	@Override protected Db_stmt Add_date(boolean where, String k, GfoDate v) {
		if (k == DbmetaFldItm.KeyNull) return this;	// key is explicitly null; ignore; allows version_2+ type definitions
		return super.Add_str(where, k, v.ToStrFmtIso8561());
	}
}
