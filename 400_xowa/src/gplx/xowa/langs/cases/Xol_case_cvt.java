/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2020 https://github.com/desb42

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.langs.cases;

import gplx.Bool_;
import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;

public class Xol_case_cvt {
	public static byte[] Upper_1st(byte[] src, int pos, int src_len, boolean reuse) {return Up_low_1st(src, pos, src_len, Bool_.Y, reuse);}
	public static byte[] Upper_1st(byte[] src, int pos, int src_len) {return Up_low_1st(src, pos, src_len, Bool_.Y);}
	public static byte[] Lower_1st(byte[] src, int pos, int src_len) {return Up_low_1st(src, pos, src_len, Bool_.N);}
	public static byte[] Up_low_1st(byte[] src, int pos, int src_len, boolean upper) {
		return Up_low_1st(src, pos, src_len, upper, Bool_.Y);
	}
	public static byte[] Up_low_1st(byte[] src, int pos, int src_len, boolean upper, boolean reuse) {
		if (src_len == 0) return Bry_.Empty;
		byte b = src[pos];
		int b_len = gplx.core.intls.Utf8_.Len_of_char_by_1st_byte(b);
		if (b_len > src_len) return Bry_.Empty; // bad unicode
		if (reuse) {
			return Upper_Lower_1st(src, pos, src_len, b_len, upper);
		}
		else {
			int copyLen = src_len - pos;
			byte[] copy = new byte[copyLen];
			for (int i = 0; i < copyLen; i++) {
				copy[i] = src[pos + i];
			}
			return Upper_Lower_1st(copy, 0, copyLen, b_len, upper);
		}
	}
	public static byte[] Upper_1st(byte[] src, int pos, int src_len, int b_len) {return Upper_Lower_1st(src, pos, src_len, b_len, Bool_.Y);}
	public static byte[] Lower_1st(byte[] src, int pos, int src_len, int b_len) {return Upper_Lower_1st(src, pos, src_len, b_len, Bool_.N);}
	private static byte[] Upper_Lower_1st(byte[] src, int pos, int src_len, int b_len, boolean upper) {
		byte[] ucase;
		if (upper)
			ucase = Xol_case_cvt_.Upper(src, pos, b_len);
		else
			ucase = Xol_case_cvt_.Lower(src, pos, b_len);
		if (ucase == Xol_case_cvt_.byte_NOCHANGE)
			return src;
		if (ucase.length == b_len) {
			for (int i = 0; i < b_len; i++)
				src[pos+i] = ucase[i];
			return src;
		} else {
			// need to rebuild the byte string
			Bry_bfr tmp_bfr = Bry_bfr_.New();
			tmp_bfr.Add_mid(src, 0, pos);
			tmp_bfr.Add(ucase);
			tmp_bfr.Add_mid(src, pos + b_len, src_len);
			return tmp_bfr.To_bry_and_clear();
		}
	}

	public static byte[] Uppercase(byte[] src, int src_len) {return Case_cvt(src, src_len, Bool_.Y);}
	public static byte[] Lowercase(byte[] src, int src_len) {return Case_cvt(src, src_len, Bool_.N);}
	public static byte[] Case_cvt(byte[] src, int src_len, boolean upper) {
		Bry_bfr tmp_bfr = null;
		int pos = 0;
		while (pos < src_len) {
			byte b = src[pos];
			int b_len = gplx.core.intls.Utf8_.Len_of_char_by_1st_byte(b);
			byte[] ucase;
			if (upper)
				ucase = Xol_case_cvt_.Upper(src, pos, b_len);
			else
				ucase = Xol_case_cvt_.Lower(src, pos, b_len);
			if (tmp_bfr == null) {
				if (ucase != Xol_case_cvt_.byte_NOCHANGE) {
					if (ucase.length == b_len) {
						for (int i = 0; i < b_len; i++)
							src[pos+i] = ucase[i];
					} else {
						// need to rebuild the byte string
						tmp_bfr = Bry_bfr_.New();
						tmp_bfr.Add_mid(src, 0, pos);
						tmp_bfr.Add(ucase);
					}
				}
			} else {
				if (ucase != Xol_case_cvt_.byte_NOCHANGE) {
					tmp_bfr.Add(ucase);
				} else {
					if (b_len == 1)
						tmp_bfr.Add_byte(b);
					else
						tmp_bfr.Add_mid(src, pos, pos + b_len);
				}
			}
			pos += b_len;
		}
		if (tmp_bfr == null)
			return src;
		else
			return tmp_bfr.To_bry_and_clear();
	}
}