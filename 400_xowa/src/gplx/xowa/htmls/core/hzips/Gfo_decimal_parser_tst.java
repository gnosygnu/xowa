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
package gplx.xowa.htmls.core.hzips;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.BoolUtl;
import org.junit.Test;
public class Gfo_decimal_parser_tst {
	private final Gfo_decimal_parser_fxt fxt = new Gfo_decimal_parser_fxt();
	@Test public void Positive() {
		fxt.Test__parse("123"		, BoolUtl.Y,  0, 123);
		fxt.Test__parse("12.3"		, BoolUtl.Y, -1, 123);
		fxt.Test__parse("1.23"		, BoolUtl.Y, -2, 123);
		fxt.Test__parse(".123"		, BoolUtl.Y, -3, 123);
		fxt.Test__parse("0.123"		, BoolUtl.Y, -3, 123);
	}
	@Test public void Negative() {
		fxt.Test__parse("-123"		, BoolUtl.N,  0, 123);
		fxt.Test__parse("-12.3"		, BoolUtl.N, -1, 123);
		fxt.Test__parse("-1.23"		, BoolUtl.N, -2, 123);
		fxt.Test__parse("-.123"		, BoolUtl.N, -3, 123);
		fxt.Test__parse("-0.123"	, BoolUtl.N, -3, 123);
	}
}
class Gfo_decimal_parser_fxt {
	private final Gfo_decimal_parser bicoder = new Gfo_decimal_parser();
	public void Test__parse(String src_str, boolean expd_sign, int expd_exponent, long expd_number) {
		byte[] src_bry = BryUtl.NewU8(src_str);
		bicoder.Parse(src_bry, 0, src_bry.length);
		GfoTstr.Eq(expd_sign, bicoder.Sign());
		GfoTstr.Eq(expd_exponent, bicoder.Exponent());
		GfoTstr.EqLong(expd_number, bicoder.Number());
	}
}
