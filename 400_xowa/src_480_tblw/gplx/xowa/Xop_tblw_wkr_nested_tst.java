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
public class Xop_tblw_wkr_nested_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Test  public void Basic() {
		fxt.Test_parse_page_wiki(String_.Concat_lines_nl_skip_last
			( "{|"
			,	"|-"
			,		"|"
			,			"{|"
			,				"|-"
			,					"|a"
			,			"|}"
			,		"|b"
			, "|}"
			)
			, fxt.tkn_tblw_tb_(0, 25).Subs_
			(	fxt.tkn_tblw_tr_(2, 22).Subs_
				(	fxt.tkn_tblw_td_(5, 19).Subs_
					(	fxt.tkn_tblw_tb_(7, 19).Subs_
						(	fxt.tkn_tblw_tr_(10, 16).Subs_
							(	fxt.tkn_tblw_td_(13, 16).Subs_(fxt.tkn_txt_(15, 16), fxt.tkn_para_blank_(17))
							)
						)
						, fxt.tkn_para_blank_(20)
					)
				,	fxt.tkn_tblw_td_(19, 22).Subs_(fxt.tkn_txt_(21, 22), fxt.tkn_para_blank_(23))
				)
			)
			);
	}
	@Test  public void Leading_ws() {
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"{|id='a'"
			,	"|-"
			,	"|a"
			,	"|-"
			,	"|id='b'|"
			,	"  {|id='c'"
			,	"  |-"
			,	"  |d"
			,	"  |}"
			,	"|}"
			)
			, String_.Concat_lines_nl_skip_last
			(	"<table id='a'>"
			,	"  <tr>"
			,	"    <td>a"
			,	"    </td>"
			,	"  </tr>"
			,	"  <tr>"
			,	"    <td id='b'>"
			,	"      <table id='c'>"
			,	"        <tr>"
			,	"          <td>d"
			,	"          </td>"
			,	"        </tr>"
			,	"      </table>"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			)
			);
		fxt.Init_para_n_();
	}
	@Test  public void Tblx_tblw() {	// PURPOSE: if <table> followed by {|, ignore 2nd table; EX: en.b:Wikibooks:Featured_books; DATE:2014-02-08
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"<table cellpadding=\"0\">"
			,	"{| cellspacing=\"0\""
			,	"|a"
			,	"|}"
			,	"</table>"
			), String_.Concat_lines_nl_skip_last
			(	"<table cellpadding=\"0\">"
			,	"  <tr>"
			,	"    <td>a"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			));
	}
	@Test  public void Caption_and_tblw() {	// TIDY: don't try to fix <caption><table> sequence; PAGE:es.w:Sevilla; DATE:2014-06-29
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "{|"
			, 	"|+"
			, 	"{|"
			,	"|}"
			, "|}"), String_.Concat_lines_nl_skip_last
			( "<table>"
			, "  <caption>"
			, "    <table>"
			, "      <tr>"
			, "        <td>"
			, "        </td>"
			, "      </tr>"
			, "    </table>"
			, "  </caption>"
			, "</table>"
			, ""
			));
	}
	@Test  public void Tb_tr_tb() {	// PURPOSE: if <tr><table>, auto-create <tr><td>; EX:w:Paris; DATE:2014-03-18
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "{|"
			, "|-"
			, "{|"
			, "|}"
			, "|}"), String_.Concat_lines_nl_skip_last
			( "<table>"
			, "  <tr>"
			, "    <table>"
			, "      <tr>"
			, "        <td>"
			, "        </td>"
			, "      </tr>"
			, "    </table>"
			, "  </tr>"
			, "</table>"
			, ""
			));
	}
	@Test   public void Tblw_tblx_tblw_fails() {	// PURPOSE: {| -> <table> -> \n| was not rendering as <td>; EX:en.w:Paris#Demographics; DATE:2014-03-18
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "{|"
			, "|-"
			, "|a"
			, "</td></tr>"
			, "<tr><td><table>"
			, "<tr><td>b</td>"
			, "</tr>"
			, "|c"
			, "</td></tr></table>"
			, "|}"
			), String_.Concat_lines_nl_skip_last
			( "<table>"
			, "  <tr>"
			, "    <td>a"
			, "    </td>"
			, "  </tr>"
			, "  <tr>"
			, "    <td>"
			, "      <table>"
			, "        <tr>"
			, "          <td>b"
			, "          </td>"
			, "        </tr>"
			, "        <tr>"
			, "          <td>c"
			, "          </td>"
			, "        </tr>"
			, "      </table>"
			, "    </td>"
			, "  </tr>"
			, "</table>"
			));
	}
//		@Test   public void Nested_tbl_missing() {	// PURPOSE: nested table not rendering properly; EX:ar.s:; DATE:2014-03-18
//			fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
//				( "{|"
//				, "|-"
//				, "{|"
//				, "|-"
//				, "|}"
//				, "| width='50%' | a"
//				, "|}"
//				), String_.Concat_lines_nl_skip_last
//				( "<table>"
//				, "  <tr>"
//				, "    <td>a"
//				, "    </td>"
//				, "    <td>[[b|c"
//				, "    </td>"
//				, "  </tr>"
//				, "</table>"
//				, ""
//				, "<p>d"
//				, "</p>"
//				));
//		}
}
