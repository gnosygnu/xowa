/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
