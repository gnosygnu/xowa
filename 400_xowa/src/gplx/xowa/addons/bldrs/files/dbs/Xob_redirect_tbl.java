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
import gplx.dbs.*; import gplx.dbs.engines.sqlite.*;
import gplx.langs.htmls.encoders.*;
import gplx.xowa.bldrs.*;
public class Xob_redirect_tbl {
	private Gfo_url_encoder encoder; private Db_stmt insert_stmt;
	public Xob_redirect_tbl(Io_url root_dir, Gfo_url_encoder encoder) {
		this.db_file = Xob_db_file.New__wiki_redirect(root_dir);
		this.conn = db_file.Conn();
		this.encoder = encoder;
	}
	public Xob_db_file Db_file() {return db_file;} private Xob_db_file db_file;
	public Db_conn Conn() {return conn;} private Db_conn conn;
	public Xob_redirect_tbl Create_table() {Sqlite_engine_.Tbl_create(conn, Tbl_name, Tbl_sql); return this;}
	public void Create_indexes(Gfo_usr_dlg usr_dlg) {
		Sqlite_engine_.Idx_create(usr_dlg, conn, Xob_db_file.Name__wiki_redirect, Idx_trg_id, Idx_trg_ttl);
	}
	public void Update_trg_redirect_id(Io_url core_url, int max_redirected_depth) {
		Sqlite_engine_.Db_attach(conn, "page_db", core_url.Raw());		// link database with page table 
		conn.Exec_sql(Sql_get_page_data);								// fill in page_id, page_ns, page_is_redirect for trg_ttl; EX: Page_A has "#REDIRECT Page_B"; Page_B is in redirect tbl; find its id, ttl, redirect status
		for (int i = 0; i < max_redirected_depth; i++) {				// loop to find redirected redirects; note that it is bounded by depth (to guard against circular redirects)
			int affected = conn.Exec_sql(Sql_get_redirect_redirects);	// find redirects that are also redirects
			if (affected == 0) break;									// no more redirected redirects; stop
			conn.Exec_sql(Sql_get_redirect_page_data);					// get page data for redirects
		}
		Sqlite_engine_.Db_detach(conn, "page_db");
	}
	public void Update_src_redirect_id(Io_url core_url, Db_conn core_provider) {
//			core_provider.Exec_sql(Sql_ddl__page_redirect_id);				// create page.page_redirect_id
		Sqlite_engine_.Idx_create(conn, Idx_trg_src);
		Sqlite_engine_.Db_attach(conn, "page_db", core_url.Raw());		// link database with page table 
		conn.Exec_sql(Sql_update_redirect_id);							// update page_redirect_id
		Sqlite_engine_.Db_detach(conn, "page_db");
	}
	public void Insert(int src_id, byte[] src_bry, Xoa_ttl trg_ttl) {
		byte[] redirect_ttl_bry = Xoa_ttl.Replace_spaces(trg_ttl.Page_db());	// NOTE: spaces can still exist b/c redirect is scraped from #REDIRECT which sometimes has a mix; EX: "A_b c"
		redirect_ttl_bry = encoder.Decode(redirect_ttl_bry);
		this.Insert(src_id, Xoa_ttl.Replace_spaces(src_bry), -1, trg_ttl.Ns().Id(), redirect_ttl_bry, trg_ttl.Anch_txt(), 1);
	}
	public void Insert(int src_id, byte[] src_ttl, int trg_id, int trg_ns, byte[] trg_ttl, byte[] trg_anchor, int count) {
		if (insert_stmt == null) insert_stmt = Db_stmt_.new_insert_(conn, Tbl_name, Fld_src_id, Fld_src_ttl, Fld_trg_id, Fld_trg_ns, Fld_trg_ttl, Fld_trg_anchor, Fld_trg_is_redirect, Fld_redirect_count);
		insert_stmt.Clear()
		.Val_int(src_id)
		.Val_bry_as_str(src_ttl)
		.Val_int(trg_id)
		.Val_int(trg_ns)
		.Val_bry_as_str(trg_ttl)
		.Val_bry_as_str(trg_anchor)
		.Val_byte((byte)1)
		.Val_int(count)
		.Exec_insert();
	}
	public void Rls_all() {
		insert_stmt.Rls();
		conn.Rls_conn();
	}
	public static final String Tbl_name = "redirect";
	private static final String 
	  Fld_src_id = "src_id", Fld_src_ttl = "src_ttl", Fld_trg_id = "trg_id", Fld_trg_ns = "trg_ns", Fld_trg_ttl = "trg_ttl", Fld_trg_anchor = "trg_anchor"
	, Fld_trg_is_redirect = "trg_is_redirect", Fld_redirect_count = "redirect_count";
	private static final    String Tbl_sql = String_.Concat_lines_nl
	( "CREATE TABLE IF NOT EXISTS redirect"
	, "( src_id            integer             NOT NULL       PRIMARY KEY"
	, ", src_ttl           varchar(255)        NOT NULL"
	, ", trg_id            integer             NOT NULL"
	, ", trg_ns            integer             NOT NULL"
	, ", trg_ttl           varchar(255)        NOT NULL"
	, ", trg_anchor        varchar(255)        NOT NULL"
	, ", trg_is_redirect   tinyint             NOT NULL"
	, ", redirect_count    integer             NOT NULL"
	, ");"
	);
	private static final    Db_idx_itm
	  Idx_trg_ttl = Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS redirect__trg_ttl ON redirect (trg_ttl);")
	, Idx_trg_id  = Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS redirect__trg_id  ON redirect (trg_id);")
	, Idx_trg_src = Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS redirect__trg_src ON redirect (src_id, trg_id);")
	;
//		public static final    String
//		  Sql_ddl__page_redirect_id		= "ALTER TABLE page ADD COLUMN page_redirect_id integer NOT NULL DEFAULT '-1'"
//		;
	private static final    String
	  Sql_get_page_data = String_.Concat_lines_nl			// get data from page table for initial redirect dump 
	( "REPLACE INTO redirect "
	, "SELECT  t.src_id"
	, ",       t.src_ttl"
	, ",       j.page_id"
	, ",       t.trg_ns"
	, ",       t.trg_ttl"
	, ",       t.trg_anchor"
	, ",       j.page_is_redirect"
	, ",       t.redirect_count"
	, "FROM    redirect t"
	, "        JOIN page_db.page j "
	, "          ON  t.trg_ns = j.page_namespace"
	, "          AND t.trg_ttl = j.page_title"
	, "          AND t.trg_is_redirect = 1  -- limit to redirects"
	, ";"
	)
	, Sql_get_redirect_redirects = String_.Concat_lines_nl	// find redirects that are redirected
	( "REPLACE INTO redirect"
	, "SELECT  t.src_id"
	, ",       t.src_ttl"
	, ",       j.trg_id"
	, ",       -1"
	, ",       ''"
	, ",       ''"
	, ",       1"
	, ",       t.redirect_count + 1"
	, "FROM    redirect t"
	, "        JOIN redirect j "
	, "          ON  t.trg_id = j.src_id"
	, "          AND t.trg_is_redirect = 1"
	, ";"
	, ""
	)
	, Sql_get_redirect_page_data = String_.Concat_lines_nl	// get data from page table for redirected redirects
	( "REPLACE INTO redirect"
	, "SELECT  t.src_id"
	, ",       t.src_ttl"
	, ",       t.trg_id"
	, ",       j.page_namespace"
	, ",       j.page_title"
	, ",       t.trg_anchor"
	, ",       j.page_is_redirect"
	, ",       t.redirect_count"
	, "FROM    redirect t"
	, "        JOIN page_db.page j "
	, "          ON  t.trg_id = j.page_id "
	, "          AND t.trg_is_redirect = 1  -- limit to redirects"
	, ";"
	)
	, Sql_update_redirect_id = String_.Concat_lines_nl_skip_last
	( "REPLACE INTO"
	, "        page_db.page (page_id, page_namespace, page_title, page_is_redirect, page_touched, page_len, page_random_int, page_text_db_id, page_html_db_id, page_redirect_id, page_score)"
	, "SELECT  p.page_id"
	, ",       p.page_namespace"
	, ",       p.page_title"
	, ",       p.page_is_redirect"
	, ",       p.page_touched"
	, ",       p.page_len"
	, ",       p.page_random_int"
	, ",       p.page_text_db_id"
	, ",       p.page_html_db_id"
	, ",       r.trg_id"
	, ",       p.page_score"
	, "FROM    redirect r"
	, "        JOIN page_db.page p ON r.src_id = p.page_id"
	)
	;
}
