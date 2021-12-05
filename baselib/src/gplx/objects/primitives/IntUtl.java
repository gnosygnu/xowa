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
package gplx.objects.primitives;
import gplx.objects.ObjectUtl;
import gplx.objects.errs.ErrUtl;
import gplx.objects.strings.StringUtl;
public class IntUtl {
	public static final String ClsValName = "int";
	public static final Class<?> ClsRefType = Integer.class;

	public static final int
		MinValue   = Integer.MIN_VALUE,
		MaxValue   = Integer.MAX_VALUE,
		MaxValue31 = 2147483647,
		Neg1       = -1,
		Null       = IntUtl.MinValue,
		Base1      = 1, // for base-1 lists / arrays; EX: PHP; [a, b, c]; [1] => a
		Offset1    = 1; // common symbol for + 1 after current pos; EX: StringUtl.Mid(lhs + Offset1, rhs)

	public static int Cast(Object o) {
		try {
			return (Integer)o;
		}
		catch(Exception e) {
			throw ErrUtl.NewFmt(e, "failed to cast to int; obj={0}", ObjectUtl.ToStr(o));
		}
	}
	public static String ToStr(int v) {return new Integer(v).toString();}
	public static int ParseOr(String raw, int or) {
		// process args
		if (raw == null) return or;
		int rawLen = StringUtl.Len(raw);
		if (rawLen == 0) return or;

		// loop backwards from nth to 0th char
		int rv = 0, powerOf10 = 1;
		for (int idx = rawLen - 1; idx >= 0; idx--) {
			char cur = StringUtl.CharAt(raw, idx);
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
			rv += (digit * powerOf10);
			powerOf10 *= 10;
		}
		return rv;
	}

	public static boolean Between(int v, int lhs, int rhs) {
		int lhsComp = v == lhs ? 0 : (v < lhs ? -1 : 1);
		int rhsComp = v == rhs ? 0 : (v < rhs ? -1 : 1);
		return (lhsComp * rhsComp) != 1;    // 1 when v is (a) greater than both or (b) less than both
	}

	private static int[] Log10Vals = new int[] {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000, IntUtl.MaxValue};
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
}
