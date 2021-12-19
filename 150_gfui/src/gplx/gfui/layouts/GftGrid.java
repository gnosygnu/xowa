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
package gplx.gfui.layouts;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.gfui.DirInt;
import gplx.gfui.controls.elems.GfuiElemBase;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.StringUtl;
public class GftGrid {
	public String Key() {return key;} public GftGrid Key_(String v) {key = v; return this;} private String key;
	public List_adp Bands() {return bands;} List_adp bands = List_adp_.New();
	public List_adp SubLyts() {return subLyts;} List_adp subLyts = List_adp_.New();
	public void Clear() {bands.Clear(); subLyts.Clear(); bandDir = DirInt.Fwd;}
	public DirInt Bands_dir() {return bandDir;} public GftGrid Bands_dir_(DirInt v) {bandDir = v; return this;} DirInt bandDir = DirInt.Fwd;
	public GftGrid SubLyts_get(String key) {
		for (int i = 0; i < subLyts.Len(); i++) {
			GftGrid grid = (GftGrid)subLyts.GetAt(i);
			if (StringUtl.Eq(key, grid.Key())) return grid;
		}
		return null;
	}
	public GftBand Bands_get(String key) {
		for (int i = 0; i < bands.Len(); i++) {
			GftBand band = (GftBand)bands.GetAt(i);
			if (StringUtl.Eq(key, band.Key())) return band;
		}
		return null;
	}
	public GftGrid Bands_add(GftBand band) {
		bands.Add(band.Clone(this, bands.Len()));
		return this;
	}
	public GftGrid Bands_add(int count, GftBand band) {
		for (int i = 0; i < count; i++) {
			GftBand copy = band.Clone(this, bands.Len() + i);
			bands.Add(copy);
		}
		return this;
	}
	public void Bands_delAt(int i) {bands.DelAt(i);}
	public boolean Bands_has(String key) {return Bands_indexOf(key) != List_adp_.NotFound;}
	public void Bands_del(String key) {
		int idx = Bands_indexOf(key);
		if (idx != List_adp_.NotFound) bands.DelAt(idx);
	}
	int Bands_indexOf(String key) {
		int curIdx = List_adp_.NotFound;
		for (int i = 0; i < bands.Len(); i++) {
			GftBand band = (GftBand)bands.GetAt(i);
			if (StringUtl.Eq(key, band.Key())) {
				curIdx = i;
				break;
			}
		}
		return curIdx;
	}
	public GftGrid Bands_set(int idx, GftBand orig) {return Bands_set(idx, idx, orig);}
	public GftGrid Bands_set(int bgn, int end, GftBand orig) {
		int len = end - bgn + 1;
		for (int i = 0; i < len; i++) {
			GftBand copy = orig.Clone(this, bgn + i);
			bands.Add(copy);
		}
		return this;
	}
	public void Exec(GftItem owner, GftItem... ary) {
		ExecLyts(owner, ary);
		ExecBands(owner, ary);
	}
	void ExecLyts(GftItem owner, GftItem[] ary) {
		int idx = 0;
		for (int i = 0; i < subLyts.Len(); i++) {
			GftGrid subGrid = (GftGrid)subLyts.GetAt(i);
			GftItem[] subAry = new GftItem[subGrid.Bands_cellCount()];
			for (int j = 0; j < subAry.length; j++) {
				subAry[j] = ary[idx++];
			}
			subGrid.Exec(owner, subAry);
		}
	}
	void ExecBands(GftItem owner, GftItem[] ary) {
		if (bands.Len() == 0) return;
		int availY = owner.Gft_h();
		GftBand band = null;
		int bgn = bandDir.GetValByDir(bands.IdxLast(), 0);
		int end = bandDir.GetValByDir(-1, bands.Len());
		for (int i = bgn; i != end; i += bandDir.Val()) {
			band = (GftBand)bands.GetAt(i);
			if (band.Len1().Key() == GftSizeCalc_abs.KEY) {
				GftSizeCalc_abs calc = (GftSizeCalc_abs)band.Len1();
				availY -= calc.Val();
			}
		}
		int bandIdx = 0;
		band = (GftBand)bands.GetAt(bandIdx);
		band.Items().Clear();
		int y = bandDir.GetValByDir(owner.Gft_h(), 0);
		for (int itmIdx = 0; itmIdx < ary.length; itmIdx++) {
			GftItem itm = ary[itmIdx];
			if (band.Items().Len() >= band.Cells().Len()) {
				int h = band.Len1().Calc(this, band, owner, itm, availY);
				band.Calc(owner, y, h);
				y += h * bandDir.Val();
				if (bandIdx + 1 >= bands.Len()) throw ErrUtl.NewArgs("error retrieving band", "owner", owner.Key_of_GfuiElem(), "item", itm.Key_of_GfuiElem(), "bandIdx", bandIdx + 1, "count", bands.Len());
				band = (GftBand)bands.GetAt(++bandIdx);
				band.Items().Clear();
			}
			band.Items_add(itm);
		}
		band.Calc(owner, y, band.Len1().Calc(this, band, owner, null, availY));
	}
	int Bands_cellCount() {
		int rv = 0;
		for (int i = 0; i < bands.Len(); i++) {
			GftBand band = (GftBand)bands.GetAt(i);
			rv += band.Cells().Len();
		}
		return rv;
	}
	public static GftGrid new_() {return new GftGrid();} GftGrid() {}
	public static void LytExecRecur(GfuiElemBase owner) {
		if (owner.Lyt() != null) owner.Lyt_exec();
		for (int i = 0; i < owner.SubElems().Count(); i++) {
			GfuiElemBase sub = (GfuiElemBase)owner.SubElems().Get_at(i);
			LytExecRecur(sub);
		}
	}
}
