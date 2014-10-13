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
package gplx.xowa; import gplx.*;
import gplx.core.btries.*; import gplx.xowa.parsers.paras.*;
class Xop_eq_lxr implements Xop_lxr {
	public Xop_eq_lxr(boolean tmpl_mode) {this.tmpl_mode = tmpl_mode;}	boolean tmpl_mode;
	public byte Lxr_tid() {return Xop_lxr_.Tid_eq;}
	public void Init_by_wiki(Xow_wiki wiki, Btrie_fast_mgr core_trie) {core_trie.Add(Byte_ascii.Eq, this);}
	public void Init_by_lang(Xol_lang lang, Btrie_fast_mgr core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		cur_pos = Bry_finder.Find_fwd_while(src, cur_pos, src_len, Byte_ascii.Eq); // gobble up eq; "==" should produce 1 eq_tkn with len of 2, not 2 eq_tkn with len of 1; DATE:2014-04-17
		int eq_len = cur_pos - bgn_pos;
		boolean hdr_like = false;
		if (tmpl_mode) {
			Xop_tkn_itm owner = ctx.Stack_get_last();									// beginning of "is == part of a hdr tkn sequence?"; DATE:2014-02-09
			if (	owner != null && owner.Tkn_tid() == Xop_tkn_itm_.Tid_tmpl_curly_bgn	// inside curly
				&&	eq_len > 1) {														// only skip if at least "=="; don't want to skip "=" which could be kv delimiter; DATE:2014-04-17
				int prv_pos = bgn_pos - 1;
				if (prv_pos > -1 && src[prv_pos] == Byte_ascii.NewLine)					// is prv char \n; EX: "\n==="
					hdr_like = true;
				else {
					int eol_pos = Bry_finder.Find_fwd_while_space_or_tab(src, cur_pos, src_len);	// skip trailing ws; EX: "== \n"; PAGE:nl.q:Geert_Wilders; DATE:2014-06-05
					if (	eol_pos == src_len											// eos
						||	src[eol_pos] == Byte_ascii.NewLine							// cur_pos is \n; EX: "===\n"
						) {
						hdr_like = true;
						cur_pos = eol_pos;
					}
				}
				if (hdr_like)															// ignore hdr tkn;
					return ctx.Lxr_make_txt_(cur_pos);
			}
			ctx.Subs_add(root, tkn_mkr.Eq(bgn_pos, cur_pos));
			return cur_pos;
		}

		// wiki_mode; chk if hdr exists
		int stack_pos = ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_hdr);
		if (stack_pos == Xop_ctx.Stack_not_found) {	// no hdr; make eq_tkn and return;
			ctx.Subs_add(root, tkn_mkr.Eq(bgn_pos, cur_pos));
			return cur_pos;
		}
		int ws_end = Bry_finder.Find_fwd_while_space_or_tab(src, cur_pos, src_len);
		hdr_like = ws_end == src_len || src[ws_end] == Byte_ascii.NewLine;				// hdr_like if next char \n or eos
		if (!hdr_like) {
			int ctg_end = Xop_nl_lxr.Scan_fwd_for_ctg(ctx, src, cur_pos, src_len);		// check if ==[[Category:A]]; DATE:2014-04-17
			if (	ctg_end != Bry_.NotFound) {										// [[Category: found
				ctg_end = Bry_finder.Find_fwd(src, Xop_tkn_.Lnki_end, ctg_end, src_len);
				if (ctg_end != Bry_.NotFound) {										// ]] found; note that this should do more validation; EX: [[Category:]] should not be valid; DATE:2014-04-17
					ctg_end += Xop_tkn_.Lnki_end_len;
					ctg_end = Bry_finder.Find_fwd_while_space_or_tab(src, ctg_end, src_len);
					if (ctg_end == src_len || src[ctg_end] == Byte_ascii.NewLine)		// hdr_like if ]]\n after [[Category:A]]
						hdr_like = true;
				}
			}
		}
		if (hdr_like) {
			cur_pos = ws_end;
			return ctx.Hdr().Make_tkn_end(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, stack_pos, eq_len);
		}

		// = is just text; create = tkn and any other ws tkns; NOTE: also create ws tkns if scanned; EX: "== a ===  bad"; create "===" and " "; position at "b"
		ctx.Subs_add(root, tkn_mkr.Eq(bgn_pos, cur_pos, eq_len));
		return cur_pos;
	}
}
