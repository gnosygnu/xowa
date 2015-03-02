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
package gplx.xowa.html.hdumps.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.hdumps.*;
import gplx.dbs.*; import gplx.dbs.engines.sqlite.*; import gplx.xowa.dbs.*; import gplx.xowa.dbs.tbls.*;
import gplx.xowa.wikis.data.*;
import gplx.xowa.html.hdumps.data.*; import gplx.xowa.html.hzips.*;
import gplx.xowa.pages.*; import gplx.xowa2.gui.*;
public class Xob_hdump_bldr {
	private final Xowe_core_data_mgr core_data_mgr;
	private final Xodump_stats_itm stats_itm = new Xodump_stats_itm(); private final Xodump_stats_tbl stats_tbl = new Xodump_stats_tbl();
	private final Xog_page tmp_hpg = new Xog_page(); private final Bry_bfr tmp_bfr = Bry_bfr.reset_(Io_mgr.Len_mb);
	private final Xohd_page_html_mgr__save hdump_save_mgr = new Xohd_page_html_mgr__save();
	private Xowd_db_file hdump_db; private Xohd_page_html_tbl hdump_tbl; private long hdump_db_size, hdump_db_max;
	public Xob_hdump_bldr(Xodb_mgr_sql db_mgr, Db_conn make_conn, long hdump_db_max) {
		this.core_data_mgr = db_mgr.Core_data_mgr(); this.hdump_db_max = hdump_db_max;
		stats_tbl.Conn_(make_conn, Bool_.Y);
	}
	public void Bld_init() {
		Db_init(core_data_mgr.Dbs__get_by_tid_nth_or_new(Xowd_db_file_.Tid_html), core_data_mgr.Tbl__cfg().Select_as_long_or(Cfg_grp_hdump_make, Cfg_itm_hdump_size, 0));
		hdump_db.Conn().Txn_mgr().Txn_bgn_if_none();
	}
	public void Bld_term() {
		this.Commit();
		Db_term();
	}
	public void Insert_page(Xoae_page page) {
		Hzip_data(page);
		tmp_hpg.Ctor_from_page(tmp_bfr, page);
		int row_len = hdump_save_mgr.Insert(tmp_bfr, hdump_tbl, tmp_hpg, page.Hdump_data());
		stats_tbl.Insert(tmp_hpg, stats_itm, page.Root().Root_src().length, tmp_hpg.Page_body().length, row_len);
		hdump_db_size += row_len;
		if (hdump_db_size > hdump_db_max) {
			Db_term();
			Db_init(Xowd_db_init_wkr__html.I.Db_make(core_data_mgr), 0);
		}
	}
	public void Commit() {
		core_data_mgr.Dbs__save();								// commit new html_dbs
		hdump_db.Conn().Txn_mgr().Txn_end_all_bgn_if_none();	// commit entries
		core_data_mgr.Tbl__cfg().Update(Cfg_grp_hdump_make, Cfg_itm_hdump_size, Long_.Xto_str(hdump_db_size));	// update cfg; should happen after commit entries
	}
	private void Db_init(Xowd_db_file hdump_db, long hdump_db_size) {
		this.hdump_db = hdump_db;
		this.hdump_db_size = hdump_db_size;
		this.hdump_tbl = Xohd_page_html_tbl.Get_from_db(core_data_mgr, hdump_db);
	}
	private void Db_term() {
		Db_conn hdump_db_conn = hdump_db.Conn();
		hdump_db_conn.Txn_mgr().Txn_end_all();											// commit transactions
		hdump_tbl.Create_idx();
		Sqlite_engine_.Db_attach(hdump_db_conn, "page_db", core_data_mgr.Dbs__get_db_core().Url().Raw());	// update page_db.page with page_html_db_id
		hdump_db_conn.Txn_mgr().Txn_bgn();
		hdump_db_conn.Exec_sql(String_.Format(Sql_update_page_html_db_id, hdump_db.Id()));
		hdump_db_conn.Txn_mgr().Txn_end();
		Sqlite_engine_.Db_detach(hdump_db_conn, "page_db");
		hdump_db_conn.Conn_term();														// release conn
	}
	private static final String Cfg_grp_hdump_make = "hdump.make", Cfg_itm_hdump_size = "hdump.size";
	private static final String Sql_update_page_html_db_id = String_.Concat_lines_nl_skip_last
	( "REPLACE INTO page_db.page (page_id, page_namespace, page_title, page_is_redirect, page_touched, page_len, page_random_int, page_file_idx, page_redirect_id, page_html_db_id)"
	, "SELECT   p.page_id"
	, ",        p.page_namespace"
	, ",        p.page_title"
	, ",        p.page_is_redirect"
	, ",        p.page_touched"
	, ",        p.page_len"
	, ",        p.page_random_int"
	, ",        p.page_file_idx"
	, ",        p.page_redirect_id"
	, ",        {0}"
	, "FROM     page_db.page p"
	, "         JOIN wiki_page_html h ON p.page_id = h.page_id"
	);
	private void Hzip_data(Xoae_page page) {
		Xowe_wiki wiki = page.Wikie();
		Xow_hzip_mgr hzip_mgr = wiki.Html_mgr().Hzip_mgr();
		page.File_queue().Clear();																					// need to reset uid to 0, else xowa_file_# will resume from last
		wiki.Html_mgr().Page_wtr_mgr().Wkr(Xopg_view_mode.Tid_read).Write_body(tmp_bfr, Xoh_wtr_ctx.Hdump, page);	// write to html again, except in hdump mode
		hzip_mgr.Write(tmp_bfr, stats_itm, page.Url().Xto_full_bry(), tmp_bfr.Xto_bry_and_clear());					// hzip data
		page.Hdump_data().Body_(tmp_bfr.Xto_bry_and_clear());														// write to body bry
	}
}
