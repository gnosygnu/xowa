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
package gplx.xowa.htmls.hrefs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import org.junit.*; import gplx.xowa.apps.urls.*; import gplx.xowa.wikis.nss.*;
public class Xoh_href_parser_tst {
	private final    Xoh_href_parser_fxt fxt = new Xoh_href_parser_fxt();
	@Test   public void Wiki__basic() {
		fxt.Run_parse_by_href("/wiki/A").Chk_tid(Xoa_url_.Tid_page).Chk_to_str("en.wikipedia.org/wiki/A").Chk_wiki("en.wikipedia.org").Chk_page("A");
	}
	@Test   public void Wiki__page__w_question() {
		fxt.Run_parse_by_href("/wiki/%3F").Chk_page("?");
	}
	@Test   public void Wiki__qarg() {
		fxt.Run_parse_by_href("/wiki/A?action=edit").Chk_page("A").Chk_qargs("?action=edit").Chk_to_str("en.wikipedia.org/wiki/A?action=edit");
	}
	@Test   public void Wiki__qarg__w_question() {
		fxt.Run_parse_by_href("/wiki/A%3F?action=edit").Chk_page("A?").Chk_qargs("?action=edit");
	}
	@Test   public void Wiki__anchor() {
		fxt.Run_parse_by_href("/wiki/A#b").Chk_to_str("en.wikipedia.org/wiki/A#b").Chk_anch("b");
	}
	@Test   public void Wiki__xwiki__only()	{
		fxt.Prep_add_xwiki_to_wiki("c", "commons.wikimedia.org");
		fxt.Run_parse_by_href("/wiki/c:").Chk_page_is_main_y().Chk_page("Main_Page").Chk_to_str("commons.wikimedia.org/wiki/Main_Page");
	}
	@Test   public void Wiki__encoded() {
		fxt.Run_parse_by_href("/wiki/A%22b%22c").Chk_page("A\"b\"c");
	}
	@Test   public void Wiki__triple_slash() {	// PURPOSE: handle triple slashes; PAGE:esolangs.org/wiki/Language_list; DATE:2015-11-14
		fxt.Run_parse_by_href("/wiki////").Chk_to_str("en.wikipedia.org/wiki////").Chk_wiki("en.wikipedia.org").Chk_page("///");
	}
	@Test   public void Wiki__http() {	// PURPOSE: variant of triple slashes; DATE:2015-11-14
		fxt.Run_parse_by_href("/wiki/http://a").Chk_to_str("en.wikipedia.org/wiki/Http://a").Chk_wiki("en.wikipedia.org").Chk_page("Http://a");
	}
	@Test   public void Site__basic() {
		fxt.Run_parse_by_href("/site/en.wikipedia.org/wiki/A").Chk_tid(Xoa_url_.Tid_page).Chk_to_str("en.wikipedia.org/wiki/A").Chk_page("A");
	}
	@Test   public void Site__ns_case() {
		fxt.Run_parse_by_href("/site/en.wikipedia.org/wiki/file:A").Chk_page("File:A");
	}
	@Test   public void Site__main_page() {
		fxt.Run_parse_by_href("/site/en.wikipedia.org/wiki/").Chk_page("Main_Page").Chk_page_is_main_y();
	}
	@Test   public void Site__anch() {
		fxt.Run_parse_by_href("/site/en.wikipedia.org/wiki/A#b_c").Chk_page("A").Chk_anch("b_c");
	}
	@Test   public void Site__qarg() {
		fxt.Run_parse_by_href("/site/en.wikipedia.org/wiki/A?action=edit").Chk_page("A").Chk_qargs("?action=edit");
	}
	@Test   public void Site__invalid_ttl_shouldnt_fail() {	// PURPOSE: invalid title shouldn't fail; EX: A{{B}} is invalid (b/c of braces);
		fxt.Run_parse_by_href("/site/en.wikipedia.org/wiki/A{{B}}").Chk_page("");
	}
	@Test   public void Site__xwiki_cases_correctly() {	// PURPOSE: xwiki links should use case_match of xwiki (en.wiktionary.org) not cur_wiki (en.wikipedia.org); EX:w:Alphabet
		Xowe_wiki en_wiktionary_org = fxt.Prep_create_wiki("en.wiktionary.org");
		en_wiktionary_org.Ns_mgr().Ns_main().Case_match_(Xow_ns_case_.Tid__all);
		fxt.Prep_add_xwiki_to_user("en.wiktionary.org", "en.wiktionary.org");
		fxt.Run_parse_by_href("/site/en.wiktionary.org/wiki/alphabet");				
		fxt.Chk_to_str("en.wiktionary.org/wiki/alphabet").Chk_page("alphabet");
	}
	@Test   public void Site__xwiki_compound() {	// PURPOSE: [[[w:wikt:]] not handled; DATE:2013-07-25
		fxt.Prep_add_xwiki_to_wiki("wikt", "en.wiktionary.org");
		fxt.Run_parse_by_href("/site/en.wikipedia.org/wiki/wikt:")
			.Chk_tid(Xoa_url_.Tid_page)
			.Chk_page("Main_Page")
			.Chk_to_str("en.wiktionary.org/wiki/Main_Page")
			;
	}
//		@Test   public void Vnt() {
//			Xowe_wiki wiki = fxt.Wiki();
//			fxt.Prep_add_xwiki_to_user("zh.wikipedia.org");
//			wiki.Lang().Vnt_mgr().Enabled_(true);
//			wiki.Lang().Vnt_mgr().Vnt_grp().Add(new gplx.xowa.langs.vnts.Vnt_mnu_itm(Bry_.new_a7("zh-hans"), Bry_.new_a7("zh-hant")));
//			fxt.Run_parse_by_href("/site/zh.wikipedia.org/zh-hant/A").Chk_page("A").Chk_vnt("zh-hant");
//		}
	@Test   public void Http__basic() {
		fxt.Run_parse_by_href("http://a.org/b").Chk_tid(Xoa_url_.Tid_inet);
	}
	@Test   public void Prot__ftp() { // PURPOSE: check that urls with form of "ftp://" return back Tid_ftp; DATE:2014-04-25
		fxt.Run_parse_by_href("ftp://a.org").Chk_tid(Xoa_url_.Tid_inet);
	}
	@Test   public void File__basic() {
		fxt.Run_parse_by_href("file:///C/xowa/file/a.png").Chk_tid(Xoa_url_.Tid_file);
	}
	@Test   public void Anchor__basic() {
		fxt.Run_parse_by_href("#a").Chk_tid(Xoa_url_.Tid_anch).Chk_to_str("en.wikipedia.org/wiki/Page 1#a").Chk_anch("a");
	}
	@Test   public void Xcmd__basic() {
		fxt.Run_parse_by_href("/xcmd/page_edit").Chk_tid(Xoa_url_.Tid_xcmd).Chk_page("page_edit");
	}
	@Test   public void Xowa__basic() {
		fxt.Run_parse_by_href("xowa-cmd:a%22b*c").Chk_tid(Xoa_url_.Tid_xcmd).Chk_page("a\"b*c");
	}
	// COMMENTED: this seems wrong; [//wikisource.org] should go to https://wikisource.org not https://en.wikisource.org; both sites are different; DATE:2015-08-02
//		@Test   public void Site__user_wiki() {// PURPOSE: outlier for wikisource.org which is alias to en.wikisource.org; alias added in user_wiki; EX: [//wikisource.org a]; in browser, automatically goes to http://wikisource.org; in xowa, should go to /site/en.wikisource.org
//			fxt.Prep_xwiki(fxt.App().User().Wikii(), "en_wiki_alias", "en.wikipedia.org", null);
//			fxt.Run_parse_by_href("/site/en_wiki_alias/wiki/")				
//				.Chk_tid(Xoa_url_.Tid_page)
//				.Chk_page("Main_Page")
//				.Chk_to_str("en.wikipedia.org/wiki/Main_Page")
//				;
//		}
}
class Xoh_href_parser_fxt extends Xow_url_parser_fxt {	private final    Xoh_href_parser href_parser = new Xoh_href_parser();
	public Xoh_href_parser_fxt Run_parse_by_href(String raw) {
		href_parser.Parse_as_url(actl_url, Bry_.new_u8(raw), cur_wiki, Bry__page_1);
		return this;
	}
	private static final    byte[] Bry__page_1 = Bry_.new_a7("Page 1");
}
