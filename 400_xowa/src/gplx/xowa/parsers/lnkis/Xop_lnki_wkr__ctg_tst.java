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
import gplx.xowa.langs.cases.*;
public class Xop_lnki_wkr__ctg_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_y_();} private final    Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test   public void Pre() { // PURPOSE: Category should trim preceding nl; EX:w:Mount Kailash
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "a"
			, " [[Category:b]]"
			, "c"
			), String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "c"
			, "</p>"
			, ""
			));
	}
	@Test   public void Ws() {	// FUTURE: needs more para rework; conflicts with Li() test; WHEN: when issue is found
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "a"
			, "x [[Category:b]]"
			, "c"
			), String_.Concat_lines_nl_skip_last
			( "<p>a"
			, "x "
			, "c"
			, "</p>"
			, ""
			));
	}
	@Test   public void Li() {	// PURPOSE: Category strips all preceding ws; PAGE:en.w:NYC (in external links)
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			( "*a"
			, "*b"
			, " [[Category:c]]"
			, "*d"
			), String_.Concat_lines_nl_skip_last
			( "<ul>"
			, "  <li>a"
			, "  </li>"
			, "  <li>b"
			, "  </li>"
			, "  <li>d"
			, "  </li>"
			, "</ul>"
			));
	}
	@Test   public void Li_w_lnke() {	// PURPOSE: [[Category]] was being absorbed into lnke; PAGE:de.w:ISO/IEC/IEEE_29119_Software_Testing DATE:2014-07-11
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "* http://a.org"
		, "[[Category:B]]" // category should not show below
		), String_.Concat_lines_nl_skip_last
		( "<ul>"
		, "  <li> <a href=\"http://a.org\" rel=\"nofollow\" class=\"external free\">http://a.org</a>"
		, "  </li>"
		, "</ul>"
		, ""
		));
	}
	@Test   public void Merge_li() {	// PURPOSE: trim ws preceding [[Category:; de.d:plant; DATE:2014-03-27
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
			( "*a"
			, ""
			, " [[Category:B]] c" 
			), String_.Concat_lines_nl_skip_last
			( "<ul>"
			, "  <li>a c"
			, "  </li>"
			, "</ul>"
			, ""
			));
	}
	@Test   public void Merge_pre() {	// PURPOSE: leading spaces / nls should be removed from normal Category, else false pre's or excessive line breaks
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl
		(	" [[Category:A]]"	// removes \s
		,	" [[Category:B]]"	// removes \n\s
		),	String_.Concat_lines_nl
		(	"<p>"
		,	"</p>"
		));
	}
	@Test   public void Literal() {	// PURPOSE: do not trim ws if literal Category; EX:fr.wikiquote.org/wiki/Accueil; REF: https://sourceforge.net/p/xowa/tickets/167/; DATE:2013-07-10
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl
		(	"[[:Category:A]]"
		,	"[[:Category:B]]"
		),	String_.Concat_lines_nl
		(	"<p><a href=\"/wiki/Category:A\">Category:A</a>"	// NOTE: technically WP converts to "</a> <a>" not "</a>\n<a>" (via HtmlTidy?)
		,	"<a href=\"/wiki/Category:B\">Category:B</a>"
		,	"</p>"
		));
	}
	@Test   public void Hdr_w_nl() {	// PURPOSE: hdr code broken by Category; DATE:2014-04-17
		fxt.Test_parse_page_all_str("==a==\n[[Category:C]]"
		, String_.Concat_lines_nl_skip_last
		(	"<h2>a</h2>"
		,	""
		));
	}
	@Test   public void Hdr_only() {	// PURPOSE: check that == is hdr; EX:ar.d:جَبَّارَة; DATE:2014-04-17
		fxt.Test_parse_page_wiki_str("==a==[[Category:C]]"
		, String_.Concat_lines_nl_skip_last
		(	"<h2>a</h2>"
		,	""
		));
	}
	@Test   public void Hdr_ignore() {	// PURPOSE: check that hdr is ignored if next char is not nl; DATE:2014-04-17
		fxt.Test_parse_page_wiki_str("==a==[[Category:C]]b"
		, String_.Concat_lines_nl_skip_last
		(	"==a==b"
		, ""
		));
	}
}

