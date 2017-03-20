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
	public Xofulltext_searcher_svc(Xoa_app app) {
		this.app = app;
	}
	public void Search(Json_nde args) {
		// get search_args
		Xofulltext_args_qry search_args = Xofulltext_args_qry.New_by_json(args);
		search_args.cache_mgr = this.Cache_mgr();
		
		// autosave any changes if enabled
		Xocfg_mgr cfg_mgr = app.Cfg();
		if (cfg_mgr.Get_bool_app_or("xowa.addon.search.fulltext.options.autosave_enabled", true)) {
			cfg_mgr.Set_bool_app("xowa.addon.search.fulltext.special.case_match", search_args.case_match);
			cfg_mgr.Set_bool_app("xowa.addon.search.fulltext.special.auto_wildcard_bgn", search_args.auto_wildcard_bgn);
			cfg_mgr.Set_bool_app("xowa.addon.search.fulltext.special.auto_wildcard_end", search_args.auto_wildcard_end);
			cfg_mgr.Set_bool_app("xowa.addon.search.fulltext.special.expand_matches_section", search_args.expand_matches_section);
			cfg_mgr.Set_bool_app("xowa.addon.search.fulltext.special.show_all_matches", search_args.show_all_matches);
//				cfg_mgr.Set_int_app ("xowa.addon.search.fulltext.special.max_pages_per_wiki", search_args.max_pages_per_wiki);
//				cfg_mgr.Set_bry_app ("xowa.addon.search.fulltext.special.namespaces", search_args.namespaces);
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
			ui.Send_wiki_add(wiki_domain);

			// try to get from cache
			byte[] qry_key = args.Qry_key(wiki_domain, wiki_args.ns_ids);
			int qry_id = cache_mgr.Ids__get_or_neg1(qry_key);
			if (qry_id == -1) {
				qry_id = cache_mgr.Ids__next();
				cache_mgr.Add(qry_id, qry_key);
			}
			else {
				Xofulltext_cache_page[] cached_pages = cache_mgr.Get_pages_rng(qry_id, wiki_args.offset, wiki_args.limit);
				if (cached_pages != null) {
					for (Xofulltext_cache_page page : cached_pages) {
						ui.Send_page_add(new Xofulltext_searcher_page(qry_id, wiki.Domain_bry(), page.Page_id(), page.Page_ttl(), args.expand_matches_section));
						int len = page.Lines().Len();
						for (int i = 0; i < len; i++) {
							Xofulltext_cache_line line = (Xofulltext_cache_line)page.Lines().Get_at(i);
							ui.Send_line_add(args.show_all_matches, qry_id, wiki.Domain_bry(), page.Page_id(), line.Line_seq(), line.Line_html());
						}
					}
					return;
				}
			}
			args.qry_id = qry_id;

			// do search
			Xofulltext_searcher searcher = Get_searcher(wiki);
			searcher.Search(ui, wiki, args, wiki_args);
		}
		catch (Exception exc) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to search_wiki; err=~{0}", Err_.Message_gplx_log(exc));
		}
	}

	public void Get_lines_rest(Json_nde args) {
		Get_lines_rest(args.Get_as_int("qry_id"), args.Get_as_bry("wiki"), args.Get_as_int("page_id"), args.Get_as_str("page_guid"));
	}
	private void Get_lines_rest(int qry_id, byte[] wiki_bry, int page_id, String page_guid) {
		Xofulltext_cache_mgr cache_mgr = this.Cache_mgr();
		Xofulltext_searcher_ui searcher_ui = new Xofulltext_searcher_ui(cache_mgr, app.Gui__cbk_mgr(), new Xog_cbk_trg(page_guid));

		Xofulltext_cache_line[] lines = cache_mgr.Get_lines_rest(qry_id, wiki_bry, page_id);
		for (Xofulltext_cache_line line : lines) {
			searcher_ui.Send_line_add(true, qry_id, wiki_bry, page_id, line.Line_seq(), line.Line_html());
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

	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__search))           this.Search((Xofulltext_args_qry)m.ReadObj("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}   private static final String Invk__search = "search";
}
