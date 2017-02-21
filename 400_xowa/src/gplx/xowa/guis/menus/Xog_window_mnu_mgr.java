/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.guis.menus; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.xowa.guis.menus.dom.*;
import gplx.xowa.langs.*;
public class Xog_window_mnu_mgr implements Gfo_invk {
	private final    Ordered_hash hash = Ordered_hash_.New();
	public Xog_mnu_grp Browser() {return browser;} private Xog_mnu_grp browser;
	public Xog_window_mnu_mgr(Xoa_gui_mgr gui_mgr, Xog_menu_mgr menu_mgr) {
		this.gui_mgr = gui_mgr;
		browser = Get_or_new(Root_key_browser_win);
	}	private Xoa_gui_mgr gui_mgr;
	public void Init_by_kit(Xoae_app app) {
		browser.Source_exec(gui_mgr.App().Gfs_mgr());	// NOTE: build menu now; NOTE: do not set default here, or else will override user setting
		app.Cfg().Bind_many_app(this, Cfg__browser__enabled, Cfg__browser__source);
	}
	public Xog_mnu_grp Get_or_new(String key) {			
		Xog_mnu_grp rv = (Xog_mnu_grp)hash.Get_by(key);
		if (rv == null) {
			rv = new Xog_mnu_grp(gui_mgr, false, key);
			hash.Add(key, rv);
		}
		return rv;
	}
	public void Lang_changed(Xol_lang_itm lang) {
		Xog_mnu_base.Update_grp_by_lang(gui_mgr.Menu_mgr().Menu_bldr(), lang, browser);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__browser__enabled))		browser.Enabled_(m.ReadYn("v"));
		else if	(ctx.Match(k, Cfg__browser__source))		browser.Source_(m.ReadStr("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Root_key_browser_win = "main_win";

	private static final String
	  Cfg__browser__enabled	= "xowa.gui.menus.browser.enabled"
	, Cfg__browser__source	= "xowa.gui.menus.browser.source"
	;
}
