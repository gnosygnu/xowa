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
package gplx.gfml; import gplx.*;
import org.junit.*;
public class z015_GfmlDocPos_tst {
	GfmlDocPos root = GfmlDocPos_.Root;
	@Test public void Root() {
		tst_Path(root, "0");
	}
	@Test public void MoveDown() {
		tst_Path(root.NewDown(0), "0_0");
		tst_Path(root.NewDown(0).NewDown(0), "0_0_0");
		tst_Path(root.NewDown(1).NewDown(2), "0_1_2");
	}
	@Test public void MoveUp() {
		tst_Path(root.NewDown(1).NewDown(2).NewUp(), "0_1");
	}
	@Test public void CompareTo_same() {
		GfmlDocPos lhs = root.NewDown(0);
		GfmlDocPos rhs = root.NewDown(0);
		tst_CompareTo(lhs, rhs, CompareAble_.Same);
	}
	@Test public void CompareTo_diffIndex() {
		GfmlDocPos lhs = root.NewDown(0);
		GfmlDocPos rhs = root.NewDown(1);
		tst_CompareTo(lhs, rhs, CompareAble_.Less);
		tst_CompareTo(rhs, lhs, CompareAble_.More);
	}
	@Test public void CompareTo_diffLevel() {
		GfmlDocPos lhs = root;
		GfmlDocPos rhs = root.NewDown(0);
		tst_CompareTo(lhs, rhs, CompareAble_.Less);
		tst_CompareTo(rhs, lhs, CompareAble_.More);
	}
	void tst_Path(GfmlDocPos pos, String expdPath) {Tfds.Eq(expdPath, pos.Path());}
	void tst_CompareTo(GfmlDocPos lhs, GfmlDocPos rhs, int expd) {Tfds.Eq(expd, lhs.compareTo(rhs));}
}
