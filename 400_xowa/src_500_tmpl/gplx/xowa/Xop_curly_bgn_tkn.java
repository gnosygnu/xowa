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
package gplx.xowa; import gplx.*;
public class Xop_curly_bgn_tkn extends Xop_tkn_itm_base {
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_tmpl_curly_bgn;}
	public Xop_curly_bgn_tkn(int bgn, int end) {this.Tkn_ini_pos(false, bgn, end);}
}
class Xop_curly_bgn_lxr implements Xop_lxr {
	public byte Lxr_tid() {return Xop_lxr_.Tid_curly_bgn;}
	public void Init_by_wiki(Xow_wiki wiki, ByteTrieMgr_fast core_trie) {core_trie.Add(Hook, this);} public static final byte[] Hook = new byte[] {Byte_ascii.Curly_bgn, Byte_ascii.Curly_bgn};
	public void Init_by_lang(Xol_lang lang, ByteTrieMgr_fast core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {return ctx.Curly().MakeTkn_bgn(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos);}
	public static final Xop_curly_bgn_lxr _ = new Xop_curly_bgn_lxr(); Xop_curly_bgn_lxr() {}
	public static ByteTrieMgr_fast tmpl_bgn_trie_() {	// hook sequences for adding new_line to tmpl return; "{|" "|-" ":" ";" "#" "*"; EX: "{{a}}" returns "*"; convert to "\n*"
		ByteTrieMgr_fast rv = ByteTrieMgr_fast.cs_();
		rv.Add(Xop_tblw_lxr_ws.Hook_tb, Bry_.Empty);
		rv.Add(Bry_.new_ascii_("|-"), Bry_.Empty);
		rv.Add(Byte_ascii.Colon, Bry_.Empty);
		rv.Add(Byte_ascii.Semic, Bry_.Empty);
		rv.Add(Byte_ascii.Hash, Bry_.Empty);
		rv.Add(Byte_ascii.Asterisk, Bry_.Empty);
		return rv;
	}
}
class Xop_curly_end_lxr implements Xop_lxr {
	public byte Lxr_tid() {return Xop_lxr_.Tid_curly_end;}
	public void Init_by_wiki(Xow_wiki wiki, ByteTrieMgr_fast core_trie) {core_trie.Add(Hook, this);} public static final byte[] Hook = new byte[] {Byte_ascii.Curly_end, Byte_ascii.Curly_end};
	public void Init_by_lang(Xol_lang lang, ByteTrieMgr_fast core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {return ctx.Curly().MakeTkn_end(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos);}
	public static final Xop_curly_end_lxr _ = new Xop_curly_end_lxr(); Xop_curly_end_lxr() {}
}
class Xop_brack_bgn_lxr implements Xop_lxr {
	public byte Lxr_tid() {return Xop_lxr_.Tid_brack_bgn;}
	public void Init_by_wiki(Xow_wiki wiki, ByteTrieMgr_fast core_trie) {core_trie.Add(Xop_tkn_.Lnki_bgn, this);}
	public void Init_by_lang(Xol_lang lang, ByteTrieMgr_fast core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		Xop_tkn_itm tkn = tkn_mkr.Brack_bgn(bgn_pos, cur_pos);
		ctx.Subs_add_and_stack(root, tkn);			
		return cur_pos;
	}
	public static final Xop_brack_bgn_lxr _ = new Xop_brack_bgn_lxr(); Xop_brack_bgn_lxr() {}
}
class Xop_brack_end_lxr implements Xop_lxr {
	public byte Lxr_tid() {return Xop_lxr_.Tid_brack_end;}
	public void Init_by_wiki(Xow_wiki wiki, ByteTrieMgr_fast core_trie) {core_trie.Add(Xop_tkn_.Lnki_end, this);}
	public void Init_by_lang(Xol_lang lang, ByteTrieMgr_fast core_trie) {}
	public int Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		int acs_pos = ctx.Stack_idx_typ(Xop_tkn_itm_.Tid_brack_bgn);
		if (acs_pos != -1 && ctx.Cur_tkn_tid() != Xop_tkn_itm_.Tid_tmpl_curly_bgn)	// NOTE: do not pop tkn if inside tmpl; EX: [[a|{{#switch:{{{1}}}|b=c]]|d=e]]|f]]}}
			ctx.Stack_pop_til(root, src, acs_pos, true, bgn_pos, cur_pos, Xop_tkn_itm_.Tid_tmpl_curly_bgn);
		Xop_tkn_itm tkn = tkn_mkr.Brack_end(bgn_pos, cur_pos);
		ctx.Subs_add(root, tkn);
		return cur_pos;
	}
	public static final Xop_brack_end_lxr _ = new Xop_brack_end_lxr(); Xop_brack_end_lxr() {}
}
