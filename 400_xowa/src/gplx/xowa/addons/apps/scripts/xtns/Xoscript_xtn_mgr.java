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
package gplx.xowa.addons.apps.scripts.xtns; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.scripts.*;
import gplx.core.scripts.*;
import gplx.xowa.addons.apps.scripts.apis.*;
public class Xoscript_xtn_mgr {
	private Xoscript_xtn_itm root_itm;
	private final    Io_url root_dir;
	private final    Ordered_hash hash = Ordered_hash_.New();
	public Xoscript_xtn_mgr(Io_url root_dir) {
		this.root_dir = root_dir;
	}
	public void reg_xtn(String key, String file) {
		Io_url url = Io_url_.new_fil_(Xoscript_env.Resolve_file(Bool_.N, root_dir, file));
		Xoscript_xtn_itm itm = new Xoscript_xtn_itm(key, url, Gfo_script_engine_.New_by_ext(url.Ext()));
		hash.Add(key, itm);
	}
	public void Load_root() {
		Io_url[] root_urls = Io_mgr.Instance.QueryDir_args(root_dir).ExecAsUrlAry();
		int root_urls_len = root_urls.length;
		for (int i = 0; i < root_urls_len; ++i) {
			Io_url root_url = root_urls[0];
			String root_name_and_ext = root_url.NameAndExt();
			if (String_.EqAny(root_name_and_ext, "xowa.script.main.js", "xowa.script.main.lua")) {
				this.root_itm = new Xoscript_xtn_itm("xowa.root", root_url, Gfo_script_engine_.New_by_ext(root_url.Ext()));
				break;
			}
		}
		root_itm.Engine().Load_script(root_itm.Url());
		root_itm.Engine().Invoke_function("xoscript__main", this);
	}
	public void Run(Bry_bfr rv, Xow_wiki wiki, Xoa_page page) {
		int len = hash.Len();
		Xoscript_log log = new Xoscript_log();
		for (int i = 0; i < len; ++i) {
			Xoscript_xtn_itm itm = (Xoscript_xtn_itm)hash.Get_at(i);
			Gfo_script_engine engine = (Gfo_script_engine)itm.Engine();
			Xoscript_env env = new Xoscript_env(engine, itm.Url().OwnerDir());
			Xoscript_page spg = new Xoscript_page(rv, env, new Xoscript_url(page.Wiki().Domain_str(), String_.new_u8(page.Url().Page_bry())));
			engine.Put_object("xolog", log);
			engine.Load_script(itm.Url());
			try {engine.Invoke_function("xoscript__init", env);}
			catch (Exception e) {Gfo_usr_dlg_.Instance.Note_many("", "", "xoscript__init failed; url=~{0} err=~{1}", itm.Url(), Err_.Message_lang(e));}
			try {engine.Invoke_function("xoscript__page_write_end", spg);}
			catch (Exception e) {Gfo_usr_dlg_.Instance.Note_many("", "", "xoscript__page_write_end failed; url=~{0} err=~{1}", itm.Url(), Err_.Message_lang(e));}

			// overwrite html
			if (spg.doc().dirty()) {
				rv.Clear();
				rv.Add_str_u8(spg.doc().html());
			}
		}
	}	
}
