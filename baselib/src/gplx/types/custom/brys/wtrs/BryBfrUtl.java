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
package gplx.types.custom.brys.wtrs;
public class BryBfrUtl {
	private static BryBfrMkrMgr dflt;
	public static BryWtr Get() {if (dflt == null) dflt = new BryBfrMkrMgr(BryBfrMkr.TidB128, 128); return dflt.Get();}    // NOTE: lazy else "Object synchronization" error; DATE:2015-11-18
	public static void AssertAtEnd(BryWtr bfr, byte assert_byte) {
		int len = bfr.Len(); if (len == 0) return;
		int assert_count = 0;
		byte[] bfr_bry = bfr.Bry();
		for (int i = len - 1; i > -1; --i) {
			byte b = bfr_bry[i];
			if (b == assert_byte)
				++assert_count;
			else
				break;
		}
		switch (assert_count) {
			case 0: bfr.AddByte(assert_byte); break;
			case 1: break;
			default: bfr.DelBy(assert_count - 1); break;
		}
	}
}
