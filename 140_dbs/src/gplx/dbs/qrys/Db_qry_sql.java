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
import gplx.types.basics.utls.ClassUtl;
import gplx.dbs.Db_conn;
import gplx.dbs.Db_qry;
import gplx.dbs.Db_qry_;
import gplx.dbs.sqls.SqlQryWtr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryUtlByWtr;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.utls.FloatUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.commons.GfoDecimal;
import gplx.types.commons.GfoDecimalUtl;
import gplx.types.errs.ErrUtl;
public class Db_qry_sql implements Db_qry {
	public int			Tid() {return Db_qry_.Tid_sql;}
	public boolean ReturnsRdr() {return isReader;} private boolean isReader;
	public String BaseTable() {throw ErrUtl.NewUnimplemented();}
	public String ToSqlExec(SqlQryWtr wtr) {return sql;} private String sql;
	public int Exec_qry(Db_conn conn) {return conn.Exec_qry(this);}
	public static Db_qry_sql dml_(String sql) {return sql_(sql);}
	public static Db_qry_sql ddl_(String sql) {return sql_(sql);}
	public static Db_qry_sql xtn_(String sql) {return sql_(sql);}
	public static Db_qry_sql sql_(String sql) {
		Db_qry_sql rv = new Db_qry_sql();
		rv.sql = sql; rv.isReader = false;
		return rv;
	}
	public static Db_qry_sql rdr_(String sql) {
		Db_qry_sql rv = new Db_qry_sql();
		rv.sql = sql; rv.isReader = true;
		return rv;
	}
	public static Db_qry_sql as_(Object obj) {return obj instanceof Db_qry_sql ? (Db_qry_sql)obj : null;}
	public static Db_qry_sql cast(Object obj) {try {return (Db_qry_sql)obj;} catch(Exception exc) {throw ErrUtl.NewCast(exc, Db_qry_sql.class, obj);}}
	public static String Gen_sql(SqlQryWtr qry_wtr, Db_qry qry, Object... args) {
		byte[] src = BryUtl.NewU8(qry_wtr.ToSqlStr(qry, true));
		int src_len = src.length;
		int args_idx = 0, args_len = args.length, pos = 0;
		BryWtr bfr = BryWtr.NewWithSize(src_len);
		while (pos < src_len) {
			int question_pos = BryFind.FindFwd(src, AsciiByte.Question, pos);
			if (question_pos == BryFind.NotFound)
				question_pos = src_len;
			bfr.AddMid(src, pos, question_pos);
			if (args_idx < args_len)
				Gen_sql_arg(bfr, args[args_idx++]);
			pos = question_pos + 1;
		}
		return bfr.ToStrAndClear();
	}
	private static void Gen_sql_arg(BryWtr bfr, Object val) {
		if (val == null) {bfr.Add(Bry_null); return;}
		Class<?> val_type = val.getClass();
		if		(ClassUtl.Eq(val_type, IntUtl.ClsRefType))
			bfr.AddIntVariable(IntUtl.Cast(val));
		else if	(ClassUtl.Eq(val_type, BoolUtl.ClsRefType))
			bfr.AddIntFixed(1, BoolUtl.ToInt(BoolUtl.Cast(val)));	// NOTE: save boolean to 0 or 1, b/c (a) db may not support bit datatype (sqllite) and (b) avoid i18n issues with "true"/"false"
		else if (ClassUtl.Eq(val_type, DoubleUtl.ClsRefType))
			bfr.AddDouble(DoubleUtl.Cast(val));
		else if (ClassUtl.Eq(val_type, LongUtl.ClsRefType))
			bfr.AddLongVariable(LongUtl.Cast(val));
		else if (ClassUtl.Eq(val_type, FloatUtl.ClsRefType))
			bfr.AddFloat(FloatUtl.Cast(val));
		else if (ClassUtl.Eq(val_type, ByteUtl.ClsRefType))
			bfr.AddByte(ByteUtl.Cast(val));
		else if (ClassUtl.Eq(val_type, GfoDateUtl.ClsRefType))
			bfr.AddByteApos().AddStrA7(((GfoDate)val).ToStrGplxLong()).AddByteApos();
		else if (ClassUtl.Eq(val_type, GfoDecimalUtl.Cls_ref_type))
			bfr.AddStrA7(((GfoDecimal)val).ToStr());
		else {
			byte[] val_bry = BryUtl.NewU8(ObjectUtl.ToStrOrNull(val));
			val_bry = BryUtlByWtr.Replace(val_bry, AsciiByte.AposBry, Bry_escape_apos);
			bfr.AddByteApos().Add(val_bry).AddByteApos();
		}
	}	private static final byte[] Bry_null = BryUtl.NewU8("NULL"), Bry_escape_apos = BryUtl.NewA7("''");
}
