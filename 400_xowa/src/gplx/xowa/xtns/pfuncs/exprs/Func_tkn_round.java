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
import gplx.types.commons.GfoDecimal;
import gplx.types.commons.GfoDecimalUtl;
import gplx.xowa.parsers.*;
class Func_tkn_round extends Func_tkn_base {
	public Func_tkn_round(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 2;}
	@Override public int Precedence()	{return 5;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		GfoDecimal rhs = val_stack.Pop();
		GfoDecimal lhs = val_stack.Pop();
		if (rhs.CompGt(16)) {
			rhs = GfoDecimalUtl.NewByInt(16);
		}
		else if (rhs.CompLt(-16)) {
			rhs = GfoDecimalUtl.NewByInt(-16);
		}
		GfoDecimal val = lhs.RoundNative(rhs.ToInt());
		if (val.ToDouble() == 0)	// NOTE: must explicitly check for zero, else "0.0" will be pushed onto stack; EXE: {{#expr: 0 round 1}}; DATE:2013-11-09
			val_stack.Push(GfoDecimalUtl.Zero);
		else
			val_stack.Push(val);
		return true;
	}
}
