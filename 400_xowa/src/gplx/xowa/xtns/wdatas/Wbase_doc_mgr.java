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
package gplx.xowa.xtns.wdatas; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Wbase_doc_mgr {
	private final    Xoae_app app;
	private final    Wdata_wiki_mgr wbase_mgr;
	private final    Wbase_qid_mgr qid_mgr;
	private final    gplx.xowa.apps.caches.Wdata_doc_cache hash;
	public Wbase_doc_mgr(Xoae_app app, Wdata_wiki_mgr wbase_mgr, Wbase_qid_mgr qid_mgr) {
		this.app = app; this.wbase_mgr = wbase_mgr; this.qid_mgr = qid_mgr;
		this.hash = app.Cache_mgr().Doc_cache();
	}
	public void Enabled_(boolean v) {this.enabled = v;} private boolean enabled;
	public void Clear() {hash.Clear();}
	public void Add(byte[] full_db, Wdata_doc page) {hash.Add(full_db, page);}	// TEST:
	public Wdata_doc Get_by_ttl_or_null(Xowe_wiki wiki, Xoa_ttl ttl) {
		byte[] qid_bry = qid_mgr.Get_or_null(wiki, ttl);	// EX: "enwiki","Earth" -> "Q2"
		return qid_bry == null ? null : this.Get_by_bry_or_null(qid_bry);
	}
	public Wdata_doc Get_by_xid_or_null(byte[] xid) {return Get_by_bry_or_null(Prepend_property_if_needed(xid));}// scribunto passes either p1 or q1; convert p1 to "Property:P1"
	public Wdata_doc Get_by_bry_or_null(byte[] full_db) {	// must be format like "Q2" or "Property:P1"
		Wdata_doc rv = hash.Get_or_null(full_db);
		if (rv == null) {
			byte[] page_src = Load_or_null(full_db); if (page_src == null) return null;	// page not found
			rv = new Wdata_doc(full_db, wbase_mgr, wbase_mgr.Jdoc_parser().Parse(page_src));
			Add(full_db, rv);
		}
		return rv;
	}
	private byte[] Load_or_null(byte[] full_db) {
		if (!enabled) return null;
		Xoa_ttl page_ttl = Xoa_ttl.parse(wbase_mgr.Wdata_wiki(), full_db); if (page_ttl == null) {app.Usr_dlg().Warn_many("", "", "invalid qid for ttl: qid=~{0}", full_db); return null;}
		Xoae_page page_itm = wbase_mgr.Wdata_wiki().Data_mgr().Get_page(page_ttl, false);
		return page_itm.Missing() ? null : page_itm.Data_raw();
	}

	private static byte[] Prepend_property_if_needed(byte[] bry) {
		int len = bry == null ? 0 : bry.length;
		return	len > 1
			&&	Byte_ascii.Case_lower(bry[0]) == Byte_ascii.Ltr_p
			&&	Byte_ascii.Is_num(bry[1])
			?	Bry_.Add(Wdata_wiki_mgr.Ns_property_name_bry, Byte_ascii.Colon_bry, bry)	// if ttl starts with "p#", prepend "Property:" to get "Property:P#"; NOTE: do not ucase P b/c it breaks a test; DATE:2014-02-18
			:	bry;
	}
}
