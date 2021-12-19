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
package gplx.xowa.apps.apis.xowa;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.Gfo_invk_;
import gplx.frameworks.invks.GfsCtx;
import gplx.xowa.*;
import gplx.xowa.apps.apis.xowa.bldrs.*;
public class Xoapi_bldr implements Gfo_invk {
	public void Ctor_by_app(Xoa_app app) {wiki.Ctor_by_app(app);}
	public Xoapi_bldr_wiki		Wiki() {return wiki;} private final Xoapi_bldr_wiki wiki = new Xoapi_bldr_wiki();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_wiki)) 									return wiki;
		else	return Gfo_invk_.Rv_unhandled;
	}
	private static final String Invk_wiki = "wiki";
}
