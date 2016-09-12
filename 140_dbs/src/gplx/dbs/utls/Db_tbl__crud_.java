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
package gplx.dbs.utls; import gplx.*; import gplx.dbs.*;
import gplx.dbs.stmts.*;
public class Db_tbl__crud_ {
	public static void Upsert(Db_conn conn, String tbl_name, Dbmeta_fld_list flds, String[] crt_cols, Object... objs) {
		// init
		int crt_cols_len = crt_cols.length;
		String[] val_cols = Find_excepts(flds, crt_cols);

		// check if exists
		Db_stmt select_stmt = conn.Stmt_select(tbl_name, crt_cols, crt_cols);
		Add_arg(select_stmt, flds, crt_cols, objs, Bool_.Y, 0);
		Db_rdr rdr = select_stmt.Exec_select__rls_auto();
		boolean exists = rdr.Move_next();
		rdr.Rls();

		// do update / insert; NOTE: 0-index and crt_cols_len assumes that objs starts with crts; EX: (id) -> (1, 'abc') x> ('abc', 1)
		// update
		if (exists) {
			Db_stmt update_stmt = conn.Stmt_update(tbl_name, crt_cols, crt_cols);
			Add_arg(update_stmt, flds, crt_cols, objs, Bool_.Y, 0);
			Add_arg(update_stmt, flds, val_cols, objs, Bool_.N, crt_cols_len);
			update_stmt.Exec_update();
			update_stmt.Rls();
		}
		// insert
		else {
			Db_stmt insert_stmt = conn.Stmt_insert(tbl_name, flds);
			Add_arg(insert_stmt, flds, crt_cols, objs, Bool_.N, 0);
			Add_arg(insert_stmt, flds, val_cols, objs, Bool_.N, crt_cols_len);
			insert_stmt.Exec_insert();
			insert_stmt.Rls();
		}
	}
	private static String[] Find_excepts(Dbmeta_fld_list flds, String[] cols) {
		// hash cols
		Hash_adp hash = Hash_adp_.New();
		int cols_len = cols.length;
		for (int i = 0; i < cols_len; ++i)
			hash.Add_as_key_and_val(cols[i]);

		// loop flds and get excepts
		List_adp list = List_adp_.New();
		int flds_len = flds.Len();
		for (int i = 0; i < flds_len; ++i) {
			Dbmeta_fld_itm fld = flds.Get_at(i);
			if (!hash.Has(fld.Name()))
				list.Add(fld.Name());
		}
		return list.To_str_ary_and_clear();
	}
	private static void Add_arg(Db_stmt stmt, Dbmeta_fld_list flds, String[] cols, Object[] objs, boolean mode_is_crt, int objs_bgn) {
		int cols_len = cols.length;
		for (int i = 0; i < cols_len; ++i) {
			String col = cols[i];
			Dbmeta_fld_itm fld = flds.Get_by(col);
			Object obj = objs[i + objs_bgn];
			if (mode_is_crt)
				Db_stmt_arg_list.Fill_crt(stmt, fld.Type().Tid_ansi(), col, obj);
			else
				Db_stmt_arg_list.Fill_val(stmt, fld.Type().Tid_ansi(), col, obj);
		}
	}
}
