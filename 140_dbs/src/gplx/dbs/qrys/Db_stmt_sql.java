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
import gplx.core.brys.fmtrs.*; import gplx.core.stores.*;
import gplx.dbs.engines.*;
public class Db_stmt_sql implements Db_stmt {// used for formatting SQL statements; not used for actual insert into database
	private static final String Key_na = ""; // key is not_available; only called by procs with signature of Val(<type> v);
	private final    List_adp args = List_adp_.New();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private final    Bry_fmtr tmp_fmtr = Bry_fmtr.new_();
	public void Ctor_stmt(Db_engine engine, Db_qry qry) {}
	public Db_conn Conn() {return conn;} public void Conn_(Db_conn v) {this.conn = v;} Db_conn conn;
	public Db_stmt Reset_stmt() {return this;}
	public Db_stmt Crt_bool_as_byte(String k, boolean v)	{return Add_byte_by_bool(Bool_.Y, k, v);}
	public Db_stmt Val_bool_as_byte(String k, boolean v)	{return Add_byte_by_bool(Bool_.N, k, v);}
	public Db_stmt Val_bool_as_byte(boolean v)				{return Add_byte_by_bool(Bool_.N, Key_na, v);}
	private Db_stmt Add_byte_by_bool(boolean where, String k, boolean v) {return Add_byte(where, k, v ? Bool_.Y_byte : Bool_.N_byte);}
	public Db_stmt Crt_byte(String k, byte v)	{return Add_byte(Bool_.Y, k, v);}
	public Db_stmt Val_byte(String k, byte v)	{return Add_byte(Bool_.N, k, v);}
	public Db_stmt Val_byte(byte v)				{return Add_byte(Bool_.N, Key_na, v);}
	private Db_stmt Add_byte(boolean where, String k, byte v) {
		try {Add(k, Byte_.To_str(v));} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to add value", "type", "byte", "val", v);}
		return this;
	}
	public Db_stmt Crt_int(String k, int v)	{return Add_int(Bool_.Y, k, v);}
	public Db_stmt Val_int_by_bool(String k, boolean v)	{return Add_int(Bool_.N, k, v ? 1 : 0);}
	public Db_stmt Val_int(String k, int v)	{return Add_int(Bool_.N, k, v);}
	public Db_stmt Val_int(int v)			{return Add_int(Bool_.N, Key_na, v);}
	private Db_stmt Add_int(boolean where, String k, int v) {
		try {Add(k, Int_.To_str(v));} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to add value", "type", "int", "val", v);}
		return this;
	}
	public Db_stmt Crt_long(String k, long v)	{return Add_long(Bool_.Y, k, v);}
	public Db_stmt Val_long(String k, long v)	{return Add_long(Bool_.N, k, v);}
	public Db_stmt Val_long(long v)				{return Add_long(Bool_.N, Key_na, v);}
	private Db_stmt Add_long(boolean where, String k, long v) {
		try {Add(k, Long_.To_str(v));} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to add value", "type", "long", "val", v);} 
		return this;
	}
	public Db_stmt Crt_float(String k, float v)	{return Add_float(Bool_.Y, k, v);}
	public Db_stmt Val_float(String k, float v)	{return Add_float(Bool_.N, k, v);}
	public Db_stmt Val_float(float v)			{return Add_float(Bool_.N, Key_na, v);}
	private Db_stmt Add_float(boolean where, String k, float v) {
		try {Add(k, Float_.To_str(v));} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to add value", "type", "float", "val", v);}
		return this;
	}
	public Db_stmt Crt_double(String k, double v)	{return Add_double(Bool_.Y, k, v);}
	public Db_stmt Val_double(String k, double v)	{return Add_double(Bool_.N, k, v);}
	public Db_stmt Val_double(double v)				{return Add_double(Bool_.N, Key_na, v);}
	private Db_stmt Add_double(boolean where, String k, double v) {
		try {Add(k, Double_.To_str(v));} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to add value", "type", "double", "val", v);} 
		return this;
	}
	public Db_stmt Crt_decimal(String k, Decimal_adp v)	{return Add_decimal(Bool_.Y, k, v);}
	public Db_stmt Val_decimal(String k, Decimal_adp v)	{return Add_decimal(Bool_.N, k, v);}
	public Db_stmt Val_decimal(Decimal_adp v)			{return Add_decimal(Bool_.N, Key_na, v);}
	private Db_stmt Add_decimal(boolean where, String k, Decimal_adp v) {
		try {Add(k, v.To_str());} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to add value", "type", "decimal", "val", v);} 
		return this;
	}
	public Db_stmt Crt_bry(String k, byte[] v)	{return Add_bry(Bool_.Y, k, v);}
	public Db_stmt Val_bry(String k, byte[] v)	{return Add_bry(Bool_.N, k, v);}
	public Db_stmt Val_bry(byte[] v)			{return Add_bry(Bool_.N, Key_na, v);}
	private Db_stmt Add_bry(boolean where, String k, byte[] v) {// HACK: convert to String b/c tdb does not support byte[]
		try {Add(k, Val_str_wrap(String_.new_u8(v)));} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to add value", "type", "byte[]", "val", v.length);} 
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
		try {Add(k, Val_str_wrap(v));} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to add value", "type", "String", "val", v);} 
		return this;
	}
	public Db_stmt Crt_date(String k, DateAdp v)	{return Add_date(Bool_.Y, k, v);}
	public Db_stmt Val_date(String k, DateAdp v)	{return Add_date(Bool_.N, k, v);}
	private Db_stmt Add_date(boolean where, String k, DateAdp v) {
		try {Add(k, Val_str_wrap(v.XtoStr_fmt_iso_8561()));} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to add value", "type", "date", "val", v);} 
		return this;
	}
	public Db_stmt Crt_text(String k, String v)	{return Add_text(Bool_.Y, k, v);}
	public Db_stmt Val_text(String k, String v)	{return Add_text(Bool_.N, k, v);}
	private Db_stmt Add_text(boolean where, String k, String v) {
		try {Add(k, Val_str_wrap(v));} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to add value", "type", "text", "val", v);} 
		return this;
	}
	public Db_stmt Val_rdr_(gplx.core.ios.streams.Io_stream_rdr v, long rdr_len) {
		try {
			Bry_bfr bfr = Bry_bfr_.New();
			gplx.core.ios.streams.Io_stream_rdr_.Load_all_to_bfr(bfr, v);
			Add(Key_na, bfr.To_str_and_clear());
		} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to add value", "type", "rdr", "val", v);} 
		return this;
	}
	private String Val_str_wrap(String v) {
		return "'" + String_.Replace(v, "'", "\\'") + "'";
	}
	public boolean Exec_insert() {
		try {boolean rv = conn.Exec_qry(Db_qry_sql.dml_(this.Xto_sql())) != 0; return rv;} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to exec prepared statement", "sql", sql_orig);}
	}
	public int Exec_update() {
		try {int rv = conn.Exec_qry(Db_qry_sql.dml_(this.Xto_sql())); return rv;} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to exec prepared statement", "sql", sql_orig);}
	}
	public int Exec_delete() {
		try {int rv = conn.Exec_qry(Db_qry_sql.dml_(this.Xto_sql())); return rv;} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to exec prepared statement", "sql", sql_orig);}
	}
	public DataRdr Exec_select() {
		try {DataRdr rv = conn.Exec_qry_as_old_rdr(Db_qry_sql.rdr_(this.Xto_sql())); return rv;} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to exec prepared statement", "sql", sql_orig);}
	}	
	public Db_rdr Exec_select__rls_auto()	{return Db_rdr_.Empty;}
	public Db_rdr Exec_select__rls_manual() {return Db_rdr_.Empty;}
	public Object Exec_select_val() {
		try {Object rv = DataRdr_.Read_1st_row_and_1st_fld(this.Exec_select()); return rv;} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to exec prepared statement", "sql", sql_orig);}
	}
	public Db_stmt Clear() {
		args.Clear();
		return this;
	}
	public void Rls() {this.Clear();}
	public void Add(String k, String v) {
		if (k == Dbmeta_fld_itm.Key_null) return;	// key is explicitly null; ignore; allows version_2+ type definitions
		args.Add(v);
	}
	public String Xto_sql() {
		tmp_fmtr.Bld_bfr_many(tmp_bfr, (Object[])args.To_ary_and_clear(Object.class));
		return tmp_bfr.To_str_and_clear();
	}
	public int Args_len() {return args.Count();}
	public String Args_get_at(int i) {return (String)args.Get_at(i);}
	private String sql_orig; 
	public Db_qry Qry() {return qry;} private Db_qry qry;
	public Db_stmt Parse(Db_qry qry, String sql_str) {
		this.qry = qry;
		this.sql_orig = sql_str;
		Init_fmtr(tmp_bfr, tmp_fmtr, sql_str);
		return this;
	}
	private static void Init_fmtr(Bry_bfr tmp_bfr, Bry_fmtr tmp_fmtr, String sql_str) {
		byte[] sql_bry = Bry_.new_u8(sql_str);
		int arg_idx = 0; int pos_prv = 0;
		tmp_bfr.Clear();
		while (true) {
			int pos_cur = Bry_find_.Find_fwd(sql_bry, Byte_ascii.Question, pos_prv); if (pos_cur == Bry_find_.Not_found) break;
			tmp_bfr.Add_mid(sql_bry, pos_prv, pos_cur);
			tmp_bfr.Add_byte(Byte_ascii.Tilde).Add_byte(Byte_ascii.Curly_bgn);
			tmp_bfr.Add_int_variable(arg_idx++);
			tmp_bfr.Add_byte(Byte_ascii.Curly_end);
			pos_prv = pos_cur + 1;
		}
		tmp_bfr.Add_mid(sql_bry, pos_prv, sql_bry.length);
		tmp_fmtr.Fmt_(tmp_bfr.To_bry_and_clear());
	}
	public static String Xto_str(Bry_bfr tmp_bfr, Bry_fmtr tmp_fmtr, String sql_str, List_adp args) {
		Init_fmtr(tmp_bfr, tmp_fmtr, sql_str);
		Object[] ary = args.To_obj_ary();
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Object obj = ary[i];
			String str = "";
			if (obj == null)
				str = "NULL";
			else {
				str = Object_.Xto_str_strict_or_null(obj);
				if (Type_adp_.Eq(obj.getClass(), String_.Cls_ref_type))
					str = "'" + String_.Replace(str, "'", "''") + "'";					
			}
			ary[i] = str;
		}
		tmp_fmtr.Bld_bfr_many(tmp_bfr, ary);
		return tmp_bfr.To_str_and_clear();
	} 
}
