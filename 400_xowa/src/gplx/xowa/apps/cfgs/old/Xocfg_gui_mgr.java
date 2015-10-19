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
package gplx.xowa.apps.cfgs.old; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.cfgs.*;
public class Xocfg_gui_mgr implements GfoInvkAble {
	public Xocfg_gui_mgr(Xoae_app app) {bnd_mgr = new Xocfg_bnd_mgr(app);}
	public Xocfg_tab_mgr Tab_mgr() {return tab_mgr;} private Xocfg_tab_mgr tab_mgr = new Xocfg_tab_mgr();
	public Xocfg_bnd_mgr Bnd_mgr() {return bnd_mgr;} private Xocfg_bnd_mgr bnd_mgr;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_tabs))				return tab_mgr;
		else if	(ctx.Match(k, Invk_bnds))				return bnd_mgr;
		else	return GfoInvkAble_.Rv_unhandled;
	}
	private static final String Invk_tabs = "tabs", Invk_bnds = "bnds";
}
