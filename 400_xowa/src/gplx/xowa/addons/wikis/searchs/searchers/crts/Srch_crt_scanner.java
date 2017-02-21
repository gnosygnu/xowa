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
package gplx.xowa.addons.wikis.searchs.searchers.crts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.searchers.*;
import gplx.core.btries.*; import gplx.xowa.langs.cases.*;
import gplx.xowa.addons.wikis.searchs.parsers.*;
class Srch_crt_scanner {
	private final    List_adp tkns = List_adp_.New(); private byte[] src; private int src_len, pos, txt_bgn;
	private final    Srch_crt_scanner_syms trie_bldr; private final    Btrie_slim_mgr trie; private final    Btrie_rv trv = new Btrie_rv();
	private final    Bry_bfr word_bfr = Bry_bfr_.New(); private boolean word_is_dirty;		
	public Srch_crt_scanner(Srch_crt_scanner_syms trie_bldr) {
		this.trie_bldr = trie_bldr;
		this.trie = trie_bldr.Trie();
	}
	public Srch_crt_tkn[] Scan(byte[] src) {
		this.src = src; this.src_len = src.length;
		tkns.Clear(); pos = 0; txt_bgn = -1; 
		while (pos < src_len) {
			byte cur_b = src[pos];
			byte cur_tid = trie.Match_byte_or(trv, cur_b, src, pos, src_len, Byte_.Max_value_127);
			if (cur_tid == Byte_.Max_value_127) {					// text character
				if (txt_bgn == -1) txt_bgn = pos;					// 1st character not set; set it
				if (word_is_dirty) word_bfr.Add_byte(cur_b);
				++pos;
			}	
			else {													// \ \s " - & | ( )
				int pos_end = trv.Pos();
				if (	cur_tid == Srch_crt_tkn.Tid__not			// if "-"
					&&	txt_bgn != -1) {							// && "word has started"
						++pos;
						continue;									// ignore; EX: "a-b" -> "a-b"; "-ab" -> "NOT" "ab"
				}
				if (	txt_bgn != -1								// pending word
					&&	cur_tid != Srch_crt_tkn.Tid__escape			// not escape
					) {
					Add_word(Srch_crt_tkn.Tid__word, txt_bgn, pos);
				}
				switch (cur_tid) {
					case Srch_crt_tkn.Tid__escape:					// handle escape
						int nxt_pos = pos + 1;
						if (txt_bgn == -1) {
							txt_bgn = nxt_pos;
							word_is_dirty = true;
						} else {									// word has started; transfer existing word to bfr;
							if (!word_is_dirty) { 
								word_is_dirty = true;
								word_bfr.Add_mid(src, txt_bgn, pos);
							}
						}
						pos = nxt_pos;								// skip "\"
						if (pos < src_len) {
                            word_bfr.Add_byte(src[pos]);			// add next char literally
							++pos;
						}
						break;
					case Srch_crt_tkn.Tid__space:					// discard spaces
						pos = Bry_find_.Find_fwd_while(src, pos, src_len, trie_bldr.Space());
						break;
					case Srch_crt_tkn.Tid__quote:					// find end quote and add as word
						int quote_bgn = pos + 1;
						int quote_end = Int_.Min_value;
						int tmp_pos = quote_bgn;
						while (true) {
							quote_end = Bry_find_.Find_fwd(src, trie_bldr.Quote(), tmp_pos, src_len);
							if (quote_end == Bry_find_.Not_found) {	// no end-quote found; use space
								quote_end = Bry_find_.Find_fwd(src, trie_bldr.Space(), quote_bgn, src_len);
								if (quote_end == Bry_find_.Not_found) quote_end = src_len;	// no space found; use EOS
							}
							else {									// end-quote found; check if it's doubled
								int double_pos = quote_end + 1;
								if (	double_pos < src_len
									&&	src[double_pos] == Byte_ascii.Quote) {
									if (!word_is_dirty) {
										word_is_dirty = true;
									}
									word_bfr.Add_mid(src, tmp_pos, double_pos);
									tmp_pos = double_pos + 1;
									continue;
								}
							}
							break;
						}
						if (word_is_dirty)
							word_bfr.Add_mid(src, tmp_pos, quote_end);
						Add_word(Srch_crt_tkn.Tid__word_w_quote, quote_bgn, quote_end);
						pos = quote_end + 1;						// +1 to place after quote
						break;
					case Srch_crt_tkn.Tid__not: 
						Add_word(Srch_crt_tkn.Tid__not, pos, pos_end);
						pos = pos_end;
						break;
					case Srch_crt_tkn.Tid__paren_bgn: case Srch_crt_tkn.Tid__paren_end:
					case Srch_crt_tkn.Tid__and: case Srch_crt_tkn.Tid__or:
						tkns.Add(New_tkn(cur_tid, Bry_.Mid(src, pos, pos_end)));
						pos = pos_end;
						break;
					default: throw Err_.new_unhandled_default(cur_tid);
				}
			}
		}
		if (txt_bgn != -1) {	// pending word; create
			Add_word(Srch_crt_tkn.Tid__word, txt_bgn, pos);
		}
		return (Srch_crt_tkn[])tkns.To_ary_and_clear(Srch_crt_tkn.class);
	}
	private void Add_word(byte tid, int src_bgn, int src_end) {
		// generate word_bry
		byte[] word_bry = null;
		if (word_is_dirty) {
			word_is_dirty = false;
			if (word_bfr.Len_eq_0()) return;
			word_bry = word_bfr.To_bry_and_clear();
		}
		else {
			if (src_end - src_bgn == 0) return;
			word_bry = Bry_.Mid(src, src_bgn, src_end);
		}
		tkns.Add(New_tkn(tid, word_bry));
		txt_bgn = -1;
	}
	private static Srch_crt_tkn New_tkn(byte tid, byte[] val) {return new Srch_crt_tkn(tid, val);}
}
