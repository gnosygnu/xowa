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
package gplx.xowa.parsers.lnkes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
import gplx.xowa.langs.cases.*;
public class Xop_lnke_wkr_text_tst {
	@Before public void init() {fxt.Reset();} private Xop_fxt fxt = new Xop_fxt();
	@Test  public void Text_obj() {
		fxt.Test_parse_page_wiki("irc://a", fxt.tkn_lnke_(0, 7).Lnke_typ_(Xop_lnke_tkn.Lnke_typ_text).Lnke_rng_(0, 7));
	}
	@Test  public void Text_html() {
		fxt.Test_parse_page_wiki_str("irc://a", "<a href=\"irc://a\" class=\"external text\" rel=\"nofollow\">irc://a</a>");
	}
	@Test  public void Text_after() {
		fxt.Test_parse_page_wiki("irc://a b c", fxt.tkn_lnke_(0, 7).Lnke_rng_(0, 7), fxt.tkn_space_(7, 8), fxt.tkn_txt_(8, 9), fxt.tkn_space_(9, 10), fxt.tkn_txt_(10, 11));
	}
	@Test  public void Text_before_ascii() {	// PURPOSE: free form external urls should not match if preceded by letters; EX:de.w:Sylvie_und_Bruno; DATE:2014-05-11
		fxt.Ctx().Lang().Case_mgr_utf8_();
		String expd_lnke_html = "<a href=\"tel:a\" class=\"external text\" rel=\"nofollow\">tel:a</a>";
		fxt.Test_parse_page_wiki_str("titel:a"		, "titel:a");
		fxt.Test_parse_page_wiki_str(" tel:a"		, " " + expd_lnke_html);
		fxt.Test_parse_page_wiki_str("!tel:a"		, "!" + expd_lnke_html);
		fxt.Test_parse_page_wiki_str("ätel:a"		, "ätel:a");
		fxt.Test_parse_page_wiki_str("€tel:a"		, "€" + expd_lnke_html);
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
		,	"  <li><a href=\"irc://a\" class=\"external text\" rel=\"nofollow\">irc://a</a>"
		,	"  </li>"
		,	"  <li><a href=\"irc://b\" class=\"external text\" rel=\"nofollow\">irc://b</a>"
		,	"  </li>"
		,	"</ul>"
		));
	}
	@Test  public void Defect_reverse_caption_link() { // PURPOSE: bad lnke formatting (caption before link); ] should show up at end, but only [ shows up; PAGE:en.w:Paul Philippoteaux; [caption http://www.americanheritage.com]
		fxt.Test_parse_page_wiki_str("[caption irc://a]", "[caption <a href=\"irc://a\" class=\"external text\" rel=\"nofollow\">irc://a</a>]");
	}
	@Test  public void Lnki() {	// PURPOSE: trailing lnki should not get absorbed into lnke; DATE:2014-07-11
		fxt.Test_parse_page_wiki_str
		( "http://a.org[[B]]"	// NOTE: [[ should create another lnki
		,String_.Concat_lines_nl_skip_last
		( "<a href=\"http://a.org\" class=\"external text\" rel=\"nofollow\">http://a.org</a><a href=\"/wiki/B\">B</a>"
		));
	}
}
