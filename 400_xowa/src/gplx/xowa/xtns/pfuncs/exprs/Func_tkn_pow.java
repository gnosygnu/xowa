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
import gplx.xowa.parsers.*;
class Func_tkn_pow extends Func_tkn_base {
	public Func_tkn_pow(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 2;}
	@Override public int Precedence()	{return 8;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		Decimal_adp rhs = val_stack.Pop();
		Decimal_adp lhs = val_stack.Pop();
		int rhs_int = rhs.To_int();	
		if ((double)rhs_int == rhs.To_double())	// exponent is integer; use decimal pow which does less casts to double
			val_stack.Push(lhs.Pow(rhs_int));
		else {
			double rslt = Math_.Pow(lhs.To_double(), rhs.To_double());
			if (Double_.IsNaN(rslt)) {
				shunter.Rslt_set(Double_.NaN_bry);
				return false;
			}
			else
				val_stack.Push(Decimal_adp_.double_thru_str_(rslt));
		}
		return true;
	}
}
