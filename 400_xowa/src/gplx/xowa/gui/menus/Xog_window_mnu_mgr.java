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
package gplx.xowa.gui.menus; import gplx.*; import gplx.xowa.*; import gplx.xowa.gui.*;
import gplx.xowa.gui.menus.dom.*;
public class Xog_window_mnu_mgr implements GfoInvkAble {
	private Ordered_hash hash = Ordered_hash_.new_();
	public Xog_mnu_grp Browser() {return browser;} private Xog_mnu_grp browser;
	public Xog_window_mnu_mgr(Xoa_gui_mgr gui_mgr, Xog_menu_mgr menu_mgr) {
		this.gui_mgr = gui_mgr;
		browser = Get_or_new(Root_key_browser_win).Source_default_(Xog_menu_mgr_src.Browser_win);	// NOTE: set default here (fires before cfg)
	}	private Xoa_gui_mgr gui_mgr;
	public void Init_by_kit() {
		browser.Source_exec(gui_mgr.App().Gfs_mgr());	// NOTE: build menu now; NOTE: do not set default here, or else will override user setting
	}
	public Xog_mnu_grp Get_or_new(String key) {			
		Xog_mnu_grp rv = (Xog_mnu_grp)hash.Get_by(key);
		if (rv == null) {
			rv = new Xog_mnu_grp(gui_mgr, false, key);
			hash.Add(key, rv);
		}
		return rv;
	}
	public void Lang_changed(Xol_lang lang) {
		Xog_mnu_base.Update_grp_by_lang(gui_mgr.Menu_mgr().Menu_bldr(), lang, browser);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))		return Get_or_new(m.ReadStr("v"));
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_get = "get";
	public static final String Root_key_browser_win = "main_win";
}
