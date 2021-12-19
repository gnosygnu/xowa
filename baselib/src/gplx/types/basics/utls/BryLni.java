package gplx.types.basics.utls;
public class BryLni {
	public static final byte[] Empty = new byte[0];
	public static final int LenSafe(byte[] v) {return v == null ? 0 : v.length;}
	public static final boolean Eq(byte[] lhs, byte[] rhs) {return Eq(lhs, 0, LenSafe(lhs), rhs, 0, LenSafe(rhs));}
	public static final boolean Eq(byte[] lhs, int lhsBgn, byte[] rhs) {return Eq(lhs, lhsBgn, LenSafe(lhs), rhs, 0, LenSafe(rhs));}
	public static final boolean Eq(byte[] lhs, int lhsBgn, int lhsEnd, byte[] rhs) {return Eq(lhs, lhsBgn, lhsEnd, rhs, 0, LenSafe(rhs));}
	public static final boolean Eq(byte[] lhs, int lhsBgn, int lhsEnd, byte[] rhs, int rhsBgn, int rhsEnd) {
		if (lhsBgn == -1) return false;
		int lhsLen = LenSafe(lhs);
		if (lhsEnd > lhsLen) lhsEnd = lhsLen; // must limit lhsEnd to lhsLen, else ArrayIndexOutOfBounds below; DATE:2015-01-31

		int rhsLen = rhsEnd - rhsBgn;
		if (rhsLen != lhsEnd - lhsBgn) return false;
		if (rhsLen == 0) return lhsEnd - lhsBgn == 0;    // "" only matches ""
		for (int i = 0; i < rhsLen; i++) {
			int lhsPos = lhsBgn + i;
			if (lhsPos >= lhsEnd) return false;    // ran out of lhs; exit; EX: lhs=ab; rhs=abc
			if (lhs[lhsPos] != rhs[i + rhsBgn]) return false;
		}
		return true;
	}
	public static final byte[] NewA7(String str) {
		if (str == null) return null;
		int strLen = str.length();  // PERF:NATIVE
		if (strLen == 0) return Empty;
		byte[] rv = new byte[strLen];
		for (int i = 0; i < strLen; ++i) {
			char c = str.charAt(i); // PERF:NATIVE
			if (c > 128) c = '?';
			rv[i] = (byte)c;
		}
		return rv;
	}
	public static int NewU8ByLen(String src, int srcLen) {
		int rv = 0;
		for (int i = 0; i < srcLen; ++i) {
			char c = src.charAt(i);
			int cLen = 0;
			if      (    c <      128)  cLen = 1; // 1 <<  7
			else if (    c <     2048)  cLen = 2; // 1 << 11
			else if (   (c >    55295)            // 0xD800
					&&  (c <    56320)) cLen = 4; // 0xDFFF
			else                        cLen = 3; // 1 << 16
			if (cLen == 4) ++i;                   // surrogate is 2 wide, not 1
			rv += cLen;
		}
		return rv;
	}
	public static void NewU8Write(String src, int srcLen, byte[] trg, int trgPos) {
		for (int i = 0; i < srcLen; ++i) {
			char c = src.charAt(i);
			if      (  c <   128) {
				trg[trgPos++] = (byte)c;
			}
			else if (  c <  2048) {
				trg[trgPos++] = (byte)(0xC0 | (c >>   6));
				trg[trgPos++] = (byte)(0x80 | (c & 0x3F));
			}
			else if (  (c > 55295)    // 0xD800
					&& (c < 56320)) { // 0xDFFF
				if (i >= srcLen) throw ErrLni.NewMsg("incomplete surrogate pair at end of String");
				char nxtChar = src.charAt(i + 1);
				int v = 0x10000 + (c - 0xD800) * 0x400 + (nxtChar - 0xDC00);
				trg[trgPos++] = (byte)(0xF0 | (v >> 18));
				trg[trgPos++] = (byte)(0x80 | (v >> 12) & 0x3F);
				trg[trgPos++] = (byte)(0x80 | (v >>  6) & 0x3F);
				trg[trgPos++] = (byte)(0x80 | (v        & 0x3F));
				++i;
			}
			else {
				trg[trgPos++] = (byte)(0xE0 | (c >> 12));
				trg[trgPos++] = (byte)(0x80 | (c >>  6) & 0x3F);
				trg[trgPos++] = (byte)(0x80 | (c        & 0x3F));
			}
		}
	}
	public static void CopyTo(byte[] src, int srcBgn, int srcEnd, byte[] trg, int trgBgn) {
		int trgAdj = trgBgn - srcBgn;
		for (int i = srcBgn; i < srcEnd; i++)
			trg[i + trgAdj] = src[i];
	}
	public static byte[] Resize(byte[] src, int trgLen) {return Resize(src, 0, trgLen);}
	public static byte[] Resize(byte[] src, int srcBgn, int trgLen) {
		byte[] trg = new byte[trgLen];
		int srcLen = src.length;
		if (srcLen > trgLen) srcLen = trgLen;    // trgLen can be less than srcLen
		CopyTo(src, srcBgn, srcLen, trg, 0);
		return trg;
	}
	public static byte[] Mid(byte[] src, int bgn) {return Mid(src, bgn, src.length);}
	public static byte[] Mid(byte[] src, int bgn, int end) {
		try {
			int len = end - bgn; if (len == 0) return BryLni.Empty;
			byte[] rv = new byte[len];
			for (int i = bgn; i < end; i++)
				rv[i - bgn] = src[i];
			return rv;
		} catch (Exception e) {throw ErrLni.NewMsg("mid failed; " + " bgn=" + bgn + " end=" + end);}
	}
}
