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
package gplx.types.basics.utls;
import gplx.frameworks.tests.GfoTstr;
import org.junit.Test;
public class MathUtlTest {
	@Test public void Abs() {
		TestAbs(1, 1);
		TestAbs(-1, 1);
		TestAbs(0, 0);
	}   void TestAbs(int val, int expd) {GfoTstr.Eq(expd, MathUtl.Abs(val));}
	@Test public void Log10() {
		TestLog10(0, IntUtl.MinValue);
		TestLog10(9, 0);
		TestLog10(10, 1);
		TestLog10(99, 1);
		TestLog10(100, 2);
	}   void TestLog10(int val, int expd) {GfoTstr.Eq(expd, MathUtl.Log10(val));}
	@Test public void Min() {
		TestMin(0, 1, 0);
		TestMin(1, 0, 0);
		TestMin(0, 0, 0);
	}   void TestMin(int val0, int val1, int expd) {GfoTstr.Eq(expd, MathUtl.Min(val0, val1));}
	@Test public void Pow() {
		TestPow(2, 0, 1);
		TestPow(2, 1, 2);
		TestPow(2, 2, 4);
	}   void TestPow(int val, int exponent, double expd) {GfoTstr.EqDouble(expd, MathUtl.Pow(val, exponent));}
	@Test public void Mult() {
		TestMult(100, .01f, 1);
	}   void TestMult(int val, float multiplier, int expd) {GfoTstr.Eq(expd, IntUtl.Mult(val, multiplier));}
	@Test public void Base2Ary() {
		TestBase2Ary(  1, 256, 1);
		TestBase2Ary(  2, 256, 2);
		TestBase2Ary(  3, 256, 1, 2);
		TestBase2Ary(  4, 256, 4);
		TestBase2Ary(  5, 256, 1, 4);
		TestBase2Ary(  6, 256, 2, 4);
		TestBase2Ary(511, 256, 1, 2, 4, 8, 16, 32, 64, 128, 256);
	}   void TestBase2Ary(int v, int max, int... expd) {GfoTstr.EqAry(expd, MathUtl.Base2Ary(v, max));}
	@Test public void Round() {
		TestRound(1.5        , 0, 2);
		TestRound(2.5        , 0, 3);
		TestRound(2.123        , 2, 2.12);
		TestRound(21.1        , -1, 20);
	}   void TestRound(double v, int places, double expd) {GfoTstr.EqDouble(expd, MathUtl.Round(v, places));}
}
