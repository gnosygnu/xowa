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
public class Decimal_adp__tst {
	private final Decimal_adp__fxt fxt = new Decimal_adp__fxt();
	@Test  public void divide_() {
		fxt.Test_divide(1, 1000, "0.001");
		fxt.Test_divide(1, 3, "0.33333333333333");	
		fxt.Test_divide(1, 7, "0.14285714285714");	
	}
	@Test  public void base1000_() {
		fxt.Test_base_1000(1000, "1");
		fxt.Test_base_1000(1234, "1.234");
		fxt.Test_base_1000(123, "0.123");
	}
	@Test  public void parts_() {
		fxt.Test_parts(1, 0, "1");
		fxt.Test_parts(1, 2, "1.2");
		fxt.Test_parts(1, 23, "1.23");
		fxt.Test_parts(123, 4567, "123.4567");
	}
	@Test  public void parse() {
		fxt.Test_parse("1", "1");
		fxt.Test_parse("1.2", "1.2");
		fxt.Test_parse("0.1", "0.1");
	}
	@Test  public void Truncate_decimal() {
		fxt.Test_truncate_decimal("1", "1");
		fxt.Test_truncate_decimal("1.1", "1");
		fxt.Test_truncate_decimal("1.9", "1");
	}
	@Test  public void Fraction1000() {
		fxt.Test_frac_1000(1, 1000, 1);			// 0.001
		fxt.Test_frac_1000(1, 3, 333);			// 0.33333333
		fxt.Test_frac_1000(1234, 1000, 234);		// 1.234
		fxt.Test_frac_1000(12345, 10000, 234);	// 1.2345
	}
	@Test  public void Lt() {
		fxt.Test_comp_lt(1,123, 2, true);
		fxt.Test_comp_lt(1,99999999, 2, true);
	}
	@Test  public void To_str_fmt() {
		fxt.Test_to_str_fmt(1, 2, "0.0", "0.5");
		fxt.Test_to_str_fmt(1, 3, "0.0", "0.3");
		fxt.Test_to_str_fmt(10000, 7, "0,000.000", "1,428.571");
		fxt.Test_to_str_fmt(1, 2, "00.00", "00.50");
	}
	@Test  public void Round() {
		fxt.Test_round("123.456",  3, "123.456");
		fxt.Test_round("123.456",  2, "123.46");
		fxt.Test_round("123.456",  1, "123.5");
		fxt.Test_round("123.456",  0, "123");
		fxt.Test_round("123.456", -1, "120");
		fxt.Test_round("123.456", -2, "100");
		fxt.Test_round("123.456", -3, "0");

		fxt.Test_round("6", -1, "10");
		fxt.Test_round("5", -1, "10");
		fxt.Test_round("6", -2, "0");
	}
}
class Decimal_adp__fxt {
	public void Test_divide(int lhs, int rhs, String expd) {Tfds.Eq(expd, Decimal_adp_.divide_(lhs, rhs).To_str());}
	public void Test_base_1000(int val, String expd) {Tfds.Eq(expd, Decimal_adp_.base1000_(val).To_str());}
	public void Test_parts(int num, int fracs, String expd) {Tfds.Eq(expd, Decimal_adp_.parts_(num, fracs).To_str());}
	public void Test_parse(String raw, String expd) {Tfds.Eq(expd, Decimal_adp_.parse(raw).To_str());}
	public void Test_truncate_decimal(String raw, String expd) {Tfds.Eq(Decimal_adp_.parse(expd).To_str(), Decimal_adp_.parse(raw).Truncate().To_str());}
	public void Test_frac_1000(int lhs, int rhs, int expd) {Tfds.Eq(expd, Decimal_adp_.divide_(lhs, rhs).Frac_1000());}
	public void Test_comp_lt(int lhsNum, int lhsFrc, int rhs, boolean expd) {Tfds.Eq(expd, Decimal_adp_.parts_(lhsNum, lhsFrc).Comp_lt(rhs));}
	public void Test_to_str_fmt(int l, int r, String fmt, String expd) {Tfds.Eq(expd, Decimal_adp_.divide_(l, r).To_str(fmt));}
	public void Test_round(String raw, int places, String expd) {Tfds.Eq_str(expd, Decimal_adp_.parse(raw).Round(places).To_str(), "round");}
}
