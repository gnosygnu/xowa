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
package gplx.xowa.xtns.translates; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.btries.*; import gplx.xowa.langs.*;
import gplx.xowa.parsers.*;
public class Xop_tvar_lxr implements Xop_lxr {
	public int Lxr_tid() {return Xop_lxr_.Tid_tvar;}
	public void Init_by_wiki(Xowe_wiki wiki, Btrie_fast_mgr core_trie) {core_trie.Add(Hook_bgn, this);}
	public void Init_by_lang(Xol_lang_itm lang, Btrie_fast_mgr core_trie) {}
	public void Term(Btrie_fast_mgr core_trie) {}
	private static final    byte[] Hook_bgn = Bry_.new_a7("<tvar|"), Close_nde = Bry_.new_a7("</>");
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		int rhs_end = Bry_find_.Find_fwd(src, Byte_ascii.Gt, cur_pos); if (rhs_end == Bry_find_.Not_found) return ctx.Lxr_make_txt_(cur_pos);
		int lhs_bgn = Bry_find_.Find_fwd(src, Close_nde    , rhs_end); if (lhs_bgn == Bry_find_.Not_found) return ctx.Lxr_make_txt_(cur_pos);
		byte[] body = Bry_.Mid(src, rhs_end + Int_.Const_position_after_char, lhs_bgn);
		Xop_ctx sub_ctx = Xop_ctx.New__sub__reuse_page(ctx);
		Xop_root_tkn sub_root = tkn_mkr.Root(body);
		body = ctx.Wiki().Parser_mgr().Main().Expand_tmpl(sub_root, sub_ctx, tkn_mkr, body);	// NOTE: must parse inner text for templates; EX:<tvar|a>{{B}}</>; PAGE:mw:Download; DATE:2014-04-27
		int end_pos = lhs_bgn + Close_nde.length;
		ctx.Subs_add(root, tkn_mkr.Tvar(bgn_pos, end_pos, cur_pos, rhs_end, rhs_end + Int_.Const_position_after_char, lhs_bgn, body));
		return end_pos;
	}
	public static final    Xop_tvar_lxr Instance = new Xop_tvar_lxr(); Xop_tvar_lxr() {}
}
