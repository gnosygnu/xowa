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
public class Xop_xnde_wkr__xatrs_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Inline() {
		fxt.Test_parse_page_wiki("<ref cd=\"ef\" />"		, fxt.tkn_xnde_(0, 15).Atrs_rng_(5, 13));
		fxt.Test_parse_page_wiki("<ref   cd = \"e f\"  />"	, fxt.tkn_xnde_(0, 21).Atrs_rng_(5, 19)); // ws
	}
	@Test  public void Bgn() {
		fxt.Test_parse_page_wiki("<div cd=\"ef\"></div>"	, fxt.tkn_xnde_(0, 19).Atrs_rng_(5, 12)); // basic
	}
	@Test  public void Repeated() {	// PURPOSE: if atr is repeated, take 1st, not last; EX: it.u:Dipartimento:Fisica_e_Astronomia; DATE:2014-02-09
		fxt.Test_parse_page_all_str("<span style='color:red' style='color:green'>a</span>"						, "<span style='color:green'>a</span>");	// two
		fxt.Test_parse_page_all_str("<span style='color:red' style='color:green' style='color:blue'>a</span>"	, "<span style='color:blue'>a</span>");		// three
	}
	@Test  public void Non_ws() {			// PURPOSE: <br$2/> is valid; symbols function as ws
		fxt.Init_log_(Xop_xatr_parser.Log_invalid_atr).Test_parse_page_wiki("<br$2/>"	, fxt.tkn_xnde_(0, 7).Atrs_rng_(3, 5));
	}
	@Test  public void Invalid() {			// PURPOSE: make sure brx does not match br
		fxt.Test_parse_page_wiki("<brx/>"	, fxt.tkn_bry_(0, 1), fxt.tkn_txt_(1, 6));
	}
	@Test  public void Id_encode() {
		fxt.Test_parse_page_all_str("<div id=\"a b c\"></div>", "<div id=\"a_b_c\"></div>");
	}
	@Test  public void Lt_should_not_be_escaped_in_input() {	// PURPOSE: options textboxes were escaped if input's value had "<"; DATE:2014-07-04
		fxt.Page().Html_data().Restricted_n_();
		fxt.Test_parse_page_wiki_str("<input value='a<'></input>", "<input value='a<'></input>");	// NOTE: do not call parse_page_all_str which will call Page.Clear and reset Restricted
		fxt.Page().Html_data().Restricted_y_();
	}
//		@Test  public void Unclosed() {	// PURPOSE: unclosed atr should be treated as key, which should be ignored; PAGE:en.w:Palace of Versailles
//			fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
//			(	"<span id=\"1�>a"		// id="1� -> key named 'id="1�' which fails whitelist keys
//			,	"</span>"
//			), String_.Concat_lines_nl_skip_last
//			(	"<span>a"
//			,	"</span>"
//			));
//		}
}
