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
package gplx.objects.primitives;
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BoolUtl;
import org.junit.*;
public class BoolUtlTest {
	private final BoolUtlTstr fxt = new BoolUtlTstr();
	@Test public void Compare() {
		fxt.TestCompare(BoolUtl.Y, BoolUtl.Y, CompareAbleUtl.Same);
		fxt.TestCompare(BoolUtl.N, BoolUtl.N, CompareAbleUtl.Same);
		fxt.TestCompare(BoolUtl.N, BoolUtl.Y, CompareAbleUtl.Less);
		fxt.TestCompare(BoolUtl.Y, BoolUtl.N, CompareAbleUtl.More);
	}
}
class BoolUtlTstr {
	public void TestCompare(boolean lhs, boolean rhs, int expd) {GfoTstr.Eq(expd, BoolUtl.Compare(lhs, rhs));}
}
