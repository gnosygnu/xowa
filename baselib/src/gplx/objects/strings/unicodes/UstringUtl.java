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
package gplx.objects.strings.unicodes;
import gplx.objects.errs.ErrUtl;
public class UstringUtl {
	public static Ustring NewCodepoints(String src) {
		if (src == null) throw ErrUtl.NewNull("src");

		// calc lens
		int charsLen = src.length();
		int codesLen = UstringUtl.Len(src, charsLen);

		return charsLen == codesLen
			? new UstringSingle(src, charsLen)
			: new UstringCodepoints(src, charsLen, codesLen);
	}
	public static int Len(String src, int srcLen) {
		int rv = 0;
		for (int i = 0; i < srcLen; i++) {
			char c = src.charAt(i); 
			if (c >= SurrogateHiBgn && c <= SurrogateHiEnd) {
				i++;
			}
			rv++;
		}
		return rv;
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
