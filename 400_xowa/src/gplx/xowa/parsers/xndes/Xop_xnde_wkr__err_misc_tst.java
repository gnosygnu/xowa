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
public class Xop_xnde_wkr__err_misc_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Error_br_removed() {
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <th><span>a</span><br/><span>b</span>"
			,	"    </th>"
			,	"  </tr>"
			,	"</table>"
			), String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <th><span>a</span><br/><span>b</span>"
			,	"    </th>"
			,	"  </tr>"
			,	"</table>"
			,	""
			));
		fxt.Init_para_n_();
	}
	@Test  public void Div_should_not_pop_past_td() {	// PURPOSE: extra </div> should not close <div> that is outside of <td>; PAGE:en.w:Rome en.w:Ankara
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"<table>"
			,		"<tr>"
			,			"<td>"
			,				"<div>"									// this is <div> #1
			,					"<table>"
			,						"<tr>"
			,							"<td>"
			,								"<div>"					// this is <div> #2
			,									"<div>"
			,										"a"
			,									"</div>"
			,							"</td>"
			,							"<td>"
			,									"<div>"
			,										"b"
			,									"</div>"
			,								"</div>"				// this </div> was supposed to pop <div> #2, but can't (b/c of HTML rules); however, do not try to pop <div> #1;
			,							"</td>"
			,							"<td>"
			,								"<div>"
			,									"c"
			,								"</div>"
			,							"</td>"
			,						"</tr>"
			,					"</table>"
			,				"</div>"
			,			"</td>"
			,		"</tr>"
			,	"</table>"
			), String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td>"
			,	"<div>"
			,	"      <table>"
			,	"        <tr>"
			,	"          <td>"
			,	"<div>"
			,	"<div>"
			,	""
			,	"<p>a"
			,	"</p>"
			,	"</div>"
			,	"</div>"
			,	"          </td>"
			,	"          <td>"
			,	"<div>"
			,	""
			,	"<p>b"
			,	"</p>"
			,	"</div>"
			,	"</div>"				// TIDY.dangling: tidy will correct dangling node; DATE:2014-07-22
			,	"          </td>"
			,	"          <td>"
			,	"<div>"
			,	""
			,	"<p>c"
			,	"</p>"
			,	"</div>"
			,	"          </td>"
			,	"        </tr>"
			,	"      </table>"
			,	"</div>"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			));
		fxt.Init_para_n_();
	}
	@Test  public void Xnde_pops() {	// PURPOSE: somehow xnde pops upper nde; PAGE:en.w:Greek government debt crisis; "History of government debt"
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"<i>"
			,	"{|"
			,	"|-"
			,	"|<i>a</i>"
			,	"|}"
			,	"</i>"
			), String_.Concat_lines_nl_skip_last
			(	"<i>"
			,	"<table>"
			,	"  <tr>"
			,	"    <td><i>a</i>"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	"</i>"
			));
	}
	@Test  public void Err_inline_extension() {
		fxt.Test_parse_page_all_str
			(	"<poem/>"
			,	""
			);
	}
	@Test  public void Xnde_para() {	// PURPOSE: buggy code caused </p> to close everything; keeping test b/c of <p> logic
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"<tr>"
			,	"<td>"
			,	"<div>"
			,	"<p>"
			,	"<span>"
			,	"</span>"
			,	"</p>"
			,	"</div>"
			,	"</td>"
			,	"</tr>"
			,	"</table>"
			), String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td>"
			,	"<div>"
			,	"<p>"
			,	"<span>"
			,	"</span>"
			,	"</p>"
			,	"</div>"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			)
			);
	}
	@Test  public void Sup_bug() {	// PURPOSE: occurred at ref of UK; a {{cite web|url=http://www.abc.gov/{{dead link|date=December 2011}}|title=UK}} b
		fxt.Test_parse_page_wiki_str("x <b><sup>y</b> z", "x <b><sup>y</sup></b> z");
	}
	@Test  public void Br_backslash() {	// PURPOSE: allow <br\>; EX:w:Mosquito; [[Acalyptratae|A<br\>c<br\>a<br\>l<br\>y<br\>p<br\>t<br\>r<br\>a<br\>t<br\>a<br\>e]]
		fxt.Test_parse_page_all_str("<br\\>", "<br/>");
	}
	@Test  public void Tt_does_not_repeat() {	// PURPOSE: handle <tt>a<tt>; EX:w:Domain name registry
		fxt.Test_parse_page_all_str("<tt>a<tt>", "<tt>a</tt>");
	}
	@Test  public void Loose_xnde_names() {	// PURPOSE: MW allows <font-> and other variations; EX:w:2012_in_film
		fxt.Test_parse_page_all_str("<font-size='100%'>a</font>", "<font>a</font>");
	}
	@Test   public void Anchor_nested() {
		fxt.Test_parse_page_all_str("b<a>c<a>d [[e]] f", "b&lt;a>c&lt;a>d <a href=\"/wiki/E\">e</a> f");
	}
	@Test   public void Img_should_not_be_xtn() {	// PURPOSE:<img> marked as .xtn; unclosed <img> was escaping rest of text; PAGE:de.w:Wikipedia:Technik/Archiv/2014 DATE:2014-11-06
		fxt.Test_parse_page_all_str("<img>''a''", "&lt;img><i>a</i>");
	}
}
