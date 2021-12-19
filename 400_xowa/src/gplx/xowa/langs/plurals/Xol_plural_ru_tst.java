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
package gplx.xowa.langs.plurals;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
import org.junit.*;
public class Xol_plural_ru_tst {
	@Test public void Plural() {
		Tst(1, StringUtl.AryEmpty, null);			// 0 forms
		Tst(1, StringUtl.Ary("a", "b"), "a");			// 2 forms; singluar
		Tst(2, StringUtl.Ary("a", "b"), "b");			// 2 forms; plural
		Tst(111, StringUtl.Ary("a", "b", "c"), "c");	// 3 forms; (count % 100) / 10 == 0; should not return "a"
		Tst(1, StringUtl.Ary("a", "b", "c"), "a");	// 3 forms; count % 10 == 1
		Tst(2, StringUtl.Ary("a", "b", "c"), "b");	// 3 forms; count % 10 == (2, 3, 4)
		Tst(5, StringUtl.Ary("a", "b", "c"), "c");	// 3 forms; count % 10 != (1, 2, 3, 4)
		Tst(5, StringUtl.Ary("a"), "a");				// 1 form; count % 10 != (1, 2, 3, 4); but only 1 element, so take 1st
	}
	private void Tst(int count, String[] forms, String expd) {
		byte[] actl = Xol_plural_ru.Instance.Plural_eval(null, count, BryUtl.Ary(forms));
		GfoTstr.Eq(BryUtl.NewA7(expd), actl);
	}
}
