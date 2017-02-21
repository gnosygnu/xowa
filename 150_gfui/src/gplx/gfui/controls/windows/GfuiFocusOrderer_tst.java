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
package gplx.gfui.controls.windows; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import org.junit.*; import gplx.gfui.controls.elems.*;
public class GfuiFocusOrderer_tst {
	@Before public void setup() {
		owner = GfuiElem_.new_();
		list = List_adp_.New();	// list of all controls
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

		GfuiElem sub1 = owner.SubElems().Get_at(0);
		GfuiElem sub2 = owner.SubElems().Get_at(1);
		sub1.Focus_idx_(1);
		sub2.Focus_idx_(0);

		GfuiFocusOrderer.OrderByX(owner);
		tst_FocusIndxs(owner, list, 1, 0);
	}
	PointAdp xy_(int x, int y) {return PointAdp_.new_(x, y);}
	GfuiElem sub_(GfuiElem owner, int i) {return owner.SubElems().Get_at(i);}
	void ini_Subs(GfuiElem owner, List_adp list, PointAdp... points) {
		for (int i = 0; i < points.length; i++) {
			GfuiElem sub = GfuiElem_.sub_(Int_.To_str(i), owner);
			sub.Pos_(points[i]);
			sub.UnderElem().Core().Focus_index_set(i);
			list.Add(sub);
		}
	}
	void tst_FocusIndxs(GfuiElem owner, List_adp list, int... expd) {
		int[] actl = new int[list.Count()];
		for (int i = 0; i < actl.length; i++) {
			GfuiElem sub = (GfuiElem)list.Get_at(i);
			actl[i] = sub.UnderElem().Core().Focus_index();
		}
		Tfds.Eq_ary(expd, actl);
	}
	GfuiElem owner; List_adp list;
}
