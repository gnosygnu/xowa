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
package gplx.xowa.ctgs; import gplx.*; import gplx.xowa.*;
import org.junit.*;
import gplx.xowa.dbs.tbls.*;
public class Xoctg_pagelist_wtr_tst {
	@Before public void init() {fxt.Clear();} private Xoctg_pagelist_mgr_fxt fxt = new Xoctg_pagelist_mgr_fxt();
	@Test   public void Basic() {
		fxt.Init_ctg_hidden("A", "B", "C");
		fxt.Init_ctg_normal("D", "E", "F");
		fxt.Test_print_hidden(String_.Concat_lines_nl
		(	"<div id=\"catlinks\" class=\"catlinks\">"
		,	"  <div id=\"mw-normal-catlinks\" class=\"mw-normal-catlinks\">"
		,	"    <a href=\"/wiki/Special:Categories\" title=\"Special:Categories\">Categories</a>:"
		,	"    <ul>"
		,	"      <li>"
		,	"        <a href=\"/wiki/Category:D\" title=\"Category:D\">D</a>"
		,	"      </li>"
		,	"      <li>"
		,	"        <a href=\"/wiki/Category:E\" title=\"Category:E\">E</a>"
		,	"      </li>"
		,	"      <li>"
		,	"        <a href=\"/wiki/Category:F\" title=\"Category:F\">F</a>"
		,	"      </li>"
		,	"    </ul>"
		,	"  </div>"
		,	"  <div id=\"mw-hidden-catlinks\" class=\"mw-hidden-catlinks mw-hidden-cats-user-shown\">Hidden categories:"
		,	"    <ul>"
		,	"      <li>"
		,	"        <a href=\"/wiki/Category:A\" title=\"Category:A\">A</a>"
		,	"      </li>"
		,	"      <li>"
		,	"        <a href=\"/wiki/Category:B\" title=\"Category:B\">B</a>"
		,	"      </li>"
		,	"      <li>"
		,	"        <a href=\"/wiki/Category:C\" title=\"Category:C\">C</a>"
		,	"      </li>"
		,	"    </ul>"
		,	"  </div>"
		,	"</div>"
		));
	}
}
class Xoctg_pagelist_mgr_fxt {
	public void Clear() {
		fxt = new Xop_fxt();
		app = fxt.App();
		hidden_wtr = new Xoctg_pagelist_wtr();
		hidden_wtr.Init_by_app(app);
		init_ctgs.Clear();
	}	private Xop_fxt fxt; private Xoa_app app; private Xoctg_pagelist_wtr hidden_wtr;
	public void Init_ctg_normal(String... ary) {Init_ctg(Bool_.N, ary);}
	public void Init_ctg_hidden(String... ary) {Init_ctg(Bool_.Y, ary);}
	public void Init_ctg(boolean hidden, String[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			String ttl = ary[i];
			Xodb_page page = new Xodb_page();
			Xodb_category_itm ctg_xtn = Xodb_category_itm.load_(0, 0, hidden, 0, 0, 0);
			page.Xtn_(ctg_xtn);
			page.Ttl_wo_ns_(Bry_.new_ascii_(ttl));
			init_ctgs.AddMany(page);
		}
	}	private ListAdp init_ctgs = ListAdp_.new_();
	public void Test_print_hidden(String expd) {
		Bry_bfr bfr = Bry_bfr.new_();
		Xodb_page[] page_ary = (Xodb_page[])init_ctgs.Xto_ary_and_clear(Xodb_page.class);
		hidden_wtr.Print_hidden(bfr, fxt.Wiki(), page_ary);
		Tfds.Eq_str_lines(expd, bfr.Xto_str_and_clear());
//		Tfds.Write(bfr.Xto_bry_and_clear());
	}
}
