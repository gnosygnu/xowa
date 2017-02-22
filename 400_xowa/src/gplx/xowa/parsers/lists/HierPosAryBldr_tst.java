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
package gplx.xowa.parsers.lists; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
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
