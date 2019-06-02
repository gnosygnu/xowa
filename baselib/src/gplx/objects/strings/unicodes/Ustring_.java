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
package gplx.objects.strings.unicodes; import gplx.*; import gplx.objects.*; import gplx.objects.strings.*;
import gplx.objects.errs.*;
public class Ustring_ {
	public static Ustring New_codepoints(String src) {
		if (src == null) throw Err_.New_null("src");

		// calc lens
		int chars_len = src.length(); 
		int codes_len = Ustring_.Len(src, chars_len);

		return chars_len == codes_len
			? (Ustring)new Ustring_single(src, chars_len)
			: (Ustring)new Ustring_codepoints(src, chars_len, codes_len);
	}
	public static int Len(String src, int src_len) {
		int rv = 0;
		for (int i = 0; i < src_len; i++) {
			char c = src.charAt(i); 
			if (c >= Surrogate_hi_bgn && c <= Surrogate_hi_end) {
				i++;
			}
			rv++;
		}
		return rv;
	}

	public static final int // REF: https://en.wikipedia.org/wiki/Universal_Character_Set_characters
	  Surrogate_hi_bgn = 0xD800   //    55,296: Surrogate high start
	, Surrogate_hi_end = 0xDBFF   //    56,319: Surrogate high end
	, Surrogate_lo_bgn = 0xDC00   //    56,320: Surrogate low start
	, Surrogate_lo_end = 0xDFFF   //    57,343: Surrogate low end
	, Surrogate_cp_bgn = 0x010000 //    65,536: Surrogate codepoint start
	, Surrogate_cp_end = 0x10FFFF // 1,114,111: Surrogate codepoint end
	, Surrogate_range  = 0x400    //     1,024: Surrogate range (end - start) for high / low
	;
}
