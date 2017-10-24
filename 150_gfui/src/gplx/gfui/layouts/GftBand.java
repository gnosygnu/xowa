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
package gplx.gfui.layouts; import gplx.*; import gplx.gfui.*;
import gplx.gfui.controls.elems.*;
public class GftBand {
	public String Key() {return key;} public GftBand Key_(String v) {key = v; return this;} private String key;
	public int Idx() {return idx;} public GftBand Idx_(int v) {idx = v; return this;} int idx;
	public GftSizeCalc Len1() {return len1;} public GftBand Len1_(GftSizeCalc v) {len1 = v; return this;} GftSizeCalc len1 = new GftSizeCalc_abs(20);
	public GftBand Len1_pct_(float val) {return Len1_(new GftSizeCalc_pct(val));}
	public GftBand Len1_abs_(int v) {return Len1_(new GftSizeCalc_abs(v));}
	public GftCell Cell_dfl() {return cell_dfl;} GftCell cell_dfl = new GftCell();
	public List_adp Cells() {return cells;} List_adp cells = List_adp_.New();
	public GftBand Cells_var_(int count) {
		for (int i = 0; i < count; i++)
			cells.Add(new GftCell().Len0_(new GftSizeCalc_var(count)));
		return this;
	}
	public GftBand Cell_abs_(int val) {cells.Add(new GftCell().Len0_(new GftSizeCalc_abs(val))); return this;}
	public GftBand Cell_pct_(float val) {cells.Add(new GftCell().Len0_(new GftSizeCalc_pct(val))); return this;}
	public GftBand Cells_num_(int num) {
		cells.Clear();
		for (int i = 0; i < num; i++)
			cells.Add(new GftCell().Len0_(new GftSizeCalc_num(num)));
		return this;
	}
	public List_adp Items() {return items;} List_adp items = List_adp_.New();
	public void Items_add(GftItem item) {items.Add(item);}
	public void Calc(GftItem owner, int y, int h) {
		int x = 0;
		y = grid.Bands_dir().GetValByDir(y - h, y);
		int availX = owner.Gft_w();
		for (int i = 0; i < cells.Count(); i++) {
			GftCell cell = (GftCell)cells.Get_at(i);
			if (cell.Len0().Key() == GftSizeCalc_abs.KEY) {
				GftSizeCalc_abs calc = (GftSizeCalc_abs)cell.Len0();
				availX -= calc.Val();
			}
			else if (cell.Len0().Key() == GftSizeCalc_var.KEY) {
				if (i >= items.Count()) continue;
				GftItem item = (GftItem)items.Get_at(i);
				GfuiElem elem = GfuiElem_.as_(item);
				availX -= elem == null ? item.Gft_w() : elem.Width();
			}
		}
		for (int i = 0; i < items.Count(); i++) {
			GftItem item = (GftItem)items.Get_at(i);
			GftCell cell = i >= cells.Count() ? cell_dfl : (GftCell)cells.Get_at(i);
			int w = cell.Len0().Calc(grid, this, owner, item, availX);
			item.Gft_rect_(RectAdp_.new_(x, y, w, h));
//				Tfds.Write(item.Key_of_GfuiElem(), w, h, x, y);
			x += w;
		}
		this.items.Clear();
	}
	public GftBand Clone(GftGrid grid, int idx) {
		GftBand rv = new GftBand();
		rv.grid = grid;
		rv.key = key; rv.idx = idx; rv.cell_dfl = cell_dfl.Clone(); rv.len1 = this.len1.Clone();
		for (int i = 0; i < cells.Count(); i++) {
			GftCell cell = (GftCell)cells.Get_at(i);
			rv.cells.Add(cell.Clone());
		}
		return rv;
	}	GftGrid grid;
	public static GftBand new_() {return new GftBand();} GftBand() {}
	public static GftBand fillWidth_() {
		GftBand rv = new GftBand();
		rv.Cells_num_(1);
		return rv;
	}
	public static GftBand fillAll_() {
		GftBand rv = new GftBand();
		rv.Cells_num_(1).Len1_pct_(100);
		return rv;
	}
}
