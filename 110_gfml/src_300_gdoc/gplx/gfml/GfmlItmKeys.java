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
public class GfmlItmKeys {
	public int Count() {return list.Count();}
	public boolean Has(String key) {return hash.Has(key);}
	public GfmlItm Get_at(int i) {return (GfmlItm)list.Get_at(i);}
	public GfmlItm Get_by(String key) {return (GfmlItm)hash.Get_by(key);}
	public String FetchDataOr(String key, String or) {
		GfmlAtr atr = FetchAtr(key);
		return (atr == null) ? or : atr.DatTkn().Val();
	}
	public String FetchDataOrNull(String key) {return FetchDataOr(key, null);}
	public String FetchDataOrFail(String key) {
		GfmlAtr atr = FetchAtr(key); if (atr == null) throw Err_.new_missing_key(key);
		return atr.DatTkn().Val();
	}
	public GfmlTkn FetchDataTknOrNull(String key) {
		GfmlAtr atr = FetchAtr(key); if (atr == null) return GfmlTkn_.Null;
		return atr.DatTkn();
	}
	public void Add(GfmlItm itm) {
		String key = itm.Key();
		if (hash.Has(key)) DelDefault(itm);	// default attribs are added automatically; drop default when adding newElm
		list.Add(itm);
		if (!String_.Eq(key, GfmlItmKeys.NullKey))
			RegisterKey(key, itm);
	}
	@gplx.Internal protected void RegisterKey(String key, GfmlItm itm) {
		if (hash.Has(key)) hash.Del(key); // replace default
		hash.Add(key, itm);
	}
	void DelDefault(GfmlItm elm) {
		GfmlItm toDel = null;
		for (Object subObj : list) {
			GfmlItm sub = (GfmlItm)subObj;
			if (String_.Eq(sub.Key(), elm.Key()))
				toDel = sub;
		}
		if (toDel != null) list.Del(toDel);
	}
	GfmlAtr FetchAtr(String key) {return GfmlAtr.as_(hash.Get_by(key));}
	List_adp list = List_adp_.New(); Hash_adp hash = Hash_adp_.New(); 
	public static GfmlItmKeys new_() {return new GfmlItmKeys();} GfmlItmKeys() {}
	@gplx.Internal protected static final    String NullKey = "";
}
