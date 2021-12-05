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
package gplx.objects.arrays;
import gplx.objects.errs.ErrUtl;
public class BryUtl {
	public static final Class<?> ClsRefType = byte[].class;
	public static final byte[] Empty = new byte[0];

	public static boolean Eq(byte[] lhs, byte[] rhs) {return Eq(lhs, 0, lhs == null ? 0 : lhs.length, rhs);}
	public static boolean Eq(byte[] lhs, int lhsBgn, int lhsEnd, byte[] rhs) {
		if      (lhs == null && rhs == null) return true;
		else if (lhs == null || rhs == null) return false;
		if (lhsBgn < 0) return false;
		int rhsLen = rhs.length;
		if (rhsLen != lhsEnd - lhsBgn) return false;
		int lhsLen = lhs.length;
		for (int i = 0; i < rhsLen; i++) {
			int lhsPos = i + lhsBgn;
			if (lhsPos == lhsLen) return false;
			if (rhs[i] != lhs[lhsPos]) return false;
		}
		return true;
	}

	public static byte[][] Ary(byte[]... ary) {return ary;}
	public static byte[][] Ary(String... ary) {
		int aryLen = ary.length;
		byte[][] rv = new byte[aryLen][];
		for (int i = 0; i < aryLen; i++) {
			String itm = ary[i];
			rv[i] = itm == null ? null : BryUtl.NewUtf08(itm);
		}
		return rv;
	}

	public static byte[] NewA7(String str) {
		if (str == null) return null;
		int str_len = str.length();
		if (str_len == 0) return Empty;
		byte[] rv = new byte[str_len];
		for (int i = 0; i < str_len; ++i) {
			char c = str.charAt(i);
			if (c > 128) c = '?';
			rv[i] = (byte)c;
		}
		return rv;
	}
	public static byte[] NewUtf08(String src) {
		try {
			int srcLen = src.length();
			if (srcLen == 0) return BryUtl.Empty;
			int bryLen = NewUtf08Count(src, srcLen);
			byte[] bry = new byte[bryLen];
			NewUtf08Write(src, srcLen, bry, 0);
			return bry;
		}
		catch (Exception e) {throw ErrUtl.NewFmt(e, "invalid UTF-8 sequence; src={0}", src);}
	}
	private static int NewUtf08Count(String src, int srcLen) {
		int rv = 0;
		for (int i = 0; i < srcLen; ++i) {
			char c = src.charAt(i);                                
			int cLen = 0;
			if      (    c <      128)      cLen = 1;      // 1 <<  7
			else if (    c <     2048)      cLen = 2;      // 1 << 11
			else if (   (c >    55295)                      // 0xD800
					&&  (c <    56320))     cLen = 4;      // 0xDFFF
			else                            cLen = 3;      // 1 << 16
			if (cLen == 4) ++i;                            // surrogate is 2 wide, not 1
			rv += cLen;
		}
		return rv;
	}
	private static void NewUtf08Write(String src, int srcLen, byte[] bry, int bryPos) {
		for (int i = 0; i < srcLen; ++i) {
			char c = src.charAt(i);                                
			if      (  c <   128) {
				bry[bryPos++] = (byte)c;
			}
			else if (  c <  2048) {
				bry[bryPos++] = (byte)(0xC0 | (c >>   6));
				bry[bryPos++] = (byte)(0x80 | (c & 0x3F));
			}
			else if (  (c > 55295)    // 0xD800
					&& (c < 56320)) { // 0xDFFF
				if (i >= srcLen) throw ErrUtl.NewMsg("incomplete surrogate pair at end of String");
				char nxtChar = src.charAt(i + 1);
				int v = 0x10000 + (c - 0xD800) * 0x400 + (nxtChar - 0xDC00);
				bry[bryPos++] = (byte)(0xF0 | (v >> 18));
				bry[bryPos++] = (byte)(0x80 | (v >> 12) & 0x3F);
				bry[bryPos++] = (byte)(0x80 | (v >>  6) & 0x3F);
				bry[bryPos++] = (byte)(0x80 | (v        & 0x3F));
				++i;
			}
			else {
				bry[bryPos++] = (byte)(0xE0 | (c >> 12));
				bry[bryPos++] = (byte)(0x80 | (c >>  6) & 0x3F);
				bry[bryPos++] = (byte)(0x80 | (c        & 0x3F));
			}
		}
	}
}
