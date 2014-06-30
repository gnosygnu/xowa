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
package gplx.xowa.apis.xowa.gui.browsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.apis.*; import gplx.xowa.apis.xowa.*; import gplx.xowa.apis.xowa.gui.*;
import gplx.gfui.*; import gplx.xowa.gui.views.*;
public class Xoapi_info implements GfoInvkAble {
	public void Init_by_kit(Xoa_app app) {this.app = app;} private Xoa_app app;
	private Xog_win_itm Win() {return app.Gui_mgr().Browser_win();}
	public void Focus()				{this.Win().Info_box().Focus();}
	public void Clear()				{app.Usr_dlg().Ui_wkr().Clear();}
	public void Launch() {
		Io_url session_fil = app.Log_wtr().Session_fil();
		app.Fsys_mgr().App_mgr().App_view_text().Run(session_fil);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_focus)) 					this.Focus();
		else if	(ctx.Match(k, Invk_clear)) 					this.Clear();
		else if	(ctx.Match(k, Invk_launch)) 				this.Launch();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_focus = "focus", Invk_clear = "clear", Invk_launch = "launch";
}
