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
package gplx.xowa.xtns.syntaxHighlight; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public interface Int_rng_mgr {
	boolean Match(int v);
	boolean Parse(byte[] src);
}
class Int_rng_mgr_null implements Int_rng_mgr {
	public boolean Match(int v) {return false;}
	public boolean Parse(byte[] src) {return false;}
	public static final Int_rng_mgr_null _ = new Int_rng_mgr_null(); Int_rng_mgr_null() {}
}
class Int_rng_mgr_base implements Int_rng_mgr {
	private ListAdp itms = ListAdp_.new_();
	public void Clear() {itms.Clear();}
	public boolean Match(int v) {
		int len = itms.Count();
		for (int i = 0; i < len; i++) {
			Int_where itm = (Int_where)itms.FetchAt(i);
			if (itm.Match(v)) return true;
		}
		return false;
	}
	public boolean Parse(byte[] src) {
		byte[][] lines = Bry_.Split(src, Byte_ascii.Comma);
		int lines_len = lines.length;
		for (int i = 0; i < lines_len; i++) {
			if (!Parse_line(lines[i])) {
				itms.Clear();
				return false;
			}
		}
		return true;
	}
	private boolean Parse_line(byte[] src) {
		int src_len = src.length;
		int val_bgn = -1;
		int pos = -1;
		for (int i = 0; i < src_len; i++) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
					if (pos == -1) pos = i;
					break;
				case Byte_ascii.Dash:
					val_bgn = Bry_.Xto_int_or(src, pos, i, -1); if (val_bgn == -1) return false;
					pos = -1;
					break;
				default:	// invalid char;
					return false;
			}
		}
		int val_end = Bry_.Xto_int_or(src, pos, src_len, -1); if (val_end == -1) return false;
		if (val_bgn == -1)
			itms.Add(new Int_where_val(val_end));
		else
			itms.Add(new Int_where_rng(val_bgn, val_end));
		return true;
	}
}
interface Int_where {
	boolean Match(int v);
}
class Int_where_val implements Int_where {
	public Int_where_val(int val) {this.val = val;} private int val;
	public boolean Match(int v) {return v == val;}
}
class Int_where_rng implements Int_where {
	public Int_where_rng(int bgn, int end) {this.bgn = bgn; this.end = end;} private int bgn, end;
	public boolean Match(int v) {return v >= bgn && v <= end;}
}