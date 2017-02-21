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
package gplx.xowa.xtns.syntax_highlights; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public interface Int_rng_mgr {
	boolean Match(int v);
	boolean Parse(byte[] src);
}
class Int_rng_mgr_null implements Int_rng_mgr {
	public boolean Match(int v) {return false;}
	public boolean Parse(byte[] src) {return false;}
	public static final    Int_rng_mgr_null Instance = new Int_rng_mgr_null(); Int_rng_mgr_null() {}
}
class Int_rng_mgr_base implements Int_rng_mgr {
	private List_adp itms = List_adp_.New();
	public void Clear() {itms.Clear();}
	public boolean Match(int v) {
		int len = itms.Count();
		for (int i = 0; i < len; i++) {
			Int_where itm = (Int_where)itms.Get_at(i);
			if (itm.Match(v)) return true;
		}
		return false;
	}
	public boolean Parse(byte[] src) {
		byte[][] lines = Bry_split_.Split(src, Byte_ascii.Comma);
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
					val_bgn = Bry_.To_int_or(src, pos, i, -1); if (val_bgn == -1) return false;
					pos = -1;
					break;
				default:	// invalid char;
					return false;
			}
		}
		int val_end = Bry_.To_int_or(src, pos, src_len, -1); if (val_end == -1) return false;
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
