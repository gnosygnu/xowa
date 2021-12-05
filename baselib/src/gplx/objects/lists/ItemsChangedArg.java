/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.objects.lists;
public class ItemsChangedArg<T> {
	public ItemsChangedArg(ItemsChangedType type, GfoListBase<T> itms) {
		this.type = type;
		this.itms = itms;
	}
	public ItemsChangedType Type() {return type;} private final ItemsChangedType type;
	public GfoListBase<T> Itms() {return itms;} private final GfoListBase<T> itms;
	public T Itm() {return itms.GetAt(0);}
}
