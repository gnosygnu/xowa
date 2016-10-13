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
package gplx.xowa.addons.htmls.scripts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.htmls.*;
import gplx.langs.htmls.scripts.*;
import gplx.xowa.wikis.pages.tags.*;
import gplx.xowa.addons.htmls.scripts.apis.*;
public class Xoscript_mgr {
	private Io_url root_dir;
	private Io_url[] script_urls; private int script_urls_len;
	public void Init(Xow_wiki wiki) {
		// check script_dir
		this.root_dir = wiki.Fsys_mgr().Root_dir().GenSubDir_nest("bin", "html", "script");
		if (!Io_mgr.Instance.ExistsDir(root_dir)) return;

		// xowa.boot.js
		// find script urls
		this.script_urls = Io_mgr.Instance.QueryDir_args(root_dir).FilPath_("*.js").ExecAsUrlAry();
		this.script_urls_len = script_urls.length;
		if (script_urls_len == 0) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "xoscript:no '.js' files found; dir=~{0}", root_dir.Raw());
			return;
		}
	}
	public void Write(Bry_bfr rv, Xow_wiki wiki, Xoa_page page) {
		// init
		this.Init(wiki);
		if (script_urls_len == 0) return;

		// create engine and load scripts
		Gfh_script_engine engine = new Gfh_script_engine__java();
		for (int i = 0; i < script_urls_len; ++i) {
			engine.Load_script(script_urls[i]);
		}

		// call script
		Object xowa_script = engine.Get_object("xowa_script");
		Xoscript_env env = new Xoscript_env(root_dir);
		Xoscript_page spg = new Xoscript_page(rv, env, new Xoscript_url(page.Wiki().Domain_str(), String_.new_u8(page.Url().Page_bry())));
		engine.Invoke_method(xowa_script, "main", spg);

		// overwrite html
		if (spg.Doc().Dirty()) {
			rv.Clear();
			rv.Add_str_u8(spg.Doc().Html());
		}
	}	
}
