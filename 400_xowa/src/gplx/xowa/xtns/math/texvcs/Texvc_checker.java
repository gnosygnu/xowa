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
package gplx.xowa.xtns.math.texvcs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.math.*;
import gplx.xowa.xtns.math.texvcs.tkns.*; import gplx.xowa.xtns.math.texvcs.funcs.*;
public class Texvc_checker {
	private final    List_adp func_stack = List_adp_.New();
	private Texvc_root root; private int root_idx, root_len;
	private boolean fail;
	public void Check(byte[] src, Texvc_root root) {
		func_stack.Clear();
		this.root = root;
		this.root_len = root.Subs__len();
		this.root_idx = 0;
		this.fail = false;
		while (root_idx < root_len) {
			if (fail) break;
			Texvc_tkn tkn = root.Subs__get_at(root_idx);
			if (tkn.Tid() == Texvc_tkn_.Tid__func) {
				Check_func(tkn);
			}				
			++root_idx;
		}
	}
	private void Check_func(Texvc_tkn tkn) {
		Texvc_tkn__func func_tkn = (Texvc_tkn__func)tkn;
		Texvc_func_itm func_itm = func_tkn.Func_itm();
		int arg_len = func_itm.Args(); if (arg_len == 0) return;	// arg-less funcs don't need to be checked;
		func_stack.Add(func_tkn);
		int arg_idx = 0;
		while (arg_idx < arg_len) {
			++root_idx;
			Texvc_tkn arg_tkn = root.Subs__get_at(root_idx);
			// skip ws, dlm, lit
			int arg_tid = arg_tkn.Tid();
//				switch (arg_tid) {
//					case Texvc_tkn_.Tid__func:
//					case Texvc_tkn_.Tid__curly:
					root.Regy__move(arg_tkn.Uid(), func_tkn.Uid());
					--root_len;
					--root_idx;
					++arg_idx;
					if (arg_tid == Texvc_tkn_.Tid__func) {
						Check_func(arg_tkn);
					}
//						break;
//				}
		}
		List_adp_.Pop_last(func_stack);
	}
}
