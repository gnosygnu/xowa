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
package gplx.xowa.parsers.miscs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_comm_lxr_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Test  public void Basic() {
		fxt.Test_parse_page_all_str("a<!-- b -->c", "ac");
	}
	@Test  public void Err() {
		fxt.Init_log_(Xop_comm_log.Eos).Test_parse_page_all_str("<!-- ", "");
	}
	@Test  public void Ws_end() {
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "a"
		, "<!-- b --> "
		, "c"
		), String_.Concat_lines_nl_skip_last
		( "a"
		, "c"
		));
	}
	@Test  public void Ws_bgn_end() {
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "a"
		, " <!-- b --> "
		, "c"
		), String_.Concat_lines_nl_skip_last
		( "a"
		, "c"
		));
	}
	@Test  public void Ws_noop() {	// PURPOSE: assert that comments do not strip ws
		fxt.Test_parse_page_all_str("a <!-- b -->c", "a c");
	}
	@Test  public void Noinclude() {// PURPOSE: templates can construct comments; EX:WBK: {{Subjects/allbooks|subject=Computer programming|origin=Computer programming languages|diagnose=}}
		fxt.Test_parse_page_all_str("a <!-<noinclude></noinclude>- b -->c", "a c");
	}
	@Test  public void Comment_can_cause_pre() {// PURPOSE: assert that comment causes pre; DATE:2014-02-18
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "a"
		, " <!-- b -->c"
		, "d"
		), String_.Concat_lines_nl_skip_last
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
	@Test  public void Ws_bgn_needs_nl() {	// PURPOSE: do not strip new line unles *entire* line is comment
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "a"
		, " <!-- b -->"
		, "c"
		), String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "c"
		, "</p>"
		, ""
		));
		fxt.Init_para_n_();
	}
	@Test  public void Ws_strip_nl() {	// PURPOSE: handle multiple "<!-- -->\n"; was only trimming 1st; DATE:2014-02-24
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "a"
		, "<!-- -->"
		, "<!-- -->"
		, "b"
		), String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "b"
		, "</p>"
		, ""
		));
		fxt.Init_para_n_();
	}
}
