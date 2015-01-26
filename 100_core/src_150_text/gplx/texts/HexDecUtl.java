/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.texts; import gplx.*;
public class HexDecUtl {
	public static int parse_or_(String raw, int or) {
		int rv = 0; int digit; int factor = 1, rawLen = String_.Len(raw);
		for (int i = rawLen - 1; i >= 0; i--) {
			digit = XtoInt(String_.CharAt(raw, i));
			if (digit == -1) return or;
			rv += digit * factor;
			factor *= 16;
		}
		return rv;
	}
	public static int parse_or_(byte[] raw, int or) {return parse_or_(raw, 0, raw.length, or);}
	public static int parse_or_(byte[] raw, int bgn, int end, int or) {
		int rv = 0; int factor = 1;
		byte b = Byte_.Max_value_127;
		for (int i = end - 1; i >= bgn; i--) {
			switch (raw[i]) {
				case Byte_ascii.Num_0: b =  0; break; case Byte_ascii.Num_1: b =  1; break; case Byte_ascii.Num_2: b =  2; break; case Byte_ascii.Num_3: b =  3; break; case Byte_ascii.Num_4: b =  4; break;
				case Byte_ascii.Num_5: b =  5; break; case Byte_ascii.Num_6: b =  6; break; case Byte_ascii.Num_7: b =  7; break; case Byte_ascii.Num_8: b =  8; break; case Byte_ascii.Num_9: b =  9; break;
				case Byte_ascii.Ltr_A: b = 10; break; case Byte_ascii.Ltr_B: b = 11; break; case Byte_ascii.Ltr_C: b = 12; break; case Byte_ascii.Ltr_D: b = 13; break; case Byte_ascii.Ltr_E: b = 14; break; case Byte_ascii.Ltr_F: b = 15; break;
				case Byte_ascii.Ltr_a: b = 10; break; case Byte_ascii.Ltr_b: b = 11; break; case Byte_ascii.Ltr_c: b = 12; break; case Byte_ascii.Ltr_d: b = 13; break; case Byte_ascii.Ltr_e: b = 14; break; case Byte_ascii.Ltr_f: b = 15; break;
				default: b = Byte_.Max_value_127; break;
			}
			if (b == Byte_.Max_value_127) return or;
			rv += b * factor;
			factor *= 16;
		}
		return rv;
	}
	public static int parse_(String raw) {
		int rv = parse_or_(raw, -1); if (rv == -1) throw Err_.parse_("HexDec", "raw");
		return rv;
	}
	public static String XtoStr(int val, int pad) {
		char[] ary = new char[8]; int idx = 8; // 8 is max len of hexString; (2^4 * 8); EX: int.MaxValue = 7FFFFFFF
		do {
			int byt = val % 16;
			ary[--idx] = XtoChar(byt);
			val /= 16;
		}	while (val > 0);
		while (8 - idx < pad)					// pad left with zeros
			ary[--idx] = '0';
		return String_.new_charAry_(ary, idx, 8-idx);
	}
	static int XtoInt(char c) {
		switch (c) {
			case '0': return 0; case '1': return 1; case '2': return 2; case '3': return 3; case '4': return 4;
			case '5': return 5; case '6': return 6; case '7': return 7; case '8': return 8; case '9': return 9;
			case 'A': return 10; case 'B': return 11; case 'C': return 12; case 'D': return 13; case 'E': return 14; case 'F': return 15;
			case 'a': return 10; case 'b': return 11; case 'c': return 12; case 'd': return 13; case 'e': return 14; case 'f': return 15;
			default: return -1;
		}
	}
	static char XtoChar(int val) {
		switch (val) {
			case 0: return '0'; case 1: return '1'; case 2: return '2'; case 3: return '3'; case 4: return '4';
			case 5: return '5'; case 6: return '6'; case 7: return '7'; case 8: return '8'; case 9: return '9';
			case 10: return 'A'; case 11: return 'B'; case 12: return 'C'; case 13: return 'D'; case 14: return 'E'; case 15: return 'F';
			default: throw Err_.parse_("hexstring", Int_.Xto_str(val));
		}
	}
	static byte Xto_byte(int v) {
		switch (v) {
			case  0: return Byte_ascii.Num_0; case  1: return Byte_ascii.Num_1; case  2: return Byte_ascii.Num_2; case  3: return Byte_ascii.Num_3; case  4: return Byte_ascii.Num_4;
			case  5: return Byte_ascii.Num_5; case  6: return Byte_ascii.Num_6; case  7: return Byte_ascii.Num_7; case  8: return Byte_ascii.Num_8; case  9: return Byte_ascii.Num_9;
			case 10: return Byte_ascii.Ltr_A; case 11: return Byte_ascii.Ltr_B; case 12: return Byte_ascii.Ltr_C; case 13: return Byte_ascii.Ltr_D; case 14: return Byte_ascii.Ltr_E; case 15: return Byte_ascii.Ltr_F;
			default: throw Err_.parse_("hexstring", Int_.Xto_str(v));
		}
	}
	public static void Write(byte[] bry, int bgn, int end, int val) {
		for (int i = end - 1; i > bgn - 1; i--) {
			int b = val % 16;
			bry[i] = Xto_byte(b);
			val /= 16;
			if (val == 0) break;
		}
	}
}
