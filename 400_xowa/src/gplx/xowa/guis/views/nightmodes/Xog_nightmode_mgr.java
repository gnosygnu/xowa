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
public class Xog_nightmode_mgr {
	private Xoae_app app;
	private boolean enabled;
	private Xog_win_itm win;
	public void Init_by_app(Xoae_app app) {
		this.app = app;
		this.win = app.Gui_mgr().Browser_win();
	}
	public boolean Enabled() {return enabled;}
	public void Enabled_by_cfg() {
		Enabled_(app.Cfg().Get_bool_app_or(Cfg__nightmode_enabled, false));
	}
	public void Enabled_toggle() {
		boolean val = !enabled;
		Enabled_(val);
		app.Cfg().Set_bool_app(Cfg__nightmode_enabled, val);
	}
	private void Enabled_(boolean v) {
		this.enabled = v;

		// get colors
		ColorAdp backcolor, forecolor, edgecolor;
		if (enabled) {
			backcolor = Parse_from_cfg(app, "xowa.gui.nightmode.nightcolors.backcolor", ColorAdp_.White);
			forecolor = Parse_from_cfg(app, "xowa.gui.nightmode.nightcolors.forecolor", ColorAdp_.Black);
			edgecolor = Parse_from_cfg(app, "xowa.gui.nightmode.nightcolors.edgecolor", ColorAdp_.LightGray);
		}
		else {
			backcolor = Parse_from_cfg(app, "xowa.gui.nightmode.daycolors.backcolor", ColorAdp_.Black);
			forecolor = Parse_from_cfg(app, "xowa.gui.nightmode.daycolors.forecolor", ColorAdp_.White);
			edgecolor = Parse_from_cfg(app, "xowa.gui.nightmode.daycolors.edgecolor", ColorAdp_.Black);
		}

		// set back / fore for window and tab 
		win.Win_box().BackColor_(backcolor);
		win.Tab_mgr().Tab_mgr().BackColor_(backcolor);
		win.Tab_mgr().Tab_mgr().Btns_selected_background_(backcolor);
		win.Tab_mgr().Tab_mgr().Btns_selected_foreground_(forecolor);
		win.Tab_mgr().Tab_mgr().Btns_unselected_background_(backcolor);
		win.Tab_mgr().Tab_mgr().Btns_unselected_foreground_(forecolor);

		// set back / fore for other elems
		GfuiElemBase[] elems = new GfuiElemBase[] 
		{ win.Toolbar_grp()
		, win.Go_bwd_btn()
		, win.Go_fwd_btn()
		, win.Url_box()
		, win.Url_exec_btn()
		, win.Search_box()
		, win.Search_exec_btn()
		, win.Statusbar_grp()
		, win.Find_close_btn()
		, win.Find_bwd_btn()
		, win.Find_fwd_btn()
		, win.Find_box()
		, win.Prog_box()
		};
		for (GfuiElemBase elem : elems)
			elem.BackColor_(backcolor).ForeColor_(forecolor);
		
		// set edge colors
		win.Url_box().Border_color_(edgecolor);
		win.Search_box().Border_color_(edgecolor);
		win.Find_box().Border_color_(edgecolor);
		win.Prog_box().Border_color_(backcolor);

		// change button icons
		// note that nightmode needs 16px and unresized b/c swt interpolates white pixels when resizing images (even when downsizing?)
		// note that daymode needs 32px and resized b/c resizing "blurs" image which looks better
		Io_url img_dir = app.Fsys_mgr().Bin_xowa_file_dir().GenSubDir_nest("app.window", enabled ? "16px" : "32px");
		win.Go_bwd_btn().Btn_img_(app.Gui_mgr().Kit().New_img_load(img_dir.GenSubFil("go_bwd.png")));
		win.Go_fwd_btn().Btn_img_(app.Gui_mgr().Kit().New_img_load(img_dir.GenSubFil("go_fwd.png")));
		win.Url_exec_btn().Btn_img_(app.Gui_mgr().Kit().New_img_load(img_dir.GenSubFil("url_exec.png")));
		win.Search_exec_btn().Btn_img_(app.Gui_mgr().Kit().New_img_load(img_dir.GenSubFil("search_exec.png")));
		win.Find_close_btn().Btn_img_(app.Gui_mgr().Kit().New_img_load(img_dir.GenSubFil("find_close.png")));
		win.Find_bwd_btn().Btn_img_(app.Gui_mgr().Kit().New_img_load(img_dir.GenSubFil("find_bwd.png")));
		win.Find_fwd_btn().Btn_img_(app.Gui_mgr().Kit().New_img_load(img_dir.GenSubFil("find_fwd.png")));
	}
	private static ColorAdp Parse_from_cfg(Xoa_app app, String key, ColorAdp or) {
		String val = app.Cfg().Get_str_app_or(key, null);
		try {
			return val == null ? or : ColorAdp_.parse_hex_("#00" + val);	// parse_hex requires leading "#00"
		} catch (Exception e) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to parse color; key=~{0} val=~{1} err=~{2}", key, val, Err_.Message_gplx_log(e));
			return or;
		}
	}
	private static final    String Cfg__nightmode_enabled = "xowa.gui.nightmode.enabled";
}
