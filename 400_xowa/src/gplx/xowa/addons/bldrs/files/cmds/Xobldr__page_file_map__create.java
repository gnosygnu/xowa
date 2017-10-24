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
import gplx.dbs.*; import gplx.xowa.addons.bldrs.files.dbs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;	
public class Xobldr__page_file_map__create extends Xob_cmd__base {
	private Db_conn conn;
	public Xobldr__page_file_map__create(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	@Override public void Cmd_run() {
		this.conn = Xob_db_file.New__file_make(wiki.Fsys_mgr().Root_dir()).Conn();
		Append__fsdb_id();
		Create__fsdb_idxs();
		Update__fsdb_id();
		Create__page_file_map();
	}
	private void Append__fsdb_id() { // NOTE: append the field; do not create with table b/c will be extra baggage during all REPLACE INTO
		String[] tbls = String_.Ary("xfer_regy", "xfer_temp", "lnki_regy", "lnki_temp", "fsdb_regy");
		String fld = "fsdb_id";
		for (String tbl : tbls) {
			if (!conn.Meta_fld_exists(tbl, fld))
				conn.Meta_fld_append(tbl, Dbmeta_fld_itm.new_int(fld).Default_(-1));	// EX: ALTER TABLE xfer_regy ADD fsdb_id INTEGER DEFAULT -1;
		}
	}
	private void Create__fsdb_idxs() {
		// EX: CREATE INDEX xfer_regy__fsdb_regy ON xfer_regy (lnki_ttl, file_is_orig, orig_repo, file_w, lnki_time, lnki_page);
		Dbmeta_idx_itm[] idxs = new Dbmeta_idx_itm[]
		{ Dbmeta_idx_itm.new_normal_by_tbl("xfer_regy", "page_file_map_1", "lnki_ttl", "file_is_orig", "orig_repo", "file_w", "lnki_time", "lnki_page")
		, Dbmeta_idx_itm.new_normal_by_tbl("xfer_temp", "page_file_map_1", "lnki_ttl"			, "lnki_w", "lnki_h", "lnki_upright", "lnki_time", "lnki_page")
		, Dbmeta_idx_itm.new_normal_by_tbl("xfer_temp", "page_file_map_2", "orig_redirect_src", "lnki_w", "lnki_h", "lnki_upright", "lnki_time", "lnki_page")
		, Dbmeta_idx_itm.new_normal_by_tbl("lnki_regy", "page_file_map_1", "lnki_ttl", "lnki_w", "lnki_h", "lnki_upright", "lnki_time", "lnki_page")
		, Dbmeta_idx_itm.new_normal_by_tbl("lnki_temp", "page_file_map_1", "lnki_ttl", "lnki_w", "lnki_h", "lnki_upright", "lnki_time", "lnki_page")
		, Dbmeta_idx_itm.new_normal_by_tbl("fsdb_regy", "page_file_map_1", "fsdb_id", "fsdb_fil_id", "fsdb_thm_id")
		};
		for (Dbmeta_idx_itm idx : idxs) {
			if (!conn.Meta_idx_exists(idx.Name()))
				conn.Meta_idx_create(idx);
		}
	}
	private void Update__fsdb_id() {
		conn.Exec_sql_concat_w_msg
		( "updating fsdb_id.xfer_regy"
		, "UPDATE  xfer_regy "
		, "SET     fsdb_id = Coalesce"
		, "(("
		, "SELECT  fsdb_id "
		, "FROM    fsdb_regy fr"
		, "WHERE   fr.fsdb_name     = xfer_regy.lnki_ttl"
		, "AND     fr.fsdb_is_orig  = xfer_regy.file_is_orig"
		, "AND     fr.fsdb_repo     = xfer_regy.orig_repo"
		, "AND     fr.fsdb_w        = xfer_regy.file_w"
		, "AND     fr.fsdb_time     = xfer_regy.lnki_time"
		, "AND     fr.fsdb_page     = xfer_regy.lnki_page"
		, "), -1);"
		);
		conn.Exec_sql_concat_w_msg
		( "updating fsdb_id.xfer_temp"
		, "UPDATE  xfer_temp"
		, "SET     fsdb_id = Coalesce"
		, "(("
		, "SELECT  fsdb_id "
		, "FROM    xfer_regy xr"
		, "WHERE   xr.lnki_ttl      = xfer_temp.lnki_ttl"
		, "AND     xr.file_is_orig  = xfer_temp.file_is_orig"
		, "AND     xr.orig_repo     = xfer_temp.orig_repo"
		, "AND     xr.file_w        = xfer_temp.file_w"
		, "AND     xr.lnki_time     = xfer_temp.lnki_time"
		, "AND     xr.lnki_page     = xfer_temp.lnki_page"
		, "), -1);"
		);
		conn.Exec_sql_concat_w_msg
		( "updating fsdb_id.lnki_regy.redirect"
		, "UPDATE  lnki_regy"
		, "SET     fsdb_id = Coalesce"
		, "(("
		, "SELECT  fsdb_id "
		, "FROM    xfer_temp xt"
		, "WHERE   xt.orig_redirect_src = lnki_regy.lnki_ttl"
		, "AND     xt.lnki_w        = lnki_regy.lnki_w"
		, "AND     xt.lnki_h        = lnki_regy.lnki_h"
		, "AND     xt.lnki_upright  = lnki_regy.lnki_upright"
		, "AND     xt.lnki_time     = lnki_regy.lnki_time"
		, "AND     xt.lnki_page     = lnki_regy.lnki_page"
		, "), -1);"
		);
		conn.Exec_sql_concat_w_msg
		( "updating fsdb_id.lnki_regy.direct"
		, "UPDATE  lnki_regy"
		, "SET     fsdb_id = Coalesce"
		, "(("
		, "SELECT  fsdb_id "
		, "FROM    xfer_temp xt"
		, "WHERE   xt.lnki_ttl      = lnki_regy.lnki_ttl"
		, "AND     xt.lnki_w        = lnki_regy.lnki_w"
		, "AND     xt.lnki_h        = lnki_regy.lnki_h"
		, "AND     xt.lnki_upright  = lnki_regy.lnki_upright"
		, "AND     xt.lnki_time     = lnki_regy.lnki_time"
		, "AND     xt.lnki_page     = lnki_regy.lnki_page"
		, "), -1)"
		, "WHERE   Coalesce(fsdb_id, -1) = -1"
		, ";"
		);
		conn.Exec_sql_concat_w_msg
		( "updating fsdb_id.lnki_temp"
		, "UPDATE  lnki_temp"
		, "SET     fsdb_id = Coalesce"
		, "(("
		, "SELECT  fsdb_id "
		, "FROM    lnki_regy lr"
		, "WHERE   lr.lnki_ttl      = lnki_temp.lnki_ttl"
		, "AND     lr.lnki_w        = lnki_temp.lnki_w"
		, "AND     lr.lnki_h        = lnki_temp.lnki_h"
		, "AND     lr.lnki_upright  = lnki_temp.lnki_upright"
		, "AND     lr.lnki_time     = lnki_temp.lnki_time"
		, "AND     lr.lnki_page     = lnki_temp.lnki_page"
		, "), -1);"
		);
	}
	private void Create__page_file_map() {
		Xob_db_file map_db = Xob_db_file.New__page_file_map(wiki);
		Db_conn map_conn = map_db.Conn();
		Page_file_map_tbl map_tbl = new Page_file_map_tbl(map_conn, "page_file_map");
		map_conn.Meta_tbl_remake(map_tbl.Meta());
		map_conn.Env_db_attach("make_db", conn);
		map_conn.Exec_sql_concat_w_msg
		( "inserting page_file_map"
		, "INSERT INTO page_file_map (page_id, fil_id, thm_id, sort_id, count_of)"
		, "SELECT  lt.lnki_page_id"
		, ",       fr.fsdb_fil_id"
		, ",       fr.fsdb_thm_id"
		, ",       -1"
		, ",       Count(fr.fsdb_thm_id)"
		, "FROM    make_db.lnki_temp lt"
		, "        JOIN make_db.fsdb_regy fr ON lt.fsdb_id = fr.fsdb_id"
		, "GROUP BY lt.lnki_page_id"
		, ",       fr.fsdb_fil_id"
		, ",       fr.fsdb_thm_id"
		, ";"
		);
		map_conn.Env_db_detach("make_db");
		map_conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_tbl("page_file_map", "main", "fil_id", "thm_id"));
	}

	public static final String BLDR_CMD_KEY = "file.page_file_map.create";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Xobldr__page_file_map__create(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xobldr__page_file_map__create(bldr, wiki);}
}
