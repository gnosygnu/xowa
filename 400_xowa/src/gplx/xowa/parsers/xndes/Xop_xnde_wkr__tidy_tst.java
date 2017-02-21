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
public class Xop_xnde_wkr__tidy_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Sub_sup_autocorrect() {
		fxt.Test_parse_page_wiki_str("<sub>a</sup>b", "<sub>a</sub>b");
		fxt.Test_parse_page_wiki_str("<sup>a</sub>b", "<sup>a</sup>b");
	}
	@Test  public void Span_font_autocorrect() {	// PURPOSE: force </font> to close <span>; EX:w:Rupee; DATE:2014-04-07
		fxt.Test_parse_page_wiki_str("<span>a</font>b", "<span>a</span>b");
	}
	@Test  public void Move_ws_char() {
		fxt.Test_parse_page_all_str("a<i> b </i>c", "a <i>b</i> c");
	}
	@Test  public void Move_ws_ent() {
		fxt.Test_parse_page_all_str("a<i>&#32;b&#32;</i>c", "a&#32;<i>b</i>&#32;c");
	}
	@Test  public void Ignore_empty_tags() {	// PURPOSE: ignore tag if marked ignore_empty; EX:uk.b:HTML; DATE:2014-03-12
		fxt.Test_parse_page_all_str("a<pre></pre>b", "ab");
	}
//		@Test  public void Escaped_div() {	// NOTE: WP <div><span>a</span></div><span>b</span>; MW: <div><span>a</div>b</span> // REVISIT: 2012-05-11; WP does harder split-span
//			fxt.Init_log_(Xop_xnde_log.Auto_closing_section, Xop_xnde_log.Escaped_xnde).Test_parse_page_wiki("<div><span></div></span>"
//				, fxt.tkn_xnde_(0, 17).Subs_
//				(	fxt.tkn_xnde_(5, 11))
//				, fxt.tkn_ignore_(17, 24)
//				);
//		}
}
