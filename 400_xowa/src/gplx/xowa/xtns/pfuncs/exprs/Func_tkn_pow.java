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
package gplx.xowa.xtns.pfuncs.exprs;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.commons.GfoDecimal;
import gplx.types.commons.GfoDecimalUtl;
import gplx.types.basics.utls.MathUtl;
import gplx.xowa.parsers.*;
class Func_tkn_pow extends Func_tkn_base {
	public Func_tkn_pow(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 2;}
	@Override public int Precedence()	{return 8;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		GfoDecimal rhs = val_stack.Pop();
		GfoDecimal lhs = val_stack.Pop();
		int rhs_int = rhs.ToInt();
		if ((double)rhs_int == rhs.ToDouble())	// exponent is integer; use decimal pow which does less casts to double
			val_stack.Push(lhs.Pow(rhs_int));
		else {
			double rslt = MathUtl.Pow(lhs.ToDouble(), rhs.ToDouble());
			if (DoubleUtl.IsNaN(rslt)) {
				shunter.Rslt_set(DoubleUtl.NaNBry);
				return false;
			}
			else
				val_stack.Push(GfoDecimalUtl.NewByDoubleThruStr(rslt));
		}
		return true;
	}
}
