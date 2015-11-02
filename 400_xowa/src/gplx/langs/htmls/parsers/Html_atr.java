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
package gplx.langs.htmls.parsers; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
public class Html_atr {
	private final byte[] src;
	public Html_atr(int idx, byte[] key, byte[] val, byte[] src, int val_bgn, int val_end) {
		this.idx = idx; this.key = key; this.val = val;
		this.src = src; this.val_bgn = val_bgn; this.val_end = val_end;
	}
	public int Idx() {return idx;} private final int idx;
	public byte[] Key() {return key;} private final byte[] key;
	public int Val_bgn() {return val_bgn;} private final int val_bgn;
	public int Val_end() {return val_end;} private final int val_end;
	public byte[] Val() {
		if (val == null)
			val = Bry_.Mid(src, val_bgn, val_end);
		return val;
	}	private byte[] val;
	public static final Html_atr Noop = new Html_atr(-1, Bry_.Empty, Bry_.Empty, Bry_.Empty, 0, 0);
}