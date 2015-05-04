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
public class Xog_menu_mgr implements GfoInvkAble {
	private Xoae_app app;
	public Xog_menu_mgr(Xoa_gui_mgr gui_mgr) {
		menu_bldr = new Xog_mnu_bldr();
		regy = new Xog_mnu_regy(gui_mgr);
		popup_mnu_mgr = new Xog_popup_mnu_mgr(gui_mgr, this);
		window_mnu_mgr = new Xog_window_mnu_mgr(gui_mgr, this);
	}
	public Xog_mnu_regy Regy() {return regy;} private Xog_mnu_regy regy;
	public Xog_popup_mnu_mgr Popup() {return popup_mnu_mgr;} private Xog_popup_mnu_mgr popup_mnu_mgr;
	public Xog_window_mnu_mgr Window() {return window_mnu_mgr;} private Xog_window_mnu_mgr window_mnu_mgr;
	public Xog_mnu_bldr Menu_bldr() {return menu_bldr;} private Xog_mnu_bldr menu_bldr;
	public void Init_by_app(Xoae_app app) {
		this.app = app;
		regy.Init_by_app(app);
	}
	public void Init_by_kit() {
		try {
			if (!app.App_type().Uid_is_gui()) return;	// NOTE: do not try to initialize menu if http_server; will fail in headless mode when it tries to load SWT images; DATE:2015-03-27
			popup_mnu_mgr.Init_by_kit();
			window_mnu_mgr.Init_by_kit();
			Lang_changed(app.User().Lang());
		}
		catch (Exception e) {	// ignore errors while loading custom menus, else fatal error; DATE:2014-07-01
			app.Usr_dlg().Warn_many("", "", "error while loading menus; err=~{0}", Err_.Message_gplx(e));
		}
	}
	public void Lang_changed(Xol_lang lang) {
		window_mnu_mgr.Lang_changed(lang);
		popup_mnu_mgr.Lang_changed(lang);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_contexts))			return popup_mnu_mgr;
		else if	(ctx.Match(k, Invk_windows))			return window_mnu_mgr;
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_contexts = "contexts", Invk_windows = "windows";
}
