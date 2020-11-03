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
package gplx.xowa.xtns.wbases.imports;

import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.Gfo_invk;
import gplx.Ordered_hash;
import gplx.String_;
import gplx.dbs.Db_conn;
import gplx.langs.jsons.Json_doc;
import gplx.langs.jsons.Json_parser;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.bldrs.Xob_bldr;
import gplx.xowa.bldrs.wkrs.Xob_itm_dump_base;
import gplx.xowa.bldrs.wkrs.Xob_page_wkr;
import gplx.xowa.wikis.data.Xow_db_file;
import gplx.xowa.wikis.data.Xow_db_file_;
import gplx.xowa.wikis.data.Xow_db_mgr;
import gplx.xowa.wikis.data.tbls.Xowd_page_itm;
import gplx.xowa.wikis.nss.Xow_ns_;
import gplx.xowa.xtns.wbases.core.Wdata_sitelink_itm;
import gplx.xowa.xtns.wbases.dbs.Wbase_qid_tbl;
import gplx.xowa.xtns.wbases.parsers.Wdata_doc_parser;

public class Xob_wdata_qid extends Xob_itm_dump_base implements Xob_page_wkr, Gfo_invk {
	private Db_conn conn;
	private Wbase_qid_tbl tbl;
	private final Object thread_lock = new Object();
	private final Json_parser parser = new Json_parser();
	private Xob_wbase_ns_parser ns_parser; private final Xob_wbase_ns_parser_rslt ns_parser_rslt = new Xob_wbase_ns_parser_rslt();
	public Xob_wdata_qid(Db_conn conn) {
		this.conn = conn;
	}
	public String Page_wkr__key() {return gplx.xowa.bldrs.Xob_cmd_keys.Key_wbase_qid;}
	public Xob_wdata_qid Ctor(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki); return this;}
	public void Page_wkr__bgn() {
		this.ns_parser = new Xob_wbase_ns_parser(bldr.App().Fsys_mgr().Cfg_site_meta_fil());
		this.Qid__bgn();
	}
	public void Page_wkr__run(Xowd_page_itm page) {
		if (page.Ns_id() != Xow_ns_.Tid__main) return;	// qid pages are only in the Main Srch_rslt_cbk
		Json_doc jdoc = parser.Parse(page.Text()); 
		if (jdoc == null) {bldr.Usr_dlg().Warn_many("", "", "json is invalid: ns=~{0} id=~{1}", page.Ns_id(), String_.new_u8(page.Ttl_page_db())); return;}
		this.Qid__run(jdoc);
	}
	public void Page_wkr__run_cleanup() {}
	public void Page_wkr__end() {this.Qid__end();}
	public void Qid__bgn() {
		if (conn == null) {
			Xow_db_file wbase_db = Make_wbase_db(wiki.Db_mgr_as_sql().Core_data_mgr());
			conn = wbase_db.Conn();
		}
		tbl = Wbase_qid_tbl.New_make(conn, false);
		tbl.Create_tbl();
		tbl.Insert_bgn();
	}
	public void Qid__run(Json_doc jdoc) {
		synchronized (thread_lock) {
			Wdata_doc_parser wdoc_parser = app.Wiki_mgr().Wdata_mgr().Wdoc_parser(jdoc);
			byte[] qid = wdoc_parser.Parse_qid(jdoc);
			Bry_bfr tmp_bfr = Bry_bfr_.Reset(255);
			Ordered_hash sitelinks = wdoc_parser.Parse_sitelinks(qid, jdoc);
			int sitelinks_len = sitelinks.Count(); if (sitelinks_len == 0) return;	// no subs; return;
			for (int i = 0; i < sitelinks_len; i++) { // iterate sitelinks
				Wdata_sitelink_itm sitelink = (Wdata_sitelink_itm)sitelinks.Get_at(i);
				byte[] sitelink_site = sitelink.Site(), sitelink_ttl = sitelink.Name();
				ns_parser.Find(ns_parser_rslt, sitelink_site, sitelink_ttl);
				int sitelink_ns = ns_parser_rslt.Ns_id();
				if (sitelink_ns != Xow_ns_.Tid__main)	// ttl not in main; chop off ns portion; EX:Aide:French_title -> French_title
					sitelink_ttl = Bry_.Mid(sitelink_ttl, ns_parser_rslt.Ttl_bgn(), sitelink_ttl.length);
				sitelink_ttl = wiki.Lang().Case_mgr().Case_build_1st_upper(tmp_bfr, sitelink_ttl, 0, sitelink_ttl.length);
				tbl.Insert_cmd_by_batch(sitelink.Site(), sitelink_ns, Xoa_ttl.Replace_spaces(sitelink_ttl), qid);	// NOTE: always convert spaces to underscores; EX: "A B" -> "A_B" DATE:2015-04-21
			}
		}
	}
	public void Qid__end() {
		tbl.Insert_end();
		tbl.Create_idx();
	}
	public static Xow_db_file Make_wbase_db(Xow_db_mgr db_mgr) {
		boolean db_is_all_or_few = db_mgr.Props().Layout_text().Tid_is_all_or_few();
		Xow_db_file wbase_db = db_is_all_or_few
			? db_mgr.Db__core()
			: db_mgr.Dbs__make_by_tid(Xow_db_file_.Tid__wbase);
		if (db_is_all_or_few)
			db_mgr.Db__wbase_(wbase_db);
		return wbase_db;
	}
}
