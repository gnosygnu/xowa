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
class Db_stmt_sql implements Db_stmt {
	private Bry_bfr tmp_bfr = Bry_bfr.new_();
	private Bry_fmtr tmp_fmtr = Bry_fmtr.new_();
	private int val_idx = 0;
	public Db_provider Provider() {return provider;} public void Provider_(Db_provider v) {this.provider = v;} Db_provider provider;
	public Db_stmt New() {return this;}
	public Db_stmt Val_bool_(boolean v) {
		try {Add(++val_idx, v ? "true" : "false");} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "int", v);}
		return this;
	}
	public Db_stmt Val_byte_by_bool_(boolean v) {return Val_byte_(v ? Bool_.Y_byte : Bool_.N_byte);}
	public Db_stmt Val_byte_(byte v) {
		try {Add(++val_idx, Byte_.Xto_str(v));} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "byte", v);}
		return this;
	}
	public Db_stmt Val_int_(int v) {
		try {Add(++val_idx, Int_.Xto_str(v));} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "int", v);}
		return this;
	}
	public Db_stmt Val_long_(long v) {
		try {Add(++val_idx, Long_.Xto_str(v));} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "long", v);} 
		return this;
	}
	public Db_stmt Val_float_(float v) {
		try {Add(++val_idx, Float_.Xto_str(v));} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "float", v);}
		return this;
	}
	public Db_stmt Val_double_(double v) {
		try {Add(++val_idx, Double_.Xto_str(v));} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "double", v);} 
		return this;
	}
	public Db_stmt Val_decimal_(DecimalAdp v) {
		try {Add(++val_idx, v.Xto_str());} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "decimal", v);} 
		return this;
	}
	public Db_stmt Val_bry_by_str_(String v) {return Val_bry_(Bry_.new_utf8_(v));}
	public Db_stmt Val_bry_(byte[] v) {	// HACK: convert to String b/c tdb does not support byte[]
		try {Add(++val_idx, Val_str_wrap(String_.new_utf8_(v)));} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "byte[]", v.length);} 
		return this;
	}
	public Db_stmt Val_str_by_bry_(byte[] v) {return Val_str_(String_.new_utf8_(v));}
	public Db_stmt Val_str_(String v) {
		try {Add(++val_idx, Val_str_wrap(v));} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "String", v);} 
		return this;
	}
	public Db_stmt Val_rdr_(gplx.ios.Io_stream_rdr v, long rdr_len) {
		try {
			Bry_bfr bfr = Bry_bfr.new_();
			gplx.ios.Io_stream_rdr_.Load_all_to_bfr(bfr, v);
			Add(++val_idx, bfr.Xto_str_and_clear());
		} catch (Exception e) {throw Err_.err_(e, "failed to add value: type={0} val={1}", "rdr", v);} 
		return this;
	}
	String Val_str_wrap(String v) {
		return "'" + String_.Replace(v, "'", "\\'") + "'";
	}
	public boolean Exec_insert() {
		try {boolean rv = provider.Exec_qry(Db_qry_sql.dml_(this.Xto_sql())) != 0; return rv;} catch (Exception e) {throw Err_.err_(e, "failed to exec prepared statement: sql={0}", sql_orig);}
	}
	public int Exec_update() {
		try {int rv = provider.Exec_qry(Db_qry_sql.dml_(this.Xto_sql())); return rv;} catch (Exception e) {throw Err_.err_(e, "failed to exec prepared statement: sql={0}", sql_orig);}
	}
	public int Exec_delete() {
		try {int rv = provider.Exec_qry(Db_qry_sql.dml_(this.Xto_sql())); return rv;} catch (Exception e) {throw Err_.err_(e, "failed to exec prepared statement: sql={0}", sql_orig);}
	}
	public DataRdr Exec_select() {
		try {DataRdr rv = provider.Exec_qry_as_rdr(Db_qry_sql.rdr_(this.Xto_sql())); return rv;} catch (Exception e) {throw Err_.err_(e, "failed to exec prepared statement: sql={0}", sql_orig);}
	}	
	public Db_rdr Exec_select_as_rdr() {throw Err_.not_implemented_();}	
	public Object Exec_select_val() {
		try {Object rv = Db_qry_select.Rdr_to_val(this.Exec_select()); return rv;} catch (Exception e) {throw Err_.err_(e, "failed to exec prepared statement: sql={0}", sql_orig);}
	}
	public Db_stmt Clear() {
		val_idx = 0;
		args.Clear();
		return this;
	}
	public void Rls() {
		this.Clear();
	}
	public void Add(String v) {Add(-1, v);}
	public void Add(int idx, String v) {args.Add(v);} ListAdp args = ListAdp_.new_();
	public String Xto_sql() {
		tmp_fmtr.Bld_bfr_many(tmp_bfr, (Object[])args.Xto_ary_and_clear(Object.class));
		return tmp_bfr.Xto_str_and_clear();
	}
	String sql_orig; 
	public Db_stmt Parse(String sql_str) {
		this.sql_orig = sql_str;
		int arg_idx = 0;
		byte[] src = Bry_.new_utf8_(sql_str);
		int pos_prv = 0;
		tmp_bfr.Clear();
		while (true) {
			int pos_cur = Bry_finder.Find_fwd(src, Byte_ascii.Question, pos_prv);
			if (pos_cur == Bry_.NotFound) break;
			tmp_bfr.Add_mid(src, pos_prv, pos_cur);
			tmp_bfr.Add_byte(Byte_ascii.Tilde).Add_byte(Byte_ascii.Curly_bgn);
			tmp_bfr.Add_int_variable(arg_idx++);
			tmp_bfr.Add_byte(Byte_ascii.Curly_end);
			pos_prv = pos_cur + 1;
		}
		tmp_bfr.Add_mid(src, pos_prv, src.length);
		tmp_fmtr.Fmt_(tmp_bfr.Xto_bry_and_clear());
		return this;
	} 
}
