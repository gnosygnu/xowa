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
package gplx;
import org.junit.*;
public class String__tst {
	@Test  public void Len() {
		tst_Len("", 0);
		tst_Len("abc", 3);
	}	void tst_Len(String v, int expd) {Tfds.Eq(expd, String_.Len(v), "Len");}

	@Test  public void LimitToFirst() {
		tst_LimitToFirst("abc", 0, "");
		tst_LimitToFirst("abc", 1, "a");
		tst_LimitToFirst("abc", 2, "ab");
		tst_LimitToFirst("abc", 3, "abc");
		tst_LimitToFirst("abc", 4, "abc");
		err_LimitToFirst("abc", -1);
	}
	void tst_LimitToFirst(String s, int v, String expd) {Tfds.Eq(expd, String_.LimitToFirst(s, v));}
	void err_LimitToFirst(String s, int v) {try {String_.LimitToFirst(s, v);} catch (Exception exc) {Tfds.Err_classMatch(exc, Err.class); return;} Tfds.Fail_expdError();}
	@Test  public void LimitToLast() {
		tst_LimitToLast("abc", 0, "");
		tst_LimitToLast("abc", 1, "c");
		tst_LimitToLast("abc", 2, "bc");
		tst_LimitToLast("abc", 3, "abc");
		tst_LimitToLast("abc", 4, "abc");
		err_LimitToLast("abc", -1);
	}
	void tst_LimitToLast(String s, int v, String expd) {Tfds.Eq(expd, String_.LimitToLast(s, v));}
	void err_LimitToLast(String s, int v) {try {String_.LimitToLast(s, v);} catch (Exception exc) {Tfds.Err_classMatch(exc, Err.class); return;} Tfds.Fail_expdError();}
	@Test  public void DelBgn() {
		tst_DelBgn("abc", 0, "abc");
		tst_DelBgn("abc", 1, "bc");
		tst_DelBgn("abc", 2, "c");
		tst_DelBgn("abc", 3, "");
		err_DelBgn(null, 0);
		err_DelBgn("abc", 4);
	}
	void tst_DelBgn(String s, int v, String expd) {Tfds.Eq(expd, String_.DelBgn(s, v));}
	void err_DelBgn(String s, int v) {try {String_.DelBgn(s, v);} catch (Exception exc) {Tfds.Err_classMatch(exc, Err.class); return;} Tfds.Fail_expdError();}
	@Test  public void DelBgnIf() {
		tst_DelBgnIf("abc", "", "abc");
		tst_DelBgnIf("abc", "a", "bc");
		tst_DelBgnIf("abc", "ab", "c");
		tst_DelBgnIf("abc", "abc", "");
		tst_DelBgnIf("abc", "abcd", "abc");
		tst_DelBgnIf("abc", "bcd", "abc");
		err_DelBgnIf(null, "abc");
		err_DelBgnIf("abc", null);
	}
	void tst_DelBgnIf(String s, String v, String expd) {Tfds.Eq(expd, String_.DelBgnIf(s, v));}
	void err_DelBgnIf(String s, String v) {try {String_.DelBgnIf(s, v);} catch (Exception exc) {Tfds.Err_classMatch(exc, Err.class); return;} Tfds.Fail_expdError();}
	@Test  public void DelEnd() {
		tst_DelEnd("abc", 0, "abc");
		tst_DelEnd("abc", 1, "ab");
		tst_DelEnd("abc", 2, "a");
		tst_DelEnd("abc", 3, "");
		err_DelEnd(null, 0);
		err_DelEnd("abc", 4);
	}
	void tst_DelEnd(String s, int v, String expd) {Tfds.Eq(expd, String_.DelEnd(s, v));}
	void err_DelEnd(String s, int v) {try {String_.DelEnd(s, v);} catch (Exception exc) {Tfds.Err_classMatch(exc, Err.class); return;} Tfds.Fail_expdError();}
	@Test  public void DelEndIf() {
		tst_DelEndIf("abc", "", "abc");
		tst_DelEndIf("abc", "c", "ab");
		tst_DelEndIf("abc", "bc", "a");
		tst_DelEndIf("abc", "abc", "");
		tst_DelEndIf("abc", "abcd", "abc");
		tst_DelEndIf("abc", "ab", "abc");
		err_DelEndIf(null, "");
		err_DelEndIf("", null);
	}
	void tst_DelEndIf(String s, String v, String expd) {Tfds.Eq(expd, String_.DelEndIf(s, v));}
	void err_DelEndIf(String s, String v) {try {String_.DelEndIf(s, v);} catch (Exception exc) {Tfds.Err_classMatch(exc, Err.class); return;} Tfds.Fail_expdError();}
	@Test  public void MidByPos() {
		tst_MidByPos("abc", 0, 0, "");
		tst_MidByPos("abc", 0, 1, "a");
		tst_MidByPos("abc", 0, 2, "ab");
		tst_MidByPos("abc", 0, 3, "abc");
		tst_MidByPos("abc", 2, 3, "c");
		err_MidByPos("abc", 1, 5);
//			err_MidByPos("abc", 0, 4);
	}
	void tst_MidByPos(String s, int bgn, int end, String expd) {Tfds.Eq(expd, String_.Mid(s, bgn, end));}
	void err_MidByPos(String s, int bgn, int end) {try {String_.Mid(s, bgn, end);} catch (Exception e) {Tfds.Err_classMatch(e, Err.class); return;} Tfds.Fail_expdError();}
	@Test  public void TrimEnd() {
		tst_TrimEnd("a", "a");
		tst_TrimEnd("a ", "a");
		tst_TrimEnd("a\t", "a");
		tst_TrimEnd("a\n", "a");
		tst_TrimEnd("a\r", "a");
		tst_TrimEnd("a\r\n \t", "a");
		tst_TrimEnd(" a", " a");
		tst_TrimEnd(null, null);
	}
	void tst_TrimEnd(String s, String expd) {Tfds.Eq(expd, String_.TrimEnd(s));}

