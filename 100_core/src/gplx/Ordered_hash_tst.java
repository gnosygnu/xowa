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
package gplx;
import org.junit.*;
public class Ordered_hash_tst {
	@Before public void setup() {
		hash = Ordered_hash_.New();
	}
	@Test public void Get_at() {
		hash.Add("key1", "val1");
		Tfds.Eq("val1", hash.Get_at(0));
	}
	@Test public void iterator() {
		hash.Add("key2", "val2");
		hash.Add("key1", "val1");

		List_adp list = List_adp_.New();
		for (Object val : hash)
			list.Add(val);
		Tfds.Eq("val2", list.Get_at(0));
		Tfds.Eq("val1", list.Get_at(1));
	}

//  NOTE: this test shows that Del breaks iterator when vals are the same
//	@Test
//	public void Del() {
//		hash.Add("a", "v1");
//		hash.Add("b", "v2");
//		hash.Add("c", "v1");
//		hash.Del("c");
//		Tfds.Eq("v1", hash.Get_at(0)); // should be "v1" b/c "a" was not deleted, but Del deletes "c" and first instance of "v1"
//	}

	Ordered_hash hash;
}
