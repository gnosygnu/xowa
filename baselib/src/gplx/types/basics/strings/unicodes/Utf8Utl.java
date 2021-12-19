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
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.IntUtl;
public class Utf8Utl {
	public static int LenOfBry(byte[] ary) {
		if (ary == null) return 0;
		int rv = 0;
		int pos = 0, len = ary.length;
		while (pos < len) {
			int char_len = LenOfCharBy1stByte(ary[pos]);
			++rv;
			pos += char_len;
		}
		return rv;
	}
	public static int LenOfCharBy1stByte(byte b) {// SEE:w:UTF-8
		int i = b & 0xff;    // PATCH.JAVA:need to convert to unsigned byte
		switch (i) {
			case   0: case   1: case   2: case   3: case   4: case   5: case   6: case   7: case   8: case   9: case  10: case  11: case  12: case  13: case  14: case  15: 
			case  16: case  17: case  18: case  19: case  20: case  21: case  22: case  23: case  24: case  25: case  26: case  27: case  28: case  29: case  30: case  31: 
			case  32: case  33: case  34: case  35: case  36: case  37: case  38: case  39: case  40: case  41: case  42: case  43: case  44: case  45: case  46: case  47: 
			case  48: case  49: case  50: case  51: case  52: case  53: case  54: case  55: case  56: case  57: case  58: case  59: case  60: case  61: case  62: case  63: 
			case  64: case  65: case  66: case  67: case  68: case  69: case  70: case  71: case  72: case  73: case  74: case  75: case  76: case  77: case  78: case  79: 
			case  80: case  81: case  82: case  83: case  84: case  85: case  86: case  87: case  88: case  89: case  90: case  91: case  92: case  93: case  94: case  95: 
			case  96: case  97: case  98: case  99: case 100: case 101: case 102: case 103: case 104: case 105: case 106: case 107: case 108: case 109: case 110: case 111: 
			case 112: case 113: case 114: case 115: case 116: case 117: case 118: case 119: case 120: case 121: case 122: case 123: case 124: case 125: case 126: case 127:
			case 128: case 129: case 130: case 131: case 132: case 133: case 134: case 135: case 136: case 137: case 138: case 139: case 140: case 141: case 142: case 143: 
			case 144: case 145: case 146: case 147: case 148: case 149: case 150: case 151: case 152: case 153: case 154: case 155: case 156: case 157: case 158: case 159: 
			case 160: case 161: case 162: case 163: case 164: case 165: case 166: case 167: case 168: case 169: case 170: case 171: case 172: case 173: case 174: case 175: 
			case 176: case 177: case 178: case 179: case 180: case 181: case 182: case 183: case 184: case 185: case 186: case 187: case 188: case 189: case 190: case 191: 
				return 1;
			case 192: case 193: case 194: case 195: case 196: case 197: case 198: case 199: case 200: case 201: case 202: case 203: case 204: case 205: case 206: case 207: 
			case 208: case 209: case 210: case 211: case 212: case 213: case 214: case 215: case 216: case 217: case 218: case 219: case 220: case 221: case 222: case 223: 
				return 2;
			case 224: case 225: case 226: case 227: case 228: case 229: case 230: case 231: case 232: case 233: case 234: case 235: case 236: case 237: case 238: case 239: 
				return 3;
			case 240: case 241: case 242: case 243: case 244: case 245: case 246: case 247:
				return 4;
			default: throw ErrUtl.NewArgs("invalid initial utf8 byte", "byte", b);
		}
	}
	public static byte[] GetCharAtPosAsBry(byte[] bry, int pos) {
		int len = LenOfCharBy1stByte(bry[pos]);
		return BryLni.Mid(bry, pos, pos + len);
	}
	public static byte[] IncrementCharAtLastPos(byte[] bry) {    // EX: abc -> abd; complexity is for multi-byte chars
		int bry_len = bry.length; if (bry_len == 0) return bry;
		int pos = bry_len - 1;
		while (true) {                                                // loop bwds
			int cur_char_pos0 = GetPrvCharPos0Old(bry, pos);        // get byte0 of char
			int cur_char_len = (pos - cur_char_pos0) + 1;            // calc len of char
			int nxt_char = Codepoint_max;
			if (cur_char_len == 1) {                                // len=1; just change 1 byte
				nxt_char = IncrementChar(bry[cur_char_pos0]);        // get next char
				if (nxt_char < 128) {                                // single-byte char; just change pos
					bry = BryUtl.Copy(bry);                        // always return new bry; never reuse existing
					bry[cur_char_pos0] = (byte)nxt_char;
					return bry;
				}
			}
			int cur_char = Utf16Utl.DecodeToInt(bry, cur_char_pos0);
			nxt_char = IncrementChar(cur_char);
			if (nxt_char != IntUtl.MinValue) {
				byte[] nxt_char_as_bry = Utf16Utl.EncodeIntToBry(nxt_char);
				bry = BryUtl.Add(BryLni.Mid(bry, 0, cur_char_pos0), nxt_char_as_bry);
				return bry;
			}
			pos = cur_char_pos0 - 1;
			if (pos < 0) return null;
		}
	}
	public static int GetPrvCharPos0Old(byte[] bry, int pos) {    // find pos0 of char while moving bwd through bry; see test
		int stop = pos - 4;                        // UTF8 char has max of 4 bytes
		if (stop < 0) stop = 0;                    // if at pos 0 - 3, stop at 0
		for (int i = pos - 1; i >= stop; i--) {    // start at pos - 1, and move bwd; NOTE: pos - 1 to skip pos, b/c pos will never definitively yield any char_len info
			byte b = bry[i];
			int char_len = LenOfCharBy1stByte(b);
			switch (char_len) {    // if char_len is multi-byte and pos is at correct multi-byte pos (pos - i = # of bytes - 1), then pos0 found; EX: € = {226,130,172}; 172 is skipped; 130 has len of 1 -> continue; 226 has len of 3 and is found at correct pos for 3 byte char -> return
				case 2: if (pos - i == 1) return i; break;
				case 3: if (pos - i == 2) return i; break;
				case 4: if (pos - i == 3) return i; break;
			}
		}
		return pos;    // no mult-byte char found; return pos
	}
	public static int GetPrvCharPos0(byte[] src, int cur) {    // find pos0 of char while moving bwd through src; see test
		// do bounds checks
		if (cur == 0) return -1;
		if (cur <= -1 || cur > src.length) throw ErrUtl.NewArgs("invalid index for get_prv_char_pos0", "src", src, "cur", cur);

		// start at cur - 1; note bounds checks above
		int pos = cur - 1; 

		// get 1st byte and check if ASCII for (a) error-checking (ASCII can only be in 1st byte); (b) performance
		byte b = src[pos];
		if (b >= 0 && b <= ByteUtl.MaxValue127) return pos;

		// loop maximum of 4 times; note that UTF8 char has max of 4 bytes
		for (int i = 0; i < 4; i++) {
			int char_len = LenOfCharBy1stByte(b);
			switch (char_len) {    // if char_len is multi-byte and cur is at correct multi-byte pos (cur - i = # of bytes - 1), then pos0 found; EX: € = {226,130,172}; 172 is skipped; 130 has len of 1 -> continue; 226 has len of 3 and is found at correct cur for 3 byte char -> return
				case 2: if (i == 1) return pos; break;
				case 3: if (i == 2) return pos; break;
				case 4: if (i == 3) return pos; break;
			}

			// decrement and set byte
			pos--;
			b = src[pos];
		}

		throw ErrUtl.NewArgs("could not get prv_char", "src", src, "cur", cur);
	}
	public static int IncrementChar(int cur) {
		while (cur++ < Codepoint_max) {
			if (cur == Codepoint_surrogate_bgn) cur = Codepoint_surrogate_end + 1;    // skip over surrogate range
			if (!CodepointValid(cur)) continue;
			return cur;
		}
		return IntUtl.MinValue;
	}
	private static boolean CodepointValid(int v) {
				return Character.isDefined(v);
			}
	public static final int
		Codepoint_max = 0x10FFFF, //see http://unicode.org/glossary/
		Codepoint_surrogate_bgn = 0xD800,
		Codepoint_surrogate_end = 0xDFFF;
}
/*
== Definitions ==
=== a7 vs u8 ===
* a7 -> ASCII (7 bits)
* u8 -> UTF-8 (8 bytes)

In retrospect, better abbreviations would have been:
* ascii -> ASCII
* utf08 -> UTF-8
* utf16 -> UTF-16

=== General ===
==== Byte ====
* Standard definition; 8 bits (2^8 or 256)

==== Codepoint ====
* Represents 1 atomic character but can be composed of multiple bytes
** Examples:
<pre>
1 byte : "a"  (letter a)
2 bytes: "¢"  (cent)
3 bytes: "€"  (euro)
4 bytes: "𤭢" (Chinese character)
</pre>
* Defined by unicode as a sequence of 4 hexadecimals (2 bytes) or 8 hexadecimals (4 bytes); REF:http://www.unicode.org
** 4 hexadecimal is 2 bytes (2^(4 * 4) -> 2^16)

==== char ====
* Java definition of a codepoint which is encoded as 2 bytes (2^16 or 65,536)
* For Western langauges: 1 codepoint equals 1 char (2 bytes);
** For example, chars like "a", "œ", "é" are 1 Java char
* For Eastern langauges: 1 codepoint can equal 2 chars (4 bytes);
** For example, chars like "駣" are 2 Java chars though they represent 1 conceptual codepoint (in English terms, "駣" is a single letter just like the letter "a")

==== Supplementary characters ====
* Represents a codepoint which is defined by 3 or 4 bytes
* Is defined by 1 surrogate pair
** lo-surrogate : 2 bytes
** hi-surrogate : 2 bytes

=== Conventions ===
* Codepoints will be rendered as one int (4 bytes), not 4 hexadecimals (1 byte) 8 hexadecimal (4 bytes)
* The "char" datatype will rarely be used in code; instead byte arrays or codepoint-ints will be used
* The "character" word will not be used in comments; instead the "codepoint" word will be used
*/
