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
package gplx.xowa.apis.xowa.gui.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.apis.*; import gplx.xowa.apis.xowa.*; import gplx.xowa.apis.xowa.gui.*;
import gplx.gfui.*; import gplx.xowa.gui.*; import gplx.xowa.gui.views.*; import gplx.xowa.pages.*;
public class Xoapi_view implements GfoInvkAble {
	private Xoae_app app; private Xog_win_itm win;
	public void Init_by_kit(Xoae_app app) {
		this.app = app; this.win = app.Gui_mgr().Browser_win();
	}
	private boolean Active_tab_is_null() {return win.Tab_mgr().Active_tab_is_null();}
	public void Mode_read()				{Mode(Xopg_view_mode.Tid_read);}
	public void Mode_edit()				{Mode(Xopg_view_mode.Tid_edit);}
	public void Mode_html()				{Mode(Xopg_view_mode.Tid_html);}
	private void Mode(byte v)			{if (Active_tab_is_null()) return; win.Page__mode_(v);}
	public void Reload()				{if (Active_tab_is_null()) return; win.Page__reload();}
	public void Refresh()				{if (Active_tab_is_null()) return; win.Page__refresh();}
	public void Print() {
		if (this.Active_tab_is_null()) return;
		win.Active_html_box().Html_window_print_preview();
	}
	public void Save_as() {
		if (this.Active_tab_is_null()) return;
		Xog_tab_itm tab = win.Tab_mgr().Active_tab();
		String file_name = Xoa_app_.Utl__encoder_mgr().Fsys_safe().Encode_str(String_.new_u8(tab.Page().Ttl().Full_url())) + ".html";
		String file_url = app.Gui_mgr().Kit().New_dlg_file(Gfui_kit_.File_dlg_type_save, "Select file to save to:").Init_file_(file_name).Ask();
		if (String_.Len_eq_0(file_url)) return;
		Io_mgr.I.SaveFilStr(file_url, tab.Html_box().Text());
		app.Usr_dlg().Prog_many("", "", "saved page: file=~{0}", file_url);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_mode_read)) 				this.Mode_read();
		else if	(ctx.Match(k, Invk_mode_edit)) 				this.Mode_edit();
		else if	(ctx.Match(k, Invk_mode_html)) 				this.Mode_html();
		else if	(ctx.Match(k, Invk_reload)) 				this.Reload();
		else if	(ctx.Match(k, Invk_refresh)) 				this.Refresh();
		else if	(ctx.Match(k, Invk_print)) 					this.Print();
		else if	(ctx.Match(k, Invk_save_as)) 				this.Save_as();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk_mode_read = "mode_read", Invk_mode_edit = "mode_edit", Invk_mode_html = "mode_html"
	, Invk_reload = "reload", Invk_refresh = "refresh"
	, Invk_print = "print", Invk_save_as = "save_as"
	;
}
