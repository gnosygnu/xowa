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
package gplx.xowa.parsers.miscs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*; import gplx.xowa.langs.*;
import gplx.xowa.parsers.tblws.*; import gplx.xowa.parsers.lnkis.*; import gplx.xowa.parsers.vnts.*;
public class Xop_pipe_lxr implements Xop_lxr {
	public int Lxr_tid() {return Xop_lxr_.Tid_pipe;}
	public void Init_by_wiki(Xowe_wiki wiki, Btrie_fast_mgr core_trie) {core_trie.Add(Byte_ascii.Pipe, this);}
	public void Init_by_lang(Xol_lang_itm lang, Btrie_fast_mgr core_trie) {}
	public void Term(Btrie_fast_mgr core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		int cur_stack_tid = ctx.Cur_tkn_tid(), rv = -1;
		switch (cur_stack_tid) {
			case Xop_tkn_itm_.Tid_brack_bgn:					// used for tmpl mode where full lnki_wkr is too heavyweight; matches "[ |"
				switch (ctx.Parse_tid()) {
					case Xop_parser_.Parse_tid_tmpl:
					case Xop_parser_.Parse_tid_page_tmpl:
						ctx.Subs_add(root, tkn_mkr.Txt(bgn_pos, cur_pos));
						break;
					case Xop_parser_.Parse_tid_page_wiki:		// should never happen?
						ctx.Subs_add(root, tkn_mkr.Pipe(bgn_pos, cur_pos));
						break;
					default: throw Err_.new_unhandled(ctx.Parse_tid());
				}
				return cur_pos;
			case Xop_tkn_itm_.Tid_tblw_tb:
			case Xop_tkn_itm_.Tid_tblw_tr:
				rv = Xop_tblw_lxr_ws.Make(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, Xop_tblw_wkr.Tblw_type_td, false);
				if (rv == Xop_tblw_lxr_ws.Tblw_ws_cell_pipe) {
					ctx.Subs_add(root, tkn_mkr.Pipe(bgn_pos, cur_pos));
					return cur_pos;
				}
				else
					return rv;
			case Xop_tkn_itm_.Tid_tblw_td:
			case Xop_tkn_itm_.Tid_tblw_th:
			case Xop_tkn_itm_.Tid_tblw_tc:
				rv = Xop_tblw_lxr_ws.Make(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, Xop_tblw_wkr.Tblw_type_td, false);
				if (rv != Xop_tblw_lxr_ws.Tblw_ws_cell_pipe) return rv;

				if (ctx.Tblw().Cell_pipe_seen()) {
					ctx.Subs_add(root, tkn_mkr.Pipe(bgn_pos, cur_pos));
					return cur_pos;
				}
				else {
					Xop_tblw_tkn cur_tkn = (Xop_tblw_tkn)ctx.Stack_get_typ(cur_stack_tid);
					Xop_tblw_wkr.Atrs_make(ctx, src, root, ctx.Tblw(), cur_tkn, Bool_.N);
					return cur_pos;
				}
			case Xop_tkn_itm_.Tid_lnki:
				Xop_lnki_tkn lnki = (Xop_lnki_tkn)ctx.Stack_get_last();	// BLOCK:invalid_ttl_check
				if (	lnki.Pipe_count_is_zero()
					&&	!Xop_lnki_wkr_.Parse_ttl(ctx, src, lnki, bgn_pos)) {
					ctx.Stack_pop_last();
					return Xop_lnki_wkr_.Invalidate_lnki(ctx, src, root, lnki, bgn_pos);
				}
				ctx.Subs_add(root, tkn_mkr.Pipe(bgn_pos, cur_pos));
				return cur_pos;
			default:
				ctx.Subs_add(root, tkn_mkr.Pipe(bgn_pos, cur_pos));
				return cur_pos;
		}
	}
	public static final Xop_pipe_lxr Instance = new Xop_pipe_lxr();
}
