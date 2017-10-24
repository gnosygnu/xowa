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
package gplx.xowa.xtns.pfuncs.ifs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.xtns.pfuncs.exprs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pfunc_ifexpr extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_xtn_ifexpr;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_ifexpr().Name_(name);}
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {			
		int self_args_len = self.Args_len();
		byte[] argx = Eval_argx(ctx, src, caller, self); if (argx == null) return;
		Pfunc_expr_shunter shunter = ctx.Tmp_mgr().Expr_shunter();
		Decimal_adp result = shunter.Evaluate(ctx, argx);
		boolean is_nan = result == Pfunc_expr_shunter.Null_rslt;
		if (is_nan && shunter.Err().Len() > 0) {
			bfr.Add_bfr_and_preserve(shunter.Err());
			shunter.Err().Clear();
		}
		else {
			if (is_nan || result.To_int() == 0)
				bfr.Add(Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 1));
			else
				bfr.Add(Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 0));
		}
	}
}	
