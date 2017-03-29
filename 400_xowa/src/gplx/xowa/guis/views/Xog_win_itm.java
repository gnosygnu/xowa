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
import gplx.core.threads.*; import gplx.core.envs.*;
import gplx.gfui.*; import gplx.gfui.draws.*; import gplx.gfui.kits.core.*; import gplx.gfui.controls.windows.*; import gplx.gfui.controls.standards.*;
import gplx.xowa.guis.*; import gplx.xowa.guis.history.*; import gplx.xowa.guis.langs.*; import gplx.xowa.guis.urls.*; import gplx.xowa.guis.views.*; 
import gplx.gfui.layouts.swts.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
import gplx.xowa.wikis.pages.*; import gplx.xowa.apps.urls.*; import gplx.xowa.files.*;
import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.wikis.pages.lnkis.*; import gplx.xowa.specials.*; import gplx.xowa.xtns.math.*; 	
import gplx.xowa.guis.views.url_box_fmts.*;
public class Xog_win_itm implements Gfo_invk, Gfo_evt_itm {
	private Gfo_invk sync_cmd;
	private Xog_url_box__selection_changed url_box__selection_changed;
	public Xog_win_itm(Xoae_app app, Xoa_gui_mgr gui_mgr) {
		this.app = app; this.gui_mgr = gui_mgr;
		this.tab_mgr = new Xog_tab_mgr(this);
	}
	public Gfui_kit			Kit() {return kit;} private Gfui_kit kit;
	public Xoa_gui_mgr		Gui_mgr() {return gui_mgr;} private Xoa_gui_mgr gui_mgr;

	public GfuiWin          Win_box()            {return win_box;}            private GfuiWin        win_box;
	public Gfui_grp         Toolbar_grp()        {return toolbar_grp;}        private Gfui_grp       toolbar_grp;
	public GfuiBtn          Go_bwd_btn()         {return go_bwd_btn;}         private GfuiBtn        go_bwd_btn;
	public GfuiBtn          Go_fwd_btn()         {return go_fwd_btn;}         private GfuiBtn        go_fwd_btn;
	public GfuiComboBox     Url_box()            {return url_box;}            private GfuiComboBox   url_box;
	public GfuiBtn          Url_exec_btn()       {return url_exec_btn;}       private GfuiBtn        url_exec_btn;
	public GfuiTextBox      Search_box()         {return search_box;}         private GfuiTextBox    search_box;
	public GfuiBtn          Search_exec_btn()    {return search_exec_btn;}    private GfuiBtn        search_exec_btn;
	public GfuiTextBox      Allpages_box()       {return allpages_box;}       private GfuiTextBox    allpages_box;
	public GfuiBtn          Allpages_exec_btn()  {return allpages_exec_btn;}  private GfuiBtn        allpages_exec_btn;
	public GfuiTextBox      Find_box()           {return find_box;}           private GfuiTextBox    find_box;
	public Gfui_grp         Statusbar_grp()      {return statusbar_grp;}      private Gfui_grp       statusbar_grp;
	public GfuiBtn          Find_close_btn()     {return find_close_btn;}     private GfuiBtn        find_close_btn;
	public GfuiBtn          Find_fwd_btn()       {return find_fwd_btn;}       private GfuiBtn        find_fwd_btn;
	public GfuiBtn          Find_bwd_btn()       {return find_bwd_btn;}       private GfuiBtn        find_bwd_btn;
	public GfuiTextBox      Prog_box()           {return prog_box;}           private GfuiTextBox    prog_box;
	public GfuiTextBox      Info_box()           {return info_box;}           private GfuiTextBox    info_box;

