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
public class Xofulltext_searcher_ui__gui implements Xofulltext_searcher_ui {
	private final    Xog_cbk_mgr cbk_mgr;
	private final    Xog_cbk_trg cbk_trg;
	public Xofulltext_searcher_ui__gui(Xog_cbk_mgr cbk_mgr, Xog_cbk_trg cbk_trg) {
		this.cbk_mgr = cbk_mgr;
		this.cbk_trg = cbk_trg;
	}
	public void Send_wiki_add(byte[] wiki_domain) {
		cbk_mgr.Send_json(cbk_trg, "xo.fulltext_searcher.results__wiki__add__recv", gplx.core.gfobjs.Gfobj_nde.New()
			.Add_bry("wiki", wiki_domain)
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
		.Add_str("wiki", page.Wiki_domain())
		.Add_int("page_id", page.Page_id())
		.Add_str("page_ttl", page.Page_title())
		.Add_bool("expand_matches_section", page.Expand_matches_section())
		);
	}
	public void Send_line_add(Xofulltext_searcher_line match) {
		cbk_mgr.Send_json(cbk_trg, "xo.fulltext_searcher.results__line__add__recv", gplx.core.gfobjs.Gfobj_nde.New()
			.Add_str("wiki", match.Wiki_domain())
			.Add_int("page_id", match.Page_id())
			.Add_int("line", match.Found_idx())
			.Add_str("html", match.Excerpt())
			);
	}
}
