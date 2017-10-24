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
package gplx.dbs.qrys; import gplx.*; import gplx.dbs.*;
import java.sql.*;	
import gplx.dbs.engines.*; import gplx.dbs.sqls.*; import gplx.core.stores.*;
public class Db_stmt_cmd implements Db_stmt {
	private static final String Key_na = ""; // key is not_available; only called by procs with signature of Val(<type> v);
	private Db_engine engine;
	private PreparedStatement stmt = null;	
	private String sql; private int val_idx = 0;
	public Db_stmt_cmd(Db_engine engine, Db_qry qry) {Ctor_stmt(engine, qry);}
	public void Ctor_stmt(Db_engine engine, Db_qry qry) {
		this.engine = engine;
		sql = qry.Tid() == Db_qry_.Tid_select_in_tbl ? ((Db_qry__select_in_tbl)qry).To_sql__exec(engine.Sql_wtr()) : engine.Sql_wtr().To_sql_str(qry, true);
		Reset_stmt();
	}
	public Db_stmt Reset_stmt() {
		stmt = (PreparedStatement)engine.Stmt_by_sql(sql);	
		return this;
	}
	public Db_stmt Crt_bool_as_byte(String k, boolean v)	{return Add_byte_by_bool(Bool_.Y, k, v);}
	public Db_stmt Val_bool_as_byte(String k, boolean v)	{return Add_byte_by_bool(Bool_.N, k, v);}
	public Db_stmt Val_bool_as_byte(boolean v)				{return Add_byte_by_bool(Bool_.N, Key_na, v);}
	private Db_stmt Add_byte_by_bool(boolean where, String k, boolean v) {return Add_byte(where, k, v ? Bool_.Y_byte : Bool_.N_byte);}
	public Db_stmt Crt_byte(String k, byte v)	{return Add_byte(Bool_.Y, k, v);}
	public Db_stmt Val_byte(String k, byte v)	{return Add_byte(Bool_.N, k, v);}
	public Db_stmt Val_byte(byte v)				{return Add_byte(Bool_.N, Key_na, v);}
	private Db_stmt Add_byte(boolean where, String k, byte v) {
		if (k == Dbmeta_fld_itm.Key_null) return this;	// key is explicitly null; ignore; allows version_2+ type definitions
		try {stmt.setByte(++val_idx, v);} catch (Exception e) {this.Rls(); throw Err_.new_exc(e, "db", "failed to add value", "type", "byte", "val", v, "sql", sql);}	
		return this;
	}
	public Db_stmt Crt_int(String k, int v)	{return Add_int(Bool_.Y, k, v);}
	public Db_stmt Val_int_by_bool(String k, boolean v)	{return Add_int(Bool_.N, k, v ? 1 : 0);}
	public Db_stmt Val_int(String k, int v)	{return Add_int(Bool_.N, k, v);}
	public Db_stmt Val_int(int v)			{return Add_int(Bool_.N, Key_na, v);}
	private Db_stmt Add_int(boolean where, String k, int v) {
		if (k == Dbmeta_fld_itm.Key_null) return this;	// key is explicitly null; ignore; allows version_2+ type definitions
		try {stmt.setInt(++val_idx, v);} catch (Exception e) {this.Rls(); throw Err_.new_exc(e, "db", "failed to add value", "type", "int", "val", v, "sql", sql);}	
		return this;
	}
	public Db_stmt Crt_long(String k, long v)	{return Add_long(Bool_.Y, k, v);}
	public Db_stmt Val_long(String k, long v)	{return Add_long(Bool_.N, k, v);}
	public Db_stmt Val_long(long v)				{return Add_long(Bool_.N, Key_na, v);}
	private Db_stmt Add_long(boolean where, String k, long v) {
		if (k == Dbmeta_fld_itm.Key_null) return this;	// key is explicitly null; ignore; allows version_2+ type definitions
		try {stmt.setLong(++val_idx, v);} catch (Exception e) {this.Rls(); throw Err_.new_exc(e, "db", "failed to add value", "type", "long", "val", v, "sql", sql);}	
		return this;
	}
	public Db_stmt Crt_float(String k, float v)	{return Add_float(Bool_.Y, k, v);}
	public Db_stmt Val_float(String k, float v)	{return Add_float(Bool_.N, k, v);}
	public Db_stmt Val_float(float v)			{return Add_float(Bool_.N, Key_na, v);}
	private Db_stmt Add_float(boolean where, String k, float v) {
		if (k == Dbmeta_fld_itm.Key_null) return this;	// key is explicitly null; ignore; allows version_2+ type definitions
		try {stmt.setFloat(++val_idx, v);} catch (Exception e) {this.Rls(); throw Err_.new_exc(e, "db", "failed to add value", "type", "float", "val", v, "sql", sql);}	
		return this;
	}
	public Db_stmt Crt_double(String k, double v)	{return Add_double(Bool_.Y, k, v);}
	public Db_stmt Val_double(String k, double v)	{return Add_double(Bool_.N, k, v);}
	public Db_stmt Val_double(double v)				{return Add_double(Bool_.N, Key_na, v);}
	private Db_stmt Add_double(boolean where, String k, double v) {
		if (k == Dbmeta_fld_itm.Key_null) return this;	// key is explicitly null; ignore; allows version_2+ type definitions
		try {stmt.setDouble(++val_idx, v);} catch (Exception e) {this.Rls(); throw Err_.new_exc(e, "db", "failed to add value", "type", "double", "val", v, "sql", sql);}	
		return this;
	}
	public Db_stmt Crt_decimal(String k, Decimal_adp v)	{return Add_decimal(Bool_.Y, k, v);}
	public Db_stmt Val_decimal(String k, Decimal_adp v)	{return Add_decimal(Bool_.N, k, v);}
	public Db_stmt Val_decimal(Decimal_adp v)			{return Add_decimal(Bool_.N, Key_na, v);}
	private Db_stmt Add_decimal(boolean where, String k, Decimal_adp v) {
		if (k == Dbmeta_fld_itm.Key_null) return this;	// key is explicitly null; ignore; allows version_2+ type definitions
		try {stmt.setBigDecimal(++val_idx, v.Under_as_native());} catch (Exception e) {this.Rls(); throw Err_.new_exc(e, "db", "failed to add value", "type", "decimal", "val", v, "sql", sql);}	
		return this;
	}
	public Db_stmt Crt_bry(String k, byte[] v)	{return Add_bry(Bool_.Y, k, v);}
	public Db_stmt Val_bry(String k, byte[] v)	{return Add_bry(Bool_.N, k, v);}
	public Db_stmt Val_bry(byte[] v)			{return Add_bry(Bool_.N, Key_na, v);}
	private Db_stmt Add_bry(boolean where, String k, byte[] v) {
		if (k == Dbmeta_fld_itm.Key_null) return this;	// key is explicitly null; ignore; allows version_2+ type definitions
		try {stmt.setBytes(++val_idx, v);} catch (Exception e) {this.Rls(); throw Err_.new_exc(e, "db", "failed to add value", "type", "byte[]", v.length, sql);}	
		return this;
	}
	public Db_stmt Crt_bry_as_str(String k, byte[] v)	{return Add_bry_as_str(Bool_.Y, k, v);}
	public Db_stmt Val_bry_as_str(String k, byte[] v)	{return Add_bry_as_str(Bool_.N, k, v);}
	public Db_stmt Val_bry_as_str(byte[] v)				{return Add_bry_as_str(Bool_.N, Key_na, v);}
	private Db_stmt Add_bry_as_str(boolean where, String k, byte[] v) {return Add_str(where, k, String_.new_u8(v));}
	public Db_stmt Crt_str(String k, String v)	{return Add_str(Bool_.Y, k, v);}
	public Db_stmt Val_str(String k, String v)	{return Add_str(Bool_.N, k, v);}
	public Db_stmt Val_str(String v)			{return Add_str(Bool_.N, Key_na, v);}
	@gplx.Virtual protected Db_stmt Add_str(boolean where, String k, String v) {
		if (k == Dbmeta_fld_itm.Key_null) return this;	// key is explicitly null; ignore; allows version_2+ type definitions
		try {stmt.setString(++val_idx, v);} catch (Exception e) {this.Rls(); throw Err_.new_exc(e, "db", "failed to add value", "type", "String", "val", v, "sql", sql);}	
		return this;
	}
	public Db_stmt Crt_date(String k, DateAdp v)	{return Add_date(Bool_.Y, k, v);}
	public Db_stmt Val_date(String k, DateAdp v)	{return Add_date(Bool_.N, k, v);}
	@gplx.Virtual protected Db_stmt Add_date(boolean where, String k, DateAdp v) {
		if (k == Dbmeta_fld_itm.Key_null) return this;	// key is explicitly null; ignore; allows version_2+ type definitions
		try {stmt.setTimestamp(++val_idx, new java.sql.Timestamp(v.UnderDateTime().getTime().getTime()));} catch (Exception e) {this.Rls(); throw Err_.new_exc(e, "db", "failed to add value", "type", "date", "val", v, "sql", sql);}	
		return this;
	}
	public Db_stmt Crt_text(String k, String v)	{return Add_text(Bool_.Y, k, v);}
	public Db_stmt Val_text(String k, String v)	{return Add_text(Bool_.N, k, v);}
	private Db_stmt Add_text(boolean where, String k, String v) {
		if (k == Dbmeta_fld_itm.Key_null) return this;	// key is explicitly null; ignore; allows version_2+ type definitions
		try {stmt.setString(++val_idx, v);} catch (Exception e) {this.Rls(); throw Err_.new_exc(e, "db", "failed to add value", "type", "text", "val", v, "sql", sql);}	
		return this;
	}
	public Db_stmt Val_rdr_(gplx.core.ios.streams.Io_stream_rdr v, long rdr_len) {
		try {stmt.setBinaryStream(++val_idx, (java.io.InputStream)v.Under(), (int)rdr_len);} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to add value", "type", "rdr", "val", v);}	
		return this;
	}
	public boolean Exec_insert() {
		try		{boolean rv = stmt.execute(); return rv;}	
		catch (Exception e) {
			this.Rls();
			Reset_stmt();
			throw Err_.new_exc(e, "db_stmt", "insert failed", "url", engine.Conn_info().Db_api(), "sql", sql);
		}
	}
	public int Exec_update() {
		try		{int rv = stmt.executeUpdate(); return rv;}		
		catch (Exception e) {
			this.Rls();
			Reset_stmt();
			throw Err_.new_exc(e, "db_stmt", "update failed", "url", engine.Conn_info().Db_api(), "sql", sql);
		}
	}
	public int Exec_delete() {
		try		{int rv = stmt.executeUpdate(); return rv;}		
		catch (Exception e) {
			this.Rls();
			Reset_stmt();
			throw Err_.new_exc(e, "db_stmt", "delete failed", "url", engine.Conn_info().Db_api(), "sql", sql);
		}
	}
	public DataRdr Exec_select() {
		try {DataRdr rv = engine.New_rdr(stmt.executeQuery(), sql); return rv;} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to exec prepared statement", "sql", sql);}	
	}
	public Db_rdr Exec_select__rls_auto() {
		try {return engine.Exec_as_rdr__rls_auto(this, stmt.executeQuery(), sql);}	catch (Exception e) {throw Err_.new_exc(e, "db", "select failed", "sql", sql);}	
	}
	public Db_rdr Exec_select__rls_manual() {
		try {return engine.Exec_as_rdr__rls_manual(stmt.executeQuery(), sql);}	catch (Exception e) {throw Err_.new_exc(e, "db", "select failed", "sql", sql);}	
	}
	public Object Exec_select_val() {
		try {Object rv = DataRdr_.Read_1st_row_and_1st_fld(engine.New_rdr(stmt.executeQuery(), sql)); return rv;} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to exec prepared statement", "sql", sql);}	
	}
	public Db_stmt Clear() {
		val_idx = 0;
		try {stmt.clearBatch();}	
		catch (Exception e) {throw Err_.new_exc(e, "db", "failed to clear parameters", "sql", sql);}
		return this;
	}
		public void Rls() {
		if (stmt == null) return;							// Null instance
		try {
			if (stmt.getConnection().isClosed()) return;	// do not close stmt if connection is already closed; throws null error; DATE:2015-02-11
			stmt.close();									
			stmt = null;
		}
		catch (Exception e) {throw Err_.new_exc(e, "db", "failed to close command", "sql", sql);}
	}
	}
