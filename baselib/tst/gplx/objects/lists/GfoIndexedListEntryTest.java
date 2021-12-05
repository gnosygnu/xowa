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
package gplx.objects.lists;
import gplx.tests.GfoTstr;
import org.junit.Test;
public class GfoIndexedListEntryTest {
	private GfoIndexedList<String, String> list = new GfoIndexedList<>();
	@Test public void Add() {
		list.Add("A", "a");
		list.Add("B", "b");
		list.Add("C", "c");

		testGetAt(0, "a");
		testGetAt(1, "b");
		testGetAt(2, "c");
		testGetByOrFail("A", "a");
		testGetByOrFail("B", "b");
		testGetByOrFail("C", "c");
		testIterate("a", "b", "c");
	}
	@Test public void DelBy() {
		list.Add("A", "a");
		list.Add("B", "b");
		list.Add("C", "c");

		list.DelBy("A");

		testIterate("b", "c");

		list.DelBy("B");

		testIterate("c");

		list.DelBy("C");

		testIterate();
	}
	@Test public void DelBySameVal() {
		list.Add("A", "a");
		list.Add("B", "b");
		list.Add("C", "a");

		list.DelBy("C");

		testIterate("a", "b"); // fails if "b", "a"
	}
	@Test public void Set() {
		list.Add("A", "a");
		list.Add("B", "b");
		list.Add("C", "c");

		list.Set("B", "bb");
		testGetByOrFail("B", "bb");
		testIterate("a", "bb", "c");
	}
	private void testGetByOrFail(String key, String expd) {
		GfoTstr.EqStr(expd, list.GetByOrFail(key));
	}
	private void testGetAt(int idx, String expd) {
		GfoTstr.EqStr(expd, list.GetAt(idx));
	}
	private void testIterate(String... expd) {
		String[] actl = new String[expd.length];
		int i = 0;
		for (String itm : list) {
			actl[i++] = itm;
		}
		GfoTstr.EqAry(expd, actl);
	}
}
