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
package gplx.xowa.urls; import gplx.*; import gplx.xowa.*;
import org.junit.*; import gplx.xowa.nss.*;
public class Xoa_url_parser__xwiki_tst {
	private final Xoa_url_parser_fxt tstr = new Xoa_url_parser_fxt();
	@Test  public void Commons() {	// PURPOSE: "C" was being picked up as an xwiki to commons; PAGE:no.b:C/Variabler; DATE:2014-10-14
		tstr.Prep_add_xwiki_to_user("c", "commons.wikimedia.org");		// add alias of "c"
		tstr.Run_parse("C/D").Chk_tid(Xoa_url_.Tid_page).Chk_wiki("en.wikipedia.org").Chk_page("C/D");	// should use current wiki (enwiki), not xwiki to commons; also, page should be "C/D", not "D"
	}
	@Test  public void Parse_lang() {
		tstr.Prep_add_xwiki_to_wiki("fr", "fr.wikipedia.org", "http://fr.wikipedia.org/~{0}");
		tstr.Run_parse("http://en.wikipedia.org/wiki/fr:A").Chk_tid(Xoa_url_.Tid_page).Chk_wiki("fr.wikipedia.org").Chk_page("A");
	}
	@Test  public void Alias_wiki() {
		tstr.Prep_add_xwiki_to_wiki("s", "en.wikisource.org");
		tstr.Run_parse("s:A/b/c").Chk_wiki("en.wikisource.org").Chk_page("A/b/c");
	}
	@Test  public void Xwiki_no_segs() {	// PURPOSE: handle xwiki without full url; EX: "commons:Commons:Media_of_the_day"; DATE:2014-02-19
		tstr.Prep_add_xwiki_to_wiki("s", "en.wikisource.org");
		tstr.Run_parse("s:Project:A").Chk_wiki("en.wikisource.org").Chk_page("Project:A");
	}
	@Test  public void Domain_only() {
		tstr.Prep_add_xwiki_to_user("fr.wikipedia.org");
		tstr.Run_parse("fr.wikipedia.org").Chk_wiki("fr.wikipedia.org").Chk_page("");
	}
	@Test  public void Domain_and_wiki() {
		tstr.Prep_add_xwiki_to_user("fr.wikipedia.org");
		tstr.Run_parse("fr.wikipedia.org/wiki").Chk_wiki("fr.wikipedia.org").Chk_page("");
	}
	@Test  public void Domain_and_wiki_w_http() {
		tstr.Prep_add_xwiki_to_user("fr.wikipedia.org");
		tstr.Run_parse("http://fr.wikipedia.org/wiki").Chk_wiki("fr.wikipedia.org").Chk_page("");
	}		
	@Test  public void Namespace_in_different_wiki() {	// PURPOSE.fix: namespaced titles would default to default_wiki instead of current_wiki
		Xowe_wiki en_s = tstr.Prep_create_wiki("en.wikisource.org");
		tstr.Run_parse(en_s, "Category:A").Chk_wiki("en.wikisource.org").Chk_page("Category:A");
	}		
	@Test  public void Case_sensitive() {
		// tstr.Run_parse("en.wikipedia.org/wiki/a").Chk_wiki("en.wikipedia.org").Chk_page("A");
		Xowe_wiki en_d = tstr.Prep_create_wiki("en.wiktionary.org");
		Xow_ns_mgr ns_mgr = en_d.Ns_mgr();

		ns_mgr.Ns_main().Case_match_(Xow_ns_case_.Id_all);
		tstr.Run_parse("en.wiktionary.org/wiki/a").Chk_wiki("en.wiktionary.org").Chk_page("a");

		ns_mgr.Ns_category().Case_match_(Xow_ns_case_.Id_all);
		tstr.Run_parse("en.wiktionary.org/wiki/Category:a").Chk_wiki("en.wiktionary.org").Chk_page("Category:a");

		tstr.Run_parse("en.wiktionary.org/wiki/A/B/C").Chk_page("A/B/C");
	}
	@Test  public void Xwiki__to_enwiki() {	// PURPOSE: handle alias of "wikipedia" and sv.wikipedia.org/wiki/Wikipedia:Main_Page; DATE:2015-07-31
		Xowe_wiki xwiki = tstr.Prep_create_wiki("sv.wikipedia.org");
		tstr.Prep_xwiki(xwiki, "wikipedia", "en.wikipedia.org", null);
		tstr.Prep_get_ns_mgr_from_meta("sv.wikipedia.org").Add_new(Xow_ns_.Id_project, "Wikipedia");
		tstr.Run_parse(xwiki, "sv.wikipedia.org/wiki/wikipedia:X").Chk_wiki("sv.wikipedia.org").Chk_page("wikipedia:X");
		tstr.Run_parse(xwiki, "sv.wikipedia.org/wiki/Wikipedia:X").Chk_wiki("sv.wikipedia.org").Chk_page("Wikipedia:X");
	}
	@Test  public void Xwiki__to_ns() {	// PURPOSE: handle alias of "wikipedia" in current, but no "Wikipedia" ns in other wiki; PAGE:pt.w:Wikipedia:P�gina_de_testes DATE:2015-09-17
		tstr.Prep_create_wiki("pt.wikipedia.org");
		tstr.Prep_get_ns_mgr_from_meta("pt.wikipedia.org").Add_new(Xow_ns_.Id_project, "Project");	// clear ns_mgr and add only "Project" ns, not "Wikipedia" ns
		tstr.Prep_xwiki(tstr.Wiki(), "wikipedia", "en.wikipedia.org", null);	// add alias of "wikipedia" in current wiki
		tstr.Run_parse(tstr.Wiki(), "pt.wikipedia.org/wiki/Wikipedia:X").Chk_wiki("pt.wikipedia.org").Chk_page("Wikipedia:X");	// should get "pt.wikipedia.org", not "en.wikipedia.org" (through alias)
	}
}
