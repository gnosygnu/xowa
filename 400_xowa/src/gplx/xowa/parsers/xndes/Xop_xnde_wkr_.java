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
package gplx.xowa.parsers.xndes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.primitives.*; import gplx.core.btries.*;
import gplx.xowa.xtns.pfuncs.strings.*;
class Xop_xnde_wkr_ {
	private static final    Btrie_slim_mgr xtn_end_tag_trie = Btrie_slim_mgr.ci_a7();	// NOTE:ci.ascii:MW_const.en; listed XML node names are en
	private static final int Find_xtn_end__tid__bgn = 0, Find_xtn_end__tid__end = 1, Find_xtn_end__tid__xtag = 2;
	private static final    Int_obj_ref Find_xtn_end__key__bgn = Int_obj_ref.New(Find_xtn_end__tid__bgn), Find_xtn_end__key__end = Int_obj_ref.New(Find_xtn_end__tid__end), Find_xtn_end__key__xtag = Int_obj_ref.New(Find_xtn_end__tid__xtag);
	public static void AutoClose_handle_dangling_nde_in_caption(Xop_root_tkn root, Xop_tkn_itm owner) {
		int subs_bgn = -1, subs_len = owner.Subs_len();
		for (int i = 0; i < subs_len; i++) {
			Xop_tkn_itm sub_itm = owner.Subs_get(i);
			if (sub_itm.Tkn_tid() == Xop_tkn_itm_.Tid_pipe) {	// tkn is "|"; assume that caption should end here
				subs_bgn = i;
				break;
			}
		}
		if (subs_bgn != -1) 
			root.Subs_move(owner, subs_bgn, subs_len);			// move everything after "|" back to root
	}
	public static int Find_xtn_end(Xop_ctx ctx, byte[] src, int open_end, int src_end, byte[] open_bry, byte[] close_bry) {
		synchronized (xtn_end_tag_trie) {	// LOCK:static-obj; DATE:2016-07-06
			xtn_end_tag_trie.Clear();
			xtn_end_tag_trie.Add_obj(Pfunc_tag.Xtag_bgn_lhs, Find_xtn_end__key__xtag);
			xtn_end_tag_trie.Add_obj(open_bry, Find_xtn_end__key__bgn);
			xtn_end_tag_trie.Add_obj(close_bry, Find_xtn_end__key__end);
			int depth = 0;
			for (int i = open_end; i < src_end; ++i) {
				Object o = xtn_end_tag_trie.Match_bgn(src, i, src_end);
				if (o != null) {
					int tid = ((Int_obj_ref)o).Val();
					switch (tid) {
						case Find_xtn_end__tid__bgn:		// handle nested refs; PAGE:en.w:UK; DATE:2015-12-26
							int angle_end_pos = Bry_find_.Find_fwd(src, Byte_ascii.Angle_end, i, src_end); if (angle_end_pos == Bry_find_.Not_found) {Xoa_app_.Usr_dlg().Warn_many("", "", "parser.xtn: could not find angle_end: page=~{0}", ctx.Page().Url().To_str()); return Bry_find_.Not_found;}
							if (src[angle_end_pos -1] == Byte_ascii.Slash) {}
							else
								++depth;
							break;
						case Find_xtn_end__tid__end:		// xtn_end found; use it
							if (depth == 0)
								return i;
							else
								--depth;
							break;
						case Find_xtn_end__tid__xtag:		// xtag found; skip over it; PAGE:it.s:La_Secchia_rapita/Canto_primo DATE:2015-12-03
							int xtag_end = Find_xtag_end(ctx, src, i, src_end);
							int angle_end = Bry_find_.Find_fwd(src, Byte_ascii.Angle_end, xtag_end, src_end);
							i = angle_end;
							break;
					}
				}
			}
			return Bry_find_.Not_found;
		}
	}
	public static int Find_xtag_end(Xop_ctx ctx, byte[] src, int pos, int src_end) {
		int xtag_bgn = pos + Pfunc_tag.Xtag_bgn_lhs.length;
		int tag_id = Bry_.To_int_or(src, xtag_bgn, xtag_bgn + Pfunc_tag.Id_len, -1); if (tag_id == -1) {Xoa_app_.Usr_dlg().Warn_many("", "", "parser.xtn: could not extract id from xtag_bgn: page=~{0}", ctx.Page().Url().To_str()); return Bry_find_.Not_found;}
		Bry_bfr tmp_bfr = ctx.Wiki().Utl__bfr_mkr().Get_b128();
		tmp_bfr.Add(Pfunc_tag.Xtag_end_lhs).Add_int_pad_bgn(Byte_ascii.Num_0, Pfunc_tag.Id_len, tag_id).Add(Pfunc_tag.Xtag_rhs);
		byte[] tag_end = tmp_bfr.To_bry_and_clear();
		tmp_bfr.Mkr_rls();
		int rv = Bry_find_.Find_fwd(src, tag_end, pos + Pfunc_tag.Xtag_rhs.length); if (rv == Bry_find_.Not_found) {ctx.App().Usr_dlg().Warn_many("", "", "parser.xtn: could not find xtag end: page=~{0}", ctx.Page().Url().To_str()); return Bry_find_.Not_found;}
		rv = Bry_find_.Find_bwd(src, Byte_ascii.Lt, rv - 1); if (rv == Bry_find_.Not_found) {ctx.App().Usr_dlg().Warn_many("", "", "parser.xtn: could not find <: page=~{0}", ctx.Page().Url().To_str()); return Bry_find_.Not_found;}
		return rv;
	}
}
