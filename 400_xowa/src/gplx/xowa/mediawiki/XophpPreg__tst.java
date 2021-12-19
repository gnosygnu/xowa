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
package gplx.xowa.mediawiki;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.lists.IntList;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.utls.BoolUtl;
import org.junit.Test;
public class XophpPreg__tst {
	private final XophpPreg__fxt fxt = new XophpPreg__fxt();
	@Test public void Basic()         {fxt.Test_split("a''b''c"          , "''", BoolUtl.Y, "a", "''", "b", "''", "c");}
	@Test public void Extend()        {fxt.Test_split("a'''b'''c"        , "''", BoolUtl.Y, "a", "'''", "b", "'''", "c");}
	@Test public void Eos()           {fxt.Test_split("a''"              , "''", BoolUtl.Y, "a", "''");}
}
class XophpPreg__fxt {
	private final IntList rv = new IntList(16);
	public void Test_split(String src, String dlm, boolean extend, String... expd) {Test_split(src, 0, StringUtl.Len(src), dlm, extend, expd);}
	public void Test_split(String src, int src_bgn, int src_end, String dlm, boolean extend, String... expd) {
		byte[][] actl = XophpPreg_.split(rv, BryUtl.NewU8(src), src_bgn, src_end, BryUtl.NewU8(dlm), extend);
		GfoTstr.EqLines(expd, StringUtl.Ary(actl), "find_failed");
	}
}
