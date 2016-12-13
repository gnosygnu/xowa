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
package gplx.xowa.addons.apps.cfgs.mgrs.types; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.mgrs.*;
public class Xocfg_type_mgr {
	private final    Ordered_hash list_hash = Ordered_hash_.New();
	public Xocfg_type_mgr() {
		this.Lists__add("list:xowa.app.security.privacy.load_mode", "mem", "url");
		this.Lists__add("list:xowa.app.startup.window.mode", "previous", "maximized", "absolute", "relative", "default");
		this.Lists__add("list:xowa.app.startup.pages.type", "blank", "xowa", "previous", "custom");
		this.Lists__add("list:xowa.gui.window.html_box.adj_type", "none", "relative", "absolute");
		this.Lists__add("list:xowa.wiki.database.general.zip_mode", "text", "gzip", "bzip2", "xz");
		this.Lists__add("list:xowa.html.wiki.portal.missing_class", Keyval_.new_("", "Show as blue link"), Keyval_.new_("new", "Show as red link"), Keyval_.new_("xowa_display_none", "Hide"));
		this.Lists__add("list:xowa.html.category.basic.missing_class", "normal", "hide", "red_link");
		this.Lists__add("list:xowa.html.tidy.general.engine", "tidy", "jtidy");
		this.Lists__add("list:xowa.addon.http_server.general.file_retrieve_mode", Keyval_.new_("wait"), Keyval_.new_("skip"), Keyval_.new_("async_server", "async server"));
		this.Lists__add("list:xowa.addon.search_suggest.html_bar.search_mode", "Search", "AllPages", "AllPages_(v2)");
		this.Lists__add("list:xowa.addon.math.general.renderer", "MathJax", "LaTeX");
		this.Lists__add("list:xowa.addon.scribunto.general.engine", "luaj", "lua");
	}
	public void	Lists__add(String key, String... vals) {
		int len = vals.length;
		Keyval[] itms = new Keyval[len];
		for (int i = 0; i < len; i++) {
			itms[i] = Keyval_.new_(vals[i]);
		}
		Lists__add(key, itms);
	}
	public void	Lists__add(String key, Keyval... itms) {
		if (!list_hash.Has(key)) // ignore multiple calls from Init_by_wiki; EX: Xow_hdump_mode
			list_hash.Add(key, itms);
	}
	public Keyval[] Lists__get(String key) {
		return (Keyval[])list_hash.Get_by_or_fail(key);
	}
}
