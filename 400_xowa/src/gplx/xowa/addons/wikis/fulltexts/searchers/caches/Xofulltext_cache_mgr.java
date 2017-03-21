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
import gplx.core.primitives.*;
public class Xofulltext_cache_mgr {
	private final    Ordered_hash qry_hash = Ordered_hash_.New();
	private final    Hash_adp_bry id_hash = Hash_adp_bry.cs();
	public int Next_qry_id() {return next_qry_id++;} private int next_qry_id;
	public void Clear() {
		qry_hash.Clear();
	}
	public int Ids__get_or_neg1(byte[] qry_key) {
		Int_obj_val id = (Int_obj_val)id_hash.Get_by(qry_key);
		return id == null ? -1 : id.Val();
	}
	public int Ids__next() {return next_qry_id++;}
	public void Add(int id, byte[] key) {
		id_hash.Add(key, new Int_obj_val(id));
		Xofulltext_cache_qry qry = (Xofulltext_cache_qry)qry_hash.Get_by(id);
		if (qry == null) {
			qry = new Xofulltext_cache_qry(id, key);
			qry_hash.Add(id, qry);
		}
	}
	public void Add_page(int query_id, byte[] wiki_bry, int page_id, byte[] page_ttl) {
		// get qry
		Xofulltext_cache_qry qry = (Xofulltext_cache_qry)qry_hash.Get_by_or_fail(query_id);

		// get page
		Xofulltext_cache_page page = (Xofulltext_cache_page)qry.Pages().Get_by(page_id);
		if (page == null) {
			page = new Xofulltext_cache_page(page_id, qry.Pages().Count(), page_ttl);
			qry.Pages().Add(page_id, page);
		}
	}
	public void Add_line(int query_id, byte[] wiki_bry, int page_id, int line_seq, byte[] line_html) {
		// get qry
		Xofulltext_cache_qry qry = (Xofulltext_cache_qry)qry_hash.Get_by_or_fail(query_id);
		Xofulltext_cache_page page = (Xofulltext_cache_page)qry.Pages().Get_by_or_fail(page_id);

		// add line
		Xofulltext_cache_line line = new Xofulltext_cache_line(line_seq, line_html);
		page.Lines().Add(line);
	}
	public Xofulltext_cache_qry Get_or_null(int qry_id) {
		return (Xofulltext_cache_qry)qry_hash.Get_by(qry_id);
	}
	public Xofulltext_cache_page[] Get_pages_rng(int qry_id, int page_seq_bgn, int page_seq_end) {
		Xofulltext_cache_qry qry = (Xofulltext_cache_qry)qry_hash.Get_by(qry_id);
		if (qry == null) return null;

		List_adp list = List_adp_.New();
		int len = qry.Pages().Len();
		for (int i = page_seq_bgn; i < page_seq_end; i++) {
			if (i >= len) break;
			Xofulltext_cache_page page = (Xofulltext_cache_page)qry.Pages().Get_at(i);
			list.Add(page);
		}
		return (Xofulltext_cache_page[])list.To_ary_and_clear(Xofulltext_cache_page.class);
	}
	public Xofulltext_cache_line[] Get_lines_rest(int qry_id, byte[] wiki_bry, int page_id) {
		// get page
		Xofulltext_cache_qry qry = (Xofulltext_cache_qry)qry_hash.Get_by(qry_id);
		Xofulltext_cache_page page = (Xofulltext_cache_page)qry.Pages().Get_by(page_id);

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
