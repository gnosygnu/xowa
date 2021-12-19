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
package gplx.core.lists;
import gplx.types.basics.wrappers.IntVal;
import gplx.core.tests.Gftest;
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.types.commons.lists.ComparerAble;
import gplx.types.basics.utls.BoolUtl;
import org.junit.Test;
public class Binary_heap_tst {
	private final Binary_heap_fxt fxt = new Binary_heap_fxt();
	@Test public void Max() {
		fxt.Init(BoolUtl.Y);
		fxt.Exec__add(4, 3, 5, 7, 1, 6, 9, 8, 2);
		fxt.Test__pop(9, 8, 7, 6, 5, 4, 3, 2, 1);
	}
	@Test public void Min() {
		fxt.Init(BoolUtl.N);
		fxt.Exec__add(4, 3, 5, 7, 1, 6, 9, 8, 2);
		fxt.Test__pop(1, 2, 3, 4, 5, 6, 7, 8, 9);
	}
}
class Binary_heap_fxt implements ComparerAble {
	private Binary_heap heap;
	public int compare(Object lhsObj, Object rhsObj) {
		return CompareAbleUtl.Compare_obj(lhsObj, rhsObj);
	}
	public void Init(boolean is_max) {
		heap = new Binary_heap(this, is_max, 2);
	}
	public void Exec__add(int... ary) {
		for (int i : ary)
			heap.Add(new IntVal(i));
	}
	public void Test__pop(int... expd) {
		int len = expd.length;
		int[] actl = new int[len];
		for (int i = 0; i < len; i++)
			actl[i] = ((IntVal)heap.Pop()).Val();
		Gftest.EqAry(expd, actl, "heaps don't match");
	}
}
