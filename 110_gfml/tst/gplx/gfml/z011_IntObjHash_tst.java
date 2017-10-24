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
package gplx.gfml; import gplx.*;
import org.junit.*;
public class z011_IntObjHash_tst {
	@Before public void setup() {
		hash = new IntObjHash_base();
	}	IntObjHash_base hash;
	@Test  public void Empty() {
		tst_Count(0);
		tst_Fetch(1, null);
	}
	@Test  public void Add() {
		hash.Add(1, "1");
		tst_Count(1);
		tst_Fetch(1, "1");
		tst_Fetch(2, null);
	}
	@Test  public void Del() {
		hash.Add(1, "1");

		hash.Del(1);
		tst_Count(0);
		tst_Fetch(1, null);
	}
	@Test  public void Clear() {
		hash.Add(1, "1");
		hash.Add(32, "32");
		tst_Fetch(1, "1");
		tst_Fetch(32, "32");
		tst_Count(2);

		hash.Clear();
		tst_Count(0);
		tst_Fetch(2, null);
		tst_Fetch(32, null);
	}
	@Test  public void Add_bug() { // fails after expanding ary, and fetching at key=n*16
		hash.Add(1, "1");
		tst_Count(1);
		tst_Fetch(1, "1");
		tst_Fetch(15, null);	// works
		tst_Fetch(17, null);	// works
		tst_Fetch(16, null);	// used to fail
	}
	void tst_Fetch(int key, Object expd) {Tfds.Eq(expd, hash.Get_by(key));}
	void tst_Count(int expd) {Tfds.Eq(expd, hash.Count());}
}
