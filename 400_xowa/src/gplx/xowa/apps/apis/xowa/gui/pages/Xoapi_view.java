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
package gplx.xowa.apps.apis.xowa.gui.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*; import gplx.xowa.apps.apis.xowa.gui.*;
import gplx.gfui.*; import gplx.gfui.kits.core.*; import gplx.xowa.guis.*; import gplx.xowa.guis.views.*; import gplx.xowa.wikis.pages.*;
import gplx.langs.htmls.encoders.*;
public class Xoapi_view implements Gfo_invk {
	private Xoae_app app; private Xog_win_itm win;
	private final    Gfo_url_encoder fsys_encoder = Gfo_url_encoder_.New__fsys_wnt().Make();
	public void Init_by_kit(Xoae_app app) {
		this.app = app; this.win = app.Gui_mgr().Browser_win();
	}
	private boolean Active_tab_is_null() {return win.Tab_mgr().Active_tab_is_null();}
	public void Mode_read()				{Mode(Xopg_page_.Tid_read);}
	public void Mode_edit()				{Mode(Xopg_page_.Tid_edit);}
	public void Mode_html()				{Mode(Xopg_page_.Tid_html);}
	private void Mode(byte v)			{if (Active_tab_is_null()) return; win.Page__mode_(v);}
	public void Reload()				{if (Active_tab_is_null()) return; win.Page__reload();}
	public void Refresh()				{if (Active_tab_is_null()) return; win.Page__refresh();}
	public void Print() {
		if (this.Active_tab_is_null()) return;
		win.Active_html_box().Html_js_eval_proc_as_str(Xog_js_procs.Win__print_preview);
	}
	public void Save_as() {
		if (this.Active_tab_is_null()) return;
		Xog_tab_itm tab = win.Tab_mgr().Active_tab();
		String file_name = fsys_encoder.Encode_str(String_.new_u8(tab.Page().Ttl().Full_url())) + ".html";
		String file_url = app.Gui_mgr().Kit().New_dlg_file(Gfui_kit_.File_dlg_type_save, "Select file to save to:").Init_file_(file_name).Ask();
		if (String_.Len_eq_0(file_url)) return;
		Io_mgr.Instance.SaveFilStr(file_url, tab.Html_box().Text());
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
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk_mode_read = "mode_read", Invk_mode_edit = "mode_edit", Invk_mode_html = "mode_html"
	, Invk_reload = "reload", Invk_refresh = "refresh"
	, Invk_print = "print", Invk_save_as = "save_as"
	;
}
