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
package gplx.xowa.guis.views.nightmodes; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*; import gplx.xowa.guis.views.*;
import gplx.gfui.controls.elems.*; import gplx.gfui.draws.*;
import gplx.xowa.specials.xowa.default_tab.*;
public class Xog_nightmode_mgr implements Gfo_invk {
	private Xoae_app app;
	private boolean enabled;
	private Xog_win_itm win;
	private GfuiElemBase[] backcolor_elems, forecolor_elems;
	public void Init_by_app(Xoae_app app) {
		this.app = app;
		this.win = app.Gui_mgr().Browser_win();
		app.Cfg().Sub_many_app(this
			, Cfg__enabled
			, Cfg__night_back, Cfg__night_fore, Cfg__night_edge
			, Cfg__day_back, Cfg__day_fore, Cfg__day_edge
			);
	}
	public void Init_by_kit(Xoae_app app) {
		// set back / fore for other elems
		this.backcolor_elems = this.forecolor_elems = new GfuiElemBase[] 
		{ win.Toolbar_grp()
		, win.Go_bwd_btn()
		, win.Go_fwd_btn()
		, win.Url_box()
		, win.Url_exec_btn()
		, win.Search_box()
		, win.Search_exec_btn()
		, win.Allpages_box()
		, win.Allpages_exec_btn()
		, win.Statusbar_grp()
		, win.Find_close_btn()
		, win.Find_bwd_btn()
		, win.Find_fwd_btn()
		, win.Find_box()
		, win.Prog_box()
		};
	}
	private void Set_color(int color_group_type, ColorAdp color) {
		if (color == null) return; // null passed by invk
		switch (color_group_type) {
			case COLOR_GROUP_BACK:
				win.Win_box().BackColor_(color);
				win.Tab_mgr().Tab_mgr().BackColor_(color);
				win.Tab_mgr().Tab_mgr().Btns_selected_background_(color);
				win.Tab_mgr().Tab_mgr().Btns_unselected_background_(color);
				win.Url_box().Items__backcolor_(color);
				for (GfuiElemBase elem : backcolor_elems)
					elem.BackColor_(color);
				win.Prog_box().Border_color_(color);
				break;
			case COLOR_GROUP_FORE:
				win.Tab_mgr().Tab_mgr().Btns_selected_foreground_(color);
				win.Tab_mgr().Tab_mgr().Btns_unselected_foreground_(color);
				win.Url_box().Items__forecolor_(color);
				for (GfuiElemBase elem : forecolor_elems)
					elem.ForeColor_(color);
				break;
			case COLOR_GROUP_EDGE:
				win.Url_box().Border_color_(color);
				win.Search_box().Border_color_(color);
				win.Find_box().Border_color_(color);
				break;
		}

	}
	public boolean Enabled() {return enabled;}
	public void Enabled_by_cfg() {
		Enabled_(app.Cfg().Get_bool_app_or(Cfg__enabled, false));
	}
	public void Enabled_(boolean v) {
		// set enabled
		this.enabled = v;

		// set colors
		ColorAdp backcolor, forecolor, edgecolor;
		if (enabled) {
			backcolor = Parse_from_cfg(app, Cfg__night_back, ColorAdp_.White);
			forecolor = Parse_from_cfg(app, Cfg__night_fore, ColorAdp_.Black);
			edgecolor = Parse_from_cfg(app, Cfg__night_edge, ColorAdp_.LightGray);
		}
		else {
			backcolor = Parse_from_cfg(app, Cfg__day_back, ColorAdp_.Black);
			forecolor = Parse_from_cfg(app, Cfg__day_fore, ColorAdp_.White);
			edgecolor = Parse_from_cfg(app, Cfg__day_edge, ColorAdp_.Black);
		}
		Set_color(COLOR_GROUP_BACK, backcolor);
		Set_color(COLOR_GROUP_FORE, forecolor);
		Set_color(COLOR_GROUP_EDGE, edgecolor);

		// set button icons
		// note that nightmode needs 16px and unresized b/c swt interpolates white pixels when resizing images (even when downsizing?)
		// note that daymode needs 32px and resized b/c resizing "blurs" image which looks better
		Io_url img_dir = app.Fsys_mgr().Bin_xowa_file_dir().GenSubDir_nest("app.window", enabled ? "16px" : "32px");
		win.Go_bwd_btn().Btn_img_(app.Gui_mgr().Kit().New_img_load(img_dir.GenSubFil("go_bwd.png")));
		win.Go_fwd_btn().Btn_img_(app.Gui_mgr().Kit().New_img_load(img_dir.GenSubFil("go_fwd.png")));
		win.Url_exec_btn().Btn_img_(app.Gui_mgr().Kit().New_img_load(img_dir.GenSubFil("url_exec.png")));
		win.Search_exec_btn().Btn_img_(app.Gui_mgr().Kit().New_img_load(img_dir.GenSubFil("search_exec.png")));
		win.Allpages_exec_btn().Btn_img_(app.Gui_mgr().Kit().New_img_load(img_dir.GenSubFil("allpages_exec.png")));
		win.Find_close_btn().Btn_img_(app.Gui_mgr().Kit().New_img_load(img_dir.GenSubFil("find_close.png")));
		win.Find_bwd_btn().Btn_img_(app.Gui_mgr().Kit().New_img_load(img_dir.GenSubFil("find_bwd.png")));
		win.Find_fwd_btn().Btn_img_(app.Gui_mgr().Kit().New_img_load(img_dir.GenSubFil("find_fwd.png")));
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__enabled))	  {this.Enabled_(m.ReadBool("v")); app.Api_root().Gui().Browser().Nightmode_reload();}
		else if	(ctx.MatchIn(k, Cfg__night_back)) {if (enabled) this.Set_color(COLOR_GROUP_BACK, Parse(k, m.ReadStr("v"), null));}
		else if	(ctx.MatchIn(k, Cfg__night_fore)) {if (enabled) this.Set_color(COLOR_GROUP_FORE, Parse(k, m.ReadStr("v"), null));}
		else if	(ctx.MatchIn(k, Cfg__night_edge)) {if (enabled) this.Set_color(COLOR_GROUP_EDGE, Parse(k, m.ReadStr("v"), null));}
		else if	(ctx.MatchIn(k, Cfg__day_back))   {if (!enabled) this.Set_color(COLOR_GROUP_BACK, Parse(k, m.ReadStr("v"), null));}
		else if	(ctx.MatchIn(k, Cfg__day_fore))   {if (!enabled) this.Set_color(COLOR_GROUP_FORE, Parse(k, m.ReadStr("v"), null));}
		else if	(ctx.MatchIn(k, Cfg__day_edge))   {if (!enabled) this.Set_color(COLOR_GROUP_EDGE, Parse(k, m.ReadStr("v"), null));}
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final    String
	  Cfg__enabled     = "xowa.gui.nightmode.enabled"
	, Cfg__night_back  = "xowa.gui.nightmode.nightcolors.backcolor"
	, Cfg__night_fore  = "xowa.gui.nightmode.nightcolors.forecolor"
	, Cfg__night_edge  = "xowa.gui.nightmode.nightcolors.edgecolor"
	, Cfg__day_back    = "xowa.gui.nightmode.daycolors.backcolor"
	, Cfg__day_fore    = "xowa.gui.nightmode.daycolors.forecolor"
	, Cfg__day_edge    = "xowa.gui.nightmode.daycolors.edgecolor"
	;

	private static final int COLOR_GROUP_BACK = 0, COLOR_GROUP_FORE = 1, COLOR_GROUP_EDGE = 2;
	private static ColorAdp Parse_from_cfg(Xoa_app app, String key, ColorAdp or) {
		return Parse(key, app.Cfg().Get_str_app_or(key, null), or);
	}
	private static ColorAdp Parse(String key, String val, ColorAdp or) {
		try {
			return val == null ? or : ColorAdp_.parse_hex_("#00" + val);	// parse_hex requires leading "#00"
		} catch (Exception e) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to parse color; key=~{0} val=~{1} err=~{2}", key, val, Err_.Message_gplx_log(e));
			return or;
		}
	}
}
