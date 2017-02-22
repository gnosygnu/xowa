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
package gplx.xowa.parsers.lists; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_list_wkr_uncommon_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Bug_specified_div() {	// FIX: </div> was not clearing state for lnki; PAGE:en.w:Ananke (moon)
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "<div>"
		, "#<i>a"
		, "</div>"
		, "*b"
		), String_.Concat_lines_nl_skip_last
		( "<div>"
		, "<ol>"
		, "  <li><i>a"
		, "</i>"
		, "  </li>"
		, "</ol></div>"
		, "<ul>"
		, "  <li>b"
		, "  </li>"
		, "</ul>"
		));
	}
	@Test  public void Bug_mismatched() {	// FIX: </div> was not clearing state for lnki; PAGE:en.w:Ananke (moon)
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "::a"
		, ":::1"
		, "::::11"
		, ":::::111"
		, "::b"
		), String_.Concat_lines_nl_skip_last
		( "<dl>"
		, "  <dd>"
		, "    <dl>"
		, "      <dd>a"
		, "        <dl>"
		, "          <dd>1"
		, "            <dl>"
		, "              <dd>11"
		, "                <dl>"
		, "                  <dd>111"
		, "                  </dd>"
		, "                </dl>"
		, "              </dd>"
		, "            </dl>"
		, "          </dd>"
		, "        </dl>"
		, "      </dd>"
		, "      <dd>b"
		, "      </dd>"
		, "    </dl>"
		, "  </dd>"
		, "</dl>"
		));
	}
	@Test  public void Empty_li_ignored() {	// PURPOSE: inner template can cause dupe li; PAGE:en.w:any Calendar day and NYT link; NOTE:deactivated prune_empty_list logic; DATE:2014-09-05
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "*a"
		, "*    "
		, "*b"
		, "*c"
		), String_.Concat_lines_nl_skip_last
		( "<ul>"
		, "  <li>a"
		, "  </li>"
		, "  <li>    "
		, "  </li>"
		, "  <li>b"
		, "  </li>"
		, "  <li>c"
		, "  </li>"
		, "</ul>"
		, ""
		));
		fxt.Init_para_n_();
	}
	@Test  public void List_in_tblw() {	// PURPOSE: list inside table should not be close outer list; PAGE:en.w:Cato the Elder
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "*a"
		, "{|"
		, "|b"
		, "::c"
		, "|}"
		), String_.Concat_lines_nl_skip_last
		( "<ul>"
		, "  <li>a"
		, "  </li>"
		, "</ul>"
		, "<table>"
		, "  <tr>"
		, "    <td>b"
		, ""
		, "      <dl>"
		, "        <dd>"
		, "          <dl>"
		, "            <dd>c"
		, "            </dd>"
		, "          </dl>"
		, "        </dd>"
		, "      </dl>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		, ""
		));
		fxt.Init_para_n_();
	}
	@Test  public void Dt_dd_colon_at_eol() {		// PURPOSE: dangling ":" should not put next line in <dt>; PAGE:en.w:Stein; b was being wrapped in <dt>b</dt>; NOTE:deactivated prune_empty_list logic; DATE:2014-09-05
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( ";a:"
		, "*b"
		, ""
		, ";c"
		, "*d"
		), String_.Concat_lines_nl_skip_last
		( "<dl>"
		, "  <dt>a"
		, "  </dt>"
		,   "  <dd>"
		,   "  </dd>"
		, "</dl>"
		, "<ul>"
		, "  <li>b"
		, "  </li>"
		, "</ul>"
		, ""
		, "<dl>"
		, "  <dt>c"
		, "  </dt>"
		, "</dl>"
		, "<ul>"
		, "  <li>d"
		, "  </li>"
		, "</ul>"
		, ""
		));
		fxt.Init_para_n_();
	}
	@Test  public void Dd_should_not_print_colon() {// PURPOSE: ;a:\n should show as ";a" not ";a:". colon should still be considered as part of empty list; DATE:2013-11-07; NOTE:deactivated prune_empty_list logic; DATE:2014-09-05
		fxt.Test_parse_page_all_str
		( ";a:\nb"
		,	String_.Concat_lines_nl_skip_last
		( "<dl>"
		, "  <dt>a"
		, "  </dt>"
		, "  <dd>"
		, "  </dd>"
		, "</dl>"
		, "b"
		));		
	}
	@Test  public void Dt_dd_colon_in_lnki() {	// PURPOSE: "; [[Portal:a]]" should not split lnki; PAGE:en.w:Wikipedia:WikiProject Military history/Operation Majestic Titan; "; [[Wikipedia:WikiProject Military history/Operation Majestic Titan/Phase I|Phase I]]: a b"
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( ";[[Portal:a]]"
		), String_.Concat_lines_nl_skip_last
		( "<dl>"
		, "  <dt><a href=\"/wiki/Portal:A\">Portal:A</a>"
		, "  </dt>"
		, "</dl>"
		, ""
		));
		fxt.Init_para_n_();
	}			
	@Test  public void Max_list_depth() {	// PURPOSE: 256+ * caused list parser to fail; ignore; PAGE:en.w:Bariatric surgery
		String multiple = String_.Repeat("*", 300);
		fxt.Test_parse_page_all_str(multiple, multiple);
	}
	@Test  public void Numbered_list_resets_incorrectly() {	// PURPOSE: as description
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "#A"
		, "#*Aa"
		, "#**Aaa"
		, "#*Ab"
		, "#B"
		), String_.Concat_lines_nl_skip_last
		( "<ol>"
		, "  <li>A"
		, ""
		, "    <ul>"
		, "      <li>Aa"
		, ""
		, "        <ul>"
		, "          <li>Aaa"
		, "          </li>"
		, "        </ul>"
		, "      </li>"
		, "      <li>Ab"
		, "      </li>"
		, "    </ul>"	// was showing as </ol>
		, "  </li>"
		, "  <li>B"
		, "  </li>"
		, "</ol>"
		, ""
		));
		fxt.Init_para_n_();
	}			
	@Test   public void List_should_not_end_indented_table() {// PURPOSE: :{| was being closed by \n*; EX:w:Maxwell's equations; DATE:20121231
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( ":{|"
		, "|-"
		, "|"
		, "*a"
		, "|b"
		, "|}"
		), String_.Concat_lines_nl_skip_last
		( "<dl>"
		, "  <dd>"
		, "    <table>"
		, "      <tr>"
		, "        <td>"
		, "          <ul>"
		, "            <li>a"
		, "            </li>"
		, "          </ul>"
		, "        </td>"
		, "        <td>b"
		, "        </td>"
		, "      </tr>"
		, "    </table>"
		, "  </dd>"
		, "</dl>"
		));
	}
	@Test  public void Dt_dd_broken_by_xnde() {	// PURPOSE.fix: xnde was resetting dl incorrectly; EX:w:Virus; DATE:2013-01-31 
		fxt.Test_parse_page_all_str(";<b>a</b>:c"
		, String_.Concat_lines_nl_skip_last
		( "<dl>"
		, "  <dt><b>a</b>"
		, "  </dt>"
		, "  <dd>c"
		, "  </dd>"
		, "</dl>"
		));		
	}
	@Test   public void Trim_empty_list_items() {	// PURPOSE: empty list items should be ignored; DATE:2013-07-02; NOTE:deactivated prune_empty_list logic; DATE:2014-09-05
		fxt.Test_parse_page_all_str
		("***   \n"
		, String_.Concat_lines_nl_skip_last
		( "<ul>"
		, "  <li>"
		, "    <ul>"
		, "      <li>"
		, "        <ul>"
		, "          <li>   "
		, "          </li>"
		, "        </ul>"
		, "      </li>"
		, "    </ul>"
		, "  </li>"
		, "</ul>"
		, ""
		));		
	}
	@Test   public void Trim_empty_list_items_error() {	// PURPOSE.fix: do not add empty itm's nesting to current list; DATE:2013-07-07; NOTE:deactivated prune_empty_list logic; DATE:2014-09-05
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl
		( "* a"
		, "** "	// was: do not add ** to nest; now: add ** and \s
		, "*** b"
		, "* c"
		), String_.Concat_lines_nl
		( "<ul>"
		, "  <li> a"
		, "    <ul>"
		, "      <li> "
		, "        <ul>"
		, "          <li> b"
		, "          </li>"
		, "        </ul>"
		, "      </li>"
		, "    </ul>"
		, "  </li>"
		, "  <li> c"
		, "  </li>"
		, "</ul>"
		));		
	}
	@Test   public void Tblw_should_autoclose() {// PURPOSE: tblw should auto-close open list
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
			( "#a"
			, "{|"
			, "|b"
			, "|}"
			), String_.Concat_lines_nl_skip_last
			( "<ol>"
			, "  <li>a"
			, "  </li>"
			, "</ol>"
			, "<table>"
			, "  <tr>"
			, "    <td>b"
			, "    </td>"
			, "  </tr>"
			, "</table>"
			, ""
			));
	}
	@Test   public void Tblx_should_not_autoclose() {	// PURPOSE: do not auto-close list if table is xnde; DATE:2014-02-05
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl
		( "#a"
		, "# <table><tr><td>b</td></tr></table>"
		, "c"
		), String_.Concat_lines_nl
		( "<ol>"
		, "  <li>a"
		, "  </li>"
		, "  <li> "
		, "    <table>"
		, "      <tr>"
		, "        <td>b"
		, "        </td>"
		, "      </tr>"
		, "    </table>"
		, "  </li>"
		, "</ol>"
		, "c"
		));		
	}
	@Test   public void Li_disappears() {	// PURPOSE: "\n*" disappears when followed by "<li>"; PAGE:en.w:Bristol_Bullfinch; DATE:2014-06-24
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl
		( "a"
		, "*b<li>"
		), String_.Concat_lines_nl_skip_last	// NOTE: tag sequence matches MW output
		( "a"
		, "<ul>"
		, "  <li>b"
		, "<li>"
		, "</li>"
		, "  </li>"
		, "</ul>"
		));		
	}
	@Test   public void Ul_should_end_wlst() {	// PURPOSE: </ul> should end wiki_list; PAGE:en.w:Bristol_Bullfinch; DATE:2014-06-24
		fxt.Test_parse_page_all_str
		( "*a</ul>b"
		, String_.Concat_lines_nl_skip_last
		( "<ul>"
		, "  <li>a</ul>b"	// TIDY.dangling: tidy will correct dangling node; DATE:2014-07-22
		, "  </li>"
		, "</ul>"
		));
	}
	@Test  public void Colon_causes_dd() { // PURPOSE: colon was mistakenly being ignored due to proximity to "\n;"; PAGE:de.w:Schmach_von_Tirana#Kuriosit.C3.A4t:_EM-Qualifikationsspiel_vom_20._November_1983 DATE:2014-07-11
		fxt.Test_parse_page_all_str
		( String_.Concat_lines_nl_skip_last
		( "a:b"
		, ";c"
		), String_.Concat_lines_nl_skip_last
		( "a:b"
		, "<dl>"
		, "  <dt>c"
		, "  </dt>"
		, "</dl>"
		));		
	}
	@Test   public void Pre_and_nested() {	// PURPOSE: pre should interrupt list; PAGE:fi.w:Luettelo_hyönteisistä; DATE:2015-03-31
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str
		( String_.Concat_lines_nl_skip_last
		( "*a"
		, "**b"
		, " c"		// pre
		, "*d"		// *d treated mistakenly as **d
		), String_.Concat_lines_nl_skip_last
		( "<ul>"
		, "  <li>a"
		, ""
		, "    <ul>"
		, "      <li>b"
		, "      </li>"
		, "    </ul>"
		, "  </li>"
		, "</ul>"
		, ""
		, "<pre>c"
		, "</pre>"
		, ""
		, "<ul>"
		, "  <li>d"
		, "  </li>"
		, "</ul>"
		, ""
		));
		fxt.Init_para_n_();
	}
}
