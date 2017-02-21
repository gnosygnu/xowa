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
import gplx.gfui.*; import gplx.gfui.controls.gxws.*; import gplx.gfui.controls.standards.*;
import gplx.xowa.wikis.pages.*; import gplx.xowa.guis.*; import gplx.xowa.guis.views.*;
public class Xoapi_html_box implements Gfo_invk {
	private Xog_win_itm win;
	public void Init_by_kit(Xoae_app app) {this.win = app.Gui_mgr().Browser_win();}
	public void Focus() {
		Xog_tab_itm tab = win.Active_tab(); if (tab == Xog_tab_itm_.Null) return;
		Gfui_html html_box = tab.Html_itm().Html_box();
		html_box.Focus();
		if (tab.View_mode() != Xopg_page_.Tid_read)	// if edit / html, place focus in edit box
			html_box.Html_js_eval_proc_as_str(Xog_js_procs.Doc__elem_focus, Xog_html_itm.Elem_id__xowa_edit_data_box);
	}
	public void Selection_focus() {
		Xog_tab_itm tab = win.Active_tab(); if (tab == Xog_tab_itm_.Null) return;
		Gfui_html html_box = tab.Html_itm().Html_box();
		html_box.Html_js_eval_proc_as_str(Xog_js_procs.Selection__toggle_focus_for_anchor);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_focus)) 							this.Focus();
		else if	(ctx.Match(k, Invk_selection_focus_toggle)) 		this.Selection_focus();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_focus = "focus", Invk_selection_focus_toggle = "selection_focus_toggle";
}
