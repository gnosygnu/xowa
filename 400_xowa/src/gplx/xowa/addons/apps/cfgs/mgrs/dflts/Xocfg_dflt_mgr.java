/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.addons.apps.cfgs.mgrs.dflts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.mgrs.*;
import gplx.xowa.addons.apps.cfgs.mgrs.caches.*;
public class Xocfg_dflt_mgr {
	private final    Xocfg_cache_mgr cache_mgr;
	private final    Ordered_hash hash = Ordered_hash_.New();
	public Xocfg_dflt_mgr(Xocfg_cache_mgr cache_mgr) {
		this.cache_mgr = cache_mgr;
	}
	public String Get_or(String key, String or) {
		Gfo_invk itm = (Gfo_invk)hash.Get_by(key);
		return (itm == null) ? or : (String)Gfo_invk_.Invk_by_key(itm, key);
	}
	public void Add(String key, String val) {
		hash.Add(key, new Xocfg_dflt_itm__static(val));
		cache_mgr.Dflt(key, val);
	}
	public void Add(Gfo_invk invk, String... keys) {
		for (String key : keys) {
			hash.Add_if_dupe_use_nth(key, invk);
		}
	}
	public static void Run_os_gfs(String user_name, gplx.xowa.apps.gfs.Xoa_gfs_mgr gfs_mgr, gplx.xowa.apps.fsys.Xoa_fsys_mgr fsys_mgr) {
		Io_url url = fsys_mgr.Root_dir().GenSubFil_nest("user", user_name, "app", "cfg", "os." + Xoa_app_.Op_sys_str + ".gfs");
		if (!Io_mgr.Instance.ExistsFil(url)) {
			Io_url dflt_url = fsys_mgr.Bin_plat_dir().GenSubFil_nest("xowa", "cfg", "os.default.gfs");
			if (Io_mgr.Instance.ExistsFil(dflt_url))	// TEST: also, DRD
				Io_mgr.Instance.CopyFil_args(dflt_url, url, true).MissingFails_off().Exec();
		}
		gfs_mgr.Run_url(url);
	}
}
class Xocfg_dflt_itm__static implements Gfo_invk {
	private final    String val;
	public Xocfg_dflt_itm__static(String val) {
		this.val = val;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		return val;
	}
}
