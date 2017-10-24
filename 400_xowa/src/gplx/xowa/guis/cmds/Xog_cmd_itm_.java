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
package gplx.xowa.guis.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
public class Xog_cmd_itm_ {
	private static final    Ordered_hash regy = Ordered_hash_.New();	// NOTE: must be defined at top
	public static final    String 
	  Key_app_exit												= new_dflt_(Xog_ctg_itm_.Tid_app			, "xowa.app.exit")

	, Key_nav_go_bwd											= new_dflt_(Xog_ctg_itm_.Tid_nav			, "xowa.nav.go_bwd")
	, Key_nav_go_fwd											= new_dflt_(Xog_ctg_itm_.Tid_nav			, "xowa.nav.go_fwd")

	, Key_nav_cfg_main											= new_page_(Xog_ctg_itm_.Tid_nav			, "xowa.nav.cfg.main"								, "home/wiki/Options")				// HOME
	, Key_nav_cfg_menu											= new_page_(Xog_ctg_itm_.Tid_nav			, "xowa.nav.cfg.menus"								, "home/wiki/Options/Menus")		// HOME

	, Key_nav_wiki_main_page									= new_dflt_(Xog_ctg_itm_.Tid_nav			, "xowa.nav.wiki.main_page")
	, Key_nav_wiki_sandbox										= new_dflt_(Xog_ctg_itm_.Tid_nav			, "xowa.nav.wiki.sandbox")
	, Key_nav_wiki_random										= new_dflt_(Xog_ctg_itm_.Tid_nav			, "xowa.nav.wiki.random")
	, Key_nav_wiki_allpages										= new_dflt_(Xog_ctg_itm_.Tid_nav			, "xowa.nav.wiki.allpages")
	, Key_nav_wiki_search_title									= new_dflt_(Xog_ctg_itm_.Tid_nav			, "xowa.nav.wiki.search_title")
	, Key_nav_wiki_search_full									= new_dflt_(Xog_ctg_itm_.Tid_nav			, "xowa.nav.wiki.search_full")
	, Key_nav_wiki_search_per_cfg								= new_dflt_(Xog_ctg_itm_.Tid_nav			, "xowa.nav.wiki.search_per_cfg")

	, Key_nav_help_help											= new_page_(Xog_ctg_itm_.Tid_nav_pages		, "xowa.nav.help.help"								, "home/wiki/Help/Contents")               // HOME
	, Key_nav_help_about										= new_page_(Xog_ctg_itm_.Tid_nav_pages		, "xowa.nav.help.about"								, "home/wiki/Help/About")                  // HOME
	, Key_nav_help_change_log									= new_page_(Xog_ctg_itm_.Tid_nav_pages		, "xowa.nav.help.change_log"						, "home/wiki/Change_log")                  // HOME
	, Key_nav_help_diagnostics									= new_page_(Xog_ctg_itm_.Tid_nav_pages		, "xowa.nav.help.diagnostics"						, "home/wiki/Diagnostics")                 // HOME
	, Key_nav_help_xowa_update									= new_page_(Xog_ctg_itm_.Tid_nav_pages		, "xowa.nav.help.xowa_update"						, "home/wiki/Special:XowaAppUpdate")       // HOME
	, Key_nav_help_xowa_main									= new_page_(Xog_ctg_itm_.Tid_nav_pages		, "xowa.nav.help.xowa_main"							, "home/wiki/Main_Page")                   // HOME
	, Key_nav_help_xowa_blog									= new_page_(Xog_ctg_itm_.Tid_nav_pages		, "xowa.nav.help.xowa_blog"							, "home/wiki/Blog")                        // HOME

