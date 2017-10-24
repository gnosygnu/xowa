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
package gplx.xowa.htmls; import gplx.*; import gplx.xowa.*;
import gplx.xowa.htmls.core.htmls.tidy.*; import gplx.xowa.htmls.js.*; import gplx.xowa.htmls.skins.*;
import gplx.xowa.parsers.xndes.*;
public class Xoh_html_mgr implements Gfo_invk {
	public Xoh_html_mgr(Xoae_app app) {}
	public void Init_by_app(Xoae_app app) {
		page_mgr.Init_by_app(app);
	}
	public Xoh_page_mgr Page_mgr() {return page_mgr;} private final    Xoh_page_mgr page_mgr = new Xoh_page_mgr();
	public Xoh_skin_mgr Skin_mgr() {return skin_mgr;} private final    Xoh_skin_mgr skin_mgr = new Xoh_skin_mgr();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_page))	return page_mgr;
		else if	(ctx.Match(k, Invk_skins))	return skin_mgr;
		else	return Gfo_invk_.Rv_unhandled;
	}	private static final String Invk_page = "page", Invk_skins = "skins";
}
