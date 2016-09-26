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
		fxt	.Init_itms__pages("A1", "A2", "A3").Init_grp_max(1).Init_grp__pages(1, 2)
			.Test__navlink(Bool_.Y, "Category:Ctg_1", String_.Concat_lines_nl
		( ""
		, "(<a href=\"/wiki/Category:Ctg_1?pageuntil=A2#mw-pages\" class=\"xowa_nav\" title=\"Category:Ctg_1\">previous 1</a>)"
		, "(<a href=\"/wiki/Category:Ctg_1?pagefrom=A3#mw-pages\" class=\"xowa_nav\" title=\"Category:Ctg_1\">next 1</a>)"
		));
	}
	@Test  public void Navlink__encoded() {	// escape quotes and spaces; DATE:2016-01-11
		fxt	.Init_itms__pages("A\" 1", "A\" 2", "A\" 3").Init_grp_max(1).Init_grp__pages(1, 2)
			.Test__navlink(Bool_.Y, "Category:Ctg_1", String_.Concat_lines_nl
		( ""
		, "(<a href=\"/wiki/Category:Ctg_1?pageuntil=A%22+2#mw-pages\" class=\"xowa_nav\" title=\"Category:Ctg_1\">previous 1</a>)"
		, "(<a href=\"/wiki/Category:Ctg_1?pagefrom=A%22+3#mw-pages\" class=\"xowa_nav\" title=\"Category:Ctg_1\">next 1</a>)"
		));
	}
	@Test  public void Navlink__bos() {
		fxt	.Init_itms__pages("A1", "A2", "A3").Init_grp_max(1).Init_grp__pages(0, 1)
			.Test__navlink(Bool_.Y, "Category:Ctg_1", String_.Concat_lines_nl
		( ""
		, "(previous 1)"
		, "(<a href=\"/wiki/Category:Ctg_1?pagefrom=A2#mw-pages\" class=\"xowa_nav\" title=\"Category:Ctg_1\">next 1</a>)"
		));
	}
	@Test  public void Navlink__eos() {
		fxt	.Init_itms__pages("A1", "A2", "A3").Init_grp_max(2).Init_grp__pages(2, 3)
			.Test__navlink(Bool_.Y, "Category:Ctg_1", String_.Concat_lines_nl
		( ""
		, "(<a href=\"/wiki/Category:Ctg_1?pageuntil=A3#mw-pages\" class=\"xowa_nav\" title=\"Category:Ctg_1\">previous 2</a>)"
		, "(next 2)"
		));
	}
	@Test  public void Navlink__eos__2() {	// PURPOSE.fix: do not suppress next page on penultimate page; DATE:2016-09-25
		fxt	.Init_itms__pages("A1", "A2", "A3", "A4").Init_grp_max(2).Init_grp__pages(2, 3)
			.Test__navlink(Bool_.Y, "Category:Ctg_1", String_.Concat_lines_nl
		( ""
		, "(<a href=\"/wiki/Category:Ctg_1?pageuntil=A3#mw-pages\" class=\"xowa_nav\" title=\"Category:Ctg_1\">previous 2</a>)"
		, "(<a href=\"/wiki/Category:Ctg_1?pagefrom=A4#mw-pages\" class=\"xowa_nav\" title=\"Category:Ctg_1\">next 2</a>)"
		));
	}
}