	, Key_nav_setup_download_central							= new_page_(Xog_ctg_itm_.Tid_nav_pages		, "xowa.nav.setup.download_central"					, "home/wiki/Special:XowaDownloadCentral")	// HOME
	, Key_nav_setup_import_from_list							= new_page_(Xog_ctg_itm_.Tid_nav_pages		, "xowa.nav.setup.import_from_list"					, "home/wiki/Dashboard/Import/Online")		// HOME
	, Key_nav_setup_import_from_script							= new_page_(Xog_ctg_itm_.Tid_nav_pages		, "xowa.nav.setup.import_from_script"				, "home/wiki/Dashboard/Import/Offline")		// HOME
	, Key_nav_setup_maintenance									= new_page_(Xog_ctg_itm_.Tid_nav_pages		, "xowa.nav.setup.maintenance"						, "home/wiki/Dashboard/Wiki_maintenance")	// HOME
	, Key_nav_setup_download									= new_page_(Xog_ctg_itm_.Tid_nav_pages		, "xowa.nav.setup.download"							, "home/wiki/Dashboard/Image_databases")	// HOME

	, Key_nav_system_data_log_session							= new_page_(Xog_ctg_itm_.Tid_nav_pages		, "xowa.nav.system_data.log_session"				, "Special:XowaSystemData?type=log_session")
	, Key_nav_system_data_cfg_app								= new_page_(Xog_ctg_itm_.Tid_nav_pages		, "xowa.nav.system_data.cfg_app"					, "Special:XowaSystemData?type=cfg_app")
	, Key_nav_system_data_cfg_lang								= new_page_(Xog_ctg_itm_.Tid_nav_pages		, "xowa.nav.system_data.cfg_lang"					, "Special:XowaSystemData?type=cfg_lang")
	, Key_nav_system_data_usr_history							= new_page_(Xog_ctg_itm_.Tid_nav_pages		, "xowa.nav.system_data.usr_history"				, "Special:XowaSystemData?type=usr_history")

	, Key_nav_personal_item										= new_page_(Xog_ctg_itm_.Tid_nav_pages		, "xowa.nav.personal.item"							, "home/wiki/Special:XowaWikiItem")
	, Key_nav_personal_list										= new_page_(Xog_ctg_itm_.Tid_nav_pages		, "xowa.nav.personal.list"							, "home/wiki/Special:XowaWikiDirectory")

	, Key_gui_font_increase										= new_dflt_(Xog_ctg_itm_.Tid_font			, "xowa.gui.font.increase")
	, Key_gui_font_decrease										= new_dflt_(Xog_ctg_itm_.Tid_font			, "xowa.gui.font.decrease")
	, Key_gui_font_reset										= new_dflt_(Xog_ctg_itm_.Tid_font			, "xowa.gui.font.reset")

	, Key_gui_page_view_mode_read								= new_dflt_(Xog_ctg_itm_.Tid_page			, "xowa.gui.page.view.mode_read")
	, Key_gui_page_view_mode_edit								= new_dflt_(Xog_ctg_itm_.Tid_page			, "xowa.gui.page.view.mode_edit")
	, Key_gui_page_view_mode_html								= new_dflt_(Xog_ctg_itm_.Tid_page			, "xowa.gui.page.view.mode_html")
	, Key_gui_page_view_refresh									= new_dflt_(Xog_ctg_itm_.Tid_page			, "xowa.gui.page.view.refresh")
	, Key_gui_page_view_reload									= new_dflt_(Xog_ctg_itm_.Tid_page			, "xowa.gui.page.view.reload")
	, Key_gui_page_view_save_as									= new_dflt_(Xog_ctg_itm_.Tid_page			, "xowa.gui.page.view.save_as")
	, Key_gui_page_view_print									= new_dflt_(Xog_ctg_itm_.Tid_page			, "xowa.gui.page.view.print")

	, Key_gui_page_selection_select_all							= new_dflt_(Xog_ctg_itm_.Tid_selection		, "xowa.gui.page.selection.select_all")
	, Key_gui_page_selection_copy								= new_dflt_(Xog_ctg_itm_.Tid_selection		, "xowa.gui.page.selection.copy")
	, Key_gui_page_selection_save_file_as						= new_dflt_(Xog_ctg_itm_.Tid_selection		, "xowa.gui.page.selection.save_file_as")

