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
import gplx.xowa.addons.wikis.searchs.searchers.crts.*;
import gplx.xowa.addons.wikis.searchs.searchers.crts.visitors.*;
class Xosearch_fulltext_svc {
	private final    Xoa_app app;
	private final    gplx.xowa.guis.cbks.Xog_cbk_trg cbk_trg = gplx.xowa.guis.cbks.Xog_cbk_trg.New(Xosearch_fulltext_special.Prototype.Special__meta().Ttl_bry());
	private final    Xosearch_finder_mgr finder = new Xosearch_finder_mgr();
	private final    Xosearch_finder_cbk__eval cbk_eval = new Xosearch_finder_cbk__eval();
	private final    Xosearch_finder_cbk__highlight cbk_highlight;
	public Xosearch_fulltext_svc(Xoa_app app) {
		this.app = app;
		cbk_highlight = new Xosearch_finder_cbk__highlight(app, cbk_trg);
	}
	public void Search(Json_nde args) {
		String wikis = args.Get_as_str("wikis");
		byte[] query_mcase = args.Get_as_bry("query");

		String[] wikis_ary = String_.Split(wikis, "|");
		for (String wiki_domain : wikis_ary) {
			Xow_wiki wiki = app.Wiki_mgri().Get_by_or_make_init_y(Bry_.new_u8(wiki_domain));
			byte[] query_lcase = wiki.Case_mgr().Case_build_lower(query_mcase);
			Search_wiki(wiki, query_lcase);
		}
	}
	private void Search_wiki(Xow_wiki wiki, byte[] query_lcase) {
		Db_conn page_conn = wiki.Data__core_mgr().Tbl__page().Conn();
		Db_rdr page_rdr = page_conn.Stmt_sql("SELECT * FROM page WHERE page_namespace IN (0) ORDER BY page_score DESC").Exec_select__rls_auto();
		
		app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.search_fulltext.results__wiki__add__recv", gplx.core.gfobjs.Gfobj_nde.New()
			.Add_bry("wiki", wiki.Domain_bry())
			.Add_long("page_count", 0)
			);

		finder.Init(query_lcase, false, false, Byte_ascii.Star, Byte_ascii.Dash);
		try {
			int found = 0;
			while (page_rdr.Move_next()) {
				int page_id = page_rdr.Read_int("page_id");
				int text_db_id = page_rdr.Read_int("page_text_db_id");
				byte[] text_mcase = wiki.Data__core_mgr().Dbs__get_by_id_or_fail(text_db_id).Tbl__text().Select(page_id);

				cbk_eval.found = false;
				finder.Match(text_mcase, 0, text_mcase.length, cbk_eval);
				if (cbk_eval.found) {
					int ns_id = page_rdr.Read_int("page_namespace");
					byte[] ttl_bry = page_rdr.Read_bry_by_str("page_title");
					app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.search_fulltext.results__wiki__update__recv", gplx.core.gfobjs.Gfobj_nde.New()
						.Add_bry("wiki", wiki.Domain_bry())
						.Add_int("found", ++found)
						);

					Xoa_ttl ttl = wiki.Ttl_parse(ns_id, ttl_bry);
					cbk_highlight.Init(wiki, ttl);
					app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.search_fulltext.results__page__add__recv", gplx.core.gfobjs.Gfobj_nde.New()
						.Add_bry("wiki", wiki.Domain_bry())
						.Add_bry("page", ttl.Full_db())
						.Add_int("found", 0)
						);
					finder.Match(text_mcase, 0, text_mcase.length, cbk_highlight);
				}
			}
		} finally {
			page_rdr.Rls();
		}
	}
}
