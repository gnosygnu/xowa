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
package gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.doubles; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.*;
import org.junit.*;
import gplx.xowa.wikis.data.tbls.*;
public class Xoctg_double_box__tst {
	@Before public void init() {fxt.Clear();} private Xoctg_double_box__fxt fxt = new Xoctg_double_box__fxt();
	@Test   public void Basic() {
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
	private final    List_adp init_ctgs = List_adp_.New();
	public void Clear() {
		fxt = new Xop_fxt();
		hidden_wtr = new Xoctg_double_box();
		hidden_wtr.Init_by_wiki(fxt.Wiki());
		init_ctgs.Clear();
	}
	public void Init_ctg_normal(String... ary) {Init_ctg(Bool_.N, ary);}
	public void Init_ctg_hidden(String... ary) {Init_ctg(Bool_.Y, ary);}
	public void Init_ctg(boolean hidden, String[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Xoa_ttl ttl = fxt.Wiki().Ttl_parse(Bry_.new_u8(ary[i]));
			Xoctg_pagebox_itm itm = Xoctg_pagebox_itm.New_by_ttl(ttl);
			itm.Load_by_cat_core(hidden, 0, 0, 0);
			init_ctgs.Add_many(itm);
		}
	}
	public void Test_print_hidden(String expd) {
		Bry_bfr bfr = Bry_bfr_.New();
		Xoctg_pagebox_itm[] ary = (Xoctg_pagebox_itm[])init_ctgs.To_ary_and_clear(Xoctg_pagebox_itm.class);
		hidden_wtr.Write_pagebox(bfr, ary);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_clear());
	}
}
