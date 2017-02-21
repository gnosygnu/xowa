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
package gplx.fsdb; import gplx.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.fsdb.meta.*; import gplx.fsdb.data.*; import gplx.xowa.files.origs.*;
import gplx.xowa.*; import gplx.xowa.wikis.data.*; import gplx.xowa.bldrs.infos.*;
public class Fsdb_db_mgr__v2_bldr {
	public static Fsdb_db_mgr__v2 Get_or_make(Xow_wiki wiki, boolean delete_if_exists) { 	// NOTE: must check if file exists else imports with existing v2 dbs will fail; DATE:2015-05-23
		Xow_db_layout layout = wiki.Data__core_mgr().Props().Layout_file();
		String domain_str = wiki.Domain_str();
		Io_url wiki_dir = wiki.Fsys_mgr().Root_dir();
		String main_core_name = Main_core_name(layout, domain_str);
		String user_core_name = Make_user_name(domain_str);
		Io_url main_core_url = wiki_dir.GenSubFil(main_core_name);
		Io_url user_core_url = wiki_dir.GenSubFil(user_core_name);
		if (delete_if_exists) {
			Db_conn_bldr.Instance.Get_or_noop(main_core_url).Rls_conn();
			Db_conn_bldr.Instance.Get_or_noop(user_core_url).Rls_conn();
			Io_mgr.Instance.DeleteFil(main_core_url);
			Io_mgr.Instance.DeleteFil(user_core_url);
		}
		Fsdb_db_file main_core_file = Io_mgr.Instance.ExistsFil(main_core_url) ? Load_core_file(main_core_url) : Make_core_file_main(wiki, main_core_url, main_core_name, layout);
		Fsdb_db_file user_core_file = Io_mgr.Instance.ExistsFil(user_core_url) ? Load_core_file(user_core_url) : Make_core_file_user(wiki, user_core_url, user_core_name, main_core_name);
		return new Fsdb_db_mgr__v2(layout, wiki_dir, main_core_file, user_core_file);
	}
	private static Fsdb_db_file Load_core_file(Io_url url) {return new Fsdb_db_file(url, Db_conn_bldr.Instance.Get(url));}
	public static Fsdb_db_file Make_core_file_main(Xow_wiki wiki, Io_url main_core_url, String main_core_name, Xow_db_layout layout) {
		Db_conn conn = layout.Tid_is_all() ? Db_conn_bldr.Instance.Get(main_core_url) : Db_conn_bldr.Instance.New(main_core_url);	// if all, use existing (assumes same file name); else, create new
		conn.Txn_bgn("fsdb__core_file");
		Fsdb_db_file rv = Make_core_file(main_core_url, conn, schema_is_1, Fsm_mnt_mgr.Mnt_idx_main);
		if (!layout.Tid_is_all()) // do not make cfg data if all
			Make_cfg_data(wiki, main_core_name, rv, Main_core_tid(layout), -1);
		Fsdb_db_mgr__v2.Cfg__layout_file__set(rv.Tbl__cfg(), layout);
		conn.Txn_end();
		return rv;
	}
	public static Fsdb_db_file Make_core_file_user(Xow_wiki wiki, Io_url user_core_url, String user_file_name, String main_core_name) { // always create file; do not create mnt_tbl;
		Db_conn conn = Db_conn_bldr.Instance.New(user_core_url);
		conn.Txn_bgn("fsdb__core_user");
		Fsdb_db_file rv = Make_core_file(user_core_url, conn, schema_is_1, Fsm_mnt_mgr.Mnt_idx_user);
		Fsm_bin_tbl dbb_tbl = new Fsm_bin_tbl(conn, schema_is_1, Fsm_mnt_mgr.Mnt_idx_user); dbb_tbl.Insert(0, user_file_name);
		Make_bin_tbl(rv);
		Make_cfg_data(wiki, main_core_name, rv, Xow_db_file_.Tid__file_user, -1);
		conn.Txn_end();
		return rv;
	}
	private static Fsdb_db_file Make_core_file(Io_url core_url, Db_conn core_conn, boolean schema_is_1, int mnt_id) {
		Fsdb_db_file rv = new Fsdb_db_file(core_url, core_conn);
		Db_cfg_tbl cfg_tbl = rv.Tbl__cfg();
		cfg_tbl.Create_tbl();
		Fsm_mnt_mgr.Patch(cfg_tbl);
		Fsm_mnt_mgr.Patch_core(cfg_tbl);
		Xof_orig_tbl orig_tbl = new Xof_orig_tbl(core_conn, schema_is_1); orig_tbl.Create_tbl();
		if (mnt_id == Fsm_mnt_mgr.Mnt_idx_main) {
			Fsm_mnt_tbl mnt_tbl = new Fsm_mnt_tbl(core_conn, schema_is_1); mnt_tbl.Create_tbl();
			cfg_tbl.Insert_int("core", "mnt.insert_idx", Fsm_mnt_mgr.Mnt_idx_user);
		}
		Fsm_atr_tbl dba_tbl = new Fsm_atr_tbl(core_conn, schema_is_1); dba_tbl.Create_tbl();
		dba_tbl.Insert(mnt_id, core_url.NameAndExt());
		Fsm_bin_tbl dbb_tbl = new Fsm_bin_tbl(core_conn, schema_is_1, mnt_id); dbb_tbl.Create_tbl();
		Fsd_dir_tbl dir_tbl = new Fsd_dir_tbl(core_conn, schema_is_1); dir_tbl.Create_tbl();
		Fsd_fil_tbl fil_tbl = new Fsd_fil_tbl(core_conn, schema_is_1, mnt_id); fil_tbl.Create_tbl();
		Fsd_thm_tbl thm_tbl = new Fsd_thm_tbl(core_conn, schema_is_1, mnt_id, Bool_.Y); thm_tbl.Create_tbl();
		return rv;
	}
	public static Fsdb_db_file Make_bin_tbl(Fsdb_db_file file) {
		Fsd_bin_tbl bin_tbl = new Fsd_bin_tbl(file.Conn(), schema_is_1); bin_tbl.Create_tbl();
		return file;
	}
	public static String Main_core_name(Xow_db_layout layout, String wiki_domain) {
		switch (layout.Tid()) {
			case Xow_db_layout.Tid__all:	return Main_core_name_all(wiki_domain);
			case Xow_db_layout.Tid__few:	return Main_core_name_few(wiki_domain);
			case Xow_db_layout.Tid__lot:  return Main_core_name_lot(wiki_domain);
			default:						throw Err_.new_unimplemented();
		}
	}
	private static byte Main_core_tid(Xow_db_layout layout) {
		switch (layout.Tid()) {
			case Xow_db_layout.Tid__all:	return Xow_db_file_.Tid__core;
			case Xow_db_layout.Tid__few:	return Xow_db_file_.Tid__file_solo;
			case Xow_db_layout.Tid__lot:	return Xow_db_file_.Tid__file_core;
			default:						throw Err_.new_unimplemented();
		}
	}
	public static void Make_cfg_data(Xow_wiki wiki, String file_core_name, Fsdb_db_file file, byte file_tid, int part_id) {
		Db_cfg_tbl cfg_tbl = file.Tbl__cfg();
		Xow_db_file core_db = wiki.Data__core_mgr().Db__core();
		core_db.Info_session().Save(cfg_tbl);
		Xob_info_file info_file = new Xob_info_file(-1, Xow_db_file_.To_key(file_tid), Xob_info_file.Ns_ids_empty, part_id, Guid_adp_.New(), 2, file_core_name, file.Url().NameAndExt());
		info_file.Save(cfg_tbl);
	}
	private static String	Main_core_name_all(String wiki_domain)	{return wiki_domain + ".xowa";}					// EX: en.wikipedia.org.xowa
	private static String	Main_core_name_few(String wiki_domain)	{return wiki_domain + "-file.xowa";}			// EX: en.wikipedia.org-file.xowa
	private static String	Main_core_name_lot(String wiki_domain)	{return wiki_domain + "-file-core.xowa";}		// EX: en.wikipedia.org-file-core.xowa
	public static String	Make_user_name(String wiki_domain)		{return wiki_domain + "-file-user.xowa";}		// EX: en.wikipedia.org-file-user.xowa
	private static final boolean schema_is_1 = false;
 	}
