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
package gplx.xowa.langs.grammars; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
public class Xol_grammar_ru implements Xol_grammar {
	static final byte Genitive_null = 0, Genitive_bnkn = 1, Genitive_Bnkn = 26, Genitive_b = 3, Genitive_nr = 4, Genitive_ka = 5, Genitive_tn = 6, Genitive_abl = 7, Genitive_hnk = 8;
	private static ByteTrieMgr_bwd_slim Genitive_trie;
	private static ByteTrieMgr_bwd_slim genitive_trie_() {
		ByteTrieMgr_bwd_slim rv = new ByteTrieMgr_bwd_slim(false);
		genitive_trie_add(rv, Genitive_bnkn, "вики", null);
		genitive_trie_add(rv, Genitive_Bnkn, "Вики", null);
		genitive_trie_add(rv, Genitive_b, "ь", "я");
		genitive_trie_add(rv, Genitive_nr, "ия", "ии");
		genitive_trie_add(rv, Genitive_ka, "ка", "ки");
		genitive_trie_add(rv, Genitive_tn, "ти", "тей");
		genitive_trie_add(rv, Genitive_abl, "ды", "дов");
		genitive_trie_add(rv, Genitive_hnk , "ник", "ника");
		return rv;
	}
	private static void genitive_trie_add(ByteTrieMgr_bwd_slim trie, byte tid, String find_str, String repl_str) {
		byte[] find_bry = Bry_.new_utf8_(find_str);
		byte[] repl_bry = repl_str == null ? null : Bry_.new_utf8_(repl_str);
		Xol_grammar_ru_genitive_itm itm = new Xol_grammar_ru_genitive_itm(tid, find_bry, repl_bry);
		trie.Add(find_bry, itm);
	}
	public boolean Grammar_eval(Bry_bfr bfr, Xol_lang lang, byte[] word, byte[] type) {
		if (Bry_.Len_eq_0(word)) return true;	// empty_string returns ""
		byte tid = Xol_grammar_.Tid_of_type(type);
		switch (tid) {
			case Xol_grammar_.Tid_genitive:		{
				if (Genitive_trie == null) Genitive_trie = genitive_trie_(); 
				Object o = Genitive_trie.MatchAtCur(word, word.length - 1, -1);
				if (o != null) {
					Xol_grammar_ru_genitive_itm itm = (Xol_grammar_ru_genitive_itm)o;
					if (!itm.Repl_is_noop()) {
						bfr.Add_mid(word, 0, Genitive_trie.Match_pos() + 1);
						bfr.Add(itm.Repl());
						return true;
					}
				}
				break;
			}
			case Xol_grammar_.Tid_dative:		break;
			case Xol_grammar_.Tid_accusative:	break;
			case Xol_grammar_.Tid_instrumental:	break;
			case Xol_grammar_.Tid_prepositional:break;
		}
		bfr.Add(word);
		return true;
	}
}
class Xol_grammar_ru_genitive_itm {
	public Xol_grammar_ru_genitive_itm(byte tid, byte[] find, byte[] repl) {this.tid = tid; this.find = find; this.repl = repl;}
	public byte Tid() {return tid;} private byte tid;
	public byte[] Find() {return find;} private byte[] find;
	public byte[] Repl() {return repl;} private byte[] repl;
	public boolean Repl_is_noop() {return repl == null;}
}
