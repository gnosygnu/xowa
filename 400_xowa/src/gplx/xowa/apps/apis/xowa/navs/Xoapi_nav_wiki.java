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
package gplx.xowa.apps.apis.xowa.navs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*;
import gplx.xowa.guis.views.*;
import gplx.xowa.htmls.hrefs.*;
public class Xoapi_nav_wiki implements Gfo_invk {
	private Xog_win_itm win;
	public void Init_by_kit(Xoae_app app) {
		win = app.Gui_mgr().Browser_win();
	}
	public void Main_page()		{
		win.Tab_mgr().Active_tab_assert();	// force an active tab in case all tabs are closed; needed for win.Active_page() below; DATE:2014-09-17
		win.Page__navigate_by_url_bar(win.Active_tab().Wiki().Domain_str() + Xoh_href_.Str__wiki);	// NOTE: add "/wiki/" to generate non-page like url;  EX: "home" -> "home/wiki/" which will be interpreted as a url, as opposed to "home" which will be intrepretted as page; DATE:2014-04-14
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, "main_page")) 						this.Main_page();
		else if	(ctx.Match(k, "random")) 							win.Page__navigate_by_url_bar("Special:Random");
		else if	(ctx.Match(k, "sandbox")) 							win.Page__navigate_by_url_bar("Project:Sandbox");
		else if	(ctx.Match(k, "allpages")) 							win.Page__navigate_by_url_bar("Special:AllPages?from=!"); // NOTE: for main_menu, default to ! else empty page
		else if	(ctx.Match(k, "search_title")) 						win.Page__navigate_by_url_bar("Special:Search?fulltext=y");
		else if	(ctx.Match(k, "search_full")) 						win.Page__navigate_by_url_bar("Special:XowaSearch");
		else if	(ctx.Match(k, "search_per_cfg")) 					win.Page__navigate_by_url_bar(win.Gui_mgr().Win_cfg().Search_url());
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
}
