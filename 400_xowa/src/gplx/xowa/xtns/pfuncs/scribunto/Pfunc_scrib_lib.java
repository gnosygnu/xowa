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
package gplx.xowa.xtns.pfuncs.scribunto;
import gplx.libs.files.Io_url;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.xtns.pfuncs.exprs.Pfunc_expr;
import gplx.xowa.xtns.scribunto.Scrib_core;
import gplx.xowa.xtns.scribunto.Scrib_lib;
import gplx.xowa.xtns.scribunto.Scrib_lua_mod;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_args;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_mgr;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_rslt;
public class Pfunc_scrib_lib implements Scrib_lib {
	private Scrib_core core;
	public String Key() {return "mw.ext.ParserFunctions";}
	public Scrib_lua_mod Mod() {return mod;} private Scrib_lua_mod mod;
	public Scrib_lib Init() {procs.Init_by_lib(this, Proc_names); return this;}
	public void Core_(Scrib_core v) {this.core = v;} // TEST:
	public Scrib_lib Clone_lib(Scrib_core core) {return new Pfunc_scrib_lib();}
	public Scrib_lua_mod Register(Scrib_core core, Io_url script_dir) {
		this.core = core;
		Init();
		mod = core.RegisterInterface(this, core.App().Fsys_mgr().Bin_xtns_dir().GenSubFil_nest("ParserFunctions", "mw.ext.ParserFunctions.lua"));
		return mod;
	}
	public Scrib_proc_mgr Procs() {return procs;} private Scrib_proc_mgr procs = new Scrib_proc_mgr();
	public boolean Procs_exec(int key, Scrib_proc_args args, Scrib_proc_rslt rslt) {
		switch (key) {
			case Proc_expr:										return Expr(args, rslt);
			default: throw ErrUtl.NewUnhandled(key);
		}
	}
	private static final int Proc_expr = 0;
	public static final String Invk_expr = "expr";
	private static final String[] Proc_names = StringUtl.Ary(Invk_expr);
	public boolean Expr(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte[] expr_bry = args.Xstr_bry_or_null(0);	// NOTE: some modules will pass in an int; PAGE:en.w:531_BC DATE:2016-04-29
		BryWtr tmp_bfr = core.Wiki().Utl__bfr_mkr().GetB128();
		Pfunc_expr.Evaluate(tmp_bfr, core.Ctx(), expr_bry);
		String expr_rslt = tmp_bfr.ToStrAndRls();
		return rslt.Init_obj(expr_rslt);	// NOTE: always return rslt; don't throw error even if expr is invalid; EX:mw.ParserFuntion.expr('fail'); PAGE:es.w:Freer_(Texas) DATE:2015-07-28
	}
}
