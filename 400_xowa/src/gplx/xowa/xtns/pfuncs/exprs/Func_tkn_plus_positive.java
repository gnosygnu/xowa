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
class Func_tkn_plus_positive extends Func_tkn_base {
	Func_tkn_plus_positive(String v) {this.Ctor(v);}
	@Override public int ArgCount()		{return 1;}
	@Override public int Precedence()	{return 10;}
	@Override public boolean Calc_hook(Xop_ctx ctx, Pfunc_expr_shunter shunter, Val_stack val_stack) {return true;}// effectively a noop
	public static final    Func_tkn_plus_positive Instance = new Func_tkn_plus_positive("+");
}
