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
package gplx.xowa.addons.bldrs.volumes;
import gplx.core.tests.Gftest;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
import org.junit.Test;
public class Volume_prep_rdr_tst {
	private final Volume_prep_rdr_fxt fxt = new Volume_prep_rdr_fxt();
	@Test 	public void Parse() {
		fxt.Test__parse(StringUtl.ConcatLinesNlSkipLast("A", "", "B")
			, fxt.Make__itm("A")
			, fxt.Make__itm("B")
			);
	}
}
class Volume_prep_rdr_fxt {
	private final Volume_prep_rdr rdr = new Volume_prep_rdr();
	public Volume_prep_rdr_fxt Test__parse(String raw, Volume_prep_itm... expd) {
		Gftest.EqAry(expd, rdr.Parse(BryUtl.NewU8(raw)));
		return this;
	}
	public Volume_prep_itm Make__itm(String page_ttl) {
		Volume_prep_itm rv = new Volume_prep_itm();
		rv.Page_ttl = BryUtl.NewU8(page_ttl);
		return rv;
	}
}
