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
public class Xoa_url_parser_url_bar_tst {
	@Before public void init() {fxt.Reset();} private Xoa_url_parser_chkr fxt = new Xoa_url_parser_chkr();
	@Test  public void Basic() {
		fxt.Test_parse_from_url_bar("Page_1"					, "en.wikipedia.org/wiki/Page_1");				// basic
	}
	@Test  public void Lang() {
		fxt.App().Usere().Wiki().Xwiki_mgr().Add_full("uk", "uk.wikipedia.org");
		fxt.Test_parse_from_url_bar("uk"						, "en.wikipedia.org/wiki/uk");					// lang-like page (uk=Ukraine) should not try to open wiki; DATE:2014-02-07
	}
	@Test  public void Lang_like() {
		fxt.App().Usere().Wiki().Xwiki_mgr().Add_full(Bry_.new_a7("uk"), Bry_.new_a7("uk.wikipedia.org"), Bry_.new_a7("http://~{1}.wikipedia.org"));	// NOTE: fmt needed for Type_is_lang
		fxt.Test_parse_from_url_bar("uk/A"						, "en.wikipedia.org/wiki/uk/A");				// uk/A should not try be interpreted as wiki="uk" page="A"; DATE:2014-04-26
	}
	@Test  public void Macro() {
		fxt.App().Usere().Wiki().Xwiki_mgr().Add_full("fr.wikisource.org", "fr.wikisource.org");
		fxt.Test_parse_from_url_bar("fr.s:Auteur:Shakespeare"	, "fr.wikisource.org/wiki/Auteur:Shakespeare");	// url_macros
	}
	@Test  public void Home() {
		fxt.Test_parse_from_url_bar("home"						, "en.wikipedia.org/wiki/home");				// home should go to current wiki's home; DATE:2014-02-09
		fxt.Test_parse_from_url_bar("home/wiki/Main_Page"		, "home/wiki/Main_Page");						// home Main_Page should go to home; DATE:2014-02-09
	}
	@Test  public void Custom() {
		fxt.App().Usere().Wiki().Xwiki_mgr().Add_full("zh.wikipedia.org", "zh.wikipedia.org");
		gplx.xowa.wikis.Xoa_wiki_regy.Make_wiki_dir(fxt.App(), "zh.wikipedia.org");
		fxt.App().Wiki_mgr().Get_by_key_or_make(Bry_.new_a7("zh.wikipedia.org")).Props().Main_page_(Bry_.new_a7("Zh_Main_Page"));
		fxt.Test_parse_from_url_bar("zh.w:"						, "zh.wikipedia.org/wiki/Zh_Main_Page");
		fxt.Test_parse_from_url_bar("zh.w:Main_Page"			, "zh.wikipedia.org/wiki/Main_Page");
	}
	@Test  public void Mobile() {	// PURPOSE: handle mobile links; DATE:2014-05-03
		fxt.Test_parse_from_url_bar("en.m.wikipedia.org/wiki/A"	, "en.wikipedia.org/wiki/A");		// basic
		fxt.Test_parse_from_url_bar("en.M.wikipedia.org/wiki/A"	, "en.wikipedia.org/wiki/A");		// upper
		fxt.Test_parse_from_url_bar("A"							, "en.wikipedia.org/wiki/A");		// bounds-check: 0
		fxt.Test_parse_from_url_bar("A."						, "en.wikipedia.org/wiki/A.");		// bounds-check: 1
		fxt.Test_parse_from_url_bar("A.b"						, "en.wikipedia.org/wiki/A.b");		// bounds-check: 2
		fxt.Test_parse_from_url_bar("A.b.m."					, "en.wikipedia.org/wiki/A.b.m.");	// false-match
		fxt.Test_parse_from_url_bar("en.x.wikipedia.org/wiki/A"	, "en.wikipedia.org/wiki/en.x.wikipedia.org/A");	// fail
	}
}
