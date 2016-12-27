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
package gplx.xowa.guis.views; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.xowa.addons.apps.cfgs.*;
public class Xog_startup_tabs_ {
	public static String Manual = null;	// note should only be set once, at startup;
	public static void Shutdown(Xoae_app app) {
		if (String_.Eq(app.Cfg().Get_str_app_or(Cfg__tabs_type, Opt__tabs_type__previous), Opt__tabs_type__previous)) {
			app.Cfg().Set_str_app(Cfg__prev_list	, Calc_previous_tabs(app.Gui_mgr().Browser_win().Tab_mgr()));
			app.Cfg().Set_int_app(Cfg__prev_selected, app.Gui_mgr().Browser_win().Tab_mgr().Active_tab().Tab_idx());
		}
	}
	private static String Calc_previous_tabs(gplx.xowa.guis.views.Xog_tab_mgr tab_mgr) {
		Bry_bfr bfr = Bry_bfr_.New();
		int len = tab_mgr.Tabs_len();
		for (int i = 0; i < len; ++i) {
			if (i != 0) bfr.Add_byte_nl();
			gplx.xowa.guis.views.Xog_tab_itm tab = tab_mgr.Tabs_get_at(i);
			bfr.Add_str_u8(tab.Page().Url().To_str());
		}
		return bfr.To_str_and_clear();
	}
	public static void Select_startup_tab(Xoae_app app) {
		if (String_.Eq(app.Cfg().Get_str_app_or(Cfg__tabs_type, Opt__tabs_type__previous), Opt__tabs_type__previous)) {
			int selected_tab = app.Cfg().Get_int_app_or(Cfg__prev_selected, -1);
			if (selected_tab != -1) {
				app.Gui_mgr().Browser_win().Tab_mgr().Tabs_select_by_idx(selected_tab);
			}
		}
	}
	public static final String Page_xowa = "home/wiki/Main_Page";
	public static String[] Calc_startup_strs(Xoae_app app) {
		List_adp rv = List_adp_.New();
		Xocfg_mgr cfg = app.Cfg();
		String xowa_home = Page_xowa;
		String type = cfg.Get_str_app_or(Cfg__tabs_type, Opt__tabs_type__previous);
		if (Manual == null) {
			if		(String_.Eq(type, "blank"))						{rv.Add(gplx.xowa.specials.Xow_special_meta_.Itm__default_tab.Ttl_str());}
			else if (String_.Eq(type, "xowa"))						{rv.Add(xowa_home);}
			else if (String_.Eq(type, "custom"))					{Add_pages_to_list(rv, cfg.Get_str_app_or(Cfg__tabs_custom, ""));}
			else if (String_.Eq(type, Opt__tabs_type__previous))	{Add_pages_to_list(rv, cfg.Get_str_app_or(Cfg__prev_list, ""));}
			else													{throw Err_.new_unhandled(type);}
		}
		else
			rv.Add(Manual);
		Add_xowa_home_if_new_version(rv, app, xowa_home);
		return rv.To_str_ary();
	}
	private static void Add_pages_to_list(List_adp list, String s) {
		if (String_.Len_eq_0(s)) return;
		String[] ary = String_.SplitLines_nl(String_.Trim(s));
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			String itm = ary[i];
			if (String_.Len_eq_0(itm)) continue;
			list.Add(itm);
		}
	}
	public static String Version_previous(Xoa_app app) {return app.Cfg().Get_str_app_or(Cfg__prev_version, "");}
	private static void Add_xowa_home_if_new_version(List_adp rv, Xoae_app app, String xowa_home) {
		if (gplx.xowa.apps.versions.Xoa_version_.Compare(Version_previous(app), Xoa_app_.Version) == CompareAble_.Less) {
			boolean xowa_home_exists = false;
			int len = rv.Count();
			for (int i = 0; i < len; ++i) {
				String itm = (String)rv.Get_at(i);
				if (String_.Eq(itm, xowa_home)) {
					xowa_home_exists = true;
					break;
				}
			}
			if (!xowa_home_exists) {
				rv.Add(xowa_home);
				app.Cfg().Set_int_app(Cfg__prev_selected, rv.Len());
			}
		}
	}
	private static final String
	  Cfg__tabs_type				= "xowa.app.startup.tabs.type"
	, Cfg__tabs_custom				= "xowa.app.startup.tabs.custom"
	, Cfg__prev_list				= "xowa.app.startup.tabs.previous_list"
	, Cfg__prev_selected			= "xowa.app.startup.tabs.previous_selected"
	, Opt__tabs_type__previous		= "previous"
	;
	public static final String Cfg__prev_version				= "xowa.app.previous_version";
}
