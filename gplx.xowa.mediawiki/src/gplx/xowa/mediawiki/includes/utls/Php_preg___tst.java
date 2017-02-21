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
package gplx.xowa.mediawiki.includes.utls; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import org.junit.*; import gplx.core.tests.*;
public class Php_preg___tst {
	private final    Php_preg___fxt fxt = new Php_preg___fxt();
	@Test  public void Basic()         {fxt.Test__split("a''b''c"          , "''", Bool_.Y, "a", "''", "b", "''", "c");}
	@Test  public void Extend()        {fxt.Test__split("a'''b'''c"        , "''", Bool_.Y, "a", "'''", "b", "'''", "c");}
	@Test  public void Eos()           {fxt.Test__split("a''"              , "''", Bool_.Y, "a", "''");}
}
class Php_preg___fxt {
	private final    gplx.core.primitives.Int_list rv = new gplx.core.primitives.Int_list();
	public void Test__split(String src, String dlm, boolean extend, String... expd) {Test__split(src, 0, String_.Len(src), dlm, extend, expd);}
	public void Test__split(String src, int src_bgn, int src_end, String dlm, boolean extend, String... expd) {
		byte[][] actl = Php_preg_.Split(rv, Bry_.new_u8(src), src_bgn, src_end, Bry_.new_u8(dlm), extend);
		Gftest.Eq__ary(expd, String_.Ary(actl), "find_failed");
	}
}
