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
import org.junit.*;
public class Int__tst {
	@Test public void XtoStr_PadBgn() {
		tst_XtoStr_PadLeft_Zeroes(1		, 3, "001");		// pad
		tst_XtoStr_PadLeft_Zeroes(123	, 3, "123");		// no pad
		tst_XtoStr_PadLeft_Zeroes(1234	, 3, "1234");		// val exceeds pad; confirm noop
		tst_XtoStr_PadLeft_Zeroes(-1	, 3, "-01");		// negative
		tst_XtoStr_PadLeft_Zeroes(-12	, 3, "-12");		// negative
		tst_XtoStr_PadLeft_Zeroes(-123	, 3, "-123");		// negative
		tst_XtoStr_PadLeft_Zeroes(-1234	, 3, "-1234");		// negative
	}	void tst_XtoStr_PadLeft_Zeroes(int val, int zeros, String expd) {Tfds.Eq(expd, Int_.To_str_pad_bgn_zero(val, zeros));}
	@Test public void Xto_fmt() {
		tst_XtoStr_fmt(1, "1");
		tst_XtoStr_fmt(1000, "1,000");
	}	void tst_XtoStr_fmt(int v, String expd) {Tfds.Eq(expd, Int_.To_str_fmt(v, "#,###"));}
	@Test public void Xto_int_hex_tst() {
		Xto_int_hex("007C", 124);
	}	void Xto_int_hex(String raw, int expd) {Tfds.Eq(expd, Int_.By_hex_bry(Bry_.new_a7(raw)));}
}
