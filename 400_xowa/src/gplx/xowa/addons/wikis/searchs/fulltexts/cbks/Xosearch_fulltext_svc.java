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
package gplx.xowa.addons.wikis.searchs.fulltexts.cbks; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.fulltexts.*;
import gplx.core.btries.*;
import gplx.langs.jsons.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.addons.wikis.searchs.fulltexts.specials.*;
import gplx.xowa.addons.wikis.searchs.fulltexts.finders.*;
import gplx.xowa.addons.wikis.searchs.fulltexts.caches.*;
import gplx.xowa.addons.wikis.searchs.searchers.crts.*;
import gplx.xowa.addons.wikis.searchs.searchers.crts.visitors.*;
import gplx.xowa.addons.apps.cfgs.*;
class Xosearch_fulltext_svc implements Gfo_invk {
	private final    Xoa_app app;
	private final    gplx.xowa.guis.cbks.Xog_cbk_trg cbk_trg = gplx.xowa.guis.cbks.Xog_cbk_trg.New(Xosearch_fulltext_special.Prototype.Special__meta().Ttl_bry());
	private final    Xosearch_finder_mgr finder = new Xosearch_finder_mgr();
	private final    Xosearch_finder_cbk__eval cbk_eval = new Xosearch_finder_cbk__eval();
	private final    Xosearch_finder_cbk__highlight cbk_highlight;
	private final    Xosearch_cache_mgr cache_mgr = new Xosearch_cache_mgr();
	public Xosearch_fulltext_svc(Xoa_app app) {
		this.app = app;
		cbk_highlight = new Xosearch_finder_cbk__highlight(app, cbk_trg, cache_mgr);
	}
	public void Search(Json_nde args) {
		cache_mgr.Clear();

		Xocfg_mgr cfg_mgr = app.Cfg();
		Xosearch_search_args thread_args = Xosearch_search_args.New_by_json(args);
		
		if (cfg_mgr.Get_bool_app_or("xowa.addon.search.fulltext.options.autosave_enabled", true)) {
			cfg_mgr.Set_bool_app("xowa.addon.search.fulltext.special.case_match", thread_args.case_match);
			cfg_mgr.Set_bool_app("xowa.addon.search.fulltext.special.auto_wildcard_bgn", thread_args.auto_wildcard_bgn);
			cfg_mgr.Set_bool_app("xowa.addon.search.fulltext.special.auto_wildcard_end", thread_args.auto_wildcard_end);
			cfg_mgr.Set_bool_app("xowa.addon.search.fulltext.special.expand_matches_section", thread_args.expand_matches_section);
			cfg_mgr.Set_bool_app("xowa.addon.search.fulltext.special.show_all_matches", thread_args.show_all_matches);
			cfg_mgr.Get_int_app_or ("xowa.addon.search.fulltext.special.max_pages_per_wiki", thread_args.max_pages_per_wiki);
			cfg_mgr.Get_str_app_or ("xowa.addon.search.fulltext.special.namespaces", thread_args.namespaces);
		}

		gplx.core.threads.Thread_adp_.Start_by_val("search", Cancelable_.Never, this, Invk__search, thread_args);
	}
	private void Search(Xosearch_search_args args) {
		try {
			byte[][] wiki_domains = Bry_split_.Split(args.wikis, Byte_ascii.Pipe_bry);
			for (byte[] wiki_domain : wiki_domains) {
				Xow_wiki wiki = app.Wiki_mgri().Get_by_or_make_init_y(wiki_domain);
				Search_wiki(wiki, args);
			}
		} catch (Exception exc) {				
			if (app.Tid_is_edit())
				((Xoae_app)app).Gui_mgr().Kit().Ask_ok("", "", Err_.Message_gplx_full(exc));
		}
	} 
	public void Get_lines_rest(Json_nde args) {
		Get_lines_rest(args.Get_as_int("qry_id"), args.Get_as_bry("wiki"), args.Get_as_int("page_id"));
	}
	private void Get_lines_rest(int qry_id, byte[] wiki_bry, int page_id) {
		Xosearch_cache_line[] lines = cache_mgr.Get_lines_rest(qry_id, wiki_bry, page_id);
		for (Xosearch_cache_line line : lines) {
			app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.search_fulltext.results__line__add__recv", gplx.core.gfobjs.Gfobj_nde.New()
				.Add_bry("wiki", wiki_bry)
				.Add_int("page_id", page_id)
				.Add_int("line", line.Line_seq() + 1)
				.Add_bry("html", line.Line_html())
				);
		}
	}
	private void Search_wiki(Xow_wiki wiki, Xosearch_search_args args) {
		byte[] wiki_domain = wiki.Domain_bry();
		Db_conn page_conn = wiki.Data__core_mgr().Tbl__page().Conn();
		Db_rdr page_rdr = page_conn.Stmt_sql("SELECT * FROM page WHERE page_namespace IN (0) ORDER BY page_score DESC").Exec_select__rls_auto();

		app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.search_fulltext.results__wiki__add__recv", gplx.core.gfobjs.Gfobj_nde.New()
			.Add_bry("wiki", wiki_domain)
			.Add_long("page_count", 0)
			);

		finder.Init(args.query, args.case_match, args.auto_wildcard_bgn, args.auto_wildcard_end, Byte_ascii.Star, Byte_ascii.Dash);
		int query_id = cache_mgr.Next_qry_id();
		try {
			int found = 0;
			int searched = 0;
			while (page_rdr.Move_next()) {
				int page_id = page_rdr.Read_int("page_id");
				int text_db_id = page_rdr.Read_int("page_text_db_id");
				byte[] text_mcase = wiki.Data__core_mgr().Dbs__get_by_id_or_fail(text_db_id).Tbl__text().Select(page_id);
				int ns_id = page_rdr.Read_int("page_namespace");
				byte[] ttl_bry = page_rdr.Read_bry_by_str("page_title");
				Xoa_ttl ttl = wiki.Ttl_parse(ns_id, ttl_bry);

				// do eval
				cbk_eval.Init(ttl.Full_db());
				finder.Match(text_mcase, 0, text_mcase.length, cbk_eval);
				searched++;
				if (cbk_eval.found) {
					++found;

					Notify_pages_found_and_searched(wiki_domain, found, searched);

					// do highlight
					if (found <= args.max_pages_per_wiki) {
						cbk_highlight.Init(args.query, query_id, wiki, page_id, ttl.Full_db(), args.show_all_matches);
						app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.search_fulltext.results__page__add__recv", gplx.core.gfobjs.Gfobj_nde.New()
						.Add_int("query_id", query_id)
						.Add_bry("wiki", wiki_domain)
						.Add_int("page_id", page_id)
						.Add_bry("page_ttl", ttl.Full_db())
						.Add_bool("expand_matches_section", args.expand_matches_section)
						);

						finder.Match(text_mcase, 0, text_mcase.length, cbk_highlight);
					}
				}
				if (searched % 100 == 0) {
					Notify_pages_found_and_searched(wiki_domain, found, searched);
				}
			}
			Notify_pages_found_and_searched(wiki_domain, found, searched);
		} finally {
			page_rdr.Rls();
		}
	}
	private void Notify_pages_found_and_searched(byte[] wiki, int found, int searched) {
		app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.search_fulltext.results__wiki__update__recv", gplx.core.gfobjs.Gfobj_nde.New()
			.Add_bry("wiki", wiki)
			.Add_int("found", found)
			.Add_int("searched", searched)
			);
	}

	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__search))           this.Search((Xosearch_search_args)m.ReadObj("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}   private static final String Invk__search = "search";
}
class Xosearch_search_args {
	public boolean case_match;
	public boolean auto_wildcard_bgn;
	public boolean auto_wildcard_end;
	public boolean expand_matches_section;
	public boolean show_all_matches;
	public int max_pages_per_wiki;
	public byte[] wikis;
	public byte[] query;
	public String namespaces;
	public static Xosearch_search_args New_by_json(Json_nde args) {
		Xosearch_search_args rv = new Xosearch_search_args();
		rv.case_match = args.Get_as_bool_or("case_match", false);
		rv.auto_wildcard_bgn = args.Get_as_bool_or("auto_wildcard_bgn", false);
		rv.auto_wildcard_end = args.Get_as_bool_or("auto_wildcard_end", false);
		rv.expand_matches_section = args.Get_as_bool_or("expand_matches_section", false);
		rv.show_all_matches = args.Get_as_bool_or("show_all_matches", false);
		rv.max_pages_per_wiki = args.Get_as_int_or("max_pages_per_wiki", 25);
		rv.wikis = args.Get_as_bry("wikis");
		rv.query = args.Get_as_bry("query");
		rv.namespaces = args.Get_as_str("namespaces");
		return rv;
	}
}
