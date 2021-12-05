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
package gplx;
import gplx.objects.arrays.ArrayUtl;
import gplx.objects.strings.AsciiByte;
public class Int_ary_ {//RF:DATE:2017-10-09
	public static int[] Empty = new int[0];

	public static int[] New(int... v) {return v;}

	public static void Copy_to(int[] src, int src_len, int[] trg) {
		for (int i = 0; i < src_len; ++i)
			trg[i] = src[i];
	}

	public static int[] Mid(int[] src, int bgn, int end) {
		int len = end - bgn + 1;
		int[] rv = new int[len];
		for (int i = 0; i < len; i++) {
			rv[i] = src[i + bgn];
		}
		return rv;
	}

	public static String To_str(String spr, int... ary) {
		Bry_bfr bfr = Bry_bfr_.New();
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			if (i != 0) bfr.Add_str_u8(spr);
			int itm = ary[i];
			bfr.Add_int_variable(itm);
		}
		return bfr.To_str_and_clear();
	}

	public static int[] Parse(String raw, String spr) {
		String[] ary = String_.Split(raw, spr);
		int len = ary.length;
		int[] rv = new int[len];
		for (int i = 0; i < len; i++)
			rv[i] = Int_.Parse(ary[i]);
		return rv;
	}

	// parses to a reqd len; EX: "1" -> "[1, 0]"
	public static int[] Parse(String raw_str, int reqd_len, int[] or) {
		byte[] raw_bry = Bry_.new_a7(raw_str);
		int raw_bry_len = raw_bry.length;
		int[] rv = new int[reqd_len];
		int cur_val = 0, cur_mult = 1, cur_idx = reqd_len - 1; boolean signed = false;
		for (int i = raw_bry_len - 1; i > -2; i--) {
			byte b = i == -1 ? AsciiByte.Comma : raw_bry[i];
			switch (b) {
				case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
				case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
					if (signed) return or;
					cur_val += (b - AsciiByte.Num0) * cur_mult;
					cur_mult *= 10;
					break;
				case AsciiByte.Space: case AsciiByte.Nl: case AsciiByte.Cr: case AsciiByte.Tab:
					break;
				case AsciiByte.Comma:
					if (cur_idx < 0) return or;
					rv[cur_idx--] = cur_val;
					cur_val = 0; cur_mult = 1;
					signed = false;
					break;
				case AsciiByte.Dash:
					if (signed) return or;
					cur_val *= -1;
					signed = true;
					break;
				case AsciiByte.Plus:	// noop; all values positive by default
					if (signed) return or;
					signed = true;
					break;
				default:
					return or;
			}
		}
		return cur_idx == -1 ? rv : or;	// cur_idx == -1 checks for unfilled; EX: Ary_parse("1,2", 3, null) is unfilled
	}

	// optimizes parse
	public static int[] Parse_or(byte[] src, int[] or) {
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
					rv = (int[])ArrayUtl.Resize(rv, rv_len);
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
				case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
				case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
					if (num_bgn == -1)	// num_bgn not set
						num_bgn = pos;
					num_end = pos + 1;	// num_end is always after pos; EX: "9": num_end = 1; "98,7": num_end=2
					break;
				case AsciiByte.Space: case AsciiByte.Tab: case AsciiByte.Nl: case AsciiByte.Cr:	// NOTE: parseNumList replaces ws with '', so "1 1" will become "11"
					break;
				case AsciiByte.Comma:
					if (pos == raw_len -1) return or;	// eos; EX: "1,"
					if (num_bgn == -1) return or;		// empty itm; EX: ","; "1,,2"
					itm_done = true;
					break;
				case AsciiByte.Dash:
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
			: (int[])ArrayUtl.Resize(rv, rv_idx);
		} catch (Exception e) {Err_.Noop(e); return or;}
	}
}
