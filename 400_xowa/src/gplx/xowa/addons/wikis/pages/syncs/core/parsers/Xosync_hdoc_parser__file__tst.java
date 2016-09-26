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
package gplx.xowa.addons.wikis.pages.syncs.core.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.pages.*; import gplx.xowa.addons.wikis.pages.syncs.*; import gplx.xowa.addons.wikis.pages.syncs.core.*;
import org.junit.*;
import gplx.langs.htmls.*;
public class Xosync_hdoc_parser__file__tst {
	@Before public void init() {fxt.Clear();} private final    Xosync_hdoc_parser__fxt fxt = new Xosync_hdoc_parser__fxt();
	@Test   public void Commons__thumb() {
		fxt.Exec__parse(Gfh_utl.Replace_apos("<img src='//upload.wikimedia.org/wikipedia/commons/thumb/7/70/A.png/320px-A.png'>"))
			.Test__html(Gfh_utl.Replace_apos("<img src='xowa:/file/commons.wikimedia.org/thumb/7/0/1/c/A.png/320px.png'>"))
			.Test__fsdb(fxt.Make__fsdb(Bool_.Y, Bool_.N, "A.png", 320, -1, -1));
	}
	@Test   public void Url_encoded() {
		fxt.Exec__parse(Gfh_utl.Replace_apos("<img src='//upload.wikimedia.org/wikipedia/commons/thumb/9/91/A%2CB.png/320px-A%2CB.png'>"))
			.Test__html(Gfh_utl.Replace_apos("<img src='xowa:/file/commons.wikimedia.org/thumb/9/1/0/8/A%2CB.png/320px.png'>"))
			.Test__fsdb(fxt.Make__fsdb(Bool_.Y, Bool_.N, "A,B.png", 320, -1, -1));
	}
	@Test   public void Local__orig() {
		fxt.Exec__parse(Gfh_utl.Replace_apos("<img src='//upload.wikimedia.org/wikipedia/en/7/70/A.png'>"))
			.Test__html(Gfh_utl.Replace_apos("<img src='xowa:/file/en.wikipedia.org/orig/7/0/1/c/A.png'>"))
			.Test__fsdb(fxt.Make__fsdb(Bool_.N, Bool_.Y, "A.png", -1, -1, -1));
	}
	@Test   public void Svg() {
		fxt.Exec__parse(Gfh_utl.Replace_apos("<img src='//upload.wikimedia.org/wikipedia/commons/thumb/7/75/A.svg/12px-A.svg.png'>"))
			.Test__html(Gfh_utl.Replace_apos("<img src='xowa:/file/commons.wikimedia.org/thumb/7/5/9/a/A.svg/12px.png'>"))
			.Test__fsdb(fxt.Make__fsdb(Bool_.Y, Bool_.N, "A.svg", 12, -1, -1));
	}
	@Test   public void Ogg() {
		fxt.Exec__parse(Gfh_utl.Replace_apos("<img src='//upload.wikimedia.org/wikipedia/commons/thumb/4/42/A.ogg/320px--A.ogg.jpg'>"))
			.Test__html(Gfh_utl.Replace_apos("<img src='xowa:/file/commons.wikimedia.org/thumb/4/2/7/e/A.ogg/320px.jpg'>"))
			.Test__fsdb(fxt.Make__fsdb(Bool_.Y, Bool_.N, "A.ogg", "ogv", 320, -1, -1));
	}
	@Test   public void Ogg__time() {
		fxt.Exec__parse(Gfh_utl.Replace_apos("<img src='//upload.wikimedia.org/wikipedia/commons/thumb/4/42/A.ogg/320px-seek%3D1.2-A.ogg.jpg'>"))
			.Test__html(Gfh_utl.Replace_apos("<img src='xowa:/file/commons.wikimedia.org/thumb/4/2/7/e/A.ogg/320px-1.2.jpg'>"))
			.Test__fsdb(fxt.Make__fsdb(Bool_.Y, Bool_.N, "A.ogg", "ogv", 320, 1.2, -1));
	}
	@Test   public void Pdf__page() {
		fxt.Exec__parse(Gfh_utl.Replace_apos("<img src='//upload.wikimedia.org/wikipedia/commons/thumb/7/76/A.djvu/page1-320px-A.djvu.jpg'>"))
			.Test__html(Gfh_utl.Replace_apos("<img src='xowa:/file/commons.wikimedia.org/thumb/7/6/9/a/A.djvu/320px-1.jpg'>"))
			.Test__fsdb(fxt.Make__fsdb(Bool_.Y, Bool_.N, "A.djvu", 320, -1, 1));
	}
}
