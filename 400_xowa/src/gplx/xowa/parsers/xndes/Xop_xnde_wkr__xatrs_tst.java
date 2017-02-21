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
package gplx.xowa.parsers.xndes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_xnde_wkr__xatrs_tst {
	private final Xop_fxt fxt = new Xop_fxt();
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
		fxt.Test_parse_page_wiki("<br$2/>"	, fxt.tkn_xnde_(0, 7).Atrs_rng_(3, 5));
	}
	@Test  public void Invalid() {			// PURPOSE: make sure brx does not match br
		fxt.Test_parse_page_wiki("<brx/>"	, fxt.tkn_bry_(0, 1), fxt.tkn_txt_(1, 6));
	}
	@Test  public void Id_encode() {
		fxt.Test_parse_page_all_str("<div id=\"a b c\"></div>", "<div id=\"a_b_c\"></div>");
	}
	@Test  public void Lt_should_not_be_escaped_in_input() {	// PURPOSE: options textboxes were escaped if input's value had "<"; DATE:2014-07-04
		fxt.Page().Html_data().Html_restricted_n_();
		fxt.Test_parse_page_wiki_str("<input value='a<'></input>", "<input value='a<'></input>");	// NOTE: do not call parse_page_all_str which will call Page.Clear and reset Restricted
		fxt.Page().Html_data().Html_restricted_y_();
	}
	@Test  public void  Style__decode() {	// PURPOSE: style values should be decoded; PAGE:en.w:Boron; DATE:2015-07-29
		fxt.Test_parse_page_all_str("<span style='background:&#x23;ffc0c0'>a</span>", "<span style='background:#ffc0c0'>a</span>");
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
