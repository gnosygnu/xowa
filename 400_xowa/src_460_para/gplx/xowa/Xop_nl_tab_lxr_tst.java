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
public class Xop_nl_tab_lxr_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_y_();} private Xop_fxt fxt = new Xop_fxt();
	@After public void teardown() {fxt.Init_para_n_();}
	@Test  public void Basic() {		// PURPOSE: \n\t|- should be recognized as tblw; EX:zh.v:西安; DATE:2014-05-06
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl
		( "{|"
		, "\t|-"
		, "|a"
		, "|}"
		), String_.Concat_lines_nl
		( "<table>"
		, "  <tr>"
		, "    <td>a"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test  public void Ws() {			// PURPOSE: \n\t|- should be recognized as tblw; EX:zh.v:西安; DATE:2014-05-06
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl
		( "{|"
		, "\t  |-"	// \t  
		, "|a"
		, "|}"
		), String_.Concat_lines_nl
		( "<table>"
		, "  <tr>"
		, "    <td>a"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test  public void Ignore() {// PURPOSE: \n\t should not be pre; EX:pl.w:Main_Page; DATE:2014-05-06
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "a"
		, "\t b"
		, "c"
		),	String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "\t b"
		, "c"
		, "</p>"
		));
	}
}
