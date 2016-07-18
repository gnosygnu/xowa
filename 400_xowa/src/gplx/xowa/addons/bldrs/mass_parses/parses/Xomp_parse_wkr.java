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
package gplx.xowa.addons.bldrs.mass_parses.parses; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.dbs.*; import gplx.xowa.addons.bldrs.mass_parses.dbs.*;
import gplx.xowa.htmls.core.bldrs.*;
import gplx.xowa.parsers.*;	
class Xomp_parse_wkr implements Gfo_invk {
	private final    Xomp_parse_mgr mgr;		
	private final    Xomp_page_pool page_pool;
	private final    int idx;
	private final    List_adp list = List_adp_.New(); private int list_idx = 0, list_len = 0;
	private final    Xob_hdump_bldr hdump_bldr = new Xob_hdump_bldr();
	private int done_count; private long done_time;
	private Xomp_wkr_db wkr_db;
	public Xomp_parse_wkr(Xomp_parse_mgr mgr, Xowe_wiki wiki, Xomp_page_pool page_pool, int idx) {
		this.mgr = mgr; this.wiki = wiki;
		this.page_pool = page_pool;
		this.idx = idx;
		this.wkr_db = mgr.Db_core().Wkr_db(Bool_.Y, idx);	// NOTE: must go in ctor, or else thread issues
	}
	public Xowe_wiki Wiki() {return wiki;} private final    Xowe_wiki wiki;
	public void Exec() {
		// init
		Xow_parser_mgr parser_mgr = new Xow_parser_mgr(wiki);
		wiki.Html_mgr().Page_wtr_mgr().Wkr(gplx.xowa.wikis.pages.Xopg_page_.Tid_read).Ctgs_enabled_(false);		// disable categories else progress messages written (also for PERF)
		if (wiki.File__bin_mgr() != null)
			wiki.File__bin_mgr().Wkrs__del(gplx.xowa.files.bins.Xof_bin_wkr_.Key_http_wmf);		// remove wmf wkr, else will try to download images during parsing
		hdump_bldr.Enabled_(true).Hzip_enabled_(true).Hzip_diff_(true).Init(wiki, wkr_db.Conn(), new Xob_hdump_tbl_retriever__xomp(wkr_db.Html_tbl()));
		wkr_db.Conn().Txn_bgn("xomp");

		while (true) {
			Xomp_page_itm ppg = Get_next(); if (ppg == Xomp_page_itm.Null) break;	// no more pages
			if (ppg.Text() == null) continue; // some pages have no text; ignore them else null ref; PAGE: it.d:miercuri DATE:2015-12-05
			
			try {
				// init page
				long done_bgn = gplx.core.envs.Env_.TickCount();
				Xoa_ttl ttl = wiki.Ttl_parse(ppg.Ns_id(), ppg.Ttl_bry());
				Xoae_page wpg = Xoae_page.New(wiki, ttl);
				wpg.Db().Text().Text_bry_(ppg.Text());
				wpg.Db().Page().Id_(ppg.Id());

				// parse page
				Xop_ctx pctx = parser_mgr.Ctx();
				pctx.Clear_all();
				parser_mgr.Parse(wpg, true);

				// gen_html
				hdump_bldr.Insert(pctx, wpg);

				// mark done for sake of progress
				mgr.Prog_mgr().Mark_done(ppg.Id());

				// update stats
				long time_cur = gplx.core.envs.Env_.TickCount();
				done_time += time_cur - done_bgn;
				done_bgn = time_cur;
				++done_count;

				// cleanup
				// ctx.App().Utl__bfr_mkr().Clear_fail_check();	// make sure all bfrs are released
				if (wiki.Cache_mgr().Tmpl_result_cache().Count() > 50000) 
					wiki.Cache_mgr().Tmpl_result_cache().Clear();
				if (done_count % 50 == 0) {
					wiki.Cache_mgr().Free_mem_all(Bool_.N);
					wiki.Parser_mgr().Scrib().Core_term();
				}
			} catch (Exception e) {
				Gfo_usr_dlg_.Instance.Warn_many("", "", "mass_parse.fail:ns=~{0} ttl=~{1} err=~{2}", ppg.Ns_id(), ppg.Ttl_bry(), Err_.Message_gplx_log(e));
			}
		}
		wkr_db.Conn().Txn_end();	// NOTE: must end txn before running update wkr_id
		mgr.Db_core().Update_wkr_id(idx, wkr_db.Conn());
		mgr.Wkrs_done_add_1();
		wkr_db.Conn().Rls_conn();
	}
	public void Bld_stats(Bry_bfr bfr) {
		int done_time_in_sec = (int)(done_time / 1000); if (done_time_in_sec == 0) done_time_in_sec = 1;
		bfr.Add_int_pad_bgn(Byte_ascii.Space, 4, idx		);
		bfr.Add_int_pad_bgn(Byte_ascii.Space, 8, (int)(done_count / done_time_in_sec));
		bfr.Add_int_pad_bgn(Byte_ascii.Space, 8, done_count);
		bfr.Add_int_pad_bgn(Byte_ascii.Space, 8, done_time_in_sec);
		bfr.Add_byte_nl();
	}
	private Xomp_page_itm Get_next() {
		if (list_idx == list_len) {
			list.Clear();
			page_pool.Get_next(list);
			list_len = list.Len();
			if (list_len == 0) return Xomp_page_itm.Null;
			list_idx = 0;
		}
		return (Xomp_page_itm)list.Get_at(list_idx++);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__exec))		this.Exec();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final String Invk__exec = "exec";
}
