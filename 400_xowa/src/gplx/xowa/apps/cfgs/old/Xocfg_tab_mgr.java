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
public class Xocfg_tab_mgr implements Gfo_invk {
	public Xocfg_tab_new_mgr New_mgr() {return new_mgr;} private Xocfg_tab_new_mgr new_mgr = new Xocfg_tab_new_mgr();
	public Xocfg_tab_btn_mgr Btn_mgr() {return btn_mgr;} private Xocfg_tab_btn_mgr btn_mgr = new Xocfg_tab_btn_mgr();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_new))				return new_mgr;
		else if	(ctx.Match(k, Invk_btns))				return btn_mgr;
		else	return Gfo_invk_.Rv_unhandled;
	}	private static final String Invk_new = "new", Invk_btns = "btns";
}
