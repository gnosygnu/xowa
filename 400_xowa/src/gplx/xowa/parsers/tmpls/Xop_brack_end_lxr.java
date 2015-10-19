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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*; import gplx.xowa.langs.*;
public class Xop_brack_end_lxr implements Xop_lxr {
	public int Lxr_tid() {return Xop_lxr_.Tid_brack_end;}
	public void Init_by_wiki(Xowe_wiki wiki, Btrie_fast_mgr core_trie) {core_trie.Add(Xop_tkn_.Lnki_end, this);}
	public void Init_by_lang(Xol_lang_itm lang, Btrie_fast_mgr core_trie) {}
	public void Term(Btrie_fast_mgr core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		int acs_pos = ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_brack_bgn);
		if (acs_pos != -1 && ctx.Cur_tkn_tid() != Xop_tkn_itm_.Tid_tmpl_curly_bgn)	// NOTE: do not pop tkn if inside tmpl; EX: [[a|{{#switch:{{{1}}}|b=c]]|d=e]]|f]]}}
			ctx.Stack_pop_til(root, src, acs_pos, true, bgn_pos, cur_pos, Xop_tkn_itm_.Tid_tmpl_curly_bgn);
		Xop_tkn_itm tkn = tkn_mkr.Brack_end(bgn_pos, cur_pos);
		ctx.Subs_add(root, tkn);
		return cur_pos;
	}
	public static final Xop_brack_end_lxr Instance = new Xop_brack_end_lxr(); Xop_brack_end_lxr() {}
}
