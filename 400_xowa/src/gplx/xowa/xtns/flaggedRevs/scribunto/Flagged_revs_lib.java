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
package gplx.xowa.xtns.flaggedRevs.scribunto;
import gplx.libs.files.Io_url;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.KeyVal;
import gplx.types.errs.ErrUtl;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.xtns.scribunto.Scrib_core;
import gplx.xowa.xtns.scribunto.Scrib_lib;
import gplx.xowa.xtns.scribunto.Scrib_lua_mod;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_args;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_mgr;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_rslt;
public class Flagged_revs_lib implements Scrib_lib {
	private Scrib_core core;
	public String Key() {return "mw.ext.FlaggedRevs";}
	public Scrib_lua_mod Mod() {return mod;} private Scrib_lua_mod mod;
	public Scrib_lib Init() {procs.Init_by_lib(this, Proc_names); return this;}
	public Scrib_lib Core_(Scrib_core v) {this.core = v; return this;} // TEST:
	public Scrib_lib Clone_lib(Scrib_core core) {return new Flagged_revs_lib();}
	public Scrib_lua_mod Register(Scrib_core core, Io_url script_dir) {
		this.core = core;
		Init();
		mod = core.RegisterInterface(this, core.App().Fsys_mgr().Bin_xtns_dir().GenSubFil_nest("FlaggedRevs", "scribunto", "mw.ext.FlaggedRevs.lua"));
		return mod;
	}
	public Scrib_proc_mgr Procs() {return procs;} private final Scrib_proc_mgr procs = new Scrib_proc_mgr();
	public boolean Procs_exec(int key, Scrib_proc_args args, Scrib_proc_rslt rslt) {
		switch (key) {
			case Proc_getStabilitySettings:									return GetStabilitySettings(args, rslt);
			default: throw ErrUtl.NewUnhandled(key);
		}
	}
	private static final int Proc_getStabilitySettings = 0;
	public static final String Invk_getStabilitySettings = "getStabilitySettings";
	private static final String[] Proc_names = StringUtl.Ary(Invk_getStabilitySettings);
	public boolean GetStabilitySettings(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte[] page_name = args.Cast_bry_or_null(0);
		Xoa_ttl page_ttl = null;
		if (page_name == null)
			page_ttl = core.Ctx().Page().Ttl();
		else { 
			page_ttl = core.Wiki().Ttl_parse(page_name);
			if (page_ttl == null) return rslt.Init_null();
		}
		// if ( !FlaggedRevs::inReviewNamespace( $title ) ) return rslt.Init_null();
		return rslt.Init_obj(getDefaultVisibilitySettings());
	}
	private static KeyVal[] getDefaultVisibilitySettings() {
		KeyVal[] rv = new KeyVal[3];
		rv[0] = KeyVal.NewStr("over"+"ride", 0);	// FlaggedRevs::isStableShownByDefault() ? 1 : 0,
		rv[1] = KeyVal.NewStr("autoreview", "");
		rv[2] = KeyVal.NewStr("expiry", "infinity");
		return rv;
	}
}
