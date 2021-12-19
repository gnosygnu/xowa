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
import gplx.types.errs.ErrUtl;
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
			if (c >= Utf16Utl.SurrogateHiBgn && c <= Utf16Utl.SurrogateHiEnd) {
				i++;
			}
			rv++;
		}
		return rv;
	}

}
