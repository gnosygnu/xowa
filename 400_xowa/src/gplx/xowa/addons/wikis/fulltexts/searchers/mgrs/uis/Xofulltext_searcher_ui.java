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
package gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.uis; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*; import gplx.xowa.addons.wikis.fulltexts.searchers.*; import gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.*;
import gplx.xowa.guis.cbks.*;
import gplx.xowa.addons.wikis.fulltexts.searchers.caches.*;
public class Xofulltext_searcher_ui {
	private final    Xog_cbk_mgr cbk_mgr;
	private final    Xog_cbk_trg cbk_trg;
	private final    Xofulltext_cache_mgr cache_mgr;
	public Xofulltext_searcher_ui(Xofulltext_cache_mgr cache_mgr, Xog_cbk_mgr cbk_mgr, Xog_cbk_trg cbk_trg) {
		this.cache_mgr = cache_mgr;
		this.cbk_mgr = cbk_mgr;
		this.cbk_trg = cbk_trg;
	}
	public void Send_wiki_add(boolean type_is_lucene, boolean expand_pages, byte[] wiki, int rng_bgn, int rng_end) {
		cbk_mgr.Send_json(cbk_trg, "xo.fulltext_searcher.results__wiki__add__recv", gplx.core.gfobjs.Gfobj_nde.New()
			.Add_bry("wiki", wiki)
			.Add_bool("type_is_lucene", type_is_lucene)
			.Add_bool("expand_pages", expand_pages)
			.Add_int("rng_bgn", rng_bgn + List_adp_.Base1)
			.Add_int("rng_end", rng_end)
			);
	}
	public void Send_wiki_update(byte[] wiki, int found, int searched) {
		cbk_mgr.Send_json(cbk_trg, "xo.fulltext_searcher.results__wiki__update__recv", gplx.core.gfobjs.Gfobj_nde.New()
			.Add_bry("wiki", wiki)
			.Add_int("found", found)
			.Add_int("searched", searched)
			);
	}
	public void Send_page_add(Xofulltext_searcher_page page) {
		cbk_mgr.Send_json(cbk_trg, "xo.fulltext_searcher.results__page__add__recv", gplx.core.gfobjs.Gfobj_nde.New()
		.Add_int("query_id", page.Query_id())
		.Add_bry("wiki", page.Wiki_domain())
		.Add_int("page_id", page.Page_id())
		.Add_bry("page_ttl", page.Page_ttl())
		.Add_bool("expand_snips", page.Expand_snips())
		);
		cache_mgr.Add_page(page.Query_id(), page.Wiki_domain(), page.Page_id(), page.Page_ttl());
	}
	public void Send_line_add(boolean add_to_cache, boolean show_all_snips, int qry_id, byte[] wiki_domain, int page_id, int line_sort_order, byte[] line_html) {
		if (add_to_cache)
			cache_mgr.Add_line(qry_id, wiki_domain, page_id, line_sort_order, line_html);

		line_sort_order += List_adp_.Base1; // NOTE: increment after cache_mgr
		if (line_sort_order == 1 || show_all_snips) {
			cbk_mgr.Send_json(cbk_trg, "xo.fulltext_searcher.results__line__add__recv", gplx.core.gfobjs.Gfobj_nde.New()
				.Add_bry("wiki", wiki_domain)
				.Add_int("page_id", page_id)
				.Add_int("line", line_sort_order)
				.Add_bry("html", line_html)
				);
		}
		cbk_mgr.Send_json(cbk_trg, "xo.fulltext_searcher.results__page__update__recv", gplx.core.gfobjs.Gfobj_nde.New()
			.Add_bry("wiki", wiki_domain)
			.Add_int("page_id", page_id)
			.Add_int("found", line_sort_order)
			.Add_bool("show_all_snips", show_all_snips)
			);
	}
	public void Send_done() {
		cbk_mgr.Send_json(cbk_trg, "xo.fulltext_searcher.results__done__recv", gplx.core.gfobjs.Gfobj_nde.New());
	}
}
