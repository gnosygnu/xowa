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
package gplx.xowa.htmls.heads; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.xowa.guis.*;
import gplx.xowa.apps.apis.xowa.html.modules.*;
import gplx.xowa.htmls.modules.popups.*; import gplx.xowa.addons.apps.cfgs.*;
public class Xoh_head_itm__popups extends Xoh_head_itm__base {
	@Override public byte[] Key() {return Xoh_head_itm_.Key__popups;}
	@Override public int Flags() {return Flag__css_include | Flag__js_head_global | Flag__js_tail_script;}
	public boolean Bind_hover_area() {return bind_hover_area;} public void Bind_hover_area_(boolean v) {bind_hover_area = v;} private boolean bind_hover_area;
	@Override public void Clear() {this.Enabled_n_(); bind_hover_area = false;}
	@Override public void Write_css_include(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		if (Css_url == null) Css_url = app.Fsys_mgr().Bin_any_dir().GenSubFil_nest("xowa", "html", "res", "src", "xowa", "popups", "popups.css").To_http_file_bry();
		wtr.Write_css_include(Css_url);
	}
	@Override public void Write_js_head_global(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		Xocfg_mgr cfg_mgr = app.Cfg();
		wtr.Write_js_global_ini_atr_val(Key_win_show_delay			, cfg_mgr.Get_int_by_wiki_or(wiki, Xow_popup_mgr.Cfg__win_show_delay, 0));
		wtr.Write_js_global_ini_atr_val(Key_win_hide_delay			, cfg_mgr.Get_int_by_wiki_or(wiki, Xow_popup_mgr.Cfg__win_hide_delay, 0));
		wtr.Write_js_global_ini_atr_val(Key_win_max_w				, cfg_mgr.Get_int_by_wiki_or(wiki, Xow_popup_mgr.Cfg__win_max_w, 0));
		wtr.Write_js_global_ini_atr_val(Key_win_max_h				, cfg_mgr.Get_int_by_wiki_or(wiki, Xow_popup_mgr.Cfg__win_max_h, 0));
		wtr.Write_js_global_ini_atr_val(Key_win_show_all_max_w		, cfg_mgr.Get_int_by_wiki_or(wiki, Xow_popup_mgr.Cfg__win_show_all_max_w, 0));
		wtr.Write_js_global_ini_atr_val(Key_win_bind_focus_blur		, cfg_mgr.Get_bool_by_wiki_or(wiki, Xow_popup_mgr.Cfg__win_bind_focus_blur, false));
		wtr.Write_js_global_ini_atr_val(Key_win_bind_hover_area		, bind_hover_area);
	}
	@Override public void Write_js_tail_script(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		wtr.Write_js_line(Jquery_init);	// NOTE: must assert that jquery is init'd, else popup.js will not compile after going back / forward; DATE:2014-09-10
		wtr.Write_js_tail_load_lib(app.Fsys_mgr().Bin_any_dir().GenSubFil_nest("xowa", "html", "res", "src", "xowa", "popups", "popups.js"));
	}	public static final    byte[] Jquery_init = Bry_.new_a7("xowa.js.jquery.init();"), Mw_init = Bry_.new_a7("xowa.js.mediaWiki.init();");
	private static byte[] Css_url;
	private static final    byte[]
	  Key_win_show_delay			= Bry_.new_a7("popups-win-show_delay")
	, Key_win_hide_delay			= Bry_.new_a7("popups-win-hide_delay")
	, Key_win_max_w					= Bry_.new_a7("popups-win-max_w")
	, Key_win_max_h					= Bry_.new_a7("popups-win-max_h")
	, Key_win_show_all_max_w		= Bry_.new_a7("popups-win-show_all_max_w")
	, Key_win_bind_focus_blur		= Bry_.new_a7("popups-win-bind_focus_blur")
	, Key_win_bind_hover_area		= Bry_.new_a7("popups-win-bind_hover_area")
	;
}
