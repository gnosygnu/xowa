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
package gplx.xowa.apps.apis.xowa.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*;
import gplx.xowa.apps.apis.xowa.bldrs.filters.*;
import gplx.xowa.apps.apis.xowa.bldrs.imports.*;
import gplx.xowa.apps.apis.xowa.bldrs.runners.*;
public class Xoapi_bldr_wiki implements Gfo_invk {
	public void Ctor_by_app(Xoa_app app) {
		runner.Ctor_by_app(app);
	}
	public Xoapi_import Import() {return import_api;} private final    Xoapi_import import_api = new Xoapi_import();
	public Xoapi_runner Runner() {return runner;} private final    Xoapi_runner runner = new Xoapi_runner();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_import)) 						return import_api;
		else if	(ctx.Match(k, Invk_runner)) 						return runner;
		else	return Gfo_invk_.Rv_unhandled;
	}
	private static final String Invk_import = "import", Invk_runner = "runner";
}
