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
package gplx.xowa.gui.views; import gplx.*; import gplx.xowa.*; import gplx.xowa.gui.*;
import gplx.gfui.*; import gplx.threads.*;
import gplx.xowa.parsers.lnkis.redlinks.*; import gplx.xowa.gui.history.*; import gplx.xowa.pages.*;
public class Xog_tab_itm_read_mgr {
	public static void Async(Xog_tab_itm tab) {tab.Async();}
	public static void Show_page(Xog_tab_itm tab, Xoae_page new_page, boolean reset_to_read) {Show_page(tab, new_page, reset_to_read, false, false, Xog_history_stack.Nav_fwd);}
	public static void Show_page(Xog_tab_itm tab, Xoae_page new_page, boolean reset_to_read, boolean new_page_is_same, boolean show_is_err, byte history_nav_type) {
		if (reset_to_read) tab.View_mode_(Xopg_view_mode.Tid_read);
		if (new_page.Url().Action_is_edit()) tab.View_mode_(Xopg_view_mode.Tid_edit);
		Xoae_page cur_page = tab.Page(); Xog_html_itm html_itm = tab.Html_itm(); Gfui_html html_box = html_itm.Html_box();
		Xog_win_itm win = tab.Tab_mgr().Win();
		if (cur_page != null && !new_page_is_same) {	// if new_page_is_same, don't update DocPos; will "lose" current position
			cur_page.Html_data().Bmk_pos_(html_box.Html_window_vpos());
			tab.History_mgr().Update_html_doc_pos(cur_page, history_nav_type);	// HACK: old_page is already in stack, but need to update its hdoc_pos
		}
		win.Usr_dlg().Prog_none("", "", "locating images");
		Xowe_wiki wiki = new_page.Wikie();
		try	{tab.Html_itm().Show(new_page);}
		catch (Exception e) {
			if (String_.Eq(Err_.Message_lang(e), "class org.eclipse.swt.SWTException Widget is disposed")) return; // ignore errors caused by user closing tab early; DATE:2014-07-26
			if (show_is_err) {	// trying to show error page, but failed; don't show again, else recursion until out of memory; TODO:always load error page; no reason it should fail; WHEN:html_skin; DATE:2014-06-08
				Gfo_usr_dlg_._.Warn_many("", "", "fatal error trying to load error page; page=~{0} err=~{1}" + new_page.Url().Xto_full_str_safe(), Err_.Message_gplx(e));
				return;
			}
			else
				Show_page_err(win, tab, wiki, new_page.Url(), new_page.Ttl(), e);
			return;
		}
		tab.Page_(new_page);
		if (tab == tab.Tab_mgr().Active_tab())
			Update_selected_tab(win.App().Url_parser(), win, new_page.Url(), new_page.Ttl());
		Xol_font_info lang_font = wiki.Lang().Gui_font();
		if (lang_font.Name() == null) lang_font = win.Gui_mgr().Win_cfg().Font();
		Xog_win_itm_.Font_update(win, lang_font);
		tab.Tab_mgr().Tab_mgr().Focus();
		html_box.Focus();
		win.Usr_dlg().Prog_none("", "", "");	// blank out status bar
		if (tab.View_mode() == Xopg_view_mode.Tid_read)
			html_itm.Scroll_page_by_bmk_gui();
		else
			GfoInvkAble_.InvkCmd_val(tab.Html_itm().Cmd_async(), Xog_html_itm.Invk_html_elem_focus, Xog_html_itm.Elem_id__xowa_edit_data_box);	// NOTE: must be async, else won't work; DATE:2014-06-05
	}
	public static void Update_selected_tab_blank(Xoa_url_parser url_parser, Xog_win_itm win) {Update_selected_tab(url_parser, win, null, null);} // called when all tabs are null
	public static void Update_selected_tab(Xoa_url_parser url_parser, Xog_win_itm win, Xoa_url url, Xoa_ttl ttl) {
		String url_str = "", win_str = Win_text_blank;
		if (url != null && ttl != null) {
			try {url_str = url_parser.Build_str(url);}
			catch (Exception e) {	// HACK: failed pages will have a null wiki; for now, catch and ignore; DATE:2014-06-22
				Gfo_usr_dlg_._.Warn_many("", "", "failed to build url: url=~{0}, err=~{1}", String_.new_utf8_(url.Raw()), Err_.Message_gplx(e));
				url_str = String_.new_utf8_(ttl.Full_txt());
			}
			win_str = String_.new_utf8_(Bry_.Add(ttl.Full_txt(), Win_text_suffix_page));
		}
		win.Url_box().Text_(url_str);
		win.Win_box().Text_(win_str);
	}
	private static final byte[] Win_text_suffix_page = Bry_.new_ascii_(" - XOWA"); private static final String Win_text_blank = "XOWA";
	public static void Show_page_err(Xog_win_itm win, Xog_tab_itm tab, Xowe_wiki wiki, Xoa_url url, Xoa_ttl ttl, Exception e) {
		String err_msg = String_.Format("page_load fail: page={0} err={1}", String_.new_utf8_(url.Raw()), Err_.Message_gplx(e));
		win.Usr_dlg().Warn_many("", "", err_msg);
		win.App().Log_wtr().Queue_enabled_(false);
		Xoae_page fail_page = wiki.Data_mgr().Get_page(ttl, false);
		tab.View_mode_(Xopg_view_mode.Tid_edit);
		Update_selected_tab(win.App().Url_parser(), win, url, ttl);
		Show_page(tab, fail_page, false, false, true, Xog_history_stack.Nav_fwd);
		win.Win_box().Text_(err_msg);
	}
	public static void Launch(Xog_win_itm win) {
		Xog_launcher_tabs._.Launch(win);
	}
}
