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
package gplx.core.primitives; import gplx.*; import gplx.core.*;
import org.junit.*;
public class Int_ary__tst {
	private Int_ary__fxt fxt = new Int_ary__fxt();
	@Test  public void Parse_list_or_() {
		fxt.Test_Parse_list_or("1", 1);
		fxt.Test_Parse_list_or("123", 123);
		fxt.Test_Parse_list_or("1,2,123", 1, 2, 123);
		fxt.Test_Parse_list_or("1,2,12,123", 1, 2, 12, 123);
		fxt.Test_Parse_list_or("1-5", 1, 2, 3, 4, 5);
		fxt.Test_Parse_list_or("1-1", 1);
		fxt.Test_Parse_list_or("1-3,7,11-13,21", 1, 2, 3, 7, 11, 12, 13, 21);

		fxt.Test_Parse_list_empty("1 2");			// NOTE: MW would gen 12; treat as invalid
		fxt.Test_Parse_list_empty("1,");			// eos
		fxt.Test_Parse_list_empty("1,,2");			// empty comma
		fxt.Test_Parse_list_empty("1-");			// eos
		fxt.Test_Parse_list_empty("3-1");			// bgn > end
		fxt.Test_Parse_list_empty("1,a,2");
		fxt.Test_Parse_list_empty("a-1,2");
		fxt.Test_Parse_list_empty("-1");			// no rng bgn
	}
}
class Int_ary__fxt {
	public void Test_Parse_list_empty(String raw) {Tfds.Eq_ary(Int_.Ary_empty, Int_ary_.Parse_list_or(Bry_.new_a7(raw), Int_.Ary_empty));}
	public void Test_Parse_list_or(String raw, int... expd) {Tfds.Eq_ary(expd, Int_ary_.Parse_list_or(Bry_.new_a7(raw), Int_.Ary_empty));}
}
