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
package gplx.xowa.addons.bldrs.mass_parses.parses.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*; import gplx.xowa.addons.bldrs.mass_parses.parses.*;
import gplx.core.threads.*; import gplx.core.threads.utils.*;
import gplx.core.caches.*; import gplx.xowa.wikis.caches.*;
import gplx.xowa.addons.bldrs.mass_parses.parses.wkrs.*; import gplx.xowa.addons.bldrs.mass_parses.dbs.*; import gplx.xowa.addons.bldrs.mass_parses.parses.pools.*; import gplx.xowa.addons.bldrs.mass_parses.parses.utls.*;
import gplx.xowa.addons.bldrs.wmdumps.imglinks.*;
public class Xomp_parse_mgr {
	private Gfo_countdown_latch latch;
	public Xomp_parse_mgr_cfg Cfg() {return cfg;} private final    Xomp_parse_mgr_cfg cfg = new Xomp_parse_mgr_cfg();		
	public void Wkrs_done_add_1() {latch.Countdown();}
	public void Run(Xowe_wiki wiki) {
		// init db
		cfg.Init(wiki);
		Xomp_mgr_db mgr_db = Xomp_mgr_db.New__load(cfg.Mgr_url());

		// init page_pool
		Xomp_page_pool_loader page_pool_loader = new Xomp_page_pool_loader(wiki, mgr_db.Conn(), cfg.Num_pages_in_pool(), cfg.Show_msg__fetched_pool());
		Xomp_page_pool page_pool = new Xomp_page_pool(page_pool_loader, cfg.Num_pages_per_wkr());
		Xomp_prog_mgr prog_mgr = new Xomp_prog_mgr();
		prog_mgr.Init(page_pool_loader.Get_pending_count(), cfg.Progress_interval());

		// cache: disable general settings
		wiki.App().User().User_db_mgr().Cache_mgr().Enabled_n_();	// disable db lookups of cache
		Gfo_cache_mgr commons_cache = new Gfo_cache_mgr().Max_size_(Int_.Max_value).Reduce_by_(Int_.Max_value);
		Gfo_cache_mgr ifexist_cache = new Gfo_cache_mgr().Max_size_(Int_.Max_value).Reduce_by_(Int_.Max_value);

		// cache: preload tmpls and imglinks
		Xow_page_cache page_cache = Xomp_tmpl_cache_bldr.New(wiki, cfg.Load_all_templates());
		Xof_orig_wkr__img_links file_orig_wkr = new Xof_orig_wkr__img_links(wiki);
		if (cfg.Load_all_imglnks()) Xof_orig_wkr__img_links_.Load_all(file_orig_wkr);

		// load_wkr: init and start
		// Xomp_load_wkr load_wkr = new Xomp_load_wkr(wiki, db_mgr.Mgr_db().Conn(), cfg.Num_pages_in_pool(), cfg.Num_wkrs());
		// Thread_adp_.Start_by_key("xomp.load", Cancelable_.Never, load_wkr, Xomp_load_wkr.Invk__exec);
 
		// assert wkr_tbl
		int wkr_len = cfg.Num_wkrs();
		int wkr_uid_bgn = mgr_db.Tbl__wkr().Init_wkrs(cfg.Wkr_machine_name(), wkr_len);
		latch = new Gfo_countdown_latch(wkr_len);
		Xomp_parse_wkr[] wkrs = new Xomp_parse_wkr[wkr_len];

		// init ns_ord_mgr
		Xomp_ns_ord_mgr ns_ord_mgr = new Xomp_ns_ord_mgr(Int_.Ary_parse(mgr_db.Tbl__cfg().Select_str("", Xomp_parse_wkr.Cfg__ns_ids), "|"));

		// init parse_wkrs
		for (int i = 0; i < wkr_len; ++i) {
			// make wiki
			Xowe_wiki wkr_wiki = Xow_wiki_utl_.Clone_wiki(wiki, wiki.Fsys_mgr().Root_dir());
			wkr_wiki.Cache_mgr().Page_cache_(page_cache).Commons_cache_(commons_cache).Ifexist_cache_(ifexist_cache);				

			// make wkr
			Xomp_parse_wkr wkr = new Xomp_parse_wkr(this, cfg, mgr_db, page_pool, prog_mgr, file_orig_wkr, ns_ord_mgr, wkr_wiki, i + wkr_uid_bgn);
			wkrs[i] = wkr;
		}

		// start threads; done separately from init b/c thread issues
		for (int i = 0; i < wkr_len; ++i) {
			Xomp_parse_wkr wkr = wkrs[i];
			Thread_adp_.Start_by_key("xomp." + Int_.To_str_fmt(i, "000"), Cancelable_.Never, wkr, Xomp_parse_wkr.Invk__exec);
		}

		// wait until wkrs are done
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
