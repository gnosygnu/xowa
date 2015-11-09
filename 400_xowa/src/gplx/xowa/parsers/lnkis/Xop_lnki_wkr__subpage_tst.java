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
package gplx.xowa.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*; import gplx.xowa.wikis.nss.*;
public class Xop_lnki_wkr__subpage_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_n_();} private Xop_fxt fxt = new Xop_fxt();
	@Test  public void Disabled() {	// PURPOSE: slash being interpreted as subpage; PAGE:en.w:[[/dev/null]]
		fxt.Wiki().Ns_mgr().Ids_get_or_null(Xow_ns_.Tid__main).Subpages_enabled_(false);
		fxt.Test_parse_page_all_str("[[/dev/null]]", "<a href=\"/wiki//dev/null\">/dev/null</a>");
		fxt.Wiki().Ns_mgr().Ids_get_or_null(Xow_ns_.Tid__main).Subpages_enabled_(true);
	}
	@Test  public void False_match() {// PAGE:en.w:en.wiktionary.org/wiki/Wiktionary:Requests for cleanup/archive/2006
		fxt.Test_parse_page_wiki_str
		( "[[.../compare ...]]"
		, "<a href=\"/wiki/.../compare_...\">.../compare ...</a>"
		);
	}
	@Test  public void Owner() {	// PURPOSE: ../c does "A/c", not "c"; DATE:2014-01-02
		fxt.Page_ttl_("A/b");
		fxt.Test_parse_page_wiki_str
		( "[[../c]]"
		, "<a href=\"/wiki/A/c\">A/c</a>"
		);
	}
	@Test  public void Owner_w_slash() {	// PURPOSE: ../c/ does "c", not "A/c"; DATE:2014-01-02
		fxt.Page_ttl_("A/b");
		fxt.Test_parse_page_wiki_str
		( "[[../c/]]"
		, "<a href=\"/wiki/A/c\">c</a>"
		);
	}
	@Test  public void Slash() {	// PURPOSE: /B should show /B, not A/B; DATE:2014-01-02
		fxt.Page_ttl_("A");
		fxt.Test_parse_page_wiki_str
		( "[[/B]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/wiki/A/B\">/B</a>"
		));
	}
	@Test  public void Slash_w_slash() {	// PURPOSE: /B/ should show B, not /B; DATE:2014-01-02
		fxt.Page_ttl_("A");
		fxt.Test_parse_page_wiki_str
		( "[[/B/]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/wiki/A/B\">B</a>"
		));
	}
	@Test  public void Leaf_w_ncr() {	// PURPOSE: /B&#x63; should not encode &#x63; PAGE:en.s:The_English_Constitution_(1894) DATE:2014-09-07
		fxt.Page_ttl_("A");
		fxt.Test_parse_page_wiki_str
		( "[[/B&#x63;|B]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/wiki/A/Bc\">B</a>"
		));
	}
}
