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
package gplx.xowa.apps.apis.xowa.addons; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.domains.crts.*;
import gplx.xowa.apps.apis.xowa.addons.searchs.*;
public class Xoapi_addon_search implements Gfo_invk {
	@gplx.Internal protected Xoapi_search_box		Search_box()	{return search_box;}	private final    Xoapi_search_box search_box = new Xoapi_search_box();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__search_box)) 					return search_box;
		else	return Gfo_invk_.Rv_unhandled;
	}
	private static final String Invk__search_box = "search_box";
}
class Xoapi_search_box implements Gfo_invk {
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		return null;
	}
}
