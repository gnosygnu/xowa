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
package gplx.xowa.guis.menus.dom; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*; import gplx.xowa.guis.menus.*;
import gplx.gfui.*; import gplx.xowa.guis.cmds.*;
public class Xog_mnu_evt_mgr implements GfoEvObj {
	private Ordered_hash itms = Ordered_hash_.New();
	public Xog_mnu_evt_mgr(Xog_mnu_base owner) {this.ev_mgr = GfoEvMgr.new_(this);}
	public GfoEvMgr EvMgr() {return ev_mgr;} private GfoEvMgr ev_mgr;
	public void Sub(Gfui_mnu_itm mnu_itm) {
		itms.Add(mnu_itm.Uid(), mnu_itm);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Evt_selected_changed)) {
			int len = itms.Count();
			for (int i = 0; i < len; i++) {
				Gfui_mnu_itm itm = (Gfui_mnu_itm)itms.Get_at(i);
				itm.Selected_(m.ReadBool("v"));
			}
		}
		return this;
	}
	public static final String Evt_selected_changed = "selected_changed";
}
