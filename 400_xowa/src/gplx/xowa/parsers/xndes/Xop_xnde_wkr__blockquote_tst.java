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
public class Xop_xnde_wkr__blockquote_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Pre() { // PURPOSE: preserve leading spaces within blockquote; PAGE:en.w:Tenerife_airport_disaster
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "<blockquote>"
		, " a"
		, "</blockquote>"
		), String_.Concat_lines_nl_skip_last
		( "<blockquote>"
		, " a"
		, "</blockquote>"
		));
		fxt.Init_para_n_();
	}
	@Test  public void Trailing_nls() { // PURPOSE: para/pre not working after blockquote; PAGE:en.w:Snappy_(software); DATE:2014-04-25
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "<blockquote>a"
		, "</blockquote>"
		, ""
		, "b"
		, ""
		, " c"
		), String_.Concat_lines_nl_skip_last
		( "<blockquote>a"
		, "</blockquote>"
		, ""
		, "<p>b"
		, "</p>"
		, ""
		, "<pre>c"
		, "</pre>"
		));
		fxt.Init_para_n_();
	}
	@Test  public void Dangling_multiple() { // PURPOSE: handle multiple dangling; PAGE:en.w:Ring_a_Ring_o'_Roses DATE:2014-06-26
		fxt.Test_parse_page_wiki_str("<blockquote>a<blockquote>b", "<blockquote>a</blockquote><blockquote>b</blockquote>");
	}
}
