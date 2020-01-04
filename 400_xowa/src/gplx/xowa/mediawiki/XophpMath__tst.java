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
public class XophpMath__tst {
	private final    XophpMath__fxt fxt = new XophpMath__fxt();
	@Test  public void fmod() {
		fxt.Test__fmod(8, 2, 0);
		fxt.Test__fmod(7, 2, 1);
		fxt.Test__fmod(5.7d, 1.3d, .5d);
	}
}
class XophpMath__fxt {
	public void Test__fmod(double lhs, double rhs, double expd) {
		Gftest.Eq__double(expd, XophpMath.fmod(lhs, rhs));
	}
}
