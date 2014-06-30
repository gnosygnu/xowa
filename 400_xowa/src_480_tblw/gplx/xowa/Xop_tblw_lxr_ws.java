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
public class Xop_tblw_lxr_ws {
	public static int Make(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos, byte wlxr_type, boolean called_from_pre) {
		int rv = Xop_tblw_lxr.Handle_bang(wlxr_type, ctx, ctx.Tkn_mkr(), root, src, src_len, bgn_pos, cur_pos);
		if (rv != Xop_tblw_lxr.Continue) return rv;
		rv = Xop_tblw_lxr.Handle_lnki(wlxr_type, ctx, ctx.Tkn_mkr(), root, src, src_len, bgn_pos, cur_pos);
		if (rv != Xop_tblw_lxr.Continue) return rv;
		if (!called_from_pre) {	// skip if called from pre, else will return text, since pre_lxr has not created \n tkn yet; EX: "\n ! a"; DATE:2014-02-14
			// find first non-ws tkn; check if nl or para
			int root_subs_len = root.Subs_len();
			int tkn_idx = root_subs_len - 1;
			boolean loop = true, nl_found = false;
			while (loop) {
				if (tkn_idx < 0) break;
				Xop_tkn_itm tkn = root.Subs_get(tkn_idx);
				switch (tkn.Tkn_tid()) {
					case Xop_tkn_itm_.Tid_space: case Xop_tkn_itm_.Tid_tab:	// ws: keep moving backwards					
						tkn_idx--;
						break;
					case Xop_tkn_itm_.Tid_newLine:
					case Xop_tkn_itm_.Tid_para:
						loop = false;
						nl_found = true;
						break;
					default:
						loop = false;
						break;
				}
			}
			if (tkn_idx == -1) {								// bos reached; all tkns are ws;
				if (wlxr_type == Xop_tblw_wkr.Tblw_type_tb) {	// wlxr_type is {|;
					root.Subs_del_after(0);						// trim
					return ctx.Tblw().Make_tkn_bgn(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, false, wlxr_type, Xop_tblw_wkr.Called_from_general, -1, -1);	// process {|
				}
				else											// wlxr_type is something else, but invalid since no containing {|
					return ctx.Lxr_make_txt_(cur_pos);
			}

			if (!nl_found && wlxr_type == Xop_tblw_wkr.Tblw_type_td)	// | but no nl; return control to pipe_lxr for further processing
				return Tblw_ws_cell_pipe;
			if (nl_found)
				root.Subs_del_after(tkn_idx);
		}
		return ctx.Tblw().Make_tkn_bgn(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, false, wlxr_type, Xop_tblw_wkr.Called_from_general, -1, -1);
	}
	public static final byte[] Hook_tb = Bry_.new_ascii_("{|"), Hook_te = Bry_.new_ascii_("|}"), Hook_tr = Bry_.new_ascii_("|-")
		, Hook_th = Bry_.new_ascii_("!"), Hook_tc = Bry_.new_ascii_("|+");
	public static final int Tblw_ws_cell_pipe = -1;
}
class Xop_tblw_ws_itm {
	public byte Tblw_type() {return tblw_type;} private byte tblw_type;
	public int Hook_len() {return hook_len;} private int hook_len;
	public Xop_tblw_ws_itm(byte tblw_type, int hook_len) {this.tblw_type = tblw_type; this.hook_len = hook_len;}

	public static final byte Type_tb = Xop_tblw_wkr.Tblw_type_tb, Type_te = Xop_tblw_wkr.Tblw_type_te, Type_tr = Xop_tblw_wkr.Tblw_type_tr, Type_tc = Xop_tblw_wkr.Tblw_type_tc
		, Type_th = Xop_tblw_wkr.Tblw_type_th, Type_td = Xop_tblw_wkr.Tblw_type_td, Type_nl = 16, Type_xnde = 17;
	public static ByteTrieMgr_slim trie_() {// MW.REF:Parser.php|doBlockLevels
		ByteTrieMgr_slim rv = ByteTrieMgr_slim.cs_();
		trie_itm(rv, Type_tb, Xop_tblw_lxr_ws.Hook_tb);
		trie_itm(rv, Type_te, Xop_tblw_lxr_ws.Hook_te);
		trie_itm(rv, Type_tr, Xop_tblw_lxr_ws.Hook_tr);
		trie_itm(rv, Type_th, Xop_tblw_lxr_ws.Hook_th);
		trie_itm(rv, Type_tc, Xop_tblw_lxr_ws.Hook_tc);
		trie_itm(rv, Type_td, Bry_.bytes_(Byte_ascii.Pipe));
		trie_itm(rv, Type_nl, Bry_.bytes_(Byte_ascii.NewLine));
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag_table);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag_tr);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag_td);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag_th);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag_blockquote);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag_h1);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag_h2);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag_h3);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag_h4);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag_h5);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag_h6);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag_pre);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag_p);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag_div);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag_hr);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag_li);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag_ul);
		trie_itm_xnde(rv, Xop_xnde_tag_.Tag_ol);
		return rv;
	}
	private static void trie_itm(ByteTrieMgr_slim trie, byte type, byte[] bry) {trie.Add(bry, new Xop_tblw_ws_itm(type, bry.length));}
	private static void trie_itm_xnde(ByteTrieMgr_slim trie, Xop_xnde_tag tag) {
		byte[] tag_name = tag.Name_bry();
		int tag_name_len = tag_name.length;
		trie.Add(Bry_.Add(Bry_xnde_bgn, tag_name), new Xop_tblw_ws_itm(Type_xnde, tag_name_len));
		trie.Add(Bry_.Add(Bry_xnde_end, tag_name), new Xop_tblw_ws_itm(Type_xnde, tag_name_len + 1));
	}	static byte[] Bry_xnde_bgn = new byte[] {Byte_ascii.Lt, Byte_ascii.Slash}, Bry_xnde_end = new byte[] {Byte_ascii.Lt};
}
