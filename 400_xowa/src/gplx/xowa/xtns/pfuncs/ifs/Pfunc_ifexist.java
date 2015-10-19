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
package gplx.xowa.xtns.pfuncs.ifs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pfunc_ifexist extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_xtn_iferror;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_ifexist().Name_(name);}
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr rslt_bfr) {
		int args_len = self.Args_len();
		byte[] val_bry = Eval_argx(ctx, src, caller, self);
		if (Exists(ctx.Wiki(), val_bry))
			rslt_bfr.Add(Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, args_len, 0));
		else
			rslt_bfr.Add(Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, args_len, 1));
	}
	public static boolean Exists(Xowe_wiki wiki, byte[] ttl_bry) {
		synchronized (Mgr) {return Mgr.Exists(wiki, ttl_bry);}
	}
	public static final Pfunc_ifexist_mgr Mgr = new Pfunc_ifexist_mgr();
}
