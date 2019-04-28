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
package gplx.objects.strings.char_sources; import gplx.*; import gplx.objects.*; import gplx.objects.strings.*;
public class Char_source_ {
	public static int Index_of_any(String src, char[] ary) {
		int src_len = String_.Len(src);
		int ary_len = ary.length;
		for (int i = 0; i < src_len; i++) {
			for (int j = 0; j < ary_len; j++) {
				if (String_.Char_at(src, i) == ary[j] ) {
					return i;
				}
			}
		}
		return -1;
	}
}
