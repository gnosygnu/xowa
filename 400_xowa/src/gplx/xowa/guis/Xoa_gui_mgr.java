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
package gplx.xowa.guis; import gplx.*; import gplx.xowa.*;
import gplx.core.brys.fmtrs.*; import gplx.core.envs.*;
import gplx.gfui.*; import gplx.gfui.ipts.*; import gplx.gfui.kits.core.*; import gplx.gfui.controls.windows.*; import gplx.gfui.controls.standards.*;
import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.guis.menus.*; import gplx.xowa.guis.cmds.*; import gplx.xowa.apps.cfgs.*; import gplx.xowa.users.*;
import gplx.xowa.langs.*;
import gplx.xowa.guis.bnds.*; import gplx.xowa.guis.views.*; import gplx.xowa.guis.urls.url_macros.*; import gplx.xowa.addons.wikis.searchs.gui.htmlbars.*;
import gplx.xowa.guis.views.boots.*; import gplx.xowa.guis.views.nightmodes.*;
import gplx.gfui.layouts.swts.*;
public class Xoa_gui_mgr implements Gfo_evt_itm, Gfo_invk {
	public Xoa_gui_mgr(Xoae_app app) {
		this.ev_mgr = new Gfo_evt_mgr(this);
		this.app = app;
		this.browser_win = new Xog_win_itm(app, this);
		bnd_mgr = new Xog_bnd_mgr(browser_win);
		html_mgr = new Xog_html_mgr(app);
		menu_mgr = new Xog_menu_mgr(this);
	}
	public Gfo_evt_mgr Evt_mgr() {return ev_mgr;} private Gfo_evt_mgr ev_mgr;
	public Xoae_app App() {return app;} private Xoae_app app;
	public Xog_win_itm Browser_win() {return browser_win;} private final    Xog_win_itm browser_win;
	public IptCfgRegy Ipt_cfgs() {return ipt_cfgs;} IptCfgRegy ipt_cfgs = new IptCfgRegy();
	public Xog_bnd_mgr Bnd_mgr() {return bnd_mgr;} private Xog_bnd_mgr bnd_mgr;
	public Gfui_kit Kit() {return kit;} private Gfui_kit kit = Gfui_kit_.Mem();
	public Xog_cmd_mgr Cmd_mgr() {return cmd_mgr;} private Xog_cmd_mgr cmd_mgr = new Xog_cmd_mgr();
	public Xocfg_win Win_cfg() {return win_cfg;} private Xocfg_win win_cfg = new Xocfg_win();
	public Xog_layout Layout() {return layout;} private Xog_layout layout = new Xog_layout();
	public Xog_html_mgr Html_mgr() {return html_mgr;} private Xog_html_mgr html_mgr;
	public Xog_menu_mgr Menu_mgr() {return menu_mgr;} private Xog_menu_mgr menu_mgr;
	public Xog_url_macro_mgr Url_macro_mgr() {return url_macro_mgr;} private final    Xog_url_macro_mgr url_macro_mgr = new Xog_url_macro_mgr();
	public Xog_nightmode_mgr Nightmode_mgr() {return nightmode_mgr;} private final    Xog_nightmode_mgr nightmode_mgr = new Xog_nightmode_mgr();
	public void Show_prog() {
		// get rects for positioning
		RectAdp statusbar_rect = browser_win.Statusbar_grp().Rect();
		RectAdp prog_box_rect = browser_win.Prog_box().Rect();

		// create window using rects
		GfuiWin memo_win = kit.New_win_utl("memo_win", browser_win.Win_box());
		memo_win.Layout_mgr_(new Swt_layout_mgr__grid().Cols_(1).Margin_w_(0).Margin_h_(0).Spacing_h_(0));
		memo_win.Rect_set(RectAdp_.new_
			( prog_box_rect.X()
			, statusbar_rect.Y() - 60     // 60=100 - 30 (height of title bar and window border)
			, prog_box_rect.Width() + 10  // 10=.Margin_w of statusbar_grp
			, 100));

		// create text
		GfuiTextBox memo_txt = kit.New_text_box("memo_txt", memo_win, Keyval_.new_(GfuiTextBox_.Ctor_Memo, true));
		memo_txt.Layout_data_(new Swt_layout_data__grid().Grab_excess_w_(Bool_.Y).Grab_excess_h_(Bool_.Y).Align_w__fill_().Align_h__fill_());
		memo_txt.Text_(String_.Concat_lines_nl(browser_win.Usr_dlg().Gui_wkr().Prog_msgs().Xto_str_ary()));

		// show and focus
		memo_win.Show();
		memo_win.Focus();
	}
	public void Init_by_app() {
		layout_Init();
		bnd_mgr.Init();
		menu_mgr.Init_by_app(app);
		if (app.Mode().Tid_is_gui()) {
			app.Gui__cbk_mgr().Reg(new gplx.xowa.guis.cbks.swts.Xog_cbk_wkr__swt(this));
			Io_url gfo_log_dir = app.Fsys_mgr().Root_dir().GenSubDir_nest("user", "anonymous", "app", "tmp", "xolog");
			Gfo_log_.Instance__set(new gplx.xowa.guis.cbks.swts.Gfo_log__swt(app.Gui__cbk_mgr()
				, Gfo_log_.New_url(gfo_log_dir)
				, new gplx.core.logs.Gfo_log_itm_wtr__csv()));
			gplx.core.logs.Gfo_log__file.Delete_old_files(gfo_log_dir, Gfo_log_.Instance);
		}
		win_cfg.Init_by_app(app);
		nightmode_mgr.Init_by_app(app);
	}
	public void Kit_(Gfui_kit kit) {
		this.kit = kit;
		kit.Kit_init(browser_win.Usr_dlg());
		kit.Kit_term_cbk_(Gfo_invk_cmd.New_by_key(app, Xoae_app.Invk_term_cbk));
		browser_win.Init_by_kit(kit);
		layout.Init(browser_win);
		cmd_mgr.Init_by_kit(app);
		app.Api_root().Init_by_kit(app);
		app.Addon_mgr().Init_by_kit(app, kit);
		menu_mgr.Menu_bldr().Init_by_kit(app, kit, app.Fsys_mgr().Bin_xowa_file_dir().GenSubDir_nest("app.menu"));
		menu_mgr.Init_by_kit();
		bnd_mgr.Init_by_kit(app);
		nightmode_mgr.Init_by_kit(app);
		Gfo_evt_mgr_.Sub_same_many(app.Usere(), this, Xoue_user.Evt_lang_changed);
		app.Sys_cfg().Lang_(app.Sys_cfg().Lang());	// NOTE: force refresh of lang. must occur after after gui_mgr init, else menu lbls will break
	}
	public void Lang_changed(Xol_lang_itm lang) {
		menu_mgr.Lang_changed(lang);
		browser_win.Lang_changed(lang);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_kit))							return kit;
		else if	(ctx.Match(k, Invk_kit_))							this.kit = Gfui_kit_.Get_by_key(m.ReadStrOr("v", Gfui_kit_.Swt().Key()));
		else if	(ctx.Match(k, Invk_run))							Run(Rls_able_.Null);
		else if	(ctx.Match(k, Invk_browser_type))					kit.Cfg_set("HtmlBox", "BrowserType", Swt_kit.Cfg_Html_BrowserType_parse(m.ReadStr("v")));
		else if	(ctx.Match(k, Invk_xul_runner_path_))				kit.Cfg_set("HtmlBox", "XulRunnerPath", Bry_fmtr_eval_mgr_.Eval_url(app.Url_cmd_eval(), m.ReadBry("v")).Xto_api());
		else if	(ctx.Match(k, Invk_bnds))							return bnd_mgr;
		else if	(ctx.Match(k, Invk_bindings))						return ipt_cfgs;
		else if	(ctx.MatchIn(k, Invk_main_win, Invk_browser_win))	return browser_win;
		else if	(ctx.Match(k, Invk_win_opts))						return win_cfg; // used by xowa.gfs and Special:Search; DATE:2017-03-29
		else if	(ctx.Match(k, Invk_layout))							return layout;
		else if	(ctx.Match(k, Invk_html))							return html_mgr;
		else if	(ctx.Match(k, Invk_menus))							return menu_mgr;
		else if	(ctx.Match(k, Invk_cmds))							return cmd_mgr;
		else if	(ctx.Match(k, Invk_url_macros))						return url_macro_mgr;
		else if	(ctx.Match(k, Xoue_user.Evt_lang_changed))			Lang_changed((Xol_lang_itm)m.ReadObj("v", null));
		else throw Err_.new_unhandled(k);
		return this;
	}
	private static final String 
	  Invk_kit = "kit", Invk_kit_ = "kit_", Invk_browser_type = "browser_type_", Invk_xul_runner_path_ = "xul_runner_path_", Invk_run = "run"
	, Invk_main_win = "main_win", Invk_browser_win = "browser_win", Invk_bnds = "bnds"
	, Invk_bindings = "bindings", Invk_win_opts = "win_opts", Invk_layout = "layout", Invk_html = "html"
	, Invk_menus = "menus", Invk_cmds = "cmds", Invk_url_macros = "url_macros";
	public void Run(Rls_able splash_win) {
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
