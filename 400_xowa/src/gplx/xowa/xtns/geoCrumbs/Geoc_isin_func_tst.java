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
package gplx.xowa.xtns.geoCrumbs;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.*;
import org.junit.*;
public class Geoc_isin_func_tst {
	@Before public void init()				{fxt.Reset();} private Geoc_isin_func_fxt fxt = new Geoc_isin_func_fxt();
	@Test public void Basic() {
		fxt.Test_parse("{{#isin:A}}", "<a href=\"/wiki/A\">A</a>");
	}
}
class Geoc_isin_func_fxt {
	private final Xop_fxt fxt = new Xop_fxt();
	public void Reset() {
		fxt.Reset();
	}
	public void Test_parse(String raw, String expd) {
		fxt.Test_parse_tmpl_str_test(raw, "{{test}}"	, "");
		GfoTstr.EqObj(expd, StringUtl.NewU8(fxt.Page().Html_data().Content_sub()));
	}
}
