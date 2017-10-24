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
package gplx.xowa.addons.wikis.pages.syncs.core.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.pages.*; import gplx.xowa.addons.wikis.pages.syncs.*; import gplx.xowa.addons.wikis.pages.syncs.core.*;
import org.junit.*;
import gplx.langs.htmls.*;
public class Xosync_hdoc_parser__misc__tst {
	@Before public void init() {fxt.Clear();} private final    Xosync_hdoc_parser__fxt fxt = new Xosync_hdoc_parser__fxt();
	@Test   public void Math() {
		fxt.Exec__parse(Gfh_utl.Replace_apos("<img src='https://wikimedia.org/api/rest_v1/media/math/render/svg/596f8baf206a81478afd4194b44138715dc1a05c' class='mwe-math-fallback-image-inline' aria-hidden='true' style='vertical-align: -2.005ex; width:16.822ex; height:6.176ex;' alt='R_{H}=a\\left({\\frac {m}{3M}}\\right)^{\\frac {1}{3}}'>"))
			.Test__html(Gfh_utl.Replace_apos("<img src='xowa:/math/596f8baf206a81478afd4194b44138715dc1a05c.svg' class='mwe-math-fallback-image-inline' aria-hidden='true' style='vertical-align: -2.005ex; width:16.822ex; height:6.176ex;' alt='R_{H}=a\\left({\\frac {m}{3M}}\\right)^{\\frac {1}{3}}'>"))
			.Test__fsdb(fxt.Make__fsdb(Bool_.Y, Bool_.Y, "596f8baf206a81478afd4194b44138715dc1a05c.svg", -1, -1, -1));
	}
}
