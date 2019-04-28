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
package gplx.objects.brys; import gplx.*; import gplx.objects.*;
import gplx.objects.errs.*;
public class Bry_ {
	public static final    Class<?> Cls_ref_type = byte[].class;
	public static final    byte[] Empty = new byte[0];

	public static boolean Eq(byte[] lhs, byte[] rhs) {return Eq(lhs, 0, lhs == null ? 0 : lhs.length, rhs);}
	public static boolean Eq(byte[] lhs, int lhs_bgn, int lhs_end, byte[] rhs) {
		if		(lhs == null && rhs == null) return true;
		else if (lhs == null || rhs == null) return false;
		if (lhs_bgn < 0) return false;
		int rhs_len = rhs.length;
		if (rhs_len != lhs_end - lhs_bgn) return false;
		int lhs_len = lhs.length;
		for (int i = 0; i < rhs_len; i++) {
			int lhs_pos = i + lhs_bgn;
			if (lhs_pos == lhs_len) return false;
			if (rhs[i] != lhs[lhs_pos]) return false;
		}
		return true;
	}

	public static byte[][] Ary(byte[]... ary) {return ary;}
	public static byte[][] Ary(String... ary) {
		int ary_len = ary.length;
		byte[][] rv = new byte[ary_len][];
		for (int i = 0; i < ary_len; i++) {
			String itm = ary[i];
			rv[i] = itm == null ? null : Bry_.New_utf08(itm);
		}
		return rv;
	}

	public static byte[] New_utf08(String src) {
		try {
			int src_len = src.length(); 
			if (src_len == 0) return Bry_.Empty;
			int bry_len = New_utf08__count(src, src_len);
			byte[] bry = new byte[bry_len];
			New_utf08__write(src, src_len, bry, 0);
			return bry;
		}
		catch (Exception e) {throw Err_.New_fmt(e, "invalid UTF-8 sequence; src={0}", src);}
	}
	public static int New_utf08__count(String src, int src_len) {
		int rv = 0;
		for (int i = 0; i < src_len; ++i) {
			char c = src.charAt(i);                                
			int c_len = 0;
			if      (    c <      128)      c_len = 1;      // 1 <<  7
			else if (    c <     2048)      c_len = 2;      // 1 << 11
			else if (   (c >    55295)                      // 0xD800
					&&  (c <    56320))     c_len = 4;      // 0xDFFF
			else                            c_len = 3;      // 1 << 16
			if (c_len == 4) ++i;                            // surrogate is 2 wide, not 1
			rv += c_len;
		}
		return rv;
	}
	public static void New_utf08__write(String src, int src_len, byte[] bry, int bry_pos) {
		for (int i = 0; i < src_len; ++i) {
			char c = src.charAt(i);                                
			if      (    c <   128) {
				bry[bry_pos++] = (byte)c;
			}
			else if (	 c <  2048) {
				bry[bry_pos++] = (byte)(0xC0 | (c >>   6));
				bry[bry_pos++] = (byte)(0x80 | (c & 0x3F));
			}
			else if	(	(c > 55295)                         // 0xD800
					&&	(c < 56320)) {                      // 0xDFFF
				if (i >= src_len) throw Err_.New_msg("incomplete surrogate pair at end of String");
				char nxt_char = src.charAt(i + 1);                 
				int v = 0x10000 + (c - 0xD800) * 0x400 + (nxt_char - 0xDC00);
				bry[bry_pos++] = (byte)(0xF0 | (v >> 18));
				bry[bry_pos++] = (byte)(0x80 | (v >> 12) & 0x3F);
				bry[bry_pos++] = (byte)(0x80 | (v >>  6) & 0x3F);
				bry[bry_pos++] = (byte)(0x80 | (v        & 0x3F));
				++i;
			}
			else {
				bry[bry_pos++] = (byte)(0xE0 | (c >> 12));
				bry[bry_pos++] = (byte)(0x80 | (c >>  6) & 0x3F);
				bry[bry_pos++] = (byte)(0x80 | (c        & 0x3F));
			}
		}
	}
}
