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
package gplx.xowa.htmls.hrefs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import org.junit.*; import gplx.xowa.apps.urls.*; import gplx.xowa.wikis.nss.*;
public class Xoh_href_parser__basic__tst {
	private final    Xoh_href_parser_fxt fxt = new Xoh_href_parser_fxt();
	@Test   public void Site__basic() {
		fxt.Exec__parse_as_url("/site/en.wikipedia.org/wiki/A").Test__tid(Xoa_url_.Tid_page).Test__to_str("en.wikipedia.org/wiki/A").Test__page("A");
	}
	@Test   public void Site__ns_case() {
		fxt.Exec__parse_as_url("/site/en.wikipedia.org/wiki/file:A").Test__page("File:A");
	}
	@Test   public void Site__main_page() {
		fxt.Exec__parse_as_url("/site/en.wikipedia.org/wiki/").Test__page("Main_Page").Test__page_is_main_y();
	}
	@Test   public void Site__anch() {
		fxt.Exec__parse_as_url("/site/en.wikipedia.org/wiki/A#b_c").Test__page("A").Test__anch("b_c");
	}
	@Test   public void Site__qarg() {
		fxt.Exec__parse_as_url("/site/en.wikipedia.org/wiki/A?action=edit").Test__page("A").Test__qargs("?action=edit");
	}
	@Test   public void Site__invalid_ttl_shouldnt_fail() {	// PURPOSE: invalid title shouldn't fail; EX: A{{B}} is invalid (b/c of braces);
		fxt.Exec__parse_as_url("/site/en.wikipedia.org/wiki/A{{B}}").Test__page("");
	}
	@Test   public void Site__xwiki_cases_correctly() {	// PURPOSE: xwiki links should use case_match of xwiki (en.wiktionary.org) not cur_wiki (en.wikipedia.org); EX:w:Alphabet
		Xowe_wiki en_wiktionary_org = fxt.Prep_create_wiki("en.wiktionary.org");
		en_wiktionary_org.Ns_mgr().Ns_main().Case_match_(Xow_ns_case_.Tid__all);
		fxt.Prep_add_xwiki_to_user("en.wiktionary.org", "en.wiktionary.org");
		fxt.Exec__parse_as_url("/site/en.wiktionary.org/wiki/alphabet");				
		fxt.Test__to_str("en.wiktionary.org/wiki/alphabet").Test__page("alphabet");
	}
	@Test   public void Site__xwiki_compound() {	// PURPOSE: [[[w:wikt:]] not handled; DATE:2013-07-25
		fxt.Prep_add_xwiki_to_wiki("wikt", "en.wiktionary.org");
		fxt.Exec__parse_as_url("/site/en.wikipedia.org/wiki/wikt:")
			.Test__tid(Xoa_url_.Tid_page)
			.Test__page("Main_Page")
			.Test__to_str("en.wiktionary.org/wiki/Main_Page")
			;
	}
//		@Test   public void Vnt() {
//			Xowe_wiki wiki = fxt.Wiki();
//			fxt.Prep_add_xwiki_to_user("zh.wikipedia.org");
//			wiki.Lang().Vnt_mgr().Enabled_(true);
//			wiki.Lang().Vnt_mgr().Vnt_grp().Add(new gplx.xowa.langs.vnts.Vnt_mnu_itm(Bry_.new_a7("zh-hans"), Bry_.new_a7("zh-hant")));
//			fxt.Exec__parse_as_url("/site/zh.wikipedia.org/zh-hant/A").Test__page("A").Chk_vnt("zh-hant");
//		}
	@Test   public void Http__basic() {
		fxt.Exec__parse_as_url("http://a.org/b").Test__tid(Xoa_url_.Tid_inet);
	}
	@Test   public void Prot__ftp() { // PURPOSE: check that urls with form of "ftp://" return back Tid_ftp; DATE:2014-04-25
		fxt.Exec__parse_as_url("ftp://a.org").Test__tid(Xoa_url_.Tid_inet);
	}
	@Test   public void File__basic() {
		fxt.Exec__parse_as_url("file:///C/xowa/file/a.png").Test__tid(Xoa_url_.Tid_file);
	}
	@Test   public void Anchor__basic() {
		fxt.Exec__parse_as_url("#a").Test__tid(Xoa_url_.Tid_anch).Test__to_str("en.wikipedia.org/wiki/Page 1#a").Test__anch("a");
	}
	@Test   public void Xcmd__basic() {
		fxt.Exec__parse_as_url("/xcmd/page_edit").Test__tid(Xoa_url_.Tid_xcmd).Test__page("page_edit");
	}
	@Test   public void Xowa__basic() {
		fxt.Exec__parse_as_url("xowa-cmd:a%22b*c").Test__tid(Xoa_url_.Tid_xcmd).Test__page("a\"b*c");
	}
	// COMMENTED: this seems wrong; [//wikisource.org] should go to https://wikisource.org not https://en.wikisource.org; both sites are different; DATE:2015-08-02
//		@Test   public void Site__user_wiki() {// PURPOSE: outlier for wikisource.org which is alias to en.wikisource.org; alias added in user_wiki; EX: [//wikisource.org a]; in browser, automatically goes to http://wikisource.org; in xowa, should go to /site/en.wikisource.org
//			fxt.Prep_xwiki(fxt.App().User().Wikii(), "en_wiki_alias", "en.wikipedia.org", null);
//			fxt.Exec__parse_as_url("/site/en_wiki_alias/wiki/")				
//				.Test__tid(Xoa_url_.Tid_page)
//				.Test__page("Main_Page")
//				.Test__to_str("en.wikipedia.org/wiki/Main_Page")
//				;
//		}
}
class Xoh_href_parser_fxt extends Xow_url_parser_fxt {	private final    Xoh_href_parser href_parser = new Xoh_href_parser();
	public Xoh_href_parser_fxt Exec__parse_as_url(String raw) {
		href_parser.Parse_as_url(actl_url, Bry_.new_u8(raw), cur_wiki, Bry__page_1);
		return this;
	}
	private static final    byte[] Bry__page_1 = Bry_.new_a7("Page 1");
}
