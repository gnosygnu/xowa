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
package gplx.dbs;
import gplx.dbs.qrys.Db_qry__select_cmd;
import gplx.dbs.qrys.Db_qry__select_in_tbl;
import gplx.dbs.qrys.Db_qry_delete;
import gplx.dbs.qrys.Db_qry_insert;
import gplx.dbs.qrys.Db_qry_sql;
import gplx.dbs.qrys.Db_qry_update;
import gplx.dbs.qrys.Db_stmt_sql;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.utls.FloatUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.utls.TypeIds;
import gplx.types.errs.Err;
import gplx.types.errs.ErrUtl;
public class Db_stmt_ {
	public static final Db_stmt Null = new Db_stmt_sql();
	public static Db_stmt new_insert_(Db_conn conn, String tbl, String... flds) {
		Db_qry qry = Db_qry_insert.new_(tbl, flds);
		return conn.Stmt_new(qry);
	}
	public static Db_stmt new_update_(Db_conn conn, String tbl, String[] where, String... flds) {
		Db_qry qry = Db_qry_update.New(tbl, where, flds);
		return conn.Stmt_new(qry);
	}
	public static Db_stmt new_delete_(Db_conn conn, String tbl, String... where) {
		Db_qry_delete qry = Db_qry_.delete_(tbl, Db_crt_.eq_many_(where));
		return conn.Stmt_new(qry);
	}
	public static Db_stmt new_select_(Db_conn conn, String tbl, String[] where, String... flds) {
		Db_qry__select_cmd qry = Db_qry_.select_cols_(tbl, Db_crt_.eq_many_(where), flds);
		return conn.Stmt_new(qry);
	}
	public static Db_stmt new_select_in_(Db_conn conn, String tbl, String in_fld, Object[] in_vals, String... flds) {
		Db_qry__select_cmd qry = Db_qry_.select_cols_(tbl, Db_crt_.New_in(in_fld, in_vals), flds).Order_asc_(in_fld);
		return conn.Stmt_new(qry);
	}
	public static Db_stmt new_select_all_(Db_conn conn, String tbl) {
		return conn.Stmt_new(Db_qry_.select_tbl_(tbl));
	}
	public static Db_stmt new_select_as_rdr(Db_conn conn, Db_qry__select_in_tbl qry) {
		return conn.Stmt_new(qry);
	}
	public static Db_stmt new_select_as_rdr(Db_conn conn, String sql) {
		return conn.Stmt_new(Db_qry_sql.rdr_(sql));
	}
	public static Db_stmt New_sql_lines(Db_conn conn, String... lines) {
		Db_qry qry = Db_qry_sql.sql_(StringUtl.ConcatWith("\n", lines));
		return conn.Stmt_new(qry);
	}
	public static Err err_(Exception e, Db_stmt stmt, String proc) {throw ErrUtl.NewArgs(e, "db stmt failed", "proc", proc).FrameIgnoreAdd1();}
	public static Err err_(Exception e, String tbl, String proc) {
		throw ErrUtl.NewArgs(e, "db call failed", "tbl", tbl, "proc", proc).FrameIgnoreAdd1();
	}
	public static Db_stmt Rls(Db_stmt v) {if (v != null) v.Rls(); return null;}
	public static void Val_by_obj(Db_stmt stmt, String key, Object val) {
		int tid = TypeIds.ToIdByObj(val);
		switch (tid) {
			case TypeIds.IdBool:        stmt.Val_bool_as_byte   (key, BoolUtl.Cast(val)); break;
			case TypeIds.IdByte:        stmt.Val_byte           (key, ByteUtl.Cast(val)); break;
			case TypeIds.IdInt:         stmt.Val_int            (key, IntUtl.Cast(val)); break;
			case TypeIds.IdLong:        stmt.Val_long           (key, LongUtl.Cast(val)); break;
			case TypeIds.IdFloat:       stmt.Val_float          (key, FloatUtl.Cast(val)); break;
			case TypeIds.IdDouble:      stmt.Val_double         (key, DoubleUtl.Cast(val)); break;
			case TypeIds.IdStr:         stmt.Val_str            (key, StringUtl.Cast(val)); break;
			case TypeIds.IdBry:         stmt.Val_bry            (key, BryUtl.Cast(val)); break;
			default:                        throw ErrUtl.NewUnhandled(tid);
		}
	}
	public static void Insert_by_rdr(DbmetaFldList flds, Db_rdr rdr, Db_stmt stmt) {
		stmt.Clear();
		Fill_by_rdr(flds, rdr, stmt);
		stmt.Exec_insert();
	}
	private static void Fill_by_rdr(DbmetaFldList flds, Db_rdr rdr, Db_stmt stmt) {
		int flds_len = flds.Len();
		for (int i = 0; i < flds_len; i++) {
			DbmetaFldItm fld = (DbmetaFldItm)flds.GetAt(i);
			String fld_name = fld.Name();
			int fld_tid = fld.Type().Tid();
			if (fld.Autonum()) continue;
			switch (fld_tid) {
				case DbmetaFldType.TidBool:        stmt.Val_bool_as_byte   (fld_name, rdr.Read_bool_by_byte(fld_name)); break;
				case DbmetaFldType.TidByte:        stmt.Val_byte           (fld_name, rdr.Read_byte(fld_name)); break;
				case DbmetaFldType.TidInt:         stmt.Val_int            (fld_name, rdr.Read_int(fld_name)); break;
				case DbmetaFldType.TidLong:        stmt.Val_long           (fld_name, rdr.Read_long(fld_name)); break;
				case DbmetaFldType.TidFloat:       stmt.Val_float          (fld_name, rdr.Read_float(fld_name)); break;
				case DbmetaFldType.TidDouble:      stmt.Val_double         (fld_name, rdr.Read_double(fld_name)); break;
				case DbmetaFldType.TidStr:         stmt.Val_str            (fld_name, rdr.Read_str(fld_name)); break;
				case DbmetaFldType.TidBry:         stmt.Val_bry            (fld_name, rdr.Read_bry(fld_name)); break;
				default:                              throw ErrUtl.NewUnhandled(fld_tid, fld_name);
			}
		}
	}
}
