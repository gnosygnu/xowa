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
package gplx;
import gplx.core.brys.*;
public class Bry_bfr_ {
        public static Bry_bfr New()				{return new Bry_bfr(16);}
        public static Bry_bfr New_w_size(int v)	{return new Bry_bfr(v);}
        public static Bry_bfr Reset(int v)		{return new Bry_bfr(16).Reset_(v);}	// PERF: set initial size to 16, not reset val; allows for faster "startup"; DATE:2014-06-14

	public static final    Bry_bfr[] Ary_empty = new Bry_bfr[0];
	private static Bry_bfr_mkr_mgr dflt;
	public static Bry_bfr Get() {if (dflt == null) dflt = new Bry_bfr_mkr_mgr(Bry_bfr_mkr.Tid_b128, 128); return dflt.Get();}	// NOTE: lazy else "Object synchronization" error; DATE:2015-11-18
	public static void Assert_at_end(Bry_bfr bfr, byte assert_byte) {
		int len = bfr.Len(); if (len == 0) return;
		int assert_count = 0;
		byte[] bfr_bry = bfr.Bfr();
		for (int i = len - 1; i > -1; --i) {
			byte b = bfr_bry[i];
			if (b == assert_byte)
				++assert_count;
			else
				break;
		}
		switch (assert_count) {
			case 0: bfr.Add_byte(assert_byte); break;
			case 1: break;
			default: bfr.Del_by(assert_count - 1); break;
		}
	}
}
