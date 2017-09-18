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
package gplx;
import org.junit.*;
public class Object__tst {
	@Before public void init() {} private Object__fxt fxt = new Object__fxt();
	@Test  public void Eq() {
		fxt.Test_eq(null, null, true);		// both null
		fxt.Test_eq(5, 5, true);			// both non-null
		fxt.Test_eq(5, null, false);		// rhs non-null
		fxt.Test_eq(null, 5, false);		// lhs non-null
	}
	@Test  public void Xto_str_loose_or_null() {
		fxt.Test_xto_str_loose_or_null(null, null);	
		fxt.Test_xto_str_loose_or_null(2449.6000000000004d, "2449.6");
	}
}
class Object__fxt {
	public void Test_eq(Object lhs, Object rhs, boolean expd) {Tfds.Eq(expd, Object_.Eq(lhs, rhs));}
	public void Test_xto_str_loose_or_null(Object v, String expd) {Tfds.Eq(expd, Object_.Xto_str_loose_or(v, null));}
}
