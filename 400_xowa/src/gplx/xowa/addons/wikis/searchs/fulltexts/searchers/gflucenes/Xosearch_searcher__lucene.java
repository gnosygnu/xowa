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
package gplx.xowa.addons.wikis.searchs.fulltexts.searchers.gflucenes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.fulltexts.*; import gplx.xowa.addons.wikis.searchs.fulltexts.searchers.*;
import gplx.gflucene.*;
import gplx.xowa.addons.wikis.searchs.fulltexts.searchers.uis.*;
public class Xosearch_searcher__lucene implements Xosearch_searcher {
	private final    Gflucene_searcher searcher = new Gflucene_searcher();
	public void Search(Xosearch_searcher_ui cbk, Xow_wiki wiki, Xosearch_searcher_args args) {
		// create list
		List_adp list = List_adp_.New();

		// init searcher with wiki
		searcher.Init(wiki.Fsys_mgr().Root_dir().GenSubDir_nest("data", "search").Xto_api());

		// exec search
		searcher.Exec(list, new Gflucene_searcher_data(String_.new_u8(args.query), args.max_pages_per_wiki));

		// term
		searcher.Term();

		// loop list
		int len = list.Len();
		for (int i = 0; i < len; i++) {
			Gflucene_index_data found = (Gflucene_index_data)list.Get_at(i);

			// call page found
			Xosearch_searcher_page page = new Xosearch_searcher_page(args.query_id, wiki.Domain_str(), found.page_id, found.title, false);
			cbk.Send_page_add(page);
		}
	}
}
