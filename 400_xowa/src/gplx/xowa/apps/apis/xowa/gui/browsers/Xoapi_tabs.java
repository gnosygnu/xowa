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
package gplx.xowa.apps.apis.xowa.gui.browsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*; import gplx.xowa.apps.apis.xowa.gui.*;
import gplx.xowa.guis.views.*;
public class Xoapi_tabs implements Gfo_invk {
	private Xog_tab_mgr tab_mgr;
	public void Init_by_kit(Xoae_app app) {
		this.tab_mgr = app.Gui_mgr().Browser_win().Tab_mgr();
	}
	private boolean Active_tab_is_null() {return tab_mgr.Active_tab_is_null();}
	public void New_dupe(boolean focus)				{tab_mgr.Tabs_new_dupe(focus);}
	public void New_dflt(boolean focus)				{tab_mgr.Tabs_new_dflt(focus);}
	public void New_link(boolean focus, String link)	{tab_mgr.Tabs_new_link(focus, link);}
	public void New_href(boolean focus) {
		Xog_tab_itm tab = tab_mgr.Active_tab(); if (tab == Xog_tab_itm_.Null) return;
		String link = tab.Html_itm().Html_selected_get_href_or_text();
		if (String_.Len_eq_0(link)) {tab_mgr.Win().Usr_dlg().Prog_many("", "", "no link or text selected"); return;}
		tab_mgr.Tabs_new_dflt(true);
		tab_mgr.Win().Page__navigate_by_url_bar(link);
	}
	public void Close_cur()							{tab_mgr.Tabs_close_cur();}
	public void Select_bwd()						{tab_mgr.Tabs_select(Bool_.N);}
	public void Select_fwd()						{tab_mgr.Tabs_select(Bool_.Y);}
	public void Select_by_idx(int v)				{tab_mgr.Tabs_select_by_idx(v - List_adp_.Base1);}
	public void Move_bwd()							{tab_mgr.Tabs_move(Bool_.N);}
	public void Move_fwd()							{tab_mgr.Tabs_move(Bool_.Y);}
	public void Close_others()						{tab_mgr.Tabs_close_others();}
	public void Close_to_bgn()						{tab_mgr.Tabs_close_to_bgn();}
	public void Close_to_end()						{tab_mgr.Tabs_close_to_end();}
	public void Close_undo()						{tab_mgr.Tabs_close_undo();}
	public void Pin_toggle()						{if (this.Active_tab_is_null()) return; tab_mgr.Active_tab().Pin_toggle();}
	public void Tab_bookmark_all()					{}//app.Gui_mgr().Browser_win().Tab_mgr().Tabs_close_others();}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_new_dflt__at_dflt__focus_y))			this.New_dflt(Bool_.Y);
		else if	(ctx.Match(k, Invk_new_link__at_dflt__focus_n))			this.New_link(Bool_.N, m.ReadStrOr("v", null));
		else if	(ctx.Match(k, Invk_new_link__at_dflt__focus_y))			this.New_link(Bool_.Y, m.ReadStrOr("v", null));
		else if	(ctx.Match(k, Invk_new_href__at_dflt__focus_y))			this.New_href(Bool_.Y);
		else if	(ctx.Match(k, Invk_new_dupe__at_dflt__focus_y)) 		this.New_dupe(Bool_.Y);
		else if	(ctx.Match(k, Invk_close_cur)) 							this.Close_cur();
		else if	(ctx.Match(k, Invk_select_bwd)) 						this.Select_bwd();
		else if	(ctx.Match(k, Invk_select_fwd)) 						this.Select_fwd();
		else if	(ctx.Match(k, Invk_move_bwd)) 							this.Move_bwd();
		else if	(ctx.Match(k, Invk_move_fwd)) 							this.Move_fwd();
		else if	(ctx.Match(k, Invk_close_others)) 						this.Close_others();
		else if	(ctx.Match(k, Invk_close_to_bgn)) 						this.Close_to_bgn();
		else if	(ctx.Match(k, Invk_close_to_end)) 						this.Close_to_end();
		else if	(ctx.Match(k, Invk_close_undo)) 						this.Close_undo();
		else if	(ctx.Match(k, Invk_pin_toggle)) 						this.Pin_toggle();
		else if	(ctx.Match(k, Invk_select_by_idx_1)) 					this.Select_by_idx(1);
		else if	(ctx.Match(k, Invk_select_by_idx_2)) 					this.Select_by_idx(2);
		else if	(ctx.Match(k, Invk_select_by_idx_3)) 					this.Select_by_idx(3);
		else if	(ctx.Match(k, Invk_select_by_idx_4)) 					this.Select_by_idx(4);
		else if	(ctx.Match(k, Invk_select_by_idx_5)) 					this.Select_by_idx(5);
		else if	(ctx.Match(k, Invk_select_by_idx_6)) 					this.Select_by_idx(6);
		else if	(ctx.Match(k, Invk_select_by_idx_7)) 					this.Select_by_idx(7);
		else if	(ctx.Match(k, Invk_select_by_idx_8)) 					this.Select_by_idx(8);
		else if	(ctx.Match(k, Invk_select_by_idx_9)) 					this.Select_by_idx(9);
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String 
	  Invk_new_dflt__at_dflt__focus_y = "new_dflt__at_dflt__focus_y"
	, Invk_new_link__at_dflt__focus_n = "new_link__at_dflt__focus_n"
	, Invk_new_link__at_dflt__focus_y = "new_link__at_dflt__focus_y"
	, Invk_new_href__at_dflt__focus_y = "new_href__at_dflt__focus_y"
	, Invk_new_dupe__at_dflt__focus_y = "new_dupe__at_dflt__focus_y"
	, Invk_close_cur = "close_cur"
	, Invk_select_bwd = "select_bwd", Invk_select_fwd = "select_fwd"
	, Invk_move_bwd = "move_bwd", Invk_move_fwd = "move_fwd"
	, Invk_close_others = "close_others", Invk_close_to_bgn = "close_to_bgn", Invk_close_to_end = "close_to_end", Invk_close_undo = "close_undo"
	, Invk_pin_toggle = "pin_toggle"
	, Invk_select_by_idx_1 = "select_by_idx_1", Invk_select_by_idx_2 = "select_by_idx_2", Invk_select_by_idx_3 = "select_by_idx_3", Invk_select_by_idx_4 = "select_by_idx_4", Invk_select_by_idx_5 = "select_by_idx_5"
	, Invk_select_by_idx_6 = "select_by_idx_6", Invk_select_by_idx_7 = "select_by_idx_7", Invk_select_by_idx_8 = "select_by_idx_8", Invk_select_by_idx_9 = "select_by_idx_9"
	;
}
