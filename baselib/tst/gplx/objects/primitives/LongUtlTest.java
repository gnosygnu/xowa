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
package gplx.objects.primitives;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.LongUtl;
import org.junit.Test;
public class LongUtlTest {
	private LongUtlTstr tstr = new LongUtlTstr();
	@Test public void DigitCount() {
		tstr.TestDigitCount(0, 1);
		tstr.TestDigitCount(1, 1);
		tstr.TestDigitCount(9, 1);
		tstr.TestDigitCount(10, 2);
		tstr.TestDigitCount(100, 3);
		tstr.TestDigitCount(10000, 5);
		tstr.TestDigitCount(100000, 6);
		tstr.TestDigitCount(1000000, 7);
		tstr.TestDigitCount(1000000000, 10);
		tstr.TestDigitCount(10000000000L, 11);
		tstr.TestDigitCount(100000000000L, 12);
		tstr.TestDigitCount(10000000000000000L, 17);
		tstr.TestDigitCount(-1, 2);
	}
	@Test public void Int_merge() {
		tstr.TestInt_merge(123, 456, 528280977864L);
		tstr.TestInt_merge(123, 457, 528280977865L);
	}
	@Test public void parse_or() {
		tstr.parse_or_tst("10000000000", 10000000000L);
	}
}
class LongUtlTstr {
	public void TestDigitCount(long val, int expd) {GfoTstr.EqLong(expd, LongUtl.DigitCount(val));}
	public void TestInt_merge(int hi, int lo, long expd) {
		GfoTstr.EqLong(expd, LongUtl.IntMerge(hi, lo));
		GfoTstr.EqLong(hi, LongUtl.IntSplitHi(expd));
		GfoTstr.EqLong(lo, LongUtl.IntSplitLo(expd));
	}
	public void parse_or_tst(String raw, long expd) {GfoTstr.EqLong(expd, LongUtl.ParseOr(raw, -1));}
}
