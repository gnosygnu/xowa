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
package gplx.xowa.mediawiki; import gplx.*; import gplx.xowa.*;
import org.junit.*; import gplx.core.tests.*; import gplx.core.btries.*;
public class XophpString__tst {
	private final    XophpString__fxt fxt = new XophpString__fxt();
	@Test  public void Strspn_fwd__byte() {
		fxt.Test_strspn_fwd__byte("aaaaab", Byte_ascii.Ltr_a, 0, -1, 5);	// basic
		fxt.Test_strspn_fwd__byte("aaaaab", Byte_ascii.Ltr_a, 1, -1, 4);	// bgn
		fxt.Test_strspn_fwd__byte("aaaaab", Byte_ascii.Ltr_a, 1,  2, 2);	// max
	}
	@Test  public void Strspn_fwd__space_or_tab() {
		fxt.Test_strspn_fwd__space_or_tab("     a", 0, -1, 5);	// basic
		fxt.Test_strspn_fwd__space_or_tab("     a", 1, -1, 4);	// bgn
		fxt.Test_strspn_fwd__space_or_tab("     a", 1,  2, 2);	// max
	}
	@Test  public void Strspn_bwd__byte() {
		fxt.Test_strspn_bwd__byte("aaaaab", Byte_ascii.Ltr_a, 5, -1, 5);	// basic
		fxt.Test_strspn_bwd__byte("aaaaab", Byte_ascii.Ltr_a, 4, -1, 4);	// bgn
		fxt.Test_strspn_bwd__byte("aaaaab", Byte_ascii.Ltr_a, 4,  2, 2);	// max
	}
	@Test  public void Strspn_bwd__space_or_tab() {
		fxt.Test_strspn_bwd__space_or_tab("     a", 5, -1, 5);	// basic
		fxt.Test_strspn_bwd__space_or_tab("     a", 4, -1, 4);	// bgn
		fxt.Test_strspn_bwd__space_or_tab("     a", 4,  2, 2);	// max
	}
	@Test   public void Substr__bgn_is_neg() {
		fxt.Test_substr("abcde"                   , -1, "e");
		fxt.Test_substr("abcde"                   , -3, -1, "cd");
	}
	@Test   public void Strtr() {
		fxt.Init_strtr_by_trie("01", "89", "02", "79");
		fxt.Test_strtr_by_trie("abc"           , "abc");                 // found=none
		fxt.Test_strtr_by_trie("ab_01_cd"      , "ab_89_cd");            // found=one
		fxt.Test_strtr_by_trie("ab_01_cd_02_ef", "ab_89_cd_79_ef");      // found=many
		fxt.Test_strtr_by_trie("01_ab"         , "89_ab");               // BOS
		fxt.Test_strtr_by_trie("ab_01"         , "ab_89");               // EOS
	}
	@Test   public void Str_repeat() {
		fxt.Test_str_repeat("abc", 3, "abcabcabc");
		fxt.Test_str_repeat("", 3, "");
		fxt.Test_str_repeat("abc", 0, "");
	}
	@Test   public void Strpos() {
		fxt.Test__strpos("abc", "b", 0, 1);
		fxt.Test__strpos("abc", "z", 0, XophpString_.strpos_NULL);
		fxt.Test__strpos("aba", "a", 1, 2);
		fxt.Test__strpos("aba", "a", -2, 2);
	}
	@Test  public void strspn() {
		fxt.Test__strspn("42 is the answer to the 128th question", fxt.Init__strspn_hash("1234567890"), 0, Int_.Min_value, 2);
		fxt.Test__strspn("foo", fxt.Init__strspn_hash("o"), 0, Int_.Min_value, 0);
		fxt.Test__strspn("foo", fxt.Init__strspn_hash("o"), 1, 2, 2);
		fxt.Test__strspn("foo", fxt.Init__strspn_hash("o"), 1, 1, 1);
	}
    @Test  public void rtrim() {
		// pad is 0, 1 char
		fxt.Test__rtrim("0100", "", "0100"); // empty pad returns String
		fxt.Test__rtrim("010", "0", "01"); // basic test; trim 1;
		fxt.Test__rtrim("0100", "0", "01"); // basic test; trim 2;
		fxt.Test__rtrim("01", "0", "01"); // nothing to trim

		// pad is 2+char
		fxt.Test__rtrim("10ab10", "01", "10ab"); // basic test
		fxt.Test__rtrim("10ab10", "34", "10ab10"); // nothing to trim
		fxt.Test__rtrim("10ab10", "010", "10ab"); // don't fail if repeated chars

		// pad has ..
		fxt.Test__rtrim("23ab23", "0..4", "23ab"); // basic test
		fxt.Test__rtrim("23ab23.", "0.4", "23ab23"); // single dot is not range
		fxt.Test__rtrim__fail("abc", "0..", ".. found but at end of String");
		fxt.Test__rtrim__fail("abc", "..0", ".. found but at start of String");
		fxt.Test__rtrim__fail("abc", "4..0", ".. found but next byte must be greater than previous byte");

		// PHP samples
		fxt.Test__rtrim("\t\tThese are a few words :) ...  ", " \t.", "\t\tThese are a few words :)");
		fxt.Test__rtrim("Hello World", "Hdle", "Hello Wor");
		fxt.Test__rtrim("\u0009Example String\n", "\u0000..\u001F", "\u0009Example String");

		// non breaking-space is "\xA0" or "\xC2\xA0" in utf-8, "µ" is "\xB5" or "\xC2\xB5" in utf-8 and "à" is "\xE0" or "\xC3\xA0" in utf-8
		// REF.MW:https://www.php.net/manual/en/function.trim.php
		fxt.Test__rtrim("\u00A0µ déjà\u00A0", "\u00A0", "\u00A0µ déj�");// NOTE: technically should be "...j\xC3", but String_.new_u8 ignores invalid bytes
	}
	@Test  public void ord() {
		fxt.Test__ord("a", 97); // 1 char
		fxt.Test__ord("abc", 97); // 2+ chars takes first
		fxt.Test__ord("", 0); // no chars returns 0
		fxt.Test__ord(null, 0); // null returns 0
	}
	@Test  public void Fmt() {
		fxt.Test__Fmt("a", "a"); // no keys
		fxt.Test__Fmt("a$", "a$"); // key at end
		fxt.Test__Fmt("ax", "a${x}", "x"); // curly
		fxt.Test__Fmt("ax", "a$x", "x"); // basic
		fxt.Test__Fmt("axyz", "a$xb$yc$zd", "x", "y", "z"); // multiple
		fxt.Test__Fmt("a$0b", "a$0b", "z"); // invalid identifier
		fxt.Test__Fmt("a0", "a$xyz0b", "0"); // long identifier
	}
}
class XophpString__fxt {
	public void Test_strspn_fwd__byte(String src_str, byte find, int bgn, int max, int expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		Gftest.Eq__int(expd, XophpString_.strspn_fwd__byte(src_bry, find, bgn, max, src_bry.length));
	}
	public void Test_strspn_fwd__space_or_tab(String src_str, int bgn, int max, int expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		Gftest.Eq__int(expd, XophpString_.strspn_fwd__space_or_tab(src_bry, bgn, max, src_bry.length));
	}
	public void Test_strspn_bwd__byte(String src_str, byte find, int bgn, int max, int expd) {
		Gftest.Eq__int(expd, XophpString_.strspn_bwd__byte(Bry_.new_u8(src_str), find, bgn, max));
	}
	public void Test_strspn_bwd__space_or_tab(String src_str, int bgn, int max, int expd) {
		Gftest.Eq__int(expd, XophpString_.strspn_bwd__space_or_tab(Bry_.new_u8(src_str), bgn, max));
	}
	public void Test_substr(String src_str, int bgn, String expd) {Test_substr(src_str, bgn, String_.Len(src_str), expd);}
	public void Test_substr(String src_str, int bgn, int len, String expd) {
		Gftest.Eq__str(expd, XophpString_.substr(Bry_.new_u8(src_str), bgn, len));
	}
	private Btrie_slim_mgr strtr_trie;
	public void Init_strtr_by_trie(String... kvs) {
		if (strtr_trie == null) strtr_trie = Btrie_slim_mgr.cs();
		int len = kvs.length;
		for (int i = 0; i < len; i += 2) {
			strtr_trie.Add_str_str(kvs[i], kvs[i + 1]);
		}
	}
	public void Test_strtr_by_trie(String src, String expd) {
		Bry_bfr tmp = Bry_bfr_.New();
		Btrie_rv trv = new Btrie_rv();
		Gftest.Eq__str(expd, XophpString_.strtr(Bry_.new_u8(src), strtr_trie, tmp, trv));
	}
	public void Test_str_repeat(String str, int count, String expd) {
		Gftest.Eq__str(expd, XophpString_.str_repeat(str, count));
	}
	public void Test__strpos(String haystack, String needle, int offset, int expd) {
		Gftest.Eq__int(expd, XophpString_.strpos(haystack, needle, offset));
	}
	public Hash_adp Init__strspn_hash(String mask) {return XophpString_.strspn_hash(mask);}
	public void Test__strspn(String subject, Hash_adp mask, int start, int length, int expd) {
		int actl = XophpString_.strspn(subject, mask, start, length);
		Gftest.Eq__int(expd, actl);
	}
	public void Test__rtrim(String str, String character_mask, String expd) {
		Gftest.Eq__str(expd, XophpString_.rtrim(str, character_mask));
	}
	public void Test__rtrim__fail(String str, String character_mask, String expd_exc) {
		try {
			XophpString_.rtrim(str, character_mask);
		} catch (Exception exc) {
			String actl_exc = Err_.Message_lang(exc);
			if (!String_.Has(actl_exc, expd_exc)) {
				Gftest.Fail("expected failure, but got this: " + actl_exc);
			}
			return;
		}
		Gftest.Fail("expected failure, but got none: " + character_mask);
	}
	public void Test__ord(String str, int expd) {
		Gftest.Eq__int(expd, XophpString_.ord(str));
	}
	public void Test__Fmt(String expd, String fmt, Object... args) {
		Gftest.Eq__str(expd, XophpString_.Fmt(fmt, args));
	}
}
