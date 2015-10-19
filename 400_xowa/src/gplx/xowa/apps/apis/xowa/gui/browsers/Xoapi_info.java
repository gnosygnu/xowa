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
package gplx.xowa.apps.apis.xowa.gui.browsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*; import gplx.xowa.apps.apis.xowa.gui.*;
import gplx.gfui.*; import gplx.xowa.guis.views.*;
public class Xoapi_info implements Gfo_usr_dlg__gui__opt, GfoInvkAble {
	public void Init_by_kit(Xoae_app app) {this.app = app;} private Xoae_app app;
	private Xog_win_itm Win() {return app.Gui_mgr().Browser_win();}
	public void Focus()				{this.Win().Info_box().Focus();}
	public void Clear()				{app.Usr_dlg().Gui_wkr().Clear();}
	public void Launch() {
		Io_url session_fil = app.Log_wtr().Session_fil();
		app.Prog_mgr().App_view_text().Run(session_fil);
	}
	public boolean Warn_enabled() {return warn_enabled;} private boolean warn_enabled;
	public boolean Note_enabled() {return note_enabled;} private boolean note_enabled;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_focus)) 					this.Focus();
		else if	(ctx.Match(k, Invk_clear)) 					this.Clear();
		else if	(ctx.Match(k, Invk_launch)) 				this.Launch();
		else if	(ctx.Match(k, Invk_warn_enabled)) 			return Yn.To_str(warn_enabled);
		else if	(ctx.Match(k, Invk_warn_enabled_)) 			warn_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_note_enabled)) 			return Yn.To_str(note_enabled);
		else if	(ctx.Match(k, Invk_note_enabled_)) 			note_enabled = m.ReadYn("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_focus = "focus", Invk_clear = "clear", Invk_launch = "launch"
	, Invk_warn_enabled = "warn_enabled", Invk_warn_enabled_ = "warn_enabled_"
	, Invk_note_enabled = "note_enabled", Invk_note_enabled_ = "note_enabled_"
	;
}
