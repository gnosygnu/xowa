/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.objects.strings;
import gplx.types.basics.utls.ObjectUtl;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.StringUtl;
import org.junit.Test;
public class StringUtlTest {
	private final StringUtlTstr fxt = new StringUtlTstr();
	@Test public void Len() {
		fxt.TestLen(""   , 0);
		fxt.TestLen("abc", 3);
	}
	@Test public void X() {
		GfoTstr.Write("k".compareTo("a"));
	}
	@Test public void Format() {
		fxt.TestFormat("empty fmt"                 , ""        , "");
		fxt.TestFormat("empty fmt w/ args"         , ""        , "", "a");
		fxt.TestFormat("no args"                   , "a"       , "a");
		fxt.TestFormat("args = 1"                  , "a"       , "{0}", "a");
		fxt.TestFormat("args = n"                  , "a + b"   , "{0} + {1}", "a", "b");
		fxt.TestFormat("escape {"                  , "{"       , "{{", 0);
		fxt.TestFormat("escape }"                  , "}"       , "}}", 0);
		fxt.TestFormat("nested"                    , "{a0c}"   , "{a{0}c}", 0);
		fxt.TestFormat("nested; invalid"           , "{a{b}c}" , "{a{b}c}", 0);
		fxt.TestFormat("out of bounds"             , "{1}"     , "{1}", "a");
		fxt.TestFormat("invalid arg"               , "{a} {b}" , "{a} {b}", 0);
		fxt.TestFormat("invalid and valid args"    , "{a}0{b}1", "{a}{0}{b}{1}", 0, 1);
		fxt.TestFormat("dangling"                  , "{0"      , "{0", 0);
	}
	@Test public void LimitToFirst() {
		TestLimitToFirst("abc", 0, "");
		TestLimitToFirst("abc", 1, "a");
		TestLimitToFirst("abc", 2, "ab");
		TestLimitToFirst("abc", 3, "abc");
		TestLimitToFirst("abc", 4, "abc");
		TestLimitToFirst("abc", -1);
	}
	void TestLimitToFirst(String s, int v, String expd) {GfoTstr.Eq(expd, StringUtl.LimitToFirst(s, v));}
	void TestLimitToFirst(String s, int v) {try {StringUtl.LimitToFirst(s, v);} catch (Exception exc) {GfoTstr.EqErr(exc, gplx.types.errs.Err.class); return;} GfoTstr.FailBcExpdError();}
	@Test public void DelBgn() {
		TestDelBgn("abc", 0, "abc");
		TestDelBgn("abc", 1, "bc");
		TestDelBgn("abc", 2, "c");
		TestDelBgn("abc", 3, "");
		TestDelBgn(null, 0);
		TestDelBgn("abc", 4);
	}
	void TestDelBgn(String s, int v, String expd) {GfoTstr.Eq(expd, StringUtl.DelBgn(s, v));}
	void TestDelBgn(String s, int v) {try {StringUtl.DelBgn(s, v);} catch (Exception exc) {GfoTstr.EqErr(exc, gplx.types.errs.Err.class); return;} GfoTstr.FailBcExpdError();}
	@Test public void DelEnd() {
		TestDelEnd("abc", 0, "abc");
		TestDelEnd("abc", 1, "ab");
		TestDelEnd("abc", 2, "a");
		TestDelEnd("abc", 3, "");
		TestDelEnd(null, 0);
		TestDelEnd("abc", 4);
	}
	void TestDelEnd(String s, int v, String expd) {GfoTstr.Eq(expd, StringUtl.DelEnd(s, v));}
	void TestDelEnd(String s, int v) {try {StringUtl.DelEnd(s, v);} catch (Exception exc) {GfoTstr.EqErr(exc, gplx.types.errs.Err.class); return;} GfoTstr.FailBcExpdError();}
	@Test public void DelEndIf() {
		TestDelEndIf("abc", "", "abc");
		TestDelEndIf("abc", "c", "ab");
		TestDelEndIf("abc", "bc", "a");
		TestDelEndIf("abc", "abc", "");
		TestDelEndIf("abc", "abcd", "abc");
		TestDelEndIf("abc", "ab", "abc");
		TestDelEndIf(null, "");
		TestDelEndIf("", null);
	}
	void TestDelEndIf(String s, String v, String expd) {GfoTstr.Eq(expd, StringUtl.DelEndIf(s, v));}
	void TestDelEndIf(String s, String v) {try {StringUtl.DelEndIf(s, v);} catch (Exception exc) {GfoTstr.EqErr(exc, gplx.types.errs.Err.class); return;} GfoTstr.FailBcExpdError();}
	@Test public void MidByPos() {
		TestMidByPos("abc", 0, 0, "");
		TestMidByPos("abc", 0, 1, "a");
		TestMidByPos("abc", 0, 2, "ab");
		TestMidByPos("abc", 0, 3, "abc");
		TestMidByPos("abc", 2, 3, "c");
		TestMidByPos("abc", 1, 5);
//            TestMidByPos("abc", 0, 4);
	}
	void TestMidByPos(String s, int bgn, int end, String expd) {GfoTstr.Eq(expd, StringUtl.Mid(s, bgn, end));}
	void TestMidByPos(String s, int bgn, int end) {try {StringUtl.Mid(s, bgn, end);} catch (Exception e) {GfoTstr.EqErr(e, gplx.types.errs.Err.class); return;} GfoTstr.FailBcExpdError();}

