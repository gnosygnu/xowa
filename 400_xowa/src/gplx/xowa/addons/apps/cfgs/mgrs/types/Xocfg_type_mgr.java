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
		this.Lists__add("list:xowa.app.startup.pages.type", "blank", "xowa", "absolute", "previous", "custom");
		this.Lists__add("list:xowa.gui.window.html_box.adj_type", "none", "absolute", "relative");
		this.Lists__add("list:xowa.wiki.dbs.html.basic.html_mode", Keyval_.new_("shown", "Shown"), Keyval_.new_("hdump_save", "Saved for HTML DB"), Keyval_.new_("hdump_load", "Loaded by HTML DB"));
		this.Lists__add("list:xowa.wiki.database.general.zip_mode", "text", "gzip", "bz2", "xz");
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
		list_hash.Add(key, itms);
	}
	public Keyval[] Lists__get(String key) {
		return (Keyval[])list_hash.Get_by_or_fail(key);
	}
}
