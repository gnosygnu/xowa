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
package gplx.xowa.addons.bldrs.exports.packs.splits;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
class Pack_list {
	private final List_adp list = List_adp_.New();
	public int Len() {return list.Len();}
	public Pack_itm Get_at(int i) {return (Pack_itm)list.GetAt(i);}
	public void Add(Pack_itm itm) {list.Add(itm);}
}
