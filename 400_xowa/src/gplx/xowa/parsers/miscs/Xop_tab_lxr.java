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
import gplx.core.btries.*;
public class Xop_tab_lxr implements Xop_lxr {
	public byte Lxr_tid() {return Xop_lxr_.Tid_tab;}
	public void Init_by_wiki(Xowe_wiki wiki, Btrie_fast_mgr core_trie) {
		core_trie.Add(Byte_ascii.Tab, this);
		core_trie.Add(Xop_tab_tkn.Bry_tab_ent, this);
	}	
	public void Init_by_lang(Xol_lang lang, Btrie_fast_mgr core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		cur_pos = Bry_finder.Find_fwd_while(src, cur_pos, src_len, Byte_ascii.Tab);
		src[bgn_pos] = Byte_ascii.Tab; // HACK: SEE:NOTE_1:tabs
		for (int i = bgn_pos + 1; i < cur_pos; i++)	
			src[i] = Byte_ascii.Space;
		ctx.Subs_add(root, tkn_mkr.Tab(bgn_pos, cur_pos));
		return cur_pos;
	}
	public static final Xop_tab_lxr _ = new Xop_tab_lxr();
}
