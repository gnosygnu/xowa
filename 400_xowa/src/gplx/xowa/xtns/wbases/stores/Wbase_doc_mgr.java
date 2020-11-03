/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.wbases.stores;

import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.Err_;
import gplx.Gfo_usr_dlg_;
import gplx.Ordered_hash;
import gplx.Ordered_hash_;
import gplx.String_;
import gplx.core.logs.Gfo_log_wtr;
import gplx.langs.jsons.Json_doc;
import gplx.langs.jsons.Json_nde;
import gplx.langs.jsons.Json_parser;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.xtns.wbases.Wdata_doc;
import gplx.xowa.xtns.wbases.Wdata_wiki_mgr;
import gplx.xowa.xtns.wbases.core.Wbase_pid;

public class Wbase_doc_mgr {
	private final Wdata_wiki_mgr wbase_mgr;
	private final Wbase_qid_mgr qid_mgr;
	private Wbase_doc_cache doc_cache;
	private final Object thread_lock = new Object();
	private final Ordered_hash wbase_db_hash = Ordered_hash_.New_bry();
	private final Gfo_log_wtr wbase_db_log;
	private final Json_parser jsonParser = new Json_parser();
	public Wbase_doc_mgr(Wdata_wiki_mgr wbase_mgr, Wbase_qid_mgr qid_mgr) {
		this.wbase_mgr = wbase_mgr;
		this.qid_mgr = qid_mgr;
		this.doc_cache = new Wbase_doc_cache__hash();
		this.wbase_db_log = Gfo_log_wtr.New_dflt("wbase", "db_log_{0}.csv");
	}
	public void Enabled_(boolean v) {this.enabled = v;} private boolean enabled;
	public void Cache__init(String cache_type, long cache_max, long compress_size, long used_weight) {
		if		(String_.Eq(cache_type, "null")) doc_cache = new Wbase_doc_cache__null();
		else if (String_.Eq(cache_type, "hash")) doc_cache = new Wbase_doc_cache__hash();
		else if (String_.Eq(cache_type, "mru" )) doc_cache = new Wbase_doc_cache__mru(cache_max, compress_size, used_weight);
		else throw Err_.new_unhandled_default(cache_type);
	}
	public void Cleanup() {
		doc_cache.Term();
		wbase_db_log__flush();
	}
	private void wbase_db_log__flush() {
		int len = wbase_db_hash.Len();
		Bry_bfr tmp_bfr = Bry_bfr_.New();
		for (int i = 0; i < len; i++) {
			Wbase_db_log_itm itm = (Wbase_db_log_itm)wbase_db_hash.Get_at(i);
			tmp_bfr.Add(itm.Ttl());
			tmp_bfr.Add_byte_pipe().Add_int_variable(itm.Count());
			tmp_bfr.Add_byte_pipe().Add_int_variable(itm.Elapsed());
			tmp_bfr.Add_byte_nl();
			wbase_db_log.Write(tmp_bfr);
		}
		wbase_db_log.Flush();
	}
	public void Clear() {
		synchronized (thread_lock) {	// LOCK:app-level
			doc_cache.Clear();
		}
	}
	public Wdata_doc Get_by_ttl_or_null(Xowe_wiki wiki, Xoa_ttl ttl) { // "enwiki", "Earth" -> "Q2" wdoc
		byte[] qid_bry = qid_mgr.Get_qid_or_null(wiki, ttl);	// EX: "enwiki", "Earth" -> "Q2"
		return qid_bry == null ? null : this.Get_by_exact_id_or_null(qid_bry);
	}
	public Wdata_doc Get_by_xid_or_null(byte[] xid) {return Get_by_loose_id_or_null(Wbase_pid.Prepend_property_if_needed(xid));}// scribunto passes either p1 or q1; convert p1 to "Property:p1"
	public Wdata_doc Get_by_loose_id_or_null(byte[] ttl_bry) {
		return Get_by_exact_id_or_null(ttl_bry);
	}
	public Wdata_doc Get_by_exact_id_or_null(byte[] ttl_bry) {// must correct case and ns; EX:"Q2" or "Property:P1"; not "q2" or "P2"
		// load from cache
		Wdata_doc rv = null;
		synchronized (thread_lock) {
			rv = doc_cache.Get_or_null(ttl_bry);
			if (rv == null) {
				// load from db
				rv = Load_wdoc_or_null(ttl_bry); 
				if (rv == null) return null;	// page not found
				Add(ttl_bry, rv);// NOTE: use ttl_bry, not rv.Qid; allows subsequent lookups to skip this redirect cycle
			}
		}
		return rv;
	}
	private Wdata_doc Load_wdoc_or_null(byte[] ttl_bry) { // EX:"Q2" or "Property:P1"
		if (!enabled) return null;

		// loggging
		Wbase_db_log_itm wbase_db_itm = (Wbase_db_log_itm)wbase_db_hash.Get_by(ttl_bry);
		if (wbase_db_itm == null) {
			wbase_db_itm = new Wbase_db_log_itm(ttl_bry);
			wbase_db_hash.Add(ttl_bry, wbase_db_itm);
		}
		long time_bgn = gplx.core.envs.System_.Ticks();

		Wdata_doc rv = null;
		synchronized (thread_lock) {	// LOCK:app-level; jdoc_parser; moved synchronized higher up; DATE:2016-09-03
			byte[] cur_ttl_bry = ttl_bry;
			int load_count = -1;
			while (load_count < 2) {	// limit to 2 tries (i.e.: 1 redirect)
				// parse ttl; note that "q2" will get parsed to "Q2" b/c of ns casing
				Xoa_ttl cur_ttl = wbase_mgr.Wdata_wiki().Ttl_parse(cur_ttl_bry);
				if (cur_ttl == null) {
					Gfo_usr_dlg_.Instance.Warn_many("", "", "invalid wbase ttl: orig=~{0} cur=~{1}", ttl_bry, cur_ttl_bry);
					break;
				}

				// get page
				Xoae_page page = wbase_mgr.Wdata_wiki().Data_mgr().Load_page_by_ttl(cur_ttl);
				if (!page.Db().Page().Exists()) break;

				// parse jdoc
				Json_doc jdoc = jsonParser.Parse(page.Db().Text().Text_bry());
				if (jdoc == null) {
					Gfo_usr_dlg_.Instance.Warn_many("", "", "invalid jdoc for ttl: orig=~{0} cur=~{1}", ttl_bry, cur_ttl_bry);
					break;
				}

				// check for redirect; EX: {"entity":"Q22350516","redirect":"Q21006972"}; PAGE:fr.w:Tour_du_Táchira_2016; DATE:2016-08-13
				Json_nde jdoc_root = jdoc.Root_nde();
				byte[] redirect_ttl = jdoc_root.Get_as_bry_or(Bry__redirect, null);
				if (redirect_ttl != null) {
					cur_ttl_bry = redirect_ttl;
					load_count++;
					continue;
				}

				// is json doc, and not a redirect; return
				rv = new Wdata_doc(wbase_mgr, jdoc, cur_ttl_bry);
				break;
			}
			if (rv == null && load_count >= 2)
				Gfo_usr_dlg_.Instance.Warn_many("", "", "too many redirects for ttl: orig=~{0} cur=~{1}", ttl_bry, cur_ttl_bry);
		}

		wbase_db_itm.Update(gplx.core.envs.System_.Ticks__elapsed_in_frac(time_bgn));
		return rv;
	}
	private static final byte[] Bry__redirect = Bry_.new_a7("redirect");

	public void Add(byte[] full_db, Wdata_doc page) {	// TEST:
		synchronized (thread_lock) {	// LOCK:app-level
			if (doc_cache.Get_or_null(full_db) == null)
				doc_cache.Add(full_db, page);
		}
	}	
}
class Wbase_db_log_itm {
	public Wbase_db_log_itm(byte[] ttl) {
		this.ttl = ttl;
	}
	public byte[] Ttl() {return ttl;} private final byte[] ttl;
	public int Count() {return count;} private int count;
	public int Elapsed() {return elapsed;} private int elapsed;
	public void Update(int elapsed_diff) {
		count++;
		this.elapsed += elapsed_diff;
	}
}
