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
class Db_stmt_cmd implements Db_stmt {
	Db_engine engine;
	PreparedStatement stmt = null;	
	String sql;
	int val_idx = 0;
	public Db_stmt_cmd(Db_provider provider, Db_qry qry) {
		this.provider = provider; this.engine = provider.Engine();
		sql = Sql_cmd_wtr_.Ansi.XtoSqlQry(qry, true);
		New();
	}
	public Db_stmt New() {
		stmt = (PreparedStatement)engine.New_db_cmd(sql);	
		return this;
	}
	public Db_provider Provider() {return provider;} Db_provider provider; 
	public Db_stmt Val_bool_(boolean v) {
		try {stmt.setBoolean(++val_idx, v);} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "int", v);}	
		return this;
	}
	public Db_stmt Val_byte_by_bool_(boolean v) {return Val_byte_(v ? Bool_.Y_byte : Bool_.N_byte);}
	public Db_stmt Val_byte_(byte v) {
		try {stmt.setByte(++val_idx, v);} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "byte", v);}	
		return this;
	}
	public Db_stmt Val_int_(int v) {
		try {stmt.setInt(++val_idx, v);} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "int", v);}	
		return this;
	}
	public Db_stmt Val_long_(long v) {
		try {stmt.setLong(++val_idx, v);} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "long", v);}	
		return this;
	}
	public Db_stmt Val_float_(float v) {
		try {stmt.setFloat(++val_idx, v);} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "float", v);}	
		return this;
	}
	public Db_stmt Val_double_(double v) {
		try {stmt.setDouble(++val_idx, v);} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "double", v);}	
		return this;
	}
	public Db_stmt Val_decimal_(DecimalAdp v) {
		try {stmt.setBigDecimal(++val_idx, v.XtoDecimal());} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "decimal", v);}	
		return this;
	}
	public Db_stmt Val_bry_by_str_(String v) {return Val_bry_(Bry_.new_utf8_(v));}
	public Db_stmt Val_bry_(byte[] v) {
		try {stmt.setBytes(++val_idx, v);} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "byte[]", v.length);}	
		return this;
	}
	public Db_stmt Val_str_by_bry_(byte[] v) {return Val_str_(String_.new_utf8_(v));}
	public Db_stmt Val_str_(String v) {
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
		try {DataRdr rv = engine.NewDataRdr(stmt.executeQuery(), sql); return rv;} catch (Exception e) {throw Err_.err_(e, "failed to exec prepared statement: sql={0}", sql);}	
	}	
	public Object Exec_select_val() {
		try {Object rv = Db_qry_select.Rdr_to_val(engine.NewDataRdr(stmt.executeQuery(), sql)); return rv;} catch (Exception e) {throw Err_.err_(e, "failed to exec prepared statement: sql={0}", sql);}	
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
