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
package gplx.objects.strings;
import gplx.objects.ObjectUtl;
import gplx.objects.arrays.ArrayUtl;
import gplx.objects.errs.ErrUtl;
import gplx.objects.primitives.IntUtl;
import gplx.objects.strings.bfrs.GfoStrBldr;
public class StringUtl {
	public static final Class<?> ClsRefType = String.class;
	public static final String ClsValName = "str" + "ing";
	public static final int FindNone = -1, PosNeg1 = -1;
	public static final String Empty = "", NullMark = "<<NULL>>", Tab = "\t", Lf = "\n", CrLf = "\r\n";

	public static boolean Eq(String lhs, String rhs) {return lhs == null ? rhs == null : lhs.equals(rhs);} 
	public static int Len(String s) {return s.length();} 
	public static char CharAt(String s, int i) {return s.charAt(i);}

	// use C# flavor ("a {0}") rather than Java format ("a %s"); also: (a) don't fail on format errors; (b) escape brackets by doubling
	private static final char FormatItmLhs = '{', FormatItmRhs = '}';
	public static String Format(String fmt, Object... args) {
		// method vars
		int argsLen = ArrayUtl.LenObjAry(args);
		if (argsLen == 0) return fmt; // nothing to format
		int fmtLen = Len(fmt);

		// loop vars
		int pos = 0; String argIdxStr = ""; boolean insideBrackets = false;
		GfoStrBldr bfr = new GfoStrBldr();
		while (pos < fmtLen) { // loop over every char; NOTE: UT8-SAFE b/c only checking for "{"; "}"
			char c = CharAt(fmt, pos);
			if (insideBrackets) {
				if (c == FormatItmLhs) { // first FormatItmLhs is fake; add FormatItmLhs and whatever is in argIdxStr
					bfr.AddChar(FormatItmLhs).Add(argIdxStr);
					argIdxStr = "";
				}
				else if (c == FormatItmRhs) { // itm completed
					int argsIdx = IntUtl.ParseOr(argIdxStr, IntUtl.MinValue);
					String itm = argsIdx != IntUtl.MinValue && IntUtl.Between(argsIdx, 0, argsLen - 1) // check (a) argsIdx is num; (b) argsIdx is in bounds
						? ObjectUtl.ToStrOrNullMark(args[argsIdx]) // valid; add itm
						: FormatItmLhs + argIdxStr + FormatItmRhs; // not valid; just add String
					bfr.Add(itm);
					insideBrackets = false;
					argIdxStr = "";
				}
				else
					argIdxStr += c;
			}
			else {
				if (c == FormatItmLhs || c == FormatItmRhs) {
					boolean posIsEnd = pos == fmtLen - 1;
					if (posIsEnd) // last char is "{" or "}" (and not insideBrackets); ignore and just ad
						bfr.AddChar(c);
					else {
						char next = CharAt(fmt, pos + 1);
						if (next == c) {    // "{{" or "}}": escape by doubling
							bfr.AddChar(c);
							pos++;
						}
						else
							insideBrackets = true;
					}
				}
				else
					bfr.AddChar(c);
			}
			pos++;
		}
		if (Len(argIdxStr) > 0)    // unclosed bracket; add FormatItmLhs and whatever is in argIdxStr; ex: "{0"
			bfr.AddChar(FormatItmLhs).Add(argIdxStr);
		return bfr.ToStr();
	}

	public static String NewByBryUtf8(byte[] v) {return v == null ? null : NewByBryUtf8(v, 0, v.length);}
	public static String NewByBryUtf8(byte[] v, int bgn, int end) {
		try {
			return v == null
				? null
				: new String(v, bgn, end - bgn, "UTF-8"); 
		}
		catch (Exception e) {throw ErrUtl.NewFmt(e, "unsupported encoding; bgn={0} end={1}", bgn, end);}
	}
}
