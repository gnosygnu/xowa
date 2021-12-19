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
import gplx.types.basics.utls.ArrayUtl;
import gplx.types.commons.GfoDecimal;
class Val_stack {
	public void Clear() {stack_len = 0;}
	public int Len() {return stack_len;}
	public GfoDecimal Pop() {
		int stack_len_new = stack_len - 1;
		GfoDecimal rv = stack[stack_len_new];
		stack_len = stack_len_new;
		return rv;
	}
	public void Push(GfoDecimal v) {
		int stack_len_new = stack_len + 1;
		if (stack_len_new > stack_max) {
			stack_max = stack_len_new * 2;
			stack = (GfoDecimal[])ArrayUtl.Resize(stack, stack_max);
		}
		stack[stack_len] = v;
		stack_len = stack_len_new;
	}
	GfoDecimal[] stack = new GfoDecimal[0]; int stack_len = 0, stack_max = 0;
}
