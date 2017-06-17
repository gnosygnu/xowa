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
package gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.singles; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.*;
import org.junit.*;
import gplx.xowa.htmls.core.htmls.*;
public class Xoctg_single_box__tst {		
	@Before public void init() {fxt.Clear();} private final    Xoh_ctg_mgr_fxt fxt = new Xoh_ctg_mgr_fxt();
	@Test   public void Basic() {
		fxt.Init__ctgs("Category:A", "Category:B").Test_html(String_.Concat_lines_nl
		(	"<div id=\"catlinks\" class=\"catlinks\">"
		,	  "<div id=\"mw-normal-catlinks\" class=\"mw-normal-catlinks\">"
		,	    "Categories:"
		,	    "<ul>"
		,	      "<li>"
		,	        "<a href=\"/wiki/Category:A\" class=\"internal\" title=\"A\">A</a>"
		,	      "</li>"
		,	      "<li>"
		,	        "<a href=\"/wiki/Category:B\" class=\"internal\" title=\"B\">B</a>"
		,	      "</li>"
		,	    "</ul>"
		,	  "</div>"
		,	"</div>"
		));
	}
}
class Xoh_ctg_mgr_fxt {
	private Xoctg_single_box ctg_grp_mgr; Xoae_app app; Xowe_wiki wiki; Bry_bfr tmp_bfr = Bry_bfr_.New();
	private Xoae_page page;
	public void Clear() {
		app = Xoa_app_fxt.Make__app__edit();
		wiki = Xoa_app_fxt.Make__wiki__edit(app);
		page = wiki.Parser_mgr().Ctx().Page();
		ctg_grp_mgr = new Xoctg_single_box();
		ctg_grp_mgr.Init_by_wiki(wiki);
	}
	public Xoh_ctg_mgr_fxt Init__ctgs(String... ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			page.Wtxt().Ctgs__add(wiki.Ttl_parse(Bry_.new_u8(ary[i])));
		}
		return this;
	}
	public void Test_html(String expd) {		
		ctg_grp_mgr.Write_pagebox(tmp_bfr, Xoctg_pagebox_itm.New_ary(page));
		Tfds.Eq_str_lines(expd, tmp_bfr.To_str_and_clear());
	}
}
