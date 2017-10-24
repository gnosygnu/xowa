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
