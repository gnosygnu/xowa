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
package gplx.core.regxs; import gplx.*; import gplx.core.*;
import org.junit.*;
public class Gfo_pattern_tst {
	@Before public void init() {fxt.Clear();} private Gfo_pattern_itm_fxt fxt = new Gfo_pattern_itm_fxt();
	@Test  public void Compile() {
		fxt.Test_Compile("a"		, fxt.itm_text_("a"));
		fxt.Test_Compile("*"		, fxt.itm_wild_());
		fxt.Test_Compile("a*"		, fxt.itm_text_("a"), fxt.itm_wild_());
		fxt.Test_Compile("*a"		, fxt.itm_wild_(), fxt.itm_text_("a"));
		fxt.Test_Compile("*ab*"		, fxt.itm_wild_(), fxt.itm_text_("ab"), fxt.itm_wild_());
		fxt.Test_Compile(""			);
	}
	@Test  public void Match() {
		Gfo_pattern pattern = fxt.pattern_("abc");
		fxt.Test_Match_y(pattern, "abc");
		fxt.Test_Match_n(pattern, "ab", "a", "bc", "Abc", "");
	}
	@Test  public void Match_all() {
		Gfo_pattern pattern = fxt.pattern_("*");
		fxt.Test_Match_y(pattern, "a", "abc", "");
	}
	@Test  public void Match_bgn() {
		Gfo_pattern pattern = fxt.pattern_("abc*");
		fxt.Test_Match_y(pattern, "abc", "abcdef");
		fxt.Test_Match_n(pattern, "abd", "aabc", "");
	}
	@Test  public void Match_end() {
		Gfo_pattern pattern = fxt.pattern_("*abc");
		fxt.Test_Match_y(pattern, "abc", "xyzabc");
		fxt.Test_Match_n(pattern, "abcd", "");
	}
}
class Gfo_pattern_itm_fxt {
	public void Clear() {}
	public Gfo_pattern pattern_(String raw) {return new Gfo_pattern(Bry_.new_utf8_(raw));}
	public void Test_Match_y(Gfo_pattern pattern, String... itms) {Test_Match(pattern, itms, Bool_.Y);}
	public void Test_Match_n(Gfo_pattern pattern, String... itms) {Test_Match(pattern, itms, Bool_.N);}
	private void Test_Match(Gfo_pattern pattern, String[] itms, boolean expd) {
		int len = itms.length;
		for (int i = 0; i < len; i++) {
			String itm = itms[i];
			Tfds.Eq(expd, pattern.Match(Bry_.new_utf8_(itm)), "pattern={0} itm={1} expd={2}", String_.new_utf8_(pattern.Raw()), itm, expd);
		}
	}
	public Gfo_pattern_itm_wild itm_wild_() {return Gfo_pattern_itm_wild._;}
	public Gfo_pattern_itm_text itm_text_(String raw) {
		Gfo_pattern_itm_text rv = new Gfo_pattern_itm_text();
		byte[] bry = Bry_.new_utf8_(raw);
		rv.Compile(bry, 0, bry.length);
		return rv;
	}
	public void Test_Compile(String raw, Gfo_pattern_itm... expd) {
		Gfo_pattern_itm[] actl = Gfo_pattern_itm_.Compile(Bry_.new_utf8_(raw));
		Tfds.Eq(Ary_xto_str(expd), Ary_xto_str(actl));
	}
	private static String Ary_xto_str(Gfo_pattern_itm[] ary) {
		int len = ary.length;
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < len; i++) {
			if (i != 0) sb.Add_char_nl();
			Gfo_pattern_itm itm = ary[i];
			itm.Xto_str(sb);
		}
		return sb.Xto_str_and_clear();
	}
}
