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
import gplx.dbs.*;
import gplx.xowa.bldrs.*;
class Xodel_make_mgr {
	public void Exec(Xow_wiki wiki) {
		// mark the records deleted
		Db_conn make_conn = Xob_db_file.New__file_make(wiki.Fsys_mgr().Root_dir()).Conn();
		make_conn.Exec_sql_concat_w_msg
		( "marking items deleted"
		, "UPDATE  fsdb_regy"
		, "SET     fsdb_deleted = 1"
		, "WHERE   NOT EXISTS "
		, "("
		, "SELECT  1"
		, "FROM    xfer_regy xr"
		, "WHERE   xr.lnki_ttl     = fsdb_regy.fsdb_name"
		, "AND     xr.file_is_orig = fsdb_regy.fsdb_is_orig"
		// , "AND     xr.orig_repo    = fsdb_regy.fsdb_repo"	// TOMBSTONE: do no reinstate; some images exist in both repos, and this will delete images from one repo; DATE:2016-09-28
		, "AND     xr.file_w       = fsdb_regy.fsdb_w"
		, "AND     xr.lnki_time    = fsdb_regy.fsdb_time"
		, "AND     xr.lnki_page    = fsdb_regy.fsdb_page"
		, ")");

		// create deletion db
		Xob_db_file deletion_db = Xob_db_file.New__deletion_db(wiki);
		Db_conn deletion_conn = deletion_db.Conn();
		deletion_db.Tbl__cfg().Upsert_str("", Xodel_exec_mgr.Cfg__deletion_db__domain, wiki.Domain_str());

		// copy records over to it
		Xob_delete_regy_tbl delete_regy_tbl = new Xob_delete_regy_tbl(deletion_conn);
		deletion_conn.Meta_tbl_remake(delete_regy_tbl.Meta());
		deletion_conn.Env_db_attach("make_db", make_conn);
		deletion_conn.Exec_sql_concat_w_msg
		( "inserting into delete_regy"
		, "INSERT INTO delete_regy (fil_id, thm_id, reason)"
		, "SELECT  fsdb_fil_id"
		, ",       fsdb_thm_id"
		, ",       ''"
		, "FROM    make_db.fsdb_regy"
		, "WHERE   fsdb_deleted = 1"
		, ";"
		);
		deletion_conn.Env_db_detach("make_db");
		deletion_conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_tbl(delete_regy_tbl.tbl_name, "main", delete_regy_tbl.fld_fil_id, delete_regy_tbl.fld_thm_id));
	}
}
