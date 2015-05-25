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
import gplx.xowa.net.*;
public class Xoh_href_parser_tst {		
	@Before public void init() {fxt.Clear();} private Xoh_href_parser_fxt fxt = new Xoh_href_parser_fxt();
	@Test   public void Parse_full_wiki() {
		fxt	.Prep_raw_("file:///wiki/A")
			.Expd_tid_(Xoh_href.Tid_wiki)
			.Expd_full_("en.wikipedia.org/wiki/A")
			.Expd_wiki_("en.wikipedia.org")
			.Expd_page_("A")
			.Test_parse();
	}
	@Test   public void Parse_full_http() {
		fxt	.Prep_raw_("http://a.org/b")
			.Expd_tid_(Xoh_href.Tid_http)
			.Expd_full_("http://a.org/b")
			.Test_parse();
	}
	@Test   public void Parse_full_file() {
		fxt	.Prep_raw_("file:///C/xowa/file/a.png")
			.Expd_tid_(Xoh_href.Tid_file)
			.Expd_full_("file:///C/xowa/file/a.png")
			.Test_parse();
	}
	@Test   public void Parse_full_anchor_only() {
		fxt	.Prep_raw_("#a")
			.Expd_tid_(Xoh_href.Tid_anchor)
			.Expd_full_("en.wikipedia.org/wiki/Page 1#a")
			.Expd_anch_("a")
			.Test_parse();
	}
	@Test   public void Parse_full_anchor_w_page() {
		fxt	.Prep_raw_("file:///wiki/A#b")
			.Expd_tid_(Xoh_href.Tid_wiki)
			.Expd_full_("en.wikipedia.org/wiki/A#b")
			.Expd_anch_("b")
			.Test_parse();
	}
	@Test   public void Parse_full_xwiki() {
		fxt	.Prep_raw_("file:///site/en.wikt.org/wiki/Page")
			.Expd_tid_(Xoh_href.Tid_site)
			.Expd_full_("en.wikt.org/wiki/Page")
			.Expd_page_("Page")
			.Test_parse();
	}
	@Test   public void Parse_full_xwiki_domain_only()	{
		fxt	.Prep_raw_("/wiki/wikt:")
			.Init_xwiki_alias("wikt", "en.wiktionary.org")			
			.Expd_full_("en.wiktionary.org/wiki/")
			.Expd_page_("")
			.Test_parse();
	}
	@Test   public void Parse_full_wiki_page() {
		fxt	.Prep_raw_("/wiki/A")
			.Expd_tid_(Xoh_href.Tid_wiki)
			.Expd_full_("en.wikipedia.org/wiki/A")
			.Expd_page_("A")
			.Test_parse();
	}
	@Test   public void Parse_empty_is_main_page() {		// PURPOSE: w/ slash; "wiki/"
		fxt	.Prep_raw_("/site/en.wikipedia.org/wiki/")
			.Expd_tid_(Xoh_href.Tid_site)
			.Expd_full_("en.wikipedia.org/wiki/Main Page")
			.Expd_page_("Main Page")
			.Test_parse();
	}
	@Test   public void Parse_empty_is_main_page_2()	{	// PURPOSE: wo slash; "wiki"
		fxt	.Prep_raw_("/site/en.wikipedia.org/wiki")
			.Expd_tid_(Xoh_href.Tid_site)
			.Expd_full_("en.wikipedia.org/wiki/Main Page")
			.Expd_page_("Main Page")
			.Test_parse();
	}
	@Test   public void Parse_site_page() {
		fxt	.Prep_raw_("/site/en.wikt.org/wiki/A")
			.Expd_tid_(Xoh_href.Tid_site)
			.Expd_full_("en.wikt.org/wiki/A")
			.Expd_page_("A")
			.Test_parse();
	}
	@Test   public void Parse_site_ns_case() {
		fxt	.Prep_raw_("/site/en.wikt.org/wiki/file:A")
			.Expd_tid_(Xoh_href.Tid_site)
			.Expd_full_("en.wikt.org/wiki/File:A")
			.Expd_page_("File:A")
			.Test_parse();
	}
	@Test   public void Parse_site_page__invalid_ttl_shouldnt_fail() {	// PURPOSE: invalid title shouldn't fail; EX: A{{B}} is invalid (b/c of braces);
		fxt	.Prep_raw_("/site/en.wikt.org/wiki/A{{B}}")
			.Expd_tid_(Xoh_href.Tid_site)
			.Expd_full_("en.wikt.org/wiki/")
			.Expd_page_("")
			.Test_parse();
	}
	@Test   public void Parse_xcmd_edit() {
		fxt	.Prep_raw_("/xcmd/page_edit")
			.Expd_tid_(Xoh_href.Tid_xcmd)
			.Expd_full_("")
			.Expd_page_("page_edit")
			.Test_parse();
	}
	@Test   public void Parse_xowa() {
		fxt	.Prep_raw_("xowa-cmd:a%22b*c")
			.Expd_tid_(Xoh_href.Tid_xowa)
			.Expd_full_("a\"b*c")
			.Expd_page_("a\"b*c")
			.Test_parse();
	}
	@Test   public void Parse_edit_wiki_quote() {
		fxt	.Prep_raw_("/wiki/A%22b%22c")
			.Expd_tid_(Xoh_href.Tid_wiki)
			.Expd_full_("en.wikipedia.org/wiki/A\"b\"c")
			.Expd_page_("A\"b\"c")
			.Test_parse();
	}

