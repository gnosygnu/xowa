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
package gplx.xowa.cfgs2; import gplx.*; import gplx.xowa.*;
public class Xocfg_root implements GfoInvkAble {
	public Xocfg_root(Xoa_app app, byte tid) {
		this.tid = tid;
		this.gui_mgr = new Xocfg_gui_mgr(app);
	}
	public byte Tid() {return tid;} private byte tid;
	public Xocfg_gui_mgr Gui_mgr() {return gui_mgr;} private Xocfg_gui_mgr gui_mgr;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_gui))				return gui_mgr;
		else	return GfoInvkAble_.Rv_unhandled;
	}
	private static final String Invk_gui = "gui";
}
