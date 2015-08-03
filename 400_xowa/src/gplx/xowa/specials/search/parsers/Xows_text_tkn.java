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
import gplx.core.btries.*; import gplx.core.primitives.*;
interface Xows_text_tkn {
	int Parse(Xows_text_parser__v2 mgr, byte[] src, int src_end, int tkn_bgn, int tkn_end);
}
class Xows_text_tkn__split implements Xows_text_tkn {
	private final boolean add_sym_as_word;
	private final Btrie_slim_mgr trie = Btrie_slim_mgr.cs();
	public Xows_text_tkn__split(boolean add_sym_as_word, byte[][] ary) {
		this.add_sym_as_word = add_sym_as_word;
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			byte[] itm = ary[i];
			trie.Add_obj(itm, Int_obj_val.new_(itm.length));
		}
	}
	public int Parse(Xows_text_parser__v2 mgr, byte[] src, int src_end, int tkn_bgn, int tkn_end) {
		if (mgr.Bgn__ws() != -1)						// pending word
			mgr.Word__add(mgr.Bgn__ws(), tkn_bgn);		// make word; EX: "B" in "A B C"
		mgr.Bgn__ws_clear();
		if (add_sym_as_word)
			mgr.Word__add(tkn_bgn, tkn_end);
		int rv = tkn_end;
		while (rv < src_end) {	// skip ws; EX: "a   b"
			byte b = src[rv];
			Object o = trie.Match_bgn_w_byte(b, src, rv, src_end);
			if (o == null) break;
			Int_obj_val itm_len = (Int_obj_val)o;
			rv += itm_len.Val();
		}
		return rv;
	}
	public boolean Is_word_end(byte[] src, int end, int pos) {
		if (pos >= end) return true;
		byte b = src[pos];
		Object o = trie.Match_bgn_w_byte(b, src, pos, end);
		return o != null;
	}
	public static Xows_text_tkn__split new_(Btrie_slim_mgr trie, boolean add_sym_as_word, String... str_ary) {
		byte[][] bry_ary = Bry_.Ary(str_ary);
		Xows_text_tkn__split rv = new Xows_text_tkn__split(add_sym_as_word, bry_ary);
		int len = bry_ary.length;
		for (int i = 0; i < len; ++i)
			trie.Add_obj(bry_ary[i], rv);
		return rv;
	}
}
class Xows_text_tkn__ellipsis implements Xows_text_tkn {
	private final byte ellipsis_byte;
	public Xows_text_tkn__ellipsis(byte ellipsis_byte) {
		this.ellipsis_byte = ellipsis_byte;
	}
	public int Parse(Xows_text_parser__v2 mgr, byte[] src, int src_end, int tkn_bgn, int tkn_end) {
		int bgn__ws = mgr.Bgn__ws();
		if (bgn__ws != -1) {
			mgr.Word__add(bgn__ws, tkn_bgn);	// add word; EX: "Dreams" in "Dreams..."
			mgr.Bgn__ws_clear();				// clear ws flag, else space will create word; EX: "A... B"
		}
		int rv = Bry_finder.Find_fwd_while(src, tkn_end, src_end, ellipsis_byte);
		mgr.Word__add(tkn_bgn, rv);				// add ellipssis
		return rv;
	}
	public static Xows_text_tkn new_(Btrie_slim_mgr trie, String ellipsis) {
		byte[] bry = Bry_.new_u8(ellipsis);
		Xows_text_tkn rv = new Xows_text_tkn__ellipsis(Bry_.Get_at_end_or_fail(bry));
		trie.Add_obj(bry, rv);
		return rv;
	}
}
class Xows_text_tkn__apos implements Xows_text_tkn {
	private final byte possessive_byte;
	public Xows_text_tkn__apos(byte possessive_byte) {this.possessive_byte = possessive_byte;}
	public int Parse(Xows_text_parser__v2 mgr, byte[] src, int src_end, int tkn_bgn, int tkn_end) {
		int rv = tkn_end;
		byte apos_mode = Apos_contraction;						
		if		(tkn_end == src_end) {					// EX: "A'<EOS>"
			apos_mode = Apos_possessive_plural;
			rv = src_end;
		}
		else if (mgr.Ws__is_word_end(tkn_end)) {		// EX: "A' "
			apos_mode = Apos_possessive_plural;
			rv = tkn_end;
		}
		else if (	src[tkn_end] == possessive_byte		// EX: "A's "
				&&	mgr.Ws__is_word_end(tkn_end + 1))
			{
			apos_mode = Apos_possessive_singular;
			rv = tkn_end + 1;
		}
		switch (apos_mode) {
			case Apos_possessive_plural:
			case Apos_possessive_singular:
				int word_bgn = mgr.Bgn__ws();
				mgr.Word__add(word_bgn, tkn_bgn);			// add noun; EX: "wiki";
				mgr.Word__add(word_bgn, rv);				// add full; EX: "wiki's"
				mgr.Bgn__ws_clear();
				return rv;
			case Apos_contraction:
				break;
		} 
		return rv;
	}
	private static final byte Apos_contraction = 1, Apos_possessive_plural = 2, Apos_possessive_singular = 3;
	public static Xows_text_tkn new_(Btrie_slim_mgr trie, String apos, byte possessive_byte) {
		byte[] bry = Bry_.new_u8(apos);			
		Xows_text_tkn rv = new Xows_text_tkn__apos(possessive_byte);
		trie.Add_obj(bry, rv);
		return rv;
	}
}
//	class Xows_text_tkn__slash : Xows_text_tkn {
//		public int Parse(Xows_text_parser__v2 mgr, byte[] src, int src_end, int tkn_bgn, int tkn_end) {
//			int bgn__dash = mgr.Bgn__dash();
//			// add part;
//			int part_bgn = mgr.Bgn__dash();
//			if (part_bgn == -1)			// no dash
//				part_bgn = mgr.Bgn__ws();
//			if (part_bgn != -1)
//				mgr.Word__add(part_bgn, tkn_bgn);		// add word; EX: "a" in "a-b"
//			mgr.Bgn__dash_(tkn_end);
//			return tkn_end;
//		}
//		public static Xows_text_tkn new_(Btrie_slim_mgr trie, String dash) {
//			byte[] bry = Bry_.new_u8(dash);
//			Xows_text_tkn rv = new Xows_text_tkn__slash();
//			trie.Add_obj(bry, rv);
//			return rv;
//		}
//	}
