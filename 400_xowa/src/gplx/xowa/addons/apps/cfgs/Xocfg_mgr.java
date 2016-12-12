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
package gplx.xowa.addons.apps.cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*;
import gplx.dbs.*; import gplx.xowa.addons.apps.cfgs.mgrs.caches.*; import gplx.xowa.addons.apps.cfgs.mgrs.dflts.*; import gplx.xowa.addons.apps.cfgs.mgrs.types.*;
public class Xocfg_mgr {
	private final    Xocfg_cache_mgr cache_mgr = new Xocfg_cache_mgr();
	public Xocfg_mgr() {
		this.dflt_mgr = new Xocfg_dflt_mgr(cache_mgr);
	}
	public Xocfg_type_mgr Type_mgr() {return type_mgr;} private final    Xocfg_type_mgr type_mgr = new Xocfg_type_mgr();
	public Xocfg_dflt_mgr Dflt_mgr() {return dflt_mgr;} private final    Xocfg_dflt_mgr dflt_mgr;
	public void Init_by_app(Xoa_app app) {
		cache_mgr.Init_by_app
		( gplx.xowa.addons.apps.cfgs.dbs.Xocfg_db_app.New_conn(app)
		, app.User().User_db_mgr().Conn());
		dflt_mgr.Init_by_app(app);
	}
	public void Clear() {
		cache_mgr.Clear();
	}
	public boolean Bind_bool(Xow_wiki wiki, String key, Gfo_invk sub) {return Yn.parse_or(Bind_str(wiki, key, sub), false);}
	public String Bind_str(Xow_wiki wiki, String key, Gfo_invk sub) {
		String ctx = wiki.Domain_itm().Abrv_xo_str();
		cache_mgr.Sub(sub, ctx, key, key);
		return cache_mgr.Get(ctx, key);
	}
	public String Get_str_app(String key) {return Get_str(gplx.xowa.addons.apps.cfgs.specials.edits.objs.Xoedit_itm.Ctx__app, key);}
	public String Get_str(String ctx, String key) {
		return cache_mgr.Get(ctx, key);
	}
	public void Set_str_app(String key, String val) {Set_str(gplx.xowa.addons.apps.cfgs.specials.edits.objs.Xoedit_itm.Ctx__app, key, val);}
	public void Set_str(String ctx, String key, String val) {
		cache_mgr.Set(ctx, key, val);
	}
	public void Del(String ctx, String key) {
		cache_mgr.Del(ctx, key);
	}
}
