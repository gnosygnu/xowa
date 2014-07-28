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
import gplx.json.*; import gplx.ios.*;
public abstract class Xob_wdata_qid_base extends Xob_itm_dump_base implements Xobd_wkr, GfoInvkAble {
	public Xob_wdata_qid_base Ctor(Xob_bldr bldr, Xow_wiki wiki) {this.Cmd_ctor(bldr, wiki); return this;}
	public abstract String Wkr_key();
	public abstract void Qid_bgn();
	public abstract void Qid_add(byte[] wiki_key, Xow_ns ns, byte[] ttl, byte[] qid);
	public abstract void Qid_end();
	public void Wkr_ini(Xob_bldr bldr) {}
	public void Wkr_bgn(Xob_bldr bldr) {
		this.Init_dump(this.Wkr_key(), wiki.Fsys_mgr().Site_dir().GenSubDir_nest("data", "qid"));	// NOTE: must pass in correct make_dir in order to delete earlier version (else make_dirs will append)
		parser = bldr.App().Wiki_mgr().Wdata_mgr().Parser();
		this.Qid_bgn();
	}	Json_parser parser;
	public void Wkr_run(Xodb_page page) {
		if (page.Ns_id() != Xow_ns_.Id_main) return;	// qid pages are only in the Main namespace
		Json_doc doc = parser.Parse(page.Text()); 
		if (doc == null) {
			bldr.Usr_dlg().Warn_many(GRP_KEY, "json.invalid", "json is invalid: ns=~{0} id=~{1}", page.Ns_id(), String_.new_utf8_(page.Ttl_wo_ns()));
			return;
		}
		byte[] qid = Wdata_doc_.Entity_extract(doc);
		Json_itm_nde links_nde = Json_itm_nde.cast_(doc.Get_grp(Wdata_doc_consts.Key_atr_links_bry)); if (links_nde == null) return; // no links; ignore
		int len = links_nde.Subs_len(); if (len == 0) return;	// no subs; return;
		Wdata_qid_data data_core = null;
		links.Clear();			
		for (int i = 0; i < len; i++) {		// iterate links; find data_core (hopefully enwiki)
			Json_itm_kv kv = (Json_itm_kv)links_nde.Subs_get_at(i);
			byte[] xwiki_key = kv.Key().Data_bry();
			byte[] ttl_bry = Wdata_doc_.Link_extract(kv);
			Wdata_qid_data data = new Wdata_qid_data(xwiki_key, ttl_bry);
			links.Add(data);
			if (i == 0) data_core = data;
			if (Bry_.Eq(xwiki_key, Xwiki_key_en)) data_core = data;
		}

		Xoa_ttl core_ttl = Xoa_ttl.parse_(wiki, data_core.Ttl());	// NOTE: parse ttl to get ns; this may still be inaccurate as it is using wikidata's ns, not enwiki's;; DATE:2014-07-23
		Xow_ns core_ns = core_ttl.Ns();
		boolean core_ns_is_main = core_ns.Id_main();
		
		for (int i = 0; i < len; i++) { // iterate links again; do parsing, but assume any ns is same as enwiki
			Wdata_qid_data data = (Wdata_qid_data)links.FetchAt(i);
			byte[] data_ttl_bry = data.Ttl();
			byte[] actl_ttl = null;
			if (core_ns_is_main) {
				Xoa_ttl data_ttl = Xoa_ttl.parse_(wiki, data_ttl_bry);
				actl_ttl = data_ttl.Page_db();
			}
			else {	// naive extract ttl based on colon; note that upper casing 1st letter is not needed, b/c ttls should be already cased correctly; EX: Aide:Page_a does not need uppercasing of "P"
				int colon_pos = Bry_finder.Find_fwd(data_ttl_bry, Byte_ascii.Colon);
				int data_ttl_len = data_ttl_bry.length;
				if (colon_pos == Bry_.NotFound || colon_pos == data_ttl_len - 1) {
					bldr.App().Usr_dlg().Log_many(GRP_KEY, "ns_not_found", "namespace not found; ~{0} ~{1} ~{2}", String_.new_ascii_(qid), core_ns.Name_str(), String_.new_utf8_(data_ttl_bry));
					actl_ttl = data_ttl_bry;
				}
				else
					actl_ttl = Bry_.Mid(data_ttl_bry, colon_pos + 1, data_ttl_len);
			}
			this.Qid_add(data.Xwiki_key(), core_ns, actl_ttl, qid);
		}
	}	ListAdp links = ListAdp_.new_(); Xob_bz2_file tmp_xwiki = new Xob_bz2_file(); Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	public void Wkr_end() {
		this.Qid_end();
	}	static final byte[] Xwiki_key_en = Bry_.new_ascii_("enwiki");

	public void Wkr_print() {}
	private static final String GRP_KEY = "xowa.wdata.qid_wkr";
}
class Wdata_qid_data {
	public Wdata_qid_data(byte[] xwiki_key, byte[] ttl) {this.xwiki_key = xwiki_key; this.ttl = ttl;}
	public byte[] Xwiki_key() {return xwiki_key;} private byte[] xwiki_key;
	public byte[] Ttl() {return ttl;} private byte[] ttl;
}
class Wdata_idx_bldr_qid extends Wdata_idx_mgr_base {
	public Wdata_idx_bldr_qid Ctor(Xob_wdata_qid_base wkr, Xob_bldr bldr, Xow_wiki wiki, int dump_fil_len) {super.Ctor(wkr, bldr, wiki, dump_fil_len); return this;}
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
