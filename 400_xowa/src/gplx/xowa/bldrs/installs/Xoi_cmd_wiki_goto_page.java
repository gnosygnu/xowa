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
package gplx.xowa.bldrs.installs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.core.threads.*;
class Xoi_cmd_wiki_goto_page extends Gfo_thread_cmd_base implements Gfo_thread_cmd {
	public Xoi_cmd_wiki_goto_page(Xoae_app app, String page) {this.app = app; this.page = page; this.Ctor(app.Usr_dlg(), app.Gui_mgr().Kit());} private Xoae_app app; String page;
	@Override public void Async_run()	{kit.New_cmd_sync(this).Invk(GfsCtx.new_(), 0, Invk_goto_page, GfoMsg_.Null);}
	private void Goto_page(String page)			{app.Gui_mgr().Browser_win().Page__navigate_by_url_bar(page);}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_goto_page))				Goto_page(page);
		else	return super.Invk(ctx, ikey, k, m);
		return this;
	}	private static final String Invk_goto_page = "goto_page";
	public static final String KEY = "wiki.goto_page";
}
