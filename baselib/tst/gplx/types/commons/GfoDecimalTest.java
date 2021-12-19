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
package gplx.types.commons;
import gplx.frameworks.tests.GfoTstr;
import org.junit.Test;
public class GfoDecimalTest {
	private final GfoDecimalTstr tstr = new GfoDecimalTstr();
	@Test public void Divide() {
		tstr.TestDivide(1, 1000, "0.001");
		tstr.TestDivide(1, 3, "0.33333333333333");
		tstr.TestDivide(1, 7, "0.14285714285714");
	}
	@Test public void Base1000() {
		tstr.TestBase1000(1000, "1");
		tstr.TestBase1000(1234, "1.234");
		tstr.TestBase1000(123, "0.123");
	}
	@Test public void Parts() {
		tstr.TestParts(1, 0, "1");
		tstr.TestParts(1, 2, "1.2");
		tstr.TestParts(1, 23, "1.23");
		tstr.TestParts(123, 4567, "123.4567");
	}
	@Test public void Parse() {
		tstr.TestParse("1", "1");
		tstr.TestParse("1.2", "1.2");
		tstr.TestParse("0.1", "0.1");
		tstr.TestParse("1.2E1", "12");
		tstr.TestParse("1.2e1", "12"); // 2020-08-27|ISSUE#:565|Parse 'e' as 'E'; PAGE:en.w:Huntington_Plaza
	}
	@Test public void ParseDot() {
		tstr.TestParse(".", "0"); // 2021-02-13|ISSUE#:838|Parse '.' as '0.'; PAGE:en.w:2019_FIVB_Volleyball_Women%27s_Challenger_Cup#Pool_A
	}
	@Test public void TruncateDecimal() {
		tstr.TestTruncateDecimal("1", "1");
		tstr.TestTruncateDecimal("1.1", "1");
		tstr.TestTruncateDecimal("1.9", "1");
	}
	@Test public void Fraction1000() {
		tstr.TestFrac1000(1, 1000, 1);            // 0.001
		tstr.TestFrac1000(1, 3, 333);            // 0.33333333
		tstr.TestFrac1000(1234, 1000, 234);        // 1.234
		tstr.TestFrac1000(12345, 10000, 234);    // 1.2345
	}
	@Test public void Lt() {
		tstr.TestCompLt(1,123, 2, true);
		tstr.TestCompLt(1,99999999, 2, true);
	}
	@Test public void ToStrFmt() {
		tstr.TestToStrFmt(1, 2, "0.0", "0.5");
		tstr.TestToStrFmt(1, 3, "0.0", "0.3");
		tstr.TestToStrFmt(10000, 7, "0,000.000", "1,428.571");
		tstr.TestToStrFmt(1, 2, "00.00", "00.50");
	}
	@Test public void Round() {
		tstr.TestRound("123.456",  3, "123.456");
		tstr.TestRound("123.456",  2, "123.46");
		tstr.TestRound("123.456",  1, "123.5");
		tstr.TestRound("123.456",  0, "123");
		tstr.TestRound("123.456", -1, "120");
		tstr.TestRound("123.456", -2, "100");
		tstr.TestRound("123.456", -3, "0");

		tstr.TestRound("6", -1, "10");
		tstr.TestRound("5", -1, "10");
		tstr.TestRound("6", -2, "0");
	}
}
class GfoDecimalTstr {
	public void TestDivide(int lhs, int rhs, String expd) {GfoTstr.Eq(expd, GfoDecimalUtl.NewByDivide(lhs, rhs).ToStr());}
	public void TestBase1000(int val, String expd) {GfoTstr.Eq(expd, GfoDecimalUtl.NewByBase1000(val).ToStr());}
	public void TestParts(int num, int fracs, String expd) {GfoTstr.Eq(expd, GfoDecimalUtl.NewByParts(num, fracs).ToStr());}
	public void TestParse(String raw, String expd) {GfoTstr.Eq(expd, GfoDecimalUtl.Parse(raw).ToStr());}
	public void TestTruncateDecimal(String raw, String expd) {GfoTstr.Eq(GfoDecimalUtl.Parse(expd).ToStr(), GfoDecimalUtl.Parse(raw).Truncate().ToStr());}
	public void TestFrac1000(int lhs, int rhs, int expd) {GfoTstr.Eq(expd, GfoDecimalUtl.NewByDivide(lhs, rhs).Frac1000());}
	public void TestCompLt(int lhsNum, int lhsFrc, int rhs, boolean expd) {GfoTstr.Eq(expd, GfoDecimalUtl.NewByParts(lhsNum, lhsFrc).CompLt(rhs));}
	public void TestToStrFmt(int l, int r, String fmt, String expd) {GfoTstr.Eq(expd, GfoDecimalUtl.NewByDivide(l, r).ToStr(fmt));}
	public void TestRound(String raw, int places, String expd) {GfoTstr.Eq(expd, GfoDecimalUtl.Parse(raw).Round(places).ToStr(), "round");}
}
