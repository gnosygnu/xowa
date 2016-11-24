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
package gplx.langs.htmls.docs; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
public class Gfh_atr implements gplx.core.brys.Bfr_arg {
	public Gfh_atr(int idx, int atr_bgn, int atr_end, byte[] key, byte[] val, byte[] src, int val_bgn, int val_end) {
		this.idx = idx; this.atr_bgn = atr_bgn; this.atr_end = atr_end; this.key = key; this.val = val;
		this.src = src; this.val_bgn = val_bgn; this.val_end = val_end;
	}
	public byte[] Src() {return src;} private final byte[] src;
	public int Idx() {return idx;} private final int idx;
	public int Atr_bgn() {return atr_bgn;} private final int atr_bgn;
	public int Atr_end() {return atr_end;} private final int atr_end;
	public byte[] Key() {return key;} private final byte[] key;
	public int Val_bgn() {return val_bgn;} private final int val_bgn;
	public int Val_end() {return val_end;} private final int val_end;
	public boolean Val_dat_exists() {return val_end != -1;}
	public boolean Val_dat_missing() {return val_end == -1;}
	public byte[] Val() {
		if (val == null)
			val = Bry_.Mid(src, val_bgn, val_end);
		return val;
	}	private byte[] val;
	public void Html__add(Bry_bfr bfr) {
		if (val_end > val_bgn)
			bfr.Add_mid(src, val_bgn, val_end);
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		if (Val_dat_exists())
			bfr.Add_mid(src, val_bgn, val_end);
	}
	public static final Gfh_atr Noop = new Gfh_atr(-1, -1, -1, Bry_.Empty, Bry_.Empty, Bry_.Empty, -1, -1);
}
