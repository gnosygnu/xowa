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
import gplx.core.strings.*; import gplx.core.envs.*;
public class Time_span_ {
	public static final    Time_span Zero = new Time_span(0);
	public static final    Time_span Null = new Time_span(-1);
	public static Time_span fracs_(long val)	{return new Time_span(val);}
	public static Time_span seconds_(double seconds)	{
		long fracs = (long)(seconds * Divisors[Idx_Sec]);
		return new Time_span(fracs);
	}
	public static Time_span decimal_(Decimal_adp seconds)	{
		return new Time_span(seconds.To_long_mult_1000());
	}
	public static Time_span units_(int frc, int sec, int min, int hour) {
		int[] units = new int[] {frc, sec, min, hour};
		long fracs = Merge_long(units, Time_span_.Divisors);
		return Time_span_.fracs_(fracs);
	}
	public static Time_span from_(long bgn) {return Time_span_.fracs_(System_.Ticks() - bgn);}
	public static final long parse_null = Long_.Min_value;
	public static Time_span parse(String raw) {
		byte[] bry = Bry_.new_u8(raw);
		long fracs = parse_to_fracs(bry, 0, bry.length, false);
		return fracs == parse_null ? null : Time_span_.fracs_(fracs);
	}
	public static long parse_to_fracs(byte[] raw, int bgn, int end, boolean fail_if_ws) {
		int sign = 1, val_f = 0, val_s = 0, val_m = 0, val_h = 0, colon_pos = 0, unit_val = 0, unit_multiple = 1;
		for (int i = end - 1; i >= bgn; i--) {	// start from end; fracs should be lowest unit
			byte b = raw[i];
			switch (b) {
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
					int unit_digit = Byte_ascii.To_a7_int(b);
					unit_val = (unit_multiple == 1) ? unit_digit : unit_val + (unit_digit * unit_multiple);
					switch (colon_pos) {
						case 0:		val_s = unit_val; break;
						case 1:		val_m = unit_val; break;
						case 2:		val_h = unit_val; break;
						default:	return parse_null;	// only hour:minute:second supported for ':' separator; ':' count should be <= 2
					}
					unit_multiple *= 10;
					break;
				case Byte_ascii.Dot:
					double factor = (double)1000 / (double)unit_multiple;	// factor is necessary to handle non-standard decimals; ex: .1 -> 100; .00199 -> .001
					val_f = (int)((double)val_s * factor);	// move val_s unit_val to val_f; logic is indirect, b/c of differing inputs: "123" means 123 seconds; ".123" means 123 fractionals
					val_s = 0;
					unit_multiple = 1;
					break;
				case Byte_ascii.Colon:
					colon_pos++;
					unit_multiple = 1;
					break;
				case Byte_ascii.Dash:
					if	(i == 0 && unit_val > 0)		// only if first char && unit_val > 0 
						sign = -1;
					break;
				case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr:
					if (fail_if_ws) return parse_null;
					break;
				default:
					return parse_null;				// invalid char; return null;
			}
		}
		return sign * (val_f + (val_s * Divisors[1]) + (val_m * Divisors[2]) + (val_h * Divisors[3]));
	}
	public static String To_str(long frc, String fmt) {
		String_bldr sb = String_bldr_.new_();
		int[] units = Split_long(frc, Divisors);

		if (String_.Eq(fmt, Time_span_.Fmt_Short)) {
			for (int i = Idx_Hour; i > -1; i--) {
				int val = units[i];
				if (val == 0 && i == Idx_Hour) continue;	// skip hour if 0; ex: 01:02, instead of 00:01:02
				if (i == Idx_Frac) continue;	// skip frac b/c fmt is short
				if (sb.Count() > 0)	// sb already has unit; add delimiter
					sb.Add(Sprs[i]);
				if (val < 10)	// zeroPad
					sb.Add("0");
				sb.Add(Int_.To_str(val));
			}
			return sb.To_str_and_clear();
		}
		boolean fmt_fracs = !String_.Eq(fmt, Time_span_.Fmt_NoFractionals);
		boolean fmt_padZeros = String_.Eq(fmt, Time_span_.Fmt_PadZeros);
		if (frc == 0) return fmt_padZeros ? "00:00:00.000" : "0";

		int[] padZerosAry = ZeroPadding;
		boolean first = true;
		String dlm = "";
		int zeros = 0;
		if (frc < 0) sb.Add("-");								// negative sign
		for (int i = Idx_Hour; i > -1; i--) {						// NOTE: "> Idx_Frac" b/c frc will be handled below
			int val = units[i];
			if (i == Idx_Frac										// only write fracs...
				&& !(val == 0 && fmt_padZeros)						// ... if val == 0 && fmt is PadZeros
				&& !(val != 0 && fmt_fracs)							// ... or val != 0 && fmt is PadZeros or Default
				) continue;
			if (first && val == 0 && !fmt_padZeros) continue;		// if first and val == 0, don't full pad (avoid "00:")
			zeros = first && !fmt_padZeros ? 1 : padZerosAry[i];	// if first, don't zero pad (avoid "01")
			dlm = first ? "" : Sprs[i];						// if first, don't use dlm (avoid ":01")
			sb.Add(dlm);
			sb.Add(Int_.To_str_pad_bgn_zero(val, zeros));
			first = false;
		}
		return sb.To_str();
	}
	@gplx.Internal protected static int[] Split_long(long fracs, int[] divisors) {
		int divLength = Array_.Len(divisors);
		int[] rv = new int[divLength];
		long cur = Math_.Abs(fracs);
		for (int i = divLength - 1; i > -1; i--) {
			int divisor = divisors[i];
			long factor = cur / divisor;
			rv[i] = (int)factor;
			cur -= (factor * divisor);
		}
		return rv;
	}
	@gplx.Internal protected static long Merge_long(int[] vals, int[] divisors) {
		long rv = 0; int valLength = Array_.Len(vals);
		for (int i = 0; i < valLength; i++) {
			rv += vals[i] * divisors[i];
		}
		return rv;
	}
	public static final    String Fmt_PadZeros = "00:00:00.000";	// u,h00:m00:s00.f000
	public static final    String Fmt_Short = "short";				// u,h##:m#0:s00;
	public static final    String Fmt_Default = "0.000";			// v,#.000
	public static final    String Fmt_NoFractionals = "0";			// v,#
	@gplx.Internal protected static final    int[] Divisors =  {
												   1,			//1 fracs
												   1000,		//1,000 fracs in a second
												   60000,		//60,000 fracs in a minute (60 seconds * 1,000)
												   3600000,	//3,600,000 fracs in an hour (60 minutes * 60,000)
	};
	public static final    String MajorDelimiter = ":";
	public static final    int 
	  Idx_Frac = 0
	, Idx_Sec = 1
	, Idx_Min = 2
	, Idx_Hour = 3;
	static int[] ZeroPadding	= {3, 2, 2, 2,};
	static String[] Sprs	= {".", MajorDelimiter, MajorDelimiter, "",};
	public static Time_span cast(Object arg) {try {return (Time_span)arg;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, Time_span.class, arg);}}
	public static final double Ratio_f_to_s = 1000;
}
