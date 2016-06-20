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
package gplx.xowa.apps.apis.xowa; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*;
import gplx.xowa.guis.views.*;
import gplx.xowa.apps.apis.xowa.apps.*; import gplx.xowa.apps.apis.xowa.envs.*; import gplx.xowa.apps.apis.xowa.startups.*;
public class Xoapi_app implements Gfo_invk {
	private Xog_win_itm win;
	public void Ctor_by_app(Xoae_app app) {
		fsys.Ctor_by_app(app);
	}
	public void Init_by_kit(Xoae_app app) {
		win = app.Gui_mgr().Browser_win();
	}
	public Xoapi_fsys		Fsys()		{return fsys;} private Xoapi_fsys fsys = new Xoapi_fsys();
	public void				Exit()		{win.App__exit();}
	public Xoapi_env		Env()		{return env;} private Xoapi_env env = new Xoapi_env();
	public Xoapi_startups	Startup()	{return startup;} private Xoapi_startups startup = new Xoapi_startups();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_exit)) 								this.Exit();
		else if	(ctx.Match(k, Invk_fsys)) 								return fsys;
		else if	(ctx.Match(k, Invk_startup)) 							return startup;
		else if	(ctx.Match(k, Invk_env)) 								return env;
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_exit = "exit", Invk_startup = "startup", Invk_env = "env", Invk_fsys = "fsys";
}
