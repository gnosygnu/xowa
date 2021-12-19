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
import gplx.types.basics.utls.BryUtl;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.IntUtl;
import org.junit.Test;
public class IntUtlTest {
	private final IntUtlTstr tstr = new IntUtlTstr();
	@Test public void ParseOr() {
		tstr.TestParseOr("123", 123);            // basic
		tstr.TestParseOrMinValue(null);        // null
		tstr.TestParseOrMinValue("");          // empty
		tstr.TestParseOrMinValue("1a");        // invalid number
		
		tstr.TestParseOr("-123", -123);          // negative
		tstr.TestParseOrMinValue("1-23");      // negative at invalid position
	}
	@Test public void Between() {
		tstr.TestBetween(1, 0, 2, true);   // simple true
		tstr.TestBetween(3, 0, 2, false);  // simple false
		tstr.TestBetween(0, 0, 2, true);   // bgn true
		tstr.TestBetween(2, 0, 2, true);   // end true
	}
	@Test public void CountDigits() {
		tstr.TestCountDigits(   0, 1);
		tstr.TestCountDigits(   9, 1);
		tstr.TestCountDigits( 100, 3);
		tstr.TestCountDigits(  -1, 2);
		tstr.TestCountDigits(-100, 4);
	}
	@Test public void Log10() {
		tstr.TestLog10(             0,  0);
		tstr.TestLog10(             1,  0);
		tstr.TestLog10(             2,  0);
		tstr.TestLog10(            10,  1);
		tstr.TestLog10(            12,  1);
		tstr.TestLog10(           100,  2);
		tstr.TestLog10(           123,  2);
		tstr.TestLog10(          1000,  3);
		tstr.TestLog10(          1234,  3);
		tstr.TestLog10(         10000,  4);
		tstr.TestLog10(         12345,  4);
		tstr.TestLog10(        100000,  5);
		tstr.TestLog10(        123456,  5);
		tstr.TestLog10(       1000000,  6);
		tstr.TestLog10(       1234567,  6);
		tstr.TestLog10(      10000000,  7);
		tstr.TestLog10(      12345678,  7);
		tstr.TestLog10(     100000000,  8);
		tstr.TestLog10(     123456789,  8);
		tstr.TestLog10(    1000000000,  9);
		tstr.TestLog10(    1234567890,  9);
		tstr.TestLog10(IntUtl.MaxValue,  9);
		tstr.TestLog10(            -1,  0);
		tstr.TestLog10(           -10, -1);
		tstr.TestLog10(          -100, -2);
		tstr.TestLog10(      -1000000, -6);
		tstr.TestLog10(   -1000000000, -9);
		tstr.TestLog10(IntUtl.MinValue, -9);
		tstr.TestLog10(IntUtl.MinValue + 1, -9);
	}
	@Test public void ToStrPadBgnZeroes() {
		tstr.TestToStrPadBgnZeroes(1        , 3, "001");        // pad
		tstr.TestToStrPadBgnZeroes(123    , 3, "123");        // no pad
		tstr.TestToStrPadBgnZeroes(1234    , 3, "1234");        // val exceeds pad; confirm noop
		tstr.TestToStrPadBgnZeroes(-1    , 3, "-01");        // negative
		tstr.TestToStrPadBgnZeroes(-12    , 3, "-12");        // negative
		tstr.TestToStrPadBgnZeroes(-123    , 3, "-123");        // negative
		tstr.TestToStrPadBgnZeroes(-1234    , 3, "-1234");        // negative
	}
	@Test public void ToStrFmt() {
		tstr.TestToStrFmt(1, "1");
		tstr.TestToStrFmt(1000, "1,000");
	}
	@Test public void ByHexBry() {
		tstr.TestByHexBry("007C", 124);
	}
}
class IntUtlTstr {
	public void TestParseOr(String raw, int expd) {
		GfoTstr.Eq(expd, IntUtl.ParseOr(raw, -1));
	}
	public void TestParseOrMinValue(String raw) {
		GfoTstr.Eq(IntUtl.MinValue, IntUtl.ParseOr(raw, IntUtl.MinValue));
	}
	public void TestBetween(int val, int lhs, int rhs, boolean expd) {
		GfoTstr.Eq(expd, IntUtl.Between(val, lhs, rhs));
	}
	public void TestCountDigits(int val, int expd) {
		GfoTstr.Eq(expd, IntUtl.CountDigits(val), IntUtl.ToStr(val));
	}
	public void TestLog10(int val, int expd) {
		GfoTstr.Eq(expd, IntUtl.Log10(val));
	}
	public void TestToStrPadBgnZeroes(int val, int zeros, String expd) {GfoTstr.Eq(expd, IntUtl.ToStrPadBgnZero(val, zeros));}
	public void TestToStrFmt(int v, String expd) {GfoTstr.Eq(expd, IntUtl.ToStrFmt(v, "#,###"));}
	public void TestByHexBry(String raw, int expd) {GfoTstr.Eq(expd, IntUtl.ByHexBry(BryUtl.NewA7(raw)));}
}
