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
public class Xop_xnde_wkr__err_malformed_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Lt_only() {
		fxt.Test_parse_page_wiki("<", fxt.tkn_txt_(0, 1));
	}
	@Test  public void Eos_while_closing_tag() {
		fxt.Init_log_(Xop_xnde_log.Eos_while_closing_tag).Test_parse_page_wiki("<ref [[a]]", fxt.tkn_txt_(0, 4), fxt.tkn_space_(4, 5), fxt.tkn_lnki_(5, 10));
	}
	@Test  public void End_tag_broken() {	// chk that name_bgn is less than src_len else arrayIndex error; EX: <ref><p></p<<ref/>;  DATE:2014-01-18
		fxt.Wiki().Xtn_mgr().Init_by_wiki(fxt.Wiki());
		fxt.Test_parse_page_all_str("<poem><p></p<</poem>", String_.Concat_lines_nl_skip_last
		( "<div class=\"poem\">"
		, "<p>"	// NOTE: technically MW / WP does not add this <p>; however, easier to hardcode <p>; no "visual" effect; DATE:2014-04-27
		, "<p>&lt;/p&lt;</p>"
		, "</p>"
		, "</div>"
		));
	}
	@Test  public void Incomplete_tag_div() { // PURPOSE: handle broken tags; EX: <div a </div> -> &lt;div a; DATE:2014-02-03
		fxt.Test_parse_page_all_str("<div a </div>", "&lt;div a ");	// note that "<div a " is escaped (not considered xnde; while "</div>" is dropped (dangling end xndes are ignored)
	}
	@Test  public void Incomplete_tag_ref() {// PURPOSE: invalid tag shouldn't break parser; EX:w:Cullen_(surname); "http://www.surnamedb.com/Surname/Cullen<ref"
		fxt.Test_parse_page_all_str("a<ref", "a&lt;ref");
	}	
	@Test  public void Inline_tag_fix() {	// PURPOSE: force <b/> to be <b></b>; EX: w:Exchange_value
		fxt.Init_log_(Xop_xnde_log.No_inline);
		fxt.Test_parse_page_all_str("<b/>", "<b></b>");
	}
	@Test  public void Tblw() {	// PURPOSE.fix: don't auto-close past tblw PAGE:ro.b:Pagina_principala DATE:2014-06-26
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "<div>"
		, "{|"			// this should stop xnde search
		, "<center>"
		, "</div>"		// this should not find <div> as its bgn_tag; note that it will "drop out" below
		, "|}"
		, "</div>"
		), String_.Concat_lines_nl_skip_last
		( "<div>"
		, "<table><center>"
		, "  <tr>"
		, "    <td>"
		, "    </td>"
		, "  </tr>"
		, "</center>"
		, "</table>"
		, "</div>"
		));
	}
}
