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
package gplx.xowa.addons.wikis.ctgs.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*;
import org.junit.*; import gplx.core.tests.*;
public class Xob_catlink_mgr__tst {
	private final    Xob_catlink_mgr__fxt fxt = new Xob_catlink_mgr__fxt();
	@Test  public void Parse_timestamp() {// fix bad parsing b/c of "YYYY" instead of "yyyy"; ISSUE#:664; DATE:2020-02-05
		fxt.Test__Parse_timestamp("2016-02-01 18:34:08", 1454351648); // fails if 1451241248
	}
}
class Xob_catlink_mgr__fxt {
	public void Test__Parse_timestamp(String raw, long expd) {
		long actl = Xob_catlink_mgr.Parse_timestamp(raw);
		Gftest.Eq__long(expd, actl);

		DateAdp date = DateAdp_.unixtime_utc_seconds_(actl);
		Gftest.Eq__str(raw, date.XtoStr_fmt("yyyy-MM-dd HH:mm:ss"));
	}
}
