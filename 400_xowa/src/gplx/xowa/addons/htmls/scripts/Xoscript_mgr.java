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
import gplx.xowa.addons.htmls.scripts.apis.*; import gplx.xowa.addons.htmls.scripts.xtns.*;
public class Xoscript_mgr {
	private Io_url root_dir;
//		private Io_url[] script_urls; private int script_urls_len;
	private Xoscript_xtn_mgr xtn_mgr;
	public void Init(Xow_wiki wiki) {
		// check script_dir
		this.root_dir = wiki.Fsys_mgr().Root_dir().GenSubDir_nest("bin", "any", "script");
		if (!Io_mgr.Instance.ExistsDir(root_dir)) return;
		this.xtn_mgr = new Xoscript_xtn_mgr(root_dir);
	}
	public void Write(Bry_bfr rv, Xow_wiki wiki, Xoa_page page) {
		// init
		this.Init(wiki);
		if (xtn_mgr == null) return;

		xtn_mgr.Load_root();
		xtn_mgr.Run(rv, wiki, page);

//			// create engine and load scripts
//			Gfh_script_engine engine = new Gfh_script_engine__luaj();
//			engine.Put_object("xolog", new Xoscript_log());
//			for (int i = 0; i < script_urls_len; ++i) {
//				engine.Load_script(script_urls[i]);
//			}
//
//			// call script
//			Xoscript_env env = new Xoscript_env(engine, root_dir);
//			engine.Invoke_function("xoscript__init", env);
//
//			Xoscript_page spg = new Xoscript_page(rv, env, new Xoscript_url(page.Wiki().Domain_str(), String_.new_u8(page.Url().Page_bry())));
//			engine.Invoke_function("xoscript__page_write_end", spg);
//
//			// overwrite html
//			if (spg.doc().dirty()) {
//				rv.Clear();
//				rv.Add_str_u8(spg.doc().html());
//			}
	}	
}
