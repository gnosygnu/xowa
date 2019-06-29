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
package gplx.xowa.mediawiki.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*;
public class XomwNamespacesByName {
	private final    Ordered_hash hash = Ordered_hash_.New_bry();
	public int Len() {return hash.Len();}
	public int GetAsIdOrNullInt(byte[] name) {
		XomwNamespaceItem item = (XomwNamespaceItem)hash.Get_by(name);
		return item == null ? XophpUtility.NULL_INT : item.id;
	}
	public XomwNamespaceItem GetAtOrNull(int idx) {
		return (XomwNamespaceItem)hash.Get_at(idx);
	}
	public void Add(byte[] name, XomwNamespaceItem item) {
		hash.Add(name, item);
	}
	public XomwNamespacesByName Add(String name, int id) {
		byte[] nameBry = Bry_.new_u8(name);
		hash.Add(nameBry, new XomwNamespaceItem(id, nameBry));
		return this;
	}
}
