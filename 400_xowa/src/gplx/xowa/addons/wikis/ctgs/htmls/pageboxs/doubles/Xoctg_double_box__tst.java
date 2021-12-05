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
package gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.doubles;
import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.List_adp;
import gplx.List_adp_;
import gplx.String_;
import gplx.Tfds;
import gplx.objects.primitives.BoolUtl;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xop_fxt;
import gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.Xoctg_pagebox_itm;
import org.junit.Before;
import org.junit.Test;
public class Xoctg_double_box__tst {
	@Before public void init() {fxt.Clear();} private Xoctg_double_box__fxt fxt = new Xoctg_double_box__fxt();
	@Test  public void Single() {
		fxt.Init_ctg_hidden("Category:A");
		fxt.Init_ctg_normal("Category:D");
		fxt.Test_print_hidden(String_.Concat_lines_nl
		(	"<div id=\"catlinks\" class=\"catlinks\">"
		,	  "<div id=\"mw-normal-catlinks\" class=\"mw-normal-catlinks\">"
		,	    "<a href=\"/wiki/Special:Categories\" title=\"Special:Categories\">Category</a>:"
		,	    "<ul>"
		,	      "<li>"
		,	        "<a href=\"/wiki/Category:D\" title=\"Category:D\">D</a>"
		,	      "</li>"
		,	    "</ul>"
		,	  "</div>"
		,	  "<div id=\"mw-hidden-catlinks\" class=\"mw-hidden-catlinks mw-hidden-cats-user-shown\">Hidden category:"
		,	    "<ul>"
		,	      "<li>"
		,	        "<a href=\"/wiki/Category:A\" title=\"Category:A\">A</a>"
		,	      "</li>"
		,	    "</ul>"
		,	  "</div>"
		,	"</div>"
		));
	}
	@Test  public void Plural() {
		fxt.Init_ctg_hidden("Category:A", "Category:B", "Category:C");
		fxt.Init_ctg_normal("Category:D", "Category:E", "Category:F");
		fxt.Test_print_hidden(String_.Concat_lines_nl
		(	"<div id=\"catlinks\" class=\"catlinks\">"
		,	  "<div id=\"mw-normal-catlinks\" class=\"mw-normal-catlinks\">"
		,	    "<a href=\"/wiki/Special:Categories\" title=\"Special:Categories\">Categories</a>:"
		,	    "<ul>"
		,	      "<li>"
		,	        "<a href=\"/wiki/Category:D\" title=\"Category:D\">D</a>"
		,	      "</li>"
		,	      "<li>"
		,	        "<a href=\"/wiki/Category:E\" title=\"Category:E\">E</a>"
		,	      "</li>"
		,	      "<li>"
		,	        "<a href=\"/wiki/Category:F\" title=\"Category:F\">F</a>"
		,	      "</li>"
		,	    "</ul>"
		,	  "</div>"
		,	  "<div id=\"mw-hidden-catlinks\" class=\"mw-hidden-catlinks mw-hidden-cats-user-shown\">Hidden categories:"
		,	    "<ul>"
		,	      "<li>"
		,	        "<a href=\"/wiki/Category:A\" title=\"Category:A\">A</a>"
		,	      "</li>"
		,	      "<li>"
		,	        "<a href=\"/wiki/Category:B\" title=\"Category:B\">B</a>"
		,	      "</li>"
		,	      "<li>"
		,	        "<a href=\"/wiki/Category:C\" title=\"Category:C\">C</a>"
		,	      "</li>"
		,	    "</ul>"
		,	  "</div>"
		,	"</div>"
		));
	}
}
class Xoctg_double_box__fxt {
	private Xop_fxt fxt; private Xoctg_double_box hidden_wtr;
	private final List_adp init_ctgs = List_adp_.New();
	public void Clear() {
		fxt = new Xop_fxt();
		hidden_wtr = new Xoctg_double_box();
		hidden_wtr.Init_by_wiki(fxt.Wiki());
		init_ctgs.Clear();
	}
	public void Init_ctg_normal(String... ary) {Init_ctg(BoolUtl.N, ary);}
	public void Init_ctg_hidden(String... ary) {Init_ctg(BoolUtl.Y, ary);}
	public void Init_ctg(boolean hidden, String[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Xoa_ttl ttl = fxt.Wiki().Ttl_parse(Bry_.new_u8(ary[i]));
			Xoctg_pagebox_itm itm = Xoctg_pagebox_itm.New_by_ttl(ttl);
			itm.Load_by_cat_core(hidden, 0, 0, 0);
			init_ctgs.AddMany(itm);
		}
	}
	public void Test_print_hidden(String expd) {
		Bry_bfr bfr = Bry_bfr_.New();
		Xoctg_pagebox_itm[] ary = (Xoctg_pagebox_itm[])init_ctgs.ToAryAndClear(Xoctg_pagebox_itm.class);
		hidden_wtr.Write_pagebox(bfr, ary);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_clear());
	}
}
