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
package gplx.gfui.controls; import gplx.*; import gplx.gfui.*;
import org.junit.*; import gplx.gfui.draws.*; import gplx.gfui.imgs.*;
public class GfuiBorderMgr_tst {
	@Before public void setup() {
		borderMgr = GfuiBorderMgr.new_();
	}
	@Test  public void NullToEdge() { // all null -> one edge
		tst_Eq(borderMgr, null, null, null, null, null);

		borderMgr.Top_(red);
		tst_Eq(borderMgr, null, red, null, null, null);
	}
	@Test  public void EdgeToAll() { // one edge -> all edge
		borderMgr.Top_(red);
		tst_Eq(borderMgr, null, red, null, null, null);

		borderMgr.All_(black);
		tst_Eq(borderMgr, black, null, null, null, null);
	}
	@Test  public void AllToEdge() { // all edge -> one new; three old
		borderMgr.All_(red);
		tst_Eq(borderMgr, red, null, null, null, null);

		borderMgr.Top_(black);
		tst_Eq(borderMgr, null, black, red, red, red);
	}
	void tst_Eq(GfuiBorderMgr borderMgr, PenAdp all, PenAdp top, PenAdp left, PenAdp right, PenAdp bottom) {
		Tfds.Eq(borderMgr.All(), all);
		Tfds.Eq(borderMgr.Top(), top);
		Tfds.Eq(borderMgr.Left(), left);
		Tfds.Eq(borderMgr.Right(), right);
		Tfds.Eq(borderMgr.Bot(), bottom);
	}
	GfuiBorderMgr borderMgr;
	PenAdp black = PenAdp_.black_(), red = PenAdp_.new_(ColorAdp_.Red, 1);
}	
