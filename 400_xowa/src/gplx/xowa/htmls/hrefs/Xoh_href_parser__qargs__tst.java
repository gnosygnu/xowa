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
package gplx.xowa.htmls.hrefs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import org.junit.*; import gplx.xowa.apps.urls.*; import gplx.xowa.wikis.nss.*;
public class Xoh_href_parser__qargs__tst {
	private final    Xoh_href_parser_fxt fxt = new Xoh_href_parser_fxt();
	@Test   public void Basic() {
		fxt.Exec__parse_as_url("/wiki/A?k1=v1&k2=v2");
		fxt.Test__page("A");
		fxt.Test__to_str("en.wikipedia.org/wiki/A?k1=v1&k2=v2");
	}
	@Test   public void Anch() { // PURPOSE.fix: anchor was being placed before qargs; DATE:2016-10-08
		fxt.Exec__parse_as_url("/wiki/Category:A?pagefrom=A#mw-pages");
		fxt.Test__page("Category:A");
		fxt.Test__to_str("en.wikipedia.org/wiki/Category:A?pagefrom=A#mw-pages");	// was Category:A#mw-page?pagefrom=A
	}
	// FUTURE: qargs should be unencoded by default; decoded on request
	@Test   public void Encoded() { // PURPOSE.fix: do not use decoded String; DATE:2016-10-08
		fxt.Exec__parse_as_url("/wiki/Category:A?pagefrom=A%26B#mw-pages");
		fxt.Test__page("Category:A");
		fxt.Test__qargs("?pagefrom=A&B");
	}
}
