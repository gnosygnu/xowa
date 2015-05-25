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
package gplx.xowa.apis.xowa.html; import gplx.*; import gplx.xowa.*; import gplx.xowa.apis.*; import gplx.xowa.apis.xowa.*;
import gplx.xowa.cfgs.*;
public class Xoapi_toggle_mgr implements GfoInvkAble {
	private Ordered_hash hash = Ordered_hash_.new_bry_();
	public Xoapi_toggle_itm Get_or_new(String key_str) {
		byte[] key_bry = Bry_.new_u8(key_str);
		Xoapi_toggle_itm rv = (Xoapi_toggle_itm)hash.Get_by(key_bry);
		if (rv == null) {
			rv = new Xoapi_toggle_itm(key_bry);
			hash.Add(key_bry, rv);
		}
		return rv;
	}
	public void Img_dir_(Io_url v) {
		int len = hash.Count();
		for (int i = 0; i < len; ++i) {
			Xoapi_toggle_itm itm = (Xoapi_toggle_itm)hash.Get_at(i);
			itm.Init_fsys(v);
		}
	}
	public void Save(Xoa_cfg_mgr cfg_mgr) {
		int len = hash.Count();
		for (int i = 0; i < len; ++i) {
			Xoapi_toggle_itm itm = (Xoapi_toggle_itm)hash.Get_at(i);
			cfg_mgr.Set_by_app("xowa.api.html.page.toggles.get('" + String_.new_u8(itm.Key_bry()) + "').visible", Yn.Xto_str(itm.Visible()));
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get)) 			return this.Get_or_new(m.ReadStr("key"));
		else	return GfoInvkAble_.Rv_unhandled;
	}
	private static final String Invk_get = "get";
}
