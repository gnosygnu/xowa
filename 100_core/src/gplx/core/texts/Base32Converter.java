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
package gplx.core.texts; import gplx.*; import gplx.core.*;
public class Base32Converter {
	public static String EncodeString(String orig) {return Encode(Bry_.new_u8(orig));}
	public static String Encode(byte[] raw) {
		int i = 0, index = 0, digit = 0; int currByte, nextByte;
		int rawLen = Array_.Len(raw);
		char[] ary = new char[(rawLen + 7) * 8 / 5]; int aryPos = 0;
		while (i < rawLen) {
			currByte = (raw[i] >= 0) ? raw[i] : raw[i] + 256; // unsign; java converts char 128+ -> byte -128
			if (index > 3) {		/* Is the curPath digit going to span a byte boundary? */
				if ((i+1) < rawLen) 
					nextByte = (raw[i+1] >= 0) ? raw[i+1] : (raw[i+1] + 256);
				else
					nextByte = 0;
			
				digit = currByte & (0xFF >> index);
				index = (index + 5) % 8;
				digit <<= index;
				digit |= nextByte >> (8 - index);
				i++;
			}
			else {
				digit = (currByte >> (8 - (index + 5))) & 0x1F;
				index = (index + 5) % 8;
				if (index == 0) i++;
			}
			ary[aryPos++] = String_.CharAt(chars, digit);
		}
		return new String(ary, 0, aryPos);
	}
	public static String DecodeString(String orig) {return String_.new_u8(Decode(orig));}
	public static byte[] Decode(String raw) {
		int i, index, lookup, offset; byte digit;
		int rawLen = String_.Len(raw);
		int rvLen = rawLen * 5 / 8;
		byte[] rv = new byte[rvLen];
		for (i = 0, index = 0, offset = 0; i < rawLen; i++) {
			lookup = String_.CharAt(raw, i) - '0';
			if (lookup < 0 || lookup >= valsLen) continue; /* Skip any char outside the lookup table */			
			digit = vals[lookup];
			if (digit == 0x7F) continue;		/* If this digit is not in the table, ignore it */

			if (index <= 3) {
				index = (index + 5) % 8;
				if (index == 0) {
					rv[offset] |= digit;
					offset++;
					if(offset >= rvLen) break;
				}
				else
					rv[offset] |= (byte)(digit << (8 - index));
			}
			else {
				index = (index + 5) % 8;
				rv[offset] |= (byte)(digit >> index);
				offset++;
			
				if(offset >= rvLen) break;
				rv[offset] |= (byte)(digit << (8 - index));
			}
		}
		return rv;
	}
	static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";	
	static int valsLen = 80;
	static byte[] vals = {
							 0x7F,0x7F,0x1A,0x1B,0x1C,0x1D,0x1E,0x1F, // '0', '1', '2', '3', '4', '5', '6', '7'
							 0x7F,0x7F,0x7F,0x7F,0x7F,0x7F,0x7F,0x7F, // '8', '9', ':', ';', '<', '=', '>', '?'
							 0x7F,0x00,0x01,0x02,0x03,0x04,0x05,0x06, // '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G'
							 0x07,0x08,0x09,0x0A,0x0B,0x0C,0x0D,0x0E, // 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O'
							 0x0F,0x10,0x11,0x12,0x13,0x14,0x15,0x16, // 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W'
							 0x17,0x18,0x19,0x7F,0x7F,0x7F,0x7F,0x7F, // 'X', 'Y', 'Z', '[', '\', ']', '^', '_'
							 0x7F,0x00,0x01,0x02,0x03,0x04,0x05,0x06, // '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g'
							 0x07,0x08,0x09,0x0A,0x0B,0x0C,0x0D,0x0E, // 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o'
							 0x0F,0x10,0x11,0x12,0x13,0x14,0x15,0x16, // 'p', 'q', 'r', 's', 't', 'u', 'v', 'w'
							 0x17,0x18,0x19,0x7F,0x7F,0x7F,0x7F,0x7F  // 'x', 'y', 'z', '{', '|', '}', '~', 'DEL'
						 };
}
/*
source:	Azraeus excerpt in java
		http://www.koders.com/java/fidA4D6F0DF43E6E9A6B518762366AB7232C14E9DB7.aspx
*/
