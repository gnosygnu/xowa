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
package gplx.dbs.engines.mems;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.core.stores.DataRdr;
import gplx.dbs.Db_qry;
import gplx.dbs.Db_rdr;
import gplx.dbs.Db_stmt;
import gplx.dbs.DbmetaFldItm;
import gplx.dbs.engines.Db_engine;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.errs.ErrUtl;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDecimal;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.StringUtl;
public class Mem_stmt implements Db_stmt {
	private static final String Key_na = ""; // key is not_available; only called by procs with signature of Val(<type> v);
	private final Ordered_hash val_list = Ordered_hash_.New();
	public Mem_stmt(Mem_engine engine, Db_qry qry) {Ctor_stmt(engine, qry);} private Mem_engine engine;
	public void Ctor_stmt(Db_engine engine, Db_qry qry) {this.engine = (Mem_engine)engine; this.qry = qry;}
	public Mem_stmt_args Stmt_args() {return stmt_args;} private final Mem_stmt_args stmt_args = new Mem_stmt_args();
	public int Args_len() {return val_list.Len();}
	public Object Args_get_at(int i)	{return val_list.GetAt(i);}
	public Object Args_get_by(String k) {return val_list.GetByOrNull(k);}
	public Db_qry Qry() {return qry;} private Db_qry qry;
	public Db_stmt Reset_stmt() {return this;}
	public Db_stmt Clear() {
		val_list.Clear();
		stmt_args.Clear();
		return this;
	}
	public void Rls() {this.Clear();}
	public Db_stmt Crt_bool_as_byte(String k, boolean v)	{return Add_byte_by_bool(BoolUtl.Y, k, v);}
	public Db_stmt Val_bool_as_byte(String k, boolean v)	{return Add_byte_by_bool(BoolUtl.N, k, v);}
	public Db_stmt Val_bool_as_byte(boolean v)				{return Add_byte_by_bool(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_byte_by_bool(boolean where, String k, boolean v) {return Add_byte(where, k, v ? BoolUtl.YByte : BoolUtl.NByte);}
	public Db_stmt Crt_byte(String k, byte v)	{return Add_byte(BoolUtl.Y, k, v);}
	public Db_stmt Val_byte(String k, byte v)	{return Add_byte(BoolUtl.N, k, v);}
	public Db_stmt Val_byte(byte v)				{return Add_byte(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_byte(boolean where, String k, byte v) {
		try {Add(k, where, v);} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to add value", "type", "byte", "val", v);}
		return this;
	}
	public Db_stmt Crt_int(String k, int v)	{return Add_int(BoolUtl.Y, k, v);}
	public Db_stmt Val_int_by_bool(String k, boolean v)	{return Add_int(BoolUtl.N, k, v ? 1 : 0);}
	public Db_stmt Val_int(String k, int v)	{return Add_int(BoolUtl.N, k, v);}
	public Db_stmt Val_int(int v)			{return Add_int(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_int(boolean where, String k, int v) {
		try {Add(k, where, v);} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to add value", "type", "int", "val", v);}
		return this;
	}
	public Db_stmt Crt_long(String k, long v)	{return Add_long(BoolUtl.Y, k, v);}
	public Db_stmt Val_long(String k, long v)	{return Add_long(BoolUtl.N, k, v);}
	public Db_stmt Val_long(long v)				{return Add_long(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_long(boolean where, String k, long v) {
		try {Add(k, where, v);} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to add value", "type", "long", "val", v);}
		return this;
	}
	public Db_stmt Crt_float(String k, float v)	{return Add_float(BoolUtl.Y, k, v);}
	public Db_stmt Val_float(String k, float v)	{return Add_float(BoolUtl.N, k, v);}
	public Db_stmt Val_float(float v)			{return Add_float(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_float(boolean where, String k, float v) {
		try {Add(k, where, v);} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to add value", "type", "float", "val", v);}
		return this;
	}
	public Db_stmt Crt_double(String k, double v)	{return Add_double(BoolUtl.Y, k, v);}
	public Db_stmt Val_double(String k, double v)	{return Add_double(BoolUtl.N, k, v);}
	public Db_stmt Val_double(double v)				{return Add_double(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_double(boolean where, String k, double v) {
		try {Add(k, where, v);} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to add value", "type", "double", "val", v);}
		return this;
	}
	public Db_stmt Crt_decimal(String k, GfoDecimal v)	{return Add_decimal(BoolUtl.Y, k, v);}
	public Db_stmt Val_decimal(String k, GfoDecimal v)	{return Add_decimal(BoolUtl.N, k, v);}
	public Db_stmt Val_decimal(GfoDecimal v)			{return Add_decimal(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_decimal(boolean where, String k, GfoDecimal v) {
		try {Add(k, where, v);} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to add value", "type", "decimal", "val", v);}
		return this;
	}
	public Db_stmt Crt_bry(String k, byte[] v)	{return Add_bry(BoolUtl.Y, k, v);}
	public Db_stmt Val_bry(String k, byte[] v)	{return Add_bry(BoolUtl.N, k, v);}
	public Db_stmt Val_bry(byte[] v)			{return Add_bry(BoolUtl.N, Key_na, v);}
	private Db_stmt Add_bry(boolean where, String k, byte[] v) {
		try {Add(k, where, v);} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to add value", "type", "byte[]", "val", v.length);}
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
		try {Add(k, where, v);} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to add value", "type", "String", "val", v);}
		return this;
	}
	public Db_stmt Crt_date(String k, GfoDate v)	{return Add_date(BoolUtl.Y, k, v);}
	public Db_stmt Val_date(String k, GfoDate v)	{return Add_date(BoolUtl.N, k, v);}
	private Db_stmt Add_date(boolean where, String k, GfoDate v) {
		try {Add(k, where, v);} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to add value", "type", "date", "val", v);}
		return this;
	}
	public Db_stmt Crt_text(String k, String v)		{return Add_text(BoolUtl.Y, k, v);}
	public Db_stmt Val_text(String k, String v)		{return Add_text(BoolUtl.N, k, v);}
	private Db_stmt Add_text(boolean where, String k, String v) {
		try {Add(k, where, v);} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to add value", "type", "text", "val", v);}
		return this;
	}
	public Db_stmt Val_rdr_(gplx.core.ios.streams.Io_stream_rdr v, long rdr_len) {
		try {
			BryWtr bfr = BryWtr.New();
			gplx.core.ios.streams.Io_stream_rdr_.Load_all_to_bfr(bfr, v);
			Add("", BoolUtl.N, bfr.ToStrAndClear());
		} catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to add value", "type", "rdr", "val", v);}
		return this;
	}
	public boolean Exec_insert() {
		Mem_tbl tbl = engine.Tbls__get(qry.BaseTable());
		if (tbl == null) throw ErrUtl.NewArgs("must call Create_tbl", "tbl", qry.BaseTable());
		return tbl.Insert(this) == 1;
	}
	public int Exec_update() {
		Mem_tbl tbl = engine.Tbls__get(qry.BaseTable());
		return tbl.Update(this);
	}
	public int Exec_delete() {
		Mem_tbl tbl = engine.Tbls__get(qry.BaseTable());
		return tbl.Delete(this);
	}
	public DataRdr Exec_select() {throw ErrUtl.NewUnimplemented();}
	public Db_rdr Exec_select__rls_auto() {return this.Exec_select__rls_manual();}
	public Db_rdr Exec_select__rls_manual() {
//			Mem_tbl tbl = engine.Tbls_get(qry.Base_table());
//			return tbl.Select(this);
		return engine.Qry_runner().Select(this);
	}
	public Object Exec_select_val() {throw ErrUtl.NewUnimplemented();}
	private void Add(String k, boolean where, Object v) {
		if (k == DbmetaFldItm.KeyNull) return;		// key is explicitly null; ignore; allows schema_2+ type definitions
		val_list.AddIfDupeUse1st(k, v);				// NOTE: only add if new; WHERE with IN will call Add many times; fld_ttl IN ('A.png', 'B.png');
		if (where) stmt_args.Add(k, v);
	}
}
