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
import gplx.core.btries.*; import gplx.xowa.langs.cases.*;
public class Srch_text_parser {
	private Btrie_slim_mgr parser_trie = Btrie_slim_mgr.cs(); public Btrie_slim_mgr word_end_trie = Btrie_slim_mgr.cs(); private Btrie_slim_mgr word_bgn_trie = Btrie_slim_mgr.cs();
	private final    Btrie_rv trv = new Btrie_rv();
	private Xol_case_mgr case_mgr;
	public final    Bry_bfr Tmp_bfr = Bry_bfr_.New_w_size(32);
	private byte[] src; private int end;
	private Srch_sym_parser__split parser__ws; private Srch_sym_parser__dash parser__dash;
	public Srch_word_hash word_hash = new Srch_word_hash();
	public boolean lcase = true;
	public Srch_text_parser Init_for_ttl(Xol_case_mgr case_mgr) {
		this.case_mgr = case_mgr;
		parser_trie.Clear(); word_end_trie.Clear();
		parser__ws = new Srch_sym_parser__split(Bool_.Y, " ", "\t", "\n", "\r", "_");
		parser__dash = new Srch_sym_parser__dash("-");
		Parsers__reg(Parsers__make__word_end
		( "!", "?", ",", ":", ";", "\"", "~"
		//, "@", "&", "*", "`", "+"			// should add for symmetry of word_bgn trie but strips "@Home" to "Home" only; also, several have many "*", "`", "+"
		));
		Parsers__reg(new Srch_sym_parser__split(Bool_.N, "/"));
		Parsers__reg(parser__ws, parser__dash
			, new Srch_sym_parser__paren_bgn(Byte_ascii.Paren_bgn, Byte_ascii.Paren_end)
			, new Srch_sym_parser__dot("."), new Srch_sym_parser__ellipsis(Byte_ascii.Dot, ".."), new Srch_sym_parser__apos("'"));	// NOTE: [ ] { } do not exist in titles
		word_bgn_trie.Add_many_int(1
		// , "!", "?", ",", ":", ";"		// low #; should add for symmetry of word_end trie;
		, "\""								// adding for symmetry of word_end trie;
		, "@", "&", "*", "`", "~", "+"		// "@Home", "&c.", "&NSYNC", "Muhammad_ibn_`Ali_at-Tirmidhi"; "Phantom_~Requiem_for_the_Phantom~"; "+pool"
		);
		return this;
	}
	public Srch_text_parser Make_copy() {
		Srch_text_parser rv = new Srch_text_parser();
		rv.parser_trie = parser_trie; rv.word_end_trie = word_end_trie; rv.word_bgn_trie = word_bgn_trie;
		rv.parser__ws = parser__ws; rv.parser__dash = parser__dash;
		rv.word_hash = word_hash; rv.case_mgr = case_mgr;
		rv.lcase = lcase;
		return rv;
	}
	public byte[][] Parse_to_bry_ary(boolean lcase, byte[] src_orig) {
		word_hash.Clear();
		this.lcase = lcase;
		Parse(lcase, src_orig, 0, src_orig.length);
		int hash_len = word_hash.Len();
		byte[][] rv = new byte[hash_len][];
		for (int i = 0; i < hash_len; ++i) {
			Srch_word_itm itm = (Srch_word_itm)word_hash.Get_at(i);
			rv[i] = itm.Word;
		}
		return rv;
	}
	public void Parse(Srch_text_parser_wkr wkr, byte[] src_orig) {
		word_hash.Clear();
		Parse(Bool_.Y, src_orig, 0, src_orig.length);
		int hash_len = word_hash.Len();
		for (int i = 0; i < hash_len; ++i) {
			Srch_word_itm itm = (Srch_word_itm)word_hash.Get_at(i);
			wkr.Parse_done(itm);
		}
	}
	public void Parse(boolean lcase, byte[] src_orig, int bgn, int end_orig) {
		this.src = lcase ? case_mgr.Case_build_lower(src_orig) : src_orig;
		this.end = end_orig + (src.length - src_orig.length);
		this.cur_bgn = dash_bgn = Srch_text_parser.None;
		int pos = bgn;
		while (true) {
			boolean pos_is_last = pos == end;
			if (pos_is_last) {						// EOS and pending word; add it
				if (cur_bgn != -1)
					Words__add__chk_dash(cur_bgn, end);
				break;
			}
			byte b = src[pos];
			Object o = parser_trie.Match_at_w_b0(trv, b, src, pos, end);
			if (o == null) {						// unknown sequence; word-char
				if (cur_bgn == -1) cur_bgn = pos;	// set 1st char for word
				++pos;
			}
			else {
				Srch_sym_parser parser = (Srch_sym_parser)o;
				pos = parser.Parse(this, src, end, pos, trv.Pos());
			}
		}
	}
	public int	Cur__bgn()					{return cur_bgn;}		private int cur_bgn;			public void Cur__bgn__reset() {this.cur_bgn = -1;}
	public void Cur__bgn__set(int v)		{this.cur_bgn = v;}	// called from dash parser
	public int	Dash__bgn()					{return dash_bgn;}		private int dash_bgn;			public void Dash__bgn_(int v) {this.dash_bgn = v;}
	public int	Cur__end__find(int pos)		{return parser__ws.Find_fwd(src, pos, end);}
	public boolean Cur__end__chk(int pos)		{return parser__ws.Is_next(src, pos, end);}
	public int	Cur__end__find__text_only(int pos) {	// primarily for parens and finding word_end after ")"; EX: "(city), " vs "(a)b "
		while (pos < end) {
			byte b = src[pos];
			Object parser_obj = parser_trie.Match_bgn_w_byte(b, src, pos, end);
			if (parser_obj == null)	// b is text; increment by 1 and continue searching
				++pos;
			else					// b is some sort of symbol; end word here; EX: "a," should produce "a", not "a,"
				return pos;
		}
		return end;
	}
	public void Words__add_if_pending_and_clear(int word_end) {
		if (cur_bgn == -1) return; // exit; no pending words
		this.Words__add__chk_dash(cur_bgn, word_end);
		cur_bgn = -1;
	}
	public void Words__add__chk_dash(int word_bgn, int word_end) {
		parser__dash.Add_pending_word(this, src, word_end);
		byte[] word = Bry_.Mid(src, word_bgn, word_end);
		Words__add_direct(word);
	}
	public void Words__add_direct(int bgn, int end)	{Words__add_direct(Bry_.Mid(src, bgn, end));}
	public void Words__add_direct(byte[] bry) {
		word_hash.Add(bry);

		// remove punctuation at bgn of word; EX: "@Home" -> "Home"
		boolean dirty = false;
		int pos = 0; int len = bry.length;
		while (pos < len) {
			byte b = bry[pos];
			if (word_bgn_trie.Match_at_w_b0(trv, b, bry, pos, len) != null) {	// b is symbol;
				dirty = true;
				pos = trv.Pos();
			}
			else {
				break;
			}
		}
		if (dirty && pos < len) {
			byte[] trunc = Bry_.Mid(bry, pos, len);
			// if (!word_hash.Has(trunc))	// don't add if it exists; EX: "'tis"
			word_hash.Add(trunc);
		}
	}

	private void Parsers__reg(Srch_sym_parser... parsers_ary) {
		int parsers_len = parsers_ary.length;
		for (int i = 0; i < parsers_len; ++i) {
			Srch_sym_parser parser = parsers_ary[i];
			byte[][] hooks_ary = parser.Hooks_ary();
			int hooks_len = hooks_ary.length;
			for (int j = 0; j < hooks_len; ++j) {
				byte[] hook = hooks_ary[j];
				parser_trie.Add_obj(hook, parser);
				if (parser.Tid() == Srch_sym_parser_.Tid__terminal)
					word_end_trie.Add_obj(hook, parser);
			}
		}
	}
	private static Srch_sym_parser[] Parsers__make__word_end(String... hooks) {
		int rv_len = hooks.length;
		Srch_sym_parser[] rv = new Srch_sym_parser[rv_len];
		for (int i = 0; i < rv_len; ++i)
			rv[i] = new Srch_sym_parser__terminal(Bry_.new_u8(hooks[i]));
		return rv;
	}
	public static final int None = -1;
}
