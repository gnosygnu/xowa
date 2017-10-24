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
package gplx.xowa.guis.views; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.updates.specials.*;
public class Xog_startup_tabs {
	private String type, custom_list, prev_list, prev_version, curr_version;
	private boolean show_app_update;
	public int Startup_idx() {return startup_idx;} private int startup_idx;
	public String[] Startup_urls() {return startup_urls;} private String[] startup_urls = String_.Ary_empty;
	public Xog_startup_tabs Init_by_app(Xoae_app app) {
		Xocfg_mgr cfg = app.Cfg();
		this.type = cfg.Get_str_app_or(Cfg__startup_type, Opt__startup_type__previous);
		this.custom_list = cfg.Get_str_app_or(Cfg__custom_list, "");
		this.prev_list = cfg.Get_str_app_or(Cfg__prev_list, "");
		this.prev_version = cfg.Get_str_app_or(Cfg__prev_version, "");
		this.curr_version = Xoa_app_.Version;
		this.startup_idx = cfg.Get_int_app_or(Cfg__prev_selected, -1);
		this.show_app_update = gplx.xowa.addons.apps.updates.Xoa_update_startup.Show_at_startup(app);
		return this;
	}
	public Xog_startup_tabs Calc() {
		List_adp list = List_adp_.New();	// NOTE: do not change to hash; duplicate urls are possible

		// process main types
		if (Manual == null) {
			if		(String_.Eq(type, "blank"))						list.Add(gplx.xowa.specials.Xow_special_meta_.Itm__default_tab.Ttl_str());
			else if (String_.Eq(type, "xowa"))						list.Add(Url__home_main);
			else if (String_.Eq(type, "custom"))					Parse_ary(list, custom_list);
			else if (String_.Eq(type, Opt__startup_type__previous))	Parse_ary(list, prev_list);
			else													throw Err_.new_unhandled(type);
		}
		else
			list.Add(Manual);

		// if new version, add home/wiki/Main_Page
		if (gplx.xowa.apps.versions.Xoa_version_.Compare(prev_version, curr_version) == CompareAble_.Less) {
			startup_idx = Add_if_absent(list, Url__home_main);
		}

		// if show_app_update, add page
		if (show_app_update) {
			startup_idx = Add_if_absent(list, Xoa_update_special.Prototype.Special__meta().Url__home());
		}

		// generate urls
		startup_urls = (String[])list.To_ary_and_clear(String.class);

		// do bounds check
		if (startup_idx < 0 || startup_idx >= startup_urls.length)
			startup_idx = startup_urls.length - 1;
		return this;
	}
	private static int Add_if_absent(List_adp list, String page) {
		// check list for page
		int len = list.Len();
		for (int i = 0; i < len; i++) {
			String itm = (String)list.Get_at(i);

			// page found; return its index
			if (String_.Eq(itm, page)) {
				return i;
			}
		}

		// add if not found
		list.Add(page);
		return list.Len();
	}
	private static void Parse_ary(List_adp list, String s) {
		if (String_.Len_eq_0(s)) return;
		String[] ary = String_.SplitLines_nl(String_.Trim(s));
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			String itm = ary[i];
			if (String_.Len_eq_0(itm)) continue;
			list.Add(itm);
		}
	}
	public static String Version_previous(Xoa_app app) {return app.Cfg().Get_str_app_or(Cfg__prev_version, "");}
	public static void Shutdown(Xoae_app app) {
		app.Cfg().Set_str_app(Cfg__prev_version, Xoa_app_.Version);
		if (String_.Eq(app.Cfg().Get_str_app_or(Cfg__startup_type, Opt__startup_type__previous), Opt__startup_type__previous)) {
			app.Cfg().Set_str_app(Cfg__prev_list	, Calc_previous_tabs(app.Gui_mgr().Browser_win().Tab_mgr()));

			// save prev_selected
			int prev_selected = app.Gui_mgr().Browser_win().Tab_mgr().Tabs_len() == 0 // must check for 0 tabs, else null ref
				? -1
				: app.Gui_mgr().Browser_win().Tab_mgr().Active_tab().Tab_idx();
			app.Cfg().Set_int_app(Cfg__prev_selected, prev_selected);
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
	public static String Manual = null;	// note set by command-line at startup;
	private static final String
	  Cfg__startup_type							= "xowa.app.startup.tabs.type"
	, Cfg__custom_list							= "xowa.app.startup.tabs.custom"
	, Cfg__prev_list							= "xowa.app.startup.tabs.previous_list"
	, Cfg__prev_selected						= "xowa.app.startup.tabs.previous_selected"
	, Cfg__prev_version							= "xowa.app.setup.previous_version"
	, Opt__startup_type__previous				= "previous";
	public static final String
	  Url__home_main							= "home/wiki/Main_Page"
	;
}
