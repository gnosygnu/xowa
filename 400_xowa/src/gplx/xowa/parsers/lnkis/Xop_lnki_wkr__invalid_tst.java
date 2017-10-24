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
package gplx.xowa.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
import gplx.xowa.langs.cases.*; import gplx.xowa.wikis.ttls.*;
public class Xop_lnki_wkr__invalid_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_n_();} private final    Xop_fxt fxt = new Xop_fxt();
	@Test  public void Ignore_invalid_url_encodings() { // PURPOSE: if url encoding is invalid, still render lnki as <a>; EX: fr.w:Bordetella; 
		fxt.Test_parse_page_all_str("[[%GC]]", "<a href=\"/wiki/%25GC\">%GC</a>");
	}
	@Test  public void Caption_still_parsed() {	// PURPOSE: failed lnki should still parse xml in caption; EX:ar.w:الصومال; DATE:2014-03-04
		fxt.Test_parse_page_all_str("[[|''a'']]"		, "[[|<i>a</i>]]");
	}
	@Test  public void Ttl_still_parsed() {	// PURPOSE: invalid lnki should still parse ttl; BASED_ON:ar.w:الصومال; DATE:2014-03-26
		fxt.Test_parse_page_all_str("[[''[a]'']]"		, "[[<i>[a]</i>]]");
	}
	@Test  public void Pipe_only() {
		fxt.Init_log_(Xop_lnki_log.Invalid_ttl);
		fxt.Test_parse_page_wiki("[[|]]", fxt.tkn_txt_(0, 2), fxt.tkn_pipe_(2), fxt.tkn_txt_(3, 5));
	}
	@Test  public void Xnde_should_force_ttl_parse() {	// PURPOSE: reparse should be forced at xnde not at pipe; EX: [[a<b>c</b>|d]] reparse should start at <b>; DATE:2014-03-30
		fxt.Test_parse_page_all_str_and_chk("[[a<b>c</b>|d]]"	, "[[a<b>c</b>|d]]", Xop_lnki_log.Invalid_ttl);
	}
	@Test  public void Tblw_tb() {	// PURPOSE: reparse should be forced at tblw.tb; DATE:2014-04-03
		fxt.Test_parse_page_all_str_and_chk("[[a\n{||b]]", String_.Concat_lines_nl_skip_last
		( "[[a"
		, "<table>|b]]"
		, "</table>"
		, ""
		), Xop_lnki_log.Invalid_ttl);
	}
	@Test  public void Tblw_tr() {	// PURPOSE: reparse should be forced at tblw.tr; DATE:2014-04-03
		fxt.Test_parse_page_all_str_and_chk("[[a\n|-b]]", String_.Concat_lines_nl_skip_last
		( "[[a"
		, "|-b]]"
		), Xop_lnki_log.Invalid_ttl);
	}
	@Test  public void Tblw_tr_like() {	// PURPOSE: do not invalidate if pseudo-tr; DATE:2014-04-03
		fxt.Test_parse_page_all_str_and_chk("[[a|-b]]", "<a href=\"/wiki/A\">-b</a>");
	}
	@Test  public void Nl() {	// PURPOSE: invalidate if nl; DATE:2014-04-03
		fxt.Test_parse_page_all_str_and_chk("''[[\n]]", "<i>[[</i>\n]]", Xop_lnki_log.Invalid_ttl);
	}
	@Test  public void Nl_with_apos_shouldnt_fail() {	// PURPOSE: apos, lnki and nl will cause parser to fail; DATE:2013-10-31
		fxt.Test_parse_page_all_str("''[[\n]]", "<i>[[</i>\n]]");
	}
//		@Test  public void Brack_end_invalid() {	// PURPOSE: invalidate if ]; DATE:2014-04-03; // TODO_OLD: backout apos changes
//			fxt.Test_parse_page_all_str_and_chk("[[A] ]", "[[A] ]", Xop_lnki_log.Invalid_ttl);
//		}
	@Test  public void Module() {	// PURPOSE: handle lnki_wkr parsing Module text (shouldn't happen); apos, tblw, lnki, and nl will cause parser to fail; also handles scan-bwd; EX:Module:Taxobox; DATE:2013-11-10
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl
		( " [[''"
		, " [["
		, "  |"	// NOTE: this is actually a tblw_ws_tkn ("\n   |") not a pipe_tkn
		, "]]"
		)
		, String_.Concat_lines_nl_skip_last
		( "<pre>[[</i>"	// NOTE: should be <i> but scan_bwd can't undo previous apos
		, "[["
		, " |"
		, "</pre>"
		, ""
		, "<p>]]"
		, "</p>"
		, ""
		));
		fxt.Init_para_n_();
	}
	@Test  public void Tblw_in_lnki() { // PURPOSE: handle invalid tblw tkn inside lnki; DATE:2014-06-06
		fxt.Test_parse_page_all_str("[[A[]\n|b]]", "[[A[]\n|b]]");	// NOTE: \n| is tblw code for td
	}
//		@Test  public void Tmpl() {	// PURPOSE: invalid lnki breaks template
//			fxt.Init_defn_clear();
//			fxt.Init_defn_add("a", "b");
//			fxt.Test_parse_page_all_str("{{a|[[}}", "b");
//		}
	@Test  public void Ns_is_not_main_and_starts_with_anchor() { // PURPOSE:page cannot start with # if non-main ns; PAGE:en.w:Spindale,_North_Carolina; DATE:2015-12-28
		fxt.Test_parse_page_all_str("[[File:#A]]"		, "[[File:#A]]");
	}
}

