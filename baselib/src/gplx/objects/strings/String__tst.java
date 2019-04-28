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
package gplx.objects.strings; import gplx.*; import gplx.objects.*;
import org.junit.*; import gplx.tests.*;
public class String__tst {
	private final    String__fxt fxt = new String__fxt();
	@Test  public void Len() {
		fxt.Test__Len(""   , 0);
		fxt.Test__Len("abc", 3);
	}
	@Test  public void Format() {
		fxt.Test__Format(""        , "");                      // empty fmt
		fxt.Test__Format(""        , "", "a");                 // empty fmt w/ args
		fxt.Test__Format("a"       , "a");                     // no args
		fxt.Test__Format("a"       , "{0}", "a");              // args = 1
		fxt.Test__Format("a + b"   , "{0} + {1}", "a", "b");   // args = n
		fxt.Test__Format("{"       , "{{", 0);                 // escape "{"
		fxt.Test__Format("}"       , "}}", 0);                 // escape "}"
		fxt.Test__Format("{a0c}"   , "{a{0}c}", 0);            // nested;
		fxt.Test__Format("{a{b}c}" , "{a{b}c}", 0);            // nested; invalid
		fxt.Test__Format("{1}"     , "{1}", "a");              // out of bounds
		fxt.Test__Format("{a} {b}" , "{a} {b}", 0);            // invalid arg
		fxt.Test__Format("{a}0{b}1", "{a}{0}{b}{1}", 0, 1);    // invalid and valid args
		fxt.Test__Format("{0", "{0", 0);                       // dangling
	}
}
class String__fxt {
	public void Test__Format(String expd, String fmt, Object... ary) {
		Gftest_fxt.Eq__str(expd, String_.Format(fmt, ary));
	}
	public void Test__Len(String v, int expd) {
		Gftest_fxt.Eq__int(expd, String_.Len(v));
	}
}
