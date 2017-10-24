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
package gplx.xowa.guis.tabs; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
public class Xog_tab_mgr__swt implements Xog_tab_mgr {
	private final    Xoa_gui_mgr gui_mgr;
	public Xog_tab_mgr__swt(Xoa_gui_mgr gui_mgr) {this.gui_mgr = gui_mgr;}
	public void New_tab(boolean focus, String site, String page) {			
		// gui_mgr.Browser_win().Tab_mgr().Tabs_new_link(url, focus);	// TODO_OLD: handle html dumps
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Xog_tab_mgr_.Invk__new_tab))		gui_mgr.Kit().New_cmd_sync(this).Invk(ctx, ikey, Invk__new_tab_async, m);
		else if	(ctx.Match(k, Invk__new_tab_async))				this.New_tab(m.ReadYn("focus"), m.ReadStr("site"), m.ReadStr("page"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk__new_tab_async = "new_tab_async";
}
