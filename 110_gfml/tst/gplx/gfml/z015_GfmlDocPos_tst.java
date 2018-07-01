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
public class z015_GfmlDocPos_tst {
	GfmlDocPos root = GfmlDocPos_.Root;
	@Test  public void Root() {
		tst_Path(root, "0");
	}
	@Test  public void MoveDown() {
		tst_Path(root.NewDown(0), "0_0");
		tst_Path(root.NewDown(0).NewDown(0), "0_0_0");
		tst_Path(root.NewDown(1).NewDown(2), "0_1_2");
	}
	@Test  public void MoveUp() {
		tst_Path(root.NewDown(1).NewDown(2).NewUp(), "0_1");
	}
	@Test  public void CompareTo_same() {
		GfmlDocPos lhs = root.NewDown(0);
		GfmlDocPos rhs = root.NewDown(0);
		tst_CompareTo(lhs, rhs, CompareAble_.Same);
	}
	@Test  public void CompareTo_diffIndex() {
		GfmlDocPos lhs = root.NewDown(0);
		GfmlDocPos rhs = root.NewDown(1);
		tst_CompareTo(lhs, rhs, CompareAble_.Less);
		tst_CompareTo(rhs, lhs, CompareAble_.More);
	}
	@Test  public void CompareTo_diffLevel() {
		GfmlDocPos lhs = root;
		GfmlDocPos rhs = root.NewDown(0);
		tst_CompareTo(lhs, rhs, CompareAble_.Less);
		tst_CompareTo(rhs, lhs, CompareAble_.More);
	}
	void tst_Path(GfmlDocPos pos, String expdPath) {Tfds.Eq(expdPath, pos.Path());}
	void tst_CompareTo(GfmlDocPos lhs, GfmlDocPos rhs, int expd) {Tfds.Eq(expd, lhs.compareTo(rhs));}
}
