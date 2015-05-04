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
package gplx.xowa.bldrs.cmds.texts; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.core.btries.*; import gplx.xowa.langs.cases.*;
class Xob_word_parser {
	private final Btrie_slim_mgr trie = Btrie_slim_mgr.cs_();
	private final Bry_bfr bfr = Bry_bfr.new_(32);
	private Xob_word_mgr word_mgr; private Xol_case_mgr case_mgr;
	private byte[] src; // private int bgn, end, src_len;
	private boolean dirty; private int word_bgn;
	public void Init_for_ttl(Xob_word_mgr word_mgr, Xol_case_mgr case_mgr) {
		this.word_mgr = word_mgr; this.case_mgr = case_mgr;
		trie.Clear();
		Init_tkn(Xob_word_tkn.new_(" ").Split_y_());
		Init_tkn(Xob_word_tkn.new_("_").Split_y_());
		Init_tkn(Xob_word_tkn.new_("..").Split_y_().Extend_y_());
	}
	private void Init_tkn(Xob_word_tkn tkn) {trie.Add_obj(tkn.Key(), tkn);}
	private void Mgr__add(int word_end) {
		byte[] word = dirty ? bfr.Xto_bry_and_clear() : Bry_.Mid(src, word_bgn, word_end);
		word_mgr.Add(word);
		word_bgn = -1;
	}
	public void Parse(byte[] src, int bgn, int end, int src_len) {
		this.src = src; // this.bgn = bgn; this.end = end; this.src_len = src_len;
		this.dirty = false; this.word_bgn = -1;
		this.src = case_mgr.Case_build_lower(src);
		int pos = bgn;
		while (true) {
			boolean add_to_word = false;
			boolean is_last = pos == end;
			if (is_last) {	// do split
				Mgr__add(end);
				break;
			}
			byte b = src[pos];
			Object o = trie.Match_bgn_w_byte(b, src, pos, end);
			int new_pos = -1;
			if (o == null) {		// unknown sequence; word-char
				add_to_word = true;
				new_pos = pos + 1;
			}
			else {
				int tkn_end = trie.Match_pos();
				Xob_word_tkn tkn = (Xob_word_tkn)o;
				if (tkn.Split()) {	// "A b" -> "A", "b"
					add_to_word = false;
					if (word_bgn != -1)	// handle sequences like "... " where "..." sets word_bgn to -1
						Mgr__add(pos);
					tkn_end = Bry_finder.Find_fwd_while(src, tkn_end, end, tkn.Key_last_byte());
					if (tkn.Extend()) {
						word_bgn = pos;
						Mgr__add(tkn_end);
					}
					pos = tkn_end;
					continue;
				}
				add_to_word = true;
				new_pos = tkn_end;
			}
			if (add_to_word) {
				if (dirty)
					bfr.Add_byte(src[pos]);
				else {
					if (word_bgn == -1)
						word_bgn = pos;
				}
			}
			pos = new_pos;
		}
	}		
}
class Xob_word_tkn {
	public Xob_word_tkn(byte[] key) {this.key = key; this.key_last_byte = key[key.length - 1];}
	public byte[] Key() {return key;} private final byte[] key;
	public byte Key_last_byte() {return key_last_byte;} private final byte key_last_byte;
	public boolean Split() {return split;} public Xob_word_tkn Split_y_() {split = true; return this;} private boolean split;
	public boolean Extend() {return extend;} public Xob_word_tkn Extend_y_() {extend = true; return this;} private boolean extend;
	public static Xob_word_tkn new_(String v) {return new Xob_word_tkn(Bry_.new_utf8_(v));}
}
class Xob_word_mgr {
	private final OrderedHash hash = OrderedHash_.new_bry_();
	public void Clear() {hash.Clear();}
	public int Len() {return hash.Count();}
	public Xob_word_itm Get_at(int i) {return (Xob_word_itm)hash.FetchAt(i);}
	public void Add(byte[] word) {
		Xob_word_itm itm = (Xob_word_itm)hash.Fetch(word);
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
