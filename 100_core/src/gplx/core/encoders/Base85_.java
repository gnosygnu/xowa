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
package gplx.core.encoders; import gplx.*; import gplx.core.*;
public class Base85_ {
	public static String To_str(int val, int min_len) {return String_.new_u8(Set_bry(val, null, 0, min_len));}
	public static byte[] To_bry(int val, int min_len) {return Set_bry(val, null, 0, min_len);}
	public static byte[] Set_bry(int val, byte[] ary, int ary_pos, int min_len) {
		int val_len = Bry_len(val);
		int ary_len = val_len, pad_len = 0;
		boolean pad = ary_len < min_len;
		if (pad) {
			pad_len = min_len - ary_len;
			ary_len = min_len;
		}
		if (ary == null) ary = new byte[ary_len];
		if (pad) {
			for (int i = 0; i < pad_len; i++)		// fill ary with pad_len
				ary[i + ary_pos] = A7_offset;
		}
		for (int i = ary_len - pad_len; i > 0; i--) {
			int div = Pow85[i - 1];
			byte tmp = (byte)(val / div);
			ary[ary_pos + ary_len - i] = (byte)(tmp + A7_offset);
			val -= tmp * div;
		}
		return ary;
	}
	public static int To_int_by_str(String s) {
		byte[] ary = Bry_.new_u8(s);
		return To_int_by_bry(ary, 0, ary.length - 1);
	}
	public static int To_int_by_bry(byte[] ary, int bgn, int end) {
		int rv = 0, factor = 1;
		for (int i = end; i >= bgn; i--) {
			rv += (ary[i] - A7_offset) * factor;
			factor *= Radix;
		}
		return rv;
	}
	public static int Bry_len(int v) {
		if (v == 0) return 1;
		for (int i = Pow85_last; i > -1; i--)
			if (v >= Pow85[i]) return i + 1;
		throw Err_.new_wo_type("neg number not allowed", "v", v);
	}
	public static final int Len_int = 5;
	private static final int Pow85_last = 4, Radix = 85; 
	public static final byte A7_offset = 33;
	public static final int Pow85_0 = 1, Pow85_1 = 85, Pow85_2 = 7225, Pow85_3 = 614125, Pow85_4 = 52200625;
	public static int[] Pow85 = new int[]{Pow85_0, Pow85_1, Pow85_2, Pow85_3, Pow85_4}; // NOTE: ary constructed to match index to exponent; Pow85[1] = 85^1
}
