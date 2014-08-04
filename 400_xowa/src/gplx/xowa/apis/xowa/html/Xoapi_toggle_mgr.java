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
	private Hash_adp_bry itms = Hash_adp_bry.cs_();
	public Xoapi_toggle_mgr() {
		itm_wikidata_langs = itms_add(itms, Key_wikidata_langs);
		itm_offline_wikis = itms_add(itms, Key_offline_wikis);
	}
	public Xoapi_toggle_itm Itm_wikidata_langs() {return itm_wikidata_langs;} private Xoapi_toggle_itm itm_wikidata_langs;
	public Xoapi_toggle_itm Itm_offline_wikis() {return itm_offline_wikis;} private Xoapi_toggle_itm itm_offline_wikis;
	public Xoapi_toggle_itm Get(byte[] key) {return (Xoapi_toggle_itm)itms.Get_by_bry(key);}
	public void Save(Xoa_cfg_mgr cfg_mgr) {
		Save_itm(cfg_mgr, itm_wikidata_langs, itm_offline_wikis);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get)) 			return this.Get(m.ReadBry("key"));
		else	return GfoInvkAble_.Rv_unhandled;
	}
	private static final String Invk_get = "get";
	private static final String Key_wikidata_langs = "wikidata-langs", Key_offline_wikis = "offline-wikis";
	private static Xoapi_toggle_itm itms_add(Hash_adp_bry itms, String name_str) {
		byte[] name_bry = Bry_.new_utf8_(name_str);
		Xoapi_toggle_itm rv = new Xoapi_toggle_itm(name_bry);
		itms.Add(name_bry, rv);
		return rv;
	}
	private static void Save_itm(Xoa_cfg_mgr cfg_mgr, Xoapi_toggle_itm... itms) {
		int itms_len = itms.length;
		for (int i = 0; i < itms_len; ++i) {
			Xoapi_toggle_itm itm = itms[i];
			cfg_mgr.Set_by_app("xowa.api.html.page.toggles.get('" + String_.new_utf8_(itm.Key_bry()) + "').visible", Yn.Xto_str(itm.Visible()));
		}
	}
}