	public Gfo_evt_mgr		Evt_mgr() {if (evt_mgr == null) evt_mgr = new Gfo_evt_mgr(this); return evt_mgr;} private Gfo_evt_mgr evt_mgr;
	public Xoae_app			App()				{return app;} private Xoae_app app;
	public Xog_tab_mgr		Tab_mgr()			{return tab_mgr;} private Xog_tab_mgr tab_mgr;
	public Xog_tab_itm		Active_tab()		{return tab_mgr.Active_tab();}
	public Xoae_page		Active_page()		{return tab_mgr.Active_tab().Page();} public void Active_page_(Xoae_page v) {tab_mgr.Active_tab().Page_(v);}
	public Xowe_wiki		Active_wiki()		{return tab_mgr.Active_tab().Wiki();}
	public Xog_html_itm		Active_html_itm()	{return tab_mgr.Active_tab().Html_itm();}
	public Gfui_html		Active_html_box()	{return tab_mgr.Active_tab().Html_itm().Html_box();}
	public Gfo_usr_dlg		Usr_dlg() {return app.Usr_dlg();}
	public Xog_urlfmtr_mgr  Url_box_fmtr() {return url_box_fmtr;} private final    Xog_urlfmtr_mgr url_box_fmtr = new Xog_urlfmtr_mgr();
	public Xog_win_itm_cfg	Cfg() {return cfg;} private final    Xog_win_itm_cfg cfg = new Xog_win_itm_cfg();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_link_click))								Win__link_click();
		else if	(ctx.Match(k, Invk_link_print))								Xog_win_itm__prog_href_mgr.Print(this);
		else if	(ctx.Match(k, Gfui_html.Evt_location_changed))				Win__link_clicked(m.ReadStr("v"));
		else if	(ctx.Match(k, Gfui_html.Evt_location_changing))				Page__navigate_by_href(tab_mgr.Active_tab(), Xoh_href_gui_utl.Standardize_xowa_link(m.ReadStr("v")));
		else if (ctx.Match(k, Invk_page_refresh))							Page__refresh();
		else if	(ctx.Match(k, Invk_page_async_exec))						Xog_async_wkr.Async(((Xog_tab_itm)m.ReadObj("v")));
		else if	(ctx.Match(k, Invk_page_view_read))							Page__mode_(Xopg_page_.Tid_read);
		else if	(ctx.Match(k, Invk_page_view_edit))							Page__mode_edit_();
		else if	(ctx.Match(k, Invk_page_view_html))							Page__mode_(Xopg_page_.Tid_html);
		else if (ctx.Match(k, Invk_page_edit_save))							Xog_tab_itm_edit_mgr.Save(tab_mgr.Active_tab(), Bool_.N);
		else if (ctx.Match(k, Invk_page_edit_save_draft))					Xog_tab_itm_edit_mgr.Save(tab_mgr.Active_tab(), Bool_.Y);
		else if (ctx.Match(k, Invk_page_edit_preview))						Xog_tab_itm_edit_mgr.Preview(tab_mgr.Active_tab());
		else if (ctx.Match(k, Invk_page_edit_rename))						Xog_tab_itm_edit_mgr.Rename(tab_mgr.Active_tab());
		else if	(ctx.Match(k, Invk_page_edit_focus_box)) 					Xog_tab_itm_edit_mgr.Focus(this, Xog_html_itm.Elem_id__xowa_edit_data_box);
		else if	(ctx.Match(k, Invk_page_edit_focus_first)) 					Xog_tab_itm_edit_mgr.Focus(this, Xog_html_itm.Elem_id__first_heading);
		else if	(ctx.Match(k, Invk_page_dbg_html))							Xog_tab_itm_edit_mgr.Debug(this, Xopg_page_.Tid_html);
		else if	(ctx.Match(k, Invk_page_dbg_wiki))							Xog_tab_itm_edit_mgr.Debug(this, Xopg_page_.Tid_edit);
		else if	(ctx.Match(k, Invk_page_goto))								Page__navigate_by_url_bar(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_page_goto_recent))						Page__navigate_by_url_bar(app.Usere().History_mgr().Get_at_last());
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
		else if	(ctx.Match(k, Invk_exit))									App__exit();
		else if	(ctx.Match(k, Gfui_html.Evt_link_hover)) {
			if (this.Active_tab() != null)	// NOTE: this.Active_tab() should not be null, but is null when running on raspberry pi; DATE:2016-09-23
				Xog_win_itm__prog_href_mgr.Hover(app, cfg.Status__show_short_url(), this.Active_tab().Wiki(), this.Active_page(), Xoh_href_gui_utl.Standardize_xowa_link(m.ReadStr("v")));
		}
		else																return Gfo_invk_.Rv_unhandled;
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
	, Invk_exit = "exit"
	// xowa.gfs: shortcuts
	, Invk_page_goto = "page_goto", Invk_page_goto_recent = "page_goto_recent"
	; 
	private void Win__link_click() {	// NOTE: only applies when content_editable=y; if n, then link_click will be handled by SwtBrowser location changed (Win__link_clicked)
		// COMMENT: ignore content editable; DATE:2016-12-25
		//Xog_tab_itm tab = tab_mgr.Active_tab(); Xowe_wiki wiki = tab.Wiki();
		//if (wiki.Gui_mgr().Cfg_browser().Content_editable()) {	
		//	String href = tab.Html_itm().Html_box().Html_js_eval_proc_as_str(Xog_js_procs.Selection__get_active_for_editable_mode, Gfui_html.Atr_href, null);
		//	if (String_.Len_eq_0(href)) return; // NOTE: href can be null for images; EX: [[File:Loudspeaker.svg|11px|link=|alt=play]]; link= basically means don't link to image
		//	Page__navigate_by_href(tab, href);
		//}
	}
	private void Win__link_clicked(String anchor_raw) {
		String url = url_box.Text();
		int pos = String_.FindFwd(url, gplx.langs.htmls.Gfh_tag_.Anchor_str);
		if (pos != Bry_find_.Not_found) url = String_.Mid(url, 0, pos);
		String anchor_str = Parse_evt_location_changing(anchor_raw);
		byte[] anchor_bry = Bry_.new_u8(anchor_str);
		Xog_tab_itm tab = tab_mgr.Active_tab(); Xoae_page page = tab.Page();
		if (anchor_str != null) {									// link has anchor
			url_box.Text_(url + "#" + anchor_str);					// update url box
			page.Html_data().Bmk_pos_(Xog_history_itm.Html_doc_pos_toc); // HACK: anchor clicked; set docPos of curentPage to TOC (so back will go back to TOC)
			tab.History_mgr().Update_html_doc_pos(page, Xog_history_stack.Nav_by_anchor); // HACK: update history_mgr; note that this must occur before setting Anchor (since Anchor will generate a new history itm)
			page.Url().Anch_bry_(anchor_bry);						// update url
		}
		tab.History_mgr().Add(page);
		app.Usere().History_mgr().Add(page.Wiki().App(), page.Url(), page.Ttl(), Bry_.Add_w_dlm(Byte_ascii.Hash, page.Url().Page_bry(), anchor_bry));
	}
	public void App__exit() {
		kit.Kit_term();	// NOTE: Kit_term calls shell.close() which in turn is hooked up to app.Term_cbk() event; DATE:2014-09-09
	}
	private void App__eval(String s) {
		String snippet = this.Active_html_itm().Html_elem_atr_get_str(s, Gfui_html.Atr_innerHTML);
		app.Gfs_mgr().Run_str(snippet);
	}
	private static String Parse_evt_location_changing(String v) { // EX: about:blank#anchor -> anchor
		int pos = String_.FindFwd(v, gplx.langs.htmls.Gfh_tag_.Anchor_str);
		return pos == Bry_find_.Not_found
			? null
			: String_.Mid(v, pos + 1);
	}
	public void Page__mode_edit_() {	// only called from by link
		// HACK: when "edit" is clicked, always reload page from database; handles rarely-reproducible issue of "edit-after-rename" causing older versions to show up
		Xog_tab_itm tab = tab_mgr.Active_tab(); Xoae_page page = tab.Page(); Xowe_wiki wiki = tab.Wiki();
		page = wiki.Page_mgr().Load_page(page.Url(), page.Ttl(), tab);
		Page__mode_(Xopg_page_.Tid_edit);
	}
	public void Page__mode_(byte new_mode_tid) {
		Xog_tab_itm tab = tab_mgr.Active_tab(); Xoae_page page = tab.Page(); Xowe_wiki wiki = tab.Wiki();
		if (	new_mode_tid == Xopg_page_.Tid_read	// used to be && cur_view_tid == Edit; removed clause else redlinks wouldn't show when going form html to read (or clicking read multiple times) DATE: 2013-11-26;
			&&	page.Db().Page().Exists()			// if new page, don't try to reload
			) {
			// NOTE: if moving from "Edit" to "Read", reload page (else Preview changes will still show); NOTE: do not call Exec_page_reload / Exec_page_refresh, which will fire redlinks code
			page = tab_mgr.Active_tab().History_mgr().Cur_page(wiki);	// NOTE: must set to CurPage() else changes will be lost when going Bwd,Fwd
			tab.Page_(page);			
			wiki.Parser_mgr().Parse(page, true);		// NOTE: must reparse page if (a) Edit -> Read; or (b) "Options" save
			Xoa_url url = page.Url();
			if (url.Qargs_mgr().Match(Xoa_url_.Qarg__action, Xoa_url_.Qarg__action__edit))	// url has ?action=edit
				url = tab.Wiki().Utl__url_parser().Parse(url.To_bry_full_wo_qargs());	// remove all query args; handle (1) s.w:Earth?action=edit; (2) click on Read; DATE:2014-03-06
		}
		tab.View_mode_(new_mode_tid);
		if (page.Db().Page().Exists_n()) return;
		Xog_tab_itm_read_mgr.Show_page(tab, page, false);
		// Exec_page_refresh(); // commented out; causes lnke to show as [2] instead of [1] when saving page; EX: [http://a.org b] DATE:2014-04-24
	}
	public void Page__navigate_by_search()   {Page__navigate_by_url_bar(app.Gui_mgr().Win_cfg().Search_box_fmtr().Bld_str_many(search_box.Text()));}
	public void Page__navigate_by_allpages() {Page__navigate_by_url_bar(app.Gui_mgr().Win_cfg().Allpages_box_fmtr().Bld_str_many(allpages_box.Text()));}
	public void Page__navigate_by_url_bar(String href) {
		Xog_tab_itm tab = tab_mgr.Active_tab_assert();
		Xoa_url url = tab.Wiki().Utl__url_parser().Parse_by_urlbar_or_null(href); if (url == null) return;
		tab.Show_url_bgn(url);
	}
	private void Page__navigate_by_href(Xog_tab_itm tab, String href) {	// NOTE: different from Navigate_by_url_bar in that it handles "file:///" and other @gplx.Internal protected formats; EX: "/site/", "about:blank"; etc..
		Xoa_url url = Xog_url_wkr.Exec_url(this, href);
		if (url != Xog_url_wkr.Rslt_handled)
			tab.Show_url_bgn(url);
	}
	public void Page__navigate_by_history(boolean fwd) {
		Xog_tab_itm tab = tab_mgr.Active_tab();
		if (tab == Xog_tab_itm_.Null) return;
		Xoae_page cur_page = tab.Page(); Xowe_wiki cur_wiki = tab.Wiki();
		Xoae_page new_page = tab.History_mgr().Go_by_dir(cur_wiki, fwd);
		if (new_page.Db().Page().Exists_n()) return;
		if (new_page.Ttl().Ns().Id_is_special())		// if Special, reload page; needed for Special:Search (DATE:2015-04-19; async loading) and Special:XowaBookmarks DATE:2015-10-05
			new_page = new_page.Wikie().Data_mgr().Load_page_and_parse(new_page.Url(), new_page.Ttl());	// NOTE: must reparse page if (a) Edit -> Read; or (b) "Options" save
		else {
			// WORKAROUND: if wikinews, then reload page; DATE:2016-11-03
			// fixes bug wherein dump_html points images to wrong repo and causes images to be blank when going backwards / forwards
			// note that this workaround will cause Wikitext Wikinews pages to reload page when going bwd / fwd, but this should be a smalldifference
			if (new_page.Wiki().Domain_tid() == gplx.xowa.wikis.domains.Xow_domain_tid_.Tid__wikinews)
				new_page = new_page.Wikie().Page_mgr().Load_page(new_page.Url(), new_page.Ttl(), tab);
		}
		byte history_nav_type = fwd ? Xog_history_stack.Nav_fwd : Xog_history_stack.Nav_bwd;
		boolean new_page_is_same = Bry_.Eq(cur_page.Ttl().Full_txt_by_orig(), new_page.Ttl().Full_txt_by_orig());
		Xog_tab_itm_read_mgr.Show_page(tab, new_page, true, new_page_is_same, false, history_nav_type);
		Page__async__bgn(tab);
	}
	public void Page__reload() {
		Xog_tab_itm tab = tab_mgr.Active_tab();
		Xoae_page page = tab.History_mgr().Cur_page(tab.Wiki());	// NOTE: must set to CurPage() else changes will be lost when going Bwd,Fwd
		tab.Page_(page);
		page = page.Wikie().Page_mgr().Load_page(page.Url(), page.Ttl(), tab);	// NOTE: must reparse page if (a) Edit -> Read; or (b) "Options" save
		Page__refresh();
	}
	public void Page__refresh() {
		Page__refresh(tab_mgr.Active_tab());
	}
	public void Page__refresh(Xog_tab_itm tab) {
		Xoae_page page = tab.Page(); Xog_html_itm html_itm = tab.Html_itm();
		page = page.Wikie().Page_mgr().Load_page(page.Url(), page.Ttl(), tab);	// NOTE: refresh should always reload and regen page; DATE:2017-02-15
		page.Html_data().Bmk_pos_(html_itm.Html_box().Html_js_eval_proc_as_str(Xog_js_procs.Win__vpos_get));
		html_itm.Show(page);
		if (page.Url().Anch_str() == null)
			html_itm.Scroll_page_by_bmk_gui();
		else
			html_itm.Scroll_page_by_id_gui(page.Url().Anch_str());
		Page__async__bgn(tab);
	}
	public void Page__async__bgn(Xog_tab_itm tab) {
		page__async__thread = Thread_adp_.Start_by_val(gplx.xowa.apps.Xoa_thread_.Key_page_async, this, Invk_page_async_exec, tab);
	}	private Thread_adp page__async__thread = Thread_adp.Noop;
	public boolean Page__async__working(Xoa_url url) {
		if (page__async__thread.Thread__is_alive()) {				// cancel pending image downloads
			page__async__restart_url = url;
			this.Usr_dlg().Canceled_y_();
			app.Wmf_mgr().Download_wkr().Download_xrg().Prog_cancel_y_();
			Thread_adp_.Start_by_key(Invk_page_async_cancel_wait, this, Invk_page_async_cancel_wait);
			return true;
		}
		return false;
	}
	private void Page__async__cancel__wait() {
		while (page__async__thread.Thread__is_alive()) {
			Thread_adp_.Sleep(10);
		}
		this.Active_page().File_queue().Clear();
		this.Usr_dlg().Canceled_n_();	// NOTE: must mark "uncanceled", else one cancelation will stop all future downloads; DATE:2014-05-04
		Gfo_invk_.Invk_by_key(sync_cmd, Invk_page_async_restart);
	}
	private void Page__async__restart() {
		tab_mgr.Active_tab().Show_url_bgn(page__async__restart_url);
	}	private Xoa_url page__async__restart_url;
	public void Lang_changed(Xol_lang_itm lang) {
		Xoae_app app = gui_mgr.App();
		Xog_win_itm_.Update_tiptext(app, go_bwd_btn			, Xol_msg_itm_.Id_xowa_window_go_bwd_btn_tooltip);
		Xog_win_itm_.Update_tiptext(app, go_fwd_btn			, Xol_msg_itm_.Id_xowa_window_go_fwd_btn_tooltip);
		Xog_win_itm_.Update_tiptext(app, url_box			, Xol_msg_itm_.Id_xowa_window_url_box_tooltip);
		Xog_win_itm_.Update_tiptext(app, url_exec_btn		, Xol_msg_itm_.Id_xowa_window_url_btn_tooltip);
		Xog_win_itm_.Update_tiptext(app, search_box			, Xol_msg_itm_.Id_xowa_window_search_box_tooltip);
		Xog_win_itm_.Update_tiptext(app, search_exec_btn	, Xol_msg_itm_.Id_xowa_window_search_btn_tooltip);
		Xog_win_itm_.Update_tiptext(app, allpages_box		, Xol_msg_itm_.Id_xowa_window_allpages_box_tooltip);
		Xog_win_itm_.Update_tiptext(app, allpages_exec_btn	, Xol_msg_itm_.Id_xowa_window_allpages_btn_tooltip);
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

			// parse url according to rules of home_wiki; 
			Xowe_wiki home_wiki = app.Usere().Wiki();
			Xoa_url url = home_wiki.Utl__url_parser().Parse_by_urlbar_or_null(url_str); if (url == null) return Bry_.Empty;

			// get wiki from url
			Xowe_wiki wiki = (Xowe_wiki)app.Wiki_mgr().Get_by_or_make_init_y(url.Wiki_bry());

			// parse url again, but this time according to rules of actual wiki
			url = wiki.Utl__url_parser().Parse(Bry_.new_u8(url_str));

			// get title
			Xoa_ttl ttl = Xoa_ttl.Parse(wiki, url.Page_bry());

			// get tab for Load_page
			gplx.xowa.apps.servers.Gxw_html_server.Assert_tab(app, Xoae_page.Empty);		// HACK: assert at least 1 tab for Firefox addon; DATE:2015-01-23
			Xog_tab_itm tab = tab_mgr.Active_tab();

			// get page
			Xoae_page new_page = wiki.Page_mgr().Load_page(url, ttl, tab);
			if (new_page.Db().Page().Exists_n()) {return Bry_.Empty;}

			// update tab-specific vars
			tab.Page_(new_page);
			tab.History_mgr().Add(new_page);

			// gen html
			byte[] rv = output_html
				? wiki.Html_mgr().Page_wtr_mgr().Gen(new_page, tab.View_mode())
				: new_page.Db().Text().Text_bry();
			if (app.Shell().Fetch_page_exec_async())
				app.Gui_mgr().Browser_win().Page__async__bgn(tab);
			return rv;
		}
	}	private Object App__retrieve__lock = new Object();
	public void Init_by_kit(Gfui_kit kit) {
		this.kit = kit;
		this.win_box = kit.New_win_app("win");
		this.sync_cmd		= win_box.Kit().New_cmd_sync(this);
		FontAdp ui_font		= app.Gui_mgr().Win_cfg().Font().To_font();

		win_box.Layout_mgr_(new Swt_layout_mgr__grid().Cols_(1).Margin_w_(0).Margin_h_(0).Spacing_h_(0));

		// toolbar
		this.toolbar_grp    = Xog_win_itm_.new_grp(app, kit, win_box, "toolbar_grp");
		go_bwd_btn			= Xog_win_itm_.new_btn(app, kit, toolbar_grp, "go_bwd_btn");
		go_fwd_btn			= Xog_win_itm_.new_btn(app, kit, toolbar_grp, "go_fwd_btn");
		url_box				= Xog_win_itm_.new_cbo(app, kit, toolbar_grp, ui_font, "url_box", true);
		url_exec_btn		= Xog_win_itm_.new_btn(app, kit, toolbar_grp, "url_exec_btn");
		search_box			= Xog_win_itm_.new_txt(app, kit, toolbar_grp, ui_font, "search_box", true);
		search_exec_btn		= Xog_win_itm_.new_btn(app, kit, toolbar_grp, "search_exec_btn");
		allpages_box		= Xog_win_itm_.new_txt(app, kit, toolbar_grp, ui_font, "allpages_box", true);
		allpages_exec_btn	= Xog_win_itm_.new_btn(app, kit, toolbar_grp, "allpages_exec_btn");

		toolbar_grp.Layout_data_(new Swt_layout_data__grid().Grab_excess_w_(true).Align_w__fill_().Hint_h_(28));
		toolbar_grp.Layout_mgr_(new Swt_layout_mgr__grid().Cols_(8)
			.Margin_w_(4)   // sets space to far-left / right window edges
			.Margin_h_(1)   // sets space to top-menu and bot-html
			.Spacing_w_(4)  // sets space between buttons, or else very squished
			.Spacing_h_(0)  // not needed since only one row, but be explicit
			);
		int toolbar_grp_h = Xog_win_itm_.Toolbar_grp_h; // WORKAROUND.SWT: need to specify height, else SWT will shrink textbox on re-layout when showing / hiding search / allpages; DATE:2017-03-28
		int toolbar_txt_w = Xog_win_itm_.Toolbar_txt_w;
		int toolbar_btn_w = Xog_win_itm_.Toolbar_btn_w;
		url_box.Layout_data_(new Swt_layout_data__grid().Grab_excess_w_(true).Align_w__fill_().Min_w_(100).Hint_h_(toolbar_grp_h));
		search_box.Layout_data_(new Swt_layout_data__grid().Hint_w_(toolbar_txt_w).Hint_h_(toolbar_grp_h));
		search_exec_btn.Layout_data_(new Swt_layout_data__grid().Align_w__fill_().Hint_w_(toolbar_btn_w).Hint_h_(toolbar_grp_h));
		allpages_box.Layout_data_(new Swt_layout_data__grid().Hint_w_(toolbar_txt_w).Hint_h_(toolbar_grp_h));
		allpages_exec_btn.Layout_data_(new Swt_layout_data__grid().Align_w__fill_().Hint_w_(toolbar_btn_w).Hint_h_(toolbar_grp_h)); // force 20 width to add even more space to right-hand of screen

		// tab / html space
		tab_mgr.Init_by_kit(kit);
		tab_mgr.Tab_mgr().Layout_data_(new Swt_layout_data__grid().Grab_excess_h_(true).Align_w__fill_().Align_h__fill_().Grab_excess_w_(true)); 

		// statusbar
		this.statusbar_grp = Xog_win_itm_.new_grp(app, kit, win_box, "statusbar_grp");
		find_close_btn		= Xog_win_itm_.new_btn(app, kit, statusbar_grp, "find_close_btn");
		find_box			= Xog_win_itm_.new_txt(app, kit, statusbar_grp, ui_font, "find_box"								, true);
		find_fwd_btn		= Xog_win_itm_.new_btn(app, kit, statusbar_grp, "find_fwd_btn");
		find_bwd_btn		= Xog_win_itm_.new_btn(app, kit, statusbar_grp, "find_bwd_btn");
		prog_box			= Xog_win_itm_.new_txt(app, kit, statusbar_grp, ui_font, "prog_box"								, false);
		info_box			= Xog_win_itm_.new_txt(app, kit, statusbar_grp, ui_font, "note_box"								, false);

		statusbar_grp.Layout_data_(new Swt_layout_data__grid().Grab_excess_w_(true).Align_w__fill_().Hint_h_(28));
		statusbar_grp.Layout_mgr_(new Swt_layout_mgr__grid().Cols_(6)
			.Margin_w_(4)	// adds buffer on far-left / right window edges
			.Margin_h_(0)	// sets space to top-html / bot-window
			.Spacing_w_(4)  // sets space between buttons, or else very squished
			.Spacing_h_(0)  // not needed since only one row, but be explicit
			);
		find_box.Layout_data_(new Swt_layout_data__grid().Hint_w_(108)); // 108 is a magic number to have status bar text line up with vertical bar on Wikipedia style sheets
		prog_box.Layout_data_(new Swt_layout_data__grid().Grab_excess_w_(true).Align_w__fill_().Min_w_(100));
		info_box.Layout_data_(new Swt_layout_data__grid().Hint_w_(0));  // hide for now; may add back later if need static text in corner; DATE:2017-02-15

		this.Lang_changed(app.Usere().Lang());

		Gfo_evt_mgr_.Sub_same_many(this, this, Gfui_html.Evt_location_changed, Gfui_html.Evt_location_changing, Gfui_html.Evt_link_hover);
		Gfo_evt_mgr_.Sub(app.Gui_mgr().Win_cfg().Font(), Xol_font_info.Font_changed, this, Invk_window_font_changed);
		url_box__selection_changed = new Xog_url_box__selection_changed(app, url_box);
		Gfo_evt_mgr_.Sub_same(url_box, GfuiComboBox.Evt__selected_changed, url_box__selection_changed);
		Gfo_evt_mgr_.Sub_same(url_box, GfuiComboBox.Evt__selected_accepted, url_box__selection_changed);

		if (	!Env_.Mode_testing()
			&&	app.Mode().Tid_is_gui())	// only run for gui; do not run for tcp/http server; DATE:2014-05-03
			app.Usr_dlg().Gui_wkr_(new Gfo_usr_dlg__gui__swt(app, kit, prog_box, info_box, info_box));
		cfg.Init_by_app(app);

		url_box_fmtr.Init_by_app(app);
	}
	public static String Remove_redirect_if_exists(String text) {
		// remove redirect target; EX: "A -> B" -> "A"
		int redirect_pos = String_.FindFwd(text, gplx.xowa.addons.wikis.searchs.searchers.rslts.Srch_rslt_row.Str__redirect__text);
		if (redirect_pos != Bry_find_.Not_found) {
			text = String_.Mid(text, 0, redirect_pos);
		}
		return text;
	}
}
class Xog_url_box__selection_changed implements Gfo_evt_itm {
	private final    GfuiComboBox url_box;
	private final    Xoae_app app;
	public Xog_url_box__selection_changed(Xoae_app app, GfuiComboBox url_box) {this.app = app; this.url_box = url_box; this.ev_mgr = new Gfo_evt_mgr(this);}
	public Gfo_evt_mgr Evt_mgr() {return ev_mgr;} private final    Gfo_evt_mgr ev_mgr;
	private void On_selection_changed() {
		String text = url_box.Text();
		text = Xog_win_itm.Remove_redirect_if_exists(text);
		// always move cursor to end; emulates firefox url_bar behavior
		url_box.Text_(text);
		url_box.Sel_(String_.Len(text), String_.Len(text));
	}
	private void On_selection_accepted() {
		app.Api_root().Nav().Goto(url_box.Text());
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, GfuiComboBox.Evt__selected_changed))			On_selection_changed();
		else if	(ctx.Match(k, GfuiComboBox.Evt__selected_accepted))			On_selection_accepted();
		else																return Gfo_invk_.Rv_unhandled;
		return this;
	}
}
