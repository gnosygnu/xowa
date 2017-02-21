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
package gplx.xowa.xtns.wbases.pfuncs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import org.junit.*;
public class Wdata_pf_wbreponame_tst {
	@Before public void init() {fxt.Clear();} private Wdata_pf_wbreponame_fxt fxt = new Wdata_pf_wbreponame_fxt();
	@Test   public void Basic() {
		fxt.Test_parse("{{wbreponame}}", "Wikidata");
	}
}
class Wdata_pf_wbreponame_fxt {
	public Wdata_pf_wbreponame_fxt Clear() {
		if (parser_fxt == null) {
			parser_fxt = new Xop_fxt();
		}
		return this;
	}	private Xop_fxt parser_fxt;
	public void Test_parse(String raw, String expd) {
		parser_fxt.Test_html_full_str(raw, expd);
	}
}
