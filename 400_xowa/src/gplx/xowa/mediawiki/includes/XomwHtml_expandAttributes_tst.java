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
package gplx.xowa.mediawiki.includes;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import org.junit.*;
import gplx.xowa.mediawiki.includes.xohtml.*;
public class XomwHtml_expandAttributes_tst{
	private final XomwHtml_expandAttributes_fxt fxt = new XomwHtml_expandAttributes_fxt();
	@Test public void Basic()	{
		fxt.Test__expand_attributes(" a=\"b\"", "a", "b");
	}
	@Test public void NullVal()	{
		fxt.Test__expand_attributes("", "a", null);
	}
}
class XomwHtml_expandAttributes_fxt {
	private final BryWtr bfr = BryWtr.New();
	private final XomwHtmlTemp temp = new XomwHtmlTemp();
	public void Test__expand_attributes(String expd, String... kvs) {
		Xomw_atr_mgr atrs = new Xomw_atr_mgr();
		int kvs_len = kvs.length;
		for (int i = 0; i < kvs_len; i += 2) {
			byte[] key = BryUtl.NewA7(kvs[i]);
			byte[] val = BryUtl.NewA7(kvs[i + 1]);
			Xomw_atr_itm itm = new Xomw_atr_itm(-1, key, val);
			atrs.Add(itm);
		}
		XomwHtml.expandAttributes(bfr, temp, atrs);
		GfoTstr.Eq(expd, bfr.ToStrAndClear());
	}
}
