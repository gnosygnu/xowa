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
import gplx.gfui.*; import gplx.gfui.envs.*; import gplx.gfui.controls.standards.*;
import gplx.xowa.guis.views.*; import gplx.core.envs.*;
public class Xoapi_url implements Gfo_invk {
	private Xoae_app app;
	public void Init_by_kit(Xoae_app app) {
		this.app = app;
	}
	private GfuiComboBox Url_box()		{return app.Gui_mgr().Browser_win().Url_box();}
	public void Focus()					{
		GfuiComboBox url_box = this.Url_box();
		url_box.Focus(); 
		url_box.Sel_(0, String_.Len(this.Url_box().Text()));
	}
	public void Exec()					{Exec_wkr(Bool_.N, this.Url_box().Text());}
	public void Exec_by_paste()			{Exec_wkr(Bool_.N, ClipboardAdp_.GetText());}
	public void Exec_new_tab_by_paste() {Exec_wkr(Bool_.Y, ClipboardAdp_.GetText());}
	public void Restore() {
		Xog_tab_itm tab = app.Gui_mgr().Browser_win().Active_tab(); if (tab == Xog_tab_itm_.Null) return;
		this.Url_box().Text_(tab.Page().Url().To_str());
	}
	void Type() {
		app.Addon_mgr().Itms__search__urlbar().Search();
	}
	private void Exec_wkr(boolean new_tab, String urls_text) {
		if (Op_sys.Cur().Tid_is_wnt())
			urls_text = String_.Replace(urls_text, Op_sys.Wnt.Nl_str(), Op_sys.Lnx.Nl_str());
		String[] urls = String_.Split(String_.Trim(urls_text), Op_sys.Lnx.Nl_str());
		int urls_len = urls.length;
		if (urls_len == 0) return;
		if (urls_len == 1) {								// 1 url; most cases
			String url = urls[0];
			gplx.core.threads.Thread_adp_.Sleep(1);			// HACK: sleep 1 ms, else rapid keystrokes may cause last keystroke to not register; EX: typing "w:" may show just "w" instead of "w:"; DATE:2016-03-27
			GfuiComboBox combo = this.Url_box();
			String[] itms_ary = combo.DataSource_as_str_ary();
			if (	itms_ary.length > 0						// results exist...
				&&	combo.List_visible()) {					// and dropdown is visible; use selected-item
				int sel_idx = combo.List_sel_idx();			// get selected item
				if (sel_idx == -1) sel_idx = 0;				// if nothing selected, default to 1st; allows typing first few characters and picking 1st item from list
				String itms_sel = itms_ary[sel_idx];
				// if	(String_.Has(String_.Lower(itms_sel), String_.Lower(url)))	// make sure itms_sel contains url; handles slow machines, where dropdown may not be updated yet
				url = Xog_win_itm.Remove_redirect_if_exists(itms_sel);
			}
			combo.Text_(url);
			app.Gui_mgr().Browser_win().Page__navigate_by_url_bar(url);
		}
		else {
			for (int i = 0; i < urls_len; i++) {
				String url = urls[i];
				if (String_.Has_at_bgn(url, "\"") &&  String_.Has_at_bgn(url, "\""))
					url = String_.Mid(url, 1, String_.Len(url) - 1);
				app.Gui_mgr().Browser_win().Tab_mgr().Tabs_new_link(url, false);
			}
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_focus)) 						this.Focus();
		else if	(ctx.Match(k, Invk_exec)) 						this.Exec();
		else if	(ctx.Match(k, Invk_exec_by_paste)) 				this.Exec_by_paste();
		else if	(ctx.Match(k, Invk_exec_new_tab_by_paste)) 		this.Exec_new_tab_by_paste();
		else if	(ctx.Match(k, Invk_restore)) 					this.Restore();
		else if	(ctx.Match(k, Invk_type)) 						this.Type();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_focus = "focus", Invk_exec_by_paste = "exec_by_paste", Invk_exec_new_tab_by_paste = "exec_new_tab_by_paste", Invk_restore = "restore", Invk_type = "type";
	public static final String Invk_exec = "exec";
}
