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
package gplx.dbs.qrys;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.ClassUtl;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDecimal;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.utls.FloatUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.custom.brys.fmts.fmtrs.BryFmtr;
import gplx.core.stores.DataRdr;
import gplx.core.stores.DataRdr_;
import gplx.dbs.Db_conn;
import gplx.dbs.Db_qry;
import gplx.dbs.Db_rdr;
import gplx.dbs.Db_rdr_;
import gplx.dbs.Db_stmt;
import gplx.dbs.DbmetaFldItm;
import gplx.dbs.engines.Db_engine;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.constants.AsciiByte;
public class Db_stmt_sql implements Db_stmt {// used for formatting SQL statements; not used for actual insert into database
	private static final String Key_na = ""; // key is not_available; only called by procs with signature of Val(<type> v);
	private final List_adp args = List_adp_.New();
	private final BryWtr tmp_bfr = BryWtr.New();
	private final BryFmtr tmp_fmtr = BryFmtr.New();
	public void Ctor_stmt(Db_engine engine, Db_qry qry) {}
	public Db_conn Conn() {return conn;} public void Conn_(Db_conn v) {this.conn = v;} Db_conn conn;
	public Db_stmt Reset_stmt() {return this;}
	public Db_stmt Crt_bool_as_byte(String k, boolean v)	{return Add_byte_by_bool(BoolUtl.Y, k, v);}
	public Db_stmt Val_bool_as_byte(String k, boolean v)	{return Add_byte_by_bool(BoolUtl.N, k, v);}
	public Db_stmt Val_bool_as_byte(boolean v)				{return Add_byte_by_bool(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_byte_by_bool(boolean where, String k, boolean v) {return Add_byte(where, k, v ? BoolUtl.YByte : BoolUtl.NByte);}
	public Db_stmt Crt_byte(String k, byte v)	{return Add_byte(BoolUtl.Y, k, v);}
	public Db_stmt Val_byte(String k, byte v)	{return Add_byte(BoolUtl.N, k, v);}
	public Db_stmt Val_byte(byte v)				{return Add_byte(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_byte(boolean where, String k, byte v) {
		try {Add(k, ByteUtl.ToStr(v));} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to add value", "type", "byte", "val", v);}
		return this;
	}
	public Db_stmt Crt_int(String k, int v)	{return Add_int(BoolUtl.Y, k, v);}
	public Db_stmt Val_int_by_bool(String k, boolean v)	{return Add_int(BoolUtl.N, k, v ? 1 : 0);}
	public Db_stmt Val_int(String k, int v)	{return Add_int(BoolUtl.N, k, v);}
	public Db_stmt Val_int(int v)			{return Add_int(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_int(boolean where, String k, int v) {
		try {Add(k, IntUtl.ToStr(v));} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to add value", "type", "int", "val", v);}
		return this;
	}
	public Db_stmt Crt_long(String k, long v)	{return Add_long(BoolUtl.Y, k, v);}
	public Db_stmt Val_long(String k, long v)	{return Add_long(BoolUtl.N, k, v);}
	public Db_stmt Val_long(long v)				{return Add_long(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_long(boolean where, String k, long v) {
		try {Add(k, LongUtl.ToStr(v));} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to add value", "type", "long", "val", v);}
		return this;
	}
	public Db_stmt Crt_float(String k, float v)	{return Add_float(BoolUtl.Y, k, v);}
	public Db_stmt Val_float(String k, float v)	{return Add_float(BoolUtl.N, k, v);}
	public Db_stmt Val_float(float v)			{return Add_float(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_float(boolean where, String k, float v) {
		try {Add(k, FloatUtl.ToStr(v));} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to add value", "type", "float", "val", v);}
		return this;
	}
	public Db_stmt Crt_double(String k, double v)	{return Add_double(BoolUtl.Y, k, v);}
	public Db_stmt Val_double(String k, double v)	{return Add_double(BoolUtl.N, k, v);}
	public Db_stmt Val_double(double v)				{return Add_double(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_double(boolean where, String k, double v) {
		try {Add(k, DoubleUtl.ToStr(v));} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to add value", "type", "double", "val", v);}
		return this;
	}
	public Db_stmt Crt_decimal(String k, GfoDecimal v)	{return Add_decimal(BoolUtl.Y, k, v);}
	public Db_stmt Val_decimal(String k, GfoDecimal v)	{return Add_decimal(BoolUtl.N, k, v);}
	public Db_stmt Val_decimal(GfoDecimal v)			{return Add_decimal(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_decimal(boolean where, String k, GfoDecimal v) {
		try {Add(k, v.ToStr());} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to add value", "type", "decimal", "val", v);}
		return this;
	}
	public Db_stmt Crt_bry(String k, byte[] v)	{return Add_bry(BoolUtl.Y, k, v);}
	public Db_stmt Val_bry(String k, byte[] v)	{return Add_bry(BoolUtl.N, k, v);}
	public Db_stmt Val_bry(byte[] v)			{return Add_bry(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_bry(boolean where, String k, byte[] v) {// HACK: convert to String b/c tdb does not support byte[]
		try {Add(k, Val_str_wrap(StringUtl.NewU8(v)));} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to add value", "type", "byte[]", "val", v.length);}
		return this;
	}
	public Db_stmt Crt_bry_as_str(String k, byte[] v)	{return Add_bry_as_str(BoolUtl.Y, k, v);}
	public Db_stmt Val_bry_as_str(String k, byte[] v)	{return Add_bry_as_str(BoolUtl.N, k, v);}
	public Db_stmt Val_bry_as_str(byte[] v)				{return Add_bry_as_str(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_bry_as_str(boolean where, String k, byte[] v) {return Add_str(where, k, StringUtl.NewU8(v));}
	public Db_stmt Crt_str(String k, String v)	{return Add_str(BoolUtl.Y, k, v);}
	public Db_stmt Val_str(String k, String v)	{return Add_str(BoolUtl.N, k, v);}
	public Db_stmt Val_str(String v)			{return Add_str(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_str(boolean where, String k, String v) {
		try {Add(k, Val_str_wrap(v));} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to add value", "type", "String", "val", v);}
		return this;
	}
	public Db_stmt Crt_date(String k, GfoDate v)	{return Add_date(BoolUtl.Y, k, v);}
	public Db_stmt Val_date(String k, GfoDate v)	{return Add_date(BoolUtl.N, k, v);}
	private Db_stmt Add_date(boolean where, String k, GfoDate v) {
		try {Add(k, Val_str_wrap(v.ToStrFmtIso8561()));} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to add value", "type", "date", "val", v);}
		return this;
	}
	public Db_stmt Crt_text(String k, String v)	{return Add_text(BoolUtl.Y, k, v);}
	public Db_stmt Val_text(String k, String v)	{return Add_text(BoolUtl.N, k, v);}
	private Db_stmt Add_text(boolean where, String k, String v) {
		try {Add(k, Val_str_wrap(v));} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to add value", "type", "text", "val", v);}
		return this;
	}
	public Db_stmt Val_rdr_(gplx.core.ios.streams.Io_stream_rdr v, long rdr_len) {
		try {
			BryWtr bfr = BryWtr.New();
			gplx.core.ios.streams.Io_stream_rdr_.Load_all_to_bfr(bfr, v);
			Add(Key_na, bfr.ToStrAndClear());
		} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to add value", "type", "rdr", "val", v);}
		return this;
	}
	private String Val_str_wrap(String v) {
		return "'" + StringUtl.Replace(v, "'", "\\'") + "'";
	}
	public boolean Exec_insert() {
		try {boolean rv = conn.Exec_qry(Db_qry_sql.dml_(this.Xto_sql())) != 0; return rv;} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to exec prepared statement", "sql", sql_orig);}
	}
	public int Exec_update() {
		try {int rv = conn.Exec_qry(Db_qry_sql.dml_(this.Xto_sql())); return rv;} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to exec prepared statement", "sql", sql_orig);}
	}
	public int Exec_delete() {
		try {int rv = conn.Exec_qry(Db_qry_sql.dml_(this.Xto_sql())); return rv;} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to exec prepared statement", "sql", sql_orig);}
	}
	public DataRdr Exec_select() {
		try {DataRdr rv = conn.Exec_qry_as_old_rdr(Db_qry_sql.rdr_(this.Xto_sql())); return rv;} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to exec prepared statement", "sql", sql_orig);}
	}	
	public Db_rdr Exec_select__rls_auto()	{return Db_rdr_.Empty;}
	public Db_rdr Exec_select__rls_manual() {return Db_rdr_.Empty;}
	public Object Exec_select_val() {
		try {Object rv = DataRdr_.Read_1st_row_and_1st_fld(this.Exec_select()); return rv;} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to exec prepared statement", "sql", sql_orig);}
	}
	public Db_stmt Clear() {
		args.Clear();
		return this;
	}
	public void Rls() {this.Clear();}
	public void Add(String k, String v) {
		if (k == DbmetaFldItm.KeyNull) return;	// key is explicitly null; ignore; allows version_2+ type definitions
		args.Add(v);
	}
	public String Xto_sql() {
		tmp_fmtr.BldToBfrMany(tmp_bfr, (Object[])args.ToAryAndClear(Object.class));
		return tmp_bfr.ToStrAndClear();
	}
	public int Args_len() {return args.Len();}
	public String Args_get_at(int i) {return (String)args.GetAt(i);}
	private String sql_orig; 
	public Db_qry Qry() {return qry;} private Db_qry qry;
	public Db_stmt Parse(Db_qry qry, String sql_str) {
		this.qry = qry;
		this.sql_orig = sql_str;
		Init_fmtr(tmp_bfr, tmp_fmtr, sql_str);
		return this;
	}
	private static void Init_fmtr(BryWtr tmp_bfr, BryFmtr tmp_fmtr, String sql_str) {
		byte[] sql_bry = BryUtl.NewU8(sql_str);
		int arg_idx = 0; int pos_prv = 0;
		tmp_bfr.Clear();
		while (true) {
			int pos_cur = BryFind.FindFwd(sql_bry, AsciiByte.Question, pos_prv); if (pos_cur == BryFind.NotFound) break;
			tmp_bfr.AddMid(sql_bry, pos_prv, pos_cur);
			tmp_bfr.AddByte(AsciiByte.Tilde).AddByte(AsciiByte.CurlyBgn);
			tmp_bfr.AddIntVariable(arg_idx++);
			tmp_bfr.AddByte(AsciiByte.CurlyEnd);
			pos_prv = pos_cur + 1;
		}
		tmp_bfr.AddMid(sql_bry, pos_prv, sql_bry.length);
		tmp_fmtr.FmtSet(tmp_bfr.ToBryAndClear());
	}
	public static String Xto_str(BryWtr tmp_bfr, BryFmtr tmp_fmtr, String sql_str, List_adp args) {
		Init_fmtr(tmp_bfr, tmp_fmtr, sql_str);
		Object[] ary = args.ToObjAry();
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Object obj = ary[i];
			String str = "";
			if (obj == null)
				str = "NULL";
			else {
				str = ObjectUtl.ToStrOrNull(obj);
				if (ClassUtl.Eq(obj.getClass(), StringUtl.ClsRefType))
					str = "'" + StringUtl.Replace(str, "'", "''") + "'";
			}
			ary[i] = str;
		}
		tmp_fmtr.BldToBfrMany(tmp_bfr, ary);
		return tmp_bfr.ToStrAndClear();
	} 
}
