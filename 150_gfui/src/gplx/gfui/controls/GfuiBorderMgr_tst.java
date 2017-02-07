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
