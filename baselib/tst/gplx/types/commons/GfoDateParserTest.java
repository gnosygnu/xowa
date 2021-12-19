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
import gplx.types.basics.utls.BryUtl;
import gplx.frameworks.tests.GfoTstr;
import org.junit.Test;
public class GfoDateParserTest {
	private final GfoDateParserTstr tstr = new GfoDateParserTstr();
	@Test public void Parse_gplx() {
		tstr.TestParseIso8651Like("2000-01-02T03:04:05.006-05:00"     , 2000, 1, 2, 3, 4, 5, 6);
		tstr.TestParseIso8651Like("2000-01-02"                        , 2000, 1, 2, 0, 0, 0, 0);
	}
}
class GfoDateParserTstr {
	private int[] actl = new int[7]; // keep as member variable to test .Clear
	public void TestParseIso8651Like(String s, int... expd) {
		GfoDateParser parser = new GfoDateParser();
		byte[] bry = BryUtl.NewA7(s);
		parser.ParseIso8651Like(actl, bry, 0, bry.length);
		GfoTstr.EqAry(expd, actl, s);
	}
}
