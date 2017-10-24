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
package gplx.xowa.xtns.flaggedRevs.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.flaggedRevs.*;
import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.libs.*;
import gplx.xowa.xtns.pfuncs.exprs.*;
import gplx.xowa.xtns.scribunto.procs.*;
public class Flagged_revs_lib implements Scrib_lib {
	private Scrib_core core;
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
	public Scrib_proc_mgr Procs() {return procs;} private final    Scrib_proc_mgr procs = new Scrib_proc_mgr();
	public boolean Procs_exec(int key, Scrib_proc_args args, Scrib_proc_rslt rslt) {
		switch (key) {
			case Proc_getStabilitySettings:									return GetStabilitySettings(args, rslt);
			default: throw Err_.new_unhandled(key);
		}
	}
	private static final int Proc_getStabilitySettings = 0;
	public static final String Invk_getStabilitySettings = "getStabilitySettings";
	private static final    String[] Proc_names = String_.Ary(Invk_getStabilitySettings);
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
	private static Keyval[] getDefaultVisibilitySettings() {
		Keyval[] rv = new Keyval[3];
		rv[0] = Keyval_.new_("over"+"ride", 0);	// FlaggedRevs::isStableShownByDefault() ? 1 : 0,
		rv[1] = Keyval_.new_("autoreview", "");
		rv[2] = Keyval_.new_("expiry", "infinity");
		return rv;
	}
}
