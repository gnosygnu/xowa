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
import gplx.xowa.parsers.lnkis.redlinks.*; import gplx.xowa.gui.history.*;
public class Xog_tab_itm_read_mgr {
	public static void Async(Xog_tab_itm tab) {tab.Async();}
	public static void Show_page(Xog_tab_itm tab, Xoa_page new_page, boolean reset_to_read) {Show_page(tab, new_page, reset_to_read, false, false, Xog_history_stack.Nav_fwd);}
	public static void Show_page(Xog_tab_itm tab, Xoa_page new_page, boolean reset_to_read, boolean new_page_is_same, boolean show_is_err, byte history_nav_type) {
		if (reset_to_read) tab.View_mode_(Xog_page_mode.Tid_read);
		if (new_page.Url().Action_is_edit()) tab.View_mode_(Xog_page_mode.Tid_edit);
		Xoa_page cur_page = tab.Page(); Xog_html_itm html_itm = tab.Html_itm(); Gfui_html html_box = html_itm.Html_box();
		Xoa_app app = cur_page.App(); Xog_win_itm win = tab.Tab_mgr().Win();
		if (cur_page != null && !new_page_is_same) {	// if new_page_is_same, don't update DocPos; will "lose" current position
			cur_page.Html_data().Bmk_pos_(html_box.Html_window_vpos());
			tab.History_mgr().Update_html_doc_pos(cur_page, history_nav_type);	// HACK: old_page is already in stack, but need to update its hdoc_pos
		}
		win.Usr_dlg().Prog_none("", "", "locating images");
		try	{tab.Html_itm().Show(new_page);}
		catch (Exception e) {
			if (show_is_err) {	// trying to show error page, but failed; don't show again, else recursion until out of memory; TODO:always load error page; no reason it should fail; WHEN:html_skin; DATE:2014-06-08
				String new_page_url = new_page.Url().X_to_full_str_safe();
				String err_msg = "fatal error trying to load error page; page=" + new_page_url;
				app.Usr_dlg().Warn_many("", "", err_msg);
				app.Gui_mgr().Kit().Ask_ok("", "", err_msg);
				return;
			}
			else
				Show_page_err(win, tab, new_page.Wiki(), new_page.Url(), new_page.Ttl(), e);
			return;
		}
		tab.Page_(new_page);
		if (tab == tab.Tab_mgr().Active_tab())
			Update_selected_tab(app, win, new_page.Url(), new_page.Ttl());
		Xol_font_info lang_font = new_page.Wiki().Lang().Gui_font();
		if (lang_font.Name() == null) lang_font = app.Gui_mgr().Win_cfg().Font();
		Xog_win_itm_.Font_update(tab.Tab_mgr().Win(), lang_font);
		tab.Tab_mgr().Tab_mgr().Focus();
		html_box.Focus();
		win.Usr_dlg().Prog_none("", "", "");	// blank out status bar
		if (tab.View_mode() == Xog_page_mode.Tid_read)
			html_itm.Scroll_page_by_bmk_gui();
		else
			GfoInvkAble_.InvkCmd_val(tab.Html_itm().Cmd_async(), Xog_html_itm.Invk_html_elem_focus, Xog_html_itm.Elem_id__xowa_edit_data_box);	// NOTE: must be async, else won't work; DATE:2014-06-05
	}
	public static void Update_selected_tab_blank(Xoa_app app, Xog_win_itm win) {Update_selected_tab(app, win, null, null);} // called when all tabs are null
	public static void Update_selected_tab(Xoa_app app, Xog_win_itm win, Xoa_url url, Xoa_ttl ttl) {
		String url_str = "", win_str = Win_text_blank;
		if (url != null && ttl != null) {
			try {url_str = app.Url_parser().Build_str(url);}
			catch (Exception e) {	// HACK: failed pages will have a null wiki; for now, catch and ignore; DATE:2014-06-22
				app.Usr_dlg().Warn_many("", "", "failed to build url: url=~{0}, err=~{1}", String_.new_utf8_(url.Raw()), Err_.Message_gplx(e));
				url_str = String_.new_utf8_(ttl.Full_txt());
			}
			win_str = String_.new_utf8_(Bry_.Add(ttl.Full_txt(), Win_text_suffix_page));
		}
		win.Url_box().Text_(url_str);
		win.Win_box().Text_(win_str);
	}
	private static final byte[] Win_text_suffix_page = Bry_.new_ascii_(" - XOWA"); private static final String Win_text_blank = "XOWA";
	public static void Show_page_err(Xog_win_itm win, Xog_tab_itm tab, Xow_wiki wiki, Xoa_url url, Xoa_ttl ttl, Exception e) {
		String err_msg = String_.Format("page_load fail: page={0} err={1}", String_.new_utf8_(url.Raw()), Err_.Message_gplx(e));
		win.Usr_dlg().Warn_many("", "", err_msg);
		win.App().Log_wtr().Queue_enabled_(false);
		Xoa_page fail_page = wiki.Data_mgr().Get_page(ttl, false);
		tab.View_mode_(Xog_page_mode.Tid_edit);
		Update_selected_tab(win.App(), win, url, ttl);
		Show_page(tab, fail_page, false, false, true, Xog_history_stack.Nav_fwd);
		win.Win_box().Text_(err_msg);
	}
	public static void Launch(Xog_win_itm win) {
		Xoa_app app = win.App();
		Gfo_log_bfr log_bfr = app.Log_bfr();
		log_bfr.Add("app.launch.page.bgn");
		Xow_wiki home_wiki = app.User().Wiki();
		Xoa_page launch_page = Xog_tab_itm_read_mgr.Launch_page(win, home_wiki, app.Sys_cfg().Launch_url());
		if (launch_page.Missing())
			launch_page = Xog_tab_itm_read_mgr.Launch_page(win, home_wiki, Xoa_sys_cfg.Launch_url_dflt);
		log_bfr.Add("app.launch.page.show");
		Xog_tab_itm tab = win.Active_tab();
		Xog_tab_itm_read_mgr.Show_page(tab, launch_page, false);
		tab.History_mgr().Add(launch_page);
		tab.Html_itm().Html_box().Focus(); //this.Html_box().Html_doc_body_focus();	// focus the html_box so wheel scroll works; DATE:2013-02-08
		app.Gui_wtr().Prog_none("", "", "");
		log_bfr.Add("app.launch.page.end");
		app.Gui_wtr().Log_wtr().Log_msg_to_session_direct(log_bfr.Xto_str());
	}
	private static Xoa_page Launch_page(Xog_win_itm win, Xow_wiki home_wiki, String launch_str) {
		Xoa_url launch_url = Xoa_url_parser.Parse_url(win.App(), home_wiki, launch_str);
		Xow_wiki launch_wiki = launch_url.Wiki();
		Xoa_ttl launch_ttl = Xoa_ttl.parse_(launch_wiki, launch_url.Page_bry());
		Xoa_page rv = launch_wiki.GetPageByTtl(launch_url, launch_ttl).Url_(launch_url);	// FUTURE: .Url_() should not be called (needed for anchor); EX: en.wikipedia.org/Earth#Biosphere
		win.Active_page_(rv);	// set to blank page
		return rv;
	}
}
