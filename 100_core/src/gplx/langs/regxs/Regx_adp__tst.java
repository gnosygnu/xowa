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
package gplx.langs.regxs;
import gplx.types.commons.lists.GfoListBase;
import gplx.types.basics.strings.bfrs.GfoStringBldr;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.ArrayUtl;
import org.junit.Test;
public class Regx_adp__tst {
	@Test public void Match() {
		tst_Match("a", "a", true);    // basic
		tst_Match("a", "b", false);    // matchNot
		tst_Match("a", "ab", true); // matchPart
		tst_Match("a\\+b", "a+b", true); // matchEscape
		tst_Match("[^a]", "b", true); // charSet_negate
	}   void tst_Match(String find, String input, boolean expd) {GfoTstr.EqObj(expd, Regx_adp_.Match(input, find));}
	@Test public void Match_all() {
		tst_Match_all("#REDIRECT [[Template:Error]]", "^\\p{Nd}*", 1);    // handle match = true but len = 0; DATE:2013-04-11
		tst_Match_all("a", "$", 1);                                        // $ should match once, not zero; DATE:2014-09-02
	}   void tst_Match_all(String input, String regx, int expd) {GfoTstr.EqObj(expd, Regx_adp_.new_(regx).Match_all(input, 0).length);}
	@Test public void Replace() {
		tst_Replace("ab", "a", "b", "bb");    // basic
		tst_Replace("ab", "c", "b", "ab");    // replaceNot
		tst_Replace("aba", "a", "b", "bbb");    // replaceMultiple
	}   void tst_Replace(String input, String find, String replace, String expd) {GfoTstr.EqObj(expd, Regx_adp_.Replace(input, find, replace));}
	@Test public void Match_WholeWord() {
		tst_WholeWord("a", "ab a", true);            // pass a
		tst_WholeWord("a", "ab c", false);            // fail ab
		tst_WholeWord("a", "a_", false);            // fail a_
		tst_WholeWord("[a]", "a [a] c", true);        // pass [a]
		tst_WholeWord("[a]", "a[a]c", false);        // fail a[a]c
	}   void tst_WholeWord(String regx, String text, boolean expd) {GfoTstr.EqObj(expd, Regx_adp_.Match(text, Regx_bldr.WholeWord(regx)));}
	@Test public void Match_As() {
		tst_Regx("public static [A-Za-z0-9_]+ as_\\(Object obj\\)", "public static Obj1 as_(Object obj) {return obj instanceof Obj1 ? (Obj1)obj : null;}", true);
		tst_Regx("public static [A-Za-z0-9_]+ as_\\(Object obj\\)", "public static boolean Asterisk(Object obj) {}", false);
	}   void tst_Regx(String regx, String text, boolean expd) {GfoTstr.EqObj(expd, Regx_adp_.Match(text, regx));}
	@Test public void Find() {
		tst_Matches("b", "a b c b a", match_(2, 1), match_(6, 1));
		tst_Matches("d", "a b c b a");
		tst_Matches("b", "a b c b a b b", matches_(2, 6, 10, 12));    // BUGFIX: multiple entries did not work b/c of += instead of +
	}
	@Test public void Groups() {
		tst_Groups("abc def ghi dz", "(d\\p{L}+)", "def", "def", "dz", "dz");
		tst_Groups("abc def", "(de)(g?)", "de", "de", ""); // NOTE: (g?) doesn't capture anything, but still add a group for it; DATE:2019-12-28
		tst_Groups("-123.456", "^-?(([0-9]+)(?:\\.([0-9]+))?)", "-123.456", "123.456", "123", "456"); // NOTE: -123.456 captured even though it's not part of a group; DATE:2019-12-28
	}
	Regx_match[] matches_(int... bgnAry) {
		int aryLen = ArrayUtl.Len(bgnAry);
		Regx_match[] rv = new Regx_match[aryLen];
		for (int i = 0; i < aryLen; i++)
			rv[i] = match_(bgnAry[i]);
		return rv;
	}
	Regx_match match_(int bgn) {return match_(bgn, 1);}
	Regx_match match_(int bgn, int len) {return new Regx_match(true, bgn, bgn + len, Regx_group.Ary_empty);}
	void tst_Matches(String find, String input, Regx_match... expd) {
		GfoListBase<Regx_match> actlList = Regx_adp_.Find_all(input, find);
		GfoTstr.EqLines(ToStr(expd), ToStr(actlList.ToAry(Regx_match.class)));
	}
	private String ToStr(Regx_match[] ary) {
		GfoStringBldr sb = new GfoStringBldr();
		for (int i = 0; i < ary.length; i++) {
			Regx_match cur = ary[i];
			String s = "bgn=" + cur.Find_bgn() + " len=" + cur.Find_len();
			sb.Add(s).AddCharNl();
		}
		return sb.ToStr();
	}
	void tst_Groups(String text, String regx, String... expd) {
		Regx_adp regx_mgr = Regx_adp_.new_(regx);
		Regx_match[] rslts = regx_mgr.Match_all(text, 0);
		GfoTstr.EqLines(expd, To_ary(rslts));
	}
	private String[] To_ary(Regx_match[] ary) {
		List_adp rv = List_adp_.New();
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Regx_match itm = ary[i];
			int cap_len = itm.Groups().length;
			for (int j = 0; j < cap_len; j++) {
				rv.Add(itm.Groups()[j].Val());
			}
		}
		return rv.ToStrAry();
	}
}
