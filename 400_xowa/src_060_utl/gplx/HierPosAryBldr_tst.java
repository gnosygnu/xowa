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
public class HierPosAryBldr_tst {
	@Before public void init() {bldr.Init();} HierPosAryBldr bldr = new HierPosAryBldr(256);
	@Test  public void Basic() {
		tst_ary(Int_.Ary_empty);
	}
	@Test  public void Move_d() {
		bldr.MoveDown();
		tst_ary(0);
	}
	@Test  public void Move_dd() {
		bldr.MoveDown();
		bldr.MoveDown();
		tst_ary(0, 0);
	}
	@Test  public void Move_ddu() {
		bldr.MoveDown();
		bldr.MoveDown();
		bldr.MoveUp();
		tst_ary(1);
	}
	@Test  public void Move_ddud() {
		bldr.MoveDown();
		bldr.MoveDown();
		bldr.MoveUp();
		bldr.MoveDown();
		tst_ary(1, 0);
	}
	@Test  public void Move_dud() {
		bldr.MoveDown();
		bldr.MoveUp();
		bldr.MoveDown();
		tst_ary(1);
	}
	@Test  public void Move_dn() {
		bldr.MoveDown();
		bldr.MoveNext();
		tst_ary(1);
	}
	@Test  public void Move_ddn() {
		bldr.MoveDown();
		bldr.MoveDown();
		bldr.MoveNext();
		tst_ary(0, 1);
	}
	private void tst_ary(int... expd) {Tfds.Eq_ary(expd, bldr.XtoIntAry());}
}
