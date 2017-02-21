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
import gplx.xowa.apps.apis.xowa.xtns.*;
public class Xoapi_xtns implements Gfo_invk {
	public void Init_by_kit(Xoae_app app) {
		wikibase.Init_by_app(app);
	}
	public Xoapi_wikibase		Wikibase()		{return wikibase;}  private final    Xoapi_wikibase  wikibase = new Xoapi_wikibase();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_wikibase))	 			return wikibase;
		else	return Gfo_invk_.Rv_unhandled;
	}
	private static final String Invk_wikibase  = "wikibase";
}
