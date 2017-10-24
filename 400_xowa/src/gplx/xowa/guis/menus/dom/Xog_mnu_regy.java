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
package gplx.xowa.guis.menus.dom; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*; import gplx.xowa.guis.menus.*;
import gplx.xowa.guis.cmds.*;
public class Xog_mnu_regy {
	private Ordered_hash hash;
	private Xoa_gui_mgr gui_mgr;
	public Xog_mnu_regy(Xoa_gui_mgr gui_mgr) {
		this.gui_mgr = gui_mgr;
	}
	public void Init_by_app(Xoae_app app) {
		hash = Ordered_hash_.New();
		Xog_cmd_mgr cmd_mgr = app.Gui_mgr().Cmd_mgr();
		int len = cmd_mgr.Len();
		for (int i = 0; i < len; i++) {
			Xog_cmd_itm cmd_itm = cmd_mgr.Get_at(i);
			String key = cmd_itm.Key();
			Xog_mnu_itm rv = new Xog_mnu_itm(gui_mgr, key);
			hash.Add(key, rv);
		}
		Init_obsolete(hash, gui_mgr);
	}
	public Xog_mnu_itm Get_or_make(String key) {
		Xog_mnu_itm rv = (Xog_mnu_itm)hash.Get_by(key);
		if (rv == null) {
			rv = new Xog_mnu_itm(gui_mgr, key);
			hash.Add(key, rv);
		}
		else
			rv = rv.Clone();
		return rv;
	}
	private static void Init_obsolete(Ordered_hash hash, Xoa_gui_mgr gui_mgr) {
		Init_obsolete_itm(hash, gui_mgr, "xowa.file"										, Xog_cmd_itm_.Key_gui_menus_group_file);
		Init_obsolete_itm(hash, gui_mgr, "xowa.tabs.new_dflt__at_dflt__focus_y"				, Xog_cmd_itm_.Key_gui_browser_tabs_new_dflt__at_dflt__focus_y);
		Init_obsolete_itm(hash, gui_mgr, "xowa.tabs.close_cur"								, Xog_cmd_itm_.Key_gui_browser_tabs_close_cur);
		Init_obsolete_itm(hash, gui_mgr, "xowa.file.save_as"								, Xog_cmd_itm_.Key_gui_page_view_save_as);
		Init_obsolete_itm(hash, gui_mgr, "xowa.file.print"									, Xog_cmd_itm_.Key_gui_page_view_print);
		Init_obsolete_itm(hash, gui_mgr, "xowa.file.exit"									, Xog_cmd_itm_.Key_app_exit);
		Init_obsolete_itm(hash, gui_mgr, "xowa.edit"										, Xog_cmd_itm_.Key_gui_menus_group_edit);
		Init_obsolete_itm(hash, gui_mgr, "xowa.edit.select_all"								, Xog_cmd_itm_.Key_gui_page_selection_select_all);
		Init_obsolete_itm(hash, gui_mgr, "xowa.edit.copy"									, Xog_cmd_itm_.Key_gui_page_selection_copy);
		Init_obsolete_itm(hash, gui_mgr, "xowa.edit.find"									, Xog_cmd_itm_.Key_gui_browser_find_show_by_paste);
		Init_obsolete_itm(hash, gui_mgr, "xowa.view"										, Xog_cmd_itm_.Key_gui_menus_group_view);
		Init_obsolete_itm(hash, gui_mgr, "xowa.view.font.increase"							, Xog_cmd_itm_.Key_gui_font_increase);
		Init_obsolete_itm(hash, gui_mgr, "xowa.view.font.decrease"							, Xog_cmd_itm_.Key_gui_font_decrease);
		Init_obsolete_itm(hash, gui_mgr, "xowa.view.page.read"								, Xog_cmd_itm_.Key_gui_page_view_mode_read);
		Init_obsolete_itm(hash, gui_mgr, "xowa.view.page.edit"								, Xog_cmd_itm_.Key_gui_page_view_mode_edit);
		Init_obsolete_itm(hash, gui_mgr, "xowa.view.page.html"								, Xog_cmd_itm_.Key_gui_page_view_mode_html);
		Init_obsolete_itm(hash, gui_mgr, "xowa.view.windows.progress.show"					, Xog_cmd_itm_.Key_gui_browser_prog_log_show);
		Init_obsolete_itm(hash, gui_mgr, "xowa.history"										, Xog_cmd_itm_.Key_gui_menus_group_history);
		Init_obsolete_itm(hash, gui_mgr, "xowa.history.go_bwd"								, Xog_cmd_itm_.Key_nav_go_bwd);
		Init_obsolete_itm(hash, gui_mgr, "xowa.history.go_fwd"								, Xog_cmd_itm_.Key_nav_go_fwd);
		Init_obsolete_itm(hash, gui_mgr, "xowa.history.show"								, Xog_cmd_itm_.Key_usr_history_show);
		Init_obsolete_itm(hash, gui_mgr, "xowa.bookmarks"									, Xog_cmd_itm_.Key_gui_menus_group_bookmarks);
		Init_obsolete_itm(hash, gui_mgr, "xowa.bookmarks.goto_main_page"					, Xog_cmd_itm_.Key_nav_wiki_main_page);
		Init_obsolete_itm(hash, gui_mgr, "xowa.bookmarks.add"								, Xog_cmd_itm_.Key_usr_bookmarks_add);
		Init_obsolete_itm(hash, gui_mgr, "xowa.bookmarks.show"								, Xog_cmd_itm_.Key_usr_bookmarks_show);
		Init_obsolete_itm(hash, gui_mgr, "xowa.tools"										, Xog_cmd_itm_.Key_gui_menus_group_tools);
		Init_obsolete_itm(hash, gui_mgr, "xowa.tools.options"								, Xog_cmd_itm_.Key_nav_cfg_main);
		Init_obsolete_itm(hash, gui_mgr, "xowa.tools.wikis.import_from_list"				, Xog_cmd_itm_.Key_nav_setup_import_from_list);
		Init_obsolete_itm(hash, gui_mgr, "xowa.tools.wikis.import_from_script"				, Xog_cmd_itm_.Key_nav_setup_import_from_script);
		Init_obsolete_itm(hash, gui_mgr, "xowa.tools.wikis.maintenance"						, Xog_cmd_itm_.Key_nav_setup_maintenance);
		Init_obsolete_itm(hash, gui_mgr, "xowa.tools.wikis.download"						, Xog_cmd_itm_.Key_nav_setup_download);
		Init_obsolete_itm(hash, gui_mgr, "xowa.help"										, Xog_cmd_itm_.Key_gui_menus_group_help);
		Init_obsolete_itm(hash, gui_mgr, "xowa.help.help"									, Xog_cmd_itm_.Key_nav_help_help);
		Init_obsolete_itm(hash, gui_mgr, "xowa.help.change_log"								, Xog_cmd_itm_.Key_nav_help_change_log);
		Init_obsolete_itm(hash, gui_mgr, "xowa.help.diagnostics"							, Xog_cmd_itm_.Key_nav_help_diagnostics);
		Init_obsolete_itm(hash, gui_mgr, "xowa.help.context_menu"							, Xog_cmd_itm_.Key_nav_cfg_menu);
		Init_obsolete_itm(hash, gui_mgr, "xowa.system.data"									, Xog_cmd_itm_.Key_gui_menus_group_system_data);
		Init_obsolete_itm(hash, gui_mgr, "xowa.system.data.log_session"						, Xog_cmd_itm_.Key_nav_system_data_log_session);
		Init_obsolete_itm(hash, gui_mgr, "xowa.system.data.cfg_app"							, Xog_cmd_itm_.Key_nav_system_data_cfg_app);
		Init_obsolete_itm(hash, gui_mgr, "xowa.system.data.cfg_lang"						, Xog_cmd_itm_.Key_nav_system_data_cfg_lang);
		Init_obsolete_itm(hash, gui_mgr, "xowa.system.data.usr_history"						, Xog_cmd_itm_.Key_nav_system_data_usr_history);
		Init_obsolete_itm(hash, gui_mgr, "xowa.help.about"									, Xog_cmd_itm_.Key_nav_help_about);
		Init_obsolete_itm(hash, gui_mgr, "xowa.tabs.new_link__at_dflt__focus_n"				, Xog_cmd_itm_.Key_gui_browser_tabs_new_link__at_dflt__focus_n);
		Init_obsolete_itm(hash, gui_mgr, "xowa.tabs.new_link__at_dflt__focus_y"				, Xog_cmd_itm_.Key_gui_browser_tabs_new_link__at_dflt__focus_y);
		Init_obsolete_itm(hash, gui_mgr, "xowa.widgets.find.show_by_paste"					, Xog_cmd_itm_.Key_gui_browser_find_show_by_paste);
		Init_obsolete_itm(hash, gui_mgr, "xowa.file.save_file_as"							, Xog_cmd_itm_.Key_gui_page_selection_save_file_as);
		Init_obsolete_itm(hash, gui_mgr, "xowa.widgets.prog_log.show"						, Xog_cmd_itm_.Key_gui_browser_prog_log_show);
	}
	private static void Init_obsolete_itm(Ordered_hash hash, Xoa_gui_mgr gui_mgr, String old, String cur) {
		Xog_mnu_itm rv = new Xog_mnu_itm(gui_mgr, cur);
		hash.Add(old, rv);
	}
}
