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
import gplx.dbs.*; import gplx.xowa.*; import gplx.xowa.wikis.data.*; import gplx.fsdb.meta.*;
public class Fsdb_db_mgr_ {
	public static Fsdb_db_mgr new_detect(Xow_wiki wiki, Io_url wiki_dir, Io_url file_dir) {
		Gfo_usr_dlg usr_dlg = Xoa_app_.Usr_dlg();
		Io_url url = file_dir.GenSubFil(Fsdb_db_mgr__v1.Mnt_name); // EX: /xowa/file/en.wikipedia.org/wiki.mnt.sqlite3
		if	(Db_conn_bldr.Instance.Exists(url)) {	// NOTE: check v1 before v2; note that as of v2.5.4, v2 files are automatically created on new import; DATE:2015-06-09
			usr_dlg.Log_many("", "", "fsdb.db_core.v1: url=~{0}", url.Raw());
			usr_dlg.Log_many("", "", "fsdb.db_core.v1 exists: orig=~{0} abc=~{1} atr_a=~{2}, atr_b=~{3}"
				, Db_conn_bldr.Instance.Exists(file_dir.GenSubFil(Fsdb_db_mgr__v1.Orig_name))
				, Db_conn_bldr.Instance.Exists(file_dir.GenSubFil_nest(Fsm_mnt_tbl.Mnt_name_main, Fsdb_db_mgr__v1.Abc_name))
				, Db_conn_bldr.Instance.Exists(file_dir.GenSubFil_nest(Fsm_mnt_tbl.Mnt_name_main, Fsdb_db_mgr__v1.Atr_name_v1a))
				, Db_conn_bldr.Instance.Exists(file_dir.GenSubFil_nest(Fsm_mnt_tbl.Mnt_name_main, Fsdb_db_mgr__v1.Atr_name_v1b))
				);
			return new Fsdb_db_mgr__v1(file_dir);
		}

		// FOLDER.RENAME: handle renamed folders; EX:"/wiki/en.wikipedia.org-2016-12" DATE:2017-02-01
		String domain_str = wiki.Domain_str();
		try {
			String cfg_domain_str = wiki.Data__core_mgr().Db__core().Tbl__cfg().Select_str_or("xowa.bldr.session", "wiki_domain", domain_str);
			if (!String_.Eq(domain_str, cfg_domain_str)) {
				Gfo_usr_dlg_.Instance.Note_many("", "", "fsdb.db_core.init: fsys.domain doesn't match db.domain; fsys=~{0} db=~{1}", domain_str, cfg_domain_str);
				domain_str = cfg_domain_str;
			}
		} catch (Exception e) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "fsdb.db_core.init: failed to get domain from config; err=~{0}", Err_.Message_gplx_log(e));
		}

		Fsdb_db_mgr rv = null;
		rv = load_or_null(Xow_db_layout.Itm_few, usr_dlg, wiki_dir, wiki, domain_str); if (rv != null) return rv;
		rv = load_or_null(Xow_db_layout.Itm_lot, usr_dlg, wiki_dir, wiki, domain_str); if (rv != null) return rv;
		rv = load_or_null(Xow_db_layout.Itm_all, usr_dlg, wiki_dir, wiki, domain_str); if (rv != null) return rv;
		usr_dlg.Log_many("", "", "fsdb.db_core.none: wiki_dir=~{0} file_dir=~{1}", wiki_dir.Raw(), file_dir.Raw());
		return null;
	}
	private static Fsdb_db_mgr load_or_null(Xow_db_layout layout, Gfo_usr_dlg usr_dlg, Io_url wiki_dir, Xow_wiki wiki, String domain_str) {
		Io_url main_core_url = wiki_dir.GenSubFil(Fsdb_db_mgr__v2_bldr.Main_core_name(layout, domain_str));
		if (!Db_conn_bldr.Instance.Exists(main_core_url)) return null;
		usr_dlg.Log_many("", "", "fsdb.db_core.v2: type=~{0} url=~{1}", layout.Key(), main_core_url.Raw());
		Db_conn main_core_conn = Db_conn_bldr.Instance.Get(main_core_url);
		if (wiki.Data__core_mgr().Props().Layout_file().Tid_is_all()) {
			return new Fsdb_db_mgr__v2(Fsdb_db_mgr__v2.Cfg__layout_file__get(main_core_conn), wiki_dir, new Fsdb_db_file(main_core_url, main_core_conn), new Fsdb_db_file(main_core_url, main_core_conn));
		}
		Io_url user_core_url = wiki_dir.GenSubFil(Fsdb_db_mgr__v2_bldr.Make_user_name(domain_str));
		if (!Db_conn_bldr.Instance.Exists(user_core_url)) {	// if user file does not exist, create it; needed b/c offline packages don't include file; DATE:2015-04-19
			try {Fsdb_db_mgr__v2_bldr.Make_core_file_user(wiki, user_core_url, user_core_url.NameAndExt(), main_core_url.NameAndExt());}
			catch (Exception e) {	// do not fail if read-only permissions
				usr_dlg.Warn_many("", "", "failed to create user db: url=~{0} err=~{1}", user_core_url.Raw(), Err_.Message_gplx_log(e));
				user_core_url = null;		// null out for conditional below
			}
		}
		Db_conn user_core_conn = null;
		if (user_core_url == null) {		// null when write permissions do not exist; for example, on Andriod; DATE:2016-04-22
			user_core_url = main_core_url;	// default to main_core; note that downloading will still fail, but at least app won't crash; DATE:2016-04-22
			user_core_conn = main_core_conn;
		}
		else {
			user_core_conn = Db_conn_bldr.Instance.Get(user_core_url);
		}
		return new Fsdb_db_mgr__v2(Fsdb_db_mgr__v2.Cfg__layout_file__get(main_core_conn), wiki_dir, new Fsdb_db_file(main_core_url, main_core_conn), new Fsdb_db_file(user_core_url, user_core_conn));
	}
}
