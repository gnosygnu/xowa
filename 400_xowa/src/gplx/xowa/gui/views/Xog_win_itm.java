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
import gplx.threads.*; import gplx.gfui.*; import gplx.xowa.gui.*; import gplx.xowa.gui.history.*; import gplx.xowa.xtns.math.*; import gplx.xowa.files.*;
import gplx.xowa.gui.urls.*; import gplx.xowa.gui.views.*; import gplx.xowa.pages.*;
import gplx.xowa.parsers.lnkis.redlinks.*;
public class Xog_win_itm implements GfoInvkAble, GfoEvObj {
	private GfoInvkAble sync_cmd;
	public Xog_win_itm(Xoae_app app, Xoa_gui_mgr gui_mgr) {
		this.app = app; this.gui_mgr = gui_mgr;
		this.tab_mgr = new Xog_tab_mgr(this);			
	}
	public Gfui_kit			Kit() {return kit;} private Gfui_kit kit;
	public Xoa_gui_mgr		Gui_mgr() {return gui_mgr;} private Xoa_gui_mgr gui_mgr;
	public GfuiWin			Win_box() {return win_box;} private GfuiWin win_box;
	public GfuiBtn			Go_bwd_btn() {return go_bwd_btn;} private GfuiBtn go_bwd_btn;
	public GfuiBtn			Go_fwd_btn() {return go_fwd_btn;} private GfuiBtn go_fwd_btn;
	public GfuiTextBox		Url_box() {return url_box;} private GfuiTextBox url_box;
	public GfuiBtn			Url_exec_btn() {return url_exec_btn;} private GfuiBtn url_exec_btn;
	public GfuiTextBox		Search_box() {return search_box;} private GfuiTextBox search_box;
	public GfuiBtn			Search_exec_btn() {return search_exec_btn;} private GfuiBtn search_exec_btn;
	public GfuiTextBox		Find_box() {return find_box;} private GfuiTextBox find_box;
	public GfuiBtn			Find_close_btn() {return find_close_btn;} private GfuiBtn find_close_btn;
	public GfuiBtn			Find_fwd_btn() {return find_fwd_btn;} private GfuiBtn find_fwd_btn;
	public GfuiBtn			Find_bwd_btn() {return find_bwd_btn;} private GfuiBtn find_bwd_btn;
	public GfuiTextBox		Prog_box() {return prog_box;} private GfuiTextBox prog_box;
	public GfuiTextBox		Info_box() {return info_box;} private GfuiTextBox info_box;
	public GfoEvMgr			EvMgr() {if (evMgr == null) evMgr = GfoEvMgr.new_(this); return evMgr;} private GfoEvMgr evMgr;
	public Xoae_app			App()				{return app;} private Xoae_app app;
	public Xog_tab_mgr		Tab_mgr()			{return tab_mgr;} private Xog_tab_mgr tab_mgr;
	public Xog_tab_itm		Active_tab()		{return tab_mgr.Active_tab();}
	public Xoae_page		Active_page()		{return tab_mgr.Active_tab().Page();} public void Active_page_(Xoae_page v) {tab_mgr.Active_tab().Page_(v);}
	public Xowe_wiki		Active_wiki()		{return tab_mgr.Active_tab().Wiki();}
	public Xog_html_itm		Active_html_itm()	{return tab_mgr.Active_tab().Html_itm();}
	public Gfui_html		Active_html_box()	{return tab_mgr.Active_tab().Html_itm().Html_box();}
	public Xog_resizer		Resizer() {return resizer;} private Xog_resizer resizer = new Xog_resizer();
	public Gfo_usr_dlg		Usr_dlg() {return app.Usr_dlg();}
	public void Refresh_win_size() {
		if (win_box != null)	// NOTE: will be null when html box adjustment pref is set and application is starting
			resizer.Exec_win_resize(app, win_box.Width(), win_box.Height());
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_link_click))								Win__link_click();
		else if	(ctx.Match(k, Invk_link_print))								Xog_win_itm__prog_href_mgr.Print(this);
		else if	(ctx.Match(k, Gfui_html.Evt_link_hover))					Xog_win_itm__prog_href_mgr.Hover(app, this.Active_tab().Wiki(), this.Active_page(), m.ReadStr("v"));
		else if	(ctx.Match(k, Gfui_html.Evt_location_changed))				Win__link_clicked(m.ReadStr("v"));
		else if	(ctx.Match(k, Gfui_html.Evt_location_changing))				Page__navigate_by_internal_href(m.ReadStr("v"), tab_mgr.Active_tab());
		else if (ctx.Match(k, Gfui_html.Evt_win_resized))					Refresh_win_size();
		else if (ctx.Match(k, Invk_page_refresh))							Page__refresh();
		else if	(ctx.Match(k, Invk_page_async_exec))						Xog_tab_itm_read_mgr.Async((Xog_tab_itm)m.ReadObj("v"));
		else if	(ctx.Match(k, Invk_page_view_read))							Page__mode_(Xopg_view_mode.Tid_read);
		else if	(ctx.Match(k, Invk_page_view_edit))							Page__mode_(Xopg_view_mode.Tid_edit);
		else if	(ctx.Match(k, Invk_page_view_html))							Page__mode_(Xopg_view_mode.Tid_html);
		else if (ctx.Match(k, Invk_page_edit_save))							Xog_tab_itm_edit_mgr.Save(tab_mgr.Active_tab(), Bool_.N);
		else if (ctx.Match(k, Invk_page_edit_save_draft))					Xog_tab_itm_edit_mgr.Save(tab_mgr.Active_tab(), Bool_.Y);
		else if (ctx.Match(k, Invk_page_edit_preview))						Xog_tab_itm_edit_mgr.Preview(tab_mgr.Active_tab());
		else if (ctx.Match(k, Invk_page_edit_rename))						Xog_tab_itm_edit_mgr.Rename(tab_mgr.Active_tab());
		else if	(ctx.Match(k, Invk_page_edit_focus_box)) 					Xog_tab_itm_edit_mgr.Focus(this, Xog_html_itm.Elem_id__xowa_edit_data_box);
		else if	(ctx.Match(k, Invk_page_edit_focus_first)) 					Xog_tab_itm_edit_mgr.Focus(this, Xog_html_itm.Elem_id__first_heading);
		else if	(ctx.Match(k, Invk_page_dbg_html))							Xog_tab_itm_edit_mgr.Debug(this, Xopg_view_mode.Tid_html);
		else if	(ctx.Match(k, Invk_page_dbg_wiki))							Xog_tab_itm_edit_mgr.Debug(this, Xopg_view_mode.Tid_edit);
		else if	(ctx.Match(k, Invk_page_goto))								Page__navigate_by_url_bar(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_page_goto_recent))						Page__navigate_by_url_bar(app.User().History_mgr().Get_at_last());
		else if	(ctx.Match(k, Invk_history_bwd))							{Page__navigate_by_history(Bool_.N);}
		else if	(ctx.Match(k, Invk_history_fwd))							{Page__navigate_by_history(Bool_.Y);}
		else if	(ctx.Match(k, Invk_eval))									App__eval(m.ReadStr("cmd"));
		else if	(ctx.Match(k, Invk_page_async_cancel_wait))					Page__async__cancel__wait();
		else if	(ctx.Match(k, Invk_page_async_restart))						Page__async__restart();
		else if	(ctx.Match(k, Invk_search))									Page__navigate_by_search();
		else if	(ctx.Match(k, Invk_window_font_changed))					Xog_win_itm_.Font_update(this, (Xol_font_info)m.CastObj("font"));
		else if	(ctx.Match(k, Invk_app))									return app;
		else if	(ctx.Match(k, Invk_page))									return this.Active_page();
		else if	(ctx.Match(k, Invk_wiki))									return this.Active_tab().Wiki();
		else																return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk_page_async_exec = "page_async_exec"
	, Invk_page_async_cancel_wait = "page_async_cancel_wait", Invk_page_async_restart = "page_async_restart"
	; 
	public static final String
	  Invk_app = "app", Invk_wiki = "wiki", Invk_page = "page", Invk_shortcuts = "shortcuts"
	, Invk_link_click = "link_click", Invk_link_print = "link_print"
	, Invk_window_font_changed = "winow_font_changed"
	, Invk_search = "search"
	, Invk_page_view_edit = "page_view_edit", Invk_page_view_read = "page_view_read", Invk_page_view_html = "page_view_html"
	, Invk_history_fwd = "history_fwd", Invk_history_bwd = "history_bwd"
	, Invk_page_refresh = "page_refresh"
	, Invk_page_edit_focus_box = "page_edit_focus_box", Invk_page_edit_focus_first = "page_edit_focus_first"
	, Invk_page_edit_save = "page_edit_save", Invk_page_edit_save_draft = "page_edit_save_draft", Invk_page_edit_preview = "page_edit_preview", Invk_page_edit_rename = "page_edit_rename"
	, Invk_page_dbg_wiki = "page_dbg_wiki", Invk_page_dbg_html = "page_dbg_html"
	, Invk_eval = "eval"
	// xowa.gfs: shortcuts
	, Invk_page_goto = "page_goto", Invk_page_goto_recent = "page_goto_recent"
	; 
	private void Win__link_click() {	// NOTE: only applies when content_editable=y; if n, then link_click will be handled by SwtBrowser location changed (Win__link_clicked)
		Xog_tab_itm tab = tab_mgr.Active_tab(); Xowe_wiki wiki = tab.Wiki();
		if (wiki.Gui_mgr().Cfg_browser().Content_editable()) {	
			String href = tab.Html_itm().Html_box().Html_active_atr_get_str(Gfui_html.Atr_href, null);
			if (String_.Len_eq_0(href)) return; // NOTE: href can be null for images; EX: [[File:Loudspeaker.svg|11px|link=|alt=play]]; link= basically means don't link to image
			Page__navigate_by_internal_href(href, tab);
		}
	}
	private void Win__link_clicked(String anchor_raw) {
		String url = url_box.Text();
		int pos = String_.FindFwd(url, gplx.html.Html_tag_.Anchor_str);
		if (pos != Bry_.NotFound) url = String_.Mid(url, 0, pos);
		String anchor_str = Parse_evt_location_changing(anchor_raw);
		byte[] anchor_bry = Bry_.new_utf8_(anchor_str);
		Xog_tab_itm tab = tab_mgr.Active_tab(); Xoae_page page = tab.Page();
		if (anchor_str != null) {									// link has anchor
			url_box.Text_(url + "#" + anchor_str);					// update url box
			page.Html_data().Bmk_pos_(Xog_history_itm.Html_doc_pos_toc); // HACK: anchor clicked; set docPos of curentPage to TOC (so back will go back to TOC)
			tab.History_mgr().Update_html_doc_pos(page, Xog_history_stack.Nav_by_anchor); // HACK: update history_mgr; note that this must occur before setting Anchor (since Anchor will generate a new history itm)
			page.Url().Anchor_bry_(anchor_bry);						// update url
		}
		tab.History_mgr().Add(page);
		app.User().History_mgr().Add(page.Url(), page.Ttl(), Bry_.Add_w_dlm(Byte_ascii.Hash, page.Url().Page_bry(), anchor_bry));
	}
	public void App__exit() {
		kit.Kit_term();	// NOTE: Kit_term calls shell.close() which in turn is hooked up to app.Term_cbk() event; DATE:2014-09-09
	}
	private void App__eval(String s) {
		String snippet = this.Active_html_box().Html_elem_atr_get_str(s, Gfui_html.Atr_innerHTML);
		app.Gfs_mgr().Run_str(snippet);
	}
	private static String Parse_evt_location_changing(String v) { // EX: about:blank#anchor -> anchor
		int pos = String_.FindFwd(v, gplx.html.Html_tag_.Anchor_str);
		return pos == Bry_.NotFound
			? null
			: String_.Mid(v, pos + 1);
	}
	public void Page__mode_(byte new_mode_tid) {
		Xog_tab_itm tab = tab_mgr.Active_tab(); Xoae_page page = tab.Page(); Xowe_wiki wiki = tab.Wiki();
		if (	new_mode_tid == Xopg_view_mode.Tid_read	// used to be && cur_view_tid == Edit; removed clause else redlinks wouldn't show when going form html to read (or clicking read multiple times) DATE: 2013-11-26;
			&& !page.Missing()							// if new page, don't try to reload
			) {
			// NOTE: if moving from "Edit" to "Read", reload page (else Preview changes will still show); NOTE: do not call Exec_page_reload / Exec_page_refresh, which will fire redlinks code
			page = tab_mgr.Active_tab().History_mgr().Cur_page(wiki);	// NOTE: must set to CurPage() else changes will be lost when going Bwd,Fwd
			tab.Page_(page);			
			wiki.ParsePage_root(page, true);		// NOTE: must reparse page if (a) Edit -> Read; or (b) "Options" save
			Xoa_url url = page.Url();
			if (url.Args_exists(Xoa_url_parser.Bry_arg_action, Xoa_url_parser.Bry_arg_action_edit))	// url has ?action=edit
				app.Url_parser().Parse(url, url.Xto_full_bry());	// remove all query args; handle (1) s.w:Earth?action=edit; (2) click on Read; DATE:2014-03-06
		}
		tab.View_mode_(new_mode_tid);
		if (page.Missing()) return;
		Xog_tab_itm_read_mgr.Show_page(tab, page, false);
		// Exec_page_refresh(); // commented out; causes lnke to show as [2] instead of [1] when saving page; EX: [http://a.org b] DATE:2014-04-24
	}
	public void Page__navigate_by_search() {Page__navigate_by_url_bar(app.Gui_mgr().Win_cfg().Search_box_fmtr().Bld_str_many(search_box.Text()));}
	public void Page__navigate_by_url_bar(String href) {
		Xog_tab_itm tab = tab_mgr.Active_tab_assert();
		Xoa_url url = Xoa_url_parser.Parse_from_url_bar(app, tab.Wiki(), href);
		tab.Show_url_bgn(url);
	}
	private void Page__navigate_by_internal_href(String href, Xog_tab_itm tab) {	// NOTE: different from Navigate_by_url_bar in that it handles "file:///" and other @gplx.Internal protected formats; EX: "/site/", "about:blank"; etc..
		Xoa_url url = Xog_url_wkr.Exec_url(this, href);
		if (url != Xog_url_wkr.Rslt_handled)
			tab.Show_url_bgn(url);
	}
	public void Page__navigate_by_history(boolean fwd) {
		Xog_tab_itm tab = tab_mgr.Active_tab();
		if (tab == Xog_tab_itm_.Null) return;
		Xoae_page cur_page = tab.Page(); Xowe_wiki cur_wiki = tab.Wiki();
		Xoae_page new_page = tab.History_mgr().Go_by_dir(cur_wiki, fwd);
		if (new_page.Missing()) return;
		if (new_page.Wikie().Special_mgr().Page_search().Match_ttl(new_page.Ttl()))		// if Special:Search, reload page; needed for async loading; DATE:2015-04-19
			new_page = new_page.Wikie().GetPageByTtl(new_page.Url(), new_page.Ttl());	// NOTE: must reparse page if (a) Edit -> Read; or (b) "Options" save
		byte history_nav_type = fwd ? Xog_history_stack.Nav_fwd : Xog_history_stack.Nav_bwd;
		boolean new_page_is_same = Bry_.Eq(cur_page.Ttl().Full_txt(), new_page.Ttl().Full_txt());
		Xog_tab_itm_read_mgr.Show_page(tab, new_page, true, new_page_is_same, false, history_nav_type);
		Page__async__bgn(tab);
	}
	public void Page__reload() {
		Xog_tab_itm tab = tab_mgr.Active_tab();
		Xoae_page page = tab.History_mgr().Cur_page(tab.Wiki());	// NOTE: must set to CurPage() else changes will be lost when going Bwd,Fwd
		tab.Page_(page);
		page.Wikie().ParsePage_root(page, true);		// NOTE: must reparse page if (a) Edit -> Read; or (b) "Options" save
		Page__refresh();
	}
	public void Page__refresh() {
		Xog_tab_itm tab = tab_mgr.Active_tab(); Xoae_page page = tab.Page(); Xog_html_itm html_itm = tab.Html_itm();
		page.Html_data().Bmk_pos_(html_itm.Html_box().Html_window_vpos());
		html_itm.Show(page);
		if (page.Url().Anchor_str() == null)
			html_itm.Scroll_page_by_bmk_gui();
		else
			html_itm.Scroll_page_by_id_gui(page.Url().Anchor_str());
		Page__async__bgn(tab);
	}
	public void Page__async__bgn(Xog_tab_itm tab) {
		page__async__thread = ThreadAdp_.invk_msg_(this, GfoMsg_.new_cast_(Invk_page_async_exec).Add("v", tab)).Start();
	}	private ThreadAdp page__async__thread = ThreadAdp.Null;
	public boolean Page__async__working(Xoa_url url) {
		if (page__async__thread.IsAlive()) {				// cancel pending image downloads
			page__async__restart_url = url;
			this.Usr_dlg().Canceled_y_();
			app.Wmf_mgr().Download_wkr().Download_xrg().Prog_cancel_y_();
			ThreadAdp_.invk_(this, Invk_page_async_cancel_wait).Start();
			return true;
		}
		return false;
	}
	private void Page__async__cancel__wait() {
		while (page__async__thread.IsAlive()) {
			ThreadAdp_.Sleep(10);
		}
		this.Active_page().File_queue().Clear();
		this.Usr_dlg().Canceled_n_();	// NOTE: must mark "uncanceled", else one cancelation will stop all future downloads; DATE:2014-05-04
		GfoInvkAble_.InvkCmd(sync_cmd, Invk_page_async_restart);
	}
	private void Page__async__restart() {
		tab_mgr.Active_tab().Show_url_bgn(page__async__restart_url);
	}	private Xoa_url page__async__restart_url;
	public void Lang_changed(Xol_lang lang) {
		Xoae_app app = gui_mgr.App();
		Xog_win_itm_.Update_tiptext(app, go_bwd_btn			, Xol_msg_itm_.Id_xowa_window_go_bwd_btn_tooltip);
		Xog_win_itm_.Update_tiptext(app, go_fwd_btn			, Xol_msg_itm_.Id_xowa_window_go_fwd_btn_tooltip);
		Xog_win_itm_.Update_tiptext(app, url_box			, Xol_msg_itm_.Id_xowa_window_url_box_tooltip);
		Xog_win_itm_.Update_tiptext(app, url_exec_btn		, Xol_msg_itm_.Id_xowa_window_url_btn_tooltip);
		Xog_win_itm_.Update_tiptext(app, search_box			, Xol_msg_itm_.Id_xowa_window_search_box_tooltip);
		Xog_win_itm_.Update_tiptext(app, search_exec_btn	, Xol_msg_itm_.Id_xowa_window_search_btn_tooltip);
		Xog_win_itm_.Update_tiptext(app, find_close_btn		, Xol_msg_itm_.Id_xowa_window_find_close_btn_tooltip);
		Xog_win_itm_.Update_tiptext(app, find_box			, Xol_msg_itm_.Id_xowa_window_find_box_tooltip);
		Xog_win_itm_.Update_tiptext(app, find_bwd_btn		, Xol_msg_itm_.Id_xowa_window_find_bwd_btn_tooltip);
		Xog_win_itm_.Update_tiptext(app, find_fwd_btn		, Xol_msg_itm_.Id_xowa_window_find_fwd_btn_tooltip);
		Xog_win_itm_.Update_tiptext(app, prog_box			, Xol_msg_itm_.Id_xowa_window_prog_box_tooltip);
		Xog_win_itm_.Update_tiptext(app, info_box			, Xol_msg_itm_.Id_xowa_window_info_box_tooltip);
	}
	public byte[] App__retrieve_by_url(String url_str, String output_str) {
		synchronized (App__retrieve__lock) {
			boolean output_html = String_.Eq(output_str, "html");
			byte[] url_bry = Bry_.new_utf8_(url_str);
			Xowe_wiki home_wiki = app.User().Wiki();
			Xoa_ttl ttl = Xoa_ttl.parse_(home_wiki, Xoa_page_.Main_page_bry);	// NOTE: must be Main_Page, not "" else Firefox Addon will fail; DATE:2014-03-13
			Xoae_page new_page = Xoae_page.new_(home_wiki, ttl);
			gplx.xowa.servers.Gxw_html_server.Assert_tab(app, new_page);		// HACK: assert at least 1 tab for Firefox addon; DATE:2015-01-23
			this.Active_page_(new_page);
			Xoa_url url = Xoa_url.blank_();
			url = Xoa_url_parser.Parse_url(url, app, home_wiki, url_bry, 0, url_bry.length, true);
			new_page.Url_(url);
			return App__retrieve_by_href(url, output_html);
		}
	}	private Object App__retrieve__lock = new Object();
	public byte[] App__retrieve_by_href(String href, boolean output_html) {return App__retrieve_by_href(Xog_url_wkr.Exec_url(this, href), output_html);}	// NOTE: used by drd
	private byte[] App__retrieve_by_href(Xoa_url url, boolean output_html) {
		if (url == null) return Bry_.new_ascii_("missing");
		Xowe_wiki wiki = app.Wiki_mgr().Get_by_key_or_null(url.Wiki_bry());
		Xoa_ttl ttl = Xoa_ttl.parse_(wiki, url.Page_bry());
		Xoae_page new_page = wiki.GetPageByTtl(url, ttl);
		if (new_page.Missing()) {return Bry_.Empty;}
		Xog_tab_itm tab = tab_mgr.Active_tab();
		tab.Page_(new_page);
		tab.History_mgr().Add(new_page);			
		byte[] rv = output_html
			? wiki.Html_mgr().Page_wtr_mgr().Gen(new_page, tab.View_mode())
			: new_page.Data_raw()
			;
		if (app.Shell().Fetch_page_exec_async())
			app.Gui_mgr().Browser_win().Page__async__bgn(tab);
		return rv;
	}
	public void Init_by_kit(Gfui_kit kit) {
		this.kit = kit;
		win_box = kit.New_win_app("win");
		sync_cmd			= win_box.Kit().New_cmd_sync(this);
		Io_url img_dir		= app.User().Fsys_mgr().App_img_dir().GenSubDir_nest("window", "chrome");
		FontAdp ui_font		= app.Gui_mgr().Win_cfg().Font().XtoFontAdp();
		go_bwd_btn			= Xog_win_itm_.new_btn(app, kit, win_box, img_dir, "go_bwd_btn", "go_bwd.png"				);
		go_fwd_btn			= Xog_win_itm_.new_btn(app, kit, win_box, img_dir, "go_fwd_btn", "go_fwd.png"				);
		url_box				= Xog_win_itm_.new_txt(app, kit, win_box, ui_font, "url_box"								, true);
		url_exec_btn		= Xog_win_itm_.new_btn(app, kit, win_box, img_dir, "url_exec_btn", "url_exec.png"			);
		search_box			= Xog_win_itm_.new_txt(app, kit, win_box, ui_font, "search_box"								, true);
		search_exec_btn		= Xog_win_itm_.new_btn(app, kit, win_box, img_dir, "search_exec_btn", "search_exec.png"		);
		find_close_btn		= Xog_win_itm_.new_btn(app, kit, win_box, img_dir, "find_close_btn", "find_close.png"		);
		find_box			= Xog_win_itm_.new_txt(app, kit, win_box, ui_font, "find_box"								, true);
		find_fwd_btn		= Xog_win_itm_.new_btn(app, kit, win_box, img_dir, "find_fwd_btn", "find_fwd.png"			);
		find_bwd_btn		= Xog_win_itm_.new_btn(app, kit, win_box, img_dir, "find_bwd_btn", "find_bwd.png"			);
		prog_box			= Xog_win_itm_.new_txt(app, kit, win_box, ui_font, "prog_box"								, false);
		info_box			= Xog_win_itm_.new_txt(app, kit, win_box, ui_font, "note_box"								, false);
		tab_mgr.Init_by_kit(kit);
		this.Lang_changed(app.User().Lang());

		GfoEvMgr_.SubSame_many(this, this, Gfui_html.Evt_location_changed, Gfui_html.Evt_location_changing, Gfui_html.Evt_link_hover);
		GfoEvMgr_.SubSame(win_box, Gfui_html.Evt_win_resized, this);
		GfoEvMgr_.Sub(app.Gui_mgr().Win_cfg().Font(), Xol_font_info.Font_changed, this, Invk_window_font_changed);

		if (	!Env_.Mode_testing()
			&&	app.Mode() == Xoa_app_.Mode_gui)	// only run for gui; do not run for tcp/http server; DATE:2014-05-03
			app.Usr_dlg().Ui_wkr_(new Gfo_usr_dlg_ui_swt(kit, prog_box, info_box, info_box, app.Api_root().Gui().Browser().Info()));
	}
}
