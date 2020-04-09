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
	// -------- BASELIB_COPY --------
	public static final String Cls_val_name = "int";
	public static final    Class<?> Cls_ref_type = Integer.class; 

	public static final int
	  Min_value     = Integer.MIN_VALUE	
	, Max_value     = Integer.MAX_VALUE	
	, Max_value__31	= 2147483647
	, Neg1          = -1
	, Null          = Int_.Min_value
	, Base1         = 1 // for super 1 lists / arrays; EX: PHP; [a, b, c]; [1] => a
	, Offset_1      = 1 // common symbol for + 1 after current pos; EX: String_.Mid(lhs + Offset_1, rhs)
	, Zero          = 0
	;

	public static int Cast(Object obj) {
		try {
			return (Integer)obj;
		}
		catch(Exception exc) {
			throw Err_.new_type_mismatch_w_exc(exc, int.class, obj);
		}
	}

	public static String To_str(int v) {return new Integer(v).toString();}
	public static int Parse_or(String raw, int or) {
		// process args
		if (raw == null) return or;
		int raw_len = String_.Len(raw);
		if (raw_len == 0) return or;

		// loop backwards from nth to 0th char
		int rv = 0, power_of_10 = 1;
		for (int idx = raw_len - 1; idx >= 0; idx--) {
			char cur = String_.CharAt(raw, idx);
			int digit = -1;
			switch (cur) {
				// numbers -> assign digit
				case '0': digit = 0; break; case '1': digit = 1; break; case '2': digit = 2; break; case '3': digit = 3; break; case '4': digit = 4; break;
				case '5': digit = 5; break; case '6': digit = 6; break; case '7': digit = 7; break; case '8': digit = 8; break; case '9': digit = 9; break;

				// negative sign
				case '-': 
					if (idx != 0) { // invalid if not 1st
						return or;
					}
					else { // is first; multiply by -1
						rv *= -1;
						continue;
					}

				// anything else
				default:
					return or;
			}
			rv += (digit * power_of_10);
			power_of_10 *= 10;
		}
		return rv;
	}

	public static int[] Log10Ary = new int[] {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000, Int_.Max_value};
	public static int Log10AryLen = 11;
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
	}

	public static int DigitCount(int v) {
		int log10 = Log10(v);
		return v > -1 ? log10 + 1 : log10 * -1 + 2;
	}

	// -------- TO_MIGRATE --------
	public static int Cast_or(Object obj, int or) {
		try {
			return (Integer)obj;
		}
		catch(Exception e) {
			Err_.Noop(e);
			return or;
		}
	}
	public static int Coerce(Object v) {
		try {
			String s = String_.as_(v);
			return s == null ? Int_.Cast(v) : Int_.Parse(s);
		}
		catch (Exception e) {throw Err_.new_cast(e, int.class, v);}
	}

	public static int Parse(String raw) {try {return Integer.parseInt(raw);} catch(Exception e) {throw Err_.new_parse_exc(e, int.class, raw);}}


	public static int By_double(double v) {return (int)v;}
	public static int By_hex_bry(byte[] src) {return By_hex_bry(src, 0, src.length);}
	public static int By_hex_bry(byte[] src, int bgn, int end) {
		int rv = 0; int factor = 1;
		for (int i = end - 1; i >= bgn; i--) {
			int val = By_hex_byte(src[i]);
			rv += (val * factor);
			factor *= 16;
		}
		return rv;
	}
	public static int By_hex_byte(byte b) {
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

	public static byte[] To_bry(int v) {return Bry_.new_a7(To_str(v));}
	public static String To_str_fmt(int v, String fmt) {return new java.text.DecimalFormat(fmt).format(v);}
	public static String To_str_pad_bgn_space(int val, int reqd_len)   {return To_str_pad(val, reqd_len, Bool_.Y, Byte_ascii.Space);}	// EX: 1, 3 returns "  1"
	public static String To_str_pad_bgn_zero (int val, int reqd_len)   {return To_str_pad(val, reqd_len, Bool_.Y, Byte_ascii.Num_0);}	// EX: 1, 3 returns "001"
	private static String To_str_pad(int val, int reqd_len, boolean bgn, byte pad_chr) {
		// get val_len and pad_len; exit early, if no padding needed
		int val_len = DigitCount(val);
		int pad_len = reqd_len - val_len; 
		if (pad_len < 0)
			return Int_.To_str(val);

		// padding needed
		Bry_bfr bfr = Bry_bfr_.New();

		// handle negative numbers; EX: -1 -> "-001", not "00-1"
		if (val < 0) {
			bfr.Add_byte(Byte_ascii.Dash);
			val *= -1;
			--val_len;
		}

		// build outpt
		if (!bgn)
			bfr.Add_int_fixed(val, val_len);
		bfr.Add_byte_repeat(pad_chr, pad_len);
		if (bgn)
			bfr.Add_int_fixed(val, val_len);

		return bfr.To_str();
	}
	public static String To_str_hex(int v) {return To_str_hex(Bool_.Y, Bool_.Y, v);}
	public static String To_str_hex(boolean zero_pad, boolean upper, int v) {
		String rv = Integer.toHexString(v); 
		int rv_len = String_.Len(rv);
		if (zero_pad && rv_len < 8) rv = String_.Repeat("0", 8 - rv_len) + rv;
		return upper ? String_.Upper(rv) : rv;
	}

	public static int Compare(int lhs, int rhs) {
		if		(lhs == rhs) 	return CompareAble_.Same;
		else if (lhs < rhs)		return CompareAble_.Less;
		else 					return CompareAble_.More;
	}
	public static boolean In(int v, int comp0, int comp1) {return v == comp0 || v == comp1;}
	public static boolean In(int v, int... ary) {
		for (int itm : ary)
			if (v == itm) return true;
		return false;
	}
	public static boolean Between(int v, int lhs, int rhs) {
		int lhsCompare = v == lhs ? 0 : (v < lhs ? -1 : 1);
		int rhsCompare = v == rhs ? 0 : (v < rhs ? -1 : 1);
		return (lhsCompare * rhsCompare) != 1;	// 1 when v is (a) greater than both or (b) less than both
	}
	public static boolean RangeCheck(int v, int max) {return v >= 0 && v < max;}
	public static void RangeCheckOrFail_list(int v, int max, String s)		{if (v < 0   || v >= max) throw Err_.new_wo_type("bounds check failed", "msg", s, "v", v, "min",   0, "max", max - 1);}
	public static boolean Bounds_chk(int bgn, int end, int len) {return bgn > -1 && end < len;}
	public static int BoundEnd(int v, int end) {return v >= end ? end - 1 : v;}
	public static int EnsureLessThan(int v, int max) {return v >= max ? max : v;}

	public static int Min(int lhs, int rhs) {return lhs < rhs ? lhs : rhs;}
	public static int Max(int lhs, int rhs) {return lhs > rhs ? lhs : rhs;}
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

	public static int Subtract_long(long lhs, long rhs) {return (int)(lhs - rhs);}
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
}
