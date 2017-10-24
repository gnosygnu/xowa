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
package gplx.xowa.addons.bldrs.updates.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.updates.*;
import gplx.core.progs.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.xowa.wikis.data.*;
import gplx.xowa.bldrs.*;
import gplx.xowa.files.fsdb.*; import gplx.fsdb.meta.*;
public class Xodel_exec_mgr {
	public void Exec_delete(Gfo_prog_ui prog_ui, Xob_bldr bldr, Io_url deletion_db_url) {
		// get domain bry from deletion_conn's cfg_tbl
		Db_conn deletion_conn = Db_conn_bldr.Instance.Get_or_fail(deletion_db_url);
		Db_cfg_tbl cfg_tbl = Xowd_cfg_tbl_.Get_or_fail(deletion_conn);
		byte[] domain_bry = cfg_tbl.Select_bry("", Xodel_exec_mgr.Cfg__deletion_db__domain);

		// get wiki; init it; do delete
		Xowe_wiki wiki = bldr.App().Wiki_mgr().Get_by_or_make(domain_bry);
		wiki.Init_assert();
		Delete_by_url(prog_ui, wiki, deletion_conn, cfg_tbl);

		// cleanup
		deletion_conn.Rls_conn();
		Io_mgr.Instance.DeleteFil(deletion_db_url);
	}
	public void Exec_cleanup(Io_url deletion_db_url) {
		Db_conn deletion_conn = Db_conn_bldr.Instance.Get_or_fail(deletion_db_url);
		Db_cfg_tbl cfg_tbl = Xowd_cfg_tbl_.Get_or_fail(deletion_conn);
		cfg_tbl.Delete_val("", Xodel_exec_mgr.Cfg__deletion_db__db_bgn);
	}
	private void Delete_by_url(Gfo_prog_ui prog_ui, Xowe_wiki wiki, Db_conn deletion_conn, Db_cfg_tbl cfg_tbl) {
		// get fsdb_mgr
		Xof_fsdb_mgr fsdb_mgr = wiki.File_mgr().Fsdb_mgr();
		Fsm_mnt_itm mnt_itm = fsdb_mgr.Mnt_mgr().Mnts__get_main();
		Db_conn core_conn = mnt_itm.Atr_mgr().Db__core().Conn();
		try {
			core_conn.Env_db_attach("delete_db", deletion_conn);

			// loop dbs
			int dbs_len = mnt_itm.Bin_mgr().Dbs__len();				
			String dbs_len_str = Int_.To_str(dbs_len - Int_.Base1);
			int db_bgn = cfg_tbl.Select_int_or("", Cfg__deletion_db__db_bgn, 0);
			for (int i = db_bgn; i < dbs_len; ++i) {
				if (prog_ui.Prog_notify_and_chk_if_suspended(i, dbs_len)) return;
				Fsm_bin_fil bin_db = mnt_itm.Bin_mgr().Dbs__get_at(i);
				Delete_by_db(core_conn, bin_db, dbs_len_str);
				cfg_tbl.Upsert_int("", Cfg__deletion_db__db_bgn, i + 1);
			}
		} finally {core_conn.Env_db_detach("delete_db");}
	}
	private void Delete_by_db(Db_conn deletion_conn, Fsm_bin_fil bin_db, String dbs_len_str) {
		Gfo_usr_dlg usr_dlg = Xoa_app_.Usr_dlg();

		// get rows to delete in db
		List_adp list = List_adp_.New();
		int bin_db_id = bin_db.Id();
		String bin_db_id_str = Int_.To_str(bin_db_id);
		usr_dlg.Prog_many("", "", "processing files for deletion in database " + bin_db_id_str + " of " + dbs_len_str);
		Db_rdr rdr = deletion_conn.Exec_rdr(String_.Concat_lines_nl
		( "SELECT  ff.fil_id    AS item_id"
		, ",       1            AS item_is_orig"
		, "FROM    fsdb_fil ff"
		, "JOIN    delete_db.delete_regy dr ON ff.fil_id = dr.fil_id AND dr.thm_id = -1"
		, "WHERE   ff.fil_bin_db_id = " + bin_db_id_str
		, "UNION"
		, "SELECT  ft.thm_id    AS item_id"
		, ",       0            AS item_is_orig"
		, "FROM    fsdb_thm ft"
		, "        JOIN delete_db.delete_regy dr ON ft.thm_owner_id = dr.fil_id AND ft.thm_id = dr.thm_id"
		, "WHERE   ft.thm_bin_db_id = " + bin_db_id_str
		));
		try {
			while (rdr.Move_next()) {
				int item_id = rdr.Read_int("item_id");
				int item_is_orig = rdr.Read_int("item_is_orig");
				list.Add(new Xodel_prune_itm(item_id, item_is_orig == 1));
			}
		}	finally {rdr.Rls();}

		int len = list.Count();
		if (len == 0) return;	// no files; exit, else will vacuum below

		deletion_conn.Env_db_attach("bin_db", bin_db.Conn());
		deletion_conn.Txn_bgn("img_prune__" + bin_db_id_str);

		// delete bin
		Db_stmt delete_bin_stmt = deletion_conn.Stmt_sql("DELETE FROM bin_db.fsdb_bin WHERE bin_owner_id = ?");
		for (int i = 0; i < len; ++i) {
			Xodel_prune_itm itm = (Xodel_prune_itm)list.Get_at(i);
			delete_bin_stmt.Clear().Crt_int("bin_owner_id", itm.Item_id);
			delete_bin_stmt.Exec_delete();

			if (i % 10000 == 0) usr_dlg.Prog_many("", "", "deleting data in database " + bin_db_id_str + " of " + dbs_len_str);
		}
		delete_bin_stmt.Rls();

		// delete meta
		Db_stmt delete_fil_stmt = deletion_conn.Stmt_sql("DELETE FROM fsdb_fil WHERE fil_id = ?");
		Db_stmt delete_thm_stmt = deletion_conn.Stmt_sql("DELETE FROM fsdb_thm WHERE thm_id = ?");
		for (int i = 0; i < len; ++i) {
			Xodel_prune_itm itm = (Xodel_prune_itm)list.Get_at(i);
			if (itm.Item_is_orig) {
				delete_fil_stmt.Clear().Crt_int("fil_id", itm.Item_id);
				delete_fil_stmt.Exec_delete();
			}
			else {
				delete_thm_stmt.Clear().Crt_int("thm_id", itm.Item_id);
				delete_thm_stmt.Exec_delete();
			}
			if (i % 10000 == 0) usr_dlg.Prog_many("", "", "deleting meta in database " + bin_db_id_str + " of " + dbs_len_str);
		}
		delete_fil_stmt.Rls();
		delete_thm_stmt.Rls();

		// cleanup
		deletion_conn.Txn_end();
		deletion_conn.Env_db_detach("bin_db");
		bin_db.Conn().Env_vacuum();
	}
	public static final String Cfg__deletion_db__domain = "file.deletion_db.domain", Cfg__deletion_db__db_bgn = "file.deletion_db.db_bgn";
}
