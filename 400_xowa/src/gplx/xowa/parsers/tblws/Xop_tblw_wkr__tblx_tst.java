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
public class Xop_tblw_wkr__tblx_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_y_();} private final    Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Ignore_td() {	// PURPOSE: do not parse pipe as td if in <table>; EX:ru.w:Сочи; DATE:2014-02-22
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td>a"
			,	"| b"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			) ,	String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td>a"
			,	"| b"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			)
			);
	}
	@Test  public void Ignore_tr() {	// PURPOSE: do not parse "\n|-", "\n!" if in <table>; EX:s.w:Uranus; DATE:2014-05-05
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		(	"<table>"
		,	"  <tr>"
		,	"    <td>a"
		,	"|-"
		,	"! b"
		,	"| c"
		,	"    </td>"
		,	"  </tr>"
		,	"</table>"
		) ,	String_.Concat_lines_nl_skip_last
		(	"<table>"
		,	"  <tr>"
		,	"    <td>a"
		,	""
		,	"<p>|-"
		,	"! b"
		,	"| c"
		,	"</p>"
		,	"    </td>"
		,	"  </tr>"
		,	"</table>"
		,	""
		)
		);
	}
	@Test   public void Tblw_tblx_tblw_fails() {	// PURPOSE: {| -> <table> -> \n| was not rendering as <td>; PAGE:en.w:Paris#Demographics; DATE:2014-03-18
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
	@Test  public void Auto_tr_after_td() {	// PURPOSE: "<tr><td>\n|" -> "<tr><td><tr><td>"; PAGE:fi.w:Salibandyn_maailmanmestaruuskilpailut_2012 DATE:2015-09-07
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		(	"{|"
		,	"  <tr>"
		,	"    <td>a"
		,	"    </td>"
		,	"    <td>"
		,	"|b"
		,	"|}"
		) ,	String_.Concat_lines_nl_skip_last
		(	"<table>"
		,	"  <tr>"
		,	"    <td>a"
		,	"    </td>"
		,	"    <td>"	// NOTE: dangling <td> from above just gets auto-closed; no logic in tblw_wkr to actually remove it
		,	"    </td>"
		,	"  </tr>"
		,	"  <tr>"	// <tr> inserted by transition from <td> to "\n|"
		,	"    <td>b"
		,	"    </td>"
		,	"  </tr>"
		,	"</table>"
		,	""
		)
		);
	}
	@Test  public void Ignore_tr_in_lnki() {	// PURPOSE: <tr> fragment within lnki should be ignored; PAGE:en.w:Aargau DATE:2016-08-14
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "<table>"
		, "<tr>"
		, "<td>[[A|B<tr><td>t_1</td></tr>]]"
		, "</td>"
		, "</tr>"
		, "</table>"
		) ,	String_.Concat_lines_nl_skip_last
		( "<table>"
		, "  <tr>"
		, "    <td><a href=\"/wiki/A\">B"
		, "      <tr>"
		, "        <td>t_1"
		, "        </td>"
		, "      </tr>"
		, "</a>"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
}
