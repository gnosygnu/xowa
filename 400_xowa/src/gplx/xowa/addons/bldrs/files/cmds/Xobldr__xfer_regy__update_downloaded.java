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
package gplx.xowa.addons.bldrs.files.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import gplx.dbs.*; import gplx.dbs.engines.sqlite.*; import gplx.xowa.addons.bldrs.files.dbs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;
import gplx.fsdb.meta.*;
public class Xobldr__xfer_regy__update_downloaded extends Xob_cmd__base implements Xob_cmd {
	public Xobldr__xfer_regy__update_downloaded(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	@Override public void Cmd_run() {
		wiki.Init_assert();	// NOTE: must init wiki to set up db_core; DATE:2015-08-17
		Db_conn make_conn = Xob_db_file.New__file_make(wiki.Fsys_mgr().Root_dir()).Conn();
		this.Create_fsdb_regy(make_conn);
		this.Update_xfer_regy(make_conn);
	}
	private void Create_fsdb_regy(Db_conn make_conn) {
		wiki.File_mgr().Init_file_mgr_by_load(wiki);	// NOTE: uses ./file.core.xowa; never uses -prv/file.core.xowa or /prv/file.core.xowa; DATE:2015-09-10
		Fsm_mnt_itm mnt_itm = wiki.File_mgr().Fsdb_mgr().Mnt_mgr().Mnts__get_main();		// 0 = fsdb.main

		// connect to fsdb_db; create fsdb_regy
		Db_conn fsdb_conn = mnt_itm.Atr_mgr().Db__core().Conn();							// 0 = fsdb.atr.00
		make_conn.Env_db_attach("fsdb_db", fsdb_conn);
		make_conn.Meta_tbl_delete(Xob_fsdb_regy_tbl_.Tbl_name);
		Sqlite_engine_.Tbl_create_and_delete(make_conn, Xob_fsdb_regy_tbl_.Tbl_name, Xob_fsdb_regy_tbl_.Tbl_sql);

		// insert fil into fsdb_regy
		make_conn.Txn_bgn("bldr__xfer_regy_update");
		make_conn.Exec_sql(Xob_fsdb_regy_tbl_.Insert_fsdb_fil);
		String fsdb_thm_tbl = mnt_itm.Db_mgr().File__schema_is_1() ? "fsdb_xtn_thm" : "fsdb_thm";

		// insert thm into fsdb_regy
		String insert_sql_fsdb_thm 
			= wiki.File_mgr().Fsdb_mgr().Mnt_mgr().Mnts__get_main().Cfg_mgr().Schema_thm_page()
			? String_.Format(Xob_fsdb_regy_tbl_.Insert_fsdb_thm, fsdb_thm_tbl)
			: Xob_fsdb_regy_tbl_.Insert_fsdb_thm_v0
			;
		make_conn.Exec_sql(insert_sql_fsdb_thm);

		// end txn; cleanup
		make_conn.Txn_end();
		Sqlite_engine_.Idx_create(make_conn, Xob_fsdb_regy_tbl_.Idx_main);
		Sqlite_engine_.Db_detach(make_conn, "fsdb_db");
	}
	private void Update_xfer_regy(Db_conn make_conn) {
		make_conn.Txn_bgn("bldr__xfer_regy_update_status");
		make_conn.Exec_sql(Xob_fsdb_regy_tbl_.Update_regy_nil);
		make_conn.Exec_sql(Xob_fsdb_regy_tbl_.Update_regy_fil);
		make_conn.Exec_sql(Xob_fsdb_regy_tbl_.Update_regy_thm);
		make_conn.Txn_end();
	}

	public static final String BLDR_CMD_KEY = "file.xfer_regy_update";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Xobldr__xfer_regy__update_downloaded(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xobldr__xfer_regy__update_downloaded(bldr, wiki);}
}
