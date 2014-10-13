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
package gplx.xowa.html.modules; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import gplx.xowa.gui.*;
import gplx.xowa.apis.xowa.html.modules.*;
public class Xoh_module_itm__popups implements Xoh_module_itm {
	public boolean Enabled() {return enabled;} public void Enabled_y_() {enabled = true;} public void Enabled_(boolean v) {enabled = v;} private boolean enabled;
	public boolean Bind_hover_area() {return bind_hover_area;} public void Bind_hover_area_(boolean v) {bind_hover_area = v;} private boolean bind_hover_area;
	public void Clear() {enabled = false;}
	public byte[] Key() {return Key_const;} private static final byte[] Key_const = Bry_.new_ascii_("popups");
	public void Write_css_script(Xoa_app app, Xow_wiki wiki, Xoa_page page, Xoh_module_wtr wtr) {}
	public void Write_js_include(Xoa_app app, Xow_wiki wiki, Xoa_page page, Xoh_module_wtr wtr) {}
	public void Write_css_include(Xoa_app app, Xow_wiki wiki, Xoa_page page, Xoh_module_wtr wtr) {
		if (!enabled) return;
		if (Css_url == null) Css_url = app.Fsys_mgr().Bin_any_dir().GenSubFil_nest("xowa", "html", "res", "src", "xowa", "popups", "popups.css").To_http_file_bry();
		wtr.Write_css_include(Css_url);
	}
	public void Write_js_head_script(Xoa_app app, Xow_wiki wiki, Xoa_page page, Xoh_module_wtr wtr) {}
	public void Write_js_tail_script(Xoa_app app, Xow_wiki wiki, Xoa_page page, Xoh_module_wtr wtr) {
		if (!enabled) return;
		wtr.Write_js_line(Jquery_init);	// NOTE: must assert that jquery is init'd, else popup.js will not compile after going back / forward; DATE:2014-09-10
		wtr.Write_js_tail_load_lib(app.Fsys_mgr().Bin_any_dir().GenSubFil_nest("xowa", "html", "res", "src", "xowa", "popups", "popups.js"));
	}	public static final byte[] Jquery_init = Bry_.new_ascii_("xowa.js.jquery.init();"), Mw_init = Bry_.new_ascii_("xowa.js.mediaWiki.init();");
	public void Write_js_head_global(Xoa_app app, Xow_wiki wiki, Xoa_page page, Xoh_module_wtr wtr) {
		if (!enabled) return;
		Xoapi_popups api_popups = app.Api_root().Html().Modules().Popups();
		wtr.Write_js_global_ini_atr_val(Key_win_show_delay			, api_popups.Win_show_delay());
		wtr.Write_js_global_ini_atr_val(Key_win_hide_delay			, api_popups.Win_hide_delay());
		wtr.Write_js_global_ini_atr_val(Key_win_max_w				, api_popups.Win_max_w());
		wtr.Write_js_global_ini_atr_val(Key_win_max_h				, api_popups.Win_max_h());
		wtr.Write_js_global_ini_atr_val(Key_win_show_all_max_w		, api_popups.Win_show_all_max_w());
		wtr.Write_js_global_ini_atr_val(Key_win_bind_focus_blur		, api_popups.Win_bind_focus_blur());
		wtr.Write_js_global_ini_atr_val(Key_win_bind_hover_area		, bind_hover_area);
	}
	private static byte[] Css_url;
	private static final byte[]
	  Key_win_show_delay			= Bry_.new_ascii_("popups-win-show_delay")
	, Key_win_hide_delay			= Bry_.new_ascii_("popups-win-hide_delay")
	, Key_win_max_w					= Bry_.new_ascii_("popups-win-max_w")
	, Key_win_max_h					= Bry_.new_ascii_("popups-win-max_h")
	, Key_win_show_all_max_w		= Bry_.new_ascii_("popups-win-show_all_max_w")
	, Key_win_bind_focus_blur		= Bry_.new_ascii_("popups-win-bind_focus_blur")
	, Key_win_bind_hover_area		= Bry_.new_ascii_("popups-win-bind_hover_area")
	;
}
