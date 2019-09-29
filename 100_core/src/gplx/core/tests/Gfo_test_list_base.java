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
package gplx.core.tests; import gplx.*; import gplx.core.*;
import gplx.core.strings.*;
public class Gfo_test_list_base {
	private final    List_adp expd = List_adp_.New();
	public void Clear() {expd.Clear();}
	public void Add(Object itm) {expd.Add(itm);}
	public void Test(Object actl_obj) {
		int len = expd.Len();
		if (len == 0) { // nothing expd; return "pass"
			return;
		}

		String actl = Type_.Is_assignable_from_by_obj(actl_obj, List_adp.class) ? To_str((List_adp)actl_obj) : actl_obj.toString();
		Gftest.Eq__ary__lines(To_str(expd), actl );
	}
	private static String To_str(List_adp list) {
		String_bldr sb = String_bldr_.new_();
		int len = list.Len();
		for (int i = 0; i < len; i++) {
			Object obj = list.Get_at(i);
			String str = Object_.Xto_str_strict_or_null_mark(obj);
			sb.Add(str).Add_char_nl();
		}
		return sb.To_str_and_clear();
	}
}
