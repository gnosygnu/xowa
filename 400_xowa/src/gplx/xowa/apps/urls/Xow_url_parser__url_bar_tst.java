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
import org.junit.*;
public class Xow_url_parser__url_bar_tst {
	private final    Xow_url_parser_fxt tstr = new Xow_url_parser_fxt();
	@Test  public void Basic() {
		tstr.Exec__parse_from_url_bar("Page_1").Test__to_str("en.wikipedia.org/wiki/Page_1");			// basic
	}
	@Test  public void Lang() {
		tstr.Prep_add_xwiki_to_user("uk", "uk.wikipedia.org");
		tstr.Exec__parse_from_url_bar("uk").Test__to_str("en.wikipedia.org/wiki/Uk");					// lang-like page (uk=Ukraine) should not try to open wiki; DATE:2014-02-07
	}
	@Test  public void Lang_like() {
		tstr.Prep_add_xwiki_to_user("uk", "uk.wikipedia.org", "http://~{1}.wikipedia.org");			// NOTE: fmt needed for Type_is_lang
		tstr.Exec__parse_from_url_bar("uk/A").Test__to_str("en.wikipedia.org/wiki/Uk/A");				// uk/A should not try be interpreted as wiki="uk" page="A"; DATE:2014-04-26
	}
	@Test  public void Macro() {
		tstr.Prep_add_xwiki_to_user("fr.wikisource.org");
		tstr.Exec__parse_from_url_bar("fr.s:Auteur:Shakespeare").Test__to_str("fr.wikisource.org/wiki/Auteur:Shakespeare");	// url_macros
	}
	@Test  public void Main_page__home() {
		tstr.Exec__parse_from_url_bar("home").Test__to_str("en.wikipedia.org/wiki/Home");				// home should go to current wiki's home; DATE:2014-02-09
		tstr.Exec__parse_from_url_bar("home/wiki/Main_Page").Test__to_str("home/wiki/Main_Page");		// home Main_Page should go to home; DATE:2014-02-09
	}
	@Test  public void Main_page__zhw() {
		Xowe_wiki zh_wiki = tstr.Prep_create_wiki("zh.wikipedia.org");
		zh_wiki.Props().Main_page_(Bry_.new_a7("Zh_Main_Page"));
		tstr.Exec__parse_from_url_bar("zh.w:Main_Page")	.Test__page_is_main_n().Test__to_str("zh.wikipedia.org/wiki/Main_Page");
		tstr.Exec__parse_from_url_bar("zh.w:")			.Test__page_is_main_y().Test__to_str("zh.wikipedia.org/wiki/Zh_Main_Page");
		tstr.Exec__parse_from_url_bar("en.w:")			.Test__page_is_main_y().Test__to_str("en.wikipedia.org/wiki/Main_Page");		// old bug: still stuck at zh main page due to reused objects
	}
	@Test  public void Mobile() {	// PURPOSE: handle mobile links; DATE:2014-05-03
		tstr.Exec__parse_from_url_bar("en.m.wikipedia.org/wiki/A"	).Test__to_str("en.wikipedia.org/wiki/A");		// basic
		tstr.Exec__parse_from_url_bar("en.M.wikipedia.org/wiki/A"	).Test__to_str("en.wikipedia.org/wiki/A");		// upper
		tstr.Exec__parse_from_url_bar("A"							).Test__to_str("en.wikipedia.org/wiki/A");		// bounds-check: 0
		tstr.Exec__parse_from_url_bar("A."						).Test__to_str("en.wikipedia.org/wiki/A.");		// bounds-check: 1
		tstr.Exec__parse_from_url_bar("A.b"						).Test__to_str("en.wikipedia.org/wiki/A.b");		// bounds-check: 2
		tstr.Exec__parse_from_url_bar("A.b.m."					).Test__to_str("en.wikipedia.org/wiki/A.b.m.");	// false-match
		tstr.Exec__parse_from_url_bar("en.x.wikipedia.org/wiki/A"	).Test__to_str("en.wikipedia.org/wiki/En.x.wikipedia.org/wiki/A");	// fail
	}
	@Test  public void Missing_page() {
		tstr.Exec__parse_from_url_bar("http://a.org").Test__is_null();	// unknown wiki; return null;
		tstr.Exec__parse_from_url_bar("http://en.wikipedia.org").Test__to_str("en.wikipedia.org/wiki/Main_Page");	// known wiki; return Main_Page
	}
	@Test  public void Invalid_names() {
		tstr.Exec__parse_from_url_bar("http://a/b/c").Test__is_null();				// unknown url
		tstr.Exec__parse_from_url_bar("war").Test__to_str("en.wikipedia.org/wiki/War");	// word looks like lang, but is actually page; default to current
	}
	@Test  public void Proper_case() {
		tstr.Exec__parse_from_url_bar("a"							).Test__to_str("en.wikipedia.org/wiki/A");		// "a" -> "A" x> "a"
	}
}