	, Key_gui_edit_save											= new_dflt_(Xog_ctg_itm_.Tid_edit			, "xowa.gui.page.edit.save")
	, Key_gui_edit_save_draft									= new_dflt_(Xog_ctg_itm_.Tid_edit			, "xowa.gui.page.edit.save_draft")
	, Key_gui_edit_focus_edit_box								= new_dflt_(Xog_ctg_itm_.Tid_edit			, "xowa.gui.page.edit.focus_edit_box")
	, Key_gui_edit_preview										= new_dflt_(Xog_ctg_itm_.Tid_edit			, "xowa.gui.page.edit.preview")
	, Key_gui_edit_dbg_tmpl										= new_dflt_(Xog_ctg_itm_.Tid_edit			, "xowa.gui.page.edit.dbg_tmpl")
	, Key_gui_edit_dbg_html										= new_dflt_(Xog_ctg_itm_.Tid_edit			, "xowa.gui.page.edit.dbg_html")
	, Key_gui_edit_exec											= new_dflt_(Xog_ctg_itm_.Tid_edit			, "xowa.gui.page.edit.exec")

	, Key_gui_browser_url_focus									= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.url.focus")
	, Key_gui_browser_url_exec									= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.url.exec")
	, Key_gui_browser_url_exec_by_paste							= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.url.exec_by_paste")
	, Key_gui_browser_url_exec_new_tab_by_paste					= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.url.exec_new_tab_by_paste")
	, Key_gui_browser_url_restore								= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.url.restore")
	, Key_gui_browser_url_type									= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.url.type")

