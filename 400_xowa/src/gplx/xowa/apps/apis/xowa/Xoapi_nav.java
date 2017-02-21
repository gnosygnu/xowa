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
package gplx.xowa.apps.apis.xowa; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*;
import gplx.xowa.guis.views.*;
import gplx.xowa.apps.apis.xowa.navs.*;
public class Xoapi_nav implements Gfo_invk {
	private Xog_win_itm win;
	public void Init_by_kit(Xoae_app app) {
		win = app.Gui_mgr().Browser_win();
		wiki.Init_by_kit(app);
	}
	public Xoapi_nav_wiki Wiki()		{return wiki;} private Xoapi_nav_wiki wiki = new Xoapi_nav_wiki();
	public void Goto(String page)		{win.Page__navigate_by_url_bar(page);}
	public void Go_bwd()				{win.Page__navigate_by_history(Bool_.N);}
	public void Go_fwd()				{win.Page__navigate_by_history(Bool_.Y);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_go_bwd)) 							this.Go_bwd();
		else if	(ctx.Match(k, Invk_go_fwd)) 							this.Go_fwd();
		else if	(ctx.Match(k, Invk_goto)) 								this.Goto(m.ReadStr("v"));
		else if (ctx.Match(k, Invk_wiki)) 								return wiki;
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_go_bwd = "go_bwd", Invk_go_fwd = "go_fwd", Invk_goto = "goto", Invk_wiki = "wiki";
}
