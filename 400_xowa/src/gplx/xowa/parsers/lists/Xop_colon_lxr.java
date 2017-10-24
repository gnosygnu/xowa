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
package gplx.xowa.parsers.lists; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*; import gplx.xowa.langs.*;
public class Xop_colon_lxr implements Xop_lxr {
	public int Lxr_tid() {return Xop_lxr_.Tid_colon;}
	public void Init_by_wiki(Xowe_wiki wiki, Btrie_fast_mgr core_trie) {core_trie.Add(Byte_ascii.Colon, this);}
	public void Init_by_lang(Xol_lang_itm lang, Btrie_fast_mgr core_trie) {}
	public void Term(Btrie_fast_mgr core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		Xop_list_wkr listCtx = ctx.List();
		if (listCtx.Dd_chk()) {	// handle ";a:b" construct; REF.MW: Parser.php|doBlockLevels|; title : definition text
			int prv_pos = cur_pos -1 ;
			if (	ctx.Cur_tkn_tid() != Xop_tkn_itm_.Tid_lnki	// ignore if inside link
				&&	prv_pos > 0
				&&	src[prv_pos] != Byte_ascii.Nl			// only consider ":" which are not preceded by \n; DATE:2014-07-11 TODO_OLD: emulate Parser.php|findColonNoLinks which does much more logic to see if ";a:b" construct should apply
				) {	
				listCtx.Dd_chk_(false);
				return listCtx.MakeTkn_bgn(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos);
			}
		}
		ctx.Subs_add(root, tkn_mkr.Colon(bgn_pos, cur_pos));
		return cur_pos;
	}
	public static final    Xop_colon_lxr Instance = new Xop_colon_lxr();
}