	@Test   public void Parse_brief_wiki()				{fxt.Init_hover_full_n_().Test_parse("file:///wiki/A"						, "A");}
	@Test   public void Parse_brief_http()				{fxt.Init_hover_full_n_().Test_parse("http://a.org/b"						, "http://a.org/b");}
	@Test   public void Parse_brief_file()				{fxt.Init_hover_full_n_().Test_parse("file:///C/xowa/file/a.png"			, "file:///C/xowa/file/a.png");}
	@Test   public void Parse_brief_anchor()			{fxt.Init_hover_full_n_().Test_parse("#a"									, "#a");}
	@Test   public void Parse_brief_anchor_file()		{fxt.Init_hover_full_n_().Test_parse("file:///#a"							, "#a");}
	@Test   public void Parse_brief_xwiki()				{fxt.Init_hover_full_n_().Test_parse("file:///site/en.wikt.org/wiki/Page"	, "en.wikt.org/Page");}
	@Test   public void Parse_brief_xwiki_2()			{fxt.Init_hover_full_n_().Expd_page_("a").Test_parse("/wiki/wikt:a"			, "en.wiktionary.org/a");}
	@Test   public void Parse_brief_error()				{fxt.Init_hover_full_n_().Test_parse("file:///wiki/{{{extlink}}}"			, "");}	// {{{extlink}}} not a valid title; return empty
//		@Test   public void Parse_site_qarg()				{fxt.Prep_raw_("/site/en.wikt.org/wiki/A?action=edit").Expd_tid_(Xoh_href.Tid_site).Expd_full_("en.wikt.org/wiki/A").Expd_page_("A").Expd_qarg_("action=edit").Test_parse();}
//		@Test   public void Parse_wiki_qarg()				{fxt.Prep_raw_("/wiki/A?action=edit").Expd_tid_(Xoh_href.Tid_wiki).Expd_full_("en.wikipedia.org/wiki/A").Expd_page_("A").Expd_qarg_("action=edit").Test_parse();}
	//@Test   public void Parse_site_anchor()				{fxt.Prep_raw_("/site/en.wikt.org/wiki/A#b_c"		).Expd_tid_(Xoh_href.Tid_site).Expd_full_("en.wikt.org/wiki/A#b_c").Expd_page_("A").Expd_anch_("b_c").Test_parse();}
	@Test   public void Build_xwiki_enc()				{fxt.Test_build("wikt:abc?d"				, "/site/en.wiktionary.org/wiki/abc%3Fd");}	
	@Test   public void Build_page_quote()				{fxt.Test_build("a\"b\"c"					, "/wiki/A%22b%22c");}
	@Test   public void Build_page()					{fxt.Test_build("abc"						, "/wiki/Abc");}
	@Test   public void Build_page_ns()					{fxt.Test_build("Image:A.png"				, "/wiki/Image:A.png");}
	@Test   public void Build_anchor()					{fxt.Test_build("#abc"						, "#abc");}
	@Test   public void Build_page_anchor()				{fxt.Test_build("Abc#def"					, "/wiki/Abc#def");}
	@Test   public void Build_xwiki()					{fxt.Test_build("wikt:abc"					, "/site/en.wiktionary.org/wiki/abc");}	// NOTE: "abc" not capitalized, b/c other wiki's case sensitivity is not known; this emulates WP's behavior
	@Test   public void Build_xwiki_2()					{fxt.Test_build("wikt:Special:Search/a"		, "/site/en.wiktionary.org/wiki/Special:Search/a");}
	@Test   public void Build_category() 				{fxt.Test_build("Category:abc"				, "/wiki/Category:Abc");}
	
