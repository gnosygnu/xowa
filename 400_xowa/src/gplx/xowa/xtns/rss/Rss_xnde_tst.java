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
package gplx.xowa.xtns.rss; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Rss_xnde_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Before public void init() {fxt.Reset();}
	@Test  public void Basic() {
		fxt.Test_parse_page_all_str("<rss max='6'>http://blog.wikimedia.org/feed/</rss>", "XOWA does not support this extension: &lt;rss max='6'&gt;<a href=\"http://blog.wikimedia.org/feed/\" rel=\"nofollow\" class=\"external free\">http://blog.wikimedia.org/feed/</a>&lt;/rss&gt;");
		fxt.Test_parse_page_all_str("<rss />", "XOWA does not support this extension: &lt;rss /&gt;");
	}
}
