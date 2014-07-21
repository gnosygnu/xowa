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
public class Xop_tblw_wkr__uncommon_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_y_();} private Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Tr_pops_entire_stack() {	// PURPOSE: in strange cases, tr will pop entire stack; EX:en.w:Turks_in_Denmark; DATE:2014-03-02
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "{|"
		, "<caption>a"
		, "|b"
		, "|-"
		, "|c"
		, "|}"
		)		
		, String_.Concat_lines_nl
		( "<table>"
		, "  <caption>a"
		, "  </caption>"
		, "  <tr>"
		, "    <td>b"
		, "    </td>"
		, "  </tr>"
		, "  <tr>"
		, "    <td>c"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test   public void Atrs_defect() {	// PURPOSE: < in atrs was causing premature termination; EX:en.w:Wikipedia:List of hoaxes on Wikipedia
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "{|id=\"a<b\""
			, "|a"
			, "|}"), String_.Concat_lines_nl_skip_last
			( "<table id=\"a.3Cb\">"
			, "  <tr>"
			, "    <td>a"
			, "    </td>"
			, "  </tr>"
			, "</table>"
			, ""
			));
	}
	@Test   public void Broken_lnki() {	// PURPOSE: broken lnki was not closing table properly; EX:en.w:Wikipedia:Changing_attribution_for_an_edit; DATE:2014-03-16
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "{|"
			, "|-"
			, "|a"
			, "|[[b|c"
			, "|}"
			, "d"
			), String_.Concat_lines_nl_skip_last
			( "<table>"
			, "  <tr>"
			, "    <td>a"
			, "    </td>"
			, "    <td>[[b|c"
			, "    </td>"
			, "  </tr>"
			, "</table>"
			, ""
			, "<p>d"
			, "</p>"
			));
	}
}
