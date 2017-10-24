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
package gplx.xowa.apps; import gplx.*; import gplx.xowa.*;
import gplx.xowa.guis.views.*;
public class Xoa_cur implements Gfo_invk {
	public Xoa_cur(Xoae_app app) {this.app = app;} private Xoae_app app;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_wiki)) {
			Xog_win_itm win = app.Gui_mgr().Browser_win();
			return win.Active_tab() == null ? Gfo_invk_.Noop : win.Active_page().Wikie(); // null check when called from mass html gen; DATE:2014-06-04
		}
		else if	(ctx.Match(k, Invk_win))			return app.Gui_mgr().Browser_win();
		else if	(ctx.Match(k, Invk_user))			return app.Usere();
		else return Gfo_invk_.Rv_unhandled;
	}	private static final String Invk_wiki = "wiki", Invk_win = "win", Invk_user = "user";
}
