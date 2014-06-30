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
package gplx.xowa.xtns.lst; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Lst_pfunc_lstx extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_lstx;}
	@Override public Pf_func New(int id, byte[] name) {return new Lst_pfunc_lstx().Name_(name);}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bfr) {
		byte[] src_ttl_bry = Eval_argx(ctx, src, caller, self); if (Bry_.Len_eq_0(src_ttl_bry)) return;		// {{#lst:}} -> ""
		int args_len = self.Args_len();
		byte[] sect_exclude = Pf_func_.Eval_arg_or(ctx, src, caller, self, args_len, 0, Lst_pfunc_wkr.Null_arg);
		byte[] sect_replace = Pf_func_.Eval_arg_or(ctx, src, caller, self, args_len, 1, Lst_pfunc_wkr.Null_arg);
		new Lst_pfunc_wkr().Init_exclude(src_ttl_bry, sect_exclude, sect_replace).Exec(bfr, ctx);
	}
	public static final Lst_pfunc_lstx _ = new Lst_pfunc_lstx(); Lst_pfunc_lstx() {}
}
