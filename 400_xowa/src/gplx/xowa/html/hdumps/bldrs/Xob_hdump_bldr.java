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
import gplx.dbs.*; import gplx.xowa.wikis.data.*; import gplx.dbs.engines.sqlite.*; import gplx.xowa.dbs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.html.hdumps.data.*; import gplx.xowa.html.hzips.*;
import gplx.xowa.pages.*; import gplx.xowa2.gui.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
public class Xob_hdump_bldr {
	private final Xowd_db_mgr wiki_db_mgr; private final Xob_ns_to_db_mgr ns_to_db_mgr; int prv_row_len = 0;
	private final Xodump_stats_itm stats_itm = new Xodump_stats_itm(); private final Xodump_stats_tbl stats_tbl;
	private final Xog_page tmp_hpg = new Xog_page(); private final Bry_bfr tmp_bfr = Bry_bfr.reset_(Io_mgr.Len_mb);
	private final Xohd_page_html_mgr__save hdump_save_mgr = new Xohd_page_html_mgr__save();
	private final boolean hzip_enabled;
	public Xob_hdump_bldr(Xow_ns_mgr ns_mgr, Xodb_mgr_sql db_mgr, Db_conn make_conn, long hdump_db_max, boolean hzip_enabled) {
		this.wiki_db_mgr = db_mgr.Core_data_mgr();
		this.ns_to_db_mgr = new Xob_ns_to_db_mgr(new Xob_ns_to_db_wkr__html(wiki_db_mgr.Db__core()), wiki_db_mgr, hdump_db_max);
		this.stats_tbl = new Xodump_stats_tbl(make_conn);
		Xob_ns_file_itm.Init_ns_bldr_data(Xowd_db_file_.Tid_html_data, ns_mgr, gplx.xowa.apis.xowa.bldrs.imports.Xoapi_import.Ns_file_map__each);
		this.hzip_enabled = hzip_enabled;
	}
	public void Bld_term() {
		this.Commit();
		Db_term();
	}
	public void Insert_page(Xoae_page page) {
		Hzip_data(page);
		tmp_hpg.Ctor_from_page(tmp_bfr, page);
		Xowd_db_file db_file = ns_to_db_mgr.Get_by_ns(page.Ttl().Ns().Bldr_data(), prv_row_len);
		this.prv_row_len = hdump_save_mgr.Insert(tmp_bfr, db_file.Tbl__html(), tmp_hpg, page.Hdump_data());
		stats_tbl.Insert(tmp_hpg, stats_itm, page.Root().Root_src().length, tmp_hpg.Page_body().length, prv_row_len);
	}
	public void Commit() {
		ns_to_db_mgr.Commit();
//			wiki_db_mgr.Tbl__cfg().Update_long(Cfg_grp_hdump_make, Cfg_itm_hdump_size, hdump_db_size);	// update cfg; should happen after commit entries
	}
	private void Db_term() {
		ns_to_db_mgr.Rls_all();
	}
//		private static final String Cfg_grp_hdump_make = "hdump.make", Cfg_itm_hdump_size = "hdump.size";
	private void Hzip_data(Xoae_page page) {
		Xowe_wiki wiki = page.Wikie();
		Xow_hzip_mgr hzip_mgr = wiki.Html_mgr().Hzip_mgr();
		page.File_queue().Clear();																					// need to reset uid to 0, else xowa_file_# will resume from last
		wiki.Html_mgr().Page_wtr_mgr().Wkr(Xopg_view_mode.Tid_read).Write_body(tmp_bfr, Xoh_wtr_ctx.Hdump, page);	// write to html again, except in hdump mode
		if (hzip_enabled) hzip_mgr.Write(tmp_bfr, stats_itm, page.Url().Xto_full_bry(), tmp_bfr.Xto_bry_and_clear());					// hzip data
		page.Hdump_data().Body_(tmp_bfr.Xto_bry_and_clear());														// write to body bry
	}
}
class Xob_ns_to_db_wkr__html implements Xob_ns_to_db_wkr {
	private final Xowd_db_file page_db;
	public Xob_ns_to_db_wkr__html(Xowd_db_file page_db) {this.page_db = page_db;}
	public byte Db_tid() {return Xowd_db_file_.Tid_html_data;}
	public void Tbl_init(Xowd_db_file db) {
		Xowd_html_tbl tbl = db.Tbl__html();
		tbl.Create_tbl();
		tbl.Insert_bgn(); 
	}
	public void Tbl_term(Xowd_db_file db) {
		db.Tbl__text().Insert_end(); 
		Db_conn db_conn = db.Conn();
		db.Tbl__html().Create_idx();
		Db_attach_cmd.new_(page_db.Conn(), "html_db", db.Url())
			.Add_fmt("hdump.update page.html_db_id", Sql_update_page_html_db_id, db.Id())
			.Exec();
		db_conn.Rls_conn();
	}
	private static final String Sql_update_page_html_db_id = String_.Concat_lines_nl_skip_last
	( "REPLACE INTO page (page_id, page_namespace, page_title, page_is_redirect, page_touched, page_len, page_random_int, page_text_db_id, page_html_db_id, page_redirect_id)"
	, "SELECT   p.page_id"
	, ",        p.page_namespace"
	, ",        p.page_title"
	, ",        p.page_is_redirect"
	, ",        p.page_touched"
	, ",        p.page_len"
	, ",        p.page_random_int"
	, ",        p.page_text_db_id"
	, ",        {0}"
	, ",        p.page_redirect_id"
	, "FROM     page p"
	, "         JOIN <attach_db>html h ON p.page_id = h.page_id"
	);
}
