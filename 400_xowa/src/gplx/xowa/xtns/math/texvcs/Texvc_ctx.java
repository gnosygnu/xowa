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
import gplx.core.primitives.*; import gplx.core.btries.*;
import gplx.xowa.xtns.math.texvcs.lxrs.*; import gplx.xowa.xtns.math.texvcs.tkns.*; import gplx.xowa.xtns.math.texvcs.funcs.*;
public class Texvc_ctx {
	public Texvc_ctx() {
		this.lxr_trie = Texvc_lxr_trie_bldr.new_(tkn_mkr);
		this.func_regy = Texvc_func_regy.new_(tkn_mkr);
		this.scope_regy = Texvc_scope_regy.new_();
	}
	public Btrie_fast_mgr Lxr_trie() {return lxr_trie;} private final Btrie_fast_mgr lxr_trie;
	public Texvc_tkn_mkr Tkn_mkr() {return tkn_mkr;} private final Texvc_tkn_mkr tkn_mkr = new Texvc_tkn_mkr();
	public Texvc_func_regy Func_regy() {return func_regy;} private final Texvc_func_regy func_regy;
	public Texvc_scope_regy Scope_regy() {return scope_regy;} private final Texvc_scope_regy scope_regy;
	public Int_ary Stack() {return stack;} private final Int_ary stack = new Int_ary(4);
	public void Clear() {
		stack.Clear();
	}
}
