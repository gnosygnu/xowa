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
package gplx.xowa.xtns.pfuncs.exprs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.parsers.*; import gplx.xowa.langs.msgs.*;
class Func_tkn_mod extends Func_tkn_base {
	public Func_tkn_mod(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 2;}
	@Override public int Precedence()	{return 7;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		// must convert to int else issues with {{#expr:0.00999999mod10}} and {{USCensusPop|1960=763956|1970=756510}}; REF: http://php.net/manual/en/language.operators.arithmetic.php: "Operands of modulus are converted to integers (by stripping the decimal part) before processing"
		// must convert to long else issues with (39052000900/1) mod 100 which should be 0, not 47; JAVA does not fail int conversion, and instead converts to Int_.Max_value; EX: de.w:Quijano_(Piélagos)
		long rhs = ((Decimal_adp)val_stack.Pop()).To_long();
		long lhs = ((Decimal_adp)val_stack.Pop()).To_long();
		if (rhs == 0) {
			shunter.Err_set(ctx, Xol_msg_itm_.Id_pfunc_expr_division_by_zero);
			return false;
		}
		val_stack.Push(Decimal_adp_.long_(lhs % rhs));
		return true;
	}
}
