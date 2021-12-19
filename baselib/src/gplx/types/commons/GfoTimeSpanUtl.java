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
package gplx.types.commons;
import gplx.types.basics.utls.ArrayUtl;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.strings.bfrs.GfoStringBldr;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.MathUtl;
import gplx.types.basics.utls.StringUtl;
public class GfoTimeSpanUtl {
	public static final GfoTimeSpan
		Zero = new GfoTimeSpan(0),
		Null = new GfoTimeSpan(-1);
	public static final long ParseNull = LongUtl.MinValue;
	public static GfoTimeSpan NewFracs(long val) {return new GfoTimeSpan(val);}
	public static GfoTimeSpan NewSeconds(double seconds) {
		long fracs = (long)(seconds * Divisors[Idx_Sec]);
		return new GfoTimeSpan(fracs);
	}
	public static GfoTimeSpan NewSeconds(GfoDecimal seconds) {return new GfoTimeSpan(seconds.ToLongMult1000());}
	public static GfoTimeSpan Parse(String raw) {
		byte[] bry = BryUtl.NewU8(raw);
		long fracs = ParseToFracs(bry, 0, bry.length, false);
		return fracs == ParseNull ? null : GfoTimeSpanUtl.NewFracs(fracs);
	}
	public static long ParseToFracs(byte[] raw, int bgn, int end, boolean fail_if_ws) {
		int sign = 1, val_f = 0, val_s = 0, val_m = 0, val_h = 0, colon_pos = 0, unit_val = 0, unit_multiple = 1;
		for (int i = end - 1; i >= bgn; i--) {    // start from end; fracs should be lowest unit
			byte b = raw[i];
			switch (b) {
				case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
				case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
					int unit_digit = AsciiByte.ToA7Int(b);
					unit_val = (unit_multiple == 1) ? unit_digit : unit_val + (unit_digit * unit_multiple);
					switch (colon_pos) {
						case 0:        val_s = unit_val; break;
						case 1:        val_m = unit_val; break;
						case 2:        val_h = unit_val; break;
						default:    return ParseNull;    // only hour:minute:second supported for ':' separator; ':' count should be <= 2
					}
					unit_multiple *= 10;
					break;
				case AsciiByte.Dot:
					double factor = (double)1000 / (double)unit_multiple;    // factor is necessary to handle non-standard decimals; ex: .1 -> 100; .00199 -> .001
					val_f = (int)((double)val_s * factor);    // move val_s unit_val to val_f; logic is indirect, b/c of differing inputs: "123" means 123 seconds; ".123" means 123 fractionals
					val_s = 0;
					unit_multiple = 1;
					break;
				case AsciiByte.Colon:
					colon_pos++;
					unit_multiple = 1;
					break;
				case AsciiByte.Dash:
					if    (i == 0 && unit_val > 0)        // only if first char && unit_val > 0 
						sign = -1;
					break;
				case AsciiByte.Space: case AsciiByte.Tab: case AsciiByte.Nl: case AsciiByte.Cr:
					if (fail_if_ws) return ParseNull;
					break;
				default:
					return ParseNull;                // invalid char; return null;
			}
		}
		return sign * (val_f + (val_s * Divisors[1]) + (val_m * Divisors[2]) + (val_h * Divisors[3]));
	}
	public static String ToStr(long frc, String fmt) {
		GfoStringBldr sb = new GfoStringBldr();
		int[] units = SplitLong(frc, Divisors);

		if (StringUtl.Eq(fmt, GfoTimeSpanUtl.Fmt_Short)) {
			for (int i = Idx_Hour; i > -1; i--) {
				int val = units[i];
				if (val == 0 && i == Idx_Hour) continue;    // skip hour if 0; ex: 01:02, instead of 00:01:02
				if (i == Idx_Frac) continue;    // skip frac b/c fmt is short
				if (sb.Len() > 0)    // sb already has unit; add delimiter
					sb.Add(Sprs[i]);
				if (val < 10)    // zeroPad
					sb.Add("0");
				sb.Add(IntUtl.ToStr(val));
			}
			return sb.ToStrAndClear();
		}
		boolean fmt_fracs = !StringUtl.Eq(fmt, GfoTimeSpanUtl.Fmt_NoFractionals);
		boolean fmt_padZeros = StringUtl.Eq(fmt, GfoTimeSpanUtl.Fmt_PadZeros);
		if (frc == 0) return fmt_padZeros ? "00:00:00.000" : "0";

		int[] padZerosAry = ZeroPadding;
		boolean first = true;
		String dlm = "";
		int zeros = 0;
		if (frc < 0) sb.Add("-");                                // negative sign
		for (int i = Idx_Hour; i > -1; i--) {                        // NOTE: "> Idx_Frac" b/c frc will be handled below
			int val = units[i];
			if (i == Idx_Frac                                        // only write fracs...
				&& !(val == 0 && fmt_padZeros)                        // ... if val == 0 && fmt is PadZeros
				&& !(val != 0 && fmt_fracs)                            // ... or val != 0 && fmt is PadZeros or Default
				) continue;
			if (first && val == 0 && !fmt_padZeros) continue;        // if first and val == 0, don't full pad (avoid "00:")
			zeros = first && !fmt_padZeros ? 1 : padZerosAry[i];    // if first, don't zero pad (avoid "01")
			dlm = first ? "" : Sprs[i];                        // if first, don't use dlm (avoid ":01")
			sb.Add(dlm);
			sb.Add(IntUtl.ToStrPadBgnZero(val, zeros));
			first = false;
		}
		return sb.ToStr();
	}
	public static int[] SplitLong(long fracs, int[] divisors) {
		int divLength = ArrayUtl.Len(divisors);
		int[] rv = new int[divLength];
		long cur = MathUtl.Abs(fracs); // NOTE: Abs necessary for toStr; EX: -1234 -> 1234 -> -1s 234f
		for (int i = divLength - 1; i > -1; i--) {
			int divisor = divisors[i];
			long factor = cur / divisor;
			rv[i] = (int)factor;
			cur -= (factor * divisor);
		}
		return rv;
	}
	public static long MergeLong(int[] vals, int[] divisors) {
		long rv = 0; int valLength = ArrayUtl.Len(vals);
		for (int i = 0; i < valLength; i++) {
			rv += vals[i] * divisors[i];
		}
		return rv;
	}
	public static final String
		Fmt_PadZeros = "00:00:00.000", // u,h00:m00:s00.f000
		Fmt_Short = "short",           // u,h##:m#0:s00;
		Fmt_Default = "0.000",         // v,#.000
		Fmt_NoFractionals = "0";       // v,#
	public static final int[] Divisors =
	{         1 //         1 fracs
	,      1000 //     1,000 fracs in a second
	,	  60000 //    60,000 fracs in a minute (60 seconds * 1,000)
	,	3600000 // 3,600,000 fracs in an hour (60 minutes * 60,000)
	};
	public static final String MajorDelimiter = ":";
	public static final int
		Idx_Frac = 0,
		Idx_Sec = 1,
		Idx_Min = 2,
		Idx_Hour = 3;
	private static int[] ZeroPadding = {3, 2, 2, 2,};
	private static String[] Sprs = {".", MajorDelimiter, MajorDelimiter, "",};
	public static final double RatioFracsToSecs = 1000;
}
