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
package gplx.xowa.xtns.graphs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*; import gplx.core.tests.*;
public class Json_fmtr_tst {
	private final Json_fmtr_fxt fxt = new Json_fmtr_fxt();
	@Test public void Comments() {
		// basic: // \n
		fxt.Test_clean("a//b\nc", "ac");

		// basic: /* */
		fxt.Test_clean("a/*b*/c", "ac");

		// unterminated: /* */
		fxt.Test_clean("a/*bc", "a");

		// ignore inside quote: // \n
		fxt.Test_clean("\"a//b\nc\"");

		// ignore inside quote: /* */
		fxt.Test_clean("\"a/*b*/c\"");

		// ignore quotes inside quotes else inside-quote turns off quotes and comment will be stripped
		fxt.Test_clean("\"a\\\"/*b*/c\"");
	}

	@Test public void Trailing_commas() {
		// remove: ]
		fxt.Test_clean("[a,]", "[a]");

		// remove: }
		fxt.Test_clean("{a,}", "{a}");

		// remove: ws
		fxt.Test_clean("[a \t,\t ]", "[a \t\t ]");

		// ignore: normal
		fxt.Test_clean("a,b");

		// ignore: String
		fxt.Test_clean("\"b\"");

		// ignore: comment block: /* */
		fxt.Test_clean("/*[b,]*", "");

		// ignore: comment block: // \n
		fxt.Test_clean("//[b,]\n", "");
	}
}
class Json_fmtr_fxt {
	private final Bry_bfr tmp_bfr = Bry_bfr_.New();
	public void Test_clean(String src) {Test_clean(src, src);}
	public void Test_clean(String src, String expd) {
		byte[] actl = Json_fmtr.clean(tmp_bfr, Bry_.new_u8(src));
		Gftest.Eq__bry(Bry_.new_u8(expd), actl);
	}
}
