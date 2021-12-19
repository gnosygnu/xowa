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
package gplx.core.tests;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.ClassUtl;
import gplx.types.commons.String_bldr;
import gplx.types.commons.String_bldr_;
public class Gfo_test_list_base {
	private final List_adp expd = List_adp_.New();
	public void Clear() {expd.Clear();}
	public void Add(Object itm) {expd.Add(itm);}
	public void Test(Object actl_obj) {
		int len = expd.Len();
		if (len == 0) { // nothing expd; return "pass"
			return;
		}

		String actl = ClassUtl.IsAssignableFromByObj(actl_obj, List_adp.class) ? To_str((List_adp)actl_obj) : actl_obj.toString();
		GfoTstr.EqLines(To_str(expd), actl );
	}
	private static String To_str(List_adp list) {
		String_bldr sb = String_bldr_.new_();
		int len = list.Len();
		for (int i = 0; i < len; i++) {
			Object obj = list.GetAt(i);
			String str = ObjectUtl.ToStrOrNullMark(obj);
			sb.Add(str).AddCharNl();
		}
		return sb.ToStrAndClear();
	}
}
