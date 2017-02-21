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
import gplx.xowa.apps.apis.xowa.apps.*;
public class Xoapi_app implements Gfo_invk {
	private Xog_win_itm win;
	public void Ctor_by_app(Xoae_app app) {
		fsys.Ctor_by_app(app);
	}
	public void Init_by_kit(Xoae_app app) {
		win = app.Gui_mgr().Browser_win();
	}
	public Xoapi_fsys		Fsys()		{return fsys;} private Xoapi_fsys fsys = new Xoapi_fsys();
	public void				Exit()		{win.App__exit();}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_exit)) 								this.Exit();
		else if	(ctx.Match(k, Invk_fsys)) 								return fsys;
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_exit = "exit", Invk_fsys = "fsys";
}
