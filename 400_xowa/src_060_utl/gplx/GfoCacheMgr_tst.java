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
package gplx;
import org.junit.*;
public class GfoCacheMgr_tst {
	@Before public void init() {fxt = new GfoCacheMgr_fxt();} GfoCacheMgr_fxt fxt;
	@Test  public void teardown() {Env_.TickCount_Test = -1;}
	@Test  public void Basic() {fxt.run_Add("a").Expd_curSize_(1).Expd_itms_("a").tst();}
//		@Test  public void Reduce() {fxt.run_Add("a", "b", "c", "d", "e").Expd_curSize_(3).Expd_itms_("c", "d", "e").tst();}
//		@Test  public void Access() {fxt.run_Add("a", "b", "c", "d").run_Get("b", "a").run_Add("e").Expd_curSize_(3).Expd_itms_("b", "a", "e").tst();}
//		@Test  public void Sizes() {fxt.run_Add("abc", "d", "e").Expd_curSize_(2).Expd_itms_("d", "e").tst();}
}
class GfoCacheMgr_fxt {
	Gfo_cache_mgr mgr = new Gfo_cache_mgr().Max_size_(4).Reduce_by_(2);
	public GfoCacheMgr_fxt() {
		Env_.TickCount_Test = 1;
	}
	public GfoCacheMgr_fxt run_Add(String... ary) {
		for (int i = 0; i < ary.length; i++) {
			String s = ary[i];
			mgr.Add(Bry_.new_utf8_(s), new GfoCacheItm_mock(s), String_.Len(s));
			Env_.TickCount_Test++;
		}
		return this;
	}
	public GfoCacheMgr_fxt run_Get(String... ary) {
		for (int i = 0; i < ary.length; i++) {
			String s = ary[i];
			mgr.Get_by_key(Bry_.new_utf8_(s));
			Env_.TickCount_Test++;
		}
		return this;
	}
	public GfoCacheMgr_fxt Expd_curSize_(int v) {expd_curSize = v; return this;} private int expd_curSize = -1;
	public GfoCacheMgr_fxt Expd_itms_(String... v) {expd_itms = v; return this;} private String[] expd_itms;
	public GfoCacheMgr_fxt tst() {
		if (expd_curSize != -1) Tfds.Eq(expd_curSize, mgr.Cur_size(), "curSize");
		if (expd_itms != null) {
			String[] actl = new String[mgr.Count()];
			for (int i = 0; i < actl.length; i++)
				actl[i] = ((GfoCacheItm_mock)mgr.Get_at(i)).S();
			Tfds.Eq_ary_str(expd_itms, actl, "itms");
		}
		return this;
	}
}
class GfoCacheItm_mock implements RlsAble {
	public void Rls() {}
	public String S() {return s;} private String s;
	public GfoCacheItm_mock(String s) {this.s = s;}
}
