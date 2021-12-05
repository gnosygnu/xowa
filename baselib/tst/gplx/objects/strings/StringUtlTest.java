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
package gplx.objects.strings;
import gplx.tests.GfoTstr;
import org.junit.Test;
public class StringUtlTest {
	private final StringUtlTstr fxt = new StringUtlTstr();
	@Test public void Len() {
		fxt.TestLen(""   , 0);
		fxt.TestLen("abc", 3);
	}
	@Test public void Format() {
		fxt.TestFormat("empty fmt"                 , ""        , "");
		fxt.TestFormat("empty fmt w/ args"         , ""        , "", "a");
		fxt.TestFormat("no args"                   , "a"       , "a");
		fxt.TestFormat("args = 1"                  , "a"       , "{0}", "a");
		fxt.TestFormat("args = n"                  , "a + b"   , "{0} + {1}", "a", "b");
		fxt.TestFormat("escape {"                  , "{"       , "{{", 0);
		fxt.TestFormat("escape }"                  , "}"       , "}}", 0);
		fxt.TestFormat("nested"                    , "{a0c}"   , "{a{0}c}", 0);
		fxt.TestFormat("nested; invalid"           , "{a{b}c}" , "{a{b}c}", 0);
		fxt.TestFormat("out of bounds"             , "{1}"     , "{1}", "a");
		fxt.TestFormat("invalid arg"               , "{a} {b}" , "{a} {b}", 0);
		fxt.TestFormat("invalid and valid args"    , "{a}0{b}1", "{a}{0}{b}{1}", 0, 1);
		fxt.TestFormat("dangling"                  , "{0"      , "{0", 0);
	}
}
class StringUtlTstr {
	public void TestLen(String v, int expd) {
		GfoTstr.EqInt(expd, StringUtl.Len(v));
	}
	public void TestFormat(String note, String expd, String fmt, Object... ary) {
		GfoTstr.EqStr(expd, StringUtl.Format(fmt, ary), note);
	}
}
