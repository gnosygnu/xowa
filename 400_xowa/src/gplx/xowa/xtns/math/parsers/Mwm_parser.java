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
package gplx.xowa.xtns.math.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.math.*;
import gplx.core.btries.*;
class Mwm_parser {
	public void Parse(Mwm_tkn__root root, byte[] src) {
		int src_len = src.length;
		Btrie_fast_mgr trie = Mwm_trie_bldr.new_();
		Parse(trie, root, new Mwm_ctx(), src, src_len, 0, src_len);
	}
	private int Parse(Btrie_fast_mgr trie, Mwm_tkn__root root, Mwm_ctx ctx, byte[] src, int src_len, int bgn_pos, int end_pos) {
		int pos = bgn_pos;
		int txt_bgn = pos, txt_uid = -1;
		byte b = src[pos];
		while (true) {
			Object o = trie.Match_bgn_w_byte(b, src, pos, src_len);
			if (o == null)				// no lxr found; char is txt; increment pos
				pos++;
			else {						// lxr found
				Mwm_lxr lxr = (Mwm_lxr)o;
				if (txt_bgn != pos)	txt_uid = Txt_calc(ctx, root, src, src_len, pos, txt_bgn, txt_uid);// chars exist between pos and txt_bgn; make txt_tkn;						
				pos = lxr.Make_tkn(ctx, root, src, src_len, pos, trie.Match_pos());
				if (pos > 0) {txt_bgn = pos; txt_uid = -1;}	// reset txt_tkn
			}
			if (pos == end_pos) break;
			b = src[pos];
		}
		if (txt_bgn != pos) txt_uid = Txt_calc(ctx, root, src, src_len, src_len, txt_bgn, txt_uid);
		return pos;
	}
	private static int Txt_calc(Mwm_ctx ctx, Mwm_tkn__root root, byte[] src, int src_len, int bgn_pos, int txt_bgn, int txt_uid) {
		if (txt_uid == -1)						// no existing txt_tkn; create new one
			txt_uid = root.Regy__add(Mwm_tkn_.Tid__text, txt_bgn, bgn_pos, null);
		else									// existing txt_tkn; happens for false matches; EX: abc[[\nef[[a]]; see NOTE_1
			root.Regy__update_end(txt_uid, bgn_pos);
		return txt_uid;
	}
}
