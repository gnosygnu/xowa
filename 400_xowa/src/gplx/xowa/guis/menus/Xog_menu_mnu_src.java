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
package gplx.xowa.guis.menus; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.xowa.guis.cmds.*;
class Xog_menu_mgr_src {
	public static final String Browser_win 
	= Xog_menu_bldr.Instance
	. Add_grp_bgn(Xog_cmd_itm_.Key_gui_menus_group_file)
	.	Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_new_dflt__at_dflt__focus_y)
	.	Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_close_cur)
	.	Add_spr()
	.	Add_btn(Xog_cmd_itm_.Key_gui_page_view_save_as)
	.	Add_btn(Xog_cmd_itm_.Key_gui_page_view_print)
	.	Add_btn(Xog_cmd_itm_.Key_app_exit)
	. Add_grp_end()
	. Add_grp_bgn(Xog_cmd_itm_.Key_gui_menus_group_edit)
	.	Add_btn(Xog_cmd_itm_.Key_gui_page_selection_select_all)
	.	Add_btn(Xog_cmd_itm_.Key_gui_page_selection_copy)
	.	Add_spr()
	.	Add_btn(Xog_cmd_itm_.Key_gui_browser_find_show)
	. Add_grp_end()
	. Add_grp_bgn(Xog_cmd_itm_.Key_gui_menus_group_view)
	.	Add_btn(Xog_cmd_itm_.Key_gui_font_increase)
	.	Add_btn(Xog_cmd_itm_.Key_gui_font_decrease)
	.	Add_btn(Xog_cmd_itm_.Key_gui_font_reset)
	.	Add_spr()
	.	Add_btn(Xog_cmd_itm_.Key_gui_page_view_mode_read)
	.	Add_btn(Xog_cmd_itm_.Key_gui_page_view_mode_edit)
	.	Add_btn(Xog_cmd_itm_.Key_gui_page_view_mode_html)
	.	Add_spr()
	.	Add_btn(Xog_cmd_itm_.Key_gui_page_view_reload)
	.	Add_spr()
	.	Add_btn(Xog_cmd_itm_.Key_gui_browser_prog_log_show)
	. Add_grp_end()
	. Add_grp_bgn(Xog_cmd_itm_.Key_gui_menus_group_history)
	.	Add_btn(Xog_cmd_itm_.Key_nav_go_bwd)
	.	Add_btn(Xog_cmd_itm_.Key_nav_go_fwd)
	.	Add_spr()
	.	Add_btn(Xog_cmd_itm_.Key_usr_history_show)
	. Add_grp_end()
	. Add_grp_bgn(Xog_cmd_itm_.Key_gui_menus_group_bookmarks)
	.	Add_btn(Xog_cmd_itm_.Key_nav_wiki_main_page)
	.	Add_spr()
	.	Add_btn(Xog_cmd_itm_.Key_usr_bookmarks_add)
	.	Add_btn(Xog_cmd_itm_.Key_usr_bookmarks_show)
	. Add_grp_end()
	. Add_grp_bgn(Xog_cmd_itm_.Key_gui_menus_group_tools)
	.	Add_btn(Xog_cmd_itm_.Key_nav_cfg_main)
	.	Add_spr()
	.	Add_btn(Xog_cmd_itm_.Key_nav_setup_import_from_list)
	.	Add_btn(Xog_cmd_itm_.Key_nav_setup_import_from_script)
	.	Add_spr()
	.	Add_btn(Xog_cmd_itm_.Key_nav_setup_maintenance)
	.	Add_btn(Xog_cmd_itm_.Key_nav_setup_download)
	. Add_grp_end()
	. Add_grp_bgn(Xog_cmd_itm_.Key_gui_menus_group_help)
	.	Add_btn(Xog_cmd_itm_.Key_nav_help_help)
	.	Add_btn(Xog_cmd_itm_.Key_nav_help_xowa_main)
	.	Add_btn(Xog_cmd_itm_.Key_nav_help_change_log)
	.	Add_btn(Xog_cmd_itm_.Key_nav_help_diagnostics)
	.	Add_btn(Xog_cmd_itm_.Key_nav_cfg_menu)
	.	Add_spr()
	.	Add_grp_bgn(Xog_cmd_itm_.Key_gui_menus_group_system_data)
	.		Add_btn(Xog_cmd_itm_.Key_nav_system_data_log_session)
	.		Add_btn(Xog_cmd_itm_.Key_nav_system_data_cfg_app)
	.		Add_btn(Xog_cmd_itm_.Key_nav_system_data_cfg_lang)
	.		Add_btn(Xog_cmd_itm_.Key_nav_system_data_cfg_user)
	.		Add_btn(Xog_cmd_itm_.Key_nav_system_data_cfg_custom)
	.		Add_btn(Xog_cmd_itm_.Key_nav_system_data_usr_history)
	.	Add_grp_end()
	.	Add_spr()
	.	Add_btn(Xog_cmd_itm_.Key_nav_help_about)
	. Add_grp_end()
	. Gen_str();
	public static final String Html_page 
	= Xog_menu_bldr.Instance
	. Add_btn(Xog_cmd_itm_.Key_nav_go_bwd)
	. Add_btn(Xog_cmd_itm_.Key_nav_go_fwd)
	. Add_spr()
	. Add_btn(Xog_cmd_itm_.Key_gui_page_view_reload)
	. Add_spr()
	. Add_btn(Xog_cmd_itm_.Key_gui_page_selection_select_all)
	. Add_spr()
	. Add_btn(Xog_cmd_itm_.Key_gui_font_increase)
	. Add_btn(Xog_cmd_itm_.Key_gui_font_decrease)
	. Add_btn(Xog_cmd_itm_.Key_gui_font_reset)
	. Add_spr()
	. Add_grp_bgn(Xog_cmd_itm_.Key_gui_menus_group_file)
	.	Add_btn(Xog_cmd_itm_.Key_gui_page_view_save_as)
