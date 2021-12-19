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
package gplx.types.custom.brys.fmts.itms;
import gplx.types.basics.utls.BryUtl;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.StringUtl;
import org.junit.Test;
public class BryFmtParserUtlTest {
	private final BryFmtParserUtlTstr tstr = new BryFmtParserUtlTstr();
	@Test public void None()       {tstr.Test("a");}
	@Test public void One()        {tstr.Test("~{a}"                , "a");}
	@Test public void Many()       {tstr.Test("~{a}b~{c}d~{e}"      , "a", "c", "e");}
	@Test public void Dupe()       {tstr.Test("~{a}b~{a}"           , "a");}
	@Test public void Bug__space() {tstr.Test("~{a}~{b} ~{c}"       , "a", "b", "c");} // DATE:2016-08-02
}
class BryFmtParserUtlTstr {
	public void Test(String fmt, String... expd) {
		GfoTstr.EqLines(expd, StringUtl.Ary(BryFmtParserUtl.ParseKeys(BryUtl.NewU8(fmt))));
	}
}
