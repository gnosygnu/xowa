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
package gplx.xowa.addons.bldrs.mass_parses.makes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.dbs.*;
import gplx.xowa.htmls.core.bldrs.*;
import gplx.xowa.parsers.*;
class Xomp_parse_wkr implements Gfo_invk {
	private final    Xomp_parse_mgr mgr;
	private final    Xowe_wiki wiki;
	private final    Xomp_page_pool page_pool;
	private final    List_adp list = List_adp_.New(); private int list_idx = 0, list_len = 0;
	private final    int num_pages_per_wkr;		
	private final    int idx;
	private final    Xob_hdump_bldr hdump_bldr = new Xob_hdump_bldr();
	// private boolean gen_html = true;// gen_hdump = true;
	private int done_count; private long done_time;
	public Xomp_parse_wkr(Xomp_parse_mgr mgr, Xowe_wiki wiki, Xomp_page_pool page_pool, int idx, int num_pages_per_wkr) {
		this.mgr = mgr; this.wiki = wiki;
		this.page_pool = page_pool; this.num_pages_per_wkr = num_pages_per_wkr;
		this.idx = idx;
	}
	public void Exec() {
		// init
		Db_conn wkr_conn = Db_conn_bldr.Instance.Get_or_autocreate(true, mgr.Cfg().Root_dir().GenSubFil_nest("xomp_" + Int_.To_str_fmt(idx, "000"), "xomp_wkr.sqlite3"));			
		Xow_parser_mgr parser = new Xow_parser_mgr(wiki);
		wiki.Html_mgr().Page_wtr_mgr().Wkr(gplx.xowa.wikis.pages.Xopg_page_.Tid_read).Ctgs_enabled_(false);		// disable categories else progress messages written (also for PERF)
		if (wiki.File__bin_mgr() != null)
			wiki.File__bin_mgr().Wkrs__del(gplx.xowa.files.bins.Xof_bin_wkr_.Key_http_wmf);		// remove wmf wkr, else will try to download images during parsing
		hdump_bldr.Init(wiki, wkr_conn, new Xob_hdump_tbl_retriever__xomp(wkr_conn));

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
				parser.Ctx().Clear_all();
				parser.Parse(wpg, true);

				// gen_html
//					if (	gen_html 
//						&&	wpg.Redirect().Itms__len() == 0)	// don't generate html for redirected pages
//						wiki.Html_mgr().Page_wtr_mgr().Gen(wpg, gplx.xowa.wikis.pages.Xopg_page_.Tid_read);
//					if (gen_hdump)
//						hdump_bldr.Insert(wpg);

				// mark done for sake of progress
				page_pool.Mark_done(ppg.Id());

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
					wiki.Cache_mgr().Free_mem_all();
					wiki.Parser_mgr().Scrib().Core_term();
				}
			} catch (Exception e) {
				Gfo_usr_dlg_.Instance.Warn_many("", "", "mass_parse.fail:ns=~{0} ttl=~{1} err=~{2}", ppg.Ns_id(), ppg.Ttl_bry(), Err_.Message_gplx_log(e));
			}
		}
		mgr.Wkrs_done_add_1();
		wkr_conn.Rls_conn();
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
			page_pool.Get_next(list, num_pages_per_wkr);
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
