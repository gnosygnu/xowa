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
class Func_tkn_stack {
	private Func_tkn[] ary = new Func_tkn[0]; private int len = 0, max = 0;
	public void Clear() {len = 0;}
	public int Len() {return len;}
	public Func_tkn Get_at_last() {return len == 0 ? null : ary[len - 1];}
	public Func_tkn Pop() {
		int new_len = len - 1;
		Func_tkn rv = ary[new_len];
		len = new_len;
		return rv;
	}
	public void Push(Func_tkn v) {
		int new_len = len + 1;
		if (new_len > max) {
			max = new_len * 2;
			ary = (Func_tkn[])Array_.Resize(ary, max);
		}
		ary[len] = v;
		len = new_len;
	}
}
