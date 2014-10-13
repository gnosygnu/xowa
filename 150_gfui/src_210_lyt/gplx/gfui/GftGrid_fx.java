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
class GftGrid_fx {
	public GftItem Owner() {return owner;} GftItem owner;
	public GftGrid_fx Clear() {
		itms.Clear();
		grid.Clear();
		curGrid = grid;
		owner = new GftItem_mok();//.Key_("owner");
		return this;
	}
	public GftGrid_fx ini_AddItms(int num) {
		for (int i = 0; i < num; i++)
			itms.Add(new GftItem_mok());//.Key_("key" + Int_.Xto_str(i)));
		return this;
	}
	public GftGrid_fx ini_ItmWidth(int i, int width) {
		GftItem itm = (GftItem)itms.FetchAt(i);
		itm.Gft_w_(width);
		return this;
	}
	public GftGrid_fx ini_AddLyts(int num) {
		for (int i = 0; i < num; i++) {
			GftGrid newGrid = GftGrid.new_();
			grid.SubLyts().Add(newGrid);
		}
		return this;
	}
	public GftGrid_fx ini_Lyt(int num) {
		curGrid = (GftGrid)grid.SubLyts().FetchAt(num);
		return this;
	}
	public GftGrid_fx ini_BandDir(DirInt dir) {curGrid.Bands_dir_(dir); return this;}
	public GftGrid_fx ini_OwnerSize(int w, int h) {owner.Gft_w_(w); owner.Gft_h_(h); return this;}
	public GftGrid_fx ini_Set(int idx, GftBand orig) {return ini_Set(idx, idx, orig);}
	public GftGrid_fx ini_Set(int bgn, int end, GftBand orig) {curGrid.Bands_set(bgn, end, orig); return this;}
	public GftGrid_fx run() {
		GftItem[] ary = (GftItem[])itms.Xto_ary(GftItem.class);
		grid.Exec(owner, ary);
		return this;
	}
	public GftGrid_fx tst_Filter(int idx) {return tst_Filter(idx, idx);}
	public GftGrid_fx tst_Filter(int bgn, int end) {this.bgn = bgn; this.end = end; return this;} int bgn, end;
	public GftGrid_fx tst_W(int... expd) {return tst_ary("w", expd);}
	public GftGrid_fx tst_H(int... expd) {return tst_ary("h", expd);}
	public GftGrid_fx tst_X(int... expd) {return tst_ary("x", expd);}
	public GftGrid_fx tst_Y(int... expd) {return tst_ary("y", expd);}
	public GftGrid_fx tst_W_all(int expd) {return tst_all("w", expd);}
	public GftGrid_fx tst_H_all(int expd) {return tst_all("h", expd);}
	public GftGrid_fx tst_X_all(int expd) {return tst_all("x", expd);}
	public GftGrid_fx tst_Y_all(int expd) {return tst_all("y", expd);}
	GftGrid_fx tst_all(String name, int expdVal) {return tst_ary(name, rng_(expdVal, end - bgn + 1));}
	GftGrid_fx tst_ary(String name, int[] expd) {
		int len = end - bgn + 1;
		int[] actl = new int[len];
		for (int i = 0; i < len; i++) {
			GftItem itm = (GftItem)itms.FetchAt(i + bgn);
			actl[i] = GetVal(itm, name);
		}
		Tfds.Eq_ary(expd, actl, name);
		return this;
	}
	int GetVal(GftItem item, String name) {
		if		(String_.Eq(name, "x")) return item.Gft_x();
		else if	(String_.Eq(name, "y")) return item.Gft_y();
		else if	(String_.Eq(name, "w")) return item.Gft_w();
		else if	(String_.Eq(name, "h")) return item.Gft_h();
		else throw Err_.unhandled(name);
	}
	static int[] rng_(int expdVal, int len) {
		int[] rv = new int[len];
		for (int i = 0; i < len; i++) rv[i] = expdVal;
		return rv;
	}
	GftGrid grid = GftGrid.new_(), curGrid;
	ListAdp itms = ListAdp_.new_();
}