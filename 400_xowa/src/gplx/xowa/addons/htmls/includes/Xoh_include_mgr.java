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
package gplx.xowa.addons.htmls.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.htmls.*;
import gplx.xowa.wikis.pages.tags.*;
public class Xoh_include_mgr {
	private Io_url[] main_urls; private int main_urls_len;
	public void Init(Xow_wiki wiki) {
		// check bin dir
		Io_url include_dir = wiki.Fsys_mgr().Root_dir().GenSubDir_nest("bin", "html", "include");
		if (!Io_mgr.Instance.ExistsDir(include_dir)) return;

		this.main_urls = Io_mgr.Instance.QueryDir_args(include_dir).FilPath_("*.js").ExecAsUrlAry();
		this.main_urls_len = main_urls.length;
		if (main_urls_len == 0) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "no '.js' files found; dir=~{0}", include_dir.Raw());
			return;
		}
	}
	public void Write(Xow_wiki wiki, Xoa_page page) {
		this.Init(wiki);
		if (main_urls_len == 0) return;

		// create engine and load scripts
		Script_engine engine = new Script_engine__java();
		for (int i = 0; i < main_urls_len; ++i) {
			engine.Load_script(main_urls[i]);
		}
//			engine.Put_object("page", new Xoscript_page())
		Xoscript_page xos_pg = new Xoscript_page();

		Object xowa_script = engine.Get_object("xowa_script");
//			engine.Invoke_method(xowa_script, "main", page.Url().To_str());
		engine.Invoke_method(xowa_script, "main", xos_pg);
//			if (String_.Has(rv, "yes")) {
//				Io_url js_url = null;
//				page.Html_data().Custom_head_tags().Add(Xopg_tag_itm.New_js_file(js_url));
//			}
	}
//		public static Xoh_include_mgr Get_or_new(Xow_wiki wiki) {
//			wiki.Addon_mgr().Itms__get_or_null();
//		}
}
