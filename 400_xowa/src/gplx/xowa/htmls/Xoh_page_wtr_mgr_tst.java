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
package gplx.xowa.htmls; import gplx.*; import gplx.xowa.*;
import org.junit.*;
import gplx.xowa.guis.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.portal.*; import gplx.xowa.wikis.pages.*;
public class Xoh_page_wtr_mgr_tst {
	@Before public void init() {}
	@Test  public void Logo_has_correct_main_page() {	// PURPOSE: Logo href should be "/site/en.wikipedia.org/wiki/", not "/wiki/Main_Page"
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app);
		Xow_portal_mgr portal_mgr = wiki.Html_mgr().Portal_mgr();
		Gfo_invk_.Invk_by_val(portal_mgr, Xow_portal_mgr.Invk_div_logo_, Bry_.new_a7("~{portal_nav_main_href}"));
		portal_mgr.Init_assert();
		Xoh_page_wtr_mgr page_wtr_mgr = new Xoh_page_wtr_mgr(true);
		page_wtr_mgr.Gen(wiki.Parser_mgr().Ctx().Page(), Xopg_page_.Tid_read);
		Tfds.Eq(String_.new_a7(portal_mgr.Div_logo_bry()), "/site/en.wikipedia.org/wiki/");
	}
	@Test   public void Skip__math__basic() {
		Xop_fxt fxt = Xop_fxt.New_app_html();
		fxt.Init_lang_vnts("zh-hans", "zh-hant");

		fxt.Test__parse_to_html_mgr(String_.Concat_lines_nl_skip_last
		( "<math>x_{1}-1</math>"
		, "<math>x-{1+2}-1</math>"
		, "-{zh-hans:A;zh-hant:B;}-"
		), String_.Concat_lines_nl_skip_last
		( "<span id='xowa_math_txt_0'>x_{1}-1</span>"		// not converted
		, "<span id='xowa_math_txt_0'>x-{1+2}-1</span>"		// not converted
		, "A" // converted
		));
	}
}
