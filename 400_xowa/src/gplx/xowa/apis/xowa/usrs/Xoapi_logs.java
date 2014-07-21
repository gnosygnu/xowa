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
package gplx.xowa.apis.xowa.usrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apis.*; import gplx.xowa.apis.xowa.*;
import gplx.xowa.gui.views.*;
public class Xoapi_logs implements GfoInvkAble {
	private Xoa_app app;
	public void Ctor_by_app(Xoa_app app) {this.app = app;}
	public void Init_by_kit(Xoa_app app) {}
	public boolean Enabled() {return app.Log_wtr().Enabled();}
	public void Enabled_(boolean v) {
		app.Log_wtr().Enabled_(v);
		if (!v)
			Io_mgr._.DeleteFil_args(app.Log_wtr().Session_fil()).MissingFails_off().Exec();
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_enabled)) 							return Yn.Xto_str(this.Enabled());
		else if	(ctx.Match(k, Invk_enabled_)) 							Enabled_(m.ReadYn("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_enabled = "enabled", Invk_enabled_ = "enabled_";
}
