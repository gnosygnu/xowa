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
package gplx.objects.primitives; import gplx.*; import gplx.objects.*;
import gplx.objects.errs.*;
import gplx.objects.strings.*;
public class Int_ {
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
	;

	public static int Cast(Object o) {
		try {
			return (Integer)o;
		}
		catch(Exception e) {
			throw Err_.New_fmt(e, "failed to cast to int; obj={0}", Object_.To_str(o));
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
			char cur = String_.Char_at(raw, idx);
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

	public static boolean Between(int v, int lhs, int rhs) {
		int lhs_comp = v == lhs ? 0 : (v < lhs ? -1 : 1);
		int rhs_comp = v == rhs ? 0 : (v < rhs ? -1 : 1);
		return (lhs_comp * rhs_comp) != 1;	// 1 when v is (a) greater than both or (b) less than both
	}

	private static int[] Log_10s = new int[] {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000, Int_.Max_value};
	public static int Log10(int v) {
		if (v == 0) return 0;
		int sign = 1;
		if (v < 0) {
			if (v == Int_.Min_value) return -9;	// NOTE: Int_.Min_value * -1 = Int_.Min_value
			v *= -1;
			sign = -1;
		}
		int log_10s_len = Log_10s.length;
		int rv = log_10s_len - 2;	// rv will only happen when v == Int_.Max_value
		int bgn = 0;
		if (v > 1000) {				// optimization to reduce number of ops to < 5
			bgn = 3;
			if (v > 1000000) bgn = 6;
		}
		for (int i = bgn; i < log_10s_len; i++) {
			if (v < Log_10s[i]) {rv = i - 1; break;}
		}
		return rv * sign;
	}

	public static int Count_digits(int v) {
		int log10 = Log10(v);
		return v > -1 ? log10 + 1 : log10 * -1 + 2;
	}
}
