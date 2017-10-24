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
package gplx.xowa.addons.wikis.fulltexts.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*;
import org.junit.*; import gplx.core.tests.*;
public class Xofulltext_extractor__tst {
	private final    Xofulltext_extractor__fxt fxt = new Xofulltext_extractor__fxt();
	@Test   public void Basic() {
		// simple node
		fxt.Test__extract("a <i>b</i> c", "a b c");

		// node with attributes
		fxt.Test__extract("a <a href='b.html' caption='c d e'>f</a> g", "a f g");

		// nested nodes
		fxt.Test__extract("a <b>b <i>c</i> d</b> e", "a b c d e");

		// periods
		fxt.Test__extract("a <b>b</b>. c d", "a b. c d");

		// parens
		fxt.Test__extract("(a <b>b</b>)", "(a b)");

		// parens
		fxt.Test__extract("<b>a</b> (b)", "a (b)");
	}
}
class Xofulltext_extractor__fxt {
	private final    Xofulltext_extractor extractor = new Xofulltext_extractor();
	public void Test__extract(String src, String expd) {
		Gftest.Eq__str(expd, extractor.Extract(Bry_.new_u8(src)));
	}
}
