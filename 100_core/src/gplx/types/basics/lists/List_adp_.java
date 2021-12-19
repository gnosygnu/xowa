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
package gplx.types.basics.lists;
public class List_adp_ {
	public static final List_adp Noop = new List_adp_noop();
	public static List_adp New()                    {return new List_adp_obj();}
	public static List_adp NewWithSize(int v)        {return new List_adp_obj(v);}
	public static List_adp NewByMany(Object... ary) {
		List_adp rv = new List_adp_obj();
		rv.AddMany(ary);
		return rv;
	}
	public static void DelAtLast(List_adp list) {list.DelAt(list.Len() - 1);}
	public static Object Pop(List_adp list) {
		int lastIdx = list.Len() - 1;
		Object rv = list.GetAt(lastIdx);
		list.DelAt(lastIdx);
		return rv;
	}
	public static Object PopFirst(List_adp list) {    // NOTE: dirty way of implementing FIFO queue; should not be used with lists with many members
		Object rv = list.GetAt(0);
		list.DelAt(0);
		return rv;
	}
	public static Object PopLast(List_adp list) {
		int last_idx = list.Len() - 1;
		Object rv = list.GetAt(last_idx);
		list.DelAt(last_idx);
		return rv;
	}
	public static Object PopOr(List_adp list, Object or) {
		int list_len = list.Len(); if (list_len == 0) return or;
		int last_idx = list_len - 1;
		Object rv = list.GetAt(last_idx);
		list.DelAt(last_idx);
		return rv;
	}
	public static final int NotFound = -1, Base1 = 1;
}
