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
import gplx.xowa.apps.apis.xowa.usrs.*;
public class Xoapi_usr implements Gfo_invk {
	public void Ctor_by_app(Xoae_app app) {
		bookmarks.Ctor_by_app(app);
		history.Ctor_by_app(app);
	}
	public void Init_by_app(Xoa_app app) {
	}
	public void Init_by_kit(Xoae_app app) {
		bookmarks.Init_by_kit(app);
		history.Init_by_kit(app);
	}
	public Xoapi_bookmarks	Bookmarks() {return bookmarks;} private final    Xoapi_bookmarks bookmarks = new Xoapi_bookmarks();
	public Xoapi_history	History()	{return history;}	private final    Xoapi_history history = new Xoapi_history();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_bookmarks)) 							return bookmarks;
		else if	(ctx.Match(k, Invk_history)) 							return history;
		else	return Gfo_invk_.Rv_unhandled;
	}
	private static final String Invk_bookmarks = "bookmarks", Invk_history = "history";
}
