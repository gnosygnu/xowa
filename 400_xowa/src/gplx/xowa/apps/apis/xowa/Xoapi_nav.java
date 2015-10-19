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
package gplx.xowa.apps.apis.xowa; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*;
import gplx.xowa.guis.views.*;
import gplx.xowa.apps.apis.xowa.navs.*;
public class Xoapi_nav implements GfoInvkAble {
	private Xog_win_itm win;
	public void Init_by_kit(Xoae_app app) {
		win = app.Gui_mgr().Browser_win();
		wiki.Init_by_kit(app);
	}
	public Xoapi_wiki Wiki()			{return wiki;} private Xoapi_wiki wiki = new Xoapi_wiki();
	public void Goto(String page)		{win.Page__navigate_by_url_bar(page);}
	public void Go_bwd()				{win.Page__navigate_by_history(Bool_.N);}
	public void Go_fwd()				{win.Page__navigate_by_history(Bool_.Y);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_go_bwd)) 							this.Go_bwd();
		else if	(ctx.Match(k, Invk_go_fwd)) 							this.Go_fwd();
		else if	(ctx.Match(k, Invk_goto)) 								this.Goto(m.ReadStr("v"));
		else if (ctx.Match(k, Invk_wiki)) 								return wiki;
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_go_bwd = "go_bwd", Invk_go_fwd = "go_fwd", Invk_goto = "goto", Invk_wiki = "wiki";
}
