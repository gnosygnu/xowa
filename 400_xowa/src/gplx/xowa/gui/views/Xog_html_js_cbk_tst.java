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
package gplx.xowa.gui.views; import gplx.*; import gplx.xowa.*; import gplx.xowa.gui.*;
import org.junit.*; import gplx.xowa.xtns.wdatas.*;
public class Xog_html_js_cbk_tst {
	@Before public void init() {fxt.Clear();} private Xog_html_js_cbk_fxt fxt = new Xog_html_js_cbk_fxt();
	@Test   public void Get_title() {
		fxt.Fxt().Init_page_create("exists");
		fxt.Test_get_title("exists", "1" , "0" , Int_.XtoStr(Int_.MinValue), "Exists", "false", "0001-01-01 00:00:00", "0");
		fxt.Test_get_title("absent", "0", "-1", Int_.XtoStr(Int_.MinValue), null	, "false", "0001-01-01 00:00:00", "0");
	}
}
class Xog_html_js_cbk_fxt {
	public void Clear() {
		fxt = new Xop_fxt();
		Xoa_app_fxt.Init_gui(fxt.App());
	}	private Xop_fxt fxt;
	public Xop_fxt Fxt() {return fxt;}
	public void Test_get_title(String ttl, Object... expd) {
		Xoa_app app = fxt.App();
		Xow_wiki wiki = fxt.Wiki();
		Xoa_page page = Xoa_page.test_(wiki, Xoa_ttl.parse_(wiki, Bry_.new_ascii_("mock_page")));
		Xog_tab_itm tab = app.Gui_mgr().Browser_win().Active_tab();
		tab.Page_(page);
		Xog_html_js_cbk exec = tab.Html_itm().Js_cbk();
		GfoMsg msg = GfoMsg_.new_cast_(Xog_html_js_cbk.Invk_get_titles_meta).Add("ttl", ttl);
		String[][] actl = (String[][])GfoInvkAble_.InvkCmd_msg(exec, Xog_html_js_cbk.Invk_get_titles_meta, msg);
		Tfds.Eq_ary_str(expd, actl[0]);
	}
}
