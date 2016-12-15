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
package gplx.xowa.apps.apis; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.xowa.apps.apis.xowa.*; import gplx.xowa.guis.cmds.*;
public class Xoapi_root implements Gfo_invk {
	private Xoae_app app;
	public Xoapi_root(Xoae_app app) {
		app_api.Ctor_by_app(app);
		usr_api.Ctor_by_app(app);
		bldr_api.Ctor_by_app(app);
		html_api.Ctor_by_app(app);
	}
	public void Init_by_kit(Xoae_app app) {
		this.app = app;
		app_api.Init_by_kit(app);
		nav_api.Init_by_kit(app);
		gui_api.Init_by_kit(app);
		html_api.Init_by_kit(app);
		usr_api.Init_by_kit(app);
		xtns_api.Init_by_kit(app);
	}
	public void Init_by_app(Xoae_app app) {
		Io_url img_dir = app.Fsys_mgr().Bin_xowa_file_dir().GenSubDir_nest("app.general");
		html_api.Page().Toggle_mgr().Img_dir_(img_dir);
		usr_api.Init_by_app(app);
	}
	public Xoapi_addon		Addon()		{return addon_api;} private final    Xoapi_addon addon_api = new Xoapi_addon();
	public Xoapi_app		App()		{return app_api;} private final    Xoapi_app app_api = new Xoapi_app();
	public Xoapi_nav		Nav()		{return nav_api;} private final    Xoapi_nav nav_api = new Xoapi_nav();
	public Xoapi_gui		Gui()		{return gui_api;} private final    Xoapi_gui gui_api = new Xoapi_gui();
	public Xoapi_html		Html()		{return html_api;} private final    Xoapi_html html_api = new Xoapi_html();
	public Xoapi_bldr		Bldr()		{return bldr_api;} private final    Xoapi_bldr bldr_api = new Xoapi_bldr();
	public Xoapi_usr		Usr()		{return usr_api;} private final    Xoapi_usr usr_api = new Xoapi_usr();
	public Xoapi_special	Special()	{return special_api;} private final    Xoapi_special special_api = new Xoapi_special();
	public Xoapi_xtns		Xtns()		{return xtns_api;} private final    Xoapi_xtns xtns_api = new Xoapi_xtns();
	public Xoapi_app_wikis	Wikis()		{return app_wikis;} private final    Xoapi_app_wikis app_wikis = new Xoapi_app_wikis();
	public String			Test_str() {return test_str;} public void Test_str_(String v) {test_str = v;} private String test_str;	// TEST
	private void Exec(String key) {
		Xog_cmd_itm cmd_itm = app.Gui_mgr().Cmd_mgr().Get_or_null(key);
		if (cmd_itm == null) app.Usr_dlg().Warn_many("", "", "could not find cmd; key=~{0}", key);
		app.Gfs_mgr().Run_str_for(app, cmd_itm.Cmd());
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_app)) 		return app_api;
		else if	(ctx.Match(k, Invk_addon)) 		return addon_api;
		else if	(ctx.Match(k, Invk_bldr)) 		return bldr_api;
		else if	(ctx.Match(k, Invk_nav)) 		return nav_api;
		else if	(ctx.Match(k, Invk_gui)) 		return gui_api;
		else if	(ctx.Match(k, Invk_html)) 		return html_api;
		else if	(ctx.Match(k, Invk_usr)) 		return usr_api;
		else if	(ctx.Match(k, Invk_special)) 	return special_api;
		else if	(ctx.Match(k, Invk_xtns)) 		return xtns_api;
		else if	(ctx.Match(k, Invk_exec)) 		Exec(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_wikis)) 		return app_wikis;
		else if	(ctx.Match(k, Invk_test_str)) 	return test_str;
		else if	(ctx.Match(k, Invk_test_str_)) 	test_str = m.ReadStr("v");
		return this;
	}
	private static final String 
	  Invk_exec = "exec"
	, Invk_app = "app", Invk_addon = "addon"
	, Invk_bldr = "bldr", Invk_nav = "nav", Invk_gui = "gui", Invk_html = "html", Invk_usr = "usr", Invk_special = "special", Invk_xtns = "xtns"
	, Invk_test_str = "test_str", Invk_test_str_ = "test_str_"
	, Invk_wikis = "wikis"
	;
}
