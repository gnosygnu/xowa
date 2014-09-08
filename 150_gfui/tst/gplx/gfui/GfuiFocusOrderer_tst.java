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
package gplx.gfui; import gplx.*;
import org.junit.*;
public class GfuiFocusOrderer_tst {
	@Before public void setup() {
		owner = GfuiElem_.new_();
		list = ListAdp_.new_();	// list of all controls
	}
	@Test  public void Horizontal() {
		ini_Subs(owner, list, xy_(40, 0), xy_(20, 0), xy_(0, 0));
		tst_FocusIndxs(owner, list, 0, 1, 2);

		GfuiFocusOrderer.OrderByX(owner);
		tst_FocusIndxs(owner, list, 2, 1, 0);
	}
	@Test  public void Vertical() {
		ini_Subs(owner, list, xy_(0, 40), xy_(0, 20), xy_(0, 0));
		tst_FocusIndxs(owner, list, 0, 1, 2);

		GfuiFocusOrderer.OrderByY(owner);
		tst_FocusIndxs(owner, list, 2, 1, 0);
	}
	@Test  public void Grid() {
		ini_Subs(owner, list, xy_(20, 20), xy_(0, 20), xy_(20, 0), xy_(0, 0));
		tst_FocusIndxs(owner, list, 0, 1, 2, 3);

		GfuiFocusOrderer.OrderByX(owner);
		tst_FocusIndxs(owner, list, 3, 2, 1, 0);
	}
	@Test  public void Deep() {
		ini_Subs(owner, list, xy_(20, 0), xy_(0, 0));
		GfuiElem sub0 = sub_(owner, 0), sub1 = sub_(owner, 1);
		ini_Subs(sub0, list, xy_(20, 0), xy_(0, 0));
		ini_Subs(sub1, list, xy_(20, 0), xy_(0, 0));
		tst_FocusIndxs(owner, list, 0, 1, 0, 1, 0, 1);		// 2 owner controls (0, 1); each has two subs (0, 1)

		GfuiFocusOrderer.OrderByX(owner);
		tst_FocusIndxs(owner, list, 3, 0, 5, 4, 2, 1);
	}
	@Test  public void Manusl() {
		ini_Subs(owner, list, xy_(0, 0), xy_(20, 0));
		tst_FocusIndxs(owner, list, 0, 1);

		GfuiElem sub1 = owner.SubElems().FetchAt(0);
		GfuiElem sub2 = owner.SubElems().FetchAt(1);
		sub1.Focus_idx_(1);
		sub2.Focus_idx_(0);

		GfuiFocusOrderer.OrderByX(owner);
		tst_FocusIndxs(owner, list, 1, 0);
	}
	PointAdp xy_(int x, int y) {return PointAdp_.new_(x, y);}
	GfuiElem sub_(GfuiElem owner, int i) {return owner.SubElems().FetchAt(i);}
	void ini_Subs(GfuiElem owner, ListAdp list, PointAdp... points) {
		for (int i = 0; i < points.length; i++) {
			GfuiElem sub = GfuiElem_.sub_(Int_.Xto_str(i), owner);
			sub.Pos_(points[i]);
			sub.UnderElem().Core().Focus_index_set(i);
			list.Add(sub);
		}
	}
	void tst_FocusIndxs(GfuiElem owner, ListAdp list, int... expd) {
		int[] actl = new int[list.Count()];
		for (int i = 0; i < actl.length; i++) {
			GfuiElem sub = (GfuiElem)list.FetchAt(i);
			actl[i] = sub.UnderElem().Core().Focus_index();
		}
		Tfds.Eq_ary(expd, actl);
	}
	GfuiElem owner; ListAdp list;
}
