/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.types.basics.utls;
import gplx.types.errs.ErrUtl;
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.strings.bfrs.GfoStringBldr;
public class IntUtl extends IntLni {
	public static final String ClsValName = "int";
	public static final Class<?> ClsRefType = Integer.class;

	public static int Cast(Object o) {
		try {
			return (Integer)o;
		}
		catch(Exception e) {
			throw ErrUtl.NewFmt(e, "failed to cast to int; obj={0}", ObjectUtl.ToStr(o));
		}
	}
	public static int CastOr(Object obj, int or) {try {return (Integer)obj;} catch(Exception e) {return or;}}
	public static int CastOrParse(Object v) {
		try {
			String s = StringUtl.CastOrNull(v);
			return s == null ? Cast(v) : IntUtl.Parse(s);
		}
		catch (Exception e) {throw ErrUtl.NewCast(int.class, v);}
	}
	public static int Parse(String raw) {try {return Integer.parseInt(raw);} catch(Exception e) {throw ErrUtl.NewParse(int.class, raw);}}
	public static int ByHexBry(byte[] src) {return IntUtl.ByHexBry(src, 0, src.length);}
	public static int ByHexBry(byte[] src, int bgn, int end) {
		int rv = 0; int factor = 1;
		for (int i = end - 1; i >= bgn; i--) {
			int val = IntUtl.ByHexByte(src[i]);
			rv += (val * factor);
			factor *= 16;
		}
		return rv;
	}
	public static int ByHexByte(byte b) {
		switch (b) {
			case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
			case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
				return b - AsciiByte.Num0;
			case AsciiByte.Ltr_A: case AsciiByte.Ltr_B: case AsciiByte.Ltr_C: case AsciiByte.Ltr_D: case AsciiByte.Ltr_E: case AsciiByte.Ltr_F:
				return b - AsciiByte.Ltr_A + 10;
			case AsciiByte.Ltr_a: case AsciiByte.Ltr_b: case AsciiByte.Ltr_c: case AsciiByte.Ltr_d: case AsciiByte.Ltr_e: case AsciiByte.Ltr_f:
				return b - AsciiByte.Ltr_a + 10;
			default:
				return -1;
		}
	}
	public static byte[] ToBry(int v) {return BryUtl.NewA7(ToStr(v));}
	public static String ToStrFmt(int v, String fmt) {return new java.text.DecimalFormat(fmt).format(v);}
	public static String ToStrPadBgnSpace(int val, int reqd_len)   {return IntUtl.ToStrPad(val, reqd_len, BoolUtl.Y, AsciiByte.Space);}    // EX: 1, 3 returns "  1"
	public static String ToStrPadBgnZero(int val, int reqd_len)   {return IntUtl.ToStrPad(val, reqd_len, BoolUtl.Y, AsciiByte.Num0);}    // EX: 1, 3 returns "001"
	private static String ToStrPad(int val, int reqd_len, boolean bgn, byte pad_chr) {
		// get val_len and pad_len; exit early, if no padding needed
		int val_len = CountDigits(val);
		int pad_len = reqd_len - val_len;
		if (pad_len < 0)
			return ToStr(val);

		// padding needed
		GfoStringBldr bldr = new GfoStringBldr();

		// handle negative numbers; EX: -1 -> "-001", not "00-1"
		if (val < 0) {
			bldr.AddChar('-');
			val *= -1;
			--val_len;
		}

		// build outpt
		if (!bgn)
			bldr.AddIntFixed(val, val_len);
		bldr.AddCharRepeat((char)pad_chr, pad_len);
		if (bgn)
			bldr.AddIntFixed(val, val_len);

		return bldr.ToStr();
	}
	public static String ToStrHex(int v) {return IntUtl.ToStrHex(BoolUtl.Y, BoolUtl.Y, v);}
	public static String ToStrHex(boolean zero_pad, boolean upper, int v) {
		String rv = Integer.toHexString(v);
		int rvLen = StringUtl.Len(rv);
		if (zero_pad && rvLen < 8) rv = StringUtl.Repeat("0", 8 - rvLen) + rv;
		return upper ? StringUtl.Upper(rv) : rv;
	}
	public static int Compare(int lhs, int rhs) {
		if        (lhs == rhs)     return CompareAbleUtl.Same;
		else if (lhs < rhs)        return CompareAbleUtl.Less;
		else                     return CompareAbleUtl.More;
	}
	public static boolean In(int v, int comp0, int comp1) {return v == comp0 || v == comp1;}
	public static boolean In(int v, int... ary) {
		for (int itm : ary)
			if (v == itm) return true;
		return false;
	}

	public static int[] Log10Vals = new int[] {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000, IntUtl.MaxValue};
	public static int Log10ValsLen = 11;
	public static int Log10(int v) {
		if (v == 0) return 0;
		int sign = 1;
		if (v < 0) {
			if (v == IntUtl.MinValue) return -9;    // NOTE: IntUtl.MinValue * -1 = IntUtl.MinValue
			v *= -1;
			sign = -1;
		}
		int log10sLen = Log10Vals.length;
		int rv = log10sLen - 2;    // rv will only happen when v == IntUtl.MaxValue
		int bgn = 0;
		if (v > 1000) {                // optimization to reduce number of ops to < 5
			bgn = 3;
			if (v > 1000000) bgn = 6;
		}
		for (int i = bgn; i < log10sLen; i++) {
			if (v < Log10Vals[i]) {rv = i - 1; break;}
		}
		return rv * sign;
	}

	public static int CountDigits(int v) {
		int log10 = Log10(v);
		return v > -1 ? log10 + 1 : log10 * -1 + 2;
	}
	public static boolean RangeCheck(int v, int max) {return v >= 0 && v < max;}
	public static void RangeCheckOrFailList(int v, int max, String s)        {if (v < 0   || v >= max) throw ErrUtl.NewFmt("bounds check failed; msg={0} v={1} min={2} max={3}", s, v,   0, max - 1);}
	public static boolean BoundsChk(int bgn, int end, int len) {return bgn > -1 && end < len;}
	public static int BoundEnd(int v, int end) {return v >= end ? end - 1 : v;}
	public static int Min(int lhs, int rhs) {return lhs < rhs ? lhs : rhs;}
	public static int Max(int lhs, int rhs) {return lhs > rhs ? lhs : rhs;}
	public static int MinMany(int... ary) {
		int len = ary.length;
		if (len == 0) throw ErrUtl.NewMsg("Min_many requires at least 1 value");
		boolean init = true;
		int min = MinValue;
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
	public static int SubtractLong(long lhs, long rhs) {return (int)(lhs - rhs);}
	public static int Div(int v, float divisor) {return (int)((float)v / divisor);}
	public static int DivAndRoundUp(int v, int divisor) {
		int whole = v / divisor;
		int partial = v % divisor == 0 ? 0 : 1;
		return whole + partial;
	}
	public static int Mult(int v, float multiplier) {
		float product = ((float)v * multiplier);    // WORKAROUND (DotNet): (int)((float)v * multiplier) returns 0 for 100 and .01f
		return (int)product;
	}
}
