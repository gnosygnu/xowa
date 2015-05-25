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
public class Char_ {
	public static final char Null = '\0', NewLine = '\n';
	public static final int CharLen = 1;
	public static final int AsciiZero = 48;
	public static boolean IsNumber(char c) {
		switch (c) {
			case '0': case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9': return true;
			default: return false;
		}
	}
	public static boolean IsCaseLower(char c) {return Character.isLowerCase(c);}				
	public static boolean IsLetterOrDigit(char c) {return Character.isLetterOrDigit(c);}	
	public static boolean IsLetterEnglish(char c) {
		switch (c) {
			case 'a': case 'b': case 'c': case 'd': case 'e': case 'f': case 'g': case 'h': case 'i': case 'j': 
			case 'k': case 'l': case 'm': case 'n': case 'o': case 'p': case 'q': case 'r': case 's': case 't': 
			case 'u': case 'v': case 'w': case 'x': case 'y': case 'z':
			case 'A': case 'B': case 'C': case 'D': case 'E': case 'F': case 'G': case 'H': case 'I': case 'J': 
			case 'K': case 'L': case 'M': case 'N': case 'O': case 'P': case 'Q': case 'R': case 'S': case 'T': 
			case 'U': case 'V': case 'W': case 'X': case 'Y': case 'Z': return true;
			default: return false;
		}
	}
	public static boolean IsLetterLowerEnglish(char c) {
		switch (c) {
			case 'a': case 'b': case 'c': case 'd': case 'e': case 'f': case 'g': case 'h': case 'i': case 'j': 
			case 'k': case 'l': case 'm': case 'n': case 'o': case 'p': case 'q': case 'r': case 's': case 't': 
			case 'u': case 'v': case 'w': case 'x': case 'y': case 'z': return true;
			default: return false;
		}
	}
	public static boolean IsWhitespace(char c) {
		switch (c) {
			case ' ': case '\t': case '\n': case '\r': return true;
			default: return false;
		}
	}
	public static int To_int_or(char c, int or) {
		switch (c) {
			case '0': return 0; case '1': return 1; case '2': return 2; case '3': return 3; case '4': return 4;
			case '5': return 5; case '6': return 6; case '7': return 7; case '8': return 8; case '9': return 9;
			default: return or;
		}
	}
	public static boolean In(char match, char... ary) {
		for (char itm : ary)
			if (itm == match) return true;
		return false;
	}
	public static String XtoStr(char[] ary, int pos, int length) {return new String(ary, pos, length);}
	public static byte[] XtoByteAry(int v) {return Bry_.new_u8(Char_.XtoStr((char)v));}
	public static char XbyInt(int i) {return (char)i;}
	public static String XtoStr(int b) {return XtoStr((char)b);}
	public static String XtoStr(char c) {return String.valueOf(c);}	
	public static byte XtoByte(char c) {return (byte)c;}
	public static char cast_(Object o) {try {return (Character)o;} catch(Exception e) {throw Err_.type_mismatch_exc_(e, char.class, o);}}
	public static char parse_(String raw) {try {return raw.charAt(0);} catch(Exception exc) {throw Err_.parse_type_exc_(exc, char.class, raw);}}
}
