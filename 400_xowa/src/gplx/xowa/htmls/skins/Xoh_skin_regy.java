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
package gplx.xowa.htmls.skins; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
class Xoh_skin_regy {
	private final Ordered_hash hash = Ordered_hash_.New();
	public int Len() {return hash.Count();}
	public Xoh_skin_itm Get_at(int i) {return (Xoh_skin_itm)hash.Get_at(i);}
	public Xoh_skin_itm Get_by_key(String key) {return (Xoh_skin_itm)hash.Get_by(key);}
	public void Set(String key, String fmt) {
		Xoh_skin_itm itm = Get_by_key(key);
		if (itm == null) {
			itm = new Xoh_skin_itm(key, fmt);
			Add(itm);
		}
		else
			itm.Fmt_(fmt);
	}
	public void Add(Xoh_skin_itm itm)		{hash.Add(itm.Key(), itm);}
}
