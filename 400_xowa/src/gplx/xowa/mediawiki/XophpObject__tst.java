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
package gplx.xowa.mediawiki;
import gplx.Object_;
import gplx.core.tests.Gftest;
import gplx.objects.primitives.BoolUtl;
import org.junit.Test;
public class XophpObject__tst {
	private final XophpObject__fxt fxt = new XophpObject__fxt();
	@Test public void Empty_obj() {
		fxt.Test_empty_obj_y("");            // "" (an empty String)
		fxt.Test_empty_obj_y(0);             // 0 (0 as an integer)
		fxt.Test_empty_obj_y(0.0d);          // 0.0 (0 as a float)
		fxt.Test_empty_obj_y("0");           // "0" (0 as a String)
		fxt.Test_empty_obj_y(null);          // NULL
		fxt.Test_empty_obj_y(false);         // FALSE
		fxt.Test_empty_obj_y(new int[0]);    // array() (an empty array)

		fxt.Test_empty_obj_n(BoolUtl.Y);
		fxt.Test_empty_obj_n("a");
		fxt.Test_empty_obj_n(9);
		fxt.Test_empty_obj_n(0.8d);
		fxt.Test_empty_obj_n("7");
		fxt.Test_empty_obj_n(true);
		fxt.Test_empty_obj_n(new int[1]);
	}
}
class XophpObject__fxt {
	public void Test_empty_obj_n(Object o) {Test_empty_obj(BoolUtl.N, o);}
	public void Test_empty_obj_y(Object o) {Test_empty_obj(BoolUtl.Y, o);}
	public void Test_empty_obj(boolean expd, Object o) {
		Gftest.Eq__bool(expd, XophpObject_.empty_obj(o), Object_.Xto_str_strict_or_empty(o));
	}
}