	@Test  public void Count() {
		String text = "0 0 0";
		Tfds.Eq(3, String_.Count(text, "0"));
	}
	@Test  public void Has() {
		String text = "find word";
		Tfds.Eq_true(String_.Has(text, "word"));
		Tfds.Eq_false(String_.Has(text, "nothing"));
	}
	@Test  public void Repeat() {
		Tfds.Eq("333", String_.Repeat("3", 3));
	}
	@Test  public void Format() {
		tst_Format("", "");										// empty
		tst_Format("no args", "no args");						// no args
		tst_Format("0", "{0}", 0);								// one
		tst_Format("0 and 1", "{0} and {1}", 0, 1);				// many
		tst_Format("{", "{{", 0);								// escape bracketBgn
		tst_Format("}", "}}", 0);								// escape bracketEnd
		tst_Format("{a0c}", "{a{0}c}", 0);						// nested;
		tst_Format("{a{b}c}", "{a{b}c}", 0);					// invalid invalid
		tst_Format("{1}", "{1}", 1);							// invalid array index
		tst_Format("{a} {b}", "{a} {b}", 0);					// invalid many
		tst_Format("{a}0{b}1", "{a}{0}{b}{1}", 0, 1);			// invalid and valid
		tst_Format("{0", "{0", 0);								// invalid dangling
	}	void tst_Format(String expd, String fmt, Object... ary) {Tfds.Eq(expd, String_.Format(fmt, ary));}
	@Test  public void Split() {
		tst_Split("ab", " ", "ab");							// no match -> return array with original input
		tst_Split("ab cd", " ", "ab", "cd");				// separator.length = 1
		tst_Split("ab+!cd", "+!", "ab", "cd");				// separator.length = 2
		tst_Split("ab+!cd+!ef", "+!", "ab", "cd", "ef");	// terms = 3
		tst_Split("ab+!cd+!", "+!", "ab", "cd", "");		// closing separator
		tst_Split("+!ab", "+!", "", "ab");					// opening separator
		tst_Split("ab+cd+!ef", "+!", "ab+cd", "ef");		// ignore partial matches
		tst_Split("ab+!cd+", "+!", "ab", "cd+");			// ignore partial matches; end of String

		// boundary
		tst_Split("ab", "", "ab");							// separator.length = 0 -> return array with input as only member
		tst_Split("", " ", "");								// empty input -> return array with empty input

		// acceptance
		tst_Split("this\r\nis\na\rtest\r\n.", "\r\n", "this", "is\na\rtest", ".");
	}	void tst_Split(String text, String separator, String... expd) {Tfds.Eq_ary(expd, String_.Split(text, separator));}
	@Test  public void Concat_with_obj() {
		tst_ConcatWith_any("a|b", "|", "a", "b");						// do not append final delimiter
		tst_ConcatWith_any("a||c", "|", "a", null, "c");				// null
		tst_ConcatWith_any("a|b", "|", Object_.Ary("a", "b"));			// pass array as arg
	}	void tst_ConcatWith_any(String expd, String delimiter, Object... array) {Tfds.Eq(expd, String_.Concat_with_obj(delimiter, array));}
	@Test  public void Compare_byteAry() {
		tst_Compare_byteAry("a", "a", CompareAble_.Same);
		tst_Compare_byteAry("a", "b", CompareAble_.Less);
		tst_Compare_byteAry("b", "a", CompareAble_.More);
		tst_Compare_byteAry("ab", "ac", CompareAble_.Less);
		tst_Compare_byteAry("ac", "ab", CompareAble_.More);
		tst_Compare_byteAry("a", "ab", CompareAble_.Less);
		tst_Compare_byteAry("ab", "a", CompareAble_.More);
		tst_Compare_byteAry("101", "1-0-1", CompareAble_.More);			// NOTE: regular String_.Compare_as_ordinals returns Less in .NET, More in Java
		tst_Compare_byteAry("1-0-1", "101 (album)", CompareAble_.Less);	
	}	void tst_Compare_byteAry(String lhs, String rhs, int expd) {Tfds.Eq(expd, String_.Compare_byteAry(lhs, rhs));}
	@Test  public void FindBwd() {	// WORKAROUND.CS:String.LastIndexOf returns -1 for multi-chars; 
		tst_FindRev("abc", "a", 0, 0);
		tst_FindRev("abc", "ab", 0, 0);		// 2 chars
		tst_FindRev("abc", "abc", 0, 0);	// 3 chars
		tst_FindRev("ab", "abc", 0, -1);	// out of index error
		tst_FindRev("ababab", "ab", 2, 2);	// make sure cs implementation doesn't pick up next
	}	void tst_FindRev(String s, String find, int pos, int expd) {Tfds.Eq(expd, String_.FindBwd(s, find, pos));}
	@Test  public void Extract_after_bwd() {
		Extract_after_bwd_tst("a/b", "/", "b");
		Extract_after_bwd_tst("a/", "/", "");
		Extract_after_bwd_tst("a", "/", "");
	}	void Extract_after_bwd_tst(String src, String dlm, String expd) {Tfds.Eq(expd, String_.Extract_after_bwd(src, dlm));} 
}
