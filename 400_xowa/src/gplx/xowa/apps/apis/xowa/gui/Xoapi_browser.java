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
package gplx.xowa.apps.apis.xowa.gui; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*;
import gplx.xowa.apps.apis.xowa.gui.browsers.*;
public class Xoapi_browser implements Gfo_invk {
	private Xoae_app app;
	public void Init_by_kit(Xoae_app app) {
		this.app = app;
		url.Init_by_kit(app);
		tabs.Init_by_kit(app);
		html.Init_by_kit(app);
		search.Init_by_kit(app);
		allpages.Init_by_kit(app);
		find.Init_by_kit(app);
		prog.Init_by_kit(app);
		info.Init_by_kit(app);
		prog_log.Init_by_kit(app);
	}
	public Xoapi_url		Url()		{return url;} private Xoapi_url url = new Xoapi_url();
	public Xoapi_tabs		Tabs()		{return tabs;} private Xoapi_tabs tabs = new Xoapi_tabs();
	public Xoapi_html_box	Html()		{return html;} private Xoapi_html_box html = new Xoapi_html_box();
	public Xoapi_search		Search()	{return search;} private Xoapi_search search = new Xoapi_search();
	public Xoapi_allpages	Allpages()	{return allpages;} private Xoapi_allpages allpages = new Xoapi_allpages();
	public Xoapi_find		Find()		{return find;} private Xoapi_find find = new Xoapi_find();
	public Xoapi_prog		Prog()		{return prog;} private Xoapi_prog prog = new Xoapi_prog();
	public Xoapi_info		Info()		{return info;} private Xoapi_info info = new Xoapi_info();
	public Xoapi_prog_log	Prog_log()	{return prog_log;} private Xoapi_prog_log prog_log = new Xoapi_prog_log();
	public void Nightmode_toggle() {
		// toggle nightmode
		boolean val = !app.Gui_mgr().Nightmode_mgr().Enabled();
		app.Gui_mgr().Nightmode_mgr().Enabled_(val);
		app.Cfg().Set_bool_app(gplx.xowa.guis.views.nightmodes.Xog_nightmode_mgr.Cfg__enabled, val);
		this.Nightmode_reload();
	}
	public void Nightmode_reload() {
		// toggle nightmode for all other tabs
		gplx.xowa.guis.views.Xog_tab_mgr tab_mgr = app.Gui_mgr().Browser_win().Tab_mgr();
		int len = tab_mgr.Tabs_len();
		for (int i = 0; i < len; i++) {
			app.Gui_mgr().Browser_win().Page__refresh(tab_mgr.Tabs_get_at(i));
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_url)) 						return url;
		else if	(ctx.Match(k, Invk_tabs)) 						return tabs;
		else if	(ctx.Match(k, Invk_html)) 						return html;
		else if	(ctx.Match(k, Invk_search)) 					return search;
		else if	(ctx.Match(k, "allpages")) 						return allpages;
		else if	(ctx.Match(k, Invk_find)) 						return find;
		else if	(ctx.Match(k, Invk_prog)) 						return prog;
		else if	(ctx.Match(k, Invk_info)) 						return info;
		else if	(ctx.Match(k, Invk_prog_log)) 					return prog_log;
		else if	(ctx.Match(k, Invk__nightmode_toggle)) 			Nightmode_toggle();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String 
	  Invk_url = "url", Invk_tabs = "tabs", Invk_html = "html", Invk_search = "search"
	, Invk_find = "find", Invk_prog = "prog", Invk_info = "info", Invk_prog_log = "prog_log"
	, Invk__nightmode_toggle = "nightmode_toggle"
	;
}
