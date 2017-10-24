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
package gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.brutes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*; import gplx.xowa.addons.wikis.fulltexts.searchers.*; import gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.*;
import gplx.dbs.*;
import gplx.xowa.guis.cbks.*;
import gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.uis.*;
import gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.brutes.finders.*;
import gplx.xowa.addons.wikis.fulltexts.searchers.caches.*;
public class Xofulltext_searcher__brute implements Xofulltext_searcher {
	private final    Xofulltext_finder_mgr finder = new Xofulltext_finder_mgr();
	private final    Xofulltext_finder_cbk__eval cbk_eval = new Xofulltext_finder_cbk__eval();
	private final    Xofulltext_finder_cbk__highlight cbk_highlight = new Xofulltext_finder_cbk__highlight();
	public boolean Type_is_lucene() {return false;}
	public void Search(Xofulltext_searcher_ui ui, Xow_wiki wiki, Xofulltext_cache_qry qry, Xofulltext_args_qry args, Xofulltext_args_wiki wiki_args) {
		// get pages from db
		Db_conn page_conn = wiki.Data__core_mgr().Tbl__page().Conn();
		Db_rdr page_rdr = page_conn.Stmt_sql("SELECT * FROM page WHERE page_namespace IN (0) ORDER BY page_score DESC").Exec_select__rls_auto();

		// init finder
		finder.Init(args.search_text, args.case_match, args.auto_wildcard_bgn, args.auto_wildcard_end, Byte_ascii.Star, Byte_ascii.Dash);

		// loop
		byte[] wiki_domain = wiki.Domain_bry();
		int found = 0;
		int searched = 0;
		int rng_bgn = wiki_args.bgn;
		int rng_end = wiki_args.bgn + wiki_args.len;
		try {
			while (page_rdr.Move_next()) {
				// read data from reader
				int page_id = page_rdr.Read_int("page_id");
				int text_db_id = page_rdr.Read_int("page_text_db_id");
				byte[] text_mcase = wiki.Data__core_mgr().Dbs__get_by_id_or_fail(text_db_id).Tbl__text().Select(page_id);
				int ns_id = page_rdr.Read_int("page_namespace");
				byte[] ttl_bry = page_rdr.Read_bry_by_str("page_title");
				Xoa_ttl ttl = wiki.Ttl_parse(ns_id, ttl_bry);

				// eval query
				cbk_eval.Init(ttl.Full_db());
				finder.Match(text_mcase, 0, text_mcase.length, cbk_eval);
				searched++;

				// check if page matches query
				if (cbk_eval.found) {
					++found;

					// paging: ignore any results less than rng_bgn; EX: 21-40 are requested; ignore 1-20
					if (found <= rng_bgn) continue;

					// update pages found
					ui.Send_wiki_update(wiki_domain, found, searched);

					// do highlight
					cbk_highlight.Init(ui, args.qry_id, wiki, page_id, ttl.Full_db(), wiki_args.show_all_snips);
					ui.Send_page_add(new Xofulltext_searcher_page
						( args.qry_id
						, wiki_domain
						, page_id
						, ttl.Full_db()
						, wiki_args.expand_snips
						));
					finder.Match(text_mcase, 0, text_mcase.length, cbk_highlight);

					// paging; enough pages found; stop
					if (found > rng_end) break;
				}

				// update update pages found every 100 pages
				if (searched % 100 == 0) {
					ui.Send_wiki_update(wiki_domain, found, searched);
				}
			}
		}
		finally {
			page_rdr.Rls();
		}

		// update one last time for final searched
		ui.Send_wiki_update(wiki_domain, found, searched);
	}
}
