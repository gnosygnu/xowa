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
package gplx.xowa.langs.grammars; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.core.btries.*;
public class Xol_grammar_ru implements Xol_grammar {
	static final byte Genitive_null = 0, Genitive_bnkn = 1, Genitive_Bnkn = 26, Genitive_b = 3, Genitive_nr = 4, Genitive_ka = 5, Genitive_tn = 6, Genitive_abl = 7, Genitive_hnk = 8;
	private final    Btrie_rv trv = new Btrie_rv();
	private static Btrie_bwd_mgr Genitive_trie;
	private static Btrie_bwd_mgr genitive_trie_() {
		Btrie_bwd_mgr rv = new Btrie_bwd_mgr(false);
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
	private static void genitive_trie_add(Btrie_bwd_mgr trie, byte tid, String find_str, String repl_str) {
		byte[] find_bry = Bry_.new_u8(find_str);
		byte[] repl_bry = repl_str == null ? null : Bry_.new_u8(repl_str);
		Xol_grammar_ru_genitive_itm itm = new Xol_grammar_ru_genitive_itm(tid, find_bry, repl_bry);
		trie.Add(find_bry, itm);
	}
	public boolean Grammar_eval(Bry_bfr bfr, Xol_lang_itm lang, byte[] word, byte[] type) {
		if (Bry_.Len_eq_0(word)) return true;	// empty_string returns ""
		byte tid = Xol_grammar_.Tid_of_type(type);
		switch (tid) {
			case Xol_grammar_.Tid_genitive:		{
				if (Genitive_trie == null) Genitive_trie = genitive_trie_(); 
				Object o = Genitive_trie.Match_at(trv, word, word.length - 1, -1);
				if (o != null) {
					Xol_grammar_ru_genitive_itm itm = (Xol_grammar_ru_genitive_itm)o;
					if (!itm.Repl_is_noop()) {
						bfr.Add_mid(word, 0, trv.Pos() + 1);
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
