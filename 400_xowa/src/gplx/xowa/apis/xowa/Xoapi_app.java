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
package gplx.xowa.apis.xowa; import gplx.*; import gplx.xowa.*; import gplx.xowa.apis.*;
import gplx.xowa.gui.views.*;
import gplx.xowa.apis.xowa.envs.*; import gplx.xowa.apis.xowa.startups.*;
public class Xoapi_app implements GfoInvkAble {
	private Xog_win_itm win;
	public void Init_by_kit(Xoa_app app) {
		win = app.Gui_mgr().Browser_win();
	}
	public void Exit()			{win.App__exit();}
	public Xoapi_env		Env() {return env;} private Xoapi_env env = new Xoapi_env();
	public Xoapi_startups	Startup() {return startup;} private Xoapi_startups startup = new Xoapi_startups();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_exit)) 								this.Exit();
		else if	(ctx.Match(k, Invk_startup)) 							return startup;
		else if	(ctx.Match(k, Invk_env)) 								return env;
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_exit = "exit", Invk_startup = "startup", Invk_env = "env";
}
