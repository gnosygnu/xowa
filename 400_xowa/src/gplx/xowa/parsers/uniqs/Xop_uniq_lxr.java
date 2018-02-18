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
package gplx.xowa.parsers.uniqs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*; import gplx.xowa.langs.*;
// EX: "\u007fUNIQ-item-1-QINU\u007f"
public class Xop_uniq_lxr implements Xop_lxr {
	public int Lxr_tid() {return Xop_lxr_.Tid_uniq;}
	public void Init_by_wiki(Xowe_wiki wiki, Btrie_fast_mgr core_trie) {
		core_trie.Add(Xop_uniq_mgr.Bry__uniq__bgn_w_dash, this);
	}
	public void Init_by_lang(Xol_lang_itm lang, Btrie_fast_mgr core_trie) {}
	public void Term(Btrie_fast_mgr core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		// find end
		int rhs_bgn = Bry_find_.Find_fwd(src, Xop_uniq_mgr.Bry__uniq__add__end, cur_pos);
		if (rhs_bgn == Bry_find_.Not_found) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "uniq_mgr:unable to find uniq; src=~{0}", src);
			return ctx.Lxr_make_txt_(cur_pos);
		}
		int rhs_end = rhs_bgn + Xop_uniq_mgr.Bry__uniq__add__end.length;

		byte[] key = Bry_.Mid(src, bgn_pos, rhs_end);
		Xop_uniq_tkn uniq_tkn = new Xop_uniq_tkn(bgn_pos, rhs_end, key);
		ctx.Subs_add(root, uniq_tkn);
		return rhs_end;
	}
        public static final    Xop_uniq_lxr Instance = new Xop_uniq_lxr(); Xop_uniq_lxr() {}
}
