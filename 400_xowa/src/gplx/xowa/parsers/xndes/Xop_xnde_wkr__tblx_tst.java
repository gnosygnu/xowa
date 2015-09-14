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
public class Xop_xnde_wkr__tblx_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Table() {
		fxt.Test_parse_page_wiki("a<table><tr><td>b</td></tr></table>c"
			, fxt.tkn_txt_ ( 0,  1)
			, fxt.tkn_tblw_tb_(1, 35).Subs_
			(	fxt.tkn_tblw_tr_(8, 27).Subs_
			(		fxt.tkn_tblw_td_(12, 22).Subs_(fxt.tkn_txt_(16, 17))
			)
			)
			, fxt.tkn_txt_ (35, 36)
			);
	}
	@Test  public void Ws_bgn() {	// PURPOSE: some templates return leading ws; PAGE:en.w:UK
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"  <table>"
			,	"    <tr>"
			,	"      <td>a"
			,	"      </td>"
			,	"    </tr>"
			,	"  </table>"
			), String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td>a"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,   ""
			));
		fxt.Init_para_n_();
	}
	@Test  public void Td_in_lnki_should_be_ignored() {// PURPOSE: \n| inside lnki should not be interpreted as table cell; EX: uk.w:Дніпро; DATE:2014-03-11
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "<table><tr><td>"
		, "[[File:A.png|150px"
		, "|B]]</td></tr></table>"
		), String_.Concat_lines_nl_skip_last
		( "<table>"
		, "  <tr>"
		, "    <td>"
		, "<a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"B\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/150px.png\" width=\"150\" height=\"0\" /></a>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
		fxt.Init_para_n_();
	}
	@Test  public void Nl() {
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str
			(	"<table>\n\n\n\n\n</table>"
			,	"<table>\n"
			+	"</table>\n"
			);
		fxt.Init_para_n_();
	}
}
