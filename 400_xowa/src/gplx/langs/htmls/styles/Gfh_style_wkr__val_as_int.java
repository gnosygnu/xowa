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
package gplx.langs.htmls.styles; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
public class Gfh_style_wkr__val_as_int implements Gfh_style_wkr {
	private byte[] find_key;
	private int val_bgn, val_end;
	public boolean On_atr(byte[] src, int atr_idx, int atr_val_bgn, int atr_val_end, int itm_bgn, int itm_End, int key_bgn, int key_end, int val_bgn, int val_end) {
		boolean rv = Bry_.Match(src, key_bgn, key_end, find_key);
		if (rv) {
			this.val_bgn = val_bgn;
			this.val_end = val_end;
		}
		return rv;
	}
	public int Parse(byte[] src, int src_bgn, int src_end, byte[] find_key) {
		this.find_key = find_key;
		Gfh_style_parser_.Parse(src, src_bgn, src_end, this);
		return Bry_.To_int_or__lax(src, val_bgn, val_end, -1);
	}
}