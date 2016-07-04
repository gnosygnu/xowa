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
