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
package gplx.core.caches; import gplx.*; import gplx.core.*;
import org.junit.*; import gplx.core.tests.*;
public class Lru_cache_tst {
	private final Lru_cache_fxt fxt = new Lru_cache_fxt();
	@Test public void Get_one() {
		fxt.Exec__set("a", 5);
		fxt.Test__get_y("a");
	}
	@Test public void Pop_one() {
		fxt.Exec__set("a", 10);
		fxt.Exec__set("b", 10);
		fxt.Test__get_n("a");
		fxt.Test__get_y("b");
	}
	@Test public void Add_many() {
		fxt.Exec__set("a", 4);
		fxt.Exec__set("b", 3);
		fxt.Exec__set("c", 2);
		fxt.Exec__set("d", 1);
		fxt.Test__get_y("a", "b", "c", "d");
	}
	@Test public void Pop_many() {
		fxt.Exec__set("a", 4);
		fxt.Exec__set("b", 3);
		fxt.Exec__set("c", 2);
		fxt.Exec__set("d", 1);
		fxt.Exec__set("e", 6);
		fxt.Test__get_y("c", "d", "e");
		fxt.Test__get_n("a", "b");
	}
	@Test public void Set_repeatedly() {
		fxt.Exec__set("a", "a1", 10);
		fxt.Exec__set("a", "a2", 10);
		fxt.Exec__set("a", "a3", 10);
		fxt.Test__get_val("a", "a3");
	}
	@Test public void Set_bumps_priority() {
		fxt.Exec__set("a", 2);
		fxt.Exec__set("b", 3);
		fxt.Exec__set("c", 2);
		fxt.Exec__set("a", 2);
		fxt.Exec__set("d", 7);
		fxt.Test__get_y("a", "d");
		fxt.Test__get_n("b", "c");
	}
	@Test public void Del() {
		fxt.Exec__set("a", 2);
		fxt.Exec__set("b", 2);
		fxt.Exec__del("b");
		fxt.Test__get_y("a");
		fxt.Test__get_n("b");
	}
	@Test public void Clear() {
		fxt.Exec__set("a", 2);
		fxt.Exec__set("b", 2);
		fxt.Exec__clear();
		fxt.Test__get_n("a", "b");
	}
}
class Lru_cache_fxt {
	private final Lru_cache cache = new Lru_cache(Bool_.N, "test", -1, 10);
	public void Exec__set(String key, long size) {
		cache.Set(key, key, size);
	}
	public void Exec__set(String key, String val, long size) {
		cache.Set(key, val, size);
	}
	public void Exec__del(String key) {
		cache.Del(key);
	}
	public void Exec__clear() {
		cache.Clear_all();
	}
	public void Test__get_y(String... keys) {
		for (String key : keys)
			Test__get(key, key);
	}
	public void Test__get_n(String... keys) {
		for (String key : keys)
			Test__get(key, null);
	}
	public void Test__get_val(String key, String val) {
		Test__get(key, val);
	}
	private void Test__get(String key, String expd) {
		Object actl = cache.Get_or_null(key);
		Gftest.Eq__obj_or_null(expd, actl);
	}
}
