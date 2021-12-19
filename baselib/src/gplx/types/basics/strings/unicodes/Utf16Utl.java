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
package gplx.types.basics.strings.unicodes;
import gplx.types.basics.wrappers.IntRef;
import gplx.types.errs.ErrUtl;
public class Utf16Utl {
	public static int SurrogateMerge(int hi, int lo) { // REF: http://perldoc.perl.org/Encode/Unicode.html
		return 0x10000 + (hi - 0xD800) * 0x400 + (lo - 0xDC00);
	}
	public static void SurrogateSplit(int v, IntRef hi, IntRef lo) {
		hi.ValSet((v - 0x10000) / 0x400 + 0xD800);
		lo.ValSet((v - 0x10000) % 0x400 + 0xDC00);
	}
	public static int DecodeToInt(byte[] ary, int pos) {
		byte b0 = ary[pos];
		if      ((b0 & 0x80) == 0) {
			return  b0;
		}
		else if ((b0 & 0xE0) == 0xC0) {
			return ( b0           & 0x1f) <<  6
				|  ( ary[pos + 1] & 0x3f)
				;
		}
		else if ((b0 & 0xF0) == 0xE0) {
			return ( b0           & 0x0f) << 12
				|  ((ary[pos + 1] & 0x3f) <<  6)
				|  ( ary[pos + 2] & 0x3f)
				;            
		}
		else if ((b0 & 0xF8) == 0xF0) {
			return ( b0           & 0x07) << 18
				|  ((ary[pos + 1] & 0x3f) << 12)
				|  ((ary[pos + 2] & 0x3f) <<  6)
				|  ( ary[pos + 3] & 0x3f)
				;            
		}
		else
			throw ErrUtl.NewArgs("invalid utf8 byte", "byte", b0);
	}
	public static byte[] EncodeIntToBry(int c) {
		int bry_len = LenByInt(c);
		byte[] bry = new byte[bry_len];
		EncodeInt(c, bry, 0);
		return bry;
	}
	public static int EncodeChar(int c, char[] c_ary, int c_pos, byte[] b_ary, int b_pos) {
		if       ((c >  -1)
				&& (c < 128)) {
			b_ary[  b_pos]    = (byte)c;
			return 1;
		}
		else if (c < 2048) {
			b_ary[  b_pos]     = (byte)(0xC0 | (c >>   6));
			b_ary[++b_pos]     = (byte)(0x80 | (c & 0x3F));
			return 1;
		}   
		else if((c > 55295)                // 0xD800
				&& (c < 56320)) {            // 0xDFFF
			if (c_pos >= c_ary.length) throw ErrUtl.NewArgs("incomplete surrogate pair at end of String", "char", c);
			char nxt_char = c_ary[c_pos + 1];
			int v = SurrogateMerge(c, nxt_char);
			b_ary[b_pos]     = (byte)(0xF0 | (v >> 18));
			b_ary[++b_pos]     = (byte)(0x80 | (v >> 12) & 0x3F);
			b_ary[++b_pos]     = (byte)(0x80 | (v >>  6) & 0x3F);
			b_ary[++b_pos]     = (byte)(0x80 | (v        & 0x3F));
			return 2;
		}
		else {
			b_ary[b_pos]     = (byte)(0xE0 | (c >> 12));
			b_ary[++b_pos]     = (byte)(0x80 | (c >>  6) & 0x3F);
			b_ary[++b_pos]     = (byte)(0x80 | (c        & 0x3F));
			return 1;
		}
	}
	public static int EncodeInt(int c, byte[] src, int pos) {
		if     ((c > -1)
			&&  (c < 128)) {
			src[  pos] = (byte)c;
			return 1;
		}
		else if (c < 2048) {
			src[  pos] = (byte)(0xC0 | (c >>   6));
			src[++pos] = (byte)(0x80 | (c & 0x3F));
			return 2;
		}   
		else if (c < 65536) {
			src[pos]   = (byte)(0xE0 | (c >> 12));
			src[++pos] = (byte)(0x80 | (c >>  6) & 0x3F);
			src[++pos] = (byte)(0x80 | (c        & 0x3F));
			return 3;
		}
		else if (c < 2097152) {
			src[pos]   = (byte)(0xF0 | (c >> 18));
			src[++pos] = (byte)(0x80 | (c >> 12) & 0x3F);
			src[++pos] = (byte)(0x80 | (c >>  6) & 0x3F);
			src[++pos] = (byte)(0x80 | (c        & 0x3F));
			return 4;
		}
		else throw ErrUtl.NewArgs("UTF-16 int must be between 0 and 2097152", "char", c);
	}
	private static int LenByInt(int c) {
		if     ((c >      -1)
			&&  (c <     128)) return 1; // 1 <<  7
		else if (c <    2048)  return 2; // 1 << 11
		else if (c <   65536)  return 3; // 1 << 16
		else if (c < 2097152)  return 4;
		else throw ErrUtl.NewArgs("UTF-16 int must be between 0 and 2097152", "char", c);
	}
	public static int LenByChar(int c) {
		if      ((c >       -1)
			&&   (c <      128)) return 1; // 1 <<  7
		else if  (c <     2048)  return 2; // 1 << 11
		else if ((c >    55295)            // 0xD800
			&&   (c <    56320)) return 4; // 0xDFFF
		else if  (c <    65536)  return 3; // 1 << 16
		else throw ErrUtl.NewArgs("UTF-16 int must be between 0 and 65536", "char", c);
	}
	public static final int // REF: https://en.wikipedia.org/wiki/Universal_Character_Set_characters
		SurrogateHiBgn = 0xD800,   //    55,296: Surrogate high start
		SurrogateHiEnd = 0xDBFF,   //    56,319: Surrogate high end
		SurrogateLoBgn = 0xDC00,   //    56,320: Surrogate low start
		SurrogateLoEnd = 0xDFFF,   //    57,343: Surrogate low end
		SurrogateCpBgn = 0x010000, //    65,536: Surrogate codepoint start
		SurrogateCpEnd = 0x10FFFF, // 1,114,111: Surrogate codepoint end
		SurrogateRange = 0x400;    //     1,024: Surrogate range (end - start) for high / low
}
