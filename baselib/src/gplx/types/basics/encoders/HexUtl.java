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
package gplx.types.basics.encoders;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
public class HexUtl {
	public static int ParseOr(byte[] src, int or) {return ParseOr(src, 0, src.length, or);}
	public static int ParseOr(byte[] src, int bgn, int end, int or) {
		int rv = 0; int factor = 1;
		byte b;
		for (int i = end - 1; i >= bgn; i--) {
			switch (src[i]) {
				case AsciiByte.Num0: b =  0; break; case AsciiByte.Num1: b =  1; break; case AsciiByte.Num2: b =  2; break; case AsciiByte.Num3: b =  3; break; case AsciiByte.Num4: b =  4; break;
				case AsciiByte.Num5: b =  5; break; case AsciiByte.Num6: b =  6; break; case AsciiByte.Num7: b =  7; break; case AsciiByte.Num8: b =  8; break; case AsciiByte.Num9: b =  9; break;
				case AsciiByte.Ltr_A: b = 10; break; case AsciiByte.Ltr_B: b = 11; break; case AsciiByte.Ltr_C: b = 12; break; case AsciiByte.Ltr_D: b = 13; break; case AsciiByte.Ltr_E: b = 14; break; case AsciiByte.Ltr_F: b = 15; break;
				case AsciiByte.Ltr_a: b = 10; break; case AsciiByte.Ltr_b: b = 11; break; case AsciiByte.Ltr_c: b = 12; break; case AsciiByte.Ltr_d: b = 13; break; case AsciiByte.Ltr_e: b = 14; break; case AsciiByte.Ltr_f: b = 15; break;
				default: b = ByteUtl.MaxValue127; break;
			}
			if (b == ByteUtl.MaxValue127) return or;
			rv += b * factor;
			factor *= 16;
		}
		return rv;
	}
	public static int Parse(String src) {
		int rv = ParseOr(src, -1); if (rv == -1) throw ErrUtl.NewParse("hexstring","src");
		return rv;
	}
	public static int ParseOr(String src, int or) {
		int rv = 0; int digit = 0, factor = 1, len = StringUtl.Len(src);
		for (int i = len - 1; i > -1; --i) {
			digit = ToInt(StringUtl.CharAt(src, i));
			if (digit == -1) return or;
			rv += digit * factor;
			factor *= 16;
		}
		return rv;
	}
	public static byte[] ParseHexToBry(String src) {return ParseHexToBry(BryUtl.NewU8(src));}
	public static byte[] ParseHexToBry(byte[] src) {
		int src_len = src.length;
		if ((src_len % 2) != 0) throw ErrUtl.NewArgs("hex_utl: hex_string must have an even length; src=~{0}", src);
		int ary_len = src_len / 2;
		byte[] rv = new byte[ary_len];
		int rv_idx = 0;

		for (int i = 0; i < src_len; i += 2) {
			int val = ParseOr(src, i, i + 2, -1);
			if (val == -1) throw ErrUtl.NewArgs("hex_utl: hex_string has invalid char; src=~{0}", src);
			rv[rv_idx] = (byte)val;
			rv_idx++;
		}
		return rv;
	}
	public static byte[] EncodeBry(byte[] src) {
		int src_len = src.length;
		byte[] trg = new byte[src_len * 2];
		EncodeBry(src, trg);
		return trg;
	}
	public static void EncodeBry(byte[] src, byte[] trg) {
		int src_len = src.length, trg_len = trg.length;
		if (trg_len != src_len * 2) throw ErrUtl.NewArgs("hex", "trg.len must be src.len * 2", "src_len", src_len, "trg_len", trg_len);
		int trg_idx = -1;
		for (int src_idx = 0; src_idx < src_len; ++src_idx) {
			byte src_byte = src[src_idx];
			trg[++trg_idx] = ToByteLcase(0xf & src_byte >>> 4);
			trg[++trg_idx] = ToByteLcase(0xf & src_byte);
		}
	}
	public static String ToStr(int val, int pad) {
		char[] ary = new char[8]; int idx = 8; // 8 is max len of hexString; (2^4 * 8); EX: int.MaxValue = 7FFFFFFF
		do {
			int byt = val % 16;
			ary[--idx] = ToChar(byt);
			val /= 16;
		}   while (val > 0);
		while (8 - idx < pad)                    // pad left with zeros
			ary[--idx] = '0';
		return StringUtl.NewCharAry(ary, idx, 8-idx);
	}
	public static void Write(byte[] bry, int bgn, int end, int val) {
		for (int i = end - 1; i > bgn - 1; i--) {
			int b = val % 16;
			bry[i] = ToByteUcase(b);
			val /= 16;
			if (val == 0) break;
		}
	}
	public static boolean IsHexMany(byte... ary) {
		for (byte itm : ary) {
			if (!IsHex(itm))
				return false;
		}
		return true;
	}
	public static boolean IsHex(byte itm) {
		switch (itm) {
			case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
			case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
			case AsciiByte.Ltr_A: case AsciiByte.Ltr_B: case AsciiByte.Ltr_C: case AsciiByte.Ltr_D: case AsciiByte.Ltr_E: case AsciiByte.Ltr_F:
			case AsciiByte.Ltr_a: case AsciiByte.Ltr_b: case AsciiByte.Ltr_c: case AsciiByte.Ltr_d: case AsciiByte.Ltr_e: case AsciiByte.Ltr_f:
				return true;
			default:
				return false;
		}
	}
	private static int ToInt(char c) {
		switch (c) {
			case '0': return 0; case '1': return 1; case '2': return 2; case '3': return 3; case '4': return 4;
			case '5': return 5; case '6': return 6; case '7': return 7; case '8': return 8; case '9': return 9;
			case 'A': return 10; case 'B': return 11; case 'C': return 12; case 'D': return 13; case 'E': return 14; case 'F': return 15;
			case 'a': return 10; case 'b': return 11; case 'c': return 12; case 'd': return 13; case 'e': return 14; case 'f': return 15;
			default: return -1;
		}
	}
	private static char ToChar(int val) {
		switch (val) {
			case 0: return '0'; case 1: return '1'; case 2: return '2'; case 3: return '3'; case 4: return '4';
			case 5: return '5'; case 6: return '6'; case 7: return '7'; case 8: return '8'; case 9: return '9';
			case 10: return 'A'; case 11: return 'B'; case 12: return 'C'; case 13: return 'D'; case 14: return 'E'; case 15: return 'F';
			default: throw ErrUtl.NewParse("hexstring", IntUtl.ToStr(val));
		}
	}
	protected static byte ToByteUcase(int v) {
		switch (v) {
			case  0: return AsciiByte.Num0; case  1: return AsciiByte.Num1; case  2: return AsciiByte.Num2; case  3: return AsciiByte.Num3; case  4: return AsciiByte.Num4;
			case  5: return AsciiByte.Num5; case  6: return AsciiByte.Num6; case  7: return AsciiByte.Num7; case  8: return AsciiByte.Num8; case  9: return AsciiByte.Num9;
			case 10: return AsciiByte.Ltr_A; case 11: return AsciiByte.Ltr_B; case 12: return AsciiByte.Ltr_C; case 13: return AsciiByte.Ltr_D; case 14: return AsciiByte.Ltr_E; case 15: return AsciiByte.Ltr_F;
			default: throw ErrUtl.NewParse("hexstring", IntUtl.ToStr(v));
		}
	}
	protected static byte ToByteLcase(int v) {
		switch (v) {
			case  0: return AsciiByte.Num0; case  1: return AsciiByte.Num1; case  2: return AsciiByte.Num2; case  3: return AsciiByte.Num3;
			case  4: return AsciiByte.Num4; case  5: return AsciiByte.Num5; case  6: return AsciiByte.Num6; case  7: return AsciiByte.Num7;
			case  8: return AsciiByte.Num8; case  9: return AsciiByte.Num9; case 10: return AsciiByte.Ltr_a; case 11: return AsciiByte.Ltr_b;
			case 12: return AsciiByte.Ltr_c; case 13: return AsciiByte.Ltr_d; case 14: return AsciiByte.Ltr_e; case 15: return AsciiByte.Ltr_f;
			default: throw ErrUtl.NewParse("hexstring", IntUtl.ToStr(v));
		}
	}
}
