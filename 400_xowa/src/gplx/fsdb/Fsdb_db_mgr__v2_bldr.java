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
package gplx.fsdb; import gplx.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.fsdb.meta.*; import gplx.fsdb.data.*; import gplx.xowa.files.origs.*;
import gplx.xowa.*; import gplx.xowa.wikis.data.*; import gplx.xowa.bldrs.infos.*;
public class Fsdb_db_mgr__v2_bldr {
	public Fsdb_db_mgr__v2 Make(Xowe_wiki wiki) {
		Xowd_db_layout layout = wiki.Data_mgr__core_mgr().Props().Layout_file();
		String domain_str = wiki.Domain_str();
		Io_url wiki_dir = wiki.Fsys_mgr().Root_dir();
		String main_core_name = Main_core_name(layout, domain_str);
		Fsdb_db_file main_core_file = Make_core_file_main(wiki, wiki_dir, main_core_name, layout);
		Fsdb_db_file user_core_file = Make_core_file_user(wiki, wiki_dir, Make_user_name(domain_str), main_core_name);
		return new Fsdb_db_mgr__v2(layout, wiki_dir, main_core_file, user_core_file);
	}
	private Fsdb_db_file Make_core_file_main(Xowe_wiki wiki, Io_url wiki_dir, String main_core_name, Xowd_db_layout layout) {
		Io_url url = wiki_dir.GenSubFil(main_core_name);
		Db_conn conn = layout.Tid_is_all() ? Db_conn_bldr.I.Get(url) : Db_conn_bldr.I.New(url);	// if all, use existing (assumes same file name); else, create new
		conn.Txn_bgn();
		Fsdb_db_file rv = Make_core_file(url, conn, schema_is_1, Fsm_mnt_mgr.Mnt_idx_main);
		if (!layout.Tid_is_all()) // do not make cfg data if all
			Make_cfg_data(wiki, main_core_name, rv, Main_core_tid(layout), -1);
		Fsdb_db_mgr__v2.Cfg__layout_file__set(rv.Tbl__cfg(), layout);
		conn.Txn_end();
		return rv;
	}
	private Fsdb_db_file Make_core_file_user(Xowe_wiki wiki, Io_url wiki_dir, String user_file_name, String main_core_name) { // always create file; do not create mnt_tbl;
		Io_url url = wiki_dir.GenSubFil(user_file_name);
		Db_conn conn = Db_conn_bldr.I.New(url);
		conn.Txn_bgn();
		Fsdb_db_file rv = Make_core_file(url, conn, schema_is_1, Fsm_mnt_mgr.Mnt_idx_user);
		Fsm_bin_tbl dbb_tbl = new Fsm_bin_tbl(conn, schema_is_1, Fsm_mnt_mgr.Mnt_idx_user); dbb_tbl.Insert(0, user_file_name);
		Make_bin_tbl(rv);
		Make_cfg_data(wiki, main_core_name, rv, Xowd_db_file_.Tid_file_user, -1);
		conn.Txn_end();
		return rv;
	}
	private Fsdb_db_file Make_core_file(Io_url core_url, Db_conn core_conn, boolean schema_is_1, int mnt_id) {
		Fsdb_db_file rv = new Fsdb_db_file(core_url, core_conn);
		Db_cfg_tbl cfg_tbl = rv.Tbl__cfg();
		cfg_tbl.Create_tbl();
		cfg_tbl.Insert_int(Fsm_cfg_mgr.Grp_core, Fsm_cfg_mgr.Key_next_id				, 1);		// start next_id at 1
		cfg_tbl.Insert_yn(Fsm_cfg_mgr.Grp_core, Fsm_cfg_mgr.Key_schema_thm_page			, Bool_.Y);	// new dbs automatically have page and time in fsdb_xtn_tm
		cfg_tbl.Insert_yn(Fsm_cfg_mgr.Grp_core, Fsm_cfg_mgr.Key_patch_next_id			, Bool_.Y);	// new dbs automatically have correct next_id
		Fsm_mnt_mgr.Patch(cfg_tbl);
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
	public static String Main_core_name(Xowd_db_layout layout, String wiki_domain) {
		switch (layout.Tid()) {
			case Xowd_db_layout.Const_all:	return Main_core_name_all(wiki_domain);
			case Xowd_db_layout.Const_few:	return Main_core_name_few(wiki_domain);
			case Xowd_db_layout.Const_lot:  return Main_core_name_lot(wiki_domain);
			default:						throw Err_.not_implemented_();
		}
	}
	private static byte Main_core_tid(Xowd_db_layout layout) {
		switch (layout.Tid()) {
			case Xowd_db_layout.Const_all:	return Xowd_db_file_.Tid_core;
			case Xowd_db_layout.Const_few:	return Xowd_db_file_.Tid_file_solo;
			case Xowd_db_layout.Const_lot:  return Xowd_db_file_.Tid_file_core;
			default:						throw Err_.not_implemented_();
		}
	}
	public static void Make_cfg_data(Xowe_wiki wiki, String file_core_name, Fsdb_db_file file, byte file_tid, int part_id) {
		Db_cfg_tbl cfg_tbl = file.Tbl__cfg();
		Xowd_db_file core_db = wiki.Data_mgr__core_mgr().Db__core();
		core_db.Info_session().Save(cfg_tbl);
		Xob_info_file info_file = new Xob_info_file(-1, Xowd_db_file_.To_key(file_tid), Xob_info_file.Ns_ids_empty, part_id, Guid_adp_.random_(), 2, file_core_name, file.Url().NameAndExt());
		info_file.Save(cfg_tbl);
	}
	private static String	Main_core_name_all(String wiki_domain)	{return wiki_domain + ".xowa";}					// EX: en.wikipedia.org.xowa
	private static String	Main_core_name_few(String wiki_domain)	{return wiki_domain + "-file.xowa";}			// EX: en.wikipedia.org-file.xowa
	private static String	Main_core_name_lot(String wiki_domain)	{return wiki_domain + "-file-core.xowa";}		// EX: en.wikipedia.org-file-core.xowa
	public static String	Make_user_name(String wiki_domain)		{return wiki_domain + "-file-user.xowa";}		// EX: en.wikipedia.org-file-user.xowa
	private static final boolean schema_is_1 = false;
        public static final Fsdb_db_mgr__v2_bldr I = new Fsdb_db_mgr__v2_bldr(); Fsdb_db_mgr__v2_bldr() {}
}
