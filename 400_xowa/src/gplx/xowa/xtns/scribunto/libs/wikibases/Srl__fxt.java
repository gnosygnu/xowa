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
package gplx.xowa.xtns.scribunto.libs.wikibases; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.libs.*;
import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.stores.*; import gplx.xowa.xtns.wbases.claims.itms.*;
public class Srl__fxt {
	private Wdata_doc_bldr wdoc_bldr;
	private Wbase_prop_mgr prop_mgr;
	public void Clear() {
		wdata_fxt = new Wdata_wiki_mgr_fxt();
		wdata_fxt.Init();
		wdoc_bldr = wdata_fxt.Wdoc_bldr("q2");
		header_enabled = false;
		this.prop_mgr = wdata_fxt.App().Wiki_mgr().Wdata_mgr().Prop_mgr();
	}
	public Wdata_wiki_mgr_fxt Wdata_fxt() {return wdata_fxt;} private Wdata_wiki_mgr_fxt wdata_fxt;
	private boolean header_enabled;
	public Srl__fxt Init_header_enabled_y_() {header_enabled = true; return this;}
	public Srl__fxt Init_label(String lang, String label) {
		wdoc_bldr.Add_label(lang, label);
		return this;
	}
	public Srl__fxt Init_description(String lang, String description) {
		wdoc_bldr.Add_description(lang, description);
		return this;
	}
	public Srl__fxt Init_link(String xwiki, String val) {
		wdoc_bldr.Add_sitelink(xwiki, val);
		return this;
	}
	public Srl__fxt Init_alias(String lang, String... ary) {
		wdoc_bldr.Add_alias(lang, ary);
		return this;
	}
	public Srl__fxt Init_prop(Wbase_claim_base prop) {wdoc_bldr.Add_claims(prop); return this;}
	public Srl__fxt Test(String... expd) {return Test(false, expd);}
	public Srl__fxt Test(boolean base0, String... expd) {
		Keyval[] actl = Scrib_lib_wikibase_srl.Srl(prop_mgr, wdoc_bldr.Xto_wdoc(), header_enabled, base0);
		Tfds.Eq_ary_str(expd, String_.SplitLines_nl(Xto_str(actl)));
		return this;
	}
	public Srl__fxt Test(Wdata_doc wdoc, String... expd) {return Test(false, wdoc, expd);}
	public Srl__fxt Test(boolean base0, Wdata_doc wdoc, String... expd) {
		Keyval[] actl = Scrib_lib_wikibase_srl.Srl(prop_mgr, wdoc, header_enabled, base0);
		Tfds.Eq_ary_str(expd, String_.SplitLines_nl(Xto_str(actl)));
		return this;
	}
	private String Xto_str(Keyval[] ary) {
		Bry_bfr bfr = Bry_bfr_.New();
		Xto_str(bfr, ary, 0);
		return bfr.To_str_and_clear();
	}
	private void Xto_str(Bry_bfr bfr, Keyval[] ary, int depth) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Keyval kv = ary[i];
			Xto_str(bfr, kv, depth);
		}
	}
	private void Xto_str(Bry_bfr bfr, Keyval kv, int depth) {
		bfr.Add_byte_repeat(Byte_ascii.Space, depth * 2);
		bfr.Add_str_u8(kv.Key()).Add_byte(Byte_ascii.Colon);
		Object kv_val = kv.Val();
		if		(kv_val == null) 							{bfr.Add_str_a7("null").Add_byte_nl(); return;}
		Class<?> kv_val_cls = kv_val.getClass();
		if 	(Type_.Eq(kv_val_cls, Keyval[].class)) 	{bfr.Add_byte_nl(); Xto_str(bfr, (Keyval[])kv_val, depth + 1);}
		else if (Type_.Eq(kv_val_cls, Keyval[].class)) 	{bfr.Add_byte_nl(); Xto_str(bfr, (Keyval)kv_val, depth + 1);}
		else bfr.Add_byte(Byte_ascii.Apos).Add_str_u8(Object_.Xto_str_strict_or_empty(kv_val)).Add_byte(Byte_ascii.Apos).Add_byte_nl();
	}
}
