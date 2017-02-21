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
package gplx.gfml; import gplx.*;
class GfmlTkn_mok {
	public String Raw() {return raw;} public GfmlTkn_mok Raw_(String v) {raw = v; return this;} private String raw;
	public List_adp Subs() {return list;} List_adp list = List_adp_.New();
	public GfmlTkn_mok Subs_(GfmlTkn_mok... ary) {
		for (GfmlTkn_mok itm : ary)
			list.Add(itm);
		return this;
	}
	// statics
	public static GfmlTkn_mok new_() {return new GfmlTkn_mok();} GfmlTkn_mok() {}
	public static GfmlTkn_mok gfmlNde_(GfmlNde nde) {
		GfmlTkn_mok rv = new GfmlTkn_mok();
		InitItm(rv, nde);
		return rv;
	}
	static void InitItm(GfmlTkn_mok rv, GfmlItm owner) {
		for (int i = 0; i < owner.SubObjs_Count(); i++) {
			GfmlObj subTkn = (GfmlObj)owner.SubObjs_GetAt(i);
			GfmlTkn_mok subMok = GfmlTkn_mok.new_();
			rv.Subs_(subMok);
			GfmlItm subItm = GfmlItm_.as_(subTkn);
			if (subItm != null)
				InitItm(subMok, subItm);
			else
				InitTkn(subMok, (GfmlTkn)subTkn);
		}
	}
	static void InitTkn(GfmlTkn_mok mok, GfmlTkn tkn) {
		if (tkn.SubTkns().length == 0)	// leafTkn; no subs; simply set
			mok.Raw_(tkn.Raw());			
		else {							// compTkn; createTkn and iterate
			for (int i = 0; i < tkn.SubTkns().length; i++) {
				GfmlTkn subTkn = (GfmlTkn)tkn.SubTkns()[i];
				GfmlTkn_mok subMok = GfmlTkn_mok.new_();
				InitTkn(subMok, subTkn);
				mok.Subs_(subMok);
			}
		}
	}
	public static GfmlTkn_mok[] Xto_bry(String... ary) {
		GfmlTkn_mok[] rv = new GfmlTkn_mok[ary.length];
		for (int i = 0; i < rv.length; i++) 
			rv[i] = GfmlTkn_mok.new_().Raw_(ary[i]);
		return rv;
	}
	public static void tst(TfdsTstr_fxt tstr, GfmlTkn_mok expd, GfmlTkn_mok actl) {
		expd = NullObj(expd); actl = NullObj(actl);
		if (expd.Raw() != null) tstr.Eq_str(expd.Raw(), actl.Raw(), "raw");
		int max = tstr.List_Max(expd.Subs(), actl.Subs());
		for (int i = 0; i < max; i++) {
			GfmlTkn_mok expdSub = (GfmlTkn_mok)tstr.List_FetchAtOrNull(expd.Subs(), i);
			GfmlTkn_mok actlSub = (GfmlTkn_mok)tstr.List_FetchAtOrNull(actl.Subs(), i);
			tstr.SubName_push(Int_.To_str(i));
			tst(tstr, expdSub, actlSub);
			tstr.SubName_pop();
		}
	}
	static GfmlTkn_mok NullObj(GfmlTkn_mok v) {return v == null ? GfmlTkn_mok.new_().Raw_(String_.Null_mark) : v;}
}
