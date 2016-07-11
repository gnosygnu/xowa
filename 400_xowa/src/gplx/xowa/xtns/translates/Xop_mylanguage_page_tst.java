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
package gplx.xowa.xtns.translates; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Xop_mylanguage_page_tst {
	@Before public void init() {fxt.Clear();} private Xop_mylanguage_page_fxt fxt = new Xop_mylanguage_page_fxt();
	@Test  public void Non_english_exists() {
		fxt.Init_create_page("Help:A/fr");
		fxt.Init_cur_lang("fr");
		fxt.Test_open("Special:MyLanguage/Help:A", "Help:A/fr");
	}
	@Test  public void English() {
		fxt.Init_create_page("Help:A");
		fxt.Init_cur_lang("en");
		fxt.Test_open("Special:MyLanguage/Help:A", "Help:A");
	}
	@Test  public void Non_english_absent() {
		fxt.Init_create_page("Help:A");
		fxt.Init_cur_lang("fr");
		fxt.Test_open("Special:MyLanguage/Help:A", "Help:A");
	}
}
class Xop_mylanguage_page_fxt {
	public void Clear() {
		parser_fxt = new Xop_fxt();
		parser_fxt.Reset();
		app = parser_fxt.App();
		wiki = parser_fxt.Wiki();
		special_page = wiki.Special_mgr().Page_mylanguage();
	}	private Xop_fxt parser_fxt; private Xoae_app app; private Xop_mylanguage_page special_page; private Xowe_wiki wiki;
	public void Init_create_page(String page) {parser_fxt.Init_page_create(page, page);}
	public void Init_cur_lang(String lang) {app.Sys_cfg().Lang_(Bry_.new_a7(lang));}
	public void Test_open(String link, String expd) {
		Xoae_page page = parser_fxt.Ctx().Page();
		Xoa_url url = app.User().Wikii().Utl__url_parser().Parse(Bry_.new_u8(link));
		page.Url_(url);
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, Bry_.new_a7(link));
		page.Ttl_(ttl);
		special_page.Special__gen(wiki, page, url, ttl);
		Tfds.Eq(expd, String_.new_a7(page.Url().Page_bry()));
		Tfds.Eq(expd, String_.new_a7(page.Db().Text().Text_bry()));
	}
}
