/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.parsers.tblws; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_tblw_wkr__basic_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Test  public void Td() {					// Tb_tr_td_te
		fxt.Test_parse_page_wiki("{|\n|-\n|a\n|}"
			, fxt.tkn_tblw_tb_(0, 11).Subs_
			(	fxt.tkn_tblw_tr_(2, 8).Subs_
			(		fxt.tkn_tblw_td_(5, 8).Subs_(fxt.tkn_txt_(7, 8), fxt.tkn_para_blank_(9))))				
			);
	}
	@Test  public void Td2() {					// Tb_tr_td_td2_te
		fxt.Test_parse_page_wiki("{|\n|-\n|a||b\n|}"
			, fxt.tkn_tblw_tb_(0, 14).Subs_
			(	fxt.tkn_tblw_tr_(2, 11).Subs_
			(		fxt.tkn_tblw_td_(5,  8).Subs_(fxt.tkn_txt_( 7,  8), fxt.tkn_para_blank_(9))
			,		fxt.tkn_tblw_td_(8, 11).Subs_(fxt.tkn_txt_(10, 11), fxt.tkn_para_blank_(12))
			)));
	}
	@Test  public void Tc() {					// Tb_tc_te
		fxt.Test_parse_page_wiki("{|\n|+a\n|}"
			, fxt.tkn_tblw_tb_(0, 9).Caption_count_(1).Subs_
			(	fxt.tkn_tblw_tc_(2, 6).Subs_
			(		fxt.tkn_txt_(5, 6)
			, fxt.tkn_para_blank_(7)
			)
			)
			);
	}
	@Test  public void Tc_longer() {			// Tb_tc_tr_td_te
		fxt.Test_parse_page_wiki("{|\n|+a\n|-\n|b\n|}"
			, fxt.tkn_tblw_tb_(0, 15).Caption_count_(1).Subs_
			(	fxt.tkn_tblw_tc_(2,  6).Subs_(fxt.tkn_txt_(5, 6))
			,	fxt.tkn_tblw_tr_(6, 12).Subs_
			(		fxt.tkn_tblw_td_(9, 12).Subs_(fxt.tkn_txt_(11, 12), fxt.tkn_para_blank_(13))
			)
			));
	}
	@Test  public void Th() {					// Tb_th_te
		fxt.Test_parse_page_wiki("{|\n|-\n!a\n|}"
			, fxt.tkn_tblw_tb_(0, 11).Subs_
			(	fxt.tkn_tblw_tr_(2, 8).Subs_
			(		fxt.tkn_tblw_th_(5, 8).Subs_(fxt.tkn_txt_(7, 8), fxt.tkn_para_blank_(9))
			)));
	}
	@Test  public void Th2() {					// Tb_th_th2_te
		fxt.Test_parse_page_wiki("{|\n|-\n!a!!b\n|}"
			, fxt.tkn_tblw_tb_(0, 14).Subs_
			(	fxt.tkn_tblw_tr_(2, 11).Subs_
			(		fxt.tkn_tblw_th_(5,  8).Subs_(fxt.tkn_txt_( 7,  8))
			,		fxt.tkn_tblw_th_(8, 11).Subs_(fxt.tkn_txt_(10, 11), fxt.tkn_para_blank_(12))
			)));
	}
	@Test  public void Th2_td_syntax() {		// Tb_th_td; || should be treated as th
		fxt.Test_parse_page_wiki("{|\n|-\n!a||b\n|}"
			, fxt.tkn_tblw_tb_(0, 14).Subs_
			(	fxt.tkn_tblw_tr_(2, 11).Subs_
			(		fxt.tkn_tblw_th_(5,  8).Subs_(fxt.tkn_txt_( 7,  8))
			,		fxt.tkn_tblw_th_(8, 11).Subs_(fxt.tkn_txt_(10, 11), fxt.tkn_para_blank_(12))
			)));
	}
	@Test  public void Tb_td2() {	// PAGE:en.w:Hectare; {| class="wikitable" || style="border: 1px solid #FFFFFF;"
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"{|id='1' || class='a'"
			,	"|-"
			,	"|a"
			,	"|}")
			, String_.Concat_lines_nl_skip_last
			(	"<table id='1' class='a'>"
			,	"  <tr>"
			,	"    <td>a"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			));
	}
	@Test  public void Td_lnki() {
		fxt.Test_parse_page_wiki("{|\n|-\n|[[a|b]]\n|}"
			, fxt.tkn_tblw_tb_(0, 17).Subs_
			(	fxt.tkn_tblw_tr_(2, 14).Subs_
			(		fxt.tkn_tblw_td_(5, 14).Subs_(fxt.tkn_lnki_(7, 14), fxt.tkn_para_blank_(15))))
			);
	}
	@Test  public void Tr_dupe_xnde() {	// PURPOSE: redundant tr should not be dropped; see [[Jupiter]]
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"{|"
			,	"|-"
			,	"<tr><td>a</td></tr>"
			,	"|-"
			,	"|}"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td>a"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			)
			);
	}
	@Test  public void Tr_dupe_xnde_2() {	// <td></th> causes problems
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"{|"
			,	"<tr><th>a</td></tr>"
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
	@Test  public void Bang_should_not_make_cell_td_1_bang() {	// PURPOSE: "| a! b" ! should not separate cell
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last("{|", "|-", "|a!b", "|}"),	String_.Concat_lines_nl_skip_last("<table>", "  <tr>", "    <td>a!b"	, "    </td>", "  </tr>", "</table>", ""));
	}
	@Test  public void Bang_should_not_make_cell_td_2_bang() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last("{|", "|-", "|a!!b", "|}"),	String_.Concat_lines_nl_skip_last("<table>", "  <tr>", "    <td>a!!b"	, "    </td>", "  </tr>", "</table>", ""));
	}
	@Test  public void Bang_should_not_make_cell_th_1_bang() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last("{|", "|-", "!a!b", "|}"),	String_.Concat_lines_nl_skip_last("<table>", "  <tr>", "    <th>a!b"	, "    </th>", "  </tr>", "</table>", ""));
	}
	@Test  public void Bang_should_not_make_cell_th_2_bang() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last("{|", "|-", "!a!!b", "|}")	
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
			));
	}
	@Test  public void Bang_should_not_make_cell_th_mult_line() {	// FIX: make sure code does not disable subsequent bangs
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last("{|", "|-", "!a", "!b", "|}")	
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
			));
	}
	@Test  public void Fix_extra_cell() {	// PURPOSE: trim should not affect td; WP:Base32
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"{|"
			,	"!id='1'|a"
			,	"|"
			,	"!id='2'|b"
			,	"|-"
			,	"|a1|| ||b1"
			,	"|}"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <th id='1'>a"
			,	"    </th>"
			,	"    <td>"
			,	"    </td>"
			,	"    <th id='2'>b"
			,	"    </th>"
			,	"  </tr>"
			,	"  <tr>"
			,	"    <td>a1"
			,	"    </td>"
			,	"    <td> "
			,	"    </td>"
			,	"    <td>b1"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			)
			);
		fxt.Init_para_n_();
	}
	@Test  public void Nl_td() {	// PURPOSE: <p> inside <td> does not get enclosed
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"<tr>"
			,	"<td>"
			,	""
			,	""
			,	"a"
			,	""
			,	""
			,	"</td>"
			,	"</tr>"
			,	"</table>"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td>"
			,	""
			,	"<p><br/>"
			,	"a"
			,	"</p>"
			,	""
			,	"<p><br/>"
			,	"</p>"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			)
			);
		fxt.Init_para_n_();
	}
	@Test  public void Trim_ws() {	// PURPOSE: trim should be done from both sides
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"<tr>"
			,	"<td>"
			,	"</td>"
			,	"</tr>"
			,	""
			,	""
			,	"a"
			,	""
			,	""
			,	"</table>"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td>"
			,	"    </td>"
			,	"  </tr>"
			,	"a"
			,	"</table>"
			,	""
			)
			);
		fxt.Init_para_n_();
	}
	@Test  public void Trim_ws_tr() {	// PURPOSE: trim should be done from both sides
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"<tr>"
			,	"<td>"
			,	"</td>"
			,	"</tr>"
			,	""
			,	""
			,	""
			,	""
			,	"<tr>"
			,	"<td>"
			,	"</td>"
			,	"</tr>"
			,	"</table>"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td>"
			,	"    </td>"
			,	"  </tr>"
			,	"  <tr>"
			,	"    <td>"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			)
			);
		fxt.Init_para_n_();
	}
	@Test  public void Trim_ws_td() {	// PURPOSE: trim should not affect td
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"<tr>"
			,	"<td>"
			,	""
			,	""
			,	"a"
			,	""
			,	""
			,	"</td>"
			,	"</tr>"
			,	"</table>"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td>"
			,	""
			,	"<p><br/>"
			,	"a"
			,	"</p>"
			,	""
			,	"<p><br/>"
			,	"</p>"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			)
			);
		fxt.Init_para_n_();
	}
	@Test  public void No_wiki_3() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"{|"
			,	"|style=<nowiki>'a[b]c'</nowiki>|d"
			,	"|}"
			), String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td style='a[b]c'>d"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			));
	}
	@Test  public void Trailing_tr_breaks_para_mode() {// PURPOSE.fix: empty trailing tr breaks para mode; EX:w:Sibelius
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
			(	"{|"
			,	"|a"
			,	"|-"	// causes lines below not to be put in paras
			,	"|}"
			,	"b"
			,	""
			,	"c"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td>a"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			,	"<p>b"
			,	"</p>"
			,	""
			,	"<p>c"
			,	"</p>"
			,	""
			));
		fxt.Init_para_n_();
	}
	@Test  public void Blank_line_should_be_own_para() {// PURPOSE.fix: caption does not begin on own line; EX:w:Old St. Peter's Basilica
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
			(	"{|"
			,	"|a"
			,	"b"
			,	"|}"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td>a"
			,	""
			,	"<p>b"
			,	"</p>"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			));
		fxt.Init_para_n_();
	}
	@Test  public void Blank_line_should_be_own_para_2() {// PURPOSE.fix: caption does not begin on own line; EX:w:Old St. Peter's Basilica
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
			(	"{|"
			,	"|a"
			,	"b"
			,	"|-"
			,	"|}"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td>a"
			,	""
			,	"<p>b"
			,	"</p>"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			));
		fxt.Init_para_n_();
	}
	@Test  public void Bold_stops_at_table() {	// PURPOSE: do not allow unclosed bold to extend over tables;
		fxt.Test_parse_page_all_str("'''<table><tr><td>a</td></tr></table>", String_.Concat_lines_nl_skip_last
			(	"<b></b>"
			,	"<table>"
			,	"  <tr>"
			,	"    <td>a"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			));
		fxt.Init_defn_clear();
	}
	@Test  public void Orphaned_tr_breaks_nested_tables() {	// PUPRPOSE: </tr> should not match <tr> outside scope; EX:w:Enthalpy_of_fusion; {{States of matter}}
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		(	"<table>"
		,	  "<tr>"
		,	    "<td>"
		,	      "<table>"
		,	        "</tr>"
		,	      "</table>"
		,	    "</td>"
		,	    "<td>a"
		,	    "</td>"
		,	  "</tr>"
		,	"</table>"
		),
		String_.Concat_lines_nl_skip_last
		(	"<table>"
		,	"  <tr>"
		,	"    <td>"
		,	"      <table>"
		,	"      </table>"
		,	"    </td>"
		,	"    <td>a"
		,	"    </td>"
		,	"  </tr>"
		,	"</table>"
		,	""
		)
		);
	}
	@Test  public void Space_causes_extra_p() {// PURPOSE: "\n\s</td>" should be equivalent to "\n</td>"; EX: w:Earth
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
			(	"<table><tr><td>"
			,	"b"
			,	"<br/>c"
			,	" </td></tr></table>"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td>"
			,	""
			,	"<p>b"			// used to close <p> here; <p>b</p>
			,	"<br/>c"
			,	"</p>"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			));
		fxt.Init_para_n_();
	}
	@Test  public void Br_should_not_be_ignored() {// PURPOSE: document <br />'s should not be ignored between tables; 20121226
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
			(	"{|"
			,	"|-"
			,	"|a"
			,	"|}"
			,	"<br />"
			,	"{|"
			,	"|-"
			,	"|b"
			,	"|}"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td>a"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			,	"<p><br />"	// (a) <br/> was being ignored; (b) <p> added for TRAILING_TBLW fix; DATE:2017-04-08
			,	"</p>"
			,	"<table>"
			,	"  <tr>"
			,	"    <td>b"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			));
		fxt.Init_para_n_();
	}
	@Test  public void AutoClose_td_when_new_tr() {	// retain; needed for de.w:Main_Page; DATE:2013-12-09
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "{|"
		, "==a=="
		, "|}"
		)
		, String_.Concat_lines_nl_skip_last
		( "<table>"
		, ""
		, "<h2>a</h2>"		// NOTE: malformed html matches MW
		, "  <tr>"
		, "    <td>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		, ""
		));
	}
	@Test  public void Auto_create_table() {// PURPOSE: <td> should create table; EX:w:Hatfield-McCoy_feud; DATE:20121226
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
			(	"<td>a"
			,	"</td>"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td>a"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			));
	}
	@Test  public void List_and_orphaned_td2_should_not_create_tblw() {// PURPOSE: !! was creating table; DATE:2013-04-28
		fxt.Test_parse_page_all_str("*a !! b", String_.Concat_lines_nl_skip_last
			(	"<ul>"
			,	"  <li>a !! b"
			,	"  </li>"
			,	"</ul>"
			));
	}
	@Test  public void Tr_trailing_dashes_should_be_stripped() {// PURPOSE: trailing dashes should be stripped; |--- -> |-; EX: |--style="x" was being ignored; DATE:2013-06-21
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
			(	"{|"
			,	"|-----style='a'"
			,	"|b"
			,	"|}"
			), String_.Concat_lines_nl
			(	"<table>"
			,	"  <tr style='a'>"
			,	"    <td>b"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			));
	}
	@Test  public void Th_without_tr() {	// PURPOSE: !! without preceding ! should not create table-cell; DATE:2013-12-18
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
			(	"{|"
			,	"|-"
			,	"|"
			,	"a!!b"
			,	"|}"
			), String_.Concat_lines_nl
			(	"<table>"
			,	"  <tr>"
			,	"    <td>"
			,	"a!!b"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			));
	}
	@Test  public void Td_at_eos() {// PURPOSE.fix: !! at eos fails; EX:es.s:Si_mis_manos_pudieran_deshojar; DATE:2014-02-11
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		(	"{|"
		,	"|-"
		,	"| <poem>!!</poem>"	// note that "!!" is eos inside the <poem> src
		,	"|}"
		), String_.Concat_lines_nl
		(	"<table>"
		,	"  <tr>"
		,	"    <td> <div class=\"poem\">"
		,	"<p>"
		,	"!!"
		,	"</p>"
		,	"</div>"
		,	"    </td>"
		,	"  </tr>"
		,	"</table>"
		));
	}
	@Test  public void Tr_without_tb_should_start_tb() {// PURPOSE: orphaned tr should automatically start table; EX: pl.w:Portal:Technika; DATE:2014-02-13
		fxt.Test_parse_page_all_str("<tr><td>a"
		, String_.Concat_lines_nl
		(	"<table>"
		,	"  <tr>"
		,	"    <td>a"
		,	"    </td>"
		,	"  </tr>"
		,	"</table>"
		));
	}
	@Test  public void Tblx_should_not_close_tblw() {// PURPOSE: </table> should not close {|; EX:fr.w:Exp%C3%A9dition_Endurance; DATE:2014-02-13
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		(	"{|"
		,	"|-"
		,	"|"
		,	"</table>"
		,	"|}"
		)		
		, String_.Concat_lines_nl
		(	"<table>"
		,	"  <tr>"
		,	"    <td>"
		,	"    </td>"
		,	"  </tr>"
		,	"</table>"
		));
	}
	@Test  public void Tblx_should_not_close_tblw_2() {// PURPOSE: </table> should close {|; ignore latter |}; EX:ru.q:Авель; DATE:2014-02-22
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		(	"{|"
		,	"|-"
		,	"|a"
		,	"</table>"
		,	"{|"
		,	"|-"
		,	"|b"
		,	"</table>"
		,	"{|"
		,	"|-"
		,	"|c"
		,	"</table>"
		,	"|}"
		)
		, String_.Concat_lines_nl
		(	"<table>"
		,	"  <tr>"
		,	"    <td>a"
		,	"    </td>"
		,	"  </tr>"
		,	"</table>"
		,	"<table>"
		,	"  <tr>"
		,	"    <td>b"
		,	"    </td>"
		,	"  </tr>"
		,	"</table>"
		,	"<table>"
		,	"  <tr>"
		,	"    <td>c"
		,	"    </td>"
		,	"  </tr>"
		,	"</table>"
		));
	}
	@Test  public void Td_in_list_in_tblw_should_be_ignored() {// PURPOSE: || should be ignored if in list; EX:es.d:casa; DATE:2014-02-15
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		(	"{|"
		,	"|-"
		,	"|"
		,	"* a || b"
		,	"|}"
		)		
		, String_.Concat_lines_nl
		(	"<table>"
		,	"  <tr>"
		,	"    <td>"
		,	"      <ul>"
		,	"        <li> a || b"
		,	"        </li>"
		,	"      </ul>"
		,	"    </td>"
		,	"  </tr>"
		,	"</table>"
		));
	}
	@Test  public void List_in_tblw() {// PURPOSE: list should close previous cell; EX: ru.d:Викисловарь:Условные_сокращения; DATE:2014-02-22
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		(	"{|"
		,	"|-"
		,	"|"
		,	"{|"
		,	"*a"
		,	"|}"
		,	"|}"
		)		
		, String_.Concat_lines_nl
		(	"<table>"
		,	"  <tr>"
		,	"    <td>"
		,	"      <table>"
		,	"        <ul>"			// NOTE: this should probably be inside <tr>, but this matches MW behavior; DATE:2014-02-22
		,	"          <li>a"
		,	"          </li>"
		,	"        </ul>"
		,	"        <tr>"
		,	"          <td>"
		,	"          </td>"
		,	"        </tr>"
		,	"      </table>"
		,	"    </td>"
		,	"  </tr>"
		,	"</table>"
		));
	}
}
//		@Test  public void Tb_under_tr_is_ignored() {	// PURPOSE: table directly under tr is ignored; PAGE:en.w:Category:Dessert stubs; TODO_OLD: complicated, especially to handle 2nd |}
//			fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
//				(	"{|"
//				,	"|-id='a'"
//				,	"{|style='border:1px;'"
//				,	"|-id='b'"
//				,	"|b"
//				,	"|}"
//				,	"|}"
//				), String_.Concat_lines_nl_skip_last
//				(	"<table>"
//				,	"  <tr id=\"b\">"
//				,	"    <td>b"
//				,	"    </td>"
//				,	"  </tr>"
//				,	"</table>"
//				,	""
//				));
//		}
//		@Test  public void Leading_ws() { // PAGE:en.w:Corneal dystrophy (human)
//			fxt.Test_parse_page_wiki(String_.Concat_lines_nl_skip_last
//				( " {|"
//				, " |-"
//				, " |a"
//				, " |}"
//				)
//				, fxt.tkn_tblw_tb_(1, 15).Subs_
//				(	fxt.tkn_tblw_tr_(3, 11).Subs_
//					(	fxt.tkn_tblw_td_(7, 11).Subs_
//						(	fxt.tkn_txt_())
//					)
//				)
//				);
//		}
//		@Test  public void Atrs_tb() {				// Tb_te		// FUTURE: reinstate; WHEN: Template
//			fxt.Init_log_(Xop_tblw_log.Tbl_empty).Test_parse_page_wiki("{|style='a'\n|}"
//				, fxt.tkn_tblw_tb_(0, 14).Atrs_rng_(2, 11).Subs_
//				(	fxt.tkn_tblw_tr_(11, 11).Subs_
//				(		fxt.tkn_tblw_td_(11, 11)
//				)));
//		}
//		@Test  public void Td_p() {	// PURPOSE: <p> not being closed correctly
//			fxt.Init_para_y_();
//			fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
//				(	"{|"
//				,	"|-"
//				,	"|"
//				,	"a"
//				,	"|}"), String_.Concat_lines_nl_skip_last
//				(	"<table>"
//				,	"  <tr>"
//				,	"    <td>"
//				,	""
//				,	"<p>a"
//				,	"</p>"
//				,	"    </td>"
//				,	"  </tr>"
//				,	"</table>"
//				,	""
//				));
//			fxt.Init_para_n_();
//		}
//		@Test  public void Tb_tb() {
//			fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
//				(	"{|id='1'"
//				, 	"{|id='2'"
//				,	"|-id='3'"
//				,	"|a"
//				,	"|}"
//				,	"|}"), String_.Concat_lines_nl_skip_last
//				( "<table id='1'>"
//				, "  <tr id='3'>"
//				, "    <td>a"
//				, "    </td>"
//				, "  </tr>"
//				, "</table>"
//				, ""
//				));
//		}
//		@Test  public void Tb_tb_2() {
//			fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
//				(	"{|id='1'"
//				, 	"{|id='2' <table id='3'>"
//				,	"|a"
//				,	"</table>"
//				,	"|}"
//				,	"|}"), String_.Concat_lines_nl_skip_last
//				( "<table id='1'>"
//				, "  <tr id='3'>"
//				, "    <td>a"
//				, "    </td>"
//				, "  </tr>"
//				, "</table>"
//				, ""
//				));
//		}
