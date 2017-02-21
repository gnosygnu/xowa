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
package gplx.xowa.mediawiki.includes.utls; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import org.junit.*; import gplx.core.tests.*; import gplx.core.btries.*;
public class Php_str___tst {
	private final    Php_str___fxt fxt = new Php_str___fxt();
	@Test  public void Strspn_fwd__byte() {
		fxt.Test__strspn_fwd__byte("aaaaab", Byte_ascii.Ltr_a, 0, -1, 5);	// basic
		fxt.Test__strspn_fwd__byte("aaaaab", Byte_ascii.Ltr_a, 1, -1, 4);	// bgn
		fxt.Test__strspn_fwd__byte("aaaaab", Byte_ascii.Ltr_a, 1,  2, 2);	// max
	}
	@Test  public void Strspn_fwd__space_or_tab() {
		fxt.Test__strspn_fwd__space_or_tab("     a", 0, -1, 5);	// basic
		fxt.Test__strspn_fwd__space_or_tab("     a", 1, -1, 4);	// bgn
		fxt.Test__strspn_fwd__space_or_tab("     a", 1,  2, 2);	// max
	}
	@Test  public void Strspn_bwd__byte() {
		fxt.Test__strspn_bwd__byte("aaaaab", Byte_ascii.Ltr_a, 5, -1, 5);	// basic
		fxt.Test__strspn_bwd__byte("aaaaab", Byte_ascii.Ltr_a, 4, -1, 4);	// bgn
		fxt.Test__strspn_bwd__byte("aaaaab", Byte_ascii.Ltr_a, 4,  2, 2);	// max
	}
	@Test  public void Strspn_bwd__space_or_tab() {
		fxt.Test__strspn_bwd__space_or_tab("     a", 5, -1, 5);	// basic
		fxt.Test__strspn_bwd__space_or_tab("     a", 4, -1, 4);	// bgn
		fxt.Test__strspn_bwd__space_or_tab("     a", 4,  2, 2);	// max
	}
	@Test   public void Substr__bgn_is_neg() {
		fxt.Test__substr("abcde"                   , -1, "e");
		fxt.Test__substr("abcde"                   , -3, -1, "cd");
	}
	@Test   public void Strtr() {
		fxt.Init__strtr_by_trie("01", "89", "02", "79");
		fxt.Test__strtr_by_trie("abc"           , "abc");                 // found=none
		fxt.Test__strtr_by_trie("ab_01_cd"      , "ab_89_cd");            // found=one
		fxt.Test__strtr_by_trie("ab_01_cd_02_ef", "ab_89_cd_79_ef");      // found=many
		fxt.Test__strtr_by_trie("01_ab"         , "89_ab");               // BOS
		fxt.Test__strtr_by_trie("ab_01"         , "ab_89");               // EOS
	}
}
class Php_str___fxt {
	public void Test__strspn_fwd__byte(String src_str, byte find, int bgn, int max, int expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		Gftest.Eq__int(expd, Php_str_.Strspn_fwd__byte(src_bry, find, bgn, max, src_bry.length));
	}
	public void Test__strspn_fwd__space_or_tab(String src_str, int bgn, int max, int expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		Gftest.Eq__int(expd, Php_str_.Strspn_fwd__space_or_tab(src_bry, bgn, max, src_bry.length));
	}
	public void Test__strspn_bwd__byte(String src_str, byte find, int bgn, int max, int expd) {
		Gftest.Eq__int(expd, Php_str_.Strspn_bwd__byte(Bry_.new_u8(src_str), find, bgn, max));
	}
	public void Test__strspn_bwd__space_or_tab(String src_str, int bgn, int max, int expd) {
		Gftest.Eq__int(expd, Php_str_.Strspn_bwd__space_or_tab(Bry_.new_u8(src_str), bgn, max));
	}
	public void Test__substr(String src_str, int bgn, String expd) {Test__substr(src_str, bgn, String_.Len(src_str), expd);}
	public void Test__substr(String src_str, int bgn, int len, String expd) {
		Gftest.Eq__str(expd, Php_str_.Substr(Bry_.new_u8(src_str), bgn, len));
	}
	private Btrie_slim_mgr strtr_trie;
	public void Init__strtr_by_trie(String... kvs) {
		if (strtr_trie == null) strtr_trie = Btrie_slim_mgr.cs();
		int len = kvs.length;
		for (int i = 0; i < len; i += 2) {
			strtr_trie.Add_str_str(kvs[i], kvs[i + 1]);
		}
	}
	public void Test__strtr_by_trie(String src, String expd) {
		Bry_bfr tmp = Bry_bfr_.New();
		Btrie_rv trv = new Btrie_rv();
		Gftest.Eq__str(expd, Php_str_.Strtr(Bry_.new_u8(src), strtr_trie, tmp, trv));
	}
}
