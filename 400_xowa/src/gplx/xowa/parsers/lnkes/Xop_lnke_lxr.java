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
package gplx.xowa.parsers.lnkes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*; import gplx.core.net.*;
public class Xop_lnke_lxr implements Xop_lxr {
	Xop_lnke_lxr(byte lnke_typ, byte[] protocol, byte tid) {this.lnke_typ = lnke_typ; this.protocol = protocol; this.tid = tid;} private byte lnke_typ; byte[] protocol; byte tid;
	public byte Lxr_tid() {return Xop_lxr_.Tid_lnke_bgn;}
	public void Init_by_wiki(Xowe_wiki wiki, Btrie_fast_mgr core_trie) {
		Gfo_protocol_itm[] ary = Gfo_protocol_itm.Ary();
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			Gfo_protocol_itm itm = ary[i];
			Ctor_lxr_add(core_trie, itm.Key_w_colon_bry(), itm.Tid());
		}
		core_trie.Add(Bry_relative_1, new Xop_lnke_lxr(Xop_lnke_tkn.Lnke_typ_brack, Xoa_consts.Url_relative_prefix, Gfo_protocol_itm.Tid_relative_1));
		core_trie.Add(Bry_relative_2, new Xop_lnke_lxr(Xop_lnke_tkn.Lnke_typ_brack, Xoa_consts.Url_relative_prefix, Gfo_protocol_itm.Tid_relative_2));
		Ctor_lxr_add(core_trie, Bry_.new_a7("xowa-cmd"), Gfo_protocol_itm.Tid_xowa);
	}	private static final byte[] Bry_relative_1 = Bry_.new_a7("[//"), Bry_relative_2 = Bry_.new_a7("[[//");
	public void Init_by_lang(Xol_lang lang, Btrie_fast_mgr core_trie) {}
	private void Ctor_lxr_add(Btrie_fast_mgr core_trie, byte[] protocol_bry, byte tid) {
		core_trie.Add(protocol_bry										, new Xop_lnke_lxr(Xop_lnke_tkn.Lnke_typ_text, protocol_bry, tid));
		core_trie.Add(Bry_.Add(Byte_ascii.Brack_bgn, protocol_bry)	, new Xop_lnke_lxr(Xop_lnke_tkn.Lnke_typ_brack, protocol_bry, tid));
	}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		if (this.tid == Gfo_protocol_itm.Tid_xowa && !ctx.Wiki().Sys_cfg().Xowa_proto_enabled()) return ctx.Lxr_make_txt_(cur_pos);
		return ctx.Lnke().MakeTkn_bgn(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, protocol, tid, lnke_typ);
	}
	public static final Xop_lnke_lxr _ = new Xop_lnke_lxr(); Xop_lnke_lxr() {}
}
