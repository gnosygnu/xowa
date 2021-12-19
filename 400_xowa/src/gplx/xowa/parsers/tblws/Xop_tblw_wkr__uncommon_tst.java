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
package gplx.xowa.parsers.tblws;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.*;
import org.junit.*;
public class Xop_tblw_wkr__uncommon_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_y_();} private final Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test public void Tr_pops_entire_stack() {	// PURPOSE: in strange cases, tr will pop entire stack; PAGE:en.w:Turks_in_Denmark; DATE:2014-03-02
		fxt.Test_parse_page_all_str(StringUtl.ConcatLinesNlSkipLast
		( "{|"
		, "<caption>a"
		, "|b"
		, "|-"
		, "|c"
		, "|}"
		)		
		, StringUtl.ConcatLinesNl
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
	@Test public void Atrs_defect() {	// PURPOSE: < in atrs was causing premature termination; PAGE:en.w:Wikipedia:List of hoaxes on Wikipedia
		fxt.Test_parse_page_wiki_str(StringUtl.ConcatLinesNlSkipLast
		( "{|id=\"a<b\""
		, "|a"
		, "|}"), StringUtl.ConcatLinesNlSkipLast
		( "<table id=\"a.3Cb\">"
		, "  <tr>"
		, "    <td>a"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		, ""
		));
	}
	@Test public void Broken_lnki() {	// PURPOSE: broken lnki was not closing table properly; PAGE:en.w:Wikipedia:Changing_attribution_for_an_edit; DATE:2014-03-16
		fxt.Test_parse_page_wiki_str(StringUtl.ConcatLinesNlSkipLast
		( "{|"
		, "|-"
		, "|a"
		, "|[[b|c"
		, "|}"
		, "d"
		), StringUtl.ConcatLinesNlSkipLast
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
	@Test public void Broken_lnki_2() {	// PURPOSE: variation on above; PAGE:hr.b:Knjiga_pojmova_u_zrakoplovstvu/Kratice_u_zrakoplovstvu/S; DATE:2014-09-05
		fxt.Test_parse_page_wiki_str(StringUtl.ConcatLinesNlSkipLast
		( "{|"
		, "|-"
		, "| [[A | b"
		, "|-"
		, "| B"
		, "|}"
		), StringUtl.ConcatLinesNlSkipLast
		( "<table>"
		, "  <tr>"
		, "    <td> [[A | b"
		, "    </td>"
		, "  </tr>"
		, "  <tr>"
		, "    <td> B"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test public void Tr_with_pipe_ignores_content() {	// PURPOSE: "|-" followed by "|" ignores rest of content; EX: {|\n|-|<b>a</b>\n|} PAGE:lv.w:Starptautiska_kosmosa_stacija; DATE:2015-11-21
		fxt.Test_parse_page_wiki_str(StringUtl.ConcatLinesNlSkipLast
		( "{|"
		, "|- |<b>a</b>"
		, "|}"
		), StringUtl.ConcatLinesNlSkipLast
		( "<table>"
		, "</table>"
		));
	}
	@Test public void Tr_with_pipe_should_ignore() {	// PURPOSE: ignore sequences like "\n|- ||"; PAGE: nl.w:Tabel_van_Belgische_gemeenten; DATE:2015-12-03
		fxt.Test_parse_page_wiki_str(StringUtl.ConcatLinesNlSkipLast
		( "{|"
		, "|- ||"
		, "|a|b"
		, "|}"
		), StringUtl.ConcatLinesNlSkipLast
		( "<table>"
		, "  <tr>"
		, "    <td>b"
		, "    </td>"
		, "  </tr>"
		, "</table>"
		));
	}
}
