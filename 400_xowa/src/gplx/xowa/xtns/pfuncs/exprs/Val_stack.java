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
