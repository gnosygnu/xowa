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
package gplx.xowa.mediawiki; import gplx.*; import gplx.xowa.*;
import org.junit.*; import gplx.core.tests.*;
public class XophpBool__tst {
	private final    XophpBool__fxt fxt = new XophpBool__fxt();
	@Test  public void is_true() {
		fxt.Test__is_true_bry(Bool_.N, null);
		fxt.Test__is_true_str(Bool_.N, null, "", "False", "0", "-0", "0.0", "-0.0");
		fxt.Test__is_true_str(Bool_.Y, "a", "0.1");
	}
}
class XophpBool__fxt {
	public void Test__is_true_str(boolean expd, String... ary) {
		for (String itm : ary) {
			Gftest.Eq__bool(expd, XophpBool_.is_true(itm));
		}
	}
	public void Test__is_true_bry(boolean expd, byte[] itm) {
		Gftest.Eq__bool(expd, XophpBool_.is_true(itm));
	}
}
