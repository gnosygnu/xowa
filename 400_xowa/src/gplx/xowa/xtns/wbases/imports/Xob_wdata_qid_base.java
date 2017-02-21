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
package gplx.xowa.xtns.wbases.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.langs.jsons.*; import gplx.core.ios.*; import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.parsers.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;
import gplx.xowa.bldrs.wms.sites.*;
public abstract class Xob_wdata_qid_base extends Xob_itm_dump_base implements Xob_page_wkr, Gfo_invk {
	private final    Object thread_lock = new Object();
	private Json_parser parser; private Xob_wbase_ns_parser ns_parser; private final    Xob_wbase_ns_parser_rslt ns_parser_rslt = new Xob_wbase_ns_parser_rslt();
	public Xob_wdata_qid_base Ctor(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki); return this;}
	public abstract String Page_wkr__key();
	public abstract void Qid_bgn();
	public abstract void Qid_add(byte[] wiki_key, int ns_id, byte[] ttl, byte[] qid);
	public abstract void Qid_end();
	public void Page_wkr__bgn() {
		this.Init_dump(this.Page_wkr__key(), wiki.Tdb_fsys_mgr().Site_dir().GenSubDir_nest("data", "qid"));	// NOTE: must pass in correct make_dir in order to delete earlier version (else make_dirs will append)
		this.parser = bldr.App().Wiki_mgr().Wdata_mgr().Jdoc_parser();
		this.ns_parser = new Xob_wbase_ns_parser(bldr.App().Fsys_mgr().Cfg_site_meta_fil());
		this.Qid_bgn();
	}
	public void Page_wkr__run(Xowd_page_itm page) {
		if (page.Ns_id() != Xow_ns_.Tid__main) return;	// qid pages are only in the Main Srch_rslt_cbk
		Json_doc jdoc = parser.Parse(page.Text()); 
		if (jdoc == null) {bldr.Usr_dlg().Warn_many("", "", "json is invalid: ns=~{0} id=~{1}", page.Ns_id(), String_.new_u8(page.Ttl_page_db())); return;}
		this.Parse_jdoc(jdoc);
	}
	public void Page_wkr__run_cleanup() {}
	public void Parse_jdoc(Json_doc jdoc) {
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
				this.Qid_add(sitelink.Site(), sitelink_ns, Xoa_ttl.Replace_spaces(sitelink_ttl), qid);	// NOTE: always convert spaces to underscores; EX: "A B" -> "A_B" DATE:2015-04-21
			}
		}
	}
	public void Page_wkr__end() {
		this.Qid_end();
		// wiki.Data__core_mgr().Db__wbase().Tbl__cfg().Insert_int("", "", 1);
	}
}
