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
package gplx.xowa.apis.xowa.gui.browsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.apis.*; import gplx.xowa.apis.xowa.*; import gplx.xowa.apis.xowa.gui.*;
import gplx.gfui.*; import gplx.xowa.pages.*; import gplx.xowa.gui.*; import gplx.xowa.gui.views.*;
public class Xoapi_find implements GfoInvkAble {
	private Xog_find_box find_box;
	public void Init_by_kit(Xoa_app app) {
		find_box = new Xog_find_box(app);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_show)) 						find_box.Show();
		else if	(ctx.Match(k, Invk_show_by_paste)) 				find_box.Show_by_paste();
		else if	(ctx.Match(k, Invk_exec)) 						find_box.Exec();
		else if	(ctx.Match(k, Invk_type)) 						find_box.Exec();	// NOTE: same as exec; provided so that exec doesn't accidentally overwrite auto-type for find
		else if	(ctx.Match(k, Invk_find_fwd)) 					find_box.Exec_by_dir(Bool_.Y);
		else if	(ctx.Match(k, Invk_find_bwd)) 					find_box.Exec_by_dir(Bool_.N);
		else if	(ctx.Match(k, Invk_case_toggle)) 				find_box.Case_toggle();
		else if	(ctx.Match(k, Invk_wrap_toggle)) 				find_box.Wrap_toggle();
		else if	(ctx.Match(k, Invk_hide)) 						find_box.Hide();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_show = "show", Invk_show_by_paste = "show_by_paste", Invk_hide = "hide", Invk_exec = "exec", Invk_type = "type"
	, Invk_find_bwd = "find_bwd", Invk_find_fwd = "find_fwd", Invk_case_toggle = "case_toggle", Invk_wrap_toggle = "wrap_toggle";
}
class Xog_find_box {
	private Xoa_app app; private Xog_win_itm win; private GfuiTextBox find_box;
	private boolean dir_fwd = true, case_match = false, wrap_search = true;
	public Xog_find_box(Xoa_app app) {
		this.app = app;
		this.win = app.Gui_mgr().Browser_win();
		this.find_box = win.Find_box();
	}
	public void Show() {app.Gui_mgr().Layout().Find_show();}
	public void Hide() {app.Gui_mgr().Layout().Find_close();}
	public void Show_by_paste()		{
		this.Show();
		if (win.Tab_mgr().Active_tab_is_null()) return;	// if no active_tab, just show box; don't try to copy what's on tab;
		find_box.Text_(win.Active_html_itm().Html_selected_get_text_or_href());
	}
	public void Exec_by_dir(boolean fwd) {
		boolean changed = dir_fwd != fwd;
		dir_fwd = fwd;
		Exec();
		if (changed)	// clicking on buttons calls Exec_by_dir; in case of repeated clicks on same button, don't flash changed message again
			win.Usr_dlg().Prog_direct("Find direction changed to " + (fwd ? "forward" : "backward"));
	}
	public void Exec() {
		Xog_tab_itm tab = win.Tab_mgr().Active_tab(); if (tab == Xog_tab_itm_.Null) return;
		String elem_id = tab.View_mode() == Xopg_view_mode.Tid_read 
			? Gfui_html.Elem_id_body
			: Xog_html_itm.Elem_id__xowa_edit_data_box
			;
		tab.Html_box().Html_doc_find(elem_id, find_box.Text(), dir_fwd, case_match, wrap_search);
		win.Usr_dlg().Prog_direct("");
	}
	public void Case_toggle() {
		case_match = !case_match;
		win.Usr_dlg().Prog_direct("Case match " + (case_match ? "enabled" : "disabled"));
	}
	public void Wrap_toggle() {
		wrap_search = !wrap_search;
		win.Usr_dlg().Prog_direct("Wrap search " + (wrap_search ? "enabled" : "disabled"));
	}
}
