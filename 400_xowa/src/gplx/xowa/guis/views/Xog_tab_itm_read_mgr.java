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
package gplx.xowa.guis.views; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.core.threads.*;
import gplx.gfui.*; import gplx.gfui.controls.standards.*; import gplx.xowa.guis.langs.*; import gplx.xowa.guis.history.*;
import gplx.xowa.wikis.pages.lnkis.*; import gplx.xowa.wikis.pages.*;
import gplx.xowa.guis.views.url_box_fmts.*;
public class Xog_tab_itm_read_mgr {
	public static void Show_page(Xog_tab_itm tab, Xoae_page new_page, boolean reset_to_read) {Show_page(tab, new_page, reset_to_read, false, false, Xog_history_stack.Nav_fwd);}
	public static void Show_page(Xog_tab_itm tab, Xoae_page new_page, boolean reset_to_read, boolean new_page_is_same, boolean show_is_err, byte history_nav_type) {
		if (reset_to_read)
			tab.View_mode_(Xopg_page_.Tid_read);
		if (new_page.Url().Qargs_mgr().Match(Xoa_url_.Qarg__action, Xoa_url_.Qarg__action__edit)) 
			tab.View_mode_(Xopg_page_.Tid_edit);

		Xoae_page cur_page = tab.Page(); Xog_html_itm html_itm = tab.Html_itm(); Gfui_html html_box = html_itm.Html_box();
		Xog_win_itm win = tab.Tab_mgr().Win();
		if (cur_page != null && !new_page_is_same) {	// if new_page_is_same, don't update DocPos; will "lose" current position
			cur_page.Html_data().Bmk_pos_(html_box.Html_js_eval_proc_as_str(Xog_js_procs.Win__vpos_get));
			tab.History_mgr().Update_html_doc_pos(cur_page, history_nav_type);	// HACK: old_page is already in stack, but need to update its hdoc_pos
		}
		win.Usr_dlg().Prog_none("", "", "locating images");
		Xowe_wiki wiki = new_page.Wikie();
		try	{tab.Html_itm().Show(new_page);}
		catch (Exception e) {
			if (String_.Eq(Err_.Message_lang(e), "class org.eclipse.swt.SWTException Widget is disposed")) return; // ignore errors caused by user closing tab early; DATE:2014-07-26
			if (show_is_err) {	// trying to show error page, but failed; don't show again, else recursion until out of memory; TODO_OLD:always load error page; no reason it should fail; WHEN:html_skin; DATE:2014-06-08
				Gfo_usr_dlg_.Instance.Warn_many("", "", "fatal error trying to load error page; page=~{0} err=~{1}" + new_page.Url().To_str(), Err_.Message_gplx_full(e));
				return;
			}
			else
				Show_page_err(win, tab, wiki, new_page.Url(), new_page.Ttl(), e);
			return;
		}
		tab.Page_(new_page);
		if (tab == tab.Tab_mgr().Active_tab())
			Update_selected_tab(win, new_page.Url(), new_page.Ttl());
		Xol_font_info lang_font = wiki.Lang().Gui_font();
		if (lang_font.Name() == null) lang_font = win.Gui_mgr().Win_cfg().Font();
		Xog_win_itm_.Font_update(win, lang_font);
		tab.Tab_mgr().Tab_mgr().Focus();
		html_box.Focus();
		win.Usr_dlg().Prog_none("", "", "");	// blank out status bar
		if (tab.View_mode() == Xopg_page_.Tid_read)
			html_itm.Scroll_page_by_bmk_gui();
		else
			Gfo_invk_.Invk_by_val(tab.Html_itm().Cmd_async(), Xog_html_itm.Invk_html_elem_focus, Xog_html_itm.Elem_id__xowa_edit_data_box);	// NOTE: must be async, else won't work; DATE:2014-06-05
	}
	public static void Update_selected_tab_blank(Xog_win_itm win) {Update_selected_tab(win, null, null);} // called when all tabs are null
	public static void Update_selected_tab(Xog_win_itm win, Xoa_url url, Xoa_ttl ttl) {
		String url_str = "", win_str = Win_text_blank;
		if (url != null && ttl != null) {
			url_str = url.To_str();
			win_str = String_.new_u8(Bry_.Add(ttl.Full_txt_w_ttl_case(), Win_text_suffix_page));

			// fmt to url if set
			Xog_urlfmtr_mgr url_box_fmtr = win.Url_box_fmtr();
			if (url_box_fmtr.Exists()) {
				url_str = url_box_fmtr.Gen(url);
			}
		}
		win.Url_box().Text_(url_str);
		win.Win_box().Text_(win_str);
	}
	private static final    byte[] Win_text_suffix_page = Bry_.new_a7(" - XOWA"); private static final String Win_text_blank = "XOWA";
	public static void Show_page_err(Xog_win_itm win, Xog_tab_itm tab, Xowe_wiki wiki, Xoa_url url, Xoa_ttl ttl, Exception e) {
		String err_msg = String_.Format("page_load fail: page={0} err={1}", String_.new_u8(url.Raw()), Err_.Message_gplx_full(e));
		win.Usr_dlg().Warn_many("", "", err_msg);
		win.App().Log_wtr().Queue_enabled_(false);
		Xoae_page fail_page = wiki.Data_mgr().Load_page_by_ttl(ttl);
		tab.View_mode_(Xopg_page_.Tid_edit);
		Update_selected_tab(win, url, ttl);
		Show_page(tab, fail_page, false, false, true, Xog_history_stack.Nav_fwd);
		win.Win_box().Text_(err_msg);
	}
	public static void Launch(Xog_win_itm win) {
		Xog_launcher_tabs.Instance.Launch(win);
	}
}
