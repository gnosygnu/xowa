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
package gplx.xowa.xtns.wbases; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.primitives.*;
import gplx.langs.jsons.*;
import gplx.xowa.langs.*;
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.parsers.*;
public class Wdata_doc {
	private final Wdata_wiki_mgr mgr;
	public Wdata_doc(Wdata_wiki_mgr mgr, Json_doc jdoc, byte[] qid) {
		this.mgr = mgr; this.jdoc = jdoc; this.qid = qid;
	}
	public byte[] Qid() {return qid;} private final byte[] qid;
	public Json_doc Jdoc() {return jdoc;} private final Json_doc jdoc;
	public int Jdoc_size() {return jdoc == null ? 1 : jdoc.Src().length;}
	public byte[][] Sort_langs() {return sort_langs;} public void Sort_langs_(byte[][] v) {sort_langs = v;} private byte[][] sort_langs = Bry_.Ary_empty;

	// NOTE: lazy instantiation b/c we don't want to parse entire json unless called; particulary necessary for {{#property}} calls;
	public Ordered_hash Slink_list() {if (slink_list == null) slink_list = mgr.Wdoc_parser(jdoc).Parse_sitelinks(qid, jdoc);         return slink_list;} private Ordered_hash slink_list;
	public Ordered_hash Label_list() {if (label_list == null) label_list = mgr.Wdoc_parser(jdoc).Parse_langvals(qid, jdoc, Bool_.Y); return label_list;} private Ordered_hash label_list;
	public Ordered_hash Descr_list() {if (descr_list == null) descr_list = mgr.Wdoc_parser(jdoc).Parse_langvals(qid, jdoc, Bool_.N); return descr_list;} private Ordered_hash descr_list;
	public Ordered_hash Alias_list() {if (alias_list == null) alias_list = mgr.Wdoc_parser(jdoc).Parse_aliases(qid, jdoc);           return alias_list;} private Ordered_hash alias_list;
	public Ordered_hash Claim_list() {if (claim_list == null) claim_list = mgr.Wdoc_parser(jdoc).Parse_claims(qid, jdoc);            return claim_list;} private Ordered_hash claim_list;

	// various getters
	public Wbase_claim_grp Get_claim_grp_or_null(int pid) {
		Object o = this.Claim_list().Get_by(Int_obj_ref.New(pid));
		return (Wbase_claim_grp)o;
	}
	public byte[] Get_label_bry_or_null(byte[] lang_key) {
		Wdata_langtext_itm itm = (Wdata_langtext_itm)this.Label_list().Get_by(lang_key);
		return itm == null ? null : itm.Text();
	}
	public Wdata_langtext_itm Get_label_itm_or_null(Xol_lang_itm lang) {return Get_langtext_itm_or_null(this.Label_list(), lang);}
	public Wdata_langtext_itm Get_descr_itm_or_null(Xol_lang_itm lang) {return Get_langtext_itm_or_null(this.Descr_list(), lang);}
	public Wdata_sitelink_itm Get_slink_itm_or_null(byte[] abrv_wm)    {return (Wdata_sitelink_itm)this.Slink_list().Get_by(abrv_wm);}

	// helper method
	private Wdata_langtext_itm Get_langtext_itm_or_null(Ordered_hash hash, Xol_lang_itm lang) {
		// get itm by lang's key
		Wdata_langtext_itm itm = (Wdata_langtext_itm)hash.Get_by(lang.Key_bry());
		if (itm != null) return itm;

		// loop over fallback_langs
		byte[][] fallback_langs = lang.Fallback_bry_ary();	// NOTE: en is currently automatically being added by Xol_lang_itm
		int len = fallback_langs.length;
		for (int i = 0; i < len; i++) {
			byte[] lang_key = fallback_langs[i];
			Object itm_obj = hash.Get_by(lang_key);
			if (itm_obj != null) {
				return (Wdata_langtext_itm)itm_obj;
			}
		}
		return null;
	}
	public Wdata_doc Ctor_by_test(Ordered_hash slink_list, Ordered_hash label_list, Ordered_hash descr_list, Ordered_hash alias_list, Ordered_hash claim_list) {// TEST
		this.slink_list = slink_list; this.label_list = label_list; this.descr_list = descr_list; this.alias_list = alias_list; this.claim_list = claim_list;
		return this;
	}
}
