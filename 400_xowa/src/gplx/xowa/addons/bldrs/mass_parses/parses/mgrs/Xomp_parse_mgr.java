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
package gplx.xowa.addons.bldrs.mass_parses.parses.mgrs;
import gplx.frameworks.objects.Cancelable_;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.arrays.IntAryUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.*;
import gplx.xowa.addons.bldrs.mass_parses.parses.*;
import gplx.core.threads.*; import gplx.core.threads.utils.*;
import gplx.core.caches.*; import gplx.xowa.wikis.caches.*;
import gplx.xowa.addons.bldrs.mass_parses.parses.wkrs.*; import gplx.xowa.addons.bldrs.mass_parses.dbs.*; import gplx.xowa.addons.bldrs.mass_parses.parses.pools.*; import gplx.xowa.addons.bldrs.mass_parses.parses.utls.*;
import gplx.xowa.addons.bldrs.wmdumps.imglinks.*;
import gplx.xowa.addons.wikis.fulltexts.indexers.bldrs.*;
public class Xomp_parse_mgr {
	private Gfo_countdown_latch latch;
	public Xomp_parse_mgr_cfg Cfg() {return cfg;} private final Xomp_parse_mgr_cfg cfg = new Xomp_parse_mgr_cfg();
	public void Wkrs_done_add_1() {latch.Countdown();}
	public void Run(Xowe_wiki wiki) {
		// init db
		cfg.Init(wiki);
		Xomp_mgr_db mgr_db = Xomp_mgr_db.New__load(cfg.Mgr_url());

		// init page_pool
		Lru_cache_root.Instance.Del(wiki.Cache_mgr().Page_cache().Cache_key()); // delete defautl page_cache, b/c we will make one below
		Xomp_page_pool_loader page_pool_loader = new Xomp_page_pool_loader(wiki, mgr_db.Conn(), cfg.Num_pages_in_pool(), cfg.Show_msg__fetched_pool());
		Xomp_page_pool page_pool = new Xomp_page_pool(page_pool_loader, cfg.Num_pages_per_wkr());

		// cache: preload tmpls and imglinks
		Xow_page_cache page_cache = Xomp_tmpl_cache_bldr.New(wiki, cfg.Load_all_templates(), cfg.Page_cache_min(), cfg.Page_cache_max());
		wiki.App().User().User_db_mgr().Cache_mgr().Enabled_n_();	// disable db lookups of user cache

		Xomp_prog_mgr prog_mgr = new Xomp_prog_mgr();
		prog_mgr.Init(page_cache, page_pool_loader.Get_pending_count(), cfg.Progress_interval(), cfg.Perf_interval(), mgr_db.Url().GenNewNameAndExt("xomp.perf.csv"));

		Gfo_cache_mgr commons_cache = new Gfo_cache_mgr().Max_size_(IntUtl.MaxValue).Reduce_by_(IntUtl.MaxValue);
		Xow_ifexist_cache ifexist_cache = new Xow_ifexist_cache(wiki, page_cache).Cache_sizes_(IntUtl.MaxValue, IntUtl.MaxValue);
		if (cfg.Load_ifexists_ns() != null) Load_ifexists_ns(wiki, ifexist_cache, cfg.Load_ifexists_ns());

		Xof_orig_wkr__img_links file_orig_wkr = new Xof_orig_wkr__img_links(wiki);
		if (cfg.Load_all_imglinks()) Xof_orig_wkr__img_links_.Load_all(file_orig_wkr);

		wiki.Appe().Wiki_mgr().Wdata_mgr().Doc_mgr.Cache__init(cfg.Wbase_cache_mru_type(), cfg.Wbase_cache_mru_size(), cfg.Wbase_cache_mru_compress_size(), cfg.Wbase_cache_mru_weight());

		// load_wkr: init and start
		// Xomp_load_wkr load_wkr = new Xomp_load_wkr(wiki, db_mgr.Mgr_db().Conn(), cfg.Num_pages_in_pool(), cfg.Num_wkrs());
		// Thread_adp_.Start_by_key("xomp.load", Cancelable_.Never, load_wkr, Xomp_load_wkr.Invk__exec);
 
		// assert wkr_tbl
		Gfo_usr_dlg_.Instance.Prog_many("", "", "initing wkrs");
		int wkr_len = cfg.Num_wkrs();
		int wkr_uid_bgn = mgr_db.Tbl__wkr().Init_wkrs(cfg.Wkr_machine_name(), wkr_len);
		latch = new Gfo_countdown_latch(wkr_len);
		Xomp_parse_wkr[] wkrs = new Xomp_parse_wkr[wkr_len];

		// init ns_ord_mgr
		Xomp_ns_ord_mgr ns_ord_mgr = new Xomp_ns_ord_mgr(IntAryUtl.Parse(mgr_db.Tbl__cfg().Select_str("", Xomp_parse_wkr.Cfg__ns_ids), "|"));

		// init indexer
		Xofulltext_indexer_wkr indexer = cfg.Indexer_enabled() ? new Xofulltext_indexer_wkr() : null;
		if (indexer != null) indexer.Init(wiki, cfg.Indexer_opt());

		// init parse_wkrs
		for (int i = 0; i < wkr_len; ++i) {
			// make wiki
			Xowe_wiki wkr_wiki = Xow_wiki_utl_.Clone_wiki(wiki, wiki.Fsys_mgr().Root_dir());
			Lru_cache_root.Instance.Del(wkr_wiki.Cache_mgr().Page_cache().Cache_key());
			wkr_wiki.Cache_mgr().Page_cache_(page_cache).Commons_cache_(commons_cache).Ifexist_cache_(ifexist_cache);				


			// make wkr
			Xomp_parse_wkr wkr = new Xomp_parse_wkr(this, cfg, mgr_db, page_pool, prog_mgr, file_orig_wkr, ns_ord_mgr, wkr_wiki, indexer, i + wkr_uid_bgn);
			wkrs[i] = wkr;
		}

		// start threads; done separately from init b/c thread issues
		for (int i = 0; i < wkr_len; ++i) {
			Xomp_parse_wkr wkr = wkrs[i];
			Thread_adp_.Start_by_key("xomp." + IntUtl.ToStrFmt(i, "000"), Cancelable_.Never, wkr, Xomp_parse_wkr.Invk__exec);
		}

		// wait until wkrs are done
		latch.Await();
		page_pool.Rls();
		if (indexer != null) indexer.Term();
		wiki.Appe().Wiki_mgr().Wdata_mgr().Doc_mgr.Cleanup();

		// print stats
		BryWtr bfr = BryWtr.New();
		for (int i = 0; i < wkr_len; ++i) {
			wkrs[i].Bld_stats(bfr);
		}
		Gfo_usr_dlg_.Instance.Note_many("", "", bfr.ToStrAndClear());
	}
	private static void Load_ifexists_ns(Xow_wiki wiki, Xow_ifexist_cache cache, String ns_list) {
		// expand "*" to all
		if (StringUtl.Eq(ns_list, "*")) {
			BryWtr bfr = BryWtr.New();
			gplx.xowa.wikis.nss.Xow_ns_mgr ns_mgr = wiki.Ns_mgr();
			int len = ns_mgr.Ids_len();
			for (int i = 0; i < len; i++) {
				gplx.xowa.wikis.nss.Xow_ns ns = ns_mgr.Ids_get_at(i);
				if (ns.Id() >= 0) {	// skip Media / Special
					if (bfr.Len() != 0) bfr.AddByteComma();
					bfr.AddIntVariable(ns.Id());
				}
			}
			ns_list = bfr.ToStrAndClear();
		}
		// load all titles
		gplx.xowa.wikis.data.tbls.Xowd_page_tbl page_tbl = wiki.Data__core_mgr().Db__core().Tbl__page();
		String sql = gplx.dbs.Db_sql_.Make_by_fmt(StringUtl.Ary
		( "SELECT  {0}, {1}"
		, "FROM    {2}"
		, "WHERE   {0} IN ({3})"
		), page_tbl.Fld_page_ns(), page_tbl.Fld_page_title()
		, page_tbl.Tbl_name()
		, ns_list
		);
		gplx.dbs.Db_rdr rdr = page_tbl.Conn().Stmt_sql(sql).Exec_select__rls_auto();
		try {
			int counter = 0;
			while (rdr.Move_next()) {
				int ns = rdr.Read_int(page_tbl.Fld_page_ns());
				byte[] page_db = rdr.Read_bry_by_str(page_tbl.Fld_page_title());
				Xoa_ttl ttl = wiki.Ttl_parse(ns, page_db);
				cache.Add(ttl, true);
				if (counter % 100000 == 0) Gfo_usr_dlg_.Instance.Prog_many("", "", "loading ifexists: " + counter);
				counter++;
			}
		} finally {rdr.Rls();}
		
		// mark ns
		int[] ns_ids = IntAryUtl.Parse(ns_list, ",");
		cache.Mark_ns_loaded(ns_ids);
	}
}
