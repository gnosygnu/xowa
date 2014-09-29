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
import org.junit.*; import gplx.xowa.parsers.lists.*;
public class Xop_xnde_wkr__err_dangling_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Basic() {
		fxt.Init_log_(Xop_xnde_log.Dangling_xnde)
			.Test_parse_page_wiki("<div>", fxt.tkn_xnde_(0, 5));
	}
	@Test  public void Many() {
		fxt.Init_log_(Xop_xnde_log.Dangling_xnde, Xop_xnde_log.Dangling_xnde, Xop_xnde_log.Dangling_xnde)
			.Test_parse_page_wiki("<div><div><div>", fxt.tkn_xnde_(0, 15).Subs_(fxt.tkn_xnde_(5, 15).Subs_(fxt.tkn_xnde_(10, 15))));
	}
	@Test  public void Nested() {
		fxt.Test_parse_page_wiki_str
		( "<div><div><center>a</div></div>"
		, "<div><div><center>a</center></div></div>"
		);
	}
	@Test  public void Center() {
		fxt.Init_log_(Xop_xnde_log.Dangling_xnde).Test_parse_page_wiki("a<center>b"
			, fxt.tkn_txt_(0, 1)
			, fxt.tkn_xnde_(1, 10).CloseMode_(Xop_xnde_tkn.CloseMode_open).Subs_(fxt.tkn_txt_(9, 10))
			);
	}
	@Test  public void P() {
		fxt.Init_log_(Xop_xnde_log.Auto_closing_section).Test_parse_page_wiki("a<p>b<p>c</p>"
			, fxt.tkn_txt_	(0, 1)
			, fxt.tkn_xnde_	(1, 4).Subs_(fxt.tkn_txt_(4, 5))
			, fxt.tkn_xnde_	(5, 13).Subs_(fxt.tkn_txt_(8, 9))
			);
	}
	@Test   public void Alternating() {	// PURPOSE: confirmation test for alternating dangling nodes; PAGE:en.w:Portal:Pornography/Selected_historical_image/Archive; DATE:2014-09-24
		fxt.Test_parse_page_wiki_str
		( "c<b><i>d<b><i>e"
		, "c<b><i>d<b><i>e</i></b></i></b>"
		);
	}
	@Test  public void Li() {	// PURPOSE: auto-close <li>; NOTE: no longer encloses in <ul/>; DATE:2014-06-26
		fxt.Test_parse_page_wiki_str
			( "<li>a<li>b"
			,	String_.Concat_lines_nl_skip_last
			( "<li>a</li>"
			, "<li>b</li>"
			));
	}
	@Test  public void Br() {
		fxt.Test_parse_page_wiki("<br>a"	, fxt.tkn_xnde_(0, 4), fxt.tkn_txt_(4, 5));
		fxt.Test_parse_page_wiki("a<br name=b>c", fxt.tkn_txt_(0, 1), fxt.tkn_xnde_(1, 12), fxt.tkn_txt_(12, 13));
	}
	@Test  public void Td_and_td() {	// PURPOSE: when "<td>a<td>", 2nd <td> should auto-close
		fxt.Test_parse_page_wiki("<table><tr><td>a<td></tr><tr><td>b</td></tr></table>"
			, fxt.tkn_tblw_tb_(0, 52).Subs_
			( fxt.tkn_tblw_tr_(7, 25).Subs_
			( 	fxt.tkn_tblw_td_(11, 16).Subs_(fxt.tkn_txt_(15, 16))	// FUTURE: change to 11,20
			,		fxt.tkn_tblw_td_(16, 25)	// FUTURE: change this to 16, 20
			)
			,	fxt.tkn_tblw_tr_(25, 44).Subs_
			( 	fxt.tkn_tblw_td_(29, 39).Subs_(fxt.tkn_txt_(33, 34))
			)
			)				
			);
	}
	@Test  public void Tblw_and_tr() {// PURPOSE: <tr> should auto-close |-; EX:fr.wikipedia.org/wiki/Napoléon_Ier; DATE:2013-12-09
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl
		( "{|"
		, "|-"
		, "<td>row1</td>"
		, "<tr><td>row2</td>"
		, "|}"
		)
		,	String_.Concat_lines_nl
		( "<table>"
		, "  <tr>"
		, "    <td>row1"
		, "    </td>"
		, "  </tr>"
		, "  <tr>"
		, "    <td>row2"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		)
		);
	}
	@Test  public void Tblx_and_b() {
		fxt.Init_log_(Xop_xnde_log.Dangling_xnde).Test_parse_page_wiki("<table><tr><td><b>a<td></tr></table>"
			, fxt.tkn_tblw_tb_(0, 36).Subs_
			( fxt.tkn_tblw_tr_(7, 28).Subs_
			( 	fxt.tkn_tblw_td_(11, 19).Subs_	// FUTURE: change to 11,23
					( fxt.tkn_xnde_(15, 36).Subs_(fxt.tkn_txt_(18, 19))	// FUTURE: should be 19, but xnde.Close() is passing in src_len
					)
			,		fxt.tkn_tblw_td_(19, 28)	// FUTURE: should be 23
			)
			)
			);
	}
	@Test  public void Tblx_and_li() {	// PURPOSE: </td> should close list; see Stamp Act 1765
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "<table><tr><td>"
			, "*abc</td></tr><tr><td>bcd</td></tr>"
			, "</table>"
			), String_.Concat_lines_nl_skip_last
			( "<table>"
			, "  <tr>"
			, "    <td>"
			, ""
			, "      <ul>"
			, "        <li>abc"
			, "        </li>"
			, "      </ul>"
			, "    </td>"
			, "  </tr>"
			, "  <tr>"
			, "    <td>bcd"
			, "    </td>"
			, "  </tr>"
			, "</table>"
			, ""
			)
			);
		fxt.Init_para_n_();
	}
	@Test  public void Tblx_and_small() {	// PURPOSE: </td> should close <small> correctly; see Stamp Act 1765
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "<table><tr><td>"
			, "<small>abc</td></tr><tr><td>bcd</td></tr>"
			, "</table>"
			), String_.Concat_lines_nl_skip_last
			( "<table>"
			, "  <tr>"
			, "    <td>"
			, "<small>abc</small>"
			, "    </td>"
			,   "  </tr>"
			, "  <tr>"
			, "    <td>bcd"
			, "    </td>"
			, "  </tr>"
			, "</table>"
			, ""
			)
			);
		fxt.Init_para_n_();
	}
	@Test  public void Blockquote_and_p() {
		fxt.Init_log_(Xop_xnde_log.Auto_closing_section).Test_parse_page_wiki("<blockquote>a<p>b</blockquote>"
			, fxt.tkn_xnde_(0, 30).Subs_
			( fxt.tkn_txt_(12, 13)
			,	fxt.tkn_xnde_(13, 17).Subs_(fxt.tkn_txt_(16, 17))
			));
	}
	@Test  public void List_and_b() {
		fxt.Init_log_(Xop_xnde_log.Dangling_xnde).Test_parse_page_wiki("*<b>a\n*"
			, fxt.tkn_list_bgn_(0, 1, Xop_list_tkn_.List_itmTyp_ul).List_path_(0)
			, fxt.tkn_xnde_(1, 7).Subs_(fxt.tkn_txt_(4, 5))
			, fxt.tkn_list_end_(5).List_path_(0)
			, fxt.tkn_list_bgn_(5, 7, Xop_list_tkn_.List_itmTyp_ul).List_path_(1)
			, fxt.tkn_list_end_(7).List_path_(1)
			);
	}
	@Test  public void Underline() {	// PURPOSE: 2nd <u> should auto-close; PAGE:en.b:Textbook_of_Psychiatry/Alcoholism_and_Psychoactive_Substance_Use_Disorders DATE:2014-09-05
		fxt.Test_html_full_str("a<u>b<u>c", "a<u>b</u>c");
	}
}
