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
public class Xol_grammar_ {
	public static final byte Tid__max = 9; 
	public static final byte Tid_genitive = 0, Tid_elative = 1, Tid_partitive = 2, Tid_illative = 3, Tid_inessive = 4, Tid_accusative = 5, Tid_instrumental = 6, Tid_prepositional = 7, Tid_dative = 8, Tid_unknown = Byte_.Max_value_127;
	private static final    Btrie_slim_mgr Tid_trie = Btrie_slim_mgr.ci_a7()	// NOTE:ci.ascii:MW kwds
	.Add_str_byte("genitive", Tid_genitive)
	.Add_str_byte("elative", Tid_elative)
	.Add_str_byte("partitive", Tid_partitive)
	.Add_str_byte("illative", Tid_illative)
	.Add_str_byte("inessive", Tid_inessive)
	.Add_str_byte("accusative", Tid_accusative)
	.Add_str_byte("instrumental", Tid_instrumental)
	.Add_str_byte("prepositional", Tid_prepositional)
	.Add_str_byte("dative", Tid_dative)
	;
	public static byte Tid_of_type(byte[] v) {
		if (Bry_.Len_eq_0(v)) return Tid_unknown;
		Object o = Xol_grammar_.Tid_trie.Match_exact(v, 0, v.length);
		return o == null ? Tid_unknown : ((Byte_obj_val)o).Val();
	}
	public static Xol_grammar new_by_lang_id(int lang_id) {
		switch (lang_id) {
			case Xol_lang_stub_.Id_fi:	return new Xol_grammar_fi();
			case Xol_lang_stub_.Id_ru:	return new Xol_grammar_ru();
			case Xol_lang_stub_.Id_he:	return new Xol_grammar_he();
			case Xol_lang_stub_.Id_pl:
			case Xol_lang_stub_.Id_cs:	// PAGE:cs.q; DATE:2016-09-04
										return Xol_grammar__noop.Instance;
			default:					return Xol_grammar__unimplemented.Instance;
		}
	}
}
class Xol_grammar__unimplemented implements Xol_grammar {
	public boolean Grammar_eval(Bry_bfr bfr, Xol_lang_itm lang, byte[] word, byte[] type) {return false;}
	public static final    Xol_grammar__unimplemented Instance = new Xol_grammar__unimplemented(); Xol_grammar__unimplemented() {}
}
class Xol_grammar__noop implements Xol_grammar {
	public boolean Grammar_eval(Bry_bfr bfr, Xol_lang_itm lang, byte[] word, byte[] type) {bfr.Add(word); return true;}
	public static final    Xol_grammar__noop Instance = new Xol_grammar__noop(); Xol_grammar__noop() {}
}
