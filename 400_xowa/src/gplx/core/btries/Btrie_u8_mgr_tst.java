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
package gplx.core.btries; import gplx.*; import gplx.core.*;
import org.junit.*;
import gplx.xowa.langs.cases.*;
public class Btrie_u8_mgr_tst {
	@Before public void init() {fxt.Clear();} private Btrie_u8_mgr_fxt fxt = new Btrie_u8_mgr_fxt();
	@Test  public void Ascii() {
		fxt.Init_add(Bry_.new_a7("a")	,	"1");
		fxt.Init_add(Bry_.new_a7("abc")	, "123");
		fxt.Test_match("a"		, "1");			// single.exact
		fxt.Test_match("abc"	, "123");		// many.exact
		fxt.Test_match("ab"		, "1");			// single.more
		fxt.Test_match("abcde"	, "123");		// many.more
		fxt.Test_match(" a"		, null);		// no_match
		fxt.Test_match("aBC"	, "123");		// upper
	}
	@Test  public void Uft8() {
		fxt.Init_add(Bry_.new_u8("aéi")	, "1");
		fxt.Test_match("aéi"	, "1");			// exact
		fxt.Test_match("aÉi"	, "1");			// upper.utf8
		fxt.Test_match("AÉI"	, "1");			// upper.all
		fxt.Test_match("AÉIo"	, "1");			// trailing-char
		fxt.Test_match("aei"	, null);		// no_match
	}
	@Test  public void Uft8_match_pos() {
		fxt.Init_add(Bry_.new_u8("aéi")	, "1");
		fxt.Test_match_pos("aAÉI"	, 1, "1");	// match at 1
		fxt.Test_match_pos("aAÉI"	, 0, null);	// no_match at 0
	}
	@Test  public void Uft8_asymmetric() {
		fxt.Init_add(Bry_.new_u8("İ")	, "1");
		fxt.Test_match("İ"	, "1");				// exact=y; İ = Bry_.New_by_ints(196,176)
		fxt.Test_match("i"	, "1");				// lower=y; i = Bry_.New_by_ints(105)
		fxt.Test_match("I"	, null);			// upper=n; I = Bry_.New_by_ints( 73); see Btrie_u8_itm and rv.asymmetric_bry

		fxt.Clear();
		fxt.Init_add(Bry_.new_u8("i")	, "1");
		fxt.Test_match("i"	, "1");				// exact=y
		fxt.Test_match("I"	, "1");				// upper=y
		fxt.Test_match("İ"	, "1");				// utf_8=y; note that "i" matches "İ" b/c hash is case-insensitive and "İ" lower-cases to "i"; DATE:2015-09-07
	}
	@Test  public void Utf8_asymmetric_multiple() {	// PURPOSE: problems in original implementation of Hash_adp_bry and uneven source / target counts;
		fxt.Init_add(Bry_.new_u8("İİ")	, "1");
		fxt.Test_match("İİ"	, "1");				// exact
		fxt.Test_match("ii"	, "1");				// lower
		fxt.Test_match("İi"	, "1");				// mixed
		fxt.Test_match("iİ"	, "1");				// mixed
	}
	@Test  public void Utf8_asymmetric_upper() {	// PURPOSE: "İ" and "I" should co-exist; see Btrie_u8_itm and called_by_match
		fxt.Init_add(Bry_.new_u8("İ")	, "1");
		fxt.Init_add(Bry_.new_u8("I")	, "1");
		fxt.Test_match("İ"	, "1");				// exact
		fxt.Test_match("I"	, "1");				// exact
		fxt.Test_match("i"	, "1");				// lower
	}
	@Test  public void Utf8_asymmetric_symbols() {	// PURPOSE: test Hash_adp_bry and multi-byte syms (chars that will never be cased)
		fxt.Init_add(Bry_.new_u8("a＿b")	, "1");
		fxt.Test_match("a＿b"	, "1");			// exact: len=3
		fxt.Test_match("a†b"	, null);		// diff : len=3
		fxt.Test_match("a±b"	, null);		// diff : len=2
		fxt.Test_match("a_b"	, null);		// diff : len=1
	}
}
class Btrie_u8_mgr_fxt {
	private Btrie_u8_mgr trie;
	public void Clear() {
		trie = Btrie_u8_mgr.new_(Xol_case_mgr_.U8());
	}
	public void Init_add(byte[] key, Object val) {trie.Add_obj(key, val);}
	public void Test_match_pos(String src_str, int bgn_pos, String expd) {
		byte[] src = Bry_.new_u8(src_str);
		Object actl = trie.Match_bgn_w_byte(src[bgn_pos], src, bgn_pos, src.length);
		Tfds.Eq(expd, actl, src_str);
	}
	public void Test_match(String src_str, String expd) {
		byte[] src = Bry_.new_u8(src_str);
		Object actl = trie.Match_bgn_w_byte(src[0], src, 0, src.length);
		Tfds.Eq(expd, actl, src_str);
	}
}
