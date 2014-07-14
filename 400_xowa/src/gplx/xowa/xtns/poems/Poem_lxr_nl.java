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
package gplx.xowa.xtns.poems; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.btries.*;
public class Poem_lxr_nl implements Xop_lxr {
	public byte Lxr_tid() {return Xop_lxr_.Tid_nl_poem;}
	public void Init_by_wiki(Xow_wiki wiki, Btrie_fast_mgr core_trie) {core_trie.Add(Byte_ascii.NewLine, this);}
	public void Init_by_lang(Xol_lang lang, Btrie_fast_mgr core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		if (bgn_pos == Xop_parser_.Doc_bgn_bos) return ctx.Lxr_make_txt_(cur_pos); // simulated nl at beginning of every parse
		ctx.Subs_add(root, tkn_mkr.Xnde(bgn_pos, cur_pos).Tag_(Xop_xnde_tag_.Tag_br));	// add <br/>
		ctx.Subs_add(root, tkn_mkr.NewLine(cur_pos, cur_pos, Xop_nl_tkn.Tid_char, 1));	// add \n; for pretty-printing
		return cur_pos;
	}
	public static final Poem_lxr_nl _ = new Poem_lxr_nl(); Poem_lxr_nl() {}
}
