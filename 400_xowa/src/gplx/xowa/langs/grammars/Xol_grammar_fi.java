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
import gplx.core.primitives.*; import gplx.core.btries.*;
import gplx.xowa.apps.urls.*;
public class Xol_grammar_fi implements Xol_grammar {
	public boolean Vowel_harmony(byte[] word, int word_len) {
		// $aou = preg_match( '/[aou][^äöy]*$/i', $word );
		boolean aou_found = false;
		for (int i = 0; i < word_len; i++) {
			byte b = word[i];
			Object o = trie_vh.Match_bgn_w_byte(b, word, i, word_len);
			if (o != null) {
				byte vh_type = ((Byte_obj_val)o).Val();
				if (vh_type == Trie_vh_back)
					aou_found = true;
				else
					aou_found = false;
			}
		}
		return aou_found;
	}
	public boolean Grammar_eval(Bry_bfr bfr, Xol_lang_itm lang, byte[] word, byte[] type) {
		if (Bry_.Len_eq_0(word)) return true;	// empty_string returns ""
		byte tid = Xol_grammar_.Tid_of_type(type);
		if (tid == Xol_grammar_.Tid_unknown) {bfr.Add(word); return true;} // unknown type returns word
		// PHP: if (isset($wgGrammarForms['fi'][$case][$word])){ return $wgGrammarForms['fi'][$case][$word];
		if (manual_regy == null) {
			manual_regy = new Xol_grammar_manual_regy()
			.Itms_add(Xol_grammar_.Tid_elative, "Wikiuutiset", "Wikiuutisista");
		}
		byte[] manual_repl = manual_regy.Itms_get(tid, word);
		if (manual_repl != null) {
			bfr.Add(manual_repl);
			return true;
		}
		bfr.Add(word); // NOTE: preemptively add word now; the rest of this function takes "word" and adds other letters to it;
		int word_len = word.length;
		byte[] lower = lang.Case_mgr().Case_build_lower(word, 0, word_len);
		boolean aou = Vowel_harmony(lower, word_len);
		// PHP: if ( preg_match( '/wiki$/i', $word ) ) $aou = false;
		if (aou && Bry_.Has_at_end(lower, Bry_wiki))
			aou = false;			
		// PHP: if ( preg_match( '/[bcdfghjklmnpqrstvwxz]$/i', $word ) ) $word .= 'i';
		switch (lower[word_len - 1]) {
			case Byte_ascii.Ltr_b: case Byte_ascii.Ltr_c: case Byte_ascii.Ltr_d: case Byte_ascii.Ltr_f: case Byte_ascii.Ltr_g:
			case Byte_ascii.Ltr_h: case Byte_ascii.Ltr_j: case Byte_ascii.Ltr_k: case Byte_ascii.Ltr_l: case Byte_ascii.Ltr_m:
			case Byte_ascii.Ltr_n: case Byte_ascii.Ltr_p: case Byte_ascii.Ltr_q: case Byte_ascii.Ltr_r: case Byte_ascii.Ltr_s:
			case Byte_ascii.Ltr_t: case Byte_ascii.Ltr_v: case Byte_ascii.Ltr_w: case Byte_ascii.Ltr_x: case Byte_ascii.Ltr_z:
				bfr.Add_byte(Byte_ascii.Ltr_i);
				break;
		}

		switch (tid) {
			case Xol_grammar_.Tid_genitive:		bfr.Add_byte(Byte_ascii.Ltr_n); break;						// case 'genitive': $word .= 'n';
			case Xol_grammar_.Tid_elative:		bfr.Add(aou ? Bry_sta_y : Bry_sta_n); break;				// case 'elative': $word .= ( $aou ? 'sta' : 'stä' );
			case Xol_grammar_.Tid_partitive:	bfr.Add(aou ? Bry_a_y : Bry_a_n); break;					// case 'partitive': $word .= ( $aou ? 'a' : 'ä' );
			case Xol_grammar_.Tid_inessive:		bfr.Add(aou ? Bry_ssa_y : Bry_ssa_n); break;				// case 'inessive': $word .= ( $aou ? 'ssa' : 'ssä' );
			case Xol_grammar_.Tid_illative:		bfr.Add_byte(word[word_len - 1]).Add_byte(Byte_ascii.Ltr_n); break;// # Double the last letter and add 'n'
		}
		return true;
	}	private static Xol_grammar_manual_regy manual_regy;
	private static final    byte[] Bry_sta_y = Bry_.new_a7("sta"), Bry_sta_n = Bry_.new_u8("stä"), Bry_a_y = Bry_.new_a7("a"), Bry_a_n = Bry_.new_u8("ä"), Bry_ssa_y = Bry_.new_a7("ssa"), Bry_ssa_n = Bry_.new_u8("ssä");
	static final byte Trie_vh_back = 0, Trie_vh_front = 1;
	private static Btrie_slim_mgr trie_vh = Btrie_slim_mgr.cs().Add_str_byte__many(Trie_vh_back, "a", "o", "u").Add_str_byte__many(Trie_vh_front, "ä", "ö", "y");
	private static final    byte[] Bry_wiki = Bry_.new_a7("wiki");
}
