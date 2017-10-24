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
package gplx.xowa.addons.bldrs.hdumps.diffs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.hdumps.*;
import gplx.core.brys.*;
import gplx.dbs.*;
import gplx.xowa.htmls.*;
import gplx.xowa.wikis.data.tbls.*;
class Dumpdiff_mgr {
	public void Exec(Xoae_app app, Xowe_wiki wiki, Dumpdiff_cfg cfg) {
		// init log_tbl, wikis
		Dumpdiff_log_tbl log_tbl = Dumpdiff_log_tbl.New(wiki);
		Xowe_wiki cur_wiki = Get_wiki_by_dir(wiki, Bool_.Y, cfg.Cur_dir());
		Xowe_wiki prv_wiki = Get_wiki_by_dir(wiki, Bool_.N, cfg.Prv_dir());

		// init html_loader, page_loader
		Hdump_html_loader cur_html_loader = new Hdump_html_loader(cur_wiki);
		Hdump_html_loader prv_html_loader = new Hdump_html_loader(prv_wiki);
		Dumpdiff_page_loader page_loader = new Dumpdiff_page_loader(cur_wiki, prv_wiki, 10000);
		List_adp list = List_adp_.New();

		// prepare for loop
		int page_count = 0, diff_count = 0;
		log_tbl.Insert_bgn();

		// loop page_table until no more
		while (true) {
			page_loader.Load(list);
			int list_len = list.Count();
			if (list_len == 0) break;

			// loop pages, compare, and log
			for (int i = 0; i < list_len; ++i) {
				Dumpdiff_page_itm page = (Dumpdiff_page_itm)list.Get_at(i);
				int page_id = page.Page_id();
				byte[] cur_html = cur_html_loader.Load(page_id, page.Cur_db_id());
				byte[] prv_html = prv_html_loader.Load(page_id, page.Prv_db_id());
				byte[][] diff = Bry_diff_.Diff_1st_line(cur_html, prv_html);
				++page_count;
				if (diff != null) {
					log_tbl.Insert_by_batch(page_id, diff[0], diff[1]);
					++diff_count;
					Gfo_usr_dlg_.Instance.Warn_many("", "", "hdump diff: pages=~{0} diffs=~{1} page_id=~{2} lhs=~{3} rhs=~{4}", page_count, diff_count, page_id, diff[0], diff[1]);
				}
			}
			list.Clear();
		}

		// cleanup
		log_tbl.Insert_end();
	}
	private static Xowe_wiki Get_wiki_by_dir(Xowe_wiki dflt_wiki, boolean wiki_is_cur, Io_url wiki_dir) {
		Xowe_wiki rv = null;
		if (wiki_dir == null) {
			if (wiki_is_cur)
				rv = dflt_wiki;
			else
				throw Err_.new_("", "prv_dir not specified");
		}
		if (rv == null)
			rv = gplx.xowa.addons.bldrs.mass_parses.parses.Xow_wiki_utl_.Clone_wiki(dflt_wiki, wiki_dir);
		return rv;
	}
}
