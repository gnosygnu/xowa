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
class Val_stack {
	public void Clear() {stack_len = 0;}
	public int Len() {return stack_len;}
	public Decimal_adp Pop() {
		int stack_len_new = stack_len - 1;
		Decimal_adp rv = stack[stack_len_new];
		stack_len = stack_len_new;
		return rv;
	}
	public void Push(Decimal_adp v) {
		int stack_len_new = stack_len + 1;
		if (stack_len_new > stack_max) {
			stack_max = stack_len_new * 2;
			stack = (Decimal_adp[])Array_.Resize(stack, stack_max);
		}
		stack[stack_len] = v;
		stack_len = stack_len_new;
	}
	Decimal_adp[] stack = new Decimal_adp[0]; int stack_len = 0, stack_max = 0;
}
