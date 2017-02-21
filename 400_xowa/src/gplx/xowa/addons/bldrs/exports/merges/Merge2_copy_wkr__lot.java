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
import gplx.dbs.*; import gplx.xowa.addons.bldrs.exports.utls.*;
class Merge2_copy_wkr__lot {
	private final    Split_tbl tbl;
	private final    String tbl_name, fld_blob_pkey, fld_blob_data;
	private final    Merge2_copy_utl copy_utl;
	private int resume__db_id;
	private boolean tbl_has_blob;
	public Merge2_copy_wkr__lot(Split_tbl tbl) {
		this.tbl = tbl;
		this.tbl_name = tbl.Tbl_name();
		this.fld_blob_pkey = tbl.Fld_pkeys()[0];
		this.fld_blob_data = tbl.Fld_blob();
		this.tbl_has_blob = Split_tbl_.Tbl_has_blob(tbl);
		this.copy_utl = new Merge2_copy_utl(tbl.Tbl_name(), fld_blob_pkey, fld_blob_data, tbl_has_blob);
	}
	public void Copy_by_sql(String msg, Merge_ctx ctx, Merge_prog_wkr prog_wkr, Db_conn src_conn, Merge2_trg_itm trg_db, Merge2_trg_mgr trg_mgr, boolean src_is_pack) {
		Xow_wiki wiki = ctx.Wiki();
		if (src_is_pack) {
			int[] trg_db_idxs = Get_trg_dbs(src_conn, tbl_name);
			int trg_db_idxs_len = trg_db_idxs.length;
			for (int i = 0; i < trg_db_idxs_len; ++i) {
				int trg_db_id = trg_db_idxs[i];
				Merge2_trg_itm trg_db_new = trg_mgr.Cur_or_new(ctx, prog_wkr, wiki, trg_db_id);
				Copy_by_trg_conn(msg, prog_wkr, src_conn, trg_db_new, trg_db_id, src_is_pack);
			}
		}
		else {
			Copy_by_trg_conn(msg, prog_wkr, src_conn, trg_db, trg_db.Idx(), src_is_pack);
		}
	}
	private void Copy_by_trg_conn(String msg, Merge_prog_wkr prog_wkr, Db_conn src_conn, Merge2_trg_itm trg_db, int trg_db_id, boolean src_is_pack) {
		Dbmeta_fld_list src_flds = tbl.Flds().Clone(); // src_flds.Insert(0, Dbmeta_fld_itm.new_int("trg_db_id"));
		Merge_wkr_utl.Merge_by_sql(prog_wkr, msg, tbl.Tbl_name(), src_flds, src_conn, trg_db, trg_db_id, !src_is_pack);
	}
	private int[] Get_trg_dbs(Db_conn conn, String tbl_name) {
		List_adp list = List_adp_.New();
		Db_rdr rdr = conn.Stmt_sql(String_.Format("SELECT trg_db_id FROM {0} GROUP BY trg_db_id", tbl_name)).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				list.Add(rdr.Read_int("trg_db_id"));
			}
		}
		finally {
			rdr.Rls();
		}
		return (int[])list.To_ary_and_clear(int.class);
	}
	public void Copy_entire_src(Merge_ctx ctx, Merge_prog_wkr prog_wkr, Db_conn src_conn, Merge2_trg_itm trg_db, Merge2_trg_mgr trg_mgr, boolean src_is_pack) {
		Xow_wiki wiki = ctx.Wiki();

		if (gplx.core.envs.Op_sys.Cur().Tid_is_drd() && tbl_has_blob) {
			Copy_rows(ctx, prog_wkr, wiki, tbl, src_conn, trg_db, trg_mgr, src_is_pack, Merge2_copy_utl.Mode__drd__small);
			Copy_rows(ctx, prog_wkr, wiki, tbl, src_conn, trg_db, trg_mgr, src_is_pack, Merge2_copy_utl.Mode__drd__large);
		}
		else
			Copy_rows(ctx, prog_wkr, wiki, tbl, src_conn, trg_db, trg_mgr, src_is_pack, Merge2_copy_utl.Mode__all);
	}
	private void Copy_rows(Merge_ctx ctx, Merge_prog_wkr prog_wkr, Xow_wiki wiki, Split_tbl tbl, Db_conn src_conn, Merge2_trg_itm trg_db, Merge2_trg_mgr trg_mgr, boolean src_is_pack, byte select_mode) {
		// init src
		Dbmeta_fld_list src_flds = tbl.Flds().Clone();
		if (src_is_pack)
			src_flds.Insert(0, Dbmeta_fld_itm.new_int("trg_db_id"));
		int flds_nth = src_flds.Len();
		if (tbl_has_blob) {
			Split_tbl_.Flds__add_blob_len(src_flds);
			flds_nth = src_flds.Len() - 1;	// ignore blob which is last fld by convention
		}
		String src_sql = copy_utl.Bld_sql(src_flds, flds_nth, src_is_pack, select_mode, resume__db_id);
		Db_rdr src_rdr = src_conn.Stmt_sql(src_sql).Exec_select__rls_auto();
		copy_utl.Init_conn(src_conn);

		Dbmeta_fld_list trg_flds = tbl.Flds().Clone();
		if (tbl_has_blob && src_is_pack) {
			Split_tbl_.Flds__add_blob_len(trg_flds);
		}

		Db_stmt trg_stmt = null; Db_conn trg_conn = null; int cur_trg = -1;
		if (trg_db != null) {
			trg_conn = trg_db.Conn();
			trg_conn.Txn_bgn("merge_" + tbl_name);
			trg_stmt = trg_conn.Stmt_insert(tbl_name, trg_flds);
			cur_trg = trg_db.Idx();
		}
		try {
			while (src_rdr.Move_next()) {
				// switch trg_db if null, or diff than cur; note that trg_stmt also changes
				if (src_is_pack) {
					int trg_db_id = src_rdr.Read_int("trg_db_id");
					if (cur_trg != trg_db_id) {
						if (trg_conn != null) trg_conn.Txn_end();
						trg_conn = trg_mgr.Cur_or_new(ctx, prog_wkr, wiki, trg_db_id).Conn();
						trg_conn.Txn_bgn("merge_" + tbl_name);
						trg_stmt = trg_conn.Stmt_insert(tbl_name, trg_flds);
						cur_trg = trg_db_id;
						resume__db_id = trg_db_id;
						prog_wkr.Checkpoint__save();
					}
				}

				// read and insert
				trg_stmt.Clear();
				gplx.dbs.diffs.Gfdb_rdr_utl_.Stmt_args(trg_stmt, trg_flds, 0, trg_flds.Len() - (tbl_has_blob ? 1 : 0), src_rdr);
				if (tbl_has_blob) {
					byte[] val_blob = select_mode == Merge2_copy_utl.Mode__drd__large
						? copy_utl.Select_blob(src_rdr, fld_blob_pkey)
						: src_rdr.Read_bry(fld_blob_data);
					trg_stmt.Val_bry(fld_blob_data	, val_blob);
				}
				trg_stmt.Exec_insert();
				
				if (prog_wkr.Prog__insert_and_stop_if_suspended(0)) break;
			}
		}
		finally {
			src_rdr.Rls();
			if (trg_conn != null) {
				trg_conn.Txn_end();
			}
			trg_stmt = Db_stmt_.Rls(trg_stmt);
		}
	}
}
/*
2 situations:
- pack (trg_db,blob_len,*)	-> heap (blob_len,*)
- heap (blob_len,*)			-> wiki (*)
*/
