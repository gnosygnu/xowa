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
package gplx.xowa.apps.apis.xowa.html.modules; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*; import gplx.xowa.apps.apis.xowa.html.*;
import gplx.xowa.htmls.modules.popups.*;
public class Xoapi_popups implements Gfo_invk, Gfo_evt_mgr_owner {
	private Xoae_app app;
	public Xoapi_popups() {
		evt_mgr = new Gfo_evt_mgr(this);
	}
	public Gfo_evt_mgr Evt_mgr() {return evt_mgr;} private Gfo_evt_mgr evt_mgr;
	public void Init_by_app(Xoae_app app) {this.app = app;}
	public void Show_more(String popup_id) {
		Xowe_wiki wiki = app.Gui_mgr().Browser_win().Active_tab().Wiki();
		wiki.Html_mgr().Head_mgr().Popup_mgr().Show_more(popup_id);
	}
	public void Show_all(String popup_id) {
		Xowe_wiki wiki = app.Gui_mgr().Browser_win().Active_tab().Wiki();
		wiki.Html_mgr().Head_mgr().Popup_mgr().Show_all(popup_id);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_show_more))	 					Show_more(m.ReadStr("popup_id"));
		else if	(ctx.Match(k, Invk_show_all))	 					Show_all (m.ReadStr("popup_id"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk_show_more = "show_more", Invk_show_all = "show_all"
	;
}
