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
import org.junit.*; import gplx.core.tests.*;
public class Int_ary_parser_tst {
	private final    Int_ary_parser_fxt fxt = new Int_ary_parser_fxt();
	@Test  public void Many()		{fxt.Test__Parse_ary("1,2,3,4,5"		, 0, 9, Int_.Ary(1, 2, 3, 4, 5));}
	@Test  public void One()		{fxt.Test__Parse_ary("1"				, 0, 1, Int_.Ary(1));}
	@Test  public void None()		{fxt.Test__Parse_ary(""					, 0, 0, Int_.Ary());}
}
class Int_ary_parser_fxt {
	public void Test__Parse_ary(String raw, int bgn, int end, int[] expd) {
		Gftest.Eq__ary(expd, new Int_ary_parser().Parse_ary(Bry_.new_a7(raw), bgn, end, Byte_ascii.Comma), "parse_ary failed");
	}
}
