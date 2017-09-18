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
public class Xop_xnde_wkr__text_block_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Source_wikitext() {	// PURPOSE.ASSERT: wikitext should be rendered literally; DATE:2014-03-11
		fxt.Test_parse_page_wiki_str("<source>''a''</source>", "<div class=\"mw-highlight\"><pre style=\"overflow:auto\">''a''</pre></div>");
	}
	@Test  public void Source_nowiki() {	// PURPOSE.ASSERT: onlyinclude should be rendered literally; DATE:2014-03-11
		fxt.Test_parse_page_wiki_str("<source><onlyinclude>a</onlyinclude></source>", "<div class=\"mw-highlight\"><pre style=\"overflow:auto\">&lt;onlyinclude&gt;a&lt;/onlyinclude&gt;</pre></div>");
	}
	@Test  public void Source_escape() {
		fxt.Test_parse_page_wiki_str("<source><b></source>", "<div class=\"mw-highlight\"><pre style=\"overflow:auto\">&lt;b&gt;</pre></div>");
	}
	@Test  public void Source_escape_amp() {	// PURPOSE: &lt; should be rendered as &amp;lt; PAGE:uk.b:HTML; DATE:2014-03-11
		fxt.Test_parse_page_wiki_str("<source>&lt;</source>", "<div class=\"mw-highlight\"><pre style=\"overflow:auto\">&amp;lt;</pre></div>");
	}
	@Test  public void Source_pre() {	// PURPOSE: handle pre; PAGE:en.w:Comment_(computer_programming); DATE:2014-06-23
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "  <source>"
		, "  a"
		, "  </source>"
		), String_.Concat_lines_nl_skip_last
		( "  <div class=\"mw-highlight\"><pre style=\"overflow:auto\">"
		, "  a"
		, "</pre></div>"
		));
		fxt.Init_para_n_();
	}
	@Test  public void Code_dangling() {	// PAGE:en.w:HTML; <code>&lt;i&gt;<code> and <code>&lt;center&gt;<code> tags. There are
		fxt.Test_parse_page_wiki_str("a<code>b<code>c", "a<code>b</code>c");
	}
	@Test  public void Code_do_not_escape() { // PURPOSE: <code> was mistakenly marked as escape, causing inner tags to be rendered incorrectly; PAGE:en.w:UTF8
		fxt.Test_parse_page_all_str
		( "<code><span style=\"color:red;\">0100100</span></code>"
		, "<code><span style=\"color:red;\">0100100</span></code>"
		);
	}
	@Test  public void Pre_and_html_chars() {// PURPOSE: <pre> should handle '"<> according to context
		fxt.Test_parse_page_all_str("<pre>a&#09;b</pre>"			, "<pre>a&#09;b</pre>");					// known ncr/dec; embed and depend on browser transforming; EX: de.w:Wikipedia:Technik/Skin/Werkstatt
		fxt.Test_parse_page_all_str("<pre>a&#9999999999;b</pre>"	, "<pre>a&amp;#9999999999;b</pre>");		// unknown ncr/dec; escape & (since browser cannot render);
		fxt.Test_parse_page_all_str("<pre>a&#af ;b</pre>"			, "<pre>a&amp;#af ;b</pre>");				// unknown ncr/dec 2
		fxt.Test_parse_page_all_str("<pre>a&#x9;b</pre>"			, "<pre>a&#x9;b</pre>");					// known ncr/hex
		fxt.Test_parse_page_all_str("<pre>a&apos;b</pre>"			, "<pre>a&apos;b</pre>");					// known name; embed
		fxt.Test_parse_page_all_str("<pre>a&apox;b</pre>"			, "<pre>a&amp;apox;b</pre>");				// unknown name; escape
		fxt.Test_parse_page_all_str("<pre>&\"<></pre>"				, "<pre>&amp;&quot;&lt;&gt;</pre>");		// no ncr or name; escape; needed for <pre><img ...></pre>; PAGE:en.w:Alt attribute
	}
	@Test  public void Pre_and_space() {// PURPOSE: make sure pre does not careate <p></p> around it; also, make sure " a" is preserved; DATE:2014-02-20
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "<pre>"
		, " a"
		, "</pre>"
		), String_.Concat_lines_nl_skip_last
		( "<pre>"
		, " a"
		, "</pre>"
		));
		fxt.Init_para_n_();
	}
}
