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
package gplx.xowa.addons.apps.cfgs.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*;
class Xocfg_cache_grp {
	private final    Hash_adp vals = Hash_adp_.New();
	private final    Hash_adp subs = Hash_adp_.New();
	private final    String dflt;
	public Xocfg_cache_grp(String key, String dflt) {
		this.key = key;
		this.dflt = dflt;
	}
	public String Key() {return key;} private final    String key;
	public String Get(String ctx) {
		// exact match; EX: "en.w|key_1"
		Xocfg_cache_itm rv = (Xocfg_cache_itm)vals.Get_by(ctx);
		if (rv != null) return rv.Val();

		// global match; EX: "app|key_1"
		rv = (Xocfg_cache_itm)vals.Get_by(gplx.xowa.addons.apps.cfgs.gui.Xogui_itm.Ctx__app);
		if (rv != null) return rv.Val();

		// dflt
		return dflt;
	}
	public void Set(String ctx, String val) {
		Xocfg_cache_itm rv = (Xocfg_cache_itm)vals.Get_by(ctx);
		if (rv == null) {
			rv = new Xocfg_cache_itm(ctx, key, val);
			vals.Add(ctx, rv);
		}
		else {
			rv.Val_(val);
		}
	}
	public void Add(String ctx, Xocfg_cache_itm itm) {
		vals.Add(ctx, itm);
	}
	public void Sub(Gfo_evt_itm sub, String ctx, String evt) {
		List_adp list = (List_adp)subs.Get_by(ctx);
		if (list == null) {
			list = List_adp_.New();
			subs.Add(ctx, list);
		}
		list.Add(new Xocfg_cache_sub(sub, ctx, evt, key));
	}
	public void Pub(String ctx, String val) {
		// exact match; EX: "en.w|key_1"
		List_adp list = (List_adp)subs.Get_by(ctx);
		if (list == null) {// global match; EX: "app|key_1"
			list = (List_adp)subs.Get_by(ctx);
			if (list == null) return;
		}
		Pub(list, val);
	}
	private void Pub(List_adp list, String val) {
		int len = list.Len();
		for (int i = 0; i < len; i++) {
			Xocfg_cache_sub sub = (Xocfg_cache_sub)list.Get_at(i);
			Gfo_evt_mgr_.Pub_val(sub.Sub(), sub.Evt(), val);
		}
	}
}
