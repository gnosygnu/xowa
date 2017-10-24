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
package gplx.xowa.addons.wikis.searchs.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
import gplx.core.btries.*; import gplx.core.primitives.*;
interface Srch_sym_parser {
	int			Tid();
	byte[][]	Hooks_ary();
	int			Parse(Srch_text_parser mgr, byte[] src, int src_end, int hook_bgn, int hook_end);
}
class Srch_sym_parser_ {
	public static final int Tid__terminal = 1, Tid__split = 2, Tid__enclosure = 3, Tid__dot = 4, Tid__ellipsis = 5, Tid__apos = 6, Tid__dash = 7;
}
class Srch_sym_parser__terminal implements Srch_sym_parser {
	public Srch_sym_parser__terminal(byte[] hook_bry) {this.hooks_ary = Bry_.Ary(hook_bry);}
	public int Tid() {return Srch_sym_parser_.Tid__terminal;}
	public byte[][]	Hooks_ary() {return hooks_ary;} private final    byte[][] hooks_ary;
	public int Parse(Srch_text_parser mgr, byte[] src, int src_end, int hook_bgn, int hook_end) {
		if (mgr.Cur__end__chk(hook_end)) {	// hook at word_end; EX: "a, b" -> "a", "b"
			int word_bgn = mgr.Cur__bgn();
			if (word_bgn == Srch_text_parser.None) return hook_end;
			int word_end = hook_bgn;
			for (int i = hook_bgn - 1; i >= word_bgn; --i) {	// loop bwd to gobble up streams of terminals; EX: "?!"
				byte b = src[i];
				if (mgr.word_end_trie.Match_bgn_w_byte(b, src, i, hook_bgn) == null) break;
				word_end = i;
			}
			mgr.Words__add_if_pending_and_clear(word_end);	// make word; note that " , " will not make word b/c word_bgn == -1
			return hook_end;
		}
		else {}// hook at word_mid or word_bgn; noop; EX: "1,000" -> "1,000"; note that " ,abc" will ignore ","
//				if (mgr.Cur__bgn() == - 1) {	
//					mgr.Cur__bgn__set(hook_bgn);
//				}
//			}								
		return hook_end;
	}
}
class Srch_sym_parser__split implements Srch_sym_parser {
	private final    Btrie_slim_mgr trie = Btrie_slim_mgr.cs();
	private final    boolean handle_eos;
	public Srch_sym_parser__split(boolean handle_eos, String... hooks_ary_as_str) {
		this.handle_eos = handle_eos;
		this.hooks_ary = Bry_.Ary(hooks_ary_as_str);
		int hooks_len = hooks_ary.length;
		for (int i = 0; i < hooks_len; ++i) {
			byte[] hook = hooks_ary[i];
			trie.Add_obj(hook, new Int_obj_val(hook.length));
		}
	}
	public int Tid() {return Srch_sym_parser_.Tid__split;}
	public byte[][]	Hooks_ary() {return hooks_ary;} private final    byte[][] hooks_ary;
	public int Parse(Srch_text_parser mgr, byte[] src, int src_end, int hook_bgn, int hook_end) {
		mgr.Words__add_if_pending_and_clear(hook_bgn);

		int rv = hook_end;
		while (rv < src_end) {		// loop to skip multiple syms in same group; EX: "a \n\t\r b"
			byte b = src[rv];
			Object o = trie.Match_bgn_w_byte(b, src, rv, src_end);
			if (o == null) break;	// current sequence is not in grp; stop
			Int_obj_val itm_len = (Int_obj_val)o;
			rv += itm_len.Val();	// add len of sym to pos
		}
		return rv;
	}
	public int Find_fwd(byte[] src, int src_bgn, int src_end) {
		for (int i = src_bgn; i < src_end; ++i) {
			byte b = src[i];
			Object o = trie.Match_bgn_w_byte(b, src, i, src_end);
			if (o != null) return i;
		}
		return src_end;
	}
	public boolean Is_next(byte[] src, int pos, int end) {
		if (pos >= end) return handle_eos;
		byte b = src[pos];
		Object o = trie.Match_bgn_w_byte(b, src, pos, end);
		return o != null;
	}
}
class Srch_sym_parser__paren_bgn implements Srch_sym_parser {
	private final    byte bgn_byte, end_byte;
	public Srch_sym_parser__paren_bgn(byte bgn_byte, byte end_byte) {
		this.bgn_byte = bgn_byte; this.end_byte = end_byte;
		this.hooks_ary = Bry_.Ary(Bry_.New_by_byte(bgn_byte));
	}
	public int Tid() {return Srch_sym_parser_.Tid__enclosure;}
	public byte[][]	Hooks_ary() {return hooks_ary;} private final    byte[][] hooks_ary;
	public int Parse(Srch_text_parser mgr, byte[] src, int src_end, int paren_lhs_bgn, int paren_lhs_end) {
		int word_bgn = mgr.Cur__bgn();
		paren_lhs_end = Bry_find_.Find_fwd_while(src, paren_lhs_end, src_end, bgn_byte);
		int paren_rhs_bgn = Bry_find_.Find_fwd(src, end_byte, paren_lhs_end, src_end);
		if (paren_rhs_bgn == Bry_find_.Not_found) return paren_lhs_end;	// paren_rhs missing; noop; NOTE: handles both "a(b" -> "a(b" and "a (b" -> "a", "b"
		int paren_rhs_end = Bry_find_.Find_fwd_while(src, paren_rhs_bgn, src_end, end_byte);
		int word_end = mgr.Cur__end__find__text_only(paren_lhs_end + 1);
		if (word_end < paren_rhs_bgn)
			word_end = mgr.Cur__end__find__text_only(paren_rhs_end);
		boolean recurse = false;
		if (word_bgn == -1) {											// paren_lhs at word_bgn; EX: "a (b)"
			if (word_end == paren_rhs_end)								// paren_rhs at word_end; EX: "a (b) c"
				recurse = true;
			else {														// paren_rhs at word_mid; EX: "a (b)c"
				mgr.Words__add_direct(paren_lhs_bgn, word_end);
				mgr.Words__add_direct(paren_rhs_end, word_end);
			}
		}
		else {															// paren_lhs at word_mid; EX: "a(b)"
			mgr.Words__add_direct(word_bgn, word_end);
			if (word_end == paren_rhs_end)								// paren_rhs at word_end; EX: "a(b) c"
				mgr.Words__add_direct(word_bgn, paren_lhs_bgn);
			else {}														// paren_rhs at word_mid; EX: "a (b)c"
			mgr.Cur__bgn__reset();
		}
		if (recurse)
			mgr.Make_copy().Parse(mgr.lcase, src, paren_lhs_end, paren_rhs_bgn);
		return word_end;
	}
}
class Srch_sym_parser__dot implements Srch_sym_parser {				// handle periods which will add two entries; EX: "H. G. Wells" -> "H.", "G.", "H", "G", "Wells"
	private final    byte[] sym; private final    int sym_len;
	public Srch_sym_parser__dot(String sym_str) {this.sym = Bry_.new_u8(sym_str); this.sym_len = sym.length;}
	public int Tid() {return Srch_sym_parser_.Tid__dot;}
	public byte[][]	Hooks_ary() {return Hooks_const;} private static final    byte[][] Hooks_const = Bry_.Ary(".");
	public int Parse(Srch_text_parser mgr, byte[] src, int src_end, int hook_bgn, int hook_end) {
		int word_bgn = mgr.Cur__bgn();
		if		(word_bgn == -1) {									// dot at start of word; EX: ".NET", "Colt .45"
			int word_end = mgr.Cur__end__find(hook_bgn + 1);
			if (word_end - hook_bgn == 1) return hook_end;			// ignore stand-alone sym; EX: "a . . . b"
			mgr.Words__add__chk_dash(hook_bgn, word_end);			// make word; EX: ".45"
			if (Bry_find_.Find_fwd(src, sym, hook_bgn + 1, word_end) == -1)	// only add "root" word, if sym is not in middle of String; EX: ".int" -> "int"; ".A.B" x> "A.B"
				mgr.Words__add__chk_dash(hook_bgn + 1, word_end);	// make word; EX: "45"
			mgr.Cur__bgn__reset();
			return word_end;
		}
		else {
			if (mgr.Cur__end__chk(hook_end)) {							// at end of word; EX: "U.S.A. b" vs. "H. G. b"
				mgr.Words__add__chk_dash(word_bgn, hook_end);			// make word; EX: "vs."
				if (Bry_find_.Find_fwd(src, sym, word_bgn, hook_bgn) == -1)	// no dots in word; add "root" word; "vs." -> "vs.", "vs"; "U.S.A." -> "U.S.A." x> "U.S.A"
					mgr.Words__add__chk_dash(word_bgn, hook_bgn);		// make word; EX: "vs"
				else {
					for (int i = word_bgn; i < hook_end; ++i) {			// dots in middle of String; extract word; EX: "U.S.A." -> USA
						if (Bry_.Eq(src, i, i + sym_len, sym)) continue;// skip syms
						mgr.Tmp_bfr.Add_byte(src[i]);
					}
					mgr.Words__add_direct(mgr.Tmp_bfr.To_bry_and_clear());
				}
				mgr.Cur__bgn__reset();
			}
		}
		return hook_end;
	}
}
class Srch_sym_parser__ellipsis implements Srch_sym_parser {	// ellipsis which have variable length; EX: "..", "...", ".... "
	private final    byte ellipsis_byte;
	public Srch_sym_parser__ellipsis(byte ellipsis_byte, String hook) {
		this.ellipsis_byte = ellipsis_byte;
		this.hooks_ary = Bry_.Ary(hook);
	}
	public int Tid() {return Srch_sym_parser_.Tid__ellipsis;}
	public byte[][]	Hooks_ary() {return hooks_ary;} private final    byte[][] hooks_ary;
	public int Parse(Srch_text_parser mgr, byte[] src, int src_end, int hook_bgn, int hook_end) {
		mgr.Words__add_if_pending_and_clear(hook_bgn);								// add word; EX: "Dreams" in "Dreams..."
		int rv = Bry_find_.Find_fwd_while(src, hook_end, src_end, ellipsis_byte);	// skip multiple ellipsis; EX: ......
		mgr.Words__add__chk_dash(hook_bgn, rv);										// add ellipsis
		return rv;
	}
}
class Srch_sym_parser__apos implements Srch_sym_parser {	// apos which can be contraction ("I'm"), possessive ("today's") and plural ("plans'")
	private final    byte[] hook_bry;
	public Srch_sym_parser__apos(String hook) {
		this.hook_bry = Bry_.new_u8(hook);
		this.hooks_ary = Bry_.Ary(hook_bry);
	}
	public int Tid() {return Srch_sym_parser_.Tid__apos;}
	public byte[][]	Hooks_ary() {return hooks_ary;} private final    byte[][] hooks_ary;
	public int Parse(Srch_text_parser mgr, byte[] src, int src_end, int hook_bgn, int hook_end) {
		int word_bgn = mgr.Cur__bgn();
		int word_end = -1;
		if		(word_bgn == -1) {							// sym at word_bgn; EX: "'n'"
			word_bgn = hook_bgn;
			word_end = mgr.Cur__end__find(hook_bgn + 1);
			mgr.Words__add__chk_dash(word_bgn, word_end);

			// "'n'" -> "n"
			int alt_word_bgn = word_bgn + 1;
			int alt_word_end = Bry_find_.Find_bwd__skip(src, word_end, alt_word_bgn, hook_bry);
			if (alt_word_end - alt_word_bgn > 0)	// do not add if 0-len; EX: "' (disambiguation)"
				mgr.Words__add__chk_dash(alt_word_bgn, alt_word_end);
			mgr.Cur__bgn__reset();
			return word_end;
		}
		word_end = mgr.Cur__end__find(hook_end);
		if (hook_end == word_end) {							// sym at word_end; EX: "A' "
			mgr.Words__add_if_pending_and_clear(hook_bgn);	// add root only; EX: "a'" -> "a" x> "a", "a'"
			return hook_end;
		}
		else {
			byte[] root_word = null;
			if		(hook_bgn  - word_bgn == 1)
				root_word = mgr.Tmp_bfr.Add_byte(src[word_bgn]).Add_mid(src, hook_bgn + 1, word_end).To_bry_and_clear();
			else if	(word_end - hook_end  == 1)
				root_word = mgr.Tmp_bfr.Add_mid(src, word_bgn, hook_bgn).Add_byte(src[hook_end]).To_bry_and_clear();
                // Tfds.Write(word_bgn, hook_bgn, hook_end, word_end);
			if (root_word != null) {
				mgr.Words__add__chk_dash(word_bgn, word_end);	// add full; EX: "o'clock"
				mgr.Words__add_direct(root_word);				// add root; EX: "oclock"
				mgr.Cur__bgn__reset();
			}
			return word_end;
		}
	}
}
class Srch_sym_parser__dash implements Srch_sym_parser {
	public Srch_sym_parser__dash(String hook) {this.hooks_ary = Bry_.Ary(hook);}
	public int Tid() {return Srch_sym_parser_.Tid__dash;}
	public byte[][]	Hooks_ary() {return hooks_ary;} private final    byte[][] hooks_ary;
	public int Parse(Srch_text_parser mgr, byte[] src, int src_end, int hook_bgn, int hook_end) {
		int cur_bgn = mgr.Cur__bgn();						// get word_bgn
		if (cur_bgn == -1) {								// no word_bgn; "-" is 1st char
			if (mgr.Cur__end__chk(hook_end)) {				// next char is space
				mgr.Words__add_direct(hook_bgn, hook_end);	// add dash; EX: "a - b" -> "a", "-", "b"
			}
			else {											// next char is something else
				mgr.Cur__bgn__set(hook_bgn);				// update word_bgn to dash
				mgr.Dash__bgn_(hook_end);
			}
			return hook_end;
		}
		else {												// word_bgn exists
			int dash_bgn = mgr.Dash__bgn();
			if (dash_bgn == Srch_text_parser.None) {		// 1st dash
				mgr.Words__add__chk_dash(cur_bgn, hook_bgn);// add word; EX: "a" in "a-b"
			} else {										// 2nd or more; add stub; EX: "a-b-c"; 2nd "-" should add "b"
				if (hook_bgn > dash_bgn)					// only add if len > 0; handles multiple dashes; EX: "---"
					mgr.Words__add_direct(dash_bgn, hook_bgn);
			}
		}
		mgr.Dash__bgn_(hook_end);
		return hook_end;
	}
	public void Add_pending_word(Srch_text_parser mgr, byte[] src, int word_end) {
		int dash_bgn = mgr.Dash__bgn();
		if (dash_bgn == Srch_text_parser.None) return;
		if	(word_end - dash_bgn > 0)				// only add if there is word to right of dash; EX: "a-"
			mgr.Words__add_direct(dash_bgn, word_end);
		mgr.Dash__bgn_(Srch_text_parser.None);		// clear the dash
	}
}
