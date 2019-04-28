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
package gplx.objects.primitives; import gplx.*; import gplx.objects.*;
import org.junit.*; import gplx.tests.*;
public class Int__tst {
	private final    Int__fxt fxt = new Int__fxt();
	@Test  public void Parse_or() {			
		fxt.Test__Parse_or("123", 123);            // basic			
		fxt.Test__Parse_or_min_value(null);        // null			
		fxt.Test__Parse_or_min_value("");          // empty			
		fxt.Test__Parse_or_min_value("1a");        // invalid number
		
		fxt.Test__Parse_or("-123", -123);          // negative
		fxt.Test__Parse_or_min_value("1-23");      // negative at invalid position
	}
	@Test  public void Between() {
		fxt.Test__Between(1, 0, 2, true);   // simple true
		fxt.Test__Between(3, 0, 2, false);  // simple false
		fxt.Test__Between(0, 0, 2, true);   // bgn true
		fxt.Test__Between(2, 0, 2, true);   // end true
	}
	@Test  public void Count_digits() {
		fxt.Test__Count_digits(   0, 1);
		fxt.Test__Count_digits(   9, 1);
		fxt.Test__Count_digits( 100, 3);
		fxt.Test__Count_digits(  -1, 2);
		fxt.Test__Count_digits(-100, 4);
	}
	@Test  public void Log10() {
		fxt.Test__Log10(             0,  0);
		fxt.Test__Log10(             1,  0);
		fxt.Test__Log10(             2,  0);
		fxt.Test__Log10(            10,  1);
		fxt.Test__Log10(            12,  1);
		fxt.Test__Log10(           100,  2);
		fxt.Test__Log10(           123,  2);
		fxt.Test__Log10(          1000,  3);
		fxt.Test__Log10(          1234,  3);
		fxt.Test__Log10(         10000,  4);
		fxt.Test__Log10(         12345,  4);
		fxt.Test__Log10(        100000,  5);
		fxt.Test__Log10(        123456,  5);
		fxt.Test__Log10(       1000000,  6);
		fxt.Test__Log10(       1234567,  6);
		fxt.Test__Log10(      10000000,  7);
		fxt.Test__Log10(      12345678,  7);
		fxt.Test__Log10(     100000000,  8);
		fxt.Test__Log10(     123456789,  8);
		fxt.Test__Log10(    1000000000,  9);
		fxt.Test__Log10(    1234567890,  9);
		fxt.Test__Log10(Int_.Max_value,  9);
		fxt.Test__Log10(            -1,  0);
		fxt.Test__Log10(           -10, -1);
		fxt.Test__Log10(          -100, -2);
		fxt.Test__Log10(      -1000000, -6);
		fxt.Test__Log10(   -1000000000, -9);
		fxt.Test__Log10(Int_.Min_value, -9);
		fxt.Test__Log10(Int_.Min_value + 1, -9);
	}
}
class Int__fxt {
	public void Test__Parse_or(String raw, int expd) {
		Gftest_fxt.Eq__int(expd, Int_.Parse_or(raw, -1));
	}
	public void Test__Parse_or_min_value(String raw) {
		Gftest_fxt.Eq__int(Int_.Min_value, Int_.Parse_or(raw, Int_.Min_value));
	}
	public void Test__Between(int val, int lhs, int rhs, boolean expd) {
		Gftest_fxt.Eq__bool(expd, Int_.Between(val, lhs, rhs));
	}
	public void Test__Count_digits(int val, int expd) {
		Gftest_fxt.Eq__int(expd, Int_.Count_digits(val), Int_.To_str(val));
	}
	public void Test__Log10(int val, int expd) {
		Gftest_fxt.Eq__int(expd, Int_.Log10(val));
	}
}
