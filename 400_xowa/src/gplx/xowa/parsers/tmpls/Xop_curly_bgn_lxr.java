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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*; import gplx.xowa.langs.*;
import gplx.xowa.parsers.tblws.*;
public class Xop_curly_bgn_lxr implements Xop_lxr {
	public int Lxr_tid() {return Xop_lxr_.Tid_curly_bgn;}
	public void Init_by_wiki(Xowe_wiki wiki, Btrie_fast_mgr core_trie) {core_trie.Add(Hook, this);} public static final    byte[] Hook = new byte[] {Byte_ascii.Curly_bgn, Byte_ascii.Curly_bgn};
	public void Init_by_lang(Xol_lang_itm lang, Btrie_fast_mgr core_trie) {}
	public void Term(Btrie_fast_mgr core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {return ctx.Curly().MakeTkn_bgn(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos);}
	public static final    Xop_curly_bgn_lxr Instance = new Xop_curly_bgn_lxr(); Xop_curly_bgn_lxr() {}
	public static Btrie_fast_mgr tmpl_bgn_trie_() {	// hook sequences for adding new_line to tmpl return; "{|" "|-" ":" ";" "#" "*"; EX: "{{a}}" returns "*"; convert to "\n*"
		Btrie_fast_mgr rv = Btrie_fast_mgr.cs();
		rv.Add(Xop_tblw_lxr_ws.Hook_tb, Bry_.Empty);
		rv.Add(Bry_.new_a7("|-"), Bry_.Empty);
		rv.Add(Byte_ascii.Colon, Bry_.Empty);
		rv.Add(Byte_ascii.Semic, Bry_.Empty);
		rv.Add(Byte_ascii.Hash, Bry_.Empty);
		rv.Add(Byte_ascii.Star, Bry_.Empty);
		return rv;
	}
}
