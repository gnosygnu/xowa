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
package gplx.xowa.htmls.js; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import org.junit.*; import gplx.xowa.xtns.wbases.*;
import gplx.xowa.guis.views.*;
public class Xoh_js_cbk_tst {
	@Before public void init() {fxt.Clear();} private Xoh_js_cbk_fxt fxt = new Xoh_js_cbk_fxt();
	@Test   public void Get_title() {
		fxt.Fxt().Init_page_create("exists");
		fxt.Test_get_title("exists", "1" , "0" , Int_.To_str(Int_.Min_value), "Exists", "false", "0001-01-01 00:00:00", "0");
		fxt.Test_get_title("absent", "0", "-1", Int_.To_str(Int_.Min_value), null	, "false", "0001-01-01 00:00:00", "0");
	}
}
class Xoh_js_cbk_fxt {
	public void Clear() {
		fxt = new Xop_fxt();
		Xoa_app_fxt.Init_gui(fxt.App(), fxt.Wiki());
	}	private Xop_fxt fxt;
	public Xop_fxt Fxt() {return fxt;}
	public void Test_get_title(String ttl, Object... expd) {
		Xoae_app app = fxt.App();
		Xowe_wiki wiki = fxt.Wiki();
		Xoae_page page = Xoae_page.New_test(wiki, Xoa_ttl.Parse(wiki, Bry_.new_a7("mock_page")));
		Xog_tab_itm tab = app.Gui_mgr().Browser_win().Active_tab();
		tab.Page_(page);
		Xoh_js_cbk exec = tab.Html_itm().Js_cbk();
		GfoMsg msg = GfoMsg_.new_cast_(Xoh_js_cbk.Invk_get_titles_meta).Add("ttl", ttl);
		String[][] actl = (String[][])Gfo_invk_.Invk_by_msg(exec, Xoh_js_cbk.Invk_get_titles_meta, msg);
		Tfds.Eq_ary_str(expd, actl[0]);
	}
}
