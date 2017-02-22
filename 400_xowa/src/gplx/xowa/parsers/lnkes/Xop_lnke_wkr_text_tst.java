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
package gplx.xowa.parsers.lnkes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*; import gplx.xowa.langs.cases.*;
public class Xop_lnke_wkr_text_tst {
	@Before public void init() {fxt.Reset();} private final Xop_fxt fxt = new Xop_fxt();
	@Test  public void Text_obj() {
		fxt.Test_parse_page_wiki("irc://a", fxt.tkn_lnke_(0, 7).Lnke_typ_(Xop_lnke_tkn.Lnke_typ_text).Lnke_rng_(0, 7));
	}
	@Test  public void Text_html() {
		fxt.Test_parse_page_wiki_str("irc://a", "<a href=\"irc://a\" rel=\"nofollow\" class=\"external free\">irc://a</a>");
	}
	@Test  public void Text_after() {
		fxt.Test_parse_page_wiki("irc://a b c", fxt.tkn_lnke_(0, 7).Lnke_rng_(0, 7), fxt.tkn_space_(7, 8), fxt.tkn_txt_(8, 9), fxt.tkn_space_(9, 10), fxt.tkn_txt_(10, 11));
	}
	@Test  public void Text_before_ascii() {	// PURPOSE: free form external urls should not match if preceded by letters; EX:de.w:Sylvie_und_Bruno; DATE:2014-05-11
		fxt.Ctx().Lang().Case_mgr_u8_();
		String expd_lnke_html = "<a href=\"tel:a\" rel=\"nofollow\" class=\"external free\">tel:a</a>";
		fxt.Test_parse_page_wiki_str("titel:a"		, "titel:a");
		fxt.Test_parse_page_wiki_str(" tel:a"		, " " + expd_lnke_html);
		fxt.Test_parse_page_wiki_str("!tel:a"		, "!" + expd_lnke_html);
		fxt.Test_parse_page_wiki_str("ätel:a"		, "ätel:a");
		fxt.Test_parse_page_wiki_str("€tel:a"		, "€" + expd_lnke_html);
	}
	@Test  public void Invalid_lnki_and_list_dt_dd() {	// PURPOSE: invalid lnke should still allow processing of ":" in list <dd>; PAGE:de.w:Mord_(Deutschland)#Besonders_verwerfliche_Begehungsweise DATE:2015-01-08
		fxt.Test_parse_page_wiki_str("; atel: b"		, String_.Concat_lines_nl_skip_last
		( "<dl>"
		, "  <dt> atel"
		, "  </dt>"
		, "  <dd> b"
		, "  </dd>"
		, "</dl>"
		));
	}
	@Test  public void Xnde() {// NOTE: compare to Brace_lt
		fxt.Test_parse_page_wiki("<span>irc://a</span>"
		,	fxt.tkn_xnde_(0, 20).Subs_
		(		fxt.tkn_lnke_(6, 13)
		)
		);
	}
	@Test  public void List() {
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		(	"*irc://a"
		,	"*irc://b"	
		),String_.Concat_lines_nl_skip_last
		(	"<ul>"
		,	"  <li><a href=\"irc://a\" rel=\"nofollow\" class=\"external free\">irc://a</a>"
		,	"  </li>"
		,	"  <li><a href=\"irc://b\" rel=\"nofollow\" class=\"external free\">irc://b</a>"
		,	"  </li>"
		,	"</ul>"
		));
	}
	@Test  public void Defect_reverse_caption_link() { // PURPOSE: bad lnke formatting (caption before link); ] should show up at end, but only [ shows up; PAGE:en.w:Paul Philippoteaux; [caption http://www.americanheritage.com]
		fxt.Test_parse_page_wiki_str("[caption irc://a]", "[caption <a href=\"irc://a\" rel=\"nofollow\" class=\"external free\">irc://a</a>]");
	}
	@Test  public void Lnki() {	// PURPOSE: trailing lnki should not get absorbed into lnke; DATE:2014-07-11
		fxt.Test_parse_page_wiki_str
		( "http://a.org[[B]]"	// NOTE: [[ should create another lnki
		,String_.Concat_lines_nl_skip_last
		( "<a href=\"http://a.org\" rel=\"nofollow\" class=\"external free\">http://a.org</a><a href=\"/wiki/B\">B</a>"
		));
	}
	@Test   public void Protocol_only() {	// PURPOSE: protocol only should return text; DATE:2014-10-09
		fxt.Test_parse_page_wiki_str("http://"		, "http://");
		fxt.Test_parse_page_wiki_str("http:"		, "http:");
		fxt.Test_parse_page_wiki_str("[http://]"	, "[http://]");
		fxt.Test_parse_page_wiki_str("[http:]"		, "[http:]");
	}
	@Test   public void Ignore_punctuation_at_end() {	// PURPOSE: ignore "," and related punctuation at end; DATE:2014-10-09
		fxt.Test_parse_page_wiki_str("http://a.org,"	, "<a href=\"http://a.org\" rel=\"nofollow\" class=\"external free\">http://a.org</a>,");			// basic
		fxt.Test_parse_page_wiki_str("http://a.org,,"	, "<a href=\"http://a.org\" rel=\"nofollow\" class=\"external free\">http://a.org</a>,,");			// many
		fxt.Test_parse_page_wiki_str("http://a.org/b,c"	, "<a href=\"http://a.org/b,c\" rel=\"nofollow\" class=\"external free\">http://a.org/b,c</a>");	// do not ignore if in middle
		fxt.Test_parse_page_wiki_str("http://a.org:"	, "<a href=\"http://a.org\" rel=\"nofollow\" class=\"external free\">http://a.org</a>:");			// colon at end; compare to "http:"
	}
	@Test   public void Ignore_punctuation_at_end__paren_end() {	// PURPOSE: end parent has special rules; DATE:2014-10-10
		fxt.Test_parse_page_wiki_str("(http://a.org)"	, "(<a href=\"http://a.org\" rel=\"nofollow\" class=\"external free\">http://a.org</a>)");			// trim=y
		fxt.Test_parse_page_wiki_str("http://a.org/b(c)", "<a href=\"http://a.org/b(c)\" rel=\"nofollow\" class=\"external free\">http://a.org/b(c)</a>");	// trim=n
	}
	@Test   public void Sym_quote() {	// PURPOSE: quote should interrupt lnke; DATE:2014-10-10
		fxt.Test_parse_page_wiki_str("http://a.org/b\"c", "<a href=\"http://a.org/b\" rel=\"nofollow\" class=\"external free\">http://a.org/b</a>&quot;c");
	}
}
