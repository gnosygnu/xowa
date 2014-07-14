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
package gplx.xowa.gui.menus.dom; import gplx.*; import gplx.xowa.*; import gplx.xowa.gui.*; import gplx.xowa.gui.menus.*;
import gplx.gfui.*;
import gplx.xowa.apps.*; import gplx.xowa.gui.views.*;
public class Xog_mnu_grp extends Xog_mnu_base {
	public Xog_mnu_grp(Xoa_gui_mgr gui_mgr, boolean mnu_is_popup, String key) {
		this.app = gui_mgr.App(); this.mnu_is_popup = mnu_is_popup; this.key = key;
		this.Ctor(gui_mgr);
	}	private Xoa_app app;
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
		if (!mnu_is_popup && app.Gui_mgr().Browser_win().Win_box() != null)
			GfoInvkAble_.InvkCmd(app.Gui_mgr().Browser_win(), gplx.gfui.Gfui_html.Evt_win_resized);
	}
	public String Source() {return source;} private String source;
	public String Source_default() {return source_default;} public Xog_mnu_grp Source_default_(String v) {source_default = source = v; return this;} private String source_default;
	private Xog_mnu_grp Source_(Xoa_gfs_mgr gfs_mgr, String v) {
		Object rslt = Source_exec(gfs_mgr, v);
		if (rslt != GfoInvkAble_.Rv_error)
			source = v;
		return this;
	} 
	public Object Source_exec(Xoa_gfs_mgr gfs_mgr) {return Source_exec(gfs_mgr, source);}
	private Object Source_exec(Xoa_gfs_mgr gfs_mgr, String v) {
		if (!enabled) return GfoInvkAble_.Rv_handled;
		String script = "clear;\n" + v + "build;";
		return gfs_mgr.Run_str_for(this, script);
	}
	public void Build() {
		Xoa_gui_mgr gui_mgr = app.Gui_mgr(); Gfui_kit kit = gui_mgr.Kit(); Xog_win_itm win = gui_mgr.Browser_win();
		if (!kit.Kit_init_done()) return;	// NOTE: .gfs will fire Build before Kit.Init; check that kit is inited
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
		Xog_mnu_base.Update_grp_by_lang(bldr, app.User().Lang(), this);	// NOTE: always set lang after rebuild; else changes in Help:Options/Menus will show blank captions; DATE:2014-06-05
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
		else if	(ctx.Match(k, Invk_enabled))				return Yn.X_to_str(enabled);
		else if	(ctx.Match(k, Invk_enabled_))				this.Enabled_(m.ReadYn("v"));
		else if	(ctx.Match(k, Invk_source))					return source;
		else if	(ctx.Match(k, Invk_source_))				this.Source_(app.Gfs_mgr(), m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_source_default))			return source_default;
		else if	(ctx.Match(k, Invk_source_default_))		source_default = m.ReadStr("v");
		else	return super.Invk(ctx, ikey, k, m);
		return this;
	}	private static final String Invk_build = "build", Invk_enabled = "enabled", Invk_enabled_ = "enabled_", Invk_source = "source", Invk_source_ = "source_", Invk_source_default = "source_default", Invk_source_default_ = "source_default_";
}
