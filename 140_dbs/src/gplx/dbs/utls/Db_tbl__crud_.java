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
package gplx.dbs.utls;
import gplx.Hash_adp;
import gplx.Hash_adp_;
import gplx.List_adp;
import gplx.List_adp_;
import gplx.dbs.Db_conn;
import gplx.dbs.Db_rdr;
import gplx.dbs.Db_stmt;
import gplx.dbs.DbmetaFldItm;
import gplx.dbs.DbmetaFldList;
import gplx.dbs.stmts.Db_stmt_arg_list;
import gplx.objects.primitives.BoolUtl;
public class Db_tbl__crud_ {
	public static boolean Upsert(Db_conn conn, String tbl_name, DbmetaFldList flds, String[] crt_cols, Object... objs) {
		// init
		int crt_cols_len = crt_cols.length;
		String[] val_cols = Find_excepts(flds, crt_cols);

		// check if exists
		Db_stmt select_stmt = conn.Stmt_select(tbl_name, crt_cols, crt_cols);
		Add_arg(select_stmt, flds, crt_cols, objs, BoolUtl.Y, 0);
		Db_rdr rdr = select_stmt.Exec_select__rls_auto();
		boolean exists = rdr.Move_next();
		rdr.Rls();

		// do update / insert; NOTE: 0-index and crt_cols_len assumes that objs starts with crts; EX: (id) -> (1, 'abc') x> ('abc', 1)
		// update
		if (exists) {
			Db_stmt update_stmt = conn.Stmt_update(tbl_name, crt_cols, val_cols);
			Add_arg(update_stmt, flds, val_cols, objs, BoolUtl.N, crt_cols_len);
			Add_arg(update_stmt, flds, crt_cols, objs, BoolUtl.Y, 0);
			update_stmt.Exec_update();
			update_stmt.Rls();
			return false;
		}
		// insert
		else {
			Db_stmt insert_stmt = conn.Stmt_insert(tbl_name, flds);
			Add_arg(insert_stmt, flds, crt_cols, objs, BoolUtl.N, 0);
			Add_arg(insert_stmt, flds, val_cols, objs, BoolUtl.N, crt_cols_len);
			insert_stmt.Exec_insert();
			insert_stmt.Rls();
			return true;
		}
	}
	private static String[] Find_excepts(DbmetaFldList flds, String[] cols) {
		// hash cols
		Hash_adp hash = Hash_adp_.New();
		int cols_len = cols.length;
		for (int i = 0; i < cols_len; ++i)
			hash.AddAsKeyAndVal(cols[i]);

		// loop flds and get excepts
		List_adp list = List_adp_.New();
		int flds_len = flds.Len();
		for (int i = 0; i < flds_len; ++i) {
			DbmetaFldItm fld = flds.GetAt(i);
			if (!hash.Has(fld.Name()))
				list.Add(fld.Name());
		}
		return list.ToStrAryAndClear();
	}
	private static void Add_arg(Db_stmt stmt, DbmetaFldList flds, String[] cols, Object[] objs, boolean mode_is_crt, int objs_bgn) {
		int cols_len = cols.length;
		for (int i = 0; i < cols_len; ++i) {
			String col = cols[i];
			DbmetaFldItm fld = flds.GetByOrNull(col);
			Object obj = objs[i + objs_bgn];
			if (mode_is_crt)
				Db_stmt_arg_list.Fill_crt(stmt, fld.Type().Tid(), col, obj);
			else
				Db_stmt_arg_list.Fill_val(stmt, fld.Type().Tid(), col, obj);
		}
	}
}
