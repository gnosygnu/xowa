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
package gplx.xowa.xtns.pfuncs.ifs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.core.primitives.*; import gplx.core.btries.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pfunc_iferror extends Pf_func_base {
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		int self_args_len = self.Args_len();
		byte[] argx = Eval_argx(ctx, src, caller, self);
		if (argx == null) return;
		if (Error_exists(argx))
			bfr.Add(Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 0));
		else {
			if (self_args_len < 2)		// pass clause absent; add original
				bfr.Add(argx);
			else
				bfr.Add(Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 1));
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
		Btrie_rv trv = new Btrie_rv();
		while (true) {
			if (pos == src_len) break;
			byte b = src[pos];
			Object o = trie.Match_at_w_b0(trv, b, src, pos, src_len);
			if (o == null) 
				++pos;
			else {
				Byte_obj_val bv = (Byte_obj_val)o;
				int pos_nxt = trv.Pos();
				if (pos_nxt == src_len) return false; // each of the three states requires at least one character afterwards
				switch (bv.Val()) {
					case State_close:	// >: reset state
						state = State_null;
						break;
					case State_nde:		// <(?:strong|span|p|div)\s
						switch (src[pos_nxt]) {
							case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl:
								state = State_nde;
								++pos_nxt;
								break;
						}
						break;
					case State_class:						
						if (state == State_nde) {
							valid = true;
							switch (src[pos - 1]) {								
								case Byte_ascii.Quote: case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl:
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
								case Byte_ascii.Quote: case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl:
									break;
								default:
									valid = false;
									break;
							}
							switch (src[pos_nxt]) {
								case Byte_ascii.Quote: case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl:
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
	private static final    Btrie_slim_mgr trie = trie_();
	static final byte State_null = 0, State_nde = 1, State_class = 2, State_error = 3, State_close = 4;
	private static Btrie_slim_mgr trie_() {
		Btrie_slim_mgr rv = Btrie_slim_mgr.ci_a7();	// NOTE:ci.ascii:MW_const.en
		trie_init(rv, State_nde  , "<strong");
		trie_init(rv, State_nde  , "<span");
		trie_init(rv, State_nde  , "<p");
		trie_init(rv, State_nde  , "<div");
		trie_init(rv, State_class, "class=");
		trie_init(rv, State_error, "error");
		trie_init(rv, State_close, ">");
		return rv;
	}
	private static void trie_init(Btrie_slim_mgr trie, byte b, String s) {trie.Add_obj(s, Byte_obj_val.new_(b));}
}
