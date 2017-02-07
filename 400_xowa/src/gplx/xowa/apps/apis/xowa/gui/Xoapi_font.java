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
package gplx.xowa.apps.apis.xowa.gui; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*;
import gplx.xowa.apps.cfgs.*; import gplx.xowa.htmls.*;
public class Xoapi_font implements Gfo_invk {
	private Xoae_app app;
	public void Init_by_kit(Xoae_app app) {
		this.app = app;
	}
	public void Increase() {Adj(1);}
	public void Decrease() {Adj(-1);}
	public void Reset() {Set(false, Xoh_page_mgr.Font_size_default, Xocfg_win.Font_size_default);}
	public void Adj(int adj) {
		float html_font_size = app.Html_mgr().Page_mgr().Font_size() + adj;
		float gui_font_size = app.Gui_mgr().Win_cfg().Font().Size() + adj; // (html_font_size * .75f) - 4;	// .75f b/c 16px = 12 pt; -4 b/c gui font is currently 4 pt smaller 
		if (html_font_size < 1 || gui_font_size < 1) return;
		Set(true, html_font_size, gui_font_size);
	}
	private void Set(boolean enabled, float html_font_size, float gui_font_size) {
		if (html_font_size <= 0) return;	// font must be positive
		app.Cfg().Set_bool_app(gplx.xowa.htmls.Xoh_page_mgr.Cfg__font_enabled, enabled);
		app.Cfg().Set_float_app(gplx.xowa.htmls.Xoh_page_mgr.Cfg__font_size, html_font_size);
		app.Cfg().Set_float_app(gplx.xowa.guis.langs.Xol_font_info.Cfg__font_size, gui_font_size);
		app.Gui_mgr().Browser_win().Page__reload();	// NOTE: force reload; needed if viewing home/wiki/Options/HTML, else Font size won't update
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_increase))	 		this.Increase();
		else if	(ctx.Match(k, Invk_decrease))	 		this.Decrease();
		else if	(ctx.Match(k, Invk_reset))	 			this.Reset();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk_increase = "increase", Invk_decrease = "decrease", Invk_reset = "reset"
	;
}
