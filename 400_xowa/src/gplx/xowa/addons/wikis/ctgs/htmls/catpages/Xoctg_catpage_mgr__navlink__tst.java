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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*;
import org.junit.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.fmts.*;
public class Xoctg_catpage_mgr__navlink__tst {
	@Before public void init() {fxt.Clear();} private Xoctg_catpage_mgr_fxt fxt = new Xoctg_catpage_mgr_fxt();
	@Test  public void Navlink__basic() {
		fxt.Init_itms__pages("A2", "A3", "A4");
		fxt.Init__next_sortkey_(Xoa_ctg_mgr.Tid__page, "A5");
		fxt.Test__navlink(Bool_.Y, "Category:Ctg_1", String_.Concat_lines_nl
		( ""
		, "(<a href=\"/wiki/Category:Ctg_1?pageuntil=A2%0AA2#mw-pages\" class=\"xowa_nav\" title=\"Category:Ctg_1\">previous 3</a>)"
		, "(<a href=\"/wiki/Category:Ctg_1?pagefrom=A5#mw-pages\" class=\"xowa_nav\" title=\"Category:Ctg_1\">next 3</a>)"
		));
	}
	@Test  public void Navlink__encoded() {	// escape quotes and spaces; DATE:2016-01-11
		fxt.Init_itms__pages("A\" 2", "A\" 3", "A\" 4");
		fxt.Init__next_sortkey_(Xoa_ctg_mgr.Tid__page, "A\" 5");
		fxt.Test__navlink(Bool_.Y, "Category:Ctg_1", String_.Concat_lines_nl
		( ""
		, "(<a href=\"/wiki/Category:Ctg_1?pageuntil=A%22+2%0AA%22+2#mw-pages\" class=\"xowa_nav\" title=\"Category:Ctg_1\">previous 3</a>)"
		, "(<a href=\"/wiki/Category:Ctg_1?pagefrom=A%22+5#mw-pages\" class=\"xowa_nav\" title=\"Category:Ctg_1\">next 3</a>)"
		));
	}
	@Test  public void Navlink__bos() {
		fxt.Init_itms__pages("A2", "A3", "A4");
		fxt.Init__prev_hide_y_(Xoa_ctg_mgr.Tid__page);
		fxt.Init__next_sortkey_(Xoa_ctg_mgr.Tid__page, "A5");
		fxt.Test__navlink(Bool_.Y, "Category:Ctg_1", String_.Concat_lines_nl
		( ""
		, "(previous 3)"
		, "(<a href=\"/wiki/Category:Ctg_1?pagefrom=A5#mw-pages\" class=\"xowa_nav\" title=\"Category:Ctg_1\">next 3</a>)"
		));
	}
	@Test  public void Navlink__eos() {
		fxt.Init_itms__pages("A2", "A3", "A4");
		fxt.Test__navlink(Bool_.Y, "Category:Ctg_1", String_.Concat_lines_nl
		( ""
		, "(<a href=\"/wiki/Category:Ctg_1?pageuntil=A2%0AA2#mw-pages\" class=\"xowa_nav\" title=\"Category:Ctg_1\">previous 3</a>)"
		, "(next 3)"
		));
	}
}
