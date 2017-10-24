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
import gplx.gfui.*; import gplx.gfui.ipts.*; import gplx.gfui.kits.core.*; import gplx.gfui.controls.elems.*; import gplx.gfui.controls.standards.*;
import gplx.xowa.guis.history.*; import gplx.xowa.guis.bnds.*;
import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
import gplx.xowa.langs.vnts.*;
import gplx.xowa.parsers.*; import gplx.xowa.wikis.pages.lnkis.*;
import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.pages.skins.*;
public class Xog_tab_itm implements Gfo_invk {
	private Xog_win_itm win;
	public Xog_tab_itm(Xog_tab_mgr tab_mgr, Gfui_tab_itm_data tab_data, Xowe_wiki wiki, Xoae_page page) {
		this.tab_mgr = tab_mgr; this.tab_data = tab_data; this.wiki = wiki; this.page = page;
		this.win = tab_mgr.Win();
		this.html_itm = new Xog_html_itm(this);
		cmd_sync = win.Kit().New_cmd_sync(this);
	}
	public Xowe_wiki Wiki() {return wiki;} public void Wiki_(Xowe_wiki v) {this.wiki = v;} private Xowe_wiki wiki;
	public Gfo_invk Cmd_sync() {return cmd_sync;} private Gfo_invk cmd_sync;
	public void Make_html_box(int uid, Gfui_tab_itm tab_box, Xog_win_itm win, GfuiElem owner) {
		this.tab_box = tab_box;
		Xoae_app app = win.App(); Xoa_gui_mgr gui_mgr = win.Gui_mgr(); Gfui_kit kit = win.Kit();
		Gfui_html html_box	= kit.New_html("html_box" + Int_.To_str(uid), owner);
		html_box.Html_js_enabled_(tab_mgr.Javascript_enabled());
		html_box.Html_invk_src_(win);
		html_itm.Html_box_(html_box);
		if (app.Mode().Tid_is_gui()) {	// NOTE: only run for gui; will cause firefox_addon to fail; DATE:2014-05-03								
			// NOTE: must set source, else control will be empty, and key events will not be raised; DATE:2014-04-30
			// NOTE: use load_by_url for nightmode else load_by_mem causes "white flashing" DATE:2017-03-04
			if (app.Gui_mgr().Nightmode_mgr().Enabled()) {
				html_box.Html_doc_html_load_by_url
				( app.Usere().Fsys_mgr().App_temp_html_dir().GenSubFil("tab_new.html")
				, String_.new_u8(gplx.xowa.specials.xowa.default_tab.Default_tab_page.DEFAULT_HTML_NIGHT)
				);
			}
			else {
				html_box.Html_doc_html_load_by_mem("");
			}
			IptBnd_.ipt_to_(IptCfg_.Null, html_box, this, "popup", IptEventType_.MouseDown, IptMouseBtn_.Right);
			IptBnd_.cmd_to_(IptCfg_.Null, html_box, win, Xog_win_itm.Invk_exit, IptKey_.add_(IptKey_.Alt, IptKey_.F4));	// WORKAROUND:SWT: xulrunner_v24 no longer sends Alt+F4 to SwtShell; must manually subscribe it to quit; DATE:2015-07-31
			Gfo_evt_mgr_.Sub_same(html_box, GfuiElemKeys.Evt_menu_detected, html_itm);
			gui_mgr.Bnd_mgr().Bind(Xog_bnd_box_.Tid_browser_html, html_box);
			if (!Env_.Mode_testing())
				kit.Set_mnu_popup(html_box, gui_mgr.Menu_mgr().Popup().Html_page().Under_mnu());
		}
	}
	public void Switch_mem(Xog_tab_itm comp) {
		html_itm.Switch_mem(comp.html_itm);					// switch html_itm.owner_tab reference only
		Xog_html_itm temp_html_itm = html_itm;				// switch .html_itm, since the underlying CTabFolder has reparented the control
		this.html_itm = comp.html_itm;
		comp.html_itm = temp_html_itm;

		Xoae_page temp_page = page;							// switch .page, since its underlying html_box has changed and .page must reflect html_box
		this.page = comp.page;
		comp.page = temp_page;
		page.Tab_data().Tab_(this); comp.Page().Tab_data().Tab_(comp);

		byte temp_view_mode = view_mode;					// switch .view_mode to match .page
		this.view_mode = comp.view_mode;
		comp.view_mode = temp_view_mode;

		Xog_history_mgr temp_history_mgr = history_mgr;		// switch .history_mgr along with .page
		this.history_mgr = comp.history_mgr;
		comp.history_mgr = temp_history_mgr;
	}
	public Gfui_tab_itm_data	Tab_data() {return tab_data;} private Gfui_tab_itm_data tab_data;
	public String				Tab_key() {return tab_data.Key();}
	public int					Tab_idx() {return tab_data.Idx();} public void Tab_idx_(int v) {tab_data.Idx_(v);}
	public Xog_tab_mgr			Tab_mgr() {return tab_mgr;} private Xog_tab_mgr tab_mgr;
	public Gfui_tab_itm			Tab_box() {return tab_box;} private Gfui_tab_itm tab_box;
	public boolean				Tab_is_loading() {return tab_is_loading;} private boolean tab_is_loading;
	public Xog_html_itm			Html_itm() {return html_itm;} private Xog_html_itm html_itm;
	public Gfui_html			Html_box() {return html_itm.Html_box();}
	public Xoae_page			Page() {return page;}
	public void Page_ref_(Xoae_page v) {
		this.page = v;
		this.wiki = page.Wikie();	// NOTE: must update wiki else back doesn't work; DATE:2015-03-05
	}
	public void Page_(Xoae_page page) {
		Page_ref_(page);
		this.Page_update_ui();	// force tab button to update when page changes
	}	private Xoae_page page;		
	public void Page_update_ui() {
		this.Tab_name_();
		tab_box.Tab_tip_text_(page.Url().To_str());
	}
	public void Tab_name_() {
		byte[] tab_name = page.Html_data().Custom_tab_name();				// Custom_tab_name set by Special:Default_tab or variants; DATE:2015-10-05
		if (tab_name == null) tab_name = page.Ttl().Full_txt_w_ttl_case();	// no custom_tab_name; use ttl's text
		Tab_name_(String_.new_u8(tab_name));
	}
	public void Tab_name_(String tab_name) {
		tab_name = Xog_tab_itm_.Tab_name_min(tab_name, tab_mgr.Btns__min_chars());
		tab_name = Xog_tab_itm_.Tab_name_max(tab_name, tab_mgr.Btns__max_chars());
		tab_box.Tab_name_(tab_name);
	}
	public Xog_history_mgr		History_mgr() {return history_mgr;} private Xog_history_mgr history_mgr = new Xog_history_mgr();
	public byte					View_mode() {return view_mode;} public Xog_tab_itm View_mode_(byte v) {view_mode = v; return this;} private byte view_mode = Xopg_page_.Tid_read;
	public void Pin_toggle() {}
	public void Show_url_bgn(Xoa_url url) {
		this.tab_is_loading = true;
		Xoae_app app = win.App(); Gfo_usr_dlg usr_dlg = app.Usr_dlg();

		// get new_tab_name
		this.wiki = (Xowe_wiki)app.Wiki_mgr().Get_by_or_make_init_y(url.Wiki_bry());	// NOTE: must go before wiki.Props().Main_page(); DATE:2016-08-02; NOTE: must load wiki; DATE:2015-07-22
		if (url.Page_is_main()) url.Page_bry_(wiki.Props().Main_page());				// NOTE: must go before ttl.Make; DATE:2016-07-31
		Xoa_ttl ttl = wiki.Ttl_parse(url.Page_bry());
		if (ttl == null) {usr_dlg.Prog_one("", "", "title is invalid: ~{0}", String_.new_u8(url.Raw())); return;}
		String new_tab_name = String_.new_u8(ttl.Full_txt_w_ttl_case());

		// if clicking on anchor, just scroll; do not load page
		if (	url.Anch_str() != null							// url has anchor
			&&	url.Eq_page(page.Url())							// url has same page_name as existing page
			&&	url.Qargs_ary().length == 0						// url has no args; needed for Category:A?from=b#mw-pages
			&&	String_.Eq(new_tab_name, tab_data.Name())		// NOTE: name will be null / empty when starting app and last session had page with #anchor; EX:Main_Page#Links; DATE:2016-07-21
			) {
			html_itm.Scroll_page_by_id_gui(url.Anch_str());	
			return;
		}
		if (win.Page__async__working(url)) return;
		if (page != null) page.Tab_data().Close_mgr().When_close(this, url);			// cancel any current search cmds
		app.Log_wtr().Queue_enabled_(true);
		usr_dlg.Gui_wkr().Clear();
		if (url.Vnt_bry() != null) {
			byte[] vnt = url.Vnt_bry();
			if (!Bry_.Eq(vnt, wiki.Lang().Vnt_mgr().Cur_itm().Key()))
				wiki.Appe().Cfg().Set_bry_wiki(wiki, Xowe_wiki.Cfg__variant__current, vnt);
		}
		Tab_name_(new_tab_name);
		usr_dlg.Prog_one("", "", "loading: ~{0}", String_.new_u8(ttl.Raw()));
		if (wiki.Html_mgr().Head_mgr().Popup_mgr().Enabled())
			this.Html_box().Html_js_eval_script("if (window.xowa_popups_hide_all != null) window.xowa_popups_hide_all();");	// should be more configurable; DATE:2014-07-09
		app.Thread_mgr_old().Page_load_mgr().Add_at_end(new Load_page_wkr(this, wiki, url, ttl)).Run();
	}
	private void Show_url_loaded(Load_page_wkr wkr) {
		Xowe_wiki wiki = wkr.Wiki(); Xoae_page page = wkr.Page();
		Xoa_url url = page.Url(); Xoa_ttl ttl = page.Ttl();
		Xoae_app app = wiki.Appe(); Gfo_usr_dlg usr_dlg = app.Usr_dlg();
		try {
			if (page.Tab_data().Cancel_show()) return;	// Special:Search canceled show; NOTE: must be inside try b/c finally handles thread
			wiki.Parser_mgr().Ctx().Page_(page);
			if (	page.Db().Page().Exists_n() 
				&& !page.Commons_mgr().Xowa_mockup()) {	// do not enter "missing" section if File_mockup; EX:en.wikipedia.org/wiki/File:Protoplanetary-disk.jpg DATE:2016-11-13
				if (wiki.Db_mgr().Save_mgr().Create_enabled()
					|| wiki.Page_mgr().Sync_mgr().Auto_enabled()) {
					page = Xoae_page.New_edit(wiki, ttl);
					view_mode = Xopg_page_.Tid_edit;
					history_mgr.Add(page);	// NOTE: must put new_page on stack so that pressing back will pop new_page, not previous page
					Xog_tab_itm_read_mgr.Show_page(this, page, false);
				}
				else {
					wkr.Page().Tab_data().Tab().Page_(page);	// NOTE: must set tab's page to current page, so that switching to it will update url bar; EX:pt.b:A"MANUAL_DE_PROCEDURI_.Sectiunea:""CONTABILITATE_SI_MANAGEMENT_FINANCIAR""" DATE:2015-09-17
					if (page.Redirect_trail().Itms__len() > 0)
						usr_dlg.Prog_many("", "", "could not find page in wiki: ~{0} (redirected from ~{1})", String_.new_u8(page.Url().Page_bry()), page.Redirect_trail().Itms__get_at_0th_or_null());
					else {
						if (ttl.Ns().Id_is_file())
							usr_dlg.Prog_one("", "", "commons.wikimedia.org must be installed in order to view the file. See [[App/Wiki_types/Commons]]: ~{0}", String_.new_u8(url.Raw()));// HOME
						else
							usr_dlg.Prog_one("", "", "could not find page in wiki: ~{0}", String_.new_u8(url.Raw()));
					}
				}
				app.Log_wtr().Queue_enabled_(false);
				return;
			}
			// if (!page.Redirected()) page.Url_(url);	// NOTE: handle redirect from commons; COMMENTED: part of redirect rewrite; DATE:2016-07-05
			if (page.Ttl().Anch_bgn() != Bry_find_.Not_found) page.Url().Anch_bry_(page.Ttl().Anch_txt());	// NOTE: occurs when page is a redirect to an anchor; EX: w:Duck race -> Rubber duck#Races
			history_mgr.Add(page);
			Xog_tab_itm_read_mgr.Show_page(this, page, true);
			if (app.Usere().History_mgr().Enabled()) {
				app.Usere().History_mgr().Add(page);
				app.User().User_db_mgr().History_mgr().Update_async(app.Async_mgr(), ttl, url);
			}
			usr_dlg.Prog_none("", "", "rendering html");
			// html_itm.Html_box().Size_(tab_mgr.Tab_mgr().Size()); // COMMENTED: causes clicks on macosx to be off by 4 px; NOTE: must resize tab here, else scrolling to anchor in background tab doesn't work (html_box has size of 0, 0) DATE:2015-05-03
			//	win.Page__async__bgn(this);
			Gfo_thread_wkr async_wkr = null;
			if (page.Html_data().Hdump_exists()) {
//					wiki.File_mgr().Init_file_mgr_by_load(wiki);
//					Xof_fsdb_mgr fsdb_mgr = wiki.File_mgr().Fsdb_mgr();
//					async_wkr = new Xof_file_wkr(wiki.File__orig_mgr(), fsdb_mgr.Bin_mgr(), fsdb_mgr.Mnt_mgr(), app.Usere().User_db_mgr().Cache_mgr(), wiki.File__repo_mgr(), html_itm, page, page.Hdump_mgr().Imgs());
				async_wkr = new Load_files_wkr(this);
				if (wiki.Html__hdump_enabled() && page.Db().Page().Html_db_id() == -1) {
					wiki.Html__hdump_mgr().Save_mgr().Save(page);
				}
			}
			else
				async_wkr = new Load_files_wkr(this);

			page.Html_data().Mode_wtxt_shown_y_();
			app.Thread_mgr_old().File_load_mgr().Add_at_end(async_wkr).Run();
			// app.Thread_mgr().Get_by_or_new("on_page_load").Add(new Xopg_img_thread(), new Xopg_rl_thread());
		}
		finally {
			app.Thread_mgr_old().Page_load_mgr().Resume();
			this.tab_is_loading = false;
		}
	}
	public void Exec_async_hdump(Xoa_app app, Xow_wiki wiki, gplx.xowa.guis.cbks.js.Xog_js_wkr js_wkr, gplx.core.threads.Gfo_thread_pool thread_pool, Xoa_page page, List_adp imgs, int[] redlink_ary) {
		if (imgs.Count() > 0) {
			Xof_file_wkr file_thread = new Xof_file_wkr
				( wiki.File__orig_mgr(), wiki.File__bin_mgr(), wiki.File__mnt_mgr()
				, app.User().User_db_mgr().Cache_mgr(), wiki.File__repo_mgr(), html_itm, page, imgs
				);
			thread_pool.Add_at_end(file_thread); thread_pool.Run();
		}
		if (redlink_ary.length > 0) {
			Xog_redlink_thread redlink_thread = new Xog_redlink_thread(redlink_ary, js_wkr);
			thread_pool.Add_at_end(redlink_thread); thread_pool.Run();
		}
	}
	public void Exec_notify(boolean pass, String msg) {
		this.Html_box().Html_js_eval_proc_as_str("xowa.cmds.exec_by_str", "xowa.notify", "{\"text\":\"" + msg + "\",\"status\":\"" + (pass ? "success" : "error") + "\"}");
	}
	@gplx.Internal protected void Show_url_failed(Load_page_wkr wkr) {
		try {
			Xog_tab_itm_read_mgr.Show_page_err(win, this, wkr.Wiki(), wkr.Url(), wkr.Ttl(), wkr.Exec_err());
		} finally {
			wkr.Wiki().Appe().Thread_mgr_old().Page_load_mgr().Resume();
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_show_url_loaded_swt))	this.Show_url_loaded((Load_page_wkr)m.ReadObj("v"));
		else if	(ctx.Match(k, Invk_show_url_failed_swt))	this.Show_url_failed((Load_page_wkr)m.ReadObj("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final String Invk_show_url_loaded_swt = "show_url_loaded_swt", Invk_show_url_failed_swt = "show_url_failed_swt";
}
class Load_files_wkr implements Gfo_thread_wkr {
	private Xog_tab_itm tab;
	public Load_files_wkr(Xog_tab_itm tab) {this.tab = tab;}
	public String			Thread__name() {return "xowa.load_files_wkr";}
	public boolean			Thread__resume() {return true;}
	public void Thread__exec() {
		try {Xog_async_wkr.Async(tab);}
		catch (Exception e) {
			tab.Tab_mgr().Win().App().Usr_dlg().Warn_many("error while running file wkr; page=~{0} err=~{1}", tab.Page().Url().To_str(), Err_.Message_gplx_full(e));
		}
	}
}
