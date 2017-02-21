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
import org.junit.*; import gplx.core.tests.*; import gplx.core.envs.*;
public class Gfo_cache_mgr_tst {
	@Before public void init() {fxt.Clear();} private final    Gfo_cache_mgr_fxt fxt = new Gfo_cache_mgr_fxt();
	@Test  public void Basic() {
		fxt.Exec__add("a");
		fxt.Test__cur_size(1);
		fxt.Test__itms("a");
	}
	@Test  public void Reduce() {
		fxt.Exec__add("a", "b", "c", "d", "e");
		fxt.Test__cur_size(2);
		fxt.Test__itms("a", "b");
	}
	@Test  public void Reduce_after_get() {
		fxt.Exec__add("a", "b", "c", "d");
		fxt.Exec__get("a", "c");
		fxt.Exec__add("e");
		fxt.Test__itms("a", "c");
	}
}
class Gfo_cache_mgr_fxt {
	private final    Gfo_cache_mgr mgr = new Gfo_cache_mgr().Max_size_(4).Reduce_by_(2);
	public void Clear() {mgr.Clear();}
	public Gfo_cache_mgr_fxt Exec__add(String... ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			String itm = ary[i];
			byte[] key = Bry_.new_u8(itm);
			mgr.Add(key, new Gfo_cache_itm_mock(itm), key.length);
		}
		return this;
	}
	public Gfo_cache_mgr_fxt Exec__get(String... ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			String itm = ary[i];
			mgr.Get_by_key(Bry_.new_u8(itm));
		}
		return this;
	}
	public Gfo_cache_mgr_fxt Test__cur_size(int expd) {Gftest.Eq__int(expd, mgr.Cur_size(), "cur_size"); return this;}
	public Gfo_cache_mgr_fxt Test__itms(String... expd) {
		int len = mgr.Test__len();
		String[] actl = new String[len];
		for (int i = 0; i < len; ++i)
			actl[i] = ((Gfo_cache_itm_mock)mgr.Test__get_at(i)).Val();
		Gftest.Eq__ary(expd, actl, "itms");
		return this;
	}
}
class Gfo_cache_itm_mock implements Rls_able {
	public Gfo_cache_itm_mock(String val) {this.val = val;}
	public String Val() {return val;} private String val;
	public void Rls() {}
}
