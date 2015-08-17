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
package gplx.xowa.gui; import gplx.*; import gplx.xowa.*;
import gplx.gfui.*; import gplx.xowa.specials.search.*; import gplx.xowa.gui.menus.*; import gplx.xowa.gui.cmds.*; import gplx.xowa.cfgs.gui.*; import gplx.xowa.users.*;
import gplx.xowa.gui.bnds.*; import gplx.xowa.gui.views.*; import gplx.xowa.gui.urls.url_macros.*;
import gplx.xowa.gui.views.boots.*;
public class Xoa_gui_mgr implements GfoEvObj, GfoInvkAble {
	public Xoa_gui_mgr(Xoae_app app) {
		this.ev_mgr = GfoEvMgr.new_(this);
		this.app = app;
		this.browser_win = new Xog_win_itm(app, this);
		bnd_mgr = new Xog_bnd_mgr(browser_win);
		win_cfg = new Xocfg_win(app);
		html_mgr = new Xog_html_mgr(app);
		menu_mgr = new Xog_menu_mgr(this);
		search_suggest_mgr = new Xog_search_suggest_mgr(this);
	}
	public GfoEvMgr EvMgr() {return ev_mgr;} private GfoEvMgr ev_mgr;
	public Xoae_app App() {return app;} private Xoae_app app;
	public Xog_win_itm Browser_win() {return browser_win;} private final Xog_win_itm browser_win;
	public IptCfgRegy Ipt_cfgs() {return ipt_cfgs;} IptCfgRegy ipt_cfgs = new IptCfgRegy();
	public Xog_bnd_mgr Bnd_mgr() {return bnd_mgr;} private Xog_bnd_mgr bnd_mgr;
	public Gfui_kit Kit() {return kit;} private Gfui_kit kit = Gfui_kit_.Mem();
	public Xog_cmd_mgr Cmd_mgr() {return cmd_mgr;} private Xog_cmd_mgr cmd_mgr = new Xog_cmd_mgr();
	public Xocfg_win Win_cfg() {return win_cfg;} private Xocfg_win win_cfg;
	public Xog_layout Layout() {return layout;} private Xog_layout layout = new Xog_layout();
	public Xog_html_mgr Html_mgr() {return html_mgr;} private Xog_html_mgr html_mgr;
	public Xog_search_suggest_mgr Search_suggest_mgr() {return search_suggest_mgr;} private Xog_search_suggest_mgr search_suggest_mgr;
	public Xog_menu_mgr Menu_mgr() {return menu_mgr;} private Xog_menu_mgr menu_mgr;
	public Xog_url_macro_mgr Url_macro_mgr() {return url_macro_mgr;} private Xog_url_macro_mgr url_macro_mgr = new Xog_url_macro_mgr();
	public void Show_prog() {
		GfuiWin memo_win = kit.New_win_utl("memo_win", browser_win.Win_box());
		GfuiTextBox memo_txt = kit.New_text_box("memo_txt", memo_win, KeyVal_.new_(GfuiTextBox_.Ctor_Memo, true));
		RectAdp prog_box_rect = browser_win.Prog_box().Rect();
		memo_win.Rect_set(RectAdp_.new_(prog_box_rect.X(), prog_box_rect.Y() - 75, prog_box_rect.Width(), 100));
		memo_txt.Size_(memo_win.Size().Op_add(-8, -30));
		memo_txt.Text_(String_.Concat_lines_nl(browser_win.Usr_dlg().Gui_wkr().Prog_msgs().Xto_str_ary()));
		memo_win.Show();
		memo_win.Focus();
	}
	public void Init_by_app() {
		layout_Init();
		bnd_mgr.Init();
		menu_mgr.Init_by_app(app);
	}
	public void Kit_(Gfui_kit kit) {
		this.kit = kit;
		kit.Kit_init(browser_win.Usr_dlg());
		kit.Kit_term_cbk_(GfoInvkAbleCmd.new_(app, Xoae_app.Invk_term_cbk));
		browser_win.Init_by_kit(kit);
		layout.Init(browser_win);
		cmd_mgr.Init_by_kit(app);
		app.Api_root().Init_by_kit(app);
		menu_mgr.Menu_bldr().Init_by_kit(app, kit, app.Usere().Fsys_mgr().App_img_dir().GenSubDir_nest("window", "menu"));
		menu_mgr.Init_by_kit();
		bnd_mgr.Init_by_kit(app);
		GfoEvMgr_.SubSame_many(app.Usere(), this, Xoue_user.Evt_lang_changed);
		app.Sys_cfg().Lang_(app.Sys_cfg().Lang());	// NOTE: force refresh of lang. must occur after after gui_mgr init, else menu lbls will break
	}
	public void Lang_changed(Xol_lang lang) {
		menu_mgr.Lang_changed(lang);
		browser_win.Lang_changed(lang);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_kit))							return kit;
		else if	(ctx.Match(k, Invk_kit_))							this.kit = Gfui_kit_.Get_by_key(m.ReadStrOr("v", Gfui_kit_.Swt().Key()));
		else if	(ctx.Match(k, Invk_run))							Run(RlsAble_.Null);
		else if	(ctx.Match(k, Invk_browser_type))					kit.Cfg_set("HtmlBox", "BrowserType", gplx.gfui.Swt_kit.Cfg_Html_BrowserType_parse(m.ReadStr("v")));
		else if	(ctx.Match(k, Invk_xul_runner_path_))				kit.Cfg_set("HtmlBox", "XulRunnerPath", Bry_fmtr_eval_mgr_.Eval_url(app.Url_cmd_eval(), m.ReadBry("v")).Xto_api());
		else if	(ctx.Match(k, Invk_bnds))							return bnd_mgr;
		else if	(ctx.Match(k, Invk_bindings))						return ipt_cfgs;
		else if	(ctx.MatchIn(k, Invk_main_win, Invk_browser_win))	return browser_win;
		else if	(ctx.Match(k, Invk_win_opts))						return win_cfg;
		else if	(ctx.Match(k, Invk_layout))							return layout;
		else if	(ctx.Match(k, Invk_html))							return html_mgr;
		else if	(ctx.Match(k, Invk_search_suggest))					return search_suggest_mgr;
		else if	(ctx.Match(k, Invk_menus))							return menu_mgr;
		else if	(ctx.Match(k, Invk_cmds))							return cmd_mgr;
		else if	(ctx.Match(k, Invk_url_macros))						return url_macro_mgr;
		else if	(ctx.Match(k, Xoue_user.Evt_lang_changed))			Lang_changed((Xol_lang)m.ReadObj("v", ParseAble_.Null));
		else throw Err_.new_unhandled(k);
		return this;
	}
	private static final String 
	  Invk_kit = "kit", Invk_kit_ = "kit_", Invk_browser_type = "browser_type_", Invk_xul_runner_path_ = "xul_runner_path_", Invk_run = "run"
	, Invk_main_win = "main_win", Invk_browser_win = "browser_win", Invk_bnds = "bnds"
	, Invk_bindings = "bindings", Invk_win_opts = "win_opts", Invk_layout = "layout", Invk_html = "html"
	, Invk_search_suggest = "search_suggest", Invk_menus = "menus", Invk_cmds = "cmds", Invk_url_macros = "url_macros";
	public void Run(RlsAble splash_win) {
		Gfo_log_bfr log_bfr = app.Log_bfr();
		try {
			Xoa_gui_mgr ui_mgr = app.Gui_mgr();
			Gfui_kit kit = ui_mgr.Kit();
			ui_mgr.Kit_(kit); log_bfr.Add("app.gui.kit_init.done");
			Xog_win_itm main_win = ui_mgr.Browser_win();
			Xog_win_itm_.Show_win(main_win); log_bfr.Add("app.gui.win_load.done");
			Xog_tab_itm_read_mgr.Launch(main_win);
			app.Log_wtr().Log_to_session_direct(log_bfr.Xto_str());
			splash_win.Rls();
			kit.Kit_run();	// NOTE: enters thread-loop
		} catch (Exception e) {
			app.Usr_dlg().Warn_many("", "", "run_failed: ~{0} ~{1}", log_bfr.Xto_str(), Err_.Message_gplx_full(e));
			if (app.Gui_mgr().Kit() != null) app.Gui_mgr().Kit().Ask_ok("", "", Err_.Message_gplx_full(e));
		}
	}
	private void layout_Init() {
		Op_sys os = Op_sys.Cur();
		int html_box_w = -8; int html_box_h = -30;	// default adjustments since version 0.0.0.0; seems to work on XP and LNX
		switch (os.Tid()) {
			case Op_sys.Tid_wnt:
				switch (os.Sub_tid()) {
					case Op_sys.Sub_tid_win_xp:	break;	// NOOP; will keep values as above
					default:							// for all else, use Windows 7 border; note that Windows 8 is being detected as "Windows NT (unknown)", so need to use default; this may not work with Windows 2000
						html_box_w = -16; html_box_h = -40;	// Windows 7 has a thicker windows border and the html_box must be smaller else scroll bar gets cut off
						break;
				}
				break;
			default:
				break;
		}
		layout.Html_box().W_rel_(html_box_w).H_rel_(html_box_h);
	}
}
