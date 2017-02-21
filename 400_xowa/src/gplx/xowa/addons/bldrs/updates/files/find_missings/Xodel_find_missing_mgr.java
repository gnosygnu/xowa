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
package gplx.xowa.addons.bldrs.updates.files.find_missings; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.updates.*; import gplx.xowa.addons.bldrs.updates.files.*;
import gplx.dbs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.addons.bldrs.files.dbs.*;
class Xodel_find_missing_mgr {
	public void Exec(Xow_wiki wiki) {
		// get page_file_map; note that it must have fsdb_regy
		Db_conn pfm_conn = Xob_db_file.New__page_file_map(wiki).Conn();
		
		// attach page_db
		Db_attach_mgr attach_mgr = new Db_attach_mgr(pfm_conn, new Db_attach_itm("page_db", wiki.Data__core_mgr().Tbl__page().Conn()));
		Page_file_map_tbl pfm_tbl = new Page_file_map_tbl(pfm_conn, "page_file_map_found");
		pfm_conn.Meta_tbl_remake(pfm_tbl);
		attach_mgr.Exec_sql_w_msg("generating page_file_map_found", Db_sql_.Make_by_fmt(String_.Ary
		( "INSERT INTO page_file_map_found (page_id, fil_id, thm_id, sort_id, count_of)"
		, "SELECT  pfm.page_id, pfm.fil_id, pfm.thm_id, pfm.sort_id, pfm.count_of"
		, "FROM    page_file_map pfm"
		, "        JOIN <page_db>page p ON pfm.page_id = p.page_id"
		)));
		pfm_conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_tbl("page_file_map_found", "fil_id__thm_id", "fil_id", "thm_id"));

		// update fsdb_deleted
		pfm_conn.Exec_sql("", Db_sql_.Make_by_fmt(String_.Ary
		( "UPDATE  fsdb_regy"
		, "SET     fsdb_deleted = 1"
		, "WHERE   NOT EXISTS "
		, "("
		, "SELECT  1"
		, "FROM    page_file_map_found pfm"
		, "WHERE   pfm.fil_id     = fsdb_regy.fsdb_fil_id"
		, "AND     pfm.thm_id     = fsdb_regy.fsdb_thm_id"
		, ")"
		)));

		// create deletion db
		Xob_db_file deletion_db = Xob_db_file.New__deletion_db(wiki);
		Db_conn deletion_conn = deletion_db.Conn();
		deletion_db.Tbl__cfg().Upsert_str("", Xodel_exec_mgr.Cfg__deletion_db__domain, wiki.Domain_str());

		// copy records over to it
		Xob_delete_regy_tbl delete_regy_tbl = new Xob_delete_regy_tbl(deletion_conn);
		deletion_conn.Meta_tbl_remake(delete_regy_tbl.Meta());
		deletion_conn.Env_db_attach("make_db", pfm_conn);
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
/*
--create fsdb_regy in pfm_db
CREATE TABLE fsdb_regy 
( fsdb_id               integer             NOT NULL       PRIMARY KEY        AUTOINCREMENT
, fsdb_name             varchar(255)        NOT NULL
, fsdb_is_orig          tinyint             NOT NULL
, fsdb_repo             tinyint             NOT NULL
, fsdb_w                integer             NOT NULL
, fsdb_time		       double              NOT NULL
, fsdb_page		       integer             NOT NULL
, fsdb_db_id		       integer             NOT NULL
, fsdb_size		       bigint              NOT NULL
, fsdb_status		   tinyint             NOT NULL
, fsdb_fil_id           integer             NOT NULL
, fsdb_thm_id           integer             NOT NULL
, fsdb_deleted          tinyint             NOT NULL
);		

--export fsdb_regy from file.make
ATTACH 'en.wikipedia.org-file-page_map.xowa' AS pfm_db;
INSERT INTO pfm_db.fsdb_regy SELECT * FROM fsdb_regy;
CREATE INDEX fsdb_regy__main ON fsdb_regy (fsdb_fil_id, fsdb_thm_id);
*/
}
