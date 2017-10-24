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
package gplx.xowa.addons.wikis.ctgs.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*;
import gplx.dbs.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.addons.wikis.ctgs.dbs.*;
class Xob_catlink_wkr {
	public void Make_catlink_dbs(Xowe_wiki wiki, Db_conn tmp_conn, Db_conn page_conn, Db_conn cat_core_conn) {
		// init select
		Db_attach_mgr attach_mgr = new Db_attach_mgr(page_conn, new Db_attach_itm("temp_db", tmp_conn), new Db_attach_itm("cat_db", cat_core_conn));	// NOTE: main_conn must be page_conn, else sqlite will be very slow when doing insert
		String sql = attach_mgr.Resolve_sql(String_.Concat_lines_nl_skip_last
		( "SELECT  tcl.cl_from"
		, ",       p.page_id"
		, ",       tcl.cl_type_id"
		, ",       tcl.cl_timestamp"
		, ",       tcl.cl_sortkey"
		, ",       tcl.cl_sortkey_prefix"
		, "FROM    <temp_db>tmp_cat_link tcl"
		, "        JOIN page p ON tcl.cl_to_ttl = p.page_title AND p.page_namespace = 14"
		, "ORDER BY 1"	// NOTE: must sort by page_id to keep all cats for page in one db
		));
		attach_mgr.Attach();

		// select from tmp_db and insert into cat_link
		Xodb_cat_link_tbl cat_link_tbl = Make_cat_link_tbl(wiki, null);
		Db_rdr rdr = attach_mgr.Conn_main().Stmt_sql(sql).Exec_select__rls_auto();
		try {
			// misc row vals
			long db_size_cur = 0, db_size_max = wiki.Appe().Api_root().Bldr().Wiki().Import().Cat_link_db_max();
			int page_id_prv = -1;
			int rows = 0;
			while (rdr.Move_next()) {
				// check if row can fit in db; else update db_size
				int page_id_cur = rdr.Read_int("cl_from");
				byte[] sortkey = rdr.Read_bry("cl_sortkey");
				if (sortkey == null) sortkey = Bry_.Empty;	// WORKAROUND: sortkey should never be null; however, sqlite.jdbc sometimes returns as null; EX:ru.s and cl_from = 1324; DATE:2016-11-19
				byte[] sortkey_prefix = rdr.Read_bry_by_str("cl_sortkey_prefix");
				long db_size_new = db_size_cur + 48 + (sortkey.length * 2) + sortkey_prefix.length;// 46 = 3 ints (12) + 1 long (8) + 1 byte (2?) + 2 index (24?) + 11 fudge factor (?); DATE:2016-09-06
				if (	db_size_cur > db_size_max		// size exceeded
					&&	page_id_cur != page_id_prv) {	// and page_id is diff; keeps all page_ids in one db
					cat_link_tbl = Make_cat_link_tbl(wiki, cat_link_tbl);
					db_size_new = 0;
				}
				db_size_cur = db_size_new;
				page_id_prv = page_id_cur;

				// insert; notify;
				cat_link_tbl.Insert_cmd_by_batch(page_id_prv, rdr.Read_int("page_id"), rdr.Read_byte("cl_type_id"), rdr.Read_long("cl_timestamp"), sortkey, sortkey_prefix);
				if (++rows % 100000 == 0) {
					Gfo_usr_dlg_.Instance.Plog_many("", "", "inserting cat_link row: ~{0}", Int_.To_str_fmt(rows, "#,##0"));
				}
			}
		}
		finally {rdr.Rls();}
		Term_cat_link_tbl(cat_link_tbl);
		attach_mgr.Detach();	// NOTE: must detach after txn
	}
	private static Xodb_cat_link_tbl Make_cat_link_tbl(Xowe_wiki wiki, Xodb_cat_link_tbl cat_link_tbl) {
		Term_cat_link_tbl(cat_link_tbl);

		// get cat_link_conn
		Db_conn cat_link_conn = wiki.Data__core_mgr().Props().Layout_text().Tid_is_lot()
			? wiki.Data__core_mgr().Dbs__make_by_tid(Xow_db_file_.Tid__cat_link).Conn()
			: wiki.Data__core_mgr().Db__core().Conn();

		// make tbl
		Xodb_cat_link_tbl rv = new Xodb_cat_link_tbl(cat_link_conn);
		cat_link_conn.Meta_tbl_remake(rv);
		rv.Insert_bgn();
		return rv;
	}
	private static void Term_cat_link_tbl(Xodb_cat_link_tbl cat_link_tbl) {
		if (cat_link_tbl == null) return;
		cat_link_tbl.Insert_end();
		cat_link_tbl.Create_idx__catbox();
		cat_link_tbl.Create_idx__catpage();
	}
	public void Make_catcore_tbl(Xowe_wiki wiki, Db_conn tmp_conn, Db_conn page_conn, Db_conn cat_core_conn) {
		Db_attach_mgr attach_mgr = new Db_attach_mgr(cat_core_conn, new Db_attach_itm("temp_db", tmp_conn), new Db_attach_itm("page_db", page_conn));
		Xowd_cat_core_tbl cat_core_tbl = new Xowd_cat_core_tbl(cat_core_conn, Bool_.N);
		cat_core_conn.Meta_tbl_remake(cat_core_tbl);
		String sql = String_.Concat_lines_nl_skip_last	// ANSI.Y
		( "INSERT INTO cat_core (cat_id, cat_pages, cat_subcats, cat_files, cat_hidden, cat_link_db_id)"
		, "SELECT  p.page_id"
		, ",       Sum(CASE WHEN tcl.cl_type_id = 2 THEN 1 ELSE 0 END)"
		, ",       Sum(CASE WHEN tcl.cl_type_id = 0 THEN 1 ELSE 0 END)"
		, ",       Sum(CASE WHEN tcl.cl_type_id = 1 THEN 1 ELSE 0 END)"
		, ",       CASE WHEN h.cat_id IS NULL THEN 0 ELSE 1 END"
		, ",       -1"
		, "FROM    <page_db>page p"
		, "        JOIN <temp_db>tmp_cat_link tcl ON tcl.cl_to_ttl = p.page_title"
		, "        LEFT JOIN <temp_db>tmp_cat_hidden h ON h.cat_id = p.page_id"
		, "WHERE   p.page_namespace = 14"
		, "GROUP BY p.page_id"
		);
		attach_mgr.Exec_sql(sql);
	}
	public void Update_page_cat_db_id(Xowe_wiki wiki, Db_conn page_conn) {
		// assert page_cat_db_id exists
		Xow_db_mgr db_mgr = wiki.Data__core_mgr();
		Xowd_page_tbl page_tbl = db_mgr.Db__core().Tbl__page();
		page_conn.Meta_fld_append_if_missing(page_tbl.Tbl_name(), page_tbl.Flds__all(), Dbmeta_fld_itm.new_int("page_cat_db_id").Default_(-1));

		// prep sql
		String sql = String_.Concat_lines_nl_skip_last
		( "UPDATE  page"
		, "SET     page_cat_db_id = {0}"
		, "WHERE   page_id IN (SELECT cl_from FROM <link_db>cat_link WHERE cl_from = page.page_id);"
		);
		Db_attach_mgr attach_mgr = new Db_attach_mgr(page_conn);

		// loop cat_link dbs and update page_id
		boolean layout_is_lot = wiki.Data__core_mgr().Props().Layout_text().Tid_is_lot();
		int len = db_mgr.Dbs__len();
		for (int i = 0; i < len; ++i) {
			Xow_db_file link_db = db_mgr.Dbs__get_at(i);
			switch (link_db.Tid()) {
				case Xow_db_file_.Tid__core:
					if (layout_is_lot) continue;	// use core_db is all or few; skip if lot;
					break;
				case Xow_db_file_.Tid__cat_link:
					break;
				default:
					continue;
			}
			attach_mgr.Conn_links_(new Db_attach_itm("link_db", link_db.Conn()));
			Gfo_usr_dlg_.Instance.Prog_many("", "", "updating page.cat_db_id; db=~{0}", link_db.Id());
			attach_mgr.Exec_sql(sql, link_db.Id());
		}
	}
}