	, Key_gui_browser_search_focus								= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.search.focus")
	, Key_gui_browser_search_exec								= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.search.exec")
	, Key_gui_browser_allpages_focus							= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.allpages.focus")
	, Key_gui_browser_allpages_exec								= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.allpages.exec")
	, Key_gui_browser_tabs_new_dflt__at_dflt__focus_y			= new_dflt_(Xog_ctg_itm_.Tid_tabs			, "xowa.gui.browser.tabs.new_dflt__at_dflt__focus_y")
	, Key_gui_browser_tabs_new_link__at_dflt__focus_n			= new_dflt_(Xog_ctg_itm_.Tid_tabs			, "xowa.gui.browser.tabs.new_link__at_dflt__focus_n")
	, Key_gui_browser_tabs_new_link__at_dflt__focus_y			= new_dflt_(Xog_ctg_itm_.Tid_tabs			, "xowa.gui.browser.tabs.new_link__at_dflt__focus_y")
	, Key_gui_browser_tabs_new_href__at_dflt__focus_y			= new_dflt_(Xog_ctg_itm_.Tid_tabs			, "xowa.gui.browser.tabs.new_href__at_dflt__focus_y")
	, Key_gui_browser_tabs_new_dupe__at_dflt__focus_y			= new_dflt_(Xog_ctg_itm_.Tid_tabs			, "xowa.gui.browser.tabs.new_dupe__at_dflt__focus_y")
	, Key_gui_browser_tabs_select_bwd							= new_dflt_(Xog_ctg_itm_.Tid_tabs			, "xowa.gui.browser.tabs.select_bwd")
	, Key_gui_browser_tabs_select_fwd							= new_dflt_(Xog_ctg_itm_.Tid_tabs			, "xowa.gui.browser.tabs.select_fwd")
	, Key_gui_browser_tabs_select_by_idx_1						= new_dflt_(Xog_ctg_itm_.Tid_tabs			, "xowa.gui.browser.tabs.select_by_idx_1")
	, Key_gui_browser_tabs_select_by_idx_2						= new_dflt_(Xog_ctg_itm_.Tid_tabs			, "xowa.gui.browser.tabs.select_by_idx_2")
	, Key_gui_browser_tabs_select_by_idx_3						= new_dflt_(Xog_ctg_itm_.Tid_tabs			, "xowa.gui.browser.tabs.select_by_idx_3")
	, Key_gui_browser_tabs_select_by_idx_4						= new_dflt_(Xog_ctg_itm_.Tid_tabs			, "xowa.gui.browser.tabs.select_by_idx_4")
	, Key_gui_browser_tabs_select_by_idx_5						= new_dflt_(Xog_ctg_itm_.Tid_tabs			, "xowa.gui.browser.tabs.select_by_idx_5")
	, Key_gui_browser_tabs_select_by_idx_6						= new_dflt_(Xog_ctg_itm_.Tid_tabs			, "xowa.gui.browser.tabs.select_by_idx_6")
	, Key_gui_browser_tabs_select_by_idx_7						= new_dflt_(Xog_ctg_itm_.Tid_tabs			, "xowa.gui.browser.tabs.select_by_idx_7")
	, Key_gui_browser_tabs_select_by_idx_8						= new_dflt_(Xog_ctg_itm_.Tid_tabs			, "xowa.gui.browser.tabs.select_by_idx_8")
	, Key_gui_browser_tabs_select_by_idx_9						= new_dflt_(Xog_ctg_itm_.Tid_tabs			, "xowa.gui.browser.tabs.select_by_idx_9")
	, Key_gui_browser_tabs_move_bwd								= new_dflt_(Xog_ctg_itm_.Tid_tabs			, "xowa.gui.browser.tabs.move_bwd")
	, Key_gui_browser_tabs_move_fwd								= new_dflt_(Xog_ctg_itm_.Tid_tabs			, "xowa.gui.browser.tabs.move_fwd")
	, Key_gui_browser_tabs_close_cur							= new_dflt_(Xog_ctg_itm_.Tid_tabs			, "xowa.gui.browser.tabs.close_cur")
	, Key_gui_browser_tabs_close_others							= new_dflt_(Xog_ctg_itm_.Tid_tabs			, "xowa.gui.browser.tabs.close_others")
	, Key_gui_browser_tabs_close_to_bgn							= new_dflt_(Xog_ctg_itm_.Tid_tabs			, "xowa.gui.browser.tabs.close_to_bgn")
	, Key_gui_browser_tabs_close_to_end							= new_dflt_(Xog_ctg_itm_.Tid_tabs			, "xowa.gui.browser.tabs.close_to_end")
	, Key_gui_browser_tabs_close_undo							= new_dflt_(Xog_ctg_itm_.Tid_tabs			, "xowa.gui.browser.tabs.close_undo")
	, Key_gui_browser_tabs_pin_toggle							= new_dflt_(Xog_ctg_itm_.Tid_tabs			, "xowa.gui.browser.tabs.pin_toggle")
	, Key_gui_browser_html_focus								= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.html.focus")
	, Key_gui_browser_html_selection_focus_toggle				= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.html.selection_focus_toggle")
	, Key_gui_browser_find_show									= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.find.show")
	, Key_gui_browser_find_show_by_paste						= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.find.show_by_paste")
	, Key_gui_browser_find_hide									= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.find.hide")
	, Key_gui_browser_find_exec									= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.find.exec")
	, Key_gui_browser_find_type									= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.find.type")
	, Key_gui_browser_find_find_fwd								= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.find.find_fwd")
	, Key_gui_browser_find_find_bwd								= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.find.find_bwd")
	, Key_gui_browser_find_case_toggle							= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.find.case_toggle")
	, Key_gui_browser_find_wrap_toggle							= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.find.wrap_toggle")
	, Key_gui_browser_prog_focus								= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.prog.focus")
	, Key_gui_browser_prog_log_show								= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.prog_log.show")
	, Key_gui_browser_info_focus								= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.info.focus")
	, Key_gui_browser_info_clear								= new_dflt_(Xog_ctg_itm_.Tid_browser		, "xowa.gui.browser.info.clear")
	, Key_gui_browser_nightmode_toggle                          = new_dflt_(Xog_ctg_itm_.Tid_browser        , "xowa.gui.browser.nightmode_toggle")

