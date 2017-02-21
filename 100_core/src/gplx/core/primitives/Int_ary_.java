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
package gplx.core.primitives; import gplx.*; import gplx.core.*;
public class Int_ary_ {
	public static int[] Parse_list_or(byte[] src, int[] or) {
		try {
		if (Bry_.Len_eq_0(src)) return or;	// null, "" should return [0]
		int raw_len = src.length;
		int[] rv = null; int rv_idx = 0, rv_len = 0;
		int pos = 0;
		int num_bgn = -1, num_end = -1;
		boolean itm_done = false, itm_is_rng = false;
		int rng_bgn = Int_.Min_value;
		while (true) {
			boolean pos_is_last = pos == raw_len;
			if (	itm_done
				||	pos_is_last
				) {
				if (num_bgn == -1) return or;			// empty itm; EX: "1,"; "1,,2"
				int num = Bry_.To_int_or(src, num_bgn, num_end, Int_.Min_value);
				if (num == Int_.Min_value) return or;	// not a number; parse failed
				if (rv_len == 0) {						// rv not init'd
					rv_len = (raw_len / 2) + 1;			// default rv_len to len of String / 2; + 1 to avoid fraction rounding down
					rv = new int[rv_len];
				}
				int add_len = 1;
				if (itm_is_rng) {
					add_len = num - rng_bgn + List_adp_.Base1;
					if (add_len == 0) return or;		// bgn >= end;
				}
				if (add_len + rv_idx > rv_len) {		// ary out of space; resize
					rv_len = (add_len + rv_idx) * 2;
					rv = (int[])Array_.Resize(rv, rv_len);
				}
				if (itm_is_rng) {
					for (int i = rng_bgn; i <= num; i++)
						rv[rv_idx++] = i;
				}
				else  {
					rv[rv_idx++] = num;
				}
				num_bgn = num_end = -1;
				itm_done = itm_is_rng = false;
				rng_bgn = Int_.Min_value;
				if (pos_is_last) break;
			}
			byte b = src[pos];
			switch (b) {
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
					if (num_bgn == -1)	// num_bgn not set
						num_bgn = pos;
					num_end = pos + 1;	// num_end is always after pos; EX: "9": num_end = 1; "98,7": num_end=2
					break;
				case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr:	// NOTE: parseNumList replaces ws with '', so "1 1" will become "11"
					break;
				case Byte_ascii.Comma:
					if (pos == raw_len -1) return or;	// eos; EX: "1,"
					if (num_bgn == -1) return or;		// empty itm; EX: ","; "1,,2"
					itm_done = true;
					break;
				case Byte_ascii.Dash:
					if (pos == raw_len -1) return or;	// eos; EX: "1-"
					if (num_bgn == -1) return or;		// no rng_bgn; EX: "-2"
					rng_bgn = Bry_.To_int_or(src, num_bgn, pos, Int_.Min_value);
					if (rng_bgn == Int_.Min_value) return or;
					num_bgn = -1;
					itm_is_rng = true;
					break;
				default:
					return or;
			}
			++pos;
		}
		return (rv_idx == rv_len)	// on the off-chance that rv_len == rv_idx; EX: "1"
			? rv
			: (int[])Array_.Resize(rv, rv_idx);
		} catch (Exception e) {Err_.Noop(e); return or;}
	}
}
