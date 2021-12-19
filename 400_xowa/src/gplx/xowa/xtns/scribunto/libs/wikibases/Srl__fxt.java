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
package gplx.xowa.xtns.scribunto.libs.wikibases;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.KeyVal;
import gplx.types.basics.utls.ClassUtl;
import gplx.xowa.xtns.scribunto.libs.*;
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
		KeyVal[] actl = Scrib_lib_wikibase_srl.Srl(prop_mgr, wdoc_bldr.Xto_wdoc(), header_enabled, base0, BryUtl.NewU8("Test_page"));
		GfoTstr.EqLines(expd, StringUtl.SplitLinesNl(Xto_str(actl)));
		return this;
	}
	public Srl__fxt Test(Wdata_doc wdoc, String... expd) {return Test(false, wdoc, expd);}
	public Srl__fxt Test(boolean base0, Wdata_doc wdoc, String... expd) {
		KeyVal[] actl = Scrib_lib_wikibase_srl.Srl(prop_mgr, wdoc, header_enabled, base0, BryUtl.NewU8("Test_page"));
		GfoTstr.EqLines(expd, StringUtl.SplitLinesNl(Xto_str(actl)));
		return this;
	}
	private String Xto_str(KeyVal[] ary) {
		BryWtr bfr = BryWtr.New();
		Xto_str(bfr, ary, 0);
		return bfr.ToStrAndClear();
	}
	private void Xto_str(BryWtr bfr, KeyVal[] ary, int depth) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			KeyVal kv = ary[i];
			Xto_str(bfr, kv, depth);
		}
	}
	private void Xto_str(BryWtr bfr, KeyVal kv, int depth) {
		bfr.AddByteRepeat(AsciiByte.Space, depth * 2);
		bfr.AddStrU8(kv.KeyToStr()).AddByte(AsciiByte.Colon);
		Object kv_val = kv.Val();
		if		(kv_val == null) 							{bfr.AddStrA7("null").AddByteNl(); return;}
		Class<?> kv_val_cls = kv_val.getClass();
		if 	(ClassUtl.Eq(kv_val_cls, KeyVal[].class)) 	{bfr.AddByteNl(); Xto_str(bfr, (KeyVal[])kv_val, depth + 1);}
		else if (ClassUtl.Eq(kv_val_cls, KeyVal[].class)) 	{bfr.AddByteNl(); Xto_str(bfr, (KeyVal)kv_val, depth + 1);}
		else bfr.AddByte(AsciiByte.Apos).AddStrU8(ObjectUtl.ToStrOrEmpty(kv_val)).AddByte(AsciiByte.Apos).AddByteNl();
	}
}
