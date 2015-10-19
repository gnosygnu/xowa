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
package gplx.xowa.apps.apis.xowa.html; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*;
import gplx.xowa.htmls.tidy.*;
public class Xoapi_tidy implements GfoInvkAble {
	private Xoae_app app;
	public void Init_by_kit(Xoae_app app) {
		this.app = app;
	}
	public void Toggle() {
		app.Html_mgr().Tidy_mgr().Enabled_toggle();
		app.Gui_mgr().Browser_win().Page__refresh();
	}
	public void Engine_(byte v)	{
		app.Html_mgr().Tidy_mgr().Wkr_tid_(v);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_toggle)) 					this.Toggle();
		else if	(ctx.Match(k, Invk_engine_tidy_))	 			Engine_(Xoh_tidy_wkr_.Tid_tidy);
		else if	(ctx.Match(k, Invk_engine_jtidy_))	 			Engine_(Xoh_tidy_wkr_.Tid_jtidy);
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_toggle = "toggle", Invk_engine_tidy_ = "engine_tidy_", Invk_engine_jtidy_ = "engine_jtidy_";
}
