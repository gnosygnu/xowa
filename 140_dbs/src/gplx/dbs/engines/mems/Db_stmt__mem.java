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
public class Db_stmt__mem implements Db_stmt {
	private final ListAdp val_list = ListAdp_.new_(); private int val_idx;
	public Db_stmt__mem(Db_engine__mem engine, Db_qry qry) {this.engine = engine; this.qry = qry;} private Db_engine__mem engine;
	public HashAdp Crts() {return crt_hash;} private final HashAdp crt_hash = HashAdp_.new_();
	public int Args_len() {return val_list.Count();}
	public Object Args_get_at(int i) {return val_list.FetchAt(i);}
	public Db_qry Qry() {return qry;} private Db_qry qry;
	public Db_stmt Reset_stmt() {return this;}
	public Db_stmt Clear() {
		val_idx = 0;
		val_list.Clear();
		crt_hash.Clear();
		return this;
	}
	public void Rls() {
		this.Clear();
	}
	public Db_stmt Crt_bool_as_byte(String k, boolean v)	{return Add_byte_by_bool(Bool_.Y, k, v);}
	public Db_stmt Val_bool_as_byte(String k, boolean v)	{return Add_byte_by_bool(Bool_.N, k, v);}
	public Db_stmt Val_bool_as_byte(boolean v)			{return Add_byte_by_bool(Bool_.N, null, v);}
	private Db_stmt Add_byte_by_bool(boolean where, String k, boolean v) {return Add_byte(where, k, v ? Bool_.Y_byte : Bool_.N_byte);}
	public Db_stmt Crt_byte(String k, byte v)	{return Add_byte(Bool_.Y, k, v);}
	public Db_stmt Val_byte(String k, byte v)	{return Add_byte(Bool_.N, k, v);}
	public Db_stmt Val_byte(byte v)			{return Add_byte(Bool_.N, null, v);}
	private Db_stmt Add_byte(boolean where, String k, byte v) {
		try {Add(++val_idx, k, where, v);} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "byte", v);}
		return this;
	}
	public Db_stmt Crt_int(String k, int v)	{return Add_int(Bool_.Y, k, v);}
	public Db_stmt Val_int(String k, int v)	{return Add_int(Bool_.N, k, v);}
	public Db_stmt Val_int(int v)			{return Add_int(Bool_.N, null, v);}
	private Db_stmt Add_int(boolean where, String k, int v) {
		try {Add(++val_idx, k, where, v);} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "int", v);}
		return this;
	}
	public Db_stmt Crt_long(String k, long v)	{return Add_long(Bool_.Y, k, v);}
	public Db_stmt Val_long(String k, long v)	{return Add_long(Bool_.N, k, v);}
	public Db_stmt Val_long(long v)			{return Add_long(Bool_.N, null, v);}
	private Db_stmt Add_long(boolean where, String k, long v) {
		try {Add(++val_idx, k, where, v);} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "long", v);} 
		return this;
	}
	public Db_stmt Crt_float(String k, float v)	{return Add_float(Bool_.Y, k, v);}
	public Db_stmt Val_float(String k, float v)	{return Add_float(Bool_.N, k, v);}
	public Db_stmt Val_float(float v)			{return Add_float(Bool_.N, null, v);}
	private Db_stmt Add_float(boolean where, String k, float v) {
		try {Add(++val_idx, k, where, v);} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "float", v);}
		return this;
	}
	public Db_stmt Crt_double(String k, double v)	{return Add_double(Bool_.Y, k, v);}
	public Db_stmt Val_double(String k, double v)	{return Add_double(Bool_.N, k, v);}
	public Db_stmt Val_double(double v)			{return Add_double(Bool_.N, null, v);}
	private Db_stmt Add_double(boolean where, String k, double v) {
		try {Add(++val_idx, k, where, v);} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "double", v);} 
		return this;
	}
	public Db_stmt Crt_decimal(String k, DecimalAdp v)	{return Add_decimal(Bool_.Y, k, v);}
	public Db_stmt Val_decimal(String k, DecimalAdp v)	{return Add_decimal(Bool_.N, k, v);}
	public Db_stmt Val_decimal(DecimalAdp v)			{return Add_decimal(Bool_.N, null, v);}
	private Db_stmt Add_decimal(boolean where, String k, DecimalAdp v) {
		try {Add(++val_idx, k, where, v);} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "decimal", v);} 
		return this;
	}
	public Db_stmt Crt_bry(String k, byte[] v)	{return Add_bry(Bool_.Y, k, v);}
	public Db_stmt Val_bry(String k, byte[] v)	{return Add_bry(Bool_.N, k, v);}
	public Db_stmt Val_bry(byte[] v)			{return Add_bry(Bool_.N, null, v);}
	private Db_stmt Add_bry(boolean where, String k, byte[] v) {
		try {Add(++val_idx, k, where, v);} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "byte[]", v.length);} 
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
		try {Add(++val_idx, k, where, v);} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "String", v);} 
		return this;
	}
	public Db_stmt Val_rdr_(gplx.ios.Io_stream_rdr v, long rdr_len) {
		try {
			Bry_bfr bfr = Bry_bfr.new_();
			gplx.ios.Io_stream_rdr_.Load_all_to_bfr(bfr, v);
			Add(++val_idx, "", Bool_.N, bfr.Xto_str_and_clear());
		} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "rdr", v);} 
		return this;
	}
	public boolean Exec_insert() {
		Mem_tbl tbl = engine.Tbls_get(qry.Base_table());
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
	public DataRdr Exec_select() {throw Err_.not_implemented_();}	
	public Db_rdr Exec_select_as_rdr() {
		Mem_tbl tbl = engine.Tbls_get(qry.Base_table());
		return tbl.Select(this);
	}	
	public Object Exec_select_val() {
		try {Object rv = Db_qry_select.Rdr_to_val(this.Exec_select()); return rv;} catch (Exception e) {throw Err_.err_(e, "failed to exec select_val: tbl={0}", qry.Base_table());}
	}
	private void Add(int idx, String k, boolean where, Object v) {
		val_list.Add(v);
		if (where) {
			ListAdp list = (ListAdp)crt_hash.Fetch(k);
			if (list == null) {
				list = ListAdp_.new_();
				crt_hash.Add(k, list);
			}
			list.Add(v);
		}
	}
}
