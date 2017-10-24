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
import gplx.gfui.*; import gplx.gfui.kits.core.*; import gplx.xowa.guis.*; import gplx.xowa.guis.views.*; import gplx.core.envs.*;
public class Xoapi_selection implements Gfo_invk {
	private Xoae_app app; private Xog_win_itm win;
	public void Init_by_kit(Xoae_app app) {
		this.app = app;
		win = app.Gui_mgr().Browser_win();
	}
	private boolean Active_tab_is_null() {return win.Tab_mgr().Active_tab_is_null();}
	public void Copy()			{if (Active_tab_is_null()) return; win.Kit().Clipboard().Copy(win.Active_html_itm().Html_selected_get_text_or_href());}
	public void Select_all()	{if (Active_tab_is_null()) return; Gfo_invk_.Invk_by_key(win.Win_box().Kit().Clipboard(), Gfui_clipboard_.Invk_select_all);}
	public void Save_file_as() {
		if (this.Active_tab_is_null()) return;
		Xog_html_itm html_itm = win.Tab_mgr().Active_tab().Html_itm();
		String src = html_itm.Html_selected_get_src_or_empty();
		if (String_.Len_eq_0(src)) {app.Usr_dlg().Prog_many("", "", "no file selected: tab=~{0}", html_itm.Owner_tab().Page().Url().To_str()); return;}
		Io_url src_url = Io_url_.New__http_or_fail(src);
		String trg_name = src_url.NameAndExt();
		if (String_.Has(src, "/thumb/")) trg_name = src_url.OwnerDir().NameOnly();
		String trg = app.Gui_mgr().Kit().New_dlg_file(Gfui_kit_.File_dlg_type_save, "Select file to save to:").Init_file_(trg_name).Ask();
		if (String_.Len_eq_0(trg)) return;
		Io_url trg_url = Io_url_.new_fil_(trg); 
		Io_mgr.Instance.CopyFil(src_url, trg_url, true);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_copy)) 					this.Copy();
		else if	(ctx.Match(k, Invk_select_all)) 			this.Select_all();
		else if	(ctx.Match(k, Invk_save_file_as)) 			this.Save_file_as();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk_copy = "copy", Invk_select_all = "select_all", Invk_save_file_as = "save_file_as"
	;
}
