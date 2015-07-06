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
package gplx.xowa.apis.xowa.navs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apis.*; import gplx.xowa.apis.xowa.*;
import gplx.xowa.gui.views.*;
import gplx.xowa.html.hrefs.*;
public class Xoapi_wiki implements GfoInvkAble {
	private Xog_win_itm win;
	public void Init_by_kit(Xoae_app app) {
		win = app.Gui_mgr().Browser_win();
	}
	public void Random()		{win.Page__navigate_by_url_bar("Special:Random");}
	public void Sandbox()		{win.Page__navigate_by_url_bar("Project:Sandbox");}
	public void Main_page()		{
		win.Tab_mgr().Active_tab_assert();	// force an active tab in case all tabs are closed; needed for win.Active_page() below; DATE:2014-09-17
		win.Page__navigate_by_url_bar(win.Active_tab().Wiki().Domain_str() + Xoh_href_parser.Href_wiki_str);	// NOTE: add "/wiki/" to generate non-page like url;  EX: "home" -> "home/wiki/" which will be interpreted as a url, as opposed to "home" which will be intrepretted as page; DATE:2014-04-14
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_main_page)) 						this.Main_page();
		else if	(ctx.Match(k, Invk_random)) 						this.Random();
		else if	(ctx.Match(k, Invk_sandbox)) 						this.Sandbox();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_main_page = "main_page", Invk_random = "random", Invk_sandbox = "sandbox";
}
