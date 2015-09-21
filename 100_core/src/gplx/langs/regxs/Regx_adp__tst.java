/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.langs.regxs; import gplx.*; import gplx.langs.*;
import org.junit.*;
public class Regx_adp__tst implements TfdsEqListItmStr {
	@Test  public void Match() {
		tst_Match("a", "a", true);	// basic
		tst_Match("a", "b", false);	// matchNot
		tst_Match("a", "ab", true); // matchPart
		tst_Match("a\\+b", "a+b", true); // matchEscape
		tst_Match("[^a]", "b", true); // charSet_negate
	}	void tst_Match(String find, String input, boolean expd) {Tfds.Eq(expd, Regx_adp_.Match(input, find));}
	@Test  public void Match_all() {
		tst_Match_all("#REDIRECT [[Template:Error]]", "^\\p{Nd}*", 1);	// handle match = true but len = 0; DATE:2013-04-11
		tst_Match_all("a", "$", 1);										// $ should match once, not zero; DATE:2014-09-02
	}	void tst_Match_all(String input, String regx, int expd) {Tfds.Eq(expd, Regx_adp_.new_(regx).Match_all(input, 0).length);}
	@Test  public void Replace() {
		tst_Replace("ab", "a", "b", "bb");	// basic
		tst_Replace("ab", "c", "b", "ab");	// replaceNot
		tst_Replace("aba", "a", "b", "bbb");	// replaceMultiple
	}	void tst_Replace(String input, String find, String replace, String expd) {Tfds.Eq(expd, Regx_adp_.Replace(input, find, replace));}
	@Test  public void Match_WholeWord() {
		tst_WholeWord("a", "ab a", true);			// pass a
		tst_WholeWord("a", "ab c", false);			// fail ab
		tst_WholeWord("a", "a_", false);			// fail a_
		tst_WholeWord("[a]", "a [a] c", true);		// pass [a]
		tst_WholeWord("[a]", "a[a]c", false);		// fail a[a]c
	}	void tst_WholeWord(String regx, String text, boolean expd) {Tfds.Eq(expd, Regx_adp_.Match(text, Regx_bldr.WholeWord(regx)));}
	@Test  public void Match_As() {
		tst_Regx("public static [A-Za-z0-9_]+ as_\\(Object obj\\)", "public static Obj1 as_(Object obj) {return obj instanceof Obj1 ? (Obj1)obj : null;}", true);
		tst_Regx("public static [A-Za-z0-9_]+ as_\\(Object obj\\)", "public static boolean Asterisk(Object obj) {}", false);
	}	void tst_Regx(String regx, String text, boolean expd) {Tfds.Eq(expd, Regx_adp_.Match(text, regx));}
	@Test  public void Find() {
		tst_Matches("b", "a b c b a", match_(2, 1), match_(6, 1));
		tst_Matches("d", "a b c b a");
		tst_Matches("b", "a b c b a b b", matches_(2, 6, 10, 12));	// BUGFIX: multiple entries did not work b/c of += instead of +
	}
	@Test  public void Groups() {
		tst_Groups("abc def ghi dz", "(d\\p{L}+)", "def", "dz");
	}
	Regx_match[] matches_(int... bgnAry) {
		int aryLen = Array_.Len(bgnAry);
		Regx_match[] rv = new Regx_match[aryLen];
		for (int i = 0; i < aryLen; i++)
			rv[i] = match_(bgnAry[i]);
		return rv;
	}
	Regx_match match_(int bgn) {return match_(bgn, Int_.Min_value);}
	Regx_match match_(int bgn, int len) {return new Regx_match(true, bgn, bgn + len, Regx_group.Ary_empty);}
	void tst_Matches(String find, String input, Regx_match... expd) {
		List_adp expdList = Array_.To_list(expd);			
		List_adp actlList = Regx_adp_.Find_all(input, find);
		Tfds.Eq_list(expdList, actlList, this);
	}
	void tst_Groups(String text, String regx, String... expd) {
		Regx_adp regx_mgr = Regx_adp_.new_(regx);
		Regx_match[] rslts = regx_mgr.Match_all(text, 0);
		Tfds.Eq_ary_str(expd, To_ary(rslts));
	}
	String[] To_ary(Regx_match[] ary) {
		List_adp rv = List_adp_.new_();
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Regx_match itm = ary[i];
			int cap_len = itm.Groups().length;
			for (int j = 0; j < cap_len; j++) {
				rv.Add(itm.Groups()[j].Val());
			}
		}
		return rv.To_str_ary();
	}
	public String To_str(Object curObj, Object expdObj) {
		Regx_match cur = (Regx_match)curObj, expd = (Regx_match)expdObj;
		String rv = "bgn=" + cur.Find_bgn();
		if (expd != null && expd.Find_len() != Int_.Min_value) rv += " len=" + cur.Find_len();
		return rv;
	}
}
