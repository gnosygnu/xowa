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
package gplx.xowa.xtns.xowa_cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Xop_xowa_func extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_xowa;}
	@Override public Pf_func New(int id, byte[] name) {return new Xop_xowa_func().Name_(name);}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bfr) {			
		if (ctx.Wiki().Sys_cfg().Xowa_cmd_enabled()) {	// only exec if enabled for wiki
			int args_len = self.Args_len();
			byte[] arg_0 = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, args_len, 0);
			Object rslt = ctx.App().Gfs_mgr().Run_str(String_.new_u8(arg_0));
			bfr.Add(Bry_.new_u8(Object_.Xto_str_strict_or_null_mark(rslt)));
		}
		else {
			bfr.Add_mid(src, self.Src_bgn(), self.Src_end());
		}
	}
}
