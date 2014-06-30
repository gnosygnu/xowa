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
package gplx.xowa.bldrs.wikis.redirects; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wikis.*;
import gplx.dbs.*; import gplx.xowa.dbs.*; import gplx.xowa.bldrs.oimgs.*;
class Xob_wiki_redirect_tbl {
	public Xob_wiki_redirect_tbl Create_table(Db_provider p) {Sqlite_engine_.Tbl_create(p, Tbl_name, Tbl_sql); return this;}
	public void Create_indexes(Gfo_usr_dlg usr_dlg, Db_provider p) {
		Sqlite_engine_.Idx_create(usr_dlg, p, Xodb_db_file.Name__wiki_redirect, Idx_trg_id, Idx_trg_ttl);
	}
	public void Update_redirects(Db_provider p, Io_url core_url, int max_redirected_depth) {
		Sqlite_engine_.Db_attach(p, "join_db", core_url.Raw());			// link database with page table 
		p.Exec_sql(Sql_get_page_data);									// fill in page_id, page_ns, page_is_redirect for trg_ttl; EX: Page_A has "#REDIRECT Page_B"; Page_B is in redirect tbl; find its id, ttl, redirect status
		for (int i = 0; i < max_redirected_depth; i++) {				// loop to find redirected redirects; note that it is bounded by depth (to guard against circular redirects)
			int affected = p.Exec_sql(Sql_get_redirect_redirects);		// find redirects that are also redirects
			if (affected == 0) break;									// no more redirected redirects; stop
			p.Exec_sql(Sql_get_redirect_page_data);						// get page data for redirects
		}
		Sqlite_engine_.Db_detach(p, "join_db");
	}
	public Db_stmt Insert_stmt(Db_provider p) {return Db_stmt_.new_insert_(p, Tbl_name, Fld_src_id, Fld_src_ttl, Fld_trg_id, Fld_trg_ns, Fld_trg_ttl, Fld_trg_anchor, Fld_trg_is_redirect, Fld_redirect_count);}
	public void Insert(Db_stmt stmt, int src_id, byte[] src_ttl, int trg_id, int trg_ns, byte[] trg_ttl, byte[] trg_anchor, int count) {
		stmt.Clear()
		.Val_int_(src_id)
		.Val_str_by_bry_(src_ttl)
		.Val_int_(trg_id)
		.Val_int_(trg_ns)
		.Val_str_by_bry_(trg_ttl)
		.Val_str_by_bry_(trg_anchor)
		.Val_byte_((byte)1)
		.Val_int_(count)
		.Exec_insert();
	}
	public static final String Tbl_name = "redirect"
	, Fld_src_id = "src_id", Fld_src_ttl = "src_ttl", Fld_trg_id = "trg_id", Fld_trg_ns = "trg_ns", Fld_trg_ttl = "trg_ttl", Fld_trg_anchor = "trg_anchor"
	, Fld_trg_is_redirect = "trg_is_redirect", Fld_redirect_count = "redirect_count";
	private static final String Tbl_sql = String_.Concat_lines_nl
	(	"CREATE TABLE IF NOT EXISTS redirect"
	,	"( src_id            integer             NOT NULL       PRIMARY KEY"
	,	", src_ttl           varchar(255)        NOT NULL"
	,	", trg_id            integer             NOT NULL"
	,	", trg_ns            integer             NOT NULL"
	,	", trg_ttl           varchar(255)        NOT NULL"
	,	", trg_anchor        varchar(255)        NOT NULL"
	,	", trg_is_redirect   tinyint             NOT NULL"
	,	", redirect_count    integer             NOT NULL"
	,	");"
	);
	private static final Db_idx_itm
		Idx_trg_ttl = Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS redirect__trg_ttl ON redirect (trg_ttl);")
	,	Idx_trg_id  = Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS redirect__trg_id  ON redirect (trg_id);")
	;
	private static final String
	Sql_get_page_data = String_.Concat_lines_nl				// get data from page table for initial redirect dump 
	(	"REPLACE INTO redirect "
	,	"SELECT  t.src_id"
	,	",       t.src_ttl"
	,	",       j.page_id"
	,	",       t.trg_ns"
	,	",       t.trg_ttl"
	,	",       t.trg_anchor"
	,	",       j.page_is_redirect"
	,	",       t.redirect_count"
	,	"FROM    redirect t"
	,	"        JOIN join_db.page j "
	,	"          ON  t.trg_ns = j.page_namespace"
	,	"          AND t.trg_ttl = j.page_title"
	,	"          AND t.trg_is_redirect = 1  -- limit to redirects"
	,	";"
	)
	,	Sql_get_redirect_redirects = String_.Concat_lines_nl	// find redirects that are redirected
	(	"REPLACE INTO redirect"
	,	"SELECT  t.src_id"
	,	",       t.src_ttl"
	,	",       j.trg_id"
	,	",       -1"
	,	",       ''"
	,	",       ''"
	,	",       1"
	,	",       t.redirect_count + 1"
	,	"FROM    redirect t"
	,	"        JOIN redirect j "
	,	"          ON  t.trg_id = j.src_id"
	,	"          AND t.trg_is_redirect = 1"
	,	";"
	,	""
	)
	,	Sql_get_redirect_page_data = String_.Concat_lines_nl	// get data from page table for redirected redirects
	(	"REPLACE INTO redirect"
	,	"SELECT  t.src_id"
	,	",       t.src_ttl"
	,	",       t.trg_id"
	,	",       j.page_namespace"
	,	",       j.page_title"
	,	",       t.trg_anchor"
	,	",       j.page_is_redirect"
	,	",       t.redirect_count"
	,	"FROM    redirect t"
	,	"        JOIN join_db.page j "
	,	"          ON  t.trg_id = j.page_id "
	,	"          AND t.trg_is_redirect = 1  -- limit to redirects"
	,	";"
	);
}
