/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.xtns.wdatas.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.json.*; import gplx.ios.*; import gplx.xowa.xtns.wdatas.core.*; import gplx.xowa.xtns.wdatas.parsers.*; import gplx.xowa.wikis.data.tbls.*;
public abstract class Xob_wdata_qid_base extends Xob_itm_dump_base implements Xobd_wkr, GfoInvkAble {
	private Json_parser parser; private Xob_wbase_ns_parser ns_parser; private final Xob_wbase_ns_parser_rslt ns_parser_rslt = new Xob_wbase_ns_parser_rslt();
	public Xob_wdata_qid_base Ctor(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki); return this;}
	public abstract String Wkr_key();
	public abstract void Qid_bgn();
	public abstract void Qid_add(byte[] wiki_key, int ns_id, byte[] ttl, byte[] qid);
	public abstract void Qid_end();
	public void Wkr_ini(Xob_bldr bldr) {}
	public void Wkr_bgn(Xob_bldr bldr) {
		this.Init_dump(this.Wkr_key(), wiki.Tdb_fsys_mgr().Site_dir().GenSubDir_nest("data", "qid"));	// NOTE: must pass in correct make_dir in order to delete earlier version (else make_dirs will append)
		this.parser = bldr.App().Wiki_mgr().Wdata_mgr().Jdoc_parser();
		this.ns_parser = new Xob_wbase_ns_parser(Xowmf_site_tbl.Get_conn_or_new(bldr.App().Fsys_mgr().Root_dir()));
		this.Qid_bgn();
	}
	public void Wkr_run(Xowd_page_itm page) {
		if (page.Ns_id() != Xow_ns_.Id_main) return;	// qid pages are only in the Main namespace
		Json_doc jdoc = parser.Parse(page.Text()); 
		if (jdoc == null) {bldr.Usr_dlg().Warn_many("", "", "json is invalid: ns=~{0} id=~{1}", page.Ns_id(), String_.new_utf8_(page.Ttl_page_db())); return;}
		Wdata_doc_parser wdoc_parser = app.Wiki_mgr().Wdata_mgr().Wdoc_parser(jdoc);
		byte[] qid = wdoc_parser.Parse_qid(jdoc);
		Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
		OrderedHash sitelinks = wdoc_parser.Parse_sitelinks(qid, jdoc);
		int sitelinks_len = sitelinks.Count(); if (sitelinks_len == 0) return;	// no subs; return;
		for (int i = 0; i < sitelinks_len; i++) { // iterate sitelinks
			Wdata_sitelink_itm sitelink = (Wdata_sitelink_itm)sitelinks.FetchAt(i);
			byte[] sitelink_site = sitelink.Site(), sitelink_ttl = sitelink.Name();
			ns_parser.Find(ns_parser_rslt, sitelink_site, sitelink_ttl);
			int sitelink_ns = ns_parser_rslt.Ns_id();
			if (sitelink_ns != Xow_ns_.Id_main)	// ttl not in main; chop off ns portion; EX:Aide:French_title -> French_title
				sitelink_ttl = Bry_.Mid(sitelink_ttl, ns_parser_rslt.Ttl_bgn(), sitelink_ttl.length);
			sitelink_ttl = wiki.Lang().Case_mgr().Case_build_1st_upper(tmp_bfr, sitelink_ttl, 0, sitelink_ttl.length);
			this.Qid_add(sitelink.Site(), sitelink_ns, Xoa_ttl.Replace_spaces(sitelink_ttl), qid);	// NOTE: always convert spaces to underscores; EX: "A B" -> "A_B" DATE:2015-04-21
		}
	}
	public void Wkr_end() {
		this.Qid_end();
		// wiki.Data_mgr__core_mgr().Db__wbase().Tbl__cfg().Insert_int("", "", 1);
	}
	public void Wkr_print() {}
}
