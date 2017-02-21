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
import gplx.core.strings.*;
interface GfmlItm_mok {
	int ObjType();
}
class GfmlAtr_mok implements GfmlItm_mok {
	public int ObjType() {return GfmlObj_.Type_atr;}
	public String Key() {return key;} public GfmlAtr_mok Key_(String v) {key = v; return this;} private String key;
	public String Val() {return val;} public GfmlAtr_mok Val_(String v) {val = v; return this;} private String val;
	public GfmlAtr XtoGfmlItm() {
		GfmlAtr rv = GfmlAtr.new_(GfmlTkn_.raw_(key), GfmlTkn_.raw_(val), GfmlType_.String);
		return rv;
	}
	public String XtoStrStub() {
		String_bldr sb = String_bldr_.new_();
		sb.Add_kv("key=", key).Add_kv("val=", val);
		return sb.To_str();
	}
        public static final    GfmlAtr_mok Null = new GfmlAtr_mok().Key_(String_.Null_mark).Val_(String_.Null_mark);
	public static GfmlAtr_mok as_(Object obj) {return obj instanceof GfmlAtr_mok ? (GfmlAtr_mok)obj : null;}
        public static GfmlAtr_mok new_(String key, String val) {
		GfmlAtr_mok rv = new GfmlAtr_mok();
		rv.key = key; rv.val = val;
		return rv;
	}	GfmlAtr_mok() {}
}
class GfmlNde_mok implements GfmlItm_mok {
	public int ObjType() {return GfmlObj_.Type_nde;}
	public String Key() {return key;} public GfmlNde_mok Key_(String v) {key = v; return this;} private String key;
	public String Hnd() {return hnd;} public GfmlNde_mok Hnd_(String v) {hnd = v; return this;} private String hnd;
	public String Typ() {return typ;} public GfmlNde_mok Typ_(String v) {typ = v; return this;} private String typ;
	public int ChainId() {return chainId;} public GfmlNde_mok ChainId_(int v) {chainId = v; return this;} int chainId = -1;
	public boolean KeyedSubObj() {return keyed;}
	public GfmlNde_mok KeyedSubObj_() {return KeyedSubObj_(true);}
	public GfmlNde_mok KeyedSubObj_(boolean v) {keyed = v; return this;} private boolean keyed;
	public List_adp Subs() {return subs;}
	public String XtoStrStub() {
		String_bldr sb = String_bldr_.new_();
		sb.Add_kv("key=", key).Add_kv("hnd=", hnd).Add_kv("typ=", typ).Add_kv("subs=", Int_.To_str(subs.Count()));
		return sb.To_str();
	}
	public GfmlNde_mok Subs_(GfmlItm_mok... ary) {
		for (GfmlItm_mok itm : ary)
			subs.Add(itm);
		return this;
	}	List_adp subs = List_adp_.New();

