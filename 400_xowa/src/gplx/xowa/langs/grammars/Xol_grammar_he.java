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
public class Xol_grammar_he implements Xol_grammar {
	public boolean Grammar_eval(Bry_bfr bfr, Xol_lang_itm lang, byte[] word, byte[] type) {
		// if ( isset( $wgGrammarForms['he'][$case][$word] ) ) return $wgGrammarForms['he'][$case][$word]; // TODO_OLD: implement global $wgGrammarForms; WHEN: need to find he.w entries for DefaultSettings.php
		if (hash.Get_as_int_or(type, -1) == Tid__prefixed) {
			// Duplicate the "Waw" if prefixed, but not if it is already double.
			if	(	 Bry_.Match(word, 0, 2, Bry__waw__0)		// "ו"
				&&	!Bry_.Match(word, 0, 4, Bry__waw__1)		// "וו"
				)
				word = Bry_.Add(Bry__waw__0, word);
			// Remove the "He" article if prefixed
			if  (	 Bry_.Match(word, 0, 2, Bry__he__0))		// "ה"
				word = Bry_.Mid(word, 2);
			// Add a hyphen (maqaf) before non-Hebrew letters.
			if	(	 Bry_.Match(word, 0, 2, Bry__maqaf__0)			// "א"
				||	 Bry_.Compare(word, 0, 2, Bry__maqaf__1, 0, 2) == CompareAble_.More		// "ת"
				)
				word = Bry_.Add(Bry__maqaf__2, word);
		}
		bfr.Add(word);
		return true;
	}
	private static final int Tid__prefixed = 1;
	private static final    Hash_adp_bry hash = Hash_adp_bry.ci_u8(gplx.xowa.langs.cases.Xol_case_mgr_.U8())
	.Add_str_int("prefixed"	, Tid__prefixed)
	.Add_str_int("תחילית"	, Tid__prefixed)
	;
	private static final    byte[] 
	  Bry__waw__0 = Bry_.new_u8("ו"), Bry__waw__1 = Bry_.new_u8("וו")
	, Bry__he__0  = Bry_.new_u8("ה")
	, Bry__maqaf__0 = Bry_.new_u8("א"), Bry__maqaf__1 = Bry_.new_u8("ת"), Bry__maqaf__2 = Bry_.new_u8("־")
	;
}
