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
package gplx.xowa.apps.urls; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import org.junit.*; import gplx.xowa.wikis.nss.*;
public class Xow_url_parser__xwiki_tst {
	private final    Xow_url_parser_fxt tstr = new Xow_url_parser_fxt();
	@Test  public void Commons() {	// PURPOSE: "C" was being picked up as an xwiki to commons; PAGE:no.b:C/Variabler; DATE:2014-10-14
		tstr.Prep_add_xwiki_to_user("c", "commons.wikimedia.org");		// add alias of "c"
		tstr.Exec__parse("C/D").Test__tid(Xoa_url_.Tid_page).Test__wiki("en.wikipedia.org").Test__page("C/D");	// should use current wiki (enwiki), not xwiki to commons; also, page should be "C/D", not "D"
	}
	@Test  public void Parse_lang() {
		tstr.Prep_add_xwiki_to_wiki("fr", "fr.wikipedia.org", "http://fr.wikipedia.org/~{0}");
		tstr.Exec__parse("http://en.wikipedia.org/wiki/fr:A").Test__tid(Xoa_url_.Tid_page).Test__wiki("fr.wikipedia.org").Test__page("A");
	}
	@Test  public void Alias_wiki() {
		tstr.Prep_add_xwiki_to_wiki("s", "en.wikisource.org");
		tstr.Exec__parse("s:A/b/c").Test__wiki("en.wikisource.org").Test__page("A/b/c");
	}
	@Test  public void Xwiki_no_segs() {	// PURPOSE: handle xwiki without full url; EX: "commons:Commons:Media_of_the_day"; DATE:2014-02-19
		tstr.Prep_add_xwiki_to_wiki("s", "en.wikisource.org");
		tstr.Exec__parse("s:Project:A").Test__wiki("en.wikisource.org").Test__page("Project:A");
	}
	@Test  public void Domain_only() {
		tstr.Prep_add_xwiki_to_user("fr.wikipedia.org");
		tstr.Exec__parse("fr.wikipedia.org").Test__wiki("fr.wikipedia.org").Test__page("");
	}
	@Test  public void Domain_and_wiki() {
		tstr.Prep_add_xwiki_to_user("fr.wikipedia.org");
		tstr.Exec__parse("fr.wikipedia.org/wiki").Test__wiki("fr.wikipedia.org").Test__page("");
	}
	@Test  public void Domain_and_wiki_w_http() {
		tstr.Prep_add_xwiki_to_user("fr.wikipedia.org");
		tstr.Exec__parse("http://fr.wikipedia.org/wiki").Test__wiki("fr.wikipedia.org").Test__page("");
	}		
	@Test  public void Namespace_in_different_wiki() {	// PURPOSE.fix: namespaced titles would default to default_wiki instead of current_wiki
		Xowe_wiki en_s = tstr.Prep_create_wiki("en.wikisource.org");
		tstr.Exec__parse(en_s, "Category:A").Test__wiki("en.wikisource.org").Test__page("Category:A");
	}		
	@Test  public void Case_sensitive() {
		// tstr.Exec__parse("en.wikipedia.org/wiki/a").Test__wiki("en.wikipedia.org").Test__page("A");
		Xowe_wiki en_d = tstr.Prep_create_wiki("en.wiktionary.org");
		Xow_ns_mgr ns_mgr = en_d.Ns_mgr();

		ns_mgr.Ns_main().Case_match_(Xow_ns_case_.Tid__all);
		tstr.Exec__parse("en.wiktionary.org/wiki/a").Test__wiki("en.wiktionary.org").Test__page("a");

		ns_mgr.Ns_category().Case_match_(Xow_ns_case_.Tid__all);
		tstr.Exec__parse("en.wiktionary.org/wiki/Category:a").Test__wiki("en.wiktionary.org").Test__page("Category:a");

		tstr.Exec__parse("en.wiktionary.org/wiki/A/B/C").Test__page("A/B/C");
	}
	@Test  public void Xwiki__to_enwiki() {	// PURPOSE: handle alias of "wikipedia" and sv.wikipedia.org/wiki/Wikipedia:Main_Page; DATE:2015-07-31
		Xowe_wiki xwiki = tstr.Prep_create_wiki("sv.wikipedia.org");
		tstr.Prep_xwiki(xwiki, "wikipedia", "en.wikipedia.org", null);
		tstr.Prep_get_ns_mgr_from_meta("sv.wikipedia.org").Add_new(Xow_ns_.Tid__project, "Wikipedia");
		tstr.Exec__parse(xwiki, "sv.wikipedia.org/wiki/wikipedia:X").Test__wiki("sv.wikipedia.org").Test__page("wikipedia:X");
		tstr.Exec__parse(xwiki, "sv.wikipedia.org/wiki/Wikipedia:X").Test__wiki("sv.wikipedia.org").Test__page("Wikipedia:X");
	}
	@Test  public void Xwiki__to_ns() {	// PURPOSE: handle alias of "wikipedia" in current, but no "Wikipedia" ns in other wiki; PAGE:pt.w:Wikipedia:P�gina_de_testes DATE:2015-09-17
		tstr.Prep_create_wiki("pt.wikipedia.org");
		tstr.Prep_get_ns_mgr_from_meta("pt.wikipedia.org").Add_new(Xow_ns_.Tid__project, "Project");	// clear ns_mgr and add only "Project" ns, not "Wikipedia" ns
		tstr.Prep_xwiki(tstr.Wiki(), "wikipedia", "en.wikipedia.org", null);	// add alias of "wikipedia" in current wiki
		tstr.Exec__parse(tstr.Wiki(), "pt.wikipedia.org/wiki/Wikipedia:X").Test__wiki("pt.wikipedia.org").Test__page("Wikipedia:X");	// should get "pt.wikipedia.org", not "en.wikipedia.org" (through alias)
	}
}
