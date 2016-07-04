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
class Func_tkn_stack {
	public void Clear() {stack_len = 0;}
	public int Len() {return stack_len;}
	public Func_tkn GetLast() {return stack_len == 0 ? null : stack[stack_len - 1];}
	public Func_tkn Pop() {
		int stack_len_new = stack_len - 1;
		Func_tkn rv = stack[stack_len_new];
		stack_len = stack_len_new;
		return rv;
	}
	public void Push(Func_tkn v) {
		int stack_len_new = stack_len + 1;
		if (stack_len_new > stack_max) {
			stack_max = stack_len_new * 2;
			stack = (Func_tkn[])Array_.Resize(stack, stack_max);
		}
		stack[stack_len] = v;
		stack_len = stack_len_new;
	}
	Func_tkn[] stack = new Func_tkn[0]; int stack_len = 0, stack_max = 0;
}
