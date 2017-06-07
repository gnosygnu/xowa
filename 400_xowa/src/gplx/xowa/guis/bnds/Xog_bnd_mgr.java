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
package gplx.xowa.guis.bnds; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.gfui.*; import gplx.gfui.ipts.*; import gplx.gfui.controls.elems.*;
import gplx.xowa.guis.views.*; import gplx.xowa.guis.cmds.*; import gplx.xowa.guis.menus.dom.*;
public class Xog_bnd_mgr implements Gfo_invk {
	private Xoae_app app;
	private Xog_win_itm win; private Xog_cmd_mgr_invk invk_mgr;
	private Xog_bnd_box[] boxs = Xog_bnd_box_.Ary();
	private List_adp startup_itms = List_adp_.New();
	private Ordered_hash regy = Ordered_hash_.New();
	public Xog_bnd_mgr(Xog_win_itm win) {this.win = win; invk_mgr = win.Gui_mgr().Cmd_mgr().Invk_mgr();}
	public int Len() {return regy.Count();}
	public Xog_bnd_itm Get_at(int i)			{return (Xog_bnd_itm)regy.Get_at(i);}
	public Xog_bnd_itm Get_or_null(String v)	{return (Xog_bnd_itm)regy.Get_by(v);}
	public void Init_by_kit(Xoae_app app) {
		this.app = app;
		Add_system_bnds();
		Add_custom_bnds();	// NOTE: should go after Add_system_bnds in case user overrides any;
		Bind_all();

		app.Cfg().Sub_many_app(this, Run__show_remap_win);
		app.Cfg().Bind_many_app(this
		, "xowa.gui.shortcuts.xowa.app.exit-1"
		, "xowa.gui.shortcuts.xowa.nav.go_bwd-1"
		, "xowa.gui.shortcuts.xowa.nav.go_fwd-1"
		, "xowa.gui.shortcuts.xowa.nav.cfg.main-1"
		, "xowa.gui.shortcuts.xowa.nav.cfg.menus-1"
		, "xowa.gui.shortcuts.xowa.nav.wiki.main_page-1"
		, "xowa.gui.shortcuts.xowa.nav.wiki.sandbox-1"
		, "xowa.gui.shortcuts.xowa.nav.wiki.sandbox-2"
		, "xowa.gui.shortcuts.xowa.nav.wiki.random-1"
		, "xowa.gui.shortcuts.xowa.nav.wiki.allpages-1"
		, "xowa.gui.shortcuts.xowa.nav.wiki.search_title-1"
		, "xowa.gui.shortcuts.xowa.nav.wiki.search_full-1"
		, "xowa.gui.shortcuts.xowa.nav.wiki.search_per_cfg-1"
		, "xowa.gui.shortcuts.xowa.nav.help.help-1"
		, "xowa.gui.shortcuts.xowa.nav.help.about-1"
		, "xowa.gui.shortcuts.xowa.nav.help.change_log-1"
		, "xowa.gui.shortcuts.xowa.nav.help.diagnostics-1"
		, "xowa.gui.shortcuts.xowa.nav.help.xowa_update-1"
		, "xowa.gui.shortcuts.xowa.nav.help.xowa_main-1"
		, "xowa.gui.shortcuts.xowa.nav.help.xowa_blog-1"
		, "xowa.gui.shortcuts.xowa.nav.setup.download_central-1"
		, "xowa.gui.shortcuts.xowa.nav.setup.import_from_list-1"
		, "xowa.gui.shortcuts.xowa.nav.setup.import_from_script-1"
		, "xowa.gui.shortcuts.xowa.nav.setup.maintenance-1"
		, "xowa.gui.shortcuts.xowa.nav.setup.download-1"
		, "xowa.gui.shortcuts.xowa.nav.system_data.log_session-1"
		, "xowa.gui.shortcuts.xowa.nav.system_data.cfg_app-1"
		, "xowa.gui.shortcuts.xowa.nav.system_data.cfg_lang-1"
		, "xowa.gui.shortcuts.xowa.nav.system_data.usr_history-1"
		, "xowa.gui.shortcuts.xowa.nav.personal.item-1"
		, "xowa.gui.shortcuts.xowa.nav.personal.list-1"
		, "xowa.gui.shortcuts.xowa.gui.font.increase-1"
		, "xowa.gui.shortcuts.xowa.gui.font.decrease-1"
		, "xowa.gui.shortcuts.xowa.gui.font.reset-1"
		, "xowa.gui.shortcuts.xowa.gui.page.view.mode_read-1"
		, "xowa.gui.shortcuts.xowa.gui.page.view.mode_edit-1"
		, "xowa.gui.shortcuts.xowa.gui.page.view.mode_html-1"
		, "xowa.gui.shortcuts.xowa.gui.page.view.mode_html-2"
		, "xowa.gui.shortcuts.xowa.gui.page.view.refresh-1"
		, "xowa.gui.shortcuts.xowa.gui.page.view.reload-1"
		, "xowa.gui.shortcuts.xowa.gui.page.view.save_as-1"
		, "xowa.gui.shortcuts.xowa.gui.page.view.print-1"
		, "xowa.gui.shortcuts.xowa.gui.page.edit.save-1"
		, "xowa.gui.shortcuts.xowa.gui.page.edit.save_draft-1"
		, "xowa.gui.shortcuts.xowa.gui.page.edit.focus_edit_box-1"
		, "xowa.gui.shortcuts.xowa.gui.page.edit.preview-1"
		, "xowa.gui.shortcuts.xowa.gui.page.edit.dbg_tmpl-1"
		, "xowa.gui.shortcuts.xowa.gui.page.edit.dbg_tmpl-2"
		, "xowa.gui.shortcuts.xowa.gui.page.edit.dbg_html-1"
		, "xowa.gui.shortcuts.xowa.gui.page.edit.exec-1"
		, "xowa.gui.shortcuts.xowa.gui.page.selection.select_all-1"
		, "xowa.gui.shortcuts.xowa.gui.page.selection.copy-1"
		, "xowa.gui.shortcuts.xowa.gui.page.selection.save_file_as-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.url.focus-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.url.focus-2"
		, "xowa.gui.shortcuts.xowa.gui.browser.url.exec-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.url.exec_by_paste-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.url.exec_by_paste-2"
		, "xowa.gui.shortcuts.xowa.gui.browser.url.exec_new_tab_by_paste-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.url.restore-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.search.focus-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.search.exec-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.allpages.focus-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.allpages.exec-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.html.focus-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.html.focus-2"
		, "xowa.gui.shortcuts.xowa.gui.browser.html.focus-3"
		, "xowa.gui.shortcuts.xowa.gui.browser.html.focus-4"
		, "xowa.gui.shortcuts.xowa.gui.browser.html.selection_focus_toggle-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.find.show-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.find.show_by_paste-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.find.hide-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.find.exec-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.find.find_fwd-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.find.find_bwd-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.find.case_toggle-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.find.wrap_toggle-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.prog.focus-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.prog_log.show-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.prog_log.show-2"
		, "xowa.gui.shortcuts.xowa.gui.browser.info.focus-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.info.clear-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.new_dflt__at_dflt__focus_y-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.new_link__at_dflt__focus_y-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.new_link__at_dflt__focus_n-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.new_href__at_dflt__focus_y-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.select_bwd-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.select_bwd-2"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.select_fwd-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.select_fwd-2"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.select_by_idx_1-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.select_by_idx_2-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.select_by_idx_3-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.select_by_idx_4-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.select_by_idx_5-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.select_by_idx_6-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.select_by_idx_7-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.select_by_idx_8-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.select_by_idx_9-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.move_bwd-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.move_fwd-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.close_cur-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.close_others-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.close_to_bgn-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.close_to_end-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.close_undo-1"
		, "xowa.gui.shortcuts.xowa.gui.browser.tabs.pin_toggle-1"
		, "xowa.gui.shortcuts.xowa.html.tidy.toggle-1"
		, "xowa.gui.shortcuts.xowa.net.enabled_n_-1"
		, "xowa.gui.shortcuts.xowa.net.enabled_y_-1"
		, "xowa.gui.shortcuts.xowa.net.enabled_x_-1"
		, "xowa.gui.shortcuts.xowa.usr.bookmarks.add-1"
		, "xowa.gui.shortcuts.xowa.usr.bookmarks.show-1"
		, "xowa.gui.shortcuts.xowa.usr.history.goto_recent-1"
		, "xowa.gui.shortcuts.xowa.usr.history.show-1"
		);
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
	public void Del(Xog_bnd_itm new_bnd, IptArg new_ipt) {
		boolean new_ipt_exists = !IptArg_.Is_null_or_none(new_ipt);
		List_adp deleted = List_adp_.New();

		// loop over each box
		for (int i = 0; i < Xog_bnd_box_.Ary_len; i++) {
			Xog_bnd_box old_box = boxs[i];
			int old_itms_len = old_box.Len();

			// loop over each bnd
			for (int j = 0; j < old_itms_len; j++) {
				Xog_bnd_itm old_bnd = old_box.Get_at(j);

				// if keys match, delete old_bnd
				if		(	String_.Eq(old_bnd.Key(), new_bnd.Key())) {
					Xog_bnd_box_.Set_bnd_for_grp(Xog_bnd_box_.Set_del_key, win, invk_mgr, old_box, old_bnd, new_bnd.Ipt());
					deleted.Add(new_bnd);
				}
				// if ipts match, delete old_bnd
				else if (	new_ipt_exists
						&&	old_bnd.Box() == new_bnd.Box()
						&&	String_.Eq(old_bnd.Ipt().Key(), new_ipt.Key())) {
					Xog_bnd_box_.Set_bnd_for_grp(Xog_bnd_box_.Set_del_ipt, win, invk_mgr, old_box, old_bnd, old_bnd.Ipt());
					old_bnd.Ipt_to_none();
				}
			}

			// remove old bnd from box
			int deleted_len = deleted.Count();
			for (int j = 0; j < deleted_len; j++) {
				// delete from box
				Xog_bnd_itm deleted_itm = (Xog_bnd_itm)deleted.Get_at(j);
				old_box.Del(deleted_itm.Key());

				// add back other items with same cmd but different key; needed b/c gfui.ipt_mgr hashes by cmd ("sandbox"), not key ("sandbox-1"); DATE:2016-12-25
				List_adp list = old_box.Get_list_by_cmd(deleted_itm.Cmd());
				if (list != null) {
					int len = list.Len();
					for (int k = 0; k < len; k++) {
						Xog_bnd_itm restore_itm = (Xog_bnd_itm)list.Get_at(k);
						Xog_bnd_box_.Set_bnd_for_grp(Xog_bnd_box_.Set_add, win, invk_mgr, old_box, restore_itm, restore_itm.Ipt());
					}
				}
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
		Init_itm(Xog_cmd_itm_.Key_nav_wiki_allpages								, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_wiki_search_title							, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_wiki_search_full							, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_wiki_search_per_cfg						, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_wiki_sandbox								, Xog_bnd_box_.Tid_browser				, "mod.cs+key.g,mod.cs+key.s", "mod.c+key.f1");
		Init_itm(Xog_cmd_itm_.Key_nav_help_help									, Xog_bnd_box_.Tid_browser				, "key.f1");
		Init_itm(Xog_cmd_itm_.Key_nav_help_change_log							, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_help_diagnostics							, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_help_about								, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_help_xowa_main							, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_help_xowa_blog							, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_help_xowa_update							, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_setup_download_central					, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_setup_import_from_list					, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_setup_import_from_script					, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_setup_maintenance							, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_setup_download							, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_personal_item								, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_personal_list								, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_system_data_log_session					, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_system_data_cfg_app						, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_nav_system_data_cfg_lang						, Xog_bnd_box_.Tid_browser				, "");
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
		Init_itm(Xog_cmd_itm_.Key_gui_page_view_print							, Xog_bnd_box_.Tid_browser				, "mod.c+key.p");
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
		Init_itm(Xog_cmd_itm_.Key_gui_browser_url_exec							, Xog_bnd_box_.Tid_browser_url			, "key.enter");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_url_exec_new_tab_by_paste			, Xog_bnd_box_.Tid_browser_url			, "mod.c+key.enter");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_url_exec_by_paste					, Xog_bnd_box_.Tid_browser_url			, "mouse.middle", "mod.a+key.enter");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_url_restore						, Xog_bnd_box_.Tid_browser_url			, "key.escape");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_search_focus						, Xog_bnd_box_.Tid_browser				, "mod.ca+key.s");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_search_exec						, Xog_bnd_box_.Tid_browser_search		, "key.enter");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_allpages_focus					, Xog_bnd_box_.Tid_browser				, "mod.ca+key.a");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_allpages_exec						, Xog_bnd_box_.Tid_browser_allpages		, "key.enter");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_new_dflt__at_dflt__focus_y	, Xog_bnd_box_.Tid_browser				, "mod.c+key.t");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_new_link__at_dflt__focus_n	, Xog_bnd_box_.Tid_browser_html			, "mouse.middle");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_tabs_new_link__at_dflt__focus_y	, Xog_bnd_box_.Tid_browser				, "");
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
		Init_itm(Xog_cmd_itm_.Key_gui_browser_html_focus					,  0, Xog_bnd_box_.Tid_browser_url			, "key.escape");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_html_focus					,  1, Xog_bnd_box_.Tid_browser_search		, "key.escape");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_html_focus					,  2, Xog_bnd_box_.Tid_browser_prog			, "key.escape");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_html_focus					,  3, Xog_bnd_box_.Tid_browser_info			, "key.escape");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_html_selection_focus_toggle		, Xog_bnd_box_.Tid_browser				, "mod.c+key.g,mod.c+key.g");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_find_show							, Xog_bnd_box_.Tid_browser				, "mod.c+key.f");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_find_show_by_paste				, Xog_bnd_box_.Tid_browser				, "");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_find_hide							, Xog_bnd_box_.Tid_browser_find			, "key.escape");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_find_exec							, Xog_bnd_box_.Tid_browser_find			, "key.enter");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_find_find_fwd						, Xog_bnd_box_.Tid_browser_find			, "mod.a+key.n");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_find_find_bwd						, Xog_bnd_box_.Tid_browser_find			, "mod.a+key.p");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_find_case_toggle					, Xog_bnd_box_.Tid_browser_find			, "mod.a+key.c");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_find_wrap_toggle					, Xog_bnd_box_.Tid_browser_find			, "mod.a+key.w");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_prog_focus						, Xog_bnd_box_.Tid_browser				, "mod.ca+key.p");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_info_focus						, Xog_bnd_box_.Tid_browser				, "mod.ca+key.i");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_info_clear						, Xog_bnd_box_.Tid_browser				, "mod.ca+key.c");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_prog_log_show						, Xog_bnd_box_.Tid_browser_prog			, "mouse.middle", "mod.cs+key.p");
		Init_itm(Xog_cmd_itm_.Key_gui_browser_nightmode_toggle					, Xog_bnd_box_.Tid_browser				, "");
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
		IptBnd_.ipt_to_(null_cfg		, win.Go_bwd_btn()		 , invk_mgr, Xog_cmd_itm_.Key_nav_go_bwd					, btn_event_type, btn_args);
		IptBnd_.ipt_to_(null_cfg		, win.Go_fwd_btn()		 , invk_mgr, Xog_cmd_itm_.Key_nav_go_fwd					, btn_event_type, btn_args);
		IptBnd_.ipt_to_(null_cfg		, win.Url_exec_btn()	 , invk_mgr, Xog_cmd_itm_.Key_gui_browser_url_exec			, btn_event_type, btn_args);
		IptBnd_.ipt_to_(null_cfg		, win.Search_exec_btn()	 , invk_mgr, Xog_cmd_itm_.Key_gui_browser_search_exec		, btn_event_type, btn_args);
		IptBnd_.ipt_to_(null_cfg		, win.Allpages_exec_btn(), invk_mgr, Xog_cmd_itm_.Key_gui_browser_allpages_exec		, btn_event_type, btn_args);
		IptBnd_.ipt_to_(null_cfg		, win.Find_close_btn()	 , invk_mgr, Xog_cmd_itm_.Key_gui_browser_find_hide			, btn_event_type, btn_args);
		IptBnd_.ipt_to_(null_cfg		, win.Find_fwd_btn()	 , invk_mgr, Xog_cmd_itm_.Key_gui_browser_find_find_fwd		, btn_event_type, btn_args);
		IptBnd_.ipt_to_(null_cfg		, win.Find_bwd_btn()	 , invk_mgr, Xog_cmd_itm_.Key_gui_browser_find_find_bwd		, btn_event_type, btn_args);
		IptBnd_.ipt_to_(null_cfg		, win.Find_box()		 , invk_mgr, Xog_cmd_itm_.Key_gui_browser_find_type			, IptEventType_.KeyUp, IptKey_.printableKeys_(IptKey_.Ary(IptKey_.Back, IptKey_.Escape, IptKey_.Ctrl.Add(IptKey_.V)), IptKey_.Ary()));
		IptBnd_.ipt_to_(null_cfg		, win.Url_box()			 , invk_mgr, Xog_cmd_itm_.Key_gui_browser_url_type			, IptEventType_.KeyUp, IptKey_.printableKeys_(IptKey_.Ary(IptKey_.Back, IptKey_.Escape, IptKey_.Ctrl.Add(IptKey_.X), IptKey_.Ctrl.Add(IptKey_.V)), IptKey_.Ary()));
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
		this.Bind(Xog_bnd_box_.Tid_browser_allpages		, win.Allpages_box());
		this.Bind(Xog_bnd_box_.Tid_browser_prog			, win.Prog_box());
		this.Bind(Xog_bnd_box_.Tid_browser_info			, win.Info_box());
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(String_.Eq(k, Run__show_remap_win)) {
			Xog_bnd_win win = new Xog_bnd_win();			
			String[] args = m.ReadStrAry("v", "\n");
			win.Show(app, app.Gui_mgr().Kit(), app.Gui_mgr().Browser_win().Win_box(), args[0], args[1], args[2]);
		}
		else {
			String val = m.ReadStr("v");
			if (String_.Len_eq_0(val)) { // need to check, or may fail when running newer codebase against old cfgs; DATE:2017-06-02
				Gfo_usr_dlg_.Instance.Warn_many("", "", "binding does not have val; key=~{0}", k);
				return this;
			}
			String[] flds = gplx.xowa.addons.apps.cfgs.enums.Xoitm_gui_binding.To_ary(val);
			int box = Xog_bnd_box_.Xto_sys_int(flds[0]);
			String key = String_.Replace(k, "xowa.gui.shortcuts.", "");
			Xog_bnd_itm bnd = app.Gui_mgr().Bnd_mgr().Get_or_null(key);
			Set(bnd, box, IptArg_.parse(flds[1]));
		}
		return this;
	}
	private static final String Run__show_remap_win = "xowa.gui.shortcuts.show_remap_win";
}
