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
package gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.gflucenes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*; import gplx.xowa.addons.wikis.fulltexts.searchers.*; import gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.*;
import gplx.gflucene.*;
import gplx.gflucene.core.*;
import gplx.gflucene.indexers.*;
import gplx.gflucene.searchers.*;
import gplx.gflucene.highlighters.*;
import gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.uis.*;
public class Xofulltext_searcher__lucene implements Xofulltext_searcher {
	private final    Gflucene_searcher_mgr searcher = new Gflucene_searcher_mgr();
	public void Search(Xofulltext_searcher_ui ui, Xow_wiki wiki, Xofulltext_searcher_args args) {
		// create list
		List_adp list = List_adp_.New();

		// init searcher with wiki
		Gflucene_analyzer_data analyzer_data = Gflucene_analyzer_data.New_data_from_locale(wiki.Lang().Key_str());
		searcher.Init(new Gflucene_index_data
		( analyzer_data
		, wiki.Fsys_mgr().Root_dir().GenSubDir_nest("data", "search").Xto_api()));

		// exec search
		Gflucene_searcher_qry searcher_data = new Gflucene_searcher_qry(String_.new_u8(args.query), args.max_pages_per_wiki);
		searcher.Exec(list, searcher_data);

		// term
		searcher.Term();

		// loop list and send pages
		int len = list.Len();
		for (int i = 0; i < len; i++) {
			Gflucene_doc_data found = (Gflucene_doc_data)list.Get_at(i);

			// call page found
			Xofulltext_searcher_page page = new Xofulltext_searcher_page(args.query_id, wiki.Domain_str(), found.page_id, found.title, args.expand_matches_section);
			ui.Send_page_add(page);
		}

		// create highlighter thread and launch it
		Xofulltext_highlighter_mgr highlighter_mgr = new Xofulltext_highlighter_mgr(ui, wiki, analyzer_data, searcher_data, list);
		gplx.core.threads.Thread_adp_.Start_by_key("highlighter", Cancelable_.Never, highlighter_mgr, Xofulltext_highlighter_mgr.Invk__highlight);
	}
}
