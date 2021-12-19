/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.dbs.qrys;

import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDecimal;
import gplx.types.basics.utls.StringUtl;
import gplx.core.stores.DataRdr;
import gplx.core.stores.DataRdr_;
import gplx.dbs.Db_qry;
import gplx.dbs.Db_qry_;
import gplx.dbs.Db_rdr;
import gplx.dbs.Db_stmt;
import gplx.dbs.DbmetaFldItm;
import gplx.dbs.engines.Db_engine;
import gplx.types.commons.lists.GfoListBase;

import java.sql.PreparedStatement;

public class Db_stmt_cmd implements Db_stmt {
	private static final String Key_na = ""; // key is not_available; only called by procs with signature of Val(<type> v);
	private Db_engine engine;
	private PreparedStatement stmt = null;	
	private String sql;
	private int val_idx = 0;
	private final GfoListBase<Object> paramList = new GfoListBase<>();
	public Db_stmt_cmd(Db_engine engine, Db_qry qry) {Ctor_stmt(engine, qry);}
	public void Ctor_stmt(Db_engine engine, Db_qry qry) {
		this.engine = engine;
		sql = qry.Tid() == Db_qry_.Tid_select_in_tbl ? ((Db_qry__select_in_tbl)qry).ToSqlExec(engine.Sql_wtr()) : engine.Sql_wtr().ToSqlStr(qry, true);
		Reset_stmt();
	}
	public Db_stmt Reset_stmt() {
		stmt = (PreparedStatement)engine.Stmt_by_sql(sql);	
		return this;
	}
	public Db_stmt Crt_bool_as_byte(String k, boolean v)	{return Add_byte_by_bool(BoolUtl.Y, k, v);}
	public Db_stmt Val_bool_as_byte(String k, boolean v)	{return Add_byte_by_bool(BoolUtl.N, k, v);}
	public Db_stmt Val_bool_as_byte(boolean v)				{return Add_byte_by_bool(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_byte_by_bool(boolean where, String k, boolean v) {return Add_byte(where, k, v ? BoolUtl.YByte : BoolUtl.NByte);}
	public Db_stmt Crt_byte(String k, byte v)	{return Add_byte(BoolUtl.Y, k, v);}
	public Db_stmt Val_byte(String k, byte v)	{return Add_byte(BoolUtl.N, k, v);}
	public Db_stmt Val_byte(byte v)				{return Add_byte(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_byte(boolean where, String k, byte v) {
		if (k == DbmetaFldItm.KeyNull) return this;	// key is explicitly null; ignore; allows version_2+ type definitions
		try {
			stmt.setByte(++val_idx, v);
			paramList.Add(v);
		} catch (Exception e) {
			this.Rls();
			throw ErrUtl.NewArgs(e, "failed to add value", "type", "byte", "val", v, "sql", sql);
		}
		return this;
	}
	public Db_stmt Crt_int(String k, int v)	{return Add_int(BoolUtl.Y, k, v);}
	public Db_stmt Val_int_by_bool(String k, boolean v)	{return Add_int(BoolUtl.N, k, v ? 1 : 0);}
	public Db_stmt Val_int(String k, int v)	{return Add_int(BoolUtl.N, k, v);}
	public Db_stmt Val_int(int v)			{return Add_int(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_int(boolean where, String k, int v) {
		if (k == DbmetaFldItm.KeyNull) return this;	// key is explicitly null; ignore; allows version_2+ type definitions
		try {
			stmt.setInt(++val_idx, v);
			paramList.Add(v);
		} catch (Exception e) {
			this.Rls();
			throw ErrUtl.NewArgs(e, "failed to add value", "type", "int", "val", v, "sql", sql);
		}
		return this;
	}
	public Db_stmt Crt_long(String k, long v)	{return Add_long(BoolUtl.Y, k, v);}
	public Db_stmt Val_long(String k, long v)	{return Add_long(BoolUtl.N, k, v);}
	public Db_stmt Val_long(long v)				{return Add_long(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_long(boolean where, String k, long v) {
		if (k == DbmetaFldItm.KeyNull) return this;	// key is explicitly null; ignore; allows version_2+ type definitions
		try {
			stmt.setLong(++val_idx, v);
			paramList.Add(v);
		} catch (Exception e) {
			this.Rls();
			throw ErrUtl.NewArgs(e, "failed to add value", "type", "long", "val", v, "sql", sql);
		}
		return this;
	}
	public Db_stmt Crt_float(String k, float v)	{return Add_float(BoolUtl.Y, k, v);}
	public Db_stmt Val_float(String k, float v)	{return Add_float(BoolUtl.N, k, v);}
	public Db_stmt Val_float(float v)			{return Add_float(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_float(boolean where, String k, float v) {
		if (k == DbmetaFldItm.KeyNull) return this;	// key is explicitly null; ignore; allows version_2+ type definitions
		try {
			stmt.setFloat(++val_idx, v);
			paramList.Add(v);
		} catch (Exception e) {
			this.Rls();
			throw ErrUtl.NewArgs(e, "failed to add value", "type", "float", "val", v, "sql", sql);
		}
		return this;
	}
	public Db_stmt Crt_double(String k, double v)	{return Add_double(BoolUtl.Y, k, v);}
	public Db_stmt Val_double(String k, double v)	{return Add_double(BoolUtl.N, k, v);}
	public Db_stmt Val_double(double v)				{return Add_double(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_double(boolean where, String k, double v) {
		if (k == DbmetaFldItm.KeyNull) return this;	// key is explicitly null; ignore; allows version_2+ type definitions
		try {
			stmt.setDouble(++val_idx, v);
			paramList.Add(v);
		} catch (Exception e) {
			this.Rls();
			throw ErrUtl.NewArgs(e, "failed to add value", "type", "double", "val", v, "sql", sql);
		}
		return this;
	}
	public Db_stmt Crt_decimal(String k, GfoDecimal v)	{return Add_decimal(BoolUtl.Y, k, v);}
	public Db_stmt Val_decimal(String k, GfoDecimal v)	{return Add_decimal(BoolUtl.N, k, v);}
	public Db_stmt Val_decimal(GfoDecimal v)			{return Add_decimal(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_decimal(boolean where, String k, GfoDecimal v) {
		if (k == DbmetaFldItm.KeyNull) return this;	// key is explicitly null; ignore; allows version_2+ type definitions
		try {
			stmt.setBigDecimal(++val_idx, v.UnderAsNative());
			paramList.Add(v);
		} catch (Exception e) {
			this.Rls();
			throw ErrUtl.NewArgs(e, "failed to add value", "type", "decimal", "val", v, "sql", sql);
		}
		return this;
	}
	public Db_stmt Crt_bry(String k, byte[] v)	{return Add_bry(BoolUtl.Y, k, v);}
	public Db_stmt Val_bry(String k, byte[] v)	{return Add_bry(BoolUtl.N, k, v);}
	public Db_stmt Val_bry(byte[] v)			{return Add_bry(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_bry(boolean where, String k, byte[] v) {
		if (k == DbmetaFldItm.KeyNull) return this;	// key is explicitly null; ignore; allows version_2+ type definitions
		try {
			stmt.setBytes(++val_idx, v);
			paramList.Add(v);
		} catch (Exception e) {
			this.Rls();
			throw ErrUtl.NewArgs(e, "failed to add value", "type", "byte[]", v.length, sql);
		}
		return this;
	}
	public Db_stmt Crt_bry_as_str(String k, byte[] v)	{return Add_bry_as_str(BoolUtl.Y, k, v);}
	public Db_stmt Val_bry_as_str(String k, byte[] v)	{return Add_bry_as_str(BoolUtl.N, k, v);}
	public Db_stmt Val_bry_as_str(byte[] v)				{return Add_bry_as_str(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_bry_as_str(boolean where, String k, byte[] v) {return Add_str(where, k, StringUtl.NewU8(v));}
	public Db_stmt Crt_str(String k, String v)	{return Add_str(BoolUtl.Y, k, v);}
	public Db_stmt Val_str(String k, String v)	{return Add_str(BoolUtl.N, k, v);}
	public Db_stmt Val_str(String v)			{return Add_str(BoolUtl.N, Key_na, v);}
	protected Db_stmt Add_str(boolean where, String k, String v) {
		if (k == DbmetaFldItm.KeyNull) return this;	// key is explicitly null; ignore; allows version_2+ type definitions
		try {
			stmt.setString(++val_idx, v);
			paramList.Add(v);
		} catch (Exception e) {
			this.Rls();
			throw ErrUtl.NewArgs(e, "failed to add value", "type", "String", "val", v, "sql", sql);
		}
		return this;
	}
	public Db_stmt Crt_date(String k, GfoDate v)	{return Add_date(BoolUtl.Y, k, v);}
	public Db_stmt Val_date(String k, GfoDate v)	{return Add_date(BoolUtl.N, k, v);}
	protected Db_stmt Add_date(boolean where, String k, GfoDate v) {
		if (k == DbmetaFldItm.KeyNull) return this;	// key is explicitly null; ignore; allows version_2+ type definitions
		try {
			stmt.setTimestamp(++val_idx, new java.sql.Timestamp(v.UnderCalendar().getTime().getTime()));
			paramList.Add(v);
		} catch (Exception e) {
			this.Rls();
			throw ErrUtl.NewArgs(e, "failed to add value", "type", "date", "val", v, "sql", sql);
		}
		return this;
	}
	public Db_stmt Crt_text(String k, String v)	{return Add_text(BoolUtl.Y, k, v);}
	public Db_stmt Val_text(String k, String v)	{return Add_text(BoolUtl.N, k, v);}
	private Db_stmt Add_text(boolean where, String k, String v) {
		if (k == DbmetaFldItm.KeyNull) return this;	// key is explicitly null; ignore; allows version_2+ type definitions
		try {
			stmt.setString(++val_idx, v);
			paramList.Add(v);
		} catch (Exception e) {
			this.Rls();
			throw ErrUtl.NewArgs(e, "failed to add value", "type", "text", "val", v, "sql", sql);
		}
		return this;
	}
	public Db_stmt Val_rdr_(gplx.core.ios.streams.Io_stream_rdr v, long rdr_len) {
		try {
			stmt.setBinaryStream(++val_idx, (java.io.InputStream)v.Under(), (int)rdr_len);
		} catch (Exception e) {
			// DATE:2021-07-16: shouldn't there be a this.Rls()?
			throw ErrUtl.NewArgs(e, "failed to add value", "type", "rdr", "val", v);
		}
		return this;
	}
	public boolean Exec_insert() {
		try		{boolean rv = stmt.execute(); return rv;}	
		catch (Exception e) {
			String sqlStr = ToSqlStr();
			this.Rls();
			Reset_stmt();
			throw ErrUtl.NewArgs(e, "insert failed", "url", engine.Conn_info().Db_api(), "sql", sqlStr);
		}
	}
	public int Exec_update() {
		try		{int rv = stmt.executeUpdate(); return rv;}		
		catch (Exception e) {
			String sqlStr = ToSqlStr();
			this.Rls();
			Reset_stmt();
			throw ErrUtl.NewArgs(e, "update failed", "url", engine.Conn_info().Db_api(), "sql", sqlStr);
		}
	}
	public int Exec_delete() {
		try		{int rv = stmt.executeUpdate(); return rv;}		
		catch (Exception e) {
			String sqlStr = ToSqlStr();
			this.Rls();
			Reset_stmt();
			throw ErrUtl.NewArgs(e, "delete failed", "url", engine.Conn_info().Db_api(), "sql", sqlStr);
		}
	}
	public DataRdr Exec_select() {
		try {
			DataRdr rv = engine.New_rdr(stmt.executeQuery(), sql); return rv;
		} catch (Exception e) {
			String sqlStr = ToSqlStr();
			// DATE:2021-07-16: this needs a this.Rls() / Reset_stmt
			throw ErrUtl.NewArgs(e, "failed to exec prepared statement", "sql", sqlStr);
		}
	}
	public Db_rdr Exec_select__rls_auto() {
		try {
			return engine.Exec_as_rdr__rls_auto(this, stmt.executeQuery(), sql);
		} catch (Exception e) {
			String sqlStr = ToSqlStr();
			// DATE:2021-07-16: this needs a this.Rls() / Reset_stmt
			throw ErrUtl.NewArgs(e, "select failed", "sql", sqlStr);
		}
	}
	public Db_rdr Exec_select__rls_manual() {
		try {
			return engine.Exec_as_rdr__rls_manual(stmt.executeQuery(), sql);
		} catch (Exception e) {
			String sqlStr = ToSqlStr();
			// DATE:2021-07-16: this needs a this.Rls() / Reset_stmt
			throw ErrUtl.NewArgs(e, "select failed", "sql", sqlStr);
		}
	}
	public Object Exec_select_val() {
		try {
			Object rv = DataRdr_.Read_1st_row_and_1st_fld(engine.New_rdr(stmt.executeQuery(), sql)); return rv;
		} catch (Exception e) {
			String sqlStr = ToSqlStr();
			// DATE:2021-07-16: this needs a this.Rls() / Reset_stmt
			throw ErrUtl.NewArgs(e, "failed to exec prepared statement", "sql", sqlStr);
		}
	}
	public Db_stmt Clear() {
		val_idx = 0;
		paramList.Clear();
		try {stmt.clearBatch();}	
		catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to clear parameters", "sql", sql);}
		return this;
	}
	public void Rls() {
		this.Clear(); // DATE:2021-07-16: added this.Clear();
		if (stmt == null) return;							// Null instance
		try {
			if (stmt.getConnection().isClosed()) return;	// do not close stmt if connection is already closed; throws null error; DATE:2015-02-11
			stmt.close();									
			stmt = null;
		}
		catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to close command", "sql", sql);}
	}
	public String ToSqlStr() {
		return Db_val_type.ToSqlStr(sql, paramList);
	}
}
