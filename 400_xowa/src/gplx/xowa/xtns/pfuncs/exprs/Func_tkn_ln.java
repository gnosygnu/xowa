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
import gplx.types.basics.utls.MathUtl;
import gplx.xowa.parsers.*;
import gplx.xowa.langs.msgs.*;
class Func_tkn_ln extends Func_tkn_base {
	public Func_tkn_ln(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 1;}
	@Override public int Precedence()	{return 9;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		GfoDecimal val = val_stack.Pop();
		if (val.CompLte(0)) {shunter.Err_set(ctx, Xol_msg_itm_.Id_pfunc_expr_invalid_argument_ln); return false;}
		val = GfoDecimalUtl.NewByDouble(MathUtl.Log(val.ToDouble()));
		val = val.RoundToDefaultPrecision();// NOTE: needed for test; may need to apply to other ops; DATE:2020-03-24
		val_stack.Push(val);
		return true;
	}
}
