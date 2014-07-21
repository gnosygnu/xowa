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
public class Poem_lxr_pre implements Xop_lxr {
	public byte Lxr_tid() {return Xop_lxr_.Tid_poem;}
	public void Init_by_wiki(Xow_wiki wiki, Btrie_fast_mgr core_trie) {core_trie.Add(Hook_ary, this);}
	public void Init_by_lang(Xol_lang lang, Btrie_fast_mgr core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		int space_count = 1;
		while (cur_pos < src_len) {
			if (src[cur_pos++] == Byte_ascii.Space) {
				++space_count;
			}
			else {
				--cur_pos;
				break;
			}
		}
		if (bgn_pos != Xop_parser_.Doc_bgn_bos) {	// do not add xnde/nl if \n is BOS \n; PAGE:en.w:Teresa of Ávila; "<poem>\n\s\s"
			ctx.Subs_add(root, tkn_mkr.Xnde(cur_pos, cur_pos).Tag_(Xop_xnde_tag_.Tag_br));
			ctx.Subs_add(root, tkn_mkr.NewLine(cur_pos, cur_pos, Xop_nl_tkn.Tid_char, 1));
		}
		for (int i = 0; i < space_count; i++)
			ctx.Subs_add(root, tkn_mkr.Amp_num(bgn_pos + 1, cur_pos, 160, Nbsp_bry));
		return cur_pos;
	}
	private static final byte[] Nbsp_bry = gplx.intl.Utf16_.Encode_int_to_bry(160);
	private static final byte[] Hook_ary = new byte[] {Byte_ascii.NewLine, Byte_ascii.Space};
	public static final Poem_lxr_pre _ = new Poem_lxr_pre(); Poem_lxr_pre() {}
}
