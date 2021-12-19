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
package gplx.core.lists.binary_searches;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.wrappers.StringVal;
import org.junit.*;
public class Binary_search__tst {
	private final Binary_search__fxt fxt = new Binary_search__fxt();
	@Test public void Basic() {
		fxt.Init__ary("", "e", "j", "o", "t", "y");
		fxt.Test__binary_search("a", 0);
		fxt.Test__binary_search("f", 1);
		fxt.Test__binary_search("k", 2);
		fxt.Test__binary_search("p", 3);
		fxt.Test__binary_search("u", 4);
		fxt.Test__binary_search("z", 5);
	}
	@Test public void One() {
		fxt.Init__ary("");
		fxt.Test__binary_search("a", 0);
	}
	@Test public void Catpage() {
		String[] ary = new String[25];
		for (int i = 0; i < 25; ++i)
			ary[i] = IntUtl.ToStrPadBgnZero(i, 2);
		fxt.Init__ary(ary);
		fxt.Test__binary_search("10", 10);	// was 9
	}
}
class Binary_search__fxt {
	private StringVal[] ary;
	public void Init__ary(String... v) {
		int ary_len = v.length;
		ary = new StringVal[ary_len];
		for (int i = 0; i < ary_len; i++)
			ary[i] = StringVal.New(v[i]);
	}
	public void Test__binary_search(String val, int expd) {
		int actl = Binary_search_.Search(ary, StringVal.New(val));
		GfoTstr.EqObj(expd, actl, val);
	}
}
