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
public class Xog_nightmode_mgr {
	private boolean enabled;
	private Xog_win_itm win;
	public void Init_by_app(Xoae_app app) {
		this.win = app.Gui_mgr().Browser_win();
	}
	public boolean Enabled() {return enabled;}
	public void Enabled_dflt_() {
		Enabled_(false);
	}
	public void Enabled_toggle_() {
		Enabled_(!enabled);
	}
	private void Enabled_(boolean v) {
		this.enabled = v;

		// get colors
		ColorAdp backcolor, forecolor, edgecolor;
		if (enabled) {
			backcolor = ColorAdp_.Black;
			forecolor = ColorAdp_.White;
			edgecolor = ColorAdp_.Black;
		}
		else {
			backcolor = ColorAdp_.White;
			forecolor = ColorAdp_.Black;
			edgecolor = ColorAdp_.LightGray;
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
	}
}
