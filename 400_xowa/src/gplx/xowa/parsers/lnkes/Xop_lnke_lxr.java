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
package gplx.xowa.parsers.lnkes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*; import gplx.xowa.langs.*;
import gplx.core.net.*;
public class Xop_lnke_lxr implements Xop_lxr {
	Xop_lnke_lxr(byte lnke_typ, byte[] protocol, byte tid) {this.lnke_typ = lnke_typ; this.protocol = protocol; this.tid = tid;} private byte lnke_typ; byte[] protocol; byte tid;
	public int Lxr_tid() {return Xop_lxr_.Tid_lnke_bgn;}
	public void Init_by_wiki(Xowe_wiki wiki, Btrie_fast_mgr core_trie) {
		Gfo_protocol_itm[] ary = Gfo_protocol_itm.Ary();
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			Gfo_protocol_itm itm = ary[i];
			Ctor_lxr_add(core_trie, itm.Key_w_colon_bry(), itm.Tid());
		}
		core_trie.Add(Bry_relative_1, new Xop_lnke_lxr(Xop_lnke_tkn.Lnke_typ_brack, Gfo_protocol_itm.Bry_relative, Gfo_protocol_itm.Tid_relative_1));
		core_trie.Add(Bry_relative_2, new Xop_lnke_lxr(Xop_lnke_tkn.Lnke_typ_brack, Gfo_protocol_itm.Bry_relative, Gfo_protocol_itm.Tid_relative_2));
		Ctor_lxr_add(core_trie, Bry_.new_a7("xowa-cmd"), Gfo_protocol_itm.Tid_xowa);
	}	private static final byte[] Bry_relative_1 = Bry_.new_a7("[//"), Bry_relative_2 = Bry_.new_a7("[[//");
	public void Init_by_lang(Xol_lang_itm lang, Btrie_fast_mgr core_trie) {}
	public void Term(Btrie_fast_mgr core_trie) {}
	private void Ctor_lxr_add(Btrie_fast_mgr core_trie, byte[] protocol_bry, byte tid) {
		core_trie.Add(protocol_bry										, new Xop_lnke_lxr(Xop_lnke_tkn.Lnke_typ_text, protocol_bry, tid));
		core_trie.Add(Bry_.Add(Byte_ascii.Brack_bgn, protocol_bry)	, new Xop_lnke_lxr(Xop_lnke_tkn.Lnke_typ_brack, protocol_bry, tid));
	}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		if (this.tid == Gfo_protocol_itm.Tid_xowa && !ctx.Wiki().Sys_cfg().Xowa_proto_enabled()) return ctx.Lxr_make_txt_(cur_pos);
		return ctx.Lnke().MakeTkn_bgn(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, protocol, tid, lnke_typ);
	}
	public static final Xop_lnke_lxr Instance = new Xop_lnke_lxr(); Xop_lnke_lxr() {}
}
