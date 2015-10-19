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
package gplx.xowa.apps; import gplx.*; import gplx.xowa.*;
import gplx.xowa.guis.views.*;
public class Xoa_cur implements GfoInvkAble {
	public Xoa_cur(Xoae_app app) {this.app = app;} private Xoae_app app;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_wiki)) {
			Xog_win_itm win = app.Gui_mgr().Browser_win();
			return win.Active_tab() == null ? GfoInvkAble_.Null : win.Active_page().Wikie(); // null check when called from mass html gen; DATE:2014-06-04
		}
		else if	(ctx.Match(k, Invk_win))			return app.Gui_mgr().Browser_win();
		else if	(ctx.Match(k, Invk_user))			return app.Usere();
		else return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_wiki = "wiki", Invk_win = "win", Invk_user = "user";
}