//		.	Add_btn(Xog_cmd_itm_.Key_gui_page_view_print)
	.	Add_btn(Xog_cmd_itm_.Key_app_exit)
	. Add_grp_end()
	. Add_grp_bgn(Xog_cmd_itm_.Key_gui_menus_group_tabs)
	.	Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_new_dflt__at_dflt__focus_y)
	.	Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_new_dupe__at_dflt__focus_y)
	.   Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_close_cur)
	.	Add_spr()
	.	Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_select_fwd)
	.	Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_select_bwd)
	.	Add_spr()
	.	Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_move_fwd)
	.	Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_move_bwd)
	.	Add_spr()
	.   Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_close_others)
	.   Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_close_to_bgn)
	.   Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_close_to_end)
	.	Add_spr()
	.	Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_close_undo)
	. Add_grp_end()
	. Add_grp_bgn(Xog_cmd_itm_.Key_gui_menus_group_view)
	.	Add_btn(Xog_cmd_itm_.Key_gui_page_view_mode_read)
	.	Add_btn(Xog_cmd_itm_.Key_gui_page_view_mode_edit)
	.	Add_btn(Xog_cmd_itm_.Key_gui_page_view_mode_html)
	.	Add_spr()
	.	Add_btn(Xog_cmd_itm_.Key_gui_browser_prog_log_show)
	. Add_grp_end()
	. Add_grp_bgn(Xog_cmd_itm_.Key_gui_menus_group_history)
	.	Add_btn(Xog_cmd_itm_.Key_usr_history_show)
	. Add_grp_end()
	. Add_grp_bgn(Xog_cmd_itm_.Key_gui_menus_group_bookmarks)
	.	Add_btn(Xog_cmd_itm_.Key_nav_wiki_main_page)
	.	Add_spr()
	.	Add_btn(Xog_cmd_itm_.Key_usr_bookmarks_add)
	.	Add_btn(Xog_cmd_itm_.Key_usr_bookmarks_show)
	. Add_grp_end()
	. Add_grp_bgn(Xog_cmd_itm_.Key_gui_menus_group_tools)
	.	Add_btn(Xog_cmd_itm_.Key_nav_cfg_main)
	.	Add_spr()
	.	Add_btn(Xog_cmd_itm_.Key_nav_setup_import_from_list)
	.	Add_btn(Xog_cmd_itm_.Key_nav_setup_import_from_script)
	.	Add_spr()
	.	Add_btn(Xog_cmd_itm_.Key_nav_setup_maintenance)
	.	Add_btn(Xog_cmd_itm_.Key_nav_setup_download)
	. Add_grp_end()
	. Add_grp_bgn(Xog_cmd_itm_.Key_gui_menus_group_help)
	.	Add_btn(Xog_cmd_itm_.Key_nav_help_help)
	.	Add_btn(Xog_cmd_itm_.Key_nav_help_xowa_main)
	.	Add_btn(Xog_cmd_itm_.Key_nav_help_change_log)
	.	Add_btn(Xog_cmd_itm_.Key_nav_help_diagnostics)
	.	Add_btn(Xog_cmd_itm_.Key_nav_cfg_menu)
	.	Add_spr()
	.	Add_btn(Xog_cmd_itm_.Key_nav_help_about)
	. Add_grp_end()
	. Gen_str();

	public static final String Html_link
	= Xog_menu_bldr.Instance
	. Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_new_link__at_dflt__focus_n)
	. Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_new_link__at_dflt__focus_y)
	. Add_spr()
	. Add_btn(Xog_cmd_itm_.Key_gui_page_selection_copy)
	. Add_btn(Xog_cmd_itm_.Key_gui_browser_find_show_by_paste)
	. Gen_str();
	public static final String Html_file
	= Xog_menu_bldr.Instance
	. Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_new_link__at_dflt__focus_n)
	. Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_new_link__at_dflt__focus_y)
	. Add_spr()
	. Add_btn(Xog_cmd_itm_.Key_gui_page_selection_save_file_as)
	. Gen_str();
	public static final String Tabs_btns
	= Xog_menu_bldr.Instance
	. Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_new_dflt__at_dflt__focus_y)
	. Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_new_dupe__at_dflt__focus_y)
	. Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_close_cur)
	. Add_spr()
	. Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_select_fwd)
	. Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_select_bwd)
	. Add_spr()
	. Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_move_fwd)
	. Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_move_bwd)
	. Add_spr()
	. Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_close_others)
	. Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_close_to_bgn)
	. Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_close_to_end)
	. Add_spr()
	. Add_btn(Xog_cmd_itm_.Key_gui_browser_tabs_close_undo)
	. Gen_str();		
	public static final String Prog 
	= Xog_menu_bldr.Instance
	. Add_btn(Xog_cmd_itm_.Key_gui_browser_prog_log_show)
	. Gen_str();
	public static final String Info = String_.Concat_lines_nl
	( ""
	);
}
class Xog_menu_bldr {
	private int indent = 0;
	private Bry_bfr bfr = Bry_bfr.reset_(0);
	public String Gen_str() {return bfr.To_str_and_clear();}
	private Xog_menu_bldr Indent_add() {indent += 2; return this;}
	private Xog_menu_bldr Indent_del() {indent -= 2; return this;}
	private void Indent() {
		if (indent > 0)
			bfr.Add_byte_repeat(Byte_ascii.Space, indent);
	}
	public Xog_menu_bldr Add_spr() {
		Indent();
		bfr.Add(Const_spr);
		return this;
	}
	public Xog_menu_bldr Add_grp_bgn(String key) {
		Indent();
		bfr.Add(Const_itm_grp_bgn_lhs);
		bfr.Add_str(key);
		bfr.Add(Const_itm_grp_bgn_rhs);
		Indent_add();
		return this;
	}
	public Xog_menu_bldr Add_grp_end() {
		Indent_del();
		Indent();
		bfr.Add(Const_itm_grp_end);
		return this;
	}
	public Xog_menu_bldr Add_btn(String key) {
		Indent();
		bfr.Add(Const_itm_btn_bgn_lhs);
		bfr.Add_str(key);
		bfr.Add(Const_itm_btn_bgn_rhs);
		return this;
	}
	private static final byte[]
	  Const_spr				= Bry_.new_a7("add_spr;\n")
	, Const_itm_btn_bgn_lhs	= Bry_.new_a7("add_btn_default('")
	, Const_itm_btn_bgn_rhs	= Bry_.new_a7("');\n")
	, Const_itm_grp_bgn_lhs	= Bry_.new_a7("add_grp_default('")
	, Const_itm_grp_bgn_rhs	= Bry_.new_a7("') {\n")
	, Const_itm_grp_end		= Bry_.new_a7("}\n")
	;
	public static final Xog_menu_bldr Instance = new Xog_menu_bldr(); Xog_menu_bldr() {}
}
