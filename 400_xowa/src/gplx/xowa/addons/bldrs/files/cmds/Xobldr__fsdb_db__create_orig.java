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
import gplx.dbs.*; import gplx.dbs.engines.sqlite.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;
import gplx.xowa.files.*; import gplx.fsdb.*;
public class Xobldr__fsdb_db__create_orig extends Xob_cmd__base {
	private Db_conn conn; private boolean schema_1;
	public Xobldr__fsdb_db__create_orig(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	@Override public void Cmd_bgn(Xob_bldr bldr) {
		Xof_fsdb_mode fsdb_mode = wiki.File__fsdb_mode();
		fsdb_mode.Tid__v2__bld__y_();
		wiki.Init_assert();
		Fsdb_db_mgr db_core_mgr = Fsdb_db_mgr_.new_detect(wiki, wiki.Fsys_mgr().Root_dir(), wiki.Fsys_mgr().File_dir());
		this.schema_1 = db_core_mgr.File__schema_is_1();
		conn = db_core_mgr.File__orig_tbl_ary()[gplx.fsdb.meta.Fsm_mnt_mgr.Mnt_idx_main].Conn();
		Io_url make_db_url = Xob_db_file.New__file_make(wiki.Fsys_mgr().Root_dir()).Url();
		Sqlite_engine_.Db_attach(conn, "make_db", make_db_url.Raw());
	}
	@Override public void Cmd_run() {
		String tbl_name = "orig_reg", fld_status = "orig_status";
		if (schema_1) {
			tbl_name = "wiki_orig";
			fld_status = "status";
		}
		conn.Exec_sql_plog_txn("orig_wkr.deleting orig_reg"			, String_.Format(Sql_delete_wiki_orig, tbl_name)); // always delete orig_reg, else will not pick up changed sizes / moved repos; DATE:2014-07-21
		conn.Exec_sql_plog_txn("orig_wkr.inserting xfer direct"		, String_.Format(Sql_create_xfer_direct, tbl_name, fld_status));
		conn.Exec_sql_plog_txn("orig_wkr.inserting xfer redirect"	, String_.Format(Sql_create_xfer_redirect, tbl_name, fld_status));
		conn.Exec_sql_plog_txn("orig_wkr.inserting orig direct"		, String_.Format(Sql_create_orig_direct, tbl_name, fld_status));
		conn.Exec_sql_plog_txn("orig_wkr.inserting orig redirect"	, String_.Format(Sql_create_orig_redirect, tbl_name, fld_status));
	}
	private static final    String 
	  Sql_delete_wiki_orig = "DELETE FROM {0};"
	, Sql_create_xfer_direct = String_.Concat_lines_nl
	( "INSERT INTO {0} "
	, "(orig_ttl, {1}, orig_repo, orig_ext, orig_w, orig_h, orig_redirect)"
	, "SELECT DISTINCT"
	, "        xfer.lnki_ttl"
	, ",       1 --pass"
	, ",       xfer.orig_repo"
	, ",       xfer.lnki_ext"
	, ",       xfer.orig_w"
	, ",       xfer.orig_h"
	, ",       ''"
	, "FROM    make_db.xfer_regy xfer"
	, "        LEFT JOIN {0} cur ON xfer.lnki_ttl = cur.orig_ttl"
	, "WHERE   cur.orig_ttl IS NULL"
	)
	, Sql_create_xfer_redirect = String_.Concat_lines_nl
	( "INSERT INTO {0} "
	, "(orig_ttl, {1}, orig_repo, orig_ext, orig_w, orig_h, orig_redirect)"
	, "SELECT DISTINCT"
	, "        xfer.orig_redirect_src"
	, ",       1 --pass"
	, ",       xfer.orig_repo"
	, ",       xfer.lnki_ext"
	, ",       xfer.orig_w"
	, ",       xfer.orig_h"
	, ",       xfer.lnki_ttl"
	, "FROM    make_db.xfer_regy xfer"
	, "        LEFT JOIN {0} cur ON xfer.orig_redirect_src = cur.orig_ttl"
	, "WHERE   cur.orig_ttl IS NULL"
	, "AND     Coalesce(xfer.orig_redirect_src, '') != ''"
	) 
	, Sql_create_orig_direct = String_.Concat_lines_nl
	( "INSERT INTO {0} "
	, "(orig_ttl, {1}, orig_repo, orig_ext, orig_w, orig_h, orig_redirect)"
	, "SELECT DISTINCT"
	, "        orig.lnki_ttl"
	, ",       0 --unknown"
	, ",       orig.orig_repo"
	, ",       orig.lnki_ext"
	, ",       orig.orig_w"
	, ",       orig.orig_h"
	, ",       ''"
	, "FROM    make_db.orig_regy orig"
	, "        LEFT JOIN {0} cur ON orig.lnki_ttl = cur.orig_ttl"
	, "WHERE   cur.orig_ttl IS NULL"							// not already in orig_reg
	, "AND     orig.orig_repo IS NOT NULL"					// not found in oimg_image.sqlite3
	, "AND     Coalesce(orig.orig_w           , -1) != -1"	// ignore entries that are either ext_id = 0 ("File:1") or don't have any width / height info (makes it useless); need to try to get again from wmf_api
	, "AND     Coalesce(orig.orig_redirect_ttl, '') == ''"	// direct
	)
	, Sql_create_orig_redirect = String_.Concat_lines_nl
	( "INSERT INTO {0} "
	, "(orig_ttl, {1}, orig_repo, orig_ext, orig_w, orig_h, orig_redirect)"
	, "SELECT DISTINCT"
	, "        orig.orig_redirect_ttl"
	, ",       0 --unknown"
	, ",       orig.orig_repo"
	, ",       orig.lnki_ext"
	, ",       orig.orig_w"
	, ",       orig.orig_h"
	, ",       ''"
	, "FROM    make_db.orig_regy orig"
	, "        LEFT JOIN {0} cur ON orig.orig_redirect_ttl = cur.orig_ttl"
	, "WHERE   cur.orig_ttl IS NULL"							// not already in orig_reg
	, "AND     orig.orig_repo IS NOT NULL"					// not found in oimg_image.sqlite3
	, "AND     Coalesce(orig.orig_w,            -1) != -1"	// ignore entries that are either ext_id = 0 ("File:1") or don't have any width / height info (makes it useless); need to try to get again from wmf_api
	, "AND     Coalesce(orig.orig_redirect_ttl, '') != ''"	// redirect
	)
	; 

	public static final String BLDR_CMD_KEY = "file.orig_reg";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Xobldr__fsdb_db__create_orig(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xobldr__fsdb_db__create_orig(bldr, wiki);}
}
