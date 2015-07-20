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
package gplx;
public class Base85_utl {
	public static String XtoStr(int val, int minLen) {return String_.new_u8(XtoStrByAry(val, null, 0, minLen));}
	public static byte[] XtoStrByAry(int val, int minLen) {return XtoStrByAry(val, null, 0, minLen);}
	public static byte[] XtoStrByAry(int val, byte[] ary, int aryPos, int minLen) {
		int strLen = DigitCount(val);
		int aryLen = strLen, padLen = 0;
		boolean pad = aryLen < minLen;
		if (pad) {
			padLen = minLen - aryLen;
			aryLen = minLen;
		}
		if (ary == null) ary = new byte[aryLen];
		if (pad) {
			for (int i = 0; i < padLen; i++)		// fill ary with padLen
				ary[i + aryPos] = AsciiOffset;
		}
		for (int i = aryLen - padLen; i > 0; i--) {
			int div = Pow85[i - 1];
			byte tmp = (byte)(val / div);
			ary[aryPos + aryLen - i] = (byte)(tmp + AsciiOffset);
			val -= tmp * div;
		}
		return ary;
	}
	public static byte XtoByteChar(int v) {return (byte)(v + AsciiOffset);}
	public static int XtoInt(byte v) {return v - AsciiOffset;}
	public static int XtoIntByStr(String s) {
		byte[] ary = Bry_.new_u8(s);
		return XtoIntByAry(ary, 0, ary.length - 1);
	}
	public static int XtoIntByAry(byte[] ary, int bgn, int end) {
		int rv = 0, factor = 1;
		for (int i = end; i >= bgn; i--) {
			rv += (ary[i] - AsciiOffset) * factor;
			factor *= Radix;
		}
		return rv;
	}
	public static int DigitCount(int v) {
		if (v == 0) return 1;
		for (int i = Pow85Last; i > -1; i--)
			if (v >= Pow85[i]) return i + 1;
		throw Err_.new_wo_type("neg number not allowed", "v", v);
	}
	static int[] Pow85 = new int[]{1, 85, 7225, 614125, 52200625}; // NOTE: ary constructed to match index to exponent; Pow85[1] = 85^1
	static final int Pow85Last = 4, Radix = 85; static final byte AsciiOffset = 33;
	public static final int Len_int = 5;
}
