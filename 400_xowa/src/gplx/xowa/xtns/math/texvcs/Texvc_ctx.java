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
