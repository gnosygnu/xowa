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
