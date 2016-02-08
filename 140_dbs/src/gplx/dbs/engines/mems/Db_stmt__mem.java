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
package gplx.dbs.engines.mems; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import gplx.core.stores.*;
public class Db_stmt__mem implements Db_stmt {
	private static final String Key_na = ""; // key is not_available; only called by procs with signature of Val(<type> v);
	private final Ordered_hash val_list = Ordered_hash_.New();
	public Db_stmt__mem(Db_engine__mem engine, Db_qry qry) {Ctor_stmt(engine, qry);} private Db_engine__mem engine;
	public void Ctor_stmt(Db_engine engine, Db_qry qry) {this.engine = (Db_engine__mem)engine; this.qry = qry;}
	public Hash_adp Crts() {return crt_hash;} private final Hash_adp crt_hash = Hash_adp_.new_();
	public int Args_len() {return val_list.Count();}
	public Object Args_get_at(int i)	{return val_list.Get_at(i);}
	public Object Args_get_by(String k) {return val_list.Get_by(k);}
	public Db_qry Qry() {return qry;} private Db_qry qry;
	public Db_stmt Reset_stmt() {return this;}
	public Db_stmt Clear() {
		val_list.Clear();
		crt_hash.Clear();
		return this;
	}
	public void Rls() {this.Clear();}
	public Db_stmt Crt_bool_as_byte(String k, boolean v)	{return Add_byte_by_bool(Bool_.Y, k, v);}
	public Db_stmt Val_bool_as_byte(String k, boolean v)	{return Add_byte_by_bool(Bool_.N, k, v);}
	public Db_stmt Val_bool_as_byte(boolean v)				{return Add_byte_by_bool(Bool_.N, Key_na, v);}
	private Db_stmt Add_byte_by_bool(boolean where, String k, boolean v) {return Add_byte(where, k, v ? Bool_.Y_byte : Bool_.N_byte);}
	public Db_stmt Crt_byte(String k, byte v)	{return Add_byte(Bool_.Y, k, v);}
	public Db_stmt Val_byte(String k, byte v)	{return Add_byte(Bool_.N, k, v);}
	public Db_stmt Val_byte(byte v)				{return Add_byte(Bool_.N, Key_na, v);}
	private Db_stmt Add_byte(boolean where, String k, byte v) {
		try {Add(k, where, v);} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to add value", "type", "byte", "val", v);}
		return this;
	}
	public Db_stmt Crt_int(String k, int v)	{return Add_int(Bool_.Y, k, v);}
	public Db_stmt Val_int_by_bool(String k, boolean v)	{return Add_int(Bool_.N, k, v ? 1 : 0);}
	public Db_stmt Val_int(String k, int v)	{return Add_int(Bool_.N, k, v);}
	public Db_stmt Val_int(int v)			{return Add_int(Bool_.N, Key_na, v);}
	private Db_stmt Add_int(boolean where, String k, int v) {
		try {Add(k, where, v);} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to add value", "type", "int", "val", v);}
		return this;
	}
	public Db_stmt Crt_long(String k, long v)	{return Add_long(Bool_.Y, k, v);}
	public Db_stmt Val_long(String k, long v)	{return Add_long(Bool_.N, k, v);}
	public Db_stmt Val_long(long v)				{return Add_long(Bool_.N, Key_na, v);}
	private Db_stmt Add_long(boolean where, String k, long v) {
		try {Add(k, where, v);} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to add value", "type", "long", "val", v);} 
		return this;
	}
	public Db_stmt Crt_float(String k, float v)	{return Add_float(Bool_.Y, k, v);}
	public Db_stmt Val_float(String k, float v)	{return Add_float(Bool_.N, k, v);}
	public Db_stmt Val_float(float v)			{return Add_float(Bool_.N, Key_na, v);}
	private Db_stmt Add_float(boolean where, String k, float v) {
		try {Add(k, where, v);} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to add value", "type", "float", "val", v);}
		return this;
	}
	public Db_stmt Crt_double(String k, double v)	{return Add_double(Bool_.Y, k, v);}
	public Db_stmt Val_double(String k, double v)	{return Add_double(Bool_.N, k, v);}
	public Db_stmt Val_double(double v)				{return Add_double(Bool_.N, Key_na, v);}
	private Db_stmt Add_double(boolean where, String k, double v) {
		try {Add(k, where, v);} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to add value", "type", "double", "val", v);} 
		return this;
	}
	public Db_stmt Crt_decimal(String k, Decimal_adp v)	{return Add_decimal(Bool_.Y, k, v);}
	public Db_stmt Val_decimal(String k, Decimal_adp v)	{return Add_decimal(Bool_.N, k, v);}
	public Db_stmt Val_decimal(Decimal_adp v)			{return Add_decimal(Bool_.N, Key_na, v);}
	private Db_stmt Add_decimal(boolean where, String k, Decimal_adp v) {
		try {Add(k, where, v);} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to add value", "type", "decimal", "val", v);} 
		return this;
	}
	public Db_stmt Crt_bry(String k, byte[] v)	{return Add_bry(Bool_.Y, k, v);}
	public Db_stmt Val_bry(String k, byte[] v)	{return Add_bry(Bool_.N, k, v);}
	public Db_stmt Val_bry(byte[] v)			{return Add_bry(Bool_.N, Key_na, v);}
	private Db_stmt Add_bry(boolean where, String k, byte[] v) {
		try {Add(k, where, v);} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to add value", "type", "byte[]", "val", v.length);} 
		return this;
	}
	public Db_stmt Crt_bry_as_str(String k, byte[] v)	{return Add_bry_as_str(Bool_.Y, k, v);}
	public Db_stmt Val_bry_as_str(String k, byte[] v)	{return Add_bry_as_str(Bool_.N, k, v);}
	public Db_stmt Val_bry_as_str(byte[] v)				{return Add_bry_as_str(Bool_.N, Key_na, v);}
	private Db_stmt Add_bry_as_str(boolean where, String k, byte[] v) {return Add_str(where, k, String_.new_u8(v));}
	public Db_stmt Crt_str(String k, String v)	{return Add_str(Bool_.Y, k, v);}
	public Db_stmt Val_str(String k, String v)	{return Add_str(Bool_.N, k, v);}
	public Db_stmt Val_str(String v)			{return Add_str(Bool_.N, Key_na, v);}
	private Db_stmt Add_str(boolean where, String k, String v) {
		try {Add(k, where, v);} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to add value", "type", "String", "val", v);} 
		return this;
	}
	public Db_stmt Crt_date(String k, DateAdp v)	{return Add_date(Bool_.Y, k, v);}
	public Db_stmt Val_date(String k, DateAdp v)	{return Add_date(Bool_.N, k, v);}
	private Db_stmt Add_date(boolean where, String k, DateAdp v) {
		try {Add(k, where, v);} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to add value", "type", "date", "val", v);} 
		return this;
	}
	public Db_stmt Crt_text(String k, String v)		{return Add_text(Bool_.Y, k, v);}
	public Db_stmt Val_text(String k, String v)		{return Add_text(Bool_.N, k, v);}
	private Db_stmt Add_text(boolean where, String k, String v) {
		try {Add(k, where, v);} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to add value", "type", "text", "val", v);} 
		return this;
	}
	public Db_stmt Val_rdr_(gplx.core.ios.Io_stream_rdr v, long rdr_len) {
		try {
			Bry_bfr bfr = Bry_bfr.new_();
			gplx.core.ios.Io_stream_rdr_.Load_all_to_bfr(bfr, v);
			Add("", Bool_.N, bfr.To_str_and_clear());
		} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to add value", "type", "rdr", "val", v);} 
		return this;
	}
	public boolean Exec_insert() {
		Mem_tbl tbl = engine.Tbls_get(qry.Base_table());
		if (tbl == null) throw Err_.new_wo_type("must call Create_tbl", "tbl", qry.Base_table());
		return tbl.Insert(this) == 1;
	}
	public int Exec_update() {
		Mem_tbl tbl = engine.Tbls_get(qry.Base_table());
		return tbl.Update(this);
	}
	public int Exec_delete() {
		Mem_tbl tbl = engine.Tbls_get(qry.Base_table());
		return tbl.Delete(this);
	}
	public DataRdr Exec_select() {throw Err_.new_unimplemented();}	
	public Db_rdr Exec_select__rls_auto() {return this.Exec_select__rls_manual();}
	public Db_rdr Exec_select__rls_manual() {
		Mem_tbl tbl = engine.Tbls_get(qry.Base_table());
		return tbl.Select(this);
	}
	public Object Exec_select_val() {throw Err_.new_unimplemented();}
	private void Add(String k, boolean where, Object v) {
		if (k == Dbmeta_fld_itm.Key_null) return;	// key is explicitly null; ignore; allows schema_2+ type definitions
		val_list.Add_if_dupe_use_1st(k, v);				// NOTE: only add if new; WHERE with IN will call Add many times; fld_ttl IN ('A.png', 'B.png');
		if (where) {
			List_adp list = (List_adp)crt_hash.Get_by(k);
			if (list == null) {
				list = List_adp_.new_();
				crt_hash.Add(k, list);
			}
			list.Add(v);
		}
	}
}
