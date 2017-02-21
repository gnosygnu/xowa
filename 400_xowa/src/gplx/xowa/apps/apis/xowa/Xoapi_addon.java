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
import gplx.xowa.apps.apis.xowa.addons.*;
public class Xoapi_addon implements Gfo_invk {
	public void Ctor_by_app(Xoa_app app) {}
	public Xoapi_addon_search		Search()	{return search;}	private final    Xoapi_addon_search search = new Xoapi_addon_search();
	public Xoapi_addon_bldr			Bldr()		{return bldr;}		private final    Xoapi_addon_bldr bldr = new Xoapi_addon_bldr();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__search)) 								return search;
		else if	(ctx.Match(k, Invk__bldr)) 									return bldr;
		else	return Gfo_invk_.Rv_unhandled;
	}
	private static final String Invk__search = "search", Invk__bldr = "bldr";
}
