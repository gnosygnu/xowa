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
public class Xop_list_lxr implements Xop_lxr {
	public int Lxr_tid() {return Xop_lxr_.Tid_list;}
	public void Init_by_wiki(Xowe_wiki wiki, Btrie_fast_mgr core_trie) {Add_ary(core_trie, this, Xop_list_tkn_.Hook_ul, Xop_list_tkn_.Hook_ol, Xop_list_tkn_.Hook_dt, Xop_list_tkn_.Hook_dd);}
	public void Init_by_lang(Xol_lang_itm lang, Btrie_fast_mgr core_trie) {}
	public void Term(Btrie_fast_mgr core_trie) {}
	private void Add_ary(Btrie_fast_mgr core_trie, Object val, byte[]... ary) {for (byte[] itm : ary) core_trie.Add(itm, val);}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {return ctx.List().MakeTkn_bgn(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos);}
	public static final Xop_list_lxr Instance = new Xop_list_lxr(); Xop_list_lxr() {}
}
