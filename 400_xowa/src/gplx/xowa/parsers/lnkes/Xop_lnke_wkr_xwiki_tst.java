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
public class Xop_lnke_wkr_xwiki_tst {
	@Before public void init() {fxt.Reset();} private Xop_fxt fxt = new Xop_fxt();
	@Test  public void Xwiki() {
		fxt.App().Usere().Wiki().Xwiki_mgr().Add_by_atrs(Bry_.new_a7("en.wikipedia.org"), Bry_.new_a7("en.wikipedia.org"));
		fxt.Test_parse_page_wiki_str("[http://en.wikipedia.org/wiki/A a]", "<a href=\"/site/en.wikipedia.org/wiki/A\">a</a>");
	}
	@Test  public void Xwiki_relative() {
		fxt.App().Usere().Wiki().Xwiki_mgr().Add_by_atrs(Bry_.new_a7("en.wikipedia.org"), Bry_.new_a7("en.wikipedia.org"));
		fxt.Test_parse_page_wiki_str("[//en.wikipedia.org/ a]", "<a href=\"/site/en.wikipedia.org/wiki/\">a</a>");
	}
	@Test  public void Xwiki_qarg() {// DATE:2013-02-02
		fxt.Init_xwiki_add_user_("en.wikipedia.org");
		fxt.Test_parse_page_wiki_str("http://en.wikipedia.org/wiki/Special:Allpages?from=Earth", "<a href=\"/site/en.wikipedia.org/wiki/Special:Allpages?from=Earth\">http://en.wikipedia.org/wiki/Special:Allpages?from=Earth</a>");
	}
	@Test  public void Lang_prefix() {
		fxt.App().Usere().Wiki().Xwiki_mgr().Add_by_atrs(Bry_.new_a7("en.wikipedia.org"), Bry_.new_a7("en.wikipedia.org"));
		fxt.Wiki().Xwiki_mgr().Add_by_atrs(Bry_.new_a7("fr"), Bry_.new_a7("fr.wikipedia.org"));
		fxt.Test_parse_page_wiki_str("[http://en.wikipedia.org/wiki/fr:A a]", "<a href=\"/site/fr.wikipedia.org/wiki/A\">a</a>");
	}
	@Test  public void Xwiki_query_arg() {
		fxt.App().Usere().Wiki().Xwiki_mgr().Add_by_atrs(Bry_.new_a7("en.wikipedia.org"), Bry_.new_a7("en.wikipedia.org"));
		fxt.Test_parse_page_wiki_str("[http://en.wikipedia.org/wiki/A?action=edit a]", "<a href=\"/site/en.wikipedia.org/wiki/A?action=edit\">a</a>");
	}
	@Test  public void Ignore_proto() {	// PURPOSE: handle other protocols; PAGE:uk.w:Маскалі; DATE:2015-07-28
		fxt.Test_parse_page_wiki_str("[mailto:a b]", "<a href=\"mailto:a\" class=\"external text\" rel=\"nofollow\">b</a>");// should be /w/, not /en.wikipedia.org
	}
	@Test  public void Ignore_alias() {	// PURPOSE: fictitious example to make sure aliases are not subbed for domains; DATE:2015-07-28
		fxt.Init_xwiki_add_user_("w", "en.wikipedia.org");
		fxt.Test_parse_page_wiki_str("[https://w/b c]", "<a href=\"https://w/b\" class=\"external text\" rel=\"nofollow\">c</a>");// should be /w/, not /en.wikipedia.org
	}
	@Test  public void Xwiki__qargs() {	// PURPOSE: fix null ref error; PAGE:en.w:Wikipedia:Template_standardisation/demometa DATE:2015-08-02
		fxt.Init_xwiki_add_user_("en.wikipedia.org");
		fxt.Test_parse_page_wiki_str("[http://en.wikipedia.org/w/index.php?action&#61;edit&preload&#61;Template:Afd2+starter&editintro&#61;Template:Afd3+starter&title&#61;Wikipedia:Articles+for+deletion/Template_standardisation/demometa]"
			, "<a href=\"/site/en.wikipedia.org/wiki/index.php?action=&#61;edit=&preload=&#61;Template:Afd2+starter=&editintro=&#61;Template:Afd3+starter=&title=&=\">[1]</a>");
	}
}
