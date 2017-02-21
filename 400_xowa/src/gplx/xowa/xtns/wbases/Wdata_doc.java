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
	private Wdata_wiki_mgr mgr; private Int_obj_ref tmp_key;
	public Wdata_doc(byte[] qid, Wdata_wiki_mgr mgr, Json_doc jdoc) {this.qid = qid; this.mgr = mgr; this.jdoc = jdoc;}
	public Wdata_doc(byte[] qid, Ordered_hash slink_list, Ordered_hash label_list, Ordered_hash descr_list, Ordered_hash alias_list, Ordered_hash claim_list) {	// TEST
		this.qid = qid;
		this.slink_list = slink_list; this.label_list = label_list; this.descr_list = descr_list; this.alias_list = alias_list; this.claim_list = claim_list;
	}
	public Json_doc Jdoc() {return jdoc;} private Json_doc jdoc;
	public byte[] Qid() {return qid;} private byte[] qid;
	public byte[][] Sort_langs() {return sort_langs;} public void Sort_langs_(byte[][] v) {sort_langs = v;} private byte[][] sort_langs = Bry_.Ary_empty;
	public Ordered_hash Slink_list()	{if (slink_list == null) slink_list = mgr.Wdoc_parser(jdoc).Parse_sitelinks(qid, jdoc);			return slink_list;} private Ordered_hash slink_list;
	public Ordered_hash Label_list()	{if (label_list == null) label_list = mgr.Wdoc_parser(jdoc).Parse_langvals(qid, jdoc, Bool_.Y); return label_list;} private Ordered_hash label_list;
	public Ordered_hash Descr_list()	{if (descr_list == null) descr_list = mgr.Wdoc_parser(jdoc).Parse_langvals(qid, jdoc, Bool_.N); return descr_list;} private Ordered_hash descr_list;
	public Ordered_hash Alias_list()	{if (alias_list == null) alias_list = mgr.Wdoc_parser(jdoc).Parse_aliases(qid, jdoc);			return alias_list;} private Ordered_hash alias_list;
	public Ordered_hash Claim_list()	{if (claim_list == null) claim_list = mgr.Wdoc_parser(jdoc).Parse_claims(qid, jdoc);			return claim_list;} private Ordered_hash claim_list;
	public Wbase_claim_grp Claim_list_get(int pid) {
		if (tmp_key == null) tmp_key = Int_obj_ref.New_neg1();			
		Object o = this.Claim_list().Get_by(tmp_key.Val_(pid));
		return (Wbase_claim_grp)o;
	}	
	public byte[] Label_list__get(byte[] lang_key) {return Lang_text_list__get(this.Label_list(), lang_key);}
	public byte[] Label_list__get_or_fallback(Xol_lang_itm lang) {return Lang_text_list__get_or_fallback(this.Label_list(), lang);}
	public byte[] Descr_list__get_or_fallback(Xol_lang_itm lang) {return Lang_text_list__get_or_fallback(this.Descr_list(), lang);}
	public byte[] Slink_list__get_or_fallback(byte[] abrv_wm) {
		Wdata_sitelink_itm rv = (Wdata_sitelink_itm)this.Slink_list().Get_by(abrv_wm);
		return rv == null ? null : rv.Name();
	}
	private byte[] Lang_text_list__get(Ordered_hash hash, byte[] lang_key) {
		Object rv_obj = hash.Get_by(lang_key); if (rv_obj == null) return null;
		Wdata_langtext_itm rv = (Wdata_langtext_itm)rv_obj;
		return rv.Text();
	}
	public byte[] Lang_text_list__get_or_fallback(Ordered_hash lang_text_list, Xol_lang_itm lang) {
		byte[] rv = Lang_text_list__get(lang_text_list, lang.Key_bry()); if (rv != null) return rv;
		byte[][] ary = lang.Fallback_bry_ary();	// NOTE: en is currently automatically being added by Xol_lang_itm
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			byte[] lang_key = ary[i];
			Object itm_obj = lang_text_list.Get_by(lang_key);
			if (itm_obj != null) {
				Wdata_langtext_itm itm = (Wdata_langtext_itm)itm_obj;
				return itm.Text();
			}
		}
		return null;
	}
}
