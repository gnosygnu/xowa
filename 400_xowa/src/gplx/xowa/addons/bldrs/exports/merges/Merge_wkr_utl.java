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
package gplx.xowa.addons.bldrs.exports.merges; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*;
import gplx.dbs.*; import gplx.dbs.bulks.*; import gplx.dbs.engines.sqlite.*;
import gplx.xowa.addons.bldrs.exports.utls.*;
class Merge_wkr_utl {
	public static void Merge_by_sql(Db_bulk_prog prog_wkr, String msg, String tbl_name, Dbmeta_fld_list flds, Db_conn src_conn, Merge2_trg_itm trg_db, int trg_db_id, boolean disable_synchronous) {
		Bry_bfr bfr = Bry_bfr_.New();
		Db_conn trg_conn = trg_db.Conn();
		Db_attach_mgr attach_mgr = new Db_attach_mgr(trg_conn, new Db_attach_itm("src_db", src_conn));
		Io_url trg_url = Sqlite_conn_info.To_url(trg_conn);
		if (disable_synchronous) {				
			Io_mgr.Instance.CopyFil(trg_url, trg_url.GenNewExt(".bak"), true);
			trg_conn.Exec_qry(Sqlite_pragma.New__synchronous__off());
			Set_journal(trg_conn, "OFF");
		}
		attach_mgr.Exec_sql(Bld_insert_into(bfr, tbl_name, flds, trg_db_id, disable_synchronous));
		if (disable_synchronous) {
			trg_conn.Exec_qry(Sqlite_pragma.New__synchronous__normal());
			Io_mgr.Instance.DeleteFil(trg_url.GenNewExt(".bak"));
			Set_journal(trg_conn, "DELETE");
		}
		prog_wkr.Prog__insert_and_stop_if_suspended(1);
	}
	private static void Set_journal(Db_conn conn, String mode) {
		Db_rdr rdr = conn.Stmt_sql("PRAGMA journal_mode=" + mode).Exec_select__rls_auto();
		try {
			rdr.Move_next();
			rdr.Read_at(0);
		}
		finally {
			rdr.Rls();
		}
	}
	private static String Bld_insert_into(Bry_bfr bfr, String tbl_name, Dbmeta_fld_list flds, int trg_db_id, boolean disable_synchronous) {
		Split_tbl_.Bld_insert_by_select(bfr, tbl_name, flds);
		if (trg_db_id != -1 && !disable_synchronous)
			bfr.Add_str_u8_fmt("WHERE trg_db_id = {0}", trg_db_id);
		return bfr.To_str_and_clear();
	}
	public static void Merge_by_rows(Db_bulk_prog prog_wkr, String msg, String tbl_name, Dbmeta_fld_list flds, Db_conn src_conn, Db_conn trg_conn, String[] order_bys) {
		Db_stmt src_stmt = order_bys == String_.Ary_empty
			? src_conn.Stmt_select_all(tbl_name, flds)
			: src_conn.Stmt_select_order(tbl_name, flds, String_.Ary_empty, order_bys)
			;
		Db_rdr src = src_stmt.Exec_select__rls_auto();
		Db_stmt trg = trg_conn.Stmt_insert(tbl_name, flds);
		try {Db_bulk_exec_.Insert(prog_wkr, msg, flds.To_fld_ary(), src, trg, trg_conn);}
		finally {
			src.Rls();
			trg.Rls();
		}
	}
}
