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
import java.sql.*;	
import gplx.dbs.sqls.*;
class Db_stmt_cmd implements Db_stmt {
	private final Db_engine engine;
	private PreparedStatement stmt = null;	
	private String sql; private int val_idx = 0;
	public Db_stmt_cmd(Db_engine engine, Db_qry qry) {
		this.engine = engine;
		sql = qry.Tid() == Db_qry_.Tid_select_in_tbl ? ((Db_qry__select_in_tbl)qry).Xto_sql() : Sql_qry_wtr_.I.Xto_str(qry, true);
		Reset_stmt();
	}
	public Db_stmt Reset_stmt() {
		stmt = (PreparedStatement)engine.New_stmt_prep_as_obj(sql);	
		return this;
	}
	public Db_stmt Crt_bool_as_byte(String k, boolean v)	{return Add_byte_by_bool(Bool_.Y, k, v);}
	public Db_stmt Val_bool_as_byte(String k, boolean v)	{return Add_byte_by_bool(Bool_.N, k, v);}
	public Db_stmt Val_bool_as_byte(boolean v)			{return Add_byte_by_bool(Bool_.N, null, v);}
	private Db_stmt Add_byte_by_bool(boolean where, String k, boolean v) {return Add_byte(where, k, v ? Bool_.Y_byte : Bool_.N_byte);}
	public Db_stmt Crt_byte(String k, byte v)	{return Add_byte(Bool_.Y, k, v);}
	public Db_stmt Val_byte(String k, byte v)	{return Add_byte(Bool_.N, k, v);}
	public Db_stmt Val_byte(byte v)			{return Add_byte(Bool_.N, null, v);}
	private Db_stmt Add_byte(boolean where, String k, byte v) {
		try {stmt.setByte(++val_idx, v);} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "byte", v);}	
		return this;
	}
	public Db_stmt Crt_int(String k, int v)	{return Add_int(Bool_.Y, k, v);}
	public Db_stmt Val_int(String k, int v)	{return Add_int(Bool_.N, k, v);}
	public Db_stmt Val_int(int v)			{return Add_int(Bool_.N, null, v);}
	private Db_stmt Add_int(boolean where, String k, int v) {
		try {stmt.setInt(++val_idx, v);} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "int", v);}	
		return this;
	}
	public Db_stmt Crt_long(String k, long v)	{return Add_long(Bool_.Y, k, v);}
	public Db_stmt Val_long(String k, long v)	{return Add_long(Bool_.N, k, v);}
	public Db_stmt Val_long(long v)			{return Add_long(Bool_.N, null, v);}
	private Db_stmt Add_long(boolean where, String k, long v) {
		try {stmt.setLong(++val_idx, v);} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "long", v);}	
		return this;
	}
	public Db_stmt Crt_float(String k, float v)	{return Add_float(Bool_.Y, k, v);}
	public Db_stmt Val_float(String k, float v)	{return Add_float(Bool_.N, k, v);}
	public Db_stmt Val_float(float v)			{return Add_float(Bool_.N, null, v);}
	private Db_stmt Add_float(boolean where, String k, float v) {
		try {stmt.setFloat(++val_idx, v);} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "float", v);}	
		return this;
	}
	public Db_stmt Crt_double(String k, double v)	{return Add_double(Bool_.Y, k, v);}
	public Db_stmt Val_double(String k, double v)	{return Add_double(Bool_.N, k, v);}
	public Db_stmt Val_double(double v)			{return Add_double(Bool_.N, null, v);}
	private Db_stmt Add_double(boolean where, String k, double v) {
		try {stmt.setDouble(++val_idx, v);} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "double", v);}	
		return this;
	}
	public Db_stmt Crt_decimal(String k, DecimalAdp v)	{return Add_decimal(Bool_.Y, k, v);}
	public Db_stmt Val_decimal(String k, DecimalAdp v)	{return Add_decimal(Bool_.N, k, v);}
	public Db_stmt Val_decimal(DecimalAdp v)			{return Add_decimal(Bool_.N, null, v);}
	private Db_stmt Add_decimal(boolean where, String k, DecimalAdp v) {
		try {stmt.setBigDecimal(++val_idx, v.Xto_decimal());} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "decimal", v);}	
		return this;
	}
	public Db_stmt Crt_bry(String k, byte[] v)	{return Add_bry(Bool_.Y, k, v);}
	public Db_stmt Val_bry(String k, byte[] v)	{return Add_bry(Bool_.N, k, v);}
	public Db_stmt Val_bry(byte[] v)			{return Add_bry(Bool_.N, null, v);}
	private Db_stmt Add_bry(boolean where, String k, byte[] v) {
		try {stmt.setBytes(++val_idx, v);} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "byte[]", v.length);}	
		return this;
	}
	public Db_stmt Crt_bry_as_str(String k, byte[] v)	{return Add_bry_as_str(Bool_.Y, k, v);}
	public Db_stmt Val_bry_as_str(String k, byte[] v)	{return Add_bry_as_str(Bool_.N, k, v);}
	public Db_stmt Val_bry_as_str(byte[] v)			{return Add_bry_as_str(Bool_.N, null, v);}
	private Db_stmt Add_bry_as_str(boolean where, String k, byte[] v) {return Add_str(where, k, String_.new_utf8_(v));}
	public Db_stmt Crt_str(String k, String v)	{return Add_str(Bool_.Y, k, v);}
	public Db_stmt Val_str(String k, String v)	{return Add_str(Bool_.N, k, v);}
	public Db_stmt Val_str(String v)			{return Add_str(Bool_.N, null, v);}
	private Db_stmt Add_str(boolean where, String k, String v) {
		try {stmt.setString(++val_idx, v);} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "String", v);}	
		return this;
	}
	public Db_stmt Val_rdr_(gplx.ios.Io_stream_rdr v, long rdr_len) {
		try {stmt.setBinaryStream(++val_idx, (java.io.InputStream)v.Under(), (int)rdr_len);} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "rdr", v);}	
		return this;
	}
	public boolean Exec_insert() {			
		try {boolean rv = stmt.execute(); return rv;} catch (Exception e) {throw Err_.err_(e, "failed to exec prepared statement: sql={0} err={1}", sql, Err_.Message_gplx_brief(e));}	
	}
	public int Exec_update() {
		try {int rv = stmt.executeUpdate(); return rv;} catch (Exception e) {throw Err_.err_(e, "failed to exec prepared statement: sql={0}", sql);}		
	}
	public int Exec_delete() {
		try {int rv = stmt.executeUpdate(); return rv;} catch (Exception e) {throw Err_.err_(e, "failed to exec prepared statement: sql={0}", sql);}		
	}
	public DataRdr Exec_select() {
		try {DataRdr rv = engine.New_rdr(stmt.executeQuery(), sql); return rv;} catch (Exception e) {throw Err_.err_(e, "failed to exec prepared statement: sql={0}", sql);}	
	}	
	public Db_rdr Exec_select_as_rdr() {
		try {return engine.New_rdr_by_obj(stmt.executeQuery(), sql);}	catch (Exception e) {throw Err_.err_(e, "select failed: sql={0}", sql);}	
	}
	public Object Exec_select_val() {
		try {Object rv = Db_qry_select.Rdr_to_val(engine.New_rdr(stmt.executeQuery(), sql)); return rv;} catch (Exception e) {throw Err_.err_(e, "failed to exec prepared statement: sql={0}", sql);}	
	}
	public Db_stmt Clear() {
		val_idx = 0;
		try {stmt.clearBatch();}	
		catch (Exception e) {throw Err_.err_(e, "failed to clear parameters;", sql);}
		return this;
	}
		public void Rls() {
		if (stmt == null) return;	// Null instance
		try {stmt.close();}		
		catch (Exception e) {throw Err_.err_(e, "failed to close command: {0}", sql);}
	}
	}
