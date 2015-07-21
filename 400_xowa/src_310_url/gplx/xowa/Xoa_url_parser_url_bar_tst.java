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
	private final Xoa_url_parser_fxt fxt = new Xoa_url_parser_fxt();
	@Test  public void Basic() {
		fxt.Run_parse_from_url_bar("Page_1").Chk_to_str("en.wikipedia.org/wiki/Page_1");				// basic
	}
	@Test  public void Lang() {
		fxt.Prep_add_xwiki_to_user("uk", "uk.wikipedia.org");
		fxt.Run_parse_from_url_bar("uk").Chk_to_str("en.wikipedia.org/wiki/uk");					// lang-like page (uk=Ukraine) should not try to open wiki; DATE:2014-02-07
	}
	@Test  public void Lang_like() {
		fxt.Prep_add_xwiki_to_user("uk", "uk.wikipedia.org", "http://~{1}.wikipedia.org");			// NOTE: fmt needed for Type_is_lang
		fxt.Run_parse_from_url_bar("uk/A").Chk_to_str("en.wikipedia.org/wiki/uk/A");				// uk/A should not try be interpreted as wiki="uk" page="A"; DATE:2014-04-26
	}
	@Test  public void Macro() {
		fxt.Prep_add_xwiki_to_user("fr.wikisource.org");
		fxt.Run_parse_from_url_bar("fr.s:Auteur:Shakespeare").Chk_to_str("fr.wikisource.org/wiki/Auteur:Shakespeare");	// url_macros
	}
	@Test  public void Main_page__home() {
		fxt.Run_parse_from_url_bar("home").Chk_to_str("en.wikipedia.org/wiki/home");				// home should go to current wiki's home; DATE:2014-02-09
		fxt.Run_parse_from_url_bar("home/wiki/Main_Page").Chk_to_str("home/wiki/Main_Page");		// home Main_Page should go to home; DATE:2014-02-09
	}
	@Test  public void Main_page__zhw() {
		Xowe_wiki zh_wiki = fxt.Prep_create_wiki("zh.wikipedia.org");
		gplx.xowa.wikis.Xoa_wiki_regy.Make_wiki_dir(zh_wiki.App(), "zh.wikipedia.org");	// HACK: needed for to_url_bar
		zh_wiki.Props().Main_page_(Bry_.new_a7("Zh_Main_Page"));
		fxt.Run_parse_from_url_bar("zh.w:").Chk_to_str("zh.wikipedia.org/wiki/Zh_Main_Page");
		fxt.Run_parse_from_url_bar("zh.w:Main_Page").Chk_to_str("zh.wikipedia.org/wiki/Main_Page");
	}
	@Test  public void Mobile() {	// PURPOSE: handle mobile links; DATE:2014-05-03
		fxt.Run_parse_from_url_bar("en.m.wikipedia.org/wiki/A"	).Chk_to_str("en.wikipedia.org/wiki/A");		// basic
		fxt.Run_parse_from_url_bar("en.M.wikipedia.org/wiki/A"	).Chk_to_str("en.wikipedia.org/wiki/A");		// upper
		fxt.Run_parse_from_url_bar("A"							).Chk_to_str("en.wikipedia.org/wiki/A");		// bounds-check: 0
		fxt.Run_parse_from_url_bar("A."							).Chk_to_str("en.wikipedia.org/wiki/A.");		// bounds-check: 1
		fxt.Run_parse_from_url_bar("A.b"						).Chk_to_str("en.wikipedia.org/wiki/A.b");		// bounds-check: 2
		fxt.Run_parse_from_url_bar("A.b.m."						).Chk_to_str("en.wikipedia.org/wiki/A.b.m.");	// false-match
		fxt.Run_parse_from_url_bar("en.x.wikipedia.org/wiki/A"	).Chk_to_str("en.wikipedia.org/wiki/en.x.wikipedia.org/A");	// fail
	}
}
