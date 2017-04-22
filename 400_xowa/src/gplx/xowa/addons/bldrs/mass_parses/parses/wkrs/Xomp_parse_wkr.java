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
package gplx.xowa.addons.bldrs.mass_parses.parses.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*; import gplx.xowa.addons.bldrs.mass_parses.parses.*;
import gplx.dbs.*; import gplx.xowa.addons.bldrs.mass_parses.dbs.*;
import gplx.xowa.files.origs.*;
import gplx.xowa.htmls.core.bldrs.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.logs.*;
import gplx.xowa.addons.bldrs.mass_parses.parses.mgrs.*; import gplx.xowa.addons.bldrs.mass_parses.parses.utls.*; import gplx.xowa.addons.bldrs.mass_parses.parses.*; import gplx.xowa.addons.bldrs.mass_parses.parses.pools.*;
import gplx.xowa.addons.wikis.fulltexts.indexers.bldrs.*;
public class Xomp_parse_wkr implements Gfo_invk {
	// mgr vars
	private final    Xomp_parse_mgr mgr;
	private final    Xomp_mgr_db mgr_db;
	private final    Xomp_prog_mgr prog_mgr;
	private final    Xomp_page_pool page_pool;
	private final    Xof_orig_wkr file_orig_wkr;
	private final    Xomp_ns_ord_mgr ns_ord_mgr;

	// cfg vars
	private final    Xomp_parse_mgr_cfg cfg;
	private int cleanup_interval, commit_interval;
	private boolean log_file_lnkis;

	// wkr vars
	private final    Xowe_wiki wiki;
	private final    Xob_hdump_bldr hdump_bldr = new Xob_hdump_bldr();
	private final    int uid;
	private Xomp_wkr_db wkr_db;

	private final    Xofulltext_indexer_wkr indexer;

	private final    List_adp list = List_adp_.New(); private int list_idx = 0, list_len = 0;		
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
	}
	public void Exec() {
		// init
		Xow_parser_mgr parser_mgr = wiki.Parser_mgr();

		// disable file download
		wiki.File_mgr().Init_file_mgr_by_load(wiki);											// must happen after fsdb.make
		wiki.File__bin_mgr().Wkrs__del(gplx.xowa.files.bins.Xof_bin_wkr_.Key_http_wmf);			// must happen after init_file_mgr_by_load; remove wmf wkr, else will try to download images during parsing
		wiki.File__orig_mgr().Wkrs__set(file_orig_wkr);
		wiki.File_mgr().Fsdb_mode().Tid__v2__mp__y_();

		// enable disable categories according to flag
		wiki.Html_mgr().Page_wtr_mgr().Wkr(gplx.xowa.wikis.pages.Xopg_page_.Tid_read).Ctgs_enabled_(cfg.Hdump_catboxs());

		// enable lnki_temp
		Xomp_lnki_temp_wkr logger = null;
		if (log_file_lnkis) {
			logger = new Xomp_lnki_temp_wkr(wiki, wkr_db.Conn());
			parser_mgr.Ctx().Lnki().File_logger_(logger);
			logger.Bgn();
		}

		// init log_mgr / property_wkr
		Xop_log_wkr_factory wkr_factory = new Xop_log_wkr_factory(wkr_db.Conn());
		if (cfg.Log_math()) wiki.Parser_mgr().Math__core().Log_wkr_(wkr_factory);

		// enable hdump			
		hdump_bldr.Enabled_(cfg.Hdump_enabled()).Hzip_enabled_(cfg.Hzip_enabled()).Hzip_diff_(cfg.Hdiff_enabled()).Zip_tid_(cfg.Zip_tid());
		hdump_bldr.Init(wiki, wkr_db.Conn(), new Xob_hdump_tbl_retriever__xomp(wkr_db.Html_tbl()));
		wkr_db.Conn().Txn_bgn("xomp");

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
				// init page
				long done_bgn = gplx.core.envs.System_.Ticks();
				int cur_ns = ppg.Ns_id();
				Xoa_ttl ttl = wiki.Ttl_parse(cur_ns, ppg.Ttl_bry());
				// if ns changed and prv_ns is main
				if (cur_ns != prv_ns) {
					if (prv_ns == gplx.xowa.wikis.nss.Xow_ns_.Tid__main)
						wiki.Cache_mgr().Free_mem__all();	// NOTE: clears page and wbase cache only; needed else OutOfMemory error for en.w in 25th hour; DATE:2017-01-11
					prv_ns = cur_ns;
				}
				Xoae_page wpg = Xoae_page.New(wiki, ttl);
				wpg.Bldr__ns_ord_(ns_ord_mgr.Get_ord_by_ns_id(cur_ns));	// NOTE: must set ns_id for tier_id in lnki_temp; DATE:2016-09-19
				wpg.Db().Text().Text_bry_(ppg.Text());
				wpg.Db().Page().Init_by_mp(ppg.Id(), ppg.Page_score());

				// parse page
				Xop_ctx pctx = parser_mgr.Ctx();
				pctx.Clear_all();
				parser_mgr.Parse(wpg, true);

				// gen_html
				hdump_bldr.Insert(pctx, wpg);

				// index
				if (indexer != null) indexer.Index(wpg);

				// mark done for sake of progress
				prog_mgr.Mark_done(ppg.Id());

				// update stats
				long time_cur = gplx.core.envs.System_.Ticks();
				done_time += time_cur - done_bgn;
				done_bgn = time_cur;
				++done_count;

				// cleanup
				// ctx.App().Utl__bfr_mkr().Clear_fail_check();	// make sure all bfrs are released
				if (wiki.Cache_mgr().Tmpl_result_cache().Count() > 50000) 
					wiki.Cache_mgr().Tmpl_result_cache().Clear();
				if (done_count % cleanup_interval == 0) {
					wiki.Cache_mgr().Free_mem__page();
					wiki.Parser_mgr().Scrib().Core_term();
					wiki.Appe().Wiki_mgr().Wdata_mgr().Clear();
				}
				if (done_count % commit_interval == 0)
					wkr_db.Conn().Txn_sav();
			} catch (Exception e) {
				Gfo_usr_dlg_.Instance.Warn_many("", "", "mass_parse.fail:ns=~{0} ttl=~{1} err=~{2}", ppg.Ns_id(), ppg.Ttl_bry(), Err_.Message_gplx_log(e));
			}
		}

		// cleanup
		if (logger != null) logger.End();
		wkr_db.Conn().Txn_end();
		wkr_db.Conn().Rls_conn();
		mgr.Wkrs_done_add_1();		// NOTE: must release latch last else thread errors
	}
	public void Bld_stats(Bry_bfr bfr) {
		int done_time_in_sec = (int)(done_time / 1000); if (done_time_in_sec == 0) done_time_in_sec = 1;
		bfr.Add_int_pad_bgn(Byte_ascii.Space, 4, uid		);
		bfr.Add_int_pad_bgn(Byte_ascii.Space, 8, (int)(done_count / done_time_in_sec));
		bfr.Add_int_pad_bgn(Byte_ascii.Space, 8, done_count);
		bfr.Add_int_pad_bgn(Byte_ascii.Space, 8, done_time_in_sec);
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
