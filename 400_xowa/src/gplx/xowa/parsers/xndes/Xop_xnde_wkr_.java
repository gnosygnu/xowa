/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.parsers.xndes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.primitives.*; import gplx.core.btries.*;
import gplx.xowa.xtns.pfuncs.strings.*;
class Xop_xnde_wkr_ {
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
		// UNIQ; DATE:2017-03-31
		Btrie_slim_mgr xtn_end_tag_trie = ctx.Tmp_mgr().Xnde__xtn_end();
		xtn_end_tag_trie.Clear();
		xtn_end_tag_trie.Add_obj(open_bry, Find_xtn_end__key__bgn);
		xtn_end_tag_trie.Add_obj(close_bry, Find_xtn_end__key__end);
		int depth = 0;
		for (int i = open_end; i < src_end; ++i) {
			Object o = xtn_end_tag_trie.Match_at(ctx.Tmp_mgr().Xnde__trv(), src, i, src_end);
			if (o != null) {
				int tid = ((Int_obj_val)o).Val();
				switch (tid) {
					case Find_xtn_end__tid__bgn:		// handle nested refs; PAGE:en.w:UK; DATE:2015-12-26
						int angle_end_pos = Bry_find_.Find_fwd(src, Byte_ascii.Angle_end, i, src_end);

						// if dangling, return not found; EX:"<ref>a</ref" PAGE:en.w:Leo_LeBlanc DATE:2017-04-10
						if (angle_end_pos == Bry_find_.Not_found) {
							Xoa_app_.Usr_dlg().Warn_many("", "", "parser.xtn: could not find angle_end: page=~{0} close_bry=~{1} excerpt=~{2}", ctx.Page().Url().To_str(), close_bry, String_.new_u8(src, open_end, src_end));
							return Bry_find_.Not_found;
						}
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
				}
			}
		}
		return Bry_find_.Not_found;
	}
	private static final int Find_xtn_end__tid__bgn = 0, Find_xtn_end__tid__end = 1;//, Find_xtn_end__tid__xtag = 2;
	private static final    Int_obj_val 
	  Find_xtn_end__key__bgn  = new Int_obj_val(Find_xtn_end__tid__bgn)
	, Find_xtn_end__key__end  = new Int_obj_val(Find_xtn_end__tid__end)
	;
}
