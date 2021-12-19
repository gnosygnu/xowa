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
package gplx.objects.arrays;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.ArrayUtl;
import org.junit.Test;
public class ArrayUtlTest {
	@Test public void Append() {
		TestAppend(AryInt(), AryInt(1), AryInt(1));        // 0 + 1 = 1
		TestAppend(AryInt(0), AryInt(), AryInt(0));        // 1 + 0 = 1
		TestAppend(AryInt(0), AryInt(1), AryInt(0, 1));    // 1 + 1 = 2
	}
	private void TestAppend(int[] source, int[] added, int[] expd) {
		GfoTstr.EqAry(expd, (int[])ArrayUtl.Append(source, added));
	}
	@Test public void Resize() {
		TestResize(AryInt(0), 0, AryInt());                // 1 -> 0
		TestResize(AryInt(0, 1), 1, AryInt(0));            // 2 -> 1
	}
	private void TestResize(int[] source, int length, int[] expd) {
		GfoTstr.EqAry(expd, (int[])ArrayUtl.Resize(source, length));
	}
	@Test public void Insert() {
		TestInsert(AryObj(0, 1, 4, 5), AryObj(2, 3), 2, AryObj(0, 1, 2, 3, 4, 5));
	}
	private void TestInsert(Object[] cur, Object[] add, int addPos, Object[] expd) {
		GfoTstr.EqAryObjAry(expd, ArrayUtl.Insert(cur, add, addPos));
	}
	private Object[] AryObj(Object... ary) {return ary;}
	private int[] AryInt(int... ary) {return ary;}
}
