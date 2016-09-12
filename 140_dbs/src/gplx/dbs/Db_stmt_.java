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
import gplx.dbs.qrys.*;
public class Db_stmt_ {
	public static final    Db_stmt Null = new Db_stmt_sql();
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
	public static Err err_(Exception e, Db_stmt stmt, String proc) {
		throw Err_.new_exc(e, "db", "db stmt failed", "proc", proc);
	}
	public static Err err_(Exception e, String tbl, String proc) {
		throw Err_.new_exc(e, "core", "db call failed", "tbl", tbl, "proc", proc);
	}
	public static Db_stmt Rls(Db_stmt v) {if (v != null) v.Rls(); return null;}
	public static void Val_by_obj(Db_stmt stmt, String key, Object val) {
		int tid = Type_adp_.To_tid_obj(val);
		switch (tid) {
			case Type_adp_.Tid__bool:			stmt.Val_bool_as_byte	(key, Bool_.cast(val)); break;
			case Type_adp_.Tid__byte:			stmt.Val_byte			(key, Byte_.cast(val)); break;
			case Type_adp_.Tid__int:			stmt.Val_int			(key, Int_.cast(val)); break;
			case Type_adp_.Tid__long:			stmt.Val_long			(key, Long_.cast(val)); break;
			case Type_adp_.Tid__float:			stmt.Val_float			(key, Float_.cast(val)); break;
			case Type_adp_.Tid__double:			stmt.Val_double			(key, Double_.cast(val)); break;
			case Type_adp_.Tid__str:			stmt.Val_str			(key, String_.cast(val)); break;
			case Type_adp_.Tid__bry:			stmt.Val_bry			(key, Bry_.cast(val)); break;
			default:							throw Err_.new_unhandled_default(tid);
		}
	}
}
