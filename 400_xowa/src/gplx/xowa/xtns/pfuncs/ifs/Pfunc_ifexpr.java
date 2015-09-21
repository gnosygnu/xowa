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
import gplx.xowa.xtns.pfuncs.exprs.*;
import gplx.xowa.langs.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pfunc_ifexpr extends Pf_func_base {
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bb) {			
		int self_args_len = self.Args_len();
		byte[] val_dat_ary = Eval_argx(ctx, src, caller, self);
		if (val_dat_ary == null) return;
		Decimal_adp result = shunter.Evaluate(ctx, val_dat_ary);
		boolean is_nan = result == Pfunc_expr_shunter.Null_rslt;
		if (is_nan && shunter.Err().Len() > 0) {
			bb.Add_bfr_and_preserve(shunter.Err());
			shunter.Err().Clear();
		}
		else {
			if (is_nan || result.To_int() == 0)
				bb.Add(Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 1));
			else
				bb.Add(Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 0));
		}
	}
	Pfunc_expr_shunter shunter = Pfunc_expr_shunter._;
	@Override public int Id() {return Xol_kwd_grp_.Id_xtn_ifexpr;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_ifexpr().Name_(name);}
}	
