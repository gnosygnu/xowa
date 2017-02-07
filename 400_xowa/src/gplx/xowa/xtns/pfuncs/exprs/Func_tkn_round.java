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
class Func_tkn_round extends Func_tkn_base {
	public Func_tkn_round(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 2;}
	@Override public int Precedence()	{return 5;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {
		Decimal_adp rhs = val_stack.Pop();
		Decimal_adp lhs = val_stack.Pop();
		if (rhs.Comp_gt(16)) {
			rhs = Decimal_adp_.int_(16);
		}
		else if (rhs.Comp_lt(-16)) {
			rhs = Decimal_adp_.int_(-16);
		}
		Decimal_adp val = lhs.Round_old(rhs.To_int());
		if (val.To_double() == 0)	// NOTE: must explicitly check for zero, else "0.0" will be pushed onto stack; EXE: {{#expr: 0 round 1}}; DATE:2013-11-09
			val_stack.Push(Decimal_adp_.Zero);
		else
			val_stack.Push(val);
		return true;
	}
}
