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
public class Xop_lnki_wkr__xwiki_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_n_();} private Xop_fxt fxt = new Xop_fxt();
	@Test  public void Xwiki_file() {	// PURPOSE: if xwiki and File, ignore xwiki (hackish); DATE:2013-12-22
		Reg_xwiki_alias("test", "test.wikimedia.org");													// must register xwiki, else ttl will not parse it
		fxt.Wiki().Cfg_parser().Lnki_cfg().Xwiki_repo_mgr().Add_or_mod(Bry_.new_a7("test"));	// must add to xwiki_repo_mgr
		fxt.Test_parse_page_wiki_str
		( "[[test:File:A.png|12x10px]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xoimg_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
		fxt.Wiki().Cfg_parser_lnki_xwiki_repos_enabled_(false);	// disable for other tests
	}
	@Test  public void Xwiki_anchor() {
		Reg_xwiki_alias("test", "test.wikimedia.org");
		fxt.Test_parse_page_wiki_str
		( "[[test:A#b]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/site/test.wikimedia.org/wiki/A#b\">test:A#b</a>"
		));
	}
	@Test  public void Xwiki_empty() {
		Reg_xwiki_alias("test", "test.wikimedia.org");
		fxt.Test_parse_page_wiki_str
		( "[[test:]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/site/test.wikimedia.org/wiki/\">test:</a>"
		));
	}
	@Test  public void Xwiki_empty_literal() {
		Reg_xwiki_alias("test", "test.wikimedia.org");
		fxt.Test_parse_page_wiki_str
		( "[[:test:]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/site/test.wikimedia.org/wiki/\">test:</a>"
		));
	}
	@Test  public void Xwiki_not_registered() {
		fxt.App().Usere().Wiki().Xwiki_mgr().Clear();
		fxt.Wiki().Xwiki_mgr().Add_by_atrs(Bry_.new_a7("test"), Bry_.new_a7("test.wikimedia.org"));	// register alias only, but not in user_wiki
		fxt.Test_parse_page_wiki_str
		( "[[test:A|A]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"https://test.wikimedia.org/wiki/A\">A</a>"
		));
	}
	@Test  public void Literal_lang() {
		Reg_xwiki_alias("fr", "fr.wikipedia.org");
		fxt.Test_parse_page_wiki_str
		( "[[:fr:A]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/site/fr.wikipedia.org/wiki/A\">A</a>"
		));
		Tfds.Eq(0, fxt.Page().Slink_list().Count());
	}
	@Test  public void Simple_and_english() {	// PURPOSE: s.w xwiki links to en were not working b/c s.w and en had same super lang of English; PAGE:s.q:Anonymous DATE:2014-09-10
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app, "simple.wikipedia.org");
		fxt = new Xop_fxt(app, wiki);						// change fxt to simple.wikipedia.org
		Reg_xwiki_alias("en", "en.wikipedia.org");			// register "en" alias
		fxt.Test_parse_page_wiki_str						// test nothing printed
		( "[[en:A]]"
		, ""
		);
		Tfds.Eq(1, fxt.Page().Slink_list().Count());		// test 1 xwiki lang
	}
	@Test  public void Species_and_commons() {	// PURPOSE: species xwiki links to commons should not put link in wikidata langs; PAGE:species:Scarabaeidae DATE:2014-09-10
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app, "species.wikimedia.org");
		fxt = new Xop_fxt(app, wiki);							// change fxt to species.wikimedia.org
		Reg_xwiki_alias("commons", "commons.wikimedia.org");	// register "en" alias
		fxt.Test_parse_page_wiki_str							// test something printed
		( "[[commons:A]]"
		, "<a href=\"/site/commons.wikimedia.org/wiki/A\">commons:A</a>"
		);
		Tfds.Eq(0, fxt.Page().Slink_list().Count());			// no xwiki langs
	}
	@Test  public void Wiktionary_and_wikipedia() {	// PURPOSE: do not create xwiki links if same lang and differet type; PAGE:s.d:water DATE:2014-09-14
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app, "simple.wiktionary.org");
		fxt = new Xop_fxt(app, wiki);						// change fxt to simple.wiktionary.org
		Reg_xwiki_alias("w", "simple.wikipedia.org");		// register "w" alias
		fxt.Test_parse_page_wiki_str						// test something printed
		( "[[w:A]]"
		, "<a href=\"/site/simple.wikipedia.org/wiki/A\">w:A</a>"
		);
		Tfds.Eq(0, fxt.Page().Slink_list().Count());		// test 0 xwiki lang
	}
	@Test  public void Species_and_wikipedia() {	// PURPOSE: species creates xwiki links to wikipedia; PAGE:species:Puccinia DATE:2014-09-14
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app, "species.wikimedia.org");
		fxt = new Xop_fxt(app, wiki);						// change fxt to species.wikimedia.org
		Reg_xwiki_alias("fr", "fr.wikipedia.org");			// register "fr" alias
		fxt.Test_parse_page_wiki_str						// nothing printed
		( "[[fr:A]]"
		, ""
		);
		Tfds.Eq(1, fxt.Page().Slink_list().Count());		// 1 xwiki lang
	}
	private void Reg_xwiki_alias(String alias, String domain) {
		Xop_fxt.Reg_xwiki_alias(fxt.Wiki(), alias, domain);
	}
}
