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
package gplx.xowa.specials.search.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*; import gplx.xowa.specials.search.*;
import gplx.core.btries.*; import gplx.xowa.langs.cases.*;
class Xows_text_parser__v2 {
	private final Btrie_slim_mgr trie = Btrie_slim_mgr.cs();
	private final Bry_bfr bfr = Bry_bfr.new_(32);
	private Xob_word_mgr word_mgr; private Xol_case_mgr case_mgr;
	private byte[] src; private int end;	// bgn, 
	private boolean dirty; private Xows_text_tkn__split parser__ws;
	public void Init_for_ttl(Xob_word_mgr word_mgr, Xol_case_mgr case_mgr) {
		this.word_mgr = word_mgr; this.case_mgr = case_mgr;
		trie.Clear();
		this.parser__ws = Xows_text_tkn__split.new_(trie, Bool_.N, " ", "\t", "\n", "\r", "_");
		Xows_text_tkn__split.new_(trie, Bool_.N, "!", "?");
		Xows_text_tkn__split.new_(trie, Bool_.N, "(", ")");
		Xows_text_tkn__split.new_(trie, Bool_.N, "/");
		Xows_text_tkn__ellipsis.new_(trie, "..");
		Xows_text_tkn__apos.new_(trie, "'", Byte_ascii.Ltr_s);
	}
	public void Parse(byte[] src, int src_len, int bgn, int end) {
		this.src = src; this.end = end; // this.bgn = bgn;
		this.dirty = false; this.bgn__ws = bgn__dash = bgn__slash = -1;
		this.src = case_mgr.Case_build_lower(src); src_len = this.src.length;
		int pos = bgn;
		while (true) {
			boolean is_last = pos == end;
			if (is_last) {	// do split
				Word__add(bgn__ws, end);
				break;
			}
			byte b = src[pos];
			Object o = trie.Match_bgn_w_byte(b, src, pos, end);
			if (o == null) {		// unknown sequence; word-char
				Char__add(b, pos);
				++pos;
			}
			else {
				Xows_text_tkn parser = (Xows_text_tkn)o;
				pos = parser.Parse(this, src, end, pos, trie.Match_pos());
			}
		}
	}
	public int Bgn__ws()		{return bgn__ws;} private int bgn__ws;
	public int Bgn__dash()		{return bgn__dash;} private int bgn__dash;
	public int Bgn__slash()		{return bgn__slash;} private int bgn__slash;
	public void Bgn__ws_clear()		{this.bgn__ws = -1;}
	public void Bgn__dash_(int v)	{this.bgn__dash = v;}
	public void Bgn__slash_(int v)	{this.bgn__slash= v;}
	public void Word__add(int bgn__ws, int word_end) {
		if (bgn__ws == word_end) return;	// handle situations like "A!" where sym is at eos
		if (bgn__ws == -1) return;			// handle situations like "A... " where " " is different than "..."
		byte[] word = dirty ? bfr.To_bry_and_clear() : Bry_.Mid(src, bgn__ws, word_end);
		word_mgr.Add(word);
		if (bgn__dash != -1) {
			word_mgr.Add(Bry_.Mid(src, bgn__dash, word_end));
			bgn__dash = -1;
		}
	}
	public void Char__add(byte b, int pos) {
		if (dirty)
			bfr.Add_byte(b);
		else {
			if (bgn__ws == -1)
				bgn__ws = pos;
		}
	}
	public boolean Ws__is_word_end(int pos) {return parser__ws.Is_word_end(src, end, pos);}
}
class Xob_word_mgr {
	private final Ordered_hash hash = Ordered_hash_.New_bry();
	public void Clear() {hash.Clear();}
	public int Len() {return hash.Count();}
	public Xob_word_itm Get_at(int i) {return (Xob_word_itm)hash.Get_at(i);}
	public void Add(byte[] word) {
		Xob_word_itm itm = (Xob_word_itm)hash.Get_by(word);
		if (itm == null) {
			itm = new Xob_word_itm(word);
			hash.Add(word, itm);
		}
		itm.Count_add_1_();
	}
}
class Xob_word_itm {
	public Xob_word_itm(byte[] word) {
		this.word = word;
		this.count = 0;
	}
	public byte[] Word() {return word;} private final byte[] word;
	public int Count() {return count;} private int count; public void Count_add_1_() {++count;}
	@gplx.Internal protected Xob_word_itm Count_(int v) {this.count = v; return this;}
}
