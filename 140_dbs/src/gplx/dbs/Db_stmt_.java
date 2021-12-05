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
import gplx.Bry_;
import gplx.Byte_;
import gplx.Double_;
import gplx.Err;
import gplx.Err_;
import gplx.Float_;
import gplx.Int_;
import gplx.Long_;
import gplx.String_;
import gplx.Type_ids_;
import gplx.dbs.qrys.Db_qry__select_cmd;
import gplx.dbs.qrys.Db_qry__select_in_tbl;
import gplx.dbs.qrys.Db_qry_delete;
import gplx.dbs.qrys.Db_qry_insert;
import gplx.dbs.qrys.Db_qry_sql;
import gplx.dbs.qrys.Db_qry_update;
import gplx.dbs.qrys.Db_stmt_sql;
import gplx.objects.primitives.BoolUtl;
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
		Db_qry qry = Db_qry_sql.sql_(String_.Concat_with_str("\n", lines));
		return conn.Stmt_new(qry);
	}
	public static Err err_(Exception e, Db_stmt stmt, String proc) {
		throw Err_.new_exc(e, "db", "db stmt failed", "proc", proc);
	}
	public static Err err_(Exception e, String tbl, String proc) {
		throw Err_.new_exc(e, "core", "db call failed", "tbl", tbl, "proc", proc);
	}
	public static Db_stmt Rls(Db_stmt v) {if (v != null) v.Rls(); return null;}
	public static void Val_by_obj(Db_stmt stmt, String key, Object val) {
		int tid = Type_ids_.To_id_by_obj(val);
		switch (tid) {
			case Type_ids_.Id__bool:        stmt.Val_bool_as_byte   (key, BoolUtl.Cast(val)); break;
			case Type_ids_.Id__byte:        stmt.Val_byte           (key, Byte_.Cast(val)); break;
			case Type_ids_.Id__int:         stmt.Val_int            (key, Int_.Cast(val)); break;
			case Type_ids_.Id__long:        stmt.Val_long           (key, Long_.cast(val)); break;
			case Type_ids_.Id__float:       stmt.Val_float          (key, Float_.cast(val)); break;
			case Type_ids_.Id__double:      stmt.Val_double         (key, Double_.cast(val)); break;
			case Type_ids_.Id__str:         stmt.Val_str            (key, String_.cast(val)); break;
			case Type_ids_.Id__bry:         stmt.Val_bry            (key, Bry_.cast(val)); break;
			default:                        throw Err_.new_unhandled_default(tid);
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
				default:                              throw Err_.new_unhandled_default_w_msg(fld_tid, fld_name);
			}
		}
	}
}
