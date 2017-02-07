/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
