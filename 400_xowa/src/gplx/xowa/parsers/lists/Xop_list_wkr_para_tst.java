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
package gplx.xowa.parsers.lists; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_list_wkr_para_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_y_();} private Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Basic() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"*a"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<ul>"
			,	"  <li>a"
			,	"  </li>"
			,	"</ul>"
			,	""
			)
			);
	}
	@Test  public void Multiple() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"*a"
			,	"*b"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<ul>"
			,	"  <li>a"
			,	"  </li>"
			,	"  <li>b"
			,	"  </li>"
			,	"</ul>"
			)
			);
	}
	@Test  public void Multiple_w_1_nl() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"*a"
			,	""
			,	"*b"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<ul>"
			,	"  <li>a"
			,	"  </li>"
			,	"</ul>"
			,	""
			,	"<ul>"
			,	"  <li>b"
			,	"  </li>"
			,	"</ul>"
			)
			);
	}
	@Test  public void Pre_between_lists() {	// PURPOSE: list should close pre; EX:en.b:Knowing Knoppix/Other applications; DATE:2014-02-18
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"#a"
			,	" b"
			,	"#c"	// should close <pre> opened by b
			) ,	String_.Concat_lines_nl_skip_last
			(	"<ol>"
			,	"  <li>a"
			,	"  </li>"
			,	"</ol>"
			,	""
			,	"<pre>b"
			,	"</pre>"
			,	""
			,	"<ol>"
			,	"  <li>c"
			,	"  </li>"
			,	"</ol>"
			)
			);
	}
}
