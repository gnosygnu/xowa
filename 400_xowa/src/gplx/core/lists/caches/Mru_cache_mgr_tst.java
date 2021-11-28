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
package gplx.core.lists.caches; import gplx.*; import gplx.core.*; import gplx.core.lists.*;
import org.junit.*; import gplx.core.tests.*;
public class Mru_cache_mgr_tst {
	private final Mru_cache_mgr_fxt fxt = new Mru_cache_mgr_fxt();
	@Before public void init() {
		fxt.Init__New_cache_mgr(3, 3, 2);
	}
	@Test public void Basic() {
		fxt.Exec__Add("a", "b", "c", "d");
		fxt.Test__Print("b", "c", "d"); // adding "d" pushes out "a"
	}
	@Test public void Used() {
		fxt.Exec__Add("a", "b", "c");
		fxt.Exec__Get_and_compress("a");
		fxt.Test__Print("b", "c", "a"); // getting "a" pushes to back
	}
	@Test public void Used__more_uses_at_back() {
		fxt.Exec__Add("a", "b", "c");
		fxt.Exec__Get_and_compress("a", "a", "a");
		fxt.Test__Print("b", "c", "a");
		fxt.Exec__Get_and_compress("b");
		fxt.Test__Print("c", "b", "a"); // getting "a" multiple time still keeps towards back
	}
	@Test public void Time() {
		fxt.Exec__Add("a", "b", "c");
		fxt.Exec__Get_and_compress("a", "a", "a");
		fxt.Exec__Wait(10);
		fxt.Exec__Get_and_compress("b");
		fxt.Test__Print("c", "a", "b");	// long wait puts "b" at back
	}
	@Test public void Compress() {
		fxt.Init__New_cache_mgr(3, 2, 2);
		fxt.Exec__Add("a", "b", "c", "d");
		fxt.Test__Print("c", "d");
	}
}
class Mru_cache_mgr_fxt {
	private final Mru_cache_time_mgr__mock time_mgr = new Mru_cache_time_mgr__mock();
	private Mru_cache_mgr cache_mgr;
	public void Init__New_cache_mgr(long cache_max, long used_weight, long compress_size) {
		cache_mgr = Mru_cache_mgr.New_test(time_mgr, null, cache_max, used_weight, compress_size);
	}
	public void Exec__Add(String... ary) {
		for (String itm : ary)
			cache_mgr.Add(itm, itm, 1);
	}
	public void Exec__Get(String... ary) {
		for (String itm : ary)
			cache_mgr.Get_or_null(itm);
	}
	public void Exec__Wait(int time) {
		time_mgr.Add(time);
	}
	public void Exec__Compress(long val_size) {
		cache_mgr.Compress(val_size);
	}
	public void Exec__Get_and_compress(String... ary) {
		this.Exec__Get(ary);
		this.Exec__Compress(0);
	}
	public void Test__Print(String... expd) {
		Object[] actl_objs = cache_mgr.Values_array();
		int actl_len = actl_objs.length;
		String[] actl = new String[actl_len];			
		for (int i = 0; i < actl_len; i++) {
			actl[i] = Object_.Xto_str_strict_or_null(((Mru_cache_itm)actl_objs[i]).Val());
		}
		Gftest.Eq__ary(expd, actl);
	}
}
class Mru_cache_time_mgr__mock implements Mru_cache_time_mgr {
	private long time;
	public long Now() {return time++;}
	public void Add(int v) {time += v;}
}
