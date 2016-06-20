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
package gplx.xowa.guis.bnds; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.gfui.*; import gplx.gfui.ipts.*; import gplx.gfui.controls.elems.*;
import gplx.xowa.guis.views.*; import gplx.xowa.guis.cmds.*; import gplx.xowa.guis.menus.dom.*;
public class Xog_bnd_mgr {
	private Xog_win_itm win; private Xog_cmd_mgr_invk invk_mgr;
	private Xog_bnd_box[] boxs = Xog_bnd_box_.Ary();
	private List_adp startup_itms = List_adp_.New();
	private Ordered_hash regy = Ordered_hash_.New();
	public Xog_bnd_mgr(Xog_win_itm win) {this.win = win; invk_mgr = win.Gui_mgr().Cmd_mgr().Invk_mgr();}
	public Gfui_bnd_parser Bnd_parser() {if (bnd_parser == null) bnd_parser = Gfui_bnd_parser.new_en_(); return bnd_parser;} private Gfui_bnd_parser bnd_parser;
	public int Len() {return regy.Count();}
	public Xog_bnd_itm Get_at(int i)			{return (Xog_bnd_itm)regy.Get_at(i);}
	public Xog_bnd_itm Get_or_null(String v)	{return (Xog_bnd_itm)regy.Get_by(v);}
	public void Init_by_kit(Xoae_app app) {
		Add_system_bnds();
		Add_custom_bnds();	// NOTE: should go after Add_system_bnds in case user overrides any;
		Bind_all();
		app.Cfg_regy().App().Gui_mgr().Bnd_mgr().Init();
	}
	public Xog_bnd_itm Set(Xog_bnd_itm itm, int new_box, IptArg new_ipt) {
		if (win.Win_box() == null) {	// kit not built yet; occurs when restoring bindings through cfg file; DATE:2014-05-16
			Xog_bnd_itm new_itm = new Xog_bnd_itm(itm.Key(), false, "", new_box, new_ipt);
			startup_itms.Add(new_itm);
			return new_itm;
		}
		Del(itm, new_ipt);
		itm.Init_by_set(new_box, new_ipt);
		Add(itm);
		return itm;
	}
	public void Del(Xog_bnd_itm itm, IptArg new_ipt) {
		boolean itm_has_ipt = !IptArg_.Is_null_or_none(new_ipt);
		List_adp deleted = List_adp_.New();
		for (int i = 0; i < Xog_bnd_box_.Ary_len; i++) {
			Xog_bnd_box old_box = boxs[i];
			int old_itms_len = old_box.Len();
			for (int j = 0; j < old_itms_len; j++) {
				Xog_bnd_itm old_itm = old_box.Get_at(j);
				if		(	String_.Eq(old_itm.Key(), itm.Key())) {
					Xog_bnd_box_.Set_bnd_for_grp(Xog_bnd_box_.Set_del_key, win, invk_mgr, old_box, old_itm, itm.Ipt());
					deleted.Add(itm.Key());
				}
				else if (	itm_has_ipt
						&&	String_.Eq(old_itm.Ipt().Key(), new_ipt.Key())) {
					Xog_bnd_box_.Set_bnd_for_grp(Xog_bnd_box_.Set_del_ipt, win, invk_mgr, old_box, old_itm, old_itm.Ipt());
					Xog_bnd_mgr_srl.Update_cfg(win.App(), old_itm, i, IptKey_.None);
					old_itm.Ipt_to_none();
				}
			}
			int deleted_len = deleted.Count();
			for (int j = 0; j < deleted_len; j++) {
				String deleted_key = (String)deleted.Get_at(j);
				old_box.Del(deleted_key);
			}
			deleted.Clear();
		}
	}
	private void Add(Xog_bnd_itm itm) {
		Xog_bnd_box box = boxs[itm.Box()];
		Xog_bnd_box_.Set_bnd_for_grp(Xog_bnd_box_.Set_add, win, invk_mgr, box, itm, itm.Ipt());
		box.Add(itm);
	}
	public void Bind(int tid, GfuiElem box_elem) {
		Xog_bnd_box box = boxs[tid];
		int len = box.Len();
		for (int i = 0; i < len; i++) {
			Xog_bnd_itm itm = box.Get_at(i);
			Xog_bnd_box_.Set_bnd_for_elem(Xog_bnd_box_.Set_add, box, box_elem, invk_mgr, itm, itm.Ipt());
		}
	}
	public void Init() {
		regy.Clear();	// clear regy, else 1 test will fail
		Init_itm(Xog_cmd_itm_.Key_app_exit										, Xog_bnd_box_.Tid_browser				, "mod.c+key.q");
		Init_itm(Xog_cmd_itm_.Key_nav_go_bwd									, Xog_bnd_box_.Tid_browser				, "mod.a+key.left");
		Init_itm(Xog_cmd_itm_.Key_nav_go_fwd									, Xog_bnd_box_.Tid_browser				, "mod.a+key.right");
		Init_itm(Xog_cmd_itm_.Key_nav_cfg_main									, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_cfg_menu									, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_wiki_main_page							, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_wiki_random								, Xog_bnd_box_.Tid_browser				, "mod.cs+key.r");
		Init_itm(Xog_cmd_itm_.Key_nav_wiki_sandbox								, Xog_bnd_box_.Tid_browser				, "mod.cs+key.g,mod.cs+key.s", "mod.c+key.f1");
		Init_itm(Xog_cmd_itm_.Key_nav_help_help									, Xog_bnd_box_.Tid_browser				, "key.f1");
		Init_itm(Xog_cmd_itm_.Key_nav_help_change_log							, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_help_diagnostics							, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_help_about								, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_help_xowa_main							, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_help_xowa_blog							, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_setup_download_central					, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_setup_import_from_list					, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_setup_import_from_script					, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_setup_maintenance							, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_setup_download							, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_system_data_log_session					, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_system_data_cfg_app						, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_system_data_cfg_lang						, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_system_data_cfg_user						, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_system_data_cfg_custom					, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_system_data_usr_history					, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_gui_font_increase								, Xog_bnd_box_.Tid_browser				, "mod.c+key.equal");
		Init_itm(Xog_cmd_itm_.Key_gui_font_decrease								, Xog_bnd_box_.Tid_browser				, "mod.c+key.minus");
		Init_itm(Xog_cmd_itm_.Key_gui_font_reset								, Xog_bnd_box_.Tid_browser				, "mod.c+key.d0");
		Init_itm(Xog_cmd_itm_.Key_gui_page_view_mode_read						, Xog_bnd_box_.Tid_browser				, "mod.c+key.m,mod.c+key.r");
		Init_itm(Xog_cmd_itm_.Key_gui_page_view_mode_edit						, Xog_bnd_box_.Tid_browser				, "mod.c+key.m,mod.c+key.e");
		Init_itm(Xog_cmd_itm_.Key_gui_page_view_mode_html						, Xog_bnd_box_.Tid_browser				, "mod.c+key.m,mod.c+key.h", "mod.c+key.u");
		Init_itm(Xog_cmd_itm_.Key_gui_page_view_reload							, Xog_bnd_box_.Tid_browser				, "key.f5");
		Init_itm(Xog_cmd_itm_.Key_gui_page_view_refresh							, Xog_bnd_box_.Tid_browser				, "mod.s+key.f5");
		Init_itm(Xog_cmd_itm_.Key_gui_page_view_save_as							, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_gui_page_view_print							, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_gui_page_selection_select_all					, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_gui_page_selection_copy						, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_gui_page_selection_save_file_as				, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_gui_edit_save									, Xog_bnd_box_.Tid_browser				, "mod.as+key.s");
		Init_itm(Xog_cmd_itm_.Key_gui_edit_save_draft							, Xog_bnd_box_.Tid_browser				, "mod.c+key.s");
		Init_itm(Xog_cmd_itm_.Key_gui_edit_focus_edit_box						, Xog_bnd_box_.Tid_browser				, "mod.as+key.comma");
		Init_itm(Xog_cmd_itm_.Key_gui_edit_preview								, Xog_bnd_box_.Tid_browser				, "mod.as+key.p");
		Init_itm(Xog_cmd_itm_.Key_gui_edit_dbg_tmpl								, Xog_bnd_box_.Tid_browser				, "mod.c+key.e,mod.c+key.e", "mod.as+key.d,mod.as+key.d");
		Init_itm(Xog_cmd_itm_.Key_gui_edit_dbg_html								, Xog_bnd_box_.Tid_browser				, "mod.c+key.e,mod.c+key.h");
		Init_itm(Xog_cmd_itm_.Key_gui_edit_exec									, Xog_bnd_box_.Tid_browser				, "mod.c+key.e,mod.c+key.g");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_url_focus							, Xog_bnd_box_.Tid_browser				, "mod.a+key.d", "mod.c+key.l");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_url_exec							, Xog_bnd_box_.Tid_browser_url			, "key.enter", "key.keypad_enter");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_url_exec_new_tab_by_paste			, Xog_bnd_box_.Tid_browser_url			, "mod.c+key.enter", "mod.c+key.keypad_enter");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_url_exec_by_paste					, Xog_bnd_box_.Tid_browser_url			, "mouse.middle", "mod.a+key.enter", "mod.a+key.keypad_enter");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_url_restore						, Xog_bnd_box_.Tid_browser_url			, "mod.c+key.u");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_search_focus						, Xog_bnd_box_.Tid_browser				, "mod.ca+key.s");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_search_exec						, Xog_bnd_box_.Tid_browser_search		, "key.enter", "key.keypad_enter");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_new_dflt__at_dflt__focus_y	, Xog_bnd_box_.Tid_browser				, "mod.c+key.t");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_new_link__at_dflt__focus_n	, Xog_bnd_box_.Tid_browser_html			, "mouse.middle");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_new_href__at_dflt__focus_y	, Xog_bnd_box_.Tid_browser				, "mod.c+key.g,mod.c+key.f");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_close_cur					, Xog_bnd_box_.Tid_browser				, "mod.c+key.w");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_close_others					, Xog_bnd_box_.Tid_browser				, "mod.cs+key.w,mod.cs+key.w");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_close_to_bgn					, Xog_bnd_box_.Tid_browser				, "mod.cs+key.w,mod.cs+key.left");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_close_to_end					, Xog_bnd_box_.Tid_browser				, "mod.cs+key.w,mod.cs+key.right");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_close_undo					, Xog_bnd_box_.Tid_browser				, "mod.cs+key.t");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_select_fwd					, Xog_bnd_box_.Tid_browser				, "mod.c+key.tab", "mod.c+key.pageDown");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_select_bwd					, Xog_bnd_box_.Tid_browser				, "mod.cs+key.tab", "mod.c+key.pageUp");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_select_by_idx_1				, Xog_bnd_box_.Tid_browser				, "mod.c+key.d1");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_select_by_idx_2				, Xog_bnd_box_.Tid_browser				, "mod.c+key.d2");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_select_by_idx_3				, Xog_bnd_box_.Tid_browser				, "mod.c+key.d3");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_select_by_idx_4				, Xog_bnd_box_.Tid_browser				, "mod.c+key.d4");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_select_by_idx_5				, Xog_bnd_box_.Tid_browser				, "mod.c+key.d5");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_select_by_idx_6				, Xog_bnd_box_.Tid_browser				, "mod.c+key.d6");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_select_by_idx_7				, Xog_bnd_box_.Tid_browser				, "mod.c+key.d7");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_select_by_idx_8				, Xog_bnd_box_.Tid_browser				, "mod.c+key.d8");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_select_by_idx_9				, Xog_bnd_box_.Tid_browser				, "mod.c+key.d9");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_move_bwd						, Xog_bnd_box_.Tid_browser				, "mod.a+key.pageUp");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_move_fwd						, Xog_bnd_box_.Tid_browser				, "mod.a+key.pageDown");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_pin_toggle					, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_html_focus					,  1, Xog_bnd_box_.Tid_browser_url			, "key.escape");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_html_focus					,  2, Xog_bnd_box_.Tid_browser_search		, "key.escape");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_html_focus					,  3, Xog_bnd_box_.Tid_browser_prog			, "key.escape");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_html_focus					,  4, Xog_bnd_box_.Tid_browser_info			, "key.escape");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_html_selection_focus_toggle		, Xog_bnd_box_.Tid_browser				, "mod.c+key.g,mod.c+key.g");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_find_show							, Xog_bnd_box_.Tid_browser				, "mod.c+key.f");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_find_show_by_paste				, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_find_hide							, Xog_bnd_box_.Tid_browser_find			, "key.escape");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_find_exec							, Xog_bnd_box_.Tid_browser_find			, "key.enter", "key.keypad_enter");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_find_find_fwd						, Xog_bnd_box_.Tid_browser_find			, "mod.a+key.n");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_find_find_bwd						, Xog_bnd_box_.Tid_browser_find			, "mod.a+key.p");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_find_case_toggle					, Xog_bnd_box_.Tid_browser_find			, "mod.a+key.c");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_find_wrap_toggle					, Xog_bnd_box_.Tid_browser_find			, "mod.a+key.w");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_prog_focus						, Xog_bnd_box_.Tid_browser				, "mod.ca+key.p");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_info_focus						, Xog_bnd_box_.Tid_browser				, "mod.ca+key.i");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_info_clear						, Xog_bnd_box_.Tid_browser				, "mod.ca+key.c");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_prog_log_show						, Xog_bnd_box_.Tid_browser_prog			, "mouse.middle", "mod.cs+key.p");
		Init_itm(Xog_cmd_itm_.Key_html_tidy_toggle								, Xog_bnd_box_.Tid_browser				, "key.f7");
		Init_itm(Xog_cmd_itm_.Key_usr_bookmarks_add								, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_usr_bookmarks_show							, Xog_bnd_box_.Tid_browser				, "key.f3");
		Init_itm(Xog_cmd_itm_.Key_usr_history_goto_recent						, Xog_bnd_box_.Tid_browser				, "mod.cs+key.l");
		Init_itm(Xog_cmd_itm_.Key_usr_history_show								, Xog_bnd_box_.Tid_browser				, "mod.cs+key.h");
		Init_itm(Xog_cmd_itm_.Key_net_enabled_n_								, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_net_enabled_y_								, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_net_enabled_x_								, Xog_bnd_box_.Tid_browser				, "");
	}
	private void Init_itm(String cmd, int box, String... ipts) {
		int ipts_len = ipts.length;
		for (int i = 0; i < ipts_len; i++) {
			String ipt_str = ipts[i];
			Init_itm(cmd, i, box, IptArg_.parse_or_none_(ipt_str));
		}
	}
	private void Init_itm(String cmd, int idx, int box, String ipt) {Init_itm(cmd, idx, box, IptArg_.parse_or_none_(ipt));}
	private void Init_itm(String cmd, int idx, int box, IptArg ipt) {
		String key = cmd + "-" + Int_.To_str(idx + List_adp_.Base1);		// EX: xowa.widgets.url.focus-1 xowa.widgets.url.focus-2
		Xog_bnd_itm itm = new Xog_bnd_itm(key, Bool_.Y, cmd, box, ipt);
		boxs[box].Add(itm);
		regy.Add(itm.Key(), itm);
	}
	private void Add_system_bnds() {
		IptCfg null_cfg = IptCfg_.Null; IptEventType btn_event_type = IptEventType_.add_(IptEventType_.MouseUp, IptEventType_.KeyDown); IptArg[] btn_args = IptArg_.Ary(IptMouseBtn_.Left, IptKey_.Enter, IptKey_.Space);
		IptBnd_.ipt_to_(null_cfg		, win.Go_bwd_btn()		, invk_mgr, Xog_cmd_itm_.Key_nav_go_bwd						, btn_event_type, btn_args);
		IptBnd_.ipt_to_(null_cfg		, win.Go_fwd_btn()		, invk_mgr, Xog_cmd_itm_.Key_nav_go_fwd						, btn_event_type, btn_args);
		IptBnd_.ipt_to_(null_cfg		, win.Url_exec_btn()	, invk_mgr, Xog_cmd_itm_.Key_gui_browser_url_exec			, btn_event_type, btn_args);
		IptBnd_.ipt_to_(null_cfg		, win.Search_exec_btn()	, invk_mgr, Xog_cmd_itm_.Key_gui_browser_search_exec		, btn_event_type, btn_args);
		IptBnd_.ipt_to_(null_cfg		, win.Find_close_btn()	, invk_mgr, Xog_cmd_itm_.Key_gui_browser_find_hide			, btn_event_type, btn_args);
		IptBnd_.ipt_to_(null_cfg		, win.Find_fwd_btn()	, invk_mgr, Xog_cmd_itm_.Key_gui_browser_find_find_fwd		, btn_event_type, btn_args);
		IptBnd_.ipt_to_(null_cfg		, win.Find_bwd_btn()	, invk_mgr, Xog_cmd_itm_.Key_gui_browser_find_find_bwd		, btn_event_type, btn_args);
		IptBnd_.ipt_to_(null_cfg		, win.Find_box()		, invk_mgr, Xog_cmd_itm_.Key_gui_browser_find_type			, IptEventType_.KeyUp, IptKey_.printableKeys_(IptKey_.Ary(IptKey_.Back, IptKey_.Escape, IptKey_.Ctrl.Add(IptKey_.V)), IptKey_.Ary()));
		IptBnd_.ipt_to_(null_cfg		, win.Url_box()			, invk_mgr, Xog_cmd_itm_.Key_gui_browser_url_type			, IptEventType_.KeyUp, IptKey_.printableKeys_(IptKey_.Ary(IptKey_.Back, IptKey_.Escape, IptKey_.Ctrl.Add(IptKey_.X), IptKey_.Ctrl.Add(IptKey_.V)), IptKey_.Ary()));
	}
	private void Add_custom_bnds() {	// NOTE: custom bnds are stored in cfg; cfg executes before Init_by_kit when all windows elements are null; run cfg now, while Init_by_kit is called and elems are now created
		int len = startup_itms.Count();
		for (int i = 0; i < len; i++) {
			Xog_bnd_itm new_itm = (Xog_bnd_itm)startup_itms.Get_at(i);
			try {
				Xog_bnd_itm cur_itm = (Xog_bnd_itm)regy.Get_by(new_itm.Key());
				if (cur_itm == null) {win.Usr_dlg().Warn_many("", "", "binding no longer exists; key=~{0}", new_itm.Key());}	// could happen when breaking backward compatibility
				cur_itm.Init_by_set(new_itm.Box(), new_itm.Ipt());
			}	catch (Exception e) {win.Usr_dlg().Warn_many("", "", "failed to set custom binding; key=~{0} err=~{1}", new_itm.Key(), Err_.Message_gplx_full(e));}
		}
		startup_itms.Clear();
	}
	private void Bind_all() {
		this.Bind(Xog_bnd_box_.Tid_browser				, win.Win_box());
		this.Bind(Xog_bnd_box_.Tid_browser_url			, win.Url_box());
		this.Bind(Xog_bnd_box_.Tid_browser_find			, win.Find_box());
		this.Bind(Xog_bnd_box_.Tid_browser_search		, win.Search_box());
		this.Bind(Xog_bnd_box_.Tid_browser_prog			, win.Prog_box());
		this.Bind(Xog_bnd_box_.Tid_browser_info			, win.Info_box());
	}
}
