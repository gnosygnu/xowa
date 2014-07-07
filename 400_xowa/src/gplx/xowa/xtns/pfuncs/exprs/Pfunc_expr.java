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
package gplx.xowa.xtns.pfuncs.exprs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
public class Pfunc_expr extends Pf_func_base {
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bb) {
		byte[] val_dat_ary = Eval_argx(ctx, src, caller, self); if (val_dat_ary == Bry_.Empty) return;
		DecimalAdp rslt = shunter.Evaluate(ctx, val_dat_ary);	// NOTE: php uses "float" but really is a double; http://www.php.net/manual/en/language.types.float.php
		if (rslt == Pfunc_expr_shunter.Null_rslt) {
			bb.Add_bfr_and_preserve(shunter.Err());
//				ctx.Msg_log().Add_args(src, self.Src_bgn(), self.Src_end(), Pfunc_expr_msg.Unknown);
			shunter.Err().Clear();
		}
		else
			bb.Add_str(rslt.XtoStr());
	}
	Pfunc_expr_shunter shunter = Pfunc_expr_shunter._;
	@Override public int Id() {return Xol_kwd_grp_.Id_xtn_expr;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_expr().Name_(name);}
}
class Pfunc_expr_msg {
	public static final Gfo_msg_grp Nde = Gfo_msg_grp_.new_(Xoa_app_.Nde, "expr");
	public static final Gfo_msg_itm Unknown = Gfo_msg_itm_.new_warn_(Nde, "unknown", "unknown");
}
