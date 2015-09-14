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
package gplx.xowa.parsers.xndes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_xnde_wkr__li_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Inside_tblx() {	// PURPOSE: auto-close <li> (EX: "<li>a<li>") was causing 3rd <li> to close incorrectly
		fxt.Test_parse_page_wiki_str
			(	"<table><tr><td><ul><li>a</li><li>b</li><li>c</li></ul></td></tr></table>"
			,	String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td><ul>"
			,	"<li>a</li>"
			,	"<li>b</li>"
			,	"<li>c</li></ul>"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			));
	}
	@Test   public void Li_nested_inside_ul() {	// PURPOSE: nested li in ul should not be escaped; DATE:2013-12-04
		fxt.Test_parse_page_wiki_str
		(	"<ul><li>a<ul><li>b</li></ul></li></ul>"
		,	String_.Concat_lines_nl_skip_last
		(	"<ul>"
		,	"<li>a<ul>"
		,	"<li>b</li></ul></li></ul>"	// note that <li><li>b becomes <li>&lt;li>b but <li><ul><li>b should stay the same
		));
	}
	@Test  public void Empty_ignored() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"<ul>"
			,	"<li>a"
			,	"</li><li>"
			,	"</li><li>b"
			,	"</li>"
			,	"</ul>"
			), String_.Concat_lines_nl_skip_last
			(	"<ul>"
			,	"<li>a"
			,	"</li>"
			,	"<li>b"
			,	"</li>"
			,	"</ul>"
			));
	}
	@Test  public void Empty_ignored_error() { // PAGE:en.w:Sukhoi_Su-47; "* </li>" causes error b/c </li> tries to close non-existent node
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		(	"* a"
		,	"* </li>"
		), String_.Concat_lines_nl_skip_last
		(	"<ul>"
		,	"  <li> a"
		,	"  </li>"
		,	"  <li> </li>" // TIDY.dangling: tidy will correct dangling node; DATE:2014-07-22
		,	"  </li>"
		,	"</ul>"
		));
	}
	@Test  public void Insert_nl() {// PURPOSE: <li> should always be separated by nl, or else items will merge, creating long horizontal scroll bar; EX:w:Music
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str("<ul><li>a</li><li>b</li></ul>"
			,	String_.Concat_lines_nl_skip_last
			(	"<ul>"
			,	"<li>a</li>"
			,	"<li>b</li></ul>"
			,	""
			));
		fxt.Init_para_n_();
	}
	@Test  public void Duplicate() {	// PURPOSE: redundant li; EX: "* <li>"; PAGE:it.w:Milano#Bibliographie; DATE:2013-07-23
		fxt.Test_parse_page_all_str("* <li>x</li>", String_.Concat_lines_nl_skip_last
		(	"<ul>"
		,	"  <li> "
		,	"<li>x</li>"	// TIDY: duplicate li will be stripped out; DATE:2014-06-26
		,	"  </li>"
		,	"</ul>"
		));
	}
	@Test  public void Dangling_inside_xnde() {	// PURPOSE.TIDY: handle "<li><span>a<li><span>b"; PAGE:ro.w:Pagina principala; DATE:2014-06-26
		fxt.Test_parse_page_all_str("<li><span>a<li><span>b", String_.Concat_lines_nl_skip_last
		(	"<li><span>a"
		,	"<li><span>b</span></li></span></li>"	// TIDY: will (a) move </span></li> to 1st line
		));	
	}
}