	@Test public void Count() {
		String text = "0 0 0";
		GfoTstr.Eq(3, StringUtl.Count(text, "0"));
	}
	@Test public void Has() {
		String text = "find word";
		GfoTstr.EqBoolY(StringUtl.Has(text, "word"));
		GfoTstr.EqBoolN(StringUtl.Has(text, "nothing"));
	}
	@Test public void Repeat() {
		GfoTstr.Eq("333", StringUtl.Repeat("3", 3));
	}
	@Test public void Split() {
		TestSplit("ab", " ", "ab");                            // no match -> return array with original input
		TestSplit("ab cd", " ", "ab", "cd");                // separator.length = 1
		TestSplit("ab+!cd", "+!", "ab", "cd");                // separator.length = 2
		TestSplit("ab+!cd+!ef", "+!", "ab", "cd", "ef");    // terms = 3
		TestSplit("ab+!cd+!", "+!", "ab", "cd", "");        // closing separator
		TestSplit("+!ab", "+!", "", "ab");                    // opening separator
		TestSplit("ab+cd+!ef", "+!", "ab+cd", "ef");        // ignore partial matches
		TestSplit("ab+!cd+", "+!", "ab", "cd+");            // ignore partial matches; end of String

		// boundary
		TestSplit("ab", "", "ab");                            // separator.length = 0 -> return array with input as only member
		TestSplit("", " ", "");                                // empty input -> return array with empty input

		// acceptance
		TestSplit("this\r\nis\na\rtest\r\n.", "\r\n", "this", "is\na\rtest", ".");
	}   void TestSplit(String text, String separator, String... expd) {GfoTstr.EqLines(expd, StringUtl.Split(text, separator));}
	@Test public void Concat_with_obj() {
		TestConcatWith_any("a|b", "|", "a", "b");                        // do not append final delimiter
		TestConcatWith_any("a||c", "|", "a", null, "c");                // null
		TestConcatWith_any("a|b", "|", ObjectUtl.Ary("a", "b"));            // pass array as arg
	}   void TestConcatWith_any(String expd, String delimiter, Object... array) {GfoTstr.Eq(expd, StringUtl.ConcatWithObj(delimiter, array));}
	@Test public void FindBwd() {    // WORKAROUND.CS:String.LastIndexOf returns -1 for multi-chars;
		TestFindRev("abc", "a", 0, 0);
		TestFindRev("abc", "ab", 0, 0);        // 2 chars
		TestFindRev("abc", "abc", 0, 0);    // 3 chars
		TestFindRev("ab", "abc", 0, -1);    // out of index error
		TestFindRev("ababab", "ab", 2, 2);    // make sure cs implementation doesn't pick up next
	}   void TestFindRev(String s, String find, int pos, int expd) {GfoTstr.Eq(expd, StringUtl.FindBwd(s, find, pos));}
	@Test public void Extract_after_bwd() {
		Extract_after_bwd_tst("a/b", "/", "b");
		Extract_after_bwd_tst("a/", "/", "");
		Extract_after_bwd_tst("a", "/", "");
	}   void Extract_after_bwd_tst(String src, String dlm, String expd) {GfoTstr.Eq(expd, StringUtl.ExtractAfterBwd(src, dlm));}
}
class StringUtlTstr {
	public void TestLen(String v, int expd) {
		GfoTstr.Eq(expd, StringUtl.Len(v));
	}
	public void TestFormat(String note, String expd, String fmt, Object... ary) {
		GfoTstr.Eq(expd, StringUtl.Format(fmt, ary), note);
	}
}
