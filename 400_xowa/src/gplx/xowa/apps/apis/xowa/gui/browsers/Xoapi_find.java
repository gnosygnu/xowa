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
package gplx.xowa.apps.apis.xowa.gui.browsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*; import gplx.xowa.apps.apis.xowa.gui.*;
import gplx.gfui.*; import gplx.gfui.controls.standards.*;
import gplx.xowa.wikis.pages.*; import gplx.xowa.guis.*; import gplx.xowa.guis.views.*;
public class Xoapi_find implements Gfo_invk {
	private Xog_find_box find_box;
	public void Init_by_kit(Xoae_app app) {
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
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_show = "show", Invk_show_by_paste = "show_by_paste", Invk_hide = "hide", Invk_exec = "exec", Invk_type = "type"
	, Invk_find_bwd = "find_bwd", Invk_find_fwd = "find_fwd", Invk_case_toggle = "case_toggle", Invk_wrap_toggle = "wrap_toggle";
}
class Xog_find_box {
	private Xoae_app app; private Xog_win_itm win; private GfuiTextBox find_box;
	private String prv_find_text = "";	// NOTE: must set to "", else will fail during Hide
	private boolean dir_fwd = true, case_match = false, wrap_search = true;
	public Xog_find_box(Xoae_app app) {
		this.app = app;
		this.win = app.Gui_mgr().Browser_win();
		this.find_box = win.Find_box();
	}
	public void Show() {app.Gui_mgr().Layout().Find_show();}
	public void Hide() {
		app.Gui_mgr().Layout().Find_close();
		Xog_tab_itm tab = win.Tab_mgr().Active_tab(); if (tab == Xog_tab_itm_.Null) return;
		if (tab.View_mode() == Xopg_page_.Tid_read)	// do not fire find("") for edit / html, else focus issues; DATE:2015-06-10
			Exec_find(prv_find_text, Bool_.N);
	}
	public void Show_by_paste()	{
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
		prv_find_text = find_box.Text(); 
		Exec_find(prv_find_text, Bool_.Y);
	}
	private void Exec_find(String find, boolean highlight_matches) {
		Xog_tab_itm tab = win.Tab_mgr().Active_tab(); if (tab == Xog_tab_itm_.Null) return;
		find = String_.Replace(find, "\\", "\\\\");		// NOTE: backslashes are always literal, never escape codes; EX: "C:\new" "\n" means "\n", not (byte)10; DATE:2015-08-17
		boolean find_in_hdoc = tab.View_mode() == Xopg_page_.Tid_read;
		if (find_in_hdoc)
			tab.Html_box().Html_js_eval_proc_as_str(Xog_js_procs.Win__find_in_hdoc		, find, dir_fwd, case_match, wrap_search, highlight_matches);
		else
			tab.Html_box().Html_js_eval_proc_as_str(Xog_js_procs.Win__find_in_textarea	, find, dir_fwd, case_match, wrap_search);
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