	, Key_gui_menus_group_file									= "xowa.gui.menus.group.file"
	, Key_gui_menus_group_edit									= "xowa.gui.menus.group.edit"
	, Key_gui_menus_group_view									= "xowa.gui.menus.group.view"
	, Key_gui_menus_group_history								= "xowa.gui.menus.group.history"
	, Key_gui_menus_group_bookmarks								= "xowa.gui.menus.group.bookmarks"
	, Key_gui_menus_group_tools									= "xowa.gui.menus.group.tools"
	, Key_gui_menus_group_tools_wikis							= "xowa.gui.menus.group.tools.wikis"
	, Key_gui_menus_group_help									= "xowa.gui.menus.group.help"
	, Key_gui_menus_group_system_data							= "xowa.gui.menus.group.system_data"
	, Key_gui_menus_group_tabs									= "xowa.gui.menus.group.tabs"

	, Key_html_tidy_toggle										= new_dflt_(Xog_ctg_itm_.Tid_html			, "xowa.html.tidy.toggle")
	, Key_html_tidy_engine_tidy_								= new_dflt_(Xog_ctg_itm_.Tid_html			, "xowa.html.tidy.engine_tidy_")
	, Key_html_tidy_engine_jtidy_								= new_dflt_(Xog_ctg_itm_.Tid_html			, "xowa.html.tidy.engine_jtidy_")

	, Key_net_enabled											= new_dflt_(Xog_ctg_itm_.Tid_net			, "xowa.net.enabled")
	, Key_net_enabled_n_										= new_dflt_(Xog_ctg_itm_.Tid_net			, "xowa.net.enabled_n_")
	, Key_net_enabled_y_										= new_dflt_(Xog_ctg_itm_.Tid_net			, "xowa.net.enabled_y_")
	, Key_net_enabled_x_										= new_dflt_(Xog_ctg_itm_.Tid_net			, "xowa.net.enabled_x_")

	, Key_usr_bookmarks_add										= new_dflt_(Xog_ctg_itm_.Tid_bookmarks		, "xowa.usr.bookmarks.add")
	, Key_usr_bookmarks_show									= new_dflt_(Xog_ctg_itm_.Tid_bookmarks		, "xowa.usr.bookmarks.show")

	, Key_usr_history_goto_recent								= new_dflt_(Xog_ctg_itm_.Tid_history		, "xowa.usr.history.goto_recent")
	, Key_usr_history_show										= new_page_(Xog_ctg_itm_.Tid_history		, "xowa.usr.history.show"							, "home/wiki/Special:XowaPageHistory")

	, Key_xtns_scribunto_engine_lua_							= new_dflt_(Xog_ctg_itm_.Tid_xtns			, "xowa.xtns.scribunto.engine_lua_")
	, Key_xtns_scribunto_engine_luaj_							= new_dflt_(Xog_ctg_itm_.Tid_xtns			, "xowa.xtns.scribunto.engine_luaj_")
	;
	private static String new_dflt_(int ctg, String key)				{return new_text_(ctg, key, "app.api." + String_.Mid(key, 5) + ";");}		// 5 to skip "xowa."
	private static String new_page_(int ctg, String key, String page)	{return new_text_(ctg, key, "app.api.nav.goto(\"" + page + "\");");}
	private static String new_text_(int ctg, String key, String text)	{
		Xog_cmd_ctg ctg_itm = Xog_ctg_itm_.Ary[ctg];
		regy.Add(key, new Xog_cmd_itm(key, ctg_itm, text));
		return key;
	}
	public static int Regy_len() {return regy.Count();}
	public static Xog_cmd_itm Regy_get_at(int i) {return (Xog_cmd_itm)regy.Get_at(i);}
	public static Xog_cmd_itm Regy_get_or_null(String key) {return (Xog_cmd_itm)regy.Get_by(key);}
	public static void Regy_add(Xog_cmd_itm itm) {regy.Add(itm.Key(), itm);}
	public static final    byte[]
	  Msg_pre_api		= Bry_.new_a7("api-")
	, Msg_pre_ctg		= Bry_.new_a7("api.ctg-")
	, Msg_suf_name		= Bry_.new_a7("-name")
	, Msg_suf_tip		= Bry_.new_a7("-tip")
	, Msg_suf_letter	= Bry_.new_a7("-letter")
	, Msg_suf_image		= Bry_.new_a7("-image")
	;
}
