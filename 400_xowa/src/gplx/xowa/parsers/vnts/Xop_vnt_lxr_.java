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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*; import gplx.xowa.langs.*;
import gplx.xowa.langs.vnts.*;
public class Xop_vnt_lxr_ {
	public static void Init(Xowe_wiki wiki) {
		Btrie_fast_mgr wiki_trie = wiki.Parser_mgr().Main().Wtxt_lxr_mgr().Trie();
		Object exists = wiki_trie.Match_bgn(Xop_vnt_lxr_.Hook_bgn, 0, Xop_vnt_lxr_.Hook_bgn.length);
		if (exists == null) {
			Xop_vnt_lxr_eqgt._.Init_by_wiki(wiki, wiki_trie);
			Xop_vnt_lxr_bgn._.Init_by_wiki(wiki, wiki_trie);
			new Xop_vnt_lxr_end().Init_by_wiki(wiki, wiki_trie);
			// Btrie_fast_mgr tmpl_trie = wiki.Parser_mgr().Main().Tmpl_trie();	// do not add to tmpl trie
			// Xop_vnt_lxr_bgn._.Init_by_wiki(wiki, tmpl_trie);
		}
	}
	public static final byte[] Hook_bgn = new byte[] {Byte_ascii.Dash, Byte_ascii.Curly_bgn}, Hook_end = new byte[] {Byte_ascii.Curly_end, Byte_ascii.Dash};
}
class Xop_vnt_lxr_eqgt implements Xop_lxr {
	public int Lxr_tid() {return Xop_lxr_.Tid_vnt_eqgt;}
	public void Init_by_wiki(Xowe_wiki wiki, Btrie_fast_mgr core_trie) {core_trie.Add(Hook, this);}
	public void Init_by_lang(Xol_lang lang, Btrie_fast_mgr core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		ctx.Subs_add_and_stack(root, tkn_mkr.Vnt_eqgt(bgn_pos, cur_pos));
		return cur_pos;
	}
	public void Term(Btrie_fast_mgr core_trie) {}
	public static final byte[] Hook = new byte[] {Byte_ascii.Eq, Byte_ascii.Gt};
	public static final Xop_vnt_lxr_eqgt _ = new Xop_vnt_lxr_eqgt(); Xop_vnt_lxr_eqgt() {}
}
