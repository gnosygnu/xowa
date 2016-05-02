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
package gplx.xowa.addons.builds.centrals; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.builds.*;
import gplx.xowa.specials.*; import gplx.core.net.*; import gplx.xowa.wikis.pages.*;
import gplx.xowa.addons.builds.centrals.itms.*; import gplx.xowa.addons.builds.centrals.*;
import gplx.xowa.addons.apps.file_browsers.*;
import gplx.core.security.*; import gplx.core.progs.*; import gplx.xowa.guis.cbks.*;
public class Xoa_dashboard_special implements Xows_page {
	public static Xobc_ui_mgr Download_mgr;
	private static boolean init = true;
	public void Special__gen(Xow_wiki wiki, Xoa_page page, Xoa_url url, Xoa_ttl ttl) {
		if (init) {
			init = false;
			Download_mgr = new Xobc_ui_mgr(wiki.App().Gui__cbk_mgr());
			wiki.App().Gui__cbk_mgr().Reg(new gplx.xowa.guis.cbks.swts.Xog_cbk_wkr__swt(((Xoae_app)wiki.App()).Gui_mgr()));
		}
		Download_mgr.Clear();
		Io_url addon_dir = wiki.App().Fsys_mgr().Bin_addon_dir().GenSubDir_nest("bldr", "central");
		Xopage_html_data html_data = Write_html(wiki.App(), addon_dir);
		html_data.Apply(page);
	}
	private static Xopage_html_data Write_html(Xoa_app app, Io_url addon_dir) {
		// write body
		Xodl_itm_regy owner_itm = new Xodl_itm_regy();
		byte[] template_src = Io_mgr.Instance.LoadFilBry(addon_dir.GenSubFil_nest("tmpl", "bldr.central.main.mustache.html"));
		Bry_bfr tmp_bfr = Bry_bfr.new_();
		byte[] body = gplx.langs.mustaches.Mustache_wtr_.Write_to_bry(tmp_bfr, template_src, owner_itm);

		// write head
		Xopage_html_data rv = new Xopage_html_data(Display_ttl, body);
		rv.Head_tags().Add(Xopg_tag_itm.New_css_file(addon_dir.GenSubFil_nest("css", "bldr.central.css")));
		rv.Head_tags().Add(Xopg_tag_itm.New_js_file(addon_dir.GenSubFil_nest("js", "lib", "mustache.js")));
		rv.Head_tags().Add(Xopg_tag_itm.New_html_code(addon_dir.GenSubFil_nest("tmpl", "bldr.central.row.mustache.html"), "bldr.central.row"));
		rv.Tail_tags().Add(Xopg_tag_itm.New_js_file(addon_dir.GenSubFil_nest("js", "xo.elem.js")));
		rv.Tail_tags().Add(Xopg_tag_itm.New_js_file(addon_dir.GenSubFil_nest("js", "xo.tmpl.js")));
		rv.Tail_tags().Add(Xopg_tag_itm.New_js_file(addon_dir.GenSubFil_nest("js", "xo.notify.js")));
		rv.Tail_tags().Add(Xopg_tag_itm.New_js_file(addon_dir.GenSubFil_nest("js", "xo.server.js")));
		rv.Tail_tags().Add(Xopg_tag_itm.New_js_file(addon_dir.GenSubFil_nest("js", "bldr.central.util.js")));
		rv.Tail_tags().Add(Xopg_tag_itm.New_js_file(addon_dir.GenSubFil_nest("js", "bldr.central.js")));
		return rv;
	}

	public static final String SPECIAL_KEY = "XowaWikiDownload";
	public static final    byte[] Display_ttl = Bry_.new_a7("Download Central");
	public Xows_special_meta Special__meta() {return new Xows_special_meta(Xows_special_meta_.Src__xowa, SPECIAL_KEY);}
	public static final    Xows_page Prototype = new Xoa_dashboard_special();
	public Xows_page Special__clone() {return this;}
}
