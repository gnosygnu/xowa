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
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.types.errs.ErrUtl;
public class DoubleUtl {
	public static final String ClsValName = "double";
	public static final Class<?> ClsRefType = Double.class;
	public static final double MinValue = Double.MIN_VALUE;
	public static final double NaN = Double.NaN;
	public static final double InfinityPos = Double.POSITIVE_INFINITY;
	public static final byte[] NaNBry = BryUtl.NewA7("NaN");
	public static final byte[] InfinityPosBry = BryUtl.NewA7("INF");
	public static String ToStrByPrintF(double val) {
		// call sprintf-like format; EX:"sprintf((s), "%.14g", (n));"
		return TrimZeroes(String.format("%.14g", val));
	}
	public static String TrimZeroes(String valStr) { // TEST
		// NOTE:this function was originally in LuaDouble; it was refactored for clarity, and also to handle trimming zeroes from exponent; EX:"e-05" ISSUE#:697; DATE:2020-08-12
		int valStrLen = valStr.length();
		boolean startTrimming = true; // will be set to true at end of string (12.00), or before "e" (1.00e5)
		boolean trimmingZeroes = false;
		int trimIdx = -1;
		int expIdx = -1;

		// read backwards from end of string
		for (int i = valStrLen - 1; i > -1; i--) {
			switch (valStr.charAt(i)) {
				case '0':
					// if startTrimming is true, then enable trimmingZeroes; should only occur twice; (1) end of string ("12.00"); (2) before "e" ("1.00e5")
					if (startTrimming) {
						startTrimming = false;
						trimmingZeroes = true;
					}

					// if trimmingZeroes, set trimIdx just before this `0`
					if (trimmingZeroes)
						trimIdx = i;
					break;
				case '1': case '2': case '3': case '4': case '5':
				case '6': case '7': case '8': case '9':
					// disable trimmingZeroes; EX: "12.30"
					trimmingZeroes = false;
					startTrimming = false; // mark startTrimming false else will gobble up zeroes before numbers; EX: "0.00001" x> "0.1"; ISSUE#:697; DATE:2020-08-11
					break;
				case '-': // ignore scientific notation or negative sign; EX: "5.0e-17", "-1"
					break;
				case 'e': // scientific notation; reset startTrimming; EX: 5.0000000000000e-17
					expIdx = i;
					startTrimming = true;
					break;
				case '.':
					// if still trimmingZeroes, and reached decimalPoint, then update trimIdx to truncate decimalPoint also; EX: "123.0000" -> "123" x> "123."
					if (trimmingZeroes)
						trimIdx = i;

					// trimIdx has been set
					if (trimIdx != -1) {
						if (expIdx == -1) { // decimal; EX: "12.00"
							valStr = valStr.substring(0, trimIdx);
						}
						else { // exponent; "1.00e5"
							// get exponent portion; EX: "e-05"
							String expStr = valStr.substring(expIdx, valStrLen);
							int expStrLen = expStr.length();

							// set expSymIdx to be after "e" or "e-"
							int expSymIdx = 1; // skip the "e"
							if (expSymIdx < expStrLen && !Character.isDigit(expStr.charAt(expSymIdx))) { // skip "-" if it's there
								expSymIdx++;
							}

							// exponent can have 0s; skip them; EX: 1.1e-05
							int expNumIdx = expSymIdx;
							for (int j = expNumIdx; j < expStrLen; j++) {
								if (expStr.charAt(j) == '0') { // skip zeroes
									expNumIdx++;
								}
								else {
									break;
								}
							}

							// now stitch it together
							valStr  = valStr.substring(0, trimIdx)
									+ expStr.substring(0, expSymIdx) // add "e-"
									+ expStr.substring(expNumIdx); // add numbers with any leading zeroes trimmed
						}
					}

					// NOTE: "e" needs to be uppercased to "E"; PAGE:en.w:List_of_countries_and_dependencies_by_population
					if (expIdx != -1) {
						valStr = valStr.toUpperCase();
					}
					i = -1; // no more trimming needed before decimalPoint; stop looping
					break;
				default:    // anything else; stop looping
					i = -1;
					break;
			}
		}
		return valStr;
	}
	public static double Cast(Object o) {try {return (Double)o;} catch(Exception e) {throw ErrUtl.NewCast(double.class, o);}}
	public static double Parse(String raw)             {try {return Double.parseDouble(raw);} catch(Exception e) {throw ErrUtl.NewParse(double.class, raw);}}
	public static double ParseOr(String raw, double v) {try {return Double.parseDouble(raw);} catch(Exception e) {return v;}}
	public static double CastOrParse(Object v) {
		try {String s = StringUtl.CastOrNull(v); return s == null ? DoubleUtl.Cast(v) : DoubleUtl.Parse(s);}
		catch (Exception e) {throw ErrUtl.NewCast(double.class, v);}
	}
	public static boolean IsNaN(double v) {return Double.isNaN(v);}
	public static String ToStr(double v) {
		int vInt = (int)v;
		return v - vInt == 0 ? IntUtl.ToStr(vInt) : Double.toString(v);
	}
	public static String ToStrLoose(double v) {
		int vInt = (int)v;
		return v == vInt
			// convert to int, and call print String to eliminate any trailing decimal places
			? IntUtl.ToStr(vInt)
			// DATE:2014-07-29; calling ((float)v).toString is better at removing trailing 0s than String.format("%g", v). note that .net .toString() handles it better; EX:2449.600000000000d
			// DATE:2020-08-12; calling ToStrByPrintF b/c better at removing trailing 0s; ISSUE#:697;
			: DoubleUtl.ToStrByPrintF(v);
	}
	public static int Compare(double lhs, double rhs) {
		if      (lhs == rhs) return CompareAbleUtl.Same;
		else if (lhs <  rhs) return CompareAbleUtl.Less;
		else                 return CompareAbleUtl.More;
	}
}