	public GfmlNde_mok Atrk_(String k, String v)	{subs.Add(GfmlAtr_mok.new_(k, v)); return this;}
	public GfmlNde_mok Atru_(String v)				{subs.Add(GfmlAtr_mok.new_(GfmlTkn_.NullVal, v)); return this;}
	public GfmlNde_mok Atrs_(String... ary)	{
		for (String itm : ary)
			subs.Add(GfmlAtr_mok.new_(GfmlTkn_.NullVal, itm));
		return this;
	}
	public GfmlNde XtoGfmlItm(GfmlTypRegy regy) {return XtoGfmlNde(regy, this);}
	static GfmlNde XtoGfmlNde(GfmlTypRegy regy, GfmlNde_mok ownerMok) {
		String tmpTypeKey = ownerMok.typ == null ? GfmlType_.AnyKey : ownerMok.typ;
		String tmpHnd = ownerMok.hnd == null ? "" : ownerMok.hnd;
		GfmlNde rv = GfmlNde.new_(GfmlTkn_.raw_(tmpHnd), regy.FetchOrNull(tmpTypeKey), ownerMok.key != null);
		rv.ChainId_(ownerMok.chainId);
		if (ownerMok.keyed) rv.KeyedSubObj_(ownerMok.keyed);
		if (ownerMok.key != null) rv.KeyTkn_set(GfmlTkn_.val_(ownerMok.key));
		for (int i = 0; i < ownerMok.subs.Count(); i++) {
			GfmlItm_mok itm = (GfmlItm_mok)ownerMok.subs.Get_at(i);
			if (itm.ObjType() == GfmlObj_.Type_nde) {
				GfmlNde_mok itmMok = (GfmlNde_mok)itm;
				rv.SubObjs_Add(itmMok.XtoGfmlItm(regy));
			}
			else {
				GfmlAtr_mok atrMok = (GfmlAtr_mok)itm;
				rv.SubObjs_Add(atrMok.XtoGfmlItm());
			}
		}
		return rv;
	}
	public static GfmlNde_mok as_(Object obj) {return obj instanceof GfmlNde_mok ? (GfmlNde_mok)obj : null;}
        public static final    GfmlNde_mok Null = new GfmlNde_mok().Hnd_(String_.Null_mark).Typ_(String_.Null_mark);
        public static final    GfmlNde_mok ErrAtr = new GfmlNde_mok().Hnd_("<<ErrAtr>>").Typ_("<<ErrAtr>>");
        public static GfmlNde_mok new_() {return new GfmlNde_mok();} GfmlNde_mok() {}
        public static GfmlNde_mok gfmlNde_(GfmlNde nde) {return InitNde(nde);}
	static GfmlNde_mok InitNde(GfmlNde nde) {
		GfmlNde_mok rv = new GfmlNde_mok();
		rv.typ = nde.Type().Key();
		rv.hnd = nde.Hnd();
		rv.keyed = nde.KeyedSubObj();
		rv.chainId = nde.ChainId();
		if (nde.Key() != null) rv.key = nde.Key();
		for (int i = 0; i < nde.SubKeys().Count(); i++) {
			GfmlItm subItm = (GfmlItm)nde.SubKeys().Get_at(i);
			if (subItm.ObjType() == GfmlObj_.Type_atr) {
				GfmlAtr subAtr = (GfmlAtr)subItm;
				GfmlAtr_mok mokAtr = GfmlAtr_mok.new_(subAtr.Key(), subAtr.DatTkn().Val());
				if (!String_.Len_eq_0(subAtr.Key())) mokAtr.Key_(subAtr.Key());
				rv.subs.Add(mokAtr);
			}
			else {
				GfmlNde subNde = (GfmlNde)subItm;
				GfmlNde_mok mokNde = InitNde(subNde);
				rv.subs.Add(mokNde);
			}
		}
		for (int i = 0; i < nde.SubHnds().Count(); i++) {
			GfmlNde subNde = (GfmlNde)nde.SubHnds().Get_at(i);
			GfmlNde_mok mokNde = InitNde(subNde);
			rv.subs.Add(mokNde);
		}
		return rv;
	}
}
class GfmlTypeResolver_fxt {
	public GfmlType ini_MakeType(GfmlTyp_mok mok) {
		type = mok.XtoGfmlType();
		regy.Add(type);
		return type;
	}
	GfmlType type;
	public static void tst_Nde(TfdsTstr_fxt tstr, GfmlNde_mok expd, GfmlNde_mok actl) {
		expd = NullObj(expd); actl = NullObj(actl);
		if (expd.Typ() != null) tstr.Eq_str(expd.Typ(), actl.Typ(), "typ");
		if (expd.KeyedSubObj()) tstr.Eq_str(expd.KeyedSubObj(), actl.KeyedSubObj(), "keyed");
		if (expd.Key() != null) tstr.Eq_str(expd.Key(), actl.Key(), "key");
		if (expd.Hnd() != null) tstr.Eq_str(expd.Hnd(), actl.Hnd(), "hnd");
		if (expd.ChainId() != -1) tstr.Eq_str(expd.ChainId(), actl.ChainId(), "chainId");
		int max = tstr.List_Max(expd.Subs(), actl.Subs());
		for (int i = 0; i < max; i++) {
			GfmlItm_mok expdSub = (GfmlItm_mok)tstr.List_FetchAtOrNull(expd.Subs(), i);
			GfmlItm_mok actlSub = (GfmlItm_mok)tstr.List_FetchAtOrNull(actl.Subs(), i);
			tstr.SubName_push(Int_.To_str(i));
			if (expdSub == null) {
				GfmlNde_mok mm = GfmlNde_mok.as_(actlSub);
				String actlSubStr = mm == null ? "sub:null" : mm.XtoStrStub();
				tstr.Eq_str("nde:" + "sub:null", actlSubStr, "expdSub null");
				tstr.Fail();
				break;
			}
			else if (expdSub.ObjType() == GfmlObj_.Type_atr) {
				GfmlAtr_mok actlAtrMok = GfmlAtr_mok.as_(actlSub);
				tst_Nde(tstr, (GfmlAtr_mok)expdSub, actlAtrMok);
			}
			else {
				GfmlNde_mok actlMok = GfmlNde_mok.as_(actlSub);
				if (actlMok == null) {
					String actlSubStr = actlSub == null ? "sub:null" : "atr:" + ((GfmlAtr_mok)actlSub).XtoStrStub();
					tstr.Eq_str("nde:" + ((GfmlNde_mok)expdSub).XtoStrStub(), actlSubStr, "actlSub null");
					tstr.Fail();
					break;
				}
				tst_Nde(tstr, (GfmlNde_mok)expdSub, actlMok);
			}
			tstr.SubName_pop();
		}
	}
	static GfmlNde_mok NullObj(GfmlNde_mok v) {return v == null ? GfmlNde_mok.Null : v;}
	static GfmlAtr_mok NullObj(GfmlAtr_mok v) {return v == null ? GfmlAtr_mok.Null : v;}
	static void tst_Nde(TfdsTstr_fxt tstr, GfmlAtr_mok expd, GfmlAtr_mok actl) {
		expd = NullObj(expd); actl = NullObj(actl);
		if (expd.Key() != null) tstr.Eq_str(expd.Key(), actl.Key(), "key");
		if (expd.Val() != null) tstr.Eq_str(expd.Val(), actl.Val(), "val");
	}
	GfmlTypRegy regy = GfmlTypRegy.new_();
        public static GfmlTypeResolver_fxt new_() {
		GfmlTypeResolver_fxt rv = new GfmlTypeResolver_fxt();
		return rv;
	}	GfmlTypeResolver_fxt() {}
}
