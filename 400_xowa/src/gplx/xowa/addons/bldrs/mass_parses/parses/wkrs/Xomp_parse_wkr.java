/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.addons.bldrs.mass_parses.parses.wkrs;

import gplx.Bry_bfr;
import gplx.objects.strings.AsciiByte;
import gplx.Err_;
import gplx.GfoMsg;
import gplx.Gfo_invk;
import gplx.Gfo_invk_;
import gplx.Gfo_usr_dlg_;
import gplx.GfsCtx;
import gplx.List_adp;
import gplx.List_adp_;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.addons.bldrs.mass_parses.dbs.Xomp_mgr_db;
import gplx.xowa.addons.bldrs.mass_parses.dbs.Xomp_stat_tbl;
import gplx.xowa.addons.bldrs.mass_parses.dbs.Xomp_wkr_db;
import gplx.xowa.addons.bldrs.mass_parses.dbs.Xomp_wkr_tbl;
import gplx.xowa.addons.bldrs.mass_parses.parses.mgrs.Xomp_parse_mgr;
import gplx.xowa.addons.bldrs.mass_parses.parses.mgrs.Xomp_parse_mgr_cfg;
import gplx.xowa.addons.bldrs.mass_parses.parses.mgrs.Xomp_prog_mgr;
import gplx.xowa.addons.bldrs.mass_parses.parses.pools.Xomp_page_itm;
import gplx.xowa.addons.bldrs.mass_parses.parses.pools.Xomp_page_pool;
import gplx.xowa.addons.bldrs.mass_parses.parses.utls.Xomp_lnki_temp_wkr;
import gplx.xowa.addons.bldrs.mass_parses.parses.utls.Xomp_ns_ord_mgr;
import gplx.xowa.addons.wikis.fulltexts.indexers.bldrs.Xofulltext_indexer_wkr;
import gplx.xowa.files.origs.Xof_orig_wkr;
import gplx.xowa.htmls.core.bldrs.Xob_hdump_bldr;
import gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx;
import gplx.xowa.htmls.core.hzips.Xoh_hzip_dict_;
import gplx.xowa.htmls.hxtns.pages.Hxtn_page_mgr;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.parsers.Xow_parser_mgr;
import gplx.xowa.parsers.logs.Xop_log_wkr_factory;
import gplx.xowa.wikis.pages.Xopg_view_mode_;

public class Xomp_parse_wkr implements Gfo_invk {
	// mgr vars
	private final Xomp_parse_mgr mgr;
	private final Xomp_mgr_db mgr_db;
	private final Xomp_prog_mgr prog_mgr;
	private final Xomp_page_pool page_pool;
	private final Xof_orig_wkr file_orig_wkr;
	private final Xomp_ns_ord_mgr ns_ord_mgr;

	// cfg vars
	private final Xomp_parse_mgr_cfg cfg;
	private int cleanup_interval, commit_interval;
	private boolean log_file_lnkis;

	// wkr vars
	private final Xowe_wiki wiki;
	private final Xob_hdump_bldr hdump_bldr = new Xob_hdump_bldr();
	private final int uid;
	private Xomp_wkr_db wkr_db;
	private Xomp_stat_tbl stat_tbl;
	private Hxtn_page_mgr hxtn_mgr;

	// indexer vars
	private final Xofulltext_indexer_wkr indexer;

