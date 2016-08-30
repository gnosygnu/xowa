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
package gplx.xowa.apps.wms.apis.parses; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.wms.*; import gplx.xowa.apps.wms.apis.*;
import org.junit.*;
import gplx.langs.htmls.*;
public class Wm_page_loader_tst {
	@Before public void init() {fxt.Clear();} private final    Wm_page_loader_fxt fxt = new Wm_page_loader_fxt();
	@Test   public void Basic() {
		fxt.Exec__parse(Gfh_utl.Replace_apos("a<img src='xowa:/file/commons.wikimedia.org/thumb/4/a/6/9/Commons-logo.svg/12px.png'>b"))
			.Test__html(Gfh_utl.Replace_apos("a<img src='file:///mem/xowa/file/commons.wikimedia.org/thumb/4/a/6/9/Commons-logo.svg/12px.png'>b"))
			.Test__fsdb(fxt.Make__fsdb(Bool_.Y, Bool_.N, "Commons-logo.svg", 12, -1, -1))
			;
	}
}
