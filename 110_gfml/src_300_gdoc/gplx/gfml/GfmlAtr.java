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
public class GfmlAtr implements GfmlItm {
	public int		ObjType() {return GfmlObj_.Type_atr;}
	public GfmlTkn	KeyTkn() {return keyTkn;} GfmlTkn keyTkn; public String Key() {return keyTkn.Val();}
	public GfmlTkn	DatTkn() {return datTkn;} GfmlTkn datTkn;
	public GfmlType Type() {return type;} GfmlType type; 
	public boolean		KeyedSubObj() {return true;}
	public int		SubObjs_Count()			{return subObjs.Count();}
	public GfmlObj	SubObjs_GetAt(int i)	{return (GfmlObj)subObjs.Get_at(i);} GfmlObjList subObjs = GfmlObjList.new_();	// PERF?: make capacity 3 instead of 8
	public void		SubObjs_Add(GfmlObj o)	{subObjs.Add(o);}
	public String	To_str() {return String_.Concat(this.Key(), "=", this.DatTkn().Val());}
	@gplx.Internal protected void	Key_set(String v) {keyTkn = GfmlTkn_.val_(v);}	// used for 1 test

	public static GfmlAtr as_(Object obj) {return obj instanceof GfmlAtr ? (GfmlAtr)obj : null;}
	public static GfmlAtr string_(GfmlTkn keyTkn, GfmlTkn datTkn)				{return new_(keyTkn, datTkn, GfmlType_.String);}
	public static GfmlAtr new_(GfmlTkn keyTkn, GfmlTkn datTkn, GfmlType type)	{
		GfmlAtr rv = new GfmlAtr();
		rv.keyTkn = keyTkn; rv.datTkn = datTkn; rv.type = type;
		return rv;
	}	GfmlAtr() {}
	public void UpdateAtr(String key, String dat) {
		keyTkn = MakeTknAndReplace(keyTkn, key);
		datTkn = MakeTknAndReplace(datTkn, dat);
	}
	GfmlTkn MakeTknAndReplace(GfmlTkn oldTkn, String s) {
		int idx = GetTknIdx(oldTkn);
		GfmlTkn tkn = MakeTkn(oldTkn, s);
		if (idx != -1)
			subObjs.Del_at(idx);
		if (idx == -1) idx = 0;
		subObjs.Add_at(tkn, idx);
		return tkn;
	}
	int GetTknIdx(GfmlTkn t) {
		for (int i = 0; i < subObjs.Count(); i++) {
			GfmlObj obj = (GfmlObj)subObjs.Get_at(i);
			if (obj == t) return  i;
		}
		return -1;
	}
	GfmlTkn MakeTkn(GfmlTkn oldTkn, String newStr) {
		if (newStr == null) return oldTkn;
		if (oldTkn.SubTkns().length > 0) {
			GfmlTkn bgn = oldTkn.SubTkns()[0];
			GfmlTkn end = oldTkn.SubTkns()[oldTkn.SubTkns().length - 1];				
			newStr = String_.Replace(newStr, bgn.Raw(), bgn.Raw() + bgn.Raw());
			if (bgn.Raw() != end.Raw())
				newStr = String_.Replace(newStr, end.Raw(), end.Raw() + end.Raw());				
			return GfmlTkn_.composite_(oldTkn.TknType(), GfmlTknAry_.ary_(bgn, GfmlTkn_.raw_(newStr), end));
		}
		else {
			boolean hasQuote = String_.Has(newStr, "'");
			if (hasQuote)
				return GfmlTkn_.composite_("composite", GfmlTknAry_.ary_(GfmlTkn_.new_("'", ""), GfmlTkn_.raw_(String_.Replace(newStr, "'", "''")), GfmlTkn_.new_("'", "")));
			else
				return GfmlTkn_.raw_(newStr);
		}
	}
}
