/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
