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
package gplx.xowa.addons.wikis.fulltexts.searchers.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*; import gplx.xowa.addons.wikis.fulltexts.searchers.*;
public class Xofulltext_cache_mgr {
	private final    Ordered_hash qry_hash = Ordered_hash_.New();
	public int Next_qry_id() {return next_qry_id++;} private int next_qry_id;
	public void Clear() {
		qry_hash.Clear();
	}
	public void Add(int query_id, byte[] query) {
		Xofulltext_cache_qry qry = (Xofulltext_cache_qry)qry_hash.Get_by(query_id);
		if (qry == null) {
			qry = new Xofulltext_cache_qry(query_id, query);
			qry_hash.Add(query_id, qry);
		}
	}
	public void Add(int query_id, byte[] wiki_bry, int page_id, int line_seq, byte[] line_html) {
		// get qry
		Xofulltext_cache_qry qry = (Xofulltext_cache_qry)qry_hash.Get_by(query_id);
		if (qry == null) {
			throw Err_.new_wo_type("query not found; query_id=~{0}", query_id);
		}

		// get wiki
		Xofulltext_cache_wiki wiki = (Xofulltext_cache_wiki)qry.Wikis().Get_by(wiki_bry);
		if (wiki == null) {
			wiki = new Xofulltext_cache_wiki(wiki_bry);
			qry.Wikis().Add(wiki_bry, wiki);
		}

		// get page
		Xofulltext_cache_page page = (Xofulltext_cache_page)wiki.Pages().Get_by(page_id);
		if (page == null) {
			page = new Xofulltext_cache_page(page_id, wiki.Pages().Count());
			wiki.Pages().Add(page_id, page);
		}

		// add line
		Xofulltext_cache_line line = new Xofulltext_cache_line(line_seq, line_html);
		page.Lines().Add(line);
	}
	public Object Get_pages_rng(int qry_id, int page_seq_bgn, int page_seq_end) {
		return null;
	}
	public Xofulltext_cache_line[] Get_lines_rest(int qry_id, byte[] wiki_bry, int page_id) {
		// get page
		Xofulltext_cache_qry qry = (Xofulltext_cache_qry)qry_hash.Get_by(qry_id);
		Xofulltext_cache_wiki wiki = (Xofulltext_cache_wiki)qry.Wikis().Get_by(wiki_bry);
		Xofulltext_cache_page page = (Xofulltext_cache_page)wiki.Pages().Get_by(page_id);

		// loop lines from 1 to n; note "1" b/c results will always show at least 1st line
		List_adp list = List_adp_.New();
		int lines_len = page.Lines().Len();
		for (int i = 1; i < lines_len; i++) {
			Xofulltext_cache_line line = (Xofulltext_cache_line)page.Lines().Get_at(i);				
			list.Add(line);
		}
		return (Xofulltext_cache_line[])list.To_ary_and_clear(Xofulltext_cache_line.class);
	}
}