	private final List_adp list = List_adp_.New(); private int list_idx = 0, list_len = 0;		
	private int done_count; private long done_time;
	public Xomp_parse_wkr(Xomp_parse_mgr mgr, Xomp_parse_mgr_cfg cfg
		, Xomp_mgr_db mgr_db, Xomp_page_pool page_pool
		, Xomp_prog_mgr prog_mgr, Xof_orig_wkr file_orig_wkr, Xomp_ns_ord_mgr ns_ord_mgr
		, Xowe_wiki wiki, Xofulltext_indexer_wkr indexer, int uid) {
		// mgr vars
		this.mgr = mgr; this.mgr_db = mgr_db;
		this.page_pool = page_pool; this.prog_mgr = prog_mgr; this.file_orig_wkr = file_orig_wkr;
		this.ns_ord_mgr = ns_ord_mgr;
		this.indexer = indexer;

		// cfg vars
		this.cfg = cfg;
		this.cleanup_interval = cfg.Cleanup_interval();
		this.commit_interval = cfg.Commit_interval();
		this.log_file_lnkis = cfg.Log_file_lnkis();

		// wkr-specific vars
		this.wiki = wiki; this.uid = uid;
		this.wkr_db = Xomp_wkr_db.New(Xomp_mgr_db.New__url(wiki), uid);
		this.stat_tbl = new Xomp_stat_tbl(wkr_db.Conn());
		this.hxtn_mgr = wiki.Hxtn_mgr();
		this.hxtn_mgr.Init_by_xomp_wkr(wkr_db.Conn(), cfg.Zip_tid());
	}
	public void Exec() {
		Xow_parser_mgr parser_mgr = wiki.Parser_mgr();

		// disable file download
		wiki.File_mgr().Init_file_mgr_by_load(wiki);											// must happen after fsdb.make
		wiki.File__bin_mgr().Wkrs__del(gplx.xowa.files.bins.Xof_bin_wkr_.Key_http_wmf);			// must happen after init_file_mgr_by_load; remove wmf wkr, else will try to download images during parsing
		wiki.File__orig_mgr().Wkrs__set(file_orig_wkr);
		wiki.File_mgr().Fsdb_mode().Tid__v2__mp__y_();

		// enable disable categories according to flag
		wiki.Html_mgr().Page_wtr_mgr().Wkr(Xopg_view_mode_.Tid__read).Ctgs_enabled_(cfg.Hdump_catboxs());

		// enable lnki_temp
		Xomp_lnki_temp_wkr logger = null;
		if (log_file_lnkis) {
			logger = new Xomp_lnki_temp_wkr(wiki, wkr_db.Conn());
			parser_mgr.Ctx().Lnki().File_logger_(logger);
			logger.Bgn();
		}

		// init log_mgr / property_wkr / stats
		Xop_log_wkr_factory wkr_factory = new Xop_log_wkr_factory(wkr_db.Conn());
		if (cfg.Log_math()) wiki.Parser_mgr().Math__core().Log_wkr_(wkr_factory);

		// enable hdump			
		hdump_bldr.Enabled_(cfg.Hdump_enabled()).Hzip_enabled_(cfg.Hzip_enabled()).Hzip_diff_(cfg.Hdiff_enabled()).Zip_tid_(cfg.Zip_tid());
		hdump_bldr.Init(wiki, wkr_db.Conn(), new Xob_hdump_tbl_retriever__xomp(wkr_db.Html_tbl()));
		wkr_db.Conn().Txn_bgn("xomp");
		stat_tbl.Stmt_new();
		hxtn_mgr.Insert_bgn(false);
		Xoh_wtr_ctx hctx = Xoh_wtr_ctx.Hdump_by_hzip_tid(cfg.Hzip_enabled() ? Xoh_hzip_dict_.Hdb__hzip : Xoh_hzip_dict_.Hdb__htxt); // ISSUE#:553; DATE:2019-09-25

		// set status to running
		mgr_db.Tbl__wkr().Update_status(uid, Xomp_wkr_tbl.Status__running);

		// main loop
		int prv_ns = -1;
		while (true) {
			// get page from page pool
			Xomp_page_itm ppg = Get_next();
			if (ppg == Xomp_page_itm.Null) {
				mgr_db.Tbl__wkr().Update_status(uid, Xomp_wkr_tbl.Status__sleeping);
				break;	// no more pages
			}
			if (ppg.Text() == null) continue; // some pages have no text; ignore them else null ref; PAGE: it.d:miercuri DATE:2015-12-05
			
			try {
				long done_bgn = gplx.core.envs.System_.Ticks();

				// get ns / ttl
				int cur_ns = ppg.Ns_id();
				Xoa_ttl ttl = wiki.Ttl_parse(cur_ns, ppg.Ttl_bry());
				// if ns changed and prv_ns is main
				if (cur_ns != prv_ns) {
					prv_ns = cur_ns;
				}

				// init page
				Xoae_page wpg = Xoae_page.New(wiki, ttl);
				wpg.Bldr__ns_ord_(ns_ord_mgr.Get_ord_by_ns_id(cur_ns));	// NOTE: must set ns_id for tier_id in lnki_temp; DATE:2016-09-19
				wpg.Db().Text().Text_bry_(ppg.Text());
				wpg.Db().Page().Init_by_mp(ppg.Id(), ppg.Page_score());
				wpg.Stat_itm().Init(uid);

				// parse page
				Xop_ctx pctx = parser_mgr.Ctx();
				pctx.Clear_all();
				parser_mgr.Parse(wpg, true);

				// gen_html
				hdump_bldr.Insert(pctx, wpg, hctx);

				// index
				long fulltext_time = 0;
				if (indexer != null) {
					fulltext_time = gplx.core.envs.System_.Ticks();
					indexer.Index(wpg);
					fulltext_time = gplx.core.envs.System_.Ticks__elapsed_in_frac(fulltext_time);
				}

				// mark done for sake of progress
				prog_mgr.Mark_done(ppg.Id());

				// update stats
				long time_cur = gplx.core.envs.System_.Ticks();
				long page_time = time_cur - done_bgn;
				done_time += page_time;
				++done_count;
				stat_tbl.Insert(wpg, hdump_bldr.Tmp_hpg(), uid, page_time, fulltext_time);

				// cleanup
				// ctx.App().Utl__bfr_mkr().Clear_fail_check();	// make sure all bfrs are released
				if (done_count % cleanup_interval == 0) {
					wiki.Cache_mgr().Free_mem__page();
					wiki.Parser_mgr().Scrib().Core_term();
					wiki.Appe().Wiki_mgr().Wdata_mgr().Clear();
				}
				if (done_count % commit_interval == 0) {
					wkr_db.Conn().Txn_sav();
				}
			} catch (Exception e) {
				Gfo_usr_dlg_.Instance.Warn_many("", "", "mass_parse.fail:ns=~{0} ttl=~{1} err=~{2}", ppg.Ns_id(), ppg.Ttl_bry(), Err_.Message_gplx_log(e));
			}
		}

		// cleanup
		try {
			if (logger != null) logger.End();
			wkr_db.Conn().Txn_end();
			wkr_db.Conn().Rls_conn();
			stat_tbl.Stmt_rls();
			hxtn_mgr.Insert_end(false);
			mgr.Wkrs_done_add_1();		// NOTE: must release latch last else thread errors
		}
		catch (Exception e) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "mass_parse.fail_end; err=~{0}", Err_.Message_gplx_log(e));
		}
	}
	public void Bld_stats(Bry_bfr bfr) {
		int done_time_in_sec = (int)(done_time / 1000); if (done_time_in_sec == 0) done_time_in_sec = 1;
		bfr.Add_int_pad_bgn(AsciiByte.Space, 4, uid		);
		bfr.Add_int_pad_bgn(AsciiByte.Space, 8, (int)(done_count / done_time_in_sec));
		bfr.Add_int_pad_bgn(AsciiByte.Space, 8, done_count);
		bfr.Add_int_pad_bgn(AsciiByte.Space, 8, done_time_in_sec);
		bfr.Add_byte_nl();
	}
	private Xomp_page_itm Get_next() {
		if (list_idx == list_len) {
			mgr_db.Tbl__wkr().Update_exec(uid, done_count, done_time);
			list.Clear();
			page_pool.Get_next(mgr_db, cfg.Wkr_machine_name(), list);
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
	public static final String Cfg__ns_ids = "xomp.ns_ids";
}
