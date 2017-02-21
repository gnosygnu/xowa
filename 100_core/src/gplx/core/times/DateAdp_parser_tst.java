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
package gplx.core.times; import gplx.*; import gplx.core.*;
import org.junit.*;
public class DateAdp_parser_tst {
	@Before public void init() {} DateAdp_parser_fxt fxt = new DateAdp_parser_fxt();
	@Test  public void Parse_gplx() {
		fxt.Test_Parse_iso8651_like("2000-01-02T03:04:05.006-05:00"		, 2000, 1, 2, 3, 4, 5, 6);
		fxt.Test_Parse_iso8651_like("2000-01-02"						, 2000, 1, 2, 0, 0, 0, 0);
	}
}
class DateAdp_parser_fxt {
	DateAdp_parser parser = DateAdp_parser.new_(); int[] actl = new int[7];
	public void Test_Parse_iso8651_like(String s, int... expd) {
		byte[] bry = Bry_.new_a7(s);
		parser.Parse_iso8651_like(actl, bry, 0, bry.length);
		Tfds.Eq_ary(expd, actl, s);
	}
}
