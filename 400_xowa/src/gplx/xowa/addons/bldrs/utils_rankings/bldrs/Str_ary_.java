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
package gplx.xowa.addons.bldrs.utils_rankings.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.utils_rankings.*;
class Str_ary_ {
	public static String[][] To_str_ary_ary(String v, String val_dlm, String row_dlm) {// "a|b|c`"
		String[] rows_ary = String_.Split(v, row_dlm);
		int rv_len = rows_ary.length;
		String[][] rv = new String[rv_len][];
		for (int i = 0; i < rv_len; ++i) {
			String row = rows_ary[i];
			String[] vals_ary = String_.Split(row, val_dlm);
			int vals_len = vals_ary.length;
			String[] rv_row = new String[vals_len];
			rv[i] = rv_row;
			for (int j = 0; j < vals_len; ++j)
				rv[i][j] = vals_ary[j];
		}
		return rv;
	}
}
