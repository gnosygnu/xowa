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
package gplx.types.basics.utls;
import gplx.types.errs.ErrUtl;
public class CharUtl {
	public static final String ClsValName = "char";
	public static final Class<?> ClsRefType = Character.class;
	public static final char Null = '\0';
	public static final char NewLine = '\n';
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
	public static boolean IsNumber(char c) {
		switch (c) {
			case '0': case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9': return true;
			default: return false;
		}
	}
	public static boolean IsWhitespace(char c) {
		switch (c) {
			case ' ': case '\t': case '\n': case '\r': return true;
			default: return false;
		}
	}
	public static boolean In(char match, char... ary) {
		for (char itm : ary)
			if (itm == match) return true;
		return false;
	}
	public static int ToDigitOr(char c, int or) {
		switch (c) {
			case '0': return 0; case '1': return 1; case '2': return 2; case '3': return 3; case '4': return 4;
			case '5': return 5; case '6': return 6; case '7': return 7; case '8': return 8; case '9': return 9;
			default: return or;
		}
	}
	public static int ToInt(char c) {return (int)c;}
	public static String ToStr(char[] ary, int pos, int length) {return new String(ary, pos, length);}
	public static String ToStr(int b) {return CharUtl.ToStr((char)b);}
	public static String ToStr(char c) {return String.valueOf(c);}
	public static char ByInt(int i) {return (char)i;}
	public static char Cast(Object o) {try {return (Character)o;} catch(Exception e) {throw ErrUtl.NewCast(char.class, o);}}
	public static char Parse(String raw) {try {return raw.charAt(0);} catch(Exception exc) {throw ErrUtl.NewParse(char.class, raw);}}
}
