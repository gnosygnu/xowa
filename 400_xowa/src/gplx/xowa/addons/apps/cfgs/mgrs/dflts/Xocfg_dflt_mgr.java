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
package gplx.xowa.addons.apps.cfgs.mgrs.dflts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.mgrs.*;
import gplx.xowa.addons.apps.cfgs.mgrs.caches.*;
public class Xocfg_dflt_mgr {
	private final    Xocfg_cache_mgr cache_mgr;
	private final    Ordered_hash hash = Ordered_hash_.New();
	public Xocfg_dflt_mgr(Xocfg_cache_mgr cache_mgr) {
		this.cache_mgr = cache_mgr;
	}
	public void Init_by_app(Xoa_app app) {
		Xocfg_dflt_loader loader = new Xocfg_dflt_loader();
		loader.Load_by_file(this, app.Fsys_mgr().Bin_plat_dir().GenSubFil_nest("xowa", "cfg", "xo.cfg.dflt.json"));
	}
	public String Get_or(String key, String or) {
		Xocfg_dflt_itm itm = (Xocfg_dflt_itm)hash.Get_by(key);
		return (itm == null) ? or : itm.Get_str(key);
	}
	public void Add(String key, String val) {
		cache_mgr.Dflt(key, val);
		hash.Add(key, new Xocfg_dflt_itm__static(val));
	}
	public void Add(String key, Xocfg_dflt_itm itm) {
		hash.Add(key, itm);
	}
}
