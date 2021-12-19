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
package gplx.xowa.guis.views;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.Xoa_app;
import gplx.xowa.Xoa_app_;
import gplx.xowa.Xoae_app;
import gplx.xowa.addons.apps.cfgs.Xocfg_mgr;
import gplx.xowa.addons.apps.updates.specials.Xoa_update_special;
public class Xog_startup_tabs {
	private String type, custom_list, prev_list, prev_version, curr_version;
	private boolean show_app_update;
	public int Startup_idx() {return startup_idx;} private int startup_idx;
	public String[] Startup_urls() {return startup_urls;} private String[] startup_urls = StringUtl.AryEmpty;
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
			if		(StringUtl.Eq(type, "blank"))						list.Add(gplx.xowa.specials.Xow_special_meta_.Itm__default_tab.Ttl_str());
			else if (StringUtl.Eq(type, "xowa"))						list.Add(Url__home_main);
			else if (StringUtl.Eq(type, "custom"))					Parse_ary(list, custom_list);
			else if (StringUtl.Eq(type, Opt__startup_type__previous))	Parse_ary(list, prev_list);
			else													throw ErrUtl.NewUnhandled(type);
		}
		else
			list.Add(Manual);

		// if new version, add home/wiki/Main_Page
		if (gplx.xowa.apps.versions.Xoa_version_.Compare(prev_version, curr_version) == CompareAbleUtl.Less) {
			startup_idx = Add_if_absent(list, Url__home_main);
		}

		// if show_app_update, add page
		if (show_app_update) {
			startup_idx = Add_if_absent(list, Xoa_update_special.Prototype.Special__meta().Url__home());
		}

		// generate urls
		startup_urls = (String[])list.ToAryAndClear(String.class);

		// do bounds check
		if (startup_idx < 0 || startup_idx >= startup_urls.length)
			startup_idx = startup_urls.length - 1;
		return this;
	}
	private static int Add_if_absent(List_adp list, String page) {
		// check list for page
		int len = list.Len();
		for (int i = 0; i < len; i++) {
			String itm = (String)list.GetAt(i);

			// page found; return its index
			if (StringUtl.Eq(itm, page)) {
				return i;
			}
		}

		// add if not found
		list.Add(page);
		return list.Len();
	}
	private static void Parse_ary(List_adp list, String s) {
		if (StringUtl.IsNullOrEmpty(s)) return;
		String[] ary = StringUtl.SplitLinesNl(StringUtl.Trim(s));
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			String itm = ary[i];
			if (StringUtl.IsNullOrEmpty(itm)) continue;
			list.Add(itm);
		}
	}
	public static String Version_previous(Xoa_app app) {return app.Cfg().Get_str_app_or(Cfg__prev_version, "");}
	public static void Shutdown(Xoae_app app) {
		app.Cfg().Set_str_app(Cfg__prev_version, Xoa_app_.Version);
		if (StringUtl.Eq(app.Cfg().Get_str_app_or(Cfg__startup_type, Opt__startup_type__previous), Opt__startup_type__previous)) {
			app.Cfg().Set_str_app(Cfg__prev_list	, Calc_previous_tabs(app.Gui_mgr().Browser_win().Tab_mgr()));

			// save prev_selected
			int prev_selected = app.Gui_mgr().Browser_win().Tab_mgr().Tabs_len() == 0 // must check for 0 tabs, else null ref
				? -1
				: app.Gui_mgr().Browser_win().Tab_mgr().Active_tab().Tab_idx();
			app.Cfg().Set_int_app(Cfg__prev_selected, prev_selected);
		}
	}
	private static String Calc_previous_tabs(gplx.xowa.guis.views.Xog_tab_mgr tab_mgr) {
		BryWtr bfr = BryWtr.New();
		int len = tab_mgr.Tabs_len();
		for (int i = 0; i < len; ++i) {
			if (i != 0) bfr.AddByteNl();
			gplx.xowa.guis.views.Xog_tab_itm tab = tab_mgr.Tabs_get_at(i);
			bfr.AddStrU8(tab.Page().Url().To_str());
		}
		return bfr.ToStrAndClear();
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
