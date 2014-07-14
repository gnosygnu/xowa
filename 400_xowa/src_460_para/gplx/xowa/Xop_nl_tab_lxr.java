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
import gplx.core.btries.*;
class Xop_nl_tab_lxr implements Xop_lxr {
	public byte Lxr_tid() {return Xop_lxr_.Tid_nl_tab;}
	public void Init_by_wiki(Xow_wiki wiki, Btrie_fast_mgr core_trie) {core_trie.Add(Hook_nl_tab, this);} private static final byte[] Hook_nl_tab = new byte[] {Byte_ascii.NewLine, Byte_ascii.Tab};
	public void Init_by_lang(Xol_lang lang, Btrie_fast_mgr core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		int non_ws_pos = Bry_finder.Find_fwd_while_space_or_tab(src, cur_pos, src_len);
		if (non_ws_pos < src_len) {	// bounds check
			Btrie_slim_mgr tblw_trie = ctx.App().Utl_trie_tblw_ws();
			Object tblw_obj = tblw_trie.Match_bgn(src, non_ws_pos, src_len);
			if (tblw_obj != null) {
				Xop_tblw_ws_itm tblw_itm = (Xop_tblw_ws_itm)tblw_obj;
				byte itm_type = tblw_itm.Tblw_type();
				switch (itm_type) {
					case Xop_tblw_ws_itm.Type_nl:		// ignore nl
					case Xop_tblw_ws_itm.Type_xnde:		// ignore xnde
						break;
					default: {							// handle tblw
						int tblw_rv = ctx.Tblw().Make_tkn_bgn(ctx, tkn_mkr, root, src, src_len, bgn_pos, non_ws_pos + tblw_itm.Hook_len(), false, itm_type, Xop_tblw_wkr.Called_from_pre, -1, -1);
						if (tblw_rv != -1)	// \n\s| is valid tblw tkn and processed; otherwise fall through; 
							return tblw_rv;
						break;
					}
				}
			}
		}
		if (bgn_pos != Xop_parser_.Doc_bgn_bos)		// don't add \n if BOS; EX: "<BOS> a" should be " ", not "\n "
			ctx.Subs_add(root, tkn_mkr.NewLine(bgn_pos, bgn_pos + 1, Xop_nl_tkn.Tid_char, 1));
		ctx.Subs_add(root, tkn_mkr.Tab(cur_pos - 1, cur_pos));
		return cur_pos;
	}
	public static final Xop_nl_tab_lxr _ = new Xop_nl_tab_lxr(); Xop_nl_tab_lxr() {}
}
