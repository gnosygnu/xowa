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
package gplx.xowa.guis.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
public class Xog_cmd_mgr_invk implements GfoInvkAble {
	private Xoae_app app; private Xog_cmd_mgr cmd_mgr;
	public void Ctor(Xoae_app app, Xog_cmd_mgr cmd_mgr) {this.app = app; this.cmd_mgr = cmd_mgr;}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		Xog_cmd_itm cmd_itm = cmd_mgr.Get_or_null(k);
		if (cmd_itm == null) return GfoInvkAble_.Rv_unhandled;
		return app.Gfs_mgr().Run_str(cmd_itm.Cmd());
	}
}
