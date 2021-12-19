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
package gplx.xowa.xtns.titleBlacklists;
import gplx.libs.files.Io_url;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.xtns.scribunto.Scrib_core;
import gplx.xowa.xtns.scribunto.Scrib_lib;
import gplx.xowa.xtns.scribunto.Scrib_lua_mod;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_args;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_mgr;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_rslt;
public class Blacklist_scrib_lib implements Scrib_lib {
	public String Key() {return "mw.ext.TitleBlacklist";}
	public Scrib_lua_mod Mod() {return mod;} private Scrib_lua_mod mod;
	public Scrib_lib Init() {procs.Init_by_lib(this, Proc_names); return this;}
	public Scrib_lib Clone_lib(Scrib_core core) {return new Blacklist_scrib_lib();}
	public Scrib_lua_mod Register(Scrib_core core, Io_url script_dir) {
		Init();			
		mod = core.RegisterInterface(this, core.App().Fsys_mgr().Bin_xtns_dir().GenSubFil_nest("TitleBlacklist", "mw.ext.TitleBlacklist.lua"));
		return mod;
	}
	public Scrib_proc_mgr Procs() {return procs;} private Scrib_proc_mgr procs = new Scrib_proc_mgr();
	public boolean Procs_exec(int key, Scrib_proc_args args, Scrib_proc_rslt rslt) {
		switch (key) {
			case Proc_test:										return Test(args, rslt);
			default: throw ErrUtl.NewUnhandled(key);
		}
	}
	private static final int Proc_test = 0;
	public static final String Invk_test = "test";
	private static final String[] Proc_names = StringUtl.Ary(Invk_test);
	public boolean Test(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		return rslt.Init_null();	// assume all titles are blacklisted; note that this info is not available;
	}
}
