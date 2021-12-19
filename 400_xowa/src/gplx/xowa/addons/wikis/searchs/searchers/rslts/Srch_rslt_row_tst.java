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
package gplx.xowa.addons.wikis.searchs.searchers.rslts;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.xowa.*;
import org.junit.*;
public class Srch_rslt_row_tst {
	private final Srch_rslt_row_fxt fxt = new Srch_rslt_row_fxt();
	@Test public void To_display() {
		Srch_rslt_row row_straight = fxt.Make__row_straight("Ab", "<b>A</b>b");
		Srch_rslt_row row_redirect = fxt.Make__row_redirect("Ab", "<b>A</b>b", "C");

		int type = Srch_rslt_row.Display_type__suggest;
		fxt.Test__To_display(row_straight, type, "<b>A</b>b");
		fxt.Test__To_display(row_redirect, type, "<b>A</b>b → C");

		type = Srch_rslt_row.Display_type__url_bar;
		fxt.Test__To_display(row_straight, type, "Ab");
		fxt.Test__To_display(row_redirect, type, "Ab  ->  C");

		type = Srch_rslt_row.Display_type__special_page;
		fxt.Test__To_display(row_straight, type, "Ab");
		fxt.Test__To_display(row_redirect, type, "Ab → C");
	}
}
class Srch_rslt_row_fxt {
	private final Xow_wiki wiki;
	public Srch_rslt_row_fxt() {
		Xoa_app app = Xoa_app_fxt.Make__app__view();
		this.wiki = Xoa_app_fxt.Make__wiki__view(app);
	}
	public void Test__To_display(Srch_rslt_row row, int type, String expd) {
		GfoTstr.Eq(BryUtl.NewU8(expd), row.To_display(type));
	}
	public Srch_rslt_row Make__row_straight(String ttl, String highlight) {
		Srch_rslt_row rv = Srch_rslt_row.New(wiki.Domain_bry(), wiki.Ttl_parse(BryUtl.NewU8(ttl)), 123, 100, 999, Srch_rslt_row.Page_redirect_id_null);
		rv.Page_ttl_highlight = BryUtl.NewU8(highlight);
		return rv;
	}
	public Srch_rslt_row Make__row_redirect(String ttl, String highlight, String redirect) {
		Srch_rslt_row rv = Make__row_straight(ttl, highlight);
		rv.Page_redirect_id = 321;
		rv.Page_redirect_ttl = BryUtl.NewU8(redirect);
		return rv;
	}
}
