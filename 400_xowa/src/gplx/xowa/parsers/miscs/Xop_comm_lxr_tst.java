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
package gplx.xowa.parsers.miscs;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.*;
import org.junit.*;
public class Xop_comm_lxr_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Test public void Basic() {
		fxt.Test_parse_page_all_str("a<!-- b -->c", "ac");
	}
	@Test public void Err() {
		fxt.Init_log_(Xop_comm_log.Eos).Test_parse_page_all_str("<!-- ", "");
	}
	@Test public void Ws_end() {
		fxt.Test_parse_page_all_str(StringUtl.ConcatLinesNlSkipLast
		( "a"
		, "<!-- b --> "
		, "c"
		), StringUtl.ConcatLinesNlSkipLast
		( "a"
		, "c"
		));
	}
	@Test public void Ws_bgn_end() {
		fxt.Test_parse_page_all_str(StringUtl.ConcatLinesNlSkipLast
		( "a"
		, " <!-- b --> "
		, "c"
		), StringUtl.ConcatLinesNlSkipLast
		( "a"
		, "c"
		));
	}
	@Test public void Ws_noop() {	// PURPOSE: assert that comments do not strip ws
		fxt.Test_parse_page_all_str("a <!-- b -->c", "a c");
	}
	@Test public void Noinclude() {// PURPOSE: templates can construct comments; EX:WBK: {{Subjects/allbooks|subject=Computer programming|origin=Computer programming languages|diagnose=}}
		fxt.Test_parse_page_all_str("a <!-<noinclude></noinclude>- b -->c", "a c");
	}
	@Test public void Comment_can_cause_pre() {// PURPOSE: assert that comment causes pre; DATE:2014-02-18
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str(StringUtl.ConcatLinesNlSkipLast
		( "a"
		, " <!-- b -->c"
		, "d"
		), StringUtl.ConcatLinesNlSkipLast
		( "<p>a"
		, "</p>"
		, ""
		, "<pre>c"
		, "</pre>"
		, ""
		, "<p>d"
		, "</p>"
		, ""
		));
		fxt.Init_para_n_();
	}
	@Test public void Ws_bgn_needs_nl() {	// PURPOSE: do not strip new line unles *entire* line is comment
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str(StringUtl.ConcatLinesNlSkipLast
		( "a"
		, " <!-- b -->"
		, "c"
		), StringUtl.ConcatLinesNlSkipLast
		( "<p>a"
		, "c"
		, "</p>"
		, ""
		));
		fxt.Init_para_n_();
	}
	@Test public void Ws_strip_nl() {	// PURPOSE: handle multiple "<!-- -->\n"; was only trimming 1st; DATE:2014-02-24
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str(StringUtl.ConcatLinesNlSkipLast
		( "a"
		, "<!-- -->"
		, "<!-- -->"
		, "b"
		), StringUtl.ConcatLinesNlSkipLast
		( "<p>a"
		, "b"
		, "</p>"
		, ""
		));
		fxt.Init_para_n_();
	}
	@Test public void Comment_should_not_gobble_paras() {// ISSUE#:437 DATE:2019-04-27
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str(StringUtl.ConcatLinesNlSkipLast
		( "a"
		, ""
		, "<!-- z --> "
		, "b"
		), StringUtl.ConcatLinesNlSkipLast // was <p>a\nb\<p>
		( "<p>a"
		, "</p>"
		, ""
		, "<p>b"
		, "</p>"
		, ""
		));
		fxt.Init_para_n_();
	}
}
