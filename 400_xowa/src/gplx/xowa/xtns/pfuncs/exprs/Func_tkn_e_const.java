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
import gplx.types.commons.GfoDecimalUtl;
import gplx.xowa.parsers.*;
class Func_tkn_e_const extends Func_tkn_base {
	public Func_tkn_e_const(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 0;}
	@Override public int Precedence()	{return 0;}
	@Override public boolean Func_is_const() {return true;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		val_stack.Push(GfoDecimalUtl.E);
		return true;
	}
	public static final Func_tkn_e_const Instance = new Func_tkn_e_const("e");
}
