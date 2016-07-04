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
package gplx.xowa.htmls.tocs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import org.junit.*; import gplx.core.tests.*;
public class Xoh_toc_wkr__lvl__basic__tst {
	@Before public void init() {fxt.Clear();} private final    Xoh_toc_wkr__lvl__fxt fxt = new Xoh_toc_wkr__lvl__fxt();
	@Test   public void D1_S0_S0() {
		fxt.Test__calc(2, fxt.Make(1, 1, Int_.Ary(1)));
		fxt.Test__calc(2, fxt.Make(2, 1, Int_.Ary(2)));
		fxt.Test__calc(2, fxt.Make(3, 1, Int_.Ary(3)));
	}
	@Test   public void D1_D1_D1() {
		fxt.Test__calc(2, fxt.Make(1, 1, Int_.Ary(1)));
		fxt.Test__calc(3, fxt.Make(2, 2, Int_.Ary(1, 1)));
		fxt.Test__calc(4, fxt.Make(3, 3, Int_.Ary(1, 1, 1)));
	}
	@Test   public void D1_D1_S0_U1() {
		fxt.Test__calc(2, fxt.Make(1, 1, Int_.Ary(1)));
		fxt.Test__calc(3, fxt.Make(2, 2, Int_.Ary(1, 1)));
		fxt.Test__calc(3, fxt.Make(3, 2, Int_.Ary(1, 2)));
		fxt.Test__calc(2, fxt.Make(4, 1, Int_.Ary(2)));
	}
	@Test   public void D1_D1_U1_D1() {
		fxt.Test__calc(2, fxt.Make(1, 1, Int_.Ary(1)));
		fxt.Test__calc(3, fxt.Make(2, 2, Int_.Ary(1, 1)));
		fxt.Test__calc(2, fxt.Make(3, 1, Int_.Ary(2)));
		fxt.Test__calc(3, fxt.Make(4, 2, Int_.Ary(2, 1)));
	}
	@Test   public void D1_D1_D1_U2() {
		fxt.Test__calc(2, fxt.Make(1, 1, Int_.Ary(1)));
		fxt.Test__calc(3, fxt.Make(2, 2, Int_.Ary(1, 1)));
		fxt.Test__calc(4, fxt.Make(3, 3, Int_.Ary(1, 1, 1)));
		fxt.Test__calc(2, fxt.Make(4, 1, Int_.Ary(2)));
	}
}
class Xoh_toc_wkr__lvl__fxt {
	private final    Xoh_toc_wkr__lvl wkr = new Xoh_toc_wkr__lvl();
	private final    Xoh_toc_itm actl = new Xoh_toc_itm();
	public void Clear() {wkr.Clear();}
	public Xoh_toc_itm Make(int uid, int lvl, int[] path) {
		Xoh_toc_itm rv = new Xoh_toc_itm();
		rv.Set__lvl(uid, lvl, path);
		return rv;
	}
	public void Test__calc(int lvl, Xoh_toc_itm expd) {
		wkr.Calc_level(actl, lvl);
		Gftest.Eq__int(expd.Uid(), actl.Uid(), "uid");
		Gftest.Eq__int(expd.Lvl(), actl.Lvl(), "lvl");
		Gftest.Eq__ary(expd.Path(), actl.Path(), "path");
	}
}
