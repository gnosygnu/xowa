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
package gplx.xowa.xtns.pfuncs.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.libs.*;
import gplx.xowa.xtns.pfuncs.exprs.*;
public class Pfunc_scrib_lib implements Scrib_lib {
	private Scrib_core core;
	public Scrib_lua_mod Mod() {return mod;} private Scrib_lua_mod mod;
	public Scrib_lib Init() {procs.Init_by_lib(this, Proc_names); return this;}
	public void Core_(Scrib_core v) {this.core = v;} // TEST:
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
			default: throw Err_.new_unhandled(key);
		}
	}
	private static final int Proc_expr = 0;
	public static final String Invk_expr = "expr";
	private static final String[] Proc_names = String_.Ary(Invk_expr);
	public boolean Expr(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte[] expr_bry = args.Pull_bry(0);
		Bry_bfr tmp_bfr = core.Wiki().Utl__bfr_mkr().Get_b128();
		Pfunc_expr.Evaluate(tmp_bfr, core.Ctx(), expr_bry);
		String expr_rslt = tmp_bfr.To_str_and_rls();
		return rslt.Init_obj(expr_rslt);	// NOTE: always return rslt; don't throw error even if expr is invalid; EX:mw.ParserFuntion.expr('fail'); PAGE:es.w:Freer_(Texas) DATE:2015-07-28
	}
}
