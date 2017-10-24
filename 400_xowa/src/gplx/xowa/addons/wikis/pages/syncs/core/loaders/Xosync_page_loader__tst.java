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
package gplx.xowa.addons.wikis.pages.syncs.core.loaders; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.pages.*; import gplx.xowa.addons.wikis.pages.syncs.*; import gplx.xowa.addons.wikis.pages.syncs.core.*;
import org.junit.*;
import gplx.langs.htmls.*;
public class Xosync_page_loader__tst {
	@Before public void init() {fxt.Clear();} private final    Xosync_page_loader__fxt fxt = new Xosync_page_loader__fxt();
	@Test   public void File() {
		fxt.Exec__parse(Gfh_utl.Replace_apos("a<img src='xowa:/file/commons.wikimedia.org/thumb/4/a/6/9/Commons-logo.svg/12px.png' width='12' height='20'>b"))
			.Test__html(Gfh_utl.Replace_apos("a<img id='xoimg_0' src='file:///mem/xowa/file/commons.wikimedia.org/thumb/4/a/6/9/Commons-logo.svg/12px.png' width='12' height='20'>b"))
			.Test__fsdb(fxt.Make__fsdb(Bool_.Y, Bool_.N, "Commons-logo.svg", "svg", 12, -1, -1))
			;
	}
	@Test   public void Math() {
		fxt.Exec__parse(Gfh_utl.Replace_apos("a<img src='xowa:/math/596f8baf206a81478afd4194b44138715dc1a05c' width='12' height='20'>b"))
			.Test__html(Gfh_utl.Replace_apos("a<img id='xoimg_0' src='file:///mem/xowa/file/math/596f8baf206a81478afd4194b44138715dc1a05c' width='12' height='20'>b"))
			.Test__fsdb(fxt.Make__fsdb(Bool_.Y, Bool_.Y, "596f8baf206a81478afd4194b44138715dc1a05c", "svg", -1, -1, -1))
			;
	}
	@Test   public void Ogg() {
		fxt.Exec__parse(Gfh_utl.Replace_apos("a<img src='xowa:/file/commons.wikimedia.org/thumb/4/2/7/e/A.ogg/320px.jpg'>b"))
			.Test__html(Gfh_utl.Replace_apos("a<img id='xoimg_0' src='file:///mem/xowa/file/commons.wikimedia.org/thumb/4/2/7/e/A.ogg/320px.jpg'>b"))
			.Test__fsdb(fxt.Make__fsdb(Bool_.Y, Bool_.N, "A.ogg", "ogv", 320, -1, -1))
			;
	}
}
