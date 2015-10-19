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
package gplx.xowa.apps.apis.xowa.gui.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*; import gplx.xowa.apps.apis.xowa.gui.*;
import gplx.xowa.guis.*; import gplx.xowa.guis.views.*; import gplx.xowa.wikis.pages.*;
public class Xoapi_edit implements GfoInvkAble {
	private Xog_win_itm win;
	public void Init_by_kit(Xoae_app app) {
		win = app.Gui_mgr().Browser_win();
	}
	private boolean Active_tab_is_null() {return win.Tab_mgr().Active_tab_is_null();}
	private boolean Active_tab_is_edit() {return !win.Tab_mgr().Active_tab_is_null() && win.Tab_mgr().Active_tab().View_mode() == Xopg_page_.Tid_edit;}
	public void Copy()			{if (Active_tab_is_null()) return; win.Kit().Clipboard().Copy(win.Active_html_itm().Html_selected_get_text_or_href());}
	public void Select_all()	{if (Active_tab_is_null()) return; GfoInvkAble_.InvkCmd(win.Win_box().Kit().Clipboard(), gplx.gfui.Gfui_clipboard_.Invk_select_all);}
	public void Save()			{if (!Active_tab_is_edit()) return; Xog_tab_itm_edit_mgr.Save(win.Active_tab(), false);}
	public void Save_draft()	{if (!Active_tab_is_edit()) return; Xog_tab_itm_edit_mgr.Save(win.Active_tab(), true);}
	public void Preview()		{if (!Active_tab_is_edit()) return; Xog_tab_itm_edit_mgr.Preview(win.Active_tab());}
	public void Dbg_tmpl()		{if (!Active_tab_is_edit()) return; Xog_tab_itm_edit_mgr.Debug(win, Xopg_page_.Tid_edit);}
	public void Dbg_html()		{if (!Active_tab_is_edit()) return; Xog_tab_itm_edit_mgr.Debug(win, Xopg_page_.Tid_html);}
	public void Focus_edit_box(){if (!Active_tab_is_edit()) return; Xog_tab_itm_edit_mgr.Focus(win, Xog_html_itm.Elem_id__xowa_edit_data_box);}
	public void Exec() {
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_copy)) 					this.Copy();
		else if	(ctx.Match(k, Invk_select_all)) 			this.Select_all();
		else if	(ctx.Match(k, Invk_save)) 					this.Save();
		else if	(ctx.Match(k, Invk_save_draft)) 			this.Save_draft();
		else if	(ctx.Match(k, Invk_preview)) 				this.Preview();
		else if	(ctx.Match(k, Invk_focus_edit_box)) 		this.Focus_edit_box();
		else if	(ctx.Match(k, Invk_dbg_tmpl)) 				this.Dbg_tmpl();
		else if	(ctx.Match(k, Invk_dbg_html)) 				this.Dbg_html();
		else if	(ctx.Match(k, Invk_exec)) 					this.Exec();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk_copy = "copy"
	, Invk_select_all = "select_all"
	, Invk_save = "save", Invk_save_draft = "save_draft", Invk_preview = "preview"
	, Invk_focus_edit_box = "focus_edit_box"
	, Invk_dbg_tmpl = "dbg_tmpl", Invk_dbg_html = "dbg_html", Invk_exec = "exec"		
	;
}
