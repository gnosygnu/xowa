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
public class Long__tst {
	@Test  public void DigitCount() {
		tst_DigitCount(0, 1);
		tst_DigitCount(1, 1);
		tst_DigitCount(9, 1);
		tst_DigitCount(10, 2);
		tst_DigitCount(100, 3);
		tst_DigitCount(10000, 5);
		tst_DigitCount(100000, 6);
		tst_DigitCount(1000000, 7);
		tst_DigitCount(1000000000, 10);
		tst_DigitCount(10000000000L, 11);
		tst_DigitCount(100000000000L, 12);
		tst_DigitCount(10000000000000000L, 17);
		tst_DigitCount(-1, 2);
	}	void tst_DigitCount(long val, int expd) {Tfds.Eq(expd, Long_.DigitCount(val));}
	@Test  public void Int_merge() {
		tst_Int_merge(123, 456, 528280977864L);
		tst_Int_merge(123, 457, 528280977865L);
	}
	void tst_Int_merge(int hi, int lo, long expd) {
		Tfds.Eq(expd, Long_.Int_merge(hi, lo));
		Tfds.Eq(hi, Long_.Int_split_hi(expd));
		Tfds.Eq(lo, Long_.Int_split_lo(expd));
	}
	@Test  public void parse_or() {
		parse_or_tst("10000000000", 10000000000L);
	}
	void parse_or_tst(String raw, long expd) {Tfds.Eq(expd, Long_.parse_or(raw, -1));}
}
