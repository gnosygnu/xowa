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
import gplx.dbs.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.domains.*;
import gplx.xowa.bldrs.wms.sites.*;
class Xob_wbase_ns_parser {
	private final    Hash_adp_bry ns_mgr_hash = Hash_adp_bry.cs();
	private final    Site_core_db core_db;
	public Xob_wbase_ns_parser(Io_url url) {
		this.core_db = new Site_core_db(url);
	}
	public void Find(Xob_wbase_ns_parser_rslt rv, byte[] wiki_abrv, byte[] ttl) {	// enwiki, Category:Abc
		Xow_ns_mgr ns_mgr = (Xow_ns_mgr)ns_mgr_hash.Get_by_bry(wiki_abrv);
		rv.Init(Xow_ns_.Tid__main, 0);	// default to Main ns
		int ttl_len = ttl.length;
		int colon_pos = Bry_find_.Find_fwd(ttl, Byte_ascii.Colon, 0, ttl_len); if (colon_pos == Bry_find_.Not_found) return;
		if (ns_mgr == null) {			// ns_mgr not found; load from db
			wiki_abrv = Bry_.Replace(wiki_abrv, Byte_ascii.Underline, Byte_ascii.Dash);
			byte[] wiki_domain = Xow_abrv_wm_.Parse_to_domain_bry(wiki_abrv);
			ns_mgr = core_db.Load_namespace(wiki_domain);
			if (ns_mgr.Count() == 0) {Xoa_app_.Usr_dlg().Warn_many("", "", "wbase.ns_parser:no ns found; abrv=~{0}", wiki_abrv); return;}
			ns_mgr_hash.Add_bry_obj(wiki_abrv, ns_mgr);
		}
		Xow_ns ns = ns_mgr.Names_get_or_null(ttl, 0, colon_pos); if (ns == null) return; // not a ns; EX: "No_namespace:Page_title"
		rv.Init(ns.Id(), colon_pos + 1);
	}		
}
class Xob_wbase_ns_parser_rslt {
	public int Ns_id() {return ns_id;} private int ns_id;
	public int Ttl_bgn() {return ttl_bgn;} private int ttl_bgn;
	public void Init(int ns_id, int ttl_bgn) {
		this.ns_id = ns_id; this.ttl_bgn = ttl_bgn;
	}
}
