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
package gplx.objects;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.ObjectUtl;
import org.junit.Before;
import org.junit.Test;
public class ObjectUtlTest {
	private ObjectUtlTstr tstr = new ObjectUtlTstr();
	@Before public void init() {}
	@Test public void Eq() {
		tstr.TestEq(null, null, true);        // both null
		tstr.TestEq(5, 5, true);            // both non-null
		tstr.TestEq(5, null, false);        // rhs non-null
		tstr.TestEq(null, 5, false);        // lhs non-null
	}
	@Test public void ToStrLooseOrNull() {
		tstr.TestToStrLooseOrNull(null, null);
		tstr.TestToStrLooseOrNull(2449.6000000000004d, "2449.6");
	}
}
class ObjectUtlTstr {
	public void TestEq(Object lhs, Object rhs, boolean expd) {GfoTstr.Eq(expd, ObjectUtl.Eq(lhs, rhs));}
	public void TestToStrLooseOrNull(Object v, String expd) {GfoTstr.Eq(expd, ObjectUtl.ToStrLooseOr(v, null));}
}
