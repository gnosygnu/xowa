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
package gplx.xowa.apps.apis.xowa.usrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*;
import gplx.xowa.guis.views.*;
public class Xoapi_history implements Gfo_invk {
	private Xoae_app app; private Xog_win_itm win;
	public void Ctor_by_app(Xoae_app app) {this.app = app;}
	public void Init_by_kit(Xoae_app app) {this.win = app.Gui_mgr().Browser_win();}
	public void Goto_recent()		{win.Page__navigate_by_url_bar(app.Usere().History_mgr().Get_at_last());}
	public void Show()				{win.Page__navigate_by_url_bar("home/wiki/Special:XowaPageHistory");}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_goto_recent)) 						this.Goto_recent();
		else if	(ctx.Match(k, Invk_show)) 								this.Show();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_goto_recent = "goto_recent", Invk_show = "show";
}
