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
package gplx.core.brys; import gplx.*; import gplx.core.*;
public class Bry_bfr_able_ {
	public static byte[][] To_bry_ary(Bry_bfr tmp_bfr, Bry_bfr_able[] ary) {
		int len = ary.length;
		byte[][] rv = new byte[len][];
		for (int i = 0; i < len; ++i) {
			Bry_bfr_able itm = ary[i];
			if (itm != null) {
				itm.To_bfr(tmp_bfr);
				rv[i] = tmp_bfr.To_bry_and_clear();
			}
		}
		return rv;
	}
	public static byte[] To_bry_or_null(Bry_bfr tmp_bfr, Bry_bfr_able itm) {
		if (itm == null) return null;
		itm.To_bfr(tmp_bfr);
		return tmp_bfr.To_bry_and_clear();
	}
}
