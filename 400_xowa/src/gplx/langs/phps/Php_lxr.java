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
package gplx.langs.phps; import gplx.*; import gplx.langs.*;
import gplx.core.btries.*; import gplx.core.log_msgs.*;
interface Php_lxr {
	int Lxr_tid();
	void Lxr_ini(Btrie_slim_mgr trie, Php_parser_interrupt[] parser_interrupts);
	void Lxr_bgn(byte[] src, int src_len, Php_tkn_wkr tkn_wkr, Php_tkn_factory tkn_factory);
	int Lxr_make(Php_ctx ctx, int bgn, int cur);
}
class Php_lxr_ {
	public static final byte Tid_declaration = 1, Tid_ws = 2, Tid_comment = 3, Tid_var = 4, Tid_sym = 5, Tid_keyword = 6, Tid_num = 7, Tid_quote = 8;
}
abstract class Php_lxr_base implements Php_lxr {
	protected byte[] src; protected int src_len; protected Php_tkn_wkr tkn_wkr; protected Php_tkn_factory tkn_factory;
	public abstract int Lxr_tid();
	public abstract void Lxr_ini(Btrie_slim_mgr trie, Php_parser_interrupt[] parser_interrupts);
	public void Lxr_bgn(byte[] src, int src_len, Php_tkn_wkr tkn_wkr, Php_tkn_factory tkn_factory) {this.src = src; this.src_len = src_len; this.tkn_wkr = tkn_wkr; this.tkn_factory = tkn_factory;}
	public abstract int Lxr_make(Php_ctx ctx, int bgn, int cur);
}
class Php_lxr_declaration extends Php_lxr_base {
	@Override public int Lxr_tid() {return Php_lxr_.Tid_declaration;}
	@Override public void Lxr_ini(Btrie_slim_mgr trie, Php_parser_interrupt[] parser_interrupts) {
		trie.Add_obj(Bry_declaration, this);
		parser_interrupts[Byte_ascii.Lt] = Php_parser_interrupt.Char;
	}
	@Override public int Lxr_make(Php_ctx ctx, int bgn, int cur) {
		boolean loop = true;
		boolean ws_found = false;
		while (loop) {
			if (cur == src_len) break;
			byte b = src[cur];
			switch (b) {
				case Byte_ascii.Nl: case Byte_ascii.Cr:
					ws_found = true;
					++cur;
					break;
				default:
					if (ws_found) loop = false;
					else  return Php_parser.NotFound;
					break;
			}
		}
		tkn_wkr.Process(tkn_factory.Declaration(bgn, cur));
		return cur;		
	}
	private static final byte[] Bry_declaration = Bry_.new_a7("<?php");
}
class Php_lxr_ws extends Php_lxr_base {
	public Php_lxr_ws(byte ws_tid) {
		this.ws_tid = ws_tid;
		switch (ws_tid) {
			case Php_tkn_ws.Tid_space: 	ws_bry = Bry_ws_space; break;
			case Php_tkn_ws.Tid_nl: 	ws_bry = Bry_ws_nl; break;
			case Php_tkn_ws.Tid_tab: 	ws_bry = Bry_ws_tab; break;	
			case Php_tkn_ws.Tid_cr: 	ws_bry = Bry_ws_cr; break;	
		}
	}
	public byte Ws_tid() {return ws_tid;} private byte ws_tid;
	public byte[] Ws_bry() {return ws_bry;} private byte[] ws_bry;
	@Override public int Lxr_tid() {return Php_lxr_.Tid_ws;}
	@Override public void Lxr_ini(Btrie_slim_mgr trie, Php_parser_interrupt[] parser_interrupts) {
		trie.Add_obj(ws_bry, this);
		parser_interrupts[ws_bry[0]] = Php_parser_interrupt.Char;
	}
	@Override public int Lxr_make(Php_ctx ctx, int bgn, int cur) {
		boolean loop = true;
		while (loop) {
			if (cur == src_len) break;
			byte b = src[cur];
			switch (b) {
				case Byte_ascii.Space: case Byte_ascii.Nl: case Byte_ascii.Tab: case Byte_ascii.Cr:
					++cur;
					break;
				default:
					loop = false;
					break;
			}
		}
		tkn_wkr.Process(tkn_factory.Ws(bgn, cur, ws_tid));
		return cur;
	}
	public static final byte[] Bry_ws_space = Bry_.new_a7(" "), Bry_ws_nl = Bry_.new_a7("\n"), Bry_ws_tab = Bry_.new_a7("\t"), Bry_ws_cr = Bry_.new_a7("\r");
}
class Php_lxr_comment extends Php_lxr_base {
	public Php_lxr_comment(byte comment_tid) {
		this.comment_tid = comment_tid;
		switch (comment_tid) {
			case Php_tkn_comment.Tid_mult: 		comment_bgn = Bry_bgn_mult; 	comment_end = Bry_end_mult; break;
			case Php_tkn_comment.Tid_slash: 	comment_bgn = Bry_bgn_slash; 	comment_end = Bry_end_nl; break;
			case Php_tkn_comment.Tid_hash: 		comment_bgn = Bry_bgn_hash; 	comment_end = Bry_end_nl; break;
		}
	}
	@Override public int Lxr_tid() {return Php_lxr_.Tid_comment;}
	@Override public void Lxr_ini(Btrie_slim_mgr trie, Php_parser_interrupt[] parser_interrupts) {
		trie.Add_obj(comment_bgn, this);
		parser_interrupts[Byte_ascii.Slash] = Php_parser_interrupt.Char; 
		parser_interrupts[Byte_ascii.Hash] = Php_parser_interrupt.Char; 
	}
	public byte Comment_tid() {return comment_tid;} private byte comment_tid;
	public byte[] Comment_bgn() {return comment_bgn;} private byte[] comment_bgn;
	public byte[] Comment_end() {return comment_end;} private byte[] comment_end;
	@Override public int Lxr_make(Php_ctx ctx, int bgn, int cur) {
		int end = Bry_find_.Find_fwd(src, comment_end, bgn);
		if (end == Bry_find_.Not_found) {
			tkn_wkr.Msg_many(src, bgn, cur, Php_lxr_comment.Dangling_comment, comment_tid, comment_end);
			cur = src_len;	// NOTE: terminating sequence not found; assume rest of src is comment
		}
		else
			cur = end + comment_end.length;
		tkn_wkr.Process(tkn_factory.Comment(bgn, cur, comment_tid));
		return cur;
	}
	public static final Gfo_msg_itm Dangling_comment = Gfo_msg_itm_.new_warn_(Php_parser.Log_nde, "dangling_comment", "dangling_comment");
	public static final byte[] Bry_bgn_mult = Bry_.new_a7("/*"), Bry_bgn_slash = Bry_.new_a7("//"), Bry_bgn_hash = Bry_.new_a7("#")
		, Bry_end_mult = Bry_.new_a7("*/"), Bry_end_nl = Bry_.new_a7("\n");
}
class Php_lxr_var extends Php_lxr_base {
	@Override public int Lxr_tid() {return Php_lxr_.Tid_var;}
	@Override public void Lxr_ini(Btrie_slim_mgr trie, Php_parser_interrupt[] parser_interrupts) {
		trie.Add_obj(Bry_var, this);
		parser_interrupts[Byte_ascii.Dollar] = Php_parser_interrupt.Char;
	}
	@Override public int Lxr_make(Php_ctx ctx, int bgn, int cur) {
		boolean loop = true;
		while (loop) {
			if (cur == src_len) break;
			byte b = src[cur];
			switch (b) {
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
				case Byte_ascii.Ltr_A: case Byte_ascii.Ltr_B: case Byte_ascii.Ltr_C: case Byte_ascii.Ltr_D: case Byte_ascii.Ltr_E:
				case Byte_ascii.Ltr_F: case Byte_ascii.Ltr_G: case Byte_ascii.Ltr_H: case Byte_ascii.Ltr_I: case Byte_ascii.Ltr_J:
				case Byte_ascii.Ltr_K: case Byte_ascii.Ltr_L: case Byte_ascii.Ltr_M: case Byte_ascii.Ltr_N: case Byte_ascii.Ltr_O:
				case Byte_ascii.Ltr_P: case Byte_ascii.Ltr_Q: case Byte_ascii.Ltr_R: case Byte_ascii.Ltr_S: case Byte_ascii.Ltr_T:
				case Byte_ascii.Ltr_U: case Byte_ascii.Ltr_V: case Byte_ascii.Ltr_W: case Byte_ascii.Ltr_X: case Byte_ascii.Ltr_Y: case Byte_ascii.Ltr_Z:
				case Byte_ascii.Ltr_a: case Byte_ascii.Ltr_b: case Byte_ascii.Ltr_c: case Byte_ascii.Ltr_d: case Byte_ascii.Ltr_e:
				case Byte_ascii.Ltr_f: case Byte_ascii.Ltr_g: case Byte_ascii.Ltr_h: case Byte_ascii.Ltr_i: case Byte_ascii.Ltr_j:
				case Byte_ascii.Ltr_k: case Byte_ascii.Ltr_l: case Byte_ascii.Ltr_m: case Byte_ascii.Ltr_n: case Byte_ascii.Ltr_o:
				case Byte_ascii.Ltr_p: case Byte_ascii.Ltr_q: case Byte_ascii.Ltr_r: case Byte_ascii.Ltr_s: case Byte_ascii.Ltr_t:
				case Byte_ascii.Ltr_u: case Byte_ascii.Ltr_v: case Byte_ascii.Ltr_w: case Byte_ascii.Ltr_x: case Byte_ascii.Ltr_y: case Byte_ascii.Ltr_z:
				case Byte_ascii.Underline:
					++cur;
					break;
				default:
					loop = false;
					break;
			}
		}
		tkn_wkr.Process(tkn_factory.Var(bgn, cur));
		return cur;		
	}
	private static final byte[] Bry_var = Bry_.new_a7("$");
}
class Php_lxr_sym extends Php_lxr_base {
	public Php_lxr_sym(String hook_str, byte tkn_tid) {this.hook = Bry_.new_a7(hook_str); this.tkn_tid = tkn_tid;} private byte[] hook; byte tkn_tid;
	@Override public int Lxr_tid() {return Php_lxr_.Tid_sym;}
	@Override public void Lxr_ini(Btrie_slim_mgr trie, Php_parser_interrupt[] parser_interrupts) {
		trie.Add_obj(hook, this);
		parser_interrupts[hook[0]] = Php_parser_interrupt.Char;
	}
	@Override public int Lxr_make(Php_ctx ctx, int bgn, int cur) {
		tkn_wkr.Process(tkn_factory.Generic(bgn, cur, tkn_tid));
		return cur;		
	}
}
class Php_lxr_quote extends Php_lxr_base {
	public Php_lxr_quote(byte quote_tid) {
		this.quote_tid = quote_tid;
		switch (quote_tid) {
			case Byte_ascii.Apos: 		quote_bry = Quote_bry_single; break;
			case Byte_ascii.Quote: 		quote_bry = Quote_bry_double; break;
		}
	}
	@Override public int Lxr_tid() {return Php_lxr_.Tid_quote;}
	@Override public void Lxr_ini(Btrie_slim_mgr trie, Php_parser_interrupt[] parser_interrupts) {
		trie.Add_obj(quote_bry, this);
		parser_interrupts[quote_tid] = Php_parser_interrupt.Char;
	}
	public byte Quote_tid() {return quote_tid;} private byte quote_tid;
	public byte[] Quote_bry() {return quote_bry;} private byte[] quote_bry;
	@Override public int Lxr_make(Php_ctx ctx, int bgn, int cur) {
		int end = -1;
		while (true) {
			end = Bry_find_.Find_fwd(src, quote_bry, cur); 
			if (end == Bry_find_.Not_found) {
				tkn_wkr.Msg_many(src, bgn, cur, Php_lxr_quote.Dangling_quote, quote_tid, quote_bry);
				cur = src_len;	// NOTE: terminating sequence not found; assume rest of src is comment
				break;
			}
			else {
				boolean end_quote = true;
				if (src[end - 1] == Byte_ascii.Backslash) {		// \' encountered;
					int backslash_count = 1;
					for (int i = end - 2; i > -1; i--) {		// count preceding backslashes
						if (src[i] == Byte_ascii.Backslash)
							++backslash_count;
						else
							break;
					}
					if (backslash_count % 2 == 1) {				// odd backslashes; this means that ' is escaped; EX: \' and \\\'; note that even backslashes means not escaped; EX: \\'
						end_quote = false;
						cur = end + 1;
					}
				}
				if (end_quote) {
					cur = end + quote_bry.length;
					break;
				}
			}
		}
		tkn_wkr.Process(tkn_factory.Quote(bgn, cur, quote_tid));
		return cur;
	}
	public static final Gfo_msg_itm Dangling_quote = Gfo_msg_itm_.new_warn_(Php_parser.Log_nde, "dangling_quote", "dangling_quote");
	public static final byte[] Quote_bry_single = Bry_.new_a7("'"), Quote_bry_double = Bry_.new_a7("\"");
}
class Php_lxr_keyword extends Php_lxr_base {
	public Php_lxr_keyword(String hook_str, byte tkn_tid) {this.hook = Bry_.new_a7(hook_str); this.tkn_tid = tkn_tid;} private byte[] hook; byte tkn_tid;
	@Override public int Lxr_tid() {return Php_lxr_.Tid_keyword;}
	@Override public void Lxr_ini(Btrie_slim_mgr trie, Php_parser_interrupt[] parser_interrupts) {trie.Add_obj(hook, this);}
	@Override public int Lxr_make(Php_ctx ctx, int bgn, int cur) {
		if (cur < src_len) {
			byte next_byte = src[cur];
			switch (next_byte) {	// valid characters for end of word; EX: 'null '; 'null='; etc..
				case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr:
				case Byte_ascii.Hash: case Byte_ascii.Slash:
				case Byte_ascii.Quote: case Byte_ascii.Apos:
				case Byte_ascii.Bang: case Byte_ascii.Dollar: case Byte_ascii.Percent: case Byte_ascii.Amp:
				case Byte_ascii.Paren_bgn: case Byte_ascii.Paren_end: case Byte_ascii.Star: case Byte_ascii.Plus:
				case Byte_ascii.Comma: case Byte_ascii.Dash: case Byte_ascii.Dot: case Byte_ascii.Semic:
				case Byte_ascii.Lt: case Byte_ascii.Eq: case Byte_ascii.Gt: case Byte_ascii.Question: case Byte_ascii.At:
				case Byte_ascii.Brack_bgn: case Byte_ascii.Backslash: case Byte_ascii.Brack_end: case Byte_ascii.Pow: case Byte_ascii.Tick:
				case Byte_ascii.Curly_bgn: case Byte_ascii.Pipe: case Byte_ascii.Curly_end: case Byte_ascii.Tilde:
					break;
				default:			// num,ltr or extended utf8 character sequence; treat keyword as false match; EX: 'nulla'; 'null0'
					return Php_parser.NotFound;
			}
		}
		tkn_wkr.Process(tkn_factory.Generic(bgn, cur, tkn_tid));
		return cur;		
	}
}
class Php_lxr_num extends Php_lxr_base {
	@Override public int Lxr_tid() {return Php_lxr_.Tid_keyword;}
	@Override public void Lxr_ini(Btrie_slim_mgr trie, Php_parser_interrupt[] parser_interrupts) {
		for (int i = 0; i < 10; i++)
			trie.Add_obj(new byte[] {(byte)(i + Byte_ascii.Num_0)}, this);
	}
	@Override public int Lxr_make(Php_ctx ctx, int bgn, int cur) {
		boolean loop = true;
		while (loop) {
			if (cur == src_len) break;
			byte b = src[cur];
			switch (b) {
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
					++cur;
					break;
				default:
					loop = false;
					break;
			}
		}
		tkn_wkr.Process(tkn_factory.Num(bgn, cur));
		return cur;		
	}
}