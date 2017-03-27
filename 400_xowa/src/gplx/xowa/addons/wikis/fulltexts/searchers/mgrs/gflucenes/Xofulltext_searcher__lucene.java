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
import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.uis.*;
import gplx.xowa.addons.wikis.fulltexts.searchers.caches.*;
public class Xofulltext_searcher__lucene implements Xofulltext_searcher {
	private final    Gflucene_searcher_mgr searcher = new Gflucene_searcher_mgr();
	public boolean Type_is_lucene() {return true;}
	public void Search(Xofulltext_searcher_ui ui, Xow_wiki wiki, Xofulltext_cache_qry qry, Xofulltext_args_qry args, Xofulltext_args_wiki wiki_args) {
		// create lists
		Ordered_hash full_list = Ordered_hash_.New();
		Ordered_hash temp_list = Ordered_hash_.New();
		Ordered_hash page_list = qry.Pages();

		// init searcher with wiki
		Gflucene_analyzer_data analyzer_data = Gflucene_analyzer_data.New_data_from_locale(wiki.Lang().Key_str());
		searcher.Init(new Gflucene_index_data
		( analyzer_data
		, Xosearch_fulltext_addon.Get_index_dir(wiki).Xto_api()));

		// get page_load vars
		Xowd_page_itm tmp_page_row = new Xowd_page_itm();
		Xowd_page_tbl page_tbl = wiki.Data__core_mgr().Db__core().Tbl__page();

		// exec search
		int needed_bgn = wiki_args.bgn;
		if (needed_bgn < page_list.Len()) needed_bgn = page_list.Len();
		int needed_end = wiki_args.bgn + wiki_args.len;
		int needed_len = needed_end - needed_bgn;
		int found = 0;
		int threshold = 0;
		Gflucene_searcher_qry searcher_data = new Gflucene_searcher_qry(String_.new_u8(args.search_text), 100);
		while (found < needed_len) {
			if (args.Canceled()) return;

			threshold += wiki_args.len;
			searcher_data.match_max = threshold;
			searcher.Exec(temp_list, searcher_data);

			int temp_list_len = temp_list.Len();
			for (int i = 0; i < temp_list_len; i++) {
				if (args.Canceled()) return;
				Gflucene_doc_data doc_data = (Gflucene_doc_data)temp_list.Get_at(i);
				if (!page_list.Has(doc_data.page_id)) {
					// load page
					if (!page_tbl.Select_by_id(tmp_page_row, doc_data.page_id)) {
						Gfo_usr_dlg_.Instance.Warn_many("", "", "searcher.lucene: could not find page; page_id=~{0}", doc_data.page_id);
						continue;
					}

					// make page_ttl
					Xoa_ttl page_ttl = wiki.Ttl_parse(tmp_page_row.Ns_id(), tmp_page_row.Ttl_page_db());
					doc_data.ns_id = tmp_page_row.Ns_id();
					doc_data.page_full_db = page_ttl.Full_db();

					if (!wiki_args.ns_hash.Has(doc_data.ns_id)) continue;

					// call page doc_data
					Xofulltext_searcher_page page = new Xofulltext_searcher_page(args.qry_id, wiki.Domain_bry(), doc_data.page_id, doc_data.page_full_db, wiki_args.expand_snips);
					ui.Send_page_add(page);

					full_list.Add(doc_data.page_id, doc_data);
					found++;
					if (found >= needed_len) break;
				}
			}
			temp_list.Clear();
		}

		// term
		searcher.Term();

		ui.Send_wiki_update(wiki.Domain_bry(), page_list.Len(), -1);

		// create highlighter thread and launch it
		Xofulltext_highlighter_mgr highlighter_mgr = new Xofulltext_highlighter_mgr(ui, wiki, args, wiki_args, analyzer_data, searcher_data, full_list);
		highlighter_mgr.Highlight_list(); // NOTE: do not launch in separate thread, else multiple wikis will have strange race conditions
	}
}
