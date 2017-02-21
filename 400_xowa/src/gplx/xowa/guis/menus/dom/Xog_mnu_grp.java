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
package gplx.xowa.guis.menus.dom; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*; import gplx.xowa.guis.menus.*;
import gplx.gfui.*; import gplx.gfui.kits.core.*; import gplx.gfui.controls.standards.*;
import gplx.xowa.apps.*; import gplx.xowa.apps.gfs.*;
import gplx.xowa.guis.views.*;
public class Xog_mnu_grp extends Xog_mnu_base {
	public Xog_mnu_grp(Xoa_gui_mgr gui_mgr, boolean mnu_is_popup, String key) {
		this.app = gui_mgr.App(); this.mnu_is_popup = mnu_is_popup; this.key = key;
		this.Ctor(gui_mgr);
	}	private Xoae_app app;
	public String Key() {return key;} private String key; private boolean mnu_is_popup;
	public Gfui_mnu_grp Under_mnu() {
		if (under_mnu.Disposed()) Build();	// NOTE: menu may be disposed when calling .dispose on Swt_html; rebuild if needed; DATE:2014-07-09
		return under_mnu;
	}	private Gfui_mnu_grp under_mnu; 				
	@Override public boolean Tid_is_app_menu_grp() {return !mnu_is_popup;}
	public boolean Enabled() {return enabled;} private boolean enabled = true;
	public void Enabled_(boolean v) {
		this.enabled = v;
		if (under_mnu != null)		// null when changed by cfg.gfs
			under_mnu.Itms_clear();
		if (v)
			this.Source_exec(app.Gfs_mgr());
		else 
			this.Clear();
	}
	public String Source() {return source;} private String source = "";	// NOTE: default to "" not null, else init will try to run "clear;\nnullbuild;"
	public void Source_(String v) {this.source = v; this.Source_exec(app.Gfs_mgr());}
	public Object Source_exec(Xoa_gfs_mgr gfs_mgr) {return Source_exec(gfs_mgr, source);}
	private Object Source_exec(Xoa_gfs_mgr gfs_mgr, String v) {
		if (!enabled) return Gfo_invk_.Rv_handled;
		String script = "clear;\n" + v + "build;";
		return gfs_mgr.Run_str_for(this, script);
	}
	public void Build() {
		Xoa_gui_mgr gui_mgr = app.Gui_mgr(); Gfui_kit kit = gui_mgr.Kit(); Xog_win_itm win = gui_mgr.Browser_win();
		if (!kit.Kit_mode__ready()) return;	// NOTE: .gfs will fire Build before Kit.Init; check that kit is inited
		if (under_mnu == null) {
			if (mnu_is_popup) {
				if		(String_.Eq(key, Xog_popup_mnu_mgr.Root_key_tabs_btns))
					under_mnu = kit.New_mnu_popup(key, win.Tab_mgr().Tab_mgr());
				else
					under_mnu = kit.New_mnu_popup(key, win.Win_box());
			}
			else
				under_mnu = kit.New_mnu_bar(key, win.Win_box());
		}
		Xog_mnu_bldr bldr = gui_mgr.Menu_mgr().Menu_bldr();
		bldr.Build(under_mnu, this);
		Xog_mnu_base.Update_grp_by_lang(bldr, app.Usere().Lang(), this);	// NOTE: always set lang after rebuild; else changes in home/wiki/Options/Menus will show blank captions; DATE:2014-06-05
		if (mnu_is_popup) {
			boolean rebind_to_win = false;
			if		(String_.Eq(key, Xog_popup_mnu_mgr.Root_key_tabs_btns)) {
				kit.Set_mnu_popup(win.Tab_mgr().Tab_mgr(), under_mnu);
				rebind_to_win = true;
			}
			else if (String_.Eq(key, Xog_popup_mnu_mgr.Root_key_html_page))	// rebind new popup box; DATE:2014-05-16
				Bind_popup_menu(gui_mgr); 
			else if (String_.Eq(key, Xog_popup_mnu_mgr.Root_key_html_link))
				rebind_to_win = true;
			else if (String_.Eq(key, Xog_popup_mnu_mgr.Root_key_prog))
				kit.Set_mnu_popup(win.Prog_box(), under_mnu);
			else if (String_.Eq(key, Xog_popup_mnu_mgr.Root_key_info))
				kit.Set_mnu_popup(win.Info_box(), under_mnu);
			if (rebind_to_win) {	// WORKAROUND: SWT seems to bind popup menus to window; always rebind window to html_page; DATE:2014-05-17
				kit.Set_mnu_popup(win.Win_box(), gui_mgr.Menu_mgr().Popup().Html_page().Under_mnu());
			}
		}
	}
	private void Bind_popup_menu(Xoa_gui_mgr gui_mgr) {
		Gfui_kit kit = gui_mgr.Kit(); Xog_win_itm win = gui_mgr.Browser_win(); Xog_tab_mgr tab_mgr = win.Tab_mgr();
		int tabs_len = tab_mgr.Tabs_len();
		for (int i = 0; i < tabs_len; i++) {
			Xog_tab_itm tab = tab_mgr.Tabs_get_at(i);
			kit.Set_mnu_popup(tab.Html_box(), under_mnu);
		}
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_build))					this.Build();
		else	return super.Invk(ctx, ikey, k, m);
		return this;
	}
	private static final String Invk_build = "build";
}
