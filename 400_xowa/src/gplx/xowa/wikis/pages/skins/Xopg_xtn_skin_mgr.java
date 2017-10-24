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
package gplx.xowa.wikis.pages.skins; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*;
public class Xopg_xtn_skin_mgr {
	private Ordered_hash hash = Ordered_hash_.New_bry();
	public int Count() {return hash.Count();}
	public void Add(Xopg_xtn_skin_itm itm) {hash.Add_if_dupe_use_1st(itm.Key(), itm);}
	public Xopg_xtn_skin_itm Get_at(int i) {return (Xopg_xtn_skin_itm)hash.Get_at(i);}
	public Xopg_xtn_skin_itm Get_or_null(byte[] key) {return (Xopg_xtn_skin_itm)hash.Get_by(key);}
	public void Clear() {hash.Clear();}
}
