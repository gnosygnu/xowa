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
import gplx.types.basics.strings.charSources.CharSource;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
public interface Ustring extends CharSource {
	int LenInChars();
	int MapDataToChar(int pos);
	int MapCharToData(int pos);
}
class UstringSingle implements Ustring { // 1 char == 1 codepoint
	public UstringSingle(String src, int srcLen) {
		this.src = src;
		this.srcLen = srcLen;
	}
	public String Src() {return src;} private final String src;
	public int LenInChars() {return srcLen;} private final int srcLen;
	public int LenInData() {return srcLen;}
	public String Substring(int bgn, int end) {return src.substring(bgn, end);}
	public byte[] SubstringAsBry(int bgn, int end) {
		String rv = src.substring(bgn, end);
		try {
			return rv.getBytes("UTF-8");
		} catch (Exception e) {
			throw new RuntimeException("failed to get bytes; src=" + src);
		}
	}
	public int IndexOf(CharSource find, int bgn) {return src.indexOf(find.Src(), bgn);}
	public boolean Eq(int lhsBgn, CharSource rhs, int rhsBgn, int rhsEnd) {
		if (srcLen < lhsBgn + rhsEnd || rhs.LenInData() < rhsBgn + rhsEnd)
			return false;
		while ( --rhsEnd>=0 )
			if (this.GetData(lhsBgn++) != rhs.GetData(rhsBgn++))
				return false;
		return true;
	}
	public int GetData(int i) {return StringUtl.CharAt(src, i);}
	public int MapDataToChar(int i) {if (i < 0 || i > srcLen) throw ErrUtl.NewFmt("invalid idx; idx={0} src={1}", i, src); return i;}
	public int MapCharToData(int i) {if (i < 0 || i > srcLen) throw ErrUtl.NewFmt("invalid idx; idx={0} src={1}", i, src); return i;}
}
class UstringCodepoints implements Ustring {
	private final int[] codes;
	public UstringCodepoints(String src, int charsLen, int codesLen) {
		// set members
		this.src = src;
		this.charsLen = charsLen;
		this.codesLen = codesLen;

		// make codes[]
		this.codes = new int[codesLen];
		int codeIdx = 0;
		for (int i = 0; i < charsLen; i++) {
			char c = src.charAt(i); 
			if (c >= Utf16Utl.SurrogateHiBgn && c <= Utf16Utl.SurrogateHiEnd) { // character is 1st part of surrogate-pair
				i++;
				if (i == charsLen) throw ErrUtl.NewFmt("invalid surrogate pair found; src={0}", src);
				int c2 = src.charAt(i); 
				codes[codeIdx++] = Utf16Utl.SurrogateCpBgn + (c - Utf16Utl.SurrogateHiBgn) * Utf16Utl.SurrogateRange + (c2 - Utf16Utl.SurrogateLoBgn);
			}
			else {
				codes[codeIdx++] = c;
			}
		}
	}
	public String Src() {return src;} private final String src;
	public String Substring(int bgn, int end) {
		int len = 0;
		for (int i = bgn; i < end; i++) {
			int code = codes[i];
			len += code >= Utf16Utl.SurrogateCpBgn && code <= Utf16Utl.SurrogateCpEnd ? 2 : 1;
		}
		char[] rv = new char[len];
		int rvIdx = 0;
		for (int i = bgn; i < end; i++) {
			int code = codes[i];
			if (code >= Utf16Utl.SurrogateCpBgn && code <= Utf16Utl.SurrogateCpEnd) {
				rv[rvIdx++] = (char)((code - 0x10000) / 0x400 + 0xD800);
				rv[rvIdx++] = (char)((code - 0x10000) % 0x400 + 0xDC00);
			}
			else {
				rv[rvIdx++] = (char)code;
			}
		}
		return new String(rv);
	}
	public byte[] SubstringAsBry(int bgn, int end) {
		String rv = src.substring(bgn, end);
		try {
			return rv.getBytes("UTF-8");
		} catch (Exception e) {
			throw new RuntimeException("failed to get bytes; src=" + src);
		}
	}
	public int IndexOf(CharSource find, int bgn) {
		int findLen = find.LenInData();
		int codesLen = codes.length;
		for (int i = bgn; i < codes.length; i++) {
			boolean found = true;
			for (int j = 0; j < findLen; j++) {
				int codesIdx = i + j;
				if (codesIdx >= codesLen) {
					found = false;
					break;                    
				}
				if (codes[codesIdx] != find.GetData(j)) {
					found = false;
					break;
				}
			}
			if (found == true)
				return i;
		}
		return -1;
	}
	public boolean Eq(int lhsBgn, CharSource rhs, int rhsBgn, int rhsEnd) {
		if (this.LenInData() < lhsBgn + rhsEnd || rhs.LenInData() < rhsBgn + rhsEnd)
			return false;
		while ( --rhsEnd>=0 )
			if ((this.GetData(lhsBgn++) != rhs.GetData(rhsBgn++)))
				return false;
		return true;
	}
	public int LenInChars() {return charsLen;} private final int charsLen;
	public int LenInData() {return codesLen;} private final int codesLen;
	public int GetData(int i) {return codes[i];}
	public int MapDataToChar(int codePos) {
		if (codePos == codesLen) return charsLen; // if charPos is charsLen, return codesLen; allows "int end = u.MapCharToData(strLen)"

		// sum all items before requested pos
		int rv = 0;
		for (int i = 0; i < codePos; i++) {
			rv += codes[i] < Utf16Utl.SurrogateCpBgn ? 1 : 2;
		}
		return rv;
	}
	public int MapCharToData(int charPos) {
		if (charPos == charsLen) return codesLen; // if charPos is charsLen, return codesLen; allows "int end = u.MapCharToData(strLen)"

		// sum all items before requested pos
		int rv = 0;
		for (int i = 0; i < charPos; i++) {
			char c = src.charAt(i); 
			if (c >= Utf16Utl.SurrogateHiBgn && c <= Utf16Utl.SurrogateHiEnd){ // SurrogateHi
				if (i == charPos - 1) // charPos is SurrogateLo; return -1 since SurrogateLo doesn't map to a codePos
					return -1;
			}
			else
				rv++;
		}
		return rv;
	}
}
