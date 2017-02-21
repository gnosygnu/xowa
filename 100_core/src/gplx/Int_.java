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
import gplx.core.strings.*; import gplx.langs.gfs.*;
public class Int_ {
	public static final String Cls_val_name = "int";
	public static final    Class<?> Cls_ref_type = Integer.class; 
	public static final int Base1 = 1;
	public static final int Const_dlm_len = 1;
	public static final int Const_position_after_char = 1;
	public static final int Null = Int_.Min_value;
	public static int coerce_(Object v) {
		try {String s = String_.as_(v); return s == null ? Int_.cast(v) : Int_.parse(s);}
		catch (Exception e) {throw Err_.new_cast(e, int.class, v);}
	}
	public static int[] Ary_empty = new int[0];
	public static int[] Ary(int... v) {return v;}
	public static int[] Ary_copy(int[] ary) {return Ary_copy(ary, ary.length);}
	public static int[] Ary_copy(int[] ary, int new_len) {
		int old_len = ary.length;
		int[] rv = new int[new_len];
		for (int i = 0; i < old_len; i++)
			rv[i] = ary[i];
		return rv;
	}
	public static void Ary_copy_to(int[] src, int src_len, int[] trg) {
		for (int i = 0; i < src_len; ++i)
			trg[i] = src[i];
	}
	public static String Ary_concat(String spr, int... ary) {
		Bry_bfr bfr = Bry_bfr_.New();
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			if (i != 0) bfr.Add_str_u8(spr);
			int itm = ary[i];
			bfr.Add_int_variable(itm);
		}
		return bfr.To_str_and_clear();
	}
	public static int[] AryRng(int bgn, int end) {
		int len = end - bgn + 1;
		int[] rv = new int[len];
		for (int i = 0; i < len; i++)
			rv[i] = bgn + i;
		return rv;
	}
	public static boolean Bounds_chk(int bgn, int end, int len) {return bgn > -1 && end < len;}
	public static int parse_or(String raw, int or) {
		if (raw == null) return or;
		int rawLen = String_.Len(raw); if (rawLen == 0) return or;
		int rv = 0, tmp = 0, factor = 1;
		for (int i = rawLen; i > 0; i--) {
			char c = String_.CharAt(raw, i - 1);
			switch (c) {
				case '0': tmp = 0; break; case '1': tmp = 1; break; case '2': tmp = 2; break; case '3': tmp = 3; break; case '4': tmp = 4; break;
				case '5': tmp = 5; break; case '6': tmp = 6; break; case '7': tmp = 7; break; case '8': tmp = 8; break; case '9': tmp = 9; break;
				case '-': rv *= -1; continue;	// NOTE: note continue
				default: return or;
			}
			rv += (tmp * factor);
			factor *= 10;
		}
		return rv;
	}
	public static int EnsureLessThan(int v, int max) {return v >= max ? max : v;}
	public static boolean In(int v, int comp0, int comp1) {return v == comp0 || v == comp1;}
	public static boolean In(int v, int... ary) {
		for (int itm : ary)
			if (v == itm) return true;
		return false;
	}
	public static int BoundEnd(int v, int end) {return v >= end ? end - 1 : v;}
	public static int Min(int lhs, int rhs) {return lhs < rhs ? lhs : rhs;}
	public static int Min_many(int... ary) {
		int len = ary.length; if (len == 0) throw Err_.new_wo_type("Min_many requires at least 1 value");
		boolean init = true;
		int min = Int_.Min_value;
		for (int i = 0; i < len; ++i) {
			int val = ary[i];
			if (init) {
				min = val;
				init = false;
			}
			else {
				if (val < min)
					min = val;
			}
		}
		return min;
	}
	public static int Max(int lhs, int rhs) {return lhs > rhs ? lhs : rhs;}
	public static int ModIfNeg1(int v, int or) {return v == -1 ? or : v;}
	public static boolean RangeCheck(int v, int max) {return v >= 0 && v < max;}
	public static void RangeCheckOrFail_list(int v, int max, String s)		{if (v < 0   || v >= max) throw Err_.new_wo_type("bounds check failed", "msg", s, "v", v, "min",   0, "max", max - 1);}
	public static void RangeCheckOrFail(int v, int min, int max, String s)	{if (v < min || v >= max) throw Err_.new_wo_type("bounds check failed", "msg", s, "v", v, "min", min, "max", max);}
	public static boolean Between(int v, int lhs, int rhs) {
		int lhsCompare = v == lhs ? 0 : (v < lhs ? -1 : 1);
		int rhsCompare = v == rhs ? 0 : (v < rhs ? -1 : 1);
		return (lhsCompare * rhsCompare) != 1;	// 1 when v is (a) greater than both or (b) less than both
	}
	public static int Div(int v, float divisor) {return (int)((float)v / divisor);}
	public static int DivAndRoundUp(int v, int divisor) {
		int whole = v / divisor;
		int partial = v % divisor == 0 ? 0 : 1;
		return whole + partial;
	}
	public static int Mult(int v, float multiplier) {
		float product = ((float)v * multiplier);	// WORKAROUND (DotNet): (int)((float)v * multiplier) returns 0 for 100 and .01f
		return (int)product;
	}
	public static int Compare(int lhs, int rhs) {
		if		(lhs == rhs) 	return CompareAble_.Same;
		else if (lhs < rhs)		return CompareAble_.Less;
		else 					return CompareAble_.More;
	}
	public static int DigitCount(int v) {
		int log10 = Log10(v);
		return v > -1 ? log10 + 1 : log10 * -1 + 2;
	}
	public static int Log10(int v) {
		if (v == 0) return 0;
		int sign = 1;
		if (v < 0) {
			if (v == Int_.Min_value) return -9;	// NOTE: Int_.Min_value * -1 = Int_.Min_value
			v *= -1;
			sign = -1;
		}
		int rv = Log10AryLen - 2;	// rv will only happen when v == Int_.Max_value
		int bgn = 0;
		if (v > 1000) {				// optimization to reduce number of ops to < 5
			bgn = 3;
			if (v > 1000000) bgn = 6;
		}
		for (int i = bgn; i < Log10AryLen; i++) {
			if (v < Log10Ary[i]) {rv = i - 1; break;}
		}
		return rv * sign;
	}	public static int[] Log10Ary = new int[] {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000, Int_.Max_value}; public static int Log10AryLen = 11;
	public static String To_str_pad_bgn_space(int v, int reqdPlaces)	{return To_str_pad_bgn_zero(v, reqdPlaces, Byte_ascii.Space, true);}	// EX: 1, 3 returns "  1"
	public static String To_str_pad_bgn_zero(int v, int reqdPlaces)	{return To_str_pad_bgn_zero(v, reqdPlaces, Byte_ascii.Num_0, true);}	// EX: 1, 3 returns "001"
	static String To_str_pad_bgn_zero(int val, int places, byte pad_chr, boolean bgn) {
		int len = DigitCount(val);
		int pad_len = places - len; if (pad_len < 0) return Int_.To_str(val);
		Bry_bfr bfr = Bry_bfr_.New();
		boolean neg = val < 0;
		if (bgn) {	// special logic to handle negative numbers; EX: -1 -> "-001", not "00-1"
			if (neg) {
				bfr.Add_byte(Byte_ascii.Dash);
				val *= -1;
				--len;
			}
		}
		else
			bfr.Add_int_fixed(val, len);
		bfr.Add_byte_repeat(pad_chr, pad_len);
		if (bgn) bfr.Add_int_fixed(val, len);	// NOTE: neg handled above
		return bfr.To_str();
	}
	public static int read_(Object o) {String s = String_.as_(o); return s != null ? Int_.parse(s) : Int_.cast(o);}
	public static int parse(String raw) {try {return Integer.parseInt(raw);} catch(Exception e) {throw Err_.new_parse_exc(e, int.class, raw);}}
	public static int cast(Object obj) {try {return (Integer)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, int.class, obj);}}
	public static int cast_or(Object obj, int or) {try {return (Integer)obj;} catch(Exception e) {Err_.Noop(e); return or;}}
	public static int Xby_double_(double v) {return (int)v;}
	public static String To_str(int v) {return new Integer(v).toString();}
	public static String To_str_fmt(int v, String fmt) {return new java.text.DecimalFormat(fmt).format(v);}
	public static boolean TypeMatch(Class<?> type) {return type == int.class || type == Integer.class;}
	public static int To_int_hex(byte[] src) {return To_int_hex(src, 0, src.length);}
	public static int To_int_hex(byte[] src, int bgn, int end) {
		int rv = 0; int factor = 1;
		for (int i = end - 1; i >= bgn; i--) {
			int val = To_int_hex(src[i]);
			rv += (val * factor);
			factor *= 16;
		}
		return rv;
	}
	public static int Subtract_long(long lhs, long rhs) {return (int)(lhs - rhs);}
	public static int To_int_hex(byte b) {
		switch (b) {
			case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
			case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
				return b - Byte_ascii.Num_0;
			case Byte_ascii.Ltr_A: case Byte_ascii.Ltr_B: case Byte_ascii.Ltr_C: case Byte_ascii.Ltr_D: case Byte_ascii.Ltr_E: case Byte_ascii.Ltr_F:
				return b - Byte_ascii.Ltr_A + 10;
			case Byte_ascii.Ltr_a: case Byte_ascii.Ltr_b: case Byte_ascii.Ltr_c: case Byte_ascii.Ltr_d: case Byte_ascii.Ltr_e: case Byte_ascii.Ltr_f:
				return b - Byte_ascii.Ltr_a + 10;
			default:
				return -1;
		}
	}
	public static String To_str_hex(int v) {return To_str_hex(Bool_.Y, Bool_.Y, v);}
	public static String To_str_hex(boolean zero_pad, boolean upper, int v) {
		String rv = Integer.toHexString(v); 
		int rv_len = String_.Len(rv);
		if (zero_pad && rv_len < 8) rv = String_.Repeat("0", 8 - rv_len) + rv;
		return upper ? String_.Upper(rv) : rv;
	}
	public static String To_str(int[] ary) {return To_str(ary, " ");}
	public static String To_str(int[] ary, String dlm) {
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < ary.length; i++)
			sb.Add_spr_unless_first(Int_.To_str(ary[i]), dlm, i);
		return sb.To_str();
	}
	public static int[] Ary_parse(String raw_str, int reqd_len, int[] or) {
		byte[] raw_bry = Bry_.new_a7(raw_str);
		int raw_bry_len = raw_bry.length;
		int[] rv = new int[reqd_len];
		int cur_val = 0, cur_mult = 1, cur_idx = reqd_len - 1; boolean signed = false;
		for (int i = raw_bry_len - 1; i > -2; i--) {
			byte b = i == -1 ? Byte_ascii.Comma : raw_bry[i];
			switch (b) {
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
					if (signed) return or;
					cur_val += (b - Byte_ascii.Num_0) * cur_mult;
					cur_mult *= 10;
					break;
				case Byte_ascii.Space: case Byte_ascii.Nl: case Byte_ascii.Cr: case Byte_ascii.Tab:
					break;
				case Byte_ascii.Comma:
					if (cur_idx < 0) return or;
					rv[cur_idx--] = cur_val;
					cur_val = 0; cur_mult = 1;
					signed = false;
					break;
				case Byte_ascii.Dash:
					if (signed) return or;
					cur_val *= -1;
					signed = true;
					break;
				case Byte_ascii.Plus:	// noop; all values positive by default
					if (signed) return or;
					signed = true;
					break;
				default:
					return or;
			}
		}
		return cur_idx == -1 ? rv : or;	// cur_idx == -1 checks for unfilled; EX: Ary_parse("1,2", 3, null) is unfilled
	}
	public static int[] Ary_parse(String raw_str, String spr) {
		String[] ary = String_.Split(raw_str, spr);
		int len = ary.length;
		int[] rv = new int[len];
		for (int i = 0; i < len; i++)
			rv[i] = Int_.parse(ary[i]);
		return rv;
	}
	public static byte[] To_bry(int v) {return Bry_.new_a7(To_str(v));}
	public static final int
		Min_value		= Integer.MIN_VALUE	
	,	Max_value		= Integer.MAX_VALUE	
	,	Max_value__31	= 2147483647
	,	Neg1			= -1
	,	Neg1_count		= -1
	;
}
