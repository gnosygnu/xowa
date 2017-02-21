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
package gplx.xowa.parsers.paras; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*; import gplx.xowa.langs.*;
import gplx.xowa.parsers.tblws.*;
public class Xop_nl_tab_lxr implements Xop_lxr {
	public int Lxr_tid() {return Xop_lxr_.Tid_nl_tab;}
	public void Init_by_wiki(Xowe_wiki wiki, Btrie_fast_mgr core_trie) {core_trie.Add(Hook_nl_tab, this);} private static final byte[] Hook_nl_tab = new byte[] {Byte_ascii.Nl, Byte_ascii.Tab};
	public void Init_by_lang(Xol_lang_itm lang, Btrie_fast_mgr core_trie) {}
	public void Term(Btrie_fast_mgr core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		int non_ws_pos = Bry_find_.Find_fwd_while_space_or_tab(src, cur_pos, src_len);
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
	public static final Xop_nl_tab_lxr Instance = new Xop_nl_tab_lxr(); Xop_nl_tab_lxr() {}
}
