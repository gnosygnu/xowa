/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx;
import org.junit.*;
public class DecimalAdp__tst {
	@Test  public void divide_() {
		tst_divide_(1, 1000, "0.001");
		tst_divide_(1, 3, "0.33333333333333");	
		tst_divide_(1, 7, "0.14285714285714");	
	}	void tst_divide_(int lhs, int rhs, String expd) {Tfds.Eq(expd, DecimalAdp_.divide_(lhs, rhs).XtoStr());}
	@Test  public void base1000_() {
		tst_base1000_(1000, "1");
		tst_base1000_(1234, "1.234");
		tst_base1000_(123, "0.123");
	}	void tst_base1000_(int val, String expd) {Tfds.Eq(expd, DecimalAdp_.base1000_(val).XtoStr());}
	@Test  public void parts_() {
		tst_parts_(1, 0, "1");
		tst_parts_(1, 2, "1.2");
		tst_parts_(1, 23, "1.23");
		tst_parts_(123, 4567, "123.4567");
	}	void tst_parts_(int num, int fracs, String expd) {Tfds.Eq(expd, DecimalAdp_.parts_(num, fracs).XtoStr());}
	@Test  public void parse_() {
		tst_parse_("1", "1");
		tst_parse_("1.2", "1.2");
		tst_parse_("0.1", "0.1");
	}	void tst_parse_(String raw, String expd) {Tfds.Eq(expd, DecimalAdp_.parse_(raw).XtoStr());}
	@Test  public void Truncate_decimal() {
		tst_Truncate_decimal("1", "1");
		tst_Truncate_decimal("1.1", "1");
		tst_Truncate_decimal("1.9", "1");
	}	void tst_Truncate_decimal(String raw, String expd) {Tfds.Eq(DecimalAdp_.parse_(expd).XtoStr(), DecimalAdp_.parse_(raw).Op_truncate_decimal().XtoStr());}
	@Test  public void Fraction1000() {
		tst_Fraction1000(1, 1000, 1);			// 0.001
		tst_Fraction1000(1, 3, 333);			// 0.33333333
		tst_Fraction1000(1234, 1000, 234);		// 1.234
		tst_Fraction1000(12345, 10000, 234);	// 1.2345
	}	void tst_Fraction1000(int lhs, int rhs, int expd) {Tfds.Eq(expd, DecimalAdp_.divide_(lhs, rhs).Fraction1000());}
	@Test  public void Lt() {
		tst_Lt(1,123, 2, true);
		tst_Lt(1,99999999, 2, true);
	}	void tst_Lt(int lhsNum, int lhsFrc, int rhs, boolean expd) {Tfds.Eq(expd, DecimalAdp_.parts_(lhsNum, lhsFrc).Comp_lt(rhs));}
	@Test  public void XtoStr_fmt() {
		tst_XtoStr_fmt(1, 2, "0.0", "0.5");
		tst_XtoStr_fmt(1, 3, "0.0", "0.3");
		tst_XtoStr_fmt(10000, 7, "0,000.000", "1,428.571");
	}	void tst_XtoStr_fmt(int l, int r, String fmt, String expd) {Tfds.Eq(expd, DecimalAdp_.divide_(l, r).XtoStr(fmt));}
}
