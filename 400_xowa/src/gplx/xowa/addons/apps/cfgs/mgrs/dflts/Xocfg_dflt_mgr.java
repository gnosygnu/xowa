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
		Io_url url = app.Fsys_mgr().Bin_plat_dir().GenSubFil_nest("xowa", "cfg", "xo.cfg.dflt.os.gfs");
		if (!Io_mgr.Instance.ExistsFil(url)) {
			Io_url src_url = url.GenNewNameAndExt("xo.cfg.dflt.os_default.gfs");
			if (Io_mgr.Instance.ExistsFil(src_url))	// TEST:
				Io_mgr.Instance.CopyFil_args(src_url, url, true).MissingFails_off().Exec();
		}
		app.Gfs_mgr().Run_url(url);
	}
	public String Get_or(String key, String or) {
		Gfo_invk itm = (Gfo_invk)hash.Get_by(key);
		return (itm == null) ? or : (String)Gfo_invk_.Invk_by_key(itm, key);
	}
	public void Add(String key, String val) {
		cache_mgr.Dflt(key, val);
		hash.Add(key, new Xocfg_dflt_itm__static(val));
	}
	public void Add(Gfo_invk invk, String... keys) {
		for (String key : keys) {
			hash.Add_if_dupe_use_nth(key, invk);
		}
	}
}