	@Test   public void Parse_site_user_wiki() {// PURPOSE: outlier for wikisource.org which is alias to en.wikisource.org; alias added in user_wiki; EX: [//wikisource.org a]; in browser, automatically goes to http://wikisource.org; in xowa, should go to /site/en.wikisource.org
		fxt	.Prep_raw_("/site/en_wiki_alias/wiki/")
			.Init_xwiki_alias("en_wiki_alias", "en.wikipedia.org")			
			.Expd_tid_(Xoh_href.Tid_site)
			.Expd_full_("en.wikipedia.org/wiki/Main Page")
			.Expd_page_("Main Page")
			.Test_parse();
	}
	@Test   public void Parse_xwiki_cases_correctly() {	// PURPOSE: xwiki links should use case_match of xwiki (en.wiktionary.org) not cur_wiki (en.wikipedia.org); EX:w:Alphabet
		fxt	.Prep_raw_("/site/en.wiktionary.org/wiki/alphabet")
			.Init_xwiki_alias("en.wiktionary.org", "en.wiktionary.org");
		Xowe_wiki en_wiktionary_org = fxt.App().Wiki_mgr().Get_by_key_or_make(Bry_.new_a7("en.wiktionary.org"));
		en_wiktionary_org.Ns_mgr().Ns_main().Case_match_(Xow_ns_case_.Id_all);
		fxt	.Expd_tid_(Xoh_href.Tid_site)
			.Expd_full_("en.wiktionary.org/wiki/alphabet")
			.Expd_page_("alphabet")
			.Test_parse();
	}
	@Test   public void Parse_xwiki_compound() {	// PURPOSE: [[[w:wikt:]] not handled; DATE:2013-07-25
		fxt	.Prep_raw_("/site/en.wikipedia.org/wiki/wikt:")
			.Init_xwiki_alias("wikt:", "en.wiktionary.org")			
			.Expd_tid_(Xoh_href.Tid_site)
			.Expd_full_("en.wiktionary.org/wiki/Main Page")
			.Expd_page_("Main Page")
			.Test_parse();
	}
	@Test   public void Parse_protocol() {	// PURPOSE: check that urls with form of "ftp://" return back Tid_ftp; DATE:2014-04-25
		fxt	.Test_parse_protocol("ftp://a.org", Xoo_protocol_itm.Tid_ftp);
	}
	@Test   public void Build_xwiki_wikimedia_mail() {	// PURPOSE: DATE:2015-04-22
		fxt	.Init_xwiki_by_many("mail|https://lists.wikimedia.org/mailman/listinfo/$1|Wikitech Mailing List");
		fxt.Test_build("mail:A"				, "https://lists.wikimedia.org/mailman/listinfo/A");
	}
//		@Test   public void Parse_question_ttl()				{fxt.Prep_raw_("/wiki/%3F").Expd_tid_(Xoh_href.Tid_wiki).Expd_full_("en.wikipedia.org/wiki/?").Expd_page_("?").Test_parse();}
//		@Test   public void Parse_question_w_arg()			{fxt.Prep_raw_("/wiki/A%3F?action=edit").Expd_tid_(Xoh_href.Tid_wiki).Expd_full_("en.wikipedia.org/wiki/A??action=edit").Expd_page_("A??action=edit").Test_parse();}
}
class Xoh_href_parser_fxt {
	private Xowe_wiki wiki; private Xoh_href_parser href_parser; private Bry_bfr tmp_bfr = Bry_bfr.reset_(255); private Xoh_href href = new Xoh_href();
	private static final byte[] Page_1_ttl = Bry_.new_a7("Page 1");
	public void Clear() {
		expd_tid = Xoh_href.Tid_null;
		prep_raw = expd_full = expd_wiki = expd_page = expd_anch = null;
		if (app != null) return;
		app = Xoa_app_fxt.app_();
		wiki = Xoa_app_fxt.wiki_tst_(app);
		wiki.Xwiki_mgr().Add_bulk(Bry_.new_a7("wikt|en.wiktionary.org"));
		app.Usere().Wiki().Xwiki_mgr().Add_bulk(Bry_.new_a7("en.wiktionary.org|en.wiktionary.org"));
		href_parser = new Xoh_href_parser(Xoa_app_.Utl__encoder_mgr().Href(), app.Url_parser().Url_parser());
	}
	public Xoae_app App() {return app;} private Xoae_app app;
	public Xoh_href_parser_fxt Init_xwiki_alias(String alias, String domain) {
		app.Usere().Wiki().Xwiki_mgr().Add_full(alias, domain);
		return this;
	}
	public Xoh_href_parser_fxt Init_xwiki_by_many(String raw) {
		wiki.Xwiki_mgr().Add_many(Bry_.new_u8(raw));	// need to add to wiki's xwiki_mgr for ttl_parse
		return this;
	}
	public Xoh_href_parser_fxt Init_hover_full_y_() {return Init_hover_full_(Bool_.Y);}
	public Xoh_href_parser_fxt Init_hover_full_n_() {return Init_hover_full_(Bool_.N);}
	public Xoh_href_parser_fxt Init_hover_full_(boolean v)	{wiki.Gui_mgr().Cfg_browser().Link_hover_full_(v); return this;}
	public Xoh_href_parser_fxt Prep_raw_(String v)	{this.prep_raw = v; return this;} private String prep_raw;
	public Xoh_href_parser_fxt Expd_tid_(byte v)	{this.expd_tid = v; return this;} private byte expd_tid;
	public Xoh_href_parser_fxt Expd_full_(String v)	{this.expd_full = v; return this;} private String expd_full;
	public Xoh_href_parser_fxt Expd_wiki_(String v)	{this.expd_wiki = v; return this;} private String expd_wiki;
	public Xoh_href_parser_fxt Expd_page_(String v)	{this.expd_page = v; return this;} private String expd_page;
	public Xoh_href_parser_fxt Expd_anch_(String v)	{this.expd_anch = v; return this;} private String expd_anch;
	public void Test_parse() {
		href_parser.Parse(href, prep_raw, wiki, Page_1_ttl);
		if (expd_tid != Xoh_href.Tid_null) 	Tfds.Eq(expd_tid, href.Tid());
		if (expd_wiki != null) 				Tfds.Eq(expd_wiki, String_.new_u8(href.Wiki()));
		if (expd_page != null) 				Tfds.Eq(expd_page, String_.new_u8(href.Page()));
		if (expd_anch != null) 				Tfds.Eq(expd_anch, String_.new_u8(href.Anchor()));
		if (expd_full != null) {
			href.Print_to_bfr(tmp_bfr, true);
			Tfds.Eq(expd_full, tmp_bfr.Xto_str_and_clear());
		}
	}
	public void Test_parse(String raw, String expd) {
		href_parser.Parse(href, raw, wiki, Page_1_ttl);
		href.Print_to_bfr(tmp_bfr, wiki.Gui_mgr().Cfg_browser().Link_hover_full());
		Tfds.Eq(expd, tmp_bfr.Xto_str_and_clear());
	}
	public void Test_build(String raw, String expd) {
		Xoa_ttl ttl = Xoa_ttl.parse_(wiki, Bry_.new_u8(raw));
		href_parser.Build_to_bfr(tmp_bfr, app, wiki.Domain_bry(), ttl);
		Tfds.Eq(expd, tmp_bfr.Xto_str_and_clear());
	}
	public void Test_parse_protocol(String raw, byte expd_tid) {
		href_parser.Parse(href, raw, wiki, Page_1_ttl);
	    Tfds.Eq(expd_tid, href.Protocol_tid());
	}
}
