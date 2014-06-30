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
package gplx.xowa.xtns.pfuncs.ifs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
public class Pfunc_iferror extends Pf_func_base {
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bb) {			
		int self_args_len = self.Args_len();
		byte[] val_dat_ary = Eval_argx(ctx, src, caller, self);
		if (val_dat_ary == null) return;
		if (Error_exists(val_dat_ary))
			bb.Add(Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 0));
		else {
			if (self_args_len < 2)		// pass clause absent; add original
				bb.Add(val_dat_ary);
			else
				bb.Add(Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 1));
		}
	}
	@Override public int Id() {return Xol_kwd_grp_.Id_xtn_iferror;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_iferror().Name_(name);}
	boolean Error_exists(byte[] src) {
		// NOTE: approximation of MW code. basically checking for <tag class=error; REF.MW: ParserFunctions_body.php|iferror
		// /<(?:strong|span|p|div)\s(?:[^\s>]*\s+)*?class="(?:[^"\s>]*\s+)*?error(?:\s[^">]*)?"/		
		int src_len = src.length;
		byte state = State_null;
		int pos = 0;
		boolean valid = false;
		while (true) {
			if (pos == src_len) break;
			byte b = src[pos];
			Object o = trie.Match(b, src, pos, src_len);
			if (o == null) 
				++pos;
			else {
				Byte_obj_val bv = (Byte_obj_val)o;
				int pos_nxt = trie.Match_pos();
				if (pos_nxt == src_len) return false; // each of the three states requires at least one character afterwards
				switch (bv.Val()) {
					case State_close:	// >: reset state
						state = State_null;
						break;
					case State_nde:		// <(?:strong|span|p|div)\s
						switch (src[pos_nxt]) {
							case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.NewLine:
								state = State_nde;
								++pos_nxt;
								break;
						}
						break;
					case State_class:						
						if (state == State_nde) {
							valid = true;
							switch (src[pos - 1]) {								
								case Byte_ascii.Quote: case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.NewLine:
									break;
								default:
									valid = false;
									break;
							}
							if (valid) {
								state = State_class;
								++pos_nxt;
							}
						}
						break;
					case State_error:
						if (state == State_class) {
							valid = true;
							switch (src[pos - 1]) {								
								case Byte_ascii.Quote: case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.NewLine:
									break;
								default:
									valid = false;
									break;
							}
							switch (src[pos_nxt]) {
								case Byte_ascii.Quote: case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.NewLine:
									break;
								default:
									valid = false;
									break;
							}
							if (valid)
								return true;
						}
						break;
				}
				pos = pos_nxt;
			}
		}
		return false;
	}
	private static final ByteTrieMgr_slim trie = trie_();
	static final byte State_null = 0, State_nde = 1, State_class = 2, State_error = 3, State_close = 4;
	private static ByteTrieMgr_slim trie_() {
		ByteTrieMgr_slim rv = ByteTrieMgr_slim.ci_ascii_();	// NOTE:ci.ascii:MW_const.en
		trie_init(rv, State_nde  , "<strong");
		trie_init(rv, State_nde  , "<span");
		trie_init(rv, State_nde  , "<p");
		trie_init(rv, State_nde  , "<div");
		trie_init(rv, State_class, "class=");
		trie_init(rv, State_error, "error");
		trie_init(rv, State_close, ">");
		return rv;
	}
	private static void trie_init(ByteTrieMgr_slim trie, byte b, String s) {trie.Add(s, Byte_obj_val.new_(b));}
}
