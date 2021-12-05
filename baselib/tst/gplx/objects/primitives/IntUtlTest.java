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
import gplx.tests.GfoTstr;
import org.junit.Test;
public class IntUtlTest {
	private final IntUtlTstr fxt = new IntUtlTstr();
	@Test public void ParseOr() {
		fxt.TestParseOr("123", 123);            // basic
		fxt.TestParseOrMinValue(null);        // null
		fxt.TestParseOrMinValue("");          // empty
		fxt.TestParseOrMinValue("1a");        // invalid number
		
		fxt.TestParseOr("-123", -123);          // negative
		fxt.TestParseOrMinValue("1-23");      // negative at invalid position
	}
	@Test public void Between() {
		fxt.TestBetween(1, 0, 2, true);   // simple true
		fxt.TestBetween(3, 0, 2, false);  // simple false
		fxt.TestBetween(0, 0, 2, true);   // bgn true
		fxt.TestBetween(2, 0, 2, true);   // end true
	}
	@Test public void CountDigits() {
		fxt.TestCountDigits(   0, 1);
		fxt.TestCountDigits(   9, 1);
		fxt.TestCountDigits( 100, 3);
		fxt.TestCountDigits(  -1, 2);
		fxt.TestCountDigits(-100, 4);
	}
	@Test public void Log10() {
		fxt.TestLog10(             0,  0);
		fxt.TestLog10(             1,  0);
		fxt.TestLog10(             2,  0);
		fxt.TestLog10(            10,  1);
		fxt.TestLog10(            12,  1);
		fxt.TestLog10(           100,  2);
		fxt.TestLog10(           123,  2);
		fxt.TestLog10(          1000,  3);
		fxt.TestLog10(          1234,  3);
		fxt.TestLog10(         10000,  4);
		fxt.TestLog10(         12345,  4);
		fxt.TestLog10(        100000,  5);
		fxt.TestLog10(        123456,  5);
		fxt.TestLog10(       1000000,  6);
		fxt.TestLog10(       1234567,  6);
		fxt.TestLog10(      10000000,  7);
		fxt.TestLog10(      12345678,  7);
		fxt.TestLog10(     100000000,  8);
		fxt.TestLog10(     123456789,  8);
		fxt.TestLog10(    1000000000,  9);
		fxt.TestLog10(    1234567890,  9);
		fxt.TestLog10(IntUtl.MaxValue,  9);
		fxt.TestLog10(            -1,  0);
		fxt.TestLog10(           -10, -1);
		fxt.TestLog10(          -100, -2);
		fxt.TestLog10(      -1000000, -6);
		fxt.TestLog10(   -1000000000, -9);
		fxt.TestLog10(IntUtl.MinValue, -9);
		fxt.TestLog10(IntUtl.MinValue + 1, -9);
	}
}
class IntUtlTstr {
	public void TestParseOr(String raw, int expd) {
		GfoTstr.EqInt(expd, IntUtl.ParseOr(raw, -1));
	}
	public void TestParseOrMinValue(String raw) {
		GfoTstr.EqInt(IntUtl.MinValue, IntUtl.ParseOr(raw, IntUtl.MinValue));
	}
	public void TestBetween(int val, int lhs, int rhs, boolean expd) {
		GfoTstr.EqBool(expd, IntUtl.Between(val, lhs, rhs));
	}
	public void TestCountDigits(int val, int expd) {
		GfoTstr.EqInt(expd, IntUtl.CountDigits(val), IntUtl.ToStr(val));
	}
	public void TestLog10(int val, int expd) {
		GfoTstr.EqInt(expd, IntUtl.Log10(val));
	}
}
