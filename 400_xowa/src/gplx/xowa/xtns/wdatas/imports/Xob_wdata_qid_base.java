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
	public Xob_wdata_qid_base Ctor(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki); return this;}
	public abstract String Wkr_key();
	public abstract void Qid_bgn();
	public abstract void Qid_add(byte[] wiki_key, Xow_ns ns, byte[] ttl, byte[] qid);
	public abstract void Qid_end();
	public void Wkr_ini(Xob_bldr bldr) {}
	public void Wkr_bgn(Xob_bldr bldr) {
		this.Init_dump(this.Wkr_key(), wiki.Tdb_fsys_mgr().Site_dir().GenSubDir_nest("data", "qid"));	// NOTE: must pass in correct make_dir in order to delete earlier version (else make_dirs will append)
		parser = bldr.App().Wiki_mgr().Wdata_mgr().Jdoc_parser();
		this.Qid_bgn();
	}	private Json_parser parser;
	public void Wkr_run(Xowd_page_itm page) {
		if (page.Ns_id() != Xow_ns_.Id_main) return;	// qid pages are only in the Main namespace
		Json_doc jdoc = parser.Parse(page.Text()); 
		if (jdoc == null) {
			bldr.Usr_dlg().Warn_many(GRP_KEY, "json.invalid", "json is invalid: ns=~{0} id=~{1}", page.Ns_id(), String_.new_utf8_(page.Ttl_page_db()));
			return;
		}
		Wdata_doc_parser wdoc_parser = app.Wiki_mgr().Wdata_mgr().Wdoc_parser(jdoc);
		byte[] qid = wdoc_parser.Parse_qid(jdoc);			
		OrderedHash sitelinks = wdoc_parser.Parse_sitelinks(qid, jdoc);
		int sitelinks_len = sitelinks.Count(); if (sitelinks_len == 0) return;	// no subs; return;
		Wdata_sitelink_itm main_sitelink = null;		// SEE:NOTE_1:non-english non-main titles
		for (int i = 0; i < sitelinks_len; i++) {		// iterate links; find main_sitelink (hopefully enwiki)
			Wdata_sitelink_itm sitelink = (Wdata_sitelink_itm)sitelinks.FetchAt(i);
			byte[] xwiki_key = sitelink.Site();
			if (	i == 0								// assume 1st item is mainlink_data; needed for null checks; also, if no links are enwiki, then hope / assume that first link will be to an article in correct ns
				||	Bry_.Eq(xwiki_key, Xwiki_key_en)	// assume enwiki item is to correct ns
				)
				main_sitelink = sitelink;
		}

		Xoa_ttl core_ttl = Xoa_ttl.parse_(wiki, main_sitelink.Name());	// NOTE: parse ttl to get ns; this may still be inaccurate as it is using wikidata's ns, not enwiki's; DATE:2014-07-23
		Xow_ns core_ns = core_ttl.Ns();
		boolean core_ns_is_main = core_ns.Id_main();
		
		for (int i = 0; i < sitelinks_len; i++) { // iterate links again; do parsing, but assume any ns is same as enwiki
			Wdata_sitelink_itm sitelink = (Wdata_sitelink_itm)sitelinks.FetchAt(i);
			byte[] data_ttl_bry = sitelink.Name();
			byte[] actl_ttl = null;
			if (core_ns_is_main) {
				Xoa_ttl data_ttl = Xoa_ttl.parse_(wiki, data_ttl_bry);
				actl_ttl = data_ttl.Page_db();
			}
			else {	// naive extract ttl based on colon; note that upper casing 1st letter is not needed, b/c ttls should be already cased correctly; EX: Aide:Page_a does not need uppercasing of "P"
				int colon_pos = Bry_finder.Find_fwd(data_ttl_bry, Byte_ascii.Colon);
				int data_ttl_len = data_ttl_bry.length;
				if (colon_pos == Bry_.NotFound || colon_pos == data_ttl_len - 1) {
					bldr.App().Usr_dlg().Log_many(GRP_KEY, "ns_not_found", "namespace not found; qid=~{0} ns=~{1} ttl=~{2}", qid, core_ns.Name_str(), data_ttl_bry);
					actl_ttl = data_ttl_bry;
				}
				else
					actl_ttl = Bry_.Mid(data_ttl_bry, colon_pos + 1, data_ttl_len);
			}
			this.Qid_add(sitelink.Site(), core_ns, actl_ttl, qid);
		}
	}
	public void Wkr_end() {
		this.Qid_end();
	}	static final byte[] Xwiki_key_en = Bry_.new_ascii_("enwiki");

	public void Wkr_print() {}
	private static final String GRP_KEY = "xowa.wdata.qid_wkr";
}
class Wdata_idx_bldr_qid extends Wdata_idx_mgr_base {
	public Wdata_idx_bldr_qid Ctor(Xob_wdata_qid_base wkr, Xob_bldr bldr, Xowe_wiki wiki, int dump_fil_len) {super.Ctor(wkr, bldr, wiki, dump_fil_len); return this;}
	public void Add(String wiki_key, Xow_ns ns, byte[] ttl, byte[] qid) {
		Wdata_idx_wtr wtr = Get_or_new(wiki_key, ns);
		wtr.Write(ttl, qid);
	}	
	Wdata_idx_wtr Get_or_new(String wiki_key, Xow_ns ns) {
		String wtr_key = wiki_key + "|" + ns.Num_str();
		Object rv = hash.Fetch(wtr_key);
		if (rv == null) {
			Wdata_idx_wtr wtr = Wdata_idx_wtr.new_qid_(wiki, wiki_key, ns.Num_str(), dump_fil_len);
			hash.Add(wtr_key, wtr);
			return wtr;
		}
		return (Wdata_idx_wtr)rv;
	}
}
/*
NOTE_1:non-english non-main titles
The problem is that sitelinks have full titles which namespace names. EX:frwiki|Aide:Page1
. there is no way to know that "Aide" is "ns 6" in frwiki without loading up the ns names of frwiki
. furthermore, ttls can have ":" in the main namespace; EX:frwiki|Aidex:Page1 is actually in ns 0
The ideal approach is to load up a ns_mgr for every wiki. this is problematic:
. memory space: potentially 800+ wikis
. data availability: wikidata dump does not have ns names for other wikis, so they need to be provided beforehand (in a new xowa.gfs file)
So, for now, employ the following workaround:
. find the ns of the baseline sitelink: (a) enwiki; (b) wiki; (c) 1st (not commons)
. parse each sitelink
.. if a sitelink has no ":", it must be in the main ns
.. if a sitelink has a ":"
... try to parse import wikidata.org's ns_mgr. this will find simple ns like "Category", "File", etc.
... if no ns, use the ns of the baseline sitelink
Note that this approach still fails in following situations
. baseline sitelink is in non-English ns.*; EX: fr.w|Aide:Page1 en.d|Help:Page1; fr.w is identified as main ns but en.d is put in Help ns
. the wrong sitelink is chosen as baseline; EX:: en.w|Category:Page1 de.w|Page1 es.w|Page1 ru.w|MainNs:Page1; ru.w is put in Category Ns
. a non-English title happens to have an English ns name in its main ns; EX:fr.w|Category:Page1; fr.w should be in Main ns (since no "Category" ns name in fr.w), but put in Category
*/
