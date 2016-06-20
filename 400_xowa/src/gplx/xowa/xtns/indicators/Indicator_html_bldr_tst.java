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
package gplx.xowa.xtns.indicators; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*; import gplx.xowa.xtns.indicators.*;
public class Indicator_html_bldr_tst {
	@Before public void init() {fxt.Clear();} private Indicator_html_bldr_fxt fxt = new Indicator_html_bldr_fxt();
	@Test  public void Basic() {
		fxt.Init_indicator("a", "a1");
		fxt.Init_indicator("b", "b1");
		fxt.Test_bld(String_.Concat_lines_nl_skip_last
		( ""
		, "  <div class='mw-indicators'>"
		, "    <div id='mw-indicator-b' class='mw-indicator'>b1</div>"	// reverse-order
		, "    <div id='mw-indicator-a' class='mw-indicator'>a1</div>"
		, "  </div>"
		));
	}
	@Test  public void Multiple_ignore() {
		fxt.Init_indicator("a", "a1");
		fxt.Init_indicator("a", "a2");
		fxt.Test_bld(String_.Concat_lines_nl_skip_last
		( ""
		, "  <div class='mw-indicators'>"
		, "    <div id='mw-indicator-a' class='mw-indicator'>a2</div>"	// 2nd overwrites 1st
		, "  </div>"
		));
	}
}
class Indicator_html_bldr_fxt {
	private Xoae_app app; private Xowe_wiki wiki; private Xoae_page page;
	private Indicator_xtn_mgr xtn_mgr;
	public void Clear() {
		this.app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
		this.xtn_mgr = wiki.Xtn_mgr().Xtn_indicator();
		xtn_mgr.Enabled_y_();
		xtn_mgr.Xtn_init_by_wiki(wiki);
		this.page = wiki.Parser_mgr().Ctx().Page();
	}
	public void Init_indicator(String name, String html) {
		Indicator_xnde xnde = new Indicator_xnde();
		xnde.Init_for_test(name, Bry_.new_u8(html));			
		Indicator_html_bldr indicators = page.Html_data().Indicators();
		indicators.Add(xnde);
	}
	public void Test_bld(String expd) {
		Bry_bfr tmp_bfr = Bry_bfr_.Reset(255);
		page.Html_data().Indicators().Bfr_arg__add(tmp_bfr);
		Tfds.Eq_str_lines(expd, tmp_bfr.To_str_and_clear());
	}
}
