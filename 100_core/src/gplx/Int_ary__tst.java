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
import org.junit.*; import gplx.core.tests.*;
public class Int_ary__tst {
	private Int_ary__fxt fxt = new Int_ary__fxt();

	@Test  public void Parse() {
		fxt.Test__Parse("1,2,3"                        , 3, Int_ary_.Empty,   1,   2,   3);
		fxt.Test__Parse("123,321,213"                  , 3, Int_ary_.Empty, 123, 321, 213);
		fxt.Test__Parse(" 1,  2,3"                     , 3, Int_ary_.Empty,   1,   2,   3);
		fxt.Test__Parse("-1,+2,-3"                     , 3, Int_ary_.Empty,  -1,   2,  -3);
		fxt.Test__Parse(Int_.To_str(Int_.Min_value)    , 1, Int_ary_.Empty, Int_.Min_value);
		fxt.Test__Parse(Int_.To_str(Int_.Max_value)    , 1, Int_ary_.Empty, Int_.Max_value);
		fxt.Test__Parse("1,2"                          , 1, Int_ary_.Empty);
		fxt.Test__Parse("1"                            , 2, Int_ary_.Empty);
		fxt.Test__Parse("a"                            , 1, Int_ary_.Empty);
		fxt.Test__Parse("1-2,"                         , 1, Int_ary_.Empty);
	}

	@Test  public void Parse_list_or_() {
		fxt.Test__Parse_or("1", 1);
		fxt.Test__Parse_or("123", 123);
		fxt.Test__Parse_or("1,2,123", 1, 2, 123);
		fxt.Test__Parse_or("1,2,12,123", 1, 2, 12, 123);
		fxt.Test__Parse_or("1-5", 1, 2, 3, 4, 5);
		fxt.Test__Parse_or("1-1", 1);
		fxt.Test__Parse_or("1-3,7,11-13,21", 1, 2, 3, 7, 11, 12, 13, 21);

		fxt.Test__Parse_or__empty("1 2");			// NOTE: MW would gen 12; treat as invalid
		fxt.Test__Parse_or__empty("1,");			// eos
		fxt.Test__Parse_or__empty("1,,2");			// empty comma
		fxt.Test__Parse_or__empty("1-");			// eos
		fxt.Test__Parse_or__empty("3-1");			// bgn > end
		fxt.Test__Parse_or__empty("1,a,2");
		fxt.Test__Parse_or__empty("a-1,2");
		fxt.Test__Parse_or__empty("-1");			// no rng bgn
	}
}
class Int_ary__fxt {
	public void Test__Parse_or__empty(String raw) {Tfds.Eq_ary(Int_ary_.Empty, Int_ary_.Parse_or(Bry_.new_a7(raw), Int_ary_.Empty));}
	public void Test__Parse_or(String raw, int... expd) {Tfds.Eq_ary(expd, Int_ary_.Parse_or(Bry_.new_a7(raw), Int_ary_.Empty));}

	public void Test__Parse(String raw, int reqd_len, int[] or, int... expd) {Gftest.Eq__ary(expd, Int_ary_.Parse(raw, reqd_len, or), "failed to parse: {0}", raw);}
}
