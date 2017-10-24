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
package gplx.xowa.addons.bldrs.files.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import gplx.dbs.*;
public class Xob_fsdb_regy_tbl_ {
	public static final String Tbl_name = "fsdb_regy";
	public static final    String Tbl_sql = String_.Concat_lines_nl
	( "CREATE TABLE fsdb_regy "
	, "( fsdb_id               integer             NOT NULL       PRIMARY KEY        AUTOINCREMENT"
	, ", fsdb_name             varchar(255)        NOT NULL"
	, ", fsdb_is_orig          tinyint             NOT NULL"
	, ", fsdb_repo             tinyint             NOT NULL"
	, ", fsdb_w                integer             NOT NULL"
	, ", fsdb_time		       double              NOT NULL"
	, ", fsdb_page		       integer             NOT NULL"
	, ", fsdb_db_id		       integer             NOT NULL"
	, ", fsdb_size		       bigint              NOT NULL"
	, ", fsdb_status		   tinyint             NOT NULL"
	, ", fsdb_fil_id           integer             NOT NULL"
	, ", fsdb_thm_id           integer             NOT NULL"
	, ", fsdb_deleted          tinyint             NOT NULL"
	, ");"
	);
	public static final    Db_idx_itm Idx_main = Db_idx_itm.sql_("CREATE INDEX fsdb_regy__main ON fsdb_regy (fsdb_name, fsdb_is_orig, fsdb_repo, fsdb_w, fsdb_time, fsdb_page);");
	public static final    String 
	  Insert_fsdb_fil = String_.Concat_lines_nl
	( "INSERT INTO fsdb_regy (fsdb_name, fsdb_is_orig, fsdb_repo, fsdb_w, fsdb_time, fsdb_page, fsdb_db_id, fsdb_size, fsdb_status, fsdb_fil_id, fsdb_thm_id, fsdb_deleted)"
	, "SELECT  f.fil_name"
	, ",       1"
	, ",       CASE WHEN d.dir_name = 'commons.wikimedia.org' THEN 0 ELSE 1 END"
	, ",       -1"
	, ",       -1"
	, ",       -1"
	, ",       f.fil_bin_db_id"
	, ",       f.fil_size"
	, ",       0"
	, ",       f.fil_id"
	, ",       -1"
	, ",       0"
	, "FROM    fsdb_db.fsdb_fil f"
	, "        JOIN fsdb_db.fsdb_dir d ON f.fil_owner_id = d.dir_id"
	, "WHERE   f.fil_bin_db_id != -1"
	, ";"
	)
	, Insert_fsdb_thm = String_.Concat_lines_nl
	( "INSERT INTO fsdb_regy (fsdb_name, fsdb_is_orig, fsdb_repo, fsdb_w, fsdb_time, fsdb_page, fsdb_db_id, fsdb_size, fsdb_status, fsdb_fil_id, fsdb_thm_id, fsdb_deleted)"
	, "SELECT  f.fil_name"
	, ",       0"
	, ",       CASE WHEN d.dir_name = 'commons.wikimedia.org' THEN 0 ELSE 1 END"
	, ",       t.thm_w"
	, ",       t.thm_time"
	, ",       t.thm_page"
	, ",       t.thm_bin_db_id"
	, ",       t.thm_size"
	, ",       0"
	, ",       f.fil_id"
	, ",       t.thm_id"
	, ",       0"
	, "FROM    fsdb_db.fsdb_fil f"
	, "        JOIN fsdb_db.{0} t ON f.fil_id = t.thm_owner_id"
	, "        JOIN fsdb_db.fsdb_dir d ON f.fil_owner_id = d.dir_id"
	, ";"
	)
	, Insert_fsdb_thm_v0 = String_.Concat_lines_nl
	( "INSERT INTO fsdb_regy (fsdb_name, fsdb_is_orig, fsdb_repo, fsdb_w, fsdb_time, fsdb_page, fsdb_db_id, fsdb_size, fsdb_status, fsdb_fil_id, fsdb_thm_id, fsdb_deleted)"
	, "SELECT  f.fil_name"
	, ",       0"
	, ",       CASE WHEN d.dir_name = 'commons.wikimedia.org' THEN 0 ELSE 1 END"
	, ",       t.thm_w"
	, ",       t.thm_thumbtime"
	, ",       -1"
	, ",       t.thm_bin_db_id"
	, ",       t.thm_size"
	, ",       0"
	, ",       f.fil_id"
	, ",       t.thm_id"
	, ",       0"
	, "FROM    fsdb_db.fsdb_fil f"
	, "        JOIN fsdb_db.{0} t ON f.fil_id = t.thm_owner_id"
	, "        JOIN fsdb_db.fsdb_dir d ON f.fil_owner_id = d.dir_id"
	, ";"
	)
	, Update_regy_nil = "UPDATE xfer_regy SET xfer_status = 0;"
	, Update_regy_fil = String_.Concat_lines_nl
	( "REPLACE INTO xfer_regy "
	, "( lnki_id, lnki_tier_id, lnki_page_id, orig_page_id, orig_repo, lnki_ttl, orig_redirect_src, lnki_ext, orig_media_type"
	, ", file_is_orig, orig_w, orig_h, file_w, file_h, lnki_time, lnki_page, lnki_count"
	, ", xfer_status"
	, ")"
	, "SELECT "
	, "  lnki_id, lnki_tier_id, lnki_page_id, orig_page_id, orig_repo, lnki_ttl, orig_redirect_src, lnki_ext, orig_media_type"
	, ", file_is_orig, orig_w, orig_h, file_w, file_h, lnki_time, lnki_page, lnki_count"
	, ", CASE WHEN f.fsdb_name IS NOT NULL THEN 1 ELSE 0 END"
	, "FROM    xfer_regy x"
	, "        LEFT JOIN fsdb_regy f ON x.lnki_ttl = f.fsdb_name"
	, "WHERE   x.file_is_orig = 1 AND f.fsdb_is_orig  = 1"
	, "AND     x.orig_repo = f.fsdb_repo"
	, ";"
	)
	, Update_regy_thm = String_.Concat_lines_nl
	( "REPLACE INTO xfer_regy "
	, "( lnki_id, lnki_tier_id, lnki_page_id, orig_page_id, orig_repo, lnki_ttl, orig_redirect_src, lnki_ext, orig_media_type"
	, ", file_is_orig, orig_w, orig_h, file_w, file_h, lnki_time, lnki_page, lnki_count"
	, ", xfer_status"
	, ")"
	, "SELECT "
	, "  lnki_id, lnki_tier_id, lnki_page_id, orig_page_id, orig_repo, lnki_ttl, orig_redirect_src, lnki_ext, orig_media_type"
	, ", file_is_orig, orig_w, orig_h, file_w, file_h, lnki_time, lnki_page, lnki_count"
	, ", CASE WHEN f.fsdb_name IS NOT NULL THEN 1 ELSE 0 END"
	, "FROM    xfer_regy x"
	, "        LEFT JOIN fsdb_regy f ON x.lnki_ttl = f.fsdb_name AND x.file_w = f.fsdb_w"
	,   "          AND x.lnki_time = f.fsdb_time AND x.lnki_page = f.fsdb_page"
	, "WHERE   x.file_is_orig = 0 AND f.fsdb_is_orig  = 0"
	, "AND     x.orig_repo = f.fsdb_repo"
	, ";"
	);
}
