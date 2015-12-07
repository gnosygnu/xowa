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
package gplx.xowa.htmls; import gplx.*; import gplx.xowa.*;
import org.junit.*;
public class Xohp_ctg_grp_mgr_tst {
	Xoh_ctg_mgr_fxt fxt = new Xoh_ctg_mgr_fxt();
	@Before public void init() {fxt.Clear();}
	@Test   public void Basic() {
		fxt.Init_ctgs("A", "B").Test_html(String_.Concat_lines_nl
		(	"<div id=\"catlinks\" class=\"catlinks\">"
		,	  "<div id=\"mw-normal-catlinks\" class=\"mw-normal-catlinks\">"
		,	    "Categories"
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
	public Xoh_ctg_mgr_fxt Clear() {
		app = Xoa_app_fxt.app_();
		wiki = Xoa_app_fxt.wiki_tst_(app);
		ctg_grp_mgr = new Xohp_ctg_grp_mgr();
		return this;
	}	private Xohp_ctg_grp_mgr ctg_grp_mgr; Xoae_app app; Xowe_wiki wiki; Bry_bfr tmp_bfr = Bry_bfr.new_();
	public Xoh_ctg_mgr_fxt Init_ctgs(String... v) {init_ctgs = v; return this;} private String[] init_ctgs; 
	public void Test_html(String expd) {		
		byte[][] ctgs_bry_ary = Bry_.Ary(init_ctgs);
		Xoae_page page = wiki.Parser_mgr().Ctx().Cur_page();
		page.Category_list_(ctgs_bry_ary);
		ctg_grp_mgr.Bld(tmp_bfr, page, ctgs_bry_ary.length);
		Tfds.Eq_str_lines(expd, tmp_bfr.To_str_and_clear());
	}
}
