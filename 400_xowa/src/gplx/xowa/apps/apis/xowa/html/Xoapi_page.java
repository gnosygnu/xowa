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
package gplx.xowa.apps.apis.xowa.html; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*;
public class Xoapi_page implements Gfo_invk {
	public void Ctor_by_app(Xoae_app app) {
		toggle_mgr.Ctor_by_app(app);
	}
	public void Init_by_app(Xoae_app app) {toggle_mgr.Init_by_app(app);}
	public Xoapi_toggle_mgr Toggle_mgr()	{return toggle_mgr;} private final    Xoapi_toggle_mgr toggle_mgr = new Xoapi_toggle_mgr();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_toggles)) 						return toggle_mgr;
		else	return Gfo_invk_.Rv_unhandled;
	}
	private static final String Invk_toggles = "toggles";
}
