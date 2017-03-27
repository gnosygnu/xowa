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
package gplx.xowa.addons.wikis.fulltexts.searchers.svcs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*; import gplx.xowa.addons.wikis.fulltexts.searchers.*;
import gplx.core.btries.*;
import gplx.langs.jsons.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.guis.cbks.*;
import gplx.xowa.addons.apps.cfgs.*;
import gplx.xowa.addons.wikis.fulltexts.searchers.specials.*;
import gplx.xowa.addons.wikis.fulltexts.searchers.caches.*;
import gplx.xowa.addons.wikis.searchs.searchers.crts.*;
import gplx.xowa.addons.wikis.searchs.searchers.crts.visitors.*;
import gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.*;
import gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.uis.*;
import gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.gflucenes.*;
import gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.brutes.*;
class Xofulltext_searcher_svc implements Gfo_invk {
	private final    Xoa_app app;
	private final    Ordered_hash wkr_hash = Ordered_hash_.New();
	public Xofulltext_searcher_svc(Xoa_app app) {
		this.app = app;
	}
	public void Search_cxl(Json_nde args) {this.Search_cxl(args.Get_as_str("page_guid"));}
	private void Search_cxl(String page_guid) {
		Xofulltext_args_qry prv_args = (Xofulltext_args_qry)wkr_hash.Get_by(page_guid);
		if (prv_args != null) {
			prv_args.Cancel();
			synchronized (wkr_hash) {
				wkr_hash.Del(page_guid);
			}
		}
	}
	public void Options_save(Json_nde args) {
		Xocfg_mgr cfg_mgr = app.Cfg();
		cfg_mgr.Set_bool_app("xowa.addon.fulltext_search.options.expand_options", args.Get_as_bool_or("expand_options", false));
	}
	public void Search_run(Json_nde args) {
		// get search_args
		Xofulltext_args_qry search_args = Xofulltext_args_qry.New_by_json(args);
		search_args.cache_mgr = this.Cache_mgr();

		// cancel any existing searches
		this.Search_cxl(search_args.page_guid);
		Compress_cache(wkr_hash);
		synchronized (wkr_hash) {
			wkr_hash.Add(search_args.page_guid, search_args);
		}
		
		// launch thread
		gplx.core.threads.Thread_adp_.Start_by_val("search", Cancelable_.Never, this, Invk__search, search_args);
	}
	private void Search(Xofulltext_args_qry args) {
		// create ui
		Xofulltext_cache_mgr cache_mgr = args.cache_mgr;
		Xofulltext_searcher_ui ui = new Xofulltext_searcher_ui(cache_mgr, app.Gui__cbk_mgr(), new Xog_cbk_trg(args.page_guid));

		try {
			// loop wikis
			for (Xofulltext_args_wiki wiki_args : args.wikis_ary) {
				Search_wiki(args, cache_mgr, ui, wiki_args);
			}
		} catch (Exception exc) {
			if (app.Tid_is_edit())
				((Xoae_app)app).Gui_mgr().Kit().Ask_ok("", "", Err_.Message_gplx_full(exc));
		}
	}
	private void Search_wiki(Xofulltext_args_qry args, Xofulltext_cache_mgr cache_mgr, Xofulltext_searcher_ui ui, Xofulltext_args_wiki wiki_args) {
		try {
			// get wiki and notify
			byte[] wiki_domain = wiki_args.wiki;
			Xow_wiki wiki = app.Wiki_mgri().Get_by_or_make_init_y(wiki_domain);
			Xofulltext_searcher searcher = Get_searcher(wiki);
			ui.Send_wiki_add(searcher.Type_is_lucene(), wiki_args.expand_pages, wiki_domain, wiki_args.bgn, wiki_args.end());

			// try to get from cache
			byte[] qry_key = args.Qry_key(wiki_domain, wiki_args.ns_ids);
			int qry_id = cache_mgr.Ids__get_or_neg1(qry_key);
			Xofulltext_cache_qry qry = null;
			if (qry_id == -1) {
				qry_id = cache_mgr.Ids__next();
				cache_mgr.Add(qry_id, qry_key);
				qry = cache_mgr.Get_or_null(qry_id);
			}
			else {
				qry = cache_mgr.Get_or_null(qry_id);
				if (qry != null) {
					boolean all_shown = Display_cached_qry(args, ui, wiki, qry, qry_id, wiki_args);
					if (all_shown || qry.done) {
						ui.Send_done();
						return;
					}
				}
			}
			args.qry_id = qry_id;

			searcher.Search(ui, wiki, qry, args, wiki_args);
			ui.Send_done();
		}
		catch (Exception exc) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to search_wiki; err=~{0}", Err_.Message_gplx_log(exc));
		}
	}
	private boolean Display_cached_qry(Xofulltext_args_qry args, Xofulltext_searcher_ui ui, Xow_wiki wiki, Xofulltext_cache_qry qry, int qry_id, Xofulltext_args_wiki wiki_args) {
		int bgn = wiki_args.bgn;
		int len = wiki_args.len;
		int end = bgn + len;
		int max = qry.Pages().Len();
		for (int i = bgn; i < end; i++) {
			if (i >= max) return false; // more pages requested than available
			Xofulltext_cache_page page = (Xofulltext_cache_page)qry.Pages().Get_at(i);
			ui.Send_page_add(new Xofulltext_searcher_page(qry_id, wiki.Domain_bry(), page.Page_id(), page.Page_ttl(), wiki_args.expand_snips));

			// loop lines
			int lines_len = page.Lines().Len();
			for (int j = 0; j < lines_len; j++) {
				Xofulltext_cache_line line = (Xofulltext_cache_line)page.Lines().Get_at(j);
				ui.Send_line_add(false, wiki_args.show_all_snips, qry_id, wiki.Domain_bry(), page.Page_id(), line.Line_seq(), line.Line_html());
			}
		}
		return true;
	}

	public void Snips_show_all(Json_nde args) {
		Snips_show_all(args.Get_as_int("qry_id"), args.Get_as_bry("wiki"), args.Get_as_int("page_id"), args.Get_as_str("page_guid"));
	}
	private void Snips_show_all(int qry_id, byte[] wiki_bry, int page_id, String page_guid) {
		Xofulltext_cache_mgr cache_mgr = this.Cache_mgr();
		Xofulltext_searcher_ui searcher_ui = new Xofulltext_searcher_ui(cache_mgr, app.Gui__cbk_mgr(), new Xog_cbk_trg(page_guid));

		Xofulltext_cache_line[] lines = cache_mgr.Get_lines_rest(qry_id, wiki_bry, page_id);
		for (Xofulltext_cache_line line : lines) {
			searcher_ui.Send_line_add(false, true, qry_id, wiki_bry, page_id, line.Line_seq(), line.Line_html());
		}
	}
	private Xofulltext_searcher Get_searcher(Xow_wiki wiki) {
		if (Io_mgr.Instance.ExistsDir(Xosearch_fulltext_addon.Get_index_dir(wiki))) {
			return new Xofulltext_searcher__lucene();
		}
		else {
			return new Xofulltext_searcher__brute();
		}
	}
	private Xofulltext_cache_mgr Cache_mgr() {
		return Xosearch_fulltext_addon.Get_by_app(app).Cache_mgr();
	}
	private static void Compress_cache(Ordered_hash wkr_hash) {
		int max = 8; // cache no more than 8 tabs worth of queries
		int len = wkr_hash.Len();
		if (len > max) {
			synchronized (wkr_hash) {
				// create list for deleted items; in general, this list will never be more than 1
				List_adp deleted = List_adp_.New();

				int bgn = len - max;
				for (int i = 0; i < bgn; i++) {
					Xofulltext_args_qry args = (Xofulltext_args_qry)wkr_hash.Get_at(i);
					deleted.Add(args);
				}

				len = deleted.Len();
				for (int i = 0; i < len; i++) {
					Xofulltext_args_qry args = (Xofulltext_args_qry)deleted.Get_at(i);
					wkr_hash.Del(args.page_guid);
				}
			}
		}
	}

	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__search))           this.Search((Xofulltext_args_qry)m.ReadObj("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}   private static final String Invk__search = "search";
}
