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
package gplx.xowa.xtns.math.texvcs.lxrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.math.*; import gplx.xowa.xtns.math.texvcs.*;
import gplx.core.btries.*; import gplx.xowa.xtns.math.texvcs.tkns.*;
public class Texvc_lxr_trie_bldr {
	public static Btrie_fast_mgr new_(Texvc_tkn_mkr tkn_mkr) {// REF.MW:/Math/textvccheck/lexer.mll
		tkn_mkr.Reg_singleton(Texvc_tkn_.Tid__text, new Texvc_tkn__leaf_raw());
		Btrie_fast_mgr rv = Btrie_fast_mgr.cs();
		Add_lxr				(rv, tkn_mkr, new Texvc_lxr__backslash()						, "\\");
		Add_lxr				(rv, tkn_mkr, new Texvc_lxr__curly_bgn()						, "{");
		Add_lxr				(rv, tkn_mkr, new Texvc_lxr__curly_end()						, "}");
		Add_lxr_leaf		(rv, tkn_mkr, Texvc_tkn_.Tid__brack_bgn							, "[");
		Add_lxr_leaf		(rv, tkn_mkr, Texvc_tkn_.Tid__brack_end							, "]");
		Add_lxr_leaf		(rv, tkn_mkr, new Texvc_lxr__ws(), Texvc_tkn_.Tid__ws			, " ", "\t", "\n", "\r");
		Add_lxr_leaf		(rv, tkn_mkr, Texvc_tkn_.Tid__literal							, ">", "<", "~");
		Add_lxr_leaf		(rv, tkn_mkr, Texvc_tkn_.Tid__literal							, ",", ":", ";", "?", "!", "'");	// literal_uf_lt
		Add_lxr_leaf		(rv, tkn_mkr, Texvc_tkn_.Tid__literal							, "+", "-", "*", "=");				// literal_uf_op
		Add_lxr_leaf		(rv, tkn_mkr, Texvc_tkn_.Tid__delimiter							, "(", ")", ".");					// delimiter_uf_lt
		Add_lxr_leaf_repl	(rv, tkn_mkr, Texvc_tkn_.Tid__literal							, "%", "\\%");
		Add_lxr_leaf_repl	(rv, tkn_mkr, Texvc_tkn_.Tid__literal							, "$", "\\$");
		Add_lxr_leaf		(rv, tkn_mkr, Texvc_tkn_.Tid__literal							, "\\,", "\\ ", "\\;", "\\!");
		Add_lxr_leaf		(rv, tkn_mkr, Texvc_tkn_.Tid__delimiter							, "\\{", "\\}", "\\|");
		Add_lxr_leaf		(rv, tkn_mkr, Texvc_tkn_.Tid__literal							, "\\_", "\\#", "\\%", "\\$", "\\&");
		Add_lxr_leaf		(rv, tkn_mkr, Texvc_tkn_.Tid__next_cell							, "%");
		Add_lxr_leaf		(rv, tkn_mkr, Texvc_tkn_.Tid__next_row							, "\\\\");
		Add_lxr_leaf		(rv, tkn_mkr, Texvc_tkn_.Tid__literal							, "~", ">", "<");
		Add_lxr_leaf		(rv, tkn_mkr, Texvc_tkn_.Tid__sup								, "^");
		Add_lxr_leaf		(rv, tkn_mkr, Texvc_tkn_.Tid__sub								, "_");
		return rv;
	}
	private static void Add_lxr(Btrie_fast_mgr trie, Texvc_tkn_mkr tkn_mkr, Texvc_lxr lxr, String... ary) {
		for (String itm : ary)
			trie.Add(itm, lxr);
	}
	private static void Add_lxr_leaf(Btrie_fast_mgr trie, Texvc_tkn_mkr tkn_mkr, int tid, String... ary) {
		Add_lxr_leaf(trie, tkn_mkr, new Texvc_lxr__leaf(tid), tid, new Texvc_tkn__leaf_raw(), ary);
	}
	private static void Add_lxr_leaf(Btrie_fast_mgr trie, Texvc_tkn_mkr tkn_mkr, Texvc_lxr lxr, int tkn_tid, String... ary) {
		Add_lxr_leaf(trie, tkn_mkr, lxr, tkn_tid, new Texvc_tkn__leaf_raw(), ary);
	}
	private static void Add_lxr_leaf_repl(Btrie_fast_mgr trie, Texvc_tkn_mkr tkn_mkr, int tid, String src, String trg) {
		Add_lxr_leaf(trie, tkn_mkr, new Texvc_lxr__leaf(tid), tid, new Texvc_tkn__leaf_repl(tid, Bry_.new_u8(trg)), src);
	}
	private static void Add_lxr_leaf(Btrie_fast_mgr trie, Texvc_tkn_mkr tkn_mkr, Texvc_lxr lxr, int tkn_tid, Texvc_tkn proto, String... ary) {
		tkn_mkr.Reg_singleton(tkn_tid, proto);
		for (String itm : ary)
			trie.Add(itm, lxr);
	}
}
