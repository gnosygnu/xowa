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
public class GfmlNde implements GfmlItm {
	public static final int OBJ_TYPE = GfmlObj_.Type_nde;
	public int			ObjType() {return objType;} int objType = GfmlObj_.Type_nde;
	public GfmlTkn		KeyTkn() {return keyTkn;} public String Key() {return keyTkn.Val();} GfmlTkn keyTkn = GfmlTkn_.Null;
	public GfmlType		Type() {return type;} GfmlType type;
	public boolean			KeyedSubObj() {return keyedSubObj;} public GfmlNde KeyedSubObj_(boolean v) {keyedSubObj = v; return this;} private boolean keyedSubObj;
	public int ChainId() {return chainId;} public GfmlNde ChainId_(int v) {chainId = v; return this;} int chainId;	// can use boolean chainHead, but this is easier for debugging
	public int			SubObjs_Count() {return subObjs.Count();} GfmlObjList subObjs = GfmlObjList.new_();
	public GfmlObj		SubObjs_GetAt(int i) {return (GfmlObj)subObjs.Get_at(i);}
	public void			SubObjs_Add(GfmlObj gobj) {
		subObjs.Add(gobj);
		GfmlItm subItm = GfmlItm_.as_(gobj);
		if (	subItm == null							// gobj is tkn: symbol, whitespace, comment
			||	subItm.ObjType() == GfmlObj_.Type_prg)	// gobj is pragma
			return;
		if (subItm.KeyedSubObj())
			subKeys.Add(subItm);
		else
			subHnds.Add(GfmlNde.as_(subItm));
	}
	public String			Hnd() {return hndTkn.Val();} GfmlTkn hndTkn = GfmlTkn_.Null;
	public GfmlDocPos		DocPos() {return docPos;} GfmlDocPos docPos = GfmlDocPos_.Null;
	public GfmlItmKeys		SubKeys() {return subKeys;} GfmlItmKeys subKeys = GfmlItmKeys.new_();
	public GfmlItmHnds		SubHnds() {return subHnds;} GfmlItmHnds subHnds = GfmlItmHnds.new_();
	public String			To_str() {return GfmlDocWtr_.xtoStr_(this);}
	public void				UpdateNde(String hnd) {
		for (int i = 0; i < subHnds.Count(); i++) {
			GfmlNde nde = (GfmlNde)subHnds.Get_at(i);
			if (String_.Eq(nde.hndTkn.Raw(), hnd)) return;
		}
		int endAtrPos = PosOf(false, ";", "}");
		GfmlTkn bgnParen = GfmlTkn_.new_("(", "");
		GfmlTkn endParen = GfmlTkn_.new_(")", "");
		GfmlTkn bgnBrace = GfmlTkn_.new_("{", "");
		GfmlTkn endBrace = GfmlTkn_.new_("}", "");
		GfmlTkn hndTkn = GfmlTkn_.new_(hnd, hnd);
		GfmlNde subNde = GfmlNde.new_(hndTkn, GfmlType_.new_any_(), false);
		subNde.SubObjs_Add(hndTkn);
		subNde.SubObjs_Add(bgnParen);
		subNde.SubObjs_Add(endParen);
		subNde.SubObjs_Add(bgnBrace);
		subNde.SubObjs_Add(endBrace);
		subObjs.Add_at(subNde, endAtrPos);
		// a(){b(){}}
		// a:{} -> a:{b:{}}
	}
	int PosOf(boolean fwd, String... find) {
		int bgn = fwd ? 0 : subObjs.Count() - 1;
		int end = fwd ? subObjs.Count() : 0;
		int dif = fwd ? 1 : -1;
		for (int i = bgn; i != end; i+=dif) {
			GfmlObj subObj = (GfmlObj)subObjs.Get_at(i);
			GfmlTkn subTkn = GfmlTkn_.as_(subObj);
			if (subTkn == null) continue;
			if (String_.In(subTkn.Raw(), find)) {
				return i;
			}
		}
		return -1;
	}
	public void				UpdateAtr(String key, String val) {
		GfmlAtr atr = (GfmlAtr)subKeys.Get_by(key);
		if (atr != null) {atr.UpdateAtr(key, val); return;}
		val = String_.Replace(val, "'", "''");
		GfmlTkn quote = GfmlTkn_.new_("'", "");
		GfmlTkn keyTkn = GfmlTkn_.raw_(key);
		GfmlTkn valTkn = GfmlTkn_.composite_("composite", GfmlTknAry_.ary_(quote, GfmlTkn_.raw_(val), quote));
		GfmlTkn eqTkn = GfmlTkn_.new_("=", "");
		atr = GfmlAtr.string_(keyTkn, valTkn);
		atr.SubObjs_Add(keyTkn);
		atr.SubObjs_Add(eqTkn);
		atr.SubObjs_Add(valTkn);
		int endAtrPos = PosOf(true, ";", "{");
		if (subKeys.Count() != 0) {
			subObjs.Add_at(GfmlTkn_.new_(" ", ""), endAtrPos);
			endAtrPos++;
		}
		subObjs.Add_at(atr, endAtrPos);
	}

	@gplx.Internal protected void ObjType_set_pragma() {objType = GfmlObj_.Type_prg;}
	@gplx.Internal protected void KeyTkn_set(GfmlTkn gobj) {keyTkn = gobj;}
	@gplx.Internal protected void Type_set(GfmlType val) {type = val;} 
	@gplx.Internal protected void SubObjs_Clear() {subObjs.Clear();}		
	@gplx.Internal protected GfmlTkn HndTkn() {return hndTkn;}
	@gplx.Internal protected void HndTkn_set(GfmlTkn tkn) {hndTkn = tkn;}
	@gplx.Internal protected void Hnd_set(String v) {hndTkn = String_.Len_eq_0(v) ? GfmlTkn_.Null : GfmlTkn_.val_(v);}	// NOTE: v is empty for types with empty fldNames
	@gplx.Internal protected GfmlNde DocPos_(GfmlDocPos val) {docPos = val; return this;}
	
	public static GfmlNde as_(Object obj) {return obj instanceof GfmlNde ? (GfmlNde)obj : null;}
	@gplx.Internal protected static GfmlNde named_(GfmlTkn hndTkn, GfmlType type) {return new GfmlNde(hndTkn, type, false);}
	@gplx.Internal protected static GfmlNde new_(GfmlTkn hndTkn, GfmlType type, boolean keyedSubObj) {return new GfmlNde(hndTkn, type, keyedSubObj);}
	GfmlNde(GfmlTkn hndTkn, GfmlType type, boolean keyedSubObj) {
		this.hndTkn = hndTkn; this.type = type; this.keyedSubObj = keyedSubObj;}
}
