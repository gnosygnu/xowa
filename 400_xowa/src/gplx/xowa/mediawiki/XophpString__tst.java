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
package gplx.xowa.mediawiki;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import org.junit.*;
import gplx.core.btries.*;
public class XophpString__tst {
	private final XophpString__fxt fxt = new XophpString__fxt();
	@Test public void Strspn_fwd__byte() {
		fxt.Test_strspn_fwd__byte("aaaaab", AsciiByte.Ltr_a, 0, -1, 5);	// basic
		fxt.Test_strspn_fwd__byte("aaaaab", AsciiByte.Ltr_a, 1, -1, 4);	// bgn
		fxt.Test_strspn_fwd__byte("aaaaab", AsciiByte.Ltr_a, 1,  2, 2);	// max
	}
	@Test public void Strspn_fwd__space_or_tab() {
		fxt.Test_strspn_fwd__space_or_tab("     a", 0, -1, 5);	// basic
		fxt.Test_strspn_fwd__space_or_tab("     a", 1, -1, 4);	// bgn
		fxt.Test_strspn_fwd__space_or_tab("     a", 1,  2, 2);	// max
	}
	@Test public void Strspn_bwd__byte() {
		fxt.Test_strspn_bwd__byte("aaaaab", AsciiByte.Ltr_a, 5, -1, 5);	// basic
		fxt.Test_strspn_bwd__byte("aaaaab", AsciiByte.Ltr_a, 4, -1, 4);	// bgn
		fxt.Test_strspn_bwd__byte("aaaaab", AsciiByte.Ltr_a, 4,  2, 2);	// max
	}
	@Test public void Strspn_bwd__space_or_tab() {
		fxt.Test_strspn_bwd__space_or_tab("     a", 5, -1, 5);	// basic
		fxt.Test_strspn_bwd__space_or_tab("     a", 4, -1, 4);	// bgn
		fxt.Test_strspn_bwd__space_or_tab("     a", 4,  2, 2);	// max
	}
	@Test public void Substr__bgn_is_neg() {
		fxt.Test_substr("abcde"                   , -1, "e");
		fxt.Test_substr("abcde"                   , -3, -1, "cd");
	}
	@Test public void Strtr() {
		fxt.Init_strtr_by_trie("01", "89", "02", "79");
		fxt.Test_strtr_by_trie("abc"           , "abc");                 // found=none
		fxt.Test_strtr_by_trie("ab_01_cd"      , "ab_89_cd");            // found=one
		fxt.Test_strtr_by_trie("ab_01_cd_02_ef", "ab_89_cd_79_ef");      // found=many
		fxt.Test_strtr_by_trie("01_ab"         , "89_ab");               // BOS
		fxt.Test_strtr_by_trie("ab_01"         , "ab_89");               // EOS
	}
	@Test public void Str_repeat() {
		fxt.Test_str_repeat("abc", 3, "abcabcabc");
		fxt.Test_str_repeat("", 3, "");
		fxt.Test_str_repeat("abc", 0, "");
	}
	@Test public void Strpos() {
		fxt.Test__strpos("abc", "b", 0, 1);
		fxt.Test__strpos("abc", "z", 0, XophpString_.strpos_NULL);
		fxt.Test__strpos("aba", "a", 1, 2);
		fxt.Test__strpos("aba", "a", -2, 2);
	}
    @Test public void rtrim() {
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
    @Test public void ltrim() {
		// pad is 0, 1 char
		fxt.Test__ltrim("0010", "", "0010"); // empty pad returns String
		fxt.Test__ltrim("010", "0", "10"); // basic test; trim 1;
		fxt.Test__ltrim("0010", "0", "10"); // basic test; trim 2;
		fxt.Test__ltrim("10", "0", "10"); // nothing to trim

		// pad is 2+char
		fxt.Test__ltrim("10ab10", "01", "ab10"); // basic test
		fxt.Test__ltrim("10ab10", "34", "10ab10"); // nothing to trim
		fxt.Test__ltrim("10ab10", "010", "ab10"); // don't fail if repeated chars

		// pad has ..
		fxt.Test__ltrim("23ab23", "0..4", "ab23"); // basic test
		fxt.Test__ltrim(".23ab23", "0.4", "23ab23"); // single dot is not range

		// PHP samples
		fxt.Test__ltrim("\t\tThese are a few words :) ...  ", " \t.", "These are a few words :) ...  ");
		fxt.Test__ltrim("Hello World", "Hdle", "o World");
		fxt.Test__ltrim("\u0009Example String\n", "\u0000..\u001F", "Example String\n");
	}
    @Test public void trim() {
		// pad is 0, 1 char
		fxt.Test__trim("0010", "", "0010"); // empty pad returns String
		fxt.Test__trim("010", "0", "1"); // basic test; trim 1;
		fxt.Test__trim("00100", "0", "1"); // basic test; trim 2;
		fxt.Test__trim("10", "0", "1"); // nothing to trim

		// pad is 2+char
		fxt.Test__trim("10ab10", "01", "ab"); // basic test
		fxt.Test__trim("10ab10", "34", "10ab10"); // nothing to trim
		fxt.Test__trim("10ab10", "010", "ab"); // don't fail if repeated chars

		// pad has ..
		fxt.Test__trim("23ab23", "0..4", "ab"); // basic test
		fxt.Test__trim(".23ab23.", "0.4", "23ab23"); // single dot is not range

		// PHP samples
		fxt.Test__trim("\t\tThese are a few words :) ...  ", " \t.", "These are a few words :)");
		fxt.Test__trim("Hello World", "Hdle", "o Wor");
		fxt.Test__trim("\u0009Example String\n", "\u0000..\u001F", "Example String");
	}
	@Test public void ord() {
		fxt.Test__ord("a", 97); // 1 char
		fxt.Test__ord("abc", 97); // 2+ chars takes first
		fxt.Test__ord("", 0); // no chars returns 0
		fxt.Test__ord(null, 0); // null returns 0
	}
	@Test public void Fmt() {
		fxt.Test__Fmt("a", "a"); // no keys
		fxt.Test__Fmt("a$", "a$"); // key at end
		fxt.Test__Fmt("ax", "a${x}", "x"); // curly
		fxt.Test__Fmt("ax", "a$x", "x"); // basic
		fxt.Test__Fmt("axyz", "a$xb$yc$zd", "x", "y", "z"); // multiple
		fxt.Test__Fmt("a$0b", "a$0b", "z"); // invalid identifier
		fxt.Test__Fmt("a0", "a$xyz0b", "0"); // long identifier
	}
	@Test public void strrev() {
		fxt.Test__strrev("", "");
		fxt.Test__strrev("Hello world!", "!dlrow olleH");
		fxt.Test__strrev("☆❤world", "dlrow❤☆");
		fxt.Test__strrev("a¢€𤭢b", "b𤭢€¢a");
	}
	@Test public void str_repeat() {
		fxt.Test__str_repeat("-=", 10, "-=-=-=-=-=-=-=-=-=-=");
	}
	@Test public void strspn() {
		fxt.Test__strspn(1, "<-", "abc", "abc", -1);

		// PHP samples
		fxt.Test__strspn(2, "<-", "42 is the answer to the 128th question", "1234567890");
		fxt.Test__strspn(0, "<-", "foo", "o");
		fxt.Test__strspn(2, "<-", "foo", "o", 1, 2);
		fxt.Test__strspn(1, "<-", "foo", "o", 1, 1);
	}
	@Test public void strcspn() {
		fxt.Test__strcspn(3, "abc", "x", 0);
		fxt.Test__strcspn(3, "abc", "x", -5);

		// PHP samples
		fxt.Test__strcspn(0, "abcd", "apple");
		fxt.Test__strcspn(0, "abcd", "banana");
		fxt.Test__strcspn(2, "hello", "l");
		fxt.Test__strcspn(2, "hello", "world");
		fxt.Test__strcspn(5, "abcdhelloabcd", "abcd", -9);
		fxt.Test__strcspn(4, "abcdhelloabcd", "abcd", -9, -5);
	}
}
class XophpString__fxt {
	public void Test_strspn_fwd__byte(String src_str, byte find, int bgn, int max, int expd) {
		byte[] src_bry = BryUtl.NewU8(src_str);
		GfoTstr.Eq(expd, XophpString_.strspn_fwd__byte(src_bry, find, bgn, max, src_bry.length));
	}
	public void Test_strspn_fwd__space_or_tab(String src_str, int bgn, int max, int expd) {
		byte[] src_bry = BryUtl.NewU8(src_str);
		GfoTstr.Eq(expd, XophpString_.strspn_fwd__space_or_tab(src_bry, bgn, max, src_bry.length));
	}
	public void Test_strspn_bwd__byte(String src_str, byte find, int bgn, int max, int expd) {
		GfoTstr.Eq(expd, XophpString_.strspn_bwd__byte(BryUtl.NewU8(src_str), find, bgn, max));
	}
	public void Test_strspn_bwd__space_or_tab(String src_str, int bgn, int max, int expd) {
		GfoTstr.Eq(expd, XophpString_.strspn_bwd__space_or_tab(BryUtl.NewU8(src_str), bgn, max));
	}
	public void Test_substr(String src_str, int bgn, String expd) {Test_substr(src_str, bgn, StringUtl.Len(src_str), expd);}
	public void Test_substr(String src_str, int bgn, int len, String expd) {
		GfoTstr.Eq(expd, XophpString_.substr(BryUtl.NewU8(src_str), bgn, len));
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
		BryWtr tmp = BryWtr.New();
		Btrie_rv trv = new Btrie_rv();
		GfoTstr.Eq(expd, XophpString_.strtr(BryUtl.NewU8(src), strtr_trie, tmp, trv));
	}
	public void Test_str_repeat(String str, int count, String expd) {
		GfoTstr.Eq(expd, XophpString_.str_repeat(str, count));
	}
	public void Test__strpos(String haystack, String needle, int offset, int expd) {
		GfoTstr.Eq(expd, XophpString_.strpos(haystack, needle, offset));
	}
	public Hash_adp Init__strspn_hash(String mask) {return XophpString_.strspn_hash(mask);}
	public void Test__strspn(int expd, String ignore, String subject, String mask)            {Test__strspn(expd, ignore, subject, mask, IntUtl.Zero, IntUtl.Zero);}
	public void Test__strspn(int expd, String ignore, String subject, String mask, int start) {Test__strspn(expd, ignore, subject, mask,     start, IntUtl.Zero);}
	public void Test__strspn(int expd, String ignore, String subject, String mask, int start, int length) {
		int actl = XophpString_.strspn(subject, XophpString_.strspn_hash(mask), start, length);
		GfoTstr.Eq(expd, actl);
	}
	public void Test__rtrim(String str, String character_mask, String expd) {
		GfoTstr.Eq(expd, XophpString_.rtrim(str, character_mask));
	}
	public void Test__rtrim__fail(String str, String character_mask, String expd_exc) {
		try {
			XophpString_.rtrim(str, character_mask);
		} catch (Exception exc) {
			String actl_exc = ErrUtl.Message(exc);
			if (!StringUtl.Has(actl_exc, expd_exc)) {
				GfoTstr.Fail("expected failure, but got this: " + actl_exc);
			}
			return;
		}
		GfoTstr.Fail("expected failure, but got none: " + character_mask);
	}
	public void Test__ltrim(String str, String character_mask, String expd) {
		GfoTstr.Eq(expd, XophpString_.ltrim(str, character_mask));
	}
	public void Test__trim(String str, String character_mask, String expd) {
		GfoTstr.Eq(expd, XophpString_.trim(str, character_mask));
	}
	public void Test__ord(String str, int expd) {
		GfoTstr.Eq(expd, XophpString_.ord(str));
	}
	public void Test__Fmt(String expd, String fmt, Object... args) {
		GfoTstr.Eq(expd, XophpString_.Fmt(fmt, args));
	}
	public void Test__strrev(String src, String expd) {
		GfoTstr.Eq(expd, XophpString_.strrev(src));
	}
	public void Test__str_repeat(String input, int multiplier, String expd) {
		GfoTstr.Eq(expd, XophpString_.str_repeat(input, multiplier));
	}
	public void Test__strcspn(int expd, String subject, String mask)            {Test__strcspn(expd, subject, mask, IntUtl.Zero, IntUtl.Zero);}
	public void Test__strcspn(int expd, String subject, String mask, int start) {Test__strcspn(expd, subject, mask,     start, IntUtl.Zero);}
	public void Test__strcspn(int expd, String subject, String mask, int start, int length) {
		GfoTstr.Eq(expd, XophpString_.strcspn(subject, XophpString_.strspn_hash(mask), start, length));
	}
}
