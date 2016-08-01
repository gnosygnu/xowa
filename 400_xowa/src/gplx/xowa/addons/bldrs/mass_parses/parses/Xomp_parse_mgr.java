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
import gplx.core.threads.*; import gplx.core.threads.utils.*; import gplx.core.caches.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.cases.*;
import gplx.xowa.addons.bldrs.mass_parses.dbs.*;
import gplx.xowa.wikis.caches.*;
class Xomp_parse_mgr {
	private Gfo_countdown_latch latch;
	public Xomp_parse_mgr_cfg Cfg() {return cfg;} private final    Xomp_parse_mgr_cfg cfg = new Xomp_parse_mgr_cfg();		
	public Xomp_db_core Db_core() {return db_core;} private Xomp_db_core db_core;
	public Xomp_prog_mgr Prog_mgr() {return prog_mgr;} private final    Xomp_prog_mgr prog_mgr = new Xomp_prog_mgr();
	public void Wkrs_done_add_1() {latch.Countdown();}
	public void Run(Xowe_wiki wiki) {
		// init db, pool_loader, pool, prog_mgr
		cfg.Init(wiki);
		this.db_core = Xomp_db_core.New__load(wiki);
		Xomp_page_pool_loader pool_loader = new Xomp_page_pool_loader(wiki, db_core.Mgr_db().Conn(), cfg.Num_pages_in_pool());
		Xomp_page_pool page_pool = new Xomp_page_pool(pool_loader, cfg.Num_pages_per_wkr());
		prog_mgr.Init(pool_loader.Get_pending_count(), cfg.Progress_interval());
		wiki.App().User().User_db_mgr().Cache_mgr().Enabled_n_();	// disable db lookups of cache
		Xow_page_cache page_cache = Xomp_tmpl_cache_bldr.New(wiki, cfg.Load_all_templates());
		Gfo_cache_mgr commons_cache = new Gfo_cache_mgr().Max_size_(Int_.Max_value).Reduce_by_(Int_.Max_value);
		Gfo_cache_mgr ifexist_cache = new Gfo_cache_mgr().Max_size_(Int_.Max_value).Reduce_by_(Int_.Max_value);

		// load_wkr: init and start
		// Xomp_load_wkr load_wkr = new Xomp_load_wkr(wiki, db_core.Mgr_db().Conn(), cfg.Num_pages_in_pool(), cfg.Num_wkrs());
		// Thread_adp_.Start_by_key("xomp.load", Cancelable_.Never, load_wkr, Xomp_load_wkr.Invk__exec);
 
		// init parse_wkrs
		int wkr_len = cfg.Num_wkrs();
		latch = new Gfo_countdown_latch(wkr_len);
		Xomp_parse_wkr[] wkrs = new Xomp_parse_wkr[wkr_len];
		for (int i = 0; i < wkr_len; ++i) {
			Xowe_wiki wkr_wiki = Xow_wiki_utl_.Clone_wiki(wiki, wiki.Fsys_mgr().Root_dir());
			Xomp_parse_wkr wkr = new Xomp_parse_wkr(this, wkr_wiki, page_pool, i, cfg, cfg.Cleanup_interval(), cfg.Progress_interval(), cfg.Log_file_lnkis());
			wkr_wiki.Cache_mgr().Page_cache_(page_cache).Commons_cache_(commons_cache).Ifexist_cache_(ifexist_cache);
			// remove wmf wkr, else will try to download images during parsing
			if (wkr_wiki.File__bin_mgr() != null)
				wkr_wiki.File__bin_mgr().Wkrs__del(gplx.xowa.files.bins.Xof_bin_wkr_.Key_http_wmf);	
			wkr.Hdump_bldr().Enabled_(cfg.Hdump_enabled()).Hzip_enabled_(cfg.Hzip_enabled()).Hzip_diff_(cfg.Hdiff_enabled());
			wkrs[i] = wkr;
		}

		// start threads; done separately b/c thread issues when done right after init
		for (int i = 0; i < wkr_len; ++i) {
			Xomp_parse_wkr wkr = wkrs[i];
			Thread_adp_.Start_by_key("xomp." + Int_.To_str_fmt(i, "000"), Cancelable_.Never, wkr, Xomp_parse_wkr.Invk__exec);
		}

		// wait until wkrs are wkrs_done
		latch.Await();
		page_pool.Rls();

		// print stats
		Bry_bfr bfr = Bry_bfr_.New();
		for (int i = 0; i < wkr_len; ++i) {
			wkrs[i].Bld_stats(bfr);
		}
		Gfo_usr_dlg_.Instance.Note_many("", "", bfr.To_str_and_clear());
	}
}
