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
package gplx.xowa.guis.views; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
public class Xog_win_itm_cfg implements Gfo_invk {
	private Xoae_app app;
	public boolean Status__show_short_url() {return status__show_short_url;} private boolean status__show_short_url = true;
	public void Init_by_app(Xoae_app app) {
		this.app = app;
		app.Cfg().Bind_many_app(this, Cfg__status__show_short_url, Cfg__toolbar__show_search, Cfg__toolbar__show_allpages);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__status__show_short_url))			status__show_short_url = m.ReadYn("v");
		else if	(ctx.Match(k, Cfg__toolbar__show_search))		    Xog_win_itm_.Show_widget(m.ReadYn("v"), app.Gui_mgr().Browser_win().Search_box(), app.Gui_mgr().Browser_win().Search_exec_btn());
		else if	(ctx.Match(k, Cfg__toolbar__show_allpages))         Xog_win_itm_.Show_widget(m.ReadYn("v"), app.Gui_mgr().Browser_win().Allpages_box(), app.Gui_mgr().Browser_win().Allpages_exec_btn());
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String
	  Cfg__status__show_short_url = "xowa.gui.prog_box.show_short_url"
	, Cfg__toolbar__show_search   = "xowa.gui.toolbar.show_search"
	, Cfg__toolbar__show_allpages = "xowa.gui.toolbar.show_allpages"
	;
}
