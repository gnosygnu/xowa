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
package gplx.xowa.specials.search; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.core.primitives.*; import gplx.core.btries.*;
class Xosrh_scanner {
	List_adp tkns = List_adp_.new_(); byte[] src; int src_len; int pos; int txt_bgn;
	public Xosrh_qry_tkn[] Scan(byte[] src) {			
		this.src = src; this.src_len = src.length;
		tkns.Clear(); pos = 0; txt_bgn = -1; 
		while (pos < src_len) {
			byte cur_b = src[pos];
			Object cur_obj = trie.Match_bgn_w_byte(cur_b, src, pos, src_len);
			if (cur_obj == null) {					// text character
				if (txt_bgn == -1) txt_bgn = pos;	// 1st character not set; set it
				++pos;
			}	
			else {									// AND, OR, (, ), -, \s, "
				int pos_end = trie.Match_pos();
				byte cur_tid = ((Byte_obj_val)cur_obj).Val(); 
				if (Cur_join_is_word(cur_tid, pos_end)) continue;
				if (txt_bgn != -1) {	// pending word; create
					Tkns_add_word(Xosrh_qry_tkn.Tid_word, txt_bgn, pos);
					txt_bgn = -1;
				}
				switch (cur_tid) {
					case Xosrh_qry_tkn.Tid_space:	// discard spaces
						pos = Bry_find_.Find_fwd_while(src, pos, src_len, Byte_ascii.Space);
						break;
					case Xosrh_qry_tkn.Tid_quote:	// find end quote and add as word
						int quote_bgn = pos + 1;
						int quote_end = Bry_find_.Find_fwd(src, Byte_ascii.Quote, quote_bgn, src_len);
						if (quote_end == Bry_find_.Not_found) throw Err_.new_wo_type("could not find end quote", "src", String_.new_u8(src));
						Tkns_add_word(Xosrh_qry_tkn.Tid_word_quoted, quote_bgn, quote_end);
						pos = quote_end + 1;		// +1 to place after quote
						break;
					case Xosrh_qry_tkn.Tid_not: 
						Tkns_add_word(Xosrh_qry_tkn.Tid_not, pos, pos_end);
						pos = pos_end;
						break;
					case Xosrh_qry_tkn.Tid_paren_bgn: case Xosrh_qry_tkn.Tid_paren_end:
					case Xosrh_qry_tkn.Tid_and: case Xosrh_qry_tkn.Tid_or:
						tkns.Add(new_tkn_(cur_tid, pos, pos_end));
						pos = pos_end;
						break;
					default: throw Err_.new_unhandled(cur_tid);
				}
			}
		}
		if (txt_bgn != -1) {	// pending word; create
			Tkns_add_word(Xosrh_qry_tkn.Tid_word, txt_bgn, pos);
			txt_bgn = -1;
		}
		return (Xosrh_qry_tkn[])tkns.To_ary_and_clear(Xosrh_qry_tkn.class);
	}
	boolean Cur_join_is_word(byte cur_tid, int pos_end) {	// extra logic to handle and / or occuring in unquoted strings; EX: random; for
		switch (cur_tid) {
			default: return false;	// only look at AND, OR, -
			case Xosrh_qry_tkn.Tid_and: case Xosrh_qry_tkn.Tid_or: case Xosrh_qry_tkn.Tid_not:
				break;
		}
		boolean join_is_word = true;
		if (txt_bgn == -1) {		// no pending word;
			if (cur_tid == Xosrh_qry_tkn.Tid_not) return false;	// NOT is only operator if no pending tkn; EX: -abc -> NOT abc; a-b -> a-b
			byte nxt_b = pos_end < src_len ? src[pos_end] : Byte_ascii.Null;
			Object nxt_obj = trie.Match_bgn_w_byte(nxt_b, src, pos_end, src_len);
			if (nxt_obj == null)	// next tkn is text; join must be word
				join_is_word = true;
			else {					// next tkn is tkn
				byte nxt_tid = ((Byte_obj_val)nxt_obj).Val(); 
				switch (nxt_tid) {
					case Xosrh_qry_tkn.Tid_space: case Xosrh_qry_tkn.Tid_quote:
					case Xosrh_qry_tkn.Tid_paren_bgn: case Xosrh_qry_tkn.Tid_paren_end:
						join_is_word = false;	// next tkn is sym; and/or is not word; EX: a AND ; a AND"b"; a AND(b)
						break;
					case Xosrh_qry_tkn.Tid_not: case Xosrh_qry_tkn.Tid_and: case Xosrh_qry_tkn.Tid_or:
						join_is_word = true;	// next tkn is and or not; and/or is word; EX: andor; oror; or-abc;
						break;
					default: throw Err_.new_unhandled(cur_tid);
				}
			}
		}
		else {						// pending word; cur join must be word; EX: "grand": "and" invoked and "gr" pending
			join_is_word = true;
		}
		if (join_is_word) {
			if (txt_bgn == -1) txt_bgn = pos;	// 1st character not set; set it
			pos = pos_end;
			return true;
		}
		if (txt_bgn != -1) {
			Tkns_add_word(Xosrh_qry_tkn.Tid_word, txt_bgn, pos); // create word
			txt_bgn = -1;
		}
		return false;
	}
	private void Tkns_add_word(byte tid, int src_bgn, int src_end) {
		if (tkns.Count() > 0) {	// at least 1 tkn
			Xosrh_qry_tkn last_tkn = (Xosrh_qry_tkn)tkns.Get_at_last();
			if (last_tkn.Tid() == Xosrh_qry_tkn.Tid_word)	// previous tkn is not word; auto "AND" words; EX: A B -> A AND B
				tkns.Add(Xosrh_qry_tkn.new_bry_(Xosrh_qry_tkn.Tid_and, Bry_and));
		}
		if (tid == Xosrh_qry_tkn.Tid_word) {	// if word has symbol, convert to quoted; EX: a-b should become "a-b"; otherwise searcher would search for a single word a-b
			byte[] cur_word = Bry_.Mid(src, src_bgn, src_end);
			byte[][] words = gplx.xowa.bldrs.cmds.texts.Xob_search_base.Split_ttl_into_words(null, tmp_list, tmp_bfr, cur_word);
			int words_len = words.length;
			if (words_len == 1 && !Bry_.Eq(words[0], cur_word) && Bry_find_.Find_fwd(cur_word, Byte_ascii.Star) == -1) {
				tkns.Add(Xosrh_qry_tkn.new_bry_(tid, words[0]));
				return;
			}
			if (words.length > 1)
				tid = Xosrh_qry_tkn.Tid_word_quoted;
		}
		tkns.Add(new_tkn_(tid, src_bgn, src_end));
	}
	Ordered_hash tmp_list = Ordered_hash_.New(); Bry_bfr tmp_bfr = Bry_bfr.new_();
	Xosrh_qry_tkn new_tkn_(byte tid, int val_bgn, int val_end) {return Xosrh_qry_tkn.new_pos_(tid, val_bgn, val_end);}
	private static byte[] Bry_and = Bry_.new_a7("AND");
	private static final Btrie_slim_mgr trie = Btrie_slim_mgr.ci_a7()// NOTE:ci.ascii:OR / AND only
	.Add_str_byte(" ", Xosrh_qry_tkn.Tid_space)
	.Add_str_byte("\"", Xosrh_qry_tkn.Tid_quote)
	.Add_str_byte("-", Xosrh_qry_tkn.Tid_not)
	.Add_str_byte("(", Xosrh_qry_tkn.Tid_paren_bgn)
	.Add_str_byte(")", Xosrh_qry_tkn.Tid_paren_end)
	.Add_str_byte("or", Xosrh_qry_tkn.Tid_or)
	.Add_str_byte("and", Xosrh_qry_tkn.Tid_and);
	public static final Xosrh_scanner Instance = new Xosrh_scanner(); Xosrh_scanner() {}
}
class Xosrh_qry_tkn {
	Xosrh_qry_tkn(byte tid, int val_bgn, int val_end, byte[] val_bry) {this.tid = tid; this.val_bgn = val_bgn; this.val_end = val_end; this.val_bry = val_bry;}
	public byte Tid() {return tid;} private byte tid;	
	int val_bgn = -1;
	int val_end = -1;
	byte[] val_bry;
	public byte[] Val(byte[] src) {return val_bry == null ? Bry_.Mid(src, val_bgn, val_end) : val_bry;}
	public static Xosrh_qry_tkn new_pos_(byte tid, int val_bgn, int val_end)		{return new Xosrh_qry_tkn(tid, val_bgn, val_end, null);}
	public static Xosrh_qry_tkn new_bry_(byte tid, byte[] val_bry)					{return new Xosrh_qry_tkn(tid, -1, -1, val_bry);}
	public static final byte Tid_root = 1, Tid_word = 2, Tid_word_quoted = 3, Tid_space = 4, Tid_quote = 5, Tid_not = 6, Tid_paren_bgn = 7, Tid_paren_end = 8, Tid_or = 9, Tid_and = 10, Tid_eos = 11;
}
