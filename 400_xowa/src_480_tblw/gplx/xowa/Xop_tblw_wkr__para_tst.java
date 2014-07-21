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
package gplx.xowa; import gplx.*;
import org.junit.*;
public class Xop_tblw_wkr__para_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_y_();} private Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Para() {	// PURPOSE: para causing strange breaks; SEE:[[John F. Kennedy]] and "two Supreme Court appointments"
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"{|"
			,	"<p></p>"
			,	"|a"
			,	"<p></p>"
			,	"|}"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<table><p></p>"
			,	"  <tr>"
			,	"    <td>a"
			,	"<p></p>"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			)
			);
	}
	@Test  public void Nl() {	// PURPOSE: para causing strange breaks; SEE:[[John F. Kennedy]] and "two Supreme Court appointments"
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"{|"
			,	"|-"
			,	"!a"
			,	""
			,	"|-"
			,	"|}"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <th>a"
			,	"    </th>"
			,	"  </tr>"
			,	"</table>"
			,	""
			)
			);
	}
	@Test  public void Unnecessary_para() {	// PURPOSE: tblw causes unnecessary <p>; [[Help:Download]]; DATE:2014-02-20
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"{|"
			,	"|-"
			,	"|"
			,	"a<br/>"
			,	"b"
			,	"|"
			,	"c<br/>"
			,	"d"
			,	"|}"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td>"
			,	""
			,	"<p>a<br/>"
			,	"b"
			,	"</p>"
			,	"    </td>"
			,	"    <td>"
			,	""
			,	"<p>c<br/>"
			,	"d"
			,	"</p>"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			)
			);
	}
	@Test  public void Ws_leading() {	// PAGE:en.w:AGPLv3
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"{|"
			,	" !a"
			,	" !b"
			,	"|}"
			)
			, String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <th>a"
			,	"    </th>"
			,	"    <th>b"
			,	"    </th>"
			,	"  </tr>"
			,	"</table>"
			,	""
			)
			);
	}
	@Test  public void Ws_th_2() {	// "\n\s!" should still be interpreted as tblw; s.w:Manchester; DATE:2014-02-14
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"{|"
			,	"|-"
			,	"|!style='color:red'|a"
			,	" !style=\"color:blue\"|b"
			,	"|}"
			)
			, String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td>a"
			,	"    </td>"
			,	"    <th style=\"color:blue\">b"
			,	"    </th>"
			,	"  </tr>"
			,	"</table>"
			,	""
			)
			);
	}
	@Test  public void Ws_th_3() {	// "\n\s!" and "!!" breaks tblw; ru.w:Храмы_Санкт-Петербурга (List of churches in St Petersburg); DATE:2014-02-20
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"{|"
			,	" ! id='1' | a !! id='2' | b"
			,	"|}"
			)
			, String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <th id='1'> a "
			,	"    </th>"
			,	"    <th id='2'> b"
			,	"    </th>"
			,	"  </tr>"
			,	"</table>"
			,	""
			)
			);
	}
	@Test  public void Tblw_td2_should_not_create_ws() { // PURPOSE: a||b -> a\n||b; EX:none;discovered during luaj test; DATE:2014-04-14
		fxt.Test_parse_page_wiki_str("a||b", "<p>a||b\n</p>");
	}
}
